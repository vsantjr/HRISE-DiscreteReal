package br.inpe.cocte.labac.hrise.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;

import br.inpe.cocte.labac.hrise.core.SelectionSupport;

public class PopulationHandler<S extends Solution<?>> {
	
   public PopulationHandler() {
		
	   }
   
   
   public List<S> updatePopulation(List<S> population, List<S> offspringPopulation, int size) {
		 // Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: REPLACEMENT @@");
	    List<S> jointPopulation = new ArrayList<>();
	    jointPopulation.addAll(population);
	    jointPopulation.addAll(offspringPopulation);

	    RankingAndCrowdingSelection<S> rankingAndCrowdingSelection ;
	    rankingAndCrowdingSelection = new RankingAndCrowdingSelection<S>(size, new DominanceComparator<S>()) ;

	    return rankingAndCrowdingSelection.execute(jointPopulation) ;
	  }
   
   public double[] getNadirReal(String pf) throws FileNotFoundException {
	    //double[] tempMinimum = FrontUtils.getMinimumValues(front);
	    Front front = new ArrayFront(pf);
	    double[] tempMaximum = FrontUtils.getMaximumValues(front);
        return tempMaximum;
	    //addMinMaxReference(tempMinimum, tempMaximum);
	  }

   
   
   public int getRandomSolution(int high){
   	  Random r = new Random();
   	  int low = 0;
   	//int high = 152;
   	  int result = r.nextInt(high-low) + low;
   	  return result;
   }
   
   public List<S> getSharePopulation(List<S> pop, int beginPop, int len) {
       
		  List<S> sharePop = new ArrayList<>(Collections.nCopies(len, null));
	    	System.out.println("Size S: " + sharePop.size());
	    	for (int i = 0; i < len; i++) {
	        	sharePop.set(i,pop.get(i+beginPop));
	        }
	        return sharePop;
   }
   
   
   public List<S> removeRepeatedSolutions(List<S> pop) throws IOException{
	   List<Double> firstCompare = new ArrayList<Double>();
	   List<Double> secondCompare = new ArrayList<Double>();
	   Set<Integer> foundIndex = new LinkedHashSet<Integer>();
	   List<Integer> foundIndexList = new ArrayList<Integer>();
	   
	   //SaveFiles fBefore = new SaveFiles();
	   //fBefore.saveFileAnyValues("BEFOREPop", pop);
	   	   
	   //Set<S> popSet = new LinkedHashSet<>(receivedPop);
	   //receivedPop.clear();
	   //receivedPop.addAll(popSet);
	   for (int i = 0; i < pop.size() - 1; i++) {
		   for (int nvf = 0; nvf < pop.get(i).getNumberOfVariables(); nvf++) {
			   firstCompare.add((Double) pop.get(i).getVariableValue(nvf));
		   }
		   //System.out.println("First: " + firstCompare + " i: " + i);
		   for (int j = (i+1); j < pop.size(); j++) {
			   for (int nvs = 0; nvs < pop.get(j).getNumberOfVariables(); nvs++) {
				   secondCompare.add((Double) pop.get(j).getVariableValue(nvs));
			   }
			   if (firstCompare.equals(secondCompare)){
				   //System.out.println("Equal: " + secondCompare + "  j: " + j);
				   foundIndex.add(j);
				   //pop.remove(j);
			   }
			   secondCompare.clear();
		   } // end For j
		  firstCompare.clear();
	   } // end For i*/
	   
	   //System.out.println("Pop Size REMOVING REPEATED - Before: " + pop.size());
	   foundIndexList.addAll(foundIndex);
	   Collections.sort(foundIndexList, Collections.reverseOrder());
	   for (int ind : foundIndexList)
	       pop.remove(ind);
	   //System.out.println("Pop Size REMOVING REPEATED - After: " + pop.size());
	   
	   //SaveFiles fAfter = new SaveFiles();
	   //fAfter.saveFileAnyValues("AFTERPop", pop);
	   return pop;
   }
   
