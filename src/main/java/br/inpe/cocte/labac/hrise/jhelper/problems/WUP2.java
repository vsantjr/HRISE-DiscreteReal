package br.inpe.cocte.labac.hrise.jhelper.problems;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import br.inpe.cocte.labac.hrise.graph.HawickJamesSimpleCycles;
import br.inpe.cocte.labac.hrise.swtesting.SwTestingUtils;
import br.inpe.cocte.labac.hrise.util.SaveFiles;

public class WUP2 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
       
    /**
     * Constructor
     * @throws IOException 
     */
    public WUP2(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("WUP2");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        String[] initStates = {"selectChat"};
    	
    	String typeText = "typeText";
    	
    	String playVideo = "playVideo";
    	
    	String deleteTextOrEmoji = "deleteTextOrEmoji";
    	
    	//String sendMessage = "sendMessage";
    	
    	String[] manySelectText = {"selectEmoji", "typeTextEmoji", "typeEmojiText"};
    	
    	String typeTextAgain = "typeTextAgain";
    	
    	String[] selectMessage = {	"selectMessage0",
    								"selectMessage1",
    								"selectMessage2",
    								"selectMessage3", 
    								"selectMessage4",
    								"selectMessage5",
    								"selectMessage6",
    								"selectMessage7",
    								"selectMessage8",
    								"selectMessage9",
    								"selectMessage10",
    								"selectMessage11"}; 
    	
    	
    	
    	
    	
    	String unselectMessage = "unselectMessage";
    	
    	String[] tagMessage =  {"tagMsgAsFavourite", "untagMsgAsFavourite"};
    	
    	String[] copyStuff = {"copyMessage", "pasteMessage"}; 
    	
    	String replyMessage = "replyMessage";
    	
    	String forward = "forwardMessageSelectDest";
    	
    	String[] manySelectMsg = {"selectMessageData", "deleteOneAllConfirm", "deleteOneMyselfConfirm",
    		"deleteManyAllConfirm", "deleteManyMyselfConfirm", "deleteCancel"};
    	
    	String closeWU = "closeWU";
    	
       
    	
    	
    	// Instatiate the Graph
    	Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    	// Create the Vertices
    	for (int i = 0; i < initStates.length; i++) {
    		directedGraph.addVertex(initStates[i]);
    	}
    	
    	
    	directedGraph.addVertex(typeText);
    	directedGraph.addVertex(playVideo);
    	directedGraph.addVertex(deleteTextOrEmoji);
    	//directedGraph.addVertex(sendMessage);
    	directedGraph.addVertex(replyMessage);
    	directedGraph.addVertex(forward);
    	
    	
    	for (int i = 0; i < manySelectText.length; i++) {
    		directedGraph.addVertex(manySelectText[i]);
    	}
    	
    	directedGraph.addVertex(typeTextAgain);
    	
    	for (int i = 0; i < selectMessage.length; i++) {
    		directedGraph.addVertex(selectMessage[i]);
    	}
    	
    	
    	
    	directedGraph.addVertex(unselectMessage);
    	
    	for (int i = 0; i < tagMessage.length; i++) {
    		directedGraph.addVertex(tagMessage[i]);
    	}
    	
    	for (int i = 0; i < copyStuff.length; i++) {
    		directedGraph.addVertex(copyStuff[i]);
    	}
    	
    	for (int i = 0; i < manySelectMsg.length; i++) {
    		directedGraph.addVertex(manySelectMsg[i]);
        }
    	
    	directedGraph.addVertex(closeWU);
    	
    	
    	// Create the Edges: Init -> B; B -> Init; B -> C; C -> B; C -> D; D -> C; D -> E; C -> E; E -> C; E -> F
    
    	for (int i = 0; i < initStates.length; i++) {
          directedGraph.addEdge(initStates[i], typeText);
          directedGraph.addEdge(initStates[i], playVideo);
       	}
    		
    	//directedGraph.addEdge(typeText, sendMessage);
    	
    	//directedGraph.addEdge(sendMessage, sendMessage);
    	
    	
    	for (int i = 0; i < manySelectText.length; i++) {
    		directedGraph.addEdge(typeText, manySelectText[i]);
    		directedGraph.addEdge(manySelectText[i], typeTextAgain);
    		directedGraph.addEdge(typeTextAgain, manySelectText[i]);
    		switch(i) {
    			case 0:
    				directedGraph.addEdge(manySelectText[i], manySelectText[i+1]);
    				directedGraph.addEdge(manySelectText[i], manySelectText[i+2]);
    				break;
    			case 1:
    				directedGraph.addEdge(manySelectText[i], manySelectText[i-1]);
    				directedGraph.addEdge(manySelectText[i], manySelectText[i+1]);
    				break;
    			case 2:
    				directedGraph.addEdge(manySelectText[i], manySelectText[i-2]);
    				directedGraph.addEdge(manySelectText[i], manySelectText[i-1]);
    				break;	
    			default:
    				System.out.println("Error Between Vertices");
    		}
    		directedGraph.addEdge(unselectMessage, manySelectText[i]);	
    		
    	}
    	
    	directedGraph.addEdge(typeTextAgain, deleteTextOrEmoji);
    	directedGraph.addEdge(deleteTextOrEmoji, typeTextAgain);
    	
    	directedGraph.addEdge(deleteTextOrEmoji, selectMessage[0]);
    	
    	directedGraph.addEdge(typeTextAgain, playVideo);
    	
    	directedGraph.addEdge(playVideo, typeTextAgain);
    	
    	directedGraph.addEdge(typeTextAgain, selectMessage[1]);
    	
    	directedGraph.addEdge(selectMessage[2], unselectMessage);
    	
    	directedGraph.addEdge(unselectMessage, typeTextAgain);
    	
    	directedGraph.addEdge(selectMessage[3], tagMessage[0]);
    	directedGraph.addEdge(tagMessage[0], tagMessage[1]);
    	directedGraph.addEdge(tagMessage[1], selectMessage[4]);
    	
    	directedGraph.addEdge(selectMessage[5], copyStuff[0]);
    	directedGraph.addEdge(copyStuff[0], copyStuff[1]);
    	directedGraph.addEdge(copyStuff[1], selectMessage[6]);
    	
    	for (int i = 0; i < manySelectMsg.length; i++) {
    		directedGraph.addEdge(selectMessage[7], manySelectMsg[i]);
    		directedGraph.addEdge(manySelectMsg[i], selectMessage[8]);
    		directedGraph.addEdge(manySelectMsg[i], closeWU);
    	}
    	
    	directedGraph.addEdge(selectMessage[9], replyMessage);
    	directedGraph.addEdge(replyMessage, typeTextAgain);
    	//directedGraph.addEdge(typeTextAgain, sendMessage);
    	
    	directedGraph.addEdge(selectMessage[10], forward);
    	directedGraph.addEdge(forward, typeTextAgain);
    	directedGraph.addEdge(typeTextAgain, playVideo);
    	
    	directedGraph.addEdge(playVideo, selectMessage[11]);
    	
    	for (int i = 0; i < selectMessage.length; i++) {
    		for (int j = 0; j < manySelectMsg.length; j++) {
    		   directedGraph.addEdge(selectMessage[i], manySelectMsg[j]);
    		   directedGraph.addEdge(manySelectMsg[j], selectMessage[i]);
    		}   
    		
    	}
    	
    	directedGraph.addEdge(unselectMessage, closeWU);
    	
    	
       
        final int FACTOR_SC = 1000;
        int maximumSimpleCircuits = FACTOR_SC * getNumberOfVariables(); // Configuration More: 10000 possible simple circuits (test cases)
      
        Set<String> allVertices = directedGraph.vertexSet();
        Set<String> terminalVertices = new LinkedHashSet<String>();
        
       // ******************************************************************************
        //System.out.println("\nInit Vertices"); 
        //for(int ii = 0; ii < initStates.length; ii++) {
     	  // System.out.println(initStates[ii]);
     	   
        //}   
        
        //System.out.println("\nAll Vertices Before"); 
        //for(String dv: allVertices) {
     	  // System.out.println(dv);
     	   //if (!(Arrays.stream(initStates).anyMatch(dv::equals))) {
     		 //  terminalVertices.add(dv);
     	   //}
        //}
        
      //  terminalVertices.add(levelF);
        terminalVertices.add(closeWU);
        
        if (terminalVertices.isEmpty()) {
     	   directedGraph.addVertex("null_event");
     	   //allVertices.clear();
     	   allVertices = directedGraph.vertexSet();
     	   for (int i = 0; i < initStates.length; i++) {
     		  
     		   directedGraph.addEdge(initStates[i],"null_event");
     		   terminalVertices.add("null_event");
     		   //System.out.println("------- NULL NULL -------");
     	   
     	  }
        }
        
        //System.out.println("\nTerminal Vertices"); 
        //for(String dv: terminalVertices) {
     	  // System.out.println(dv);
     	  
        //}	
        
        
        //System.out.println("\nAll Vertices After"); 
        //for(String dv: allVertices) {
     	  // System.out.println(dv);
     	   //if (!(Arrays.stream(initStates).anyMatch(dv::equals))) {
     		 //  terminalVertices.add(dv);
     	   //}
       // }
        
               
        
        //System.out.println("");
        // making cycles with the terminal vertices
        for (String tver: terminalVertices) {
     	     	   
     	   for (int i = 0; i < initStates.length; i++) {
     		   directedGraph.addEdge(tver, initStates[i]);
     	   
     	   }
     	}
        
        
        // *****************************************************************************
        
        
        Set<DefaultEdge> allEdges = directedGraph.edgeSet();
        //Set<String> allEdgesSt = new LinkedHashSet<String>();
        for (DefaultEdge de: allEdges){
        	this.allEdgesSt.add(de.toString());
        }
        
        
        
        ComponentNameProvider<String> vertexIdProvider = new ComponentNameProvider<String>()
        {
            public String getName(String url)
            {
               // return url.getHost().replace('.', '_');
            	return url.toString();
            }
        };
        ComponentNameProvider<String> vertexLabelProvider = new ComponentNameProvider<String>()
        {
            public String getName(String url)
            {
                return url.toString();
            }
        };
        
        GraphExporter<String, DefaultEdge> exporter =
                new DOTExporter<>(vertexIdProvider, vertexLabelProvider, null);
            //Writer writer = new StringWriter();
        //OutputStream file = (OutputStream) Paths.get("outjg.txt");
        try {
				//exporter.exportGraph(directedGraph, new File("/Users/valdivino/Documents/Des/myscripts/outd.dot"));
				exporter.exportGraph(directedGraph, new File("graph/efg_" + getName() +".dot"));
				
	    } catch (ExportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
    
        
        /*
         * Get the simple circuits.
         * Configuration More, Constraint WIDE                
         */
        HawickJamesSimpleCycles<String, DefaultEdge> HawJam = new HawickJamesSimpleCycles<String, DefaultEdge>(directedGraph, terminalVertices, maximumSimpleCircuits,
        		"WIDE");
        this.simpleCircuits = HawJam.findSimpleCycles();
        
        // Choose simple circuits only with initial states
        for (Iterator<List<String>> iter = this.simpleCircuits.listIterator(); iter.hasNext();) {
        	String ini = SwTestingUtils.getLastElement(iter.next()); // last element of a simple circuit = initial vertex of a cycle
            if (!Arrays.asList(initStates).contains(ini))
                 iter.remove();
        }
               
          
       
              
        //System.out.println("\n\n----- REVERSED VERTICES -----  \n\n");
        for (List<String> scr: this.simpleCircuits){
        	//System.out.println(sc);
        	scr.add(0, scr.get(scr.size() - 1)); // add last vertex
        	Collections.reverse(scr);
        }
        
                
        String statGraph = "";
        
        for(List<String> logSC: this.simpleCircuits) {
     	   statGraph += logSC.toString() + "\n";
        }
         
         statGraph += "\n# Simple Circuits: " + this.simpleCircuits.size();
         statGraph += "\n-----------------------------------------------------------";
         statGraph += "\n\n\n# Vertices: " + directedGraph.vertexSet().size() + "   -   Id: " + directedGraph.vertexSet() + "\n";
         statGraph += "# Edges: " + allEdgesSt.size() + "\n";
        
         SaveFiles sFG = new SaveFiles();
         sFG.saveStatGraph("statgraph_" + getName(), statGraph);
       
         System.out.println("#### Number of Vertices: " + directedGraph.vertexSet().size() + " and " + "Edges: " + allEdgesSt.size()) ;
	   /*
	    * End - EFG Handling
	    */
        
        
        
        for (int i = 0; i < getNumberOfVariables(); i++) {
            //lowerLimit.add(0.0);
            //upperLimit.add((double) (this.simpleCircuits.size() - 1)); // It depends on the number of SC, Test Case
            
            lowerLimit.add(0);
            upperLimit.add((this.simpleCircuits.size() - 1)); // It depends on the number of SC, Test Case
            
        }
        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);
    }

    /**
     * Evaluate() method
     *
     * @param solution
     */
    @Override
    public void evaluate(IntegerSolution solution) {
    	double[] fx = new double[solution.getNumberOfObjectives()];
        List<Integer> varInt = new ArrayList<Integer>();
        
        for (int i = 0; i < getNumberOfVariables(); i++) {
            varInt.add(solution.getVariableValue(i));
        }

        try {
			fx[0] = SwTestingUtils.sizeTestSuite(this.simpleCircuits, varInt); // Test Suite Size (min)
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			fx[1] = SwTestingUtils.testCaseDiversity(this.simpleCircuits, varInt, 0.5); // Test Case Diversity - GW/Dice (min)
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			fx[2] = SwTestingUtils.testCaseDiversity(this.simpleCircuits, varInt, 2.0); // Test Case Diversity - SS/Anti-Dice (min)
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        fx[3] = 1.0 - (SwTestingUtils.edgeCoverage(this.allEdgesSt, this.simpleCircuits, varInt));  // Edge Coverage (max) as a min problem
        
        
        solution.setObjective(0, fx[0]);
        solution.setObjective(1, fx[1]);
        solution.setObjective(2, fx[2]);
        solution.setObjective(3, fx[3]);
    }
   
}
