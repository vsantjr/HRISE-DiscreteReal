package HF_LA;

import helpers.AlgorithmCreator;
import helpers.ProblemCreator;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import jmetal.qualityIndicator.HyperVolumeMinimizationProblem;
import jmetal.qualityIndicator.util.MetricsUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.management.JMException;
import jmetal.qualityIndicator.util.IBEAFitness;
import jmetal.qualityIndicator.util.MetricsUtilPlus;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
//import uk.ac.nottingham.asap.realproblems.ExternalProblem;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.LLHInterface;

public class AnyProblemOnlineLearning<S extends Solution<?>> extends LALearning {

    int acceptedQtdValue;
    LAutil util_;
    int numberOfLLH_ = 0;
    AbstractLearningAutomata al_ = null;
    AbstractSelectionLLHs sl_ = null;
    AbstractSelectionLLHs pl_ = null;
    LLH[] llhs = null;

    int runIndex;
    double[] reference;
    ArrayList<Integer> heuristicList;
    double alpha;
    int numberOfObj;
    int populationSize;
    double[] minimumValues;
    double[] maximumValues;
    double difference = 0.0;

    //the increase speed of the threshold of hypervolume
    double increaseSpeed = 1.0;
    ArrayList<Double> threshold = new ArrayList<Double>();

    MetricsUtilPlus utils_ = new MetricsUtilPlus();
    Properties config = new Properties();

    String configFile = null;
    String algorithms[] = {"NSGAII", "SPEA2", "IBEA", "mIBEA", "GDE3"};
    String folderPath = null;

    //RouletteSelectionLLHs rs = new RouletteSelectionLLHs();//
    epsilonGreedySelectionLLHs rs = new epsilonGreedySelectionLLHs();//modified!!!
    GreedySelectionLLHs ps = new GreedySelectionLLHs();
    //SoftMaxSelectionLLHs rs = new SoftMaxSelectionLLHs();

    int fixedGeneration;

    protected final ProblemCreator problemCreator;

