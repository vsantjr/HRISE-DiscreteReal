package HF_LA;

import helpers.AlgorithmCreator;
import helpers.ProblemCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.management.JMException;
import jmetal.qualityIndicator.GSpread;
import jmetal.qualityIndicator.HyperVolumeMinimizationProblem;
import jmetal.qualityIndicator.util.IBEAFitness;
import jmetal.qualityIndicator.util.MetricsUtilPlus;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.comparator.CrowdingDistanceComparator;
import org.uma.jmetal.util.distance.Distance;
import org.uma.jmetal.util.distance.impl.EuclideanDistanceBetweenSolutionAndASolutionListInObjectiveSpace;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;
//import uk.ac.nottingham.asap.realproblems.ExternalProblem;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.LLHInterface;
import br.inpe.cocte.labac.hrise.qualityreal.QualityIndicatorsRealProblems;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;
import br.inpe.cocte.labac.hrise.util.ProblemsWrapperLARILA;
import br.inpe.cocte.labac.hrise.util.SaveFiles;

public class AnyProblemDILANew<S extends Solution<?>> extends LALearning {

    int acceptedQtdValue;
    LAutil util_;
    int numberOfLLH_ = 0;
    AbstractLearningAutomata al_ = null;
    AbstractSelectionLLHs sl_ = null;
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

    // the increase speed of the threshold of hypervolume
    double increaseSpeed = 1.0;
    ArrayList<Double> threshold = new ArrayList<Double>();

    MetricsUtilPlus utils_ = new MetricsUtilPlus();
    Properties config = new Properties();

    String configFile = null;
    String algorithms[] = {"NSGAII", "SPEA2", "IBEA", "mIBEA", "GDE3"};
    String folderPath = null;

    // RouletteSelectionLLHs rs = new RouletteSelectionLLHs();//
    epsilonGreedySelectionLLHs rs = new epsilonGreedySelectionLLHs();// modified!!!

    int fixedGeneration;
    
    String srcDirRILA = ""; // Base Source Dir
    String destDirRILA = ""; // Base Dest Dir

    protected final ProblemCreator problemCreator;

