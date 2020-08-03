package br.inpe.cocte.labac.hrise.jhelper.core.interfaces;

import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;

import br.inpe.cocte.labac.hrise.jhelper.core.OpManager;

/**
 * This interface contains generic meta-heuristic methods and methods to split
 * meta-heuristic steps. This interface was created to use polymorphism without changes jMetal
 * algorithm.
 */

/**
 * The interface Standard metaheuristic.
 *
 * @param <S> jMetal need.
 */
public interface StandardMetaheuristic<S extends Solution<?>> {

  /**
   * Get max Evaluations.
   *
   * @return max number of evaluations.
   */
  int getMaxEvaluations();

  /**
   * Get current iteration (generation).
   *
   * @return current iteration.
   */
  int getIterations();

  /**
   * Set current iteration.
   *
   * @param iterations (current iteration).
   */
  void setIterations(int iterations);

  /**
   * Set Crossover operator.
   *
   * @param crossoverOperator crossover.
   */
  void setCrossoverOperator(CrossoverOperator<S> crossoverOperator);

  /**
   * Set Mutation operator.
   *
   * @param mutationOperator mutation.
   */
  void setMutationOperator(MutationOperator<S> mutationOperator);

  /**
   * This method initializes the algorithm, it have to generate the initial
   * population and perform all other initializations.
   */
  void initMetaheuristic();

  /**
   * Executes the meta-heuristic for one generation (using executeMethod),
   * selects solutions for the population (using updateMainPopulation) and
   * increment the iteration.
   */
  void generateNewPopulation();

  /**
   * Executes the meta-heuristic for one generation.
   *
   * @return The generated population.
   */
  List<?> executeMethod();

  /**
   * Selects solutions to compose the main population.
   *
   * @param matingPopulation a list of solutions.
   * @return the selected population.
   */
  List<?> updateMainPopulation(List<S> matingPopulation);

  /**
   * Return the final population.
   *
   * @return final population.
   */
  List getResult();

  /**
   * THis method is recommended when is necessary to partially generate a
   * population, so these method returns a population limited by a
   * pre-stipulated size.
   *
   * @return population. realpop
   */
  List<S> getRealpop();

  /**
   * Get the max population size.
   *
   * @return population size.
   */
  int getPopulationSize();

  /**
   * Sets max population size.
   *
   * @param populationSize the population size.
   */
  void setPopulationSize(int populationSize);

  /**
   * Executes all algorithms steps: initMetaheuristic and generateNewPopulation while
   * there are remaining iterations.
   */
  void run();

  /**
   * Get the max number of iterations.
   *
   * @return maxIterations. max iterations
   */
  int getMaxIterations();

  /**
   * Returns the main population.
   *
   * @return population. population
   */
  List<S> getPopulation();

  /**
   * Updates the main population.
   *
   * @param pop population.
   */
  void setPopulation(List<S> pop);

  /**
   * Gets selector.
   *
   * @return the selector
   */
  OpManager getSelector();

  /**
   * Sets selector.
   *
   * @param selector the selector
   */
  void setSelector(OpManager selector);
  
  /**
   * Get the name of the LLH/MOEA
   * 
   * @return name. name
   *
   */
  
  String getName();
  
  /**
   * This method initializes the algorithm, it have to generate the initial
   * population and perform all other initializations.
   */
  void initMetaheuristic(boolean f, List<S> prevPop);
}
