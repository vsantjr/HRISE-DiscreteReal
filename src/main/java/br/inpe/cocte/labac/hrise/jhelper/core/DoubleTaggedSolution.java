package br.inpe.cocte.labac.hrise.jhelper.core;

import lombok.Getter;
import lombok.Setter;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.util.JMetalException;

/**
 * This class extends the jMetal class DefaultDoubleSolution and implements TaggedSolution.
 * So, It contains methods used to associate a DefaultDoubleSolution with an algorithm and
 * LowLevelHeuristic.
 */
public class DoubleTaggedSolution extends DefaultDoubleSolution implements TaggedSolution {

  private static final long serialVersionUID = -849648866550315279L;

  /**
   * The associated algorithm.
   */
  private @Getter @Setter Algorithm algorithm;

  /**
   * The associated Low-Level Heuristic.
   */
  private @Getter @Setter LowLevelHeuristic action;
  
  private @Getter @Setter int parentNeighborType;//-1 NOTHING 0=NEIGHBOR 1=POPULATION
  
  private @Getter @Setter int subProblemId;//-1 to NOTHING
  
  private @Getter @Setter boolean newSolution;

  /**
   * @param problem a jMetal DoubleProblem.
   */
  public DoubleTaggedSolution(DoubleProblem problem) {
    super(problem);
    this.algorithm = null;
    this.action = null;
    this.parentNeighborType=-1;
    this.subProblemId=-1;
    this.newSolution=false;
  }

  /**
   * @param solution a jMetal DefaultDoubleSolution.
   */
  public DoubleTaggedSolution(DefaultDoubleSolution solution) {
    super(solution);
    this.algorithm = null;
    this.action = null;
    this.parentNeighborType=-1;
    this.subProblemId=-1;
    this.newSolution=false;
  }
  
  /**
   * @param solution a jMetal DefaultDoubleSolution.
   */
  /*public DoubleTaggedSolution(DoubleTaggedSolution solution) {
    super(solution);
    this.algorithm = solution.getAlgorithm();
    this.action = solution.getAction();
    this.parentNeighborType=solution.getParentNeighborType();
    this.subProblemId=solution.getParentNeighborType();
    this.newSolution=solution.isNewSolution();
  }*/
  
  @Override
  public Object getTheVariableValue(int i) {
    return super.getVariableValue(i);
  }
  
  @Override
  public DoubleTaggedSolution copy() {
    return new DoubleTaggedSolution(this);
  }
  
  @Override
    public String strVariables() {
        String str="";
        for (int i = 0; i < this.getNumberOfVariables(); i++) {
            str+=String.valueOf(getVariableValue(i))+".";
        }
        return str;
    }

  
// @@@@@   VALDIVINO @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  
  
  
@Override
public LowLevelHeuristic getAction() {
	// TODO Auto-generated method stub
	return action;
}

@Override
public void setAction(LowLevelHeuristic action) {
	// TODO Auto-generated method stub
	if (action == null) {
	      throw new JMetalException("action is null");
	    }
	    this.action = action;

	    //return this.action;
	
}

@Override
public Algorithm getAlgorithm() {
	// TODO Auto-generated method stub
	return algorithm;
}

@Override
public void setAlgorithm(Algorithm algorithm) {
	// TODO Auto-generated method stub
	  if (algorithm == null) {
	      throw new JMetalException("algorithm is null");
	    }
	    this.algorithm = algorithm;

	    //return this;
	
}

@Override
public int getSubProblemId() {
	// TODO Auto-generated method stub
	return subProblemId;
}

@Override
public void setSubProblemId(int id) {
	// TODO Auto-generated method stub
	  if (subProblemId == -1) {
	      throw new JMetalException("subProblemId means nothong.");
	    }
	    this.subProblemId = subProblemId;

	    //return this;
	
}

@Override
public void setNewSolution(boolean isnew) {
	// TODO Auto-generated method stub
	
}

@Override
public boolean isNewSolution() {
	// TODO Auto-generated method stub
	return false;
}
}
