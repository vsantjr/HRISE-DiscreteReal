package br.inpe.cocte.labac.hrise.jhelper.imp.crossover;


import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * The type Differential evolution.
 * This class extends the jMetal crossover class and implements Operator
 * interface. This was         created to use polymorphism without changes jMetal crossover
 * class.
 */
@SuppressWarnings("serial")
public class DifferentialEvolution extends DifferentialEvolutionCrossover implements
    Operator {

  /**
   * HashMap which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Default constructor.
   */
  public DifferentialEvolution() {
    this.parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Differential evolution.
   *
   * @param cr the cr
   * @param f the f
   * @param variant the variant
   */
  public DifferentialEvolution(double cr, double f, String variant) {
    super(cr, f, variant);
    this.parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Differential evolution.
   *
   * @param cr the cr
   * @param f the f
   * @param k the k
   * @param variant the variant
   */
  public DifferentialEvolution(double cr, double f, double k, String variant) {
    super(cr, f, k, variant);
    this.parameters = new HashMap<>();
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

  @Override
  public void allocateParameters() {
    this.setCr((double) this.parameters.get("cr"));
    this.setF((double) this.parameters.get("f"));
    this.setK((double) this.parameters.get("k"));
  }
}
