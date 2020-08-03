package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import org.uma.jmetal.util.front.Front;

/**
 * This class extends Calculator class and perform an evaluation using
 * Hypervolume Indicator.
 */
public class HypervolumeRatioCalculator extends Calculator {

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     */
    private HypervolumeCalculator hv;

    public HypervolumeRatioCalculator(int numberOfObjectives) {
        super(numberOfObjectives);
        this.indicatorName = "Hypervolume Ratio";
        this.lowerValuesAreBetter = false;
        hv = new HypervolumeCalculator(numberOfObjectives);
    }

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param path the path
     * @throws FileNotFoundException the file not found exception
     */
    public HypervolumeRatioCalculator(int numberOfObjectives, String path) throws FileNotFoundException {
        super(numberOfObjectives, path);
        this.indicatorName = "Hypervolume Ratio";
        this.lowerValuesAreBetter = false;
        hv = new HypervolumeCalculator(numberOfObjectives, path);
    }

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param referenceFront the reference front
     * @throws FileNotFoundException the file not found exception
     */
    public HypervolumeRatioCalculator(int numberOfObjectives, Front referenceFront)
            throws FileNotFoundException {
        super(numberOfObjectives, referenceFront);
        this.indicatorName = "Hypervolume Ratio";
        this.lowerValuesAreBetter = false;
        hv = new HypervolumeCalculator(numberOfObjectives, referenceFront);
    }

    public double calculate(Front front, Front pf) {
        hv.setParetoTrueFront(pf);
        double hypValueFront = hv.execute(front);
        double hypValuePf = hv.execute(pf);
        return hypValueFront / hypValuePf;
    }

    @Override
    public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
        return Double.NEGATIVE_INFINITY;
    }
}