    public AnyProblemOnlineLearning(String configFile, HashMap<String, String> parameterSet, int runNo, String outputfolderpath, ProblemCreator problemCreator, int qtdAlgs) {
        algorithms=Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        folderPath = outputfolderpath;
        double multiplier_ = Double.parseDouble(parameterSet.get("Multiplier"));
        RewardPenaltyLA RPLA = new RewardPenaltyLA(multiplier_);
        al_ = RPLA;
        sl_ = rs;
        pl_ = ps;
        numberOfLLH_ = algorithms.length;
        llhs = new LLH[numberOfLLH_];
        for (int i = 0; i < numberOfLLH_; i++) {
            //llhs.add(new LLH(numberOfLLH_, al_));
            llhs[i] = new LLH(numberOfLLH_, al_, sl_, i);
        }
        try {
            config.load(new FileInputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load configuration file!");
            System.exit(-1);
        }
        populationSize = Integer.parseInt(config.getProperty("PopulationSize"));
        alpha = 0.1;
        //alpha = Double.parseDouble(config.getProperty("alpha"));
        util_ = new LAutil();
        runIndex = runNo;

        /*		for(int i = 0; i < numberOfObj; i++){
			  reference[i] = 2 *(i+1) +1;
		   }*/
        heuristicList = new ArrayList<Integer>();
        for (int i = 0; i < algorithms.length; i++) {
            acceptedQtdValue += i;
        }
    }

    public AnyProblemOnlineLearning(String configfilePath, int runNo, int fixedGeneration, ProblemCreator problemCreator, int qtdAlgs) {
        algorithms=Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        folderPath = "HF_Config_Benchmark/Results/" + problemCreator.getProblemClass() + "/AM_" + fixedGeneration;
        configFile = configfilePath;
        RewardPenaltyLA RPLA = new RewardPenaltyLA(configFile);
        al_ = RPLA;
        sl_ = rs;
        pl_ = ps;
        numberOfLLH_ = algorithms.length;
        llhs = new LLH[numberOfLLH_];
        for (int i = 0; i < numberOfLLH_; i++) {
            //llhs.add(new LLH(numberOfLLH_, al_));
            llhs[i] = new LLH(numberOfLLH_, al_, sl_, i);
        }
        try {
            config.load(new FileInputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load configuration file!");
            System.exit(-1);
        }
        populationSize = Integer.parseInt(config.getProperty("PopulationSize"));
        alpha = 0.1;
        //alpha = Double.parseDouble(config.getProperty("alpha"));
        util_ = new LAutil();
        runIndex = runNo;

        /*		for(int i = 0; i < numberOfObj; i++){
			  reference[i] = 2 *(i+1) +1;
		   }*/
        heuristicList = new ArrayList<Integer>();
        for (int i = 0; i < algorithms.length; i++) {
            acceptedQtdValue += i;
        }
        this.fixedGeneration = fixedGeneration;
    }

//constructor
    public AnyProblemOnlineLearning(int runNo, ProblemCreator problemCreator, int qtdAlgs) {
        algorithms=Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        folderPath = "HF_Config_Benchmark/Results/" + problemCreator.getProblemClass() + "/AM";
        configFile = "HF_Config_Benchmark/" + problemCreator.getProblemClass() + "ProblemSetting.txt";
        RewardPenaltyLA RPLA = new RewardPenaltyLA(configFile);
        al_ = RPLA;
        sl_ = rs;
        pl_ = ps;
        numberOfLLH_ = algorithms.length;
        llhs = new LLH[numberOfLLH_];
        for (int i = 0; i < numberOfLLH_; i++) {
            //llhs.add(new LLH(numberOfLLH_, al_));
            llhs[i] = new LLH(numberOfLLH_, al_, sl_, i);
        }
        try {
            config.load(new FileInputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load configuration file!");
            System.exit(-1);
        }
        populationSize = Integer.parseInt(config.getProperty("PopulationSize"));
        alpha = 0.1;
        //alpha = Double.parseDouble(config.getProperty("alpha"));
        util_ = new LAutil();
        runIndex = runNo;

        /*		for(int i = 0; i < numberOfObj; i++){
			  reference[i] = 2 *(i+1) +1;
		   }*/
        heuristicList = new ArrayList<Integer>();
        for (int i = 0; i < algorithms.length; i++) {
            acceptedQtdValue += i;
        }
    }

    public List<S> runLA(HashMap<String, String> parameterSet) throws IOException, ClassNotFoundException, JMException, ConfigurationException {

        int fixedSolutionEvl = fixedGeneration * populationSize;
        MetricsUtil util = new MetricsUtil();

        int totalEval = Integer.parseInt(config.getProperty("MaxEvaluations"));
        int arcSize = Integer.parseInt(config.getProperty("ArchiveSize"));
        String SolutionType = config.getProperty("SolutionType");
        int popSize = Integer.parseInt(config.getProperty("PopulationSize"));

        double learningPhase = Double.parseDouble(parameterSet.get("LearningPhase"));
        int maximumIterations = Integer.parseInt(parameterSet.get("MaximIterations"));
        double hyperImproveLimit = Double.parseDouble(parameterSet.get("HypvolumLimit"));

        Operator crossover = null; 	// Crossover operator
        Operator selection = null; 	// Selection operator
        HashMap parameters = null; // Operator parameters

        parameters = new HashMap();
        // Operators
        //String mutationTypeStr = config.getProperty("MutationType");		

        //String crossoverTypeStr = config.getProperty("CrossoverType");
        Double crossoverProb = Double.parseDouble(config.getProperty("CrossoverProb"));

        parameters.put("probability", crossoverProb);
        Double crossoverDistributionIndex = Double.parseDouble(config.getProperty("CrossoverDistributionIndex"));

        parameters.put("distributionIndex", crossoverDistributionIndex);
        String crossoverType = config.getProperty("CrossoverType");
        crossover = new SBXCrossover(crossoverProb, crossoverDistributionIndex);

        //GDE3 operators
        parameters = new HashMap();
        parameters.put("probability", crossoverProb);
        parameters.put("CR", 0.2);
        parameters.put("F", 0.2);
        parameters.put("K", 0.5);
        parameters.put("DE_VARIANT", "rand/1/bin");
        Operator crossoverGDE3 = new DifferentialEvolutionCrossover(0.2, 0.2, 0.5, "rand/1/bin");
        parameters = null;
        Operator selectionGDE3 = new DifferentialEvolutionSelection();
        //GDE3 operators

        // Selection Operator
        parameters = null;
        String selectionType = config.getProperty("SelectionType");

        selection = new BinaryTournamentSelection();

        Problem[] problemInstances = new Problem[problemCreator.getQtdProblem()];
        int[] numberOfVar = new int[problemCreator.getQtdProblem()];
        double[] mutationProb = new double[problemCreator.getQtdProblem()];
        Operator[] mutations = new Operator[problemCreator.getQtdProblem()];
        //reference point for hypervolume calculation
        /*HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();*/
 /*double[] truePFhypervolume = new double[problemCreator.getQtdProblem()];*/
        for (int problemIndex = 0; problemIndex < problemCreator.getQtdProblem(); problemIndex++) {

            problemInstances[problemIndex] = problemCreator.getProblemInstance(problemIndex);
            numberOfVar[problemIndex] = problemInstances[problemIndex].getNumberOfVariables();
            mutationProb[problemIndex] = (Double) 1.0 / numberOfVar[problemIndex];
            parameters = new HashMap();
            parameters.put("probability", mutationProb[problemIndex]);
            Double mutationDistributionIndex = Double.parseDouble(config.getProperty("MutationDistributionIndex"));
            parameters.put("distributionIndex", mutationDistributionIndex);
            String mutationType = config.getProperty("MutationType");
            mutations[problemIndex] = new PolynomialMutation(mutationProb[problemIndex], mutationDistributionIndex);
        }

        LLHInterface[] algorithm = new LLHInterface[problemCreator.getQtdProblem()];

        int totalFixedGeneration = totalEval / fixedSolutionEvl;
        double[] realHypervolume = new double[problemInstances.length];
        HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();

        for (int instanceIndex = 0; instanceIndex < problemInstances.length; instanceIndex++) {
            fixedSolutionEvl = fixedGeneration * populationSize;
            System.out.println(problemInstances[instanceIndex].getName() + " is running...");
            numberOfObj = problemInstances[instanceIndex].getNumberOfObjectives();

            reference = new double[numberOfObj];
            for (int i = 0; i < numberOfObj; i++) {
                reference[i] = 1.0;
            }

            minimumValues = new double[numberOfObj];
            maximumValues = new double[numberOfObj];

            //random chose the first heuristic to start with
            List<S> currentPop = null;
            List<S> inputPop = ProblemCreator.generateInitialPopulation(problemInstances[instanceIndex], popSize);
            List<S> initialPop = null;

            ArrayList<Double> hypervolumeList = new ArrayList<Double>();

            boolean isInitialIteration = true;

            ArrayList<Double> recordHypList = new ArrayList<Double>();
            ArrayList<Double> currentHypList = new ArrayList<Double>();
            ArrayList<Integer> betaValue = new ArrayList<Integer>();
            double bestHyp;
            ArrayList<Double[]> selectioPro = new ArrayList<Double[]>();
            ArrayList<Double[]> updatedPro = new ArrayList<Double[]>();
            ArrayList<Double[]> updatedProNeg = new ArrayList<Double[]>();
            ArrayList<Double[]> rewardUpdate = new ArrayList<Double[]>();
            ArrayList<Double[]> QvalueList = new ArrayList<Double[]>();

            int chosenHeuristic = -1;
            double[] tabuList = new double[numberOfLLH_];
            for (int i = 0; i < numberOfLLH_; i++) {
                tabuList[i] = 0.0;
            }

            for (int i = 0; i < numberOfLLH_; i++) {
                llhs[i].intialiseTransPro(numberOfLLH_);
                llhs[i].intialiseQvalue(numberOfLLH_);
                llhs[i].intialiseExecutedNo();
                llhs[i].initialiseUtility();
                llhs[i].intialiseReward();
                llhs[i].intialistransTimes(numberOfLLH_);
                llhs[i].intialisetransferReward(numberOfLLH_);
                llhs[i].intialiseSuccessTimes();
            }
            heuristicList.clear();
            ArrayList<Double> successorProArray = new ArrayList<Double>(llhs.length);
            ArrayList<Double> transitionIntoProbArray = new ArrayList<Double>(llhs.length);
            for (int i = 0; i < llhs.length; i++) {
                successorProArray.add(1.0);
                transitionIntoProbArray.add(0.0);
            }
            int[] rankOfAlgorithms = new int[numberOfLLH_];
            for (int i = 0; i < numberOfLLH_; i++) {
                rankOfAlgorithms[i] = -1;
            }

            int remainEval = totalEval;
            chosenHeuristic = util_.showRandomInteger(0, numberOfLLH_ - 1);
            heuristicList.add(chosenHeuristic);//the initial choice

            int precessorHeuristic = chosenHeuristic;

            ArrayList<Integer> executedGeneration = new ArrayList<Integer>();
            ArrayList<Double> hypImprovementList = new ArrayList<Double>();

            int tempExecutedGen = 0;
            double hypImprovement = Double.MAX_VALUE;
            int iteration = 0;

            AlgorithmCreator ac = new AlgorithmCreator(problemInstances[instanceIndex]);
            ac.setMaxEvaluationsAndPopulation(totalEval, populationSize);
            algorithm[instanceIndex] = ac.create(chosenHeuristic, remainEval);

            // Execute the Algorithm		
            try {
                currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl);
            } catch (Exception e) {
//				} catch (ClassNotFoundException | JMException e) {
                e.printStackTrace();
            }
            inputPop = currentPop;
            //inputPop = getTheBestPopulation(currentPop, inputPop);	
            tempExecutedGen += fixedGeneration;
            iteration++;

            double currentHypervolume = computeScaledHypervolume(inputPop, isInitialIteration);
            bestHyp = 0.0;
            double initialHyp = 0.0;

            isInitialIteration = false;
            remainEval = totalEval - fixedSolutionEvl;
            recordHypList.add(currentHypervolume);
            currentHypList.add(currentHypervolume);

            hypervolumeList.add(currentHypervolume);
            //execute the chosen heuristic till convergence criteria satisfied
            hypImprovement = Double.MAX_VALUE;
            double previousHyp = 0.0;

            while (hypImprovement >= hyperImproveLimit && tempExecutedGen < maximumIterations) {

                currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl);;
                iteration++;
                remainEval -= fixedSolutionEvl;
                inputPop = currentPop;
                //inputPop = getTheBestPopulation(currentPop, inputPop);
                currentHypervolume = computeScaledHypervolume(currentPop, isInitialIteration);

                /*			if(currentHypervolume > bestHyp){
						bestHyp = currentHypervolume;
						recordHypList.add(currentHypervolume);
						recordPop =  currentPop;
					}*/
                previousHyp = currentHypList.get(currentHypList.size() - 1);
                hypImprovement = (currentHypervolume - previousHyp) / previousHyp;
                tempExecutedGen += fixedGeneration;
                currentHypList.add(currentHypervolume);
            }

            //recordPop =  currentPop;
            double hypImprovementFinal = 0.0;
            //hypImprovementFinal = (currentHypervolume - initialHyp)/initialHyp;
            initialPop = inputPop;
            initialHyp = currentHypervolume;
            bestHyp = currentHypervolume;

            executedGeneration.add(tempExecutedGen);
            //hypImprovmentList is used to store the hypervolume improvement between two heuristics.
            //hypImprovementList.add(hypImprovementFinal);
            //reset the temple executed generation and hypImprovement
            tempExecutedGen = 0;
            hypImprovement = Double.MAX_VALUE;
            for (int i = 0; i < numberOfLLH_; i++) {
                Double[] tempRewardUpdate = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
                rewardUpdate.add(tempRewardUpdate);
            }

            //double InitialTemperature = 10.0;
            //double Temperature = InitialTemperature;
            //double decayFactor = Math.pow((1.0/InitialTemperature), 1.0/(totalFixedGeneration/2));
            //ArrayList<Double> temperatureList = new ArrayList<Double>();
            //temperatureList.add(Temperature);
            double epsilon = 0.0;
            while (remainEval > 0) {
                int nextHeuristic = -1;
                // for slection method that is not softmax
                //nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].transPro_);
                //if it is softMaxSelection
                int tempIteration = (int) (totalFixedGeneration * learningPhase);
                if (iteration < tempIteration) {

                    nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].getTransPro_(), epsilon);

                    //nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].transPro_);
                } else {
                    epsilon = learningPhase + (1.0 - learningPhase) / totalFixedGeneration * iteration;

                    nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].getTransPro_(), epsilon);

                }
                if (nextHeuristic == -1) {
                    System.out.println("Not legal next heuristic!");
                }
                double tempTransTimes = llhs[precessorHeuristic].getTransTimes().get(nextHeuristic) + 1.0;
                llhs[precessorHeuristic].getTransTimes().set(nextHeuristic, tempTransTimes);