    // GreedySelectionLLHs rs = new GreedySelectionLLHs();
    // SoftMaxSelectionLLHs rs = new SoftMaxSelectionLLHs();
    public AnyProblemDILANew(String configFile, HashMap<String, String> parameterSet, int runNo, String outputfolderpath, ProblemCreator problemCreator, int qtdAlgs) {
        algorithms=Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        folderPath = outputfolderpath;
        double multiplier_ = Double.parseDouble(parameterSet.get("Multiplier"));
        RewardPenaltyLA RPLA = new RewardPenaltyLA(multiplier_);
        al_ = RPLA;
        sl_ = rs;
        numberOfLLH_ = algorithms.length;
        llhs = new LLH[numberOfLLH_];
        for (int i = 0; i < numberOfLLH_; i++) {
            // llhs.add(new LLH(numberOfLLH_, al_));
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

        /*
		 * for(int i = 0; i < numberOfObj; i++){ reference[i] = 2 *(i+1) +1; }
         */
        heuristicList = new ArrayList<Integer>();

        minimumValues = new double[numberOfObj];
        maximumValues = new double[numberOfObj];

        for (int i = 0; i < algorithms.length; i++) {
            acceptedQtdValue += i;
        }
    }

    /*
     * This is the constructor called
     */
    
    
    public AnyProblemDILANew(String configfilePath, int runNo, int fixedGeneration, ProblemCreator problemCreator, int qtdAlgs, String srDir, String deDir) {
        algorithms=Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        //folderPath = "HF_Config_Benchmark/Results/" + problemCreator.getProblemClass() + "/AM/DominanceInitalNew_" + fixedGeneration;
        System.out.println("REAL -- DILA NEW");
        folderPath = "HF_Config_Benchmark/Results/HH-RILA/Iter_" + fixedGeneration;
        
        configFile = configfilePath;
        RewardPenaltyLA RPLA = new RewardPenaltyLA(configFile);
        al_ = RPLA;
        sl_ = rs;
        numberOfLLH_ = algorithms.length;
        llhs = new LLH[numberOfLLH_];
        for (int i = 0; i < numberOfLLH_; i++) {
            // llhs.add(new LLH(numberOfLLH_, al_));
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

        /*
		 * for(int i = 0; i < numberOfObj; i++){ reference[i] = 2 *(i+1) +1; }
         */
        heuristicList = new ArrayList<Integer>();

        minimumValues = new double[numberOfObj];
        maximumValues = new double[numberOfObj];

        for (int i = 0; i < algorithms.length; i++) {
            acceptedQtdValue += i;
        }
        this.fixedGeneration = fixedGeneration;
        
        this.srcDirRILA = srDir;
        this.destDirRILA = deDir;
    }

   

    

    
    /*
     * This is the runLA related to RILA. The one which is really executed.
     * 
     */
    
    @Override
    public List<S> runLA() throws IOException, ClassNotFoundException,
            JMException {
        // TODO Auto-generated method stub

    	
    	
    	
        int fixedSolutionEvl = fixedGeneration * populationSize;

        int totalEval = Integer.parseInt(config.getProperty("MaxEvaluations"));
        
        System.out.println("Evaluations now: " + fixedSolutionEvl + " - Evaluations Total: " + totalEval);
        
        int arcSize = Integer.parseInt(config.getProperty("ArchiveSize"));
        String SolutionType = config.getProperty("SolutionType");
        int popSize = Integer.parseInt(config.getProperty("PopulationSize"));

        double learningPhase = Double.parseDouble(config.getProperty("LearningPhase"));
        int maximumIterations = Integer.parseInt(config.getProperty("MaximumIterations"));
        double hyperImproveLimit = Double.parseDouble(config.getProperty("HypImprovment"));
        //System.out.println("learningPhase: "+ learningPhase+ " maximumIterations: " + maximumIterations+ " hyperImproveLimit: " + hyperImproveLimit);

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

        //GDE3 operators
        //parameters = new HashMap();
        //parameters.put("probability", crossoverProb);
        //parameters.put("CR", 0.2);
        //parameters.put("F", 0.2);
        //parameters.put("K", 0.5);
        //parameters.put("DE_VARIANT", "rand/1/bin");
        //Operator crossoverGDE3 = null;
        //parameters = null;
        //Operator selectionGDE3 = null;
        //GDE3 operators

        // Selection Operator
        parameters = null;
        String selectionType = config.getProperty("SelectionType");

        selection = null;

        Problem[] problemInstances = new Problem[problemCreator.getQtdProblem()];
        int[] numberOfVar = new int[problemCreator.getQtdProblem()];
        double[] mutationProb = new double[problemCreator.getQtdProblem()];
        Operator[] mutations = new Operator[problemCreator.getQtdProblem()];


        List<S> pfKnown = new ArrayList<>(); // per problem
   	    //boolean[] first = new boolean[problemCreator.getQtdProblem()];
   	    //for (int ifir = 0; ifir < first.length; ifir++) {
   		  //first[ifir] = true;
   	    //}
   	    String versionHH = "HH-RILA";
   	    //int allExec = -1; // to record all time execute is run
   	    String filepfKnown;
        String hhid = " ";
        QualityIndicatorsRealProblems qireal = QualityIndicatorsRealProblems.getInstance(); 
   	    PopulationHandler popHandler = new PopulationHandler();
        //reference point for hypervolume calculation
        /*HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();*/
 /*double[] truePFhypervolume = new double[problemCreator.getQtdProblem()];*/
        for (int problemIndex = 0; problemIndex < problemCreator.getQtdProblem(); problemIndex++) {

        	ProblemsWrapperLARILA pwl = new ProblemsWrapperLARILA(problemCreator.getProblemClass(), problemIndex);
            
            problemCreator.addParam("k", pwl.getK());
            problemCreator.addParam("l", pwl.getL());
            problemCreator.addParam("m", pwl.getM());
            System.out.println("INSIDE LOOP PROBLEMS: l = "+ problemCreator.getParam("l")  +
            		 "  -  m = " + problemCreator.getParam("m") +
            		 "  -  k = " + problemCreator.getParam("k"));
        	
            problemInstances[problemIndex] = problemCreator.getProblemInstance(problemIndex);
            numberOfVar[problemIndex] = problemInstances[problemIndex].getNumberOfVariables();
            mutationProb[problemIndex] = (Double) 1.0 / numberOfVar[problemIndex];
            parameters = new HashMap();
            parameters.put("probability", mutationProb[problemIndex]);
            Double mutationDistributionIndex = Double.parseDouble(config.getProperty("MutationDistributionIndex"));
            parameters.put("distributionIndex", mutationDistributionIndex);
            String mutationType = config.getProperty("MutationType");
            mutations[problemIndex] = null;
        }

        LLHInterface[] algorithm = new LLHInterface[problemCreator.getQtdProblem()];
        System.out.println("Qtd prob: " + problemCreator.getQtdProblem());

        int totalFixedGeneration = totalEval / fixedSolutionEvl;
        double[] realHypervolume = new double[problemInstances.length];
        HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();
        GSpread GS = new GSpread();

        for (int instanceIndex = 0; instanceIndex < problemInstances.length; instanceIndex++) { //############# MAIN EXECUTION PART: BEGIN. Estava = 6 no inicio
        	
        	
        	hhid = versionHH + "." + problemInstances[instanceIndex].getName() + "." + "t_" + runIndex;		
            filepfKnown = " ";
        	
            System.out.println("Running " + problemInstances[instanceIndex].getName()); // for each instanceIndex
            numberOfObj = problemInstances[instanceIndex].getNumberOfObjectives();
            reference = new double[numberOfObj];
            for (int i = 0; i < numberOfObj; i++) {
                reference[i] = 1.0;
            }
            minimumValues = new double[numberOfObj];
            maximumValues = new double[numberOfObj];
            // random chose the first heuristic to start with
            
            ArrayList<Integer> eachDPChosenHeuristic = new ArrayList<Integer>();
            
            
            List<S> recordPop = null;
            List<S> currentPop = null;
            List<S> inputPop = null;
            List<S> initialPop = null;
            ArrayList<Double> hypervolumeList = new ArrayList<Double>();
            // boolean isInitialIteration = false;
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
                tabuList[i] = 0;
            }
            // for the purpose of computing hypervolume.
            initialiseExtremeValues();
            for (int i = 0; i < numberOfLLH_; i++) {
                llhs[i].intialiseTransPro(numberOfLLH_);
                llhs[i].intialiseQvalue(numberOfLLH_);
                llhs[i].intialiseExecutedNo();
                llhs[i].initialiseUtility();
                llhs[i].intialiseReward();
                llhs[i].intialistransTimes(numberOfLLH_);
                llhs[i].intialisetransferReward(numberOfLLH_);
            }
            heuristicList.clear();
            int remainEval = totalEval;
            int iteration = 0;
            boolean firstTimeRank = true;
            int[] rankOfAlgorithms = new int[numberOfLLH_];
            for (int i = 0; i < numberOfLLH_; i++) {
                rankOfAlgorithms[i] = -1;
            }
            boolean updatePro = true;
            // run each algorithm and rank them
            // Step 1. Generate the initial population
            List<S> initialPopulation = ProblemCreator.generateInitialPopulation(problemInstances[instanceIndex], popSize);

            // Step 2. Execute each algorithm use the same population for three
            // steps.
            List<S>[] resultSolutions = new List[algorithms.length];
            List<S>[] inputSolutions = new List[algorithms.length];
            //System.out.println("Assign init Pop");
            for (int i = 0; i < algorithms.length; i++) {
                inputSolutions[i] = new ArrayList<S>(popSize);
                // at the beginning, all inputsolutions are set as the initialPopulation
                inputSolutions[i] = initialPopulation;
                resultSolutions[i] = new ArrayList<S>(popSize);
            }
            LLHInterface eachAlgorithm = null;
            int stepSize = algorithms.length;
            int[][] Rank = new int[algorithms.length][stepSize];
            for (int i = 0; i < algorithms.length; i++) {
                for (int j = 0; j < stepSize; j++) {
                    Rank[i][j] = -1;
                }
            }
            double[][] trailHyp = new double[algorithms.length][stepSize];
            double[][] trailGeneralSpread = new double[algorithms.length][stepSize];
            int[] score = new int[algorithms.length];// how many times the algoirthm gain the best rank 0 in all time steps
            for (int i = 0; i < score.length; i++) {
                score[i] = 0;
            }
            int maximumHypPopIndex = -1;// remember the best population index
            int reRank = -1;//if reRank = 1, has equal ranks, rerank;
            List<S> tempAppFront = null;
            AlgorithmCreator ac = new AlgorithmCreator(problemInstances[instanceIndex]);
            ac.setMaxEvaluationsAndPopulation(totalEval, populationSize);
            // after the dominance initialisation
            for (int t = 0; t < stepSize; t++) {
                //System.out.println("Step size "+t+" of "+stepSize);
                //System.out.println("Create algs and assing imput pop");
                for (int j = 0; j < algorithms.length; j++) {//run each algorithm consecutively
                	 eachDPChosenHeuristic.add(j);

                	//System.out.println("Remain Eval: " + remainEval);
                    eachAlgorithm = ac.create(j, remainEval);
                    //System.out.println(eachAlgorithm.getClass().getName()+" created and ready to run");

                    try {
                        resultSolutions[j] = eachAlgorithm.execute(/*initialPopulation*/inputSolutions[j], fixedSolutionEvl);
                        //allExec++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    remainEval -= fixedSolutionEvl;
                    iteration++;
                    //iteration += 2;
                    inputSolutions[j] = resultSolutions[j];
                    //System.out.println("LARILA POP INIT --: " + inputSolutions[j].size());
                    //if ( j > 0 ) {
                    	//first[instanceIndex] = false;
                    //}
                    
                    /*
                     * PFKNOWN
                     */
                    //List<S> tempPop = new ArrayList<>();
                    //tempPop.addAll(resultSolutions[j]);
                    
                    //tempPop = popHandler.generateNonDominated(tempPop, problemInstances[instanceIndex]);
                    //tempPop = popHandler.removeRepeatedSolutions(tempPop);
                    //pfKnown.add(tempPop);
                    
                    //pfKnown = popHandler.removeRepeatedSolutions(pfKnown);
                    
                    //filepfKnown = "pareto_fronts_known/"+
                    	//	 versionHH+"."+problem.getName()+"."+problem.getNumberOfObjectives()+"D.t_"+i+".dp_"+j+
                    		// ".pf";
                     //SaveFiles sPFKSt = new SaveFiles();
                     //sPFKSt.savePFKnown(filepfKnown, pfKnown);
                    
                    
                    
                }// end j, executing each algorithm for
                //System.out.println("Update extreme, compute scaled Hyp, Spread, remain eval "+remainEval);
                // find worst points for all the three populations
                for (int i = 0; i < algorithms.length; i++) {
                    updateExtremeValues(resultSolutions[i]);
                }
                for (int k = 0; k < algorithms.length; k++) {
                    //updateExtremeValues(resultSolutions[k]);

                    trailHyp[k][t] = computeScaledHypervolume(resultSolutions[k]);
                }
                tempAppFront = utils_.getApproximateFronts(inputSolutions);

                for (int i = 0; i < trailGeneralSpread.length; i++) {
                    trailGeneralSpread[i][t] = GS.generalizedSpread(inputSolutions[i], tempAppFront, numberOfObj);
                }
                //System.out.println("Compute rank");
                // rank all algorithms at each time step.
                for (int rankIndex = 0; rankIndex < algorithms.length; rankIndex++) {
                    int count = -1;
                    double tempHyp = -Double.MAX_VALUE;
                    for (int m = 0; m < algorithms.length; m++) {
                        if (Rank[m][t] == -1) {

                            if (trailHyp[m][t] > tempHyp) {
                                tempHyp = trailHyp[m][t];
                                count = m;
                            }
                        }
                    }
                    Rank[count][t] = rankIndex;
                    /*					if(rankIndex ==0){
                    maximumHypPopIndex = count;
                    initialPopulation = resultSolutions[maximumHypPopIndex];
                    }*/
                }// end rank each algorithm at time step t
                //initialPopulation = resultSolutions[maximumHypPopIndex];
            }// end t, timeStep
            String baseDir = folderPath + "/run_" + runIndex;
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
            String trailHypPath = baseDir + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_trailHyp.txt";
            utils_.writeFront(trailHypPath, trailHyp);
            String trailGSPath = baseDir + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_trailGS.txt";
            utils_.writeFront(trailGSPath, trailGeneralSpread);
            //System.out.println("Scoring");
            // Scoring all algorithms
            for (int s = 0; s < algorithms.length; s++) {
                for (int t = 0; t < stepSize; t++) {
                    if (Rank[s][t] == 0) {
                        score[s]++;
                    }
                }
            }
            // rank the scores of all algorithms
            int[] scoreRank = new int[algorithms.length];
            for (int l = 0; l < scoreRank.length; l++) {
                scoreRank[l] = -1;
            }
            for (int i = 0; i < scoreRank.length; i++) {
                int tempMax = -Integer.MAX_VALUE;
                int count = -1;

                for (int scoreRanIndex = 0; scoreRanIndex < algorithms.length; scoreRanIndex++) {

                    if (scoreRank[scoreRanIndex] == -1 && score[scoreRanIndex] > tempMax) {
                        tempMax = score[scoreRanIndex];
                        count = scoreRanIndex;
                    }
                }
                if (count != -1) {
                    scoreRank[count] = i;
                    //if the same score, gives the same rank
                    for (int j = 0; j < algorithms.length; j++) {
                        if ((score[j] == score[count]) && (j != count)) {
                            scoreRank[j] = scoreRank[count];

                            reRank = 1;
                        }
                    }
                } else {
                    break;
                }
            }
            //if has the same rank, use General spread to rerank
            String allRankFile = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_AllRankFile.txt";
            utils_.wirteInteger(allRankFile, Rank);
            String scoreFile = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_ScoreFile.txt";
            utils_.printIntegerArray(score, scoreFile);
            if (reRank == 1) {
                //add general spread values here
                //collect a temp approximate front set and get each algorithm's general spread.
                tempAppFront = utils_.getApproximateFronts(inputSolutions);
                double[] generalSpread = new double[llhs.length];
                for (int i = 0; i < generalSpread.length; i++) {
                    generalSpread[i] = GS.generalizedSpread(inputSolutions[i], tempAppFront, numberOfObj);
                }
                String generalSpreadPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rankingGS.txt";
                utils_.printDoubleArray(generalSpread, generalSpreadPath);

                ArrayList<Integer> sameRankAlgorithms = new ArrayList<Integer>();
                for (int i = 0; i < llhs.length; i++) {
                    for (int j = 0; j < llhs.length; j++) {
                        if ((i != j) && (scoreRank[i] == scoreRank[j])) {
                            sameRankAlgorithms.add(j);
                        }

                    }//end j
                    if (sameRankAlgorithms.size() > 0) {
                        sameRankAlgorithms.add(i);
                        break;
                    }
                }//end i

                double[] newScores = new double[sameRankAlgorithms.size()];
                for (int k = 0; k < newScores.length; k++) {
                    newScores[k] = scoreRank[sameRankAlgorithms.get(k)] + generalSpread[sameRankAlgorithms.get(k)];
                }
                int[] ranked = new int[newScores.length];
                for (int i = 0; i < ranked.length; i++) {
                    ranked[i] = 0;
                }
                for (int l = 0; l < newScores.length; l++) {

                    int index = -1;

                    double tempMin = Double.MAX_VALUE;

                    for (int m = 0; m < newScores.length; m++) {
                        if ((ranked[m] == 0) && (newScores[m] < tempMin)) {
                            tempMin = newScores[m];
                            index = m;
                        }
                    }//end m
                    ranked[index] = 1;
                    scoreRank[sameRankAlgorithms.get(index)] += l;

                }

                int reRankSum = 0;
                for (int i = 0; i < llhs.length; i++) {
                    reRankSum += scoreRank[i];
                }

                if (reRankSum != acceptedQtdValue) {
                    System.out.println("Rerank sum " + reRankSum + " at " + runIndex + " problem " + (instanceIndex + 1));
                }
            }//end rerank
            String allScoreRank = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_AllScoreRank.txt";
            utils_.printIntegerArray(scoreRank, allScoreRank);
            double medianRank = Math.ceil((double) numberOfLLH_ / 2.0) - 1;
            for (int i = 0; i < numberOfLLH_; i++) {
                if (scoreRank[i] == 0) {
                    maximumHypPopIndex = i;
                }
                if (scoreRank[i] > medianRank) {
                    tabuList[i] = 1.0;
                }
            }
            for (int tabuListIndex = 0; tabuListIndex < tabuList.length; tabuListIndex++) {
                // if tabued algorithm
                if (tabuList[tabuListIndex] == 1.0) {
                    // set all other algorithm's transition probability to this
                    // tabued algorithm as 0
                    for (int i = 0; i < numberOfLLH_; i++) {
                        llhs[i].getTransPro_().set(tabuListIndex, 0.0);
                    }
                    // set the transition probability from this tabued algorithm
                    // to other algorithms
                    for (int j = 0; j < numberOfLLH_; j++) {
                        llhs[tabuListIndex].getTransPro_().set(j, 0.0);
                    }
                    llhs[tabuListIndex].setUtility(-Double.MAX_VALUE);

                }
            }
            // resize the transion probability if any algorithm is tabued.
            for (int j = 0; j < numberOfLLH_; j++) {
                double sumPro = 0.0;
                if (tabuList[j] != 1.0) {
                    for (int index = 0; index < numberOfLLH_; index++) {
                        sumPro += llhs[j].getTransPro_().get(index);
                    }
                    for (int k = 0; k < numberOfLLH_; k++) {
                        double tempPro = llhs[j].getTransPro_().get(k) / sumPro;
                        llhs[j].getTransPro_().set(k, tempPro);
                    }
                }
            }
            String tabuListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_tabuList.txt";
            utils_.printDoubleArray(tabuList, tabuListPath);
         // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            
            // Finished the dominance selection initialisation
            chosenHeuristic = maximumHypPopIndex;
            initialPopulation = resultSolutions[maximumHypPopIndex];
            heuristicList.add(chosenHeuristic);// the initial choice
            int precessorHeuristic = chosenHeuristic;
            ArrayList<Integer> executedGeneration = new ArrayList<Integer>();
            ArrayList<Double> hypImprovementList = new ArrayList<Double>();
            ac = new AlgorithmCreator(problemInstances[instanceIndex]);
            ac.setMaxEvaluationsAndPopulation(totalEval, populationSize);
            int tempExecutedGen = 0;
            //System.out.println("Choose MH is "+chosenHeuristic);
            algorithm[instanceIndex] = ac.create(chosenHeuristic, remainEval);
            // Execute the Algorithm
            inputPop = resultSolutions[maximumHypPopIndex];
            //System.out.println("Execute "+chosenHeuristic);
            try {
                recordPop = algorithm[instanceIndex].execute(inputPop,
                        fixedSolutionEvl);
            } catch (Exception e) {
                // } catch (ClassNotFoundException | JMException e) {
                e.printStackTrace();
            }
            inputPop = recordPop;
            //System.out.println("LARILA POP: " + inputPop.size());
            eachDPChosenHeuristic.add(chosenHeuristic);
            
            tempExecutedGen += fixedGeneration;
            iteration++;
            updateExtremeValues(inputPop);
            double currentHypervolume = computeScaledHypervolume(inputPop);
            bestHyp = 0.0;
            double initialHyp = 0.0;
            initialPop = inputPop;
            // isInitialIteration = false;
            remainEval -= fixedSolutionEvl;
            recordHypList.add(currentHypervolume);
            currentHypList.add(currentHypervolume);
            hypervolumeList.add(currentHypervolume);
            // execute the chosen heuristic till convergence criteria satisfied
            double hypImprovement = Double.MAX_VALUE;
            double previousHyp = 0.0;
            while (hypImprovement > hyperImproveLimit && tempExecutedGen < maximumIterations && remainEval > 0) {

                currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl);
                
                eachDPChosenHeuristic.add(chosenHeuristic);
                
                iteration++;
                remainEval -= fixedSolutionEvl;
                inputPop = currentPop;
                updateExtremeValues(inputPop);
                currentHypervolume = computeScaledHypervolume(currentPop);
                /*
                * if(currentHypervolume > bestHyp){ bestHyp =
                * currentHypervolume; recordHypList.add(currentHypervolume);
                * recordPop = currentPop; }
                 */
                previousHyp = currentHypList.get(currentHypList.size() - 1);
                hypImprovement = (currentHypervolume - previousHyp)
                        / previousHyp;
                tempExecutedGen += fixedGeneration;
                currentHypList.add(currentHypervolume);
            }
            /* recordPop = currentPop; */
            double hypImprovementFinal = 0.0;
            // hypImprovementFinal = (currentHypervolume -
            // initialHyp)/initialHyp;
            initialHyp = currentHypervolume;
            bestHyp = currentHypervolume;
            executedGeneration.add(tempExecutedGen);
            // hypImprovmentList is used to store the hypervolume improvement
            // between two heuristics.
            // hypImprovementList.add(hypImprovementFinal);
            // reset the temple executed generation and hypImprovement
            tempExecutedGen = 0;
            hypImprovement = Double.MAX_VALUE;
            for (int i = 0; i < numberOfLLH_; i++) {
                Double[] tempRewardUpdate = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
                rewardUpdate.add(tempRewardUpdate);
            }
            double lamada2 = 0.1;
            ArrayList<Integer> rejectList = new ArrayList<Integer>();
            ArrayList<ArrayList> rejectRecord = new ArrayList<ArrayList>();
            double InitialTemperature = 10.0;
            double Temperature = InitialTemperature;
            double decayFactor = Math.pow((1.0 / InitialTemperature),
                    1.0 / (totalFixedGeneration / 2));
            ArrayList<Double> temperatureList = new ArrayList<Double>();
            temperatureList.add(Temperature);
            double epsilon = 0.0;
            while (remainEval > 0) {
                int nextHeuristic = -1;
                // for slection method that is not softmax
                // nextHeuristic =
                // sl_.selectionLLHs(llhs[precessorHeuristic].transPro_);
                // if it is softMaxSelection

                int tempIteration = (int) (totalFixedGeneration * learningPhase);
                if (iteration < tempIteration) {
                    if (precessorHeuristic == -1) {
                        System.out.println("precessorHeuristic = -1!");
                        System.exit(0);
                    }
                    //epsilon = 0.01 * iteration;
                    nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].getTransPro_(), epsilon);
                    // nextHeuristic =
                    // sl_.selectionLLHs(llhs[precessorHeuristic].transPro_);
                    if (tabuList[nextHeuristic] == 1.0) {
                        System.out.println("Tabued heuristic is chosen!");
                        System.exit(0);
                    }
                } else {
                    //epsilon = 0.5+0.01 * iteration;
                    epsilon = learningPhase + (1.0 - learningPhase) / totalFixedGeneration * iteration;
                    double tabuSum = 0;
                    for (double tabuIndex : tabuList) {
                        tabuSum += tabuIndex;

                    }

                    if (tabuSum >= numberOfLLH_ - 1) {

                        for (int i = 0; i < numberOfLLH_; i++) {
                            if (tabuList[i] == 0.0) {
                                nextHeuristic = i;
                                precessorHeuristic = nextHeuristic;
                            }
                        }
                        if (nextHeuristic == -1) {
                            nextHeuristic = maximumHypPopIndex;
                            precessorHeuristic = nextHeuristic;
                        }
                        if (precessorHeuristic == -1) {
                            System.out.println("precessorHeuristic = -1!");
                            System.exit(0);
                        }
                        updatePro = false;

                    } else {

                        nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].getTransPro_(), epsilon);
                        while (tabuList[nextHeuristic] == 1.0) {
                            System.out.println("Tabued heuristic is chosen!");
                            System.exit(0);
                        }

                    }
                }
                if (precessorHeuristic < 0 || precessorHeuristic > algorithms.length - 1) {
                    System.out.println("Invalid precessorHeuristic index!" + precessorHeuristic);
                    System.exit(0);
                }
                if (nextHeuristic < 0 || nextHeuristic > algorithms.length - 1) {
                    System.out.println("Invalid nextHeuristic index!" + nextHeuristic);
                    System.exit(0);
                }
                double tempTransTimes = llhs[precessorHeuristic].getTransTimes().get(nextHeuristic) + 1.0;
                llhs[precessorHeuristic].getTransTimes().set(nextHeuristic,
                        tempTransTimes);

