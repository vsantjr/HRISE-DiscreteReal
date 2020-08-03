package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.uma.jmetal.qualityindicator.impl.R2;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;

/**
 * This class extends Calculator class and perform an evaluation using R2
 * Indicator.
 */
public class RCalculator extends Calculator {

  /**
   * R2 indicator instance.
   */
  private R2 r2indicator;

  /**
   * Instantiates a new R calculator.
   *
   * @param numberOfObjectives the number of objectives
   * @param populationSize the population size
   * @param path the path
   * @throws FileNotFoundException the file not found exception
   * @throws IOException the io exception
   */
  public RCalculator(int numberOfObjectives, int populationSize, String path)
      throws FileNotFoundException, IOException {
    super(numberOfObjectives, path);
    String weightPath = "MOEAD_Weights/W" + numberOfObjectives + "D_"
        + populationSize + ".dat";
    this.r2indicator = new R2(weightPath, new ArrayFront(path));
    this.indicatorName = "R";
    this.lowerValuesAreBetter=true;
  }

  /**
   * Instantiates a new R calculator.
   *
   * @param numberOfObjectives the number of objectives
   * @param populationSize the population size
   * @param referenceFront the reference front
   * @throws IOException the io exception
   */
  public RCalculator(int numberOfObjectives, int populationSize, Front referenceFront)
      throws IOException {
    super(numberOfObjectives, referenceFront);
    String weightPath = "MOEAD_Weights/W" + numberOfObjectives + "D_"
        + populationSize + ".dat";
    this.r2indicator = new R2(weightPath, null);
    this.indicatorName = "R";
    this.lowerValuesAreBetter=true;
  }

  @Override
  public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
    double val = 0;
    if (maximumValues != null && minimumValues != null) {
      FrontNormalizer frontNormalizer = new FrontNormalizer(minimumValues, maximumValues);
      Front normalizedFront = frontNormalizer.normalize(front);
      List<PointSolution> normalizedPopulation = FrontUtils
          .convertFrontToSolutionList(normalizedFront);
      val = r2indicator.evaluate(normalizedPopulation);
    }
    return val;
  }
}
