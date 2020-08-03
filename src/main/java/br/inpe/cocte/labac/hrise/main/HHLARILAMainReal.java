package br.inpe.cocte.labac.hrise.main;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import HF_LA.AnyProblemDILANew;
import HF_LA.AnyProblemOnlineLearning;
import HF_LA.BechmarkDILAnew;
import HF_LA.BenchmarkOnlineLearning;
import HF_LA.LALearning;
import HF_RandomGenerator.RandomGenerator;
import br.inpe.cocte.labac.hrise.jhelper.core.interfaces.LLHInterface;
import helpers.AlgorithmCreator;
import helpers.ProblemCreator;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import javax.management.JMException;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;


/**
 * This is the class which adapts the HH-RILA algorithm as described in [1]. This implementation is basically the very same as accomplished by the authors of the 
 * algorithm [1] with very few modifications to embedded it within the HRISE framework.
 *
 *[1] W. Li, E. Ozcan, R. John, A learning automata-based multiobjective hyper-heuristic, IEEE Transactions on Evolutionary Computation 23 (1)
 * (2019) 59 - 73. doi:10.1109/TEVC.2017.2785346.
 *
 * @author Wenwen Li (adapted by Vinicius Renan de Carvalho and Valdivino Alexandre de Santiago Júnior)
 */
public class HHLARILAMainReal<S extends Solution<?>> {

