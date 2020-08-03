package br.inpe.cocte.labac.hrise.jhelper.imp.algs;

import br.inpe.cocte.labac.hrise.imp.llhs.SPEA2;
import br.inpe.cocte.labac.hrise.jhelper.core.OpManager;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.ArchivedLLHInterface;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.spea2.util.EnvironmentalSelection;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

/**
 * This class extends the algorithm from jMetal and implements operations
 * necessary for algorithm transitions.
 */
/**
 * The type Spea 2.
 *
 * @param <S> jMetal need.
 */
@SuppressWarnings("serial")
public class Spea2RILA<S extends Solution<?>> extends SPEA2<S> implements
        ArchivedLLHInterface<S> {

    /**
     * Environmental Selection necessary because of constant population resize.
     */
    protected EnvironmentalSelection<S> myenvironmentalSelection;
    /**
     * Archive size.
     */
    protected int archiveSize;

    protected int maxIterations_;//the other is final

    protected OpManager selector;
    
    /**
     * Instantiates a new Spea 2.
     *
     * @param problem the problem
     * @param maxIterations the max iterations
     * @param populationSize the population size
     * @param crossoverOperator the crossover operator
     * @param mutationOperator the mutation operator
     * @param selectionOperator the selection operator
     * @param evaluator the evaluator
     * @param k
     */
    public Spea2RILA(Problem<S> problem, int maxIterations, int populationSize,
            CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
            SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
    	//int k) {
    	//super(problem, maxIterations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator, k);
        super(problem, maxIterations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator);
        archiveSize = populationSize;
        selector = new OpManager();
        selector.setCrossoverOperator(crossoverOperator);
        selector.setMutationOperator(mutationOperator);
        this.myenvironmentalSelection = new EnvironmentalSelection<>(populationSize);
        maxIterations_ = maxIterations;
    }

    /*Necessary because of myenvironmentalSelection resize*/
    @Override
    protected List<S> selection(List<S> population) {
        List<S> union = new ArrayList<>(2 * getMaxPopulationSize());
        union.addAll(archive);
        union.addAll(population);
        strenghtRawFitness.computeDensityEstimator(union);
        archive = myenvironmentalSelection.execute(union);
        return archive;
    }

    public List<S> updateMainPopulation(List<S> offspringPopulation) {
        population = replacement(population, offspringPopulation);
        selection(population);
        return archive;
    }

    public List<S> updateMainPopulation2(List<S> pop) {
        selection(pop);
        return archive;
    }

    //@Override
    //protected List<S> reproduction(List<S> population) {
      //  List<S> offspringPopulation = new ArrayList<>(getMaxPopulationSize());

        //while (offspringPopulation.size() < getMaxPopulationSize()) {
          //  List<S> parents = new ArrayList<>(2);
            //S candidateFirstParent = selectionOperator.execute(population);
            //parents.add(candidateFirstParent);
            //S candidateSecondParent;
            //candidateSecondParent = selectionOperator.execute(population);
            //parents.add(candidateSecondParent);

            //List<S> offspring = crossoverOperator.execute(parents);

            //S s = offspring.get(0);
            //mutationOperator.execute(s);
            //offspringPopulation.add(s);
       // }
        //return offspringPopulation;
    //}

    /**
     * Generate a new Environmental Selection with a given size.
     *
     * @param size the size
     */
    public void setEnvironmentalSelectionSize(int size) {
        this.myenvironmentalSelection = new EnvironmentalSelection<>(size);
    }

    @Override
    public List<S> executeMethod() {
        List<S> offspringPopulation = reproduction(archive);
        offspringPopulation = evaluatePopulation(offspringPopulation);
        return offspringPopulation;
    }

    @Override
    public void generateNewPopulation() {
        selection(population);
        List<S> offspringPopulation = this.executeMethod();
        population = replacement(population, offspringPopulation);
        updateProgress();
    }

    @Override
    public List<S> execute() {
        while (!isStoppingConditionReached()) {
            generateNewPopulation();
        }
        return archive;
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
        this.population = new ArrayList<>();
        setPopulation(createInitialPopulation());
        setPopulation(evaluatePopulation(getPopulation()));
        selection(population);
        initProgress();
    }

    @Override
    public void initMetaheuristic(List<S> pop) {
        initMetaheuristic(new ArrayList<>(), pop);
    }

    @Override
    public void initMetaheuristic(List<S> pop, List<S> off) {
        archive = pop;
        setPopulation(off);
        selection(population);
        initProgress();
    }

    @Override
    public int getPopulationSize() {
        return this.maxPopulationSize;
    }

    @Override
    public void setPopulationSize(int populationSize) {
        this.maxPopulationSize = populationSize;
    }

    @Override
    public int getMaxEvaluations() {
        return this.iterations * maxPopulationSize;
    }

    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    @Override
    public void run() {
        this.initMetaheuristic();
        this.execute();
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return iterations >= maxIterations_;
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
        return population;
    }

    @Override
    public void setPopulation(List<S> pop) {
        this.population = pop;
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
        population = new ArrayList<>();
        iterations = 0;
        maxIterations_ = evaluations / maxPopulationSize;
        return execute();
    }

    @Override
    public void setMaxIterations(int maxIteration) {
        maxIterations_ = maxIteration;
    }
    
    
    @Override
    public String getName() {
  	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET NAME  %%%%");
  	  return "Spea2";
    }
}
