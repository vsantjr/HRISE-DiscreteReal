package br.inpe.cocte.labac.hrise.jhelper.imp.crossover;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.crossover.HUXCrossover;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * The type Hux crossover.
 * This class extends the jMetal crossover class and implements Operator
 * interface. This was         created to use polymorphism without changes jMetal crossover
 * class.
 */
@SuppressWarnings("serial")
public class HuxCrossover extends HUXCrossover implements
    Operator {

  /**
   * HashMap which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Instantiates a new Hux crossover.
   *
   * @param crossoverProbability the crossover probability
   */
  public HuxCrossover(double crossoverProbability) {
    super(crossoverProbability);
    this.parameters = new HashMap<>();
  }

  /**
   * Default constructor.
   */
  public HuxCrossover() {
    super(0);
    this.parameters = new HashMap<>();
  }

  @Override
  public void allocateParameters() {
    this.setCrossoverProbability((double) this.parameters.get("probability"));
  }

  @Override
  public void setParameter(String attributeName, Object value) {
    this.parameters.put(attributeName, value);
  }

  @Override
  public Object getParameter(String attributeName) {
    return this.parameters.getOrDefault(attributeName, null);
  }

  @Override
  public Map<String, Object> getParameters() {
    return parameters;
  }

  @Override
  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }
}