    public static void main(String[] args) throws ClassNotFoundException, IOException, JMException, ConfigurationException {
        
        int runMax;
        //int l = -1, k = -1, m = -1;
        int problemIndex = 0, algId = 0;
        //for (int problemIndex = 2; problemIndex > 0; problemIndex--) {
        int fixedGeneration = 20;
        long seed = System.currentTimeMillis();
        //Logger logger_ = Configuration.logger_;
        String[] algorithm = {"LAResutls", "DominanceInitalNew", "MetaH"};
        String problemClass = "";
        //String theProblemRunner = "";
        String problemString =""; // detailed description of the problem
        //problemClass = "WFG";
        //problemClass = "VC";
        int qtdAlgs = 3;
        //String configFile = "HF_Config_Benchmark/VCProblemSetting.txt";
        int runningAlgorithmIndex = 1;//0 is LA, 1 is RILA
        //runningAlgorithmIndex = 0;//0 is LA, 1 is RILA
        int run=0;
        String srcDir = ""; // Base Source Dir
        String destDir = ""; // Base Dest Dir
        
        if (args.length == 8) {
            
            runMax = Integer.parseInt(args[0]);
            fixedGeneration = Integer.parseInt(args[1]);
            seed = Integer.parseInt(args[2]);
            if (seed == -1) {
                seed = System.currentTimeMillis();
            }
            runningAlgorithmIndex = Integer.parseInt(args[3]);
            
            problemClass = args[4];
            qtdAlgs = Integer.parseInt(args[5]);
            srcDir = args[6];
            destDir = args[7];
            
            
        } else if (args.length == 3) {
            algId = Integer.parseInt(args[0]);
            problemIndex = Integer.parseInt(args[1]);
            problemClass = args[2];
            runMax = 0;
            System.out.println("Run IT Pal");
        } else {
        	runMax = 0;
        }
        System.out.println(problemClass + " qtdAlgs=" + qtdAlgs + " fixedGeneration=" + fixedGeneration + " with runningAlgorithmIndex=" + runningAlgorithmIndex + " run for " + runMax+" from "+run);
        
        /*
         * Handling dirs
         */
        FileUtils.deleteDirectory(new File("result"));
        FileUtils.deleteDirectory(new File("exec"));
        FileUtils.deleteDirectory(new File("pareto_fronts_known"));
        //int l = 20;
        //int m = 3;
        //k = 2 * (m - 1);
        ProblemCreator problemCreator = new ProblemCreator(problemClass);
        //problemCreator.addParam("m", m);
        //problemCreator.addParam("l", l);
        //problemCreator.addParam("k", k);

        //System.out.println("l: " + problemCreator.getParam("l") + " - m:" + problemCreator.getParam("m")  + " - k: " + problemCreator.getParam("k"));
        //String folderPath = "HF_Config_Benchmark/Results/" + problemCreator.getProblemClass() + "/AM";

        RandomGenerator RG = new RandomGenerator(seed);
        String runningAlgorithm = algorithm[runningAlgorithmIndex];
        String confileFileName = null;
        if (runningAlgorithm.equals("LAResutls")) {
            confileFileName = "HHLA_" + problemCreator.getProblemClass() + "ProblemSetting.txt";
        } else if (runningAlgorithm.equals("DominanceInitalNew")) {
            confileFileName = "HHRILA_" + problemCreator.getProblemClass() + "ProblemSetting.txt";
        } else {
            confileFileName = "HHLA_" + problemCreator.getProblemClass() + "ProblemSetting.txt";
        }
        String configFile = "HF_Config_Benchmark/" + confileFileName;
        /*	    try {
	    	
	    	 fileHandler_ = new FileHandler(folderPath+(loggerTime)+".log");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    logger_.addHandler(fileHandler_);*/

        HashMap<String, String> parameterSet = new HashMap();

        for (int runIndex = run; runIndex < runMax; runIndex++) { // ####### BEGIN OF TRIALS
            //logger_.info("Start running... Run " + runIndex);
            
        	/*
        	File runFolder = new File(folderPath + "/" + runningAlgorithm + "/run_" + runIndex);
            //File runFolder = new File(folderPath+"\\LAResutls\\run_"+runIndex);	
            if (!runFolder.exists()) {
                if (runFolder.mkdirs()) {
                    //System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                    System.exit(0);
                }
            }
            

            String outputPath = folderPath + "/" + runningAlgorithm;
            */
            
            switch (runningAlgorithmIndex) {
                case 0:
                    {
                        /*		    	parameterSet.put("LearningPhase", Double.toString(0.5));
                        //System.out.print(parameterSet.get("LearningPhase") + " ");
                        parameterSet.put("Multiplier", Double.toString(2.5));
                        //System.out.print(parameterSet.get("Multiplier") + " ");
                        parameterSet.put("MaximIterations", Integer.toString(20));
                        //System.out.print(parameterSet.get("MaximIterations") + " ");
                        parameterSet.put("HypvolumLimit", Double.toString(0.0075));
                        
                        VCOnlineLearning benchmarkLAlearning = new VCOnlineLearning(configFile, parameterSet, runIndex,  outputPath);
                        benchmarkLAlearning.runLA(parameterSet);*/
                        LALearning benchmarkLAlearning;
                        if (problemClass.equalsIgnoreCase("WFG")) {
                            System.out.println("Running Over WFG");
                            benchmarkLAlearning = new BenchmarkOnlineLearning(configFile, runIndex, fixedGeneration, problemCreator, qtdAlgs);
                        } else {
                            benchmarkLAlearning = new AnyProblemOnlineLearning(configFile, runIndex, fixedGeneration, problemCreator, qtdAlgs);
                        }       
                        benchmarkLAlearning.runLA();
                        break;
                    }
            //}
                case 1:
                    {
                        /*parameterSet.put("LearningPhase", Double.toString(0.9));
                        //System.out.print(parameterSet.get("LearningPhase") + " ");
                        parameterSet.put("Multiplier", Double.toString(2.5));
                        //System.out.print(parameterSet.get("Multiplier") + " ");
                        parameterSet.put("MaximIterations", Integer.toString(30));
                        //System.out.print(parameterSet.get("MaximIterations") + " ");
                        parameterSet.put("HypvolumLimit", Double.toString(0.0025));
                        //System.out.print(parameterSet.get("HypvolumLimit") + " ");
                        parameterSet.put("PercentageofTotalEvl.", Double.toString(0.2));
                        //System.out.print(parameterSet.get("PercentageofTotalEvl.") + " ");
                        parameterSet.put("SamplingFrequency", Integer.toString(2));
                        VCDILANew benchmarkLAlearning = new VCDILANew(configFile, parameterSet, runIndex,  outputPath);
                        benchmarkLAlearning.runLA(parameterSet);*/
                        LALearning benchmarkLAlearning;
                        if (problemClass.equalsIgnoreCase("WFG") || problemClass.equalsIgnoreCase("DTLZ")) {
                            benchmarkLAlearning = new BechmarkDILAnew(configFile, runIndex, fixedGeneration, problemCreator, qtdAlgs);
                            System.out.println("RILA Bench!!");
                        } else {
                            benchmarkLAlearning = new AnyProblemDILANew(configFile, runIndex, fixedGeneration, problemCreator, qtdAlgs, srcDir, destDir);
                            //benchmarkLAlearning = new AnyProblemDILANew(configFile, runIndex, fixedGeneration, problemCreator, qtdAlgs);
                            System.out.println("RILA REAL!!");
                        }       
                        benchmarkLAlearning.runLA();
                        break;
                    }
                case 2:
                    //for (problemIndex = 0; problemIndex < problemCreator.getQtdProblem(); problemIndex++) {
                    HHLARILAMainReal rx = new HHLARILAMainReal();
                    rx.runAlg(configFile, runIndex, fixedGeneration, problemIndex, algId, problemCreator);
                    //}
                    break;
                default:
                    break;
            }

            System.out.println("Finished...");
        } // ####### END OF TRIALS

    } // ####### END OF MAIN PROGRAM

