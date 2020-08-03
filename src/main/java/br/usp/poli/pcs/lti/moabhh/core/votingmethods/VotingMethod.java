package br.usp.poli.pcs.lti.moabhh.core.votingmethods;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.math3.stat.StatUtils;

/**
 *
 * @author vinicius
 */
public abstract class VotingMethod {

    protected void normalizeValues(double[][] valuesTable) {
        for (double[] valuesTable1 : valuesTable) {
            double sum = StatUtils.sum(valuesTable1);
            for (int idCandidate = 0; idCandidate < valuesTable1.length; idCandidate++) {
                valuesTable1[idCandidate] = (valuesTable1[idCandidate] / sum) * 100.0;
            }
        }
    }

    /**
     * Prepare comparision array list.
     *
     * @param qtdCandidates
     * @return the array list
     */
    public ArrayList<String> generateComparisionNames(int qtdCandidates) {
        ArrayList<String> toReturn = new ArrayList<>();
        for (int one = 0; one < qtdCandidates; one++) {
            for (int other = 0; other < qtdCandidates; other++) {
                if (one != other) {
                    String name1 = one + "-" + other;
                    String name2 = other + "-" + one;
                    if (!toReturn.contains(name1) && !toReturn.contains(name2)) {
                        toReturn.add(name1);
                        toReturn.add(name2);
                    }
                }
            }
        }
        return toReturn;
    }

    public HashMap<String, Double> generatePairWiseComparisionTable(ArrayList<String> namesInComparision, double[][] valueTable) {
        HashMap<String, Double> pairwiseComparision = new HashMap<>();
        HashMap<String, Double> tiedpairwiseComparision = new HashMap<>();//ver
        for (int idvoter = 0; idvoter < valueTable.length; idvoter++) {
            for (String str : namesInComparision) {
                String[] aux = str.split("-");
                int one = Integer.parseInt(aux[0]);
                int other = Integer.parseInt(aux[1]);
                if (valueTable[idvoter][one] > valueTable[idvoter][other]) {
                    pairwiseComparision.put(str, pairwiseComparision.getOrDefault(str, 0.0) + 1);
                } else if (valueTable[idvoter][one] == valueTable[idvoter][other]) {
                    tiedpairwiseComparision.put(str, pairwiseComparision.getOrDefault(str, 0.0) + 1);
                }
            }
        }
        return pairwiseComparision;
    }

    public abstract int[] votationMethod(double[][] metricValues);
}
