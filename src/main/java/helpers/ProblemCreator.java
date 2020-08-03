package helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.uma.jmetal.problem.Problem;
//import org.uma.jmetal.problem.multiobjective.wfg.*;
import org.uma.jmetal.solution.Solution;

import br.inpe.cocte.labac.hrise.jhelper.problems.CS1;
import br.inpe.cocte.labac.hrise.jhelper.problems.CS2;
import br.inpe.cocte.labac.hrise.jhelper.problems.CS3;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI1;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI10;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI11;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI12;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI13;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI14;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI15;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI16;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI17;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI18;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI19;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI2;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI20;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI21;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI22;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI23;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI24;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI25;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI26;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI27;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI28;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI29;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI3;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI30;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI4;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI5;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI6;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI7;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI8;
import br.inpe.cocte.labac.hrise.jhelper.problems.GUI9;
import br.inpe.cocte.labac.hrise.jhelper.problems.LOF1;
import br.inpe.cocte.labac.hrise.jhelper.problems.LOF2;
import br.inpe.cocte.labac.hrise.jhelper.problems.LOF3;
import br.inpe.cocte.labac.hrise.jhelper.problems.LOF4;
import br.inpe.cocte.labac.hrise.jhelper.problems.SP1;
import br.inpe.cocte.labac.hrise.jhelper.problems.SP2;
import br.inpe.cocte.labac.hrise.jhelper.problems.SP3;
import br.inpe.cocte.labac.hrise.jhelper.problems.VC1;
import br.inpe.cocte.labac.hrise.jhelper.problems.VC2;
import br.inpe.cocte.labac.hrise.jhelper.problems.VC3;
import br.inpe.cocte.labac.hrise.jhelper.problems.VC4;
import br.inpe.cocte.labac.hrise.jhelper.problems.WR1;
import br.inpe.cocte.labac.hrise.jhelper.problems.WR2;
import br.inpe.cocte.labac.hrise.jhelper.problems.WR3;
import br.inpe.cocte.labac.hrise.jhelper.problems.WR4;
import br.inpe.cocte.labac.hrise.jhelper.problems.WR5;
import br.inpe.cocte.labac.hrise.jhelper.problems.WR6;
import br.inpe.cocte.labac.hrise.jhelper.problems.WUP1;
import br.inpe.cocte.labac.hrise.jhelper.problems.WUP2;
import br.inpe.cocte.labac.hrise.jhelper.problems.WUP3;
import br.inpe.cocte.labac.hrise.jhelper.problems.YOU1;

//import br.inpe.cocte.labac.hhost.jhelper.problems.*;
//import realproblems.vehiclecrashworthiness.*;
//import uk.ac.nottingham.asap.realproblems.*;

import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ2;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ3;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ4;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ5;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ6;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ7;
import org.uma.jmetal.problem.multiobjective.wfg.WFG1;
import org.uma.jmetal.problem.multiobjective.wfg.WFG2;
import org.uma.jmetal.problem.multiobjective.wfg.WFG3;
import org.uma.jmetal.problem.multiobjective.wfg.WFG4;
import org.uma.jmetal.problem.multiobjective.wfg.WFG5;
import org.uma.jmetal.problem.multiobjective.wfg.WFG6;
import org.uma.jmetal.problem.multiobjective.wfg.WFG7;
import org.uma.jmetal.problem.multiobjective.wfg.WFG8;
import org.uma.jmetal.problem.multiobjective.wfg.WFG9;
/**
 *
 * @author vinicius
 */
public class ProblemCreator {

    protected String problemClass;
    protected final HashMap<String, Integer> params;
    //private String theProbRunner;

    public ProblemCreator(String problemClass) {
        this.problemClass = problemClass;
        params = new HashMap<>();
        //this.theProbRunner = pr;
    }

    public void addParam(String name, int value) {
        params.put(name, value);
    }
    
    public int getParam(String name) {
        return params.get(name);
    }
    
    //public String getProbRunner() {
    	//return this.theProbRunner;
    //}
    
    protected Problem getWFG(int problemIndex) {
        int m = params.get("m");
        int k = params.get("k");
        int l = params.get("l");
        switch (problemIndex) {
            case 0:
                return new WFG1(k, l, m);
            case 1:
                return new WFG2(k, l, m);
            case 2:
                return new WFG3(k, l, m);
            case 3:
                return new WFG4(k, l, m);
            case 4:
                return new WFG5(k, l, m);
            case 5:
                return new WFG6(k, l, m);
            case 6:
                return new WFG7(k, l, m);
            case 7:
                return new WFG8(k, l, m);
            case 8:
                return new WFG9(k, l, m);
            default:
                return null;
        }
    }
    
    
    protected Problem getDTLZ(int problemIndex) {
        int m = params.get("m");
        //int k = params.get("k");
        int l = params.get("l");
        switch (problemIndex) {
            case 0:
                return new DTLZ1(l, m);
            case 1:
                return new DTLZ2(l, m);
            case 2:
                return new DTLZ3(l, m);
            case 3:
                return new DTLZ4(l, m);
            case 4:
                return new DTLZ5(l, m);
            case 5:
                return new DTLZ6(l, m);
            case 6:
                return new DTLZ7(l, m);
            default:
                return null;
        }
    }
    

