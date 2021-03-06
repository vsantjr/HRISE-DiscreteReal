package br.usp.poli.pcs.lti.moabhh.agents;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.DoubleTaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.core.TaggedSolution;
import br.usp.poli.pcs.lti.jmetalhhhelper.problems.CarSideImpact;
import br.usp.poli.pcs.lti.jmetalhhhelper.problems.CrashWorthiness;
import br.usp.poli.pcs.lti.jmetalhhhelper.problems.Machining;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.IndicatorFactory;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.*;
import br.usp.poli.pcs.lti.jmetalhhhelper.util.metrics.Calculator;

import cartago.AgentId;
import cartago.ArtifactId;
import cartago.CartagoException;
import cartago.Op;
import cartago.OpFeedbackParam;
import com.google.common.primitives.Doubles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.stat.StatUtils;
import org.uma.jmetal.problem.DoubleProblem;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.problem.multiobjective.Water;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.archive.impl.NonDominatedSolutionListArchive;
import org.uma.jmetal.util.front.imp.ArrayFront;

/**
 * The type Indicator voter.
 *
 * @param <S> the type parameter
 */
public class IndicatorVoter<S extends Solution<?>> extends SimplerAgent {

    protected final String indicatorName;
    protected String[] arms;
    protected Calculator qualityIndicator;
    protected ArtifactId problemArtifactId;
    protected ArtifactId votingArtifactId;
    protected double[] nadirPoints;

    /**
     * Instantiates a new Indicator voter.
     *
     * @param indicatorName the indicator name
     * @param uId
     */
    public IndicatorVoter(String indicatorName, long uId) {
        super("iVoter_" + indicatorName, uId);
        this.indicatorName = indicatorName;
        this.qualityIndicator = null;
    }

    /**
     * Instantiates a new Indicator voter.
     *
     * @param indicatorName the indicator name
     * @param agentName
     * @param uId
     */
    public IndicatorVoter(String indicatorName, String agentName, long uId) {
        super(agentName, uId);
        this.indicatorName = indicatorName;
        this.qualityIndicator = null;
    }
    
    public Solution findNadir(DoubleProblem problem, List<S> pop) {
        pop.forEach((s) -> {
            for (int i = 0; i < s.getNumberOfObjectives(); i++) {
                nadirPoints[i] = Math.max(nadirPoints[i], s.getObjective(i));
            }
        });
        Solution s=new DoubleTaggedSolution(problem);
        for (int i = 0; i < s.getNumberOfObjectives(); i++) {
            s.setObjective(i, nadirPoints[i]);
        }
        return s;
    }

