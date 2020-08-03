package br.usp.poli.pcs.lti.moabhh.agents;

import br.usp.poli.pcs.lti.jmetalhhhelper.core.TaggedSolution;
import cartago.AgentId;
import cartago.ArtifactId;
import cartago.CartagoException;
import cartago.Op;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uma.jmetal.solution.Solution;

/**
 * The type Hh agent.
 *
 * @param <S> the type parameter
 */
public abstract class HyperHeuristicAgent<S extends Solution<?>> extends SimplerAgent {

  /**
   * The Copeland artifact.
   */
  protected ArtifactId copelandArtifact;
  /**
   * The Population artifact.
   */
  protected ArtifactId populationArtifact;
  /**
   * The Population share artifact.
   */
  protected ArtifactId populationShareArtifact;
  /**
   * The Problem artifact.
   */
  protected ArtifactId problemArtifact;
  /**
   * The List mh agents.
   */
  protected ArrayList<AgentId> listMhAgents;
  /**
   * The Qtd solutions.
   */
  protected double[] qtdSolutions;
  
  protected int[] discretizedQtdSolutions;

  /**
   * Instantiates a new Hh agent.
   *
   * @param agentName the agent name
     * @param uId
   */
  public HyperHeuristicAgent(String agentName, long uId) {
    super(agentName, uId);
  }

  @Override
  public void init() {
    super.init();
    try {
      this.copelandArtifact = lookupArtifact("voting");
      this.populationArtifact = lookupArtifact("population");
      this.populationShareArtifact = lookupArtifact("populationshare");
      this.problemArtifact = lookupArtifact("problem");
      this.listMhAgents = this.getMhAgentIds();
      this.qtdSolutions = new double[this.listMhAgents.size()];
      this.discretizedQtdSolutions=new int[this.qtdSolutions.length];
    } catch (CartagoException ex) {
      Logger.getLogger(HyperHeuristicAgent.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

    /**
     * Shared population to population.
     *
     * @throws CartagoException the cartago exception
     */
    protected void sharedPopulationToPopulation() throws CartagoException {
        List<S> pop = (List<S>) this
                .getAttributeArtifact(populationShareArtifact, "getAllPopulationShare");
        int delta = (int) this.getAttributeArtifact(problemArtifact, "getDelta");
        int gen = (int) this.getAttributeArtifact(problemArtifact, "getIteration");
        if (gen < delta) {
            //System.err.println("Reset");
            for (S sl : pop) {
                if (sl instanceof TaggedSolution) {
                    ((TaggedSolution) sl).setAlgorithm(null);
                    ((TaggedSolution) sl).setAction(null);

                }
            }
        }
        //System.out.println("Shared to Population " + pop.size());
        doAction(populationArtifact, new Op("setPopulation", pop));
    }

  /**
   * Start voting.
   *
   * @throws CartagoException the cartago exception
   */
  protected void startVoting() throws CartagoException {
    doAction(problemArtifact, new Op("resetAlreadyVoted"));
    doAction(copelandArtifact, new Op("resetVotation"));
  }

  /**
   * Start mh.
   *
   * @throws CartagoException the cartago exception
   */
  protected void startMhAgent() throws CartagoException {
    doAction(problemArtifact, new Op("resetAlreadyExecuted"));
    doAction(problemArtifact, new Op("setAlgStep", 2));
  }

  /**
   * Population to shared population.
   *
   * @throws CartagoException the cartago exception
   */
  protected void populationToSharedPopulation() throws CartagoException {
    List<S> currentPopulation = (List<S>) this
        .getAttributeArtifact(this.populationArtifact, "getPopulation");
    int populationSize = (int) this.getAttributeArtifact(problemArtifact, "getQtdPopulation");
    Random rdn = new Random();
    ArrayList<List<S>> division = new ArrayList<>();
    for (int x = 0; x < discretizedQtdSolutions.length; x++) {
      int toAdd = 0;
      division.add(new ArrayList<>(populationSize));
      while (toAdd < discretizedQtdSolutions[x] && currentPopulation.size() > 0) {//MOABHH dobra
        int chosen = rdn.nextInt(currentPopulation.size());
        Solution s = currentPopulation.get(chosen);
        currentPopulation.remove(chosen);
        division.get(x).add((S) s);
        toAdd++;
      }
      Op op = new Op("setPopulationShare", this.listMhAgents.get(x), division.get(x));
      doAction(this.populationShareArtifact, op);
    }
  }
}