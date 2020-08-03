//  Epsilon.java
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
package jmetal.qualityIndicator;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.qualityIndicator.util.MetricsUtilPlus;
import org.uma.jmetal.qualityindicator.impl.Epsilon;
import org.uma.jmetal.solution.Solution;

/**
 * This class implements the unary epsilon additive indicator as proposed in E.
 * Zitzler, E. Thiele, L. Laummanns, M., Fonseca, C., and Grunert da Fonseca. V
 * (2003): Performance Assesment of Multiobjective Optimizers: An Analysis and
 * Review. The code is the a Java version of the orginal metric implementation
 * by Eckart Zitzler. It can be used also as a command line program just by
 * typing $java jmetal.qualityIndicator.Epsilon <solutionFrontFile>
 * <trueFrontFile> <numberOfOjbectives>
 */
public class EpsilonPlus<S extends Solution<?>> extends Epsilon {

    protected MetricsUtilPlus utils_;
    protected int dim_;
    protected int[] obj_;
    protected int method_;

    public EpsilonPlus() {
        utils_ = new MetricsUtilPlus();
        /* stores the number of objectives */
    }

    /**
     * Return normalised epsilon values (minimum epsilon additive value that an
     * obtained front a needs to weakly dominate approximate or true front b)
     *
     * @param a. solution front
     * @param b. True Pareto Front
     * @return the value of the normalised epsilon value
     */
    public double normalisedEpsilon(double[][] b, double[][] a, int dim, int method) {
        double[] maximumValue;

        /**
         * Stores the minimum values of the true pareto front.
         */
        double[] minimumValue;

        /**
         * Stores the normalized front.
         */
        double[][] normalizedFront;

        /**
         * Stores the normalized true Pareto front.
         */
        double[][] normalizedParetoFront;
        double[] extremeMinimumValues;
        double[] extremeMaximumValues;

        // STEP 1. Obtain the maximum and minimum values of the Pareto true front
        maximumValue = utils_.getMaximumValues(b, b[0].length);
        minimumValue = utils_.getMinimumValues(b, b[0].length);
        extremeMaximumValues = utils_.getMaximumValues(a, a[0].length);
        extremeMinimumValues = utils_.getMinimumValues(a, a[0].length);

        for (int i = 0; i < b[0].length; i++) {
            if (minimumValue[i] < 0.0) {
                minimumValue[i] = 0.0;//correct the 
            }
            if (extremeMaximumValues[i] > maximumValue[i]) {
                maximumValue[i] = extremeMaximumValues[i];
            }
            if (extremeMinimumValues[i] < minimumValue[i]) {

                /*	    		System.err.println("True front objective is worse than the obtained front!");
	    		System.out.println("True front minimum value of objective " +i+" is "  + minimumValue[i]);
	    		System.out.println("Obtained front minimum value of objective " +i+" is "  + extremeMinimumValues[i]);*/
                //minimumValue[i] = extremeMinimumValues[i];
                //System.exit(0);
            }
        }

        // STEP 2. Get the normalized front and true Pareto fronts
        normalizedFront = utils_.getNormalizedFront(a,
                maximumValue,
                minimumValue);

        normalizedParetoFront = utils_.getNormalizedFront(b,
                maximumValue,
                minimumValue);

        int i, j, k;
        double eps, eps_j = 0.0, eps_k = 0.0, eps_temp;

        dim_ = dim;
        set_params(method);

        if (method_ == 0) {
            eps = Double.MIN_VALUE;
        } else {
            eps = 0;
        }

        for (i = 0; i < normalizedFront.length; i++) {
            for (j = 0; j < normalizedParetoFront.length; j++) {
                for (k = 0; k < dim_; k++) {
                    switch (method_) {
                        case 0:
                            if (obj_[k] == 0) {
                                eps_temp = normalizedFront[i][k] - normalizedParetoFront[j][k];
                                if (eps_temp < 0.0) {
                                    eps_temp = 0.0;
                                }
                            } //eps_temp = b[j * dim_ + k] - a[i * dim_ + k];
                            else {
                                eps_temp = normalizedParetoFront[j][k] - normalizedFront[i][k];
                            }
                            //eps_temp = a[i * dim_ + k] - b[j * dim_ + k];
                            break;
                        default:
                            if ((normalizedFront[i][k] < 0 && normalizedParetoFront[j][k] > 0)
                                    || (normalizedFront[i][k] > 0 && normalizedParetoFront[j][k] < 0)
                                    || (normalizedFront[i][k] == 0 || normalizedParetoFront[j][k] == 0)) {
                                //if ( (a[i * dim_ + k] < 0 && b[j * dim_ + k] > 0) ||
                                //     (a[i * dim_ + k] > 0 && b[j * dim_ + k] < 0) ||
                                //     (a[i * dim_ + k] == 0 || b[j * dim_ + k] == 0)) {
                                System.err.println("error in data file");
                                System.exit(0);
                            }
                            if (obj_[k] == 0) {
                                eps_temp = normalizedParetoFront[j][k] / normalizedFront[i][k];
                            } //eps_temp = b[j * dim_ + k] / a[i * dim_ + k];
                            else {
                                eps_temp = normalizedFront[i][k] / normalizedParetoFront[j][k];
                            }
                            //eps_temp = a[i * dim_ + k] / b[j * dim_ + k];
                            break;
                    }
                    if (k == 0) {
                        eps_k = eps_temp;
                    } else if (eps_k < eps_temp) {
                        eps_k = eps_temp;//find the maximum distance from the point on solution front to the point on true front
                    }
                }
                if (j == 0) {
                    eps_j = eps_k;
                } else if (eps_j > eps_k) {
                    eps_j = eps_k;//find the nearest distance from the point to the true front
                }
            }
            if (i == 0) {
                eps = eps_j;
            } else if (eps < eps_j)//find the maximum distance from the solution front to the true front
            {
                eps = eps_j;
            }
        }
        return eps;
    } // epsilon
//For each reference point, compute the normalised epsilon value from the obtained front to this reference point.

