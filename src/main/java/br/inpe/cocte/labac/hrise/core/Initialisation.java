package br.inpe.cocte.labac.hrise.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import javax.management.JMException;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.DominanceComparator;
import br.inpe.cocte.labac.hrise.similarity.EuclideanDistance;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;
import br.inpe.cocte.labac.hrise.util.SaveFiles;


public class Initialisation<S extends Solution<?>> {
	
	private static final String SOLUTION_LIST_NULL_EXCEPTION="The solution list is null";
	private static final String SOLUTION_LIST_EMPTY_EXCEPTION="The solution list is empty";
	private  List<List<S>> joined; // It stores the alg and respective population after execution  
	
	
	public Initialisation(){
		this.joined = new ArrayList<List<S>>();
	}
	
	
	public List<List<String>> initializeLLHs(String[] llhs, int popSize, int numGen, Problem prob, double[] ideal, boolean first, List<S> jArchive) throws ConfigurationException, JMException, IOException {
	    	List<List<Double>> quality = new ArrayList<List<Double>>();
	    	List<List<Double>> qualityNorm; 
	    	List<Double> eucDist = new ArrayList<Double>();
	    	List<List<String>> posRanking = new ArrayList<List<String>>();    	
	    	
	    	for (int i = 0; i < llhs.length; i++) { 
	       	   for (int j = 0; j < llhs.length; j++) { 
	       		   CreateAlgorithms cr = new CreateAlgorithms();
	       		   Algorithm alg = cr.createAlg(llhs[j], popSize, numGen, prob, first, jArchive);
	               
	               alg.run();
	               List result = (List) alg.getResult();
	               PopulationHandler popHandler = new PopulationHandler();
	               List<S> archive = popHandler.generateNonDominated(result, prob); 
	               
	               //quality.add(HRISEMainReal.printResultsAsList(archive, prob, false));
	               
	    	
	           }
	                 	
	       	   Normalisation normIndic = new Normalisation();
	       	   qualityNorm = normIndic.normaliseIndicators(quality);
			   
			   
			   for (List<Double> yesNormal: qualityNorm ) {
				    double[] valQI = {2.0*yesNormal.get(0), yesNormal.get(1), yesNormal.get(2), yesNormal.get(3)}; 
					eucDist.add(new EuclideanDistance().d(valQI, ideal));						
				}
			   
			   
			    
			   
	       	    posRanking.add(SelectionSupport.rankingLLHs(llhs, eucDist));   
	       	    quality.clear(); 
	       	    eucDist.clear();
	       	   
	    	}
	    	
	    	return posRanking;
	     } 	
	 
		
	public String compLowInitialisationRPO(String[] llhs, int popSize, int numIterations, Problem prob, boolean first, List<S> jArchive, int index) throws ConfigurationException, JMException, IOException {
    	Map<String, Double> nsgaiiMax = new LinkedHashMap<String, Double>();
    	Map<String, Double> ibeaMax = new LinkedHashMap<String, Double>();
    	Map<String, Double> spea2Max = new LinkedHashMap<String, Double>();
    	/*
    	 * 0 = NSGA-II
    	 * 1 = IBEA
    	 * 2 = SPEA2
    	 */
       	for (int j = 0; j < llhs.length; j++) { 
       	  CreateAlgorithms cr = new CreateAlgorithms();
       	  Algorithm alg = cr.createAlg(llhs[j], popSize, numIterations, prob, first, jArchive);
          alg.run();
          List result = (List) alg.getResult();
          PopulationHandler popHandler = new PopulationHandler();
          List<S> popHInit = popHandler.generateNonDominated(result, prob); // archive is the current population after executing an alg.
          popHInit = popHandler.removeRepeatedSolutionsInteger(popHInit);
          this.joined.add(popHInit);
          for (int k = 0; k < prob.getNumberOfObjectives(); k++) {
              double[] objTemp = new double[popHInit.size()];
              objTemp = getObjectiveFromPop(popHInit,k);
              double max = Arrays.stream(objTemp).max().getAsDouble(); 
              switch (llhs[j]) {
            		case "NSGAII" :
            		   nsgaiiMax.put("obj"+k, max);
            		   break;
            		case "IBEA" :
            		   ibeaMax.put("obj"+k, max);
            		   break;
            		case "SPEA2" :
            			spea2Max.put("obj"+k, max);
            			break;   
            		default:
            		    System.out.println("#### Invalid obj!");
              }   
          }
        }
               
       	return findBestLLHInit(nsgaiiMax, ibeaMax, spea2Max, prob);
    } 	
	 
	
	private String findBestLLHInit(Map<String, Double> ns, Map<String, Double> ib, Map<String, Double> sp , Problem pr){
		String bestLLH = " ";
		PopulationHandler pHand = new PopulationHandler();
		Map<String, Double> nsgaiiRPO = new LinkedHashMap<String, Double>();
    	Map<String, Double> ibeaRPO = new LinkedHashMap<String, Double>();
    	Map<String, Double> spea2RPO = new LinkedHashMap<String, Double>();
		
		for (int k = 0; k < pr.getNumberOfObjectives(); k++) {
			List<Double> maxValues = new ArrayList<Double>();
      	    maxValues.add(ns.get("obj"+k));
      	    maxValues.add(ib.get("obj"+k));
      	    maxValues.add(sp.get("obj"+k));
      	    double minAll = findMinValue(maxValues);
      	    
      	    if (minAll == ns.get("obj"+k)) { // NSGAII is the best
      	       nsgaiiRPO.put("obj"+k, 0.0);
      	       ibeaRPO.put("obj"+k, (ib.get("obj"+k) - minAll) / minAll);
      	       spea2RPO.put("obj"+k, (sp.get("obj"+k) - minAll) / minAll);	 
      	    } else { // 1st else
      	    	 if (minAll == ib.get("obj"+k)) { // IBEA is the best
            	     ibeaRPO.put("obj"+k, 0.0);
            	     nsgaiiRPO.put("obj"+k, (ns.get("obj"+k) - minAll) / minAll);
            	     spea2RPO.put("obj"+k, (sp.get("obj"+k) - minAll) / minAll);	 
                 } else { // 2nd else
                	 if (minAll == sp.get("obj"+k)) { // SPEA2 is the best
                	     spea2RPO.put("obj"+k, 0.0);
                	     nsgaiiRPO.put("obj"+k, (ns.get("obj"+k) - minAll) / minAll);
                	     ibeaRPO.put("obj"+k, (ib.get("obj"+k) - minAll) / minAll);	
                      }
		      } // 2nd else
		  } // 1st else    
      	    
		}
		
		
		 double sumnsgaiiRPO = 0.0;
		 double sumibeaRPO = 0.0;
		 double sumspea2RPO = 0.0;
		 
		 for(Entry<String, Double> sr : nsgaiiRPO.entrySet()){
			   //System.out.println("RPO NS: " + sr.getKey() + " - " + sr.getValue());  	
			   sumnsgaiiRPO += sr.getValue();
		  }
     	  
     	  for(Entry<String, Double> sr : ibeaRPO.entrySet()){
			   //System.out.println("RPO IB: " + sr.getKey() + " - " + sr.getValue());  
			   sumibeaRPO += sr.getValue();
		  }
     	  
     	 for(Entry<String, Double> sr : spea2RPO.entrySet()){
			   //System.out.println("RPO SP: " + sr.getKey() + " - " + sr.getValue());  	 
			   sumspea2RPO += sr.getValue();
		  }
		
     	System.out.println("#### RPO - NSGA-II: " + sumnsgaiiRPO + " - IBEA: " + sumibeaRPO + " - SPEA2: " + sumspea2RPO); 
     	
     	List<Double> sumAll = new ArrayList<Double>();
     	sumAll.add(sumnsgaiiRPO);
     	sumAll.add(sumibeaRPO);
     	sumAll.add(sumspea2RPO);
     	double minAllFinal = findMinValue(sumAll);
     	
     	if (minAllFinal == sumnsgaiiRPO) {
     		bestLLH = "NSGAII";
     	} else {
     		if (minAllFinal == sumibeaRPO) {
         		bestLLH = "IBEA";
     	    } else {
     	    	if (minAllFinal == sumspea2RPO) {
             		bestLLH = "SPEA2";
     	        } else {
     	        	System.out.println("RANDOM LLH Selection");
     	        	int rIndex = pHand.getRandomSolution(3); 
     	        	switch (rIndex) {
     	        	  case 0:
     	        		bestLLH = "NSGAII";
     	        		break;
     	        	  case 1:
     	        		bestLLH = "IBEA";
     	        		break;
     	        	  case 2:
     	        		bestLLH = "SPEA2";
     	        		break;
     	              default:
     	            	System.out.println("Invalid LLH!");  
     	        	}	
     	        }
     	    }	
     	}
     	 	
		return bestLLH;
	}
	
	
   private double findMinValue (List<Double> ind) {
		
		
		
		Double minHV = ind
			      .stream()
			      .mapToDouble(v -> v)
			      .min().orElseThrow(NoSuchElementException::new);
		
		Double maxHV = ind
			      .stream()
			      .mapToDouble(v -> v)
			      .max().orElseThrow(NoSuchElementException::new);
		
		
		
		return minHV;
		
	}
	
