package br.inpe.cocte.labac.hrise.swtesting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SwTestingUtils {
	
	public SwTestingUtils() {
		
	}
	
	 public static String getLastElement(List<String> l) {
	        Iterator<String> itr = l.iterator();
	        String lastElement = (String) itr.next();
	        while(itr.hasNext()) {
	            lastElement = (String) itr.next();
	        }
	        return lastElement;
	    }
	    
	    
	    
	    
	   /* private static int sizeTestSuite(List<List<String>> simple){
	       	int size = 0;
	       	for (List<String> sc: simple){
	        	size += sc.size();
	        }
	       	System.out.println("\n\n Objective 1 Inside - size: " + size);
	        return size; 	
	    }*/
	    
	    
	    
	    
	    public static int sizeTestSuite(List<List<String>> simple, List<Integer> solution) throws InterruptedException{
	    	
	    	// "solution" is really an integer solution
	    	
	    	List<List<String>> allDecisionVariables = new ArrayList<List<String>>(); // It means that each element is a Dec Var = SC = Test Case
	       	int size = 0;
	       	
	       	
	     // Generates a solution based on the integer indices 
	    	for (int ind: solution){
	    		allDecisionVariables.add(simple.get(ind));
	    		//System.out.println("Id of the SC, Test Case, Value of the decision variable: " + ind);
	    	}
	    	
	    	
	    	
	       	for (List<String> sc: allDecisionVariables){
	        	size += sc.size();
	        	//System.out.println("Solution: " + sc);
	        }
	       	//System.out.println("\n\n Objective 1 - size of the solution/TS: " + size);
	       	//System.out.println("\n\n\nEnter: ");
	        //try{System.in.read();}
	        //catch(Exception e){}
	      //Thread.sleep(10000);   
	       	
	        return size; 	
	    }
	    
	    public static double testCaseDiversity(List<List<String>> simple, List<Integer> solution, double simMeasureType) throws InterruptedException{
	    	    
	    	// "solution" is really an integer solution
	    	
	    	List<List<String>> allDecisionVariables = new ArrayList<List<String>>(); // It means that each element is a Dec Var = SC = Test Case
	    	List<List<Integer>> allCombinations = new ArrayList<List<Integer>>(); // all possible combinations of 2 decision variables within a solution
	   	    List<Double> simSolution = new ArrayList<Double>(); // All similarity measures (all decision variables within a solution)
	   	    double meanSimSolution = 0; // The mean value of the similarities measures: it represents the similarity value of the solution
	    	
	   	    // Generates a solution based on the integer indices 
	    	for (int ind: solution){
	    		allDecisionVariables.add(simple.get(ind));
	    		//System.out.println("Id of the SC, Test Case, Value of the decision variable DIV: " + ind);
	    	}
	    	
	    	
	    	// ...
	    	//for(List<String> sl: allDecisionVariables){
	    		//System.out.println("Sol 1: " + sl);
	    	//}
	    	
	    	     	 
	    	 //System.out.println("\n\n Solution 1: " + allDecisionVariables + " size:" + allDecisionVariables.size() + "\n");
	    	     	 
	    	 allCombinations = combination(allDecisionVariables.size(), 2);
	    	 for (List<Integer> comb: allCombinations){
	    		  //System.out.println("Comb size: " + comb.size()); 
	    		  simSolution.add(similarityMeasure(allDecisionVariables.get(comb.get(0)-1), allDecisionVariables.get(comb.get(1)-1), simMeasureType));
	    	 }
	    			 
	    	 // Similarity measure for the entire solution
	    	 for(double mean: simSolution) {
	             meanSimSolution += mean;
	          }
	    	 
	    	 meanSimSolution /= (double)simSolution.size();
	          //System.out.println("\n\n Objectives 2 and 3: Mean Sim Measure: "+ meanSimSolution + "  ---- Size Comb: " + simSolution.size());
	    	 
	    	 
	          return meanSimSolution;
	    }
	    
	    private static List<List<Integer>> combination(int n, int k) {
	    	List<List<Integer>> result = new ArrayList<List<Integer>>();
	     
	    	if (n <= 0 || n < k)
	    		return result;
	     
	    	List<Integer> item = new ArrayList<Integer>();
	    	dfs(n, k, 1, item, result); // because it need to begin from 1
	     
	    	return result;
	    }
	     
	    private static void dfs(int n, int k, int start, List<Integer> item,
	    		List<List<Integer>> result) {
	    	if (item.size() == k) {
	    		result.add(new ArrayList<Integer>(item));
	    		return;
	    	}
	     
	    	for (int i = start; i <= n; i++) {
	    		item.add(i);
	    		dfs(n, k, i + 1, item, result);
	    		item.remove(item.size() - 1);
	    	}
	    }
	    
	    private static double similarityMeasure (List<String> sc1, List<String> sc2, double simType){
	    	// simType = 1 -> Jaccard Index
	    	// simType = 0.5 -> Gower-Legendre (Dice)
	    	// simType = 2 -> Sokal-Sneath (Anti-Dice)
	    	
	    	Set<String> union = new LinkedHashSet<String>();
	    	Set<String> intersection = new LinkedHashSet<String>();
	    	double simMeasure = 0;

	        // Union 	
	    	union.addAll(sc1);
	        union.addAll(sc2);
	        
	        // Intersection
	        for (String s : sc1) {
	            if(sc2.contains(s)) {
	                intersection.add(s);
	            }
	        }
	        
	        // Similarity Measure
	        simMeasure = ( (double)intersection.size() ) / ( (double)intersection.size() + simType*((double)union.size() - (double)intersection.size()));  
	        
	        return simMeasure;
	    }
	    
	    public static int genIndexSolution(int high){
	    	Random r = new Random();
	    	int low = 0;
	    	//int high = 152;
	    	int result = r.nextInt(high-low) + low;
	    	return result;
	    }
	    
	    
	    public static double edgeCoverage(Set<String> allEdges, List<List<String>> simple, List<Integer> solution){
	    	List<List<String>> allDecisionVariables = new ArrayList<List<String>>(); // It means that each element is a Dec Var = SC = Test Case
	    	double edgeCov = - 1.0;
	    	// Generates a solution based on the integer indices 
	    	for (int ind: solution){
	    		allDecisionVariables.add(simple.get(ind));
	    		//System.out.println("Index COVERAGE: " + ind);
	    	}
	    	
	    	Set<String> coveredEdges = new LinkedHashSet<String>();
	    	for (List<String> sc: allDecisionVariables){
	        	for (int i = 0; i < sc.size() - 1; i++){
	        		coveredEdges.add("("+sc.get(i)+" : "+sc.get(i+1)+")");
	           	}
	        }
	    	
	    	 int commonEdge = 0;
	         for (String ce: coveredEdges){
	         	if (allEdges.contains(ce)) {
	              	commonEdge++;
	            }
	         }
	    	
	        
	         edgeCov = (double)(commonEdge /  (double)allEdges.size());
	         //System.out.println("\n\n Objective 4 - edge cov: " + edgeCov + 
	         	//	" - allEdges: " + allEdges.size());
	         
	    	 //return (double)(100*commonEdge /  (double)allEdges.size());
	    	 
	    	 return edgeCov;
	    	 
	    }
	    
	    
	    
	    /*private static double edgeCoverage(Set<String> allEdges, List<List<String>> simple){
	    	Set<String> coveredEdges = new LinkedHashSet<String>();
	    	for (List<String> sc: simple){
	        	for (int i = 0; i < sc.size() - 1; i++){
	        		coveredEdges.add("("+sc.get(i)+" : "+sc.get(i+1)+")");
	           	}
	        }
	    	
	    	 int commonEdge = 0;
	         for (String ce: coveredEdges){
	         	if (allEdges.contains(ce)) {
	              	commonEdge++;
	            }
	         }
	    	
	         System.out.println("\n\n Objective 3 Inside - commonEdge: " + commonEdge + 
	         		" - allEdges: " + allEdges.size());
	         
	    	 return (double)(100*commonEdge /  (double)allEdges.size());
	    }*/

}
