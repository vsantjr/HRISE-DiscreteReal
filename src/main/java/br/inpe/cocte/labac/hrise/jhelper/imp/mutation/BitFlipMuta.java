package br.inpe.cocte.labac.hrise.jhelper.imp.mutation;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * The type Bit flip muta.
 * This class extends the jMetal mutation class and implements Operator
 * interface. This was         created to use polymorphism without changes jMetal mutation
 * class.
 */
@SuppressWarnings("serial")
public class BitFlipMuta extends BitFlipMutation implements Operator {

  /**
   * Map which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Instantiates a new Bit flip muta.
   *
   * @param mutationProbability the mutation probability
   */
  public BitFlipMuta(double mutationProbability) {
    super(mutationProbability);
    parameters = new HashMap<>();
  }

  /**
   * Default constructor.
   */
  public BitFlipMuta() {
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
