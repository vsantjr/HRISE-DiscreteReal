//  MetricsUtil.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
package jmetal.qualityIndicator.util;

import java.io.*;
import java.util.*;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.util.SolutionListUtils;

/**
 * This class provides some utilities to compute quality indicators.
 *
 */
public class MetricsUtilPlus<S extends Solution<?>> extends MetricsUtil<S> {

    public void writeFront(String filename, double[][] front) throws IOException {

        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < front.length; i++) {
            for (int j = 0; j < front[0].length; j++) {
                outputWriter.write(front[i][j] + " ");
            }
            outputWriter.newLine();
        }
        outputWriter.close();
    }

    public void wirteInteger(String filename, int[][] arr) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                outputWriter.write(arr[i][j] + " ");
            }
            outputWriter.newLine();
        }
        outputWriter.close();
    }

    public void printLongArray(long[] arr, String filename) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < arr.length; i++) {
            outputWriter.write(arr[i] + " ");
            outputWriter.newLine();
        }
        outputWriter.close();
    }

    public void printDoubleArray(double[] arr, String filename) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < arr.length; i++) {
            outputWriter.write(arr[i] + " ");
        }
        outputWriter.close();
    }

    public void printIntegerArray(int[] arr, String filename) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < arr.length; i++) {
            outputWriter.write(arr[i] + " ");

        }
        outputWriter.close();
    }

    public void print1DIntegerArray(int[] arr, String filename) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < arr.length; i++) {
            outputWriter.write(arr[i] + " ");
            outputWriter.newLine();

        }
        outputWriter.close();
    }

    public double[][] convertfloatarray2double(float[][] PF) {
        int dim = PF[0].length;
        double[][] doublePF = new double[PF.length][dim];

        for (int i = 0; i < PF.length; i++) {
            for (int j = 0; j < dim; j++) {
                doublePF[i][j] = (double) (PF[i][j]);
            }
        }

        return doublePF;
    }

    public float[][] convertdoubleArray2float(double[][] PF) {
        int dim = PF[0].length;
        float[][] doublePF = new float[PF.length][dim];

        for (int i = 0; i < PF.length; i++) {
            for (int j = 0; j < dim; j++) {
                doublePF[i][j] = (float) (PF[i][j]);
            }
        }

        return doublePF;
    }

    public double[] convertSinglefloatarray2double(float[] PF) {

        double[] doublePF = new double[PF.length];

        for (int i = 0; i < PF.length; i++) {

            doublePF[i] = (double) (PF[i]);

        }

        return doublePF;
    }

    public double[] readPositions(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        line = bufferedReader.readLine();
        String[] tmp = line.split(" ");
        for (String s : tmp) {
            lines.add(s);
        }

        bufferedReader.close();
        double[] inStream = new double[lines.size()];
        for (int i = 0; i < inStream.length; i++) {
            inStream[i] = Double.parseDouble(lines.get(i));
        }

        return inStream;
    }

    public double[][] removeDominatedSolutions(double[][] solutions) {
        //nondominatedFlag = 0 the solution is dominated; 1, the solution is not dominated
        int[] nondominatedFlag = new int[solutions.length];
        for (int m = 0; m < solutions.length; m++) {
            nondominatedFlag[m] = -1;
        }

        for (int i = 0; i < solutions.length; i++) {
            int tempFlag = -1;
            for (int j = 0; j < solutions.length; j++) {
                if (i != j) {
                    tempFlag = nondominatedSolution(solutions[i], solutions[j]);
                    if (tempFlag == 0) {
                        break;
                    }

                }
            }
            nondominatedFlag[i] = tempFlag;
        }
        int nonDominatedSolNum = 0;
        for (int j = 0; j < nondominatedFlag.length; j++) {
            nonDominatedSolNum += nondominatedFlag[j];
        }

        double[][] newSolutions = new double[nonDominatedSolNum][solutions[0].length];
        int index = -1;
        for (int k = 0; k < solutions.length; k++) {
            if (nondominatedFlag[k] == 1) {
                index++;
                double[] newSol = new double[solutions[0].length];
                newSol = solutions[k];
                newSolutions[index] = newSol;
            }

        }
        return newSolutions;
    }

    //for minimisation without constraint problem dominance comparison
    public int nondominatedSolution(double[] solution1, double[] solution2) {
        int solution1IsNonDominated = 1;
        int dominatedSingnal = 0;
        for (int i = 0; i < solution1.length; i++) {
            if (solution1[i] > solution2[i]) {
                dominatedSingnal++;
            }
        }

        if (dominatedSingnal == solution1.length) {
            solution1IsNonDominated = 0;
        }
        return solution1IsNonDominated;
    }

    public float[][] removeDominatedSolutions(float[][] solutions) {
        //nondominatedFlag = 0 the solution is dominated; 1, the solution is not dominated
        int[] nondominatedFlag = new int[solutions.length];
        for (int m = 0; m < solutions.length; m++) {
            nondominatedFlag[m] = -1;
        }

        for (int i = 0; i < solutions.length; i++) {
            int tempFlag = -1;
            for (int j = 0; j < solutions.length; j++) {
                if (i != j) {
                    tempFlag = nondominatedSolution(solutions[i], solutions[j]);
                    if (tempFlag == 0) {
                        break;
                    }

                }
            }
            nondominatedFlag[i] = tempFlag;
        }
        int nonDominatedSolNum = 0;
        for (int j = 0; j < nondominatedFlag.length; j++) {
            nonDominatedSolNum += nondominatedFlag[j];
        }

        float[][] newSolutions = new float[nonDominatedSolNum][solutions[0].length];
        int index = -1;
        for (int k = 0; k < solutions.length; k++) {
            if (nondominatedFlag[k] == 1) {
                index++;
                float[] newSol = new float[solutions[0].length];
                newSol = solutions[k];
                newSolutions[index] = newSol;
            }

        }
        return newSolutions;
    }

    //for minimisation without constraint problem dominance comparison
    public int nondominatedSolution(float[] solution1, float[] solution2) {
        int solution1IsNonDominated = 1;
        int dominatedSingnal = 0;
        for (int i = 0; i < solution1.length; i++) {
            if (solution1[i] > solution2[i]) {
                dominatedSingnal++;
            }
        }

        if (dominatedSingnal == solution1.length) {
            solution1IsNonDominated = 0;
        }
        return solution1IsNonDominated;
    }

    public void writePositions(String filename, double[] outputData) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        double x = 0;
        double y = 0;
        for (int i = 0; i < outputData.length; i += 2) {
            x = outputData[i];
            y = outputData[i + 1];
            outputWriter.write(x + " " + y);
            outputWriter.newLine();
        }
        outputWriter.close();
    }

    /**
     * This method reads a Pareto Front for a file.
     *
     * @param path The path to the file that contains the pareto front
     * @return double [][] whit the pareto front
     *
     */
    public int[][] readInteger2DArray(String path) {
        try {
            // Open the file
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            List<int[]> list = new ArrayList<int[]>();
            int numberOfObjectives = 0;
            String aux = br.readLine();
            while (aux != null) {
                StringTokenizer st = new StringTokenizer(aux);
                int i = 0;
                numberOfObjectives = st.countTokens();
                int[] vector = new int[st.countTokens()];
                while (st.hasMoreTokens()) {
                    int value = (new Integer(st.nextToken())).intValue();
                    vector[i] = value;
                    i++;
                }
                list.add(vector);
                aux = br.readLine();
            }

            br.close();

            int[][] front = new int[list.size()][numberOfObjectives];
            for (int i = 0; i < list.size(); i++) {
                front[i] = list.get(i);
            }
            return front;

        } catch (Exception e) {
            System.out.println("InputFacilities crashed reading for file: " + path);
            e.printStackTrace();
        }
        return null;
    } // readFront

    //check whether array 1 and array 2 are the same array
    public boolean isTheSameArray(double[] array1, double[] array2) {
        boolean sameArray = false;
        if (array1 == null || array2 == null) {
            sameArray = false;
        }
        if (array1.length != array2.length) {
            sameArray = false;
        } else {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] != array2[i]) {
                    sameArray = false;
                    return sameArray;
                }
            }
            sameArray = true;
        }

        return sameArray;
    }

    public boolean isTheSameSolution(Solution sol1, Solution sol2) {
        boolean sameSolution = false;
        if ((sol1 == null && sol2 != null) || (sol1 != null && sol2 == null)) {
            sameSolution = false;
        }
        if (sol1.getNumberOfObjectives() != sol2.getNumberOfObjectives()) {
            sameSolution = false;
        } else {
            for (int i = 0; i < sol1.getNumberOfObjectives(); i++) {
                if (sol1.getObjective(i) != sol2.getObjective(i)) {
                    sameSolution = false;
                    return sameSolution;
                }
            }
            sameSolution = true;
        }

        return sameSolution;
    }

    //WWL
    public double[][] removeDuplicatePointsonFront(double[][] front) {

        int nPoints = front.length;
        int numberOfObjectives = front[0].length;

        List<double[]> noDuplicateFront = new ArrayList<double[]>();
        //remove duplicated points
        noDuplicateFront.add(front[0]);
        double[] tempArray = new double[numberOfObjectives];
        double[] tempFrontPoint = new double[numberOfObjectives];

        for (int i = 0; i < nPoints; i++) {
            tempFrontPoint = front[i];
            /*			
			if(tempFrontPoint[0] == 1661.70782249999 && tempFrontPoint[1] == 8.30459999999999 && tempFrontPoint[2] ==0.0708 ){
				System.out.println("Debug..");
			}*/

            boolean alreadyExisted = false;
            for (int j = 0; j < noDuplicateFront.size(); j++) {

                tempArray = noDuplicateFront.get(j);
                alreadyExisted = isTheSameArray(tempFrontPoint, tempArray);
                if (alreadyExisted) {
                    break;
                }

            }
            if (!alreadyExisted) {
                noDuplicateFront.add(front[i]);
            }
        }

        //check whether this is a point(0.0, 0.0, 0.0)
        int nullPoint = -1;
        int flag = 0;
        for (int t = 0; t < noDuplicateFront.size(); t++) {
            flag = 0;
            for (int l = 0; l < numberOfObjectives; l++) {
                if (noDuplicateFront.get(t)[l] == 0.0) {
                    flag++;
                }
            }
            if (flag == numberOfObjectives) {
                nullPoint = t;
                break;
            }

        }
        int nonDuplicateSize = noDuplicateFront.size();
        if (nullPoint > -1) {
            nonDuplicateSize = nonDuplicateSize - 1;
        }
        double[][] newFront = new double[nonDuplicateSize][numberOfObjectives];
        int n = 0;
        for (int k = 0; k < nonDuplicateSize; k++) {
            if ((n != nullPoint) && (n < noDuplicateFront.size())) {
                newFront[k] = noDuplicateFront.get(n);

            }
            n++;
        }
        return newFront;
    }

    public List<S> removeDuplicatePointsOnFront(List<S> front) {

        int nPoints = front.size();
        int numberOfObjectives = front.get(0).getNumberOfObjectives();

        List<Solution> noDuplicateFront = new ArrayList<Solution>();
        //remove duplicated points
        noDuplicateFront.add(front.get(0));
        Solution tempArray = null;
        Solution tempFrontPoint = null;

        for (int i = 0; i < nPoints; i++) {
            tempFrontPoint = front.get(i);
            /*			
			if(tempFrontPoint[0] == 1661.70782249999 && tempFrontPoint[1] == 8.30459999999999 && tempFrontPoint[2] ==0.0708 ){
				System.out.println("Debug..");
			}*/

            boolean alreadyExisted = false;
            for (int j = 0; j < noDuplicateFront.size(); j++) {

                tempArray = noDuplicateFront.get(j);
                alreadyExisted = isTheSameSolution(tempFrontPoint, tempArray);
                if (alreadyExisted) {
                    break;
                }

            }
            if (!alreadyExisted) {
                noDuplicateFront.add(front.get(i));
            }
        }

        //check whether this is a point(0.0, 0.0, 0.0)
        int nullPoint = -1;
        int flag = 0;
        for (int t = 0; t < noDuplicateFront.size(); t++) {
            flag = 0;
            for (int l = 0; l < numberOfObjectives; l++) {
                if (noDuplicateFront.get(t).getObjective(l) == 0.0) {
                    flag++;
                }
            }
            if (flag == numberOfObjectives) {
                nullPoint = t;
                break;
            }

        }
        int nonDuplicateSize = noDuplicateFront.size();
        if (nullPoint > -1) {
            nonDuplicateSize = nonDuplicateSize - 1;
        }
        List<S> newFront = new ArrayList<S>(noDuplicateFront.size());
        int n = 0;
        for (int k = 0; k < nonDuplicateSize; k++) {
            if ((n != nullPoint) && (n < noDuplicateFront.size())) {
                newFront.add((S) noDuplicateFront.get(n));

            }
            n++;
        }
        double duplicatedratio = (nPoints - noDuplicateFront.size()) / nPoints;
        if (duplicatedratio > 0.000001) {
            System.out.println("Before remove duplicated " + nPoints + "; after removal: " + noDuplicateFront.size() + ". Duplicated ratio: " + duplicatedratio);
        }

        return newFront;
    }

    public static void main(String[] args) throws IOException {

        int numberOfFiles = 2;
        MetricsUtilPlus mc = new MetricsUtilPlus();
        int numberOfObjectives = 3;
        for (int i = 0; i < numberOfFiles; i++) {
            String file = "DataTest/HyperVolumeCompute/HH/" + "HF_ObtainedParetoFront_Final_" + i + ".txt";
            double[][] solutionFront = mc.readFront(file);
            double[][] uniquesolutionFront = mc.removeDuplicatePointsonFront(solutionFront);

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/HH/uniquefile_" + i + ".txt"));
                //BufferedWriter bw = new BufferedWriter(new FileWriter("DataTest/HyperVolumeCompute/IBEA/HyperVolume.txt"));
                // MST lower bound
                // TODO print to file
                for (int j = 0; j < uniquesolutionFront.length; j++) {
                    for (int k = 0; k < numberOfObjectives; k++) {
                        bw.append(new Double(uniquesolutionFront[j][k]).toString() + " ");
                    }

                    bw.newLine();
                }
                bw.close();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    //merge solution fronts

    public List<S> getApproximateFronts(List<S>[] fronts) {
        int size = 0;
        for (int i = 0; i < fronts.length; i++) {

            size += fronts[i].size();
        }
        List<S> totalSolutionSet = new ArrayList<S>(size);
        for (int i = 0; i < fronts.length; i++) {
            for (int j = 0; j < fronts[i].size(); j++) {
                totalSolutionSet.add(fronts[i].get(j));
            }

        }
        return SolutionListUtils.getNondominatedSolutions(totalSolutionSet);
    }

//merge two fronts(SolutionSet)
    public List<S> mergeTwoFronts(List<S> front1, List<S> front2) {

        int size = front1.size() + front2.size();
        List<S> mergedFront = new ArrayList<S>(size);

        for (int i = 0; i < front1.size(); i++) {
            mergedFront.add(front1.get(i));
        }
        for (int j = 0; j < front2.size(); j++) {
            mergedFront.add(front2.get(j));
        }
        return SolutionListUtils.getNondominatedSolutions(mergedFront);
    }

    /* merge two fronts */
    public double[][] mergeFronts(double[][] front1, int sizeFront1,
            double[][] front2, int sizeFront2, int noObjectives) {
        int i, j;
        int noPoints;
        double[][] frontPtr;

        /* allocate memory */
        noPoints = sizeFront1 + sizeFront2;
        frontPtr = new double[noPoints][noObjectives];
        /* copy points */
        noPoints = 0;
        for (i = 0; i < sizeFront1; i++) {
            for (j = 0; j < noObjectives; j++) {
                frontPtr[noPoints][j] = front1[i][j];
            }
            noPoints++;
        }
        for (i = 0; i < sizeFront2; i++) {
            for (j = 0; j < noObjectives; j++) {
                frontPtr[noPoints][j] = front2[i][j];
            }
            noPoints++;
        }

        return frontPtr;
    } // MergeFronts

    /**
     * get the distance between two points a and b according to the dominance
     * relationship for IGD+.
     *
     * @param a point on true Pareto front
     * @param b point on obtained Pareto front
     * @return maximum distance between point from true Pareto front to point
     * from obtained Pareto Front note:
     */
    public double domianceDistanceforIGDPlus(double[] a, double[] b) {
        double distance = 0.0;

        for (int i = 0; i < a.length; i++) {
            double max = Math.max((b[i] - a[i]), 0);
            distance += Math.pow(max, 2);
        }
        return Math.sqrt(distance);
    }

    //modify the distance definition in D1R as d(a,b) = max_i{b[i] - a[i], 0}, i: dimension
    //a should be on reference front, b on obtained front
    public double domianceDistanceforD1R(double[] a, double[] b) {
        double distance = -Double.MAX_VALUE;

        for (int i = 0; i < a.length; i++) {
            double temp = b[i] - a[i];

            if (distance < temp) {
                distance = temp;
            }
        }

        return distance;
    }
    //

    public double distanceToClosestPoint(double[] point, double[][] front) {
        double minDistance = domianceDistanceforIGDPlus(point, front[0]);

        for (int i = 1; i < front.length; i++) {
            double temp = domianceDistanceforIGDPlus(point, front[i]);
            if (minDistance > temp) {
                minDistance = temp;
            }
        }
        return minDistance;
    }

    public double distanceToClosestPointforD1R(double[] point, double[][] front) {
        double minDistance = domianceDistanceforD1R(point, front[0]);

        for (int i = 1; i < front.length; i++) {
            double temp = domianceDistanceforD1R(point, front[i]);
            if (minDistance > temp) {
                minDistance = temp;
            }
        }
        return minDistance;
    }

    public void printArray(double[] array, String desPath) throws IOException {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(desPath));

            for (int i = 0; i < array.length; i++) {
                bw.append(new Double(array[i]).toString());
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public double[] read1DDblArray(String desPath) throws IOException {
        try {
            // Open the file
            FileInputStream fis = new FileInputStream(desPath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            List<Double> list = new ArrayList<Double>();
            String aux = br.readLine();
            while (aux != null) {
                StringTokenizer st = new StringTokenizer(aux);

                double value = (new Double(st.nextToken())).doubleValue();

                list.add(value);
                aux = br.readLine();
            }

            br.close();

            double[] array = new double[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i);
            }
            return array;

        } catch (Exception e) {
            System.out.println("InputFacilities crashed reading for file: " + desPath);
            e.printStackTrace();
        }
        return null;
    }

    public void printArrayListInteger(ArrayList<Integer> heuristicList,
            String arrayListPath) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arrayListPath));

            for (int i = 0; i < heuristicList.size(); i++) {
                bw.append(new Integer(heuristicList.get(i)).toString());
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub

    }

    public void printArrayListDouble(ArrayList<Double> heuristicList,
            String arrayListPath) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arrayListPath));

            for (int i = 0; i < heuristicList.size(); i++) {
                bw.append(new Double(heuristicList.get(i)).toString());
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub

    }

    public void printArrayListBoolean(ArrayList<Boolean> heuristicList,
            String arrayListPath) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arrayListPath));

            for (int i = 0; i < heuristicList.size(); i++) {
                bw.append(new Boolean(heuristicList.get(i)).toString());
                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub

    }

    public void printArrayListArraylist(ArrayList<ArrayList> heuristicList,
            String arrayListPath) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arrayListPath));

            for (int i = 0; i < heuristicList.size(); i++) {
                for (int j = 0; j < heuristicList.get(i).size(); j++) {
                    bw.append(new Integer((int) heuristicList.get(i).get(j)).toString());
                    bw.append(" ");
                }

                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub

    }

    public void printArrayListDoubleArray(ArrayList<Double[]> heuristicList,
            String arrayListPath) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arrayListPath));

            for (int i = 0; i < heuristicList.size(); i++) {
                for (int j = 0; j < heuristicList.get(i).length; j++) {
                    bw.append(new Double((double) heuristicList.get(i)[j]).toString());
                    bw.append(" ");
                }

                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub

    }

    public void printArrayListdoubleArray(ArrayList<double[]> heuristicList,
            String arrayListPath) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arrayListPath));

            for (int i = 0; i < heuristicList.size(); i++) {
                for (int j = 0; j < heuristicList.get(i).length; j++) {
                    bw.append(new Double((double) heuristicList.get(i)[j]).toString());
                    bw.append(" ");
                }

                bw.newLine();
            }
            bw.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub

    }
    
    public List<S> vector2SolutionSet(double[][] front, Problem problem) {
        List<S> SolSet = new ArrayList<>();
        int dim = front[0].length;
        for (int i = 0; i < front.length; i++) {
            DefaultDoubleSolution sol = new DefaultDoubleSolution((DoubleProblem) problem);
            for (int k = 0; k < front[0].length; k++) {
                sol.setObjective(k, front[i][k]);
            }
            SolSet.add((S) sol);
        }
        return SolSet;
    }
    

} // MetricsUtil
