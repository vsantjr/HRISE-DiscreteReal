package br.inpe.cocte.labac.hrise.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.Problem;

public class StatEvalSupport implements Serializable {
	
	private List<Double> hypervolume;
	private List<Double> igd;
	private List<Double> epsilon;
	private List<Double> spread;
	private List<Double> algeffort;
	private List<Double> rni;
	private List<Double> ud;
	private List<Double> igdplus;
	private List<Double> epsilonplus;
	
	
	 private static StatEvalSupport instance = null;
	 protected StatEvalSupport() { // here is what first initialization should be done
	      //System.out.println("Sempre?");
		 this.hypervolume = new ArrayList<Double>();
		 this.igd = new ArrayList<Double>();
		 this.epsilon = new ArrayList<Double>();
		 this.spread = new ArrayList<Double>();
		 this.algeffort = new ArrayList<Double>();
		 this.rni = new ArrayList<Double>();
		 this.ud = new ArrayList<Double>();
		 this.igdplus = new ArrayList<Double>();
		 this.epsilonplus = new ArrayList<Double>();
	   }
	   public static StatEvalSupport getInstance() {
	      if(instance == null) {
	    	  //System.out.println("Dentro de inst!");
	         instance = new StatEvalSupport();
	        
	      }
	      //System.out.println("Fora de inst!");
	      
	      return instance;
	   }
	   
	   public void recordDataStatEval(String indicator, Double value) {
		 switch(indicator) {
           case "HYPERVOLUME" :
             hypervolume.add(value); 
             break;
           case "IGD" :
         	 igd.add(value); 
             break;
           case "EPSILON" :
         	 epsilon.add(value); 
             break;
           case "SPREAD" :
             spread.add(value); 
             break; 
           case "AE" :
           	  algeffort.add(value); 
              break;
           case "RNI" :
           	 rni.add(value); 
             break;
           case "UD" :
           	 ud.add(value); 
             break;  
           case "IGDPLUS" :
             igdplus.add(value); 
             break;  
           case "EPSILONPLUS" :
             epsilonplus.add(value); 
             break;    
           default :
             System.out.println("%%%%% Invalid Indicator!");
		   
	     }
	 }
	   
	 public List<Double> getAllHypervolume() {
		 return this.hypervolume;
	 }
	 
	 public List<Double> getAllIGD() {
		 return this.igd;
	 }
	 
	 public List<Double> getAllEpsilon() {
		 return this.epsilon;
	 }
	 
	 public List<Double> getAllSpread() {
		 return this.spread;
	 }
	 
	 public List<Double> getAllAE() {
		return this.algeffort;
	 }
	 
	public List<Double> getAllRNI() {
		return this.rni;
    }
	 
	public List<Double> getAllUD() {
	    return this.ud;
	}
	
	
	public List<Double> getAllIGDPlus() {
	    return this.igdplus;
	}
	
	public List<Double> getAllEpsilonPlus() {
	    return this.epsilonplus;
	}
	 
	 
	 public void clearAllIndicatorsStatEval() {
		 this.hypervolume.clear();
		 this.igd.clear();
		 this.epsilon.clear();
		 this.spread.clear();
		 this.algeffort.clear();
		 this.rni.clear();
		 this.ud.clear();
		 this.igdplus.clear();
		 this.epsilonplus.clear();
	 }
	 
	 public void saveFileStatEval(String optimizationSol, String indicatorType, List<Double> indicatorValue, Problem problem) throws IOException {
		 // PS: colocar problem como outro par. String dir = "result/" + optimizationSol + "/" + problem.getName() + "_" + problem.getNumberOfObjectives();
		 String dir = "result/" + optimizationSol + "/" +  problem.getName() + "_" + problem.getNumberOfObjectives();
	     new File(dir).mkdirs();
	     String indicatorFile = dir + "/" + indicatorType + ".tsv";
	     
	     FileOutputStream f = new FileOutputStream(indicatorFile);
	     //ObjectOutputStream out = new ObjectOutputStream(f);
	     for (double iV: indicatorValue) {
	    	 String valueTab = String.valueOf(iV) + "\t";
	    	 f.write(valueTab.getBytes(), 0, valueTab.length());
	     }
	     //out.writeObject(indicatorValue);
	     f.close();
	        //String varFile = dir + "/VAR" + i + ".tsv";
	 }

	 public void saveFileStatEvalAppend(String optimizationSol, String indicatorType, List<Double> indicatorValue, Problem problem, int first) throws IOException {
		 // PS: colocar problem como outro par. String dir = "result/" + optimizationSol + "/" + problem.getName() + "_" + problem.getNumberOfObjectives();
		 String dir = "result/" + optimizationSol + "/" +  problem.getName() + "_" + problem.getNumberOfObjectives();
		 String indicatorFile = dir + "/" + indicatorType + ".tsv";
		 
		 if (first == 0) { // first. Do not append
	       new File(dir).mkdirs();
	     //String indicatorFile = dir + "/" + indicatorType + ".tsv";
	     
	       FileOutputStream f = new FileOutputStream(indicatorFile);
	     //ObjectOutputStream out = new ObjectOutputStream(f);
	       for (double iV: indicatorValue) {
	    	 String valueTab = String.valueOf(iV) + "\t";
	    	 f.write(valueTab.getBytes(), 0, valueTab.length());
	       }
	     //out.writeObject(indicatorValue);
	       f.close();
		 } else { // append
			   FileOutputStream ff = new FileOutputStream(indicatorFile, true);
		     //ObjectOutputStream out = new ObjectOutputStream(f);
		       for (double iV: indicatorValue) {
		    	 String valueTab = String.valueOf(iV) + "\t";
		    	 ff.write(valueTab.getBytes(), 0, valueTab.length());
		       }
		     //out.writeObject(indicatorValue);
		       ff.close();
		 }
			 
	        //String varFile = dir + "/VAR" + i + ".tsv";
	 }
	 
}
