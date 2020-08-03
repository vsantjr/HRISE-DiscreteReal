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

public class GUI12 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI12(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI12");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_inputlayercombobox", "m_gpmtoolbutton", "m_attrlinkcombobox", "m_gpmlineedit", "m_clusterscheckbox", "m_nclusterslineedit", "m_popcheckbox", "m_minpoplineedit", "m_popcombobox", "m_groupcheckbox", "m_attrgroupcombobox", "m_repositorylineedit", "m_targetfiletoolbutton", "m_targetdatasourcetoolbutton", "m_newlayernamelineedit"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_inputlayercombobox");
        directedGraph.addVertex("m_gpmtoolbutton");
        directedGraph.addVertex("m_attrlinkcombobox");
        directedGraph.addVertex("m_gpmlineedit");
        directedGraph.addVertex("m_clusterscheckbox");
        directedGraph.addVertex("m_nclusterslineedit");
        directedGraph.addVertex("m_popcheckbox");
        directedGraph.addVertex("m_minpoplineedit");
        directedGraph.addVertex("m_popcombobox");
        directedGraph.addVertex("m_groupcheckbox");
        directedGraph.addVertex("m_attrgroupcombobox");
        directedGraph.addVertex("m_repositorylineedit");
        directedGraph.addVertex("m_targetfiletoolbutton");
        directedGraph.addVertex("m_targetdatasourcetoolbutton");
        directedGraph.addVertex("m_newlayernamelineedit");
        directedGraph.addVertex("m_okpushbutton");
        directedGraph.addVertex("m_cancelpushbutton");
        // Create the Edges 
        directedGraph.addEdge("m_inputlayercombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_gpmlineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_clusterscheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_nclusterslineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_popcheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_minpoplineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_popcombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_groupcheckbox");
        directedGraph.addEdge("m_inputlayercombobox", "m_attrgroupcombobox");
        directedGraph.addEdge("m_inputlayercombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_inputlayercombobox", "m_okpushbutton");
        directedGraph.addEdge("m_inputlayercombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_gpmlineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_clusterscheckbox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_nclusterslineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_popcheckbox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_minpoplineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_popcombobox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_groupcheckbox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_attrgroupcombobox");
        directedGraph.addEdge("m_gpmtoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_gpmtoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_gpmtoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_gpmlineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_clusterscheckbox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_nclusterslineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_popcheckbox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_minpoplineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_popcombobox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_groupcheckbox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_attrgroupcombobox");
        directedGraph.addEdge("m_attrlinkcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_attrlinkcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_attrlinkcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_gpmlineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_gpmlineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_clusterscheckbox");
        directedGraph.addEdge("m_gpmlineedit", "m_nclusterslineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_popcheckbox");
        directedGraph.addEdge("m_gpmlineedit", "m_minpoplineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_popcombobox");
        directedGraph.addEdge("m_gpmlineedit", "m_groupcheckbox");
        directedGraph.addEdge("m_gpmlineedit", "m_attrgroupcombobox");
        directedGraph.addEdge("m_gpmlineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_gpmlineedit", "m_okpushbutton");
        directedGraph.addEdge("m_gpmlineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_clusterscheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_clusterscheckbox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_clusterscheckbox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_clusterscheckbox", "m_gpmlineedit");
        directedGraph.addEdge("m_clusterscheckbox", "m_clusterscheckbox");
        directedGraph.addEdge("m_clusterscheckbox", "m_nclusterslineedit");
        directedGraph.addEdge("m_clusterscheckbox", "m_popcheckbox");
        directedGraph.addEdge("m_clusterscheckbox", "m_minpoplineedit");
        directedGraph.addEdge("m_clusterscheckbox", "m_popcombobox");
        directedGraph.addEdge("m_clusterscheckbox", "m_groupcheckbox");
        directedGraph.addEdge("m_clusterscheckbox", "m_attrgroupcombobox");
        directedGraph.addEdge("m_clusterscheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_clusterscheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_clusterscheckbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_clusterscheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_clusterscheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_clusterscheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_nclusterslineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_nclusterslineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_nclusterslineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_nclusterslineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_nclusterslineedit", "m_clusterscheckbox");
        directedGraph.addEdge("m_nclusterslineedit", "m_nclusterslineedit");
        directedGraph.addEdge("m_nclusterslineedit", "m_popcheckbox");
        directedGraph.addEdge("m_nclusterslineedit", "m_minpoplineedit");
        directedGraph.addEdge("m_nclusterslineedit", "m_popcombobox");
        directedGraph.addEdge("m_nclusterslineedit", "m_groupcheckbox");
        directedGraph.addEdge("m_nclusterslineedit", "m_attrgroupcombobox");
        directedGraph.addEdge("m_nclusterslineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_nclusterslineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_nclusterslineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_nclusterslineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_nclusterslineedit", "m_okpushbutton");
        directedGraph.addEdge("m_nclusterslineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_popcheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_popcheckbox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_popcheckbox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_popcheckbox", "m_gpmlineedit");
        directedGraph.addEdge("m_popcheckbox", "m_clusterscheckbox");
        directedGraph.addEdge("m_popcheckbox", "m_nclusterslineedit");
        directedGraph.addEdge("m_popcheckbox", "m_popcheckbox");
        directedGraph.addEdge("m_popcheckbox", "m_minpoplineedit");
        directedGraph.addEdge("m_popcheckbox", "m_popcombobox");
        directedGraph.addEdge("m_popcheckbox", "m_groupcheckbox");
        directedGraph.addEdge("m_popcheckbox", "m_attrgroupcombobox");
        directedGraph.addEdge("m_popcheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_popcheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_popcheckbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_popcheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_popcheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_popcheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_minpoplineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_minpoplineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_minpoplineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_minpoplineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_minpoplineedit", "m_clusterscheckbox");
        directedGraph.addEdge("m_minpoplineedit", "m_nclusterslineedit");
        directedGraph.addEdge("m_minpoplineedit", "m_popcheckbox");
        directedGraph.addEdge("m_minpoplineedit", "m_minpoplineedit");
        directedGraph.addEdge("m_minpoplineedit", "m_popcombobox");
        directedGraph.addEdge("m_minpoplineedit", "m_groupcheckbox");
        directedGraph.addEdge("m_minpoplineedit", "m_attrgroupcombobox");
        directedGraph.addEdge("m_minpoplineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_minpoplineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_minpoplineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_minpoplineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_minpoplineedit", "m_okpushbutton");
        directedGraph.addEdge("m_minpoplineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_popcombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_popcombobox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_popcombobox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_popcombobox", "m_gpmlineedit");
        directedGraph.addEdge("m_popcombobox", "m_clusterscheckbox");
        directedGraph.addEdge("m_popcombobox", "m_nclusterslineedit");
        directedGraph.addEdge("m_popcombobox", "m_popcheckbox");
        directedGraph.addEdge("m_popcombobox", "m_minpoplineedit");
        directedGraph.addEdge("m_popcombobox", "m_popcombobox");
        directedGraph.addEdge("m_popcombobox", "m_groupcheckbox");
        directedGraph.addEdge("m_popcombobox", "m_attrgroupcombobox");
        directedGraph.addEdge("m_popcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_popcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_popcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_popcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_popcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_popcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_groupcheckbox", "m_inputlayercombobox");
        directedGraph.addEdge("m_groupcheckbox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_groupcheckbox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_groupcheckbox", "m_gpmlineedit");
        directedGraph.addEdge("m_groupcheckbox", "m_clusterscheckbox");
        directedGraph.addEdge("m_groupcheckbox", "m_nclusterslineedit");
        directedGraph.addEdge("m_groupcheckbox", "m_popcheckbox");
        directedGraph.addEdge("m_groupcheckbox", "m_minpoplineedit");
        directedGraph.addEdge("m_groupcheckbox", "m_popcombobox");
        directedGraph.addEdge("m_groupcheckbox", "m_groupcheckbox");
        directedGraph.addEdge("m_groupcheckbox", "m_attrgroupcombobox");
        directedGraph.addEdge("m_groupcheckbox", "m_repositorylineedit");
        directedGraph.addEdge("m_groupcheckbox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_groupcheckbox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_groupcheckbox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_groupcheckbox", "m_okpushbutton");
        directedGraph.addEdge("m_groupcheckbox", "m_cancelpushbutton");
        directedGraph.addEdge("m_attrgroupcombobox", "m_inputlayercombobox");
        directedGraph.addEdge("m_attrgroupcombobox", "m_gpmtoolbutton");
        directedGraph.addEdge("m_attrgroupcombobox", "m_attrlinkcombobox");
        directedGraph.addEdge("m_attrgroupcombobox", "m_gpmlineedit");
        directedGraph.addEdge("m_attrgroupcombobox", "m_clusterscheckbox");
        directedGraph.addEdge("m_attrgroupcombobox", "m_nclusterslineedit");
        directedGraph.addEdge("m_attrgroupcombobox", "m_popcheckbox");
        directedGraph.addEdge("m_attrgroupcombobox", "m_minpoplineedit");
        directedGraph.addEdge("m_attrgroupcombobox", "m_popcombobox");
        directedGraph.addEdge("m_attrgroupcombobox", "m_groupcheckbox");
        directedGraph.addEdge("m_attrgroupcombobox", "m_attrgroupcombobox");
        directedGraph.addEdge("m_attrgroupcombobox", "m_repositorylineedit");
        directedGraph.addEdge("m_attrgroupcombobox", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_attrgroupcombobox", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_attrgroupcombobox", "m_newlayernamelineedit");
        directedGraph.addEdge("m_attrgroupcombobox", "m_okpushbutton");
        directedGraph.addEdge("m_attrgroupcombobox", "m_cancelpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_clusterscheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_nclusterslineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_popcheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_minpoplineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_popcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_groupcheckbox");
        directedGraph.addEdge("m_repositorylineedit", "m_attrgroupcombobox");
        directedGraph.addEdge("m_repositorylineedit", "m_repositorylineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_newlayernamelineedit");
        directedGraph.addEdge("m_repositorylineedit", "m_okpushbutton");
        directedGraph.addEdge("m_repositorylineedit", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_gpmlineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_clusterscheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_nclusterslineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_popcheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_minpoplineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_popcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_groupcheckbox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_attrgroupcombobox");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetfiletoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_inputlayercombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_gpmtoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_attrlinkcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_gpmlineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_clusterscheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_nclusterslineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_popcheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_minpoplineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_popcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_groupcheckbox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_attrgroupcombobox");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_repositorylineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_targetfiletoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_targetdatasourcetoolbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_newlayernamelineedit");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_okpushbutton");
        directedGraph.addEdge("m_targetdatasourcetoolbutton", "m_cancelpushbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_inputlayercombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_gpmtoolbutton");
        directedGraph.addEdge("m_newlayernamelineedit", "m_attrlinkcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_gpmlineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_clusterscheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_nclusterslineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_popcheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_minpoplineedit");
        directedGraph.addEdge("m_newlayernamelineedit", "m_popcombobox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_groupcheckbox");
        directedGraph.addEdge("m_newlayernamelineedit", "m_attrgroupcombobox");
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
