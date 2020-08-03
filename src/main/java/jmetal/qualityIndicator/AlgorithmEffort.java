package jmetal.qualityIndicator;

public class AlgorithmEffort {
	jmetal.qualityIndicator.util.MetricsUtil utils_;
	
	public AlgorithmEffort(){
		utils_ = new jmetal.qualityIndicator.util.MetricsUtil();
	}
	
	public double calculateAlgorithmEffort(long executionTime, int populationSize){
		double AE = 0;
		AE = (double)(executionTime/(double)populationSize);
		return AE;
		
	}
	
}
