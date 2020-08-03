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

public class GUI18 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI18(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI18");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
     // Initial Vertices (States)
        String[] initStates = {"m_layerscombobox", "m_layersearchtoolbutton", "m_dummycheckbox", "m_dummylineedit", "m_stepfixedradiobutton", "m_stepvariableradiobutton", "m_vminrasterlineedit", "m_vmaxrasterlineedit", "m_vminlineedit", "m_vmaxlineedit", "m_steplineedit", "m_valuelineedit", "m_insertpushbutton", "m_deletepushbutton", "m_deleteallpushbutton", "m_isolineslistwidget", "m_guidelinescheckbox", "m_srstoolbutton", "m_repositorylineedit", "m_targetfiletoolbutton", "m_targetdatasourcetoolbutton", "m_newlayernamelineedit"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_layerscombobox");
        directedGraph.addVertex("m_layersearchtoolbutton");
        directedGraph.addVertex("m_dummycheckbox");
        directedGraph.addVertex("m_dummylineedit");
        directedGraph.addVertex("m_stepfixedradiobutton");
        directedGraph.addVertex("m_stepvariableradiobutton");
        directedGraph.addVertex("m_vminrasterlineedit");
        directedGraph.addVertex("m_vmaxrasterlineedit");
        directedGraph.addVertex("m_vminlineedit");
        directedGraph.addVertex("m_vmaxlineedit");
        directedGraph.addVertex("m_steplineedit");
        directedGraph.addVertex("m_valuelineedit");
        directedGraph.addVertex("m_insertpushbutton");
        directedGraph.addVertex("m_deletepushbutton");
        directedGraph.addVertex("m_deleteallpushbutton");
        directedGraph.addVertex("m_isolineslistwidget");
        directedGraph.addVertex("m_guidelinescheckbox");
        directedGraph.addVertex("m_srstoolbutton");
        directedGraph.addVertex("m_repositorylineedit");
        directedGraph.addVertex("m_targetfiletoolbutton");
        directedGraph.addVertex("m_targetdatasourcetoolbutton");
        directedGraph.addVertex("m_newlayernamelineedit");
        directedGraph.addVertex("m_okpushbutton");
        directedGraph.addVertex("m_cancelpushbutton");
        // Create the Edges 
        directedGraph.addEdge("m_layerscombobox", "m_layerscombobox");
        directedGraph.addEdge("m_layerscombobox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_layerscombobox", "m_dummycheckbox");
        directedGraph.addEdge("m_layerscombobox", "m_dummylineedit");
        directedGraph.addEdge("m_layerscombobox", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_layerscombobox", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_layerscombobox", "m_vminrasterlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_vminlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_vmaxlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_steplineedit");
        directedGraph.addEdge("m_layerscombobox", "m_valuelineedit");
        directedGraph.addEdge("m_layerscombobox", "m_insertpushbutton");
        directedGraph.addEdge("m_layerscombobox", "m_deletepushbutton");
        directedGraph.addEdge("m_layerscombobox", "m_deleteallpushbutton");
        directedGraph.addEdge("m_layerscombobox", "m_isolineslistwidget");
        directedGraph.addEdge("m_layerscombobox", "m_guidelinescheckbox");
        directedGraph.addEdge("m_layerscombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_layerscombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_layerscombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_layerscombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_layerscombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_layerscombobox", "m_okpushbutton");
        directedGraph.addEdge("m_layerscombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_steplineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_valuelineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_insertpushbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_deletepushbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_layerscombobox");
        directedGraph.addEdge("m_dummycheckbox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_dummycheckbox");
        directedGraph.addEdge("m_dummycheckbox", "m_dummylineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_dummycheckbox", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_dummycheckbox", "m_vminrasterlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_vminlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_vmaxlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_steplineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_valuelineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_insertpushbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_deletepushbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_deleteallpushbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_isolineslistwidget");
        directedGraph.addEdge("m_dummycheckbox", "m_guidelinescheckbox");
        directedGraph.addEdge("m_dummycheckbox", "m_srstoolbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_layerscombobox");
        directedGraph.addEdge("m_dummylineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_dummylineedit", "m_dummylineedit");
        directedGraph.addEdge("m_dummylineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_vminlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_steplineedit");
        directedGraph.addEdge("m_dummylineedit", "m_valuelineedit");
        directedGraph.addEdge("m_dummylineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_dummylineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_dummylineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_dummylineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_dummylineedit", "m_okpushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_layerscombobox");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_dummycheckbox");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_vminlineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_steplineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_valuelineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_insertpushbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_deletepushbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_srstoolbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_stepfixedradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_layerscombobox");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_dummycheckbox");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_vminlineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_steplineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_valuelineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_insertpushbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_deletepushbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_srstoolbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_stepvariableradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_vminrasterlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_vminrasterlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_steplineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_valuelineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_vminrasterlineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_vminrasterlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_steplineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_valuelineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_vminlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_vminlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_vminlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_vminlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_vminlineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_vminlineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_vminlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_steplineedit");
        directedGraph.addEdge("m_vminlineedit", "m_valuelineedit");
        directedGraph.addEdge("m_vminlineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_vminlineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_vminlineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_vminlineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_vminlineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_vminlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_vminlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_vminlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_vminlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_vminlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_vminlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_vminlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_vmaxlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_vmaxlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_steplineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_valuelineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_vmaxlineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_vmaxlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_steplineedit", "m_layerscombobox");
        directedGraph.addEdge("m_steplineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_steplineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_steplineedit", "m_dummylineedit");
        directedGraph.addEdge("m_steplineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_steplineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_steplineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_steplineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_steplineedit", "m_vminlineedit");
        directedGraph.addEdge("m_steplineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_steplineedit", "m_steplineedit");
        directedGraph.addEdge("m_steplineedit", "m_valuelineedit");
        directedGraph.addEdge("m_steplineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_steplineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_steplineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_steplineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_steplineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_steplineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_steplineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_steplineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_steplineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_steplineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_steplineedit", "m_okpushbutton");
        directedGraph.addEdge("m_steplineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_valuelineedit", "m_layerscombobox");
        directedGraph.addEdge("m_valuelineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_valuelineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_valuelineedit", "m_dummylineedit");
        directedGraph.addEdge("m_valuelineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_valuelineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_valuelineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_valuelineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_valuelineedit", "m_vminlineedit");
        directedGraph.addEdge("m_valuelineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_valuelineedit", "m_steplineedit");
        directedGraph.addEdge("m_valuelineedit", "m_valuelineedit");
        directedGraph.addEdge("m_valuelineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_valuelineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_valuelineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_valuelineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_valuelineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_valuelineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_valuelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_valuelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_valuelineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_valuelineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_valuelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_valuelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_layerscombobox");
        directedGraph.addEdge("m_insertpushbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_insertpushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_insertpushbutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_insertpushbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_vminlineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_steplineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_valuelineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_insertpushbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_deletepushbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_insertpushbutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_insertpushbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_insertpushbutton", "m_okpushbutton");
        directedGraph.addEdge("m_insertpushbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_layerscombobox");
        directedGraph.addEdge("m_deletepushbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_deletepushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_deletepushbutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_deletepushbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_vminlineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_steplineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_valuelineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_insertpushbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_deletepushbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_deletepushbutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_deletepushbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_deletepushbutton", "m_okpushbutton");
        directedGraph.addEdge("m_deletepushbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_layerscombobox");
        directedGraph.addEdge("m_deleteallpushbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_deleteallpushbutton", "m_dummylineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_vminlineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_steplineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_valuelineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_insertpushbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_deletepushbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_deleteallpushbutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_deleteallpushbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_deleteallpushbutton", "m_okpushbutton");
        directedGraph.addEdge("m_deleteallpushbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_layerscombobox");
        directedGraph.addEdge("m_isolineslistwidget", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_dummycheckbox");
        directedGraph.addEdge("m_isolineslistwidget", "m_dummylineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_vminrasterlineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_vminlineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_vmaxlineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_steplineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_valuelineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_insertpushbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_deletepushbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_deleteallpushbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_isolineslistwidget");
        directedGraph.addEdge("m_isolineslistwidget", "m_guidelinescheckbox");
        directedGraph.addEdge("m_isolineslistwidget", "m_srstoolbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_repositorylineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_newlayernamelineedit");
        directedGraph.addEdge("m_isolineslistwidget", "m_okpushbutton");
        directedGraph.addEdge("m_isolineslistwidget", "m_cancelpushbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_layerscombobox");
        directedGraph.addEdge("m_guidelinescheckbox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_dummycheckbox");
        directedGraph.addEdge("m_guidelinescheckbox", "m_dummylineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_vminrasterlineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_vminlineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_vmaxlineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_steplineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_valuelineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_insertpushbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_deletepushbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_deleteallpushbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_isolineslistwidget");
        directedGraph.addEdge("m_guidelinescheckbox", "m_guidelinescheckbox");
        directedGraph.addEdge("m_guidelinescheckbox", "m_srstoolbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_guidelinescheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_guidelinescheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_srstoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_srstoolbutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_srstoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_steplineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_valuelineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_insertpushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_deletepushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_srstoolbutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_srstoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_layerscombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_dummylineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_vminlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_steplineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_valuelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_repositorylineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_okpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_steplineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_valuelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_insertpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_deletepushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_steplineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_valuelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_insertpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_deletepushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_deleteallpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_isolineslistwidget");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_guidelinescheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_layerscombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_dummylineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_stepfixedradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_stepvariableradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_vminlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_steplineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_valuelineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_insertpushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_deletepushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_deleteallpushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_isolineslistwidget");
        directedGraph.addEdge("m_newlayernamelineedit", "m_guidelinescheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_targetdatasourcetoolbutton");
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
