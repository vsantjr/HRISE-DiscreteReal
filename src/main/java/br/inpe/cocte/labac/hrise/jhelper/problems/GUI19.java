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

public class GUI19 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI19(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI19");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_layerscombobox", "m_layersearchtoolbutton", "m_dummycheckbox", "m_dummylineedit", "m_vminrasterlineedit", "m_vmaxrasterlineedit", "m_vminlineedit", "m_vmaxlineedit", "m_azimuthlineedit", "m_elevationlineedit", "m_relieflineedit", "m_previewtoolbutton", "m_resxlineedit", "m_resylineedit", "m_dimclineedit", "m_dimllineedit", "m_srstoolbutton", "m_repositorylineedit", "m_targetfiletoolbutton", "m_targetdatasourcetoolbutton", "m_newlayernamelineedit"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_layerscombobox");
        directedGraph.addVertex("m_layersearchtoolbutton");
        directedGraph.addVertex("m_dummycheckbox");
        directedGraph.addVertex("m_dummylineedit");
        directedGraph.addVertex("m_vminrasterlineedit");
        directedGraph.addVertex("m_vmaxrasterlineedit");
        directedGraph.addVertex("m_vminlineedit");
        directedGraph.addVertex("m_vmaxlineedit");
        directedGraph.addVertex("m_azimuthlineedit");
        directedGraph.addVertex("m_elevationlineedit");
        directedGraph.addVertex("m_relieflineedit");
        directedGraph.addVertex("m_previewtoolbutton");
        directedGraph.addVertex("m_resxlineedit");
        directedGraph.addVertex("m_resylineedit");
        directedGraph.addVertex("m_dimclineedit");
        directedGraph.addVertex("m_dimllineedit");
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
        directedGraph.addEdge("m_layerscombobox", "m_vminrasterlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_vminlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_vmaxlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_azimuthlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_elevationlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_relieflineedit");
        directedGraph.addEdge("m_layerscombobox", "m_previewtoolbutton");
        directedGraph.addEdge("m_layerscombobox", "m_resxlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_resylineedit");
        directedGraph.addEdge("m_layerscombobox", "m_dimclineedit");
        directedGraph.addEdge("m_layerscombobox", "m_dimllineedit");
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
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_azimuthlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_elevationlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_relieflineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_previewtoolbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_resxlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_resylineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_dimclineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_dimllineedit");
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
        directedGraph.addEdge("m_dummycheckbox", "m_vminrasterlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_vminlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_vmaxlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_azimuthlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_elevationlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_relieflineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_previewtoolbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_resxlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_resylineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_dimclineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_dimllineedit");
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
        directedGraph.addEdge("m_dummylineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_vminlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_relieflineedit");
        directedGraph.addEdge("m_dummylineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_resxlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_resylineedit");
        directedGraph.addEdge("m_dummylineedit", "m_dimclineedit");
        directedGraph.addEdge("m_dummylineedit", "m_dimllineedit");
        directedGraph.addEdge("m_dummylineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_dummylineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_dummylineedit", "m_okpushbutton");
        directedGraph.addEdge("m_dummylineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_vminrasterlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_vminrasterlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_relieflineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_vminrasterlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_resylineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_vminrasterlineedit", "m_dimllineedit");
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
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_relieflineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_resylineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_vmaxrasterlineedit", "m_dimllineedit");
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
        directedGraph.addEdge("m_vminlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_relieflineedit");
        directedGraph.addEdge("m_vminlineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_vminlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_vminlineedit", "m_resylineedit");
        directedGraph.addEdge("m_vminlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_vminlineedit", "m_dimllineedit");
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
        directedGraph.addEdge("m_vmaxlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_relieflineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_resylineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_dimllineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_vmaxlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_vmaxlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_azimuthlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_azimuthlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_azimuthlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_azimuthlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_relieflineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_azimuthlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_resylineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_dimllineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_azimuthlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_azimuthlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_azimuthlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_azimuthlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_azimuthlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_elevationlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_elevationlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_elevationlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_elevationlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_relieflineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_elevationlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_resylineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_dimllineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_elevationlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_elevationlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_elevationlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_elevationlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_elevationlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_relieflineedit", "m_layerscombobox");
        directedGraph.addEdge("m_relieflineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_relieflineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_relieflineedit", "m_dummylineedit");
        directedGraph.addEdge("m_relieflineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_relieflineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_relieflineedit", "m_vminlineedit");
        directedGraph.addEdge("m_relieflineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_relieflineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_relieflineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_relieflineedit", "m_relieflineedit");
        directedGraph.addEdge("m_relieflineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_relieflineedit", "m_resxlineedit");
        directedGraph.addEdge("m_relieflineedit", "m_resylineedit");
        directedGraph.addEdge("m_relieflineedit", "m_dimclineedit");
        directedGraph.addEdge("m_relieflineedit", "m_dimllineedit");
        directedGraph.addEdge("m_relieflineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_relieflineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_relieflineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_relieflineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_relieflineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_relieflineedit", "m_okpushbutton");
        directedGraph.addEdge("m_relieflineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_previewtoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_previewtoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_previewtoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_previewtoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_azimuthlineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_elevationlineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_relieflineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_previewtoolbutton");
        directedGraph.addEdge("m_previewtoolbutton", "m_resxlineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_resylineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_dimclineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_dimllineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_previewtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_previewtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_previewtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_previewtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_previewtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_resxlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_resxlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_resxlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_resxlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_resxlineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_vminlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_relieflineedit");
        directedGraph.addEdge("m_resxlineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_resxlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_resylineedit");
        directedGraph.addEdge("m_resxlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_resxlineedit", "m_dimllineedit");
        directedGraph.addEdge("m_resxlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_resxlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_resxlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_resxlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_resxlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_resxlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_resxlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_resylineedit", "m_layerscombobox");
        directedGraph.addEdge("m_resylineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_resylineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_resylineedit", "m_dummylineedit");
        directedGraph.addEdge("m_resylineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_resylineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_resylineedit", "m_vminlineedit");
        directedGraph.addEdge("m_resylineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_resylineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_resylineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_resylineedit", "m_relieflineedit");
        directedGraph.addEdge("m_resylineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_resylineedit", "m_resxlineedit");
        directedGraph.addEdge("m_resylineedit", "m_resylineedit");
        directedGraph.addEdge("m_resylineedit", "m_dimclineedit");
        directedGraph.addEdge("m_resylineedit", "m_dimllineedit");
        directedGraph.addEdge("m_resylineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_resylineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_resylineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_resylineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_resylineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_resylineedit", "m_okpushbutton");
        directedGraph.addEdge("m_resylineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_dimclineedit", "m_layerscombobox");
        directedGraph.addEdge("m_dimclineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_dimclineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_dimclineedit", "m_dummylineedit");
        directedGraph.addEdge("m_dimclineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_vminlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_relieflineedit");
        directedGraph.addEdge("m_dimclineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_dimclineedit", "m_resxlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_resylineedit");
        directedGraph.addEdge("m_dimclineedit", "m_dimclineedit");
        directedGraph.addEdge("m_dimclineedit", "m_dimllineedit");
        directedGraph.addEdge("m_dimclineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_dimclineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_dimclineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_dimclineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_dimclineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_dimclineedit", "m_okpushbutton");
        directedGraph.addEdge("m_dimclineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_dimllineedit", "m_layerscombobox");
        directedGraph.addEdge("m_dimllineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_dimllineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_dimllineedit", "m_dummylineedit");
        directedGraph.addEdge("m_dimllineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_vminlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_relieflineedit");
        directedGraph.addEdge("m_dimllineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_dimllineedit", "m_resxlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_resylineedit");
        directedGraph.addEdge("m_dimllineedit", "m_dimclineedit");
        directedGraph.addEdge("m_dimllineedit", "m_dimllineedit");
        directedGraph.addEdge("m_dimllineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_dimllineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_dimllineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_dimllineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_dimllineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_dimllineedit", "m_okpushbutton");
        directedGraph.addEdge("m_dimllineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_srstoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_azimuthlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_elevationlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_relieflineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_previewtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_resxlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_resylineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_dimclineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_dimllineedit");
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
        directedGraph.addEdge("m_repositorylineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_vminlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_relieflineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_resxlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_resylineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_dimclineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_dimllineedit");
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
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_azimuthlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_elevationlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_relieflineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_previewtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_resxlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_resylineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_dimclineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_dimllineedit");
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
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vminrasterlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vminlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vmaxlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_azimuthlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_elevationlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_relieflineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_previewtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_resxlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_resylineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_dimclineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_dimllineedit");
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
        directedGraph.addEdge("m_newlayernamelineedit", "m_vminrasterlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_vmaxrasterlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_vminlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_vmaxlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_azimuthlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_elevationlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_relieflineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_previewtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_resxlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_resylineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_dimclineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_dimllineedit");
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