                //half of the total iterations, we go greedy
                for (int i = 0; i < numberOfLLH_; i++) {
                    Double[] selectionProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                    Double[] QvalueTemp = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
                    selectioPro.add(selectionProTemp);
                    QvalueList.add(QvalueTemp);
                    if (QvalueList.size() != selectioPro.size()) {
                        System.out.println("Stop and debug!");
                    }
                }

                heuristicList.add(nextHeuristic);
                llhs[nextHeuristic]
                        .setCalledTimes(llhs[nextHeuristic].getCalledTimes() + 1);

                algorithm[instanceIndex] = ac.create(nextHeuristic, remainEval);

                while (hypImprovement > hyperImproveLimit && tempExecutedGen < maximumIterations && remainEval > 0) {//the current heuristic has potential to improve
                    //the indicator further, but not giving it too many interations for the sake of trying other heuristic

                    int feasableSolutionEval = fixedSolutionEvl;

                    if (remainEval < fixedSolutionEvl) {
                        feasableSolutionEval = remainEval;
                    }

                    currentPop = algorithm[instanceIndex].execute(inputPop,/*fixedSolutionEvl*/ feasableSolutionEval); // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    inputPop = currentPop;
                    //inputPop = getTheBestPopulation(currentPop, inputPop);
                    iteration++;
                    remainEval -= feasableSolutionEval;

                    if (remainEval == 0) {
                        System.out.println("Remain solution evl is 0. Iteration is " + iteration);
                    }

                    currentHypervolume = computeScaledHypervolume(inputPop, isInitialIteration);

                    previousHyp = currentHypList.get(currentHypList.size() - 1);

                    currentHypList.add(currentHypervolume);

                    /*						if(currentHypervolume > bestHyp){
								bestHyp = currentHypervolume;
								recordHypList.add(currentHypervolume);
								recordPop =  currentPop;
								
							}*/
 /*hypImprovement = currentHypervolume - previousHyp;*/
                    hypImprovement = (currentHypervolume - previousHyp) / previousHyp;
                    tempExecutedGen += fixedGeneration;

                }

                hypImprovementFinal = currentHypervolume - initialHyp/*Math.abs((currentHypervolume - initialHyp)/initialHyp)*/;

                double tempTransferReward = llhs[precessorHeuristic].getTransferReward().get(nextHeuristic) + hypImprovementFinal;
                llhs[precessorHeuristic].getTransferReward().set(nextHeuristic, tempTransferReward);

                /*				Temperature = Math.pow(decayFactor, iteration) * InitialTemperature;
					temperatureList.add(Temperature);*/
                if (precessorHeuristic < 0 || precessorHeuristic > algorithms.length - 1) {
                    System.out.println("Invalid precessorHeuristic index!" + precessorHeuristic);
                    System.exit(0);
                }
                if (nextHeuristic < 0 || nextHeuristic > algorithm.length - 1) {
                    System.out.println("Invalid nextHeuristic index!" + nextHeuristic);
                    System.exit(0);
                }

                try {

                    //double tempQvalue = (llhs[precessorHeuristic].transferReward.get(nextHeuristic))/llhs[precessorHeuristic].transTimes.get(nextHeuristic);
                    double tempQvalue = (hypImprovementFinal - llhs[precessorHeuristic].getQvalue().get(nextHeuristic)) * alpha + llhs[precessorHeuristic].getQvalue().get(nextHeuristic);

                    /*					if(tempQvalue <0.0){
							tempQvalue = 0.0;
							System.out.println("Negative tempQvalue:" + tempQvalue);
							System.exit(0);
						}*/
                    llhs[precessorHeuristic].getQvalue().set(nextHeuristic, tempQvalue);
                    //llhs[nextHeuristic].Qvalue.set(precessorHeuristic, tempQvalue);//we see hi to hj is equalvilant to hj to hi

                    for (int i = 0; i < numberOfLLH_; i++) {
                        Double[] tempRewardUpdate = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
                        rewardUpdate.add(tempRewardUpdate);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);
                }

                executedGeneration.add(tempExecutedGen);

                //hypImprovmentList is used to store the hypervolume improvement between two heuristics.
                hypImprovementList.add(hypImprovementFinal);
                llhs[nextHeuristic].setExecutedNo(
                        llhs[nextHeuristic].getExecutedNo() + tempExecutedGen);
                llhs[nextHeuristic]
                        .setReward(llhs[nextHeuristic].getReward() + hypImprovementFinal);
                llhs[nextHeuristic].setUtility(llhs[nextHeuristic].getReward() / llhs[nextHeuristic].getExecutedNo());

                //reset the temple executed generation and hypImprovement
                tempExecutedGen = 0;
                hypImprovement = Double.MAX_VALUE;
                //if initialHyp is improved then 1, otherwise 0;
                int beta = rewardSignalFeedback(currentHypervolume, initialHyp);
                betaValue.add(beta);

                initialHyp = currentHypervolume;

