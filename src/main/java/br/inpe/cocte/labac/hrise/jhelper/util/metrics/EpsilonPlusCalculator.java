package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.uma.jmetal.util.SolutionListUtils;

import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;

import br.inpe.cocte.labac.hrise.jhelper.metrics.extrametrics.EpsilonPlus;

/**
 * This class extends Calculator class and perform an evaluation using
 * Hypervolume Indicator.
 */
public class EpsilonPlusCalculator extends Calculator {

    protected @Getter @Setter int type=0;
    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     */
    public EpsilonPlusCalculator(int numberOfObjectives) {
        super(numberOfObjectives);
        this.indicatorName = "EpsilonPlus";
        this.lowerValuesAreBetter = true;
    }

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param path the path
     * @throws FileNotFoundException the file not found exception
     */
    public EpsilonPlusCalculator(int numberOfObjectives, String path) throws FileNotFoundException {
        this(numberOfObjectives, new ArrayFront(path));
        this.lowerValuesAreBetter = true;
    }

    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param referenceFront the reference front
     * @throws FileNotFoundException the file not found exception
     */
    public EpsilonPlusCalculator(int numberOfObjectives, Front referenceFront)
            throws FileNotFoundException {
        //super(numberOfObjectives, referenceFront, true);
        super(numberOfObjectives, referenceFront);
        this.indicatorName = "EpsilonPlus";
        this.lowerValuesAreBetter = true;
    }
    
    /**
     * Instantiates a new Hypervolume calculator.
     *
     * @param numberOfObjectives the number of objectives
     * @param referenceFront the reference front
     * @param normalize
     * @throws FileNotFoundException the file not found exception
     */
    public EpsilonPlusCalculator(int numberOfObjectives, Front referenceFront, boolean normalize)
            throws FileNotFoundException {
        //super(numberOfObjectives, referenceFront, normalize);
        super(numberOfObjectives, referenceFront);
        this.indicatorName = "EpsilonPlus";
        this.lowerValuesAreBetter = true;
    }

    @Override
    public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
        EpsilonPlus ep=new EpsilonPlus();
        //if (normalize) {
            if (maximumValues != null && minimumValues != null) {
                //if (!this.isMinMaxTheSame(maximumValues, minimumValues)) {
                    FrontNormalizer frontNormalizer = new FrontNormalizer(minimumValues, maximumValues);
                    front = frontNormalizer.normalize(front);
                //}
            } else {
                return 1D;
            }
            double[][] frontArray=SolutionListUtils.writeObjectivesToMatrix(FrontUtils.convertFrontToSolutionList(front));
            double[][] paretoTrueFrontArray=SolutionListUtils.writeObjectivesToMatrix(FrontUtils.convertFrontToSolutionList(paretoTrueFront));
            return ep.normalisedEpsilon(frontArray, paretoTrueFrontArray, numberOfObjectives, type);
       // }
        //double[][] frontArray=SolutionListUtils.writeObjectivesToMatrix(FrontUtils.convertFrontToSolutionList(front));
        //double[][] paretoTrueFrontArray=SolutionListUtils.writeObjectivesToMatrix(FrontUtils.convertFrontToSolutionList(paretoTrueFront));
        //return ep.epsilon(frontArray, paretoTrueFrontArray, numberOfObjectives, type);
    }
}
