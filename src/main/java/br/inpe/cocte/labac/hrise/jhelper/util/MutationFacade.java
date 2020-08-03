package br.inpe.cocte.labac.hrise.jhelper.util;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import br.inpe.cocte.labac.hrise.jhelper.imp.mutation.BitFlipMuta;
import br.inpe.cocte.labac.hrise.jhelper.imp.mutation.IntegerPolynomialMuta;
import br.inpe.cocte.labac.hrise.jhelper.imp.mutation.NonUniformMuta;
import br.inpe.cocte.labac.hrise.jhelper.imp.mutation.NullMuta;
import br.inpe.cocte.labac.hrise.jhelper.imp.mutation.PermutationSwapMuta;
import br.inpe.cocte.labac.hrise.jhelper.imp.mutation.PolynomialMuta;
import br.inpe.cocte.labac.hrise.jhelper.imp.mutation.SimpleRandomMuta;
import br.inpe.cocte.labac.hrise.jhelper.imp.mutation.UniformMuta;

/**
 * This class contains methods to automatically generates a Mutation Operator.
 */
public class MutationFacade extends OperatorFacade {

  /**
   * Instantiates a new Mutation facade.
   *
   * @param maxValues the max values
   * @param solutionType the solution type
   */
  public MutationFacade(Map<String, Object> maxValues, String solutionType) {
    super(maxValues, solutionType);
    this.prefix = "muta_";
  }

  /**
   * Random op mutation facade.
   *
   * @param mta the mta
   * @param maxValues the max values
   * @param currentEva the current eva
   * @return the mutation facade
   */
  public static MutationFacade randomOp(MutationFacade mta, Map<String, Object> maxValues,
      int currentEva) {
    return MutationFacade.randomOp(mta, maxValues, currentEva, Integer.MIN_VALUE);
  }

  /**
   * Random op mutation facade.
   *
   * @param mta the mta
   * @param maxValues the max values
   * @param currentEval the current eval
   * @param typeForChoosing the type for choosing
   * @return the mutation facade
   */
  public static MutationFacade randomOp(MutationFacade mta, Map<String, Object> maxValues,
      int currentEval, int typeForChoosing) {
    Random rd = new SecureRandom();
    Map<String, Object> params = new HashMap<>();
    params.put("probability",
        OperatorFacade.generateDoubleValue((double) mta.getMaxValues("probability")));
    if ("Real".equals(mta.solutionType)) {
      if (typeForChoosing == Integer.MIN_VALUE) {
        typeForChoosing = rd.nextInt(5);
      }
      switch (typeForChoosing) {
        case 0:
          params.put("distributionIndex",
              OperatorFacade
                  .generateDoubleValue((double) mta.getMaxValues("distributionIndex")));
          mta.polynomialMutation(params); //probability distributionIndex
          break;
        case 1:
          params.put("perturbation",
              OperatorFacade.generateDoubleValue((double) mta.getMaxValues("perturbation")));
          mta.uniformMutation(params);//probability perturbation
          break;
        case 2:
          params.put("perturbation",
              OperatorFacade.generateDoubleValue((double) mta.getMaxValues("perturbation")));
          params.put("maxIterations", ((int) mta.getMaxValues("maxIterations")));
          params.put("currentIteration", currentEval);
          mta.nonUniformMutation(params); //probability perturbation maxIterations
          break;
        case 3:
          mta.nullMutation(params);
          break;
        case 4:
          mta.simpleRandom(params);
          break;
        default:
          params.put("distributionIndex",
              OperatorFacade
                  .generateDoubleValue((double) mta.getMaxValues("distributionIndex")));
          mta.polynomialMutation(params); //probability distributionIndex

      }
    } else if ("Permutation".equals(mta.solutionType)) {
      if (typeForChoosing == Integer.MIN_VALUE) {
        typeForChoosing = rd.nextInt(2);
      }
      switch (typeForChoosing) {
        case 0:
          mta.permutationSwapMutation(params); //probability
          break;
        case 1:
          mta.nullMutation(params);
          break;
        default:
          mta.permutationSwapMutation(params); //probability
          break;
      }
    } else if ("Binary".equals(mta.solutionType)) {
      if (typeForChoosing == Integer.MIN_VALUE) {
        typeForChoosing = rd.nextInt(2);
      }
      switch (typeForChoosing) {
        case 0:
          mta.bitFlipMutation(params); //probability
          break;
        case 1:
          mta.nullMutation(params);
          break;
        default:
          mta.bitFlipMutation(params); //probability
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

          mta.integerPolynomialMutation(params); //probability
          break;
        case 1:
          mta.nullMutation(params);
          break;
        default:
          params.put("distributionIndex",
              OperatorFacade
                  .generateDoubleValue((double) mta.getMaxValues("distributionIndex")));

          mta.integerPolynomialMutation(params); //probability
          break;
      }
    }
    mta.getOp().allocateParameters();
    return mta;
  }