   public static List generateNonDominated(List population, Problem problem) {
       if (problem.getName().contains("NOT-ENABLED")) {
    	   //if (problem.getName().contains("DTLZ") || problem.getName().contains("WFG") || problem.getName().contains("ZDT")) {	   
           double[] nadir = getNadir(problem.getName(), problem.getNumberOfObjectives());
           NadirHandler nh = new NadirHandler();
           List aux = nh.removeWorseThanNadir(population, nadir, problem.getNumberOfObjectives());
           Logger.getLogger(SelectionSupport.class.getName()).log(Level.INFO, "Non dominated WFG, DTLZ, ZDT %%%%");
           return SolutionListUtils.getNondominatedSolutions(aux);
       }
       //Logger.getLogger(HHOSTMain.class.getName()).log(Level.INFO, "Non dominated NAO WFG %%%%");
       return SolutionListUtils.getNondominatedSolutions(population);
   }
   
   public static List generateNonDominated(List population) {
       return SolutionListUtils.getNondominatedSolutions(population);
   }
   
   public static double[] getNadir(String problemName, int m) {
   	double[] nadir = new double[m];
       if (problemName.contains("WFG")) {
           double[] base = new double[]{3.0, 5.0, 7.0, 9.0, 11.0, 13.0, 15.0, 17.0, 19.0, 21.0, 23.0, 25.0, 27.0, 29.0, 31.0, 33.0, 35.0, 37.0, 39.0, 41.0, 43.0};
           nadir = Arrays.copyOf(base, m);
       } else if (problemName.contains("DTLZ1") && m == 3) {
       	           Arrays.fill(nadir, 0.5);
       } else if ((problemName.contains("DTLZ2") || problemName.contains("DTLZ3") || problemName.contains("DTLZ4") || problemName.contains("DTLZ5") ||
    		   problemName.contains("DTLZ6")) && m == 3) {
    	           Arrays.fill(nadir, 1.0);
       } else if (problemName.contains("DTLZ7") && m == 3) {
       	          nadir[0] = 1.0;
                  nadir[1] = 1.0;
                  nadir[2] = 2.0*m;
       } else if (problemName.contains("VC1") && m == 3) {
    	          nadir[0] = 1721.0;
    	          nadir[1] = 15.0;
    	          nadir[2] = 1.0;
       } else if (problemName.contains("VC2") && m == 2) {
	          nadir[0] = 1721.0;
	          nadir[1] = 15.0;
	          
       } else if (problemName.contains("VC3") && m == 2) {
	          nadir[0] = 1721.0;
	          //nadir[1] = 14.0;
	          nadir[1] = 1.0;
       } else if (problemName.contains("VC4") && m == 2) {
	          //nadir[0] = 1712.0;
	          nadir[0] = 15.0;
	          nadir[1] = 1.0;
       } else if (problemName.contains("MAC1") && m == 4) {
	          nadir[0] = 7.0;
	          nadir[1] = -2.0;
	          nadir[2] = 0.0;
	          nadir[3] = 3.0;
       } else if (problemName.contains("MAC2") && m == 3) {
	          nadir[0] = 7.0;
	          nadir[1] = -2.0;
	          nadir[2] = 0.0;
	          //nadir[3] = 3.0;
       } else if (problemName.contains("MAC3") && m == 3) {
	          nadir[0] = 7.0;
	          nadir[1] = -2.0;
	          //nadir[2] = 0.0;
	          nadir[2] = 3.0;
       } else if (problemName.contains("MAC4") && m == 3) {
	          nadir[0] = 7.0;
	          //nadir[1] = -2.0;
	          nadir[1] = 0.0;
	          nadir[2] = 3.0;
       } else if (problemName.contains("MAC5") && m == 3) {
	          //nadir[0] = 7.0;
	          nadir[0] = -2.0;
	          nadir[1] = 0.0;
	          nadir[2] = 3.0;
       } else if (problemName.contains("WR1") && m == 5) { // f1, f2, f3, f4, f5
	          //nadir[0] = 7.0;
	          nadir[0] = 81810.0;
	          nadir[1] = 2340.0;
	          nadir[2] = 4779561.0;
	          nadir[3] = 11880069.0;
	          nadir[4] = 598237.0;
      } else if (problemName.contains("WR2") && m == 4) { // f1, f2, f3, f4
          //nadir[0] = 7.0;
          nadir[0] = 81810.0;
          nadir[1] = 2340.0;
          nadir[2] = 4779561.0;
          nadir[3] = 11880069.0;
          //nadir[4] = 598237.0;
      } else if (problemName.contains("WR3") && m == 4) { // f1, f2, f3, f5
          //nadir[0] = 7.0;
          nadir[0] = 81810.0;
          nadir[1] = 2340.0;
          nadir[2] = 4779561.0;
          //nadir[3] = 11880069.0;
          nadir[3] = 598237.0;
      } else if (problemName.contains("WR4") && m == 4) { // f1, f2, f4, f5
          //nadir[0] = 7.0;
          nadir[0] = 81810.0;
          nadir[1] = 2340.0;
          //nadir[2] = 4779561.0;
          nadir[2] = 11880069.0;
          nadir[3] = 598237.0;
      } else if (problemName.contains("WR5") && m == 4) { // f1, f3, f4, f5 
          //nadir[0] = 7.0;
          nadir[0] = 81810.0;
          //nadir[1] = 2340.0;
          nadir[1] = 4779561.0;
          nadir[2] = 11880069.0;
          nadir[3] = 598237.0;
       } else if (problemName.contains("WR6") && m == 4) { // f2, f3, f4, f5
	          //nadir[0] = 7.0;
	          //nadir[0] = 81810.0;
	          nadir[0] = 2340.0;
	          nadir[1] = 4779561.0;
	          nadir[2] = 11880069.0;
	          nadir[3] = 598237.0;
      } else if (problemName.contains("CS1") && m == 3) {
          nadir[0] = 61.0;
          nadir[1] = 5.0;
          nadir[2] = 15.0;
      } else if (problemName.contains("CS2") && m == 2) {
          nadir[0] = 61.0;
          nadir[1] = 5.0;
          //nadir[2] = 15.0;
      } else if (problemName.contains("CS3") && m == 2) {
          nadir[0] = 61.0;
          //nadir[1] = 5.0;
          nadir[1] = 15.0;
      } else if (problemName.contains("CS4") && m == 2) {
          //nadir[0] = 61.0;
          nadir[0] = 5.0;
          nadir[1] = 15.0;
      } 
       
         //System.out.println("!!!!!!!!!!!!!!!!!!! NADIR: " + Arrays.toString(nadir));  
       return nadir;
   
   }
   
