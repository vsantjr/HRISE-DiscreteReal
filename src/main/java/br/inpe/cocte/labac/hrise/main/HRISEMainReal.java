package br.inpe.cocte.labac.hrise.main;


import br.inpe.cocte.labac.hrise.core.CreateAlgorithms;
import br.inpe.cocte.labac.hrise.core.Initialisation;
import br.inpe.cocte.labac.hrise.jhelper.util.ProblemFactory;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.EpsilonCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.HypervolumeCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.IgdCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.SpreadCalculator;
import br.inpe.cocte.labac.hrise.qualityreal.QualityIndicatorsRealProblems;
import br.inpe.cocte.labac.hrise.reinforcement.BalancedExploitationExploration;
import br.inpe.cocte.labac.hrise.reinforcement.LQValue;
import br.inpe.cocte.labac.hrise.reinforcement.MoveAcceptanceMethods;
import br.inpe.cocte.labac.hrise.reinforcement.PrevPerformance;
import br.inpe.cocte.labac.hrise.reinforcement.SelectionMethods;
import br.inpe.cocte.labac.hrise.util.LogRunningLLH;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;
import br.inpe.cocte.labac.hrise.util.ProblemsWrapper;
import br.inpe.cocte.labac.hrise.util.SaveFiles;
import br.inpe.cocte.labac.hrise.util.StatEvalSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.JMException;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;


/**
 * This is the class which implements the HRISE family algorithms (HRISE_R, HRISE_M, and HRMA) as described in [1]. HRISE_M and HRISE_R
 * embed a heuristic selection method based on roulette wheel supported by Reinforcement Learning followed by a balanced exploitation/exploration
 * procedure. Moreover, they use a two-level move acceptance strategy: Only Improving plus a group-decision framework where several move 
 * acceptance methods are considered. Within the group-decision framework, HRISE_R relies on the responsibility rule where a single move 
 * acceptance method takes the responsibility/authority for the decision to accept a population, while HRISE_M is based on the majority rule 
 * which counts the votes for the accept/reject decision, and the majority leads to the final decision. HRMA is a simpler version of the two 
 * previous selection hyper-heuristics where there is a simple random heuristic selection method. There is still a two-level move acceptance 
 * approach with Only Improving but now there exists a random choice among the available move acceptance methods under the group decision-making: 
 * hence the one chosen decides to accept/reject a population.
 *  ed as LOFx), 3 are for WhatsApp (identied as WUPx)1 is for YouTube (identied as YOUx)
 *[1] SANTIAGO JÚNIOR, VALDIVINO ALEXANDRE DE ; ÖZCAN, ENDER ; DE CARVALHO, VINICIUS RENAN. Hyper-Heuristics based on Reinforcement Learning,
 * Balanced Heuristic Selection and Group Decision Acceptance. APPLIED SOFT COMPUTING, p. 1-48, 2020. Submitted (Pre-print, Revision 1).
 *
 * @author Valdivino Alexandre de Santiago Júnior
 */
public class HRISEMainReal<S extends Solution<?>> {

	
  public HRISEMainReal(String[] argspar) throws FileNotFoundException, ConfigurationException, JMException, IOException {
	
	  this.mainHRISEReal(argspar);
  }
  
