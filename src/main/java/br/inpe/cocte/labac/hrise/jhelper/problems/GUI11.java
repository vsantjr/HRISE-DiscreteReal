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

public class GUI11 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI11(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI11");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_clearradiobutton", "m_dstinradiobutton", "m_plusradiobutton", "m_colordodgeradiobutton", "m_srcradiobutton", "m_srcoutradiobutton", "m_multradiobutton", "m_colorburnradiobutton", "m_dstradiobutton", "m_dstoutradiobutton", "m_screenradiobutton", "m_hardlightradiobutton", "m_srcoverradiobutton", "m_srcattopradiobutton", "m_overlayradiobutton", "m_softlightradiobutton", "m_dstoverradiobutton", "m_dstattopradiobutton", "m_darkenradiobutton", "m_diffradiobutton", "m_srcinradiobutton", "m_xorradiobutton", "m_lightenradiobutton", "m_exclusionradiobutton"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_clearradiobutton");
        directedGraph.addVertex("m_dstinradiobutton");
        directedGraph.addVertex("m_plusradiobutton");
        directedGraph.addVertex("m_colordodgeradiobutton");
        directedGraph.addVertex("m_srcradiobutton");
        directedGraph.addVertex("m_srcoutradiobutton");
        directedGraph.addVertex("m_multradiobutton");
        directedGraph.addVertex("m_colorburnradiobutton");
        directedGraph.addVertex("m_dstradiobutton");
        directedGraph.addVertex("m_dstoutradiobutton");
        directedGraph.addVertex("m_screenradiobutton");
        directedGraph.addVertex("m_hardlightradiobutton");
        directedGraph.addVertex("m_srcoverradiobutton");
        directedGraph.addVertex("m_srcattopradiobutton");
        directedGraph.addVertex("m_overlayradiobutton");
        directedGraph.addVertex("m_softlightradiobutton");
        directedGraph.addVertex("m_dstoverradiobutton");
        directedGraph.addVertex("m_dstattopradiobutton");
        directedGraph.addVertex("m_darkenradiobutton");
        directedGraph.addVertex("m_diffradiobutton");
        directedGraph.addVertex("m_srcinradiobutton");
        directedGraph.addVertex("m_xorradiobutton");
        directedGraph.addVertex("m_lightenradiobutton");
        directedGraph.addVertex("m_exclusionradiobutton");
        directedGraph.addVertex("m_applypushbutton");
        directedGraph.addVertex("m_closepushbutton");
        // Create the Edges 
        directedGraph.addEdge("m_clearradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_clearradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_clearradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_dstinradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_plusradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_plusradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_plusradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_colordodgeradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_srcradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_srcradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_srcradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_srcoutradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_multradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_multradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_multradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_colorburnradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_dstradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_dstradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_dstradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_dstoutradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_screenradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_screenradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_screenradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_hardlightradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_srcoverradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_srcattopradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_overlayradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_softlightradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_dstoverradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_dstattopradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_darkenradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_diffradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_diffradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_diffradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_srcinradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_xorradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_xorradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_xorradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_lightenradiobutton", "m_closepushbutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_clearradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_dstinradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_plusradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_colordodgeradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_srcradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_srcoutradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_multradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_colorburnradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_dstradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_dstoutradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_screenradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_hardlightradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_srcoverradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_srcattopradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_overlayradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_softlightradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_dstoverradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_dstattopradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_darkenradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_diffradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_srcinradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_xorradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_lightenradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_exclusionradiobutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_applypushbutton");
        directedGraph.addEdge("m_exclusionradiobutton", "m_closepushbutton");




           
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
