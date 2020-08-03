package br.inpe.cocte.labac.hrise.jhelper.imp.algs;

import br.inpe.cocte.labac.hrise.imp.llhs.SPEA2;
import br.inpe.cocte.labac.hrise.jhelper.core.DoubleTaggedSolution;
import br.inpe.cocte.labac.hrise.jhelper.core.OpManager;
import br.inpe.cocte.labac.hrise.jhelper.core.TaggedSolution;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.ArchivedMetaheuristic;
import br.inpe.cocte.labac.hrise.util.SaveFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uma.jmetal.algorithm.multiobjective.spea2.util.EnvironmentalSelection;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;
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
public class Spea2<S extends Solution<?>> extends SPEA2<S> implements
    ArchivedMetaheuristic<S> {

  /**
   * Low-Level Heuristic Selector.
   */
  protected OpManager selector;
  /**
   * Environmental Selection necessary because of constant population resize.
   */
  protected EnvironmentalSelection<S> myenvironmentalSelection;
  /**
   * Archive size.
   */
  protected int archiveSize;

  
  protected boolean firstInit; // control if the initial population is randomly generated or else it is the previous iteration population
  
  protected List<S> previousPopulation; // in case the population should be considered it is the one of the previous iteration
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
   */
  public Spea2(Problem<S> problem, int maxIterations, int populationSize,
      CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
      SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator, boolean first, List<S> previousPop) {
    super(problem, maxIterations, populationSize, crossoverOperator, mutationOperator,
        selectionOperator, evaluator);
    archiveSize = populationSize;
    selector = new OpManager();
    selector.setCrossoverOperator(crossoverOperator);
    selector.setMutationOperator(mutationOperator);
    this.myenvironmentalSelection = new EnvironmentalSelection<>(populationSize);
     
    this.firstInit = first;
    this.previousPopulation = previousPop;
   // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% CONSTRUCTOR  %%%%");
  }

  /*Necessary because of myenvironmentalSelection resize*/
  @Override
  protected List<S> selection(List<S> population) {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SELECTION  %%%%");
    List<S> union = new ArrayList<>(2 * getMaxPopulationSize());
    union.addAll(archive);
    union.addAll(population);
    strenghtRawFitness.computeDensityEstimator(union);
    archive = myenvironmentalSelection.execute(union);
    return archive;
  }

  @Override
  public List<S> updateMainPopulation(List<S> offspringPopulation) {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% UPDATE MAIN POP  %%%%");
    population = replacement(population, offspringPopulation);
    selection(population);
    return archive;
  }

  /*@Override
  protected List<S> reproduction(List<S> population) {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% REPRODUCTION  %%%%");
    selector.selectOp();
    crossoverOperator = selector.getCrossoverOperator();
    mutationOperator = selector.getMutationOperator();
    List<S> offspringPopulation = new ArrayList<>(getMaxPopulationSize());

    while (offspringPopulation.size() < getMaxPopulationSize()) {
      List<S> parents = new ArrayList<>(2);
      S candidateFirstParent = selectionOperator.execute(population);
      parents.add(candidateFirstParent);
      S candidateSecondParent;
      candidateSecondParent = selectionOperator.execute(population);
      parents.add(candidateSecondParent);

      List<S> offspring = crossoverOperator.execute(parents);

      S s = offspring.get(0);
      mutationOperator.execute(s);
      TaggedSolution s2;
      if (problem instanceof AbstractDoubleProblem) {
        s2 = new DoubleTaggedSolution((DefaultDoubleSolution) s);
      } else {
        //s2 = new PermutationTaggedSolution((DefaultIntegerPermutationSolution) s);
    	  s2 = new DoubleTaggedSolution((DefaultDoubleSolution) s);
    	  Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "Useless now. Integer in the future! ");
      }
      selector.assignTag(parents, s2, this);
      offspringPopulation.add((S) s2);

      selector.selectOp();
      crossoverOperator = selector.getCrossoverOperator();
      mutationOperator = selector.getMutationOperator();
    }
    return offspringPopulation;
  }*/

  /**
   * Generate a new Environmental Selection with a given size.
   *
   * @param size the size
   */
  public void setEnvironmentalSelectionSize(int size) {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET ENVIRONMENTAL SELECTION SIZE  %%%%");
    this.myenvironmentalSelection = new EnvironmentalSelection<>(size);
  }

  @Override
  public List<S> executeMethod() {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% EXECUTE METHOD  %%%%");
    List<S> offspringPopulation = reproduction(archive);
    offspringPopulation = evaluatePopulation(offspringPopulation);
    return offspringPopulation;
  }

  @Override
  public void generateNewPopulation() {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GENERATE NEW POPULATION  %%%%");
    List<S> offspringPopulation = this.executeMethod();
    population = replacement(population, offspringPopulation);
    selection(population);
    updateProgress();
  }

  @Override
  public int getIterations() {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET ITERATIONS  %%%%");
    return this.iterations;
  }

  @Override
  public void setIterations(int iterations) {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET ITERATIONS  %%%%");
    this.iterations = iterations;
  }

  @Override
  public void setCrossoverOperator(CrossoverOperator crossoverOperator) {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET CROSS OPER  %%%%");
    this.crossoverOperator = crossoverOperator;
    selector.setCrossoverOperator(crossoverOperator);
  }

  @Override
  public void setMutationOperator(MutationOperator mutationOperator) {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET MUTA OPER  %%%%");
    this.mutationOperator = mutationOperator;
    selector.setMutationOperator(mutationOperator);
  }

  @Override
  public void initMetaheuristic(boolean first, List<S> previousPop) {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC  %%%%");
	  if (first) {
		 Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC -> TRUE %%%%");
		 this.population = new ArrayList<>();
         setPopulation(createInitialPopulation());
     } else {
    	Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC -> FALSE %%%%");
  	   //setPopulationSize(getPopulationSize());
  	    setPopulation(previousPop);
  	   //setArchiveSize(getArchiveSize());
  	    setArchive(previousPop);
  	    setEnvironmentalSelectionSize(previousPop.size());
     }
	  
	  // checking set Pop
	  //SaveFiles sf = new SaveFiles();
	  //sf.saveFunVar("FINI-SPEA2-POP" + first, this.getName() , problem, getPopulation());
	  //sf.saveFunVar("FINI-SPEA2-ARC" + first, this.getName() , problem, getArchive());
	  
    setPopulation(evaluatePopulation(getPopulation()));
    selection(population);
    initProgress();
  }

  @Override
  public List<S> getRealpop() {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET REAL POP  %%%%");
    return getArchive();
  }

  @Override
  public int getPopulationSize() {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET POPULATION SIZE  %%%%");
    return this.maxPopulationSize;
  }

  @Override
  public void setPopulationSize(int populationSize) {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET POPULATION SIZE  %%%%");
    this.maxPopulationSize = populationSize;
  }

  @Override
  public int getMaxEvaluations() {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET MAX EVALUATIONS  %%%%");
    return this.iterations * maxPopulationSize;
  }

  @Override
  public int getMaxIterations() {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET MAX ITERATIONS  %%%%");
    return maxIterations;
  }

  @Override
  public void run() {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% RUN  %%%%");
    this.initMetaheuristic(firstInit, previousPopulation);
    while (!isStoppingConditionReached()) {
      this.generateNewPopulation();
    }
  }

  @Override
  protected boolean isStoppingConditionReached() {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% IS STOPPING CONDITION REACHED  %%%%");
    return iterations >= maxIterations;
  }

  @Override
  public List<S> getArchive() {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET ARCHIVE  %%%%");
    return this.archive;
  }

  @Override
  public void setArchive(List<S> pop) {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET ARCHIVE  %%%%");
    this.archive = pop;
  }

  @Override
  public List<S> getPopulation() {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET POPULATION  %%%%");
    return population;
  }

  @Override
  public void setPopulation(List<S> pop) {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET POPULATION  %%%%");
    this.population = pop;
  }

  @Override
  public int getArchiveSize() {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET ARCHIVE SIZE  %%%%");
    return archiveSize;
  }

  @Override
  public void setArchiveSize(int archiveSize) {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET ARCHIVE SIZE  %%%%");
    this.archiveSize = archiveSize;
  }

  @Override
  public OpManager getSelector() {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET SELECTOR  %%%%");
    return selector;
  }

  @Override
  public void setSelector(OpManager selector) {
	 // Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% SET SELECTOR  %%%%");
    this.selector = selector;
  }
  
  @Override
  public String getName() {
	  //Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% GET NAME  %%%%");
	  return "Spea2H";
  }
  
  @Override
  public void initMetaheuristic() {
	  Logger.getLogger(Spea2.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC EMPTY  %%%%");
	  
  }
}
