package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.front.Front;

/**
 * This class extends Calculator class and perform an evaluation using the
 * RniCalculator Indicator.
 */
public class NRCalculator<S extends Solution<?>> extends Calculator {

    /**
     * The population size.
     */
    private @Getter
    @Setter
    int populationMaxSize;

    /**
     * Instantiates a new Rni.
     *
     * @param numberOfObjectives the number of objectives
     * @param populationMaxSize the population max size
     * @param path the path
     * @throws FileNotFoundException the file not found exception
     */
    public NRCalculator(int numberOfObjectives, int populationMaxSize, String path)
            throws FileNotFoundException {
        super(numberOfObjectives, path);
        this.populationMaxSize = populationMaxSize;
        this.indicatorName = "NR";
        lowerValuesAreBetter = false;
    }

    /**
     * Instantiates a new Rni calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param populationMaxSize the population max size
     * @param referenceFront the reference front
     */
    public NRCalculator(int numberOfObjectives, int populationMaxSize, Front referenceFront) {
        super(numberOfObjectives, referenceFront);
        this.populationMaxSize = populationMaxSize;
        this.indicatorName = "NR";
        lowerValuesAreBetter = false;
    }

    private String strVariables(S s) {
        String str = "";
        for (int i = 0; i < s.getNumberOfVariables(); i++) {
            str += String.valueOf(s.getVariableValue(i)) + ".";
        }
        return str;
    }

    private List<String> getPopAsString(List<S> front) {
        List<String> toReturn = new ArrayList<>();
        for (S s : front) {
            toReturn.add(strVariables(s));
        }
        return toReturn;
    }

    private List<S> getIntersection(List<S> frontA, List<S> frontB) {
        List<S> toReturn = new ArrayList<>();
        List<String> frontAString = getPopAsString(frontA);
        List<String> frontBString = getPopAsString(frontB);
        for (int i = 0; i < frontA.size(); i++) {
            if (frontBString.contains(frontAString.get(i))) {
                toReturn.add(frontA.get(i));
            }
        }
        return toReturn;
    }

    public double calculate(List<S> frontA, List<S> frontB) {
        List<S> intersection = getIntersection(frontA, frontB);
        return ((double) intersection.size()) / ((double) frontB.size());
    }

    @Override
    public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
        return Double.NEGATIVE_INFINITY;
    }
}
