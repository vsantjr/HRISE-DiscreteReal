package br.inpe.cocte.labac.hrise.jhelper.imp.mutation;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * This class extends the jMetal mutation class and implements Operator interface. This was created
 * to use polymorphism without changes jMetal mutation class.
 */

/**
 * The type Permutation swap muta.
 *
 * @param <T> necessary because of jMetal
 */
@SuppressWarnings("serial")
public class PermutationSwapMuta<T> extends PermutationSwapMutation<T> implements
    Operator {

  /**
   * Map which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Instantiates a new Permutation swap muta.
   *
   * @param mutationProbability the mutation probability
   */
  public PermutationSwapMuta(double mutationProbability) {
    super(mutationProbability);
    parameters = new HashMap<>();
  }

  /**
   * Default constructor.
   */
  public PermutationSwapMuta() {
    super(0);
    parameters = new HashMap<>();
  }

  @Override
  public void allocateParameters() {
    this.setMutationProbability((double) this.parameters.get("probability"));
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
