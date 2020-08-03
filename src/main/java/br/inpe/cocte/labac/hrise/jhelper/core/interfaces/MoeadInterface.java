package br.inpe.cocte.labac.hrise.jhelper.core.interfaces;

import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 * This interface contains methods used by MOEA/D meta-heuristics. This interface was created to
 * use polymorphism without changes jMetal algorithm.
 */

/**
 * @param <S> jMetal need.
 */
public interface MoeadInterface<S extends Solution<?>> extends ReferenceMetaheuristic<Solution<S>> {

  /**
   * Update resultPopulationSize, attribute from AbstractMOEAD class (JMetal).
   *
   * @param size resultPopulationSize.
   */
  void setResultPopulationSize(int size);
  
  void setSubProblemsToOptimize(List<Integer> subProblemsToOptimize);
}
