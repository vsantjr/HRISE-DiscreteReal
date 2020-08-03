package br.inpe.cocte.labac.hrise.jhelper.util;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;
import br.inpe.cocte.labac.hrise.jhelper.imp.crossover.BlxAlphaCrossover;
import br.inpe.cocte.labac.hrise.jhelper.imp.crossover.HuxCrossover;
import br.inpe.cocte.labac.hrise.jhelper.imp.crossover.IntegerSbxCrossover;
import br.inpe.cocte.labac.hrise.jhelper.imp.crossover.NullCross;
import br.inpe.cocte.labac.hrise.jhelper.imp.crossover.OnePointCrossover;
import br.inpe.cocte.labac.hrise.jhelper.imp.crossover.PmxCrossover;
import br.inpe.cocte.labac.hrise.jhelper.imp.crossover.SbxCrossover;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class contains methods to automatically generates a Crossover Operator.
 */
public class CrossoverFacade extends OperatorFacade {

  /**
   * Instantiates a new Crossover facade.
   *
   * @param maxValues the max values
   * @param solutionType the solution type
   */
  public CrossoverFacade(Map<String, Object> maxValues, String solutionType) {
    super(maxValues, solutionType);
    this.prefix = "cross_";
  }

  /**
   * Random op crossover facade.
   *
   * @param mta the mta
   * @param maxValues the max values
   * @return the crossover facade
   */
  public static CrossoverFacade randomOp(CrossoverFacade mta, Map<String, Object> maxValues) {
    return CrossoverFacade.randomOp(mta, maxValues, Integer.MIN_VALUE);
  }

  /**
   * Random generates a crossover operator.
   *
   * @param mta the mta
   * @param maxValues the max values
   * @param typeForChoosing the type for choosing
   * @return the crossover facade
   */
  public static CrossoverFacade randomOp(CrossoverFacade mta, Map<String, Object> maxValues,
      int typeForChoosing) {
    Random rd = new SecureRandom();
    HashMap<String, Object> params = new HashMap<>();
    params.put("probability",
        OperatorFacade.generateDoubleValue((double) mta.getMaxValues("probability")));
    if ("Real".equals(mta.solutionType)) {
      if (typeForChoosing == Integer.MIN_VALUE) {
        typeForChoosing = rd.nextInt(2);
      }
      switch (typeForChoosing) {
        case 0:
          params.put("distributionIndex",
              OperatorFacade
                  .generateDoubleValue((double) mta.getMaxValues("distributionIndex")));
          mta.sbxCrossover(params); //probability distributionIndex
          break;
        case 1:
          params
              .put("alpha",
                  OperatorFacade.generateDoubleValue((double) mta.getMaxValues("alpha")));
          mta.blxAlphaCrossover(params); //probability alpha
          break;
        case 2:
          mta.nullCrossover(params);
          break;
        default:
          params.put("distributionIndex",
              OperatorFacade
                  .generateDoubleValue((double) mta.getMaxValues("distributionIndex")));
          mta.sbxCrossover(params); //probability distributionIndex
          break;
      }
    } else if ("Binary".equals(mta.solutionType)) {
      if (typeForChoosing == Integer.MIN_VALUE) {
        typeForChoosing = rd.nextInt(2);
      }
      switch (typeForChoosing) {
        case 0:
          mta.huxCrossover(params); //probability
          break;
        case 1:
          mta.singlePointCrossover(params); //probability
          break;
        case 2:
          mta.nullCrossover(params);
          break;
        default:
          mta.huxCrossover(params); //probability
          break;

      }
    } else if ("Permutation".equals(mta.solutionType)) {
      if (typeForChoosing == Integer.MIN_VALUE) {
        typeForChoosing = rd.nextInt(2);
      }
      switch (typeForChoosing) {
        case 0:
          mta.pmxCrossover(params); //probability
          break;
        case 1:
          mta.nullCrossover(params);
          break;
        default:
          mta.pmxCrossover(params); //probability
          break;
      }
    } else if ("Integer".equals(mta.solutionType)) {
      if (typeForChoosing == Integer.MIN_VALUE) {
        typeForChoosing = rd.nextInt(2);
      }
      switch (typeForChoosing) {

        case 0:
          params.put("distributionIndex",
              OperatorFacade
                  .generateDoubleValue((double) mta.getMaxValues("distributionIndex")));

          mta.integerSbxCrossover(params); //probability
          break;
        case 1:
          mta.nullCrossover(params);
          break;
        default:
          params.put("distributionIndex",
              OperatorFacade
                  .generateDoubleValue((double) mta.getMaxValues("distributionIndex")));

          mta.integerSbxCrossover(params); //probability
          break;
      }
    }
    mta.getOp().allocateParameters();
    return mta;
  }

