package HF_LA;

import helpers.AlgorithmCreator;
import helpers.ProblemCreator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.management.JMException;
import jmetal.qualityIndicator.GSpread;
import jmetal.qualityIndicator.HyperVolumeMinimizationProblem;
import jmetal.qualityIndicator.util.IBEAFitness;
import jmetal.qualityIndicator.util.MetricsUtilPlus;

import org.apache.commons.io.FileUtils;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.impl.GeneralizedSpread;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.LLHInterface;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.EpsilonCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.HypervolumeCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.IgdCalculator;
import br.inpe.cocte.labac.hrise.jhelper.util.metrics.SpreadCalculator;
import br.inpe.cocte.labac.hrise.util.PopulationHandler;
import br.inpe.cocte.labac.hrise.util.ProblemsWrapperLARILA;
import br.inpe.cocte.labac.hrise.util.SaveFiles;
import br.inpe.cocte.labac.hrise.util.StatEvalSupport;

public class BechmarkDILAnew<S extends Solution<?>> extends LALearning {

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
    
    protected int acceptedQtdValue;

    // the increase speed of the threshold of hypervolume
    double increaseSpeed = 1.0;
    ArrayList<Double> threshold = new ArrayList<Double>();

    MetricsUtilPlus utils_ = new MetricsUtilPlus();
    Properties config = new Properties();

    //String algorithms[] = {"NSGAII", "SPEA2", "IBEA", "mIBEA", "GDE3"};
    String algorithms[] = {"NSGAII", "SPEA2", "IBEA"};
    String folderPath = null;

    // RouletteSelectionLLHs rs = new RouletteSelectionLLHs();//
    epsilonGreedySelectionLLHs rs = new epsilonGreedySelectionLLHs();// modified!!!

    // GreedySelectionLLHs rs = new GreedySelectionLLHs();
    // SoftMaxSelectionLLHs rs = new SoftMaxSelectionLLHs();
    int fixedGeneration;
    protected final ProblemCreator problemCreator;

    public BechmarkDILAnew(String configFile, HashMap<String, String> parameterSet, int runNo, String outputfolderpath, int fixedGeneration, ProblemCreator problemCreator, int qtdAlgs) {
        algorithms = Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        this.fixedGeneration = fixedGeneration;
        folderPath = outputfolderpath;
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
        numberOfObj = Integer.parseInt(config.getProperty("ObjectiveNumber"));
        reference = new double[numberOfObj];
        for (int i = 0; i < numberOfObj; i++) {
            reference[i] = 1.0;
        }

        /*
		 * for(int i = 0; i < numberOfObj; i++){ reference[i] = 2 *(i+1) +1; }
         */
        heuristicList = new ArrayList<Integer>();

        minimumValues = new double[numberOfObj];
        maximumValues = new double[numberOfObj];

        double multiplier_ = Double.parseDouble(parameterSet.get("Multiplier"));
        RewardPenaltyLA RPLA = new RewardPenaltyLA(multiplier_);
        al_ = RPLA;
        sl_ = rs;
    }

    
    /*
     * This is the constructor called.
     */
    