  /**
   * Random op mutation facade.
   *
   * @param maxValues the max values
   * @param currentEval the current eval
   * @param solutionType the solution type
   * @return the mutation facade
   */
  public static MutationFacade randomOp(Map<String, Object> maxValues, int currentEval,
      String solutionType) {
    MutationFacade mta = new MutationFacade(maxValues, solutionType);
    return MutationFacade.randomOp(mta, maxValues, currentEval);
  }

  /**
   * Polynomial mutation.
   *
   * @param params the params
   */
  public void polynomialMutation(Map params) {
    this.op = new PolynomialMuta();
    this.op.setParameters(params);
  }

  /**
   * Bit flip mutation.
   *
   * @param params the params
   */
  public void bitFlipMutation(Map params) {
    this.op = new BitFlipMuta();
    this.op.setParameters(params);
  }

  /**
   * Non uniform mutation.
   *
   * @param params the params
   */
  public void nonUniformMutation(Map params) {
    this.op = new NonUniformMuta();
    this.op.setParameters(params);
  }

  /**
   * Uniform mutation.
   *
   * @param params the params
   */
  public void uniformMutation(Map params) {
    this.op = new UniformMuta();
    this.op.setParameters(params);
  }

  private void integerPolynomialMutation(Map<String, Object> params) {
    this.op = new IntegerPolynomialMuta();
    this.op.setParameters(params);
  }

  private void permutationSwapMutation(Map<String, Object> params) {
    this.op = new PermutationSwapMuta();
    this.op.setParameters(params);
  }

  /**
   * Simple random.
   *
   * @param params the params
   */
  public void simpleRandom(Map<String, Object> params) {
    this.op = new SimpleRandomMuta();
    this.op.setParameters(params);
  }

  /**
   * Null mutation.
   *
   * @param params the params
   */
  public void nullMutation(Map params) {
    this.op = new NullMuta();
    this.op.setParameters(params);
  }

  @Override
  public OperatorFacade cloneMySelf() {
    MutationFacade mf = new MutationFacade(maxValues, this.solutionType);
    Map<String, Object> params = this.getParams();
    Map<String, Object> dummy = new HashMap<>();
    dummy.put("probability", 0.0);
    if (this.op instanceof PolynomialMuta) {
      dummy.put("distributionIndex", 0.0);
      mf.polynomialMutation(dummy);
    } else if (this.op instanceof IntegerPolynomialMuta) {
      dummy.put("distributionIndex", 0.0);
      mf.integerPolynomialMutation(dummy);
    } else if (this.op instanceof NullMuta) {
      mf.nullMutation(dummy);
    } else if (this.op instanceof BitFlipMuta) {
      mf.bitFlipMutation(dummy);
    } else if (this.op instanceof PermutationSwapMuta) {
      mf.permutationSwapMutation(dummy);
    } else if (this.op instanceof SimpleRandomMuta) {
      mf.simpleRandom(dummy);
    } else if (this.op instanceof UniformMuta) {
      dummy.put("perturbation", 0.0);
      mf.uniformMutation(dummy);
    } else if (this.op instanceof NonUniformMuta) {
      dummy.put("perturbation", 0.0);
      dummy.put("maxIterations", ((int) this.getMaxValues("maxIterations")));
      dummy.put("currentIteration", 0);
      mf.nonUniformMutation(dummy);
    }
    mf.setParams(params);
    return mf;
  }
}
