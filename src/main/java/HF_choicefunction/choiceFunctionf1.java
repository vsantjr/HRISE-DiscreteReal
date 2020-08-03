/**
 * 
 */
/**
 * @author wzl
 *
 */
package HF_choicefunction;

import java.util.HashMap;
import java.util.Scanner;

public class choiceFunctionf1 {
	/* constructor */
	public choiceFunctionf1() {
	}

	/* First stage rank: Rank a single measurement of all low level heuristics */
	/*
	 * order = 0, descending rank, i.e. the smaller the better, rank 0 is better
	 * than rank 1 order = 1, ascending rank, i.e. the bigger the better, rank 0
	 * is better than rank 1
	 */
	public int[][] rankMeasurement(double[][] measureMetrix, int order) {

		int numberOfHeuristics = measureMetrix.length;
		int[][] rank = new int[numberOfHeuristics][2];
		double[] measurementValue = new double[numberOfHeuristics];

		for (int i = 0; i < numberOfHeuristics; i++) {
			measurementValue[i] = measureMetrix[i][1];
		}

		for (int i = 0; i < numberOfHeuristics; i++) {
			rank[i][0] = i;
			rank[i][1] = 1;
		}

		if (order == 0) {
			for (int i = 0; i < measurementValue.length; i++) {
				for (int j = 0; j < measurementValue.length; j++) {
					if (i != j) {
						if (measurementValue[j] < measurementValue[i]) {
							rank[i][1]++;
						}
					}
				}
			}
		}// end if order
		else if (order == 1) {
			for (int i = 0; i < measurementValue.length; i++) {
				for (int j = 0; j < measurementValue.length; j++) {
					if (i != j) {
						if (measurementValue[j] > measurementValue[i]) {
							rank[i][1]++;
						}
					}
				}
			}
		}
		return rank;

	}

	/*
	 * Count the best rank of each low level heuristcs, i.e. the number of
	 * rank(0) regarding to each measure
	 */
	public int[][] frequencyOfBestRank(int[][] measurementMextrics) {

		int numberOfHeuristics = measurementMextrics.length;
		int numberOfMeasure = measurementMextrics[0].length;
		int[][] freqOfBestRank = new int[numberOfHeuristics][2];
		/*
		 * int[] measurementValue = new int[numberOfHeuristics];
		 * 
		 * for(int i = 0; i<numberOfHeuristics;i++){ measurementValue[i] =
		 * measurementMextrics[i][1]; }
		 */

		for (int i = 0; i < numberOfHeuristics; i++) {
			freqOfBestRank[i][0] = i;
			freqOfBestRank[i][1] = 0;

		}// for

		for (int i = 0; i < numberOfHeuristics; i++) {
			int temp = 0;
			for (int j = 1; j < numberOfMeasure; j++) {
				if (measurementMextrics[i][j] == 1)
					temp++;
			}
			freqOfBestRank[i][1] = temp;
		}

		return freqOfBestRank;
	}

	/* second stage rank, rank the frequency of best rank of each heuristics */
	public int[][] frequencyRank(int[][] frequencyMetrix) {
		int numberOfHeuristics = frequencyMetrix.length;
		int[][] rank = new int[numberOfHeuristics][2];
		double[] measurementValue = new double[numberOfHeuristics];

		for (int i = 0; i < numberOfHeuristics; i++) {
			measurementValue[i] = frequencyMetrix[i][1];
		}

		for (int i = 0; i < numberOfHeuristics; i++) {
			rank[i][0] = i;
			rank[i][1] = 1;
		}
		for (int i = 0; i < measurementValue.length; i++) {
			for (int j = 0; j < measurementValue.length; j++) {
				if (i != j) {
					if (measurementValue[j] > measurementValue[i]) {
						rank[i][1]++;
					}
				}
			}
		}
		return rank;
	}

	/* calculate the value of f1 */
	public int[][] calculateChoiceFunctionf1(int[][] freqRankMetrix,
			int[][] RNIRankMetrix) {

		int numberOfHeuristics = freqRankMetrix.length;
		int[][] rank = new int[numberOfHeuristics][2];

		for (int i = 0; i < numberOfHeuristics; i++) {
			rank[i][0] = i;
			rank[i][1] = 0;
		}
		for (int i = 0; i < numberOfHeuristics; i++) {
			rank[i][1] = 2 * (numberOfHeuristics + 1)
					- (freqRankMetrix[i][1] + RNIRankMetrix[i][1]);
		}

		return rank;
	}

