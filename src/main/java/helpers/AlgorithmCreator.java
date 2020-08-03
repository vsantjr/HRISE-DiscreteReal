package helpers;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.JMException;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.uma.jmetal.algorithm.Algorithm;
//import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3;
//import org.uma.jmetal.algorithm.multiobjective.ibea.IBEA;
//import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
//import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import br.inpe.cocte.labac.hrise.jhelper.core.ParametersforAlgorithm;
import br.inpe.cocte.labac.hrise.jhelper.core.ParametersforHeuristics;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.LLHInterface;
import br.inpe.cocte.labac.hrise.jhelper.util.AlgorithmBuilderRILA;

/**
 *
 * @author vinicius
 */
public class AlgorithmCreator {

    protected final ParametersforAlgorithm pNsgaii = new ParametersforAlgorithm("NSGAII.default");
    protected final ParametersforAlgorithm pSpea2 = new ParametersforAlgorithm("SPEA2.default");
    //protected final ParametersforAlgorithm pmIBEA = new ParametersforAlgorithm("mIBEA.default");
    protected final ParametersforAlgorithm pIBEA = new ParametersforAlgorithm("IBEA.default");
    //protected final ParametersforAlgorithm pGde3 = new ParametersforAlgorithm("GDE3.default");
    
    //protected final ParametersforAlgorithm pNsgaii = new ParametersforAlgorithm();
    //protected final ParametersforAlgorithm pSpea2 = new ParametersforAlgorithm();
    //protected final ParametersforAlgorithm pmIBEA = new ParametersforAlgorithm("mIBEA.default");
    //protected final ParametersforAlgorithm pIBEA = new ParametersforAlgorithm();
    //protected final ParametersforAlgorithm pGde3 = new ParametersforAlgorithm("GDE3.default");
    
    protected Problem problem;
    protected AlgorithmBuilderRILA ab;
    protected ParametersforHeuristics pHeu;
    protected ParametersforHeuristics pHeuGDE3;

