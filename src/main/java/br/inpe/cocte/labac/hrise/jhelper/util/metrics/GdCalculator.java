package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.GenerationalDistance;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;

/**
 * This class extends Calculator class and perform an evaluation using GD Indicator.
 */
public class GdCalculator extends Calculator {

  /**
   * GD Indicator instance.
   */
  private final GenerationalDistance gd;

  /**
   * Instantiates a new Gd calculator.
   *
   * @param numberOfObjectives the number of objectives
   * @param path the path
   * @throws FileNotFoundException the file not found exception
   */
  public GdCalculator(int numberOfObjectives, String path) throws FileNotFoundException {
    super(numberOfObjectives, path);
    this.gd = new GenerationalDistance(paretoTrueFront);
    this.indicatorName = "GD";
    this.lowerValuesAreBetter=gd.isTheLowerTheIndicatorValueTheBetter();
  }

  /**
   * Instantiates a new Gd calculator.
   *
   * @param numberOfObjectives the number of objectives
   * @param referenceFront the reference front
   */
  public GdCalculator(int numberOfObjectives, Front referenceFront) {
    super(numberOfObjectives, referenceFront);
    this.gd = new GenerationalDistance(paretoTrueFront);
    this.indicatorName = "GD";
    this.lowerValuesAreBetter=gd.isTheLowerTheIndicatorValueTheBetter();
  }


  @Override
  public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
    double val = 1;
    if (maximumValues != null && minimumValues != null) {
      FrontNormalizer frontNormalizer = new FrontNormalizer(minimumValues, maximumValues);
      Front normalizedFront = frontNormalizer.normalize(front);
      List<PointSolution> normalizedPopulation = FrontUtils
          .convertFrontToSolutionList(normalizedFront);
      val = gd.evaluate(normalizedPopulation);
    }
    return val;
  }
}
