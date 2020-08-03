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

public class GUI9 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI9(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI9");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_inputlayercombobox", "m_useattrlayercheckbox", "m_attrlayercombobox", "m_functioncombobox", "m_estimationcombobox", "m_useadaptradiuscheckbox", "m_radiusspinbox", "m_gridradiobutton", "m_attrradiobutton", "m_ncolslineedit", "m_attrnamelineedit", "m_repositorylineedit", "m_targetfiletoolbutton", "m_newlayernamelineedit"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_inputlayercombobox");
        directedGraph.addVertex("m_useattrlayercheckbox");
        directedGraph.addVertex("m_attrlayercombobox");
        directedGraph.addVertex("m_functioncombobox");
        directedGraph.addVertex("m_estimationcombobox");
        directedGraph.addVertex("m_useadaptradiuscheckbox");
        directedGraph.addVertex("m_radiusspinbox");
        directedGraph.addVertex("m_gridradiobutton");
        directedGraph.addVertex("m_attrradiobutton");
        directedGraph.addVertex("m_ncolslineedit");
        directedGraph.addVertex("m_attrnamelineedit");
        directedGraph.addVertex("m_repositorylineedit");
        directedGraph.addVertex("m_targetfiletoolbutton");
        directedGraph.addVertex("m_newlayernamelineedit");
        directedGraph.addVertex("m_okpushbutton");
        directedGraph.addVertex("m_cancelpushbutton");
        // Create the Edges 
        directedGraph.addEdge("m_inputlayercombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_attrlayercombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_functioncombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_estimationcombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_radiusspinbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_attrradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_ncolslineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_attrnamelineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_okpushbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_attrlayercombobox");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_functioncombobox");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_estimationcombobox");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_radiusspinbox");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_gridradiobutton");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_attrradiobutton");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_ncolslineedit");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_attrnamelineedit");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_useattrlayercheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_attrlayercombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_attrlayercombobox", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_attrlayercombobox", "m_attrlayercombobox");
        directedGraph.addEdge("m_attrlayercombobox", "m_functioncombobox");
        directedGraph.addEdge("m_attrlayercombobox", "m_estimationcombobox");
        directedGraph.addEdge("m_attrlayercombobox", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_attrlayercombobox", "m_radiusspinbox");
        directedGraph.addEdge("m_attrlayercombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_attrlayercombobox", "m_attrradiobutton");
        directedGraph.addEdge("m_attrlayercombobox", "m_ncolslineedit");
        directedGraph.addEdge("m_attrlayercombobox", "m_attrnamelineedit");
        directedGraph.addEdge("m_attrlayercombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_attrlayercombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_attrlayercombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_attrlayercombobox", "m_okpushbutton");
        directedGraph.addEdge("m_attrlayercombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_functioncombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_functioncombobox", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_functioncombobox", "m_attrlayercombobox");
        directedGraph.addEdge("m_functioncombobox", "m_functioncombobox");
        directedGraph.addEdge("m_functioncombobox", "m_estimationcombobox");
        directedGraph.addEdge("m_functioncombobox", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_functioncombobox", "m_radiusspinbox");
        directedGraph.addEdge("m_functioncombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_functioncombobox", "m_attrradiobutton");
        directedGraph.addEdge("m_functioncombobox", "m_ncolslineedit");
        directedGraph.addEdge("m_functioncombobox", "m_attrnamelineedit");
        directedGraph.addEdge("m_functioncombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_functioncombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_functioncombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_functioncombobox", "m_okpushbutton");
        directedGraph.addEdge("m_functioncombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_estimationcombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_estimationcombobox", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_estimationcombobox", "m_attrlayercombobox");
        directedGraph.addEdge("m_estimationcombobox", "m_functioncombobox");
        directedGraph.addEdge("m_estimationcombobox", "m_estimationcombobox");
        directedGraph.addEdge("m_estimationcombobox", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_estimationcombobox", "m_radiusspinbox");
        directedGraph.addEdge("m_estimationcombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_estimationcombobox", "m_attrradiobutton");
        directedGraph.addEdge("m_estimationcombobox", "m_ncolslineedit");
        directedGraph.addEdge("m_estimationcombobox", "m_attrnamelineedit");
        directedGraph.addEdge("m_estimationcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_estimationcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_estimationcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_estimationcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_estimationcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_attrlayercombobox");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_functioncombobox");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_estimationcombobox");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_radiusspinbox");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_gridradiobutton");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_attrradiobutton");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_ncolslineedit");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_attrnamelineedit");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_useadaptradiuscheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_radiusspinbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_radiusspinbox", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_radiusspinbox", "m_attrlayercombobox");
        directedGraph.addEdge("m_radiusspinbox", "m_functioncombobox");
        directedGraph.addEdge("m_radiusspinbox", "m_estimationcombobox");
        directedGraph.addEdge("m_radiusspinbox", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_radiusspinbox", "m_radiusspinbox");
        directedGraph.addEdge("m_radiusspinbox", "m_gridradiobutton");
        directedGraph.addEdge("m_radiusspinbox", "m_attrradiobutton");
        directedGraph.addEdge("m_radiusspinbox", "m_ncolslineedit");
        directedGraph.addEdge("m_radiusspinbox", "m_attrnamelineedit");
        directedGraph.addEdge("m_radiusspinbox", "m_repositorylineedit");
        directedGraph.addEdge("m_radiusspinbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_radiusspinbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_radiusspinbox", "m_okpushbutton");
        directedGraph.addEdge("m_radiusspinbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_gridradiobutton", "m_attrlayercombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_functioncombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_estimationcombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_gridradiobutton", "m_radiusspinbox");
        directedGraph.addEdge("m_gridradiobutton", "m_gridradiobutton");
        directedGraph.addEdge("m_gridradiobutton", "m_attrradiobutton");
        directedGraph.addEdge("m_gridradiobutton", "m_ncolslineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_attrnamelineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_attrradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_attrradiobutton", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_attrradiobutton", "m_attrlayercombobox");
        directedGraph.addEdge("m_attrradiobutton", "m_functioncombobox");
        directedGraph.addEdge("m_attrradiobutton", "m_estimationcombobox");
        directedGraph.addEdge("m_attrradiobutton", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_attrradiobutton", "m_radiusspinbox");
        directedGraph.addEdge("m_attrradiobutton", "m_gridradiobutton");
        directedGraph.addEdge("m_attrradiobutton", "m_attrradiobutton");
        directedGraph.addEdge("m_attrradiobutton", "m_ncolslineedit");
        directedGraph.addEdge("m_attrradiobutton", "m_attrnamelineedit");
        directedGraph.addEdge("m_attrradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_attrradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_attrradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_attrradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_attrradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_ncolslineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_ncolslineedit", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_ncolslineedit", "m_attrlayercombobox");
        directedGraph.addEdge("m_ncolslineedit", "m_functioncombobox");
        directedGraph.addEdge("m_ncolslineedit", "m_estimationcombobox");
        directedGraph.addEdge("m_ncolslineedit", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_ncolslineedit", "m_radiusspinbox");
        directedGraph.addEdge("m_ncolslineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_ncolslineedit", "m_attrradiobutton");
        directedGraph.addEdge("m_ncolslineedit", "m_ncolslineedit");
        directedGraph.addEdge("m_ncolslineedit", "m_attrnamelineedit");
        directedGraph.addEdge("m_ncolslineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_ncolslineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_ncolslineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_ncolslineedit", "m_okpushbutton");
        directedGraph.addEdge("m_ncolslineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_attrnamelineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_attrnamelineedit", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_attrnamelineedit", "m_attrlayercombobox");
        directedGraph.addEdge("m_attrnamelineedit", "m_functioncombobox");
        directedGraph.addEdge("m_attrnamelineedit", "m_estimationcombobox");
        directedGraph.addEdge("m_attrnamelineedit", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_attrnamelineedit", "m_radiusspinbox");
        directedGraph.addEdge("m_attrnamelineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_attrnamelineedit", "m_attrradiobutton");
        directedGraph.addEdge("m_attrnamelineedit", "m_ncolslineedit");
        directedGraph.addEdge("m_attrnamelineedit", "m_attrnamelineedit");
        directedGraph.addEdge("m_attrnamelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_attrnamelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_attrnamelineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_attrnamelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_attrnamelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_attrlayercombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_functioncombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_estimationcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_radiusspinbox");
        directedGraph.addEdge("m_repositorylineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_attrradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_ncolslineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_attrnamelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_okpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_attrlayercombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_functioncombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_estimationcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_radiusspinbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_gridradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_attrradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_ncolslineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_attrnamelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_useattrlayercheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_attrlayercombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_functioncombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_estimationcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_useadaptradiuscheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_radiusspinbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_attrradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_ncolslineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_attrnamelineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_cancelpushbutton");

           
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
