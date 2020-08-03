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

public class GUI10 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
        
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI10(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test case
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI10");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): \" + getName()");
        
     // Initial Vertices (States)
        String[] initStates = {"m_buildstratcontiguityradiobutton", "m_buildstratdistanceradiobutton", "m_distancelineedit", "m_buildstratnnradiobutton", "m_nearneighborlineedit", "m_calcdistcheckbox", "m_weightsquareinversedistradiobutton", "m_weightinversedistradiobutton", "m_weightnoweightradiobutton", "m_normalizeweightcheckbox", "m_filetoolbutton", "m_namelineedit", "m_galradiobutton", "m_gwtradiobutton", "m_dsradiobutton", "m_locationlineedit", "m_inputlayercombobox", "m_attridcombobox"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_buildstratcontiguityradiobutton");
        directedGraph.addVertex("m_buildstratdistanceradiobutton");
        directedGraph.addVertex("m_distancelineedit");
        directedGraph.addVertex("m_buildstratnnradiobutton");
        directedGraph.addVertex("m_nearneighborlineedit");
        directedGraph.addVertex("m_calcdistcheckbox");
        directedGraph.addVertex("m_weightsquareinversedistradiobutton");
        directedGraph.addVertex("m_weightinversedistradiobutton");
        directedGraph.addVertex("m_weightnoweightradiobutton");
        directedGraph.addVertex("m_normalizeweightcheckbox");
        directedGraph.addVertex("m_filetoolbutton");
        directedGraph.addVertex("m_namelineedit");
        directedGraph.addVertex("m_galradiobutton");
        directedGraph.addVertex("m_gwtradiobutton");
        directedGraph.addVertex("m_dsradiobutton");
        directedGraph.addVertex("m_locationlineedit");
        directedGraph.addVertex("m_okpushbutton");
        directedGraph.addVertex("m_cancelpushbutton");
        directedGraph.addVertex("m_inputlayercombobox");
        directedGraph.addVertex("m_attridcombobox");
        // Create the Edges 
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_buildstratcontiguityradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_buildstratdistanceradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_distancelineedit", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_distancelineedit");
        directedGraph.addEdge("m_distancelineedit", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_nearneighborlineedit");
        directedGraph.addEdge("m_distancelineedit", "m_calcdistcheckbox");
        directedGraph.addEdge("m_distancelineedit", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_distancelineedit", "m_filetoolbutton");
        directedGraph.addEdge("m_distancelineedit", "m_namelineedit");
        directedGraph.addEdge("m_distancelineedit", "m_galradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_gwtradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_dsradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_locationlineedit");
        directedGraph.addEdge("m_distancelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_distancelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_distancelineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_distancelineedit", "m_attridcombobox");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_buildstratnnradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_nearneighborlineedit", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_distancelineedit");
        directedGraph.addEdge("m_nearneighborlineedit", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_nearneighborlineedit");
        directedGraph.addEdge("m_nearneighborlineedit", "m_calcdistcheckbox");
        directedGraph.addEdge("m_nearneighborlineedit", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_nearneighborlineedit", "m_filetoolbutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_namelineedit");
        directedGraph.addEdge("m_nearneighborlineedit", "m_galradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_gwtradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_dsradiobutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_locationlineedit");
        directedGraph.addEdge("m_nearneighborlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_nearneighborlineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_nearneighborlineedit", "m_attridcombobox");
        directedGraph.addEdge("m_calcdistcheckbox", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_distancelineedit");
        directedGraph.addEdge("m_calcdistcheckbox", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_nearneighborlineedit");
        directedGraph.addEdge("m_calcdistcheckbox", "m_calcdistcheckbox");
        directedGraph.addEdge("m_calcdistcheckbox", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_calcdistcheckbox", "m_filetoolbutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_namelineedit");
        directedGraph.addEdge("m_calcdistcheckbox", "m_galradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_gwtradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_dsradiobutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_locationlineedit");
        directedGraph.addEdge("m_calcdistcheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_calcdistcheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_calcdistcheckbox", "m_attridcombobox");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_weightsquareinversedistradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_weightinversedistradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_weightnoweightradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_distancelineedit");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_nearneighborlineedit");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_calcdistcheckbox");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_filetoolbutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_namelineedit");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_galradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_gwtradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_dsradiobutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_locationlineedit");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_normalizeweightcheckbox", "m_attridcombobox");
        directedGraph.addEdge("m_filetoolbutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_distancelineedit");
        directedGraph.addEdge("m_filetoolbutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_filetoolbutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_filetoolbutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_filetoolbutton", "m_filetoolbutton");
        directedGraph.addEdge("m_filetoolbutton", "m_namelineedit");
        directedGraph.addEdge("m_filetoolbutton", "m_galradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_dsradiobutton");
        directedGraph.addEdge("m_filetoolbutton", "m_locationlineedit");
        directedGraph.addEdge("m_filetoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_filetoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_filetoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_filetoolbutton", "m_attridcombobox");
        directedGraph.addEdge("m_namelineedit", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_distancelineedit");
        directedGraph.addEdge("m_namelineedit", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_nearneighborlineedit");
        directedGraph.addEdge("m_namelineedit", "m_calcdistcheckbox");
        directedGraph.addEdge("m_namelineedit", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_namelineedit", "m_filetoolbutton");
        directedGraph.addEdge("m_namelineedit", "m_namelineedit");
        directedGraph.addEdge("m_namelineedit", "m_galradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_gwtradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_dsradiobutton");
        directedGraph.addEdge("m_namelineedit", "m_locationlineedit");
        directedGraph.addEdge("m_namelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_namelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_namelineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_namelineedit", "m_attridcombobox");
        directedGraph.addEdge("m_galradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_galradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_galradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_galradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_galradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_galradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_galradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_galradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_galradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_galradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_galradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_galradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_gwtradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_gwtradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_gwtradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_gwtradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_gwtradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_gwtradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_gwtradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_gwtradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_gwtradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_dsradiobutton", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_dsradiobutton", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_nearneighborlineedit");
        directedGraph.addEdge("m_dsradiobutton", "m_calcdistcheckbox");
        directedGraph.addEdge("m_dsradiobutton", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_dsradiobutton", "m_filetoolbutton");
        directedGraph.addEdge("m_dsradiobutton", "m_namelineedit");
        directedGraph.addEdge("m_dsradiobutton", "m_galradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_gwtradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_dsradiobutton");
        directedGraph.addEdge("m_dsradiobutton", "m_locationlineedit");
        directedGraph.addEdge("m_dsradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_dsradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_dsradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_dsradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_locationlineedit", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_distancelineedit");
        directedGraph.addEdge("m_locationlineedit", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_nearneighborlineedit");
        directedGraph.addEdge("m_locationlineedit", "m_calcdistcheckbox");
        directedGraph.addEdge("m_locationlineedit", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_locationlineedit", "m_filetoolbutton");
        directedGraph.addEdge("m_locationlineedit", "m_namelineedit");
        directedGraph.addEdge("m_locationlineedit", "m_galradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_gwtradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_dsradiobutton");
        directedGraph.addEdge("m_locationlineedit", "m_locationlineedit");
        directedGraph.addEdge("m_locationlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_locationlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_locationlineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_locationlineedit", "m_attridcombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_distancelineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_nearneighborlineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_calcdistcheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_filetoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_namelineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_galradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_gwtradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_dsradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_locationlineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_okpushbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_attridcombobox");
        directedGraph.addEdge("m_attridcombobox", "m_buildstratcontiguityradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_buildstratdistanceradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_distancelineedit");
        directedGraph.addEdge("m_attridcombobox", "m_buildstratnnradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_nearneighborlineedit");
        directedGraph.addEdge("m_attridcombobox", "m_calcdistcheckbox");
        directedGraph.addEdge("m_attridcombobox", "m_weightsquareinversedistradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_weightinversedistradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_weightnoweightradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_normalizeweightcheckbox");
        directedGraph.addEdge("m_attridcombobox", "m_filetoolbutton");
        directedGraph.addEdge("m_attridcombobox", "m_namelineedit");
        directedGraph.addEdge("m_attridcombobox", "m_galradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_gwtradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_dsradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_locationlineedit");
        directedGraph.addEdge("m_attridcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_attridcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_attridcombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_attridcombobox", "m_attridcombobox");

     
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
      //  for(String dv: allVertices) {
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