                double probSum = 0.0;
                for (double probEach : llhs[precessorHeuristic].getTransPro_()) {
                    probSum += probEach;
                }
                if (beta == 1) {// update the probability of the previous heuristic's probabilities of transition to the chosen heuristic and other heuristics
//						rejectList.clear();
//						rejectCount = 1;

                    //al_.updateProbabilityPositiveSignal(nextHeuristic, llhs[precessorHeuristic].transPro_);
                    llhs[nextHeuristic].setSuccessTimes(llhs[nextHeuristic].getSuccessTimes() + 1);
                    if (probSum > 1.001 || probSum < 0.99) {
                        System.out.println("The sum of probability is not equal to 1.0 for postive signal.");
                    }
                    al_.updateProbabilityPositiveSignalQ(nextHeuristic, llhs[precessorHeuristic].getTransPro_(), llhs[precessorHeuristic].getQvalue());
                    double tempPro = llhs[precessorHeuristic].getTransPro_().get(nextHeuristic);

                    for (int i = 0; i < numberOfLLH_; i++) {
                        Double[] updateProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                        updatedPro.add(updateProTemp);
                    }

                    precessorHeuristic = nextHeuristic;
                } else if (beta == 0) {
                    if (probSum > 1.001 || probSum < 0.99) {
                        System.out.println("The sum of probability is not equal to 1.0 for negative signal.");
                    }
                    al_.updateProbabilityNegativeSignalQ(nextHeuristic, llhs.length, llhs[precessorHeuristic].getTransPro_(), llhs[precessorHeuristic].getQvalue());

                    double tempPro = llhs[precessorHeuristic].getTransPro_().get(nextHeuristic);

                    Double[] updateProTemp = llhs[precessorHeuristic].getTransPro_().toArray(new Double[numberOfLLH_]);
                    updatedPro.add(updateProTemp);

                    for (int i = 0; i < numberOfLLH_; i++) {
                        Double[] updateProNegTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                        updatedProNeg.add(updateProNegTemp);
                    }
                    precessorHeuristic = nextHeuristic;
                }
                llhs[nextHeuristic].setSuccessProb((double) llhs[nextHeuristic].getSuccessTimes() / (double) llhs[nextHeuristic].getCalledTimes());

            }
            String arrayListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_ChosenHeuristic.txt";
            utils_.printArrayListInteger(heuristicList, arrayListPath);
            double[][] successProbStatistic = new double[4][algorithms.length];
            int[] selectedTimes = new int[algorithms.length];
            for (int heuristicListIndex = 1; heuristicListIndex < heuristicList.size(); heuristicListIndex++) {
                for (int algorithmIndex = 0; algorithmIndex < algorithms.length; algorithmIndex++) {
                    if (heuristicList.get(heuristicListIndex) == algorithmIndex) {
                        selectedTimes[algorithmIndex]++;
                    }
                }

            }
            double[] transferIntoProb = transferIntoProbility();
            //success times/pro and transferInto pro
            for (int i = 0; i < algorithms.length; i++) {
                successProbStatistic[0][i] = (double) llhs[i].getSuccessTimes();
                successProbStatistic[1][i] = (double) selectedTimes[i];
                successProbStatistic[2][i] = successProbStatistic[0][i] / successProbStatistic[1][i];
                successProbStatistic[3][i] = transferIntoProb[i];
            }

            String successProStatisticsPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_successStatis.txt";
            utils_.writeFront(successProStatisticsPath, successProbStatistic);
            String betaValueListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rewardList.txt";
            utils_.printArrayListInteger(betaValue, betaValueListPath);
            /*			String tabuListPath = folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_tabuList.txt";
				utils_.printDoubleArray(tabuList, tabuListPath);*/

            //print selection probability
            String SelectionProPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_SelectionProList.txt";
            utils_.printArrayListDoubleArray(selectioPro, SelectionProPath);

            String Qvaluepath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_QvalueList.txt";
            utils_.printArrayListDoubleArray(QvalueList, Qvaluepath);
            //print updated probability
            /*			String UpdatedProPath = folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_UpdatePostiveProList.txt";
				utils_.printArrayListDoubleArray(updatedPro,UpdatedProPath);
				String UpdatedNegProPath = folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_UpdateNegativeProList.txt";
				utils_.printArrayListDoubleArray(updatedProNeg,UpdatedNegProPath);*/

            String rewardUpdatePath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rewardUpdate.txt";
            utils_.printArrayListDoubleArray(rewardUpdate, rewardUpdatePath);
            //print reject heuristic and times of rejection
            //String rejectRecordPath = objConfig + "/LAResutls/run_"+runIndex+"/RejectList.txt";
            //utils_.printArrayListArraylist(rejectRecord,rejectRecordPath);
            //print recorded hypervolume
            String hypImprovementListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_hypImprovementList.txt";
            utils_.printArrayListDouble(hypImprovementList, hypImprovementListPath);

            String executedGenerationPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_executedGeneration.txt";
            utils_.printArrayListInteger(executedGeneration, executedGenerationPath);

            //print current hypervolume
            String currentHypListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_currentHypList.txt";
            utils_.printArrayListDouble(currentHypList, currentHypListPath);
            //print threshold
            /*			String thresholdPath = folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_threshold.txt";
				utils_.printArrayListDouble(threshold, thresholdPath);*/
 /*			String temperaturePath =  folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_temperature.txt";
				utils_.printArrayListDouble(temperatureList, temperaturePath); */
