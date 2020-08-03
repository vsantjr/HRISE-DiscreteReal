package br.inpe.cocte.labac.hrise.imp.llhs;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

/**
 *
 * @author psavd
 */
public class mIBEA<S extends Solution<?>> extends IBEA<S> {

    public mIBEA(Problem problem, int populationSize, int archiveSize, int maxEvaluations, SelectionOperator selectionOperator, CrossoverOperator crossoverOperator, MutationOperator mutationOperator) {
        super(problem, populationSize, archiveSize, maxEvaluations, selectionOperator, crossoverOperator, mutationOperator);
    }
    
    /**
     * Selection list.
     *
     * @param population the population
     * @return the list
     */
   /* @Override
    protected List<S> selection(List<S> population) {
        List<S> union = new ArrayList<>();
        union.addAll(population);
        union.addAll(archive);
        union = SolutionListUtils.getNondominatedSolutions(union);//modificated part
        calculateFitness(union);
        archive = union;
        while (archive.size() > populationSize) {
            removeWorst(archive);
        }
        return archive;
    }*/

    @Override
    public String getName() {
        return "mIBEA";
    }

    @Override
    public String getDescription() {
        return "Modificated Indicator based Evolutionary Algorithm";
    }
}
