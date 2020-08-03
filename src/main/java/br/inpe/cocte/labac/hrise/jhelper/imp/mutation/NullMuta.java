package br.inpe.cocte.labac.hrise.jhelper.imp.mutation;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.mutation.NullMutation;
import org.uma.jmetal.solution.Solution;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * This class extends the jMetal mutation class and implements Operator interface. This was created
 * to use polymorphism without changes jMetal mutation class.
 */

/**
 * @param <S> necessary because of jMetal.
 */
@SuppressWarnings("serial")
public class NullMuta<S extends Solution<?>> extends NullMutation<S> implements
    Operator {

  /**
   * Map which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Default constructor.
   */
  public NullMuta() {
    parameters = new HashMap<>();
  }

  @Override
  public void allocateParameters() {

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