  /**
   * Random op crossover facade.
   *
   * @param maxValues the max values
   * @param solutionType the solution type
   * @return the crossover facade
   */
  public static CrossoverFacade randomOp(HashMap<String, Object> maxValues, String solutionType) {
    CrossoverFacade mta = new CrossoverFacade(maxValues, solutionType);
    return CrossoverFacade.randomOp(mta, maxValues);
  }

  /**
   * Creates a Sbx crossover.
   *
   * @param params the params
   */
  public void sbxCrossover(Map params) {
    this.op = new SbxCrossover();
    this.op.setParameters(params);
  }

  /**
   * Creates a Pmx crossover.
   *
   * @param params the params
   */
  public void pmxCrossover(Map params) {
    this.op = new PmxCrossover();
    this.op.setParameters(params);
  }

  /**
   * Creates a Single point crossover.
   *
   * @param params the params
   */
  public void singlePointCrossover(Map params) {
    this.op = new OnePointCrossover();
    this.op.setParameters(params);
  }

  /**
   * Creates a Blx alpha crossover.
   *
   * @param params the params
   */
  public void blxAlphaCrossover(Map params) {
    this.op = new BlxAlphaCrossover();
    this.op.setParameters(params);
  }

  /**
   * Creates a huxCrossover crossover.
   *
   * @param params the params
   */
  public void huxCrossover(Map params) {
    this.op = new HuxCrossover();
    this.op.setParameters(params);
  }

  /**
   * Creates a Null crossover.
   *
   * @param params the params
   */
  public void nullCrossover(Map params) {
    this.op = new NullCross();
    this.op.setParameters(params);
  }

  /**
   * Integer sbx crossover.
   *
   * @param params the params
   */
  public void integerSbxCrossover(Map<String, Object> params) {
    this.op = new IntegerSbxCrossover();
    this.op.setParameters(params);
  }

  @Override
  public OperatorFacade cloneMySelf() {
    CrossoverFacade cf = new CrossoverFacade(maxValues, this.solutionType);
    Map<String, Object> params = this.getParams();
    Map<String, Object> dummy = new HashMap<>();
    dummy.put("probability", 0.0);
    if (op instanceof SbxCrossover) {
      dummy.put("distributionIndex", 0.0);
      cf.sbxCrossover(dummy);
    } else if (this.op instanceof IntegerSbxCrossover) {
      dummy.put("distributionIndex", 0.0);
      cf.integerSbxCrossover(dummy);
    } else if (this.op instanceof NullCross) {
      cf.nullCrossover(dummy);
    } else if (this.op instanceof HuxCrossover) {
      cf.huxCrossover(dummy);
    } else if (this.op instanceof PmxCrossover) {
      cf.pmxCrossover(dummy);
    } else if (this.op instanceof OnePointCrossover) {
      cf.singlePointCrossover(dummy);
    } else if (op instanceof BlxAlphaCrossover) {
      dummy.put("alpha", 0.0);
      cf.blxAlphaCrossover(dummy);
    }
    cf.setParams(params);
    return cf;
  }
}
