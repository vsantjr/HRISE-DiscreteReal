package br.inpe.cocte.labac.hrise.jhelper.imp.mutation;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.mutation.NonUniformMutation;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * The type Non uniform muta.
 * This class extends the jMetal mutation class and implements Operator
 * interface. This was         created to use polymorphism without changes jMetal mutation
 * class.
 */
@SuppressWarnings("serial")
public class NonUniformMuta extends NonUniformMutation implements Operator {

  /**
   * Map which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Instantiates a new Non uniform muta.
   *
   * @param mutationProbability the mutation probability
   * @param perturbation the perturbation
   * @param maxIterations the max iterations
   */
  public NonUniformMuta(double mutationProbability, double perturbation,
      int maxIterations) {
    super(mutationProbability, perturbation, maxIterations);
    parameters = new HashMap<>();
  }

  /**
   * Default constructor.
   */
  public NonUniformMuta() {
    super(0, 0, 0);
    parameters = new HashMap<>();
  }

  @Override
  public void allocateParameters() {
    this.setPerturbation((double) this.parameters.get("perturbation"));
    this.setMutationProbability((double) this.parameters.get("probability"));
    this.setMaxIterations((int) this.parameters.get("maxIterations"));
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
