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

public class GUI1RefNew extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI1RefNew(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI1");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
     // Initial Vertices (States) gui1.
        String[] initStates = {"m_changebackgroundpushbutton", "m_showscalechckbox", "m_shownorthchackbox", "m_rendertine", "m_screenheightlineedit", "m_screenwidthlineedit", "m_restorescreensizepushbutton"};
        // Instantiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_changebackgroundpushbutton");
        directedGraph.addVertex("m_showscalechckbox");
        directedGraph.addVertex("m_shownorthchackbox");
        directedGraph.addVertex("m_rendertine");
        directedGraph.addVertex("m_screenheightlineedit");
        directedGraph.addVertex("m_screenwidthlineedit");
        directedGraph.addVertex("m_applyscreensizepushbutton");
        directedGraph.addVertex("m_restorescreensizepushbutton");
        // Create the Edges 
        directedGraph.addEdge("m_changebackgroundpushbutton", "m_changebackgroundpushbutton");
        directedGraph.addEdge("m_changebackgroundpushbutton", "m_showscalechckbox");
        directedGraph.addEdge("m_changebackgroundpushbutton", "m_shownorthchackbox");
        directedGraph.addEdge("m_changebackgroundpushbutton", "m_rendertine");
        directedGraph.addEdge("m_changebackgroundpushbutton", "m_screenheightlineedit");
        directedGraph.addEdge("m_changebackgroundpushbutton", "m_screenwidthlineedit");
        directedGraph.addEdge("m_changebackgroundpushbutton", "m_applyscreensizepushbutton");
        directedGraph.addEdge("m_changebackgroundpushbutton", "m_restorescreensizepushbutton");
        directedGraph.addEdge("m_showscalechckbox", "m_changebackgroundpushbutton");
        directedGraph.addEdge("m_showscalechckbox", "m_showscalechckbox");
        directedGraph.addEdge("m_showscalechckbox", "m_shownorthchackbox");
        directedGraph.addEdge("m_showscalechckbox", "m_rendertine");
        directedGraph.addEdge("m_showscalechckbox", "m_screenheightlineedit");
        directedGraph.addEdge("m_showscalechckbox", "m_screenwidthlineedit");
        directedGraph.addEdge("m_showscalechckbox", "m_applyscreensizepushbutton");
        directedGraph.addEdge("m_showscalechckbox", "m_restorescreensizepushbutton");
        directedGraph.addEdge("m_shownorthchackbox", "m_changebackgroundpushbutton");
        directedGraph.addEdge("m_shownorthchackbox", "m_showscalechckbox");
        directedGraph.addEdge("m_shownorthchackbox", "m_shownorthchackbox");
        directedGraph.addEdge("m_shownorthchackbox", "m_rendertine");
        directedGraph.addEdge("m_shownorthchackbox", "m_screenheightlineedit");
        directedGraph.addEdge("m_shownorthchackbox", "m_screenwidthlineedit");
        directedGraph.addEdge("m_shownorthchackbox", "m_applyscreensizepushbutton");
        directedGraph.addEdge("m_shownorthchackbox", "m_restorescreensizepushbutton");
        directedGraph.addEdge("m_rendertine", "m_changebackgroundpushbutton");
        directedGraph.addEdge("m_rendertine", "m_showscalechckbox");
        directedGraph.addEdge("m_rendertine", "m_shownorthchackbox");
        directedGraph.addEdge("m_rendertine", "m_rendertine");
        directedGraph.addEdge("m_rendertine", "m_screenheightlineedit");
        directedGraph.addEdge("m_rendertine", "m_screenwidthlineedit");
        directedGraph.addEdge("m_rendertine", "m_applyscreensizepushbutton");
        directedGraph.addEdge("m_rendertine", "m_restorescreensizepushbutton");
        directedGraph.addEdge("m_screenheightlineedit", "m_changebackgroundpushbutton");
        directedGraph.addEdge("m_screenheightlineedit", "m_showscalechckbox");
        directedGraph.addEdge("m_screenheightlineedit", "m_shownorthchackbox");
        directedGraph.addEdge("m_screenheightlineedit", "m_rendertine");
        directedGraph.addEdge("m_screenheightlineedit", "m_screenheightlineedit");
        directedGraph.addEdge("m_screenheightlineedit", "m_screenwidthlineedit");
        directedGraph.addEdge("m_screenheightlineedit", "m_applyscreensizepushbutton");
        directedGraph.addEdge("m_screenheightlineedit", "m_restorescreensizepushbutton");
        directedGraph.addEdge("m_screenwidthlineedit", "m_changebackgroundpushbutton");
        directedGraph.addEdge("m_screenwidthlineedit", "m_showscalechckbox");
        directedGraph.addEdge("m_screenwidthlineedit", "m_shownorthchackbox");
        directedGraph.addEdge("m_screenwidthlineedit", "m_rendertine");
        directedGraph.addEdge("m_screenwidthlineedit", "m_screenheightlineedit");
        directedGraph.addEdge("m_screenwidthlineedit", "m_screenwidthlineedit");
        directedGraph.addEdge("m_screenwidthlineedit", "m_applyscreensizepushbutton");
        directedGraph.addEdge("m_screenwidthlineedit", "m_restorescreensizepushbutton");
        directedGraph.addEdge("m_restorescreensizepushbutton", "m_changebackgroundpushbutton");
        directedGraph.addEdge("m_restorescreensizepushbutton", "m_showscalechckbox");
        directedGraph.addEdge("m_restorescreensizepushbutton", "m_shownorthchackbox");
        directedGraph.addEdge("m_restorescreensizepushbutton", "m_rendertine");
        directedGraph.addEdge("m_restorescreensizepushbutton", "m_screenheightlineedit");
        directedGraph.addEdge("m_restorescreensizepushbutton", "m_screenwidthlineedit");
        directedGraph.addEdge("m_restorescreensizepushbutton", "m_applyscreensizepushbutton");
        directedGraph.addEdge("m_restorescreensizepushbutton", "m_restorescreensizepushbutton");

           
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
