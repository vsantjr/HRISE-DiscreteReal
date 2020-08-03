package br.inpe.cocte.labac.hrise.jhelper.imp.mutation;

import java.util.HashMap;
import java.util.Map;

import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.util.RepairDoubleSolution;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * The type Polynomial muta.
 * This class extends the jMetal mutation class and implements Operator
 * interface. This was created to use polymorphism without changes jMetal mutation class.
 */
@SuppressWarnings("serial")
public class PolynomialMuta extends PolynomialMutation implements Operator {

  /**
   * Map which contains all operator parameters.
   */
  protected Map<String, Object> parameters;

  /**
   * Default constructor.
   */
  public PolynomialMuta() {
    parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Polynomial muta.
   *
   * @param problem the problem
   * @param distributionIndex operator distribution index
   */
  public PolynomialMuta(DoubleProblem problem, double distributionIndex) {
    super(problem, distributionIndex);
    parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Polynomial muta.
   *
   * @param mutationProbability the mutation probability
   * @param distributionIndex the distribution index
   */
  public PolynomialMuta(double mutationProbability, double distributionIndex) {
    super(mutationProbability, distributionIndex);
    parameters = new HashMap<>();
  }

  /**
   * Instantiates a new Polynomial muta.
   *
   * @param mutationProbability the mutation probability
   * @param distributionIndex the distribution index
   * @param solutionRepair the solution repair
   */
  public PolynomialMuta(double mutationProbability, double distributionIndex,
      RepairDoubleSolution solutionRepair) {
    super(mutationProbability, distributionIndex, solutionRepair);
    parameters = new HashMap<>();
  }

  @Override
  public void allocateParameters() {
    this.setDistributionIndex((double) this.parameters.get("distributionIndex"));
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
