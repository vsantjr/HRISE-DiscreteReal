package br.inpe.cocte.labac.hrise.jhelper.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import br.inpe.cocte.labac.hrise.jhelper.util.ProblemFactory;

/**
 * The type Parameters for heuristics.
 */
public class ParametersforHeuristics extends DefaultParameters {

  /**
   * The De cr.
   */
  protected double deCr;
  /**
   * The De f.
   */
  protected double deF;
  /**
   * The De k.
   */
  protected double deK;
  /**
   * The Crossover probality.
   */
  protected double crossoverProbality;
  /**
   * The Alpha.
   */
  protected double alpha = 0.5;
  /**
   * The De variant.
   */
  protected String deVariant;
  /**
   * The Crossover name.
   */
  protected String crossoverName;
  /**
   * The Crossover distribution.
   */
  protected double crossoverDistribution;
  /**
   * The Mutation name.
   */
  protected String mutationName;
  /**
   * The Mutation probability.
   */
  protected double mutationProbability;
  /**
   * The Mutation pertubation.
   */
  protected double mutationPertubation;
  /**
   * The Mutation distribution.
   */
  protected double mutationDistribution;

  /**
   * The Number of variables.
   */
  protected int numberOfVariables;

  /**
   * Instantiates a new Parametersfor heuristics.
   *
   * @param configFileName the config file name
   * @param numberOfVariables the number of variables
   * @throws ConfigurationException the configuration exception
   */
  public ParametersforHeuristics(String configFileName, int numberOfVariables)
      throws ConfigurationException {
	  //Logger.getLogger(ParametersforHeuristics.class.getName()).log(Level.INFO, "config: " + configFileName + "; #dec var: " + numberOfVariables);
    this.numberOfVariables = numberOfVariables;
    readConfigsFromFile(configFileName);
  }

  /**
   * Instantiates a new Parametersfor heuristics.
   */
  public ParametersforHeuristics() {
  }

  @Override
  public void readConfigsFromFile(String configFileName) {
    Configuration configProblem = getFile(configFileName);
    if (configProblem != null) {
      crossoverName = configProblem.getString("crossoverName");
      if ("DifferentialEvolutionCrossover".equalsIgnoreCase(crossoverName)) {
        deCr = configProblem.getDouble("deCr");
        deF = configProblem.getDouble("deF");
        deK = configProblem.getDouble("deK");
        deVariant = configProblem.getString("deVariant");
      } else if ("blxAlphaCrossover".equalsIgnoreCase(crossoverName)) {
        alpha = configProblem.getDouble("alpha");
        crossoverProbality = configProblem.getDouble("crossoverProbality");
      } else if ("sbxCrossover".equalsIgnoreCase(crossoverName)) {
        crossoverProbality = configProblem.getDouble("crossoverProbality");
        crossoverDistribution = configProblem.getInt("crossoverDistribution");
      } else {
        crossoverProbality = configProblem.getDouble("crossoverProbality");
      }
      mutationName = configProblem.getString("mutationName");
      String mutationProbabilityFormula = configProblem.getString("mutationProbability");
      if (mutationProbabilityFormula.contains("/")) {
        String[] terms = mutationProbabilityFormula.split("/");
        mutationProbability = Double.valueOf(terms[0]) / numberOfVariables;
      } else {
        mutationProbability = configProblem.getDouble("mutationProbability");
      }
      if ("polynomialMutation".equalsIgnoreCase(mutationName)) {
        mutationDistribution = configProblem.getInt("mutationDistribution");
      } else if (mutationName.contains("uniformMutation")) {
        mutationPertubation = configProblem.getDouble("mutationPertubation");
      }
    }
  }

  /**
   * Gets de cr.
   *
   * @return the de cr
   */
  public double getDeCr() {
    return deCr;
  }

  /**
   * Sets de cr.
   *
   * @param deCr the de cr
   */
  public void setDeCr(double deCr) {
    this.deCr = deCr;
  }

  /**
   * Gets de f.
   *
   * @return the de f
   */
  public double getDeF() {
    return deF;
  }

  /**
   * Sets de f.
   *
   * @param deF the de f
   */
  public void setDeF(double deF) {
    this.deF = deF;
  }

  /**
   * Gets de k.
   *
   * @return the de k
   */
  public double getDeK() {
    return deK;
  }

  /**
   * Sets de k.
   *
   * @param deK the de k
   */
  public void setDeK(double deK) {
    this.deK = deK;
  }

  /**
   * Gets crossover probality.
   *
   * @return the crossover probality
   */
  public double getCrossoverProbality() {
    return crossoverProbality;
  }

  /**
   * Sets crossover probality.
   *
   * @param crossoverProbality the crossover probality
   */
  public void setCrossoverProbality(double crossoverProbality) {
    this.crossoverProbality = crossoverProbality;
  }

  /**
   * Gets alpha.
   *
   * @return the alpha
   */
  public double getAlpha() {
    return alpha;
  }

  /**
   * Sets alpha.
   *
   * @param alpha the alpha
   */
  public void setAlpha(double alpha) {
    this.alpha = alpha;
  }

  /**
   * Gets de variant.
   *
   * @return the de variant
   */
  public String getDeVariant() {
    return deVariant;
  }

  /**
   * Sets de variant.
   *
   * @param deVariant the de variant
   */
  public void setDeVariant(String deVariant) {
    this.deVariant = deVariant;
  }

  /**
   * Gets crossover name.
   *
   * @return the crossover name
   */
  public String getCrossoverName() {
    return crossoverName;
  }

  /**
   * Sets crossover name.
   *
   * @param crossoverName the crossover name
   */
  public void setCrossoverName(String crossoverName) {
    this.crossoverName = crossoverName;
  }

  /**
   * Gets crossover distribution.
   *
   * @return the crossover distribution
   */
  public double getCrossoverDistribution() {
    return crossoverDistribution;
  }

  /**
   * Sets crossover distribution.
   *
   * @param crossoverDistribution the crossover distribution
   */
  public void setCrossoverDistribution(double crossoverDistribution) {
    this.crossoverDistribution = crossoverDistribution;
  }

  /**
   * Gets mutation name.
   *
   * @return the mutation name
   */
  public String getMutationName() {
    return mutationName;
  }

  /**
   * Sets mutation name.
   *
   * @param mutationName the mutation name
   */
  public void setMutationName(String mutationName) {
    this.mutationName = mutationName;
  }

  /**
   * Gets mutation probability.
   *
   * @return the mutation probability
   */
  public double getMutationProbability() {
    return mutationProbability;
  }

  /**
   * Sets mutation probability.
   *
   * @param mutationProbability the mutation probability
   */
  public void setMutationProbability(double mutationProbability) {
    this.mutationProbability = mutationProbability;
  }

  /**
   * Gets mutation pertubation.
   *
   * @return the mutation pertubation
   */
  public double getMutationPertubation() {
    return mutationPertubation;
  }

  /**
   * Sets mutation pertubation.
   *
   * @param mutationPertubation the mutation pertubation
   */
  public void setMutationPertubation(double mutationPertubation) {
    this.mutationPertubation = mutationPertubation;
  }

  /**
   * Gets mutation distribution.
   *
   * @return the mutation distribution
   */
  public double getMutationDistribution() {
    return mutationDistribution;
  }

  /**
   * Sets mutation distribution.
   *
   * @param mutationDistribution the mutation distribution
   */
  public void setMutationDistribution(double mutationDistribution) {
    this.mutationDistribution = mutationDistribution;
  }
}