   private List removeWorseThanNadirOLD(List population, double[] nadir, int m) {
       List<S> newpopulation = new ArrayList<>();
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
               newpopulation.add((S) s);
           }
           System.out.println("stillOk: " + stillOk);
       }
       
       System.out.println("Size new Pop: " + newpopulation.size());
       
       return newpopulation;
   }
   
   
// 03/05/2019: update
   
   public static double[] getIdeal(String problemName, int m) {
	   	double[] ideal = new double[m];
	       if (problemName.contains("VC1") && m == 3) {
	    	      ideal[0] = 1636.0;
	    	      ideal[1] = 2.0;
	    	      ideal[2] = -1.0;
	       } else if (problemName.contains("VC2") && m == 2) {
		          ideal[0] = 1636.0;
		          ideal[1] = 2.0;
		          
	       } else if (problemName.contains("VC3") && m == 2) {
		          ideal[0] = 1636.0;
		          //nadir[1] = 14.0;
		          ideal[1] = -1.0;
	       } else if (problemName.contains("VC4") && m == 2) {
		          //nadir[0] = 1712.0;
		          ideal[0] = 2.0;
		          ideal[1] = -1.0;
	       } else if (problemName.contains("MAC1") && m == 4) {
		          ideal[0] = 0.0;
		          ideal[1] = -5.0;
		          ideal[2] = -8.0;
		          ideal[3] = -6.0;
	       } else if (problemName.contains("MAC2") && m == 3) {
		          ideal[0] = 0.0;
		          ideal[1] = -5.0;
		          ideal[2] = -8.0;
		          //nadir[3] = 3.0;
	       } else if (problemName.contains("MAC3") && m == 3) {
		          ideal[0] = 0.0;
		          ideal[1] = -5.0;
		          //nadir[2] = 0.0;
		          ideal[2] = -6.0;
	       } else if (problemName.contains("MAC4") && m == 3) {
		          ideal[0] = 0.0;
		          //nadir[1] = -2.0;
		          ideal[1] = -8.0;
		          ideal[2] = -6.0;
	       } else if (problemName.contains("MAC5") && m == 3) {
		          //nadir[0] = 7.0;
		          ideal[0] = -5.0;
		          ideal[1] = -8.0;
		          ideal[2] = -6.0;
	       } else if (problemName.contains("WR1") && m == 5) { // f1, f2, f3, f4, f5
		          
		        //  ideal[0] = 56138.0;
		         // ideal[1] = -960.0;
		         // ideal[2] = -1640745.0;
		         // ideal[3] = -4828959.0;
		         // ideal[4] = -256377.0;
		          
		          ideal[0] = 0.0;
		          ideal[1] = 0.0;
		          ideal[2] = 0.0;
		          ideal[3] = 0.0;
		          ideal[4] = 0.0;
		          
	       } else if (problemName.contains("WR2") && m == 4) { // f1, f2, f3, f4
		          
		        /*  ideal[0] = 56138.0;
		          ideal[1] = -960.0;
		          ideal[2] = -1640745.0;
		          ideal[3] = -4828959.0;
		          //ideal[4] = -256377.0;
		          */
		          
		          ideal[0] = 0.0;
		          ideal[1] = 0.0;
		          ideal[2] = 0.0;
		          ideal[3] = 0.0;
		          //ideal[4] = -256377.0;
		          
	       } else if (problemName.contains("WR3") && m == 4) { // f1, f2, f3, f5
		          
	    	   /*
		          ideal[0] = 56138.0;
		          ideal[1] = -960.0;
		          ideal[2] = -1640745.0;
		          //ideal[3] = -4828959.0;
		          ideal[3] = -256377.0;
		          */
	    	   
		          ideal[0] = 0.0;
		          ideal[1] = 0.0;
		          ideal[2] = 0.0;
		          //ideal[3] = -4828959.0;
		          ideal[3] = 0.0;
		          
	       } else if (problemName.contains("WR4") && m == 4) { // f1, f2, f4, f5
		          
	    	   /*
		          ideal[0] = 56138.0;
		          ideal[1] = -960.0;
		          //ideal[2] = -1640745.0;
		          ideal[2] = -4828959.0;
		          ideal[3] = -256377.0;
		          */
		          ideal[0] = 0.0;
		          ideal[1] = 0.0;
		          //ideal[2] = -1640745.0;
		          ideal[2] = 0.0;
		          ideal[3] = 0.0;
		          
	       } else if (problemName.contains("WR5") && m == 4) { // f1, f3, f4, f5
		          
		         /* ideal[0] = 56138.0;
		          //ideal[1] = -960.0;
		          ideal[1] = -1640745.0;
		          ideal[2] = -4828959.0;
		          ideal[3] = -256377.0;
		          */
		          
		          ideal[0] = 0.0;
		          //ideal[1] = -960.0;
		          ideal[1] = 0.0;
		          ideal[2] = 0.0;
		          ideal[3] = 0.0;
		          
	       } else if (problemName.contains("WR6") && m == 4) { // f2, f3, f4, f5
		          
		       /* 
	    	   //ideal[0] = 56138.0;
		          ideal[0] = -960.0;
		          ideal[1] = -1640745.0;
		          ideal[2] = -4828959.0;
		          ideal[3] = -256377.0;
		          */
		          
		          //ideal[0] = 56138.0;
		          ideal[0] = 0.0;
		          ideal[1] = 0.0;
		          ideal[2] = 0.0;
		          ideal[3] = 0.0;
		          
	       } else {
	    	   Arrays.fill(ideal, 0.0);
	       }
	    	   
	       
	         //System.out.println("!!!!!!!!!!!!!!!!!!! NADIR: " + Arrays.toString(nadir));  
	       return ideal;
	   
	   }
   
   public List removeWorseThanNadirStrict(List population, double[] nadir, int m, int pSize, Problem prob) {
       List<S> newpopulation = new ArrayList<>();
       for (Object o : population) {
           Solution s = (Solution) o;
    	   boolean decNotWorseNadir = true;
           boolean[] notWorseNadir = new boolean[m];
          
           for (int k = 0; k < notWorseNadir.length; k++) {
        	   notWorseNadir[k] = true;
           }
           for (int i = 0; i < m; i++) { // the nadir is no worse than s in all objectives. It means that there is something worse than nadir in all objectives
               if (nadir[i] <= s.getObjective(i)) {
                   //System.out.println("FALSE: " + i);
                   notWorseNadir[i] = false;
               }
           }
           
           //System.out.println("\nEqual: " + areSame(notWorseNadir, false) + "\n\n");
           
           
           //if (Arrays.asList(notWorseNadir).contains(false)) {
           int j = 0;
           while ((j < m) && decNotWorseNadir) { // the nadir is strictly better in at least one objective
        	   if (checkAllWorse(notWorseNadir, false)) {
        	     if (nadir[j] < s.getObjective(j)) {
   	                 decNotWorseNadir = false;
                     System.out.println("FALSE strictly");
   	             } else {
	                	 decNotWorseNadir = true;
   	             } 
               } else {
            	   decNotWorseNadir = true;
               }	   
        	     
                 j++;
                 //System.out.println("j: " + j + "\n");
        	   
           } 
        	   
	          
           //}
        	   
           if (decNotWorseNadir) {
               newpopulation.add((S) s);
           } 
        	   
           //System.out.println("decNotWORSE: " + decNotWorseNadir);
           //System.out.println("##################");
       }
       
       if (newpopulation.size() == 0) {
    	   System.out.println("############ All WORSE THAN NADIR");
    	   //List<Double> saux = new ArrayList<Double>(); 
   		
   		   //saux.add(-6.2);
   		   //saux.add(-9.2);
   		   //saux.add(-12.1);
    	   List<S> nPopWorse = createIniPopAllWorseNadir(pSize, prob);
    	   newpopulation.addAll(nPopWorse);
       }
       
       System.out.println("---------------------- Pop Handle: Size new Pop: " + newpopulation.size());
       
       return newpopulation;
   }
   
   private boolean checkAllWorse(boolean[] arr, boolean v) 
     { 
       // Put all array elements in a HashSet 
		List<Boolean> aList = new ArrayList<Boolean>();
		for (int i = 0; i < arr.length; i++) {
			aList.add(arr[i]);
		}
				
		//for (Boolean a: aList) {
		//	System.out.println("List: " + a);
	//	}
       Set<Boolean> c = new HashSet<>(aList); 
       boolean internalv = c.contains(v);
       //System.out.println("Size: " + c.size() + " - bool: " + internalv);
       //for (Iterator<Boolean> it = c.iterator(); it.hasNext(); ) {
         //  System.out.println("Set int:" + it.next() );
       //}
       
       // If all elements are same, size of 
       // HashSet should be 1. As HashSet contains only distinct values. 
       return (c.size() == 1 && internalv); 
     } 
   
   private List<S> createIniPopAllWorseNadir(int size, Problem problem) {
	    List<S> population = new ArrayList<>(size);
	    for (int i = 0; i < size; i++) {
	      S newIndividual = (S) problem.createSolution();
	      population.add(newIndividual);
	    }
	    return population;
	  }

   public List removeWorseThanNadirNoStrict(List population, double[] nadir, int m, int pSize, Problem prob) {
       List<S> newpopulation = new ArrayList<>();
       for (Object o : population) {
           Solution s = (Solution) o;
    	   boolean decNotWorseNadir = true;
           boolean[] notWorseNadir = new boolean[m];
          
           for (int k = 0; k < notWorseNadir.length; k++) {
        	   notWorseNadir[k] = true;
           }
           for (int i = 0; i < m; i++) { // the nadir is no worse than s in all objectives. It means that there is something worse than nadir in all objectives
               if (nadir[i] <= s.getObjective(i)) {
                   //System.out.println("FALSE: " + i);
                   notWorseNadir[i] = false;
               }
           }
           
           //System.out.println("\nEqual: " + areSame(notWorseNadir, false) + "\n\n");
           
           
           //if (Arrays.asList(notWorseNadir).contains(false)) {
          // int j = 0;
          // while ((j < m) && decNotWorseNadir) { // the nadir is strictly better in at least one objective
        	//   if (checkAllWorse(notWorseNadir, false)) {
        	  //   if (nadir[j] < s.getObjective(j)) {
   	            //     decNotWorseNadir = false;
                  //   System.out.println("FALSE strictly");
   	             //} else {
	               // 	 decNotWorseNadir = true;
   	             //} 
               //} else {
            	 //  decNotWorseNadir = true;
               //}	   
        	     
                 //j++;
                 //System.out.println("j: " + j + "\n");
        	   
           //}
           
           decNotWorseNadir = checkAllBetterNadir(notWorseNadir, false);
        	   
	          
           //}
        	   
           if (decNotWorseNadir) {
               newpopulation.add((S) s);
           } 
        	   
           //System.out.println("decNotWORSE: " + decNotWorseNadir);
           //System.out.println("##################");
       }
       
       if (newpopulation.size() == 0) {
    	   System.out.println("///////////////////////////////////// WORSE THAN NADIR");
    	   //List<Double> saux = new ArrayList<Double>(); 
   		
   		   //saux.add(-6.2);
   		   //saux.add(-9.2);
   		   //saux.add(-12.1);
    	   List<S> nPopWorse = createIniPopAllWorseNadir(pSize, prob);
    	   newpopulation.addAll(nPopWorse);
       }
       
       System.out.println("---------------------- Pop Handle No Strict: Size new Pop: " + newpopulation.size());
       
       return newpopulation;
   }
   
   private boolean checkAllBetterNadir(boolean[] arr, boolean v) 
   { 
       // Put all array elements in a HashSet 
		int count = 0;
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == v) {
				count++;
			}
		}
				
		// System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  Count " + count);
      return (count == 0); 
   } 
   
   
   public List<S> removeRepeatedSolutionsInteger(List<S> pop) throws IOException{
	   List<Integer> firstCompare = new ArrayList<Integer>();
	   List<Integer> secondCompare = new ArrayList<Integer>();
	   Set<Integer> foundIndex = new LinkedHashSet<Integer>();
	   List<Integer> foundIndexList = new ArrayList<Integer>();
	   
	   //SaveFiles fBefore = new SaveFiles();
	   //fBefore.saveFileAnyValues("BEFOREPop", pop);
	   	   
	   //Set<S> popSet = new LinkedHashSet<>(receivedPop);
	   //receivedPop.clear();
	   //receivedPop.addAll(popSet);
	   for (int i = 0; i < pop.size() - 1; i++) {
		   for (int nvf = 0; nvf < pop.get(i).getNumberOfVariables(); nvf++) {
			   firstCompare.add((Integer) pop.get(i).getVariableValue(nvf));
		   }
		   //System.out.println("First: " + firstCompare + " i: " + i);
		   for (int j = (i+1); j < pop.size(); j++) {
			   for (int nvs = 0; nvs < pop.get(j).getNumberOfVariables(); nvs++) {
				   secondCompare.add((Integer) pop.get(j).getVariableValue(nvs));
			   }
			   if (firstCompare.equals(secondCompare)){
				   //System.out.println("Equal: " + secondCompare + "  j: " + j);
				   foundIndex.add(j);
				   //pop.remove(j);
			   }
			   secondCompare.clear();
		   } // end For j
		  firstCompare.clear();
	   } // end For i*/
	   
	   //System.out.println("Pop Size REMOVING REPEATED - Before: " + pop.size());
	   foundIndexList.addAll(foundIndex);
	   Collections.sort(foundIndexList, Collections.reverseOrder());
	   for (int ind : foundIndexList)
	       pop.remove(ind);
	   //System.out.println("Pop Size REMOVING REPEATED - After: " + pop.size());
	   
	   //SaveFiles fAfter = new SaveFiles();
	   //fAfter.saveFileAnyValues("AFTERPop", pop);
	   return pop;
   }
   
   
   
}
