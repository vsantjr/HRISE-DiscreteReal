package jmetal.qualityIndicator;

import java.io.IOException;
import java.util.ArrayList;
import org.uma.jmetal.qualityindicator.impl.GeneralizedSpread;

public class Exploration {
	jmetal.qualityIndicator.util.MetricsUtil utils_; 
	
	public Exploration(){
		utils_ = new jmetal.qualityIndicator.util.MetricsUtil();
	}
	// calculate how much space newFront explored than oldFront, normalized = 0 will normalize the input front, otherwise not
	public double exploration(double[][] oldFront, double[][] newFront){
		
		int numberOfObjectives = oldFront[0].length;
		double[][] normalizedOldFront = new double[oldFront.length][numberOfObjectives];
		double[][] normalizedNewFront = new double[newFront.length][numberOfObjectives];
		
		double[] minimumValuesOld =utils_.getMinimumValues(oldFront, numberOfObjectives);
		double[] maximumValuesOld = utils_.getMaximumValues(oldFront, numberOfObjectives);
		
		double[] minimumValuesNew = utils_.getMinimumValues(newFront, numberOfObjectives);
		double[] maximumValuesNew = utils_.getMaximumValues(newFront, numberOfObjectives);
		
		double[] minimumValues = new double[numberOfObjectives];
		double[] maximumValues = new double[numberOfObjectives];
		
		
	
						
			for(int i = 0; i < numberOfObjectives; i++){
				if(minimumValuesOld[i] < minimumValuesNew[i]){
					minimumValues[i] = minimumValuesOld[i];
				}else{
					minimumValues[i] = minimumValuesNew[i];
				}
				
				if(maximumValuesOld[i] > maximumValuesNew[i]){
					maximumValues[i] = maximumValuesOld[i];
				}else{
					maximumValues[i] = maximumValuesNew[i];
				}
			}
			
			normalizedOldFront = utils_.getNormalizedFront(oldFront, maximumValues, minimumValues);
			normalizedNewFront = utils_.getNormalizedFront(newFront, maximumValues, minimumValues);
			
			double[] normalizedmaximuOld = utils_.getMaximumValues(normalizedOldFront, numberOfObjectives);
			double[] normalizedminimumOld = utils_.getMinimumValues(normalizedOldFront, numberOfObjectives);
			
			double[] normalizedmaximumNew = utils_.getMaximumValues(normalizedNewFront, numberOfObjectives);
			double[] normalizedminimumNew = utils_.getMinimumValues(normalizedNewFront, numberOfObjectives);
			
			double[] difference = new double[numberOfObjectives];
			int[] count = new int[numberOfObjectives];
			
			for(int j = 0; j < numberOfObjectives; j++){
				for(int i =0; i < normalizedNewFront.length; i++){
					if(normalizedNewFront[i][j] > normalizedmaximuOld[j]){
						if(normalizedNewFront[i][j] - normalizedmaximuOld[j] < 0){
							System.err.println("Data error in exploration indicator (maximum).");
							System.exit(0);
						}
						difference[j] +=  normalizedNewFront[i][j] - normalizedmaximuOld[j];
							
						count[j]++;
					}else if(normalizedNewFront[i][j] < normalizedminimumOld[j]){
						if(normalizedminimumOld[j] - normalizedNewFront[i][j] < 0){
							System.err.println("Data error in exploration indicator (minimum).");
							System.exit(0);
						}
						difference[j] += normalizedminimumOld[j] - normalizedNewFront[i][j];
						count[j]++;
					}
				}
				
				
			}
			
			double value = 0;
			
			for(int k = 0; k < numberOfObjectives; k++){
				value += difference[k]*count[k]/normalizedNewFront.length;
			}	
			
			return value;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		double[][] oldFront = {{0.2,0.3},{0.25,0.35},{0.3,0.4},{0.35,0.45},{0.4,0.5}};
//		double[][] newFront = {{0.25,0.3},{0.3,0.35},{0.45,0.5},{0.5,0.55},{0.55,0.6}};
		
		Exploration ep = new Exploration();
		HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();
		GeneralizedSpread GSpread = new GeneralizedSpread();
		
		String[] path = {"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/SPEA2_Iter_0_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/NSGAII_Iter_1_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/IBEA_Iter_2_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/SPEA2_Iter_3_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/NSGAII_Iter_4_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/IBEA_Iter_5_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/SPEA2_Iter_6_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/NSGAII_Iter_7_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/IBEA_Iter_8_Evl2000.pf",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0/SPEA2_Iter_9_Evl2000.pf",};
		double[] eploration = new double[path.length-1];
		double[] hypervolume = new double[path.length];
		double[] hypervolumeAP = new double[path.length];
		double[] referencePoint = {9,9.5,1.7};
		int numberOfObjectives = referencePoint.length;
		double[] minimumValues = new double[numberOfObjectives];
		double[] maximumValues = new double[numberOfObjectives];
		double[] reference = {1.0,1.0,1.0};
		double spread[] = new double[path.length];
		
		for(int k = 0; k < numberOfObjectives; k++){
			minimumValues[k] = Double.MAX_VALUE;
			maximumValues[k] = - Double.MAX_VALUE;
		}
		for(int i = 0; i < path.length; i++){
			double[][] tempFront = hyp.utils_.readFront(path[i]);
			double[] tempMin = new double[numberOfObjectives];
			double[] tempMax = new double[numberOfObjectives];
			tempMin = hyp.utils_.getMinimumValues(tempFront, numberOfObjectives);
			tempMax = hyp.utils_.getMaximumValues(tempFront, numberOfObjectives);
			
			hypervolume[i] = hyp.calculatehypervolume(tempFront, tempFront.length, numberOfObjectives, referencePoint);
			
			for(int j = 0; j < numberOfObjectives; j++){
				if(tempMin[j] < minimumValues[j]){
					minimumValues[j] = tempMin[j];
				}
				if(tempMax[j] > maximumValues[j]){
					maximumValues[j] = tempMax[j];
				}
			}
			double[][] normalizedFront = hyp.utils_.getNormalizedFront(tempFront, maximumValues, minimumValues);
			hypervolumeAP[i] = hyp.calculatehypervolume(normalizedFront, normalizedFront.length, numberOfObjectives, reference);
			
		}
		
		for(int i = 0; i < path.length-1; i++){
			double[][] oldFront = ep.utils_.readFront(path[i]);
			double[][] newFront = ep.utils_.readFront(path[i+1]);
			eploration[i] = ep.exploration(oldFront, newFront);
		
		}
		
		String[] desPath = {"DataTest/Indicators/Input/10DPFixedSequenceAM102/Hypervolume_DP.txt",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/HypervolumeAP_DP.txt",
				"DataTest/Indicators/Input/10DPFixedSequenceAM102/Exploration_DP.txt"};
		
		ArrayList<double[]> outputData = new ArrayList<double[]>();
		outputData.add(hypervolume);
		outputData.add(hypervolumeAP);
		outputData.add(eploration);
		
		for(int t = 0; t < desPath.length; t++){
			hyp.utils_.printArray(outputData.get(t), desPath[t]);
		}
	
	}

}