	 private <S> S findWorstSol(Collection<S> solutionList, Comparator<S> comparator) {
		    if ((solutionList == null) || (solutionList.isEmpty())) {
		      throw new IllegalArgumentException("No solution provided: "+solutionList);
		    }

		    S worstKnown = solutionList.iterator().next();
		    for (S candidateSolution : solutionList) {
		      int result = comparator.compare(worstKnown, candidateSolution);	
		      System.out.println("result: " + result);
		      if (result < 0) {
		        worstKnown = candidateSolution;
		      }
		    }

		    return worstKnown;
		  }
	 

	 
	 private <S> S findWorstSolNew(Collection<S> solutionList, Comparator<S> comparator) {
		    if ((solutionList == null) || (solutionList.isEmpty())) {
		      throw new IllegalArgumentException("No solution provided: "+solutionList);
		    }

		    
		    int index = 0;
		    //S worstKnown = solutionList.get(0) ;
		    S candidateSolution ;
		    
		    //S worstKnown = solutionList.iterator().next();
		    //for (S candidateSolution : solutionList) {
		      //if (comparator.compare(worstKnown, candidateSolution) < 0) {
		        //worstKnown = candidateSolution;
		      //}
		    //}

		    return null;
		  }
	 
	 
	 
	 
	 private <S> int findIndexBestSol(List<S> solutionList, Comparator<S> comparator) {
		    if (solutionList == null) {
		      throw new JMetalException(SOLUTION_LIST_NULL_EXCEPTION) ;
		    } else if (solutionList.isEmpty()) {
		      throw new JMetalException(SOLUTION_LIST_EMPTY_EXCEPTION) ;
		    } else if (comparator == null) {
		      throw new JMetalException("The comparator is null") ;
		    }

		    int index = 0;
		    S bestKnown = solutionList.get(0) ;
		    S candidateSolution ;

		    int flag;
		    for (int i = 1; i < solutionList.size(); i++) {
		      candidateSolution = solutionList.get(i);
		      flag = comparator.compare(bestKnown, candidateSolution);
		      System.out.println("flag: " + flag);
		      if (flag == 1) {
		        index = i;
		        bestKnown = candidateSolution;
		      }
		    }

		    return index;
		  }
	 
	 
	  public <S extends Solution<?>> double[] getObjectiveFromPop(List<S> solutionList, int objective) {
		    double[] result = new double[solutionList.size()] ;

		    for(int i=0; i<solutionList.size();i++){
		      result[i] = solutionList.get(i).getObjective(objective);
		    }
		    return result;
		  }
	 
	 
	 
