package br.inpe.cocte.labac.hrise.qualityreal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import br.inpe.cocte.labac.hrise.jhelper.util.ProblemFactory;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.EpsilonCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.EpsilonPlusCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.HypervolumeCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.IgdCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.IgdPlusCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.SpreadCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.UDMetricHandler;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;
import br.inpe.cocte.labac.hrise.util.ProblemsWrapper;
import br.inpe.cocte.labac.hrise.util.SaveFiles;
import br.inpe.cocte.labac.hrise.util.StatEvalSupport;



public class QualityIndicatorsRealProblems<S extends Solution<?>> {
	
	private Map<String, List<S>> allPFKnown; 
	private static QualityIndicatorsRealProblems instance = null;
	
	
	protected QualityIndicatorsRealProblems() { 
	  
		 this.allPFKnown = new LinkedHashMap<String, List<S>>();
		
	}
	
	
	public static QualityIndicatorsRealProblems getInstance() {
	    if(instance == null) {
	    	  
	         instance = new QualityIndicatorsRealProblems();
	        
	    }
	          
	    return instance;
	}
	
	
	public void addPFKnown(String hh, List<S> pop) {
		
		
		List<S> auxPop = new ArrayList<>();
		auxPop.addAll(pop);
		
		this.allPFKnown.put(hh,auxPop);
	}
	
	
    public Map<String,List<S>> getAllPFKnown(){
	    	return this.allPFKnown;
	    }
	
    
    public int getAllPFKnownSize() {
    	return this.allPFKnown.size();
    }
    
    
    public void clearAllPFKnown() {
    	this.allPFKnown.clear();
    }
    
    
    public void calculateQualityIndicatorsReal(String[] hh, String opt, String srcDir, String destDir) throws IOException {
	   
	   int l, m, k;
	   String problemString, versionHH;
	   ProblemsWrapper prw = new ProblemsWrapper(opt);
	   
	   l = prw.getL();
	   m = prw.getM();
	   problemString = prw.getProblemString();
	   
	   k = 2 * (m - 1); // POSITION VARIABLES

       
       Problem problem = ProblemFactory.getProblem(problemString, k, l, m);
	   
       FileUtils.deleteDirectory(new File("result"));
       FileUtils.deleteDirectory(new File("pareto_fronts_true"));
       
	   
	   PopulationHandler popHandler = new PopulationHandler();
	   // Get all Known PFs
	   String filepfTrueKnown = " ";
	   List<S> pfTrueKnown = new ArrayList<>();
	   for(Entry<String, List<S>> onePFKnown : allPFKnown.entrySet()){
		    System.out.println("Known PF Id: " + onePFKnown.getKey() + " -- Known PF Size: " + onePFKnown.getValue().size());  	
		    if (onePFKnown.getKey().contains(problem.getName())){ // for precaution. Per problem. 
		       pfTrueKnown.addAll(onePFKnown.getValue());
		    }   
	   }
	  
	   pfTrueKnown = popHandler.generateNonDominated(pfTrueKnown, problem); 
             
       pfTrueKnown = popHandler.removeRepeatedSolutionsInteger(pfTrueKnown);
       
       
	   filepfTrueKnown = "pareto_fronts_true/"+
      		 problem.getName()+"."+problem.getNumberOfObjectives()+"D.True"+
      		 ".pf";
       SaveFiles sPFK = new SaveFiles();
       sPFK.savePFTrueKnown(filepfTrueKnown, pfTrueKnown, "pareto_fronts_true");
       
       System.out.println("........... Copying Results: True Known PF ............................................");
       copyDirectory(srcDir + "/pareto_fronts_true",
	            destDir + "/PFTRUEKNOWN/");	
       
       for (int indHH = 0; indHH < hh.length; indHH++) { 
		  versionHH = hh[indHH];	
		  
		  for(Entry<String, List<S>> onePop : allPFKnown.entrySet()){
			    
			    
			    if (onePop.getKey().contains(versionHH + "." + problem.getName())){ // for precaution. Per problem. 
			       
			       saveIndicatorsReal(onePop.getValue(), problem, filepfTrueKnown, true);
			    }  
			    
		   }
		  
		
		  StatEvalSupport stind = StatEvalSupport.getInstance();
		  
	      
	    	
		  
		  
          stind.saveFileStatEval(versionHH, versionHH + "_hypervolume", stind.getAllHypervolume(), problem);
		  stind.saveFileStatEval(versionHH, versionHH + "_igd", stind.getAllIGD(), problem);
		  stind.saveFileStatEval(versionHH, versionHH + "_epsilon", stind.getAllEpsilon(), problem);
		  stind.saveFileStatEval(versionHH, versionHH + "_spread", stind.getAllSpread(), problem);
		  stind.saveFileStatEval(versionHH, versionHH + "_ud", stind.getAllUD(), problem);
		  stind.saveFileStatEval(versionHH, versionHH + "_igdplus", stind.getAllIGDPlus(), problem);
		  stind.saveFileStatEval(versionHH, versionHH + "_epsilonplus", stind.getAllEpsilonPlus(), problem);
		  
		  
		  System.out.println("........... Copying Results: Real Quality Indicators ................................................................");
		  copyDirectory(srcDir + "/result",
					            destDir + "/ALLRES/" + problem.getName() + "_" + problem.getNumberOfObjectives());
		  
		  		  
		  stind.clearAllIndicatorsStatEval();
		}  
       
   }
   
  
   private String findStr(String s, char c){
	   return s.substring(0, s.indexOf(c));
	} 
   
   
   private String printIndicatorsReal(List archive, Problem problem, String pf) throws FileNotFoundException, IOException {
       
   	String strToReturn = "";
       //if (problem.getName().contains("WFG") || problem.getName().contains("DTLZ") || problem.getName().contains("VC") || problem.getName().contains("MAC")) {
          // String pf
            //       = "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
           
                       
           // HV: Convergence-Diversity
           PopulationHandler popHndList = new PopulationHandler();
           HypervolumeCalculator hyp = new HypervolumeCalculator(problem.getNumberOfObjectives(), pf);
           //hyp.updatePointsUsingNadir(popHndList.getNadir(problem.getName(), problem.getNumberOfObjectives()));
           //hyp.updatePointsUsingNadirIdeal(popHndList.getNadir(problem.getName(), problem.getNumberOfObjectives()),
           	//	popHndList.getIdeal(problem.getName(), problem.getNumberOfObjectives()));
          // hyp.updatePointsUsingNadir(popHndList.getNadirReal(pf));
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
           
           // RNI: Capacity
           //RniCalculator rni = new RniCalculator(problem.getNumberOfObjectives(), populationsize,
             //      pf);
           //double rniValue = rni.execute(archive);
           //IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
           //double igdValue = igd.execute(archive);
           
           //GdCalculator gd =new GdCalculator(problem.getNumberOfObjectives(), pf);
           //double gdValue = gd.execute(archive);
           
           
           // Uniform Dist: Diversity
           UDMetricHandler ud = new UDMetricHandler(problem.getNumberOfObjectives(), pf);
           double udValue = ud.execute(archive);
           
           // IGD+: Convergence-Diversity
           IgdPlusCalculator igdplus = new IgdPlusCalculator(problem.getNumberOfObjectives(), pf);
           double igdPlusValue = igdplus.execute(archive);
           
           // Epsilon+: Convergence
           EpsilonPlusCalculator epsplus = new EpsilonPlusCalculator(problem.getNumberOfObjectives(), pf);
           double epsilonPlusValue = epsplus.execute(archive);
           
           strToReturn="Hyper: " + hypValue + " ## " + 
                        "IGD: " + igdValue + " ## " +
        		        "Epsilon: " + epsilonValue + " ## " +
                        "GSPREAD: " + spreadValue + " ## " +
        		        "UD: " + udValue +  " ## " +
                        "IGD Plus: " + igdPlusValue + " ## " +
        		        "Epsilon Plus: " + epsilonPlusValue + " ## ";
           
       
       return strToReturn;
   }
   
