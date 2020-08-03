package br.inpe.cocte.labac.hrise.imp.llhs;

import org.uma.jmetal.algorithm.impl.AbstractEvolutionStrategy;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.archive.impl.AdaptiveGridArchive;
import org.uma.jmetal.util.comparator.DominanceComparator;

import br.inpe.cocte.labac.hrise.jhelper.imp.algs.Paes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Antonio J. Nebro
 * @author Juan J. Durillo
 * @version 1.0
 *
 * This class implements the PAES algorithm.
 */
@SuppressWarnings("serial")
public class PAES<S extends Solution<?>> extends AbstractEvolutionStrategy<S, List<S>> {
  protected int archiveSize;
  protected int maxEvaluations;
  protected int biSections;
  protected int evaluations;

  protected AdaptiveGridArchive<S> archive;
  protected Comparator<S> comparator;

  /**
   * Constructor
   */
  public PAES(Problem<S> problem, int archiveSize, int maxEvaluations, int biSections,
      MutationOperator<S> mutationOperator) {
    super(problem);
    setProblem(problem);
    this.archiveSize = archiveSize;
    this.maxEvaluations = maxEvaluations;
    this.biSections = biSections;
    this.mutationOperator = mutationOperator;

    archive = new AdaptiveGridArchive<S>(archiveSize, biSections, problem.getNumberOfObjectives());
    comparator = new DominanceComparator<S>();
    
    //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: CONSTRUCTOR @@@@");
  }

  /* Getters */
  public int getArchiveSize() {
	  //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: GET ARCHIVE SIZE @@@@");
    return archiveSize;
  }

  public int getMaxEvaluations() {
	 // Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: GET MAX EVALUATIONS @@@@");
    return maxEvaluations;
  }

  public int getBiSections() {
	  //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: GET BI SECTIONS @@@@");
    return biSections;
  }

  public MutationOperator<S> getMutationOperator() {
	  //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: GET MUTATION OPER @@@@");
    return mutationOperator;
  }

  @Override protected void initProgress() {
	  //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: INIT PROGRESS @@@@");
    evaluations = 0;
  }

  @Override protected void updateProgress() {
	 // Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: UPDATE PROGRESS @@@@");
    evaluations++;
  }

  @Override protected boolean isStoppingConditionReached() {
	  //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: IS STOPPING CONDITION REACHED @@@@");
    return evaluations >= maxEvaluations;
  }

  @Override protected List<S> createInitialPopulation() {
	  //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: CREATE INITIAL POPULATION @@@@");
    List<S> solutionList = new ArrayList<>(1);
    solutionList.add(getProblem().createSolution());
    return solutionList;
  }

  @Override protected List<S> evaluatePopulation(List<S> population) {
	 // Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: EVALUATE POPULATION @@@@");
    getProblem().evaluate(population.get(0));
    return population;
  }

  @Override protected List<S> selection(List<S> population) {
	//  Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: SELECTION @@@@");
    return population;
  }

  @SuppressWarnings("unchecked")
  @Override protected List<S> reproduction(List<S> population) {
	  //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: REPRODUCTION @@@@");
    S mutatedSolution = (S)population.get(0).copy();
    mutationOperator.execute(mutatedSolution);

    List<S> mutationSolutionList = new ArrayList<>(1);
    mutationSolutionList.add(mutatedSolution);
    return mutationSolutionList;
  }

  @SuppressWarnings("unchecked")
  @Override protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
	 // Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: REPLACEMENT @@@@");
    S current = population.get(0);
    S mutatedSolution = offspringPopulation.get(0);

    int flag = comparator.compare(current, mutatedSolution);
    if (flag == 1) {
      current = (S)mutatedSolution.copy();
      archive.add(mutatedSolution);
    } else if (flag == 0) {
      if (archive.add(mutatedSolution)) {
        population.set(0, test(current, mutatedSolution, archive));
      }
    }

    population.set(0, current);
    return population;
  }

  @Override public List<S> getResult() {
	//  Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: GET RESULT @@@@");
    return archive.getSolutionList();
  }

  /**
   * Tests two solutions to determine which one becomes be the guide of PAES
   * algorithm
   *
   * @param solution        The actual guide of PAES
   * @param mutatedSolution A candidate guide
   */
  @SuppressWarnings("unchecked")
  public S test(S solution, S mutatedSolution, AdaptiveGridArchive<S> archive) {
	  //Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: TEST @@@@");
    int originalLocation = archive.getGrid().location(solution);
    int mutatedLocation = archive.getGrid().location(mutatedSolution);

    if (originalLocation == -1) {
      return (S)mutatedSolution.copy();
    }

    if (mutatedLocation == -1) {
      return (S)solution.copy();
    }

    if (archive.getGrid().getLocationDensity(mutatedLocation) < archive.getGrid()
        .getLocationDensity(originalLocation)) {
      return (S)mutatedSolution.copy();
    }

    return (S)solution.copy();
  }

  @Override public String getName() {
	 // Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: GET NAME @@@@");
    return "PAES" ;
  }

  @Override public String getDescription() {
	 // Logger.getLogger(PAES.class.getName()).log(Level.INFO, "@@@@ PAES jM: GET DESCRIPTION @@@@");
    return "Pareto-Archived Evolution Strategy" ;
  }

}