	 public List<List<String>> quickInitialisation(String[] llhs, int popSize, int numIterations, Problem prob, boolean first, List<S> jArchive, int index) throws ConfigurationException, JMException, IOException {
	    	//List<List<Double>> quality = new ArrayList<List<Double>>();
	    	//List<List<Double>> qualityNorm; 
	    	//List<Double> eucDist = new ArrayList<Double>();
	    	List<List<String>> posRanking = new ArrayList<List<String>>();    	
	    	List<S> worstSol = new ArrayList<S>();
	    	//for (int i = 0; i < llhs.length; i++) { // iteration where in each an LLH runs
	    	
	    	SaveFiles sf = new SaveFiles();
	    	
	    	/*
	    	 * 0 = NSGA-II
	    	 * 1 = IBEA
	    	 * 2 = SPEA2
	    	 */
	       	   for (int j = 0; j < llhs.length; j++) { 
	       		   CreateAlgorithms cr = new CreateAlgorithms();
	       		   Algorithm alg = cr.createAlg(llhs[j], popSize, numIterations, prob, first, jArchive);
	               //StandardMetaheuristic alg = HHOSTMain.createAlg(llhs[j], popSize, numGen, prob);

	               alg.run();
	               List result = (List) alg.getResult();
	               PopulationHandler popHandler = new PopulationHandler();
	               List<S> popHInit = popHandler.generateNonDominated(result, prob); // archive is the current population after executing an alg.
	               popHInit = popHandler.removeRepeatedSolutions(popHInit);
	               
	               
	               //System.out.println(HHOSTMain.printResults(archive, prob));  // depois retirar
	               //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ SIZE INIT POP: " + popHInit.size());
	               sf.saveFunVar(index, llhs[j] + "_INIT", prob, popHInit);
	             //  this.joined.put(llhs[j], popHInit);
	               worstSol.add(findWorstSol(popHInit, new DominanceComparator<S>()));
	           
	               //quality.add(HHOSTMain.printResultsAsList(archive, prob, false));
	               //Logger.getLogger(HHOSTMain.class.getName()).log(Level.INFO, "INITIALIZE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	    	
	           }
	               //for (S w: worstSol) {
	            	 //  System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  Index worst: " + w);
	               //}
	               
	               
	               //System.out.println("okokokokokokokokokokokokokok");
	               int indexBestNadir = findIndexBestSol(worstSol, new DominanceComparator<S>() );
	               
	               System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% "
	               		+ "Best of Nadir: " + indexBestNadir);    
	       	
