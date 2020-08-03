package HF_Indicators;

import java.io.IOException;
import java.util.ArrayList;

import HF_choicefunction.DMatrix;
import HF_utils.GetApproximatedParetoFrontfromDbl;
import jmetal.qualityIndicator.D1R;
import jmetal.qualityIndicator.EpsilonPlus;
import jmetal.qualityIndicator.Exploration;
import jmetal.qualityIndicator.HyperVolumeMinimizationProblem;
import jmetal.qualityIndicator.InvertedGenerationalDistancePlus;
import jmetal.qualityIndicator.util.MetricsUtilPlus;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.qualityindicator.impl.GeneralizedSpread;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;

public class testIndicators {

    jmetal.qualityIndicator.util.MetricsUtil util_;

    public testIndicators() {
        util_ = new jmetal.qualityIndicator.util.MetricsUtil();
    }

    public static void main(String[] args) throws IOException {
        Problem problem = new WFG1();
        MetricsUtilPlus mutil = new MetricsUtilPlus();
        // TODO Auto-generated method stub
        jmetal.qualityIndicator.util.MetricsUtilPlus util_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();
        HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();
        EpsilonPlus ep = new EpsilonPlus();
        DMatrix dm = new DMatrix();
        InvertedGenerationalDistance IGD = new InvertedGenerationalDistance();
        InvertedGenerationalDistancePlus IGDplus = new InvertedGenerationalDistancePlus();
        D1R d1r = new D1R();
        GetApproximatedParetoFrontfromDbl AF = new GetApproximatedParetoFrontfromDbl();
        GeneralizedSpread GSpread = new GeneralizedSpread();
        Exploration epl = new Exploration();

        int numberOfFiles = 400;
        double[] referencePoint = {9, 9.5, 1.7};
        int numberOfObjectives = referencePoint.length;
        double[] reference = new double[numberOfObjectives];
        for (int i = 0; i < reference.length; i++) {
            reference[i] = 1.0;
        }

        double hypervolume[] = new double[numberOfFiles];
        double hypervolumeAP[] = new double[numberOfFiles];

        double spread[] = new double[numberOfFiles];

        double[] epsilonAdditiveArray = new double[numberOfFiles - 1];
        double[] epsilonMultipicativeArray = new double[numberOfFiles - 1];
        double[] epsilonAdditiveArrayAP = new double[numberOfFiles - 1];
        double[] epsilonMultipicativeArrayAP = new double[numberOfFiles - 1];

        double[] dMetricxArray12 = new double[numberOfFiles - 1];
        double[] dMetricxArray21 = new double[numberOfFiles - 1];
        double[] dMetricxArray12AP = new double[numberOfFiles - 1];
        double[] dMetricxArray21AP = new double[numberOfFiles - 1];

        double[] IGDMetricx = new double[numberOfFiles - 1];
        double[] IGDMetricxAP = new double[numberOfFiles - 1];

        double[] IGDplusMetricx = new double[numberOfFiles - 1];
        double[] IGDplusMetricxAP = new double[numberOfFiles - 1];

        double[] d1rMetricx = new double[numberOfFiles - 1];
        double[] d1rMetricxAP = new double[numberOfFiles - 1];

        double[] exploration = new double[numberOfFiles - 1];
        double[] explorationAP = new double[numberOfFiles - 1];

        int recordFrequency = 50;
        String fileName = null;
        String algorithmName = null;
        String[] fileNames = new String[numberOfFiles];
        int interationNO = 10;
        String path = "DataTest/Indicators/Input/10DPFixedSequenceAM102/run_0";
        double minimumValues[] = new double[numberOfObjectives];
        double maximumValues[] = new double[numberOfObjectives];
        int populationSize = 50;

        for (int i = 0; i < numberOfObjectives; i++) {
            minimumValues[i] = Double.MAX_VALUE;
            maximumValues[i] = -Double.MAX_VALUE;
        }
        ArrayList<double[]> previousFront = new ArrayList<double[]>();

        int fileNameperIteration = numberOfFiles / interationNO;

        for (int j = 0; j < interationNO; j++) {

            if (Math.floorMod(j, 3) == 0) {
                algorithmName = "SPEA2";
            } else if (Math.floorMod(j, 3) == 1) {
                algorithmName = "NSGAII";
            } else if (Math.floorMod(j, 3) == 2) {
                algorithmName = "IBEA";
            }
            for (int i = 0; i < fileNameperIteration; i++) {
                fileNames[i + fileNameperIteration * j] = path + "/" + algorithmName + "_Iter_" + j + "_Evl" + (recordFrequency * (i + 1)) + ".pf";
            }
        }

        for (int index = 0; index < numberOfFiles; index++) {
            fileName = fileNames[index];

            double[][] ParetoFront = util_.readFront(fileName);
            double[][] uniqueFront = util_.removeDuplicatePointsonFront(ParetoFront);

            for (int k = 0; k < uniqueFront.length; k++) {
                previousFront.add(uniqueFront[k]);
            }

            double[][] sofarPF = new double[previousFront.size()][numberOfObjectives];
            for (int s = 0; s < sofarPF.length; s++) {
                sofarPF[s] = previousFront.get(s);
            }
            sofarPF = util_.removeDuplicatePointsonFront(sofarPF);
            sofarPF = AF.getApproxFront(sofarPF);
            sofarPF = util_.removeDuplicatePointsonFront(sofarPF);//remove all 0s in the final dataset

            hypervolume[index] = hyp.hypervolume(uniqueFront, numberOfObjectives, referencePoint);

            Front sofarPFFront = new ArrayFront(mutil.vector2SolutionSet(sofarPF, problem));
            Front uniqueFrontFront = new ArrayFront(mutil.vector2SolutionSet(sofarPF, problem));
            spread[index] = GSpread.generalizedSpread(uniqueFrontFront, sofarPFFront);

            double minimumValuesTemp[] = util_.getMinimumValues(uniqueFront, numberOfObjectives);
            double maximumValuesTemp[] = util_.getMaximumValues(uniqueFront, numberOfObjectives);

            for (int k = 0; k < numberOfObjectives; k++) {
                if (minimumValuesTemp[k] < minimumValues[k]) {
                    minimumValues[k] = minimumValuesTemp[k];
                }
                if (maximumValuesTemp[k] > maximumValues[k]) {
                    maximumValues[k] = maximumValuesTemp[k];
                }
            }

            double[][] normalizedFront = util_.getNormalizedFront(uniqueFront, maximumValues, minimumValues);
            double[][] uniqueNormalizedFront = util_.removeDuplicatePointsonFront(normalizedFront);

            hypervolumeAP[index] = hyp.hypervolume(uniqueNormalizedFront, numberOfObjectives, reference);

            if (index > 0) {
                double[][] previousFrontDubl = util_.readFront(fileNames[index - 1]);
                double[][] uniquePreviousFrontDubl = util_.removeDuplicatePointsonFront(previousFrontDubl);
                double[][] sofarPFNormalized = util_.getNormalizedFront(sofarPF, maximumValues, minimumValues);

                double[][] normalizedpreviousFront = util_.getNormalizedFront(uniquePreviousFrontDubl, maximumValues, minimumValues);

                dMetricxArray12[index - 1] = dm.DMatrixValue12Dbl(sofarPF, uniqueFront, referencePoint);
                //dMetricxArray21[index-1] = dm.DMatrixValue12Dbl(sofarPF, uniqueFront, referencePoint);

                dMetricxArray12AP[index - 1] = dm.DMatrixValue12Dbl(sofarPFNormalized, uniqueNormalizedFront, reference);
                //dMetricxArray21AP[index-1] = dm.DMatrixValue12Dbl(normalizedpreviousFront, uniqueNormalizedFront, reference);
                exploration[index - 1] = epl.exploration(uniqueFront, uniquePreviousFrontDubl);
                explorationAP[index - 1] = epl.exploration(uniqueNormalizedFront, normalizedpreviousFront);

                int method = 0;
                epsilonAdditiveArray[index - 1] = ep.epsilon(uniqueNormalizedFront, sofarPFNormalized, numberOfObjectives, method);
                //epsilonAdditiveArrayAP[index-1] = ep.epsilon(uniqueNormalizedFront, sofarPFNormalized, numberOfObjectives, method);

                method = 1;
                epsilonMultipicativeArray[index - 1] = ep.epsilon(uniqueFront, sofarPF, numberOfObjectives, method);
                //epsilonMultipicativeArrayAP[index-1] = ep.epsilon(uniqueNormalizedFront, normalizedpreviousFront, numberOfObjectives, method);

                sofarPFFront = new ArrayFront(mutil.vector2SolutionSet(sofarPF, problem));
                uniqueFrontFront = new ArrayFront(mutil.vector2SolutionSet(sofarPF, problem));
                IGDMetricx[index - 1] = IGD.invertedGenerationalDistance(uniqueFrontFront, sofarPFFront);
                // IGDMetricxAP[index-1] = IGD.invertedGDNoNormalization(uniqueNormalizedFront, normalizedpreviousFront, numberOfObjectives);

                IGDplusMetricx[index - 1] = IGDplus.invertedGenerationalDistancePlus(uniqueFront, sofarPF);
                //IGDplusMetricxAP[index-1] = IGDplus.IGDplusNoNormalization(uniqueNormalizedFront, normalizedpreviousFront);

                d1rMetricx[index - 1] = d1r.D1Rdistance(uniqueFront, sofarPF);
                // d1rMetricxAP[index-1] = d1r.D1RdistanceNoNormalization(uniqueNormalizedFront, normalizedpreviousFront);
            }

        }

        //print
        String[] outputFile = {"hypervolume", "hypervolumeAP", "dMetricxArray12", "dMetricxArray12AP",
            "epsilonAdditiveArray", "epsilonMultipicativeArray",
            "IGDMetricx", "IGDplusMetricx", "d1rMetricx", "spread", "exploration", "explorationAP", "minimumValues", "maximumValues"};
        ArrayList<double[]> outputData = new ArrayList<double[]>();
        outputData.add(hypervolume);
        outputData.add(hypervolumeAP);
        outputData.add(dMetricxArray12);
        outputData.add(dMetricxArray12AP);
        outputData.add(epsilonAdditiveArray);
        outputData.add(epsilonMultipicativeArray);
        outputData.add(IGDMetricx);
        outputData.add(IGDplusMetricx);
        outputData.add(d1rMetricx);
        outputData.add(spread);
        outputData.add(exploration);
        outputData.add(explorationAP);
        outputData.add(minimumValues);
        outputData.add(maximumValues);
        for (int t = 0; t < outputFile.length; t++) {
            String DesPath = "DataTest/Indicators/Input/10DPFixedSequenceAM102/" + outputFile[t] + ".txt";
            util_.printArray(outputData.get(t), DesPath);
        }

    }

}