                // half of the total iterations, we go greedy
                for (int i = 0; i < numberOfLLH_; i++) {
                    Double[] selectionProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                    Double[] QvalueTemp = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
                    selectioPro.add(selectionProTemp);
                    QvalueList.add(QvalueTemp);
                }

                heuristicList.add(nextHeuristic);

                int rejectCount = 1;
                algorithm[instanceIndex] = ac.create(nextHeuristic, remainEval);
                /*
                * // Execute the Algorithm try { currentPop =
                * algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl);
                * } catch (Exception e) { // } catch (ClassNotFoundException |
                * JMException e) { e.printStackTrace(); }
                *
                *
                * inputPop = currentPop; iteration++; tempExecutedGen +=
                * fixedGeneration;
                *
                * remainEval -= fixedSolutionEvl;
                *
                * currentHypervolume =
                * computeScaledHypervolume(currentPop,isInitialIteration);
                * currentHypList.add(currentHypervolume);
                 */

 /*
                * double dominatingPercentage = Double.MAX_VALUE; double
                * previousDominatingPercentage = 0.0; double
                * dominatedPercentage = 0.0; ArrayList<Double> dominatingPerHis
                * = new ArrayList<Double>();
                * dominatingPerHis.add(previousDominatingPercentage);
                 */
                while (hypImprovement > hyperImproveLimit && tempExecutedGen < maximumIterations && remainEval > 0) {
                    int feasableSolutionEval = fixedSolutionEvl;
                    if (remainEval < fixedSolutionEvl) {
                        feasableSolutionEval = remainEval;
                    }
                    //Protection
                    if(inputPop.size() % 2 != 0){
                        SecureRandom rdn=new SecureRandom();
                        inputPop.add(inputPop.get(rdn.nextInt(inputPop.size())));
                        System.err.println("Protected at "+remainEval);
                    }
                    algorithm[instanceIndex].setPopulationSize(inputPop.size());
                    //Protection
                    currentPop = algorithm[instanceIndex].execute(inputPop,/*fixedSolutionEvl*/ feasableSolutionEval); // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    inputPop = currentPop;
                    
                    eachDPChosenHeuristic.add(nextHeuristic);
                    
                    iteration++;
                    remainEval -= feasableSolutionEval;

                    if (remainEval == 0) {
                        System.out.println("Remain solution evl is 0. Iteration is " + iteration);
                    }

                    /* Dominance modification--start */
 /*
                    * ArrayList<Integer> dominanceCounting = new
                    * ArrayList<Integer>(); dominanceCounting =
                    * util_.getDomincanceSolNo(currentPop, inputPop);
                     */
                    // index 0 is dominating count, 1 is nondominating count, 2
                    // is dominated count
                    /*
                    * dominatingPercentage = (double)
                    * dominanceCounting.get(0)/currentPop.size();
                    * previousDominatingPercentage =
                    * dominatingPerHis.get(dominatingPerHis.size() - 1);
                    *
                    * dominatingPerHis.add(dominatingPercentage);
                    *
                    * dominatedPercentage = (double)
                    * dominanceCounting.get(2)/currentPop.size();
                     */
                    updateExtremeValues(inputPop);
                    currentHypervolume = computeScaledHypervolume(inputPop);
                    previousHyp = currentHypList.get(currentHypList.size() - 1);

                    currentHypList.add(currentHypervolume);

                    /*
                    * if(currentHypervolume > bestHyp){ bestHyp =
                    * currentHypervolume;
                    * recordHypList.add(currentHypervolume); recordPop =
                    * currentPop;
                    *
                    * }
                     */
 /* hypImprovement = currentHypervolume - previousHyp; */
                    hypImprovement = (currentHypervolume - previousHyp)
                            / previousHyp;
                    tempExecutedGen += fixedGeneration;

                }
                /*
                * dominatingPercentage = Double.MAX_VALUE; dominatedPercentage
                * = 0.0; previousDominatingPercentage = 0.0;
                 */

