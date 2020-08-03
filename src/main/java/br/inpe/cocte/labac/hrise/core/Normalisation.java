package br.inpe.cocte.labac.hrise.core;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Normalisation {

	
	private List<Double> hypervolume;
	private List<Double> igd;
	private List<Double> epsilon;
	private List<Double> spread;
	//private List<Double> rni;
	
	public Normalisation() { // here is what first initialization should be done
	      //System.out.println("Sempre?");
		 this.hypervolume = new ArrayList<Double>();
		 this.igd = new ArrayList<Double>();
		 this.epsilon = new ArrayList<Double>();
		 this.spread = new ArrayList<Double>();
		 //this.rni = new ArrayList<Double>();
	}
	
	public List<List<Double>> normaliseIndicators (List<List<Double>> qualityIndicators){
		
		//System.out.println("Before indNotNormal: " + qualityIndicators);
		// Get the values of all indicators per stage
		for (List<Double> notNormal: qualityIndicators ) {
			hypervolume.add(notNormal.get(0));
			igd.add(notNormal.get(1));
			epsilon.add(notNormal.get(2));
			spread.add(notNormal.get(3));
			//rni.add(notNormal.get(4));
		}
		
		//for (Double hv: hypervolume) {
			//System.out.println("Hypervolume ---: " + hv);
		//}
		
		// Find min, max values of all indicators per stage; Normalised values		
		Map<String, Double> mmHV = findMinMaxIndicatorValues(hypervolume);
		Double normHV;
		
		Map<String, Double> mmIGD = findMinMaxIndicatorValues(igd);
		Double normIGD;
		
		Map<String, Double> mmEPS = findMinMaxIndicatorValues(epsilon);
		Double normEPS;
		
		Map<String, Double> mmSPR = findMinMaxIndicatorValues(spread);
		Double normSPR;
		
		//Map<String, Double> mmRNI = findMinMaxIndicatorValues(rni);
		//Double normRNI;

		// Replace the normalised values of the indicators
		//DecimalFormat df = new DecimalFormat("#.###############################");
		//df.setRoundingMode(RoundingMode.CEILING);
		for (List<Double> yesNormal: qualityIndicators ) {
			//System.out.println("----- min HV: " + mmHV.get("MIN") + "---- max HV: " + mmHV.get("MAX"));
			if ( (mmHV.get("MIN") == 0.0) && (mmHV.get("MAX") == 0.0) ) { // it does not work if we add: ... && mmHV.get("MIN") == mmHV.get("MAX")
			  //System.out.println("here ;;;;;");
			  normHV = 0.0;
		   } else if ((mmHV.get("MIN") == 1.0) && (mmHV.get("MAX") == 1.0)){
			           //System.out.println(" 11111  here ;;;;;");
			           normHV = 1.0;
		   } else {	         
			  //System.out.println("NONONONONONONO ;;;;;");
		      normHV = (yesNormal.get(0) - mmHV.get("MIN"))/(mmHV.get("MAX") - mmHV.get("MIN"));
		   }
			
			yesNormal.set(0, normHV);
			
			
			normIGD = (yesNormal.get(1) - mmIGD.get("MIN"))/(mmIGD.get("MAX") - mmIGD.get("MIN"));
			yesNormal.set(1, normIGD);
			
			
			normEPS = (yesNormal.get(2) - mmEPS.get("MIN"))/(mmEPS.get("MAX") - mmEPS.get("MIN"));
			yesNormal.set(2, normEPS);
			
			
			normSPR = (yesNormal.get(3) - mmSPR.get("MIN"))/(mmSPR.get("MAX") - mmSPR.get("MIN"));
			yesNormal.set(3, normSPR);
			
			//if ( (mmRNI.get("MIN") == 0.0) && (mmRNI.get("MAX") == 0.0) ) {
				//normRNI = 0.0;
			//} else if ((mmRNI.get("MIN") == 1.0) && (mmRNI.get("MAX") == 1.0)) {
				//        normRNI = 1.0;
			//} else {
				//System.out.println("RNI");
				
			  //  normRNI = (yesNormal.get(4) - mmRNI.get("MIN"))/(mmRNI.get("MAX") - mmRNI.get("MIN"));
			//}    
			//yesNormal.set(4, normRNI);
								
		}
		
		//System.out.println("After indNotNormal: " + qualityIndicators);
		
		return qualityIndicators;
				
	}
	
	private Map<String, Double> findMinMaxIndicatorValues (List<Double> ind) {
		
		Map<String, Double> mm = new LinkedHashMap<String,Double>();
		
		Double minHV = ind
			      .stream()
			      .mapToDouble(v -> v)
			      .min().orElseThrow(NoSuchElementException::new);
		
		Double maxHV = ind
			      .stream()
			      .mapToDouble(v -> v)
			      .max().orElseThrow(NoSuchElementException::new);
		
		//System.out.println("Min: " + minHV + " -- Max: " + maxHV);
		
		mm.put("MIN", minHV);
		mm.put("MAX", maxHV);
		
		return mm;
		
	}
}
