package br.usp.poli.pcs.lti.moabhh.artifacts;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.DoubleTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.PermutationTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.TaggedSolution;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.ConstrainedProblem;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.Solution;

/**
 * The type Population artifact.
 *
 * @param <S> the type parameter
 */
@SuppressWarnings("serial")
public class PopulationArtifact<S extends Solution<?>> extends Artifact {

  private List<S> population;

  /**
   * Init.
   *
   * @param populationSize the population size
   */
  @OPERATION
  public void init(int populationSize) {
    this.population = new ArrayList();
  }

  /**
   * Init population.
   *
   * @param problem the problem
   * @param populationSize the population size
   */
  @OPERATION
  public void initPopulation(Problem problem, int populationSize) {
    // Create the initial solutionSet
    TaggedSolution newSolution;
    for (int i = 0; i < populationSize; i++) {
      if (problem instanceof AbstractDoubleProblem) {
        newSolution = new DoubleTaggedSolution((DoubleProblem) problem);
      } else {
        newSolution = new PermutationTaggedSolution((PermutationProblem) problem);
      }
      problem.evaluate((Solution) newSolution);
      if(problem instanceof ConstrainedProblem){
          ((ConstrainedProblem)problem).evaluateConstraints(newSolution);
      }
      population.add((S) newSolution);
    }
  }

  /**
   * Gets population.
   *
   * @param res the res
   */
  @OPERATION
  public void getPopulation(OpFeedbackParam<Object> res) {
    List<S> cpyPop = new ArrayList<>();
    cpyPop.addAll(this.population);
    res.set(cpyPop);
  }

  /**
   * Sets population.
   *
   * @param population the population
   */
  @OPERATION
  public void setPopulation(List<S> population) {
    this.population = population;
  }

}
