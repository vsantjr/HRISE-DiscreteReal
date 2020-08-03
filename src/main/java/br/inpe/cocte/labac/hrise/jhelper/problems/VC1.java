package br.inpe.cocte.labac.hrise.jhelper.problems;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public class VC1 extends AbstractDoubleProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    /**
     * Constructor
     */
    public VC1(int m) {
        setNumberOfVariables(5);
        setNumberOfObjectives(m); // {Mass, Ain, Intrusion}
        setNumberOfConstraints(0);
        setName("VC1");
        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(1.0);
            upperLimit.add(3.0);
        }
        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);
    }

    /**
     * Evaluate() method
     *
     * @param solution
     */
    @Override
    public void evaluate(DoubleSolution solution) {
        double[] var = new double[getNumberOfVariables()];
        for (int i = 0; i < getNumberOfVariables(); i++) {
            var[i] = solution.getVariableValue(i);
        }

        double obj1 = 1640.2823 + 2.3573285 * var[0] + 2.3220035 * var[1] + 4.5688768 * var[2] + 7.7213633 * var[3] + 4.4559504 * var[4];
        double obj2 = 6.5856 + 1.15 * var[0] - 1.0427 * var[1] + 0.9738 * var[2] + 0.8364 * var[3] - 0.3695 * var[0] * var[3] + 0.0861 * var[0] * var[4] + 0.3628 * var[1] * var[3] - 0.1106 * var[0] * var[0] - 0.3437 * var[2] * var[2] + 0.1764 * var[3] * var[3];
        double obj3 = -0.0551 + 0.0181 * var[0] + 0.1024 * var[1] + 0.0421 * var[2] - 0.0073 * var[0] * var[1] + 0.024 * var[1] * var[2] - 0.0118 * var[1] * var[3] - 0.0204 * var[2] * var[3] - 0.008 * var[2] * var[4] - 0.0241 * var[1] * var[1] + 0.0109 * var[3] * var[3];

        solution.setObjective(0, obj1);
        solution.setObjective(1, obj2);
        solution.setObjective(2, obj3);
    }
}
