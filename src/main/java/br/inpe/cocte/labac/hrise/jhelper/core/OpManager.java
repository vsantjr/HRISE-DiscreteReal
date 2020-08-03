package br.inpe.cocte.labac.hrise.jhelper.core;

import java.io.Serializable;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;

/**
 * This class manages a LLH for meta-heuristics. It uses OpSelector to select a
 * LLH and sets to crossoverOperator and mutationOperator.
 */

/**
 * @param <S> jMetal needs.
 */
public class OpManager<S extends Solution<?>> implements Serializable {

  /**
   * an OpSelector used to automatically selects LLHs.
   */
  protected OpSelector selector;

  /**
   * the current selected LowLevelHeuristic.
   */
  protected LowLevelHeuristic llh;

  /**
   * the current crossover operator, obtained from LowLevelHeuristic.
   */
  protected CrossoverOperator<S> crossoverOperator;

  /**
   * the current mutation operator, obtained from LowLevelHeuristic.
   */
  protected MutationOperator<S> mutationOperator;

  /**
   * Default constructor.
   */
  public OpManager() {
    selector = null;
  }

  /**
   * Parameterized Constructor.
   *
   * @param selector automatic selector.
   */
  public OpManager(OpSelector selector) {
    this.selector = selector;
  }

  /**
   * Get automatic selector.
   *
   * @return OpSelector selector.
   */
  public OpSelector getSelector() {
    return selector;
  }

  /**
   * Set automatic selector.
   *
   * @param selector (automatic).
   */
  public void setSelector(OpSelector selector) {
    this.selector = selector;
  }

  /**
   * Selects a LowLevelHeuristic using the instantiated selector.
   *
   * @return the selected LowLevelHeuristic.
   */
  public LowLevelHeuristic selectOp() {
    if (this.selector != null) {
      llh = this.selector.select();
      crossoverOperator = (CrossoverOperator<S>) llh.getCrossover().getOp();
      mutationOperator = (MutationOperator<S>) llh.getMutation().getOp();
      return llh;
    }
    this.llh = null;
    return null;
  }

  /**
   * Discover Algorithm and LowLevelHeuristic to a solution if the selector is instantiated.
   *
   * @param parents of solution.
   * @param targetsolution target solution.
   * @param appliedAlgorithm Algorithm applied.
   */
  public void assignTag(List<S> parents, TaggedSolution targetsolution,
      Algorithm appliedAlgorithm) {
    if (this.selector != null) {
      targetsolution.setAction(this.selector.buildTagCrossover(parents, targetsolution, llh));
    }
    targetsolution.setAlgorithm(this.buildTagAlg(parents, targetsolution, appliedAlgorithm));
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
    protected Algorithm buildTagAlg(List<S> parents, TaggedSolution targetsolution,
            Algorithm appliedAlgorithm) {
        for (Solution<?> parent : parents) {
            //deve garantir que todos pais sejam testados
            if (parent instanceof TaggedSolution) {
                if (((TaggedSolution) parent).strVariables().equals(targetsolution.strVariables())) {
                	System.out.println("Tagged Solution Instance");
                    return ((TaggedSolution) parent).getAlgorithm();
                }
            } else {
                int qtd = 0;
                for (int i = 0; i < targetsolution.getNumberOfVariables(); i++) {
                    if (parent.getVariableValue(i) == targetsolution.getTheVariableValue(i)) {
                        qtd++;
                        System.out.println("Parent = Target Solution");
                    }
                }
               // if (qtd == targetsolution.getNumberOfVariables()) {
                	 //System.out.println("null");
                    //return null;
                //}
            }
        }
        System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$$$$$   Applied alg: " + appliedAlgorithm);
        return appliedAlgorithm;
    }

  /**
   * Gets Crossover operator.
   *
   * @return CrossoverOperator
   */
  public CrossoverOperator<S> getCrossoverOperator() {
    return crossoverOperator;
  }

  /**
   * Set Crossover operator.
   *
   * @param crossoverOperator crossover.
   */
  public void setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
    this.crossoverOperator = crossoverOperator;
  }

  /**
   * Get Mutation operator.
   *
   * @return MutationOperator mutation.
   */
  public MutationOperator<S> getMutationOperator() {
    return mutationOperator;
  }

  /**
   * Set Mutation operator.
   *
   * @param mutationOperator mutation.
   */
  public void setMutationOperator(MutationOperator<S> mutationOperator) {
    this.mutationOperator = mutationOperator;
  }
}
