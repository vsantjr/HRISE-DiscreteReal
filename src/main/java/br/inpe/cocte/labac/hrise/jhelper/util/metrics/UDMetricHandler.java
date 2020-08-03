package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.stat.StatUtils;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;
import org.uma.jmetal.util.distance.impl.EuclideanDistanceBetweenSolutionsInObjectiveSpace;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.util.FrontUtils;

public class UDMetricHandler<S extends Solution<?>> extends Calculator {

    protected final double sigmaShare;

    public UDMetricHandler(int numObj) {
        super(numObj);
        sigmaShare = 0.1;
        lowerValuesAreBetter=false;
    }

    public UDMetricHandler(int numberOfObjectives, Front paretoFront) {
        super(numberOfObjectives, paretoFront);
        sigmaShare = 0.1;
        lowerValuesAreBetter=false;
    }

    public UDMetricHandler(int numberOfObjectives, String path) throws FileNotFoundException {
        super(numberOfObjectives, path);
        sigmaShare = 0.1;
        lowerValuesAreBetter=false;
    }
    
    

    protected double snc(double[] nicheCount) {
        double sum = 0;
        double nicheMean = StatUtils.mean(nicheCount);
        for (int i = 0; i < nicheCount.length; i++) {
            sum += Math.pow((nicheCount[i] - nicheMean), 2);
        }
        double frac = (sum / (nicheCount.length - 1));
        return Math.sqrt(frac);
    }

    protected double[] calculateNicheCount(double[][] distanceMatrix) {
        double[] nicheCount = new double[distanceMatrix.length];
        Arrays.fill(nicheCount, 0D);
        for (int i = 0; i < distanceMatrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                if (j != i) {
                    sum += (distanceMatrix[i][j] < sigmaShare) ? 1D : 0D;//(1 - Math.pow(dist/sigmaShare, 2))
                }
            }
            nicheCount[i] = sum;
        }
        return nicheCount;
    }

    protected double[][] calculatePopulationDistances(List<S> population) {
        EuclideanDistanceBetweenSolutionsInObjectiveSpace distance = new EuclideanDistanceBetweenSolutionsInObjectiveSpace();
        double[][] distances = new double[population.size()][population.size()];
        for (int i = 0; i < population.size(); i++) {
            for (int j = 0; j < population.size(); j++) {
                distances[i][j] = distance.getDistance(population.get(i), population.get(j));
            }
        }
        return distances;
    }

    @Override
    public double calculate(Front front, double[] doubles, double[] doubles1) {
        List<S> listOfFront = (List<S>) FrontUtils.convertFrontToSolutionList(front);
        NonDominatedSolutionListArchive ndominated = new NonDominatedSolutionListArchive();
        listOfFront.forEach((s) -> {ndominated.add(s);});
        double[][] distanceMatrix = calculatePopulationDistances(ndominated.getSolutionList());
        double[] nicheCount = calculateNicheCount(distanceMatrix);
        double snc = this.snc(nicheCount);
        return 1D / (1D + snc);
    }
}
