package br.inpe.cocte.labac.hrise.jhelper.util;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.problem.multiobjective.UF.UF1;
import org.uma.jmetal.problem.multiobjective.UF.UF10;
import org.uma.jmetal.problem.multiobjective.UF.UF2;
import org.uma.jmetal.problem.multiobjective.UF.UF3;
import org.uma.jmetal.problem.multiobjective.UF.UF4;
import org.uma.jmetal.problem.multiobjective.UF.UF5;
import org.uma.jmetal.problem.multiobjective.UF.UF6;
import org.uma.jmetal.problem.multiobjective.UF.UF7;
import org.uma.jmetal.problem.multiobjective.UF.UF8;
import org.uma.jmetal.problem.multiobjective.UF.UF9;
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
import org.uma.jmetal.problem.multiobjective.zdt.ZDT1;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT2;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT3;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT4;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT5;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT6;
import br.inpe.cocte.labac.hrise.jhelper.problems.CD1;
import br.inpe.cocte.labac.hrise.jhelper.problems.CS1;
import br.inpe.cocte.labac.hrise.jhelper.problems.CS2;
import br.inpe.cocte.labac.hrise.jhelper.problems.CS3;
import br.inpe.cocte.labac.hrise.jhelper.problems.CS4;
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
import br.inpe.cocte.labac.hrise.jhelper.problems.MAC1;
import br.inpe.cocte.labac.hrise.jhelper.problems.MAC2;
import br.inpe.cocte.labac.hrise.jhelper.problems.MAC3;
import br.inpe.cocte.labac.hrise.jhelper.problems.MAC4;
import br.inpe.cocte.labac.hrise.jhelper.problems.MAC5;
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

/**
 * Problem factory.
 */
public class ProblemFactory {

