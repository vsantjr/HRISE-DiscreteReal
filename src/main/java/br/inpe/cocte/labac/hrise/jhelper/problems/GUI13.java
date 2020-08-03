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

public class GUI13 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI13(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI13");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
     // Initial Vertices (States)
        String[] initStates = {"m_inputlayercombobox", "m_attridcombobox", "m_gpmtoolbutton", "m_attrlinkcombobox", "m_gpmlineedit", "m_globalmoranindexcheckbox", "m_globalmoranindexvaluelineedit", "m_globalmoranindexpvaluelineedit", "m_globalevalnotradiobutton", "m_globaleval99radiobutton", "m_globaleval999radiobutton", "m_localgstatisticscheckbox", "m_locallocalmeancheckbox", "m_localmoranindex", "m_localevalnotradiobutton", "m_localeval99radiobutton", "m_localeval999radiobutton", "m_localeval9999radiobutton", "m_repositorylineedit", "m_targetfiletoolbutton", "m_targetdatasourcetoolbutton", "m_newlayernamelineedit"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_inputlayercombobox");
        directedGraph.addVertex("m_attridcombobox");
        directedGraph.addVertex("m_gpmtoolbutton");
        directedGraph.addVertex("m_attrlinkcombobox");
        directedGraph.addVertex("m_gpmlineedit");
        directedGraph.addVertex("m_globalmoranindexcheckbox");
        directedGraph.addVertex("m_globalmoranindexvaluelineedit");
        directedGraph.addVertex("m_globalmoranindexpvaluelineedit");
        directedGraph.addVertex("m_globalevalnotradiobutton");
        directedGraph.addVertex("m_globaleval99radiobutton");
        directedGraph.addVertex("m_globaleval999radiobutton");
        directedGraph.addVertex("m_localgstatisticscheckbox");
        directedGraph.addVertex("m_locallocalmeancheckbox");
        directedGraph.addVertex("m_localmoranindex");
        directedGraph.addVertex("m_localevalnotradiobutton");
        directedGraph.addVertex("m_localeval99radiobutton");
        directedGraph.addVertex("m_localeval999radiobutton");
        directedGraph.addVertex("m_localeval9999radiobutton");
        directedGraph.addVertex("m_repositorylineedit");
        directedGraph.addVertex("m_targetfiletoolbutton");
        directedGraph.addVertex("m_targetdatasourcetoolbutton");
        directedGraph.addVertex("m_newlayernamelineedit");
        directedGraph.addVertex("m_okpushbutton");
        directedGraph.addVertex("m_cancelpushbutton");
        // Create the Edges 
        directedGraph.addEdge("m_inputlayercombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_attridcombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_gpmlineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_localmoranindex");
        directedGraph.addEdge("m_inputlayercombobox", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_localeval99radiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_localeval999radiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_okpushbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_attridcombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_attridcombobox", "m_attridcombobox");
        directedGraph.addEdge("m_attridcombobox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_attridcombobox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_attridcombobox", "m_gpmlineedit");
        directedGraph.addEdge("m_attridcombobox", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_attridcombobox", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_attridcombobox", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_attridcombobox", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_attridcombobox", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_attridcombobox", "m_localmoranindex");
        directedGraph.addEdge("m_attridcombobox", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_localeval99radiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_localeval999radiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_attridcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_attridcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_attridcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_attridcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_attridcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_attridcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_attridcombobox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_gpmlineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_localmoranindex");
        directedGraph.addEdge("m_gpmtoolbutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_attridcombobox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_gpmlineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_localmoranindex");
        directedGraph.addEdge("m_attrlinkcombobox", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_localeval99radiobutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_localeval999radiobutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_gpmlineedit", "m_attridcombobox");
        directedGraph.addEdge("m_gpmlineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_gpmlineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_gpmlineedit", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_gpmlineedit", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_gpmlineedit", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_gpmlineedit", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_gpmlineedit", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_gpmlineedit", "m_localmoranindex");
        directedGraph.addEdge("m_gpmlineedit", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_gpmlineedit", "m_localeval99radiobutton");
        directedGraph.addEdge("m_gpmlineedit", "m_localeval999radiobutton");
        directedGraph.addEdge("m_gpmlineedit", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_gpmlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_attridcombobox");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_gpmlineedit");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_localmoranindex");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_localeval99radiobutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_localeval999radiobutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_globalmoranindexcheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_attridcombobox");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_localmoranindex");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_localeval99radiobutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_localeval999radiobutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_globalmoranindexvaluelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_attridcombobox");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_localmoranindex");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_localeval99radiobutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_localeval999radiobutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_okpushbutton");
        directedGraph.addEdge("m_globalmoranindexpvaluelineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_gpmlineedit");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_localmoranindex");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_globalevalnotradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_gpmlineedit");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_localmoranindex");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_globaleval99radiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_gpmlineedit");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_localmoranindex");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_globaleval999radiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_attridcombobox");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_gpmlineedit");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_localmoranindex");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_localeval99radiobutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_localeval999radiobutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_localgstatisticscheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_attridcombobox");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_gpmlineedit");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_localmoranindex");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_localeval99radiobutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_localeval999radiobutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_locallocalmeancheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_localmoranindex", "m_inputlayercombobox");
        directedGraph.addEdge("m_localmoranindex", "m_attridcombobox");
        directedGraph.addEdge("m_localmoranindex", "m_gpmtoolbutton");
        directedGraph.addEdge("m_localmoranindex", "m_attrlinkcombobox");
        directedGraph.addEdge("m_localmoranindex", "m_gpmlineedit");
        directedGraph.addEdge("m_localmoranindex", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_localmoranindex", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_localmoranindex", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_localmoranindex", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_localmoranindex", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_localmoranindex", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_localmoranindex", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_localmoranindex", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_localmoranindex", "m_localmoranindex");
        directedGraph.addEdge("m_localmoranindex", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_localmoranindex", "m_localeval99radiobutton");
        directedGraph.addEdge("m_localmoranindex", "m_localeval999radiobutton");
        directedGraph.addEdge("m_localmoranindex", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_localmoranindex", "m_repositorylineedit");
        directedGraph.addEdge("m_localmoranindex", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_localmoranindex", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_localmoranindex", "m_newlayernamelineedit");
        directedGraph.addEdge("m_localmoranindex", "m_okpushbutton");
        directedGraph.addEdge("m_localmoranindex", "m_cancelpushbutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_gpmlineedit");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_localmoranindex");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_localevalnotradiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_localeval99radiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_localeval99radiobutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_localeval99radiobutton", "m_gpmlineedit");
        directedGraph.addEdge("m_localeval99radiobutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_localeval99radiobutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_localeval99radiobutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_localeval99radiobutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_localeval99radiobutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_localeval99radiobutton", "m_localmoranindex");
        directedGraph.addEdge("m_localeval99radiobutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_localeval99radiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_localeval99radiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_localeval99radiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_localeval999radiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_localeval999radiobutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_localeval999radiobutton", "m_gpmlineedit");
        directedGraph.addEdge("m_localeval999radiobutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_localeval999radiobutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_localeval999radiobutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_localeval999radiobutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_localeval999radiobutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_localeval999radiobutton", "m_localmoranindex");
        directedGraph.addEdge("m_localeval999radiobutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_localeval999radiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_localeval999radiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_localeval999radiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_attridcombobox");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_gpmlineedit");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_localmoranindex");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_repositorylineedit");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_okpushbutton");
        directedGraph.addEdge("m_localeval9999radiobutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_attridcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_localmoranindex");
        directedGraph.addEdge("m_repositorylineedit", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_localeval99radiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_localeval999radiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_repositorylineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_okpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_attridcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_gpmlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_localmoranindex");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_attridcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_gpmlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_localmoranindex");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_localeval99radiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_localeval999radiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_localeval9999radiobutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_attridcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_globalmoranindexcheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_globalmoranindexvaluelineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_globalmoranindexpvaluelineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_globalevalnotradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_globaleval99radiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_globaleval999radiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_localgstatisticscheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_locallocalmeancheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_localmoranindex");
        directedGraph.addEdge("m_newlayernamelineedit", "m_localevalnotradiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_localeval99radiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_localeval999radiobutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_localeval9999radiobutton");
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