    @Override
    public void init() {
        super.init();
        try {
            problemArtifactId = lookupArtifact("problem");
            votingArtifactId = lookupArtifact("voting");
            Problem problem = (Problem) this.getAttributeArtifact(problemArtifactId, "getProblem_");
            nadirPoints=new double[problem.getNumberOfObjectives()];
            int populationSize = (int) this.getAttributeArtifact(problemArtifactId, "getQtdPopulation");
            this.qualityIndicator = IndicatorFactory
                    .buildCalculator(indicatorName, problem, populationSize);
            //this.qualityIndicator.setParetoTrueFront(new ArrayFront("pareto_fronts/simpleRef.pf"));//using the same reference as maashi
            //when use IGD GD R remove it
            ArrayList<AgentId> agids = this.getMhAgentIds();
            this.arms = new String[agids.size()];
            for (int i = 0; i < agids.size(); i++) {
                this.arms[i] = agids.get(i).getAgentName();
            }
        } catch (CartagoException | IOException ex) {
            Logger.getLogger(IndicatorVoter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        int epsilon = (int) this.getAttributeArtifact(problemArtifactId, "getEpsilon");
        Problem problem = (Problem) this.getAttributeArtifact(problemArtifactId, "getProblem_");
        while (true) {
            try {
                int algStep = (int) this.getAttributeArtifact(problemArtifactId, "getAlgStep");
                OpFeedbackParam<Object> res = new OpFeedbackParam<>();
                doAction(problemArtifactId, new Op("isThisIndicatorVoterExecuted", this.id, res));
                Boolean alreadyVoted = (Boolean) res.get();
                double[] qualityValues = new double[arms.length];
                if (algStep == 0 && !alreadyVoted) {
                    int[] qtds = (int[]) this.getAttributeArtifact(votingArtifactId, "getQtdOfSolutions");
                    double[] qtdSolutions=new double[qtds.length];
                    for (int i = 0; i < qtds.length; i++) {
                        qtdSolutions[i]=qtds[i];
                    }
                    int popSize = (int) this.getAttributeArtifact(problemArtifactId, "getQtdPopulation");
                    double beta = (double) this.getAttributeArtifact(problemArtifactId, "getBeta");
                    if (StatUtils.max(qtdSolutions) == popSize && StatUtils.sum(qtdSolutions) == popSize) {
                        int maxPos = Doubles.indexOf(qtdSolutions, Doubles.max(qtdSolutions));
                        qualityValues[maxPos] = 1;
                    } else {
                        //System.out.println(this.getAgentName()+" votes");
                        int gen = (int) this.getAttributeArtifact(problemArtifactId, "getIteration");
                        List<S> currentPopulation = this.getPopulationToEvaluate();
                        if (problem instanceof MultiobjectiveTSP || problem instanceof CrashWorthiness || problem instanceof CarSideImpact ||  problem instanceof Machining || problem instanceof Water) {
                            List ax=new ArrayList(currentPopulation);
                            if (this.qualityIndicator instanceof HypervolumeCalculator || qualityIndicator instanceof RCalculator) {
                                Solution nadir=findNadir((DoubleProblem) problem, currentPopulation);
                                ax.add((S) nadir);
                            }
                            //System.out.println(Arrays.toString(nadirPoints));
                            this.qualityIndicator.setParetoTrueFront(new ArrayFront(ax));
                        }
                        //System.out.println(this.getAgentName()+" gets population "+currentPopulation.size());
                        List<List<S>[]> allsharestype = this.separatePopulationByOp(currentPopulation);
                        List<S>[] shares;
                        if (this.qualityIndicator instanceof HypervolumeCalculator) {
                            shares = allsharestype.get(0);//no meio da execucao, se tiver poucas solucoes, vai zerar, usar 2 diminui o impacto
                        } 
                        else {
                            shares = allsharestype.get(2);
                        }
                        if (this.qualityIndicator instanceof AlgorithmEffort) {
                            for (int i = 0; i < qualityValues.length; i++) {
                                res = new OpFeedbackParam<>();
                                doAction(problemArtifactId, new Op("getBeginTime", arms[i], res));
                                long beginTime = (long) res.get();
                                res = new OpFeedbackParam<>();
                                doAction(problemArtifactId, new Op("getEndTime", arms[i], res));
                                long endTime = (long) res.get();
                                qualityValues[i]=((AlgorithmEffort) this.qualityIndicator).execute(endTime - beginTime, (int) (qtdSolutions[i] * epsilon));
                                if(qualityValues[i] == Double.MAX_VALUE)
                                    qualityValues[i] = 0;//now higher is better
                            }
                            double total= StatUtils.sum(qualityValues);
                            for (int i = 0; i < qualityValues.length; i++) {
                                qualityValues[i]=1.0 - qualityValues[i]/total;
                            }
                        } else if (this.qualityIndicator instanceof RniCalculator) {
                            for (int i = 0; i < qualityValues.length; i++) {
                                double quality = 0;
                                if (shares[i].size() > 0) {
                                    //int size = (int) qtdSolutions[i];
                                    ((RniCalculator) this.qualityIndicator).setPopulationMaxSize(popSize);
                                    quality = this.qualityIndicator.execute(shares[i]);
                                }
                                qualityValues[i] = quality;
                            }
                        } 
                        else if (this.qualityIndicator instanceof NRCalculator) {
                            for (int i = 0; i < qualityValues.length; i++) {
                                double quality = 0;
                                if (shares[i].size() > 0) {
                                    quality = ((NRCalculator) this.qualityIndicator).calculate(shares[i], SolutionListUtils.getNondominatedSolutions(currentPopulation));
                                }
                                qualityValues[i] = quality;
                            }
                        } 
                        else if (this.qualityIndicator instanceof HypervolumeRatioCalculator) {
                            for (int i = 0; i < qualityValues.length; i++) {
                                double quality = 0;
                                if (shares[i].size() > 0) {
                                    quality = ((HypervolumeRatioCalculator) this.qualityIndicator).calculate(new ArrayFront(shares[i]), new ArrayFront(currentPopulation));
                                }
                                qualityValues[i] = quality;
                            }
                        } 
                        else if (this.qualityIndicator instanceof EpsilonCalculator) {
                            for (int i = 0; i < qualityValues.length; i++) {
                                double quality = 1;
                                if (shares[i].size() > 0) {
                                    quality = this.qualityIndicator.execute(shares[i]);
                                }
                                qualityValues[i] = quality;
                            }
                            if (Doubles.max(qualityValues) < 0) {
                                for (int i = 0; i < qualityValues.length; i++) {
                                    qualityValues[i] = Math.abs(qualityValues[i]);
                                }
                            } 
                            else {
                                for (int i = 0; i < qualityValues.length; i++) {
                                    qualityValues[i] = 1 - qualityValues[i];
                                }
                            }
                        }
                        else {
                            if (this.qualityIndicator.isLowerValuesAreBetter()) {
                                for (int i = 0; i < qualityValues.length; i++) {
                                    double quality = 1;
                                    if (shares[i].size() > 0) {
                                        quality = this.qualityIndicator.execute(shares[i]);
                                    }
                                    qualityValues[i] = 1 - quality;
                                }
                            } else {
                                for (int i = 0; i < qualityValues.length; i++) {
                                    double quality = 0;
                                    if (shares[i].size() > 0) {
                                        quality = this.qualityIndicator.execute(shares[i]);
                                    }
                                    qualityValues[i] = quality;
                                }
                            }
                        }
                    }
                    res = new OpFeedbackParam<>();
                    //System.out.println(this.getAgentName() + " votes " + Arrays.toString(qualityValues));
                    doAction(votingArtifactId, new Op("vote", qualityValues, this.id, beta, res));
                    //System.out.println(this.getAgentName()+" set as voted ");
                    doAction(problemArtifactId, new Op("setAlreadyVoted", this.id));
                }
            } catch (CartagoException ex) {
                Logger.getLogger(IndicatorVoter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected List<S>[] splitPopulation(List<S> population) {
        List<S>[] separatedSolutionSet;
        separatedSolutionSet = new ArrayList[arms.length];
        for (int i = 0; i < arms.length; i++) {
            separatedSolutionSet[i] = new ArrayList();
        }
        for (int i = 0; i < population.size(); i++) {
            Solution solution = population.get(i);
            if (solution instanceof TaggedSolution) {
                TaggedSolution ts = (TaggedSolution) solution;
                if (ts.getAlgorithm() != null) {
                    boolean found = false;
                    for (int j = 0; !found && j < arms.length; j++) {
                        String act = arms[j].replace(String.valueOf(uId), "").replace("_", "");
                        if (ts.getAlgorithm().getName().equalsIgnoreCase(act)) {
                            separatedSolutionSet[j].add((S) ts);
                            found = true;
                        }
                    }
                }
            }
        }
        return separatedSolutionSet;
    }

    /**
     * Separate population by op list [ ].
     *
     * @param completePopulation
     * @return the list [ ]
     */
    protected List<List<S>[]> separatePopulationByOp(List<S> completePopulation) {
        Collections.shuffle(completePopulation);
        List<S> population = SolutionListUtils.getNondominatedSolutions(completePopulation);
        List<S>[] separatedSolutionSetJustNonDominated = this.splitPopulation(population);
        List<S>[] separatedSolutionSetAll = this.splitPopulation(completePopulation);
        List<S>[] separatedSolutionSetFull = this.splitPopulation(completePopulation);
        for (int i = 0; i < arms.length; i++) {
            NonDominatedSolutionListArchive ndominated = new NonDominatedSolutionListArchive();
            separatedSolutionSetAll[i].forEach((s) -> {
                ndominated.add(s);
            });
            separatedSolutionSetAll[i] = ndominated.getSolutionList();
        }
        List<List<S>[]> toReturn = new ArrayList<>();
        toReturn.add(separatedSolutionSetJustNonDominated);
        toReturn.add(separatedSolutionSetAll);
        toReturn.add(separatedSolutionSetFull);
        return toReturn;
    }

    protected List<S> getPopulationToEvaluate() throws CartagoException {
        ArtifactId populationArtifactId = lookupArtifact("population");
        List<S> currentPopulation = (List<S>) this
                .getAttributeArtifact(populationArtifactId, "getPopulation");
        return currentPopulation;
    }
}
