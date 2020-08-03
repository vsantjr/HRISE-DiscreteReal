package br.inpe.cocte.labac.hrise.jhelper.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.solution.Solution;

/**
 * This is an abstract to define automatic LLH selection.
 */

/**
 * @param <S> jMetal needs.
 */
@SuppressWarnings("serial")
public abstract class OpSelector<S extends Solution<?>> implements Serializable {

  /**
   * Low-Level Heuristic set.
   */
  protected ArrayList<LowLevelHeuristic> llhs;

  /**
   * Default constructor.
   */
  public OpSelector() {
    this.llhs = new ArrayList<>();
  }

  /**
   * Get LowLevelHeuristic set.
   *
   * @return LowLevelHeuristic ArrayList.
   */
  public ArrayList<LowLevelHeuristic> getLlhs() {
    return llhs;
  }

  /**
   * Set LowLevelHeuristic set.
   *
   * @param llhs ArrayList.
   */
  public void setLlhs(ArrayList<LowLevelHeuristic> llhs) {
    this.llhs = llhs;
  }

  /**
   * Returns whether solutions are equals.
   *
   * @param a TaggedSolution
   * @param b TaggedSolution
   * @return if a and b are equals
   */
  public boolean equals(TaggedSolution a, TaggedSolution b) {
    for (int i = 0; i < a.getNumberOfVariables(); i++) {
      if (a.getTheVariableValue(i) != b.getTheVariableValue(i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Assign Algorithm and LowLevelHeuristic to a solution considering parents, it makes
   * offspring equals to parents assign the same LowLevelHeuristic of parents.
   *
   * @param parents of solution
   * @param targetsolution target solution
   * @param appliedAlgorithm Algorithm applied
   * @return Algorithm
   */
  public LowLevelHeuristic buildTagCrossover(List<S> parents, TaggedSolution targetsolution,
      LowLevelHeuristic appliedAlgorithm) {
    for (S parent : parents) {
      //deve garantir que todos pais sejam testados
      if (parent instanceof DoubleTaggedSolution && this
          .equals((DoubleTaggedSolution) parent, targetsolution)) {
        return ((DoubleTaggedSolution) parent).getAction();
      }
    }
    return appliedAlgorithm;
  }

  /**
   * Print Low-Level Heuristic set.
   */
  public void printSuite() {
    for (LowLevelHeuristic act : this.llhs) {
      System.out.println(act.toString());
    }
  }

  /**
   * Method to automatically selects a LowLevelHeuristic.
   *
   * @return the selected LowLevelHeuristic
   */
  public abstract LowLevelHeuristic select();
}
