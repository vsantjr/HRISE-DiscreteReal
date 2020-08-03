package HF_LA;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.JMException;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.problem.Problem;

import org.uma.jmetal.solution.Solution;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.LLHInterface;

public abstract class LALearning<S extends Solution<?>> implements Serializable {

    /**
     * Stores the problem to solve
     */
    protected Problem problem_;

    /**
     * Stores the operators used by the algorithm, such as selection, crossover,
     * etc.
     */
    protected Map<String, Operator> operators_ = null;
    /**
     * Stores the underling algorithms used by the reinforcement learning
     * process, such as NSGA-II, SPEA2, IBEA, mIBEA etc. etc.
     */
    protected Map<String, LLHInterface> lowlevelMOEAs_ = null;

    protected String[] lowlevelMOEAsStr_ = null;
    /**
     * Stores algorithm specific parameters. For example, in NSGA-II these
     * parameters include the population size and the maximum number of function
     * evaluations.
     */
    protected Map<String, Object> inputParameters_ = null;

    /**
     * Stores output parameters, which are retrieved by Main object to obtain
     * information from an algorithm.
     */
    protected Map<String, Object> outPutParameters_ = null;

    protected String name_;

    public LALearning() {
        super();
    }

    public LALearning(Problem problem) {
        problem_ = problem;
    }

    public void addAlgorithm(String name, LLHInterface algorithm) {
        if (lowlevelMOEAs_ == null) {
            lowlevelMOEAs_ = new HashMap<String, LLHInterface>();
        }
        lowlevelMOEAs_.put(name, algorithm);
    }

    /**
     * Offers facilities for add new operators for the algorithm. To use an
     * operator, an algorithm has to obtain it through the
     * <code>getOperator</code> method. e.g., addOperator("crossover",crossover)
     *
     * @param name The operator name
     * @param operator The operator
     */
    public void addOperator(String name, Operator operator) {
        if (operators_ == null) {
            operators_ = new HashMap<String, Operator>();
        }
        operators_.put(name, operator);
    } // addOperator 

    /**
     * Gets an operator through his name. If the operator doesn't exist or the
     * name is wrong this method returns null. The client of this method have to
     * check the result of the method.
     *
     * @param name The operator name
     * @return The operator if exists, null in another case.
     */
    public Operator getOperator(String name) {
        return operators_.get(name);
    } // getOperator  

    /**
     * Sets an input parameter to an algorithm. Typically, the method is invoked
     * by a Main object before running an algorithm. The parameters have to been
     * inserted using their name to access them through the
     * <code>getInputParameter</code> method.
     *
     * @param name The parameter name
     * @param object Object that represent a parameter for the algorithm.
     */
    public void setInputParameter(String name, Object object) {
        if (inputParameters_ == null) {
            inputParameters_ = new HashMap<String, Object>();
        }
        inputParameters_.put(name, object);
    } // setInputParameter 

    public void setlowlevelMOEAsStr_(String[] moeas) {
        lowlevelMOEAsStr_ = new String[moeas.length];
        for (int i = 0; i < lowlevelMOEAsStr_.length; i++) {
            lowlevelMOEAsStr_[i] = moeas[i];
        }
    }

    public String[] getlowlevelMOEAsStr_() {
        return lowlevelMOEAsStr_;
    }

    /**
     * Gets an input parameter through its name. Typically, the method is
     * invoked by an object representing an algorithm
     *
     * @param name The parameter name
     * @return Object representing the parameter or null if the parameter
     * doesn't exist or the name is wrong
     */
    public Object getInputParameter(String name) {
        return inputParameters_.get(name);
    } // getInputParameter

    public Object[] getInputParameterArray(String name) {
        return (Object[]) inputParameters_.get(name);
    }

    public LLHInterface getLowLevelMOEA(String name) {
        return lowlevelMOEAs_.get(name);
    }

    /**
     * Sets an output parameter that can be obtained by invoking
     * <code>getOutputParame</code>. Typically this algorithm is invoked by an
     * algorithm at the end of the <code>execute</code> to retrieve output
     * information
     *
     * @param name The output parameter name
     * @param object Object representing the output parameter
     */
    public void setOutputParameter(String name, Object object) {
        if (outPutParameters_ == null) {
            outPutParameters_ = new HashMap<String, Object>();
        }
        outPutParameters_.put(name, object);
    } // setOutputParameter  

    /**
     * Gets an output parameter through its name. Typically, the method is
     * invoked by a Main object after the execution of an algorithm.
     *
     * @param name The output parameter name
     * @return Object representing the output parameter, or null if the
     * parameter doesn't exist or the name is wrong.
     */
    public Object getOutputParameter(String name) {
        if (outPutParameters_ != null) {
            return outPutParameters_.get(name);
        } else {
            return null;
        }
    } // getOutputParameter   

    /**
     * Returns the problem to solve
     *
     * @return Problem The problem to solve
     */
    public Problem getProblem() {
        return problem_;
    }

    public void setName(String name) {
        name_ = name;
    }

    public String getName() {
        return name_;
    }

    public abstract List<S> runLA() throws IOException, ClassNotFoundException, JMException;//implement your learning body here! Oooh, actually I am talking to myself, nobody  would use this framework except me.

    public List<S> runLA(int problemIndex) throws IOException, ClassNotFoundException, JMException{
        return null;
    }
    
    public List<S> runLA(List<S> initialSolutionSet, String runningFolder)
            throws IOException, ClassNotFoundException, JMException {
        // TODO Auto-generated method stub
        return null;
    }

    public void initialise() {
        // TODO Auto-generated method stub

    }

}
