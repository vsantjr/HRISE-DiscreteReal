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

public class GUI21 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI21(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI21");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_vectorradiobutton", "m_gridradiobutton", "m_isolinescombobox", "m_isolinessearchtoolbutton", "m_isolineszcombobox", "m_samplescombobox", "m_samplesearchtoolbutton", "m_sampleszcombobox", "m_dummycheckbox", "m_dummylineedit", "m_layersearchtoolbutton", "m_layerscombobox", "m_interpolatorcombobox", "m_powercombobox", "m_radiuslineedit", "m_sepxspinbox", "m_sepyspinbox", "m_minptsspinbox", "m_tensionlineedit", "m_smothlineedit", "m_minptsmitlineedit", "m_resxlineedit", "m_resylineedit", "m_dimclineedit", "m_dimllineedit", "m_srstoolbutton", "m_repositorylineedit", "m_targetfiletoolbutton", "m_targetdatasourcetoolbutton", "m_newlayernamelineedit"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_vectorradiobutton");
        directedGraph.addVertex("m_gridradiobutton");
        directedGraph.addVertex("m_isolinescombobox");
        directedGraph.addVertex("m_isolinessearchtoolbutton");
        directedGraph.addVertex("m_isolineszcombobox");
        directedGraph.addVertex("m_samplescombobox");
        directedGraph.addVertex("m_samplesearchtoolbutton");
        directedGraph.addVertex("m_sampleszcombobox");
        directedGraph.addVertex("m_dummycheckbox");
        directedGraph.addVertex("m_dummylineedit");
        directedGraph.addVertex("m_layersearchtoolbutton");
        directedGraph.addVertex("m_layerscombobox");
        directedGraph.addVertex("m_interpolatorcombobox");
        directedGraph.addVertex("m_powercombobox");
        directedGraph.addVertex("m_radiuslineedit");
        directedGraph.addVertex("m_sepxspinbox");
        directedGraph.addVertex("m_sepyspinbox");
        directedGraph.addVertex("m_minptsspinbox");
        directedGraph.addVertex("m_tensionlineedit");
        directedGraph.addVertex("m_smothlineedit");
        directedGraph.addVertex("m_minptsmitlineedit");
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
        directedGraph.addEdge("m_vectorradiobutton", "m_vectorradiobutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_gridradiobutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_isolinescombobox");
        directedGraph.addEdge("m_vectorradiobutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_vectorradiobutton", "m_samplescombobox");
        directedGraph.addEdge("m_vectorradiobutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_vectorradiobutton", "m_dummycheckbox");
        directedGraph.addEdge("m_vectorradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_layerscombobox");
        directedGraph.addEdge("m_vectorradiobutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_vectorradiobutton", "m_powercombobox");
        directedGraph.addEdge("m_vectorradiobutton", "m_radiuslineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_sepxspinbox");
        directedGraph.addEdge("m_vectorradiobutton", "m_sepyspinbox");
        directedGraph.addEdge("m_vectorradiobutton", "m_minptsspinbox");
        directedGraph.addEdge("m_vectorradiobutton", "m_tensionlineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_smothlineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_minptsmitlineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_resxlineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_resylineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_dimclineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_dimllineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_srstoolbutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_vectorradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_vectorradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_vectorradiobutton");
        directedGraph.addEdge("m_gridradiobutton", "m_gridradiobutton");
        directedGraph.addEdge("m_gridradiobutton", "m_isolinescombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_samplescombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_dummycheckbox");
        directedGraph.addEdge("m_gridradiobutton", "m_dummylineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_layerscombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_powercombobox");
        directedGraph.addEdge("m_gridradiobutton", "m_radiuslineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_sepxspinbox");
        directedGraph.addEdge("m_gridradiobutton", "m_sepyspinbox");
        directedGraph.addEdge("m_gridradiobutton", "m_minptsspinbox");
        directedGraph.addEdge("m_gridradiobutton", "m_tensionlineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_smothlineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_minptsmitlineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_resxlineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_resylineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_dimclineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_dimllineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_srstoolbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_gridradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_gridradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_vectorradiobutton");
        directedGraph.addEdge("m_isolinescombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_isolinescombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_samplescombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_dummycheckbox");
        directedGraph.addEdge("m_isolinescombobox", "m_dummylineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_layerscombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_powercombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_radiuslineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_sepxspinbox");
        directedGraph.addEdge("m_isolinescombobox", "m_sepyspinbox");
        directedGraph.addEdge("m_isolinescombobox", "m_minptsspinbox");
        directedGraph.addEdge("m_isolinescombobox", "m_tensionlineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_smothlineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_resxlineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_resylineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_dimclineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_dimllineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_okpushbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_vectorradiobutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_gridradiobutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_powercombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_radiuslineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_sepxspinbox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_sepyspinbox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_minptsspinbox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_tensionlineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_smothlineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_minptsmitlineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_resxlineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_resylineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_dimclineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_dimllineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_vectorradiobutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_samplescombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_dummycheckbox");
        directedGraph.addEdge("m_isolineszcombobox", "m_dummylineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_layerscombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_powercombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_radiuslineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_sepxspinbox");
        directedGraph.addEdge("m_isolineszcombobox", "m_sepyspinbox");
        directedGraph.addEdge("m_isolineszcombobox", "m_minptsspinbox");
        directedGraph.addEdge("m_isolineszcombobox", "m_tensionlineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_smothlineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_resxlineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_resylineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_dimclineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_dimllineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_samplescombobox", "m_vectorradiobutton");
        directedGraph.addEdge("m_samplescombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_samplescombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_samplescombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_samplescombobox", "m_samplescombobox");
        directedGraph.addEdge("m_samplescombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_samplescombobox", "m_dummycheckbox");
        directedGraph.addEdge("m_samplescombobox", "m_dummylineedit");
        directedGraph.addEdge("m_samplescombobox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_layerscombobox");
        directedGraph.addEdge("m_samplescombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_samplescombobox", "m_powercombobox");
        directedGraph.addEdge("m_samplescombobox", "m_radiuslineedit");
        directedGraph.addEdge("m_samplescombobox", "m_sepxspinbox");
        directedGraph.addEdge("m_samplescombobox", "m_sepyspinbox");
        directedGraph.addEdge("m_samplescombobox", "m_minptsspinbox");
        directedGraph.addEdge("m_samplescombobox", "m_tensionlineedit");
        directedGraph.addEdge("m_samplescombobox", "m_smothlineedit");
        directedGraph.addEdge("m_samplescombobox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_samplescombobox", "m_resxlineedit");
        directedGraph.addEdge("m_samplescombobox", "m_resylineedit");
        directedGraph.addEdge("m_samplescombobox", "m_dimclineedit");
        directedGraph.addEdge("m_samplescombobox", "m_dimllineedit");
        directedGraph.addEdge("m_samplescombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_samplescombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_samplescombobox", "m_okpushbutton");
        directedGraph.addEdge("m_samplescombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_vectorradiobutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_gridradiobutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_powercombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_radiuslineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_sepxspinbox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_sepyspinbox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_minptsspinbox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_tensionlineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_smothlineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_minptsmitlineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_resxlineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_resylineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_dimclineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_dimllineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_vectorradiobutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_samplescombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_dummycheckbox");
        directedGraph.addEdge("m_sampleszcombobox", "m_dummylineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_layerscombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_powercombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_radiuslineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_sepxspinbox");
        directedGraph.addEdge("m_sampleszcombobox", "m_sepyspinbox");
        directedGraph.addEdge("m_sampleszcombobox", "m_minptsspinbox");
        directedGraph.addEdge("m_sampleszcombobox", "m_tensionlineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_smothlineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_resxlineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_resylineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_dimclineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_dimllineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_vectorradiobutton");
        directedGraph.addEdge("m_dummycheckbox", "m_gridradiobutton");
        directedGraph.addEdge("m_dummycheckbox", "m_isolinescombobox");
        directedGraph.addEdge("m_dummycheckbox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_isolineszcombobox");
        directedGraph.addEdge("m_dummycheckbox", "m_samplescombobox");
        directedGraph.addEdge("m_dummycheckbox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_sampleszcombobox");
        directedGraph.addEdge("m_dummycheckbox", "m_dummycheckbox");
        directedGraph.addEdge("m_dummycheckbox", "m_dummylineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_dummycheckbox", "m_layerscombobox");
        directedGraph.addEdge("m_dummycheckbox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_dummycheckbox", "m_powercombobox");
        directedGraph.addEdge("m_dummycheckbox", "m_radiuslineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_sepxspinbox");
        directedGraph.addEdge("m_dummycheckbox", "m_sepyspinbox");
        directedGraph.addEdge("m_dummycheckbox", "m_minptsspinbox");
        directedGraph.addEdge("m_dummycheckbox", "m_tensionlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_smothlineedit");
        directedGraph.addEdge("m_dummycheckbox", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_dummylineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_dummylineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_dummylineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_dummylineedit", "m_samplescombobox");
        directedGraph.addEdge("m_dummylineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_dummylineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_dummylineedit", "m_dummylineedit");
        directedGraph.addEdge("m_dummylineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_dummylineedit", "m_layerscombobox");
        directedGraph.addEdge("m_dummylineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_dummylineedit", "m_powercombobox");
        directedGraph.addEdge("m_dummylineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_dummylineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_dummylineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_dummylineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_dummylineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_smothlineedit");
        directedGraph.addEdge("m_dummylineedit", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_layersearchtoolbutton", "m_vectorradiobutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_gridradiobutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_powercombobox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_radiuslineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_sepxspinbox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_sepyspinbox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_minptsspinbox");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_tensionlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_smothlineedit");
        directedGraph.addEdge("m_layersearchtoolbutton", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_layerscombobox", "m_vectorradiobutton");
        directedGraph.addEdge("m_layerscombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_layerscombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_layerscombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_layerscombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_layerscombobox", "m_samplescombobox");
        directedGraph.addEdge("m_layerscombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_layerscombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_layerscombobox", "m_dummycheckbox");
        directedGraph.addEdge("m_layerscombobox", "m_dummylineedit");
        directedGraph.addEdge("m_layerscombobox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_layerscombobox", "m_layerscombobox");
        directedGraph.addEdge("m_layerscombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_layerscombobox", "m_powercombobox");
        directedGraph.addEdge("m_layerscombobox", "m_radiuslineedit");
        directedGraph.addEdge("m_layerscombobox", "m_sepxspinbox");
        directedGraph.addEdge("m_layerscombobox", "m_sepyspinbox");
        directedGraph.addEdge("m_layerscombobox", "m_minptsspinbox");
        directedGraph.addEdge("m_layerscombobox", "m_tensionlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_smothlineedit");
        directedGraph.addEdge("m_layerscombobox", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_interpolatorcombobox", "m_vectorradiobutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_samplescombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_dummycheckbox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_dummylineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_layerscombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_powercombobox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_radiuslineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_sepxspinbox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_sepyspinbox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_minptsspinbox");
        directedGraph.addEdge("m_interpolatorcombobox", "m_tensionlineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_smothlineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_resxlineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_resylineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_dimclineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_dimllineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_interpolatorcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_interpolatorcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_powercombobox", "m_vectorradiobutton");
        directedGraph.addEdge("m_powercombobox", "m_gridradiobutton");
        directedGraph.addEdge("m_powercombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_powercombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_powercombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_powercombobox", "m_samplescombobox");
        directedGraph.addEdge("m_powercombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_powercombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_powercombobox", "m_dummycheckbox");
        directedGraph.addEdge("m_powercombobox", "m_dummylineedit");
        directedGraph.addEdge("m_powercombobox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_powercombobox", "m_layerscombobox");
        directedGraph.addEdge("m_powercombobox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_powercombobox", "m_powercombobox");
        directedGraph.addEdge("m_powercombobox", "m_radiuslineedit");
        directedGraph.addEdge("m_powercombobox", "m_sepxspinbox");
        directedGraph.addEdge("m_powercombobox", "m_sepyspinbox");
        directedGraph.addEdge("m_powercombobox", "m_minptsspinbox");
        directedGraph.addEdge("m_powercombobox", "m_tensionlineedit");
        directedGraph.addEdge("m_powercombobox", "m_smothlineedit");
        directedGraph.addEdge("m_powercombobox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_powercombobox", "m_resxlineedit");
        directedGraph.addEdge("m_powercombobox", "m_resylineedit");
        directedGraph.addEdge("m_powercombobox", "m_dimclineedit");
        directedGraph.addEdge("m_powercombobox", "m_dimllineedit");
        directedGraph.addEdge("m_powercombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_powercombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_powercombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_powercombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_powercombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_powercombobox", "m_okpushbutton");
        directedGraph.addEdge("m_powercombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_radiuslineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_radiuslineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_radiuslineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_radiuslineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_radiuslineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_radiuslineedit", "m_samplescombobox");
        directedGraph.addEdge("m_radiuslineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_radiuslineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_radiuslineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_radiuslineedit", "m_dummylineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_radiuslineedit", "m_layerscombobox");
        directedGraph.addEdge("m_radiuslineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_radiuslineedit", "m_powercombobox");
        directedGraph.addEdge("m_radiuslineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_radiuslineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_radiuslineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_radiuslineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_smothlineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_minptsmitlineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_resxlineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_resylineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_dimclineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_dimllineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_radiuslineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_radiuslineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_radiuslineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_radiuslineedit", "m_okpushbutton");
        directedGraph.addEdge("m_radiuslineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_sepxspinbox", "m_vectorradiobutton");
        directedGraph.addEdge("m_sepxspinbox", "m_gridradiobutton");
        directedGraph.addEdge("m_sepxspinbox", "m_isolinescombobox");
        directedGraph.addEdge("m_sepxspinbox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_sepxspinbox", "m_isolineszcombobox");
        directedGraph.addEdge("m_sepxspinbox", "m_samplescombobox");
        directedGraph.addEdge("m_sepxspinbox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_sepxspinbox", "m_sampleszcombobox");
        directedGraph.addEdge("m_sepxspinbox", "m_dummycheckbox");
        directedGraph.addEdge("m_sepxspinbox", "m_dummylineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_sepxspinbox", "m_layerscombobox");
        directedGraph.addEdge("m_sepxspinbox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_sepxspinbox", "m_powercombobox");
        directedGraph.addEdge("m_sepxspinbox", "m_radiuslineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_sepxspinbox");
        directedGraph.addEdge("m_sepxspinbox", "m_sepyspinbox");
        directedGraph.addEdge("m_sepxspinbox", "m_minptsspinbox");
        directedGraph.addEdge("m_sepxspinbox", "m_tensionlineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_smothlineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_resxlineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_resylineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_dimclineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_dimllineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_srstoolbutton");
        directedGraph.addEdge("m_sepxspinbox", "m_repositorylineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_sepxspinbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_sepxspinbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_sepxspinbox", "m_okpushbutton");
        directedGraph.addEdge("m_sepxspinbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_sepyspinbox", "m_vectorradiobutton");
        directedGraph.addEdge("m_sepyspinbox", "m_gridradiobutton");
        directedGraph.addEdge("m_sepyspinbox", "m_isolinescombobox");
        directedGraph.addEdge("m_sepyspinbox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_sepyspinbox", "m_isolineszcombobox");
        directedGraph.addEdge("m_sepyspinbox", "m_samplescombobox");
        directedGraph.addEdge("m_sepyspinbox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_sepyspinbox", "m_sampleszcombobox");
        directedGraph.addEdge("m_sepyspinbox", "m_dummycheckbox");
        directedGraph.addEdge("m_sepyspinbox", "m_dummylineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_sepyspinbox", "m_layerscombobox");
        directedGraph.addEdge("m_sepyspinbox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_sepyspinbox", "m_powercombobox");
        directedGraph.addEdge("m_sepyspinbox", "m_radiuslineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_sepxspinbox");
        directedGraph.addEdge("m_sepyspinbox", "m_sepyspinbox");
        directedGraph.addEdge("m_sepyspinbox", "m_minptsspinbox");
        directedGraph.addEdge("m_sepyspinbox", "m_tensionlineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_smothlineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_resxlineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_resylineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_dimclineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_dimllineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_srstoolbutton");
        directedGraph.addEdge("m_sepyspinbox", "m_repositorylineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_sepyspinbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_sepyspinbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_sepyspinbox", "m_okpushbutton");
        directedGraph.addEdge("m_sepyspinbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_minptsspinbox", "m_vectorradiobutton");
        directedGraph.addEdge("m_minptsspinbox", "m_gridradiobutton");
        directedGraph.addEdge("m_minptsspinbox", "m_isolinescombobox");
        directedGraph.addEdge("m_minptsspinbox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_minptsspinbox", "m_isolineszcombobox");
        directedGraph.addEdge("m_minptsspinbox", "m_samplescombobox");
        directedGraph.addEdge("m_minptsspinbox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_minptsspinbox", "m_sampleszcombobox");
        directedGraph.addEdge("m_minptsspinbox", "m_dummycheckbox");
        directedGraph.addEdge("m_minptsspinbox", "m_dummylineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_minptsspinbox", "m_layerscombobox");
        directedGraph.addEdge("m_minptsspinbox", "m_interpolatorcombobox");
        directedGraph.addEdge("m_minptsspinbox", "m_powercombobox");
        directedGraph.addEdge("m_minptsspinbox", "m_radiuslineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_sepxspinbox");
        directedGraph.addEdge("m_minptsspinbox", "m_sepyspinbox");
        directedGraph.addEdge("m_minptsspinbox", "m_minptsspinbox");
        directedGraph.addEdge("m_minptsspinbox", "m_tensionlineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_smothlineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_minptsmitlineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_resxlineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_resylineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_dimclineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_dimllineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_srstoolbutton");
        directedGraph.addEdge("m_minptsspinbox", "m_repositorylineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_minptsspinbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_minptsspinbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_minptsspinbox", "m_okpushbutton");
        directedGraph.addEdge("m_minptsspinbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_tensionlineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_tensionlineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_tensionlineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_tensionlineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_tensionlineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_tensionlineedit", "m_samplescombobox");
        directedGraph.addEdge("m_tensionlineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_tensionlineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_tensionlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_tensionlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_tensionlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_tensionlineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_tensionlineedit", "m_powercombobox");
        directedGraph.addEdge("m_tensionlineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_tensionlineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_tensionlineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_tensionlineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_smothlineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_minptsmitlineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_resylineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_dimllineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_tensionlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_tensionlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_tensionlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_tensionlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_tensionlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_smothlineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_smothlineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_smothlineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_smothlineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_smothlineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_smothlineedit", "m_samplescombobox");
        directedGraph.addEdge("m_smothlineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_smothlineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_smothlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_smothlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_smothlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_smothlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_smothlineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_smothlineedit", "m_powercombobox");
        directedGraph.addEdge("m_smothlineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_smothlineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_smothlineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_smothlineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_smothlineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_smothlineedit", "m_smothlineedit");
        directedGraph.addEdge("m_smothlineedit", "m_minptsmitlineedit");
        directedGraph.addEdge("m_smothlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_smothlineedit", "m_resylineedit");
        directedGraph.addEdge("m_smothlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_smothlineedit", "m_dimllineedit");
        directedGraph.addEdge("m_smothlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_smothlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_smothlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_smothlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_smothlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_smothlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_smothlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_samplescombobox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_powercombobox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_minptsmitlineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_smothlineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_minptsmitlineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_resxlineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_resylineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_dimclineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_dimllineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_minptsmitlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_minptsmitlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_resxlineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_resxlineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_resxlineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_resxlineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_resxlineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_resxlineedit", "m_samplescombobox");
        directedGraph.addEdge("m_resxlineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_resxlineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_resxlineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_resxlineedit", "m_dummylineedit");
        directedGraph.addEdge("m_resxlineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_resxlineedit", "m_layerscombobox");
        directedGraph.addEdge("m_resxlineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_resxlineedit", "m_powercombobox");
        directedGraph.addEdge("m_resxlineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_resxlineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_resxlineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_resxlineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_resxlineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_smothlineedit");
        directedGraph.addEdge("m_resxlineedit", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_resylineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_resylineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_resylineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_resylineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_resylineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_resylineedit", "m_samplescombobox");
        directedGraph.addEdge("m_resylineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_resylineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_resylineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_resylineedit", "m_dummylineedit");
        directedGraph.addEdge("m_resylineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_resylineedit", "m_layerscombobox");
        directedGraph.addEdge("m_resylineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_resylineedit", "m_powercombobox");
        directedGraph.addEdge("m_resylineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_resylineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_resylineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_resylineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_resylineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_resylineedit", "m_smothlineedit");
        directedGraph.addEdge("m_resylineedit", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_dimclineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_dimclineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_dimclineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_dimclineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_dimclineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_dimclineedit", "m_samplescombobox");
        directedGraph.addEdge("m_dimclineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_dimclineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_dimclineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_dimclineedit", "m_dummylineedit");
        directedGraph.addEdge("m_dimclineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_dimclineedit", "m_layerscombobox");
        directedGraph.addEdge("m_dimclineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_dimclineedit", "m_powercombobox");
        directedGraph.addEdge("m_dimclineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_dimclineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_dimclineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_dimclineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_dimclineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_smothlineedit");
        directedGraph.addEdge("m_dimclineedit", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_dimllineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_dimllineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_dimllineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_dimllineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_dimllineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_dimllineedit", "m_samplescombobox");
        directedGraph.addEdge("m_dimllineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_dimllineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_dimllineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_dimllineedit", "m_dummylineedit");
        directedGraph.addEdge("m_dimllineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_dimllineedit", "m_layerscombobox");
        directedGraph.addEdge("m_dimllineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_dimllineedit", "m_powercombobox");
        directedGraph.addEdge("m_dimllineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_dimllineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_dimllineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_dimllineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_dimllineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_smothlineedit");
        directedGraph.addEdge("m_dimllineedit", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_srstoolbutton", "m_vectorradiobutton");
        directedGraph.addEdge("m_srstoolbutton", "m_gridradiobutton");
        directedGraph.addEdge("m_srstoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_srstoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_powercombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_radiuslineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_sepxspinbox");
        directedGraph.addEdge("m_srstoolbutton", "m_sepyspinbox");
        directedGraph.addEdge("m_srstoolbutton", "m_minptsspinbox");
        directedGraph.addEdge("m_srstoolbutton", "m_tensionlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_smothlineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_repositorylineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_samplescombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_dummylineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_layerscombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_powercombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_repositorylineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_repositorylineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_repositorylineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_smothlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_targetfiletoolbutton", "m_vectorradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_gridradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_powercombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_radiuslineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_sepxspinbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_sepyspinbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_minptsspinbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_tensionlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_smothlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_vectorradiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_gridradiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_dummycheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_dummylineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_layerscombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_interpolatorcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_powercombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_radiuslineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_sepxspinbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_sepyspinbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_minptsspinbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_tensionlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_smothlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_minptsmitlineedit");
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
        directedGraph.addEdge("m_newlayernamelineedit", "m_vectorradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_gridradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_samplescombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_dummycheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_dummylineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_layersearchtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_layerscombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_interpolatorcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_powercombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_radiuslineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_sepxspinbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_sepyspinbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_minptsspinbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_tensionlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_smothlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_minptsmitlineedit");
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
