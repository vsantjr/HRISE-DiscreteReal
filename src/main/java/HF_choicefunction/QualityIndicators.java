package HF_choicefunction;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import jmetal.qualityIndicator.AlgorithmEffort;
import jmetal.qualityIndicator.HyperVolumeMinimizationProblem;
import jmetal.qualityIndicator.RatioOfNonDominatedIndividuals;
import jmetal.qualityIndicator.UniformDistribution;
import org.uma.jmetal.solution.Solution;

public class QualityIndicators<S extends Solution<?>> {
	jmetal.qualityIndicator.util.MetricsUtil utilities_  ;
	
	public QualityIndicators(){
		utilities_ = new jmetal.qualityIndicator.util.MetricsUtil();	
		
	}
	
	public double getHyperVolume(List<S> population,double[] refernce) throws IOException{
		HyperVolumeMinimizationProblem qualityIndicator = new HyperVolumeMinimizationProblem();
		//double hv = qualityIndicator.hypervolumeForSolutionSetAfterScaled(population); 
		double hv = qualityIndicator.hypervolumeForSolutionSet(population, refernce);
		return hv;
	
	}//getHyperVolume
	
	public double getAlgorithmEffort(long exectuionTime, int populationSize){
		AlgorithmEffort AE = new AlgorithmEffort();
		double algorithmEffort = AE.calculateAlgorithmEffort(exectuionTime, populationSize);
		return algorithmEffort;
	}//getAlgorithmEffort

	public double getUniformDistribution(List<S> population){
		UniformDistribution ud = new UniformDistribution();
		double uniforDistribution = ud.uniforDistributionForSolutionSet(population);
		return uniforDistribution;
	}//getUniformDistribution
	
	public double getRatioOfNonDominatedIndividuals(List<S> population){
		RatioOfNonDominatedIndividuals ROI = new RatioOfNonDominatedIndividuals();
		double ratio = ROI.ratioOfNonDominatedIndividuals(population);
		return ratio;
	}//getRatioOfNonDominatedIndividuals
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// compute the measurement of last population
		RatioOfNonDominatedIndividuals ROI = new RatioOfNonDominatedIndividuals();
		HyperVolumeMinimizationProblem qualityIndicator = new HyperVolumeMinimizationProblem();
		UniformDistribution ud = new UniformDistribution();
		
		int numberOfFiles = 30;
		double[] referencePoint = {9,9.5,1.7};
	    int numberOfObjectives = referencePoint.length;
	    
	    double hypervolume[] = new double[numberOfFiles];
		double ROIArray[] = new double[numberOfFiles];
		double uniforDistribution[] = new double[numberOfFiles];
		int[] duplicated = new int[numberOfFiles];
		double[] duplicatedRatio = new double[numberOfFiles];
		
		for(int i = 0; i < numberOfFiles; i++){
			String LastPopulationFile = "DataTest/HyperVolumeCompute/HH/run_"+i+"/HF_Last_Generated_Population_"+i+".txt";
			String ParetoFrontFile = "DataTest/HyperVolumeCompute/HH/run_"+i+"/HF_ObtainedParetoFront_Final_"+i+".txt";
			//String LastPopulationFile = "DataTest/HyperVolumeCompute/SPEA2/SPEA2_2_30_50/run_"+ i + "/SPEA2_2_30_50_Run_" + i+"_last_generated_population.fun";
			//String ParetoFrontFile = "DataTest/HyperVolumeCompute/SPEA2/SPEA2_2_30_50/run_"+ i + "/SPEA2_2_30_50_Run_" + i+"_final_Pareto_front.fun" ;
			
			double [][] ParetoFront = ROI.utils_.readFront(ParetoFrontFile);
			double[][] LastPopulation = ROI.utils_.readFront(LastPopulationFile);
			
			ROIArray[i] = ROI.ratioOfNonDominatedIndividualsDbl(ParetoFront,LastPopulation);
			hypervolume[i] = qualityIndicator.hypervolume(ParetoFront, numberOfObjectives, referencePoint);
			uniforDistribution[i] = ud.uniformDistribution(ParetoFront);
			
			 int nonDominatedNO = ParetoFront.length;
			 double[][] nonReplicatedFront = ROI.utils_.removeDuplicatePointsonFront(ParetoFront);
			 int nonReplicatedNo = nonReplicatedFront.length;
			 duplicated[i] = nonDominatedNO - nonReplicatedNo;
			 duplicatedRatio[i] = (double) duplicated[i]/nonDominatedNO;
			 
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/HH/HyperVolume.txt"));
			//BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/NSGAII/HyperVolume.txt"));
			//BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/SPEA2/HyperVolume.txt"));
			//BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/IBEA/HyperVolume.txt"));
			for(int i = 0; i < numberOfFiles; i++){
				bw.append(new Double(hypervolume[i]).toString());
				bw.newLine();
			}
			bw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/RNICompute/HH/RNIArray.txt"));
			
			for(int i = 0; i < numberOfFiles; i++){
				bw.append(new Double(ROIArray[i]).toString());
				bw.newLine();
			}
			bw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/UDCompute/HH/UniformDistribution.txt"));
			
			for(int i = 0; i < numberOfFiles; i++){
				bw.append(new Double(uniforDistribution[i]).toString());
				bw.newLine();
			}
			bw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/RNICompute/HH/RNIDuplicatedNumber.txt"));
			
			for(int i = 0; i < numberOfFiles; i++){
				bw.append(new Double(duplicated[i]).toString()+ " "+ new Double(duplicatedRatio[i]).toString());
				bw.newLine();
			}
			bw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
