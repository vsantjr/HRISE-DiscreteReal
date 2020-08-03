package HF_LA;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class RewardPenaltyLA implements AbstractLearningAutomata{

	//int rewardSignal_;
	double lamada1_;
	double lamada2_;
	int chosenHeuristic_;
	double multiplier_;
	double lamada2Multiplier_;
	public RewardPenaltyLA(){
		//rewardSignal_ = -1;
		Properties config = new Properties();
		
/*		String configFile = "HF_Config_Benchmark/ProblemSetting.txt";
		try {
			config.load(new FileInputStream(configFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to load configuration file!");
			System.exit(-1);
		}*/
		lamada1_ = 0.1;
		lamada2_ = 0.1;
		chosenHeuristic_ = -1;
		multiplier_ = 2.0;
		lamada2Multiplier_ = 1.0;
	}
	public RewardPenaltyLA(String configFile){
		Properties config = new Properties();
		
		try {
			config.load(new FileInputStream(configFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to load configuration file!");
			System.exit(-1);
		}
		lamada1_ = 0.1;
		lamada2_ = 0.1;
/*		lamada1_ = Double.parseDouble(config.getProperty("lamada"));
		lamada2_ = Double.parseDouble(config.getProperty("lamada"));*/
		chosenHeuristic_ = -1;
		multiplier_ = Double.parseDouble(config.getProperty("Multipler"));
		//System.out.println("multiplier " + multiplier_);
		lamada2Multiplier_ = 1.0;
	}
	
	public RewardPenaltyLA(double multiplier){
		chosenHeuristic_ = -1;
		multiplier_ = multiplier;
		lamada2Multiplier_ = 1.0;
		lamada1_ = 0.1;
		lamada2_ = 0.1;
	}

	@Override
	public void updateProbabilityPositiveSignal(Object chosenHeuristic, ArrayList<Double> transPro) {
		// TODO Auto-generated method stub
		
		chosenHeuristic_ = (int) chosenHeuristic;
	
		double chosenHeuristicProOld = transPro.get(chosenHeuristic_);
		double chosenHeuristicProNew = chosenHeuristicProOld + 
				lamada1_   * ( 1 - chosenHeuristicProOld);
		
		transPro.set(chosenHeuristic_, chosenHeuristicProNew);
		
		for(int i = 0; i < transPro.size(); i++){
			if(i != chosenHeuristic_){
				double tempHeuristicProOld = transPro.get(i);
				double tempHeuristicProNew = tempHeuristicProOld -
						lamada1_ * tempHeuristicProOld;
				transPro.set(i, tempHeuristicProNew);
			}
		}
//		double sum = 0.0;
//		for(double j:transPro){
//			 sum += j;
//		}
//		
//		if(sum != 1.0){
//			for(double j:transPro){
//				System.out.println(j + " ");
//			}
//			System.err.println("Sum of probability is not equal to 1.0!");
//			System.exit(0);
//		}
	}
	@Override
	public void updateProbabilityNegativeSignal(Object chosenHeuristic, double lamada2,
			int rejectTimes, int numberOfLLHs, ArrayList<Double> transPro) {
		// TODO Auto-generated method stub
		chosenHeuristic_ = (int) chosenHeuristic;
		lamada2_ = lamada2;
		
		double chosenHeuristicProOld = transPro.get(chosenHeuristic_);
		double chosenHeuristicProNew = chosenHeuristicProOld - 
				lamada2_ * lamada2Multiplier_ * rejectTimes *chosenHeuristicProOld;
		
		transPro.set(chosenHeuristic_, chosenHeuristicProNew);
		
		for(int i = 0; i < transPro.size(); i++){
			if(i != chosenHeuristic_){
				double tempHeuristicProOld = transPro.get(i);
				
				double tempHeuristicProNew = tempHeuristicProOld +
						lamada2_ * lamada2Multiplier_ * rejectTimes *(1.0/(numberOfLLHs-1) - tempHeuristicProOld);
				
				transPro.set(i, tempHeuristicProNew);
			}
		}
		
//		double sum = 0.0;
//		for(double j:transPro){
//			 sum += j;
//		}
		
//		if(sum > 1.001){
//			for(double j:transPro){
//				System.out.println(j + " ");
//			}
//			System.err.println("Sum of probability is not equal to 1.0!");
//			System.exit(0);
//		}
	}
	
	@Override
	//precessorHeuristic's transPro and Qvalues
	public void updateProbabilityPositiveSignalQ(Object chosenHeuristic, ArrayList<Double> transPro, ArrayList<Double> Qvalues) {
		// TODO Auto-generated method stub
		//double multiplier = 2.0;
		chosenHeuristic_ = (int) chosenHeuristic;
		//double a = lamada1_;
		double a = lamada1_ + Qvalues.get(chosenHeuristic_)*multiplier_;
		//System.out.println("multiplier_: "+ multiplier_);
		if(a >=1.0){
			a = 0.99;
		}else if(a <0.0){
			a = 0.00;
		}
		//count how many tabued algorithms
		int nonTabuedSize = 0;
		for(int k = 0; k < transPro.size(); k++){
			if(transPro.get(k) > 0.0){
				nonTabuedSize++;
			}
		}
		if(nonTabuedSize -1 > 0){//only update probability if nontabued algorithm number >=2
			double chosenHeuristicProOld = transPro.get(chosenHeuristic_);
			double chosenHeuristicProNew = chosenHeuristicProOld + a  * ( 1 - chosenHeuristicProOld);
			
			transPro.set(chosenHeuristic_, chosenHeuristicProNew);
			
			
			for(int i = 0; i < transPro.size(); i++){
				if(i != chosenHeuristic_){
					if(transPro.get(i) != 0.0){
						double tempHeuristicProOld = transPro.get(i);
						double tempHeuristicProNew = (1.0 -a) * tempHeuristicProOld;
						transPro.set(i, tempHeuristicProNew);
					}
					
				}
			}
		}
		
		
		for(double eachPro:transPro){
			if(eachPro <0.0){
				System.out.println("Negative probaility values:");
				for(int i = 0; i < transPro.size(); i++){
					System.out.print(transPro.get(i)+" ");
				}
				System.out.println("precessorHeuristic's Qvalue to chosenHeuristic "+chosenHeuristic +" is "+ Qvalues);
				System.exit(0);
			}
		}
		double sum = 0.0;
		for(double j:transPro){
		 sum += j;
		}
		
		if(sum > 1.001|| sum <0.99){
			for(double j:transPro){
				System.out.println(j + " ");
			}
			System.err.println("Sum of probability is not equal to 1.0!");
			System.exit(0);
		}
	}
	
	@Override
	public void updateProbabilityNegativeSignalQ(Object chosenHeuristic, int numberOfLLHs,ArrayList<Double> transPro, ArrayList<Double> Qvalues) {
		// TODO Auto-generated method stub
		chosenHeuristic_ = (int) chosenHeuristic;
		//double multiplier = 2.0;
		double chosenHeuristicProOld = transPro.get(chosenHeuristic_);
		//double b = lamada2_;
		double b = lamada2_+ Qvalues.get(chosenHeuristic_)*multiplier_;
		//System.out.println("multiplier_: "+ multiplier_);
		
		if(b >=1.0){
			b = 0.99;
		}else if(b <0.0){
			b = 0.00;
		}
		//count how many tabued algorithms
		int nonTabuedSize = 0;
		for(int k = 0; k < transPro.size(); k++){
			if(transPro.get(k) > 0.0){
				nonTabuedSize++;
			}
		}
		if(nonTabuedSize -1 > 0){//only update probability if nontabued algorithm number >=2
			double chosenHeuristicProNew = (1.0 - b)  *chosenHeuristicProOld;
			
			transPro.set(chosenHeuristic_, chosenHeuristicProNew);
					
			for(int i = 0; i < transPro.size(); i++){
				if(i != chosenHeuristic_){
					if(transPro.get(i) !=0.0){
						double tempHeuristicProOld = transPro.get(i);
						
						double tempHeuristicProNew = tempHeuristicProOld + b*(1.0/(double)(nonTabuedSize-1) - tempHeuristicProOld);
						
						transPro.set(i, tempHeuristicProNew);
					}
					
				}
			}
		}

		for(double eachPro:transPro){
			if(eachPro <0.0){
				System.out.println("Negative probaility values:");
				for(int i = 0; i < transPro.size(); i++){
					System.out.print(transPro.get(i)+" ");
				}
				System.out.println("precessorHeuristic's Qvalue to chosenHeuristic "+chosenHeuristic +" is "+ Qvalues);
				System.exit(0);
			}
		}
		double sum = 0.0;
		for(double j:transPro){
		 sum += j;
		}
		
		if(sum > 1.001|| sum <0.99){
			for(double j:transPro){
				System.out.println(j + " ");
			}
			System.err.println("Sum of probability is not equal to 1.0!");
			System.exit(0);
		}
	}


}
