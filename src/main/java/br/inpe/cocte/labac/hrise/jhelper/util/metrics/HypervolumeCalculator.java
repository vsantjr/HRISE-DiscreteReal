package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;

/**
 * This class extends Calculator class and perform an evaluation using
 * Hypervolume Indicator.
 */
public class HypervolumeCalculator extends Calculator {

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     */
    public HypervolumeCalculator(int numberOfObjectives) {
        super(numberOfObjectives);
        this.indicatorName = "Hypervolume";
        this.lowerValuesAreBetter = new PISAHypervolume().isTheLowerTheIndicatorValueTheBetter();
    }

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param path the path
     * @throws FileNotFoundException the file not found exception
     */
    public HypervolumeCalculator(int numberOfObjectives, String path) throws FileNotFoundException {
        this(numberOfObjectives, new ArrayFront(path));
        this.lowerValuesAreBetter = new PISAHypervolume().isTheLowerTheIndicatorValueTheBetter();
    }

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param referenceFront the reference front
     * @throws FileNotFoundException the file not found exception
     */
    public HypervolumeCalculator(int numberOfObjectives, Front referenceFront)
            throws FileNotFoundException {
        super(numberOfObjectives, referenceFront);
        this.indicatorName = "Hypervolume";
        this.lowerValuesAreBetter = new PISAHypervolume().isTheLowerTheIndicatorValueTheBetter();
    }

    public void updatePointsUsingNadir(double[] nadir) {
        maximumValues = nadir;
        minimumValues = new double[numberOfObjectives];
        Arrays.fill(minimumValues, 0.0);
    }
    
    
    // 03/05/2019: update
    
    public void updatePointsUsingNadirIdeal(double[] nadir, double[] ideal) {
        maximumValues = nadir;
        minimumValues = new double[numberOfObjectives];
        minimumValues = ideal;
        //Arrays.fill(minimumValues, 0);
    }

    @Override
    public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
        if (maximumValues != null && minimumValues != null) {
            FrontNormalizer frontNormalizer = new FrontNormalizer(minimumValues, maximumValues);
            Front normalizedFront = frontNormalizer.normalize(front);
            List<PointSolution> normalizedPopulation = FrontUtils
                    .convertFrontToSolutionList(normalizedFront);
            return new PISAHypervolume<PointSolution>(paretoTrueFront).evaluate(normalizedPopulation);
        }
        return 0D;
    }
}
