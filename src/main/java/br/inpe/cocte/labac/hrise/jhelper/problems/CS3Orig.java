package br.inpe.cocte.labac.hrise.jhelper.problems;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.util.Arrays;
import java.util.List;

/**
 * Created by X250 on 2016/7/20.
 * http://ieeexplore.ieee.org/document/5196713/
 * TA FALTANDO OBJETIVO
 */
public class CS3Orig extends AbstractDoubleProblem implements ConstrainedProblem<DoubleSolution> {

    private static final long serialVersionUID = -2817990885149134634L;
    public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree;
    public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints;

    // defining the lower and upper limits
    public static final Double[] LOWERLIMIT = {0.5, 0.45, 0.5, 0.5, 0.875, 0.4, 0.4};
    public static final Double[] UPPERLIMIT = {1.5, 1.35, 1.5, 1.5, 2.625, 1.2, 1.2};

    /**
     * Constructor. Creates a default instance of the Water problem.
     */
    public CS3Orig(int m) {
        setNumberOfVariables(7);
        setNumberOfObjectives(m);
        setNumberOfConstraints(10);
        setName("CS3");

        List<Double> lowerLimit = Arrays.asList(LOWERLIMIT);
        List<Double> upperLimit = Arrays.asList(UPPERLIMIT);

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

        overallConstraintViolationDegree = new OverallConstraintViolation<DoubleSolution>();
        numberOfViolatedConstraints = new NumberOfViolatedConstraints<DoubleSolution>();
    }

    /**
     * Evaluate() method
     */
    @Override
    public void evaluate(DoubleSolution solution) {
        double[] fx = new double[solution.getNumberOfObjectives()];
        double[] x = new double[solution.getNumberOfVariables()];
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            x[i] = solution.getVariableValue(i);
        }

        double F = 4.72 - 0.5 * x[3] - 0.19 * x[1] * x[2];
        double Vmbp = 10.58 - 0.674 * x[0] * x[1] - 0.67275 * x[1];
        double Vfd = 16.45 - 0.489 * x[2] * x[6] - 0.843 * x[4] * x[5];
        fx[0] = 1.98 + 4.9 * x[0] + 6.67 * x[1] + 6.98 * x[2] + 4.01 * x[3] + 1.78 * x[4] + 0.00001 * x[5] + 2.73 * x[6];
        //fx[1] = F;
        fx[1] = 0.5 * (Vmbp + Vfd);

        solution.setObjective(0, fx[0]);
        //solution.setObjective(1, fx[1]);
        solution.setObjective(1, fx[1]);
    }

    /**
     * EvaluateConstraints() method
     * @param solution
     */
    @Override
    public void evaluateConstraints(DoubleSolution solution) {
        double[] constraint = new double[getNumberOfConstraints()];
        double[] x = new double[solution.getNumberOfVariables()];
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            x[i] = solution.getVariableValue(i);
        }

        double F = 4.72 - 0.5 * x[3] - 0.19 * x[1] * x[2];
        double Vmbp = 10.58 - 0.674 * x[0] * x[1] - 0.67275 * x[1];
        double Vfd = 16.45 - 0.489 * x[2] * x[6] - 0.843 * x[4] * x[5];
        constraint[0] = 1.0 - (1.16 - 0.3717 * x[1] * x[3] - 0.0092928 * x[2]);
        constraint[1] = 0.32 - (0.261 - 0.0159 * x[0] * x[1] - 0.06486 * x[0] - 0.019 * x[1] * x[6] + 0.0144 * x[2] * x[4] + 0.0154464 * x[5]);
        constraint[2] = 0.32 - (0.214 + 0.00817 * x[4] - 0.045195 * x[0] - 0.0135168 * x[0] + 0.03099 * x[1] * x[5] - 0.018 * x[1] * x[6] + 0.007176 * x[2] + 0.023232 * x[2] - 0.00364 * x[4] * x[5] - 0.018 * x[1] * x[1]);
        constraint[3] = 0.32 - (0.74 - 0.61 * x[1] - 0.031296 * x[2] - 0.031872 * x[6] + 0.227 * x[1] * x[1]);
        constraint[4] = 32.0 - (28.98 + 3.818 * x[2] - 4.2 * x[0] * x[1] + 1.27296 * x[5] - 2.68065 * x[6]);
        constraint[5] = 32.0 - (33.86 + 2.95 * x[2] - 5.057 * x[0] * x[1] - 3.795 * x[1] - 3.4431 * x[6] + 1.45728);
        constraint[6] = 32.0 - (46.36 - 9.9 * x[1] - 4.4505 * x[0]);
        constraint[7] = 4.0 - F;
        constraint[8] = 9.9 - Vmbp;
        constraint[9] = 15.7 - Vfd;

        double overallConstraintViolation = 0.0;
        int violatedConstraints = 0;
        for (int i = 0; i < getNumberOfConstraints(); i++) {
            if (constraint[i] < 0.0) {
                overallConstraintViolation += constraint[i];
                violatedConstraints++;
            }
        }

        overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
        numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
    }
}