//				inputPop.printObjectivesToFile(objConfig+"/LAResutls/run_"+runIndex+"/"+"LA_FinalPopulationObj_"+runIndex+".txt");

            List<S> obtainedSolutionSet = SolutionListUtils.getNondominatedSolutions(inputPop);
            new SolutionListOutput(obtainedSolutionSet)
                    .setSeparator("\t")
                    .setFunFileOutputContext(new DefaultFileOutputContext(folderPath + "/run_" + runIndex + "/" + "LA_FinalParetoFront_Real" + (instanceIndex + 1) + "_Run" + runIndex + ".txt"))
                    .print();

            ArrayList<Double[]> finalPro = new ArrayList<Double[]>();
            ArrayList<Double[]> finalTransTimes = new ArrayList<Double[]>();
            for (int i = 0; i < numberOfLLH_; i++) {
                Double[] finalProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                finalPro.add(finalProTemp);
                Double[] finalTransTemp = llhs[i].getTransTimes().toArray(new Double[numberOfLLH_]);
                finalTransTimes.add(finalTransTemp);
            }
            String finalProPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_finalProList.txt";
            utils_.printArrayListDoubleArray(finalPro, finalProPath);
            String finaltransTimesPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_finalTransTimes.txt";
            utils_.printArrayListDoubleArray(finalTransTimes, finaltransTimesPath);
            /*
            if(problemInstances[instanceIndex] instanceof ExternalProblem){
                System.out.println(((ExternalProblem) problemInstances[instanceIndex]).getQtdEvaluated());
            }
            */
        }//for each instanceIndex

        /*		String hypervolumePath = folderPath + "/LAResutls/run_"+runIndex+"/hypervolume.txt";
			utils_.printArray(realHypervolume, hypervolumePath);*/
        return null;

    }

    @Override
    public List<S> runLA() throws IOException, ClassNotFoundException, JMException {
        // TODO Auto-generated method stub

        int fixedSolutionEvl = fixedGeneration * populationSize;
        int totalEval = Integer.parseInt(config.getProperty("MaxEvaluations"));
        int popSize = Integer.parseInt(config.getProperty("PopulationSize"));
        double learningPhase = Double.parseDouble(config.getProperty("LearningPhase"));
        int maximumIterations = Integer.parseInt(config.getProperty("MaximumIterations"));
        double hyperImproveLimit = Double.parseDouble(config.getProperty("HypImprovment"));
        //System.out.println("learningPhase: "+ learningPhase+ " maximumIterations: " + maximumIterations+ " hyperImproveLimit: " + hyperImproveLimit);
        Double crossoverProb = Double.parseDouble(config.getProperty("CrossoverProb"));
        Double crossoverDistributionIndex = Double.parseDouble(config.getProperty("CrossoverDistributionIndex"));
        String crossoverType = config.getProperty("CrossoverType");
        String selectionType = config.getProperty("SelectionType");
        Double mutationDistributionIndex = Double.parseDouble(config.getProperty("MutationDistributionIndex"));
        String mutationType = config.getProperty("MutationType");
        
        if(config.getProperty("DistanceParameters")!=null){
            int distancePara = Integer.parseInt(config.getProperty("DistanceParameters"));//l
            int positionPara = Integer.parseInt(config.getProperty("PositionParameters"));//k
            numberOfObj = Integer.parseInt(config.getProperty("ObjectiveNumber"));//k
            problemCreator.addParam("l", distancePara);
            problemCreator.addParam("k" , positionPara);
            problemCreator.addParam("m", numberOfObj);
        }

        Problem[] problemInstances = new Problem[problemCreator.getQtdProblem()];
        int[] numberOfVar = new int[problemCreator.getQtdProblem()];
        double[] mutationProb = new double[problemCreator.getQtdProblem()];
        Operator[] mutations = new Operator[problemCreator.getQtdProblem()];

        //reference point for hypervolume calculation
        /*HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();*/
 /*double[] truePFhypervolume = new double[problemCreator.getQtdProblem()];*/
        for (int problemIndex = 0; problemIndex < problemCreator.getQtdProblem(); problemIndex++) {
            problemInstances[problemIndex] = problemCreator.getProblemInstance(problemIndex);
            numberOfVar[problemIndex] = problemInstances[problemIndex].getNumberOfVariables();
            mutationProb[problemIndex] = (Double) 1.0 / numberOfVar[problemIndex];
            mutations[problemIndex] = new PolynomialMutation(mutationProb[problemIndex], mutationDistributionIndex);
        }

        LLHInterface[] algorithm = new LLHInterface[problemCreator.getQtdProblem()];

        int totalFixedGeneration = totalEval / fixedSolutionEvl;
        double[] realHypervolume = new double[problemInstances.length];
        HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();

        for (int instanceIndex = 0; instanceIndex < problemInstances.length; instanceIndex++) {
            System.out.println(problemInstances[instanceIndex].getName() + " is running...");

            numberOfObj = problemInstances[instanceIndex].getNumberOfObjectives();

            reference = new double[numberOfObj];
            for (int i = 0; i < numberOfObj; i++) {
                reference[i] = 1.0;
            }

            minimumValues = new double[numberOfObj];
            maximumValues = new double[numberOfObj];

            //random chose the first heuristic to start with
            List<S> currentPop = null;
            List<S> inputPop = ProblemCreator.generateInitialPopulation(problemInstances[instanceIndex], popSize);
            List<S> initialPop = null;

            ArrayList<Double> hypervolumeList = new ArrayList<Double>();

            boolean isInitialIteration = true;

            ArrayList<Double> recordHypList = new ArrayList<Double>();
            ArrayList<Double> currentHypList = new ArrayList<Double>();
            ArrayList<Integer> betaValue = new ArrayList<Integer>();
            double bestHyp;
            ArrayList<Double[]> selectioPro = new ArrayList<Double[]>();
            ArrayList<Double[]> updatedPro = new ArrayList<Double[]>();
            ArrayList<Double[]> updatedProNeg = new ArrayList<Double[]>();
            ArrayList<Double[]> rewardUpdate = new ArrayList<Double[]>();
            ArrayList<Double[]> QvalueList = new ArrayList<Double[]>();

            int chosenHeuristic = -1;
            double[] tabuList = new double[numberOfLLH_];
            for (int i = 0; i < numberOfLLH_; i++) {
                tabuList[i] = 0.0;
            }

            for (int i = 0; i < numberOfLLH_; i++) {
                llhs[i].intialiseTransPro(numberOfLLH_);
                llhs[i].intialiseQvalue(numberOfLLH_);
                llhs[i].intialiseExecutedNo();
                llhs[i].initialiseUtility();
                llhs[i].intialiseReward();
                llhs[i].intialistransTimes(numberOfLLH_);
                llhs[i].intialisetransferReward(numberOfLLH_);
                llhs[i].intialiseSuccessTimes();
            }
            heuristicList.clear();
            ArrayList<Double> successorProArray = new ArrayList<Double>(llhs.length);
            ArrayList<Double> transitionIntoProbArray = new ArrayList<Double>(llhs.length);
            for (int i = 0; i < llhs.length; i++) {
                successorProArray.add(1.0);
                transitionIntoProbArray.add(0.0);
            }
            int[] rankOfAlgorithms = new int[numberOfLLH_];
            for (int i = 0; i < numberOfLLH_; i++) {
                rankOfAlgorithms[i] = -1;
            }

            int remainEval = totalEval;
            chosenHeuristic = util_.showRandomInteger(0, numberOfLLH_ - 1);
            heuristicList.add(chosenHeuristic);//the initial choice

            int precessorHeuristic = chosenHeuristic;

            ArrayList<Integer> executedGeneration = new ArrayList<Integer>();
            ArrayList<Double> hypImprovementList = new ArrayList<Double>();

            int tempExecutedGen = 0;
            double hypImprovement = Double.MAX_VALUE;
            int iteration = 0;

            AlgorithmCreator ac = new AlgorithmCreator(problemInstances[instanceIndex]);
            ac.setMaxEvaluationsAndPopulation(totalEval, populationSize);
            algorithm[instanceIndex] = ac.create(chosenHeuristic, remainEval);
            // Execute the Algorithm		
            try {
                currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl);
            } catch (Exception e) {
//			} catch (ClassNotFoundException | JMException e) {
                e.printStackTrace();
            }
            inputPop = currentPop;
            //inputPop = getTheBestPopulation(currentPop, inputPop);	
            tempExecutedGen += fixedGeneration;
            iteration++;

            double currentHypervolume = computeScaledHypervolume(inputPop, isInitialIteration);
            bestHyp = 0.0;
            double initialHyp = 0.0;

            isInitialIteration = false;
            remainEval = totalEval - fixedSolutionEvl;
            recordHypList.add(currentHypervolume);
            currentHypList.add(currentHypervolume);

            hypervolumeList.add(currentHypervolume);
            //execute the chosen heuristic till convergence criteria satisfied
            hypImprovement = Double.MAX_VALUE;
            double previousHyp = 0.0;

            while (hypImprovement >= hyperImproveLimit && tempExecutedGen < maximumIterations) {

                currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl);;
                iteration++;
                remainEval -= fixedSolutionEvl;
                inputPop = currentPop;
                //inputPop = getTheBestPopulation(currentPop, inputPop);
                currentHypervolume = computeScaledHypervolume(currentPop, isInitialIteration);

                /*			if(currentHypervolume > bestHyp){
					bestHyp = currentHypervolume;
					recordHypList.add(currentHypervolume);
					recordPop =  currentPop;
				}*/
                previousHyp = currentHypList.get(currentHypList.size() - 1);
                hypImprovement = (currentHypervolume - previousHyp) / previousHyp;
                tempExecutedGen += fixedGeneration;
                currentHypList.add(currentHypervolume);
            }

            //recordPop =  currentPop;
            double hypImprovementFinal = 0.0;
            //hypImprovementFinal = (currentHypervolume - initialHyp)/initialHyp;
            initialPop = inputPop;
            initialHyp = currentHypervolume;
            bestHyp = currentHypervolume;

            executedGeneration.add(tempExecutedGen);
            //hypImprovmentList is used to store the hypervolume improvement between two heuristics.
            //hypImprovementList.add(hypImprovementFinal);
            //reset the temple executed generation and hypImprovement
            tempExecutedGen = 0;
            hypImprovement = Double.MAX_VALUE;
            for (int i = 0; i < numberOfLLH_; i++) {
                Double[] tempRewardUpdate = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
                rewardUpdate.add(tempRewardUpdate);
            }

            //double InitialTemperature = 10.0;
            //double Temperature = InitialTemperature;
            //double decayFactor = Math.pow((1.0/InitialTemperature), 1.0/(totalFixedGeneration/2));
            //ArrayList<Double> temperatureList = new ArrayList<Double>();
            //temperatureList.add(Temperature);
            double epsilon = 0.0;
            while (remainEval > 0) {
                int nextHeuristic = -1;
                // for slection method that is not softmax
                //nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].transPro_);
                //if it is softMaxSelection
                int tempIteration = (int) (totalFixedGeneration * learningPhase);
                if (iteration < tempIteration) {

                    nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].getTransPro_(), epsilon);

                    //nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].transPro_);
                } else {
                    epsilon = learningPhase + (1.0 - learningPhase) / totalFixedGeneration * iteration;

                    nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].getTransPro_(), epsilon);

                }
                if (nextHeuristic == -1) {
                    System.out.println("Not legal next heuristic!");
                }
                double tempTransTimes = llhs[precessorHeuristic].getTransTimes().get(nextHeuristic) + 1.0;
                llhs[precessorHeuristic].getTransTimes().set(nextHeuristic, tempTransTimes);

                //half of the total iterations, we go greedy
                for (int i = 0; i < numberOfLLH_; i++) {
                    Double[] selectionProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                    Double[] QvalueTemp = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
                    selectioPro.add(selectionProTemp);
                    QvalueList.add(QvalueTemp);
                    if (QvalueList.size() != selectioPro.size()) {
                        System.out.println("Stop and debug!");
                    }
                }

                heuristicList.add(nextHeuristic);
                llhs[nextHeuristic]
                        .setCalledTimes(llhs[nextHeuristic].getCalledTimes() + 1);

                algorithm[instanceIndex] = ac.create(nextHeuristic, remainEval);

                /*			    // Execute the Algorithm		
				try {
					currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl);
				} catch (Exception e) {
//				} catch (ClassNotFoundException | JMException e) {
					e.printStackTrace();
				}

				
				inputPop = currentPop;
				iteration++;
				tempExecutedGen += fixedGeneration;
			
				remainEval -= fixedSolutionEvl;
				
				currentHypervolume = computeScaledHypervolume(currentPop,isInitialIteration);
				currentHypList.add(currentHypervolume);*/
                while (hypImprovement > hyperImproveLimit && tempExecutedGen < maximumIterations && remainEval > 0) {//the current heuristic has potential to improve
                    //the indicator further, but not giving it too many interations for the sake of trying other heuristic

                    int feasableSolutionEval = fixedSolutionEvl;

                    if (remainEval < fixedSolutionEvl) {
                        feasableSolutionEval = remainEval;
                    }

                    currentPop = algorithm[instanceIndex].execute(inputPop,/*fixedSolutionEvl*/ feasableSolutionEval); // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    inputPop = currentPop;
                    //inputPop = getTheBestPopulation(currentPop, inputPop);
                    iteration++;
                    remainEval -= feasableSolutionEval;

                    if (remainEval == 0) {
                        System.out.println("Remain solution evl is 0. Iteration is " + iteration);
                    }

                    currentHypervolume = computeScaledHypervolume(inputPop, isInitialIteration);

                    previousHyp = currentHypList.get(currentHypList.size() - 1);

                    currentHypList.add(currentHypervolume);

                    /*						if(currentHypervolume > bestHyp){
							bestHyp = currentHypervolume;
							recordHypList.add(currentHypervolume);
							recordPop =  currentPop;
							
						}*/
 /*hypImprovement = currentHypervolume - previousHyp;*/
                    hypImprovement = (currentHypervolume - previousHyp) / previousHyp;
                    tempExecutedGen += fixedGeneration;

                }

                hypImprovementFinal = currentHypervolume - initialHyp/*Math.abs((currentHypervolume - initialHyp)/initialHyp)*/;

                double tempTransferReward = llhs[precessorHeuristic].getTransferReward().get(nextHeuristic) + hypImprovementFinal;
                llhs[precessorHeuristic].getTransferReward().set(nextHeuristic, tempTransferReward);

                /*				Temperature = Math.pow(decayFactor, iteration) * InitialTemperature;
				temperatureList.add(Temperature);*/
                if (precessorHeuristic < 0 || precessorHeuristic > algorithms.length - 1) {
                    System.out.println("Invalid precessorHeuristic index!" + precessorHeuristic);
                    System.exit(0);
                }
                if (nextHeuristic < 0 || nextHeuristic > algorithms.length - 1) {
                    System.out.println("Invalid nextHeuristic index!" + nextHeuristic);
                    System.exit(0);
                }

                try {

                    //double tempQvalue = (llhs[precessorHeuristic].transferReward.get(nextHeuristic))/llhs[precessorHeuristic].transTimes.get(nextHeuristic);
                    double tempQvalue = (hypImprovementFinal - llhs[precessorHeuristic].getQvalue().get(nextHeuristic)) * alpha + llhs[precessorHeuristic].getQvalue().get(nextHeuristic);

                    /*					if(tempQvalue <0.0){
						tempQvalue = 0.0;
						System.out.println("Negative tempQvalue:" + tempQvalue);
						System.exit(0);
					}*/
                    llhs[precessorHeuristic].getQvalue().set(nextHeuristic, tempQvalue);
                    //llhs[nextHeuristic].Qvalue.set(precessorHeuristic, tempQvalue);//we see hi to hj is equalvilant to hj to hi

                    for (int i = 0; i < numberOfLLH_; i++) {
                        Double[] tempRewardUpdate = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
                        rewardUpdate.add(tempRewardUpdate);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);
                }

                executedGeneration.add(tempExecutedGen);

                //hypImprovmentList is used to store the hypervolume improvement between two heuristics.
                hypImprovementList.add(hypImprovementFinal);
                llhs[nextHeuristic].setExecutedNo(
                        llhs[nextHeuristic].getExecutedNo() + tempExecutedGen);
                llhs[nextHeuristic]
                        .setReward(llhs[nextHeuristic].getReward() + hypImprovementFinal);
                llhs[nextHeuristic].setUtility(llhs[nextHeuristic].getReward() / llhs[nextHeuristic].getExecutedNo());

                //reset the temple executed generation and hypImprovement
                tempExecutedGen = 0;
                hypImprovement = Double.MAX_VALUE;
                //if initialHyp is improved then 1, otherwise 0;
                int beta = rewardSignalFeedback(currentHypervolume, initialHyp);
                betaValue.add(beta);

                initialHyp = currentHypervolume;

                double probSum = 0.0;
                for (double probEach : llhs[precessorHeuristic].getTransPro_()) {
                    probSum += probEach;
                }
                if (beta == 1) {// update the probability of the previous heuristic's probabilities of transition to the chosen heuristic and other heuristics
//					rejectList.clear();
//					rejectCount = 1;

                    //al_.updateProbabilityPositiveSignal(nextHeuristic, llhs[precessorHeuristic].transPro_);
                    llhs[nextHeuristic].setSuccessTimes(llhs[nextHeuristic].getSuccessTimes() + 1);
                    if (probSum > 1.001 || probSum < 0.99) {
                        System.out.println("The sum of probability is not equal to 1.0 for postive signal.");
                    }
                    al_.updateProbabilityPositiveSignalQ(nextHeuristic, llhs[precessorHeuristic].getTransPro_(), llhs[precessorHeuristic].getQvalue());
                    double tempPro = llhs[precessorHeuristic].getTransPro_().get(nextHeuristic);

                    for (int i = 0; i < numberOfLLH_; i++) {
                        Double[] updateProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                        updatedPro.add(updateProTemp);
                    }

                    precessorHeuristic = nextHeuristic;
                } else if (beta == 0) {
                    if (probSum > 1.001 || probSum < 0.99) {
                        System.out.println("The sum of probability is not equal to 1.0 for negative signal.");
                    }
                    al_.updateProbabilityNegativeSignalQ(nextHeuristic, llhs.length, llhs[precessorHeuristic].getTransPro_(), llhs[precessorHeuristic].getQvalue());

                    double tempPro = llhs[precessorHeuristic].getTransPro_().get(nextHeuristic);

                    Double[] updateProTemp = llhs[precessorHeuristic].getTransPro_().toArray(new Double[numberOfLLH_]);
                    updatedPro.add(updateProTemp);

                    for (int i = 0; i < numberOfLLH_; i++) {
                        Double[] updateProNegTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                        updatedProNeg.add(updateProNegTemp);
                    }
                    precessorHeuristic = nextHeuristic;
                }
                llhs[nextHeuristic].setSuccessProb((double) llhs[nextHeuristic].getSuccessTimes() / (double) llhs[nextHeuristic].getCalledTimes());

            }
            String baseDir = folderPath + "/LAResutls/run_" + runIndex;
            File runFolder = new File(baseDir);
            //File runFolder = new File(folderPath+"\\LAResutls\\run_"+runIndex);	
            if (!runFolder.exists()) {
                if (runFolder.mkdirs()) {
                    //System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                    System.exit(0);
                }
            }

            String arrayListPath = baseDir + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_ChosenHeuristic.txt";
            utils_.printArrayListInteger(heuristicList, arrayListPath);
            double[][] successProbStatistic = new double[4][algorithms.length];
            int[] selectedTimes = new int[algorithms.length];
            for (int heuristicListIndex = 1; heuristicListIndex < heuristicList.size(); heuristicListIndex++) {
                for (int algorithmIndex = 0; algorithmIndex < algorithms.length; algorithmIndex++) {
                    if (heuristicList.get(heuristicListIndex) == algorithmIndex) {
                        selectedTimes[algorithmIndex]++;
                    }
                }

            }
            double[] transferIntoProb = transferIntoProbility();
            //success times/pro and transferInto pro
            for (int i = 0; i < algorithms.length; i++) {
                successProbStatistic[0][i] = (double) llhs[i].getSuccessTimes();
                successProbStatistic[1][i] = (double) selectedTimes[i];
                successProbStatistic[2][i] = successProbStatistic[0][i] / successProbStatistic[1][i];
                successProbStatistic[3][i] = transferIntoProb[i];
            }

            String successProStatisticsPath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_successStatis.txt";
            utils_.writeFront(successProStatisticsPath, successProbStatistic);
            String betaValueListPath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rewardList.txt";
            utils_.printArrayListInteger(betaValue, betaValueListPath);
            /*			String tabuListPath = folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_tabuList.txt";
			utils_.printDoubleArray(tabuList, tabuListPath);*/

            //print selection probability
            String SelectionProPath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_SelectionProList.txt";
            utils_.printArrayListDoubleArray(selectioPro, SelectionProPath);

            String Qvaluepath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_QvalueList.txt";
            utils_.printArrayListDoubleArray(QvalueList, Qvaluepath);
            //print updated probability
