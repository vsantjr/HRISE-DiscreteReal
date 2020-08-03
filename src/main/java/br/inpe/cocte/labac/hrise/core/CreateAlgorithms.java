package br.inpe.cocte.labac.hrise.core;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.JMException;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3;
import org.uma.jmetal.algorithm.multiobjective.mombi.MOMBI2;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.CrowdingDistanceComparator;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import br.inpe.cocte.labac.hrise.jhelper.core.ParametersforAlgorithm;
import br.inpe.cocte.labac.hrise.jhelper.core.ParametersforHeuristics;
import br.inpe.cocte.labac.hrise.jhelper.imp.algs.Ibea;
import br.inpe.cocte.labac.hrise.jhelper.imp.algs.Nsgaii;
import br.inpe.cocte.labac.hrise.jhelper.imp.algs.Paes;
import br.inpe.cocte.labac.hrise.jhelper.imp.algs.Spea2;
import br.inpe.cocte.labac.hrise.jhelper.util.AlgorithmBuilder;

public class CreateAlgorithms<S extends Solution<?>> {
	
	public CreateAlgorithms() {
		
	}
	
	 public Algorithm createAlg(String algName, int populationSize, int numGenerations, Problem problem, boolean f, List<S> jArchive) throws ConfigurationException, JMException, FileNotFoundException {
		    //public static StandardMetaheuristic createAlg(String algName, int populationSize, int numGenerations, Problem problem) throws ConfigurationException, JMException, FileNotFoundException {
		        System.err.println(algName);
		        AlgorithmBuilder ab = new AlgorithmBuilder(problem);
		        //ParametersforHeuristics pmxswap = new ParametersforHeuristics("SBX.Poly.default", problem.getNumberOfVariables());
		        
		        /*
		         * For continuous/real optimisation
		         */
		        //ParametersforHeuristics pmxswap = new ParametersforHeuristics("SBX.Poly.most", problem.getNumberOfVariables());
		        
		        
		        /*
		         *  For discrete/integer optimisation
		         */
		        ParametersforHeuristics pmxswap = new ParametersforHeuristics("SBX.Poly.most.discrete", problem.getNumberOfVariables());
		        //the same parameter as maashi
		        //pmxswap.setCrossoverDistribution(10);
		        //pmxswap.setMutationDistribution(20);
		        //pmxswap.setCrossoverProbality(0.9);
		        //the same parameter as maashi
		        switch (algName) {
		            case "IBEA":
		                //ParametersforAlgorithm ibea = new ParametersforAlgorithm("IBEA.default");
		                ParametersforAlgorithm ibea = new ParametersforAlgorithm();
		                if (populationSize % 2 != 0) {
		                    populationSize++;
		                }
		                ibea.setPopulationSize(populationSize);
		                ibea.setArchiveSize(populationSize);
		                ibea.setMaxIteractions(numGenerations);
		                
		                return new Ibea(problem, ibea.getPopulationSize(), ibea.getArchiveSize(), ibea.getMaxIteractions() * ibea.getPopulationSize(), ab.generateSelection(), ab.generateCross(pmxswap), ab.generateMuta(pmxswap, ibea.getMaxIteractions()), f, jArchive);
		                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //
		            case "NSGAII":
		            	//ParametersforAlgorithm nsgaii = new ParametersforAlgorithm("NSGAII.default"); // NSGAII.default or
		                ParametersforAlgorithm nsgaii = new ParametersforAlgorithm();
		                if (populationSize % 2 != 0) {
		                    populationSize++;
		                }
		                nsgaii.setPopulationSize(populationSize);
		                //nsgaii.setArchiveSize(populationSize); //???
		                nsgaii.setMaxIteractions(numGenerations);
		                int maxEvaluations=nsgaii.getMaxIteractions() * nsgaii.getPopulationSize();
		                int matingPoolSize=populationSize;
		                int offspringPopulationSize=populationSize;
		                CrossoverOperator crossoverOperator=ab.generateCross(pmxswap);
		                MutationOperator mutationOperator=ab.generateMuta(pmxswap, nsgaii.getMaxIteractions());
		                //SelectionOperator selectionOperator=ab.generateSelection();
		                //SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<DoubleSolution>(new CrowdingDistanceComparator<DoubleSolution>());
		                Comparator dominanceComparator = new DominanceComparator<>()  ;
		                
		                return new Nsgaii(problem, maxEvaluations, populationSize, matingPoolSize, offspringPopulationSize, crossoverOperator, mutationOperator, ab.generateSelection(), dominanceComparator, new SequentialSolutionListEvaluator(), f, jArchive);
		                //return new Nsgaii(problem, maxEvaluations, populationSize, matingPoolSize, offspringPopulationSize, crossoverOperator, mutationOperator, selectionOperator, dominanceComparator, new SequentialSolutionListEvaluator(), f, jArchive);
		                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //
		            case "SPEA2":
		                //ParametersforAlgorithm spea2 = new ParametersforAlgorithm("SPEA2.default");
		            	// For SPEA2 -> pass the maxIterations and not maxEvaluations?
		                ParametersforAlgorithm spea2 = new ParametersforAlgorithm();
		                if (populationSize % 2 != 0) {
		                    populationSize++;
		                }
		                spea2.setPopulationSize(populationSize);
		                spea2.setArchiveSize(populationSize);
		                spea2.setMaxIteractions(numGenerations);
		                
		                return new Spea2(problem, spea2.getMaxIteractions(), spea2.getPopulationSize(), ab.generateCross(pmxswap), ab.generateMuta(pmxswap, spea2.getMaxIteractions()), ab.generateSelection(), new SequentialSolutionListEvaluator<>(), f, jArchive);
		                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //
		            case "PAES":
		                //ParametersforAlgorithm paes = new ParametersforAlgorithm("PAES.default");//use the same
		                ParametersforAlgorithm paes = new ParametersforAlgorithm();
		                if (populationSize % 2 != 0) {
		                    populationSize++;
		                }
		                int biSections = 5;
		                paes.setPopulationSize(populationSize);
		                paes.setArchiveSize(populationSize);
		                paes.setMaxIteractions(numGenerations);
		               
		                return new Paes(problem, paes.getPopulationSize(), paes.getMaxIteractions() * paes.getPopulationSize(), biSections, ab.generateMuta(pmxswap, paes.getMaxIteractions()), f, jArchive);
		                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //
		            case "GDE3":
		                ParametersforHeuristics de = new ParametersforHeuristics("DE.Poly.default", problem.getNumberOfVariables()); // It turns out it overrides the SBX.Poly.Default
		                de.setDeCr(0.2);//TEMP @TODO new parameter file
		                de.setDeF(0.2);
		                //0.1 0.5 de https://books.google.com.br/books?id=PPxMVCKTbtoC&pg=PA662&lpg=PA662&dq=gde3+dtlz&source=bl&ots=chg6-dFh4P&sig=UXStuTF2UQI7eiXbnxHBX64fv3s&hl=pt-BR&sa=X&ved=0ahUKEwig4cGr2ebVAhUSPJAKHcnnAYoQ6AEINjAC#v=onepage&q=gde3%20dtlz&f=false
		                return new GDE3((DoubleProblem) problem, populationSize, populationSize * numGenerations, new DifferentialEvolutionSelection(), (DifferentialEvolutionCrossover) ab.generateCross(de), new SequentialSolutionListEvaluator());
		            case "MOMBI2":
		            	 //Logger.getLogger(WFGAlgs.class.getName()).log(Level.INFO, "MOMBI2 ENTER:" + mombi2.getMaxIteractions());
		                ParametersforAlgorithm mombi2 = new ParametersforAlgorithm("MOMBI2.most");
		                //Logger.getLogger(HHOSTMain.class.getName()).log(Level.INFO, "MOMBI2 ENTER:" + mombi2.getMaxIteractions());
		                
		                if (populationSize % 2 != 0) {
		                    populationSize++;
		                }
		                //mombi2.setPopulationSize(populationSize);
		                //mombiii.setArchiveSize(populationSize);
		                mombi2.setMaxIteractions(numGenerations);
		                //Logger.getLogger(HHOSTMain.class.getName()).log(Level.INFO, "NSGA-II - Population size:  " + populationSize);
		                //Logger.getLogger(HHOSTMain.class.getName()).log(Level.INFO, "MOMBI2 Generations: " + mombi2.getMaxIteractions());
		                return new MOMBI2(problem, mombi2.getMaxIteractions(), ab.generateCross(pmxswap), ab.generateMuta(pmxswap, mombi2.getMaxIteractions()),
		                		          ab.generateSelection(), new SequentialSolutionListEvaluator<DoubleSolution>(), "mombi2-weights/weight/weight_03D_12.sld");

		        }
		        return null;
		    }

}
