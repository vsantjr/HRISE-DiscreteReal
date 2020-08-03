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

public class GUI15 extends AbstractIntegerProblem {

    private static final long serialVersionUID = -6291831458123882892L;

    private List<List<String>> simpleCircuits = new ArrayList<List<String>>();
    
    private Set<String> allEdgesSt = new LinkedHashSet<String>();
    
   
    /**
     * Constructor
     * @throws IOException 
     */
    public GUI15(int m) throws IOException {
        setNumberOfVariables(10); // Configuration More: 10 decision variables = simple circuits = test cases
        setNumberOfObjectives(m); 
        setNumberOfConstraints(0);
        setName("GUI15");
        List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
        List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

        
        /*
         * This parts handles the EFG that represents the GUI application 
         */
        
        System.out.println("#### PROBLEM INSTANCE (Inside): " + getName());
        
        // Initial Vertices (States)
        String[] initStates = {"m_filenamelineedit", "m_openfiledlgtoolbutton", "m_namelineedit", "m_forcerawrastercheckbox", "m_rowslineedit", "m_resolutionxlineedit", "m_upperleftxlineedit", "m_interleavecombobox", "m_columnslineedit", "m_resolutionylineedit", "m_upperleftylineedit", "m_bandslineedit", "m_sridlineedit", "m_opensriddlgtoolbutton", "m_datatypecombobox", "m_byteordercombobox", "m_extraparameterscheckbox"};
        // Instatiate the Graph
        Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Create the Vertices 
        directedGraph.addVertex("m_filenamelineedit");
        directedGraph.addVertex("m_openfiledlgtoolbutton");
        directedGraph.addVertex("m_namelineedit");
        directedGraph.addVertex("m_forcerawrastercheckbox");
        directedGraph.addVertex("m_rowslineedit");
        directedGraph.addVertex("m_resolutionxlineedit");
        directedGraph.addVertex("m_upperleftxlineedit");
        directedGraph.addVertex("m_interleavecombobox");
        directedGraph.addVertex("m_columnslineedit");
        directedGraph.addVertex("m_resolutionylineedit");
        directedGraph.addVertex("m_upperleftylineedit");
        directedGraph.addVertex("m_bandslineedit");
        directedGraph.addVertex("m_sridlineedit");
        directedGraph.addVertex("m_opensriddlgtoolbutton");
        directedGraph.addVertex("m_datatypecombobox");
        directedGraph.addVertex("m_byteordercombobox");
        directedGraph.addVertex("m_extraparameterscheckbox");
        // Create the Edges 
        directedGraph.addEdge("m_filenamelineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_filenamelineedit", "m_namelineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_filenamelineedit", "m_rowslineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_filenamelineedit", "m_columnslineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_bandslineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_sridlineedit");
        directedGraph.addEdge("m_filenamelineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_filenamelineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_filenamelineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_filenamelineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_filenamelineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_namelineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_rowslineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_resolutionxlineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_upperleftxlineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_interleavecombobox");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_columnslineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_resolutionylineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_upperleftylineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_bandslineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_sridlineedit");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_datatypecombobox");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_byteordercombobox");
        directedGraph.addEdge("m_openfiledlgtoolbutton", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_namelineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_namelineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_namelineedit", "m_namelineedit");
        directedGraph.addEdge("m_namelineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_namelineedit", "m_rowslineedit");
        directedGraph.addEdge("m_namelineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_namelineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_namelineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_namelineedit", "m_columnslineedit");
        directedGraph.addEdge("m_namelineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_namelineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_namelineedit", "m_bandslineedit");
        directedGraph.addEdge("m_namelineedit", "m_sridlineedit");
        directedGraph.addEdge("m_namelineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_namelineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_namelineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_namelineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_filenamelineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_namelineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_rowslineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_resolutionxlineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_upperleftxlineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_interleavecombobox");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_columnslineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_resolutionylineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_upperleftylineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_bandslineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_sridlineedit");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_datatypecombobox");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_byteordercombobox");
        directedGraph.addEdge("m_forcerawrastercheckbox", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_rowslineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_rowslineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_rowslineedit", "m_namelineedit");
        directedGraph.addEdge("m_rowslineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_rowslineedit", "m_rowslineedit");
        directedGraph.addEdge("m_rowslineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_rowslineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_rowslineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_rowslineedit", "m_columnslineedit");
        directedGraph.addEdge("m_rowslineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_rowslineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_rowslineedit", "m_bandslineedit");
        directedGraph.addEdge("m_rowslineedit", "m_sridlineedit");
        directedGraph.addEdge("m_rowslineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_rowslineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_rowslineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_rowslineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_resolutionxlineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_resolutionxlineedit", "m_namelineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_resolutionxlineedit", "m_rowslineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_resolutionxlineedit", "m_columnslineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_bandslineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_sridlineedit");
        directedGraph.addEdge("m_resolutionxlineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_resolutionxlineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_resolutionxlineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_resolutionxlineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_upperleftxlineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_upperleftxlineedit", "m_namelineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_upperleftxlineedit", "m_rowslineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_upperleftxlineedit", "m_columnslineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_bandslineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_sridlineedit");
        directedGraph.addEdge("m_upperleftxlineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_upperleftxlineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_upperleftxlineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_upperleftxlineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_interleavecombobox", "m_filenamelineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_interleavecombobox", "m_namelineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_interleavecombobox", "m_rowslineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_resolutionxlineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_upperleftxlineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_interleavecombobox");
        directedGraph.addEdge("m_interleavecombobox", "m_columnslineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_resolutionylineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_upperleftylineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_bandslineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_sridlineedit");
        directedGraph.addEdge("m_interleavecombobox", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_interleavecombobox", "m_datatypecombobox");
        directedGraph.addEdge("m_interleavecombobox", "m_byteordercombobox");
        directedGraph.addEdge("m_interleavecombobox", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_columnslineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_columnslineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_columnslineedit", "m_namelineedit");
        directedGraph.addEdge("m_columnslineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_columnslineedit", "m_rowslineedit");
        directedGraph.addEdge("m_columnslineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_columnslineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_columnslineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_columnslineedit", "m_columnslineedit");
        directedGraph.addEdge("m_columnslineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_columnslineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_columnslineedit", "m_bandslineedit");
        directedGraph.addEdge("m_columnslineedit", "m_sridlineedit");
        directedGraph.addEdge("m_columnslineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_columnslineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_columnslineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_columnslineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_resolutionylineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_resolutionylineedit", "m_namelineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_resolutionylineedit", "m_rowslineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_resolutionylineedit", "m_columnslineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_bandslineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_sridlineedit");
        directedGraph.addEdge("m_resolutionylineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_resolutionylineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_resolutionylineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_resolutionylineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_upperleftylineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_upperleftylineedit", "m_namelineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_upperleftylineedit", "m_rowslineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_upperleftylineedit", "m_columnslineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_bandslineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_sridlineedit");
        directedGraph.addEdge("m_upperleftylineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_upperleftylineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_upperleftylineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_upperleftylineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_bandslineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_bandslineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_bandslineedit", "m_namelineedit");
        directedGraph.addEdge("m_bandslineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_bandslineedit", "m_rowslineedit");
        directedGraph.addEdge("m_bandslineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_bandslineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_bandslineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_bandslineedit", "m_columnslineedit");
        directedGraph.addEdge("m_bandslineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_bandslineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_bandslineedit", "m_bandslineedit");
        directedGraph.addEdge("m_bandslineedit", "m_sridlineedit");
        directedGraph.addEdge("m_bandslineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_bandslineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_bandslineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_bandslineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_sridlineedit", "m_filenamelineedit");
        directedGraph.addEdge("m_sridlineedit", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_sridlineedit", "m_namelineedit");
        directedGraph.addEdge("m_sridlineedit", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_sridlineedit", "m_rowslineedit");
        directedGraph.addEdge("m_sridlineedit", "m_resolutionxlineedit");
        directedGraph.addEdge("m_sridlineedit", "m_upperleftxlineedit");
        directedGraph.addEdge("m_sridlineedit", "m_interleavecombobox");
        directedGraph.addEdge("m_sridlineedit", "m_columnslineedit");
        directedGraph.addEdge("m_sridlineedit", "m_resolutionylineedit");
        directedGraph.addEdge("m_sridlineedit", "m_upperleftylineedit");
        directedGraph.addEdge("m_sridlineedit", "m_bandslineedit");
        directedGraph.addEdge("m_sridlineedit", "m_sridlineedit");
        directedGraph.addEdge("m_sridlineedit", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_sridlineedit", "m_datatypecombobox");
        directedGraph.addEdge("m_sridlineedit", "m_byteordercombobox");
        directedGraph.addEdge("m_sridlineedit", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_filenamelineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_namelineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_rowslineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_resolutionxlineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_upperleftxlineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_interleavecombobox");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_columnslineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_resolutionylineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_upperleftylineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_bandslineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_sridlineedit");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_datatypecombobox");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_byteordercombobox");
        directedGraph.addEdge("m_opensriddlgtoolbutton", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_datatypecombobox", "m_filenamelineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_datatypecombobox", "m_namelineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_datatypecombobox", "m_rowslineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_resolutionxlineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_upperleftxlineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_interleavecombobox");
        directedGraph.addEdge("m_datatypecombobox", "m_columnslineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_resolutionylineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_upperleftylineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_bandslineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_sridlineedit");
        directedGraph.addEdge("m_datatypecombobox", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_datatypecombobox", "m_datatypecombobox");
        directedGraph.addEdge("m_datatypecombobox", "m_byteordercombobox");
        directedGraph.addEdge("m_datatypecombobox", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_byteordercombobox", "m_filenamelineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_byteordercombobox", "m_namelineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_byteordercombobox", "m_rowslineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_resolutionxlineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_upperleftxlineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_interleavecombobox");
        directedGraph.addEdge("m_byteordercombobox", "m_columnslineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_resolutionylineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_upperleftylineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_bandslineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_sridlineedit");
        directedGraph.addEdge("m_byteordercombobox", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_byteordercombobox", "m_datatypecombobox");
        directedGraph.addEdge("m_byteordercombobox", "m_byteordercombobox");
        directedGraph.addEdge("m_byteordercombobox", "m_extraparameterscheckbox");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_filenamelineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_openfiledlgtoolbutton");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_namelineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_forcerawrastercheckbox");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_rowslineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_resolutionxlineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_upperleftxlineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_interleavecombobox");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_columnslineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_resolutionylineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_upperleftylineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_bandslineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_sridlineedit");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_opensriddlgtoolbutton");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_datatypecombobox");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_byteordercombobox");
        directedGraph.addEdge("m_extraparameterscheckbox", "m_extraparameterscheckbox");

           
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