/*			String UpdatedProPath = folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_UpdatePostiveProList.txt";
			utils_.printArrayListDoubleArray(updatedPro,UpdatedProPath);
			String UpdatedNegProPath = folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_UpdateNegativeProList.txt";
			utils_.printArrayListDoubleArray(updatedProNeg,UpdatedNegProPath);*/

            String rewardUpdatePath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rewardUpdate.txt";
            utils_.printArrayListDoubleArray(rewardUpdate, rewardUpdatePath);
            //print reject heuristic and times of rejection
            //String rejectRecordPath = objConfig + "/LAResutls/run_"+runIndex+"/RejectList.txt";
            //utils_.printArrayListArraylist(rejectRecord,rejectRecordPath);
            //print recorded hypervolume
            String hypImprovementListPath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_hypImprovementList.txt";
            utils_.printArrayListDouble(hypImprovementList, hypImprovementListPath);

            String executedGenerationPath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_executedGeneration.txt";
            utils_.printArrayListInteger(executedGeneration, executedGenerationPath);

            //print current hypervolume
            String currentHypListPath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_currentHypList.txt";
            utils_.printArrayListDouble(currentHypList, currentHypListPath);
            //print threshold
/*			String thresholdPath = folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_threshold.txt";
			utils_.printArrayListDouble(threshold, thresholdPath);*/
 /*			String temperaturePath =  folderPath + "/LAResutls/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_temperature.txt";
			utils_.printArrayListDouble(temperatureList, temperaturePath); */
