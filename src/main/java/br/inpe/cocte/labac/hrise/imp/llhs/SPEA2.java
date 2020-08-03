package br.inpe.cocte.labac.hrise.imp.llhs;

import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.algorithm.multiobjective.spea2.util.EnvironmentalSelection;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.solutionattribute.impl.StrengthRawFitness;

import br.inpe.cocte.labac.hrise.jhelper.imp.algs.Spea2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Juan J. Durillo
 **/
@SuppressWarnings("serial")
public class SPEA2<S extends Solution<?>> extends AbstractGeneticAlgorithm<S, List<S>> {
  protected final int maxIterations;
  protected final SolutionListEvaluator<S> evaluator;
  protected int iterations;
  protected List<S> archive;
  protected final StrengthRawFitness<S> strenghtRawFitness = new StrengthRawFitness<S>();
  protected final EnvironmentalSelection<S> environmentalSelection;

  public SPEA2(Problem<S> problem, int maxIterations, int populationSize,
      CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
      SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
    super(problem);
    this.maxIterations = maxIterations;
    this.setMaxPopulationSize(populationSize);

    this.crossoverOperator = crossoverOperator;
    this.mutationOperator = mutationOperator;
    this.selectionOperator = selectionOperator;
    this.environmentalSelection = new EnvironmentalSelection<S>(populationSize);

    this.archive = new ArrayList<>(populationSize);

    this.evaluator = evaluator;
    
    //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: CONSTRUCTOR  @@@@");
  }

  @Override
  protected void initProgress() {
	  //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: INIT PROGRESS  @@@@");
    iterations = 1;
  }

  @Override
  protected void updateProgress() {
	  //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: UPDATE PROGRESS  @@@@");
    iterations++;
  }

  @Override
  protected boolean isStoppingConditionReached() {
	 // Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: IS STOPPING CONDITION REACHED  @@@@");
    return iterations >= maxIterations;
  }

  @Override
  protected List<S> evaluatePopulation(List<S> population) {
	  //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: EVALUATE POPULATION  @@@@");
    population = evaluator.evaluate(population, getProblem());
    return population;
  }

  @Override
  protected List<S> selection(List<S> population) {
	  //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: SELECTION  @@@@");
    List<S> union = new ArrayList<>(2*getMaxPopulationSize());
    union.addAll(archive);
    union.addAll(population);
    strenghtRawFitness.computeDensityEstimator(union);
    archive = environmentalSelection.execute(union);
    return archive;
  }

  @Override
  protected List<S> reproduction(List<S> population) {
	  //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: REPRODUCTION  @@@@");
    List<S> offSpringPopulation= new ArrayList<>(getMaxPopulationSize());

    while (offSpringPopulation.size() < getMaxPopulationSize()){
      List<S> parents = new ArrayList<>(2);
      S candidateFirstParent = selectionOperator.execute(population);
      parents.add(candidateFirstParent);
      S candidateSecondParent;
      candidateSecondParent = selectionOperator.execute(population);
      parents.add(candidateSecondParent);

      List<S> offspring = crossoverOperator.execute(parents);
      mutationOperator.execute(offspring.get(0));
      offSpringPopulation.add(offspring.get(0));
    }
    return offSpringPopulation;
  }

  @Override
  protected List<S> replacement(List<S> population,
      List<S> offspringPopulation) {
	  //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: REPLACEMENT  @@@@");
    return offspringPopulation;
  }

  @Override
  public List<S> getResult() {
	  //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: GET RESULT  @@@@");
    return archive;
  }

  @Override public String getName() {
	  //Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: GET NAME  @@@@");
    return "SPEA2" ;
  }

  @Override public String getDescription() {
	 // Logger.getLogger(SPEA2.class.getName()).log(Level.INFO, "@@@@ SPEA2 jM: GET DESCRIPTION  @@@@");
    return "Strength Pareto. Evolutionary Algorithm" ;
  }
}
