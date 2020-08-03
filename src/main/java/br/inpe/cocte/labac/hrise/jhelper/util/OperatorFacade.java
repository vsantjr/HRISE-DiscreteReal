package br.inpe.cocte.labac.hrise.jhelper.util;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.Operator;

/**
 * This class contains methods to automatically generates an Operator.
 */
public abstract class OperatorFacade implements Serializable {

  /**
   * The Operator.
   */
  protected Operator op;
  /**
   * The type used in solutions {Real, binary, Permutation}.
   */
  protected String solutionType;
  /**
   * The Max values used in automatic operator generation.
   */
  protected Map<String, Object> maxValues;
  /**
   * The Prefix {cross or muta}.
   */
  protected String prefix;

  /**
   * Instantiates a new Operator facade.
   *
   * @param maxValues the max values
   * @param solutionType the solution type
   */
  public OperatorFacade(Map<String, Object> maxValues, String solutionType) {
    this.op = null;
    this.maxValues = maxValues;
    prefix = "";
    this.solutionType = solutionType;
  }

  /**
   * Generate double value using exponential distribution double.
   *
   * @param bound the bound
   * @return the double
   */
  public static double generateDoubleValue(double bound) {
    SecureRandom rand = new SecureRandom();
    double lambda = 1;
    if (rand.nextBoolean()) {
      lambda = 30;
    }
    double x = Math.log(1 - rand.nextDouble()) / -lambda;
    x = Math.min(1, x);
    return x * bound;
  }

  /**
   * Gets the operator.
   *
   * @return the operator
   */
  public Operator getOp() {
    return op;
  }

  /**
   * Sets the operator.
   *
   * @param op the operator
   */
  public void setOp(Operator op) {
    this.op = op;
  }

  /**
   * Finds inside the operator an attribute named as attributeName and return
   * it value.
   *
   * @param attributeName the attribute name
   * @return the attribute object
   */
  public Object getAttributeObject(String attributeName) {
    String rightAttributeName = attributeName.replace(prefix, "");
    if (this.op != null) {
      return this.op.getParameter(rightAttributeName);
    }
    return null;
  }

  /**
   * Finds inside the operator an attribute named as attributeName and update
   * it value.
   *
   * @param attributeName the attribute name
   * @param value the value
   */
  public void setAttributeObject(String attributeName, Object value) {
    String rightAttributeName = attributeName.replace(prefix, "");
    if (this.op != null) {
      this.op.setParameter(rightAttributeName, value);
      op.allocateParameters();
    }
  }

  /**
   * Verify whether there is an attribute named as attributeName inside the
   * operator, then calls setAttributeObject(attributeName, value).
   *
   * @param attributeName the attribute name
   * @param value the value
   */
  public void setAttributeObjectVerify(String attributeName, Object value) {
    String rightAttributeName = attributeName.replace(prefix, "");
    if (this.op.getParameter(rightAttributeName) != null) {
      this.setAttributeObject(rightAttributeName, value);
    }
  }

  /**
   * Return all operator attribute values as HashMap.
   *
   * @return the params
   */
  public Map<String, Object> getParams() {
    Map<String, Object> toReturn = new HashMap<>();
    for (Map.Entry<String, Object> entry : this.maxValues.entrySet()) {
      Object value = this.getAttributeObject(entry.getKey());
      if (value != null) {
        toReturn.put(entry.getKey(), value);
      }
    }
    return toReturn;
  }

  /**
   * Use params from the given HashMap to update operator attribute values.
   *
   * @param params the params
   */
  public void setParams(Map<String, Object> params) {
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      this.setAttributeObjectVerify(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Gets an specific max value.
   *
   * @param paramName the param name
   * @return the max values
   */
  protected Object getMaxValues(String paramName) {
    return this.maxValues.get(prefix + paramName);
  }

  /**
   * Clone my operator operator.
   *
   * @return the operator
   */
  public Operator cloneMyOperator() {
    OperatorFacade of = (OperatorFacade) this.cloneMySelf();
    return of.getOp();
  }

  /**
   * Create a clone of this object.
   *
   * @return clonned.
   */
  public abstract OperatorFacade cloneMySelf();

}