    public BechmarkDILAnew(String configFile, int runNo, int fixedGeneration, ProblemCreator problemCreator, int qtdAlgs) {
        algorithms = Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        this.fixedGeneration = fixedGeneration;
        //folderPath = "HF_Config_Benchmark/Results/LA/DominanceInitalNew_" + fixedGeneration;
        System.out.println("DILA NEW");
        folderPath = "HF_Config_Benchmark/Results/RILA/Iter_" + fixedGeneration;
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
        numberOfObj = Integer.parseInt(config.getProperty("ObjectiveNumber"));
        reference = new double[numberOfObj];
        for (int i = 0; i < numberOfObj; i++) {
            reference[i] = 1.0;
        }
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
     * This is the runLA related to RILA. The one which is really executed.
     * 
     */
    
    public List<S> runLA() throws IOException, ClassNotFoundException,
            JMException {
        // TODO Auto-generated method stub
        int fixedSolutionEvl = fixedGeneration * populationSize; // Evaluations = 10 *100 = 1000; Iterations = fixedGeneration = 10

        int totalEval = Integer.parseInt(config.getProperty("MaxEvaluations"));

        System.out.println("Evaluations now: " + fixedSolutionEvl + " - Evaluations Total: " + totalEval);
        
        String SolutionType = config.getProperty("SolutionType");
        int arcSize = Integer.parseInt(config.getProperty("ArchiveSize"));
        int popSize = Integer.parseInt(config.getProperty("PopulationSize"));

        double learningPhase = Double.parseDouble(config.getProperty("LearningPhase"));
        int maximumIterations = Integer.parseInt(config.getProperty("MaximumIterations")); // trials, indeed
        double hyperImproveLimit = Double.parseDouble(config.getProperty("HypImprovment"));
        Double crossoverProb = Double.parseDouble(config.getProperty("CrossoverProb"));
        Double crossoverDistributionIndex = Double.parseDouble(config.getProperty("CrossoverDistributionIndex"));
        Double mutationDistributionIndex = Double.parseDouble(config.getProperty("MutationDistributionIndex"));

        System.out.println("Iterations Total = Trials: " + maximumIterations + " - Pop Size: " + popSize + " - Archive Size: " + arcSize);
        
        System.out.println("Hyper improv: " + hyperImproveLimit + " - Cross prob: " + crossoverProb + " - Cross Dist Index: " + crossoverDistributionIndex + 
        		" - Muta Distribution Index: " + mutationDistributionIndex);
        
        //if (config.getProperty("DistanceParameters") != null) {
          //  int distancePara = Integer.parseInt(config.getProperty("DistanceParameters"));//l
            //int positionPara = Integer.parseInt(config.getProperty("PositionParameters"));//k
            //numberOfObj = Integer.parseInt(config.getProperty("ObjectiveNumber"));//k
            //problemCreator.addParam("l", distancePara);
            //problemCreator.addParam("k", positionPara);
            //problemCreator.addParam("m", numberOfObj);
        //}
        
        
        

        Problem[] problemInstances = new Problem[problemCreator.getQtdProblem()];
        int[] numberOfVar = new int[problemCreator.getQtdProblem()];
        double[] mutationProb = new double[problemCreator.getQtdProblem()];
        Operator[] mutations = new Operator[problemCreator.getQtdProblem()];

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
            HashMap parameters = new HashMap();
            parameters.put("probability", mutationProb[problemIndex]);
            mutationDistributionIndex = Double.parseDouble(config.getProperty("MutationDistributionIndex"));
            parameters.put("distributionIndex", mutationDistributionIndex);
            String mutationType = config.getProperty("MutationType");
            mutations[problemIndex] = new PolynomialMutation(mutationProb[problemIndex], mutationDistributionIndex);
        }

        LLHInterface[] algorithm = new LLHInterface[problemCreator.getQtdProblem()];
        System.out.println("Qtd prob: " + problemCreator.getQtdProblem());
        
        
        // UPDATE REFERENCES
        
       // double[] realReference = new double[numberOfObj];
        //switch(problemCreator.getProblemClass()) {
        	//case "WFG":
        		//for (int k = 0; k < numberOfObj; k++) {
                  //  realReference[k] = 2 * (k + 1) + 1;
                //}
        		//break;
        	//case "DTLZ":
        		
        		
        //}
        //for (int k = 0; k < numberOfObj; k++) {
          //  realReference[k] = 2 * (k + 1) + 1;
        //}
        
        
        int totalFixedGeneration = totalEval / fixedSolutionEvl;
        //System.out.println("\nTotal Fixed Generation = Decision Points: " + totalFixedGeneration);
        double[] realHypervolume = new double[problemInstances.length];
        HyperVolumeMinimizationProblem hyp = new HyperVolumeMinimizationProblem();
        GSpread GS = new GSpread();

        for (int instanceIndex = 0; instanceIndex < problemInstances.length; instanceIndex++) {  //############# MAIN EXECUTION PART: BEGIN
        	
        	  // UPDATE REFERENCES
            
            double[] realReference = new double[numberOfObj];
            switch(problemCreator.getProblemClass()) {
            	case "WFG":
            		for (int k = 0; k < numberOfObj; k++) {
                        realReference[k] = 2.0 * (k + 1.0) + 1.0;
                    }
            		break;
            	case "DTLZ":
            		if (instanceIndex == 0) {
            			for (int k = 0; k < numberOfObj; k++) {
                            realReference[k] = 0.5;
                        }	
            		} else if ( (instanceIndex == 1) || (instanceIndex == 2) || (instanceIndex == 3) || (instanceIndex == 4) ||
            				(instanceIndex == 5)) {
            					for (int k = 0; k < numberOfObj; k++) {
                                    realReference[k] = 1.0;
                                }
            	    } else if (instanceIndex == 6) {
            	    	       realReference[0] = 1.0;
            	    	       realReference[1] = 1.0;
            	    	       realReference[2] = 2.0*numberOfObj;
            	    } else {
            	    	System.out.println("Error Reference");
            	    }	
            		break;
            	default:
            		System.out.println("Wrong Class of Problem");
             }
            
            System.out.println("**************************** Real Reference: " );
            for (int indRealRef = 0 ; indRealRef < realReference.length; indRealRef++) {
            	System.out.println(realReference[indRealRef]);
            }
            //for (int k = 0; k < numberOfObj; k++) {
              //  realReference[k] = 2 * (k + 1) + 1;
            //}
        	
        	
        	StatEvalSupport stindic = StatEvalSupport.getInstance(); // Class to record and save the indicators for the statistical evaluation
            // Delete result directory
        	//if (instanceIndex == 0) {
              //FileUtils.deleteDirectory(new File("result"));
              //FileUtils.deleteDirectory(new File("exec"));
        	//}
            String versionHH = "RILA";
        	
        	
        	
            // random chose the first heuristic to start with
            List<S> recordPop = null;
            List<S> currentPop = null;
            List<S> inputPop = null;
            List<S> initialPopulation = ProblemCreator.generateInitialPopulation(problemInstances[instanceIndex], popSize);
            List<S> initialPop = null;

            ArrayList<Double> hypervolumeList = new ArrayList<Double>();
            ArrayList<Double> abHypervolume = new ArrayList<Double>();
            ArrayList<Integer> executedGeneration = new ArrayList<Integer>();
            ArrayList<Integer> eachDPChosenHeuristic = new ArrayList<Integer>();
            // boolean isInitialIteration = false;

            ArrayList<Double> recordHypList = new ArrayList<Double>();
            ArrayList<Double> currentHypList = new ArrayList<Double>();
            ArrayList<Integer> betaValue = new ArrayList<Integer>();
            double bestHyp;
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
            double temAbsoluteHypervolume = 0.0;
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
            int[] rankOfAlgorithms = new int[numberOfLLH_];
            for (int i = 0; i < numberOfLLH_; i++) {
                rankOfAlgorithms[i] = -1;
            }

            boolean updatePro = true;
            // run each algorithm and rank them
            // Step 1. Generate the initial population

            // Step 2. Execute each algorithm use the same population for three
            // steps.
            List[] resultSolutions = new List[algorithms.length];
            List[] inputSolutions = new List[algorithms.length];

            for (int i = 0; i < algorithms.length; i++) {
                inputSolutions[i] = new ArrayList(popSize);
                // at the beginning, all inputsolutions are set as the initialPopulation
                inputSolutions[i] = initialPopulation;
                resultSolutions[i] = new ArrayList(popSize);
            }
            LLHInterface eachAlgorithm = null;
            //algorithms.length;
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
            System.out.println("%%%%%%% TRIAL: " + (runIndex + 1) + "  -  PROBLEM: " + problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives() + " - VAR TOTAL: " + problemInstances[instanceIndex].getNumberOfVariables());
            ac.setMaxEvaluationsAndPopulation(totalEval, populationSize);

            // after the dominance initialisation
            for (int t = 0; t < stepSize; t++) {
                for (int j = 0; j < algorithms.length; j++) {//run each algorithm consecutively 
                    eachDPChosenHeuristic.add(j);

                    //System.out.println("Remain Eval: " + remainEval);
                    eachAlgorithm = ac.create(j, remainEval);

                    try {
                    	 //System.out.println("How many evaluations: " + fixedSolutionEvl); // Para o SPEA2 deveria ser fixedGeneration = 10 e nao fixedSolutionEvl = 1000?
                        resultSolutions[j] = eachAlgorithm.execute(/*initialPopulation*/inputSolutions[j], fixedSolutionEvl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    remainEval -= fixedSolutionEvl;
                    iteration++;
                    //iteration += 2;
                    inputSolutions[j] = resultSolutions[j];
                    temAbsoluteHypervolume = hyp.hypervolumeForSolutionSet(inputSolutions[j], realReference);
                    abHypervolume.add(temAbsoluteHypervolume);
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
            
            File runFolder = new File(folderPath + "/run_" + runIndex+"/");
            //File runFolder = new File(folderPath+"\\LAResutls\\run_"+runIndex);	
            if (!runFolder.exists()) {
                if (runFolder.mkdirs()) {
                    //System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                    System.exit(0);
                }
            }
            
            
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

                //if after rerank, we have the same ranks happen between reranked algorithm(s) and no-need reranked algorithms,
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

            ArrayList<Double> hypImprovementList = new ArrayList<Double>();

            int tempExecutedGen = 0;

            algorithm[instanceIndex] = ac.create(chosenHeuristic, remainEval);
            // Execute the Algorithm

            inputPop = resultSolutions[maximumHypPopIndex];
            try {
                currentPop = algorithm[instanceIndex].execute(inputPop,
                        fixedSolutionEvl);
            } catch (Exception e) {
                // } catch (ClassNotFoundException | JMException e) {
                e.printStackTrace();
            }
            inputPop = currentPop;

            eachDPChosenHeuristic.add(chosenHeuristic);
            temAbsoluteHypervolume = hyp.hypervolumeForSolutionSet(currentPop, realReference);
            abHypervolume.add(temAbsoluteHypervolume);

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

                temAbsoluteHypervolume = hyp.hypervolumeForSolutionSet(currentPop, realReference);
                abHypervolume.add(temAbsoluteHypervolume);
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
                    Double[] selectionProTemp = llhs[i].getTransPro_()
                            .toArray(new Double[numberOfLLH_]);

                    selectioPro.add(selectionProTemp);
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
                    currentPop = algorithm[instanceIndex].execute(inputPop, fixedSolutionEvl); // TODO Auto-generated catch block
                    // TODO Auto-generated catch block
                    inputPop = currentPop;
                    eachDPChosenHeuristic.add(nextHeuristic);
                    iteration++;
                    remainEval -= fixedSolutionEvl;
                    temAbsoluteHypervolume = hyp.hypervolumeForSolutionSet(currentPop, realReference);
                    abHypervolume.add(temAbsoluteHypervolume);

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
                if (nextHeuristic < 0 || nextHeuristic > algorithms.length - 1) {
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

            }
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

            String eachDPChosenHeuristicPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_eachDPChosenHeuristicList.txt";
            utils_.printArrayListInteger(eachDPChosenHeuristic, eachDPChosenHeuristicPath);
            // print current hypervolume
            String currentHypListPath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_currentHypList.txt";
            utils_.printArrayListDouble(currentHypList, currentHypListPath);

            String absoluteHypervolumePath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_absoluteHypList.txt";
            utils_.printArrayListDouble(abHypervolume, absoluteHypervolumePath);
            // print threshold
            /*
			 * String thresholdPath = folderPath +"/DominanceInital/run_"+runIndex +"/WFG"+(instanceIndex+1)+"_threshold.txt";
			 * utils_.printArrayListDouble(threshold, thresholdPath);
             */
 /*			String temperaturePath = folderPath + "/DominanceInital/run_"+ runIndex + "/WFG" + (instanceIndex + 1)+ "_temperature.txt";
			utils_.printArrayListDouble(temperatureList, temperaturePath);*/
            // inputPop.printObjectivesToFile(objConfig+"/DominanceInital/run_"+runIndex+"/"+"LA_FinalPopulationObj_"+runIndex+".txt");
            List<S> obtainedSolutionSet = SolutionListUtils.getNondominatedSolutions(inputPop);
            new SolutionListOutput(obtainedSolutionSet)
                    .setSeparator("\t")
                    .setFunFileOutputContext(new DefaultFileOutputContext(folderPath + "/run_" + runIndex + "/" + "LA_FinalParetoFront_" + problemCreator.getProblemClass() + (instanceIndex + 1) + "_Run" + runIndex + ".txt"))
                    .print();

            realHypervolume[instanceIndex] = hyp.hypervolumeForSolutionSet(obtainedSolutionSet, realReference);
            
         // My Quality Indicators
            //for (int irh = 0; irh < realHypervolume.length; irh++) {
            System.out.println("Pop Final HH - Indicators: " +  printResults(obtainedSolutionSet, problemInstances[instanceIndex]));
            // Save FUN e VAR of the HH
            SaveFiles sFV = new SaveFiles();
     		sFV.saveFunVar(this.runIndex, versionHH, problemInstances[instanceIndex], obtainedSolutionSet);
            System.out.println("Pop Final HH - Indicators: " +  printResultsAsList(obtainedSolutionSet, problemInstances[instanceIndex], true));
           // }
            
            //stindic.saveFileStatEval(versionHH, versionHH + "_hypervolume", stindic.getAllHypervolume(), problemInstances[instanceIndex]);
  		    //stindic.saveFileStatEval(versionHH, versionHH + "_igd", stindic.getAllIGD(), problemInstances[instanceIndex]);
  		    //stindic.saveFileStatEval(versionHH, versionHH + "_epsilon", stindic.getAllEpsilon(), problemInstances[instanceIndex]);
  		    //stindic.saveFileStatEval(versionHH, versionHH + "_spread", stindic.getAllSpread(), problemInstances[instanceIndex]);
  		    
  		    
  		    stindic.saveFileStatEvalAppend(versionHH, versionHH + "_hypervolume", stindic.getAllHypervolume(), problemInstances[instanceIndex], runIndex);
		    stindic.saveFileStatEvalAppend(versionHH, versionHH + "_igd", stindic.getAllIGD(), problemInstances[instanceIndex], runIndex);
		    stindic.saveFileStatEvalAppend(versionHH, versionHH + "_epsilon", stindic.getAllEpsilon(), problemInstances[instanceIndex], runIndex);
		    stindic.saveFileStatEvalAppend(versionHH, versionHH + "_spread", stindic.getAllSpread(), problemInstances[instanceIndex], runIndex);
  		    
  		// Copy result directory
  		  System.out.println("----------------- Copying Results.......................................................................");
  		  System.out.println(".......................................................................................................");
  		  System.out.println("........................................................................................................");
  		  System.out.println("........................................................................................................");
  		  copyResultDirectory("C:\\Users\\psavj\\Documents\\Des\\ProjEclipse\\HHLARILA\\result"+"\\"
  		                      +versionHH+"\\"+problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives() ,
  				             "C:\\Users\\psavj\\Documents\\Des\\Rst\\HRISE\\ResultsExec\\ALLRES\\" + problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives()+"\\"
  		               +versionHH+"\\"+problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives());
          
  		//copyResultDirectory("C:\\Users\\psavj\\Documents\\Des\\ProjEclipse\\HHLARILA\\result",
			//	  "C:\\Users\\psavj\\Documents\\Des\\Rst\\HRISE\\ResultsExec\\ALLRES\\" + problemInstances[instanceIndex].getName() + "_" + problemInstances[instanceIndex].getNumberOfObjectives());
  		  
  		stindic.clearAllIndicatorsStatEval();  
            
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

        } //############# MAIN EXECUTION PART: END

        String hypervolumePath = folderPath + "/run_" + runIndex + "/" + problemCreator.getProblemClass() +"_hypervolume.txt";
        utils_.printArray(realHypervolume, hypervolumePath);
        
        
        
        
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
 
 public String printResults(List archive, Problem problem) throws FileNotFoundException, IOException {
        
    	String strToReturn = "";
        //if (problem.getName().contains("WFG") || problem.getName().contains("DTLZ") || problem.getName().contains("VC") || problem.getName().contains("MAC")) {
            String pf
                    = "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
            
                        
            // HV: Convergence-Diversity
            PopulationHandler popHndList = new PopulationHandler();
            HypervolumeCalculator hyp = new HypervolumeCalculator(problem.getNumberOfObjectives(), pf);
            //hyp.updatePointsUsingNadir(popHndList.getNadir(problem.getName(), problem.getNumberOfObjectives()));
            hyp.updatePointsUsingNadirIdeal(popHndList.getNadir(problem.getName(), problem.getNumberOfObjectives()),
            		popHndList.getIdeal(problem.getName(), problem.getNumberOfObjectives()));
            double hypValue = hyp.execute(archive);
            
            // IGD: Convergence-Diversity
            IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
            double igdValue = igd.execute(archive);
            
            // Epsilon: Convergence
            EpsilonCalculator eps=new EpsilonCalculator(problem.getNumberOfObjectives(), pf);
            double epsilonValue=eps.execute(archive);
            
            // GSPREAD/SPREAD: Diversity
            SpreadCalculator spread = new SpreadCalculator(problem.getNumberOfObjectives(), pf);
            double spreadValue = spread.execute(archive);
            
            // RNI: Capacity
            //RniCalculator rni = new RniCalculator(problem.getNumberOfObjectives(), populationsize,
              //      pf);
            //double rniValue = rni.execute(archive);
            //IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
            //double igdValue = igd.execute(archive);
            
            //GdCalculator gd =new GdCalculator(problem.getNumberOfObjectives(), pf);
            //double gdValue = gd.execute(archive);
                       
            
            strToReturn="Hyper: " + hypValue + " ## " + "IGD: " + igdValue + " ## " + "Epsilon: " + epsilonValue + " ## " + "GSPREAD: " + spreadValue + " ## ";
            
        
        return strToReturn;
    }
    
    
    
    
    
    
 public List<Double> printResultsAsList(List archive, Problem problem, boolean recordStatEval) throws FileNotFoundException, IOException {
        
    	List<Double> qual = new ArrayList<Double>();
        StatEvalSupport stindicators = StatEvalSupport.getInstance();
        
        //if (problem.getName().contains("WFG") || problem.getName().contains("DTLZ") || problem.getName().contains("VC") || problem.getName().contains("MAC")) {
            String pf
                    = "pareto_fronts/" + problem.getName() + "." + problem.getNumberOfObjectives() + "D.pf";
            
                        
            // HV: Convergence-Diversity
            PopulationHandler popHnd = new PopulationHandler();
            HypervolumeCalculator hyp = new HypervolumeCalculator(problem.getNumberOfObjectives(), pf);
            //hyp.updatePointsUsingNadir(popHnd.getNadir(problem.getName(), problem.getNumberOfObjectives()));
            hyp.updatePointsUsingNadirIdeal(popHnd.getNadir(problem.getName(), problem.getNumberOfObjectives()),
            		popHnd.getIdeal(problem.getName(), problem.getNumberOfObjectives()));
            double hypValue = hyp.execute(archive);
            
            // IGD: Convergence-Diversity
            IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
            double igdValue = igd.execute(archive);
            
            // Epsilon: Convergence
            EpsilonCalculator eps=new EpsilonCalculator(problem.getNumberOfObjectives(), pf);
            double epsilonValue=eps.execute(archive);
            
            // GSPREAD/SPREAD: Diversity
            SpreadCalculator spread = new SpreadCalculator(problem.getNumberOfObjectives(), pf);
            double spreadValue = spread.execute(archive);
            
            // RNI: Capacity
            //RniCalculator rni = new RniCalculator(problem.getNumberOfObjectives(), populationsize,
            //        pf);
            //double rniValue = rni.execute(archive);
            //IgdCalculator igd = new IgdCalculator(problem.getNumberOfObjectives(), pf);
            //double igdValue = igd.execute(archive);
            
            //GdCalculator gd =new GdCalculator(problem.getNumberOfObjectives(), pf);
            //double gdValue = gd.execute(archive);
                        
            qual.add(hypValue);
            qual.add(igdValue);
            qual.add(epsilonValue);
            qual.add(spreadValue);
            //qual.add(rniValue);
            //double[] valQI = {2.0*hypValue, igdValue, epsilonValue/2.0, spreadValue, rniValue}; // Hypervolume and Epsilon: weight = 2; remaining: weight = 1
            //qual.add(new EuclideanDistance().d(valQI, ideal));
        
            if (recordStatEval) {
            	stindicators.recordDataStatEval("HYPERVOLUME", hypValue);
    			stindicators.recordDataStatEval("IGD", igdValue);
    			stindicators.recordDataStatEval("EPSILON", epsilonValue);
    			stindicators.recordDataStatEval("SPREAD", spreadValue);
    			//stindicators.recordDataStatEval("RNI", rniValue);
    			System.out.println("******* SAVE FILES - STATISTICAL EVALUATION ******");
            }
        
        return qual;
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
        List<S> mergedPopulation = new ArrayList<>(size);
        List<S> bestPopulation = new ArrayList<>(populationSize);

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
            List<S> obtainedSolutionSet = SolutionListUtils.getNondominatedSolutions(pop1);
            new SolutionListOutput(obtainedSolutionSet)
                    .setSeparator("\t")
                    .setFunFileOutputContext(new DefaultFileOutputContext(pop1Path))
                    .print();

            String pop2Path = folderPath + "/run_" + runIndex
                    + "/ErrorFile-Population2.txt";
            obtainedSolutionSet = SolutionListUtils.getNondominatedSolutions(pop2);
            new SolutionListOutput(obtainedSolutionSet)
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
    
    
    public BechmarkDILAnew(int runNo, int fixedGeneration, ProblemCreator problemCreator, int qtdAlgs) {
        algorithms = Arrays.copyOfRange(algorithms, 0, qtdAlgs);
        System.out.println(Arrays.toString(algorithms));
        this.problemCreator = problemCreator;
        this.fixedGeneration = fixedGeneration;
        folderPath = "HF_Config_Benchmark/Results/LA/DominanceInitalNew_" + fixedGeneration;
        String configFile = "HF_Config_Benchmark/ProblemSetting.txt";
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
        numberOfObj = Integer.parseInt(config.getProperty("ObjectiveNumber"));
        reference = new double[numberOfObj];
        for (int i = 0; i < numberOfObj; i++) {
            reference[i] = 1.0;
        }
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
}
