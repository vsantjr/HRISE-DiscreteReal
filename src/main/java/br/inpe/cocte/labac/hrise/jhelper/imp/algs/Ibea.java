package br.inpe.cocte.labac.hrise.jhelper.imp.algs;

import br.inpe.cocte.labac.hrise.imp.llhs.IBEA;
import br.inpe.cocte.labac.hrise.jhelper.core.*;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.ArchivedMetaheuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.uma.jmetal.algorithm.multiobjective.ibea.IBEA;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;

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
public class Ibea<S extends Solution<?>> extends IBEA<S> implements
    ArchivedMetaheuristic<S> {

  /**
   * Low-Level Heuristic Selector.
   */
  protected OpManager selector;
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

  protected boolean firstInit; // control if the initial population is randomly generated or else it is the previous iteration population
  
  protected List<S> previousPopulation; // in case the population should be considered it is the one of the previous iteration
  
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
  public Ibea(Problem<S> problem, int populationSize, int archiveSize, int maxEvaluations,
      SelectionOperator<List<S>, S> selectionOperator, CrossoverOperator<S> crossoverOperator,
      MutationOperator<S> mutationOperator, boolean first, List<S> previousPop) {
    super(problem, populationSize, archiveSize, maxEvaluations, selectionOperator,
        crossoverOperator, mutationOperator);
    selector = new OpManager();
    selector.setCrossoverOperator(crossoverOperator);
    selector.setMutationOperator(mutationOperator);
    maxIterations = maxEvaluations / populationSize;
    this.firstInit = first;
    this.previousPopulation = previousPop;
   // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% CONSTRUCTOR  %%%%");
  }

  /**
   * Reproduction list.
   *
   * @param population the population
   * @return the list
   */
  protected List<S> reproduction(List<S> population) {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "** NEW REPRODUCTION **");
	    offSpringSolutionSet = new ArrayList<>(getPopulationSize());
	    S parent1;
	    S parent2;
	    while (offSpringSolutionSet.size() < getPopulationSize()) {
	      selector.selectOp();
	      crossoverOperator = selector.getCrossoverOperator();
	      mutationOperator = selector.getMutationOperator();
	      
	      //this.selectionOperator.;
	      //crossoverOperator = selector.getCrossoverOperator();
	      //mutationOperator = selector.getMutationOperator();
	      int j = 0;
	      do {
	        j++;
	        parent1 = selectionOperator.execute(population);
	      } while (j < Ibea.TOURNAMENTS_ROUNDS);
	      int k = 0;
	      do {
	        k++;
	        parent2 = selectionOperator.execute(population);
	      } while (k < Ibea.TOURNAMENTS_ROUNDS);

	      List<S> parents = new ArrayList<>(2);
	      parents.add(parent1);
	      parents.add(parent2);

	      //make the crossover
	      List<S> offspring = crossoverOperator.execute(parents);
	      //S s = offspring.get(0);
	      mutationOperator.execute(offspring.get(0));
	      //TaggedSolution s2;
	      //if (problem instanceof AbstractDoubleProblem) {
	        //s2 = new DoubleTaggedSolution((DefaultDoubleSolution) s);
	      //} else {
	        //s2 = new PermutationTaggedSolution((DefaultIntegerPermutationSolution) s);
	      //}
	      problem.evaluate(offspring.get(0));
	      //selector.assignTag(parents, s2, this);
	      offSpringSolutionSet.add(offspring.get(0));
	      evaluations++;
	    }
	    return offSpringSolutionSet;
	  }
  
  /*protected List<S> reproduction(List<S> population) {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% REPRODUCTION  %%%%");
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
        parent1 = selectionOperator.execute(population);
      } while (j < Ibea.TOURNAMENTS_ROUNDS);
      int k = 0;
      do {
        k++;
        parent2 = selectionOperator.execute(population);
      } while (k < Ibea.TOURNAMENTS_ROUNDS);

      List<S> parents = new ArrayList<>(2);
      parents.add(parent1);
      parents.add(parent2);

      //make the crossover
      List<S> offspring = crossoverOperator.execute(parents);
      S s = offspring.get(0);
      mutationOperator.execute(s);
      TaggedSolution s2;
      if (problem instanceof AbstractDoubleProblem) {
        s2 = new DoubleTaggedSolution((DefaultDoubleSolution) s);
      } else {
        //s2 = new PermutationTaggedSolution((DefaultIntegerPermutationSolution) s);
    	  s2 = new DoubleTaggedSolution((DefaultDoubleSolution) s);
    	  Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "Useless now. Integer in the future! ");
      }
      problem.evaluate((S) s2);
      selector.assignTag(parents, s2, this);
      offSpringSolutionSet.add((S) s2);
      evaluations++;
    }
    return offSpringSolutionSet;
  }*/

  /**
   * Selection list.
   *
   * @param population the population
   * @return the list
   */
  protected List<S> selection(List<S> population) {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SELECTION  %%%%");
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
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% EXECUTE METHOD  %%%%");
    reproduction(archive);
    selection(offSpringSolutionSet);
    
    //try{System.in.read();}
    //catch(Exception e){}
    
    return offSpringSolutionSet;
  
    
  
  }

  @Override
  public void run() {
	  //Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% RUN  %%%%");
    this.initMetaheuristic(firstInit, previousPopulation);
    while (evaluations < maxEvaluations && iterations < maxIterations) {
      this.generateNewPopulation();
    }
   // try{System.in.read();}
    //catch(Exception e){}
  }

  /**
   * Evaluate population hypervolume contribution.
   *
   * @param list population.
   * @return the same population.
   */
  protected List<S> evaluatePopulation(List<S> list) {
	  //Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% EVALUATE POPULATION  %%%%");
    calculateFitness(list);
    return list;
  }

  @Override
  public void generateNewPopulation() {
	  //Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GENERATE NEW POPULATION  %%%%");
    this.executeMethod();
    iterations++;
    
   // try{System.in.read();}
    //catch(Exception e){}
  }

  @Override
    public List<S> updateMainPopulation(List<S> matingPopulation) {
	//  Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% UPDATE MAIN POPULATION  %%%%");
        archive=selection(matingPopulation);
        return archive;
    }

  @Override
  public int getMaxEvaluations() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET MAX EVALUATIONS  %%%%");
    return this.maxEvaluations;
  }

  @Override
  public int getIterations() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET ITERATIONS  %%%%");
    return this.iterations;
  }

  @Override
  public void setIterations(int iterations) {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SET ITERATIONS  %%%%");
    this.iterations = iterations;
  }

  @Override
  public void setCrossoverOperator(CrossoverOperator crossoverOperator) {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SET CROSS OP  %%%%");
    this.crossoverOperator = crossoverOperator;
    this.selector.setCrossoverOperator(crossoverOperator);
  }

  @Override
  public void setMutationOperator(MutationOperator mutationOperator) {
	  //Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SET MUTA OP  %%%%");
    this.mutationOperator = mutationOperator;
    this.selector.setMutationOperator(mutationOperator);
  }

  @Override
  public void initMetaheuristic(boolean first, List<S> previousPop) {
	  //Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC  %%%%");
    //Initialize the variables
	evaluations = 0;
	iterations = 0;
	 offSpringSolutionSet = new ArrayList<>(getPopulationSize());
	 archive = new ArrayList<>(archiveSize);
	if (first) {
	  Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC -> TRUE %%%%");		  
	  //offSpringSolutionSet = new ArrayList<>(getPopulationSize());
      //archive = new ArrayList<>(archiveSize);
    //evaluations = 0;
    //iterations = 0;
    //-> Create the initial solutionSet
      S newSolution;
      for (int i = 0; i < getPopulationSize(); i++) {
        newSolution = problem.createSolution();
        problem.evaluate(newSolution);
        evaluations++;
        offSpringSolutionSet.add(newSolution);
      }
    
    } else {
       Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC -> FALSE %%%%");
	   //setPopulationSize(getPopulationSize());
	   setPopulation(previousPop);
	   // a LINHA abaixo eh IMPORTANTE......................
	   setArchive(previousPop);
	   for (int ifal = 0; ifal < getPopulationSize(); ifal++) {
		   //System.out.println("False ibea  88 : " + getPopulationSize());
		   //try {
			//TimeUnit.SECONDS.sleep(1);
		//} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		   problem.evaluate(previousPop.get(ifal));
		   evaluations++;
		   offSpringSolutionSet.add(previousPop.get(ifal));
	   }
	   //setArchive(getArchive());
	   
   }
    selection(offSpringSolutionSet);
    iterations++;

   // try{System.in.read();}
    //catch(Exception e){}
  }

  @Override
  public List<S> getRealpop() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET REAL POP  %%%%");
    return getArchive();
  }

  @Override
  public int getPopulationSize() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET POP SIZE  %%%%");
    return this.populationSize;
  }

  @Override
  public void setPopulationSize(int populationSize) {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SET POP SIZE  %%%%");
    this.populationSize = populationSize;
  }

  @Override
  public int getMaxIterations() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET MAX ITERATIONS  %%%%");
    return maxIterations;
  }

  @Override
  public List<S> getArchive() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET ARCHIVE  %%%%");
    return this.archive;
  }

  @Override
  public void setArchive(List<S> pop) {
	//  Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SET ARCHIVE  %%%%");
    this.archive = pop;
  }

  @Override
  public List<S> getPopulation() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET POPULATION  %%%%");
    return offSpringSolutionSet;
  }

  @Override
  public void setPopulation(List<S> pop) {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SET POPULATION  %%%%");
    this.offSpringSolutionSet = pop;
  }

  @Override
  public int getArchiveSize() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET ARCHIVE SIZE  %%%%");
    return archiveSize;
  }

  @Override
  public void setArchiveSize(int archiveSize) {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SET ARCHIVE SIZE  %%%%");
    this.archiveSize = archiveSize;
  }

  @Override
  public OpManager getSelector() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% OP GET SELECTOR  %%%%");
    return selector;
  }

  @Override
  public void setSelector(OpManager selector) {
	//  Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% SET SELECTOR  %%%%");
    this.selector = selector;
  }
  
  @Override
  public String getName() {
	 // Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% GET NAME  %%%%");
	  return "IbeaH";
  }
  
  @Override
  public void initMetaheuristic() {
	  Logger.getLogger(Ibea.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC EMPTY  %%%%");
	  
  }
}