                hypImprovementFinal = currentHypervolume - initialHyp;
                /*
                * Math.abs(( currentHypervolume - initialHyp ) /initialHyp )
                 */

                double tempTransferReward = llhs[precessorHeuristic].getTransferReward().get(nextHeuristic) + hypImprovementFinal;
                llhs[precessorHeuristic].getTransferReward().set(nextHeuristic, tempTransferReward);

                Temperature = Math.pow(decayFactor, iteration) * InitialTemperature;
                temperatureList.add(Temperature);

                if (precessorHeuristic < 0 || precessorHeuristic > algorithms.length - 1) {
                    System.out.println("Invalid precessorHeuristic index!" + precessorHeuristic);
                    System.exit(0);
                }
                if (nextHeuristic < 0 || nextHeuristic > algorithms.length - 1) { // corrected bug: algorithm (no s) was wrong
                    System.out.println("Invalid nextHeuristic index::::::::::: " + nextHeuristic);
                    System.exit(0);
                }

                try {

                    // double tempQvalue =
                    // (llhs[precessorHeuristic].transferReward.get(nextHeuristic))/llhs[precessorHeuristic].transTimes.get(nextHeuristic);
                    double tempQvalue = (hypImprovementFinal - llhs[precessorHeuristic].getQvalue().get(nextHeuristic)) * alpha
                            + llhs[precessorHeuristic].getQvalue()
                                    .get(nextHeuristic);

                    /*
                    * if(tempQvalue <0.0){ tempQvalue = 0.0;
                    * //System.out.println("Negative tempQvalue:" +
                    * tempQvalue); }
                     */
                    llhs[precessorHeuristic].getQvalue().set(nextHeuristic,
                            tempQvalue);
                    // llhs[nextHeuristic].Qvalue.set(precessorHeuristic,
                    // tempQvalue);//we see hi to hj is equalvilant to hj to hi

                    for (int i = 0; i < numberOfLLH_; i++) {
                        Double[] tempRewardUpdate = llhs[i].getQvalue()
                                .toArray(new Double[numberOfLLH_]);
                        rewardUpdate.add(tempRewardUpdate);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(e);
                }

                executedGeneration.add(tempExecutedGen);

                // hypImprovmentList is used to store the hypervolume
                // improvement between two heuristics.
                hypImprovementList.add(hypImprovementFinal);
                llhs[nextHeuristic].setExecutedNo(
                        llhs[nextHeuristic].getExecutedNo() + tempExecutedGen);
                llhs[nextHeuristic]
                        .setReward(llhs[nextHeuristic].getReward() + hypImprovementFinal);
                llhs[nextHeuristic].setUtility(llhs[nextHeuristic].getReward()
                        / llhs[nextHeuristic].getExecutedNo());

                // reset the temple executed generation and hypImprovement
                tempExecutedGen = 0;
                hypImprovement = Double.MAX_VALUE;
                // if initialHyp is improved then 1, otherwise 0;
                int beta = rewardSignalFeedback(currentHypervolume, initialHyp);
                betaValue.add(beta);
                initialHyp = currentHypervolume;
                /*				inputPop = getTheBestPopulation(inputPop,initialPop);
                initialPop = inputPop;*/

                // if(currentHypervolume > bestHyp){
                //
                // bestHyp = currentHypervolume;
                // recordHypList.add(currentHypervolume);
                // }
                if (updatePro) {
                    // update the probability of the previous heuristic's probabilities of transition to the chosen heuristic and other heuristics
                    if (beta == 1) {
                        // rejectList.clear();
                        // rejectCount = 1;
                        // al_.updateProbabilityPositiveSignal(nextHeuristic,
                        // llhs[precessorHeuristic].transPro_);
                        al_.updateProbabilityPositiveSignalQ(nextHeuristic, llhs[precessorHeuristic].getTransPro_(), llhs[precessorHeuristic].getQvalue());
                        double tempPro = llhs[precessorHeuristic].getTransPro_().get(nextHeuristic);

                        for (int i = 0; i < numberOfLLH_; i++) {
                            Double[] updateProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                            updatedPro.add(updateProTemp);
                        }

                        // inputPop = currentPop;
                        // inputPop = getTheBestPopulation(currentPop,
                        // recordPop);
                        // recordPop = inputPop;
                        precessorHeuristic = nextHeuristic;
                    } else if (beta == 0) {

                        al_.updateProbabilityNegativeSignalQ(nextHeuristic, llhs.length, llhs[precessorHeuristic].getTransPro_(), llhs[precessorHeuristic].getQvalue());

                        double tempPro = llhs[precessorHeuristic].getTransPro_().get(nextHeuristic);

                        Double[] updateProTemp = llhs[precessorHeuristic].getTransPro_().toArray(new Double[numberOfLLH_]);
                        updatedPro.add(updateProTemp);

                        for (int i = 0; i < numberOfLLH_; i++) {
                            Double[] updateProNegTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                            updatedProNeg.add(updateProNegTemp);
                        }
                        precessorHeuristic = nextHeuristic;
                        // inputPop = currentPop;
                        // inputPop = getTheBestPopulation(currentPop,
                        // recordPop);
                        // inputPop = recordPop;
                        // precessorHeuristic = nextHeuristic;
                    }
                }

            }
            String arrayListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_ChosenHeuristic.txt";
            utils_.printArrayListInteger(heuristicList, arrayListPath);
            String betaValueListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rewardList.txt";
            utils_.printArrayListInteger(betaValue, betaValueListPath);
            // print selection probability
            String SelectionProPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_SelectionProList.txt";
            utils_.printArrayListDoubleArray(selectioPro, SelectionProPath);
            String Qvaluepath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_QvalueList.txt";
            utils_.printArrayListDoubleArray(QvalueList, Qvaluepath);
            // print updated probability
            /*
            * String UpdatedProPath = folderPath + "/DominanceInital/run_"+runIndex +"/WFG"+(instanceIndex+1)+"_UpdatePostiveProList.txt";
            * utils_.printArrayListDoubleArray(updatedPro,UpdatedProPath);
            * String UpdatedNegProPath = folderPath +"/DominanceInital/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_UpdateNegativeProList.txt";
            * utils_.printArrayListDoubleArray(updatedProNeg,UpdatedNegProPath);
             */
            String rewardUpdatePath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rewardUpdate.txt";
            utils_.printArrayListDoubleArray(rewardUpdate, rewardUpdatePath);
            // print reject heuristic and times of rejection
            // String rejectRecordPath = objConfig +"/DominanceInital/run_"+runIndex+"/RejectList.txt";
            // utils_.printArrayListArraylist(rejectRecord,rejectRecordPath);
            // print recorded hypervolume
            String hypImprovementListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_hypImprovementList.txt";
            utils_.printArrayListDouble(hypImprovementList, hypImprovementListPath);
            String executedGenerationPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_executedGeneration.txt";
            utils_.printArrayListInteger(executedGeneration, executedGenerationPath);
            // print current hypervolume
            String currentHypListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_currentHypList.txt";
            utils_.printArrayListDouble(currentHypList, currentHypListPath);
            // print threshold
            /*
            * String thresholdPath = folderPath +"/DominanceInital/run_"+runIndex +"/Real"+(instanceIndex+1)+"_threshold.txt";
            * utils_.printArrayListDouble(threshold, thresholdPath);
             */
            
            String eachDPChosenHeuristicPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_eachDPChosenHeuristicList.txt";
            utils_.printArrayListInteger(eachDPChosenHeuristic, eachDPChosenHeuristicPath);
            
 /*		String temperaturePath = folderPath + "/DominanceInital/run_"+ runIndex + "/" +problemCreator.getProblemClass()+ (instanceIndex + 1)+ "_temperature.txt";
            utils_.printArrayListDouble(temperatureList, temperaturePath);*/
            // inputPop.printObjectivesToFile(objConfig+"/DominanceInital/run_"+runIndex+"/"+"LA_FinalPopulationObj_"+runIndex+".txt");
            List<S> obtainedSolutionSet = SolutionListUtils.getNondominatedSolutions(inputPop);
            new SolutionListOutput(obtainedSolutionSet)
                    .setSeparator("\t")
                    .setFunFileOutputContext(new DefaultFileOutputContext(folderPath + "/run_" + runIndex + "/" + "LA_FinalParetoFront_" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_Run" + runIndex + ".txt"))
                    .print();
            
            
            /*
             * PFKnown and saving files: BEGIN
             */
            
            pfKnown.addAll(obtainedSolutionSet);
            pfKnown = popHandler.generateNonDominated(pfKnown, problemInstances[instanceIndex]);
            //pfKnown = popHandler.removeRepeatedSolutions(pfKnown);
            
            
            pfKnown = popHandler.removeRepeatedSolutionsInteger(pfKnown);
            
            filepfKnown = "pareto_fronts_known/"+
            		 versionHH+"."+ problemInstances[instanceIndex].getName()+ "." +problemInstances[instanceIndex].getNumberOfObjectives() +"D.t_"+runIndex+".dp_"+0+
            		 ".pf";
             SaveFiles sPFKSt = new SaveFiles();
             sPFKSt.savePFKnown(filepfKnown, pfKnown);
            
             
             // Save FUN e VAR of the HH
             SaveFiles sFV = new SaveFiles();
      		 sFV.saveFunVar(runIndex, versionHH, problemInstances[instanceIndex], obtainedSolutionSet);
      		  
      		 
      		  System.out.println("Final PF Known: " + filepfKnown + "\n");
    		  System.out.println("------ Print hhid: " + hhid);
    		  qireal.addPFKnown(hhid, obtainedSolutionSet);
             
    		  pfKnown.clear();
              filepfKnown = " ";
              
              
              System.out.println("........... Copying Results.......................................................................");
      		  copyResultDirectory(srcDirRILA + "/result"+"/"+versionHH+"/"+problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives(),
      				            destDirRILA + "/ALLRES/" + problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives()+"/"
      		      		               +versionHH+"/"+problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives());
      		  copyResultDirectory(srcDirRILA + "/pareto_fronts_known",
      				            destDirRILA + "/PFKNOWN/"+versionHH+"/");
              
              
           // Copy result directory
      		  //System.out.println("----------------- Copying Results.......................................................................");
      		  //System.out.println(".......................................................................................................");
      		  //System.out.println("........................................................................................................");
      		  //System.out.println("........................................................................................................");
      		  //copyResultDirectory("C:\\Users\\psavj\\Documents\\Des\\ProjEclipse\\HHOST\\result"+"\\"
      		    //                  +versionHH+"\\"+problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives() ,
      			//	             "C:\\Users\\psavj\\Documents\\Des\\Rst\\HRISE\\ResultsExec\\ALLRES\\" + problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives()+"\\"
      		      //         +versionHH+"\\"+problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives());
              
      		// Copy result directory
      		  //System.out.println("----------------- Copying Results PFKNOWN .......................................................................");
      		  //System.out.println("........................................................................................................");
      		  //System.out.println("........................................................................................................");
      		  //copyResultDirectory("C:\\Users\\psavj\\Documents\\Des\\ProjEclipse\\HHOST\\pareto_fronts_known",
   			//	  "C:\\Users\\psavj\\Documents\\Des\\Rst\\HRISE\\ResultsExec\\PFKNOWN\\RILA\\");
      		  
      		  
             /*
              *  PFKnown and saving files: END 
              */
            
            //print out true front's hypervolume
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
        }

        /*		String hypervolumePath = folderPath + "/DominanceInital/run_"+ runIndex + "/hypervolume.txt";
		utils_.printArray(realHypervolume, hypervolumePath);*/
        return null;

    }  //####### RUNLA: END

    
    public void copyResultDirectory(String sourceDir, String destDir) {
  		File source = new File(sourceDir);
  		File dest = new File(destDir);
  		try {
  		    FileUtils.copyDirectory(source, dest);
  		} catch (IOException e) {
  		    e.printStackTrace();
  		}
  		
  	}
    
    
    