//			inputPop.printObjectivesToFile(objConfig+"/LAResutls/run_"+runIndex+"/"+"LA_FinalPopulationObj_"+runIndex+".txt");
            List<S> obtainedSolutionSet = SolutionListUtils.getNondominatedSolutions(inputPop);
            new SolutionListOutput(obtainedSolutionSet)
                    .setSeparator("\t")
                    .setFunFileOutputContext(new DefaultFileOutputContext(folderPath + "/LAResutls/run_" + runIndex + "/" + "LA_FinalParetoFront_Real" + (instanceIndex + 1) + "_Run" + runIndex + ".txt"))
                    .print();

            ArrayList<Double[]> finalPro = new ArrayList<Double[]>();
            ArrayList<Double[]> finalTransTimes = new ArrayList<Double[]>();
            for (int i = 0; i < numberOfLLH_; i++) {
                Double[] finalProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                finalPro.add(finalProTemp);
                Double[] finalTransTemp = llhs[i].getTransTimes().toArray(new Double[numberOfLLH_]);
                finalTransTimes.add(finalTransTemp);
            }
            String finalProPath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_finalProList.txt";
            utils_.printArrayListDoubleArray(finalPro, finalProPath);
            String finaltransTimesPath = folderPath + "/LAResutls/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_finalTransTimes.txt";
            utils_.printArrayListDoubleArray(finalTransTimes, finaltransTimesPath);
            /*
            if(problemInstances[instanceIndex] instanceof ExternalProblem){
                System.out.println(((ExternalProblem) problemInstances[instanceIndex]).getQtdEvaluated());
            }
            */
        }//for each instanceIndex

        /*		String hypervolumePath = folderPath + "/LAResutls/run_"+runIndex+"/hypervolume.txt";
		utils_.printArray(realHypervolume, hypervolumePath);*/
        return null;

    }
    //intialise the maximum and minimum values for each objective

    public void initialiseExtremeValues(List<S> solutions) {
        jmetal.qualityIndicator.util.MetricsUtil util_ = new jmetal.qualityIndicator.util.MetricsUtil();

        double[][] objectiveVectors = SolutionListUtils.writeObjectivesToMatrix(solutions);

        double[] tempMinimumValues = util_.getMinimumValues(objectiveVectors, numberOfObj);

        double[] tempMaximumValues = util_.getMaximumValues(objectiveVectors, numberOfObj);

        double rho = 2.0;

        for (int i = 0; i < numberOfObj; i++) {
            minimumValues[i] = tempMinimumValues[i];
            maximumValues[i] = (tempMaximumValues[i] - tempMinimumValues[i]) * rho + tempMinimumValues[i];
        }
    }

    //Initialise the extreme values for the first time
    public void initialiseExtremeValues() {
        for (int i = 0; i < numberOfObj; i++) {
            minimumValues[i] = Double.MAX_VALUE;
            maximumValues[i] = -Double.MAX_VALUE;
        }
    }

    public double computeScaledHypervolume(List<S> pop, boolean initialFlag) {

        if (initialFlag) {
            //initialiseExtremeValues(pop);	
            initialiseExtremeValues();
        }
        updateExtremeValues(pop, initialFlag);
        //problem specific extreme value for land area
        //maximumValues[0] = 9.0;
        double[][] objValues = SolutionListUtils.writeObjectivesToMatrix(pop);

        HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();
        jmetal.qualityIndicator.util.MetricsUtil utils_ = new jmetal.qualityIndicator.util.MetricsUtil();

        double[][] normalizedObj = utils_.getNormalizedFront(objValues, maximumValues, minimumValues);

        double Hypervolume = hyp.hypervolume(normalizedObj, numberOfObj, reference);

        return Hypervolume;
    }

    //return the reward signal, i.e. beta value
    public int rewardSignalFeedback(double currentHyp, double bestHyp) {
        int isPositiveSignal;

        if (currentHyp > bestHyp) {
            isPositiveSignal = 1;
        } else {
            isPositiveSignal = 0;
//				double increment = 0.0;
//				double hypervoluemDelta = currentHyp - bestHyp;
//			
//			increment = Math.sqrt(Math.abs(hypervoluemDelta) * (-Math.log(bestHyp)) * increaseSpeed);
//						
//				double thresholdTemp =  bestHyp - increment;
//				threshold.add(thresholdTemp);
//			
//			if(currentHyp > thresholdTemp){
//					isPositiveSignal = 1;
//				}else{
//					isPositiveSignal = 0;
//				}
//				
        }

        return isPositiveSignal;

    }

    //merge the previous population and the new population to get the best population
    //1. merge two populations 2. remove duplicated solutions 3. keep solutions that have big crowding distance
    public List<S> getTheBestPopulation(List<S> pop1, List<S> pop2, Problem problem) throws IOException {
        if (pop1 == null && pop2 == null) {
            return null;
        } else if (pop1 == null) {
            return pop2;
        } else if (pop2 == null) {
            return pop1;
        } else {
            int size = pop1.size() + pop2.size();
            List<S> mergedPopulation = new ArrayList<S>(size);
            List<S> bestPopulation = new ArrayList<S>(populationSize);

            for (int i = 0; i < pop1.size(); i++) {
                mergedPopulation.add(pop1.get(i));
            }
            for (int j = 0; j < pop2.size(); j++) {
                mergedPopulation.add(pop2.get(j));
            }

//				double[][] mergedPopulationValue = mergedPopulation.writeObjectivesToMatrix();
//				MetricsUtil util_ = new MetricsUtil();
//				String mergedPopPath = objConfig+"/run_" +runIndex+ "/mergedPopulation_.fun";
//				util_.writeFront(mergedPopPath, mergedPopulationValue);
            for (int index = 0; index < size; index++) {
                Solution temp1 = mergedPopulation.get(index);

                for (int k = size - 1; k > index; k--) {
                    Solution temp2 = mergedPopulation.get(k);

                    if (isSameSolution(temp1, temp2, numberOfObj)) {
                        mergedPopulation.remove(k);
                        size--;
                    }
                }
            }

            /*			double[][] uniqueMergedPopulation = mergedPopulation.writeObjectivesToMatrix();
				filename = objConfig+"/run_" +runIndex+ "/uniqueMergedPopulation_Iter_"+iteration +".fun";
				util_.writeFront(filename, uniqueMergedPopulation);*/
            if (mergedPopulation.size() < populationSize) {

                String pop1Path = folderPath + "/run_" + runIndex + "/ErrorFile-Population1.txt";
                new SolutionListOutput(pop1)
                        .setSeparator("\t")
                        .setFunFileOutputContext(new DefaultFileOutputContext(pop1Path))
                        .print();

                String pop2Path = folderPath + "/run_" + runIndex + "/ErrorFile-Population2.txt";
                new SolutionListOutput(pop2)
                        .setSeparator("\t")
                        .setFunFileOutputContext(new DefaultFileOutputContext(pop2Path))
                        .print();
                System.err.println("uniqueMergedPopulation is less than population size!");
                System.exit(0);
            }

            IBEAFitness ibeaFit = new IBEAFitness(problem);
            ibeaFit.calculateFitness(mergedPopulation, numberOfObj);
            while (mergedPopulation.size() > populationSize) {
                ibeaFit.removeWorst(mergedPopulation);
            }

            /*			Spea2Fitness spea = new Spea2Fitness(mergedPopulation);
				spea.fitnessAssign();
				mergedPopulation = spea.environmentalSelection(popluationSize);*/
            return mergedPopulation;
        }

    }

    //return 1 if two solutions sol1 and sol2 are the same; 0 otherwise.
    public boolean isSameSolution(Solution sol1, Solution sol2, int numberOfObjectives) {
        int flag = 0;
        for (int i = 0; i < numberOfObjectives; i++) {
            if (sol1.getObjective(i) == sol2.getObjective(i)) {
                flag++;
            }
        }
        if (flag == numberOfObjectives) {
            return true;
        } else {
            return false;
        }
    }

    //update extreme values 
    public void updateExtremeValues(List<S> solutions, boolean initialFlag) {
        jmetal.qualityIndicator.util.MetricsUtil util_ = new jmetal.qualityIndicator.util.MetricsUtil();

        double[][] objectiveVectors = SolutionListUtils.writeObjectivesToMatrix(solutions);

        double[] tempMinimumValues = util_.getMinimumValues(objectiveVectors, numberOfObj);

        double[] tempMaximumValues = util_.getMaximumValues(objectiveVectors, numberOfObj);
        boolean printExtreamValues = false;

        for (int i = 0; i < numberOfObj; i++) {
            if (tempMinimumValues[i] < minimumValues[i]) {
                minimumValues[i] = tempMinimumValues[i];
            }
        }
        for (int j = 0; j < numberOfObj; j++) {
            if (tempMaximumValues[j] > maximumValues[j]) {
                maximumValues[j] = tempMaximumValues[j];
            }
        }

        if (printExtreamValues) {
            System.out.println("Maximum Values:");
            for (double value : maximumValues) {
                System.out.print(value + " ");
            }
            System.out.println();
            System.out.println("Minimum Values:");
            for (double value : minimumValues) {
                System.out.print(value + " ");
            }
        }

    }

    public double[] transferIntoProbility() {

        double[] transitionIntoProb = new double[llhs.length];
        for (int i = 0; i < llhs.length; i++) {
            for (int j = 0; j < llhs.length; j++) {
                transitionIntoProb[i] += llhs[j].getTransPro_().get(i);
            }

        }
        return transitionIntoProb;
    }
}
