package br.inpe.cocte.labac.hrise.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

public class SelectionSupport<S extends Solution<?>> {
	
	public SelectionSupport() {
		
	}
	
	/*public static List generateNonDominated(List population, Problem problem) {
        if (problem.getName().contains("WFG")) {
            double[] nadir = getNadir(problem.getName(), problem.getNumberOfObjectives());
            List aux = removeWorseThanNadir(population, nadir, problem.getNumberOfObjectives());
            Logger.getLogger(SelectionSupport.class.getName()).log(Level.INFO, "Non dominated WFG %%%%");
            return SolutionListUtils.getNondominatedSolutions(aux);
        }
        //Logger.getLogger(HHOSTMain.class.getName()).log(Level.INFO, "Non dominated NAO WFG %%%%");
        return SolutionListUtils.getNondominatedSolutions(population);
    }*/
	
	
	 /*public static double[] getNadir(String problemName, int m) {
	    	double[] nadir = new double[m];
	        if (problemName.contains("WFG")) {
	            double[] base = new double[]{3.0, 5.0, 7.0, 9.0, 11.0, 13.0, 15.0, 17.0, 19.0, 21.0, 23.0, 25.0, 27.0, 29.0, 31.0, 33.0, 35.0, 37.0, 39.0, 41.0, 43.0};
	            nadir = Arrays.copyOf(base, m);
	        } else if (problemName.contains("DTLZ1") && m == 3) {
	        	//uble[] adtlz1 = new double[] {0.5, 0.5, 0.5};
	        	
	            Arrays.fill(nadir, 0.5);
	            //Logger.getLogger(HHOSTMain.class.getName()).log(Level.INFO, "Nadir ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~: " + Arrays.toString(nadir));
	        } //else if (problemName.contains("DTLZ1")) {
	            //Arrays.fill(nadir, 10.0);
	        //} else if (problemName.contains("DTLZ") && m == 3) {
	          //  Arrays.fill(nadir, 2.0);
	        //} else if (problemName.contains("DTLZ")) {
	          //  Arrays.fill(nadir, 20.0);
	        //}
	        return nadir;
	    	
	    }*/
	 
	/* private static List removeWorseThanNadir(List population, double[] nadir, int m) {
	        List newpopulation = new ArrayList();
	        for (Object o : population) {
	            Solution s = (Solution) o;
	            boolean stillOk = true;
	            for (int i = 0; i < m && stillOk; i++) {
	                if (nadir[i] < s.getObjective(i)) {
	                    //System.err.println(nadir[i]+" menor "+s.getObjective(i));
	                    stillOk = false;
	                }
	            }
	            if (stillOk) {
	                newpopulation.add(s);
	            }
	        }
	        return newpopulation;
	    }*/
	 
	/* public static List generateNonDominated(List population) {
	        return SolutionListUtils.getNondominatedSolutions(population);
	    }*/
	 
	 
	   
	 private static <T extends Comparable<T>> int findMinDistanceIndex(final List<T> xs) {
	        int minIndex;
	        double minValue = Double.NEGATIVE_INFINITY;
	        if (xs.isEmpty()) {
	            minIndex = -1;
	            
	        } else {
	            final ListIterator<T> itr = xs.listIterator();
	            T min = itr.next(); // first element as the current minimum
	            minIndex = itr.previousIndex();
	            while (itr.hasNext()) {
	                final T curr = itr.next();
	                if (curr.compareTo(min) < 0) {
	                    min = curr;
	                    minValue = (double) min;
	                    minIndex = itr.previousIndex();
	                }
	            }
	        }
	        return minIndex;
	    }
	 
	 private static double getLastElement(List<Double> l) {
	        Iterator<Double> itr = l.iterator();
	        double lastElement = (double) itr.next();
	        while(itr.hasNext()) {
	            lastElement = (double) itr.next();
	        }
	        return lastElement;
	    }
	 
	  /*public static List<String> rankingLLHs(String[] llhs, List<List<Double>> quality){
	    	List<String> posRanking = new ArrayList<String>();
	    	List<String> llhsAsList; 
	        List<Double> eucDistance = new ArrayList<Double>();  
	       	
	       	for(Iterator<List<Double>> iter = quality.listIterator(); iter.hasNext();) 
	       	       eucDistance.add(getLastElement(iter.next()));
	       	 
	       	llhsAsList = new LinkedList<String>(Arrays.asList(llhs));
	       	//System.out.println("LLhs L: " + llhsAsList);
	       	
	       	while (!eucDistance.isEmpty()) {
	       		int minIndex = findMinDistanceIndex(eucDistance); 
	       	    posRanking.add(llhsAsList.get(minIndex));
	       	    
	       	    //System.out.println("****************************************** Size Euc  : " + eucDistance.size() + "Size Qual Inside : " + quality.size() + 
	       			// "Euc Distance: " + eucDistance + "  index val: " + llhsAsList.get(minIndex) );
	       	    eucDistance.remove(minIndex);
	       	    llhsAsList.remove(minIndex);
	       	}
	       
	       	//llhsAsList = new LinkedList<String>(Arrays.asList(llhs));
	      
	       return posRanking;	
	    }*/

	  public static List<String> rankingLLHs(String[] llhs, List<Double> eucDistance){
	    	List<String> posRanking = new ArrayList<String>();
	    	List<String> llhsAsList; 
	        //List<Double> eucDistance = new ArrayList<Double>();  
	       	
	       	//for(Iterator<List<Double>> iter = quality.listIterator(); iter.hasNext();) 
	       	  //     eucDistance.add(getLastElement(iter.next()));
	       	 
	       	llhsAsList = new LinkedList<String>(Arrays.asList(llhs));
	       	//System.out.println("LLhs L: " + llhsAsList);
	       	
	       	while (!eucDistance.isEmpty()) {
	       		int minIndex = findMinDistanceIndex(eucDistance); 
	       	    posRanking.add(llhsAsList.get(minIndex));
	       	    
	       	    //System.out.println("****************************************** Size Euc  : " + eucDistance.size() + "Size Qual Inside : " + quality.size() + 
	       			// "Euc Distance: " + eucDistance + "  index val: " + llhsAsList.get(minIndex) );
	       	    eucDistance.remove(minIndex);
	       	    llhsAsList.remove(minIndex);
	       	}
	       
	       	//llhsAsList = new LinkedList<String>(Arrays.asList(llhs));
	      
	       return posRanking;	
	    }

	 

}


