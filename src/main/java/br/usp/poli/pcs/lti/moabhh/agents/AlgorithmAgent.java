package br.usp.poli.pcs.lti.moabhh.agents;

import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Gde3;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.DoubleTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.ParametersforAlgorithm;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.ParametersforHeuristics;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.PermutationTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.ArchivedMetaheuristic;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.interfaces.StandardMetaheuristic;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Nsgaii;
import br.usp.poli.pcs.lti.jmetalhhhelper.imp.algs.Spea2;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.AlgorithmBuilder;

import cartago.ArtifactId;
import cartago.CartagoException;
import cartago.Op;
import cartago.OpFeedbackParam;

import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.JMException;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;
import org.uma.jmetal.util.SolutionListUtils;

/**
 * The type Algorithm agent.
 *
 * @param <S> the type parameter
 */
@SuppressWarnings("serial")
public class AlgorithmAgent<S extends Solution<?>> extends SimplerAgent {

    protected StandardMetaheuristic alg;
    protected ArtifactId problemArtifactId;
    protected ArtifactId populationArtifactId;
    protected ArtifactId populationShareArtifactId;
    protected boolean hasPopulationAssigned;
    protected boolean increasedPopulation;
    /**
     * The Copeland artifact.
     */
    protected ArtifactId copelandArtifact;

    /**
     * Instantiates a new Algorithm agent.
     *
     * @param agentName the agent name
     * @param uId
     * @param algConfigFileName the alg config file name
     * @param parameterHeuristic the parameter heuristic
     */
    public AlgorithmAgent(String agentName, long uId, String algConfigFileName, String parameterHeuristic) {
        super(agentName, uId);
        this.selectAlg(algConfigFileName, parameterHeuristic);
    }

