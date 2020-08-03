package br.inpe.cocte.labac.hrise.jhelper.problems;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.util.Arrays;
import java.util.List;

/**
 * Created by X250 on 2016/12/10.
 */
public class MAC3 extends AbstractDoubleProblem implements ConstrainedProblem<DoubleSolution> {

    public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree;
    public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints;

    // defining the lower and upper limits
    public static final Double[] LOWERLIMIT = {6.40, 0.69, 3.91};
    public static final Double[] UPPERLIMIT = {7.09, 2.89, 4.61};

    /**
     * Constructor. Creates a default instance of the Machining problem.
     */
    public MAC3(int m) {
        setNumberOfVariables(3);
        setNumberOfObjectives(m); // f1, f2, f4
        setNumberOfConstraints(3);
        setName("MAC3");

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

        double lnSR = 7.49 - 0.44 * x[0] + 1.16 * x[1] - 0.61 * x[2];
        double lnSI = -4.31 + 0.92 * x[0] - 0.16 * x[1] + 0.43 * x[2];
        //double lnTL = 21.90 - 1.94 * x[0] - 0.30 * x[1] - 1.04 * x[2];
        double lnMRR = -11.33 + x[0] + x[1] + x[2];
        fx[0] = lnSR;
        fx[1] = -lnSI;
        //fx[2] = -lnTL;
        fx[2] = -lnMRR;

        solution.setObjective(0, fx[0]);
        solution.setObjective(1, fx[1]);
        //solution.setObjective(2, fx[2]);
        solution.setObjective(2, fx[2]);
    }

    /**
     * EvaluateConstraints() method
     */
    @Override
    public void evaluateConstraints(DoubleSolution solution) {
        double[] constraint = new double[getNumberOfConstraints()];
        double[] x = new double[solution.getNumberOfVariables()];
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            x[i] = solution.getVariableValue(i);
        }

        constraint[0] = -3.17 - (-0.44 * x[0] + 1.16 * x[1] - 0.61 * x[2]);
        constraint[1] = -8.04 - (-0.92 * x[0] + 0.16 * x[1] - 0.43 * x[2]);
        constraint[2] = 18.50 - (1.94 * x[0] + 0.30 * x[1] + 1.04 * x[2]);
        /*
        for (int j=0;j<getNumberOfConstraints();j++){
            solution.setConstraintViolation(j,constraint[j]);
        }
         */
        double overallConstraintViolation = 0.0;
        int violatedConstraints = 0;
        for (int i = 0; i < getNumberOfConstraints(); i++) {
            if (constraint[i] < 0.0) {
                overallConstraintViolation += constraint[i];
                violatedConstraints++;
            }
        }

        solution.setAttribute("overallConstraintViolationDegree", overallConstraintViolation);
        overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
        numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
    }
}
