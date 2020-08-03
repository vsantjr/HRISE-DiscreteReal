package br.inpe.cocte.labac.hrise.util;

import java.util.ArrayList;
import java.util.List;

public class QualityIndicatorsHandler {
	
	private List<Double> hypervolume;
	private List<Double> igd;
	private List<Double> epsilon;
	private List<Double> spread;
	private List<Double> algEffort;
	private List<Double> rni;
	private List<Double> ud;
	
	public QualityIndicatorsHandler() { // here is what first initialization should be done
	      //System.out.println("Sempre?");
		 this.hypervolume = new ArrayList<Double>();
		 this.igd = new ArrayList<Double>();
		 this.epsilon = new ArrayList<Double>();
		 this.spread = new ArrayList<Double>();
		 this.algEffort = new ArrayList<Double>();
		 this.rni = new ArrayList<Double>();
		 this.ud = new ArrayList<Double>();
	}
	
	public List<Double> getAllIndicatorsCF (List<List<Double>> qualityIndicators, String ind){
		
		//System.out.println("Before indNotNormal: " + qualityIndicators);
		// Get the values of all indicators per stage
		for (List<Double> eachQI: qualityIndicators) {
			hypervolume.add(eachQI.get(0));
			igd.add(eachQI.get(1));
			epsilon.add(eachQI.get(2));
			spread.add(eachQI.get(3));
			algEffort.add(eachQI.get(4));
			rni.add(eachQI.get(5));
			ud.add(eachQI.get(6));
			
		}
		
		List<Double> res = new ArrayList<Double>();
		
		switch(ind) {
          case "HYPERVOLUME" :
            res.addAll(this.hypervolume);
            break;
          case "IGD" :
        	res.addAll(this.igd);
            break;  
          case "EPSILON" :
        	res.addAll(this.epsilon);
            break;    
          case "SPREAD" :
        	res.addAll(this.spread);
            break;  
          case "AE" :
        	res.addAll(this.algEffort);
            break;  
          case "RNI" :
        	res.addAll(this.rni);
            break;  
          case "UD" :
        	res.addAll(this.ud);
            break;  
          default:
        	  System.out.println("Wrong Quality Ind");
            
	   }
		
	   return res;	
	}	
	
	public void clearAllIndicatorsCF() {
		this.hypervolume.clear();
		this.igd.clear();
		this.epsilon.clear();
		this.spread.clear();
		this.algEffort.clear();
		this.rni.clear();
		this.ud.clear();
	}
}
