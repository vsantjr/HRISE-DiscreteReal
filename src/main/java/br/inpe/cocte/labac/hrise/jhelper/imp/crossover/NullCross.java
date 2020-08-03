package br.inpe.cocte.labac.hrise.jhelper.imp.crossover;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.solution.Solution;

/**
 * This class extends the jMetal crossover class and implements Operator interface. This was
 * created to use polymorphism without changes jMetal crossover class.
 */

/**
 * @param <S> jMetal needs.
 */
@SuppressWarnings({"unchecked", "serial"})
public class NullCross<S extends Solution<?>> extends NullCrossover<S> implements
br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator {

  /**
   * HashMap which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Default constructor.
   */
  public NullCross() {
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

  }
}
