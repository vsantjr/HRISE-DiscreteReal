package br.inpe.cocte.labac.hrise.jhelper.imp.algs;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.solutionattribute.Ranking;

import br.inpe.cocte.labac.hrise.jhelper.core.*;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.StandardMetaheuristic;

/**
 *
 * @author vinicius
 */
public class Gde3<S extends Solution<?>> extends GDE3 implements StandardMetaheuristic<S> {

    /**
     * Low-Level Heuristic Selector.
     */
    protected OpManager selector;
    protected int maxIterations;
    protected int iterations;
    protected int realSize;

    protected List<DoubleSolution> offspringPopulation;

    public Gde3(DoubleProblem problem, int populationSize, int maxEvaluations, DifferentialEvolutionSelection selection, DifferentialEvolutionCrossover crossover, SolutionListEvaluator<DoubleSolution> evaluator) {
        super(problem, populationSize, maxEvaluations, selection, crossover, evaluator);
        selector = new OpManager();
        selector.setCrossoverOperator(crossover);
        selector.setMutationOperator(null);
        maxIterations = maxEvaluations / populationSize;
        iterations = 0;
        realSize = populationSize;
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
    public OpManager getSelector() {
        return selector;
    }

    @Override
    public void setSelector(OpManager selector) {
        this.selector = selector;
    }

    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public int getPopulationSize() {
        return super.getMaxPopulationSize();
    }

    @Override
    public void setPopulationSize(int populationSize) {
        super.setMaxPopulationSize(populationSize);
    }

    public int getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(int evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return evaluations >= maxEvaluations && iterations >= maxIterations;
    }

    @Override
    public void setCrossoverOperator(CrossoverOperator<S> co) {
        this.crossoverOperator = (DifferentialEvolutionCrossover) co;
    }

    @Override
    public void setMutationOperator(MutationOperator<S> mo) {
        //nothing to do here
    }

    @Override
    public void initMetaheuristic() {
        population = createInitialPopulation();
        population = evaluatePopulation(population);
        initProgress();
    }

    @Override
    public void run() {
        this.initMetaheuristic();
        while (!isStoppingConditionReached()) {
            this.generateNewPopulation();
            population = replacement(population, offspringPopulation);
        }
    }

    @Override
    public void generateNewPopulation() {
        /*This is necessary because of GDE3 impossibility for generating solutions with less than 4 parent solutions. Just used in HHs*/
        Random rnd = new SecureRandom();
        while (population.size() < getMaxPopulationSize() || population.size() < 4) {
            population.add(population.get(rnd.nextInt(population.size())));
        }
        this.executeMethod();
        population = replacement(population, offspringPopulation);
        updateProgress();
        iterations++;
    }

    @Override
    public List<DoubleSolution> executeMethod() {
        List<DoubleSolution> matingPopulation = selection(population);
        offspringPopulation = reproduction(matingPopulation);
        offspringPopulation = evaluatePopulation(offspringPopulation);
        return offspringPopulation;
    }

    @Override
    public List getRealpop() {
        List<DoubleSolution> toReturn = new ArrayList<>();
        toReturn.addAll(population);
        toReturn.addAll(offspringPopulation);
        System.out.println(offspringPopulation.size() + " " + population.size() + " " + toReturn.size());
        return toReturn;
    }

    @Override
    public List getPopulation() {
        return population;
    }

    @Override
    public void setPopulation(List population) {
        this.population = population;
    }

    @Override
    public List updateMainPopulation(List list) {
        population = replacement(population, list);
        return population;
    }

    public List<DoubleSolution> getOffspringPopulation() {
        return offspringPopulation;
    }

    public void setOffspringPopulation(List<DoubleSolution> offspringPopulation) {
        this.offspringPopulation = offspringPopulation;
    }

    @Override
    protected List<DoubleSolution> reproduction(List<DoubleSolution> matingPopulation) {
        selector.selectOp();
        crossoverOperator = (DifferentialEvolutionCrossover) selector.getCrossoverOperator();
        offspringPopulation = new ArrayList<>();
        for (int i = 0; i < realSize && i < getPopulation().size(); i++) {
            crossoverOperator.setCurrentSolution((DoubleSolution) getPopulation().get(i));
            List<DoubleSolution> parents = new ArrayList<>(3);
            for (int j = 0; j < 3; j++) {
                parents.add(matingPopulation.get(0));
                matingPopulation.remove(0);
            }

            crossoverOperator.setCurrentSolution((DoubleSolution) getPopulation().get(i));
            List<DoubleSolution> children = crossoverOperator.execute(parents);
            TaggedSolution s2 = new DoubleTaggedSolution((DefaultDoubleSolution) children.get(0));
            selector.assignTag(parents, s2, this);
            offspringPopulation.add((DoubleSolution) s2);

            selector.selectOp();
            crossoverOperator = (DifferentialEvolutionCrossover) selector.getCrossoverOperator();
        }

        return offspringPopulation;
    }

    public int getRealSize() {
        return realSize;
    }

    public void setRealSize(int realSize) {
        this.realSize = realSize;
    }

    @Override
    protected List<DoubleSolution> replacement(List<DoubleSolution> population,
            List<DoubleSolution> offspringPopulation) {
        List<DoubleSolution> tmpList = new ArrayList<>();
        int minSize=Math.min(population.size(), offspringPopulation.size());
        while(tmpList.size() < getMaxPopulationSize()){
            for (int i = 0; i < minSize; i++) {
                // Dominance test
                DoubleSolution child = offspringPopulation.get(i);
                int result;
                result = dominanceComparator.compare(population.get(i), child);
                if (result == -1) {
                    // Solution i dominates child
                    tmpList.add(population.get(i));
                } else if (result == 1) {
                    // child dominates
                    tmpList.add(child);
                } else {
                    // the two solutions are non-dominated
                    tmpList.add(child);
                    tmpList.add(population.get(i));
                }
            }
        }
        Ranking<DoubleSolution> ranking = computeRanking(tmpList);

        return crowdingDistanceSelection(ranking);
    }
    
    @Override
    public void initMetaheuristic(boolean first, List<S> previousPop) {
  	  
    }
}
