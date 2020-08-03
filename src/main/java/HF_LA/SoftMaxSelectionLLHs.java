package HF_LA;

import java.util.ArrayList;

public class SoftMaxSelectionLLHs implements AbstractSelectionLLHs{

	@Override
	public int selectionLLHs(ArrayList<Double> heuristicTransPro) {
		// TODO Auto-generated method stub
		
		
		return -1;
	}

	@Override
	public int selectionLLHs(ArrayList<Double> heuristicTransPro,
			double temperature) {
		ArrayList<Double> temp = new ArrayList<Double>();
		double tempPro = 0.0;
		double sum = 0.0;
		// TODO Auto-generated method stub
		for(double pro:heuristicTransPro ){
			if(pro != 0.0){
				 tempPro = Math.exp(pro/temperature);
				 temp.add(tempPro);
				 sum += tempPro;
			}else{
				temp.add(0.0);
			}

		}
		int count = -1;

		for(double pro:heuristicTransPro ){
			count++;
			double newPro = temp.get(count)/sum;			
			temp.set(count, newPro);
			
		}
		
//		for(double temPro:temp){
//			int tempcount = 0;
//			if(temPro == 0.0){
//				temp.remove(tempcount);
//			}
//			tempcount++;
//		}
		
		RouletteSelectionLLHs rouletteSelect = new RouletteSelectionLLHs();
		int chosenHeuristic  = -1;
		if(temperature >=1.0){
			chosenHeuristic = rouletteSelect.selectionLLHs(temp);	
		}else{
			int tempMaxiProIndex = -1;
			double tempMaxPro = 0.0;
			for(int i = 0; i < temp.size(); i++){
				if(temp.get(i) > tempMaxPro){
					tempMaxiProIndex = i;
					tempMaxPro = temp.get(i);
				}
			}
			chosenHeuristic = tempMaxiProIndex;
		}
			
		
		return chosenHeuristic;
	}
	
	public static void main(String[] args){
		ArrayList<Double> pro = new ArrayList<Double>();
		pro.add(0.5);
		pro.add(0.3);
		pro.add(0.2);
		double temperature = 10.0;
		int chosenOne = -1;
		SoftMaxSelectionLLHs rl = new SoftMaxSelectionLLHs();
		chosenOne = rl.selectionLLHs(pro,temperature);
		
	}

}
