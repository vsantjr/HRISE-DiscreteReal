package jmetal.qualityIndicator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

/**
*This is a pareto front measurement - Ratio of Non-dominated Individuals(RNI), it evaluates the ratio of non-dominated solutions in
* a population. RNI is between [0,1].
*It is acoording to the paper "Evolutionary Algorithms for Multi-Objective \optimization: Performance Assessments and Comparisons" 
*by K.C. Tan, T.H.Lee and E.F.Khor
* @author Wenwen Li
* @param (SolutionSet) population
* @return the uniform distribution of solutions on this front
*/

public class RatioOfNonDominatedIndividuals<S extends Solution<?>> {
	
	public jmetal.qualityIndicator.util.MetricsUtilPlus utils_;
	public RatioOfNonDominatedIndividuals(){
		utils_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();
	}
	
	public double ratioOfNonDominatedIndividuals(List<S> population){
		
		//step1 get nondominated solution set
		List<S> front=SolutionListUtils.getNondominatedSolutions(population);
		
		//Step2. write solutions in a front/population to an array.
		double[][] frontMatrix = SolutionListUtils.writeObjectivesToMatrix(front);
		double[][] populationMatrix = SolutionListUtils.writeObjectivesToMatrix(population);
		
		//setp 3. remove the duplicate points in a front/population	
		double[][] newFront = utils_.removeDuplicatePointsonFront(frontMatrix);
		double[][] newPopulation = utils_.removeDuplicatePointsonFront(populationMatrix);
		
		double RNI = (double)newFront.length/newPopulation.length;
		return RNI;
	}
public double ratioOfNonDominatedIndividualsDbl(double[][] obtainedFront, double[][] population){
		
		
		//setp 3. remove the duplicate points in a front/population	
		double[][] newFront = utils_.removeDuplicatePointsonFront(obtainedFront);
		double[][] newPopulation = utils_.removeDuplicatePointsonFront(population);
		
		double RNI = (double)newFront.length/newPopulation.length;
		return RNI;
	}

	
	public static void main(String[] args) throws IOException {
		int numberOfFiles = 30;
		int[] duplicated = new int[numberOfFiles];
		
		jmetal.qualityIndicator.util.MetricsUtil utils_ = new jmetal.qualityIndicator.util.MetricsUtil();
		RatioOfNonDominatedIndividuals ROI = new RatioOfNonDominatedIndividuals();
		double[] duplicatedRatio = new double[numberOfFiles];
		
		for(int i = 0; i < numberOfFiles; i++){
			//String LastPopulationFile = "DataTest/HyperVolumeCompute/IBEA/IBEA_2_30_50/run_"+ i + "/IBEA_2_30_50_Run_" + i+"_last_generated_population.fun";
			// String ParetoFrontFile = "DataTest/HyperVolumeCompute/NSGAII/NSGAII_2_30_50/run_"+ i + "/NSGAII_2_30_50_Run_" + i+"_final_Pareto_front.fun" ;
			 String ParetoFrontFile = "DataTest/HyperVolumeCompute/HH/"+"HF_ObtainedParetoFront_Final_"+i+".txt";
			
			 double [][] ParetoFront = ROI.utils_.readFront(ParetoFrontFile);
			 //double[][] LastPopulation = ROI.utils_.readFront(LastPopulationFile);
			 
			 int nonDominatedNO = ParetoFront.length;
			 double[][] nonReplicatedFront = ROI.utils_.removeDuplicatePointsonFront(ParetoFront);
			 int nonReplicatedNo = nonReplicatedFront.length;
			 duplicated[i] = nonDominatedNO - nonReplicatedNo;
			 duplicatedRatio[i] = (double) duplicated[i]/nonDominatedNO;
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
