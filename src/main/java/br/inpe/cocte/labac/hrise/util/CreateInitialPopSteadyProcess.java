package br.inpe.cocte.labac.hrise.util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.problem.Problem;

import org.uma.jmetal.solution.Solution;

public class CreateInitialPopSteadyProcess<S extends Solution<?>> extends AbstractGeneticAlgorithm<S, List<S>> {
	
	public CreateInitialPopSteadyProcess(Problem problem, int popSize) {
		super(problem);
		setMaxPopulationSize(popSize);
		
	}
	
	public List<S> createInitialPopulationSteadyProcess() throws IOException {
		List<S> population = new ArrayList<>(getMaxPopulationSize());
	    for (int i = 0; i < getMaxPopulationSize(); i++) {
	      S newIndividual = getProblem().createSolution();
	      population.add(newIndividual);
	    }
	    SaveFiles popf = new SaveFiles();
	    popf.saveFileAnyValues("Steady", population);
	    return population;
	  }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<S> evaluatePopulation(List<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<S> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initProgress() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isStoppingConditionReached() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected List<S> replacement(List<S> arg0, List<S> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateProgress() {
		// TODO Auto-generated method stub
		
	}

	
	

}
