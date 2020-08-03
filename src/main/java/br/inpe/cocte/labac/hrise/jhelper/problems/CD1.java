package br.inpe.cocte.labac.hrise.jhelper.problems;

//import br.usp.poli.pcs.lti.jmetalproblems.interfaces.RealWorldProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.Arrays;
import java.util.List;

/**
 * Created by X250 on 2016/12/22. An Evolutionary Many-Objective Optimization
 * Algorithm Using Reference-Point-Based Nondominated Sorting Approach, Part I:
 * Solving Problems With Box Constraints
 * http://ieeexplore.ieee.org/document/6600851/ and
 * http://ieeexplore.ieee.org/document/5196713/
 */
public class CD1 extends AbstractDoubleProblem implements RealWorldProblem{

    // defining the lower and upper limits
    public static final Double[] LOWERLIMIT = {18.9, 1.0, 73.53, -567.0, 58.9, -1079.0, 61.18, 2153.65, 1080.0, 690.0, 237.5};
    public static final Double[] UPPERLIMIT = {23.1, 3.0, 81.27, -513.0, 65.1, -1009.0, 67.72, 2380.35, 1160.0, 722.0, 262.5};
    private static final long serialVersionUID = 2840095756768371133L;

    /**
     * Constructor
     */
    public CD1(int m) {
        setNumberOfVariables(11);
        setNumberOfObjectives(m);
        setNumberOfConstraints(0);
        setName("CD1");

        List<Double> lowerLimit = Arrays.asList(LOWERLIMIT);
        List<Double> upperLimit = Arrays.asList(UPPERLIMIT);

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

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

        //System.out.println("Here eval");
        fx[0] = -(20.793879 + 1.89136 * x[9] + 0.0114387 * x[0] - 9.14817e-6 * x[5] * x[7]);
        fx[1] = -(56.324681 - 0.269293 * x[10] + 0.00182543 * x[10] * x[10]);
        fx[2] = -(-526.089896 - 8.25328 * x[1] + 1.11541 * x[7] + 0.229814 * x[9] + 0.00264013 * x[0] * x[7] - 0.00827051 * x[0] * x[9] + 0.0037207 * x[1] * x[7] - 5.36287e-5 * x[4] * x[8] - 0.000217057 * x[7] * x[7]);
        fx[3] = -(1130.495134 + 0.00198502 * x[0] * x[9] - 0.0213104 * x[1] * x[3] - 0.148874 * x[1] * x[6] - 0.000157399 * x[4] * x[8] + 0.00103811 * x[5] * x[10] + 0.000177597 * x[9] + 0.000142597 * x[5] * x[5] * x[5]);
        fx[4] = -(26.915743 + 0.122458 * x[4] + 0.00388909 * x[0] * x[6] - 0.000111132 * x[0] * x[9] - 0.00514664 * x[0] - 0.00304436 * x[1] * x[2] - 0.00201733 * x[4] * x[6] + 0.000123343 * x[5] + 0.00122862 * x[6] + 4.07671e-7 * x[7] * x[8] - 1.31454e-6 * x[7] * x[9] + 1.92481e-6 * x[5] * x[5]);
        fx[5] = -(-3.194308 + 0.136207 + 1.1249e-5 * x[0] * x[7] + 9.2368e-6 * x[1] * x[7] + 4.18586e-6 * x[5] * x[6] - 1.36206e-6 * x[5] * x[9]);
        fx[6] = -(78.074648 - 0.893487 * x[0] + 0.00195946 * x[7] + 0.111704 + 0.00537999 * x[0] - 9.45877e-5 * x[1] * x[3] - 3.58433e-6 * x[5] * x[9] + 9.07678e-7 * x[8] * x[9] + 0.0183585 * x[0] * x[0]);
        fx[7] = 81.151389 - 0.0128661 * x[0] * x[1] - 0.000120121 * x[0] * x[5] - 0.003582 * x[0] - 0.00406252 * x[1] * x[4] + 0.000216719 * x[1] * x[7] + 1.30572e-5 * x[3] * x[5] + 2.07317e-5 * x[3] * x[9] + 4.75765e-6 * x[5] * x[7];
        fx[8] = -(355.411911 + 0.883443 * x[3] + 3.1973 - 0.00715459 * x[0] * x[5] - 0.00715416 * x[0] * x[9] - 0.0124904 * x[0] * x[10] + 0.0415997 * x[0] - 0.0142366 * x[1] * x[5] - 0.0537868 * x[1] * x[10] - 0.00147267 * x[4] * x[6] + 0.000620051 * x[5] * x[10] + 0.000200799 * x[8] * x[9] - 0.00356566 * x[8] + 0.000912972 * x[3] * x[3]);

        for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
            solution.setObjective(i, fx[i]);
        }
        
        //System.out.println("Here eval2");
    }
    
    @Override
    public boolean isConstrained() {
        return false;
    }

    @Override
    public boolean isDiscrete() {
        return false;
    }
}