   private List<Double> saveIndicatorsReal(List archive, Problem problem, String pf, boolean recordStatEval) throws FileNotFoundException, IOException {
       
   	List<Double> qual = new ArrayList<Double>();
       StatEvalSupport stindicators = StatEvalSupport.getInstance();
       
       //if (problem.getName().contains("WFG") || problem.getName().contains("DTLZ") || problem.getName().contains("VC") || problem.getName().contains("MAC")) {
           //String pf
             //      = "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
           
                       
           // HV: Convergence-Diversity
           PopulationHandler popHnd = new PopulationHandler();
           HypervolumeCalculator hyp = new HypervolumeCalculator(problem.getNumberOfObjectives(), pf);
           //hyp.updatePointsUsingNadir(popHnd.getNadir(problem.getName(), problem.getNumberOfObjectives()));
          // hyp.updatePointsUsingNadirIdeal(popHnd.getNadir(problem.getName(), problem.getNumberOfObjectives()),
           //		popHnd.getIdeal(problem.getName(), problem.getNumberOfObjectives()));
           //hyp.updatePointsUsingNadir(popHnd.getNadirReal(pf));
           double hypValue = hyp.execute(archive);
           
           // IGD: Convergence-Diversity
           IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
           double igdValue = igd.execute(archive);
           
           // Epsilon: Convergence
           EpsilonCalculator eps = new EpsilonCalculator(problem.getNumberOfObjectives(), pf);
           double epsilonValue = eps.execute(archive);
           
           // GSPREAD/SPREAD: Diversity
           SpreadCalculator spread = new SpreadCalculator(problem.getNumberOfObjectives(), pf);
           double spreadValue = spread.execute(archive);
           
           
          // Uniform Dist: Diversity
           UDMetricHandler ud = new UDMetricHandler(problem.getNumberOfObjectives(), pf);
           double udValue = ud.execute(archive);
           
           // IGD+: Convergence-Diversity
           IgdPlusCalculator igdplus = new IgdPlusCalculator(problem.getNumberOfObjectives(), pf);
           double igdPlusValue = igdplus.execute(archive);
           
           // Epsilon+: Convergence
           EpsilonPlusCalculator epsplus = new EpsilonPlusCalculator(problem.getNumberOfObjectives(), pf);
           double epsilonPlusValue = epsplus.execute(archive);
           
           
           
           // RNI: Capacity
           //RniCalculator rni = new RniCalculator(problem.getNumberOfObjectives(), populationsize,
           //        pf);
           //double rniValue = rni.execute(archive);
           //IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
           //double igdValue = igd.execute(archive);
           
           //GdCalculator gd =new GdCalculator(problem.getNumberOfObjectives(), pf);
           //double gdValue = gd.execute(archive);
                       
           qual.add(hypValue);
           qual.add(igdValue);
           qual.add(epsilonValue);
           qual.add(spreadValue);
           qual.add(udValue);
           qual.add(igdPlusValue);
           qual.add(epsilonPlusValue);
           
           //double[] valQI = {2.0*hypValue, igdValue, epsilonValue/2.0, spreadValue, rniValue}; // Hypervolume and Epsilon: weight = 2; remaining: weight = 1
           //qual.add(new EuclideanDistance().d(valQI, ideal));
       
           if (recordStatEval) {
           	stindicators.recordDataStatEval("HYPERVOLUME", hypValue);
   			stindicators.recordDataStatEval("IGD", igdValue);
   			stindicators.recordDataStatEval("EPSILON", epsilonValue);
   			stindicators.recordDataStatEval("SPREAD", spreadValue);
   			stindicators.recordDataStatEval("UD", udValue);
   			stindicators.recordDataStatEval("IGDPLUS", igdPlusValue);
   			stindicators.recordDataStatEval("EPSILONPLUS", epsilonPlusValue);
   			
   			
           }
       
       return qual;
   }

   private void copyDirectory(String sourceDir, String destDir) {
		File source = new File(sourceDir);
		File dest = new File(destDir);
		try {
		    FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}

}
