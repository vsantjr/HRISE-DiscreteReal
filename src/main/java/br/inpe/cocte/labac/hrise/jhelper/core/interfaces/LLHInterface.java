package br.inpe.cocte.labac.hrise.jhelper.core.interfaces;

import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author vinicius
 */
public interface LLHInterface<S extends Solution<?>> {

    /**
     * Get max Evaluations.
     *
     * @return max number of evaluations.
     */
    int getMaxEvaluations();

    /**
     * Get current iteration (generation).
     *
     * @return current iteration.
     */
    int getIterations();

    /**
     * Set current iteration.
     *
     * @param iterations (current iteration).
     */
    void setIterations(int iterations);

    /**
     * Set Crossover operator.
     *
     * @param crossoverOperator crossover.
     */
    void setCrossoverOperator(CrossoverOperator<S> crossoverOperator);

    /**
     * Set Mutation operator.
     *
     * @param mutationOperator mutation.
     */
    void setMutationOperator(MutationOperator<S> mutationOperator);

    /**
     * This method initializes the algorithm, it have to generate the initial
     * population and perform all other initializations.
     */
    void initMetaheuristic();

    /**
     * This method initializes the algorithm, it have to generate the initial
     * population and perform all other initializations.
     *
     * @param pop
     */
    void initMetaheuristic(List<S> pop);
    
    void initMetaheuristic(List<S> pop, List<S> pop2);

    /*
   * Executes the meta-heuristic for one generation (using executeMethod),
   * selects solutions for the population (using updateMainPopulation) and
   * increment the iteration.
     */
    List<S> execute();
    
    
    /*
   * Executes the meta-heuristic for one generation (using executeMethod),
   * selects solutions for the population (using updateMainPopulation) and
   * increment the iteration.
     */
    List<S> execute(List<S> inputPop, int evaluations);
    
    

    /**
     * Executes the meta-heuristic for one generation.
     *
     * @return The generated population.
     */
    List<?> executeMethod();

    /**
     * Return the final population.
     *
     * @return final population.
     */
    List getResult();

    /**
     * Get the max population size.
     *
     * @return population size.
     */
    int getPopulationSize();

    /**
     * Sets max population size.
     *
     * @param populationSize the population size.
     */
    void setPopulationSize(int populationSize);

    /**
     * Executes all algorithms steps: initMetaheuristic and
     * generateNewPopulation while there are remaining iterations.
     */
    void run();

    /**
     * Get the max number of iterations.
     *
     * @return maxIterations. max iterations
     */
    int getMaxIterations();
    
    /**
     * Get the max number of iterations.
     *
     * @param maxIteration
     */
    void setMaxIterations(int maxIteration);


    /**
     * Returns the main population.
     *
     * @return population. population
     */
    List<S> getPopulation();

    /**
     * Updates the main population.
     *
     * @param pop population.
     */
    void setPopulation(List<S> pop);
    
    void generateNewPopulation();
    
    
    String getName();
}
