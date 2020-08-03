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

public class GUI14 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI14(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI14");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_layerradiobutton", "m_fileradiobutton", "m_folderradiobutton", "m_layercombobox", "m_filetoolbutton", "m_filelineedit", "m_foldertoolbutton", "m_folderlineedit", "m_interpolatorcombobox", "m_levelsspinbox"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_layerradiobutton");
        directedGraph.addVertex("m_fileradiobutton");
        directedGraph.addVertex("m_folderradiobutton");
        directedGraph.addVertex("m_layercombobox");
        directedGraph.addVertex("m_filetoolbutton");
        directedGraph.addVertex("m_filelineedit");
        directedGraph.addVertex("m_foldertoolbutton");
        directedGraph.addVertex("m_folderlineedit");
        directedGraph.addVertex("m_interpolatorcombobox");
        directedGraph.addVertex("m_levelsspinbox");
        // Create the Edges 
        directedGraph.addEdge("m_layerradiobutton", "m_layerradiobutton");
        directedGraph.addEdge("m_layerradiobutton", "m_fileradiobutton");
        directedGraph.addEdge("m_layerradiobutton", "m_folderradiobutton");
        directedGraph.addEdge("m_layerradiobutton", "m_layercombobox");
        directedGraph.addEdge("m_layerradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_layerradiobutton", "m_filelineedit");
        directedGraph.addEdge("m_layerradiobutton", "m_foldertoolbutton");
        directedGraph.addEdge("m_layerradiobutton", "m_folderlineedit");
        directedGraph.addEdge("m_layerradiobutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_layerradiobutton", "m_levelsspinbox");
        directedGraph.addEdge("m_fileradiobutton", "m_layerradiobutton");
        directedGraph.addEdge("m_fileradiobutton", "m_fileradiobutton");
        directedGraph.addEdge("m_fileradiobutton", "m_folderradiobutton");
        directedGraph.addEdge("m_fileradiobutton", "m_layercombobox");
        directedGraph.addEdge("m_fileradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_fileradiobutton", "m_filelineedit");
        directedGraph.addEdge("m_fileradiobutton", "m_foldertoolbutton");
        directedGraph.addEdge("m_fileradiobutton", "m_folderlineedit");
        directedGraph.addEdge("m_fileradiobutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_fileradiobutton", "m_levelsspinbox");
        directedGraph.addEdge("m_folderradiobutton", "m_layerradiobutton");
        directedGraph.addEdge("m_folderradiobutton", "m_fileradiobutton");
        directedGraph.addEdge("m_folderradiobutton", "m_folderradiobutton");
        directedGraph.addEdge("m_folderradiobutton", "m_layercombobox");
        directedGraph.addEdge("m_folderradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_folderradiobutton", "m_filelineedit");
        directedGraph.addEdge("m_folderradiobutton", "m_foldertoolbutton");
        directedGraph.addEdge("m_folderradiobutton", "m_folderlineedit");
        directedGraph.addEdge("m_folderradiobutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_folderradiobutton", "m_levelsspinbox");
        directedGraph.addEdge("m_layercombobox", "m_layerradiobutton");
        directedGraph.addEdge("m_layercombobox", "m_fileradiobutton");
        directedGraph.addEdge("m_layercombobox", "m_folderradiobutton");
        directedGraph.addEdge("m_layercombobox", "m_layercombobox");
        directedGraph.addEdge("m_layercombobox", "m_filetoolbutton");
        directedGraph.addEdge("m_layercombobox", "m_filelineedit");
        directedGraph.addEdge("m_layercombobox", "m_foldertoolbutton");
        directedGraph.addEdge("m_layercombobox", "m_folderlineedit");
        directedGraph.addEdge("m_layercombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_layercombobox", "m_levelsspinbox");
        directedGraph.addEdge("m_filetoolbutton", "m_layerradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_fileradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_folderradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_layercombobox");
        directedGraph.addEdge("m_filetoolbutton", "m_filetoolbutton");
        directedGraph.addEdge("m_filetoolbutton", "m_filelineedit");
        directedGraph.addEdge("m_filetoolbutton", "m_foldertoolbutton");
        directedGraph.addEdge("m_filetoolbutton", "m_folderlineedit");
        directedGraph.addEdge("m_filetoolbutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_filetoolbutton", "m_levelsspinbox");
        directedGraph.addEdge("m_filelineedit", "m_layerradiobutton");
        directedGraph.addEdge("m_filelineedit", "m_fileradiobutton");
        directedGraph.addEdge("m_filelineedit", "m_folderradiobutton");
        directedGraph.addEdge("m_filelineedit", "m_layercombobox");
        directedGraph.addEdge("m_filelineedit", "m_filetoolbutton");
        directedGraph.addEdge("m_filelineedit", "m_filelineedit");
        directedGraph.addEdge("m_filelineedit", "m_foldertoolbutton");
        directedGraph.addEdge("m_filelineedit", "m_folderlineedit");
        directedGraph.addEdge("m_filelineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_filelineedit", "m_levelsspinbox");
        directedGraph.addEdge("m_foldertoolbutton", "m_layerradiobutton");
        directedGraph.addEdge("m_foldertoolbutton", "m_fileradiobutton");
        directedGraph.addEdge("m_foldertoolbutton", "m_folderradiobutton");
        directedGraph.addEdge("m_foldertoolbutton", "m_layercombobox");
        directedGraph.addEdge("m_foldertoolbutton", "m_filetoolbutton");
        directedGraph.addEdge("m_foldertoolbutton", "m_filelineedit");
        directedGraph.addEdge("m_foldertoolbutton", "m_foldertoolbutton");
        directedGraph.addEdge("m_foldertoolbutton", "m_folderlineedit");
        directedGraph.addEdge("m_foldertoolbutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_foldertoolbutton", "m_levelsspinbox");
        directedGraph.addEdge("m_folderlineedit", "m_layerradiobutton");
        directedGraph.addEdge("m_folderlineedit", "m_fileradiobutton");
        directedGraph.addEdge("m_folderlineedit", "m_folderradiobutton");
        directedGraph.addEdge("m_folderlineedit", "m_layercombobox");
        directedGraph.addEdge("m_folderlineedit", "m_filetoolbutton");
        directedGraph.addEdge("m_folderlineedit", "m_filelineedit");
        directedGraph.addEdge("m_folderlineedit", "m_foldertoolbutton");
        directedGraph.addEdge("m_folderlineedit", "m_folderlineedit");
        directedGraph.addEdge("m_folderlineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_folderlineedit", "m_levelsspinbox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_layerradiobutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_fileradiobutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_folderradiobutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_layercombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_filetoolbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_filelineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_foldertoolbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_folderlineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_levelsspinbox");
        directedGraph.addEdge("m_levelsspinbox", "m_layerradiobutton");
        directedGraph.addEdge("m_levelsspinbox", "m_fileradiobutton");
        directedGraph.addEdge("m_levelsspinbox", "m_folderradiobutton");
        directedGraph.addEdge("m_levelsspinbox", "m_layercombobox");
        directedGraph.addEdge("m_levelsspinbox", "m_filetoolbutton");
        directedGraph.addEdge("m_levelsspinbox", "m_filelineedit");
        directedGraph.addEdge("m_levelsspinbox", "m_foldertoolbutton");
        directedGraph.addEdge("m_levelsspinbox", "m_folderlineedit");
        directedGraph.addEdge("m_levelsspinbox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_levelsspinbox", "m_levelsspinbox");

           
        final int FACTOR_SC = 1000;
        int maximumSimpleCircuits = FACTOR_SC * getNumberOfVariables(); // Configuration More: 10000 possible simple circuits (test cases)
      
        Set<String> allVertices = directedGraph.vertexSet();
        Set<String> terminalVertices = new LinkedHashSet<String>();
        
        //System.out.println("\nInit Vertices"); 
        //for(int ii = 0; ii < initStates.length; ii++) {
     	  // System.out.println(initStates[ii]);
     	   
        //}   
        
        //System.out.println("\nAll Vertices Before"); 
        for(String dv: allVertices) {
     	   //System.out.println(dv);
     	   if (!(Arrays.stream(initStates).anyMatch(dv::equals))) {
     		   terminalVertices.add(dv);
     	   }
        }
        
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
       // for(String dv: allVertices) {
     	 //  System.out.println(dv);
     	   //if (!(Arrays.stream(initStates).anyMatch(dv::equals))) {
     		 //  terminalVertices.add(dv);
     	   //}
        //}
        
        //System.out.println("");
        // making cycles with the terminal vertices
        for (String tver: terminalVertices) {
     	   
     	   
     	   for (int i = 0; i < initStates.length; i++) {
     		   directedGraph.addEdge(tver, initStates[i]);
     	   
     	  }
     	  // directedGraph.addEdge(tver, initStates[genIndexSolution(initStates.length)]);
     	   
        }
                        
        
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