    public AlgorithmCreator(Problem problem) {
        this.problem = problem;
        ab = new AlgorithmBuilderRILA(problem);
        try {
            //pHeu = new ParametersforHeuristics("SBX.Poly.default", problem.getNumberOfVariables());
        	/*
        	 * For continuous/real optimisation
        	 */
            //pHeu = new ParametersforHeuristics("SBX.Poly.most", problem.getNumberOfVariables());
            
            /*
             * For discrete/integer optimisation
             */
            pHeu = new ParametersforHeuristics("SBX.Poly.most.discrete", problem.getNumberOfVariables());
            
            pHeuGDE3 = new ParametersforHeuristics("DE.Poly.default", problem.getNumberOfVariables());
        } catch (ConfigurationException ex) {
            Logger.getLogger(AlgorithmCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LLHInterface create(int chosenHeuristic, int eval) throws JMException {
        //if (eval != Integer.MAX_VALUE) {
          //  System.out.println("Create::   " + chosenHeuristic + " at " + eval);
        //}
        switch (chosenHeuristic) {
            case 0:
                return ab.createNsgaii(pNsgaii, pHeu);
            case 1:
                return ab.createSpea2(pSpea2, pHeu);
            case 2:
                return ab.createIbea(pIBEA, pHeu);
            //case 3:
              //  return ab.createGde3(pGde3, pHeuGDE3);
            //case 4:
              //  return ab.createMIbea(pmIBEA, pHeu);
            default:
              return null;
        }
        //return null;
    }

    public LLHInterface create(int chosenHeuristic) throws JMException {
        return create(chosenHeuristic, Integer.MAX_VALUE);
    }

   /* public Algorithm createAlg(int chosenHeuristic, int populationSize, int numGenerationsOrIterations) throws JMException {

    	// numGenerationsOrIterations = 10.
        ParametersforHeuristics configHeuristic = null;
        ParametersforAlgorithm configAlg = null;
        Algorithm algorithm = null;

        System.out.println("CreateJMetalAlg!!!!!!");
        
        switch (chosenHeuristic) {
            case 0:
                configAlg = pNsgaii;
                configHeuristic = pHeu;
                break;
            case 1:
                configAlg = pSpea2;
                configHeuristic = pHeu;
                break;
            case 2:
                configAlg = pIBEA;
                configHeuristic = pHeu;
                break;
            //case 3:
              //  configAlg = pGde3;
                //configHeuristic = pHeuGDE3;
                //configHeuristic.setDeCr(0.2);//TEMP @TODO new parameter file
                //configHeuristic.setDeF(0.2);
                //break;
            //case 4:
              //  configAlg = pmIBEA;
                //configHeuristic = pHeu;
                //break;
             default:
            	 System.out.println("Invalid Alg");
        }

        configAlg.setMaxIteractions(numGenerationsOrIterations);
        SelectionOperator selection = ab.generateSelection(); //pHeu = new ParametersforHeuristics("SBX.Poly.default", problem.getNumberOfVariables());
        CrossoverOperator crossover = ab.generateCross(configHeuristic);
        MutationOperator mutation = ab.generateMuta(configHeuristic, configAlg.getMaxIteractions());
        //Comparator comparator = ab.generateComparator(); // removed

        int matingPoolSize = populationSize;
        int offspringPopulationSize = populationSize;
        int archiveSize = populationSize;
        int maxEvaluations = numGenerationsOrIterations * populationSize;
        int k = 1;

        switch (chosenHeuristic) {
            case 0:
            	//pNsgaii.setPopulationSize(populationSize);
                //nsgaii.setArchiveSize(populationSize); //???
                //pNsgaii.setMaxIteractions(numGenerations);
               
                //int matingPoolSize=populationSize;
                //int offspringPopulationSize=populationSize;
                //CrossoverOperator crossoverOperator=ab.generateCross(pmxswap);
                //MutationOperator mutationOperator=ab.generateMuta(pmxswap, nsgaii.getMaxIteractions());
                //SelectionOperator selectionOperator=ab.generateSelection();
                //SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<DoubleSolution>(new CrowdingDistanceComparator<DoubleSolution>());
                //Comparator dominanceComparator = new DominanceComparator<>()  ;
                //algorithm = new NSGAII(problem, maxEvaluations, populationSize, matingPoolSize, offspringPopulationSize, crossover,
                  //      mutation, selection, comparator, new SequentialSolutionListEvaluator());
                 algorithm = null;
            	// algorithm = new NSGAII(problem, configAlg.getMaxEvaluations(), configAlg.getPopulationSize(), matingPoolSize, offspringPopulationSize, crossover,
                 //       mutation, selection, comparator, new SequentialSolutionListEvaluator());
                break;
            case 1:
            	//pSpea2.setPopulationSize(populationSize);
               // pSpea2.setArchiveSize(populationSize);
                //pSpea2.setMaxIteractions(numGenerations);
            	algorithm = null;
                //algorithm = new SPEA2(problem, numGenerationsOrIterations,
                  //      populationSize, crossover,
                    //    mutation, selection, new SequentialSolutionListEvaluator(), k);
                break;
            case 2:
            	 //ibea.setPopulationSize(populationSize);
	               //ibea.setArchiveSize(populationSize);
	                //ibea.setMaxIteractions(numGenerations);
                algorithm = new IBEA(problem, populationSize,
                        archiveSize,
                        maxEvaluations,
                        selection, crossover, mutation);
                break;
            //case 3:
              //  algorithm = new GDE3((DoubleProblem) problem,
                //        configAlg.getPopulationSize(), configAlg.getMaxEvaluations(), new DifferentialEvolutionSelection(),
                  //      (DifferentialEvolutionCrossover) crossover,
                    //    new SequentialSolutionListEvaluator());
                //break;
            default:
            	System.out.println("Invalid Alg");
        }
        return algorithm;
    }*/

    public void setMaxEvaluationsAndPopulation(int evaluations, int populationSize) {
        int iterations = evaluations / populationSize;
        System.out.println("Set iterations=" + iterations + " popSize=" + populationSize);
        //pGde3.setMaxIteractions(iterations);
        //pGde3.setArchiveSize(populationSize);
        //pGde3.setPopulationSize(populationSize);
        pNsgaii.setMaxIteractions(iterations);
        pNsgaii.setArchiveSize(populationSize);
        pNsgaii.setPopulationSize(populationSize);
        pSpea2.setMaxIteractions(iterations);
        pSpea2.setArchiveSize(populationSize);
        pSpea2.setPopulationSize(populationSize);
        pIBEA.setMaxIteractions(iterations);
        pIBEA.setArchiveSize(populationSize);
        pIBEA.setPopulationSize(populationSize);
        //pmIBEA.setMaxIteractions(iterations);
        //pmIBEA.setArchiveSize(populationSize);
        //pmIBEA.setPopulationSize(populationSize);
    }
}