    public void mainHRISEReal(String[] args) throws ConfigurationException, JMException, FileNotFoundException, IOException {
        String problemString, versionHH, opt;
        int m, k, l;
        int trials; // Number of trials
        int decisionPoints; // Number of decision points
        int maxIterations; // Number of iterations
        int populationSize = 100; // Population size
        int nonUnifBegin = 250; 
        int nonUnifSteady = 100;
        String[] algName = {"NSGAII", "IBEA", "SPEA2"};
        List<S> bestLLHPopInit = new ArrayList<>(); // Initialisation: population of the selected LLH 
        List<S> popAllH = new ArrayList<>(); // Auxiliary population
        List<S> pfKnown = new ArrayList<>(); // Known PF
        List<S> popFinalHH = new ArrayList<>(); // Final population
        boolean first; // Initialisation?
        double alpha;
        int netaa;
        String srcDir = ""; // Base Source Dir
        String destDir = ""; // Base Dest Dir
                
        if (args.length == 9) {
            trials = Integer.parseInt(args[0]);
            opt = args[1];
            versionHH = args[2];
            maxIterations = Integer.parseInt(args[3]);
            decisionPoints = Integer.parseInt(args[4]);
            alpha = Double.parseDouble(args[5]);
            netaa = Integer.parseInt(args[6]);
            srcDir = args[7];
            destDir = args[8];
             	
            ProblemsWrapper prw = new ProblemsWrapper(opt);
     	    l = prw.getL(); // Decision/DISTANCE variables
     	    m = prw.getM(); //  Number of objectives
     	    problemString = prw.getProblemString();
        } else {
        	  l = m = 0;
        	  problemString = "NONE";
        	  versionHH = "NONE";
        	  trials = 0;
        	  maxIterations = decisionPoints = 0;
        	  alpha = 0.0;
        	  netaa = 0;
        	  srcDir = "NONE";
        	  destDir = "NONE";
              System.out.println("#### Invalid Number of Arguments!");
        }
        
        /*
         * Handling dirs
         */
        FileUtils.deleteDirectory(new File("result"));
        FileUtils.deleteDirectory(new File("exec"));
        FileUtils.deleteDirectory(new File("pareto_fronts_known"));
                
        Logger.getLogger(HRISEMainReal.class.getName()).log(Level.INFO, "#### " + versionHH + ", Discrete Optimisation and Real Problems!"); 
        k = 2 * (m - 1); // POSITION VARIABLES
        Problem problem = ProblemFactory.getProblem(problemString, k, l, m);

        List<List<Double>> qualityIndicators = new ArrayList<List<Double>>(); // All quality indicators
        StatEvalSupport stindic = StatEvalSupport.getInstance(); // Record and save the indicators for the statistical evaluation
        /*
         * Support the Balanced Exploitation/Exploration Method
         */
        String prevPerf, beforeLLH;
        Map<String, PrevPerformance> allPrevPerf = new LinkedHashMap<String, PrevPerformance>();
        Map<String, Double> prevQualImprov = new LinkedHashMap<String, Double>();
        /*
         * Help the calculation of the Utilisation Rate
         */
        Map<String,Integer> cnQualSel = new LinkedHashMap<String,Integer>();
		cnQualSel.put("NSGAII", 0);
  		cnQualSel.put("IBEA", 0);
  		cnQualSel.put("SPEA2", 0);
  		/*
  		 * Help the Accepted Populations Analysis
  		 */
  		Map<String,Integer> cnQualAcc = new LinkedHashMap<String,Integer>();
  		cnQualAcc.put("NSGAII", 0);
  		cnQualAcc.put("IBEA", 0);
  		cnQualAcc.put("SPEA2", 0);
        
  		String filepfKnown;
  		String hhid = " ";
  		QualityIndicatorsRealProblems qireal = QualityIndicatorsRealProblems.getInstance(); 
  		  		
        for (int i = 0; i < trials; i++) { // ******* BEGIN -> TRIALS *******
          hhid = versionHH + "." + problem.getName() + "." + "t_" + i;	
          filepfKnown = " ";	
          first = true;	
          prevPerf = " ";
          beforeLLH = " ";
          allPrevPerf.put("NSGAII", new PrevPerformance(" ", -1));
  		  allPrevPerf.put("IBEA", new PrevPerformance(" ", -1));
  		  allPrevPerf.put("SPEA2", new PrevPerformance(" ", -1));
  		  prevQualImprov.put("NSGAII", 0.0);
  		  prevQualImprov.put("IBEA", 0.0);
  		  prevQualImprov.put("SPEA2", 0.0);
  		  List<String> befLLH = new ArrayList<String>();
 		  List<String> aftLLH = new ArrayList<String>();
  		  List<LogRunningLLH> qualityPopH = new ArrayList<LogRunningLLH>();
          PopulationHandler popHandler = new PopulationHandler();
          Set<Integer> indPop = new LinkedHashSet<Integer>();
          int popToCreate = 0;
          SelectionMethods sel = new SelectionMethods();
          MoveAcceptanceMethods moveac = MoveAcceptanceMethods.getInstance();
          BalancedExploitationExploration balance = BalancedExploitationExploration.getInstance(); 
          moveac.initialiseUtilityValues(algName, 0.5);
          Map<String, Double> allUt = moveac.getAllUtility();	
  		  boolean acceptPop = false;
          boolean changeItLLH = false;
          int currIterationsLLH = 0;
          int counterIterations = 0; 
          String selLLHInitialisation = " ";
          int iterToInit = 0;
          /*
           * Select number of iterations for the Initialisation
           */
          if (problem.getNumberOfVariables() < 24) {
       	   iterToInit = 5;
          } else {
       	   iterToInit = 10;
          }
         
          Initialisation qIni = new Initialisation();
          selLLHInitialisation = qIni.compLowInitialisationRPO(algName, populationSize, iterToInit, problem, true, popAllH, i);
          counterIterations += 3*iterToInit;
          System.out.println("#### " + versionHH + ", Initialisation - Selected LLH: " + selLLHInitialisation + 
       		  " -- Elapsed Iterations " + (maxIterations - counterIterations));
          int counterRep = 0;
         
          for (int j = 0; ((j < decisionPoints) && (counterIterations < maxIterations)); j++) { // ******* BEGIN -> DECISION POINTS *******
        	  String selectedLLH = null;
        	     
        	  if (j == 0) {
        	   	 selectedLLH = selLLHInitialisation;
            	 beforeLLH = selectedLLH;
          		 befLLH.add(selectedLLH);
          		 aftLLH.add(selectedLLH);
        	  } else {
        	     first = false;
        	     
        	     if ( (versionHH.equals("HRISE_M")) || (versionHH.equals("HRISE_R")) ) {
        	        selectedLLH = sel.selectLLHRW(allUt, j, decisionPoints); 
        	        beforeLLH = selectedLLH;
          		    befLLH.add(selectedLLH);
          		    if (j > 0) {
       				   String beLast =  befLLH.get(befLLH.size()-1);
       				   String bePrevLast = befLLH.get(befLLH.size()-2);
       				   String afLast = aftLLH.get(aftLLH.size()-1);
       				   if( !(beLast.equals(afLast)) 
       						&& (beLast.equals(bePrevLast))  
       						&& allPrevPerf.get(selectedLLH).getLLH().startsWith("NO")) {
       					       int value = balance.getNotImproveLLH(selectedLLH) - 1;
       					       balance.setNotImproveLLH(selectedLLH, value);
       				   }
       			    }
          		  /*
          		   * The main method related to the Balanced Exploitation/Exploration  
          		   */
          		    selectedLLH = balance.balExploitationExplorationNetaAcc(selectedLLH, allPrevPerf,netaa,prevQualImprov);
       		        allPrevPerf.put(beforeLLH, new PrevPerformance(" ", -1));
       		        aftLLH.add(selectedLLH);
        	     } else {
        	    	   if (versionHH.equals("HRMA")) {
        	    		 selectedLLH = algName[popHandler.getRandomSolution(3)]; // Random LLH Selection
        	    		 if (j > 0) {
        	    			 first = false;
        	    		 }	 
        	    	   }
        	     }
        	  }
        	          	  
        	  if (!first) {
        		 while (popAllH.size() < populationSize) {
        			popAllH.add(popAllH.get(popHandler.getRandomSolution(popAllH.size())));
        		 }	
        		 popToCreate = popAllH.size();
        	  } else {
        		 popToCreate = populationSize; // first execution
        	  }		 
        		 
        	  if (j >= 1) { 
        		 changeItLLH = true;
        	  }
        	  /*
        	   * The nonuniform iterations strategy.	 
        	   */
        	  if (!changeItLLH) {
        		 currIterationsLLH = nonUnifBegin; // Run this amount per LLH: 250 iterations       			 
        	  } else {
        		 currIterationsLLH = nonUniformIterations(qualityPopH, j, maxIterations, counterIterations, decisionPoints, counterRep, 
        					  nonUnifBegin, nonUnifSteady);
        	  }
        	      	
        	  counterIterations += currIterationsLLH;
        	  System.out.println("#### Pop To Create:  " + popToCreate + "  -  Current Iterations/LLH: " + currIterationsLLH +
        				 " - PopAllH Size:  " + popAllH.size() +  " - Current Iterations Global: " + counterIterations);
        	  /*
        	   * Create LLH and run it          		 
        	   */
        	  CreateAlgorithms cr = new CreateAlgorithms();	 
        	  Algorithm alg = cr.createAlg(selectedLLH, popToCreate, currIterationsLLH, problem, first, popAllH); // %%%% OUT Weighted MaxMin
        	  alg.run();
              List result = (List) alg.getResult();
              List<S> popH = result;
              /*
               * Known PF   
               */
              pfKnown.addAll(popH);
              if (!first) {
               	 pfKnown = popHandler.generateNonDominated(pfKnown, problem);
               	 pfKnown = popHandler.removeRepeatedSolutionsInteger(pfKnown);
              }
              filepfKnown = "pareto_fronts_known/"+
                		 versionHH+"."+problem.getName()+"."+problem.getNumberOfObjectives()+"D.t_"+i+".dp_"+j+
                		 ".pf";
              SaveFiles sPFK = new SaveFiles();
              sPFK.savePFKnown(filepfKnown, pfKnown);
                 
              qualityIndicators.add(HRISEMainReal.printResultsAsList(popH, problem, filepfKnown, false));
              LogRunningLLH runLLH = new LogRunningLLH();
              runLLH.setRunningLLH(selectedLLH);
              runLLH.setRunningQuality(HRISEMainReal.calculateSingleIndicator(popH, problem, "hypervolume", filepfKnown));
              qualityPopH.add(runLLH);
              
              /*
               * Two-level move acceptance strategy  
               */
              if (!first) {
               	 double qualityCurr = qualityPopH.get(qualityPopH.size()-1).getRunningQuality();
                 List<LQValue> allQualityLast = moveac.getAllQuality();
                 double qualityPrev = 0.0;
                 for (LQValue last: allQualityLast) {
                	qualityPrev = last.getQuality();
                 }
                 double qualityImp;
                 if ( qualityCurr == 0.0  ) {
                	qualityImp = -1.0;
                 } else {
                	if ( (qualityCurr > 0.0) && (qualityPrev == 0.0) )	{
                	   qualityImp = qualityCurr;
                    } else {       
                	   qualityImp = (qualityCurr - qualityPrev) / qualityPrev;
                    } 
                 }	 
                	 
               	 prevQualImprov.put(selectedLLH, qualityImp);
                 int maxDPAcc = (int) Math.floor(1.5*decisionPoints);
                 
                 /*
                  * Two-level move acceptance strategy: Only Improving (level 1)  
                  */	 
               	 if (qualityImp >= 0.000075) { // orig = 0.0075
                	popAllH.clear(); 
                    popAllH.addAll(popH);
                    LQValue lqin = new LQValue();
                    lqin.setLLH(selectedLLH);
                    lqin.setQuality(HRISEMainReal.calculateSingleIndicator(popAllH, problem, "hypervolume", filepfKnown));
                    
                    /*
                     * Two-level move acceptance strategy: Group-decision acceptance (level 2)  
                     */
                    acceptPop = moveac.selectHyperMoveAcceptanceAlpha(versionHH, lqin, (j+1), maxDPAcc,alpha);
                 	if (acceptPop) {
                       prevPerf = "YES"+selectedLLH;
                       moveac.setCurrentQuality(lqin);
                       popFinalHH = popHandler.updatePopulation(popFinalHH, popAllH, populationSize);
                   	} else {
                       prevPerf = "NO"+selectedLLH;
                       counterRep++;
                    }	 
                 } else {
                       prevPerf = "NO"+selectedLLH;
                       moveac.penaliseUtilityValue(selectedLLH, (j+1), maxDPAcc);
                       counterRep++;
                 } 
                         
                 popAllH.clear();
                 popAllH.addAll(popFinalHH);
              } else { // The first decision point
                 popAllH.clear(); 
                 popAllH.addAll(popH);
                 LQValue lqinFst = new LQValue();
                 lqinFst.setLLH(selectedLLH);
                 lqinFst.setQuality(HRISEMainReal.calculateSingleIndicator(popAllH, problem, "hypervolume", filepfKnown)); 
                 moveac.setCurrentQuality(lqinFst);
                 popFinalHH.clear();
                 popFinalHH.addAll(popAllH);
                 prevPerf = "YES"+selectedLLH;
              }
                 
              PrevPerformance pp = new PrevPerformance(prevPerf,j);
      		  if (balance.getStartOver()) {
      			 allPrevPerf.put("NSGAII", new PrevPerformance(" ", -1));
      		     allPrevPerf.put("IBEA", new PrevPerformance(" ", -1));
      		     allPrevPerf.put("SPEA2", new PrevPerformance(" ", -1));
      		  } else {
      		     allPrevPerf.put(selectedLLH, pp);
      		  }
                 
              indPop.clear();    
        	 
              Logger.getLogger(HRISEMainReal.class.getName()).log(Level.INFO, "#### " + versionHH + " -- TRIAL: " + (i+1) + "  --  DECISION POINT: " + (j+1) + 
            		  "  --  PROBLEM INSTANCE: " + problem.getName());
              Logger.getLogger(HRISEMainReal.class.getName()).log(Level.INFO, "## Selected LLH: " + alg.getName() + " / " + selectedLLH);
          } // ******* END -> DECISION POINTS *******
          
          /*
           * Help the calculation of the Utilisation Rate
           */
          for(LogRunningLLH spot : qualityPopH){
    		  switch(spot.getRunningLLH()) {
    	            case "NSGAII" :
    	          	   cnQualSel.put("NSGAII", cnQualSel.get("NSGAII")+1);
    	               break;
    	            case "IBEA" :
    	           	   cnQualSel.put("IBEA", cnQualSel.get("IBEA")+1);
    	               break;   
    	            case "SPEA2" :
    	           	   cnQualSel.put("SPEA2", cnQualSel.get("SPEA2")+1);
    	               break;    
    	            default :
    	               System.out.println("Invalid LLH");
    	  	  }
    	  }	
          
          qualityPopH.clear();
          popAllH.clear();
          indPop.clear();
          
          if (popFinalHH.size() > populationSize) {
			  int numberInd = 0;
			  while (numberInd < populationSize) {
				 boolean notInSet = indPop.add(popHandler.getRandomSolution(popFinalHH.size()));
				 if (notInSet)
					numberInd++;
			  }
		      List<S> partAllArchive = new ArrayList<>();
			  List<Integer> indPopAsList = new ArrayList<Integer>(indPop);
			  for (int ind = 0 ; ind < indPopAsList.size(); ind++) {
				 partAllArchive.add(popFinalHH.get(indPopAsList.get(ind)));
			  }
			  popFinalHH.clear();
			  popFinalHH.addAll(partAllArchive);
	      }
          indPop.clear();
                    
          popFinalHH = popHandler.generateNonDominated(popFinalHH, problem);
          popFinalHH = popHandler.removeRepeatedSolutionsInteger(popFinalHH); 
          System.out.println("PopFinalHH -- Size: "+ popFinalHH.size() + "  -- Indicators: " +  HRISEMainReal.printResults(popFinalHH, problem, filepfKnown));
          System.out.println("#########");
          
          SaveFiles sFV = new SaveFiles();
          sFV.saveFunVar(i, versionHH, problem, popFinalHH);
   		  qireal.addPFKnown(hhid, popFinalHH);
   		  HRISEMainReal.printResultsAsList(popFinalHH, problem, filepfKnown, true);
   		  
   		  /*
           * Help the calculation of the Accepted Population Analysis
           */
   		  List<LQValue> bef = moveac.getAllQuality();
   		  for (LQValue lbef: bef) {
		      System.out.println("#### All Quality Indicators: -- LLH: " + lbef.getLLH() + " -- Quality: " + lbef.getQuality());
		   	  switch(lbef.getLLH()) {
		         case "NSGAII" :
		            cnQualAcc.put("NSGAII", cnQualAcc.get("NSGAII")+1);
		            break;
		         case "IBEA" :
		           	cnQualAcc.put("IBEA", cnQualAcc.get("IBEA")+1);
		            break;   
		         case "SPEA2" :
		           	cnQualAcc.put("SPEA2", cnQualAcc.get("SPEA2")+1);
		            break;    
		         default :
		             System.out.println("#### Invalid LLH!");
		  	  }
		  }
   		  prevPerf = " ";
   		  first = true;
          bestLLHPopInit.clear();
          popAllH.clear();
          popFinalHH.clear();
          pfKnown.clear();
          filepfKnown = " ";
          moveac.initialiseAllDataMoveAcceptance();
          balance.initialiseAllDataBalanced();
          System.out.println("######## END OF TRIAL: " + (i+1) + " #######");
        } // ******* END -> TRIALS *******
       
        System.out.println("........... Copying Results: P1 .......................................................................");
		copyResultDirectory(srcDir + "/result",
				            destDir + "/ALLRES/" + problem.getName() + "_" + problem.getNumberOfObjectives());
		
		/*
		 * Utilisation Rate and Accepted Population Analysis: final calculation and saving to CSV
		 */
		int cnGenSel = cnQualSel.get("NSGAII") + cnQualSel.get("IBEA") + cnQualSel.get("SPEA2"); 
	  	Map<String,Double> prQualSel = new LinkedHashMap<String,Double>();
		prQualSel.put("NSGAII", cnQualSel.get("NSGAII") / (double) cnGenSel );
		prQualSel.put("IBEA", cnQualSel.get("IBEA") / (double) cnGenSel );
		prQualSel.put("SPEA2", cnQualSel.get("SPEA2") / (double) cnGenSel );
			
		Map<String,Double> prQualAcc = new LinkedHashMap<String,Double>();
		prQualAcc.put("NSGAII", cnQualAcc.get("NSGAII") / (double) cnQualSel.get("NSGAII") );
		prQualAcc.put("IBEA", cnQualAcc.get("IBEA") / (double) cnQualSel.get("IBEA") );
		prQualAcc.put("SPEA2", cnQualAcc.get("SPEA2") / (double) cnQualSel.get("SPEA2") );
			
		String toCSV = problem.getName()+"_"+problem.getNumberOfObjectives()+",Sel,Acc,Psel,Pacc\n";
		for (int line = 0; line < algName.length; line++) {
				toCSV += algName[line] + "," +
							cnQualSel.get(algName[line]) + "," +
							cnQualAcc.get(algName[line]) + "," +
							prQualSel.get(algName[line]) + "," +
							prQualAcc.get(algName[line]) + "\n";
		}
		SaveFiles sCSV = new SaveFiles();
	   	sCSV.saveToCSV(versionHH,problem.getName()+"_"+problem.getNumberOfObjectives(),toCSV);
		
	   	System.out.println("--------- Copying Results: P2 ---------------------------------------------------------------------------");
		copyResultDirectory(srcDir + "/exec",
				            destDir + "/EXEC/");
		copyResultDirectory(srcDir + "/pareto_fronts_known",
	            destDir + "/PFKNOWN/HRISE/");	 
		  
	    stindic.clearAllIndicatorsStatEval();
    } // ******* END OF MAIN PROGRAM *******

    
    public static void copyResultDirectory(String sourceDir, String destDir) {
		File source = new File(sourceDir);
		File dest = new File(destDir);
		try {
		    FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
    
    
    public static String printResults(List archive, Problem problem, String pf) throws FileNotFoundException, IOException {
       	String strToReturn = "";
                              
        // HV: Convergence-Diversity
        PopulationHandler popHndList = new PopulationHandler();
        HypervolumeCalculator hyp = new HypervolumeCalculator(problem.getNumberOfObjectives(), pf);
        double hypValue = hyp.execute(archive);
            
        // IGD: Convergence-Diversity
        IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
        double igdValue = igd.execute(archive);
            
        // Epsilon: Convergence
        EpsilonCalculator eps=new EpsilonCalculator(problem.getNumberOfObjectives(), pf);
        double epsilonValue=eps.execute(archive);
            
        // GSPREAD/SPREAD: Diversity
        SpreadCalculator spread = new SpreadCalculator(problem.getNumberOfObjectives(), pf);
        double spreadValue = spread.execute(archive);
            
        strToReturn="Hyper: " + hypValue + " ## " + "IGD: " + igdValue + " ## " + "Epsilon: " + epsilonValue + " ## " + "GSPREAD: " + spreadValue + " ## ";
            
        return strToReturn;
    }

    
    public static List<Double> printResultsAsList(List archive, Problem problem, String pf, boolean recordStatEval) throws FileNotFoundException, IOException {
       	List<Double> qual = new ArrayList<Double>();
        StatEvalSupport stindicators = StatEvalSupport.getInstance();
                              
        // HV: Convergence-Diversity
        PopulationHandler popHnd = new PopulationHandler();
        HypervolumeCalculator hyp = new HypervolumeCalculator(problem.getNumberOfObjectives(), pf);
        double hypValue = hyp.execute(archive);
            
        // IGD: Convergence-Diversity
        IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
        double igdValue = igd.execute(archive);
            
        // Epsilon: Convergence
        EpsilonCalculator eps=new EpsilonCalculator(problem.getNumberOfObjectives(), pf);
        double epsilonValue=eps.execute(archive);
            
        // GSPREAD/SPREAD: Diversity
        SpreadCalculator spread = new SpreadCalculator(problem.getNumberOfObjectives(), pf);
        double spreadValue = spread.execute(archive);
            
        qual.add(hypValue);
        qual.add(igdValue);
        qual.add(epsilonValue);
        qual.add(spreadValue);
            
        if (recordStatEval) {
          stindicators.recordDataStatEval("HYPERVOLUME", hypValue);
    	  stindicators.recordDataStatEval("IGD", igdValue);
    	  stindicators.recordDataStatEval("EPSILON", epsilonValue);
    	  stindicators.recordDataStatEval("SPREAD", spreadValue);
        }
        
        return qual;
    }
    
   
    public static double calculateSingleIndicator(List archive, Problem problem, String indicator, String pf) throws FileNotFoundException, IOException {
      	double qual = -1.0;
        
        switch(indicator) {
	      case "hypervolume" :            
              // HV: Convergence-Diversity
              PopulationHandler popHndSI = new PopulationHandler();
              HypervolumeCalculator hyp = new HypervolumeCalculator(problem.getNumberOfObjectives(), pf);
              qual = hyp.execute(archive);
              break;
                                
          case "igd" :
              // IGD: Convergence-Diversity
              IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
              qual = igd.execute(archive);
              break;
                               
          case "epsilon" :
              // Epsilon: Convergence
              EpsilonCalculator eps=new EpsilonCalculator(problem.getNumberOfObjectives(), pf);
              qual = eps.execute(archive);
              break; 
             
          case "spread" :
              // GSPREAD/SPREAD: Diversity
              SpreadCalculator spread = new SpreadCalculator(problem.getNumberOfObjectives(), pf);
              qual = spread.execute(archive);
              break;
          
          default :
	          System.out.println("#### Invalid Indicator!");  
        }
    	  
        return qual;
    }
    
    
    
    public static int nonUniformIterations(List<LogRunningLLH> qualPop, int currDP, int maxIt, int cntIt, int dp, int crep, int nonBegin, int nonSteady) {
       	List<LogRunningLLH> qualPopLocal = new ArrayList<LogRunningLLH>();
   	    qualPopLocal.addAll(qualPop);
   	    int countLow = 0;
   	    int nextIterations;
   	    for(LogRunningLLH qpl : qualPopLocal){
		  if (qpl.getRunningQuality() <= 0.000075) {
				countLow++;
		  }
	    }
		  
   	    if (countLow > 1) {
   		  if ((maxIt - cntIt) >= nonBegin)
   		      nextIterations = nonBegin;
   		  else
   		      nextIterations = maxIt - cntIt;
   	    } else { // else 1
   		  if (currDP == 1) {
   			  nextIterations = nonBegin;
   		  } else { // else 2
   			  if ( crep >= (dp/3) ) {
   				 if ((maxIt - cntIt) >= nonBegin) //250
   		    		nextIterations = nonBegin;
   		    	 else
   		    		nextIterations = maxIt - cntIt;
   			  } else { // else 3
   			     if ((maxIt - cntIt) >= nonSteady) // 100
   			        nextIterations = nonSteady; // 100
   			     else
   				    nextIterations = maxIt - cntIt; 
   		      } //else 3
   		  } //else 2
   	    } // else 1
   	   	     	
   	    return nextIterations;
    }
    
}
