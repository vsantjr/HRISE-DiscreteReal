package jmetal.qualityIndicator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

/**
 * This is a pareto front measurement - Uniform Distribution of non-dominated
 * population(UD), it evaluates the spread of solutions in objective space. UD
 * is between [0,1]. It is acoording to the paper "Evolutionary Algorithms for
 * Multi-Objective optimization: Performance Assessments and Comparisons" by
 * K.C. Tan, T.H.Lee and E.F.Khor
 *
 * @author Wenwen Li
 * @param (SolutionSet) population
 * @return the uniform distribution of solutions on this front
 */
public class UniformDistribution<S extends Solution<?>> {

    jmetal.qualityIndicator.util.MetricsUtilPlus utils_;
    //constructor

    public UniformDistribution() {
        utils_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();
    }

    /*Count how many solutions is in the shared distance of solution i in objective space*/
    public int shareDistanceCountofI(double[] solutionI, int solutionIIndex,
            double[][] front) {

        int numberInSharedDistance = 0;
        int numberObjectives = solutionI.length;
        int nPoints = front.length;
        if (numberObjectives <= 3) {
            //double maximumDistance = Math.sqrt(numberObjectives);
            double sharedDelta = 0.01;// shareDelta parameter

            /* Get the distance of two solutions in objective space */
            for (int i = 0; i < nPoints; i++) {
                double distanceIJ = 0;
                if (i != solutionIIndex) {
                    double[] solutionJ = front[i];
                    for (int j = 0; j < numberObjectives; j++) {

                        distanceIJ += Math.pow((solutionI[j] - solutionJ[j]), 2);
                    }
                    distanceIJ = Math.sqrt(distanceIJ);

                    if (distanceIJ < sharedDelta) {
                        numberInSharedDistance++;
                    }
                }//for j
            }//for i

        }

        return numberInSharedDistance;
    }//shareDistanceCountofI

    public double calculateUniformDistribution(double[][] front) {
        double uniforDistribution = 0;
        int ncI = 0;
        double ncBar = 0;
        for (int i = 0; i < front.length; i++) {
            ncBar += shareDistanceCountofI(front[i], i, front);
        }

        ncBar = ncBar / front.length;

        double temp = 0;
        for (int i = 0; i < front.length; i++) {
            ncI = shareDistanceCountofI(front[i], i, front);//niche distance of solution I
            temp += Math.pow((ncI - ncBar), 2);
        }

        double S_nc = 0;
        S_nc = Math.sqrt(temp / (front.length - 1));
        uniforDistribution = 1 / (1 + S_nc);

        return uniforDistribution;
    }

    public double uniformDistribution(double[][] front) {

        int numberOfObjectives = front[0].length;
        /*int nPoints = front.length;*/
        double[] maximumValues = new double[numberOfObjectives];
        double[] minimumValues = new double[numberOfObjectives];

        //setp 1. remove the duplicate points on a front	
        double[][] newFront = utils_.removeDuplicatePointsonFront(front);

        // STEP 2. Obtain the maximum and minimum values of the Pareto front
        maximumValues = utils_.getMaximumValues(front, numberOfObjectives);
        minimumValues = utils_.getMinimumValues(front, numberOfObjectives);

        int nPoints = front.length;//points number has changed after removing duplicated points
        /*    for(int i = 0; i< newFront.length; i++){
	    	for(int j =0 ; j <numberOfObjectives; j++){
	    		System.out.print(newFront[i][j] + " ");
	    	}
	    	System.out.println();	    	
	    }*/
        double[][] normalizedFront = new double[nPoints][numberOfObjectives];

        // STEP 3. Get the normalized front
        normalizedFront = utils_.getNormalizedFront(front,
                maximumValues,
                minimumValues);

        // STEP 4. calculate uniform distribution of non-dominated solutions
        return this.calculateUniformDistribution(normalizedFront);
    }

    /* return uniform distribution of non-dominated solutions
	 * @param population with non-dominated solutions in that*/
    public double uniforDistributionForSolutionSet(List<S> population) {

        //step1 get nondominated solution set
        List<S> front = SolutionListUtils.getNondominatedSolutions(population);

        //Step2. write solutions in a front to an array.
        double[][] frontMatrix = SolutionListUtils.writeObjectivesToMatrix(front);

        //setp 3. remove the duplicate points in a front
        //double[][] newFront = utils_.removeDuplicatePointsonFront(frontMatrix);
        return this.uniformDistribution(frontMatrix);

    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        int numberOfFiles = 40;
        int iterNO = 10;
        double uniformDistribution[][] = new double[iterNO][numberOfFiles];
        UniformDistribution ud = new UniformDistribution();
        String algorithm = "null";

        for (int i = 0; i < iterNO; i++) {

            if (Math.floorMod(i, 2) == 0) {
                algorithm = "NSGAII";
            } else if (Math.floorMod(i, 2) == 1) {
                algorithm = "IBEA";
            }/*else if(Math.floorMod(i, 3) == 2){
						algorithm = "IBEA";
					}*/
            for (int j = 0; j < numberOfFiles; j++) {
                String file = "HF_Config_Power_Area_EMST/run_0" + "/" + algorithm + "_Iter_" + i + "_Evl" + (50 + 50 * j) + ".pf";
                double[][] solutionFront = ud.utils_.readFront(file);
                uniformDistribution[i][j] = ud.uniformDistribution(solutionFront);
            }
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("HF_Config_Power_Area_EMST/GenerationalUniformDistribution.txt"));
                for (int m = 0; m < uniformDistribution.length; m++) {
                    for (int n = 0; n < uniformDistribution[m].length; n++) {
                        bw.append(new Double(uniformDistribution[m][n]).toString());
                        bw.newLine();
                    }
                }
                bw.close();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
