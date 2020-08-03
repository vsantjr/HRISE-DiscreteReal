package br.inpe.cocte.labac.hrise.imp.llhs;

import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import br.inpe.cocte.labac.hrise.jhelper.imp.algs.Nsgaii;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class NSGAII<S extends Solution<?>> extends AbstractGeneticAlgorithm<S, List<S>> {
  protected final int maxEvaluations;

  protected final SolutionListEvaluator<S> evaluator;

  protected int evaluations;
  protected Comparator<S> dominanceComparator ;

  protected int matingPoolSize;
  protected int offspringPopulationSize ;

  /**
   * Constructor
   */
  public NSGAII(Problem<S> problem, int maxEvaluations, int populationSize,
                int matingPoolSize, int offspringPopulationSize,
      CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
      SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
    this(problem, maxEvaluations, populationSize, matingPoolSize, offspringPopulationSize,
            crossoverOperator, mutationOperator, selectionOperator, new DominanceComparator<S>(), evaluator);
    //Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: CONSTRUCTOR 1 @@");
  }
  /**
   * Constructor
   */
  public NSGAII(Problem<S> problem, int maxEvaluations, int populationSize,
                int matingPoolSize, int offspringPopulationSize,
                CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
      SelectionOperator<List<S>, S> selectionOperator, Comparator<S> dominanceComparator,
                SolutionListEvaluator<S> evaluator) {
    super(problem);
    this.maxEvaluations = maxEvaluations;
    setMaxPopulationSize(populationSize); ;

    this.crossoverOperator = crossoverOperator;
    this.mutationOperator = mutationOperator;
    this.selectionOperator = selectionOperator;

    this.evaluator = evaluator;
    this.dominanceComparator = dominanceComparator ;

    this.matingPoolSize = matingPoolSize ;
    this.offspringPopulationSize = offspringPopulationSize ;
   //Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: CONSTRUCTOR 2 @@");
  }

  @Override protected void initProgress() {
	 // Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: INIT PROGRESS @@");
    evaluations = getMaxPopulationSize();
  }

  @Override protected void updateProgress() {
	 // Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: UPDATE PROGRESS @@");
    evaluations += offspringPopulationSize ;
  }

  @Override protected boolean isStoppingConditionReached() {
	  //Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: IS STOPING CONDITION REACHED @@");
    return evaluations >= maxEvaluations;
  }

  @Override protected List<S> evaluatePopulation(List<S> population) {
	  //Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: EVALUATE POPULATION @@");
    population = evaluator.evaluate(population, getProblem());

    return population;
  }

  /**
   * This method iteratively applies a {@link SelectionOperator} to the population to fill the mating pool population.
   *
   * @param population
   * @return The mating pool population
   */
  @Override
  protected List<S> selection(List<S> population) {
	  //Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: SELECTION @@");
    List<S> matingPopulation = new ArrayList<>(population.size());
    for (int i = 0; i < matingPoolSize; i++) {
      S solution = selectionOperator.execute(population);
      matingPopulation.add(solution);
    }

    return matingPopulation;
  }

  /**
   * This methods iteratively applies a {@link CrossoverOperator} a  {@link MutationOperator} to the population to
   * create the offspring population. The population size must be divisible by the number of parents required
   * by the {@link CrossoverOperator}; this way, the needed parents are taken sequentially from the population.
   *
   * The number of solutions returned by the {@link CrossoverOperator} must be equal to the offspringPopulationSize
   * state variable
   *
   * @param matingPool
   * @return The new created offspring population
   */
  @Override
  protected List<S> reproduction(List<S> matingPool) {
	 // Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: REPRODUCTION @@");
    int numberOfParents = crossoverOperator.getNumberOfRequiredParents() ;

    checkNumberOfParents(matingPool, numberOfParents);

    List<S> offspringPopulation = new ArrayList<>(offspringPopulationSize);
    for (int i = 0; i < matingPool.size(); i += numberOfParents) {
      List<S> parents = new ArrayList<>(numberOfParents);
      for (int j = 0; j < numberOfParents; j++) {
        parents.add(population.get(i+j));
      }

      List<S> offspring = crossoverOperator.execute(parents);

      for(S s: offspring){
        mutationOperator.execute(s);
        offspringPopulation.add(s);
        if (offspringPopulation.size() >= offspringPopulationSize)
          break;
      }
    }
    return offspringPopulation;
  }

  @Override protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
	 // Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: REPLACEMENT @@");
    List<S> jointPopulation = new ArrayList<>();
    jointPopulation.addAll(population);
    jointPopulation.addAll(offspringPopulation);

    RankingAndCrowdingSelection<S> rankingAndCrowdingSelection ;
    rankingAndCrowdingSelection = new RankingAndCrowdingSelection<S>(getMaxPopulationSize(), dominanceComparator) ;

    return rankingAndCrowdingSelection.execute(jointPopulation) ;
  }

  @Override public List<S> getResult() {
	 // Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: GET RESULT @@");
    return SolutionListUtils.getNondominatedSolutions(getPopulation());
  }

  @Override public String getName() {
	 // Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: GET NAME @@");
    return "NSGAII" ;
  }

  @Override public String getDescription() {
	 // Logger.getLogger(NSGAII.class.getName()).log(Level.INFO, "@@ NSGAII jM: GET DESCRIPTION @@");
    return "Nondominated Sorting Genetic Algorithm version II" ;
  }
}
