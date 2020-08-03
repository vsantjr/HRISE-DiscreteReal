package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistance;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;

/**
 * This class extends Calculator class and perform an evaluation using IGD
 * Indicator.
 */
public class IgdCalculator extends Calculator {

    /**
     * IGD indicator instance.
     */
    private final InvertedGenerationalDistance igd;

    /**
     * Instantiates a new Igd calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param path the path
     * @throws java.io.FileNotFoundException file not found.
     */
    public IgdCalculator(int numberOfObjectives, String path) throws FileNotFoundException {
        super(numberOfObjectives, path);
        this.igd = new InvertedGenerationalDistance(paretoTrueFront);
        this.indicatorName = "IGD";
        this.lowerValuesAreBetter = igd.isTheLowerTheIndicatorValueTheBetter();
    }

    /**
     * Instantiates a new Igd calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param paretoFront the pareto front
     */
    public IgdCalculator(int numberOfObjectives, Front paretoFront) {
        super(numberOfObjectives, paretoFront);
        this.igd = new InvertedGenerationalDistance(paretoTrueFront);
        this.indicatorName = "IGD";
        this.lowerValuesAreBetter = igd.isTheLowerTheIndicatorValueTheBetter();
    }

    @Override
    public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
        double val = 1;
        if (maximumValues != null && minimumValues != null) {
            FrontNormalizer frontNormalizer = new FrontNormalizer(minimumValues, maximumValues);
            Front normalizedFront = frontNormalizer.normalize(front);
            List<PointSolution> normalizedPopulation = FrontUtils
                    .convertFrontToSolutionList(normalizedFront);
            val = igd.evaluate(normalizedPopulation);
        }
        return val;
    }
}
