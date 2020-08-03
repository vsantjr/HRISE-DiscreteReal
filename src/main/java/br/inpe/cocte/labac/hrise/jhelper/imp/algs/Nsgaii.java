package br.inpe.cocte.labac.hrise.jhelper.imp.algs;

import br.inpe.cocte.labac.hrise.imp.llhs.NSGAII;
import br.inpe.cocte.labac.hrise.jhelper.core.*;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.StandardMetaheuristic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.Setter;

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
 * The type Nsgaii.
 *
 * @param <S> jMetal need.
 */
@SuppressWarnings("serial")
public class Nsgaii<S extends Solution<?>> extends NSGAII<S> implements
    StandardMetaheuristic<S> {

  /**
   * Low-Level Heuristic Selector.
   */
  protected OpManager selector;
  /**
   * Current iteration.
   */
  protected int iterations;
  /**
   * Max Number of iterations.
   */
  protected int maxIterations;
  
  protected @Getter @Setter List<S> offspringPopulation;

  protected boolean firstInit; // control if the initial population is randomly generated or else it is the previous iteration population
  
  protected List<S> previousPopulation; // in case the population should be considered it is the one of the previous iteration
  /**
   * Instantiates a new Nsgaii.
   *
   * @param problem the problem
   * @param maxEvaluations the max evaluations
   * @param populationSize the population size
   * @param crossoverOperator the crossover operator
   * @param mutationOperator the mutation operator
   * @param selectionOperator the selection operator
     * @param dominanceComparator
   * @param evaluator the evaluator
   */
  public Nsgaii(Problem<S> problem, int maxEvaluations, int populationSize, int auxMatingPoolSize, int auxOffspringPopulationSize,
      CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
      SelectionOperator<List<S>, S> selectionOperator, Comparator<S> dominanceComparator, SolutionListEvaluator<S> evaluator, boolean first, List<S> previousPop) {
    super(problem, maxEvaluations, populationSize,  auxMatingPoolSize,  auxOffspringPopulationSize, crossoverOperator, mutationOperator, selectionOperator, dominanceComparator, evaluator);
    selector = new OpManager();
    selector.setCrossoverOperator(crossoverOperator);
    selector.setMutationOperator(mutationOperator);
    maxIterations = maxEvaluations / populationSize;
    this.firstInit = first;
    this.previousPopulation = previousPop;
    //Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** CONSTRUCTOR **");
  }

 /* @Override
  protected List<S> reproduction(List<S> population) {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** REPRODUCTION **");
    selector.selectOp();
    crossoverOperator = selector.getCrossoverOperator();
    mutationOperator = selector.getMutationOperator();
    int numberOfParents = crossoverOperator.getNumberOfRequiredParents();

    //checkNumberOfParents(population, numberOfParents);//generates just one, no need
    offspringPopulation = new ArrayList<>();
    for (int i = 0; i < getMaxPopulationSize(); i++) {
      List<S> parents = new ArrayList<>(numberOfParents);
      SecureRandom rdn = new SecureRandom();
      for (int j = 0; j < numberOfParents; j++) {
        parents.add(population.get(rdn.nextInt(getMaxPopulationSize())));
      }

      List<S> offspring = crossoverOperator.execute(parents);
      S s = offspring.get(0);
      mutationOperator.execute(s);
      TaggedSolution s2;
      if (problem instanceof AbstractDoubleProblem) {
        s2 = new DoubleTaggedSolution((DefaultDoubleSolution) offspring.get(0));
      } else {
        //s2 = new PermutationTaggedSolution((DefaultIntegerPermutationSolution) offspring.get(0));
    	  s2 = new DoubleTaggedSolution((DefaultDoubleSolution) offspring.get(0));
    	  Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "Useless now. Integer in the future! ");
    	  
      }
      selector.assignTag(parents, s2, this);
      offspringPopulation.add((S) s2);

      selector.selectOp();
      crossoverOperator = selector.getCrossoverOperator();
      mutationOperator = selector.getMutationOperator();
      numberOfParents = crossoverOperator.getNumberOfRequiredParents();
      //checkNumberOfParents(population, numberOfParents);//generates just one, no need
    }
    return offspringPopulation;
  }*/

  @Override
  public void run() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** RUN INIT MH **");
    //this.initMetaheuristic();
	this.initMetaheuristic(firstInit, previousPopulation);
    while (!isStoppingConditionReached()) {
    	 //Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** RUN GENERATE NEW POPULATION **");
      this.generateNewPopulation();
    }
  }

  @Override
  protected boolean isStoppingConditionReached() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** IS STOPPING CONDITION REACHED **");
    return evaluations >= maxEvaluations && iterations >= maxIterations;
  }

  @Override
  public void generateNewPopulation() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** ITSELF GENERATE NEW POPULATION **");
    this.executeMethod();
    iterations++;
  }

  @Override
  public List<S> updateMainPopulation(List<S> matingPopulation) {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** UPDATE MAIN POP **");
    population = replacement(population, matingPopulation);
    return population;
  }

  @Override
  public List<S> executeMethod() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** EXECUTE METHOD **");
    List<S> matingPopulation = selection(population);
    offspringPopulation = reproduction(matingPopulation);
    offspringPopulation = evaluatePopulation(offspringPopulation);
    population = replacement(population, offspringPopulation);
    updateProgress();
    return population;
  }

  @Override
  public void initMetaheuristic(boolean first, List<S> previousPop) {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** ITSELF INIT MH **");
	  if (first) {
        this.population = new ArrayList<>();
        setPopulation(createInitialPopulation());
        Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC -> TRUE %%%%");
	  } else {
		  //this.population = previousPop;
		  setPopulation(previousPop);
		  Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC -> FALSE %%%%");
	  }	  
	     
    setPopulation(evaluatePopulation(getPopulation()));
    initProgress();
    iterations = 1;
  }

  @Override
  public void initMetaheuristic() {
	 Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** ITSELF INIT MH EMPTY **");
  }
  
  @Override
  public List<S> getRealpop() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** GET REAL POP **");
    return population;
  }

  @Override
  public int getPopulationSize() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** GET POP SIZE **");
    return maxPopulationSize;
  }

  @Override
  public void setPopulationSize(int populationSize) {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** SET POP SIZE **");
    maxPopulationSize = populationSize;
  }

  @Override
  public int getMaxEvaluations() {
	//  Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** GET MAX EVALUATIONS **");
    return maxEvaluations;
  }

  @Override
  public void setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** SET CROSSOVER OP **");
    this.crossoverOperator = crossoverOperator;
    selector.setCrossoverOperator(crossoverOperator);
  }

  @Override
  public void setMutationOperator(MutationOperator<S> mutationOperator) {
	//  Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** SET MUTATION OP **");
    this.mutationOperator = mutationOperator;
    selector.setMutationOperator(mutationOperator);
  }

  @Override
  public int getIterations() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** GET ITERATIONS **");
    return iterations;
  }

  @Override
  public void setIterations(int iterations) {
	//  Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** SET ITERATIONS **");
    this.iterations = iterations;
  }

  @Override
  public int getMaxIterations() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** GET MAX ITERATIONS **");
    return maxIterations;
  }

  @Override
  public List<S> getPopulation() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** GET POPULATION **");
    return population;
  }

  @Override
  public void setPopulation(List<S> population) {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** SET POPULATION **");
    this.population = population;
  }

  @Override
  public OpManager getSelector() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** GET SELECTOR **");
    return selector;
  }

  @Override
  public void setSelector(OpManager selector) {
	//  Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** SET SELECTOR **");
    this.selector = selector;
  }

  @Override
  public String getName() {
	 // Logger.getLogger(Nsgaii.class.getName()).log(Level.INFO, "** GET NAME **");
	  return "NsgaiiH";
  }
}