    public void runAlg(String configfilePath, int runIndex, int fixedGeneration, int problemIndex, int algId, ProblemCreator problemCreator) throws IOException, ClassNotFoundException,
            JMException, ConfigurationException {

        Properties config = new Properties();
        String folderPath = "HF_Config_Benchmark/Results/" + problemCreator.getProblemClass() + "/PureAlgs";
        String configFile = configfilePath;
        try {
            config.load(new FileInputStream(configFile));
        } catch (IOException e) {
            System.err.println("Failed to load configuration file!");
        }
        int populationSize = Integer.parseInt(config.getProperty("PopulationSize"));
        int totalEval = Integer.parseInt(config.getProperty("MaxEvaluations"));
        int arcSize = Integer.parseInt(config.getProperty("ArchiveSize"));
        String SolutionType = config.getProperty("SolutionType");
        int popSize = Integer.parseInt(config.getProperty("PopulationSize"));
        Operator crossover = null; 	// Crossover operator
        Operator selection = null; 	// Selection operator
        HashMap parameters = null; // Operator parameters

        parameters = new HashMap();

        Double crossoverProb = Double.parseDouble(config.getProperty("CrossoverProb"));

        parameters.put("probability", crossoverProb);
        Double crossoverDistributionIndex = Double.parseDouble(config.getProperty("CrossoverDistributionIndex"));

        parameters.put("distributionIndex", crossoverDistributionIndex);
        String crossoverType = config.getProperty("CrossoverType");
        crossover = null;

        //GDE3 operators
        parameters = new HashMap();
        parameters.put("probability", crossoverProb);
        parameters.put("CR", 0.2);
        parameters.put("F", 0.2);
        parameters.put("K", 0.5);
        parameters.put("DE_VARIANT", "rand/1/bin");
        Operator crossoverGDE3 = null;
        parameters = null;
        Operator selectionGDE3 = null;
        //GDE3 operators

        // Selection Operator
        parameters = null;
        String selectionType = config.getProperty("SelectionType");

        Problem problemInstance;
        int numberOfVar;
        double mutationProb;
        Operator mutations;

        problemInstance = problemCreator.getProblemInstance(problemIndex);
        numberOfVar = problemInstance.getNumberOfVariables();
        mutationProb = (Double) 1.0 / numberOfVar;
        parameters = new HashMap();
        parameters.put("probability", mutationProb);
        Double mutationDistributionIndex = Double.parseDouble(config.getProperty("MutationDistributionIndex"));
        parameters.put("distributionIndex", mutationDistributionIndex);
        String mutationType = config.getProperty("MutationType");

        LLHInterface eachAlgorithm;
        AlgorithmCreator ac = new AlgorithmCreator(problemInstance);
        eachAlgorithm = ac.create(algId, popSize * totalEval);

        //System.out.println(eachAlgorithm.getClass().getName()+" created and ready to run");
        System.out.println("Running " + eachAlgorithm.getClass().getSimpleName() + " " + problemInstance.getName() + " " + runIndex);
        eachAlgorithm.setMaxIterations(1);
        List<S> result = eachAlgorithm.execute();

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
        List<S> obtainedSolutionSet = SolutionListUtils.getNondominatedSolutions(result);
        new SolutionListOutput(obtainedSolutionSet)
                .setSeparator("\t")
                .setFunFileOutputContext(new DefaultFileOutputContext(folderPath + "/run_" + runIndex + "/" + "ALG" + (eachAlgorithm.getClass().getSimpleName()) + "_Run" + problemInstance.getName() + ".txt"))
                .print();

    }

}
