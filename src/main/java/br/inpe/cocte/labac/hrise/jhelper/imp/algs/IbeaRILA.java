package br.inpe.cocte.labac.hrise.jhelper.imp.algs;

import br.inpe.cocte.labac.hrise.imp.llhs.IBEA;
import br.inpe.cocte.labac.hrise.jhelper.core.OpManager;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.ArchivedLLHInterface;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

/**
 * This class extends the algorithm from jMetal and implements operations
 * necessary for algorithm transitions.
 */
/**
 * The type Ibea.
 *
 * @param <S> jMetal need.
 */
@SuppressWarnings("serial")
public class IbeaRILA<S extends Solution<?>> extends IBEA<S> implements
        ArchivedLLHInterface<S> {

    /**
     * OffSpring population.
     */
    protected List<S> offSpringSolutionSet;
    /**
     * Current evaluations.
     */
    protected int evaluations;
    /**
     * Current iteration.
     */
    protected int iterations;
    /**
     * Max Number of iterations.
     */
    protected int maxIterations;

    protected OpManager selector;
    
    /**
     * Instantiates a new Ibea.
     *
     * @param problem the problem
     * @param populationSize the population size
     * @param archiveSize the archive size
     * @param maxEvaluations the max evaluations
     * @param selectionOperator the selection operator
     * @param crossoverOperator the crossover operator
     * @param mutationOperator the mutation operator
     */
    public IbeaRILA(Problem<S> problem, int populationSize, int archiveSize, int maxEvaluations,
            SelectionOperator<List<S>, S> selectionOperator, CrossoverOperator<S> crossoverOperator,
            MutationOperator<S> mutationOperator) {
        super(problem, populationSize, archiveSize, maxEvaluations, selectionOperator,
                crossoverOperator, mutationOperator);
        selector = new OpManager();
        selector.setCrossoverOperator(crossoverOperator);
        selector.setMutationOperator(mutationOperator);
        maxIterations = maxEvaluations / populationSize;
    }

    /**
     * Reproduction list.
     *
     * @param population the population
     * @return the list
     */
    protected List<S> reproduction(List<S> population) {
        // Create a new offspringPopulation
        //offSpringSolutionSet = new ArrayList<>(populationSize);
        offSpringSolutionSet = new ArrayList<>(getPopulationSize());
        S parent1;
        S parent2;
        while (offSpringSolutionSet.size() < getPopulationSize()) {
        	selector.selectOp();
  	        crossoverOperator = selector.getCrossoverOperator();
  	        mutationOperator = selector.getMutationOperator();
            int j = 0;
            do {
                j++;
                parent1 = selectionOperator.execute(population); // it was archive
            } while (j < IbeaRILA.TOURNAMENTS_ROUNDS);
            int k = 0;
            do {
                k++;
                parent2 = selectionOperator.execute(population); // it was archive
            } while (k < IbeaRILA.TOURNAMENTS_ROUNDS);

            List<S> parents = new ArrayList<>(2);
            parents.add(parent1);
            parents.add(parent2);

            //make the crossover
            List<S> offspring = crossoverOperator.execute(parents);
            mutationOperator.execute(offspring.get(0));
            problem.evaluate(offspring.get(0));
            //problem.evaluateConstraints(offSpring[0]);
            offSpringSolutionSet.add(offspring.get(0));
            evaluations++;
        }
        return offSpringSolutionSet;
    }

    /**
     * Selection list.
     *
     * @param population the population
     * @return the list
     */
    protected List<S> selection(List<S> population) {
        List<S> union = new ArrayList<>();
        union.addAll(population);
        union.addAll(archive);
        calculateFitness(union);
        archive = union;
        while (archive.size() > populationSize) {
            removeWorst(archive);
        }
        return archive;
    }

    @Override
    public List<S> executeMethod() {
        offSpringSolutionSet = reproduction(archive);
        selection(offSpringSolutionSet);
        return archive;
    }

    @Override
    public void run() {
        this.initMetaheuristic();
        this.execute();
    }

    /**
     * Evaluate population hypervolume contribution.
     *
     * @param list population.
     * @return the same population.
     */
    protected List<S> evaluatePopulation(List<S> list) {
        calculateFitness(list);
        return list;
    }

    protected boolean isStoppingConditionReached() {
        return iterations >= maxIterations || evaluations >= maxEvaluations;
    }

    @Override
    public void generateNewPopulation() {
        this.executeMethod();
        iterations++;
    }

    @Override
    public List<S> execute() {
        while (!isStoppingConditionReached()) {
            generateNewPopulation();
        }
        return archive;
    }

    @Override
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override
    public int getIterations() {
        return this.iterations;
    }

    @Override
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public void setCrossoverOperator(CrossoverOperator crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
    }

    @Override
    public void setMutationOperator(MutationOperator mutationOperator) {
        this.mutationOperator = mutationOperator;
    }

    @Override
    public void initMetaheuristic() {
        //Initialize the variables
        offSpringSolutionSet = new ArrayList<>(populationSize);
        archive = new ArrayList<>(archiveSize);
        evaluations = 0;
        iterations = 0;
        //-> Create the initial solutionSet
        S newSolution;
        for (int i = 0; i < populationSize; i++) {
            newSolution = problem.createSolution();
            problem.evaluate(newSolution);
            evaluations++;
            offSpringSolutionSet.add(newSolution);
        }
        selection(offSpringSolutionSet);
        iterations++;
    }

    @Override
    public void initMetaheuristic(List pop) {
        initMetaheuristic(new ArrayList(), pop);
    }

    @Override
    public void initMetaheuristic(List<S> pop, List<S> offspring) {
        archive = pop;
        offSpringSolutionSet = offspring;
        selection(offSpringSolutionSet);
        iterations = 1;
        evaluations = pop.size();
    }

    @Override
    public int getPopulationSize() {
        return this.populationSize;
    }

    @Override
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    @Override
    public List<S> getArchive() {
        return this.archive;
    }

    @Override
    public void setArchive(List<S> pop) {
        this.archive = pop;
    }

    @Override
    public List<S> getPopulation() {
        return offSpringSolutionSet;
    }

    @Override
    public void setPopulation(List<S> pop) {
        this.offSpringSolutionSet = pop;
    }

    @Override
    public int getArchiveSize() {
        return archiveSize;
    }

    @Override
    public void setArchiveSize(int archiveSize) {
        this.archiveSize = archiveSize;
    }

    @Override
    public List<S> execute(List<S> inputPop, int evaluations) {
        archive = inputPop;
        iterations = 0;
        maxIterations = evaluations / populationSize;
        offSpringSolutionSet = inputPop;
        return execute();
    }

    @Override
    public void setMaxIterations(int maxIteration) {
        maxIterations = maxIteration;
    }
    
    @Override
    public String getName() {
  	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET NAME  %%%%");
  	  return "Ibea";
    }
    
}
