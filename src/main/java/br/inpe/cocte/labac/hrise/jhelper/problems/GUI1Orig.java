package br.inpe.cocte.labac.hrise.jhelper.problems;

import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.HawickJamesSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.DoubleSolution;
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

import br.inpe.cocte.labac.hrise.swtesting.SwTestingUtils;
import br.inpe.cocte.labac.hrise.util.SaveFiles;

public class GUI1Orig extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI1Orig(int m) throws IOException {
        setNumberOfVariables(15); // 25 Test Cases per Test Suite
        setNumberOfObjectives(m); // 4: Test Suite Size (min); Test Case Diversity - GW/Dice (min); Test Case Diversity - SS/Anti-Dice (min); Edge Coverage (max)
        setNumberOfConstraints(0);
        setName("GUI1");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("%%%%%[][][][][][][][][][][][][][][][][][][][] INSIDE PROBLEM INTEGER %%%%%%: " + getName());
        
        
        String[] initStates = {"circle","square", "reset", "create"};
    	
        Graph<String, DefaultEdge> directedGraph =
            new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        directedGraph.addVertex("circle");
        directedGraph.addVertex("square");
        directedGraph.addVertex("reset");
        directedGraph.addVertex("create");
        directedGraph.addVertex("exit");
        directedGraph.addVertex("uncheck");
        directedGraph.addVertex("yes");
        directedGraph.addVertex("no");
        //directedGraph.addVertex("i");
        directedGraph.addEdge("circle", "circle");
        directedGraph.addEdge("circle", "square");
        directedGraph.addEdge("circle", "reset");
        directedGraph.addEdge("circle", "create");
        directedGraph.addEdge("circle", "exit");
        directedGraph.addEdge("square", "square");
        directedGraph.addEdge("square", "circle");
        directedGraph.addEdge("square", "reset");
        directedGraph.addEdge("square", "create");
        directedGraph.addEdge("square", "exit");
        directedGraph.addEdge("reset", "reset");
        directedGraph.addEdge("reset", "circle");
        directedGraph.addEdge("reset", "square");
        directedGraph.addEdge("reset", "create");
        directedGraph.addEdge("reset", "exit");
        directedGraph.addEdge("create", "create");
        directedGraph.addEdge("create", "circle");
        directedGraph.addEdge("create", "square");
        directedGraph.addEdge("create", "reset");
        directedGraph.addEdge("create", "exit");
        directedGraph.addEdge("exit", "uncheck");
        directedGraph.addEdge("exit", "yes");
        directedGraph.addEdge("exit", "no");
        directedGraph.addEdge("uncheck", "uncheck");
        directedGraph.addEdge("uncheck", "yes");
        directedGraph.addEdge("uncheck", "no");
        directedGraph.addEdge("no", "circle");
        directedGraph.addEdge("no", "square");
        directedGraph.addEdge("no", "reset");
        directedGraph.addEdge("no", "create");
        directedGraph.addEdge("no", "exit");
        
       // final int POP_SIZE = 100;
        //final int SOLUTION_SIZE = 30;
        //System.out.println("All Edges String:  ");
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
    
        
        //System.out.println(writer.toString());
        
       // List<List<String>> simpleCircuits = new ArrayList<List<String>>();
        //Set<String> coveredEdges = new LinkedHashSet<String>();
        //Set<String> edgeNotFound = new LinkedHashSet<String>();
        
        HawickJamesSimpleCycles<String, DefaultEdge> HawJam = new HawickJamesSimpleCycles<String, DefaultEdge>(directedGraph);
        //HawJam.printSimpleCycles();
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
        
        //for (List<String> nscr:simpleCircuits){
        	//System.out.println(nscr);
        //}
        
        
        /*System.out.println("\n\n\nEnter: " + simpleCircuits.size());
        try{System.in.read();}
        catch(Exception e){}*/
                              
        //System.out.println("INDEX");
        
       // List<Integer> indexSolution = new ArrayList<Integer>(); // It is a solution (test suite), i.e. SOLUTION_SIZE (30) integer decision variables (test cases) 
       // List<Integer> popSizeTestSuite = new ArrayList<Integer>();
       // List<Double> popTestCaseDiversity = new ArrayList<Double>();
       // List<Double> popEdgeCoverage = new ArrayList<Double>();
       // int uniqueIndex = -1;
       // boolean searchingIndex = true;
        //List<List<String>> oneSolution = new ArrayList<List<String>>(); // A solution
        
        String statGraph = "";
        
        for(List<String> logSC: simpleCircuits) {
     	   statGraph += logSC.toString() + "\n";
        }
         
         statGraph += "\n# Simple Circuits: " + simpleCircuits.size();
         statGraph += "\n-----------------------------------------------------------";
         statGraph += "\n\n\n# Vertices: " + directedGraph.vertexSet().size() + "   -   Id: " + directedGraph.vertexSet() + "\n";
         statGraph += "# Edges: " + allEdgesSt.size() + "\n";
        
         SaveFiles sFG = new SaveFiles();
         sFG.saveStatGraph("statgraph_" + getName(), statGraph);
        
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
        //double[] var = new double[getNumberOfVariables()];
        
       // int[] var = new int[getNumberOfVariables()];
        
        List<Integer> varInt = new ArrayList<Integer>();
        
        for (int i = 0; i < getNumberOfVariables(); i++) {
            varInt.add(solution.getVariableValue(i));
        }

        // tem q transformar a solucao em uma lista de inteiros e passar. O resultado pode ser em double mesmo. Depois retirar
        //int[] varInt = new int[getNumberOfVariables()];
        
      
        
       // for (int j = 0; j < getNumberOfVariables(); j++) {
            //varInt.add((int) Math.round(var[j]));
         //   varInt.add(var[j]);
       // }
        
       
        
        //
        
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
        
        
        
          //double obj1 = 1640.2823 + 2.3573285 * var[0] + 2.3220035 * var[1] + 4.5688768 * var[2] + 7.7213633 * var[3] + 4.4559504 * var[4];
        //double obj2 = 6.5856 + 1.15 * var[0] - 1.0427 * var[1] + 0.9738 * var[2] + 0.8364 * var[3] - 0.3695 * var[0] * var[3] + 0.0861 * var[0] * var[4] + 0.3628 * var[1] * var[3] - 0.1106 * var[0] * var[0] - 0.3437 * var[2] * var[2] + 0.1764 * var[3] * var[3];
        //double obj3 = -0.0551 + 0.0181 * var[0] + 0.1024 * var[1] + 0.0421 * var[2] - 0.0073 * var[0] * var[1] + 0.024 * var[1] * var[2] - 0.0118 * var[1] * var[3] - 0.0204 * var[2] * var[3] - 0.008 * var[2] * var[4] - 0.0241 * var[1] * var[1] + 0.0109 * var[3] * var[3];

        solution.setObjective(0, fx[0]);
        solution.setObjective(1, fx[1]);
        solution.setObjective(2, fx[2]);
        solution.setObjective(3, fx[3]);
    }
}