    // intialise the maximum and minimum values for each objective
    public void initialiseExtremeValues(List<S> solutions) {
        jmetal.qualityIndicator.util.MetricsUtil util_ = new jmetal.qualityIndicator.util.MetricsUtil();

        double[][] objectiveVectors = SolutionListUtils.writeObjectivesToMatrix(solutions);

        double[] tempMinimumValues = util_.getMinimumValues(objectiveVectors,
                numberOfObj);

        double[] tempMaximumValues = util_.getMaximumValues(objectiveVectors,
                numberOfObj);

        double rho = 2.0;

        for (int i = 0; i < numberOfObj; i++) {
            minimumValues[i] = tempMinimumValues[i];
            maximumValues[i] = (tempMaximumValues[i] - tempMinimumValues[i])
                    * rho + tempMinimumValues[i];
        }
    }

    // Initialise the extreme values for the first time
    public void initialiseExtremeValues() {
        for (int i = 0; i < numberOfObj; i++) {
            minimumValues[i] = Double.MAX_VALUE;
            maximumValues[i] = -Double.MAX_VALUE;
        }
    }

    public double computeScaledHypervolume(List<S> pop) {

        /*
		 * if(initialFlag){ //initialiseExtremeValues(pop);
		 * initialiseExtremeValues(); }
         */
        //updateExtremeValues(pop);
        // problem specific extreme value for land area
        // maximumValues[0] = 9.0;
        double[][] objValues = SolutionListUtils.writeObjectivesToMatrix(pop);

        HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();
        jmetal.qualityIndicator.util.MetricsUtil utils_ = new jmetal.qualityIndicator.util.MetricsUtil();

        double[][] normalizedObj = utils_.getNormalizedFront(objValues,
                maximumValues, minimumValues);

        double Hypervolume = hyp.hypervolume(normalizedObj, numberOfObj,
                reference);

        return Hypervolume;
    }

    // return the reward signal, i.e. beta value
    public int rewardSignalFeedback(double currentHyp, double bestHyp) {
        int isPositiveSignal;

        if (currentHyp > bestHyp) {
            isPositiveSignal = 1;
        } else {
            isPositiveSignal = 0;
            // double increment = 0.0;
            // double hypervoluemDelta = currentHyp - bestHyp;
            //
            // increment = Math.sqrt(Math.abs(hypervoluemDelta) *
            // (-Math.log(bestHyp)) * increaseSpeed);
            //
            // double thresholdTemp = bestHyp - increment;
            // threshold.add(thresholdTemp);
            //
            // if(currentHyp > thresholdTemp){
            // isPositiveSignal = 1;
            // }else{
            // isPositiveSignal = 0;
            // }
            //
        }

        return isPositiveSignal;

    }

    // merge the previous population and the new population to get the best
    // population
    // 1. merge two populations 2. remove duplicated solutions 3. keep solutions
    // that have big crowding distance
    public List<S> getTheBestPopulation(List<S> pop1, List<S> pop2, Problem problem)
            throws IOException {

        int size = pop1.size() + pop2.size();
        List<S> mergedPopulation = new ArrayList<S>(size);
        List<S> bestPopulation = new ArrayList<S>(populationSize);

        for (int i = 0; i < pop1.size(); i++) {
            mergedPopulation.add(pop1.get(i));
        }
        for (int j = 0; j < pop2.size(); j++) {
            mergedPopulation.add(pop2.get(j));
        }

        // double[][] mergedPopulationValue =
        // mergedPopulation.writeObjectivesToMatrix();
        // MetricsUtil util_ = new MetricsUtil();
        // String mergedPopPath = objConfig+"/run_" +runIndex+
        // "/mergedPopulation_.fun";
        // util_.writeFront(mergedPopPath, mergedPopulationValue);
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

        /*
		 * double[][] uniqueMergedPopulation =
		 * mergedPopulation.writeObjectivesToMatrix(); filename =
		 * objConfig+"/run_" +runIndex+
		 * "/uniqueMergedPopulation_Iter_"+iteration +".fun";
		 * util_.writeFront(filename, uniqueMergedPopulation);
         */
        if (mergedPopulation.size() < populationSize) {

            String pop1Path = folderPath + "/run_" + runIndex
                    + "/ErrorFile-Population1.txt";
            new SolutionListOutput(pop1)
                    .setSeparator("\t")
                    .setFunFileOutputContext(new DefaultFileOutputContext(pop1Path))
                    .print();

            String pop2Path = folderPath + "/run_" + runIndex
                    + "/ErrorFile-Population2.txt";
            new SolutionListOutput(pop2)
                    .setSeparator("\t")
                    .setFunFileOutputContext(new DefaultFileOutputContext(pop2Path))
                    .print();

            System.err
                    .println("uniqueMergedPopulation is less than population size!");
            System.exit(0);
        }

        IBEAFitness ibeaFit = new IBEAFitness(problem);
        ibeaFit.calculateFitness(mergedPopulation, numberOfObj);
        while (mergedPopulation.size() > populationSize) {
            ibeaFit.removeWorst(mergedPopulation);
        }

        /*
		 * Spea2Fitness spea = new Spea2Fitness(mergedPopulation);
		 * spea.fitnessAssign(); mergedPopulation =
		 * spea.environmentalSelection(popluationSize);
         */
        return mergedPopulation;
    }

