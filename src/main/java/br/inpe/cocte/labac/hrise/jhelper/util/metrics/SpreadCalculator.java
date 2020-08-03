package br.inpe.cocte.labac.hrise.jhelper.util.metrics;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uma.jmetal.qualityindicator.impl.GeneralizedSpread;
import org.uma.jmetal.qualityindicator.impl.Spread;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.util.FrontNormalizer;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.PointSolution;

/**
 * This class extends Calculator class and perform an evaluation using the Spread
 * Indicator.
 */
public class SpreadCalculator extends Calculator {

  /**
   * Spread indicator instance for two objectives.
   */
  private final Spread spread2objectives;
  /**
   * Spread indicator instance for more than two objectives.
   */
  private final GeneralizedSpread spread4objectives;

  /**
   * Instantiates a new Spread handler.
   *
   * @param numberOfObjectives the number of objectives
   * @param path the path
   * @throws FileNotFoundException the file not found exception
   */
  public SpreadCalculator(int numberOfObjectives, String path) throws FileNotFoundException {
    super(numberOfObjectives, path);
    this.spread2objectives = new Spread(paretoTrueFront);
    this.spread4objectives = new GeneralizedSpread(paretoTrueFront);
    this.indicatorName = "Spread";
    this.lowerValuesAreBetter=spread2objectives.isTheLowerTheIndicatorValueTheBetter();
    //Logger.getLogger(SpreadCalculator.class.getName()).log(Level.INFO, "Constructor SPREAD STRING %%%%");
  }

  /**
   * Instantiates a new Spread calculator.
   *
   * @param numberOfObjectives the number of objectives
   * @param referenceFront the reference front
   */
  public SpreadCalculator(int numberOfObjectives, Front referenceFront) {
    super(numberOfObjectives, referenceFront);
    this.spread2objectives = new Spread(paretoTrueFront);
    this.spread4objectives = new GeneralizedSpread(paretoTrueFront);
    this.indicatorName = "Spread";
    this.lowerValuesAreBetter=spread2objectives.isTheLowerTheIndicatorValueTheBetter();
    //Logger.getLogger(SpreadCalculator.class.getName()).log(Level.INFO, "Constructor SPREAD FRONT %%%%");
  }

  @Override
  public double calculate(Front front, double[] maximumValues, double[] minimumValues) {
    double val = 1;
    if (maximumValues != null && minimumValues != null) {
      FrontNormalizer frontNormalizer = new FrontNormalizer(minimumValues, maximumValues);
      Front normalizedFront = frontNormalizer.normalize(front);
      List<PointSolution> normalizedPopulation = FrontUtils
          .convertFrontToSolutionList(normalizedFront);
      if (this.numberOfObjectives == 2) {
        val = this.spread2objectives.evaluate(normalizedPopulation);
        //Logger.getLogger(SpreadCalculator.class.getName()).log(Level.INFO, "SPREAD 2222!"); 
      } else {
        val = this.spread4objectives.evaluate(normalizedPopulation);
        //Logger.getLogger(SpreadCalculator.class.getName()).log(Level.INFO, "SPREAD 4444!"); 
      }
    }
    return val;
  }
}
