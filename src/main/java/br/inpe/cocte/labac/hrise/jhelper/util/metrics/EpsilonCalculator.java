package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import java.util.Arrays;
import org.uma.jmetal.qualityindicator.impl.Epsilon;

import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;

/**
 * This class extends Calculator class and perform an evaluation using
 * Hypervolume Indicator.
 */
public class EpsilonCalculator extends Calculator {

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     */
    public EpsilonCalculator(int numberOfObjectives) {
        super(numberOfObjectives);
        this.indicatorName = "Epsilon";
        this.lowerValuesAreBetter = new Epsilon().isTheLowerTheIndicatorValueTheBetter();
    }

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param path the path
     * @throws FileNotFoundException the file not found exception
     */
    public EpsilonCalculator(int numberOfObjectives, String path) throws FileNotFoundException {
        this(numberOfObjectives, new ArrayFront(path));
        this.lowerValuesAreBetter = new Epsilon().isTheLowerTheIndicatorValueTheBetter();
    }

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param referenceFront the reference front
     * @throws FileNotFoundException the file not found exception
     */
    public EpsilonCalculator(int numberOfObjectives, Front referenceFront)
            throws FileNotFoundException {
        super(numberOfObjectives, referenceFront);
        this.indicatorName = "Epsilon";
        this.lowerValuesAreBetter = new Epsilon().isTheLowerTheIndicatorValueTheBetter();
    }

    @Override
    public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
        if (maximumValues != null && minimumValues != null) {
            FrontNormalizer frontNormalizer = new FrontNormalizer(minimumValues, maximumValues);
            Front normalizedFront = frontNormalizer.normalize(front);
            return new Epsilon(paretoTrueFront).evaluate(FrontUtils.convertFrontToSolutionList(normalizedFront));
        }
        return 1D;
    }
}
