package HF_Indicators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import HF_choicefunction.DMatrix;
import jmetal.qualityIndicator.EpsilonPlus;
import jmetal.qualityIndicator.HyperVolumeMinimizationProblem;
import jmetal.qualityIndicator.RatioOfNonDominatedIndividuals;
import jmetal.qualityIndicator.UniformDistribution;

public class Indicators {
	jmetal.qualityIndicator.util.MetricsUtilPlus utilities_  ;
	
	public Indicators(){
		utilities_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();	
		
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// compute the measurement of last population
		RatioOfNonDominatedIndividuals ROI = new RatioOfNonDominatedIndividuals();
		HyperVolumeMinimizationProblem qualityIndicator = new HyperVolumeMinimizationProblem();
		UniformDistribution ud = new UniformDistribution();
		EpsilonPlus ep = new EpsilonPlus();
		Indicators id = new Indicators();
		DMatrix dm = new DMatrix();
		
		int numberOfFiles = 80;
		double[] referencePoint = {9,9.5,1.7};
	    int numberOfObjectives = referencePoint.length;
	    
	    double hypervolume[] = new double[numberOfFiles];
		double ROIArray[] = new double[numberOfFiles];
		double uniforDistribution[] = new double[numberOfFiles];
		int[] duplicated = new int[numberOfFiles];
		double[] duplicatedRatio = new double[numberOfFiles];
		double[] epsilonAdditiveArray = new double[numberOfFiles-1];
		double[] epsilonMultipicativeArray = new double[numberOfFiles-1];
		double[] dMetricxArray12 = new double[numberOfFiles-1];
		double[] dMetricxArray21 = new double[numberOfFiles-1];
		
		int recordFrequency = 250;
		String fileName = null;
		String fileNames[] = {"IBEA_2_30_50", "NSGAII_2_30_50", "SPEA2_2_30_50"};
		
		for(int index = 0; index < fileNames.length; index++){
			fileName = fileNames[index];
			for(int i = 0; i < numberOfFiles; i++){
				long fileIndex = (i+1) * recordFrequency;
				String LastPopulationFile = "DataTest/Indicators/Input"+"/"+fileName +"/run_0/"+fileName +"_PRI_"+fileIndex+".res";
				String ParetoFrontFile = "DataTest/Indicators/Input"+"/"+fileName +"/run_0/"+fileName +"_PRI_"+fileIndex+".pf";
				//String LastPopulationFile = "DataTest/HyperVolumeCompute/SPEA2/SPEA2_2_30_50/run_"+ i + "/SPEA2_2_30_50_Run_" + i+"_last_generated_population.fun";
				//String ParetoFrontFile = "DataTest/HyperVolumeCompute/SPEA2/SPEA2_2_30_50/run_"+ i + "/SPEA2_2_30_50_Run_" + i+"_final_Pareto_front.fun" ;
				
				
				double [][] ParetoFront = ROI.utils_.readFront(ParetoFrontFile);
				
				double[][] LastPopulation = ROI.utils_.readFront(LastPopulationFile);
				//RNI
				ROIArray[i] = ROI.ratioOfNonDominatedIndividualsDbl(ParetoFront,LastPopulation);
				//Hypervolume
				hypervolume[i] = qualityIndicator.hypervolume(ParetoFront, numberOfObjectives, referencePoint);
				//Uniform distribution
				uniforDistribution[i] = ud.uniformDistribution(ParetoFront);
				//duplicate ratio
				 int nonDominatedNO = ParetoFront.length;
				 double[][] nonReplicatedFront = ROI.utils_.removeDuplicatePointsonFront(ParetoFront);
				 int nonReplicatedNo = nonReplicatedFront.length;
				 duplicated[i] = nonDominatedNO - nonReplicatedNo;
				 duplicatedRatio[i] = (double) duplicated[i]/nonDominatedNO;
				 	 
				 double[][] uniquePF= id.utilities_.removeDuplicatePointsonFront(ParetoFront);
				 if(i>0){
					 long tempFileIndex = i * recordFrequency;
					 String path = "DataTest/Indicators/Input"+"/"+fileName +"/run_0/"+fileName +"_PRI_"+tempFileIndex+".pf";
					 double[][] tempFront = id.utilities_.readFront(path);
					 double[][] uniquePFPrev = tempFront;
					 /* method_ = 0 means apply additive epsilon and method_ = 1 means multiplicative
					   * epsilon. */
					 int method =0;
					 epsilonAdditiveArray[i-1] = ep.epsilon(uniquePFPrev, uniquePF, numberOfObjectives, method);
					 method = 1;
					 epsilonMultipicativeArray[i-1] = ep.epsilon(uniquePFPrev, uniquePF, numberOfObjectives, method);
					 
					 /*D metricx dMetricxArray12[i] is the space area that covered by previous PF 
					  * but not covered by current PF; dMetricxArray21[i] is otherwise.	 
					 */
					 dMetricxArray12[i-1] = dm.DMatrixValue12Dbl(uniquePFPrev, uniquePF, referencePoint);
					 dMetricxArray21[i-1] = dm.DMatrixValue12Dbl(uniquePF, uniquePFPrev, referencePoint);
				
				 }
		 
			}
			
			File runFolder = new File("DataTest/Indicators/Results/"+ fileName + "/run_0");	
			if(!runFolder.exists()){
				if (runFolder.mkdirs()) {
					//System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
					System.exit(0);
				}
			}
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/Indicators/Results/"+fileName +"/run_0/"+ fileName+".hv"));
				//BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/NSGAII/HyperVolume.txt"));
				//BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/SPEA2/HyperVolume.txt"));
				//BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/IBEA/HyperVolume.txt"));
				for(int i = 0; i < hypervolume.length; i++){
					bw.append(new Double(hypervolume[i]).toString());
					bw.newLine();
				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/Indicators/Results/"+fileName +"/run_0/"+ fileName+".rni"));
				
				for(int i = 0; i < ROIArray.length; i++){
					bw.append(new Double(ROIArray[i]).toString());
					bw.newLine();
				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/Indicators/Results/"+fileName +"/run_0/"+ fileName+".ud"));
				
				for(int i = 0; i < uniforDistribution.length; i++){
					bw.append(new Double(uniforDistribution[i]).toString());
					bw.newLine();
				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/Indicators/Results/"+fileName +"/run_0/"+ fileName+".dpr"));
				
				for(int i = 0; i < duplicated.length; i++){
					bw.append(new Double(duplicated[i]).toString()+ " "+ new Double(duplicatedRatio[i]).toString());
					bw.newLine();
				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/Indicators/Results/"+fileName +"/run_0/"+ fileName+".epa"));
				
				for(int i = 0; i < epsilonAdditiveArray.length; i++){
					bw.append(new Double(epsilonAdditiveArray[i]).toString());
					bw.newLine();
				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/Indicators/Results/"+fileName +"/run_0/"+ fileName+".epm"));
				
				for(int i = 0; i < epsilonMultipicativeArray.length; i++){
					bw.append(new Double(epsilonMultipicativeArray[i]).toString());
					bw.newLine();
				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/Indicators/Results/"+fileName +"/run_0/"+ fileName+".dm12"));
				
				for(int i = 0; i < dMetricxArray12.length; i++){
					bw.append(new Double(dMetricxArray12[i]).toString());
					bw.newLine();
				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/Indicators/Results/"+fileName +"/run_0/"+ fileName+".dm21"));
				
				for(int i = 0; i < dMetricxArray21.length; i++){
					bw.append(new Double(dMetricxArray21[i]).toString());
					bw.newLine();
				}
				bw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}

}