    public double normalisedEpsilonRevert(double[][] b, double[][] a, int dim, int method) {
        double[] maximumValue;

        /**
         * Stores the minimum values of the true pareto front.
         */
        double[] minimumValue;

        /**
         * Stores the normalized front.
         */
        double[][] normalizedFront;

        /**
         * Stores the normalized true Pareto front.
         */
        double[][] normalizedParetoFront;
        double[] extremeMinimumValues;
        double[] extremeMaximumValues;

        // STEP 1. Obtain the maximum and minimum values of the Pareto true front
        maximumValue = utils_.getMaximumValues(b, b[0].length);
        minimumValue = utils_.getMinimumValues(b, b[0].length);
        extremeMaximumValues = utils_.getMaximumValues(a, a[0].length);
        extremeMinimumValues = utils_.getMinimumValues(a, a[0].length);

        for (int i = 0; i < b[0].length; i++) {
            if (minimumValue[i] < 0.0) {
                minimumValue[i] = 0.0;//correct the minimumValue
            }
            if (extremeMaximumValues[i] > maximumValue[i]) {
                maximumValue[i] = extremeMaximumValues[i];
            }
            if (extremeMinimumValues[i] < minimumValue[i]) {

                /*	    		System.err.println("True front objective is worse than the obtained front!");
	    		System.out.println("True front minimum value of objective " +i+" is "  + minimumValue[i]);
	    		System.out.println("Obtained front minimum value of objective " +i+" is "  + extremeMinimumValues[i]);*/
                if (extremeMinimumValues[i] > 0.0) {
                    minimumValue[i] = extremeMinimumValues[i];
                } else {
                    minimumValue[i] = 0.0;
                }
                //minimumValue[i] = extremeMinimumValues[i];
                //System.exit(0);
            }
        }

        // STEP 2. Get the normalized front and true Pareto fronts
        normalizedFront = utils_.getNormalizedFront(a,
                maximumValue,
                minimumValue);

        normalizedParetoFront = utils_.getNormalizedFront(b,
                maximumValue,
                minimumValue);

        int i, j, k;
        double eps, eps_j = 0.0, eps_k = 0.0, eps_temp;

        dim_ = dim;
        set_params(method);

        if (method_ == 0) {
            eps = Double.MIN_VALUE;
        } else {
            eps = 0;
        }

        for (i = 0; i < normalizedParetoFront.length; i++) {
            for (j = 0; j < normalizedFront.length; j++) {
                for (k = 0; k < dim_; k++) {
                    switch (method_) {
                        case 0:
                            if (obj_[k] == 0) {
                                eps_temp = normalizedFront[j][k] - normalizedParetoFront[i][k];
                                if (eps_temp < 0.0) {
                                    eps_temp = 0.0;
                                }
                            } //eps_temp = b[j * dim_ + k] - a[i * dim_ + k];
                            else {
                                eps_temp = normalizedParetoFront[i][k] - normalizedFront[j][k];
                            }
                            //eps_temp = a[i * dim_ + k] - b[j * dim_ + k];
                            break;
                        default:
                            if ((normalizedFront[j][k] < 0 && normalizedParetoFront[i][k] > 0)
                                    || (normalizedFront[j][k] > 0 && normalizedParetoFront[i][k] < 0)
                                    || (normalizedFront[j][k] == 0 || normalizedParetoFront[i][k] == 0)) {
                                //if ( (a[i * dim_ + k] < 0 && b[j * dim_ + k] > 0) ||
                                //     (a[i * dim_ + k] > 0 && b[j * dim_ + k] < 0) ||
                                //     (a[i * dim_ + k] == 0 || b[j * dim_ + k] == 0)) {
                                System.err.println("error in data file");
                                System.exit(0);
                            }
                            if (obj_[k] == 0) {
                                eps_temp = normalizedFront[j][k] / normalizedParetoFront[i][k];
                            } //eps_temp = b[j * dim_ + k] / a[i * dim_ + k];
                            else {
                                eps_temp = normalizedParetoFront[i][k] / normalizedFront[j][k];
                            }
                            //eps_temp = a[i * dim_ + k] / b[j * dim_ + k];
                            break;
                    }
                    if (k == 0) {
                        eps_k = eps_temp;
                    } else if (eps_k < eps_temp) {
                        eps_k = eps_temp;//find the maximum distance from the point on solution front to the point on true front
                    }
                }
                if (j == 0) {
                    eps_j = eps_k;
                } else if (eps_j > eps_k) {
                    eps_j = eps_k;//find the nearest distance from the point to the point on the true front
                }
            }
            if (i == 0) {
                eps = eps_j;
            } else if (eps < eps_j)//find the maximum distance from the solution front to the true front
            {
                eps = eps_j;
            }
        }
        return eps;
    } // epsilon