    protected void selectAlg(String algConfigFileName, String parameterHeuristic) {
        try {
            problemArtifactId = lookupArtifact("problem");
            populationArtifactId = lookupArtifact("population");
            Problem problem = (Problem) this.getAttributeArtifact(problemArtifactId, "getProblem_");
            int populationSize = (int) this.getAttributeArtifact(problemArtifactId, "getQtdPopulation");
            int maxIterations = (int) this.getAttributeArtifact(problemArtifactId, "getMaxIterations");
            ParametersforAlgorithm paramAlg = new ParametersforAlgorithm(algConfigFileName);
            paramAlg.setMaxIteractions(maxIterations);
            paramAlg.setPopulationSize(populationSize);
            paramAlg.setArchiveSize(populationSize);
            ParametersforHeuristics pHeur = new ParametersforHeuristics(parameterHeuristic, problem.getNumberOfVariables());
            //the same parameter as maashi
            //pHeur.setCrossoverDistribution(10);
            //pHeur.setMutationDistribution(20);
            //pHeur.setCrossoverProbality(0.9);
            //the same parameter as maashi
            AlgorithmBuilder hb = new AlgorithmBuilder(problem);
            alg = hb.create(paramAlg, pHeur);
            this.alg.initMetaheuristic();
            List<S> population = (List<S>) this
                    .getAttributeArtifact(populationArtifactId, "getPopulation");
            this.alg.setPopulation(population);
        } catch (CartagoException | JMException | ConfigurationException | FileNotFoundException ex) {
            Logger.getLogger(AlgorithmAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Execute generation.
     */
    public void executeGeneration() {
        this.alg.generateNewPopulation();
    }

    protected List<S> realClone(List<S> pop) {
        List<S> toReturn = new ArrayList<>();
        for (S sol : pop) {
            if (sol instanceof DoubleTaggedSolution) {
                DoubleTaggedSolution s;
                if (sol instanceof DoubleTaggedSolution) {
                    s = new DoubleTaggedSolution((DoubleTaggedSolution) sol);
                } else {
                    s = new DoubleTaggedSolution((DefaultDoubleSolution) sol);
                }
                toReturn.add((S) s);
            } else if (sol instanceof DefaultIntegerPermutationSolution) {
                PermutationTaggedSolution s = new PermutationTaggedSolution((DefaultIntegerPermutationSolution) sol);
                toReturn.add((S) s);
            }
        }
        return toReturn;
    }

    protected void runMOABHHPrepare(int iteration) {

        try {
            OpFeedbackParam<Object> res = new OpFeedbackParam<>();
            Op op = new Op("getPopulationShare", this.id, res);
            doAction(populationShareArtifactId, op);
            List<S> currentPopulation = (List<S>) res.get();
            //List<S> fullPop = SolutionListUtils.getNondominatedSolutions(currentPopulation);//VER

            res = new OpFeedbackParam<>();
            op = new Op("getQtdOfSolutionsForId", this.id, res);
            doAction(copelandArtifact, op);
            int size = (int) (res.get());

            //System.out.println(this.id+" "+size);
            this.hasPopulationAssigned = size > 0;
            if (this.hasPopulationAssigned && size % 2 != 0) {
                this.increasedPopulation = true;
                Random rdn = new SecureRandom();
                int chosen = rdn.nextInt(currentPopulation.size());
                currentPopulation.add((S) currentPopulation.get(chosen).copy());
                size++;
            } else {
                this.increasedPopulation = false;
            }

            if (this.alg instanceof ArchivedMetaheuristic) {
                ((ArchivedMetaheuristic) alg).setPopulationSize(size);
                ((ArchivedMetaheuristic) alg).setArchive(currentPopulation);
                ((ArchivedMetaheuristic) alg).setArchiveSize(size);
                ((ArchivedMetaheuristic) alg).setPopulation(new ArrayList<>());
                if (this.alg instanceof Spea2) {
                    ((Spea2) alg).setEnvironmentalSelectionSize(size);
                }
            } else if (this.alg instanceof Gde3) {
                ((Gde3) alg).setRealSize(size);//to generate n
                this.alg.setPopulation(currentPopulation);
                this.alg.setPopulationSize(size);
            } else {
                this.alg.setPopulationSize(size);
                this.alg.setPopulation(currentPopulation);
            }
            alg.setIterations(iteration);
            op = new Op("setBeginTime", this.id.getAgentName(), System.currentTimeMillis());
            doAction(problemArtifactId, op);
        } catch (CartagoException ex) {
            Logger.getLogger(AlgorithmAgent.class.getName()).log(Level.SEVERE, null, ex);
            this.hasPopulationAssigned = false;
        }

    }

    protected void runIMOABHHPrepare(int iteration) {
        OpFeedbackParam<Object> res = new OpFeedbackParam<>();
        Op op = new Op("getPopulation", res);
        try {
            doAction(populationArtifactId, op);
            List<S> completePopulation = realClone((List<S>) res.get());
            Collections.shuffle(completePopulation);
            List<S> fullPop = SolutionListUtils.getNondominatedSolutions(completePopulation);
            if (iteration == 1) {
                fullPop = completePopulation;
            }
            if (fullPop.size() < 4) {
                fullPop = completePopulation;
            }
            res = new OpFeedbackParam<>();
            op = new Op("getQtdOfSolutionsForId", this.id, res);
            doAction(copelandArtifact, op);
            int size = (int) ((double) res.get());

            this.hasPopulationAssigned = size > 0;
            if (this.hasPopulationAssigned && size % 2 != 0) {
                this.increasedPopulation = true;
                //Random rdn = new SecureRandom();
                //int chosen = rdn.nextInt(currentPopulation.size());
                //currentPopulation.add((S) currentPopulation.get(chosen).copy());
                size++;
            } else {
                this.increasedPopulation = false;
            }

            if (this.alg instanceof ArchivedMetaheuristic) {
                ((ArchivedMetaheuristic) alg).setPopulationSize(size);
                ((ArchivedMetaheuristic) alg).setPopulation(new ArrayList<>());
                ((ArchivedMetaheuristic) alg).setArchiveSize(size);
                ((ArchivedMetaheuristic) alg).setArchive(new ArrayList<>());
                if (this.alg instanceof Spea2) {
                    ((Spea2) alg).setEnvironmentalSelectionSize(size);
                }
                List<S> selected = new ArrayList<>();
                if (hasPopulationAssigned) {
                    selected = this.alg.updateMainPopulation(fullPop);
                    //System.out.println(this.id.getAgentName()+" "+size+" "+fullPop.size()+" "+selected.size());
                }
                ((ArchivedMetaheuristic) alg).setArchive(selected);
            } else if (this.alg instanceof Gde3) {
                ((Gde3) alg).setRealSize(size);//to generate n
                List<S> selected = this.alg.updateMainPopulation(completePopulation);
                this.alg.setPopulation(selected);
            } else {
                this.alg.setPopulation(new ArrayList<>());
                this.alg.setPopulationSize(size);
                List<S> selected = new ArrayList<>();
                if (hasPopulationAssigned) {
                    selected = this.alg.updateMainPopulation(fullPop);
                    //System.out.println(this.id.getAgentName()+" "+size+" "+fullPop.size()+" "+selected.size());
                }
                this.alg.setPopulation(selected);
            }
            alg.setIterations(iteration);
            op = new Op("setBeginTime", this.id.getAgentName(), System.currentTimeMillis());
            doAction(problemArtifactId, op);
        } catch (CartagoException ex) {
            Logger.getLogger(AlgorithmAgent.class.getName()).log(Level.SEVERE, null, ex);
            this.hasPopulationAssigned = false;
        }
        //System.out.println("IN "+this.getAgentName()+" "+iteration);
    }

    protected void prepareToExecute(int iteration) {
        //this.runIMOABHHPrepare(iteration);
        this.runMOABHHPrepare(iteration);
    }

    protected void prepareToGetOut() {
        try {
            int iterations = (int) this.getAttributeArtifact(problemArtifactId, "getIteration");
            int maxIterations = (int) this.getAttributeArtifact(problemArtifactId, "getMaxIterations");
            int epsilon = (int) this.getAttributeArtifact(problemArtifactId, "getEpsilon");
            List<S> pop = new ArrayList<>();
            if (iterations < maxIterations - epsilon) {
                pop = realClone(this.alg.getPopulation());
                if (alg instanceof ArchivedMetaheuristic) {
                    pop.addAll(realClone(((ArchivedMetaheuristic) alg).getArchive()));
                }
                else{
                    pop.addAll(realClone(alg.getPopulation()));
                }
                if (this.increasedPopulation) {
                    Random rdn = new SecureRandom();
                    pop.remove(rdn.nextInt(pop.size()));
                    pop.remove(rdn.nextInt(pop.size()));
                }
            } else {
                if (alg instanceof ArchivedMetaheuristic) {
                    pop.addAll(realClone(((ArchivedMetaheuristic) alg).getArchive()));
                } else if (alg instanceof Gde3) {
                    pop.addAll(realClone(this.alg.getPopulation()));
                } else {
                    pop = realClone(this.alg.getPopulation());
                }
            }
            Op op = new Op("setPopulationShare", this.id, pop);
            doAction(populationShareArtifactId, op);
            op = new Op("setEndTime", this.id.getAgentName(), System.currentTimeMillis());
            doAction(problemArtifactId, op);
        } catch (CartagoException ex) {
            Logger.getLogger(AlgorithmAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("OUt "+this.getAgentName());
    }

    @Override
    public void run() {
        int maxGen = (int) this.getAttributeArtifact(problemArtifactId, "getMaxIterations");
        int epsilon = (int) this.getAttributeArtifact(problemArtifactId, "getEpsilon");
        try {
            populationShareArtifactId = lookupArtifact("populationshare");
            this.copelandArtifact = lookupArtifact("voting");
            while (true) {
                int algStep = (int) this.getAttributeArtifact(problemArtifactId, "getAlgStep");
                OpFeedbackParam<Object> res = new OpFeedbackParam<>();

                doAction(problemArtifactId, new Op("isThisMetaHeuristicExecuted", this.id, res));
                Boolean alreadyExecuted = (Boolean) res.get();
                //System.out.println(this.getAgentName() + " " + alreadyExecuted + " " + algStep);
                if (!alreadyExecuted && algStep == 2) {
                    //System.out.println(this.getAgentName() + " takes poulation share");
                    int iterations = (int) this.getAttributeArtifact(problemArtifactId, "getIteration");
                    this.prepareToExecute(iterations);
                    if (this.hasPopulationAssigned) {
                        //System.out.println(this.getAgentName() + " executes");
                        for (int i = 0; i < epsilon && iterations < maxGen; i++) {
                            this.executeGeneration();
                        }
                        //System.out.println(this.getAgentName() + " set population share");
                        this.prepareToGetOut();
                        //System.out.println(this.getAgentName() + " sets already executed");
                        doAction(problemArtifactId, new Op("setAlreadyExecuted", this.id));
                    } else {
                        //System.out.println(this.getAgentName() + " has no population");
                        doAction(problemArtifactId, new Op("setAlreadyExecuted", this.id));
                    }
                }
            }

        } catch (CartagoException ex) {
            Logger.getLogger(AlgorithmAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
