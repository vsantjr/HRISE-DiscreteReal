package br.inpe.cocte.labac.hrise.util;

import java.util.*;
import java.util.stream.Collectors;  
    
public class WeightedMaxMinFairness  
{  
    /*public static void main(String a[])  
    {  
    	String[] algName = {"NSGAII", "MOMBI2", "IBEA", "SPEA2"};
    	double[] wLLH = {2.0, 1.0, 0.75, 0.5};
    	int populationSize = 500;
        Map<String, Integer> propPops = null; 
    	propPops = weightedMaxMin(algName, wLLH, populationSize);
    	System.out.println("\n Proportion of Populations ************: " + propPops);
    	int elemP = 0;
    	for (int e: propPops.values()) {
    		elemP += e;
    	}
    	System.out.println("\n Current Size Pop ************: " + elemP);
    	
    }*/
    
    public static  Map<String, Integer> weightedMaxMin(List<String> llhs, double[] weightsLLH, int popSize) {
    	
    	// popSize = total capacity
    	
        double capacity = (double) popSize;
        double expFactor = 0.5;
        double normFactor = 0.0; 
        int count; // control the fill of the weights
    	
        // All the HashMaps
        Map<String, Double> desired = new LinkedHashMap<String,Double>();  
        Map<String, Double> weights = new LinkedHashMap<String, Double>();  
        Map<String, Double> weightsDeepCopy = new LinkedHashMap<String, Double>();  
        Map<String, Double> currentShare = new LinkedHashMap<String, Double>();  
        Map<String, Double> finalShare = new LinkedHashMap<String, Double>();  
        Map<String, Integer> finalShareInteger = new LinkedHashMap<String, Integer>();  
       
        
      
        // Initialize desired capacity (population; at first, the entire population)
        count = 0;
        for (String l: llhs) {
             desired.put(l, 1.0*popSize);
             weights.put(l, Math.pow(weightsLLH[count], expFactor));
             count++;
        }
       
        
        // norm_factor
        for (double n: weights.values()) {
        	normFactor += n;
        }
       
        final Double innerNormFactor = new Double(normFactor);
        
        weights.replaceAll((k, v) -> v = v/innerNormFactor);
        
              
        //System.out.println("WEIGHTS: " + weights);  
        
        weightsDeepCopy = deepCopy(weights);
        
        //System.out.println("WEIGHTS Deep : " + weightsDeepCopy);
        
        currentShare = deepCopy(desired);
        
        currentShare.replaceAll((k, v) -> 0.0);
        
        //System.out.println("Current Share : " + currentShare);
        
       // main algorithm
       while((weights.size() > 0) && (capacity > 0)) {
        	double totalShares = 0.0;
        	double unitShare, fairShare, spareCapacity;
        	for (double n: weights.values()) {
            	totalShares += n;
            }
        	unitShare = capacity/totalShares;
        	List<String> listKeys = new ArrayList<String>(weights.keySet());
        	for (String oneKey: listKeys) {
        		fairShare = unitShare * weights.get(oneKey);
        		currentShare.put(oneKey, currentShare.get(oneKey) + fairShare);
        		if (currentShare.get(oneKey) >= desired.get(oneKey)) {
        			spareCapacity = currentShare.get(oneKey) - desired.get(oneKey);
        			finalShare.put(oneKey, desired.get(oneKey));
        			currentShare.remove(oneKey);
        			weights.remove(oneKey);
        			capacity = capacity + spareCapacity - fairShare;
        		} else
        			capacity = capacity - fairShare;
        	}
        	
        	
       }
        
       // Final allocations
       Iterator it = currentShare.entrySet().iterator();
       while (it.hasNext()) {
        	Map.Entry pair = (Map.Entry)it.next();
        	finalShare.put((String)pair.getKey(), (Double)pair.getValue()) ;      
        	it.remove(); // avoids a ConcurrentModificationException
        }
        
    List<String> listKeysFinal = new ArrayList<String>(finalShare.keySet());
   	for (String oneKey: listKeysFinal) {
   	   int makeEven = (int) Math.round(finalShare.get(oneKey));	
   	   if (makeEven % 2 != 0) {
          makeEven++;
       }
       finalShareInteger.put(oneKey, makeEven);
   	}   
      
   	
   	//System.out.println("Final Sh: " + finalShare);
   	
   	
   
   	return finalShareInteger;       
        
    }  
    
    
    public static Map<String, Double> deepCopy(Map<String, Double> original)
    	{
    	Map<String, Double> copy = new LinkedHashMap<String, Double>();
    	    for (Map.Entry<String, Double> entry : original.entrySet())
    	    {
    	        copy.put(entry.getKey(), entry.getValue());
    	    }
    	    return copy;
    	}
}  
