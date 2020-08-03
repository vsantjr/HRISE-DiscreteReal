package br.inpe.cocte.labac.hrise.jhelper.core;

import org.uma.jmetal.algorithm.Algorithm;

/**
 * This interface contains methods used to associate a Solution with an algorithm and LLH.
 */
public interface TaggedSolution {

  /**
   * Gets Action
   * @return LowLevelHeuristic the associated Low-Level Heuristic.
   */
  LowLevelHeuristic getAction();

  /**
   * Sets Action
   * @param action sets a Low-Level Heuristic to associate.
   */
  void setAction(LowLevelHeuristic action);

  /**
   * Gets the associated Algorithm
   * @return Algorithm.
   */
  Algorithm getAlgorithm();

  /**
   * Sets the associated Algorithm
   * @param algorithm.
   */
  void setAlgorithm(Algorithm algorithm);

  /**
   * Gets the solution number of variables
   * @return int.
   */
  int getNumberOfVariables();

  /**
   * Gets the Object containing the i-th variable value
   * @param i variable id
   * @return Object.
   */
  Object getTheVariableValue(int i);
  
  int getSubProblemId();
  void setSubProblemId(int id);
  
  void setNewSolution(boolean isnew);
  boolean isNewSolution();
  String strVariables();
}
