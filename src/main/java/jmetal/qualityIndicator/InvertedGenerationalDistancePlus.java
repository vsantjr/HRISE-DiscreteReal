//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
package jmetal.qualityIndicator;

import java.io.FileNotFoundException;
import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 * This class implements the inverted generational distance metric plust (IGD+)
 * Reference: Ishibuchi et al 2015, "A Study on Performance Evaluation Ability
 * of a Modified Inverted Generational Distance Indicator", GECCO 2015
 *
 * @author Wenwen Li <liwenwen111@gmail.com>
 */
public class InvertedGenerationalDistancePlus<S extends Solution<?>> {

    jmetal.qualityIndicator.util.MetricsUtilPlus utils_;
    boolean normalize = true;

    public InvertedGenerationalDistancePlus() {
        utils_ = new jmetal.qualityIndicator.util.MetricsUtilPlus();

    }

    public double IGDplusNoNormalization(double[][] front, double[][] referenceFront) {
        double sum = 0.0;
        for (int i = 0; i < referenceFront.length; i++) {
            sum += utils_.distanceToClosestPoint(referenceFront[i],
                    front);
        }

        // STEP 4. Divide the sum by the maximum number of points of the reference Pareto front
        return sum / referenceFront.length;
    }

    /**
     * Returns the inverted generational distance value for a given front
     *
     * @param front The front
     * @param referenceFront The reference(true) pareto front
     */
    public double invertedGenerationalDistancePlus(double[][] front, double[][] referenceFront) {
        double[][] normalizedFront;
        double[][] normalizedReferenceFront;

        if (normalize) {
            double[] maximumValue;
            double[] minimumValue;
            int numberOfObj = front[0].length;
            // STEP 1. Obtain the maximum and minimum values of the Pareto front
            maximumValue = utils_.getMaximumValues(referenceFront, numberOfObj);
            minimumValue = utils_.getMinimumValues(referenceFront, numberOfObj);

            // STEP 2. Get the normalized front and true Pareto fronts
            normalizedFront = utils_.getNormalizedFront(front, maximumValue, minimumValue);
            normalizedReferenceFront
                    = utils_.getNormalizedFront(referenceFront, maximumValue, minimumValue);
        } else {
            normalizedFront = front;
            normalizedReferenceFront = referenceFront;
        }

        // STEP 3. Sum the distances between each point of the reference Pareto front and the dominated
        // region of the front
        double sum = 0.0;
        for (int i = 0; i < normalizedReferenceFront.length; i++) {
            sum += utils_.distanceToClosestPoint(normalizedReferenceFront[i],
                    normalizedFront);
        }

        // STEP 4. Divide the sum by the maximum number of points of the reference Pareto front
        return sum / normalizedReferenceFront.length;
    }

}
