package HF_LA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import HF_RandomGenerator.RandomGenerator;
import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.comparator.impl.OverallConstraintViolationComparator;

public class LAutil<S extends Solution<?>> {
	
	public LAutil(){
		super();
	}
	
	public int showRandomInteger(int aStart, int aEnd){
	    if (aStart > aEnd) {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    
	    Random aRandom = RandomGenerator.getRandomGenerator();
	    //get the range, casting to long to avoid overflow problems
	    long range = (long)aEnd - (long)aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * aRandom.nextDouble());
	    int randomNumber =  (int)(fraction + aStart);  
	    return randomNumber;
	  }
	//return the number of the number of dominating solutions, non-dominated solutions and dominated solutions via parameter comparing new population to the old one.
	public ArrayList<Integer> getDomincanceSolNo(List<S> populationNew, List<S> populationOld){
		Comparator dominance_ = new DominanceComparator();
		Comparator constraint_ = new OverallConstraintViolationComparator();
		
		int dominatingCount = 0;
		int nonDominatingCount = 0;
		int dominatedCount = 0;
		int flagDominate = -2; 
		int dominatedFlag = 0;
		for(int i = 0; i < populationNew.size(); i++){
			int domianceCount = 0;
			for(int j = 0; j < populationOld.size(); j++){
				flagDominate = constraint_.compare(populationNew.get(i), populationOld.get(j));
				if (flagDominate == 0) {
					flagDominate =dominance_.compare(populationNew.get(i), populationOld.get(j));
				}
				if(flagDominate == 1){
					dominatedFlag = 1;
					break;
				}else if(flagDominate == -1){
					domianceCount++;
				}
				
			}
			if(dominatedFlag ==1){
				dominatedCount++;
			}else{
				 if (domianceCount > 0) {
					 // i dominate some solutions in old populations
					 dominatingCount++;
				 }else{
					 nonDominatingCount++;
				 }
			}
			
			dominatedFlag = 0;
			
			int sum = dominatingCount+nonDominatingCount+dominatedCount;
			if(sum != i+1){
				System.out.println("Wrong dominace counting!");
				System.exit(0);
			}

		}
		ArrayList<Integer> countingResult = new ArrayList<Integer>();
		countingResult.add(dominatingCount);
		countingResult.add(nonDominatingCount);
		countingResult.add(dominatedCount);	
		return countingResult;
	}


	
	
}
