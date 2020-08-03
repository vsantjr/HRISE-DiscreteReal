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

public class GUI22 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI22(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI22");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_isolinescombobox", "m_isolinessearchtoolbutton", "m_isolineszcombobox", "m_samplescombobox", "m_samplesearchtoolbutton", "m_sampleszcombobox", "m_scalelineedit", "m_scalepushbutton", "m_typecombobox", "m_minedgelineedit", "m_yesradiobutton", "m_noradiobutton", "m_breaklinecombobox", "m_breaklinesearchtoolbutton", "m_breaktollineedit", "m_tollineedit", "m_distancelineedit", "m_srstoolbutton", "m_repositorylineedit", "m_targetfiletoolbutton", "m_targetdatasourcetoolbutton", "m_newlayernamelineedit"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_isolinescombobox");
        directedGraph.addVertex("m_isolinessearchtoolbutton");
        directedGraph.addVertex("m_isolineszcombobox");
        directedGraph.addVertex("m_samplescombobox");
        directedGraph.addVertex("m_samplesearchtoolbutton");
        directedGraph.addVertex("m_sampleszcombobox");
        directedGraph.addVertex("m_scalelineedit");
        directedGraph.addVertex("m_scalepushbutton");
        directedGraph.addVertex("m_typecombobox");
        directedGraph.addVertex("m_minedgelineedit");
        directedGraph.addVertex("m_yesradiobutton");
        directedGraph.addVertex("m_noradiobutton");
        directedGraph.addVertex("m_breaklinecombobox");
        directedGraph.addVertex("m_breaklinesearchtoolbutton");
        directedGraph.addVertex("m_breaktollineedit");
        directedGraph.addVertex("m_tollineedit");
        directedGraph.addVertex("m_distancelineedit");
        directedGraph.addVertex("m_srstoolbutton");
        directedGraph.addVertex("m_repositorylineedit");
        directedGraph.addVertex("m_targetfiletoolbutton");
        directedGraph.addVertex("m_targetdatasourcetoolbutton");
        directedGraph.addVertex("m_newlayernamelineedit");
        directedGraph.addVertex("m_okpushbutton");
        directedGraph.addVertex("m_cancelpushbutton");
        // Create the Edges 
        directedGraph.addEdge("m_isolinescombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_samplescombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_scalelineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_scalepushbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_typecombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_minedgelineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_yesradiobutton");
        directedGraph.addEdge("m_isolinescombobox", "m_noradiobutton");
        directedGraph.addEdge("m_isolinescombobox", "m_breaklinecombobox");
        directedGraph.addEdge("m_isolinescombobox", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_breaktollineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_tollineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_distancelineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_isolinescombobox", "m_okpushbutton");
        directedGraph.addEdge("m_isolinescombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_scalelineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_scalepushbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_minedgelineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_yesradiobutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_noradiobutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_breaktollineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_tollineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_distancelineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_isolinessearchtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_samplescombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_scalelineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_scalepushbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_typecombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_minedgelineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_yesradiobutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_noradiobutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_breaklinecombobox");
        directedGraph.addEdge("m_isolineszcombobox", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_breaktollineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_tollineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_distancelineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_isolineszcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_isolineszcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_samplescombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_samplescombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_samplescombobox", "m_samplescombobox");
        directedGraph.addEdge("m_samplescombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_samplescombobox", "m_scalelineedit");
        directedGraph.addEdge("m_samplescombobox", "m_scalepushbutton");
        directedGraph.addEdge("m_samplescombobox", "m_typecombobox");
        directedGraph.addEdge("m_samplescombobox", "m_minedgelineedit");
        directedGraph.addEdge("m_samplescombobox", "m_yesradiobutton");
        directedGraph.addEdge("m_samplescombobox", "m_noradiobutton");
        directedGraph.addEdge("m_samplescombobox", "m_breaklinecombobox");
        directedGraph.addEdge("m_samplescombobox", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_breaktollineedit");
        directedGraph.addEdge("m_samplescombobox", "m_tollineedit");
        directedGraph.addEdge("m_samplescombobox", "m_distancelineedit");
        directedGraph.addEdge("m_samplescombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_samplescombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_samplescombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_samplescombobox", "m_okpushbutton");
        directedGraph.addEdge("m_samplescombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_scalelineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_scalepushbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_minedgelineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_yesradiobutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_noradiobutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_breaktollineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_tollineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_distancelineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_samplesearchtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_samplescombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_scalelineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_scalepushbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_typecombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_minedgelineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_yesradiobutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_noradiobutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_breaklinecombobox");
        directedGraph.addEdge("m_sampleszcombobox", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_breaktollineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_tollineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_distancelineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_sampleszcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_sampleszcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_scalelineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_scalelineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_scalelineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_scalelineedit", "m_samplescombobox");
        directedGraph.addEdge("m_scalelineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_scalelineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_scalelineedit", "m_scalelineedit");
        directedGraph.addEdge("m_scalelineedit", "m_scalepushbutton");
        directedGraph.addEdge("m_scalelineedit", "m_typecombobox");
        directedGraph.addEdge("m_scalelineedit", "m_minedgelineedit");
        directedGraph.addEdge("m_scalelineedit", "m_yesradiobutton");
        directedGraph.addEdge("m_scalelineedit", "m_noradiobutton");
        directedGraph.addEdge("m_scalelineedit", "m_breaklinecombobox");
        directedGraph.addEdge("m_scalelineedit", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_scalelineedit", "m_breaktollineedit");
        directedGraph.addEdge("m_scalelineedit", "m_tollineedit");
        directedGraph.addEdge("m_scalelineedit", "m_distancelineedit");
        directedGraph.addEdge("m_scalelineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_scalelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_scalelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_scalelineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_scalelineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_scalelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_scalelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_scalepushbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_scalepushbutton", "m_samplescombobox");
        directedGraph.addEdge("m_scalepushbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_scalepushbutton", "m_scalelineedit");
        directedGraph.addEdge("m_scalepushbutton", "m_scalepushbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_typecombobox");
        directedGraph.addEdge("m_scalepushbutton", "m_minedgelineedit");
        directedGraph.addEdge("m_scalepushbutton", "m_yesradiobutton");
        directedGraph.addEdge("m_scalepushbutton", "m_noradiobutton");
        directedGraph.addEdge("m_scalepushbutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_scalepushbutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_breaktollineedit");
        directedGraph.addEdge("m_scalepushbutton", "m_tollineedit");
        directedGraph.addEdge("m_scalepushbutton", "m_distancelineedit");
        directedGraph.addEdge("m_scalepushbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_scalepushbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_scalepushbutton", "m_okpushbutton");
        directedGraph.addEdge("m_scalepushbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_typecombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_typecombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_typecombobox", "m_samplescombobox");
        directedGraph.addEdge("m_typecombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_typecombobox", "m_scalelineedit");
        directedGraph.addEdge("m_typecombobox", "m_scalepushbutton");
        directedGraph.addEdge("m_typecombobox", "m_typecombobox");
        directedGraph.addEdge("m_typecombobox", "m_minedgelineedit");
        directedGraph.addEdge("m_typecombobox", "m_yesradiobutton");
        directedGraph.addEdge("m_typecombobox", "m_noradiobutton");
        directedGraph.addEdge("m_typecombobox", "m_breaklinecombobox");
        directedGraph.addEdge("m_typecombobox", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_breaktollineedit");
        directedGraph.addEdge("m_typecombobox", "m_tollineedit");
        directedGraph.addEdge("m_typecombobox", "m_distancelineedit");
        directedGraph.addEdge("m_typecombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_typecombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_typecombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_typecombobox", "m_okpushbutton");
        directedGraph.addEdge("m_typecombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_minedgelineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_minedgelineedit", "m_samplescombobox");
        directedGraph.addEdge("m_minedgelineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_minedgelineedit", "m_scalelineedit");
        directedGraph.addEdge("m_minedgelineedit", "m_scalepushbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_typecombobox");
        directedGraph.addEdge("m_minedgelineedit", "m_minedgelineedit");
        directedGraph.addEdge("m_minedgelineedit", "m_yesradiobutton");
        directedGraph.addEdge("m_minedgelineedit", "m_noradiobutton");
        directedGraph.addEdge("m_minedgelineedit", "m_breaklinecombobox");
        directedGraph.addEdge("m_minedgelineedit", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_breaktollineedit");
        directedGraph.addEdge("m_minedgelineedit", "m_tollineedit");
        directedGraph.addEdge("m_minedgelineedit", "m_distancelineedit");
        directedGraph.addEdge("m_minedgelineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_minedgelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_minedgelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_minedgelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_isolinescombobox");
        directedGraph.addEdge("m_yesradiobutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_yesradiobutton", "m_samplescombobox");
        directedGraph.addEdge("m_yesradiobutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_yesradiobutton", "m_scalelineedit");
        directedGraph.addEdge("m_yesradiobutton", "m_scalepushbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_typecombobox");
        directedGraph.addEdge("m_yesradiobutton", "m_minedgelineedit");
        directedGraph.addEdge("m_yesradiobutton", "m_yesradiobutton");
        directedGraph.addEdge("m_yesradiobutton", "m_noradiobutton");
        directedGraph.addEdge("m_yesradiobutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_yesradiobutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_breaktollineedit");
        directedGraph.addEdge("m_yesradiobutton", "m_tollineedit");
        directedGraph.addEdge("m_yesradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_yesradiobutton", "m_srstoolbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_yesradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_yesradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_yesradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_noradiobutton", "m_isolinescombobox");
        directedGraph.addEdge("m_noradiobutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_noradiobutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_noradiobutton", "m_samplescombobox");
        directedGraph.addEdge("m_noradiobutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_noradiobutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_noradiobutton", "m_scalelineedit");
        directedGraph.addEdge("m_noradiobutton", "m_scalepushbutton");
        directedGraph.addEdge("m_noradiobutton", "m_typecombobox");
        directedGraph.addEdge("m_noradiobutton", "m_minedgelineedit");
        directedGraph.addEdge("m_noradiobutton", "m_yesradiobutton");
        directedGraph.addEdge("m_noradiobutton", "m_noradiobutton");
        directedGraph.addEdge("m_noradiobutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_noradiobutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_noradiobutton", "m_breaktollineedit");
        directedGraph.addEdge("m_noradiobutton", "m_tollineedit");
        directedGraph.addEdge("m_noradiobutton", "m_distancelineedit");
        directedGraph.addEdge("m_noradiobutton", "m_srstoolbutton");
        directedGraph.addEdge("m_noradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_noradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_noradiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_noradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_noradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_noradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_isolinescombobox");
        directedGraph.addEdge("m_breaklinecombobox", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_isolineszcombobox");
        directedGraph.addEdge("m_breaklinecombobox", "m_samplescombobox");
        directedGraph.addEdge("m_breaklinecombobox", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_sampleszcombobox");
        directedGraph.addEdge("m_breaklinecombobox", "m_scalelineedit");
        directedGraph.addEdge("m_breaklinecombobox", "m_scalepushbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_typecombobox");
        directedGraph.addEdge("m_breaklinecombobox", "m_minedgelineedit");
        directedGraph.addEdge("m_breaklinecombobox", "m_yesradiobutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_noradiobutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_breaklinecombobox");
        directedGraph.addEdge("m_breaklinecombobox", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_breaktollineedit");
        directedGraph.addEdge("m_breaklinecombobox", "m_tollineedit");
        directedGraph.addEdge("m_breaklinecombobox", "m_distancelineedit");
        directedGraph.addEdge("m_breaklinecombobox", "m_srstoolbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_breaklinecombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_breaklinecombobox", "m_okpushbutton");
        directedGraph.addEdge("m_breaklinecombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_scalelineedit");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_scalepushbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_minedgelineedit");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_yesradiobutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_noradiobutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_breaktollineedit");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_tollineedit");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_distancelineedit");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_breaklinesearchtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_breaktollineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_breaktollineedit", "m_samplescombobox");
        directedGraph.addEdge("m_breaktollineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_breaktollineedit", "m_scalelineedit");
        directedGraph.addEdge("m_breaktollineedit", "m_scalepushbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_typecombobox");
        directedGraph.addEdge("m_breaktollineedit", "m_minedgelineedit");
        directedGraph.addEdge("m_breaktollineedit", "m_yesradiobutton");
        directedGraph.addEdge("m_breaktollineedit", "m_noradiobutton");
        directedGraph.addEdge("m_breaktollineedit", "m_breaklinecombobox");
        directedGraph.addEdge("m_breaktollineedit", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_breaktollineedit");
        directedGraph.addEdge("m_breaktollineedit", "m_tollineedit");
        directedGraph.addEdge("m_breaktollineedit", "m_distancelineedit");
        directedGraph.addEdge("m_breaktollineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_breaktollineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_breaktollineedit", "m_okpushbutton");
        directedGraph.addEdge("m_breaktollineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_tollineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_tollineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_tollineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_tollineedit", "m_samplescombobox");
        directedGraph.addEdge("m_tollineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_tollineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_tollineedit", "m_scalelineedit");
        directedGraph.addEdge("m_tollineedit", "m_scalepushbutton");
        directedGraph.addEdge("m_tollineedit", "m_typecombobox");
        directedGraph.addEdge("m_tollineedit", "m_minedgelineedit");
        directedGraph.addEdge("m_tollineedit", "m_yesradiobutton");
        directedGraph.addEdge("m_tollineedit", "m_noradiobutton");
        directedGraph.addEdge("m_tollineedit", "m_breaklinecombobox");
        directedGraph.addEdge("m_tollineedit", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_tollineedit", "m_breaktollineedit");
        directedGraph.addEdge("m_tollineedit", "m_tollineedit");
        directedGraph.addEdge("m_tollineedit", "m_distancelineedit");
        directedGraph.addEdge("m_tollineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_tollineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_tollineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_tollineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_tollineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_tollineedit", "m_okpushbutton");
        directedGraph.addEdge("m_tollineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_distancelineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_distancelineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_distancelineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_distancelineedit", "m_samplescombobox");
        directedGraph.addEdge("m_distancelineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_distancelineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_distancelineedit", "m_scalelineedit");
        directedGraph.addEdge("m_distancelineedit", "m_scalepushbutton");
        directedGraph.addEdge("m_distancelineedit", "m_typecombobox");
        directedGraph.addEdge("m_distancelineedit", "m_minedgelineedit");
        directedGraph.addEdge("m_distancelineedit", "m_yesradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_noradiobutton");
        directedGraph.addEdge("m_distancelineedit", "m_breaklinecombobox");
        directedGraph.addEdge("m_distancelineedit", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_distancelineedit", "m_breaktollineedit");
        directedGraph.addEdge("m_distancelineedit", "m_tollineedit");
        directedGraph.addEdge("m_distancelineedit", "m_distancelineedit");
        directedGraph.addEdge("m_distancelineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_distancelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_distancelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_distancelineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_distancelineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_distancelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_distancelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_scalelineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_scalepushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_minedgelineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_yesradiobutton");
        directedGraph.addEdge("m_srstoolbutton", "m_noradiobutton");
        directedGraph.addEdge("m_srstoolbutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_srstoolbutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_breaktollineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_tollineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_distancelineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_srstoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_srstoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_samplescombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_scalelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_scalepushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_typecombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_minedgelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_yesradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_noradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_breaklinecombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_breaktollineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_tollineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_distancelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_srstoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_okpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_scalelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_scalepushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_minedgelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_yesradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_noradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_breaktollineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_tollineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_distancelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_isolinescombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_isolineszcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_samplescombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_sampleszcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_scalelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_scalepushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_typecombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_minedgelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_yesradiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_noradiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_breaklinecombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_breaktollineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_tollineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_distancelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_srstoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_isolinescombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_isolinessearchtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_isolineszcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_samplescombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_samplesearchtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_sampleszcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_scalelineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_scalepushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_typecombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_minedgelineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_yesradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_noradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_breaklinecombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_breaklinesearchtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_breaktollineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_tollineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_distancelineedit");
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
