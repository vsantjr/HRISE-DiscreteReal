package br.inpe.cocte.labac.hrise.main;


import br.inpe.cocte.labac.hrise.core.CreateAlgorithms;
import br.inpe.cocte.labac.hrise.jhelper.util.ProblemFactory;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.EpsilonCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.HypervolumeCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.IgdCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.SpreadCalculator;
import br.inpe.cocte.labac.hrise.qualityreal.QualityIndicatorsRealProblems;
import br.inpe.cocte.labac.hrise.util.LogRunningLLH;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;
import br.inpe.cocte.labac.hrise.util.ProblemsWrapper;
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
 * This is the class which implements the HH-ALL algorithm as described in [1]. HH-ALL is a rather simple selection hyper-heuristic with
 * a random LLH selection method and All Moves acceptance approach.
 *
 *[1] SANTIAGO JÚNIOR, VALDIVINO ALEXANDRE DE ; ÖZCAN, ENDER ; DE CARVALHO, VINICIUS RENAN. Hyper-Heuristics based on Reinforcement Learning,
 * Balanced Heuristic Selection and Group Decision Acceptance. APPLIED SOFT COMPUTING, p. 1-48, 2020. Submitted (Pre-print, Revision 1).
 *
 * @author Valdivino Alexandre de Santiago Júnior
 */
public class HHALLMainReal<S extends Solution<?>> {

	
  public HHALLMainReal(String[] argspar) throws FileNotFoundException, ConfigurationException, JMException, IOException {
	
	  this.mainHHALLReal(argspar);
  }
  
    public void mainHHALLReal(String[] args) throws ConfigurationException, JMException, FileNotFoundException, IOException {
        String problemString, versionHH, opt;
        int m, k, l;
        int trials; // Number of trials
        int decisionPoints; // Number of decision points
        int maxIterations; // Number of iterations
        int populationSize = 100; // Population size
        String[] algName = {"NSGAII", "IBEA", "SPEA2"}; // LLHs
        List<S> pfKnown = new ArrayList<>(); // Known PF
        List<S> popAllH = new ArrayList<>(); // Auxiliary Population
        List<S> popFinalHH = new ArrayList<>(); // Final Population
        boolean first; // First decision point?
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
      	    l = prw.getL(); // Decision/DISTANCE variables 
     	    m = prw.getM(); // Number of objectives 
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
                
        Logger.getLogger(HHALLMainReal.class.getName()).log(Level.INFO, "#### HH-ALL, Discrete Optimisation and Real Problems!");       
        k = 2 * (m - 1); // POSITION VARIABLES
        Problem problem = ProblemFactory.getProblem(problemString, k, l, m); // Create problem instance

        List<List<Double>> qualityIndicators = new ArrayList<List<Double>>(); // All quality indicators
        StatEvalSupport stindic = StatEvalSupport.getInstance(); // Record and save the indicators for the statistical evaluation
        String filepfKnown;
        String hhid = " ";
        QualityIndicatorsRealProblems qireal = QualityIndicatorsRealProblems.getInstance(); 
        
        for (int i = 0; i < trials; i++) { // ******* BEGIN -> TRIALS *******
          hhid = versionHH + "." + problem.getName() + "." + "t_" + i;
          filepfKnown = " ";
          first = true;	
          List<LogRunningLLH> qualityPopH = new ArrayList<LogRunningLLH>();
          PopulationHandler popHandler = new PopulationHandler();
          int popToCreate = 0;
          int currIterationsLLH;
          currIterationsLLH = maxIterations/decisionPoints;
             
          for (int j = 0; j < decisionPoints; j++) { // ******* BEGIN -> DECISION POINTS *******
        	  String selectedLLH = null;
        	    
        	  if (versionHH.equals("HH-ALL")) {
        	    selectedLLH = algName[popHandler.getRandomSolution(3)]; // Random
        	    if (j > 0) {
        	      first = false;
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
        	  
        	  /*
        	   * Create LLH and run it          		 
        	   */
        	  CreateAlgorithms cr = new CreateAlgorithms();
        	  Algorithm alg = cr.createAlg(selectedLLH, popToCreate, currIterationsLLH, problem, first, popAllH); 
        	  alg.run();
              List result = (List) alg.getResult();
              List<S> popH = result;
              popAllH.clear(); 
              popAllH.addAll(popH); // All Moves
              pfKnown.addAll(popH);
              if (!first) {
                 pfKnown = popHandler.generateNonDominated(pfKnown, problem);
              }
              pfKnown = popHandler.removeRepeatedSolutionsInteger(pfKnown);
              filepfKnown = "pareto_fronts_known/"+
                 		 versionHH+"."+problem.getName()+"."+problem.getNumberOfObjectives()+"D.t_"+i+".dp_"+j+
                 		 ".pf";
              SaveFiles sPFKSt = new SaveFiles();
              sPFKSt.savePFKnown(filepfKnown, pfKnown);
              Logger.getLogger(HHALLMainReal.class.getName()).log(Level.INFO, "#### HH-ALL  -- TRIAL: " + (i+1) + "  --  DECISION POINT: " + (j+1) +
            		  "  --  PROBLEM INSTANCE: " + problem.getName());
              Logger.getLogger(HHALLMainReal.class.getName()).log(Level.INFO, "## Selected LLH: " + alg.getName() + " / " + selectedLLH);
          
          } // ******* END -> DECISION POINTS *******
                  
          popFinalHH.clear();
          popFinalHH.addAll(popAllH);
          popFinalHH = popHandler.generateNonDominated(popFinalHH, problem); // Pop_f of the Trial
                    
          // Save FUN (Obj Functions) and VAR (Solutions)
          SaveFiles sFV = new SaveFiles();
   		  sFV.saveFunVar(i, versionHH, problem, popFinalHH);
   		  
   		  // Record Indicators, per trial, for Statistical Evaluation
   		  qireal.addPFKnown(hhid, popFinalHH);
 		  
   		  first = true;
          popAllH.clear();
          popFinalHH.clear();
          pfKnown.clear();
          filepfKnown = " ";
          qualityIndicators.clear();
          qualityPopH.clear();
          System.out.println("######## END OF TRIAL: " + (i+1) + " #######");
        } // ******* END -> TRIALS *******
       
        System.out.println("........... Copying Results.......................................................................");
		copyResultDirectory(srcDir + "/result",
				            destDir + "/ALLRES/" + problem.getName() + "_" + problem.getNumberOfObjectives());
		copyResultDirectory(srcDir + "/pareto_fronts_known",
				            destDir + "/PFKNOWN/HH-ALL/");
		
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
      
    
    public static String printResults(List archive, Problem problem) throws FileNotFoundException, IOException {
        String strToReturn = "";
        String pf = "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
                         
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

    
    public static List<Double> printResultsAsList(List archive, Problem problem, double[] ideal, boolean recordStatEval) throws FileNotFoundException, IOException {
        List<Double> qual = new ArrayList<Double>();
        StatEvalSupport stindicators = StatEvalSupport.getInstance();
        String pf = "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
                             
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
    
   
   public static double calculateSingleIndicator(List archive, Problem problem, String indicator) throws FileNotFoundException, IOException {
        double qual = -1.0;
        String pf = "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
            
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
    
}
