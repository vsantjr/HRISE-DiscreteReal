package HF_LA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import HF_RandomGenerator.RandomGenerator;

public class epsilonGreedySelectionLLHs implements AbstractSelectionLLHs {

	public epsilonGreedySelectionLLHs() {
		super();
	}

	private int showRandomInteger(int aStart, int aEnd) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}

		Random aRandom = RandomGenerator.getRandomGenerator();
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

	/*
	 * most of the time choose the greedy choice (the highest probability), with
	 * epsilon probability chose randomly
	 */
	@Override
	public int selectionLLHs(ArrayList<Double> heuristicTransPro, double epsilon) {
		// TODO Auto-generated method stub

		double maximumValue = 0.0;
		int chosenHeuristic = -1;
		int greedyChoice = -1;
		// double epsilon = 0.1;
		Random rand = RandomGenerator.getRandomGenerator();
		double rnd = rand.nextDouble();
		int nonZeroPro = 0;
		for(double eachPro:heuristicTransPro){
			if(eachPro > 0.0){
				nonZeroPro++;
			}
		}
		int[] nonZeroProAlgorithmIndex = new int[nonZeroPro];
		int nonZeroIndex = -1;
		for(int i =0; i < heuristicTransPro.size(); i++){
			double temp = heuristicTransPro.get(i);
			if(temp > 0.0){
				nonZeroIndex++;
				nonZeroProAlgorithmIndex[nonZeroIndex] = i;
			}
		}
		// epsilon was orginally set as 0.0;
		ArrayList<Integer> restHeuristics = new ArrayList<Integer>();
		if (rnd <= epsilon) {
			
			// get the maximum probability
			for (int i = 0; i < heuristicTransPro.size(); i++) {
				if(heuristicTransPro.get(i) > 0.0){
					if (heuristicTransPro.get(i) > maximumValue) {
						maximumValue = heuristicTransPro.get(i);
						greedyChoice = i;
					}
				}
				
			}
			// find how many heuristics have the same maximum probability

			ArrayList<Integer> equalMaximumHeuristic = new ArrayList<Integer>();
			for (int j = 0; j < heuristicTransPro.size(); j++) {
				if (heuristicTransPro.get(j) == maximumValue) {
					equalMaximumHeuristic.add(j);
				}
			}

			// random choose one of the equal maximum
			if (equalMaximumHeuristic.size() > 1) {
				int position = this.showRandomInteger(0,
						equalMaximumHeuristic.size() - 1);
				greedyChoice = equalMaximumHeuristic.get(position);
			}

			for (int i = 0; i < heuristicTransPro.size(); i++) {
				if (i != greedyChoice) {
					restHeuristics.add(i);
				}
			}

			chosenHeuristic = greedyChoice;

		} else {

	
			RouletteSelectionLLHs rouletteSelect = new RouletteSelectionLLHs();
			chosenHeuristic = rouletteSelect.selectionLLHs(heuristicTransPro);

/*			int position = this.showRandomInteger(0, heuristicTransPro.size() - 1);
			chosenHeuristic = position;*/
		}
		if(chosenHeuristic == -1){
			System.out.print("No valid heuristic is chosen in epsilon greedy selection.");
			System.exit(0);
		}
		return chosenHeuristic;
	}

	@Override
	public int selectionLLHs(ArrayList<Double> heuristicTransPro) {
		// TODO Auto-generated method stub
		return -1;
	}

}
