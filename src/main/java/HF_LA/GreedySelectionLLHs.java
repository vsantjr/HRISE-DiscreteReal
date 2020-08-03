package HF_LA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GreedySelectionLLHs implements AbstractSelectionLLHs{
	
	public GreedySelectionLLHs(){
		super();
	}

	@Override
	public int selectionLLHs(ArrayList<Double> heuristicTransPro) {
		// TODO Auto-generated method stub
		double maximumValue = -Double.MAX_VALUE;
		
		int greedyChoice = -1;
		int count = -1;
		for(double i:heuristicTransPro){
			count++;
			
			if(i > 0.0 && i > maximumValue){
				greedyChoice = count;
				maximumValue = i;
			}
		}
		
		//remove the index if its probability is not equal to the maximumValue.
		ArrayList<Integer> maximumIndex = new ArrayList<Integer>(heuristicTransPro.size());
		for(int i = 0; i < heuristicTransPro.size(); i++){
			maximumIndex.add(i);
		}
		
		for(int j = maximumIndex.size()-1; j >=0 ; j--){
			if(heuristicTransPro.get(j) != maximumValue){
				maximumIndex.remove(j);
			}
		}
		
		if(maximumIndex.size() > 1){
			Random rnd = new Random();
			int position = rnd.nextInt(maximumIndex.size() - 1);
			greedyChoice = maximumIndex.get(position);
		}
		
		
		
		return greedyChoice;
	}
	
public static void main(String[] args){
		
		ArrayList<Double> pro = new ArrayList<Double>();
		pro.add(0.2);
		pro.add(0.3);
		pro.add(0.5);
		
		int chosenOne = -1;
		GreedySelectionLLHs gl = new GreedySelectionLLHs();
		chosenOne = gl.selectionLLHs(pro);
		System.out.println(chosenOne);
		
	}

@Override
public int selectionLLHs(ArrayList<Double> heuristicTransPro, double temperature) {
	// TODO Auto-generated method stub
	return -1;
}
}