	       	   //Normalisation normIndic = new Normalisation();
	       	   //qualityNorm = normIndic.normaliseIndicators(quality);
			   //System.out.println("Main Qual Normal: " + qualityNorm);
			   
			   //for (List<Double> yesNormal: qualityNorm ) {
				 //   double[] valQI = {2.0*yesNormal.get(0), yesNormal.get(1), yesNormal.get(2), yesNormal.get(3)}; // out RNI
					//eucDist.add(new EuclideanDistance().d(valQI, ideal));						
				//}
			   
			   //double[] valQI = {2.0*hypValue, igdValue, epsilonValue/2.0, spreadValue, rniValue}; // Hypervolume and Epsilon: weight = 2; remaining: weight = 1
	           //eucDist.add(new EuclideanDistance().d(valQI, ideal));
	       	
	       	 //eucDistance.clear();
			    //System.out.println("**** Euc Distance after Normalisation: " + eucDist);
			   
	       	   //posRanking.add(SelectionSupport.rankingLLHs(llhs, eucDist));   
	       	   //quality.clear(); 
	       	   //eucDist.clear();
	       	   //System.out.println(">>>>>>> INITIALISATION - END OF STAGE: " + (i+1) + " <<<<<<<<");
	    	//}
	    	
	    	return posRanking;
	     } 	
	 
}