    /**
     * Returns the epsilon indicator.
     *
     * @param a. Solution front
     * @param b. True Pareto front
     * @return the value of the epsilon indicator
     */
    /*  public double epsilon(double [][] b, double [][] a, int dim, int method) {
    int  i, j, k;
    double  eps, eps_j = 0.0, eps_k=0.0, eps_temp;
    
    dim_ = dim ;
    set_params(method) ;
    
    if (method_ == 0)
      eps = Double.MIN_VALUE;
    else
      eps= 0;

    for (i = 0; i < a.length; i++) {
      for (j = 0; j < b.length; j++) {
        for (k = 0; k < dim_; k++) {
          switch (method_) {
            case 0:
              if (obj_[k] == 0)
                eps_temp = a[i][k] - b[j][k];                
                //eps_temp = b[j * dim_ + k] - a[i * dim_ + k];
              else
                eps_temp = b[j][k] - a[i][k];
                //eps_temp = a[i * dim_ + k] - b[j * dim_ + k];
              break;
            default:
              if ( (a[i][k] < 0 && b[j][k] > 0) ||
                   (a[i][k] > 0 && b[j][k] < 0) ||
                   (a[i][k] == 0 || b[j][k] == 0)) {
              //if ( (a[i * dim_ + k] < 0 && b[j * dim_ + k] > 0) ||
              //     (a[i * dim_ + k] > 0 && b[j * dim_ + k] < 0) ||
              //     (a[i * dim_ + k] == 0 || b[j * dim_ + k] == 0)) {
                System.err.println("error in data file");
                System.exit(0);
              }
              if (obj_[k] == 0)
                eps_temp = a[i][k]/b[j][k]  ;
                //eps_temp = b[j * dim_ + k] / a[i * dim_ + k];
              else
                eps_temp = b[j][k]/a[i][k];
                //eps_temp = a[i * dim_ + k] / b[j * dim_ + k];
            break;
          }
          if (k == 0)
            eps_k = eps_temp;
          else if (eps_k < eps_temp)
            eps_k = eps_temp;//find the maximum distance from the point on solution front to the point on true front
        }
        if (j == 0)
          eps_j = eps_k;
        else if (eps_j > eps_k)
          eps_j = eps_k;//find the nearest distance from the point to the true front
      }
      if (i == 0)
        eps = eps_j;
      else if (eps < eps_j)//find the maximum distance from the solution front to the true front
        eps = eps_j;
    }
    return eps;
  } // epsilon
     */
    /**
     * Returns the epsilon indicator.
     *
     * @param a. Solution front
     * @param b. True Pareto front
     * @param dim
     * @param method
     * @return the value of the epsilon indicator
     */
    public double epsilon(double[][] b, double[][] a, int dim, int method) {
        int i, j, k;
        double eps, eps_j = 0.0, eps_k = 0.0, eps_temp;

        dim_ = dim;
        set_params(method);

        if (method_ == 0) {
            eps = Double.MIN_VALUE;
        } else {
            eps = 0;
        }

        for (i = 0; i < b.length; i++) {
            for (j = 0; j < a.length; j++) {
                for (k = 0; k < dim_; k++) {
                    switch (method_) {
                        case 0:
                            if (obj_[k] == 0) {
                                eps_temp = a[j][k] - b[i][k];
                            } //eps_temp = b[j * dim_ + k] - a[i * dim_ + k];
                            else {
                                eps_temp = b[i][k] - a[j][k];
                            }
                            //eps_temp = a[i * dim_ + k] - b[j * dim_ + k];
                            break;
                        default:
                            if ((a[j][k] < 0 && b[i][k] > 0)
                                    || (a[j][k] > 0 && b[i][k] < 0)
                                    || (a[j][k] == 0 || b[i][k] == 0)) {
                                //if ( (a[i * dim_ + k] < 0 && b[j * dim_ + k] > 0) ||
                                //     (a[i * dim_ + k] > 0 && b[j * dim_ + k] < 0) ||
                                //     (a[i * dim_ + k] == 0 || b[j * dim_ + k] == 0)) {
                                System.err.println("error in data file");
                                System.exit(0);
                            }
                            if (obj_[k] == 0) {
                                eps_temp = a[j][k] / b[i][k];
                            } //eps_temp = b[j * dim_ + k] / a[i * dim_ + k];
                            else {
                                eps_temp = b[i][k] / a[j][k];
                            }
                            //eps_temp = a[i * dim_ + k] / b[j * dim_ + k];
                            break;
                    }
                    if (k == 0) {
                        eps_k = eps_temp;
                    } else if (eps_k < eps_temp) {
                        eps_k = eps_temp;//find the maximum distance from the point on solution front to the point on true front
                    }
                }
                if (j == 0) {
                    eps_j = eps_k;
                } else if (eps_j > eps_k) {
                    eps_j = eps_k;//find the nearest distance from the point to the true front
                }
            }
            if (i == 0) {
                eps = eps_j;
            } else if (eps < eps_j)//find the maximum distance from the solution front to the true front
            {
                eps = eps_j;
            }
        }
        return eps;
    } // epsilon

