package br.inpe.cocte.labac.hrise.jhelper.problems;

import java.util.Arrays;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

/**
 * Class representing problem Water
 */
@SuppressWarnings("serial")
public class SP3 extends AbstractDoubleProblem {
  //public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree ;
  //public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints ;

  // defining the lower and upper limits
  public static final Double [] LOWERLIMIT = {1.0, 4.0, 10.0};
  public static final Double [] UPPERLIMIT = {108.0, 12.0, 30.0};

  /**
   * Constructor.
   * Creates a default instance of the Water problem.
   */
  public SP3(int m) {
    setNumberOfVariables(3);
    setNumberOfObjectives(m);
    setNumberOfConstraints(0);
    setName("SP3") ;

    List<Double> lowerLimit = Arrays.asList(LOWERLIMIT) ;
    List<Double> upperLimit = Arrays.asList(UPPERLIMIT) ;

    setLowerLimit(lowerLimit);
    setUpperLimit(upperLimit);

    //overallConstraintViolationDegree = new OverallConstraintViolation<DoubleSolution>() ;
    //numberOfViolatedConstraints = new NumberOfViolatedConstraints<DoubleSolution>() ;
  }

  /** Evaluate() method */
  @Override
  public void evaluate(DoubleSolution solution)  {
    double[] fx = new double[solution.getNumberOfObjectives()];
    double[] x = new double[solution.getNumberOfVariables()];
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      x[i] = solution.getVariableValue(i) ;
    }

   
   
    //fx[0] = x[0]*(x[3] + ((48*x[3] + 96)/115200.0) + ((48*x[3] + 272)/500000.0)) + 0.300133;  
    fx[0] = ((115264*x[1] + 115328*x[2])/115200.0) + 0.300133;
    fx[1] = 300.0 -(x[0] + x[1] + x[2]);
    
   

    //solution.setObjective(0,fx[0]);
    solution.setObjective(0,fx[0]);
    solution.setObjective(1,fx[1]);
   

    //this.evaluateConstraints(solution);
  }

  
}