    /**
     * Gets tsp problems.
     *
     * @param distanceFile the distance file
     * @param costFile the cost file
     * @return the tsp problems
     */
    public static Problem getTspProblems(String distanceFile, String costFile) {
        try {
            return new MultiobjectiveTSP(distanceFile, costFile);
        } catch (IOException ex) {
            Logger.getLogger(ProblemFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets problem.
     *
     * @param tipo the tipo
     * @param numberOfObjectives the number of objectives
     * @return the problem
     * @throws IOException 
     */
    public static Problem getProblem(String tipo, int numberOfObjectives) throws IOException {
        return getProblems(tipo, 0, 0, numberOfObjectives)[0];
    }

    /**
     * Gets problem.
     *
     * @param tipo the tipo
     * @param numberOfDistanceVariables the number of distance variables
     * @param numberOfObjectives the number of objectives
     * @return the problem
     * @throws IOException 
     */
    public static Problem getProblem(String tipo, int numberOfDistanceVariables,
            int numberOfObjectives) throws IOException {
        return getProblems(tipo, 0, numberOfDistanceVariables, numberOfObjectives)[0];
    }

    /**
     * Gets problem.
     *
     * @param tipo the tipo
     * @param numberOfPositions the number of positions
     * @param numberOfDistanceVariables the number of distance variables
     * @param numberOfObjectives the number of objectives
     * @return the problem
     * @throws IOException 
     */
    // 4 parameters. Ok.
    
    public static Problem getProblem(String tipo, int numberOfPositions,            
    		int numberOfDistanceVariables, int numberOfObjectives) throws IOException {
    	//Logger.getLogger(level.INFO,"4 par");
    	 //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4 param");
        return getProblems(tipo, numberOfPositions, numberOfDistanceVariables, numberOfObjectives)[0]; // Default 0. It gives the index of the problem in getProblems. But we could only change the name for what we want
    }

    /**
     * Get problems problem [ ].
     *
     * @param tipo the tipo
     * @param numberOfObjectives the number of objectives
     * @return the problem [ ]
     * @throws IOException 
     */
    public static Problem[] getProblems(String tipo, int numberOfObjectives) throws IOException {
        return getProblems(tipo, 0, 0, numberOfObjectives);
    }

    /**
     * Get problems problem [ ].
     *
     * @param tipo the tipo
     * @param numberOfDistanceVariables the number of distance variables
     * @param numberOfObjectives the number of objectives
     * @return the problem [ ]
     * @throws IOException 
     */
    public static Problem[] getProblems(String tipo, int numberOfDistanceVariables,
            int numberOfObjectives) throws IOException {
        return getProblems(tipo, 0, numberOfDistanceVariables, numberOfObjectives);
    }

    /**
     * Get continuous problems problem [ ].
     *
     * @param tipo the tipo
     * @param numberOfPositions the wfg number of positions
     * @param numberOfDistanceVariables the number of distance variables
     * @param numberOfObjectives the number of objectives
     * @return the problem [ ]
     * @throws IOException 
     */
    public static Problem[] getProblems(String tipo, int numberOfPositions,
            int numberOfDistanceVariables, int numberOfObjectives) throws IOException {
        int N = 2;//in contest
        double epsilon = 0.1;
        if (tipo.equalsIgnoreCase("WFG2")) {
            Problem[] problems = {
            		
                new WFG2(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG2");
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG3")) {
            Problem[] problems = {
                new WFG3(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG3");
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG4")) {
            Problem[] problems = {
                new WFG4(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG4");
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG5")) {
            Problem[] problems = {
                new WFG5(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG5");
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG6")) {
            Problem[] problems = {
                new WFG6(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG6");
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG7")) {
            Problem[] problems = {
                new WFG7(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG7");
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG8")) {
            Problem[] problems = {
                new WFG8(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
            //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG8");
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG9")) {
            Problem[] problems = {
                new WFG9(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG9");
            return problems;
        } else if (tipo.equalsIgnoreCase("WFG1")) {
            Problem[] problems = {
                new WFG1(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WFG1");
            return problems;    
        } else if (tipo.equalsIgnoreCase("DTLZ1")) {
            Problem[] problems = {
                new DTLZ1(numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: DTLZ1");
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ2")) {
            Problem[] problems = {
                new DTLZ2(numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: DTLZ2");
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ3")) {
            Problem[] problems = {
                new DTLZ3(numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: DTLZ3"); 
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ4")) {
            Problem[] problems = {
                new DTLZ4(numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: DTLZ4");
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ5")) {
            Problem[] problems = {
                new DTLZ5(numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: DTLZ5");
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ6")) {
            Problem[] problems = {
                new DTLZ6(numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: DTLZ6");
            return problems;
        } else if (tipo.equalsIgnoreCase("DTLZ7")) {
            Problem[] problems = {
                new DTLZ7(numberOfDistanceVariables, numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: DTLZ7");
            return problems;
        } else if (tipo.equalsIgnoreCase("VC1")) {
            Problem[] problems = {
                new VC1(numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: VC1");
            return problems;
            
        } else if (tipo.equalsIgnoreCase("VC2")) {
            Problem[] problems = {
                    new VC2(numberOfObjectives)};
                    //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: VC2");
                return problems;
                
        } else if (tipo.equalsIgnoreCase("VC3")) {
            Problem[] problems = {
                    new VC3(numberOfObjectives)};
                    //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: VC3");
                return problems;
                
        } else if (tipo.equalsIgnoreCase("VC4")) {
            Problem[] problems = {
                    new VC4(numberOfObjectives)};
                    //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: VC4");
                return problems;
                
       }  else if (tipo.equalsIgnoreCase("MAC1")) {
           Problem[] problems = {
                   new MAC1(numberOfObjectives)};
                   //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: MAC1");
               return problems;
               
      }  else if (tipo.equalsIgnoreCase("MAC2")) {
          Problem[] problems = {
                  new MAC2(numberOfObjectives)};
                  //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: MAC2");
              return problems;
              
     }  else if (tipo.equalsIgnoreCase("MAC3")) {
         Problem[] problems = {
                 new MAC3(numberOfObjectives)};
                 //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: MAC3");
             return problems;
             
    }  else if (tipo.equalsIgnoreCase("MAC4")) {
        Problem[] problems = {
                new MAC4(numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: MAC4");
            return problems;
            
   }  else if (tipo.equalsIgnoreCase("MAC5")) {
       Problem[] problems = {
               new MAC5(numberOfObjectives)};
               //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: MAC5");
           return problems;
           
  }  else if (tipo.equalsIgnoreCase("WR1")) {
        Problem[] problems = {
               new WR1(numberOfObjectives)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WR1");
              return problems;
        
        
   }  else if (tipo.equalsIgnoreCase("WR2")) {
       Problem[] problems = {
               new WR2(numberOfObjectives)};
               //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WR2");
    return problems;


   } else if (tipo.equalsIgnoreCase("WR3")) {
       Problem[] problems = {
               new WR3(numberOfObjectives)};
               //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WR3");
    return problems;

   } else if (tipo.equalsIgnoreCase("WR4")) {
       Problem[] problems = {
               new WR4(numberOfObjectives)};
               //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WR4");
    return problems;


   } else if (tipo.equalsIgnoreCase("WR5")) {
       Problem[] problems = {
               new WR5(numberOfObjectives)};
               //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WR5");
    return problems;


   } else if (tipo.equalsIgnoreCase("WR6")) {
      Problem[] problems = {
              new WR6(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WR6");
   return problems;

  } else if (tipo.equalsIgnoreCase("CD1")) {
      Problem[] problems = {
              new CD1(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: CD1");
   return problems; 
   
   
  } else if (tipo.equalsIgnoreCase("CS1")) {
      Problem[] problems = {
              new CS1(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: CS1");
   return problems; 
   
   
  }  else if (tipo.equalsIgnoreCase("CS2")) {
      Problem[] problems = {
              new CS2(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: CS2");
   return problems; 
   
   
  }  else if (tipo.equalsIgnoreCase("CS3")) {
      Problem[] problems = {
              new CS3(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: CS3");
   return problems; 
   
   
  }  else if (tipo.equalsIgnoreCase("CS4")) {
      Problem[] problems = {
              new CS4(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: CS4");
   return problems; 
   
   
  }  else if (tipo.equalsIgnoreCase("SP1")) {
      Problem[] problems = {
              new SP1(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: SP1");
      return problems; 
      
  }  else if (tipo.equalsIgnoreCase("SP2")) {
      Problem[] problems = {
              new SP2(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: SP2");
      return problems; 
    
  } else if (tipo.equalsIgnoreCase("SP3")) {
      Problem[] problems = {
              new SP3(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: SP3");
       return problems; 
    
  } else if (tipo.equalsIgnoreCase("GUI1")) {
      Problem[] problems = {
              new GUI1(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI1");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI2")) {
      Problem[] problems = {
              new GUI2(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI2");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI3")) {
      Problem[] problems = {
              new GUI3(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI3");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI4")) {
      Problem[] problems = {
              new GUI4(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI4");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI5")) {
      Problem[] problems = {
              new GUI5(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI5");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI6")) {
      Problem[] problems = {
              new GUI6(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI6");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI7")) {
      Problem[] problems = {
              new GUI7(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI7");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI8")) {
      Problem[] problems = {
              new GUI8(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI8");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI9")) {
      Problem[] problems = {
              new GUI9(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI9");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI10")) {
      Problem[] problems = {
              new GUI10(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI10");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI11")) {
      Problem[] problems = {
              new GUI11(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI11");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI12")) {
      Problem[] problems = {
              new GUI12(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI12");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI13")) {
      Problem[] problems = {
              new GUI13(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI13");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI14")) {
      Problem[] problems = {
              new GUI14(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI14");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI15")) {
      Problem[] problems = {
              new GUI15(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI15");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI16")) {
      Problem[] problems = {
              new GUI16(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI16");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI17")) {
      Problem[] problems = {
              new GUI17(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI17");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI18")) {
      Problem[] problems = {
              new GUI18(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI18");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI19")) {
      Problem[] problems = {
              new GUI19(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI19");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI20")) {
      Problem[] problems = {
              new GUI20(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI20");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI21")) {
      Problem[] problems = {
              new GUI21(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI21");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI22")) {
      Problem[] problems = {
              new GUI22(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI22");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI23")) {
      Problem[] problems = {
              new GUI23(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI23");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI24")) {
      Problem[] problems = {
              new GUI24(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI24");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI25")) {
      Problem[] problems = {
              new GUI25(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI25");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI26")) {
      Problem[] problems = {
              new GUI26(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI26");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI27")) {
      Problem[] problems = {
              new GUI27(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI27");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI28")) {
      Problem[] problems = {
              new GUI28(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI28");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI29")) {
      Problem[] problems = {
              new GUI29(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI29");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("GUI30")) {
      Problem[] problems = {
              new GUI30(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: GUI30");
       return problems; 
    
  }  else if (tipo.equalsIgnoreCase("LOF1")) {
      Problem[] problems = {
              new LOF1(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: LOF1");
       return problems; 
    
  } else if (tipo.equalsIgnoreCase("LOF2")) {
      Problem[] problems = {
              new LOF2(numberOfObjectives)};
             // Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: LOF2");
       return problems; 
    
  } else if (tipo.equalsIgnoreCase("LOF3")) {
      Problem[] problems = {
              new LOF3(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: LOF3");
       return problems; 
    
  } else if (tipo.equalsIgnoreCase("LOF4")) {
      Problem[] problems = {
              new LOF4(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: LOF4");
       return problems; 
    
  } else if (tipo.equalsIgnoreCase("WUP1")) {
      Problem[] problems = {
              new WUP1(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WUP1");
       return problems; 
    
  } else if (tipo.equalsIgnoreCase("WUP2")) {
      Problem[] problems = {
              new WUP2(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WUP2");
       return problems; 
    
  }  else if (tipo.equalsIgnoreCase("WUP3")) {
      Problem[] problems = {
              new WUP3(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: WUP3");
       return problems; 
    
  } else if (tipo.equalsIgnoreCase("YOU1")) {
      Problem[] problems = {
              new YOU1(numberOfObjectives)};
              //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: YOU1");
       return problems; 
    
  }   else if (tipo.equalsIgnoreCase("ZDT1")) {
                Problem[] problems = {
                    new ZDT1(numberOfDistanceVariables)};
                    //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: ZDT1");
                return problems;
                   
            
            
        } else if (tipo.equalsIgnoreCase("ZDT2")) {
            Problem[] problems = {
                new ZDT2(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: ZDT2");
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT3")) {
            Problem[] problems = {
                new ZDT3(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: ZDT3");
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT4")) {
            Problem[] problems = {
                new ZDT4(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: ZDT4");
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT5")) {
            Problem[] problems = {
                new ZDT5(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: ZDT5");
            return problems;
        } else if (tipo.equalsIgnoreCase("ZDT6")) {
            Problem[] problems = {
                new ZDT6(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: ZDT6");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF1")) {
            Problem[] problems = {
                new UF1(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF1");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF2")) {
            Problem[] problems = {
                new UF2(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF2");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF3")) {
            Problem[] problems = {
                new UF3(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF3");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF4")) {
            Problem[] problems = {
                new UF4(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF4");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF5")) {
            Problem[] problems = {
                new UF5(numberOfDistanceVariables, N, epsilon)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF5");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF6")) {
            Problem[] problems = {
                new UF6(numberOfDistanceVariables, N, epsilon)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF6");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF7")) {
            Problem[] problems = {
                new UF7(numberOfDistanceVariables)};
               // Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF7");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF8")) {
            Problem[] problems = {
                new UF8(numberOfDistanceVariables)};
               // Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF8");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF9")) {
            Problem[] problems = {
                new UF9(numberOfDistanceVariables, epsilon)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF9");
            return problems;
        } else if (tipo.equalsIgnoreCase("UF10")) {
            Problem[] problems = {
                new UF10(numberOfDistanceVariables)};
                //Logger.getLogger(ProblemFactory.class.getName()).log(Level.INFO, "4p: UF10");
            return problems;
        } 
        
        
        else {
            Problem[] problems = {
                new WFG1(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG2(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG3(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG4(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG5(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG6(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG7(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG8(numberOfPositions, numberOfDistanceVariables, numberOfObjectives),
                new WFG9(numberOfPositions, numberOfDistanceVariables, numberOfObjectives)};
               
                
            return problems;
        }
    }
}