	/*
	 * measureMextrix 1: algorithmEffort, 2: ROIArray, 3: Hypervolume, 4,
	 * uniformDistribution, Order: ascending or descending
	 */
	public int[][] choiceFunctionF1(double[][] measureMetrix1, int order1,
			double[][] measureMetrix2, int order2, double[][] measureMetrix3,
			int order3, double[][] measureMetrix4, int order4,
			int numberOfMeasurements) {
		
		int numberOfAlgorithms = measureMetrix1.length;
		int[][] rankOfMeasures = new int[numberOfAlgorithms][numberOfMeasurements + 1];
		int[][] AERank = new int[numberOfAlgorithms][2];
		int[][] RNIRank = new int[numberOfAlgorithms][2];
		int[][] HVRank = new int[numberOfAlgorithms][2];
		int[][] UDRank = new int[numberOfAlgorithms][2];

		AERank = this.rankMeasurement(measureMetrix1, 0);//AE
		RNIRank = this.rankMeasurement(measureMetrix2, 1);//RNI
		HVRank = this.rankMeasurement(measureMetrix3, 1);//Hypervolume
		UDRank = this.rankMeasurement(measureMetrix4, 1);//UniformDistribution

		/* get the measurement rank of all algorithms regarding all measures */
		for (int k = 0; k < numberOfAlgorithms; k++) {
			rankOfMeasures[k][0] = k;
		}

		for (int i = 0; i < numberOfAlgorithms; i++) {
			rankOfMeasures[i][1] = AERank[i][1];
			rankOfMeasures[i][2] = RNIRank[i][1];
			rankOfMeasures[i][3] = HVRank[i][1];
			rankOfMeasures[i][4] = UDRank[i][1];
		}
		/*Get the frequency of best ranking mextrix of all algorithms*/
		int[][] freqOfBestRanking = this.frequencyOfBestRank(rankOfMeasures);

		int[][] freqRank = this.frequencyRank(freqOfBestRanking);
		int[][] choiceFunctionF1Mextrix = this.calculateChoiceFunctionf1(freqRank, RNIRank);
		
		return choiceFunctionF1Mextrix;
	}

	public static void main(String args[]) {

		choiceFunctionf1 ranking = new choiceFunctionf1();
		// double[] measures = {0.03,0.01,0.04};
		double[][] measures = { { 0, 1 }, { 1, 1 }, { 2, 0.6 } };
		int[][] measureMetrix = { { 0, 1, 0, 1, 0 }, { 1, 0, 0, 0, 1 },
				{ 2, 2, 2, 2, 2 } };
		double[][] measuremetrix1 = {{0,0.3},{1,0.1},{2,0.4}};
		double[][] measuremetrix2 = {{0,1.0},{1,1.0},{2,0.6}};
		double[][] measuremetrix3 = {{0,10.7},{1,11.9},{2,9.81}};
		double[][] measuremetrix4 = {{0,4.91},{1,3.75},{2,3.0}};
		
		int[][] choiceFunction = ranking.choiceFunctionF1(measuremetrix1, 0, measuremetrix2, 1, measuremetrix3, 1, measuremetrix4, 1, 4);
		
/*		int[][] rank = ranking.rankMeasurement(measures, 1);
		for (int i = 0; i < rank.length; i++) {

			System.out.println("Measurements rank: " + rank[i][0] + ":"
					+ rank[i][1]);
		}
		int[][] frequOfbestRank = ranking.frequencyOfBestRank(measureMetrix);
		for (int i = 0; i < frequOfbestRank.length; i++) {
			System.out.println("frequOfbestRank rank: " + frequOfbestRank[i][0]
					+ ":" + frequOfbestRank[i][1]);
		}

		int[][] freqRank = ranking.frequencyRank(frequOfbestRank);

		for (int i = 0; i < freqRank.length; i++) {
			System.out.println("freqRank rank: " + freqRank[i][0] + ":"
					+ freqRank[i][1]);
		}

		int[][] RNIRankMetrix = { { 0, 0 }, { 1, 0 }, { 2, 2 } };

		int[][] choiceFunction = ranking.calculateChoiceFunctionf1(freqRank,
				RNIRankMetrix);*/
/*		Scanner in = new Scanner(System.in);
		System.out.println("Input something:");
		String temp = in.nextLine();*/
	
		for (int i = 0; i < choiceFunction.length; i++) {
			System.out.println("choiceFunction rank: " + choiceFunction[i][0]
					+ ":" + choiceFunction[i][1]);
		}
		

	}
}