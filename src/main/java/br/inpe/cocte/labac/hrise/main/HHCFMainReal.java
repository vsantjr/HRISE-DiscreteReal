package br.inpe.cocte.labac.hrise.main;


import br.inpe.cocte.labac.hrise.core.CreateAlgorithms;
import br.inpe.cocte.labac.hrise.jhelper.util.ProblemFactory;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.AlgorithmEffortCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.EpsilonCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.HypervolumeCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.IgdCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.RniCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.SpreadCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.UDMetricHandler;
import br.inpe.cocte.labac.hrise.otherhhs.choiceFunction;
import br.inpe.cocte.labac.hrise.qualityreal.QualityIndicatorsRealProblems;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;
import br.inpe.cocte.labac.hrise.util.ProblemsWrapper;
import br.inpe.cocte.labac.hrise.util.QualityIndicatorsHandler;
import br.inpe.cocte.labac.hrise.util.SaveFiles;
import br.inpe.cocte.labac.hrise.util.StatEvalSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.JMException;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;


/**
 * This is the class which implements the HH-CF (Choice Function) algorithm as described in [1] and adapted by the authors in [2]. The 
 * heuristic selection method is based on a two-stage ranking scheme and four quality indicators: Algorithm Effort, Ratio of Nondominated
 * Individuals (RNI), hypervolume, and Uniform Distribution. This implementation is basically the same as accomplished by authors in [2] 
 * with a few modifications to embedded it within the HRISE framework.
 *
 *[1] M. Maashi, E. Ozcan, G. Kendall, A multi-objective hyper-heuristic based on choice function, Expert Systems with Applications 41 (9)
 * (2014) 4475 - 4493. doi:https://doi.org/10.1016/j.eswa.2013.12.050. URL http://www.sciencedirect.com/science/article/pii/S095741741400013X
 *
 *[2] W. Li, E. Ozcan, R. John, A learning automata-based multiobjective hyper-heuristic, IEEE Transactions on Evolutionary Computation 23 (1)
 * (2019) 59 - 73. doi:10.1109/TEVC.2017.2785346.
 *
 * @author Valdivino Alexandre de Santiago Júnior
 */
public class HHCFMainReal<S extends Solution<?>> {

	
  public HHCFMainReal(String[] argspar) throws FileNotFoundException, ConfigurationException, JMException, IOException {
	
	  this.mainHHCFReal(argspar);
  }
  
