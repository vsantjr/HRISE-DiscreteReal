package br.inpe.cocte.labac.hrise.jhelper.core.interfaces;

import java.util.List;

import org.uma.jmetal.solution.Solution;

/**
 * This interface contains generic archived meta-heuristic methods. This interface was created to
 * use polymorphism without changes jMetal algorithm.
 */

/**
 * @param <S> jMetal need.
 */
public interface ArchivedMetaheuristic<S extends Solution<?>> extends StandardMetaheuristic<S> {

  /**
   * Returns the main archive.
   *
   * @return archive.
   */
  List<S> getArchive();

  /**
   * Updates the main archive.
   *
   * @param pop archive.
   */
  void setArchive(List<S> pop);

  /**
   * Get the max archive size.
   *
   * @return archive size.
   */
  int getArchiveSize();

  /**
   * Sets max archive size.
   *
   * @param size size.
   */
  void setArchiveSize(int size);
}