    /**
     * Established the params by default
     *
     * @param method
     */
    public void set_params(int method) {
        int i;
        obj_ = new int[dim_];
        for (i = 0; i < dim_; i++) {
            obj_[i] = 0;
        }
        method_ = method;
    } // set_params

    /**
     * Returns the additive-epsilon value of the paretoFront.This method call to
     * the calculate epsilon-indicator one
     *
     * @param args
     * @param paretoFront The pareto front
     * @param paretoTrueFront The true pareto front
     * @param numberOfObjectives Number of objectives of the pareto front
     * @throws java.io.IOException
     */
    public static void main(String[] args) {
        //double ind_value;

        /*    if (args.length < 2) {
      System.err.println("Error using delta. Type: \n java AdditiveEpsilon " +
                         "<FrontFile>" +
                         "<TrueFrontFile> + <numberOfObjectives>");
      System.exit(1);
    }*/
        boolean normalisedEpsilonRevertWFG = false;
        boolean normalisedEpsilonRevertDTLZ = false;
        boolean normalisedEpsilonRevertVC = true;

        EpsilonPlus qualityIndicator = new EpsilonPlus();
        int maximumRun = 30;
        int dim = 3;

        if (normalisedEpsilonRevertWFG) {

            String[] algorithmNames = {"NSGAII", "SPEA2", "IBEA", "Random", "LA/LAResutls", "LA/DominanceInitalNew", "ChoiceFunction"};

            int ProblemNumber = 9;

            for (int problemIndex = 0; problemIndex < ProblemNumber; problemIndex++) {
                double[][] eachProblemEpsilon = new double[maximumRun][algorithmNames.length];
                String trueFrontPath = "HF_Config_Benchmark/WFG.3D/WFG" + (problemIndex + 1) + ".3D.pf";

                double[][] trueFront = qualityIndicator.utils_.readFront(trueFrontPath);

                for (int algorithmIndex = 0; algorithmIndex < algorithmNames.length; algorithmIndex++) {
                    double[] normalisedEpsilon = new double[maximumRun];
                    for (int runIndex = 0; runIndex < maximumRun; runIndex++) {
                        String obtainedFrontPath;

                        if (algorithmIndex <= 3) {
                            obtainedFrontPath = "HF_Config_Benchmark/Results/" + algorithmNames[algorithmIndex]
                                    + "/run_" + runIndex + "/ParetoFront_WFG" + (problemIndex + 1) + "_PF.txt";
                        } else if (algorithmIndex <= 5) {
                            obtainedFrontPath = "HF_Config_Benchmark/Results/" + algorithmNames[algorithmIndex]
                                    + "/run_" + runIndex + "/LA_FinalParetoFront_WFG" + (problemIndex + 1) + "_Run" + runIndex + ".txt";
                        } else {
                            obtainedFrontPath = "HF_Config_Benchmark/Results/" + algorithmNames[algorithmIndex]
                                    + "/run_" + runIndex + "/CF_FinalParetoFront_WFG" + (problemIndex + 1) + "_Run" + runIndex + ".txt";
                        }

                        double[][] obtainedFront = qualityIndicator.utils_.readFront(obtainedFrontPath);
                        normalisedEpsilon[runIndex] = qualityIndicator.normalisedEpsilonRevert(trueFront, obtainedFront, dim, 0);
                        eachProblemEpsilon[runIndex][algorithmIndex] = normalisedEpsilon[runIndex];
                    }
                    File runFolder = new File("DataTest/NormalisedEpsilon/WFG" + "\\" + algorithmNames[algorithmIndex]);
                    //File runFolder = new File(folderPath+"\\LAResutls\\run_"+runIndex);	
                    if (!runFolder.exists()) {
                        if (runFolder.mkdirs()) {
                            //System.out.println("Directory is created!");
                        } else {
                            System.out.println("Failed to create directory!");
                            System.exit(0);
                        }
                    }
                    String outputFilePath = runFolder + "/WFG" + (problemIndex + 1) + "_EpsilonAllRuns.txt";
                    try {
                        ((MetricsUtilPlus) qualityIndicator.utils_).printArray(normalisedEpsilon, outputFilePath); // TODO Auto-generated catch block
                    } catch (IOException ex) {
                        Logger.getLogger(EpsilonPlus.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }//end each algorithm
                String allEpsilonOutPath = "DataTest/NormalisedEpsilon/WFG/WFG" + (problemIndex + 1) + "_EpsilonAllRuns.txt";
                try {
                    ((MetricsUtilPlus) qualityIndicator.utils_).writeFront(allEpsilonOutPath, eachProblemEpsilon);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }//end each problem

            System.out.println("Finished WFG problems.");
        }//end wfg problems

        if (normalisedEpsilonRevertDTLZ) {
            String[] algorithmNames = {"NSGAII", "SPEA2", "IBEA", "RandomAM", "LAResutls", "DominanceInitalNew", "ChoiceFunction"};

            int ProblemNumber = 7;

            for (int problemIndex = 0; problemIndex < ProblemNumber; problemIndex++) {
                double[][] eachProblemEpsilon = new double[maximumRun][algorithmNames.length];
                String trueFrontPath = "HF_Config_Benchmark/DTLZ.3D/DTLZ" + (problemIndex + 1) + ".3D.pf";

                double[][] trueFront = qualityIndicator.utils_.readFront(trueFrontPath);

                for (int algorithmIndex = 0; algorithmIndex < algorithmNames.length; algorithmIndex++) {
                    double[] normalisedEpsilon = new double[maximumRun];
                    for (int runIndex = 0; runIndex < maximumRun; runIndex++) {
                        String obtainedFrontPath;

                        if (algorithmIndex <= 3) {
                            obtainedFrontPath = "HF_Config_Benchmark/Results/DTLZ/" + algorithmNames[algorithmIndex]
                                    + "/run_" + runIndex + "/ParetoFront_DTLZ" + (problemIndex + 1) + "_PF.txt";
                        } else if (algorithmIndex <= 5) {
                            obtainedFrontPath = "HF_Config_Benchmark/Results/DTLZ/" + algorithmNames[algorithmIndex]
                                    + "/run_" + runIndex + "/LA_FinalParetoFront_DTLZ" + (problemIndex + 1) + "_Run" + runIndex + ".txt";
                        } else {
                            obtainedFrontPath = "HF_Config_Benchmark/Results/DTLZ/" + algorithmNames[algorithmIndex]
                                    + "/run_" + runIndex + "/CF_FinalParetoFront_DTLZ" + (problemIndex + 1) + "_Run" + runIndex + ".txt";
                        }

                        double[][] obtainedFront = qualityIndicator.utils_.readFront(obtainedFrontPath);
                        normalisedEpsilon[runIndex] = qualityIndicator.normalisedEpsilonRevert(trueFront, obtainedFront, dim, 0);
                        eachProblemEpsilon[runIndex][algorithmIndex] = normalisedEpsilon[runIndex];
                    }
                    File runFolder = new File("DataTest/NormalisedEpsilon/DTLZ" + "\\" + algorithmNames[algorithmIndex]);
                    //File runFolder = new File(folderPath+"\\LAResutls\\run_"+runIndex);	
                    if (!runFolder.exists()) {
                        if (runFolder.mkdirs()) {
                            //System.out.println("Directory is created!");
                        } else {
                            System.out.println("Failed to create directory!");
                            System.exit(0);
                        }
                    }
                    String outputFilePath = runFolder + "/DTLZ" + (problemIndex + 1) + "_EpsilonAllRuns.txt";
                    try {
                        ((MetricsUtilPlus) qualityIndicator.utils_).printArray(normalisedEpsilon, outputFilePath);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                String allEpsilonOutPath = "DataTest/NormalisedEpsilon/DTLZ/DTLZ" + (problemIndex + 1) + "_EpsilonAllRuns.txt";
                try {
                    ((MetricsUtilPlus) qualityIndicator.utils_).writeFront(allEpsilonOutPath, eachProblemEpsilon);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            System.out.println("Finished DTLZ problems.");
        }//end DTLZ problems

        /*   String solutionFrontPath = "DataTest/solutionFront.txt";
    String TrueFrontPath = "DataTest/trueFront.txt";
    
    double [][] solutionFront = qualityIndicator.utils_.readFront(solutionFrontPath);
    double [][] trueFront     = qualityIndicator.utils_.readFront(TrueFrontPath);
    //qualityIndicator.dim_ = trueParetoFront[0].length;
    //qualityIndicator.set_params();
       
    ind_value = qualityIndicator.epsilon(solutionFront,
            trueFront,2,0);
   double ind_value1 = qualityIndicator.epsilon(trueFront,
    		solutionFront ,2,0);
   
    ind_value = qualityIndicator.epsilon(trueFront,
                                         solutionFront,
                                         new Integer(args[2]).intValue());
    
    System.out.println(ind_value);
    System.out.println(ind_value1);*/
    } // main
} // Epsilon

