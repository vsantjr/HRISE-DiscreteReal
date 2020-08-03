package br.inpe.cocte.labac.hrise.jhelper.imp.algs;

import br.inpe.cocte.labac.hrise.imp.llhs.PAES;
import br.inpe.cocte.labac.hrise.jhelper.core.OpManager;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.StandardMetaheuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author vinicius
 */
public class Paes<S extends Solution<?>> extends PAES<S> implements StandardMetaheuristic<S> {

    /**
     * Low-Level Heuristic Selector.
     */
    protected OpManager selector;

    /**
     * Max Number of real population generation.
     */
    protected int realPopMaxGeneration;

    protected int maxIterations;
    protected int iterations;

    protected List<S> offspringPopulation;
    
    protected boolean firstInit; // control if the initial population is randomly generated or else it is the previous iteration population
    
    protected List<S> previousPopulation; // in case the population should be considered it is the one of the previous iteration

    public Paes(Problem<S> problem, int archiveSize, int maxEvaluations, int biSections, MutationOperator<S> mutationOperator, boolean first, List<S> previousPop) {
        super(problem, archiveSize, maxEvaluations, biSections, mutationOperator);
        selector = new OpManager();
        selector.setCrossoverOperator(null);
        selector.setMutationOperator(mutationOperator);
        maxIterations = maxEvaluations / archiveSize;
        iterations = 0;
        
        this.firstInit = first;
        this.previousPopulation = previousPop;
        
        //Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% CONSTRUCTOR  %%%%");
    }

    @Override
    public int getMaxEvaluations() {
    	   //Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET MAX EVALUATIONS  %%%%");
        return this.maxEvaluations;
    }

    @Override
    public int getIterations() {
    	   //Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET ITERATIONS  %%%%");
        return this.iterations;
    }

    @Override
    public OpManager getSelector() {
    	   //Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET SELECTOR  %%%%");
        return selector;
    }

    @Override
    public void setSelector(OpManager selector) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% SET SELECTOR  %%%%");
        this.selector = selector;
    }

    public int getRealPopMaxGeneration() {
    	   //Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET REAL POP MAX GENERATION  %%%%");
        return realPopMaxGeneration;
    }

    public void setRealPopMaxGeneration(int realPopMaxGeneration) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% SET REAL POP MAX GENERATION  %%%%");
        this.realPopMaxGeneration = realPopMaxGeneration;
    }

    @Override
    public int getMaxIterations() {
    	   //Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET MAX ITERATIONS  %%%%");
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% SET MAX ITERATIONS  %%%%");
        this.maxIterations = maxIterations;
    }

    @Override
    public int getPopulationSize() {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET POPULATION SIZE  %%%%");
        return archiveSize;
    }

    @Override
    public void setPopulationSize(int populationSize) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% SET POPULATION SIZE  %%%%");
        this.archiveSize = populationSize;
    }

    public int getEvaluations() {
    	 //  Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET EVALUATIONS  %%%%");
        return evaluations;
    }

    public void setEvaluations(int evaluations) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% SET EVALUATIONS  %%%%");
        this.evaluations = evaluations;
    }

    @Override
    public void setIterations(int iterations) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% SET ITERATIONS  %%%%");
        this.iterations = iterations;
    }

    @Override
    protected boolean isStoppingConditionReached() {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% IS STOPPING CONDITION REACHED  %%%%");
        return evaluations >= maxEvaluations && iterations >= maxIterations;
    }

    /*@SuppressWarnings("unchecked")
    @Override
    protected List<S> reproduction(List<S> population) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% REPRODUCTION  %%%%");
        S mutatedSolution = (S) population.get(0).copy();
        mutationOperator.execute(mutatedSolution);

        List<S> mutationSolutionList = new ArrayList<>(1);
        mutationSolutionList.add(mutatedSolution);
        return mutationSolutionList;
    }*/

    @SuppressWarnings("unchecked")
    @Override
    protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
    	 //  Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% REPLACEMENT  %%%%");
        S current = population.get(0);
        S mutatedSolution = offspringPopulation.get(0);

        int flag = comparator.compare(current, mutatedSolution);
        if (flag == 1) {
            current = (S) mutatedSolution.copy();
            archive.add(mutatedSolution);
        } else if (flag == 0) {
            if (archive.add(mutatedSolution)) {
                population.set(0, test(current, mutatedSolution, archive));
            }
        }

        population.set(0, current);
        return population;
    }

    @Override
    public void initMetaheuristic(boolean first, List<S> previousPop) {
    	   //Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC  %%%%");
    	if (first) {
          Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC  -> TRUE %%%%");  	
          population = createInitialPopulation();
        } else {
        	setPopulation(previousPop);
        	//setArchive(previousPop);
        	Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC -> FALSE %%%%");
        }
        	
    	
        population = evaluatePopulation(population);
        initProgress();
        iterations=1;
    }

    @Override
    public void generateNewPopulation() {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GENERATE NEW POPULATION  %%%%");
        this.executeMethod();
        population = replacement(population, offspringPopulation);
        updateProgress();
        iterations++;
    }

    @Override
    public List<?> executeMethod() {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% EXECUTE METHOD  %%%%");
        List<S> matingPopulation = selection(population);
        offspringPopulation = reproduction(matingPopulation);
        offspringPopulation = evaluatePopulation(offspringPopulation);
        return offspringPopulation;
    }

    @Override
    public void run() {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% RUN  %%%%");
        this.initMetaheuristic(firstInit, previousPopulation);
        
        //try{System.in.read();}
        //catch(Exception e){}
        
        while (!isStoppingConditionReached()) {
            this.generateNewPopulation();
        }
    }

    @Override
    public List<?> updateMainPopulation(List<S> list) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% UPDATE MAIN POP  %%%%");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<S> getRealpop() {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET REAL POP  %%%%");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCrossoverOperator(CrossoverOperator<S> co) {
    	 //  Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% SET CROSSOVER OPER  %%%%");

    }

    @Override
    public void setMutationOperator(MutationOperator<S> mo) {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% SET MUTATION OPER  %%%%");
        this.mutationOperator = mo;
    }
    
    @Override
    public String getName() {
    	  // Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% GET NAME  %%%%");
  	  return "PaesH";
    }
    
    @Override
    public void initMetaheuristic() {
    	   Logger.getLogger(Paes.class.getName()).log(Level.INFO, "%%%% INIT METAHEURISTIC EMPTY!  %%%%");
  	  
    }
}
