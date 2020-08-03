package br.inpe.cocte.labac.hrise.jhelper.imp.crossover;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.crossover.BLXAlphaCrossover;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * The type Blx alpha crossover.
 * This class extends the jMetal crossover class and implements Operator
 * interface. This was         created to use polymorphism without changes jMetal crossover
 * class.
 */
@SuppressWarnings("serial")
public class BlxAlphaCrossover extends BLXAlphaCrossover implements Operator {

  /**
   * HashMap which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Instantiates a new Blx alpha crossover.
   *
   * @param crossoverProbability the crossover probability
   */
  public BlxAlphaCrossover(double crossoverProbability) {
    super(crossoverProbability);
    this.parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Blx alpha crossover.
   *
   * @param crossoverProbability the crossover probability
   * @param alpha the alpha
   */
  public BlxAlphaCrossover(double crossoverProbability, double alpha) {
    super(crossoverProbability, alpha);
    this.parameters = new HashMap<>();
  }

  /**
   * Default constructor.
   */
  public BlxAlphaCrossover() {
    super(0, 0);
    this.parameters = new HashMap<>();
  }

  @Override
  public void allocateParameters() {
    this.setAlpha((double) this.parameters.get("alpha"));
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