    // return 1 if two solutions sol1 and sol2 are the same; 0 otherwise.
    public boolean isSameSolution(Solution sol1, Solution sol2,
            int numberOfObjectives) {
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

    // update extreme values
    public void updateExtremeValues(List<S> solutions) {
        jmetal.qualityIndicator.util.MetricsUtil util_ = new jmetal.qualityIndicator.util.MetricsUtil();

        double[][] objectiveVectors = SolutionListUtils.writeObjectivesToMatrix(solutions);

        double[] tempMinimumValues = util_.getMinimumValues(objectiveVectors,
                numberOfObj);

        double[] tempMaximumValues = util_.getMaximumValues(objectiveVectors,
                numberOfObj);
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

    public void updateMaximumValues(List<S> solutions) {
        jmetal.qualityIndicator.util.MetricsUtil util_ = new jmetal.qualityIndicator.util.MetricsUtil();
        double[][] objectiveVectors = SolutionListUtils.writeObjectivesToMatrix(solutions);
        double[] tempMaximumValues = util_.getMaximumValues(objectiveVectors,
                numberOfObj);
        for (int j = 0; j < numberOfObj; j++) {
            if (tempMaximumValues[j] > maximumValues[j]) {
                maximumValues[j] = tempMaximumValues[j];
            }
        }

    }

    public void updateMiniumValues(List<S> solutions) {
        jmetal.qualityIndicator.util.MetricsUtil util_ = new jmetal.qualityIndicator.util.MetricsUtil();

        double[][] objectiveVectors = SolutionListUtils.writeObjectivesToMatrix(solutions);

        double[] tempMinimumValues = util_.getMinimumValues(objectiveVectors,
                numberOfObj);

        for (int i = 0; i < numberOfObj; i++) {
            if (tempMinimumValues[i] < minimumValues[i]) {
                minimumValues[i] = tempMinimumValues[i];
            }
        }
    }

    public List<S> getBestPopfromSolutionSetArray(List<S>[] resultSolutions, int numberOfObj) {

        List<S> Population = new ArrayList<S>(populationSize);
        List<S> tempInitialPopSet = new ArrayList<S>(resultSolutions.length * populationSize);
        for (int a = 0; a < resultSolutions.length; a++) {
            for (int sol = 0; sol < populationSize; sol++) {
                tempInitialPopSet.add(resultSolutions[a].get(sol));
            }
        }

        Ranking ranking = new DominanceRanking(tempInitialPopSet);
        int remain = populationSize;
        int index = 0;
        List<S> front = null;
        Distance distance = new EuclideanDistanceBetweenSolutionAndASolutionListInObjectiveSpace();
        // Obtain the next front
        front = ranking.getSubfront(index);
        Population.clear();

        while ((remain > 0) && (remain >= front.size())) {
            //Assign crowding distance to individuals
            distance.getDistance(front, numberOfObj);
            //Add the individuals of this front
            for (int k = 0; k < front.size(); k++) {
                Population.add(front.get(k));
            } // for

            //Decrement remain
            remain = remain - front.size();

            //Obtain the next front
            index++;
            if (remain > 0) {
                front = ranking.getSubfront(index);
            } // if        
        } // while

        // Remain is less than front(index).size, insert only the best one
        if (remain > 0) {  // front contains individuals to insert                        
            distance.getDistance(front, numberOfObj);
            front.sort(new CrowdingDistanceComparator<S>());
            for (int k = 0; k < remain; k++) {
                Population.add(front.get(k));
            } // for

            remain = 0;
        } // if 

        return Population;
    }


    public AnyProblemDILANew(int runNo, ProblemCreator problemCreator, int qtdAlgs) {
        algorithms=Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        folderPath = "HF_Config_Benchmark/Results/" + problemCreator.getProblemClass() + "/AM/DominanceInitalNew";
        configFile = "HF_Config_Benchmark/" + problemCreator.getProblemClass() + "ProblemSetting.txt";
        RewardPenaltyLA RPLA = new RewardPenaltyLA(configFile);
        al_ = RPLA;
        sl_ = rs;
        numberOfLLH_ = algorithms.length;
        llhs = new LLH[numberOfLLH_];
        for (int i = 0; i < numberOfLLH_; i++) {
            // llhs.add(new LLH(numberOfLLH_, al_));
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

        /*
		 * for(int i = 0; i < numberOfObj; i++){ reference[i] = 2 *(i+1) +1; }
         */
        heuristicList = new ArrayList<Integer>();

        minimumValues = new double[numberOfObj];
        maximumValues = new double[numberOfObj];

        for (int i = 0; i < algorithms.length; i++) {
            acceptedQtdValue += i;
        }
    }

    
    public List<S> runLA(HashMap<String, String> parameterSet) throws IOException, ClassNotFoundException,
    JMException, ConfigurationException {
int fixedGeneration = 1000;

int totalEval = Integer.parseInt(config.getProperty("MaxEvaluations"));
int arcSize = Integer.parseInt(config.getProperty("ArchiveSize"));
String SolutionType = config.getProperty("SolutionType");
int popSize = Integer.parseInt(config.getProperty("PopulationSize"));

double percentageEvlforIni = Double.parseDouble(parameterSet.get("PercentageofTotalEvl."));
//total number of evaluations for initialisation
int totalSamplingEvl = (int) (percentageEvlforIni * totalEval);
//how frequent we are going to do the performance ranking (i.e. stepSize)
int samplingFrequency = Integer.parseInt(parameterSet.get("SamplingFrequency"));
//the number of evaluations for each sample 
int eachSampleSize = (int) (totalSamplingEvl / samplingFrequency);

eachSampleSize = (int) (eachSampleSize / (popSize * algorithms.length)) * popSize;

if (eachSampleSize == 0) {
    eachSampleSize = popSize;
}

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
for (int problemIndex = 6; problemIndex < problemCreator.getQtdProblem(); problemIndex++) {

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
double[] realHypervolume = new double[problemInstances.length];
HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();
GSpread GS = new GSpread();
for (int instanceIndex = 6; instanceIndex < problemInstances.length; instanceIndex++) {
    int fixedSolutionEvl = fixedGeneration * populationSize;
    System.out.println(problemInstances[instanceIndex].getName() + " is running...");

    numberOfObj = problemInstances[instanceIndex].getNumberOfObjectives();

    reference = new double[numberOfObj];
    for (int i = 0; i < numberOfObj; i++) {
        reference[i] = 1.0;
    }

    minimumValues = new double[numberOfObj];
    maximumValues = new double[numberOfObj];

    // random chose the first heuristic to start with
    List<S> recordPop = null;
    List<S> currentPop = null;
    List<S> inputPop = null;
    List<S> initialPop = null;

    ArrayList<Double> hypervolumeList = new ArrayList<Double>();
    //ArrayList<Double> abHypervolume = new ArrayList<Double>();
    ArrayList<Integer> eachDPChosenHeuristic = new ArrayList<Integer>();
    // boolean isInitialIteration = false;

    ArrayList<Double> recordHypList = new ArrayList<Double>();
    ArrayList<Double> currentHypList = new ArrayList<Double>();
    ArrayList<Integer> betaValue = new ArrayList<Integer>();
    ArrayList<Double[]> selectioPro = new ArrayList<Double[]>();
    ArrayList<Double[]> updatedPro = new ArrayList<Double[]>();
    ArrayList<Double[]> updatedProNeg = new ArrayList<Double[]>();
    ArrayList<Double[]> rewardUpdate = new ArrayList<Double[]>();
    int chosenHeuristic = -1;
    double[] tabuList = new double[numberOfLLH_];
    for (int i = 0; i < numberOfLLH_; i++) {
        tabuList[i] = 0;
    }
    // for the purpose of computing hypervolume.
    initialiseExtremeValues();
    //double temAbsoluteHypervolume = 0.0;
    for (int i = 0; i < numberOfLLH_; i++) {
        llhs[i].intialiseTransPro(numberOfLLH_);
        llhs[i].intialiseQvalue(numberOfLLH_);
        llhs[i].intialiseExecutedNo();
        llhs[i].initialiseUtility();
        llhs[i].intialiseReward();
        llhs[i].intialistransTimes(numberOfLLH_);
        llhs[i].intialisetransferReward(numberOfLLH_);
    }
    heuristicList.clear();
    int remainEval = totalEval;
    int iteration = 0;
    boolean firstTimeRank = true;
    int[] rankOfAlgorithms = new int[numberOfLLH_];
    for (int i = 0; i < numberOfLLH_; i++) {
        rankOfAlgorithms[i] = -1;
    }

    boolean updatePro = true;
    // run each algorithm and rank them
    // Step 1. Generate the initial population
    List<S> initialPopulation = ProblemCreator.generateInitialPopulation(problemInstances[instanceIndex], popSize);

    // Step 2. Execute each algorithm use the same population for three
    // steps.
    List<S>[] resultSolutions = new List[algorithms.length];
    List<S>[] inputSolutions = new List[algorithms.length];

    for (int i = 0; i < algorithms.length; i++) {
        inputSolutions[i] = new ArrayList<S>(popSize);
        // at the beginning, all inputsolutions are set as the initialPopulation
        inputSolutions[i] = initialPopulation;
        resultSolutions[i] = new ArrayList<S>(popSize);
    }
    LLHInterface eachAlgorithm = null;
    //
    int stepSize = samplingFrequency;
    int[][] Rank = new int[algorithms.length][stepSize];
    for (int i = 0; i < algorithms.length; i++) {
        for (int j = 0; j < stepSize; j++) {
            Rank[i][j] = -1;
        }
    }
    double[][] trailHyp = new double[algorithms.length][stepSize];
    double[][] trailGeneralSpread = new double[algorithms.length][stepSize];
    int[] score = new int[algorithms.length];// how many times the algoirthm gain the best rank 0 in all time steps
    for (int i = 0; i < score.length; i++) {
        score[i] = 0;
    }
    int maximumHypPopIndex = -1;// remember the best population index
    int reRank = -1;//if reRank = 1, has equal ranks, rerank;							
    List<S> tempAppFront = null;
    AlgorithmCreator ac = new AlgorithmCreator(problemInstances[instanceIndex]);
    ac.setMaxEvaluationsAndPopulation(totalEval, populationSize);
    // after the dominance initialisation
    for (int t = 0; t < stepSize; t++) {
        for (int j = 0; j < algorithms.length; j++) {//run each algorithm consecutively 
            eachDPChosenHeuristic.add(j);
            eachAlgorithm = ac.create(j, remainEval);

            try {
                resultSolutions[j] = eachAlgorithm.execute(/*initialPopulation*/inputSolutions[j], eachSampleSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
            remainEval -= eachSampleSize;
            //iteration++;
            //iteration += 2;
            inputSolutions[j] = resultSolutions[j];
            //temAbsoluteHypervolume = hyp.hypervolumeForSolutionSet(inputSolutions[j] , referencePoint);
            //abHypervolume.add(temAbsoluteHypervolume);
            /*					Ranking frontRank = new Ranking(inputSolutions[j]);
				List<S> rankPF = frontRank.getSubfront(0);
				String RankingPFPath = folderPath+"/run_"+runIndex+"/"+"RankingPF_Real"+(instanceIndex+1)+"_"+algorithms[j]+"_t"+t+".txt";
				double[][] rankPFDbl = rankPF.writeObjectivesToMatrix();
				utils_.writeFront(RankingPFPath, rankPFDbl);*/

        }// end j, executing each algorithm for

        // find worst points for all the three populations
        for (int i = 0; i < algorithms.length; i++) {

            updateExtremeValues(resultSolutions[i]);
        }
        for (int k = 0; k < algorithms.length; k++) {
            //updateExtremeValues(resultSolutions[k]);

            trailHyp[k][t] = computeScaledHypervolume(resultSolutions[k]);
        }
        tempAppFront = utils_.getApproximateFronts(inputSolutions);

        for (int i = 0; i < trailGeneralSpread.length; i++) {
            trailGeneralSpread[i][t] = GS.generalizedSpread(inputSolutions[i], tempAppFront, numberOfObj);
            /*					if(Double.isNaN(trailGeneralSpread[i][t])){
					trailGeneralSpread[i][t]  = 1.0;
				}*/
        }

        // rank all algorithms at each time step.
        for (int rankIndex = 0; rankIndex < algorithms.length; rankIndex++) {
            int count = -1;
            double tempHyp = -Double.MAX_VALUE;
            for (int m = 0; m < algorithms.length; m++) {
                if (Rank[m][t] == -1) {

                    if (trailHyp[m][t] > tempHyp) {
                        tempHyp = trailHyp[m][t];
                        count = m;
                    }
                }
            }
            Rank[count][t] = rankIndex;
            /*					if(rankIndex ==0){
					maximumHypPopIndex = count;
					initialPopulation = resultSolutions[maximumHypPopIndex];
				}*/
        }// end rank each algorithm at time step t
        //initialPopulation = resultSolutions[maximumHypPopIndex];
    }// end t, timeStep
    String trailHypPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_trailHyp.txt";
    utils_.writeFront(trailHypPath, trailHyp);
    String trailGSPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_trailGS.txt";
    utils_.writeFront(trailGSPath, trailGeneralSpread);

    // Scoring all algorithms
    for (int s = 0; s < algorithms.length; s++) {
        for (int t = 0; t < stepSize; t++) {
            if (Rank[s][t] == 0) {
                score[s]++;
            }
        }
    }

    // rank the scores of all algorithms
    int[] scoreRank = new int[algorithms.length];
    for (int l = 0; l < scoreRank.length; l++) {
        scoreRank[l] = -1;
    }
    for (int i = 0; i < scoreRank.length; i++) {
        int tempMax = -Integer.MAX_VALUE;
        int count = -1;

        for (int scoreRanIndex = 0; scoreRanIndex < algorithms.length; scoreRanIndex++) {

            if (scoreRank[scoreRanIndex] == -1 && score[scoreRanIndex] > tempMax) {
                tempMax = score[scoreRanIndex];
                count = scoreRanIndex;
            }
        }
        if (count != -1) {
            scoreRank[count] = i;
            //if the same score, gives the same rank
            for (int j = 0; j < algorithms.length; j++) {
                if ((score[j] == score[count]) && (j != count)) {
                    scoreRank[j] = scoreRank[count];

                    reRank = 1;
                }
            }
        } else {
            break;
        }
    }
    //if has the same rank, use General spread to rerank
    String allRankFile = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_AllRankFile.txt";
    utils_.wirteInteger(allRankFile, Rank);
    String scoreFile = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_ScoreFile.txt";
    utils_.printIntegerArray(score, scoreFile);
    if (reRank == 1) {
        //add general spread values here
        //collect a temp approximate front set and get each algorithm's general spread.
        tempAppFront = utils_.getApproximateFronts(inputSolutions);
        double[] generalSpread = new double[llhs.length];
        for (int i = 0; i < generalSpread.length; i++) {
            generalSpread[i] = GS.generalizedSpread(inputSolutions[i], tempAppFront, numberOfObj);
            if (Double.isNaN(generalSpread[i])) {
                generalSpread[i] = 1.0;
            }
            /*			for(int j = 0; j < stepSize; j++){
					generalSpread[i] += trailGeneralSpread[i][j];
				}
				generalSpread[i] = generalSpread[i]/(double)stepSize;*/
        }
        String generalSpreadPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rankingGS.txt";
        utils_.printDoubleArray(generalSpread, generalSpreadPath);

        ArrayList<Integer> sameRankAlgorithms = new ArrayList<Integer>();
        for (int i = 0; i < llhs.length; i++) {
            for (int j = 0; j < llhs.length; j++) {
                if ((i != j) && (scoreRank[i] == scoreRank[j])) {
                    sameRankAlgorithms.add(j);
                }

            }//end j
            if (sameRankAlgorithms.size() > 0) {
                sameRankAlgorithms.add(i);
                break;
            }
        }//end i

        double[] newScores = new double[sameRankAlgorithms.size()];
        for (int k = 0; k < newScores.length; k++) {
            newScores[k] = scoreRank[sameRankAlgorithms.get(k)] + generalSpread[sameRankAlgorithms.get(k)];
        }
        int[] ranked = new int[newScores.length];
        for (int i = 0; i < ranked.length; i++) {
            ranked[i] = 0;
        }
        for (int l = 0; l < newScores.length; l++) {

            int index = -1;

            double tempMin = Double.MAX_VALUE;

            for (int m = 0; m < newScores.length; m++) {
                if ((ranked[m] == 0) && (newScores[m] < tempMin)) {
                    tempMin = newScores[m];
                    index = m;
                }
            }//end m
            ranked[index] = 1;
            scoreRank[sameRankAlgorithms.get(index)] += l;

        }
        //if after rerank, we have the same ranks happen between reranked algorithm(s) and no-need reranked algorithms, e.g., 0, 0, 1
        //the no-need re-ranked algorithms' original scoreRank +1

        int tempHighestRankInsameRankAlgorithms = -1;
        for (int s = 0; s < sameRankAlgorithms.size(); s++) {
            if (scoreRank[sameRankAlgorithms.get(s)] > tempHighestRankInsameRankAlgorithms) {
                tempHighestRankInsameRankAlgorithms = scoreRank[sameRankAlgorithms.get(s)];
            }
        }

        if (sameRankAlgorithms.size() < algorithms.length && tempHighestRankInsameRankAlgorithms < (algorithms.length - 1)) {

            for (int a = 0; a < algorithms.length; a++) {
                int exisitInSameRankAlgorithm = 0;//false
                for (int s = 0; s < sameRankAlgorithms.size(); s++) {
                    if (a == sameRankAlgorithms.get(s)) {
                        exisitInSameRankAlgorithm = 1;
                        break;
                    }
                }//end s

                if (exisitInSameRankAlgorithm == 0) {
                    scoreRank[a]++;
                }

            }//end a
        }
        int reRankSum = 0;
        for (int i = 0; i < llhs.length; i++) {
            reRankSum += scoreRank[i];
        }

        if (reRankSum != acceptedQtdValue) {
            System.out.println("Rerank sum " + reRankSum + " at " + runIndex + " problem " + (instanceIndex + 1));
        }
    }//end rerank

    String allScoreRank = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_AllScoreRank.txt";
    utils_.printIntegerArray(scoreRank, allScoreRank);

    double medianRank = Math.ceil((double) numberOfLLH_ / 2.0) - 1;

    for (int i = 0; i < numberOfLLH_; i++) {
        if (scoreRank[i] == 0) {
            maximumHypPopIndex = i;
        }
        if (scoreRank[i] > medianRank) {
            tabuList[i] = 1.0;
        }
    }
    for (int tabuListIndex = 0; tabuListIndex < tabuList.length; tabuListIndex++) {
        // if tabued algorithm
        if (tabuList[tabuListIndex] == 1.0) {
            // set all other algorithm's transition probability to this
            // tabued algorithm as 0
            for (int i = 0; i < numberOfLLH_; i++) {
                llhs[i].getTransPro_().set(tabuListIndex, 0.0);
            }
            // set the transition probability from this tabued algorithm
            // to other algorithms
            for (int j = 0; j < numberOfLLH_; j++) {
                llhs[tabuListIndex].getTransPro_().set(j, 0.0);
            }
            llhs[tabuListIndex].setUtility(-Double.MAX_VALUE);

        }
    }
    // resize the transion probability if any algorithm is tabued.
    for (int j = 0; j < numberOfLLH_; j++) {
        double sumPro = 0.0;
        if (tabuList[j] != 1.0) {
            for (int index = 0; index < numberOfLLH_; index++) {
                sumPro += llhs[j].getTransPro_().get(index);
            }
            for (int k = 0; k < numberOfLLH_; k++) {
                double tempPro = llhs[j].getTransPro_().get(k) / sumPro;
                llhs[j].getTransPro_().set(k, tempPro);
            }
        }
    }

    String tabuListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_tabuList.txt";
    utils_.printDoubleArray(tabuList, tabuListPath);
    // finished the dominance selection initialisation

    chosenHeuristic = maximumHypPopIndex;
    //combine all the resultant solutions from all the low level heuristics to get the best population
    //initialPopulation = resultSolutions[maximumHypPopIndex];
    //get the best solution from the populations in the final stage.
    initialPopulation = getBestPopfromSolutionSetArray(resultSolutions, numberOfObj);
    iteration = (totalEval - remainEval) / fixedSolutionEvl;

    heuristicList.add(chosenHeuristic);// the initial choice
    int precessorHeuristic = chosenHeuristic;

    ArrayList<Integer> executedGeneration = new ArrayList<Integer>();
    ArrayList<Double> hypImprovementList = new ArrayList<Double>();

    int tempExecutedGen = 0;
    algorithm[instanceIndex] = ac.create(chosenHeuristic, remainEval);
    inputPop = initialPopulation;
    try {
        currentPop = algorithm[instanceIndex].execute(inputPop,
                fixedSolutionEvl);
    } catch (Exception e) {
        // } catch (ClassNotFoundException | JMException e) {
        e.printStackTrace();
    }
    inputPop = currentPop;
    eachDPChosenHeuristic.add(chosenHeuristic);
    //temAbsoluteHypervolume = hyp.hypervolumeForSolutionSet(currentPop, referencePoint);
    //abHypervolume.add(temAbsoluteHypervolume);

    tempExecutedGen += fixedGeneration;
    iteration++;
    updateExtremeValues(inputPop);
    double currentHypervolume = computeScaledHypervolume(inputPop);
    double initialHyp = 0.0;
    initialPop = inputPop;

    // isInitialIteration = false;
    remainEval -= fixedSolutionEvl;
    recordHypList.add(currentHypervolume);
    currentHypList.add(currentHypervolume);

    hypervolumeList.add(currentHypervolume);
    // execute the chosen heuristic till convergence criteria satisfied
    double hypImprovement = Double.MAX_VALUE;
    double previousHyp = 0.0;
    //
    while (hypImprovement > hyperImproveLimit && tempExecutedGen < maximumIterations && remainEval > 0) {

        if (fixedSolutionEvl > remainEval) {
            fixedSolutionEvl = remainEval;
        }

        currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl);
        //temAbsoluteHypervolume = hyp.hypervolumeForSolutionSet(currentPop, referencePoint);
        //abHypervolume.add(temAbsoluteHypervolume);

        eachDPChosenHeuristic.add(chosenHeuristic);
        iteration++;
        remainEval -= fixedSolutionEvl;
        inputPop = currentPop;
        updateExtremeValues(inputPop);
        currentHypervolume = computeScaledHypervolume(currentPop);
        /*
			 * if(currentHypervolume > bestHyp){ bestHyp =
			 * currentHypervolume; recordHypList.add(currentHypervolume);
			 * recordPop = currentPop; }
         */
        previousHyp = currentHypList.get(currentHypList.size() - 1);
        hypImprovement = (currentHypervolume - previousHyp)
                / previousHyp;
        tempExecutedGen += fixedGeneration;
        currentHypList.add(currentHypervolume);
    }

    /* recordPop = currentPop; */
    double hypImprovementFinal = 0.0;
    // hypImprovementFinal = (currentHypervolume -
    // initialHyp)/initialHyp;

    initialHyp = currentHypervolume;
    executedGeneration.add(tempExecutedGen);
    // hypImprovmentList is used to store the hypervolume improvement
    // between two heuristics.
    // hypImprovementList.add(hypImprovementFinal);
    // reset the temple executed generation and hypImprovement
    tempExecutedGen = 0;
    hypImprovement = Double.MAX_VALUE;
    for (int i = 0; i < numberOfLLH_; i++) {
        Double[] tempRewardUpdate = llhs[i].getQvalue().toArray(new Double[numberOfLLH_]);
        rewardUpdate.add(tempRewardUpdate);
    }

    double lamada2 = 0.1;
    ArrayList<Integer> rejectList = new ArrayList<Integer>();
    ArrayList<ArrayList> rejectRecord = new ArrayList<ArrayList>();
    /*			double InitialTemperature = 10.0;
		double Temperature = InitialTemperature;
		double decayFactor = Math.pow((1.0 / InitialTemperature),
				1.0 / (totalFixedGeneration / 2));
		ArrayList<Double> temperatureList = new ArrayList<Double>();
		temperatureList.add(Temperature);*/
    double epsilon = 0.0;
    int tempIteration = (int) (remainEval / fixedSolutionEvl * learningPhase);
    int tempRemainEvl = remainEval;
    while (remainEval > 0) {
        int nextHeuristic = -1;

        if (iteration < tempIteration) {
            if (precessorHeuristic == -1) {
                System.out.println("precessorHeuristic = -1!");
                System.exit(0);
            }
            //epsilon = 0.01 * iteration;
            nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].getTransPro_(), epsilon);
            // nextHeuristic =
            // sl_.selectionLLHs(llhs[precessorHeuristic].transPro_);
            if (tabuList[nextHeuristic] == 1.0) {
                System.out.println("Tabued heuristic is chosen!");
                System.exit(0);
            }
        } else {
            //epsilon = 0.5+0.01 * iteration;
            epsilon = learningPhase + (1.0 - learningPhase) / (tempRemainEvl / fixedSolutionEvl) * iteration;
            double tabuSum = 0;
            for (double tabuIndex : tabuList) {
                tabuSum += tabuIndex;

            }

            if (tabuSum >= numberOfLLH_ - 1) {

                for (int i = 0; i < numberOfLLH_; i++) {
                    if (tabuList[i] == 0.0) {
                        nextHeuristic = i;
                        precessorHeuristic = nextHeuristic;
                    }
                }
                if (nextHeuristic == -1) {
                    nextHeuristic = maximumHypPopIndex;
                    precessorHeuristic = nextHeuristic;
                }
                if (precessorHeuristic == -1) {
                    System.out.println("precessorHeuristic = -1!");
                    System.exit(0);
                }
                updatePro = false;

            } else {

                nextHeuristic = sl_.selectionLLHs(llhs[precessorHeuristic].getTransPro_(), epsilon);
                while (tabuList[nextHeuristic] == 1.0) {
                    System.out.println("Tabued heuristic is chosen!");
                    System.exit(0);
                }

            }
        }
        if (precessorHeuristic < 0 || precessorHeuristic > algorithms.length - 1) {
            System.out.println("Invalid precessorHeuristic index!" + precessorHeuristic);
            System.exit(0);
        }
        if (nextHeuristic < 0 || nextHeuristic > algorithms.length - 1) {
            System.out.println("Invalid nextHeuristic index!" + nextHeuristic);
            System.exit(0);
        }
        double tempTransTimes = llhs[precessorHeuristic].getTransTimes().get(nextHeuristic) + 1.0;
        llhs[precessorHeuristic].getTransTimes().set(nextHeuristic,
                tempTransTimes);

        // half of the total iterations, we go greedy
        for (int i = 0; i < numberOfLLH_; i++) {
            Double[] selectionProTemp = llhs[i].getTransPro_()
                    .toArray(new Double[numberOfLLH_]);

            selectioPro.add(selectionProTemp);
        }

        heuristicList.add(nextHeuristic);

        int rejectCount = 1;
        algorithm[instanceIndex] = ac.create(nextHeuristic, remainEval);
        while (hypImprovement > hyperImproveLimit && tempExecutedGen < maximumIterations && remainEval > 0) {
            if (fixedSolutionEvl > remainEval) {
                fixedSolutionEvl = remainEval;
            }

            currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl); // TODO Auto-generated catch block
            // TODO Auto-generated catch block
            inputPop = currentPop;
            //temAbsoluteHypervolume = hyp.hypervolumeForSolutionSet(currentPop, referencePoint);
            //abHypervolume.add(temAbsoluteHypervolume);

            eachDPChosenHeuristic.add(nextHeuristic);
            iteration++;
            remainEval -= fixedSolutionEvl;

            updateExtremeValues(inputPop);
            currentHypervolume = computeScaledHypervolume(inputPop);
            previousHyp = currentHypList.get(currentHypList.size() - 1);

            currentHypList.add(currentHypervolume);

            hypImprovement = (currentHypervolume - previousHyp)
                    / previousHyp;
            tempExecutedGen += fixedGeneration;

        }

        hypImprovementFinal = currentHypervolume - initialHyp;

        double tempTransferReward = llhs[precessorHeuristic].getTransferReward().get(nextHeuristic) + hypImprovementFinal;
        llhs[precessorHeuristic].getTransferReward().set(nextHeuristic, tempTransferReward);

        if (precessorHeuristic < 0 || precessorHeuristic > algorithms.length - 1) {
            System.out.println("Invalid precessorHeuristic index!" + precessorHeuristic);
            System.exit(0);
        }
        if (nextHeuristic < 0 || nextHeuristic > algorithm.length - 1) {
            System.out.println("Invalid nextHeuristic index!" + nextHeuristic);
            System.exit(0);
        }

        try {

            // double tempQvalue =
            // (llhs[precessorHeuristic].transferReward.get(nextHeuristic))/llhs[precessorHeuristic].transTimes.get(nextHeuristic);
            double tempQvalue = (hypImprovementFinal - llhs[precessorHeuristic].getQvalue().get(nextHeuristic)) * alpha
                    + llhs[precessorHeuristic].getQvalue()
                            .get(nextHeuristic);

            /*
				 * if(tempQvalue <0.0){ tempQvalue = 0.0;
				 * //System.out.println("Negative tempQvalue:" +
				 * tempQvalue); }
             */
            llhs[precessorHeuristic].getQvalue().set(nextHeuristic,
                    tempQvalue);
            // llhs[nextHeuristic].Qvalue.set(precessorHeuristic,
            // tempQvalue);//we see hi to hj is equalvilant to hj to hi

            for (int i = 0; i < numberOfLLH_; i++) {
                Double[] tempRewardUpdate = llhs[i].getQvalue()
                        .toArray(new Double[numberOfLLH_]);
                rewardUpdate.add(tempRewardUpdate);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }

        executedGeneration.add(tempExecutedGen);

        // hypImprovmentList is used to store the hypervolume
        // improvement between two heuristics.
        hypImprovementList.add(hypImprovementFinal);
        llhs[nextHeuristic].setExecutedNo(
                llhs[nextHeuristic].getExecutedNo() + tempExecutedGen);
        llhs[nextHeuristic]
                .setReward(llhs[nextHeuristic].getReward() + hypImprovementFinal);
        llhs[nextHeuristic].setUtility(llhs[nextHeuristic].getReward()
                / llhs[nextHeuristic].getExecutedNo());

        // reset the temple executed generation and hypImprovement
        tempExecutedGen = 0;
        hypImprovement = Double.MAX_VALUE;
        // if initialHyp is improved then 1, otherwise 0;
        int beta = rewardSignalFeedback(currentHypervolume, initialHyp);
        betaValue.add(beta);
        initialHyp = currentHypervolume;
        /*				inputPop = getTheBestPopulation(inputPop,initialPop);				
			initialPop = inputPop;*/

        // if(currentHypervolume > bestHyp){
        //
        // bestHyp = currentHypervolume;
        // recordHypList.add(currentHypervolume);
        // }
        if (updatePro) {
            // update the probability of the previous heuristic's probabilities of transition to the chosen heuristic and other heuristics
            if (beta == 1) {
                // rejectList.clear();
                // rejectCount = 1;
                // al_.updateProbabilityPositiveSignal(nextHeuristic,
                // llhs[precessorHeuristic].transPro_);
                al_.updateProbabilityPositiveSignalQ(nextHeuristic, llhs[precessorHeuristic].getTransPro_(), llhs[precessorHeuristic].getQvalue());
                double tempPro = llhs[precessorHeuristic].getTransPro_().get(nextHeuristic);

                for (int i = 0; i < numberOfLLH_; i++) {
                    Double[] updateProTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                    updatedPro.add(updateProTemp);
                }

                // inputPop = currentPop;
                // inputPop = getTheBestPopulation(currentPop,
                // recordPop);
                // recordPop = inputPop;
                precessorHeuristic = nextHeuristic;
            } else if (beta == 0) {

                al_.updateProbabilityNegativeSignalQ(nextHeuristic, llhs.length, llhs[precessorHeuristic].getTransPro_(), llhs[precessorHeuristic].getQvalue());

                double tempPro = llhs[precessorHeuristic].getTransPro_().get(nextHeuristic);

                Double[] updateProTemp = llhs[precessorHeuristic].getTransPro_().toArray(new Double[numberOfLLH_]);
                updatedPro.add(updateProTemp);

                for (int i = 0; i < numberOfLLH_; i++) {
                    Double[] updateProNegTemp = llhs[i].getTransPro_().toArray(new Double[numberOfLLH_]);
                    updatedProNeg.add(updateProNegTemp);
                }
                precessorHeuristic = nextHeuristic;
                // inputPop = currentPop;
                // inputPop = getTheBestPopulation(currentPop,
                // recordPop);
                // inputPop = recordPop;
                // precessorHeuristic = nextHeuristic;
            }
        }

    }//end of remain >0  
    String arrayListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_ChosenHeuristic.txt";
    utils_.printArrayListInteger(heuristicList, arrayListPath);

    String betaValueListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rewardList.txt";
    utils_.printArrayListInteger(betaValue, betaValueListPath);

    // print selection probability
    String SelectionProPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_SelectionProList.txt";
    utils_.printArrayListDoubleArray(selectioPro, SelectionProPath);

    // print updated probability
    /*
		 * String UpdatedProPath = folderPath + "/DominanceInital/run_"+runIndex +"/WFG"+(instanceIndex+1)+"_UpdatePostiveProList.txt";
		 * utils_.printArrayListDoubleArray(updatedPro,UpdatedProPath);
		 * String UpdatedNegProPath = folderPath +"/DominanceInital/run_"+runIndex+"/WFG"+(instanceIndex+1)+"_UpdateNegativeProList.txt";
		 * utils_.printArrayListDoubleArray(updatedProNeg,UpdatedNegProPath);
     */
    String rewardUpdatePath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_rewardUpdate.txt";
    utils_.printArrayListDoubleArray(rewardUpdate, rewardUpdatePath);
    // print reject heuristic and times of rejection
    // String rejectRecordPath = objConfig +"/DominanceInital/run_"+runIndex+"/RejectList.txt";
    // utils_.printArrayListArraylist(rejectRecord,rejectRecordPath);
    // print recorded hypervolume
    String hypImprovementListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_hypImprovementList.txt";
    utils_.printArrayListDouble(hypImprovementList, hypImprovementListPath);

    String executedGenerationPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_executedGeneration.txt";
    utils_.printArrayListInteger(executedGeneration, executedGenerationPath);

    // print current hypervolume
    String currentHypListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_currentHypList.txt";
    utils_.printArrayListDouble(currentHypList, currentHypListPath);
    // print threshold
    /*
		 * String thresholdPath = folderPath +"/DominanceInital/run_"+runIndex +"/Real"+(instanceIndex+1)+"_threshold.txt";
		 * utils_.printArrayListDouble(threshold, thresholdPath);
     */
/*		String temperaturePath = folderPath + "/DominanceInital/run_"+ runIndex + "/" +problemCreator.getProblemClass()+ (instanceIndex + 1)+ "_temperature.txt";
		utils_.printArrayListDouble(temperatureList, temperaturePath);*/
    // inputPop.printObjectivesToFile(objConfig+"/DominanceInital/run_"+runIndex+"/"+"LA_FinalPopulationObj_"+runIndex+".txt");
    List<S> obtainedSolutionSet = SolutionListUtils.getNondominatedSolutions(inputPop);
    new SolutionListOutput(obtainedSolutionSet)
            .setSeparator("\t")
            .setFunFileOutputContext(new DefaultFileOutputContext(folderPath + "/run_" + runIndex + "/" + "LA_FinalParetoFront_Real" + (instanceIndex + 1) + "_Run" + runIndex + ".txt"))
            .print();
    //print out true front's hypervolume

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

    //System.out.println(((ExternalProblem) problemInstances[instanceIndex]).getQtdEvaluated());
}// for each instanceIndex

/*		String hypervolumePath = folderPath + "/DominanceInital/run_"+ runIndex + "/hypervolume.txt";
	utils_.printArray(realHypervolume, hypervolumePath);*/
return null;

}
    
}
