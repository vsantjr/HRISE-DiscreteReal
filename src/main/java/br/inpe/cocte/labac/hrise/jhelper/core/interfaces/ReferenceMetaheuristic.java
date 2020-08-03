package br.inpe.cocte.labac.hrise.jhelper.core.interfaces;

import java.util.List;

import org.uma.jmetal.solution.Solution;

/**
 * This interface contains methods used by meta-heuristics that uses reference
 * points. This interface was created to use polymorphism without changes jMetal algorithm.
 */

/**
 * @param <S> jMetal need.
 */
public interface ReferenceMetaheuristic<S extends Solution<?>> extends LLHInterface {

  /**
   * This method is used to update all reference points (ideal and nadir)
   * using solutions of the parameter population.
   * @param matingPopulation population to work.
   */
  void updatePoints(List<S> matingPopulation);

  /**
   * Returns the max size of the real population, it is used in getRealpop
   * method.
   *
   * @return max size of the real population.
   */
  int getRealPopMaxGeneration();

  /**
   * Updates the max size of the real population, it is used in getRealpop
   * method.
   *
   * @param realPopMaxGeneration the max size.
   */
  void setRealPopMaxGeneration(int realPopMaxGeneration);
}
