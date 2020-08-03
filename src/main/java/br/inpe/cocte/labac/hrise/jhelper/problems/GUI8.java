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

public class GUI8 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI8(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI8");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_inputlayercombobox", "m_attributecombobox", "m_changeattrtoolbutton", "m_typecombobox", "m_nlagslineedit", "m_lagsincrementlineedit", "m_angledirlineedit", "m_angletollineedit", "m_modelcombobox", "m_nuggethorizontalslider", "m_sillhorizontalslider", "m_rangehorizontalslider"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_inputlayercombobox");
        directedGraph.addVertex("m_attributecombobox");
        directedGraph.addVertex("m_changeattrtoolbutton");
        directedGraph.addVertex("m_typecombobox");
        directedGraph.addVertex("m_nlagslineedit");
        directedGraph.addVertex("m_lagsincrementlineedit");
        directedGraph.addVertex("m_angledirlineedit");
        directedGraph.addVertex("m_angletollineedit");
        directedGraph.addVertex("m_modelcombobox");
        directedGraph.addVertex("m_nuggethorizontalslider");
        directedGraph.addVertex("m_sillhorizontalslider");
        directedGraph.addVertex("m_rangehorizontalslider");
        directedGraph.addVertex("m_applypushbutton");
        directedGraph.addVertex("m_closepushbutton");
        // Create the Edges 
        directedGraph.addEdge("m_inputlayercombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_attributecombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_typecombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_nlagslineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_angledirlineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_angletollineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_modelcombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_inputlayercombobox", "m_sillhorizontalslider");
        directedGraph.addEdge("m_inputlayercombobox", "m_rangehorizontalslider");
        directedGraph.addEdge("m_inputlayercombobox", "m_applypushbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_closepushbutton");
        directedGraph.addEdge("m_attributecombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_attributecombobox", "m_attributecombobox");
        directedGraph.addEdge("m_attributecombobox", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_attributecombobox", "m_typecombobox");
        directedGraph.addEdge("m_attributecombobox", "m_nlagslineedit");
        directedGraph.addEdge("m_attributecombobox", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_attributecombobox", "m_angledirlineedit");
        directedGraph.addEdge("m_attributecombobox", "m_angletollineedit");
        directedGraph.addEdge("m_attributecombobox", "m_modelcombobox");
        directedGraph.addEdge("m_attributecombobox", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_attributecombobox", "m_sillhorizontalslider");
        directedGraph.addEdge("m_attributecombobox", "m_rangehorizontalslider");
        directedGraph.addEdge("m_attributecombobox", "m_applypushbutton");
        directedGraph.addEdge("m_attributecombobox", "m_closepushbutton");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_attributecombobox");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_nlagslineedit");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_angledirlineedit");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_angletollineedit");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_modelcombobox");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_sillhorizontalslider");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_rangehorizontalslider");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_applypushbutton");
        directedGraph.addEdge("m_changeattrtoolbutton", "m_closepushbutton");
        directedGraph.addEdge("m_typecombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_typecombobox", "m_attributecombobox");
        directedGraph.addEdge("m_typecombobox", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_typecombobox");
        directedGraph.addEdge("m_typecombobox", "m_nlagslineedit");
        directedGraph.addEdge("m_typecombobox", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_typecombobox", "m_angledirlineedit");
        directedGraph.addEdge("m_typecombobox", "m_angletollineedit");
        directedGraph.addEdge("m_typecombobox", "m_modelcombobox");
        directedGraph.addEdge("m_typecombobox", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_typecombobox", "m_sillhorizontalslider");
        directedGraph.addEdge("m_typecombobox", "m_rangehorizontalslider");
        directedGraph.addEdge("m_typecombobox", "m_applypushbutton");
        directedGraph.addEdge("m_typecombobox", "m_closepushbutton");
        directedGraph.addEdge("m_nlagslineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_nlagslineedit", "m_attributecombobox");
        directedGraph.addEdge("m_nlagslineedit", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_nlagslineedit", "m_typecombobox");
        directedGraph.addEdge("m_nlagslineedit", "m_nlagslineedit");
        directedGraph.addEdge("m_nlagslineedit", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_nlagslineedit", "m_angledirlineedit");
        directedGraph.addEdge("m_nlagslineedit", "m_angletollineedit");
        directedGraph.addEdge("m_nlagslineedit", "m_modelcombobox");
        directedGraph.addEdge("m_nlagslineedit", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_nlagslineedit", "m_sillhorizontalslider");
        directedGraph.addEdge("m_nlagslineedit", "m_rangehorizontalslider");
        directedGraph.addEdge("m_nlagslineedit", "m_applypushbutton");
        directedGraph.addEdge("m_nlagslineedit", "m_closepushbutton");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_attributecombobox");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_typecombobox");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_nlagslineedit");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_angledirlineedit");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_angletollineedit");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_modelcombobox");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_sillhorizontalslider");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_rangehorizontalslider");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_applypushbutton");
        directedGraph.addEdge("m_lagsincrementlineedit", "m_closepushbutton");
        directedGraph.addEdge("m_angledirlineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_angledirlineedit", "m_attributecombobox");
        directedGraph.addEdge("m_angledirlineedit", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_angledirlineedit", "m_typecombobox");
        directedGraph.addEdge("m_angledirlineedit", "m_nlagslineedit");
        directedGraph.addEdge("m_angledirlineedit", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_angledirlineedit", "m_angledirlineedit");
        directedGraph.addEdge("m_angledirlineedit", "m_angletollineedit");
        directedGraph.addEdge("m_angledirlineedit", "m_modelcombobox");
        directedGraph.addEdge("m_angledirlineedit", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_angledirlineedit", "m_sillhorizontalslider");
        directedGraph.addEdge("m_angledirlineedit", "m_rangehorizontalslider");
        directedGraph.addEdge("m_angledirlineedit", "m_applypushbutton");
        directedGraph.addEdge("m_angledirlineedit", "m_closepushbutton");
        directedGraph.addEdge("m_angletollineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_angletollineedit", "m_attributecombobox");
        directedGraph.addEdge("m_angletollineedit", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_angletollineedit", "m_typecombobox");
        directedGraph.addEdge("m_angletollineedit", "m_nlagslineedit");
        directedGraph.addEdge("m_angletollineedit", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_angletollineedit", "m_angledirlineedit");
        directedGraph.addEdge("m_angletollineedit", "m_angletollineedit");
        directedGraph.addEdge("m_angletollineedit", "m_modelcombobox");
        directedGraph.addEdge("m_angletollineedit", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_angletollineedit", "m_sillhorizontalslider");
        directedGraph.addEdge("m_angletollineedit", "m_rangehorizontalslider");
        directedGraph.addEdge("m_angletollineedit", "m_applypushbutton");
        directedGraph.addEdge("m_angletollineedit", "m_closepushbutton");
        directedGraph.addEdge("m_modelcombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_modelcombobox", "m_attributecombobox");
        directedGraph.addEdge("m_modelcombobox", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_modelcombobox", "m_typecombobox");
        directedGraph.addEdge("m_modelcombobox", "m_nlagslineedit");
        directedGraph.addEdge("m_modelcombobox", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_modelcombobox", "m_angledirlineedit");
        directedGraph.addEdge("m_modelcombobox", "m_angletollineedit");
        directedGraph.addEdge("m_modelcombobox", "m_modelcombobox");
        directedGraph.addEdge("m_modelcombobox", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_modelcombobox", "m_sillhorizontalslider");
        directedGraph.addEdge("m_modelcombobox", "m_rangehorizontalslider");
        directedGraph.addEdge("m_modelcombobox", "m_applypushbutton");
        directedGraph.addEdge("m_modelcombobox", "m_closepushbutton");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_inputlayercombobox");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_attributecombobox");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_typecombobox");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_nlagslineedit");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_angledirlineedit");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_angletollineedit");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_modelcombobox");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_sillhorizontalslider");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_rangehorizontalslider");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_applypushbutton");
        directedGraph.addEdge("m_nuggethorizontalslider", "m_closepushbutton");
        directedGraph.addEdge("m_sillhorizontalslider", "m_inputlayercombobox");
        directedGraph.addEdge("m_sillhorizontalslider", "m_attributecombobox");
        directedGraph.addEdge("m_sillhorizontalslider", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_sillhorizontalslider", "m_typecombobox");
        directedGraph.addEdge("m_sillhorizontalslider", "m_nlagslineedit");
        directedGraph.addEdge("m_sillhorizontalslider", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_sillhorizontalslider", "m_angledirlineedit");
        directedGraph.addEdge("m_sillhorizontalslider", "m_angletollineedit");
        directedGraph.addEdge("m_sillhorizontalslider", "m_modelcombobox");
        directedGraph.addEdge("m_sillhorizontalslider", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_sillhorizontalslider", "m_sillhorizontalslider");
        directedGraph.addEdge("m_sillhorizontalslider", "m_rangehorizontalslider");
        directedGraph.addEdge("m_sillhorizontalslider", "m_applypushbutton");
        directedGraph.addEdge("m_sillhorizontalslider", "m_closepushbutton");
        directedGraph.addEdge("m_rangehorizontalslider", "m_inputlayercombobox");
        directedGraph.addEdge("m_rangehorizontalslider", "m_attributecombobox");
        directedGraph.addEdge("m_rangehorizontalslider", "m_changeattrtoolbutton");
        directedGraph.addEdge("m_rangehorizontalslider", "m_typecombobox");
        directedGraph.addEdge("m_rangehorizontalslider", "m_nlagslineedit");
        directedGraph.addEdge("m_rangehorizontalslider", "m_lagsincrementlineedit");
        directedGraph.addEdge("m_rangehorizontalslider", "m_angledirlineedit");
        directedGraph.addEdge("m_rangehorizontalslider", "m_angletollineedit");
        directedGraph.addEdge("m_rangehorizontalslider", "m_modelcombobox");
        directedGraph.addEdge("m_rangehorizontalslider", "m_nuggethorizontalslider");
        directedGraph.addEdge("m_rangehorizontalslider", "m_sillhorizontalslider");
        directedGraph.addEdge("m_rangehorizontalslider", "m_rangehorizontalslider");
        directedGraph.addEdge("m_rangehorizontalslider", "m_applypushbutton");
        directedGraph.addEdge("m_rangehorizontalslider", "m_closepushbutton");

   

           
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
