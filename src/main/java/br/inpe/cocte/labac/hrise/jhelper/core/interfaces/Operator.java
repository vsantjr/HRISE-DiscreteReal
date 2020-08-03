package br.inpe.cocte.labac.hrise.jhelper.core.interfaces;

import java.util.Map;

/**
 * This interface contains generic archived meta-heuristic methods. This interface
 * was created to use polymorphism without changes jMetal algorithm.
 */
public interface Operator {

  /**
   * Sets a value in a attribute named as attributeName.
   *
   * @param attributeName attribute name.
   * @param value value.
   */
  void setParameter(String attributeName, Object value);

  /**
   * Returns a value in a attribute named as attributeName.
   *
   * @param attributeName attribute name.
   * @return Object value.
   */
  Object getParameter(String attributeName);

  /**
   * Returns the parameters HashMap.
   *
   * @return HashMap.
   */
  Map<String, Object> getParameters();

  /**
   * Sets the parameters Map.
   *
   * @param parameters (Map).
   */
  void setParameters(Map<String, Object> parameters);

  /**
   * Allocate HashMap parameters to class attributes.
   */
  void allocateParameters();
}