    public void mainHHCFReal(String[] args) throws ConfigurationException, JMException, FileNotFoundException, IOException {
        String problemString, versionHH, opt;
        int m, k, l;
        int trials; // Number of trials
        int decisionPoints; // Number of decision points
        int maxIterations; // Total number of iterations
        int populationSize = 100; // Population size
        String[] algName = {"NSGAII", "IBEA", "SPEA2"}; // LLHs
        List<S> pfKnown = new ArrayList<>(); // Known PF
        List<S> popAllH = new ArrayList<>(); // Auxiliary Population
        List<S> popFinalHH = new ArrayList<>(); // Final Population
        boolean first; // Initialisation?
        String srcDir = ""; // Base Source Dir
        String destDir = ""; // Base Dest Dir
                
        if (args.length == 7) {
           	trials = Integer.parseInt(args[0]);
            opt = args[1];
            versionHH = args[2];
            maxIterations = Integer.parseInt(args[3]);
            decisionPoints = Integer.parseInt(args[4]);
            srcDir = args[5];
            destDir = args[6];
                        
            ProblemsWrapper prw = new ProblemsWrapper(opt);
      	    l = prw.getL(); // Decision/DISTANCE variables. 
     	    m = prw.getM(); //  Number of objectives. 
     	    problemString = prw.getProblemString();
            
        	
        } else {
        	l = m = 0;
        	problemString = "NONE";
        	versionHH = "NONE";
        	trials = 0;
        	maxIterations = decisionPoints = 0;
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
                
        Logger.getLogger(HHCFMainReal.class.getName()).log(Level.INFO, "#### HH-CF, Discrete Optimisation and Real Problems!"); 
        k = 2 * (m - 1); // POSITION VARIABLES
        Problem problem = ProblemFactory.getProblem(problemString, k, l, m); // Create problem instance

        List<List<Double>> qualityIndicators = new ArrayList<List<Double>>(); // All quality indicators
        List<List<S>> popsInit = new ArrayList<List<S>>();
        StatEvalSupport stindic = StatEvalSupport.getInstance(); // Record and save the indicators for the statistical evaluation
        String filepfKnown;
        String hhid = " ";
        QualityIndicatorsRealProblems qireal = QualityIndicatorsRealProblems.getInstance(); 
        
        for (int i = 0; i < trials; i++) { // ******* BEGIN -> TRIALS *******
          hhid = versionHH + "." + problem.getName() + "." + "t_" + i;		
          filepfKnown = " ";	
          first = true;	
          PopulationHandler popHandler = new PopulationHandler();
          int popToCreate = 0;
          int currIterationsLLH;
          
          /*
           *  Time
           */
          long[] elapsedTime = new long[algName.length];
		  long[] lastInvokedTime = new long[algName.length];
		  long[] executionTimeArray = new long[algName.length];
		  for(int itim = 0; itim <algName.length; itim++){
				 elapsedTime[itim] = 0;					
		  }
         
		  /*
		   * Choice Function - Initialisation: BEGIN
		   */
		  int cntOnlyIni = 0;
		  currIterationsLLH = maxIterations/decisionPoints;
 	      if ((first) && versionHH.equals("HH-CF")) {
 	    	 System.out.println("#### HH-CF: Initialisation -- LLHs: " + algName[0] + " // " + algName[1] + " // " + algName[2]); 
 	    	 for (int cllh = 0; cllh < algName.length; cllh++) { // algName: [0] = NSGAII; [1] = IBEA; [2] = SPEA2
 	    		 CreateAlgorithms crin = new CreateAlgorithms();
 	    		 popToCreate = populationSize;
 	    		 lastInvokedTime[cllh] = System.currentTimeMillis();
 	    		 Algorithm algin = crin.createAlg(algName[cllh], popToCreate, currIterationsLLH, problem, first, popAllH); 
 	    		 long startTimein = System.currentTimeMillis(); // Record the execution time for each LLH
 	    		 algin.run();
 	    		 long executionTimein = System.currentTimeMillis() -  startTimein;
 	    		 executionTimeArray[cllh] = executionTimein;
 	    		 List resultin = (List) algin.getResult();
                 List<S> popHin = resultin;
                 popsInit.add(popHin);
                 pfKnown.addAll(popHin);
                 if (cntOnlyIni > 0) {
                   pfKnown = popHandler.generateNonDominated(pfKnown, problem);
                 }
                 pfKnown = popHandler.removeRepeatedSolutionsInteger(pfKnown);
                 filepfKnown = "pareto_fronts_known/"+
                 		 versionHH+"."+problem.getName()+"."+problem.getNumberOfObjectives()+"D.t_"+i+".dp_"+cntOnlyIni+
                 		 ".pf";
                 SaveFiles sPFK = new SaveFiles();
                 sPFK.savePFKnown(filepfKnown, pfKnown);
                 cntOnlyIni++;
                 qualityIndicators.add(HHCFMainReal.printResultsAsList(popHin, problem, false, popToCreate, executionTimeArray[cllh], filepfKnown));
 	    	 }
 	      }
 	       	       
 	      for(int cllh = 0; cllh < algName.length; cllh++){
			elapsedTime[cllh] = System.currentTimeMillis() - lastInvokedTime[cllh];
		  }
           	       
 	     /*
 	      * Quality indicators stored in matrices
 	      */
 	      double[][] algEffortValues = new double[algName.length][2];
		  double[][] rniValues = new double[algName.length][2];
		  double[][] hypervolumeValues = new double[algName.length][2];		 
		  double[][] uniDistValues = new double[algName.length][2];
 	      
		  QualityIndicatorsHandler qualIndMatrices = new  QualityIndicatorsHandler();
		  List<Double> allAEs = new ArrayList<Double>();
		  allAEs = qualIndMatrices.getAllIndicatorsCF(qualityIndicators, "AE");
		  
		  List<Double> allRNIs = new ArrayList<Double>();
		  allRNIs = qualIndMatrices.getAllIndicatorsCF(qualityIndicators, "RNI");
		  
		  List<Double> allHypervolumes = new ArrayList<Double>();
		  allHypervolumes = qualIndMatrices.getAllIndicatorsCF(qualityIndicators, "HYPERVOLUME");
		  
		  List<Double> allUDs = new ArrayList<Double>();
		  allUDs = qualIndMatrices.getAllIndicatorsCF(qualityIndicators, "UD");
		  		  		  		  
		  for (int qind = 0; qind < qualityIndicators.size(); qind++){ //algName: [0] = NSGAII; [1] = IBEA; [2] = SPEA2
				 algEffortValues[qind][0] = qind;
				 algEffortValues[qind][1] = allAEs.get(qind);
				 
				 rniValues[qind][0] = qind;
				 rniValues[qind][1] = allRNIs.get(qind);
				 
				 hypervolumeValues[qind][0] = qind;
				 hypervolumeValues[qind][1] = allHypervolumes.get(qind);
				 
				 uniDistValues[qind][0] = qind;
				 uniDistValues[qind][1] = allUDs.get(qind);
	      }		 
 	     
		  int outcomeLLHCF = -1;
  	      choiceFunction chFun = new choiceFunction();
  	      int numberOfMeasures = 4;
  	      int alpha = 100;
  	     
  	      qualIndMatrices.clearAllIndicatorsCF();
  	      outcomeLLHCF = chFun.getMaxChoiceFunction(algEffortValues, 0, rniValues, 1, hypervolumeValues, 1, 
					 uniDistValues, 1, numberOfMeasures, alpha, elapsedTime);
 	      lastInvokedTime[outcomeLLHCF] = System.currentTimeMillis(); // Start time of the chosen LLH
  	      String selectedLLH = null;
  	      selectedLLH = stringSelectedLLH(outcomeLLHCF);
  	      /*
		   * Choice Function - Initialisation: END
		   */
  	        	      
          for (int j = 3; j < decisionPoints; j++) { // ******* BEGIN -> DECISION POINTS ******* -> j = 3 -> minus 3 iterations due to the initialisation 
        	  first = false;
        	  CreateAlgorithms cr = new CreateAlgorithms();
           	  if (j == 3) { // Get the population of the selected LLH as a result of the initialisation
        	     popAllH.addAll(popsInit.get(outcomeLLHCF));
        	  }        	         
        	  while (popAllH.size() < populationSize) {
				   popAllH.add(popAllH.get(popHandler.getRandomSolution(popAllH.size())));
			  }	
			  popToCreate = popAllH.size();
       	      Algorithm alg = cr.createAlg(selectedLLH, popToCreate, currIterationsLLH, problem, first, popAllH); 
         	  long beginTime = System.currentTimeMillis();
              alg.run();
              executionTimeArray[outcomeLLHCF] = System.currentTimeMillis() - beginTime;
              /*
               * Update elapsed time since the LLH was last called.
               */
			  for(int itimst = 0; itimst < algName.length; itimst++){
				elapsedTime[itimst] = System.currentTimeMillis() - lastInvokedTime[itimst];
			  }
              List result = (List) alg.getResult();
              List<S> popH = result;
              popAllH.clear(); 
              popAllH.addAll(popH); // All Moves
              
              /*
               * Calculate Known PF
               */
              pfKnown.addAll(popH);
              pfKnown = popHandler.generateNonDominated(pfKnown, problem);
              pfKnown = popHandler.removeRepeatedSolutionsInteger(pfKnown);
              filepfKnown = "pareto_fronts_known/"+
             		 versionHH+"."+problem.getName()+"."+problem.getNumberOfObjectives()+"D.t_"+i+".dp_"+j+
             		 ".pf";
              SaveFiles sPFKSt = new SaveFiles();
              sPFKSt.savePFKnown(filepfKnown, pfKnown);
              qualityIndicators.clear();
              qualityIndicators.add(HHCFMainReal.printResultsAsList(popAllH, problem, false, popToCreate, executionTimeArray[outcomeLLHCF], filepfKnown));
                            
              Logger.getLogger(HHCFMainReal.class.getName()).log(Level.INFO, "#### HH-CF  -- TRIAL: " + (i+1) + "  --  DECISION POINT: " + (j+1) +
 	  	    		 "  --  PROBLEM INSTANCE: " + problem.getName());
              Logger.getLogger(HHCFMainReal.class.getName()).log(Level.INFO, "## Selected LLH: " + alg.getName() + " -- " + selectedLLH + 
             		  " -- Integer Id: " + outcomeLLHCF);
              
              /*
               * Update matrices of indicators: clear lists and get a single value
               */
              allAEs.clear();
              allAEs = qualIndMatrices.getAllIndicatorsCF(qualityIndicators, "AE");
                            
       		  allRNIs.clear();
       		  allRNIs = qualIndMatrices.getAllIndicatorsCF(qualityIndicators, "RNI");
       		  
       		  allHypervolumes.clear();
       		  allHypervolumes = qualIndMatrices.getAllIndicatorsCF(qualityIndicators, "HYPERVOLUME");
       		  
       		  allUDs.clear();
       		  allUDs = qualIndMatrices.getAllIndicatorsCF(qualityIndicators, "UD");
                 
       		  /*
       		   * Update matrices of indicators: update itself 
       		   */
              algEffortValues[outcomeLLHCF][1] = allAEs.get(0); // Only 1 value
				 
			  rniValues[outcomeLLHCF][1] = allRNIs.get(0); // Only 1 value
				 
			  hypervolumeValues[outcomeLLHCF][1] = allHypervolumes.get(0); // Only 1 value
				 
			  uniDistValues[outcomeLLHCF][1] = allUDs.get(0); // Only 1 value
                
			  qualIndMatrices.clearAllIndicatorsCF();
			  outcomeLLHCF = chFun.getMaxChoiceFunction(algEffortValues, 0, rniValues, 1, hypervolumeValues, 1, 
						                                  uniDistValues, 1, numberOfMeasures, alpha, elapsedTime);
	 	       
		      /*
		       * Start time of the chosen LLH
		       */
			  lastInvokedTime[outcomeLLHCF] = System.currentTimeMillis();
	  	      selectedLLH = stringSelectedLLH(outcomeLLHCF);
          } // ******* END -> DECISION POINTS *******
          
          popFinalHH.clear();
          popFinalHH.addAll(popAllH);
          int popSizeFinal = popFinalHH.size();
          popFinalHH = popHandler.generateNonDominated(popFinalHH, problem);
                    
          SaveFiles sFV = new SaveFiles();
   		  sFV.saveFunVar(i, versionHH, problem, popFinalHH);
   		  qireal.addPFKnown(hhid, popFinalHH);
 		  HHCFMainReal.printResultsAsList(popFinalHH, problem, true, popSizeFinal, 0, filepfKnown);
   		    
   		  
   		
   		  first = true;
         
          popAllH.clear();
         
          popFinalHH.clear();
          
          qualityIndicators.clear();
          
          pfKnown.clear();
          
          filepfKnown = " ";
          
          popsInit.clear(); // FD
         
          System.out.println("@@@@@@@ END OF TRIAL: " + (i+1) + " @@@@@@@");
        } // ******* END -> TRIALS *******
       
        System.out.println("........... Copying Results.......................................................................");
		copyResultDirectory(srcDir + "/result",
				            destDir + "/ALLRES/" + problem.getName() + "_" + problem.getNumberOfObjectives());
		copyResultDirectory(srcDir + "/pareto_fronts_known",
				            destDir + "/PFKNOWN/HH-CF/");
        
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
    
    
    public static String printResults(List archive, Problem problem, int popSize,
    		long timeEffort, String pf) throws FileNotFoundException, IOException {
        
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
            
        // Algorithm Effort
        AlgorithmEffortCalculator algeff = new AlgorithmEffortCalculator(problem.getNumberOfObjectives(), timeEffort, archive.size());
        double algEffortValue = algeff.execute(archive);
            
        // RNI: Capacity
        RniCalculator rni = new RniCalculator(problem.getNumberOfObjectives(), popSize, pf);
        double rniValue = rni.execute(archive);
            
        // Uniform Dist
        UDMetricHandler ud = new UDMetricHandler(problem.getNumberOfObjectives(), pf);
        double udValue = ud.execute(archive);
                       
        strToReturn="Hyper: " + hypValue + " ## " + "IGD: " + igdValue + " ## " + "Epsilon: " + epsilonValue + " ## " + "GSPREAD: " + spreadValue + " ## "
            		+ "Alg Effort: " + algEffortValue + " ## " + "RNI: " + rniValue + " ## " + "Uniform Dist: " + udValue;
            
        return strToReturn;
    }

    
    public static List<Double> printResultsAsList(List archive, Problem problem, boolean recordStatEval, int popSize,
    		long timeEffort, String pf) throws FileNotFoundException, IOException {
        
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
            
        // Algorithm Effort
        AlgorithmEffortCalculator algeff = new AlgorithmEffortCalculator(problem.getNumberOfObjectives(), timeEffort, archive.size());
        double algEffortValue = algeff.execute(archive);
            
        // RNI: Capacity
        RniCalculator rni = new RniCalculator(problem.getNumberOfObjectives(), popSize, pf);
        double rniValue = rni.execute(archive);
            
        // Uniform Dist
        UDMetricHandler ud = new UDMetricHandler(problem.getNumberOfObjectives(), pf);
        double udValue = ud.execute(archive);
            
        qual.add(hypValue);
        qual.add(igdValue);
        qual.add(epsilonValue);
        qual.add(spreadValue);
        qual.add(algEffortValue);
        qual.add(rniValue);
        qual.add(udValue);
                    
        if (recordStatEval) {
           stindicators.recordDataStatEval("HYPERVOLUME", hypValue);
           stindicators.recordDataStatEval("IGD", igdValue);
           stindicators.recordDataStatEval("EPSILON", epsilonValue);
           stindicators.recordDataStatEval("SPREAD", spreadValue);
           stindicators.recordDataStatEval("AE", algEffortValue);
           stindicators.recordDataStatEval("RNI", rniValue);
           stindicators.recordDataStatEval("UD", udValue);
        }
        
        return qual;
    }
    
   
    private static String stringSelectedLLH(int res) {
    	String resSt = null;
    	switch(res) {
          case 0 :
            resSt = "NSGAII";
            break;
          case 1 :
        	resSt = "IBEA";
            break;  
          case 2 :
        	resSt = "SPEA2";
            break;    
        default:
      	  System.out.println("Wrong LLH!");
        }
    	
        return resSt;	
    }
    
}
