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

public class GUI2 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI2(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI2");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_contrastrhorizontalslider", "m_contrastghorizontalslider", "m_contrastbhorizontalslider", "m_contrastmhorizontalslider", "m_contrasttypecombobox", "m_rasterdummylineedit", "m_dummylineedit", "m_dummypushbutton", "m_gainresetpushbutton", "m_gainminuspushbutton", "m_gainpluspushbutton", "m_offsetminuspushbutton", "m_offsetpluspushbutton", "m_offsetresetpushbutton", "m_composemradiobutton", "m_composemcombobox", "m_composerradiobutton", "m_composercombobox", "m_composegradiobutton", "m_composegcombobox", "m_composebradiobutton", "m_composebcombobox", "m_composecradiobutton", "m_typecombobox", "m_bandcombobox", "m_histogramtoolbutton", "m_allimageradiobutton", "m_visiblearearadiobutton", "m_inminlineedit", "m_inmaxlineedit", "m_gainlineedit", "m_offset1lineedit", "m_offset2lineedit", "m_resettoolbutton"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_contrastrhorizontalslider");
        directedGraph.addVertex("m_contrastghorizontalslider");
        directedGraph.addVertex("m_contrastbhorizontalslider");
        directedGraph.addVertex("m_contrastmhorizontalslider");
        directedGraph.addVertex("m_contrasttypecombobox");
        directedGraph.addVertex("m_rasterdummylineedit");
        directedGraph.addVertex("m_dummylineedit");
        directedGraph.addVertex("m_dummypushbutton");
        directedGraph.addVertex("m_gainresetpushbutton");
        directedGraph.addVertex("m_gainminuspushbutton");
        directedGraph.addVertex("m_gainpluspushbutton");
        directedGraph.addVertex("m_offsetminuspushbutton");
        directedGraph.addVertex("m_offsetpluspushbutton");
        directedGraph.addVertex("m_offsetresetpushbutton");
        directedGraph.addVertex("m_composemradiobutton");
        directedGraph.addVertex("m_composemcombobox");
        directedGraph.addVertex("m_composerradiobutton");
        directedGraph.addVertex("m_composercombobox");
        directedGraph.addVertex("m_composegradiobutton");
        directedGraph.addVertex("m_composegcombobox");
        directedGraph.addVertex("m_composebradiobutton");
        directedGraph.addVertex("m_composebcombobox");
        directedGraph.addVertex("m_composecradiobutton");
        directedGraph.addVertex("m_typecombobox");
        directedGraph.addVertex("m_bandcombobox");
        directedGraph.addVertex("m_histogramtoolbutton");
        directedGraph.addVertex("m_allimageradiobutton");
        directedGraph.addVertex("m_visiblearearadiobutton");
        directedGraph.addVertex("m_inminlineedit");
        directedGraph.addVertex("m_inmaxlineedit");
        directedGraph.addVertex("m_gainlineedit");
        directedGraph.addVertex("m_offset1lineedit");
        directedGraph.addVertex("m_offset2lineedit");
        directedGraph.addVertex("m_applytoolbutton");
        directedGraph.addVertex("m_resettoolbutton");
        // Create the Edges 
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_contrasttypecombobox");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_rasterdummylineedit");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_dummylineedit");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_dummypushbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_gainresetpushbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_gainminuspushbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_gainpluspushbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composemradiobutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composemcombobox");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composerradiobutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composercombobox");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composegradiobutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composegcombobox");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composebradiobutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composebcombobox");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_composecradiobutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_typecombobox");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_bandcombobox");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_histogramtoolbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_allimageradiobutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_inminlineedit");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_inmaxlineedit");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_gainlineedit");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_offset1lineedit");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_offset2lineedit");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_applytoolbutton");
        directedGraph.addEdge("m_contrastrhorizontalslider", "m_resettoolbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_contrasttypecombobox");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_rasterdummylineedit");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_dummylineedit");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_dummypushbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_gainresetpushbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_gainminuspushbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_gainpluspushbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composemradiobutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composemcombobox");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composerradiobutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composercombobox");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composegradiobutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composegcombobox");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composebradiobutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composebcombobox");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_composecradiobutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_typecombobox");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_bandcombobox");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_histogramtoolbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_allimageradiobutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_inminlineedit");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_inmaxlineedit");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_gainlineedit");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_offset1lineedit");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_offset2lineedit");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_applytoolbutton");
        directedGraph.addEdge("m_contrastghorizontalslider", "m_resettoolbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_contrasttypecombobox");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_rasterdummylineedit");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_dummylineedit");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_dummypushbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_gainresetpushbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_gainminuspushbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_gainpluspushbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composemradiobutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composemcombobox");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composerradiobutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composercombobox");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composegradiobutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composegcombobox");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composebradiobutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composebcombobox");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_composecradiobutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_typecombobox");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_bandcombobox");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_histogramtoolbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_allimageradiobutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_inminlineedit");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_inmaxlineedit");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_gainlineedit");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_offset1lineedit");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_offset2lineedit");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_applytoolbutton");
        directedGraph.addEdge("m_contrastbhorizontalslider", "m_resettoolbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_contrasttypecombobox");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_rasterdummylineedit");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_dummylineedit");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_dummypushbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_gainresetpushbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_gainminuspushbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_gainpluspushbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composemradiobutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composemcombobox");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composerradiobutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composercombobox");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composegradiobutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composegcombobox");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composebradiobutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composebcombobox");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_composecradiobutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_typecombobox");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_bandcombobox");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_histogramtoolbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_allimageradiobutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_inminlineedit");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_inmaxlineedit");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_gainlineedit");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_offset1lineedit");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_offset2lineedit");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_applytoolbutton");
        directedGraph.addEdge("m_contrastmhorizontalslider", "m_resettoolbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_contrasttypecombobox", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_contrasttypecombobox", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_contrasttypecombobox", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_contrasttypecombobox", "m_contrasttypecombobox");
        directedGraph.addEdge("m_contrasttypecombobox", "m_rasterdummylineedit");
        directedGraph.addEdge("m_contrasttypecombobox", "m_dummylineedit");
        directedGraph.addEdge("m_contrasttypecombobox", "m_dummypushbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_gainresetpushbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_gainminuspushbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_gainpluspushbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composemradiobutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composemcombobox");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composerradiobutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composercombobox");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composegradiobutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composegcombobox");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composebradiobutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composebcombobox");
        directedGraph.addEdge("m_contrasttypecombobox", "m_composecradiobutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_typecombobox");
        directedGraph.addEdge("m_contrasttypecombobox", "m_bandcombobox");
        directedGraph.addEdge("m_contrasttypecombobox", "m_histogramtoolbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_allimageradiobutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_inminlineedit");
        directedGraph.addEdge("m_contrasttypecombobox", "m_inmaxlineedit");
        directedGraph.addEdge("m_contrasttypecombobox", "m_gainlineedit");
        directedGraph.addEdge("m_contrasttypecombobox", "m_offset1lineedit");
        directedGraph.addEdge("m_contrasttypecombobox", "m_offset2lineedit");
        directedGraph.addEdge("m_contrasttypecombobox", "m_applytoolbutton");
        directedGraph.addEdge("m_contrasttypecombobox", "m_resettoolbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_rasterdummylineedit", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_rasterdummylineedit", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_rasterdummylineedit", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_rasterdummylineedit", "m_contrasttypecombobox");
        directedGraph.addEdge("m_rasterdummylineedit", "m_rasterdummylineedit");
        directedGraph.addEdge("m_rasterdummylineedit", "m_dummylineedit");
        directedGraph.addEdge("m_rasterdummylineedit", "m_dummypushbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_gainresetpushbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_gainminuspushbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_gainpluspushbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composemradiobutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composemcombobox");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composerradiobutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composercombobox");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composegradiobutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composegcombobox");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composebradiobutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composebcombobox");
        directedGraph.addEdge("m_rasterdummylineedit", "m_composecradiobutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_typecombobox");
        directedGraph.addEdge("m_rasterdummylineedit", "m_bandcombobox");
        directedGraph.addEdge("m_rasterdummylineedit", "m_histogramtoolbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_allimageradiobutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_inminlineedit");
        directedGraph.addEdge("m_rasterdummylineedit", "m_inmaxlineedit");
        directedGraph.addEdge("m_rasterdummylineedit", "m_gainlineedit");
        directedGraph.addEdge("m_rasterdummylineedit", "m_offset1lineedit");
        directedGraph.addEdge("m_rasterdummylineedit", "m_offset2lineedit");
        directedGraph.addEdge("m_rasterdummylineedit", "m_applytoolbutton");
        directedGraph.addEdge("m_rasterdummylineedit", "m_resettoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_dummylineedit", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_dummylineedit", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_dummylineedit", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_dummylineedit", "m_contrasttypecombobox");
        directedGraph.addEdge("m_dummylineedit", "m_rasterdummylineedit");
        directedGraph.addEdge("m_dummylineedit", "m_dummylineedit");
        directedGraph.addEdge("m_dummylineedit", "m_dummypushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_gainresetpushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_gainminuspushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_gainpluspushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_composemradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_composemcombobox");
        directedGraph.addEdge("m_dummylineedit", "m_composerradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_composercombobox");
        directedGraph.addEdge("m_dummylineedit", "m_composegradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_composegcombobox");
        directedGraph.addEdge("m_dummylineedit", "m_composebradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_composebcombobox");
        directedGraph.addEdge("m_dummylineedit", "m_composecradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_typecombobox");
        directedGraph.addEdge("m_dummylineedit", "m_bandcombobox");
        directedGraph.addEdge("m_dummylineedit", "m_histogramtoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_allimageradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_inminlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_inmaxlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_gainlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_offset1lineedit");
        directedGraph.addEdge("m_dummylineedit", "m_offset2lineedit");
        directedGraph.addEdge("m_dummylineedit", "m_applytoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_resettoolbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_dummypushbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_dummypushbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_dummypushbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_dummypushbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_dummypushbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_dummypushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_dummypushbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_dummypushbutton", "m_composemcombobox");
        directedGraph.addEdge("m_dummypushbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_dummypushbutton", "m_composercombobox");
        directedGraph.addEdge("m_dummypushbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_dummypushbutton", "m_composegcombobox");
        directedGraph.addEdge("m_dummypushbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_dummypushbutton", "m_composebcombobox");
        directedGraph.addEdge("m_dummypushbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_dummypushbutton", "m_typecombobox");
        directedGraph.addEdge("m_dummypushbutton", "m_bandcombobox");
        directedGraph.addEdge("m_dummypushbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_dummypushbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_dummypushbutton", "m_inminlineedit");
        directedGraph.addEdge("m_dummypushbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_dummypushbutton", "m_gainlineedit");
        directedGraph.addEdge("m_dummypushbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_dummypushbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_dummypushbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_dummypushbutton", "m_resettoolbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_gainresetpushbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_gainresetpushbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_gainresetpushbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_gainresetpushbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_gainresetpushbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_gainresetpushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_gainresetpushbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composemcombobox");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composercombobox");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composegcombobox");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composebcombobox");
        directedGraph.addEdge("m_gainresetpushbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_typecombobox");
        directedGraph.addEdge("m_gainresetpushbutton", "m_bandcombobox");
        directedGraph.addEdge("m_gainresetpushbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_inminlineedit");
        directedGraph.addEdge("m_gainresetpushbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_gainresetpushbutton", "m_gainlineedit");
        directedGraph.addEdge("m_gainresetpushbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_gainresetpushbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_gainresetpushbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_gainresetpushbutton", "m_resettoolbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_gainminuspushbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_gainminuspushbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_gainminuspushbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_gainminuspushbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_gainminuspushbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_gainminuspushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_gainminuspushbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composemcombobox");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composercombobox");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composegcombobox");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composebcombobox");
        directedGraph.addEdge("m_gainminuspushbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_typecombobox");
        directedGraph.addEdge("m_gainminuspushbutton", "m_bandcombobox");
        directedGraph.addEdge("m_gainminuspushbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_inminlineedit");
        directedGraph.addEdge("m_gainminuspushbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_gainminuspushbutton", "m_gainlineedit");
        directedGraph.addEdge("m_gainminuspushbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_gainminuspushbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_gainminuspushbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_gainminuspushbutton", "m_resettoolbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_gainpluspushbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_gainpluspushbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_gainpluspushbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_gainpluspushbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_gainpluspushbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_gainpluspushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_gainpluspushbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composemcombobox");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composercombobox");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composegcombobox");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composebcombobox");
        directedGraph.addEdge("m_gainpluspushbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_typecombobox");
        directedGraph.addEdge("m_gainpluspushbutton", "m_bandcombobox");
        directedGraph.addEdge("m_gainpluspushbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_inminlineedit");
        directedGraph.addEdge("m_gainpluspushbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_gainpluspushbutton", "m_gainlineedit");
        directedGraph.addEdge("m_gainpluspushbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_gainpluspushbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_gainpluspushbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_gainpluspushbutton", "m_resettoolbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composemcombobox");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composercombobox");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composegcombobox");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composebcombobox");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_typecombobox");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_bandcombobox");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_inminlineedit");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_gainlineedit");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_offsetminuspushbutton", "m_resettoolbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composemcombobox");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composercombobox");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composegcombobox");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composebcombobox");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_typecombobox");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_bandcombobox");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_inminlineedit");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_gainlineedit");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_offsetpluspushbutton", "m_resettoolbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composemcombobox");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composercombobox");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composegcombobox");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composebcombobox");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_typecombobox");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_bandcombobox");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_inminlineedit");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_gainlineedit");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_offsetresetpushbutton", "m_resettoolbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composemradiobutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composemradiobutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composemradiobutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composemradiobutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composemradiobutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composemradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_composemradiobutton", "m_dummypushbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_composemradiobutton");
        directedGraph.addEdge("m_composemradiobutton", "m_composemcombobox");
        directedGraph.addEdge("m_composemradiobutton", "m_composerradiobutton");
        directedGraph.addEdge("m_composemradiobutton", "m_composercombobox");
        directedGraph.addEdge("m_composemradiobutton", "m_composegradiobutton");
        directedGraph.addEdge("m_composemradiobutton", "m_composegcombobox");
        directedGraph.addEdge("m_composemradiobutton", "m_composebradiobutton");
        directedGraph.addEdge("m_composemradiobutton", "m_composebcombobox");
        directedGraph.addEdge("m_composemradiobutton", "m_composecradiobutton");
        directedGraph.addEdge("m_composemradiobutton", "m_typecombobox");
        directedGraph.addEdge("m_composemradiobutton", "m_bandcombobox");
        directedGraph.addEdge("m_composemradiobutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_composemradiobutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composemradiobutton", "m_inminlineedit");
        directedGraph.addEdge("m_composemradiobutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_composemradiobutton", "m_gainlineedit");
        directedGraph.addEdge("m_composemradiobutton", "m_offset1lineedit");
        directedGraph.addEdge("m_composemradiobutton", "m_offset2lineedit");
        directedGraph.addEdge("m_composemradiobutton", "m_applytoolbutton");
        directedGraph.addEdge("m_composemradiobutton", "m_resettoolbutton");
        directedGraph.addEdge("m_composemcombobox", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composemcombobox", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composemcombobox", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composemcombobox", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composemcombobox", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composemcombobox", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composemcombobox", "m_dummylineedit");
        directedGraph.addEdge("m_composemcombobox", "m_dummypushbutton");
        directedGraph.addEdge("m_composemcombobox", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composemcombobox", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composemcombobox", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composemcombobox", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composemcombobox", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composemcombobox", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composemcombobox", "m_composemradiobutton");
        directedGraph.addEdge("m_composemcombobox", "m_composemcombobox");
        directedGraph.addEdge("m_composemcombobox", "m_composerradiobutton");
        directedGraph.addEdge("m_composemcombobox", "m_composercombobox");
        directedGraph.addEdge("m_composemcombobox", "m_composegradiobutton");
        directedGraph.addEdge("m_composemcombobox", "m_composegcombobox");
        directedGraph.addEdge("m_composemcombobox", "m_composebradiobutton");
        directedGraph.addEdge("m_composemcombobox", "m_composebcombobox");
        directedGraph.addEdge("m_composemcombobox", "m_composecradiobutton");
        directedGraph.addEdge("m_composemcombobox", "m_typecombobox");
        directedGraph.addEdge("m_composemcombobox", "m_bandcombobox");
        directedGraph.addEdge("m_composemcombobox", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composemcombobox", "m_allimageradiobutton");
        directedGraph.addEdge("m_composemcombobox", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composemcombobox", "m_inminlineedit");
        directedGraph.addEdge("m_composemcombobox", "m_inmaxlineedit");
        directedGraph.addEdge("m_composemcombobox", "m_gainlineedit");
        directedGraph.addEdge("m_composemcombobox", "m_offset1lineedit");
        directedGraph.addEdge("m_composemcombobox", "m_offset2lineedit");
        directedGraph.addEdge("m_composemcombobox", "m_applytoolbutton");
        directedGraph.addEdge("m_composemcombobox", "m_resettoolbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composerradiobutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composerradiobutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composerradiobutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composerradiobutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composerradiobutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composerradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_composerradiobutton", "m_dummypushbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_composemradiobutton");
        directedGraph.addEdge("m_composerradiobutton", "m_composemcombobox");
        directedGraph.addEdge("m_composerradiobutton", "m_composerradiobutton");
        directedGraph.addEdge("m_composerradiobutton", "m_composercombobox");
        directedGraph.addEdge("m_composerradiobutton", "m_composegradiobutton");
        directedGraph.addEdge("m_composerradiobutton", "m_composegcombobox");
        directedGraph.addEdge("m_composerradiobutton", "m_composebradiobutton");
        directedGraph.addEdge("m_composerradiobutton", "m_composebcombobox");
        directedGraph.addEdge("m_composerradiobutton", "m_composecradiobutton");
        directedGraph.addEdge("m_composerradiobutton", "m_typecombobox");
        directedGraph.addEdge("m_composerradiobutton", "m_bandcombobox");
        directedGraph.addEdge("m_composerradiobutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_composerradiobutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composerradiobutton", "m_inminlineedit");
        directedGraph.addEdge("m_composerradiobutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_composerradiobutton", "m_gainlineedit");
        directedGraph.addEdge("m_composerradiobutton", "m_offset1lineedit");
        directedGraph.addEdge("m_composerradiobutton", "m_offset2lineedit");
        directedGraph.addEdge("m_composerradiobutton", "m_applytoolbutton");
        directedGraph.addEdge("m_composerradiobutton", "m_resettoolbutton");
        directedGraph.addEdge("m_composercombobox", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composercombobox", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composercombobox", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composercombobox", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composercombobox", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composercombobox", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composercombobox", "m_dummylineedit");
        directedGraph.addEdge("m_composercombobox", "m_dummypushbutton");
        directedGraph.addEdge("m_composercombobox", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composercombobox", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composercombobox", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composercombobox", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composercombobox", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composercombobox", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composercombobox", "m_composemradiobutton");
        directedGraph.addEdge("m_composercombobox", "m_composemcombobox");
        directedGraph.addEdge("m_composercombobox", "m_composerradiobutton");
        directedGraph.addEdge("m_composercombobox", "m_composercombobox");
        directedGraph.addEdge("m_composercombobox", "m_composegradiobutton");
        directedGraph.addEdge("m_composercombobox", "m_composegcombobox");
        directedGraph.addEdge("m_composercombobox", "m_composebradiobutton");
        directedGraph.addEdge("m_composercombobox", "m_composebcombobox");
        directedGraph.addEdge("m_composercombobox", "m_composecradiobutton");
        directedGraph.addEdge("m_composercombobox", "m_typecombobox");
        directedGraph.addEdge("m_composercombobox", "m_bandcombobox");
        directedGraph.addEdge("m_composercombobox", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composercombobox", "m_allimageradiobutton");
        directedGraph.addEdge("m_composercombobox", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composercombobox", "m_inminlineedit");
        directedGraph.addEdge("m_composercombobox", "m_inmaxlineedit");
        directedGraph.addEdge("m_composercombobox", "m_gainlineedit");
        directedGraph.addEdge("m_composercombobox", "m_offset1lineedit");
        directedGraph.addEdge("m_composercombobox", "m_offset2lineedit");
        directedGraph.addEdge("m_composercombobox", "m_applytoolbutton");
        directedGraph.addEdge("m_composercombobox", "m_resettoolbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composegradiobutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composegradiobutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composegradiobutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composegradiobutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composegradiobutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composegradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_composegradiobutton", "m_dummypushbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_composemradiobutton");
        directedGraph.addEdge("m_composegradiobutton", "m_composemcombobox");
        directedGraph.addEdge("m_composegradiobutton", "m_composerradiobutton");
        directedGraph.addEdge("m_composegradiobutton", "m_composercombobox");
        directedGraph.addEdge("m_composegradiobutton", "m_composegradiobutton");
        directedGraph.addEdge("m_composegradiobutton", "m_composegcombobox");
        directedGraph.addEdge("m_composegradiobutton", "m_composebradiobutton");
        directedGraph.addEdge("m_composegradiobutton", "m_composebcombobox");
        directedGraph.addEdge("m_composegradiobutton", "m_composecradiobutton");
        directedGraph.addEdge("m_composegradiobutton", "m_typecombobox");
        directedGraph.addEdge("m_composegradiobutton", "m_bandcombobox");
        directedGraph.addEdge("m_composegradiobutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_composegradiobutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composegradiobutton", "m_inminlineedit");
        directedGraph.addEdge("m_composegradiobutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_composegradiobutton", "m_gainlineedit");
        directedGraph.addEdge("m_composegradiobutton", "m_offset1lineedit");
        directedGraph.addEdge("m_composegradiobutton", "m_offset2lineedit");
        directedGraph.addEdge("m_composegradiobutton", "m_applytoolbutton");
        directedGraph.addEdge("m_composegradiobutton", "m_resettoolbutton");
        directedGraph.addEdge("m_composegcombobox", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composegcombobox", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composegcombobox", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composegcombobox", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composegcombobox", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composegcombobox", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composegcombobox", "m_dummylineedit");
        directedGraph.addEdge("m_composegcombobox", "m_dummypushbutton");
        directedGraph.addEdge("m_composegcombobox", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composegcombobox", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composegcombobox", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composegcombobox", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composegcombobox", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composegcombobox", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composegcombobox", "m_composemradiobutton");
        directedGraph.addEdge("m_composegcombobox", "m_composemcombobox");
        directedGraph.addEdge("m_composegcombobox", "m_composerradiobutton");
        directedGraph.addEdge("m_composegcombobox", "m_composercombobox");
        directedGraph.addEdge("m_composegcombobox", "m_composegradiobutton");
        directedGraph.addEdge("m_composegcombobox", "m_composegcombobox");
        directedGraph.addEdge("m_composegcombobox", "m_composebradiobutton");
        directedGraph.addEdge("m_composegcombobox", "m_composebcombobox");
        directedGraph.addEdge("m_composegcombobox", "m_composecradiobutton");
        directedGraph.addEdge("m_composegcombobox", "m_typecombobox");
        directedGraph.addEdge("m_composegcombobox", "m_bandcombobox");
        directedGraph.addEdge("m_composegcombobox", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composegcombobox", "m_allimageradiobutton");
        directedGraph.addEdge("m_composegcombobox", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composegcombobox", "m_inminlineedit");
        directedGraph.addEdge("m_composegcombobox", "m_inmaxlineedit");
        directedGraph.addEdge("m_composegcombobox", "m_gainlineedit");
        directedGraph.addEdge("m_composegcombobox", "m_offset1lineedit");
        directedGraph.addEdge("m_composegcombobox", "m_offset2lineedit");
        directedGraph.addEdge("m_composegcombobox", "m_applytoolbutton");
        directedGraph.addEdge("m_composegcombobox", "m_resettoolbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composebradiobutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composebradiobutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composebradiobutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composebradiobutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composebradiobutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composebradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_composebradiobutton", "m_dummypushbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_composemradiobutton");
        directedGraph.addEdge("m_composebradiobutton", "m_composemcombobox");
        directedGraph.addEdge("m_composebradiobutton", "m_composerradiobutton");
        directedGraph.addEdge("m_composebradiobutton", "m_composercombobox");
        directedGraph.addEdge("m_composebradiobutton", "m_composegradiobutton");
        directedGraph.addEdge("m_composebradiobutton", "m_composegcombobox");
        directedGraph.addEdge("m_composebradiobutton", "m_composebradiobutton");
        directedGraph.addEdge("m_composebradiobutton", "m_composebcombobox");
        directedGraph.addEdge("m_composebradiobutton", "m_composecradiobutton");
        directedGraph.addEdge("m_composebradiobutton", "m_typecombobox");
        directedGraph.addEdge("m_composebradiobutton", "m_bandcombobox");
        directedGraph.addEdge("m_composebradiobutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_composebradiobutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composebradiobutton", "m_inminlineedit");
        directedGraph.addEdge("m_composebradiobutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_composebradiobutton", "m_gainlineedit");
        directedGraph.addEdge("m_composebradiobutton", "m_offset1lineedit");
        directedGraph.addEdge("m_composebradiobutton", "m_offset2lineedit");
        directedGraph.addEdge("m_composebradiobutton", "m_applytoolbutton");
        directedGraph.addEdge("m_composebradiobutton", "m_resettoolbutton");
        directedGraph.addEdge("m_composebcombobox", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composebcombobox", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composebcombobox", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composebcombobox", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composebcombobox", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composebcombobox", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composebcombobox", "m_dummylineedit");
        directedGraph.addEdge("m_composebcombobox", "m_dummypushbutton");
        directedGraph.addEdge("m_composebcombobox", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composebcombobox", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composebcombobox", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composebcombobox", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composebcombobox", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composebcombobox", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composebcombobox", "m_composemradiobutton");
        directedGraph.addEdge("m_composebcombobox", "m_composemcombobox");
        directedGraph.addEdge("m_composebcombobox", "m_composerradiobutton");
        directedGraph.addEdge("m_composebcombobox", "m_composercombobox");
        directedGraph.addEdge("m_composebcombobox", "m_composegradiobutton");
        directedGraph.addEdge("m_composebcombobox", "m_composegcombobox");
        directedGraph.addEdge("m_composebcombobox", "m_composebradiobutton");
        directedGraph.addEdge("m_composebcombobox", "m_composebcombobox");
        directedGraph.addEdge("m_composebcombobox", "m_composecradiobutton");
        directedGraph.addEdge("m_composebcombobox", "m_typecombobox");
        directedGraph.addEdge("m_composebcombobox", "m_bandcombobox");
        directedGraph.addEdge("m_composebcombobox", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composebcombobox", "m_allimageradiobutton");
        directedGraph.addEdge("m_composebcombobox", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composebcombobox", "m_inminlineedit");
        directedGraph.addEdge("m_composebcombobox", "m_inmaxlineedit");
        directedGraph.addEdge("m_composebcombobox", "m_gainlineedit");
        directedGraph.addEdge("m_composebcombobox", "m_offset1lineedit");
        directedGraph.addEdge("m_composebcombobox", "m_offset2lineedit");
        directedGraph.addEdge("m_composebcombobox", "m_applytoolbutton");
        directedGraph.addEdge("m_composebcombobox", "m_resettoolbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_composecradiobutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_composecradiobutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_composecradiobutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_composecradiobutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_composecradiobutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_composecradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_composecradiobutton", "m_dummypushbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_composemradiobutton");
        directedGraph.addEdge("m_composecradiobutton", "m_composemcombobox");
        directedGraph.addEdge("m_composecradiobutton", "m_composerradiobutton");
        directedGraph.addEdge("m_composecradiobutton", "m_composercombobox");
        directedGraph.addEdge("m_composecradiobutton", "m_composegradiobutton");
        directedGraph.addEdge("m_composecradiobutton", "m_composegcombobox");
        directedGraph.addEdge("m_composecradiobutton", "m_composebradiobutton");
        directedGraph.addEdge("m_composecradiobutton", "m_composebcombobox");
        directedGraph.addEdge("m_composecradiobutton", "m_composecradiobutton");
        directedGraph.addEdge("m_composecradiobutton", "m_typecombobox");
        directedGraph.addEdge("m_composecradiobutton", "m_bandcombobox");
        directedGraph.addEdge("m_composecradiobutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_composecradiobutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_composecradiobutton", "m_inminlineedit");
        directedGraph.addEdge("m_composecradiobutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_composecradiobutton", "m_gainlineedit");
        directedGraph.addEdge("m_composecradiobutton", "m_offset1lineedit");
        directedGraph.addEdge("m_composecradiobutton", "m_offset2lineedit");
        directedGraph.addEdge("m_composecradiobutton", "m_applytoolbutton");
        directedGraph.addEdge("m_composecradiobutton", "m_resettoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_typecombobox", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_typecombobox", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_typecombobox", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_typecombobox", "m_contrasttypecombobox");
        directedGraph.addEdge("m_typecombobox", "m_rasterdummylineedit");
        directedGraph.addEdge("m_typecombobox", "m_dummylineedit");
        directedGraph.addEdge("m_typecombobox", "m_dummypushbutton");
        directedGraph.addEdge("m_typecombobox", "m_gainresetpushbutton");
        directedGraph.addEdge("m_typecombobox", "m_gainminuspushbutton");
        directedGraph.addEdge("m_typecombobox", "m_gainpluspushbutton");
        directedGraph.addEdge("m_typecombobox", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_typecombobox", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_typecombobox", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_typecombobox", "m_composemradiobutton");
        directedGraph.addEdge("m_typecombobox", "m_composemcombobox");
        directedGraph.addEdge("m_typecombobox", "m_composerradiobutton");
        directedGraph.addEdge("m_typecombobox", "m_composercombobox");
        directedGraph.addEdge("m_typecombobox", "m_composegradiobutton");
        directedGraph.addEdge("m_typecombobox", "m_composegcombobox");
        directedGraph.addEdge("m_typecombobox", "m_composebradiobutton");
        directedGraph.addEdge("m_typecombobox", "m_composebcombobox");
        directedGraph.addEdge("m_typecombobox", "m_composecradiobutton");
        directedGraph.addEdge("m_typecombobox", "m_typecombobox");
        directedGraph.addEdge("m_typecombobox", "m_bandcombobox");
        directedGraph.addEdge("m_typecombobox", "m_histogramtoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_allimageradiobutton");
        directedGraph.addEdge("m_typecombobox", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_typecombobox", "m_inminlineedit");
        directedGraph.addEdge("m_typecombobox", "m_inmaxlineedit");
        directedGraph.addEdge("m_typecombobox", "m_gainlineedit");
        directedGraph.addEdge("m_typecombobox", "m_offset1lineedit");
        directedGraph.addEdge("m_typecombobox", "m_offset2lineedit");
        directedGraph.addEdge("m_typecombobox", "m_applytoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_resettoolbutton");
        directedGraph.addEdge("m_bandcombobox", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_bandcombobox", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_bandcombobox", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_bandcombobox", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_bandcombobox", "m_contrasttypecombobox");
        directedGraph.addEdge("m_bandcombobox", "m_rasterdummylineedit");
        directedGraph.addEdge("m_bandcombobox", "m_dummylineedit");
        directedGraph.addEdge("m_bandcombobox", "m_dummypushbutton");
        directedGraph.addEdge("m_bandcombobox", "m_gainresetpushbutton");
        directedGraph.addEdge("m_bandcombobox", "m_gainminuspushbutton");
        directedGraph.addEdge("m_bandcombobox", "m_gainpluspushbutton");
        directedGraph.addEdge("m_bandcombobox", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_bandcombobox", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_bandcombobox", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_bandcombobox", "m_composemradiobutton");
        directedGraph.addEdge("m_bandcombobox", "m_composemcombobox");
        directedGraph.addEdge("m_bandcombobox", "m_composerradiobutton");
        directedGraph.addEdge("m_bandcombobox", "m_composercombobox");
        directedGraph.addEdge("m_bandcombobox", "m_composegradiobutton");
        directedGraph.addEdge("m_bandcombobox", "m_composegcombobox");
        directedGraph.addEdge("m_bandcombobox", "m_composebradiobutton");
        directedGraph.addEdge("m_bandcombobox", "m_composebcombobox");
        directedGraph.addEdge("m_bandcombobox", "m_composecradiobutton");
        directedGraph.addEdge("m_bandcombobox", "m_typecombobox");
        directedGraph.addEdge("m_bandcombobox", "m_bandcombobox");
        directedGraph.addEdge("m_bandcombobox", "m_histogramtoolbutton");
        directedGraph.addEdge("m_bandcombobox", "m_allimageradiobutton");
        directedGraph.addEdge("m_bandcombobox", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_bandcombobox", "m_inminlineedit");
        directedGraph.addEdge("m_bandcombobox", "m_inmaxlineedit");
        directedGraph.addEdge("m_bandcombobox", "m_gainlineedit");
        directedGraph.addEdge("m_bandcombobox", "m_offset1lineedit");
        directedGraph.addEdge("m_bandcombobox", "m_offset2lineedit");
        directedGraph.addEdge("m_bandcombobox", "m_applytoolbutton");
        directedGraph.addEdge("m_bandcombobox", "m_resettoolbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_histogramtoolbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_histogramtoolbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_histogramtoolbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_histogramtoolbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_histogramtoolbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_histogramtoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_histogramtoolbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composemcombobox");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composercombobox");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composegcombobox");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composebcombobox");
        directedGraph.addEdge("m_histogramtoolbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_histogramtoolbutton", "m_bandcombobox");
        directedGraph.addEdge("m_histogramtoolbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_inminlineedit");
        directedGraph.addEdge("m_histogramtoolbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_histogramtoolbutton", "m_gainlineedit");
        directedGraph.addEdge("m_histogramtoolbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_histogramtoolbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_histogramtoolbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_histogramtoolbutton", "m_resettoolbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_allimageradiobutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_allimageradiobutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_allimageradiobutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_allimageradiobutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_allimageradiobutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_allimageradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_allimageradiobutton", "m_dummypushbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_composemradiobutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_composemcombobox");
        directedGraph.addEdge("m_allimageradiobutton", "m_composerradiobutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_composercombobox");
        directedGraph.addEdge("m_allimageradiobutton", "m_composegradiobutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_composegcombobox");
        directedGraph.addEdge("m_allimageradiobutton", "m_composebradiobutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_composebcombobox");
        directedGraph.addEdge("m_allimageradiobutton", "m_composecradiobutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_typecombobox");
        directedGraph.addEdge("m_allimageradiobutton", "m_bandcombobox");
        directedGraph.addEdge("m_allimageradiobutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_inminlineedit");
        directedGraph.addEdge("m_allimageradiobutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_allimageradiobutton", "m_gainlineedit");
        directedGraph.addEdge("m_allimageradiobutton", "m_offset1lineedit");
        directedGraph.addEdge("m_allimageradiobutton", "m_offset2lineedit");
        directedGraph.addEdge("m_allimageradiobutton", "m_applytoolbutton");
        directedGraph.addEdge("m_allimageradiobutton", "m_resettoolbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_dummypushbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composemradiobutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composemcombobox");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composerradiobutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composercombobox");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composegradiobutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composegcombobox");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composebradiobutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composebcombobox");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_composecradiobutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_typecombobox");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_bandcombobox");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_inminlineedit");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_gainlineedit");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_offset1lineedit");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_offset2lineedit");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_applytoolbutton");
        directedGraph.addEdge("m_visiblearearadiobutton", "m_resettoolbutton");
        directedGraph.addEdge("m_inminlineedit", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_inminlineedit", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_inminlineedit", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_inminlineedit", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_inminlineedit", "m_contrasttypecombobox");
        directedGraph.addEdge("m_inminlineedit", "m_rasterdummylineedit");
        directedGraph.addEdge("m_inminlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_inminlineedit", "m_dummypushbutton");
        directedGraph.addEdge("m_inminlineedit", "m_gainresetpushbutton");
        directedGraph.addEdge("m_inminlineedit", "m_gainminuspushbutton");
        directedGraph.addEdge("m_inminlineedit", "m_gainpluspushbutton");
        directedGraph.addEdge("m_inminlineedit", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_inminlineedit", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_inminlineedit", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_inminlineedit", "m_composemradiobutton");
        directedGraph.addEdge("m_inminlineedit", "m_composemcombobox");
        directedGraph.addEdge("m_inminlineedit", "m_composerradiobutton");
        directedGraph.addEdge("m_inminlineedit", "m_composercombobox");
        directedGraph.addEdge("m_inminlineedit", "m_composegradiobutton");
        directedGraph.addEdge("m_inminlineedit", "m_composegcombobox");
        directedGraph.addEdge("m_inminlineedit", "m_composebradiobutton");
        directedGraph.addEdge("m_inminlineedit", "m_composebcombobox");
        directedGraph.addEdge("m_inminlineedit", "m_composecradiobutton");
        directedGraph.addEdge("m_inminlineedit", "m_typecombobox");
        directedGraph.addEdge("m_inminlineedit", "m_bandcombobox");
        directedGraph.addEdge("m_inminlineedit", "m_histogramtoolbutton");
        directedGraph.addEdge("m_inminlineedit", "m_allimageradiobutton");
        directedGraph.addEdge("m_inminlineedit", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_inminlineedit", "m_inminlineedit");
        directedGraph.addEdge("m_inminlineedit", "m_inmaxlineedit");
        directedGraph.addEdge("m_inminlineedit", "m_gainlineedit");
        directedGraph.addEdge("m_inminlineedit", "m_offset1lineedit");
        directedGraph.addEdge("m_inminlineedit", "m_offset2lineedit");
        directedGraph.addEdge("m_inminlineedit", "m_applytoolbutton");
        directedGraph.addEdge("m_inminlineedit", "m_resettoolbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_inmaxlineedit", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_inmaxlineedit", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_inmaxlineedit", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_inmaxlineedit", "m_contrasttypecombobox");
        directedGraph.addEdge("m_inmaxlineedit", "m_rasterdummylineedit");
        directedGraph.addEdge("m_inmaxlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_inmaxlineedit", "m_dummypushbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_gainresetpushbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_gainminuspushbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_gainpluspushbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_composemradiobutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_composemcombobox");
        directedGraph.addEdge("m_inmaxlineedit", "m_composerradiobutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_composercombobox");
        directedGraph.addEdge("m_inmaxlineedit", "m_composegradiobutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_composegcombobox");
        directedGraph.addEdge("m_inmaxlineedit", "m_composebradiobutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_composebcombobox");
        directedGraph.addEdge("m_inmaxlineedit", "m_composecradiobutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_typecombobox");
        directedGraph.addEdge("m_inmaxlineedit", "m_bandcombobox");
        directedGraph.addEdge("m_inmaxlineedit", "m_histogramtoolbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_allimageradiobutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_inminlineedit");
        directedGraph.addEdge("m_inmaxlineedit", "m_inmaxlineedit");
        directedGraph.addEdge("m_inmaxlineedit", "m_gainlineedit");
        directedGraph.addEdge("m_inmaxlineedit", "m_offset1lineedit");
        directedGraph.addEdge("m_inmaxlineedit", "m_offset2lineedit");
        directedGraph.addEdge("m_inmaxlineedit", "m_applytoolbutton");
        directedGraph.addEdge("m_inmaxlineedit", "m_resettoolbutton");
        directedGraph.addEdge("m_gainlineedit", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_gainlineedit", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_gainlineedit", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_gainlineedit", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_gainlineedit", "m_contrasttypecombobox");
        directedGraph.addEdge("m_gainlineedit", "m_rasterdummylineedit");
        directedGraph.addEdge("m_gainlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_gainlineedit", "m_dummypushbutton");
        directedGraph.addEdge("m_gainlineedit", "m_gainresetpushbutton");
        directedGraph.addEdge("m_gainlineedit", "m_gainminuspushbutton");
        directedGraph.addEdge("m_gainlineedit", "m_gainpluspushbutton");
        directedGraph.addEdge("m_gainlineedit", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_gainlineedit", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_gainlineedit", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_gainlineedit", "m_composemradiobutton");
        directedGraph.addEdge("m_gainlineedit", "m_composemcombobox");
        directedGraph.addEdge("m_gainlineedit", "m_composerradiobutton");
        directedGraph.addEdge("m_gainlineedit", "m_composercombobox");
        directedGraph.addEdge("m_gainlineedit", "m_composegradiobutton");
        directedGraph.addEdge("m_gainlineedit", "m_composegcombobox");
        directedGraph.addEdge("m_gainlineedit", "m_composebradiobutton");
        directedGraph.addEdge("m_gainlineedit", "m_composebcombobox");
        directedGraph.addEdge("m_gainlineedit", "m_composecradiobutton");
        directedGraph.addEdge("m_gainlineedit", "m_typecombobox");
        directedGraph.addEdge("m_gainlineedit", "m_bandcombobox");
        directedGraph.addEdge("m_gainlineedit", "m_histogramtoolbutton");
        directedGraph.addEdge("m_gainlineedit", "m_allimageradiobutton");
        directedGraph.addEdge("m_gainlineedit", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_gainlineedit", "m_inminlineedit");
        directedGraph.addEdge("m_gainlineedit", "m_inmaxlineedit");
        directedGraph.addEdge("m_gainlineedit", "m_gainlineedit");
        directedGraph.addEdge("m_gainlineedit", "m_offset1lineedit");
        directedGraph.addEdge("m_gainlineedit", "m_offset2lineedit");
        directedGraph.addEdge("m_gainlineedit", "m_applytoolbutton");
        directedGraph.addEdge("m_gainlineedit", "m_resettoolbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_offset1lineedit", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_offset1lineedit", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_offset1lineedit", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_offset1lineedit", "m_contrasttypecombobox");
        directedGraph.addEdge("m_offset1lineedit", "m_rasterdummylineedit");
        directedGraph.addEdge("m_offset1lineedit", "m_dummylineedit");
        directedGraph.addEdge("m_offset1lineedit", "m_dummypushbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_gainresetpushbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_gainminuspushbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_gainpluspushbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_composemradiobutton");
        directedGraph.addEdge("m_offset1lineedit", "m_composemcombobox");
        directedGraph.addEdge("m_offset1lineedit", "m_composerradiobutton");
        directedGraph.addEdge("m_offset1lineedit", "m_composercombobox");
        directedGraph.addEdge("m_offset1lineedit", "m_composegradiobutton");
        directedGraph.addEdge("m_offset1lineedit", "m_composegcombobox");
        directedGraph.addEdge("m_offset1lineedit", "m_composebradiobutton");
        directedGraph.addEdge("m_offset1lineedit", "m_composebcombobox");
        directedGraph.addEdge("m_offset1lineedit", "m_composecradiobutton");
        directedGraph.addEdge("m_offset1lineedit", "m_typecombobox");
        directedGraph.addEdge("m_offset1lineedit", "m_bandcombobox");
        directedGraph.addEdge("m_offset1lineedit", "m_histogramtoolbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_allimageradiobutton");
        directedGraph.addEdge("m_offset1lineedit", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_offset1lineedit", "m_inminlineedit");
        directedGraph.addEdge("m_offset1lineedit", "m_inmaxlineedit");
        directedGraph.addEdge("m_offset1lineedit", "m_gainlineedit");
        directedGraph.addEdge("m_offset1lineedit", "m_offset1lineedit");
        directedGraph.addEdge("m_offset1lineedit", "m_offset2lineedit");
        directedGraph.addEdge("m_offset1lineedit", "m_applytoolbutton");
        directedGraph.addEdge("m_offset1lineedit", "m_resettoolbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_offset2lineedit", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_offset2lineedit", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_offset2lineedit", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_offset2lineedit", "m_contrasttypecombobox");
        directedGraph.addEdge("m_offset2lineedit", "m_rasterdummylineedit");
        directedGraph.addEdge("m_offset2lineedit", "m_dummylineedit");
        directedGraph.addEdge("m_offset2lineedit", "m_dummypushbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_gainresetpushbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_gainminuspushbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_gainpluspushbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_composemradiobutton");
        directedGraph.addEdge("m_offset2lineedit", "m_composemcombobox");
        directedGraph.addEdge("m_offset2lineedit", "m_composerradiobutton");
        directedGraph.addEdge("m_offset2lineedit", "m_composercombobox");
        directedGraph.addEdge("m_offset2lineedit", "m_composegradiobutton");
        directedGraph.addEdge("m_offset2lineedit", "m_composegcombobox");
        directedGraph.addEdge("m_offset2lineedit", "m_composebradiobutton");
        directedGraph.addEdge("m_offset2lineedit", "m_composebcombobox");
        directedGraph.addEdge("m_offset2lineedit", "m_composecradiobutton");
        directedGraph.addEdge("m_offset2lineedit", "m_typecombobox");
        directedGraph.addEdge("m_offset2lineedit", "m_bandcombobox");
        directedGraph.addEdge("m_offset2lineedit", "m_histogramtoolbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_allimageradiobutton");
        directedGraph.addEdge("m_offset2lineedit", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_offset2lineedit", "m_inminlineedit");
        directedGraph.addEdge("m_offset2lineedit", "m_inmaxlineedit");
        directedGraph.addEdge("m_offset2lineedit", "m_gainlineedit");
        directedGraph.addEdge("m_offset2lineedit", "m_offset1lineedit");
        directedGraph.addEdge("m_offset2lineedit", "m_offset2lineedit");
        directedGraph.addEdge("m_offset2lineedit", "m_applytoolbutton");
        directedGraph.addEdge("m_offset2lineedit", "m_resettoolbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_contrastrhorizontalslider");
        directedGraph.addEdge("m_resettoolbutton", "m_contrastghorizontalslider");
        directedGraph.addEdge("m_resettoolbutton", "m_contrastbhorizontalslider");
        directedGraph.addEdge("m_resettoolbutton", "m_contrastmhorizontalslider");
        directedGraph.addEdge("m_resettoolbutton", "m_contrasttypecombobox");
        directedGraph.addEdge("m_resettoolbutton", "m_rasterdummylineedit");
        directedGraph.addEdge("m_resettoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_resettoolbutton", "m_dummypushbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_gainresetpushbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_gainminuspushbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_gainpluspushbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_offsetminuspushbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_offsetpluspushbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_offsetresetpushbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_composemradiobutton");
        directedGraph.addEdge("m_resettoolbutton", "m_composemcombobox");
        directedGraph.addEdge("m_resettoolbutton", "m_composerradiobutton");
        directedGraph.addEdge("m_resettoolbutton", "m_composercombobox");
        directedGraph.addEdge("m_resettoolbutton", "m_composegradiobutton");
        directedGraph.addEdge("m_resettoolbutton", "m_composegcombobox");
        directedGraph.addEdge("m_resettoolbutton", "m_composebradiobutton");
        directedGraph.addEdge("m_resettoolbutton", "m_composebcombobox");
        directedGraph.addEdge("m_resettoolbutton", "m_composecradiobutton");
        directedGraph.addEdge("m_resettoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_resettoolbutton", "m_bandcombobox");
        directedGraph.addEdge("m_resettoolbutton", "m_histogramtoolbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_allimageradiobutton");
        directedGraph.addEdge("m_resettoolbutton", "m_visiblearearadiobutton");
        directedGraph.addEdge("m_resettoolbutton", "m_inminlineedit");
        directedGraph.addEdge("m_resettoolbutton", "m_inmaxlineedit");
        directedGraph.addEdge("m_resettoolbutton", "m_gainlineedit");
        directedGraph.addEdge("m_resettoolbutton", "m_offset1lineedit");
        directedGraph.addEdge("m_resettoolbutton", "m_offset2lineedit");
        directedGraph.addEdge("m_resettoolbutton", "m_applytoolbutton");
        directedGraph.addEdge("m_resettoolbutton", "m_resettoolbutton");

     
           
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
