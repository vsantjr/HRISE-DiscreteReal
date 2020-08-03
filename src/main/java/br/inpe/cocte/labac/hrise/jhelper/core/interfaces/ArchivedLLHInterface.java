package br.inpe.cocte.labac.hrise.jhelper.core.interfaces;

import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author vinicius
 */
public interface ArchivedLLHInterface<S extends Solution<?>> extends LLHInterface<S> {

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