    protected Problem getVC(int problemIndex) {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new VC1(m);
		    case 1:
		        return new VC2(m);
		    case 2:
		        return new VC3(m);
		    case 3:
		        return new VC4(m);
		    default:
		        return null;
		}
    }
    
    protected Problem getWR(int problemIndex) {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new WR1(m);
		    case 1:
		        return new WR2(m);
		    case 2:
		        return new WR3(m);
		    case 3:
		        return new WR4(m);
		    case 4:
		        return new WR5(m);   
		    case 5:
		        return new WR6(m);    
		    default:
		        return null;
		}
    }
    
    protected Problem getCS(int problemIndex) {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new CS1(m);
		    case 1:
		        return new CS2(m);
		    case 2:
		        return new CS3(m);
		    default:
		        return null;
		}
    }
    
    protected Problem getSP(int problemIndex) {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new SP1(m);
		    case 1:
		        return new SP2(m);
		    case 2:
		        return new SP3(m);
		    default:
		        return null;
		}
    }
    
    
    protected Problem getGU1_5(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new GUI1(m);
		    case 1:
		        return new GUI2(m);
		    case 2:
		        return new GUI3(m);
		    case 3:
		        return new GUI4(m);
		    case 4:
		        return new GUI5(m);    
		    default:
		        return null;
		}
    }
    
    
    protected Problem getGU6_10(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new GUI6(m);
		    case 1:
		        return new GUI7(m);
		    case 2:
		        return new GUI8(m);
		    case 3:
		        return new GUI9(m);
		    case 4:
		        return new GUI10(m);    
		    default:
		        return null;
		}
    }
    
    
    
    protected Problem getGU11_15(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new GUI11(m);
		    case 1:
		        return new GUI12(m);
		    case 2:
		        return new GUI13(m);
		    case 3:
		        return new GUI14(m);
		    case 4:
		        return new GUI15(m);    
		    default:
		        return null;
		}
    }
    
    
    protected Problem getGU16_20(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new GUI16(m);
		    case 1:
		        return new GUI17(m);
		    case 2:
		        return new GUI18(m);
		    case 3:
		        return new GUI19(m);
		    case 4:
		        return new GUI20(m);    
		    default:
		        return null;
		}
    }
    
    
    protected Problem getGU21_24(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new GUI21(m);
		    case 1:
		        return new GUI22(m);
		    case 2:
		        return new GUI23(m);
		    case 3:
		        return new GUI24(m);
		    		           
		    default:
		        return null;
		}
    }
    
    
    protected Problem getGU26_30(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new GUI26(m);
		    case 1:
		        return new GUI27(m);
		    case 2:
		        return new GUI28(m);
		    case 3:
		        return new GUI29(m);
		    case 4:
		        return new GUI30(m);    
		    default:
		        return null;
		}
    }
    
    
    protected Problem getLOF1_4(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new LOF1(m);
		    case 1:
		        return new LOF2(m);
		    case 2:
		        return new LOF3(m);
		    case 3:
		        return new LOF4(m);
		       
		    default:
		        return null;
		}
    }
    
    
    /*
    protected Problem getRealWorld(int problemIndex) {
        switch (problemIndex) {
            case 0:
                return new HeatExchanger();
            case 1:
                return new DiskBrakeDesign();
            case 2:
                return new WeldedBeamDesign();
            case 3:
                return new HydroDynamics();
            case 4:
                return new OpticalFilter();
            case 5:
                return new VibratingPlatformDesign();
            case 6:
                return new AucMaximization();
            case 7:
                return new NeuralNetDoublePoleBalancing();
            default:
                return null;
        }
    }
*/
    
    protected Problem getWUP1_3(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new WUP1(m);
		    case 1:
		        return new WUP2(m);
		    case 2:
		        return new WUP3(m);
		   
		       
		    default:
		        return null;
		}
    }
    
    
    protected Problem getYOU1(int problemIndex) throws IOException {
        int m = params.get("m");
        //int l = params.get("l");
        switch (problemIndex) {
		    case 0:
		        return new YOU1(m);
		     
		    default:
		        return null;
		}
    }
    

    public Problem getProblemInstance(int problemIndex) throws IOException {
        switch (problemClass) {
            case "WFG":
                return getWFG(problemIndex);
            case "DTLZ":
                return getDTLZ(problemIndex);
            case "VC":
                return getVC(problemIndex);
            case "WR":
                return getWR(problemIndex);
            case "CS":
                return getCS(problemIndex);
            case "SP":
                return getSP(problemIndex);    
            case "GU1-5":
                return getGU1_5(problemIndex);  
            case "GU6-10":
                return getGU6_10(problemIndex);   
            case "GU11-15":
                return getGU11_15(problemIndex);    
            case "GU16-20":
                return getGU16_20(problemIndex);     
            case "GU21-24":
                return getGU21_24(problemIndex);   
            case "GU26-30":
                return getGU26_30(problemIndex);   
            case "LOF1-4":
                return getLOF1_4(problemIndex);
            case "WUP1-3":
                return getWUP1_3(problemIndex); 
            case "YOU1":
                return getYOU1(problemIndex);      
            default:
                return null;
        }
    }

    public int getProblemInstanceIndex(String opt) {
    	int index = -1;
    	switch(opt) {
        	case "d1":
        	case "w1":
        	case "vc1":
        	case "wr1":
        	case "cs1":
        	case "sp1":	
        	case "gu1":	
        	case "gu6":	
        	case "gu11":
        	case "gu16":
        	case "gu21":
        	case "gu26":	
        	case "lof1":
        	case "wup1":
        	case "you1":	
        		index = 0;
            break;
            
        	case "d2":
        	case "w2":
        	case "vc2":
        	case "wr2":
        	case "cs2":
        	case "sp2":	
        	case "gu2":	
        	case "gu7":	
        	case "gu12":
        	case "gu17":
        	case "gu22":
        	case "gu27":
        	case "lof2":
        	case "wup2":	
        		index = 1;
            break;
            
        	case "d3":
        	case "w3":
        	case "vc3":
        	case "wr3":
        	case "cs3":
        	case "sp3":
        	case "gu3":	
        	case "gu8":	
        	case "gu13":	
        	case "gu18":
        	case "gu23":
        	case "gu28":
        	case "lof3":
        	case "wup3":	
        		index = 2;
            break;
            
        	case "d4":
        	case "w4":
        	case "vc4":
        	case "wr4":
        	case "gu4":	
        	case "gu9":	
        	case "gu14":	
        	case "gu19":
        	case "gu24":
        	case "gu29":
        	case "lof4":	
        	    index = 3;
            break;
            
        	case "d5":
        	case "w5":
        	case "wr5":
        	case "gu5":	
        	case "gu10":
        	case "gu15":
        	case "gu20":
        	case "gu25":
        	case "gu30":	
        	    index = 4;
            break;
            
        	case "d6":
        	case "w6":
        	case "wr6":
        	    index = 5;
            break;
            
        	case "d7":
        	case "w7":
        	    index = 6;
            break;
            
        	case "w8":
        	    index = 7;
            break;
            
        	case "w9":
        	    index = 8;
            break;
      	    
        default :
      	  System.out.println("%%%%% Invalid Problem");
       }
        return index;
    }
    
    
   public int getQtdProblem() {
      switch (problemClass) {
          case "WFG":
              return 9;
          case "DTLZ":
              return 7;
          case "VC":
              return 4;
          case "WR":
              return 6;  
          case "CS":
              return 3; 
          case "SP":
              return 3;  
          case "GU1-5":
          case "GU6-10":
          case "GU11-15":
          case "GU16-20":
          case "GU26-30":	  
              return 5;  
          case "GU21-24":
          case "LOF1-4":
        	  return 4;
          case "WUP1-3":
        	  return 3;
          case "YOU1":
        	  return 1; 	  
          default:
              return -1;
      }
   }
    
   // public int getQtdProblem() {
     //   return 1; // one at a time
   // }

    public static List generateInitialPopulation(Problem problem, int popSize) {
        List<Solution> initialPopulation = new ArrayList();
        Solution newSolution;
        for (int i = 0; i < popSize; i++) {
            newSolution = (Solution) problem.createSolution();
            problem.evaluate(newSolution);
            //problemInstances[instanceIndex].evaluateConstraints(newSolution);
            initialPopulation.add(newSolution);
        }
        return initialPopulation;
    }

    public String getProblemClass() {
        return problemClass;
    }

    public void setProblemClass(String problemClass) {
        this.problemClass = problemClass;
    }
}
