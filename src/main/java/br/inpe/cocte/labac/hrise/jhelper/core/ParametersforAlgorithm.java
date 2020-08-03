package br.inpe.cocte.labac.hrise.jhelper.core;

import org.apache.commons.configuration2.Configuration;
import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD.FunctionType;

/**
 * The type Parameters for algorithm.
 */
public class ParametersforAlgorithm extends DefaultParameters {

  /**
   * The Population size.
   */
  protected int populationSize;

  /**
   * The Archive size.
   */
  protected int archiveSize;

  /**
   * The Max iteractions.
   */
  protected int maxIteractions;

  /**
   * The Algorithm name.
   */
  protected String algorithmName;

  /**
   * The Weights path.
   */
  protected String weightsPath;

  /**
   * The Neighbor size.
   */
  protected int neighborSize;

  /**
   * The Neighborhood selection probability.
   */
  protected double neighborhoodSelectionProbability;

  /**
   * The Maximum number of replaced solutions.
   */
  protected int maximumNumberOfReplacedSolutions;

  /**
   * The Moead function.
   */
  protected FunctionType moeadFunction;

  /**
   * The Sms offset.
   */
  protected double smsOffset;

  /**
   * Instantiates a new Parametersfor algorithm.
   *
   * @param configFileName the config file name
   */
  public ParametersforAlgorithm(String configFileName) {
    readConfigsFromFile(configFileName);
  }

  /**
   * Instantiates a new Parametersfor algorithm.
   */
  public ParametersforAlgorithm() {
  }


  @Override
  public void readConfigsFromFile(String configFileName) {
    Configuration configProblem = getFile(configFileName);
    if (configProblem != null) {
      algorithmName = configProblem.getString("algorithmName");
      populationSize = configProblem.getInt("populationSize");
      maxIteractions = configProblem.getInt("maxIteractions");
      if ("IBEA".equalsIgnoreCase(algorithmName) || "SPEA2".equalsIgnoreCase(algorithmName)) {
        archiveSize = configProblem.getInt("archiveSize");
      } else if (algorithmName.contains("Moead")) {
        neighborSize = configProblem.getInt("neighborSize");
        neighborhoodSelectionProbability = configProblem
            .getDouble("neighborhoodSelectionProbability");
        maximumNumberOfReplacedSolutions = configProblem.getInt("maximumNumberOfReplacedSolutions");
        weightsPath = configProblem.getString("weightsPath");
        String aux = configProblem.getString("moeadFunction");
        switch (aux) {
          case "TCHE":
            moeadFunction = FunctionType.TCHE;
            break;
          case "PBI":
            moeadFunction = FunctionType.PBI;
            break;
          case "AGG":
            moeadFunction = FunctionType.AGG;
            break;
          default:
            moeadFunction = FunctionType.TCHE;
            break;
        }
      } else if (algorithmName.contains("Mombi")) {
        weightsPath = configProblem.getString("weightsPath");
      } else if (algorithmName.equalsIgnoreCase("SmsEmoa")) {
        smsOffset = configProblem.getDouble("smsOffset");
      }
    }

  }

  /**
   * Gets population size.
   *
   * @return the population size
   */
  public int getPopulationSize() {
    return populationSize;
  }

  /**
   * Sets population size.
   *
   * @param populationSize the population size
   */
  public void setPopulationSize(int populationSize) {
    this.populationSize = populationSize;
  }

  /**
   * Gets archive size.
   *
   * @return the archive size
   */
  public int getArchiveSize() {
    return archiveSize;
  }

  /**
   * Sets archive size.
   *
   * @param archiveSize the archive size
   */
  public void setArchiveSize(int archiveSize) {
    this.archiveSize = archiveSize;
  }

  /**
   * Gets max iteractions.
   *
   * @return the max iteractions
   */
  public int getMaxIteractions() {
    return maxIteractions;
  }

  /**
   * Sets max iteractions.
   *
   * @param maxIteractions the max iteractions
   */
  public void setMaxIteractions(int maxIteractions) {
    this.maxIteractions = maxIteractions;
  }

  /**
   * Gets algorithm name.
   *
   * @return the algorithm name
   */
  public String getAlgorithmName() {
    return algorithmName;
  }

  /**
   * Sets algorithm name.
   *
   * @param algorithmName the algorithm name
   */
  public void setAlgorithmName(String algorithmName) {
    this.algorithmName = algorithmName;
  }

  /**
   * Gets neighbor size.
   *
   * @return the neighbor size
   */
  public int getNeighborSize() {
    return neighborSize;
  }

  /**
   * Sets neighbor size.
   *
   * @param neighborSize the neighbor size
   */
  public void setNeighborSize(int neighborSize) {
    this.neighborSize = neighborSize;
  }

  /**
   * Gets neighborhood selection probability.
   *
   * @return the neighborhood selection probability
   */
  public double getNeighborhoodSelectionProbability() {
    return neighborhoodSelectionProbability;
  }

  /**
   * Sets neighborhood selection probability.
   *
   * @param neighborhoodSelectionProbability the neighborhood selection probability
   */
  public void setNeighborhoodSelectionProbability(double neighborhoodSelectionProbability) {
    this.neighborhoodSelectionProbability = neighborhoodSelectionProbability;
  }

  /**
   * Gets maximum number of replaced solutions.
   *
   * @return the maximum number of replaced solutions
   */
  public int getMaximumNumberOfReplacedSolutions() {
    return maximumNumberOfReplacedSolutions;
  }

  /**
   * Sets maximum number of replaced solutions.
   *
   * @param maximumNumberOfReplacedSolutions the maximum number of replaced solutions
   */
  public void setMaximumNumberOfReplacedSolutions(int maximumNumberOfReplacedSolutions) {
    this.maximumNumberOfReplacedSolutions = maximumNumberOfReplacedSolutions;
  }

  /**
   * Gets weights path.
   *
   * @return the weights path
   */
  public String getWeightsPath() {
    return weightsPath;
  }

  /**
   * Sets weights path.
   *
   * @param weightsPath the weights path
   */
  public void setWeightsPath(String weightsPath) {
    this.weightsPath = weightsPath;
  }

  /**
   * Gets moead function.
   *
   * @return the moead function
   */
  public FunctionType getMoeadFunction() {
    return moeadFunction;
  }

  /**
   * Sets moead function.
   *
   * @param moeadFunction the moead function
   */
  public void setMoeadFunction(FunctionType moeadFunction) {
    this.moeadFunction = moeadFunction;
  }

  /**
   * Gets sms offset.
   *
   * @return the sms offset
   */
  public double getSmsOffset() {
    return smsOffset;
  }

  /**
   * Sets sms offset.
   *
   * @param smsOffset the sms offset
   */
  public void setSmsOffset(double smsOffset) {
    this.smsOffset = smsOffset;
  }

}
