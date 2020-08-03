package br.inpe.cocte.labac.hrise.jhelper.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;
import br.inpe.cocte.labac.hrise.jhelper.util.CrossoverFacade;
import br.inpe.cocte.labac.hrise.jhelper.util.MutationFacade;

/**
 * The type Low level heuristic.
 * This abstract class is an implementation of a Low-Level Heuristic
 * (LowLevelHeuristic).
 */
public abstract class LowLevelHeuristicRILA implements Serializable {

  /**
   * The associated crossover. It uses CrossoverFacade class.
   */
  protected CrossoverFacade crossover;

  /**
   * The associated mutation. It uses MutationFacade class.
   */
  protected MutationFacade mutation;

  /**
   * The max Values for each operator parameter. Normally you should use 1.0
   * and 100.0, then random values would be bounded by them.
   */
  protected HashMap<String, Object> maxValues;

  /**
   * The type of solutions.
   */
  protected String solutionType;

  /**
   * Constructor with parameters
   *
   * @param crossover instance of CrossoverFacade.
   * @param mutation instance of MutationFacade.
   * @param maxValues HashMap containing max values for random attributes generation.
   * @param solutionType The type of solutions.
   */
  public LowLevelHeuristicRILA(CrossoverFacade crossover, MutationFacade mutation,
      HashMap<String, Object> maxValues,
      String solutionType) {
    this.crossover = crossover;
    this.mutation = mutation;
    this.maxValues = maxValues;
    this.solutionType = solutionType;
  }

  /**
   * Default Constructor.
   */
  public LowLevelHeuristicRILA() {
    this.crossover = null;
    this.mutation = null;
  }


  /**
   * Gets crossover.
   *
   * @return CrossoverFacade (crossover).
   */
  public CrossoverFacade getCrossover() {
    return crossover;
  }

  /**
   * Sets crossover.
   *
   * @param crossover sets CrossoverFacade.
   */
  public void setCrossover(CrossoverFacade crossover) {
    this.crossover = crossover;
  }

  /**
   * Gets mutation.
   *
   * @return MutationFacade (mutation).
   */
  public MutationFacade getMutation() {
    return mutation;
  }

  /**
   * Sets mutation.
   *
   * @param mutation sets MutationFacade.
   */
  public void setMutation(MutationFacade mutation) {
    this.mutation = mutation;
  }

  /**
   * Creates a HashMap, adds crossover and mutation operators, and converts
   * all crossover and mutation attributes to add to HashMap.
   *
   * @return HashMap containing operator and its parameters.
   */
  public Map<String, Object> allParametersToHash() {
    Map<String, Object> allparameterAction = new HashMap();
    allparameterAction.put("crossover", this.crossover.getOp());
    Map<String, Object> params = this.crossover.getParams();
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      allparameterAction.put(entry.getKey(), entry.getValue());
    }
    allparameterAction.put("mutation", this.mutation.getOp());
    params = this.mutation.getParams();
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      allparameterAction.put(entry.getKey(), entry.getValue());
    }
    return allparameterAction;
  }

  /**
   * Sets HashMap values to crossover and mutation attributes
   *
   * @param allparameterAction an HashMap containing operators with its parameters.
   */
  public void hashToParameter(HashMap<String, Object> allparameterAction) {
    this.crossover.setOp((Operator) allparameterAction.get("crossover"));
    this.mutation.setOp((Operator) allparameterAction.get("mutation"));
    this.crossover.setOp(this.crossover.cloneMyOperator());
    this.mutation.setOp(this.mutation.cloneMyOperator());
    for (Map.Entry<String, Object> entry : allparameterAction.entrySet()) {
      String keyname = entry.getKey();
      if (keyname.startsWith("cross_")) {
        this.crossover.setAttributeObjectVerify(keyname.replace("cross_", ""),
            allparameterAction.get(keyname));
      } else if (keyname.startsWith("muta_")) {
        this.mutation.setAttributeObjectVerify(keyname.replace("muta_", ""),
            allparameterAction.get(keyname));
      }
    }
  }

  /**
   * Randomly generates crossover and mutation attributes considering
   * solutionType and maxValues.
   */
  public void randomize() {
    randomize(0);
  }

  /**
   * Recreate crossover and mutation automatically.
   *
   * @param currentEval current evaluation
   */
  public void randomize(int currentEval) {
    this.crossover = CrossoverFacade.randomOp(this.maxValues, solutionType);
    this.mutation = MutationFacade.randomOp(this.maxValues, currentEval, solutionType);
  }

  @Override
  public String toString() {
    return "Action{" + "crossover=" + crossover.toString() + ", mutation=" + mutation.toString()
        + '}';
  }

  /**
   * Gets max values.
   *
   * @return the max values
   */
  public HashMap<String, Object> getMaxValues() {
    return maxValues;
  }

  /**
   * Sets max values.
   *
   * @param maxValues the max values
   */
  public void setMaxValues(HashMap<String, Object> maxValues) {
    this.maxValues = maxValues;
  }

  /**
   * Gets solution type.
   *
   * @return the solution type
   */
  public String getSolutionType() {
    return solutionType;
  }

  /**
   * Sets solution type.
   *
   * @param solutionType the solution type
   */
  public void setSolutionType(String solutionType) {
    this.solutionType = solutionType;
  }


}
