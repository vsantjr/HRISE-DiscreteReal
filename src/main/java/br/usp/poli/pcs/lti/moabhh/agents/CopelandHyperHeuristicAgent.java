package br.usp.poli.pcs.lti.moabhh.agents;

import br.usp.poli.pcs.lti.moabhh.core.votingmethods.VotingMethod;
import br.usp.poli.pcs.lti.moabhh.core.votingmethods.VotingOutcomeProcessing;

import cartago.CartagoException;
import cartago.Op;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uma.jmetal.solution.Solution;

/**
 * The type Copeland hh agent.
 *
 * @param <S> the type parameter
 */
public class CopelandHyperHeuristicAgent<S extends Solution<?>> extends HyperHeuristicAgent {

   protected VotingMethod votingmethod;
  /**
   * Instantiates a new Copeland hh agent.
   *
   * @param agentName the agent name
     * @param uId
   */
  public CopelandHyperHeuristicAgent(String agentName, long uId) {
    super(agentName, uId);
  }

  @Override
  public void run() {
    this.init();
    votingmethod=(VotingMethod) this.getAttributeArtifact(copelandArtifact, "getVotingMethod");
    int populationSize = (int) this.getAttributeArtifact(problemArtifact, "getQtdPopulation");
    int maxGen = (int) this.getAttributeArtifact(problemArtifact, "getMaxIterations");
    int epsilon = (int) this.getAttributeArtifact(problemArtifact, "getEpsilon");
    int delta = (int) this.getAttributeArtifact(problemArtifact, "getDelta");
    double beta = (double) this.getAttributeArtifact(problemArtifact, "getBeta");
    try {
      while (true) {
        int gen = (int) this.getAttributeArtifact(problemArtifact, "getIteration");
        int algStep = (int) this.getAttributeArtifact(problemArtifact, "getAlgStep");
        //System.out.println(this.getAgentName()+" algstep "+algStep);
        if (gen < delta) {
          //System.out.println(algStep+" "+gen);
          switch (algStep) {
            case -1:
              //calculate each one participation
              double equalsPercent = 1.0 / ((double) listMhAgents.size());
              double qtd = Math.floor(equalsPercent * populationSize);
              Arrays.fill(qtdSolutions, qtd);
              try {
                    discretizedQtdSolutions = VotingOutcomeProcessing.discretizeArray(qtdSolutions, populationSize);
                    doAction(copelandArtifact, new Op("setQtdOfSolutions", discretizedQtdSolutions));
                    doAction(copelandArtifact, new Op("generateQtdSolutionsHash", this.listMhAgents, discretizedQtdSolutions));
                } catch (CartagoException ex) {
                Logger.getLogger(CopelandHyperHeuristicAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
              //System.out.println("\n\n" + Arrays.toString(qtdSolutions));
              //share population
              this.populationToSharedPopulation();
              //set alg to execute
              this.startMhAgent();
              break;
            case 2:
              boolean allfinished = (boolean) this
                  .getAttributeArtifact(problemArtifact, "isExecutionFinished");
              if (allfinished) {
                //System.out.println(this.getAgentName() + " sets all finished");
                doAction(problemArtifact, new Op("setAlgStep", 3));
              }
              break;
            case 3:
              //set population share to population
              this.sharedPopulationToPopulation();
              //update evaluation
              doAction(problemArtifact, new Op("setIteration", gen + epsilon));
              doAction(problemArtifact, new Op("setAlgStep", -1));
              break;
            default:
              break;

          }

        } else if (gen < maxGen) {
          //System.out.println(algStep+" "+gen+" Second stage");
          switch (algStep) {
            case -1:
              if (gen % epsilon == 0) {
                //time to voting
                //System.out.println(gen+" of "+maxGen+" "+(gen%epsilon));
                this.startVoting();
                doAction(problemArtifact, new Op("setAlgStep", 0));
              } else {
                doAction(problemArtifact, new Op("setAlgStep", 1));
              }
              break;
            case 0:
              boolean allfinishedVote = (boolean) this
                  .getAttributeArtifact(problemArtifact, "isVotationFinished");
              if (allfinishedVote) {
                //System.out.println(this.getAgentName() + " sets all votation finished");
                doAction(problemArtifact, new Op("setAlgStep", 1));
              }
              break;
            case 1://indicator voter will change from 1 setAlgStep to 1
              if (gen % epsilon == 0) {
                //apply percentual
                this.applyPercentual(beta, gen % epsilon == 0, populationSize);
                try {
                    discretizedQtdSolutions = VotingOutcomeProcessing.discretizeArray(qtdSolutions, populationSize);
                    doAction(copelandArtifact, new Op("setQtdOfSolutions", discretizedQtdSolutions));
                    doAction(copelandArtifact, new Op("generateQtdSolutionsHash", this.listMhAgents, discretizedQtdSolutions));
                } catch (CartagoException ex) {
                Logger.getLogger(CopelandHyperHeuristicAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
              }   //share population
              this.populationToSharedPopulation();
              this.startMhAgent();
              break;
            case 2:
              boolean allfinished = (boolean) this
                  .getAttributeArtifact(problemArtifact, "isExecutionFinished");
              if (allfinished) {
                //System.out.println(this.getAgentName() + " sets all finished");
                doAction(problemArtifact, new Op("setAlgStep", 3));
              }
              break;
            //case 2 just MHAgents can execute and they will change setAlgStep to 3
            case 3:
              //set population share to population
              this.sharedPopulationToPopulation();
              //update evaluation
              doAction(problemArtifact, new Op("setIteration", gen + epsilon));
              doAction(problemArtifact, new Op("setAlgStep", -1));
              break;
            default:
              break;
          }
        }

      }
    } catch (CartagoException ex) {
      Logger.getLogger(HyperHeuristicAgent.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

    protected void applyPercentual(double beta, boolean timeToUpdate, int populationSize) {
        int maxGen = (int) this.getAttributeArtifact(problemArtifact, "getMaxIterations");
        int gen = (int) this.getAttributeArtifact(problemArtifact, "getIteration");
        double linear = (1.0 - ((double) gen) / ((double) maxGen)) * beta;
        int[] votationResults = (int[]) this
                .getAttributeArtifact(copelandArtifact, "getVotationResults");
        if (timeToUpdate) {
            VotingOutcomeProcessing.votesProcessing(qtdSolutions, discretizedQtdSolutions, votationResults, beta, populationSize);
            String toprint = "";
            for (Object id : listMhAgents) {
                toprint += id.toString().split("_")[0] + " ";
            }
            //System.out.println(toprint + "\n" + Arrays.toString(qtdSolutions) + "\n");

        }

        try {
            int[] rank = new int[qtdSolutions.length];
            for (int i = 0; i < rank.length; i++) {
                rank[i] = (int) qtdSolutions[i];
            }
            doAction(problemArtifact, new Op("addVote", rank));
        } catch (CartagoException ex) {
            Logger.getLogger(CopelandHyperHeuristicAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
