package br.inpe.cocte.labac.hrise.jhelper.metrics.extrametrics;

import java.io.FileNotFoundException;
import java.util.List;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.stat.StatUtils;
import static org.apache.commons.math3.util.MathArrays.distance;
import org.uma.jmetal.qualityindicator.impl.GenericIndicator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;

/**
 * This class implements the Spacing metric. This metric is a value measuring
 * how evenly the nondominated solutions are distributed along the approximation
 * front. S = 0 indicates that all members of the approximation front are
 * equidistantly spaced.
 *
 * Reference: J. R. Schott. Fault Tolerant Design Using Single and Multicriteria
 * Genetic Algorithm Optimization. Master Thesis, Boston, MA: Department of
 * Aeronautics and Astronautics, Massachusetts Institute of Technology, 1995.
 *
 * @param <T>
 */
public class Spacing<T extends Solution<?>> extends GenericIndicator<T> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor. Creates a new instance of the spacing metric.
     *
     * @param referenceParetoFrontFile
     * @throws java.io.FileNotFoundException
     */
    public Spacing(String referenceParetoFrontFile) throws FileNotFoundException {
        super(referenceParetoFrontFile);
    }

    public Spacing(Front paretoTrueFront) {
        super(paretoTrueFront);
    }

    @Override
    public Double evaluate(List<T> approximationSet) {

        double sum = 0.0;

        // STEP 1. Obtain the maximum and minimum values of the Pareto front
        double[] maximumValues = FrontUtils.getMaximumValues(referenceParetoFront);
        double[] minimumValues = FrontUtils.getMinimumValues(referenceParetoFront);

        // STEP 2. Get the normalized front
        FrontNormalizer frontNormalizer = new FrontNormalizer(minimumValues, maximumValues);
        Front normalizedFront = frontNormalizer.normalize(new ArrayFront(approximationSet));
        double[][] normalizedApproximation = FrontUtils.convertFrontToArray(normalizedFront);

        double[] d = new double[approximationSet.size()];

        for (int i = 0; i < approximationSet.size(); i++) {
            double min = Double.POSITIVE_INFINITY;
            double[] solutionI = normalizedApproximation[i];

            /*if (solutionI.violatesConstraints()) {
				continue;
			}*/
            for (int j = 0; j < approximationSet.size(); j++) {
                if (i != j) {
                    double[] solutionJ = normalizedApproximation[j];

                    /*if (solutionJ.violatesConstraints()) {
						continue;
					}*/
                    try {
                        min = Math.min(min, distance(solutionI, solutionJ));
                    } catch (DimensionMismatchException e) {
                    }
                }
            }
            d[i] = min;
        }
        double dbar = StatUtils.sum(d) / approximationSet.size();

        for (int i = 0; i < approximationSet.size(); i++) {
            /*
            if (approximationSet.get(i).violatesConstraints()) {
                continue;
            }
             */
            sum += Math.pow(d[i] - dbar, 2.0);
        }

        return Math.sqrt(sum / (approximationSet.size() - 1));
    }

    @Override
    public boolean isTheLowerTheIndicatorValueTheBetter() {
        return true;
    }

    @Override
    public String getName() {
        return "Spacing";
    }

    @Override
    public String getDescription() {
        return "Spacing quality indicator";
    }
}
