package HF_LA;

import java.util.ArrayList;
import java.util.Random;

import HF_RandomGenerator.RandomGenerator;

public class RouletteSelectionLLHs implements AbstractSelectionLLHs {

	
	public RouletteSelectionLLHs(){
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
	
	@Override
	//how about heuristics with the same probability?
	public int selectionLLHs(ArrayList<Double> heuristicTransPro) {
		// TODO Auto-generated method stub
		Random rand = RandomGenerator.getRandomGenerator();
		double rnd = rand.nextDouble();
		int chosenHeuristic = -1;
/*		ArrayList<Integer> equalProHeuristic = new ArrayList<Integer>();
		
		for (int j = 0; j < heuristicTransPro.size(); j++) {
			if (heuristicTransPro.get(j) >= rnd) {
				equalProHeuristic.add(j);
			}
		}
		
		// random choose one of the equal maximum
		if (equalProHeuristic.size() > 1) {
			int position = this.showRandomInteger(0,
					equalProHeuristic.size() - 1);
			chosenHeuristic = equalProHeuristic.get(position);
		}else{
			for(int i = 0; i < heuristicTransPro.size(); i++){
				if(heuristicTransPro.get(i) != 0.0){
					rnd -= heuristicTransPro.get(i);
					if(rnd <= 0.0){
						chosenHeuristic = i;
						break;
					}
				}
			}
		}*/
		
		for(int i = 0; i < heuristicTransPro.size(); i++){
			if(heuristicTransPro.get(i) != 0.0){
				rnd -= heuristicTransPro.get(i);
				if(rnd <= 0.0){
					chosenHeuristic = i;
					break;
				}
			}
		}
		
		if(chosenHeuristic == -1){
			System.out.println("Random variale: "+ rnd);
			for(int j = 0; j < heuristicTransPro.size(); j++){
				System.out.print(heuristicTransPro.get(j) + " ");
			}
		}
		
//		ArrayList<Integer> sameProHeuristics = new ArrayList<Integer>();
//		LAutil util = new LAutil();
//		
//		for(int j = 0; j < heuristicTransPro.size(); j++){
//			
//				if(heuristicTransPro.get(j) == heuristicTransPro.get(chosenHeuristic)){
//					sameProHeuristics.add(j);
//				}			
//		}
//		
//		if(sameProHeuristics.size() > 1){
//			int position = util.showRandomInteger(0, sameProHeuristics.size() - 1);
//			chosenHeuristic = sameProHeuristics.get(position);
//		}
		return chosenHeuristic;
	}

	@Override
	public int selectionLLHs(ArrayList<Double> heuristicTransPro,
			double temperature) {
		// TODO Auto-generated method stub
		return -1;
	}
	public static void main(String[] args){
		
		ArrayList<Double> pro = new ArrayList<Double>();
		pro.add(0.0);
		pro.add(0.49303318116927075);
		pro.add(0.48861798972619636);
		
		int chosenOne = -1;
		RouletteSelectionLLHs rl = new RouletteSelectionLLHs();
		chosenOne = rl.selectionLLHs(pro);
		
	}

}
