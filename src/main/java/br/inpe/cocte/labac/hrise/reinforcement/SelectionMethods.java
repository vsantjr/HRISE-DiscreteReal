package br.inpe.cocte.labac.hrise.reinforcement;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Stream;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.HypervolumeCalculator;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;


public class SelectionMethods<S extends Solution<?>> {

	
	public SelectionMethods() {
		
	}
	
	
	public List<Double> findFitness(List archive, List<Double> quality){
		
		return new ArrayList<Double>();
	}
	
	
	public String selectLLHAll (Map<String, Double> llhUt, int dp, int maxDP){
		   
		   final double tau = 0.2;
		   final double beta = -0.25*tau + 0.95;
		   double[] utility = new double[llhUt.size()];
		   String selMethod = null;
		   String nextLLH = null; // The next LLH to run
		   
		   
		   int indUt = 0;
		   for(Entry<String, Double> llhUtElem : llhUt.entrySet()){
	      	  utility[indUt] = llhUtElem.getValue();
	      	  indUt++;
		    }
		   
		   
		   
		    if (dp <= Math.round(tau*maxDP) ) {
		    	selMethod = "ROULETTE"; 
		    	System.out.println("Primeiro ROULETTE");
		    	
		    } else {
		    	 if ( (dp > Math.round(tau*maxDP)) && (dp < Math.round(beta*maxDP)) ){
		    		System.out.println("Intermediario ROULETTE");
		    		
		    		   selMethod = "ROULETTE"; 
		    		} else
		    			if (dp >= Math.round(beta*maxDP) ) {
		    				 
				    		 selMethod = "ROULETTE"; 
		    				System.out.println("Ultimo ROULETTE de novo!");
		    			}
		    }	
		    switch(selMethod) {
		        case  "ROULETTE":
	              nextLLH = rouletteWheelSelector(llhUt, utility);
	              System.out.println("%%%% -- ROULETTE");
	              break;
	            case "MEANFIRSTABOVE" :
	              nextLLH = meanFirstAboveSelector(llhUt, utility);  
	              System.out.println("%%%% -- MEAN OR FIRST ABOVE");
	              break;
	            case "GREEDY" :
	              nextLLH = greedySelector(llhUt, utility);
	              System.out.println("%%%% -- GREEDY");
	              break;	   
	            default :
	              System.out.println("%%%%% Invalid Sel Method AGAIN!");
	         }	
		    	
		    	    	
		    return nextLLH;
	    }
	
		
	public String selectLLH (Map<String, Double> llhUt, int dp, int maxDP){
	   
	   final double tau = 0.4;
	   final double beta = -0.25*tau + 0.95;
	   double[] utility = new double[llhUt.size()];
	   String selMethod = null;
	   String nextLLH = null; // The next LLH to run
	   
	   
	   int indUt = 0;
	   for(Entry<String, Double> llhUtElem : llhUt.entrySet()){
      	  utility[indUt] = llhUtElem.getValue();
      	  indUt++;
	    }
	   
	   
	   
	    if (dp <= Math.round(tau*maxDP) ) {
	    	selMethod = "ROULETTE"; 
	    	System.out.println("Primeiro ROULETTE");
	    	
	    } else {
	    	 if ( (dp > Math.round(tau*maxDP)) && (dp < Math.round(beta*maxDP)) ){
	    		System.out.println("Intermediario RANDOM");
	    		int indSelMethod = genRandomInt(3);
	    		switch(indSelMethod) {
	    		     case 0 :
	                    selMethod = "ROULETTE"; 
	                    break;
	                 case 1 :
	                	selMethod = "MEANFIRSTABOVE"; 
	                    break;
	                 case 2 :
	                	selMethod = "GREEDY"; 
	                    break;
	                  default :
	                     System.out.println("%%%%% Invalid Sel Method!");
	                }
	    		    selMethod = "MEANFIRSTABOVE"; 
	    		} else
	    			if (dp >= Math.round(beta*maxDP) ) {
	    				selMethod = "GREEDY"; 
	    				System.out.println("Ultimo GREEDY");
	    			}
	    }	
	    switch(selMethod) {
	        case  "ROULETTE":
              nextLLH = rouletteWheelSelector(llhUt, utility);
              System.out.println("%%%% -- ROULETTE");
              break;
            case "MEANFIRSTABOVE" :
              nextLLH = meanFirstAboveSelector(llhUt, utility);  
              System.out.println("%%%% -- MEAN OR FIRST ABOVE");
              break;
            case "GREEDY" :
              nextLLH = greedySelector(llhUt, utility);
              System.out.println("%%%% -- GREEDY");
              break;	   
            default :
              System.out.println("%%%%% Invalid Sel Method AGAIN!");
         }	
	    	
	    	    	
	    return nextLLH;
    }
	
	
	public int genRandomInt(int high){
    	Random r = new Random();
    	int low = 0;
    	
    	int result = r.nextInt(high-low) + low;
    	return result;
    }
	
	
	public String selectLLHRW (Map<String, Double> llhUt, int dp, int maxDP){
		   		   
		   double[] utility = new double[llhUt.size()];
		   String selMethod = "ROULETTE"; 
		   String nextLLH = null; // The next LLH to run
		   		   
		   int indUt = 0;
		   for(Entry<String, Double> llhUtElem : llhUt.entrySet()){
	      	  utility[indUt] = llhUtElem.getValue();
	      	  indUt++;
		    }
		  		  		   	    	
		   switch(selMethod) {
		       case  "ROULETTE":
	              nextLLH = rouletteWheelSelector(llhUt, utility);
	              System.out.println("#### LLH Selection Method: ROULETTE");
	              break;
	           case "MEANFIRSTABOVE" :
	              nextLLH = meanFirstAboveSelector(llhUt, utility);  
	              System.out.println("#### LLH Selection Method: MEAN OR FIRST ABOVE");
	              break;
	           case "GREEDY" :
	              nextLLH = greedySelector(llhUt, utility);
	              System.out.println("#### LLH Selection Method: GREEDY");
	              break;	   
	           default :
	              System.out.println("#### Invalid LLH Selection Method!");
	       }	
		     	    	
		   return nextLLH;
	}
		
	
	public double genRandomDouble(double high){
    	Random r = new Random();
    	double low = 0.0;
    	
    	double result = high*r.nextDouble() + low;
    	return result;
    }
	
	
    private String greedySelector (Map<String, Double> llhUt, double[] utility) {
		
    	String nextLLH; 
    	List<Double> utilityList = new ArrayList<Double>();
    	for (int i = 0; i < utility.length; i++) {
    	     utilityList.add(utility[i]);
    	}
    	
    	boolean allEqual = utilityList.isEmpty() || utilityList.stream().allMatch(utilityList.get(0)::equals);
    	
    	if (allEqual) {
    		nextLLH = genRandomLLH(llhUt);
    		
    	} else {
    		
		   Double nextUtility = utilityList
			      .stream()
			      .mapToDouble(v -> v)
			      .max().orElseThrow(NoSuchElementException::new);
		
				
		   Stream<String> keyStream = keys(llhUt, nextUtility);
		   nextLLH = keyStream.findFirst().get();
    	}
	  
       return nextLLH;
		
	}
     
    
    private String meanFirstAboveSelector (Map<String, Double> llhUt, double[] utility) {
 		
    	String nextLLH; 
     	List<Double> utilityList = new ArrayList<Double>();
     	for (int i = 0; i < utility.length; i++) {
     	     utilityList.add(utility[i]);
     	}
     	
     	boolean allEqual = utilityList.isEmpty() || utilityList.stream().allMatch(utilityList.get(0)::equals);
     	
     	if (allEqual) {
    		nextLLH = genRandomLLH(llhUt);
    	
    	} else {
    		
    	    double sum = 0.0;
    	    double mean;
    	    for (int i = 0; i < utility.length; i++) {
    	       sum += utility[i];
    	    }
    	    mean = sum / (double) utility.length; 
 		
    	    Arrays.sort(utility);  
    	    List<Double> aboveMean = new ArrayList<Double>();  
    	    for(int i = 0; i < utility.length; i++) {
    	      if(utility[i] >= mean){
    	        aboveMean.add(utility[i]);
    	      }
    	    }
 		
    	
    	
      	    double nextUtility =  aboveMean.get(0);
            Stream<String> keyStream = keys(llhUt, nextUtility);
 	        nextLLH = keyStream.findFirst().get();
    	}	  
 	   return nextLLH;
 		
 	}
     
    
    private String rouletteWheelSelector (Map<String, Double> llhUt, double[] utility) {
       	String nextLLH; 
     	List<Double> utilityList = new ArrayList<Double>();
     	for (int i = 0; i < utility.length; i++) {
     	     utilityList.add(utility[i]);
     	}
     	
     	boolean allEqual = utilityList.isEmpty() || utilityList.stream().allMatch(utilityList.get(0)::equals);
     	
     	if (allEqual) {
     		nextLLH = genRandomLLH(llhUt);
     		
     	} else {
     		
    	 
    	  double[] probs = new double[utility.length];
 		  Random rand;
 		
 		  double sum = 0.0;
 		  for (int i = 0; i < utility.length; i++) {
 			sum += utility[i];
 		  }
 		
 		  for (int i = 0; i < probs.length; i++) {
 			probs[i] = utility[i]/(double) sum;
 			//System.out.println("Prob: " + probs[i]);
 		  }
 		
 		   
 		  rand = new Random();
 		  double p = rand.nextDouble();
          double sumProb = 0.0;
          int j = 0;
          while(sumProb < p){
           sumProb += probs[j];
           j++;
          }
         
          double nextUtility = utility[j-1];
          Stream<String> keyStream = keys(llhUt, nextUtility);
  	      nextLLH = keyStream.findFirst().get();
     	}	  
       return nextLLH;
     }
     
    
    private <K, V> Stream<K> keys(Map<K, V> map, V value) {
    	    return map
    	      .entrySet()
    	      .stream()
    	      .filter(entry -> value.equals(entry.getValue()))
    	      .map(Map.Entry::getKey);
    }
     
    public String genRandomLLH(Map<String, Double> llhUt) {
        
    	String nextLLH = null;
    	String[] singleLLH = new String[llhUt.size()];
    	int ind = 0;
    	for(Entry<String, Double> llhUtElem : llhUt.entrySet()){
        	singleLLH[ind] = llhUtElem.getKey();
        	ind++;
    	}
        
    	int indRnd = genRandomInt(llhUt.size());
		switch(indRnd) {
		     case 0 :
             nextLLH = singleLLH[0]; 
             break;
          case 1 :
         	  nextLLH = singleLLH[1]; 
              break;
          case 2 :
         	 nextLLH = singleLLH[2]; 
             break;
           default :
              System.out.println("%%%%% Invalid LLH!");
         }	
		
		return nextLLH;
	 	
	}
    
    
    public boolean aboveFrequency (double freq, int decisionPoints, double K) {
		if (freq > K*decisionPoints)
			return true;
		else
			return false;
	}
    
    
    public boolean aboveFrequency (double freq, double K) {
		if (freq > K)
			return true;
		else
			return false;
	}
}
