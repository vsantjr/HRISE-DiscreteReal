/*
 * (C) Copyright 2014-2018, by Luiz Kill and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * See the CONTRIBUTORS.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the
 * GNU Lesser General Public License v2.1 or later
 * which is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR LGPL-2.1-or-later
 */
//package org.jgrapht.alg.cycle;
package br.inpe.cocte.labac.hrise.graph;

import org.jgrapht.*;

import java.util.*;

/**
 * Find all simple cycles of a directed graph using the algorithm described by Hawick and James.
 *
 * <p>
 * See:<br>
 * K. A. Hawick, H. A. James. Enumerating Circuits and Loops in Graphs with Self-Arcs and
 * Multiple-Arcs. Computational Science Technical Note CSTN-013, 2008
 *
 * @param <V> the vertex type.
 * @param <E> the edge type.
 *
 * @author Luiz Kill
 */
public class HawickJamesSimpleCycles<V, E>
    implements
    DirectedSimpleCycles<V, E>
{
    private enum Operation
    {
        ENUMERATE,
        PRINT_ONLY,
        COUNT_ONLY
    }

    // The graph
    private Graph<V, E> graph;

    // Number of vertices
    private int nVertices = 0;

    // Number of simple cycles
    private long nCycles = 0;

    // Simple cycles found
    private List<List<V>> cycles = null;

    // The main state of the algorithm
    private Integer start = 0;
    
    // Ak and B are arrays of Lists
    private List<Integer>[] Ak = null;
    private List<Integer>[] B = null;
   
    // blocked array of boolean
    private boolean[] blocked = null;
    private ArrayDeque<Integer> stack = null;

    // Giving an index to every V
    private V[] iToV = null;
    private Map<V, Integer> vToI = null;

    
    private Set<String> termVertices = null;
    
    private int maxSimpleCircuits = 0;
    
    private String width = null;
    
    /**
     * Create a simple cycle finder with an unspecified graph.
     */
    public HawickJamesSimpleCycles()
    {
    }

    /**
     * Create a simple cycle finder for the specified graph.
     *
     * @param graph the DirectedGraph in which to find cycles.
     *
     * @throws IllegalArgumentException if the graph argument is <code>
     * null</code>.
     */
    public HawickJamesSimpleCycles(Graph<V, E> graph)
        throws IllegalArgumentException
    {
        this.graph = GraphTests.requireDirected(graph, "Graph must be directed");
        System.out.println("My HJ Simple Circuit/Cycle");
    }
    
    
    public HawickJamesSimpleCycles(Graph<V, E> graph, Set<String> ts)
            throws IllegalArgumentException
        {
            this.graph = GraphTests.requireDirected(graph, "Graph must be directed");
            this.termVertices = new LinkedHashSet<String>();
            this.termVertices.addAll(ts);
            //for (String s: termVertices) {
            	//System.out.println("s:" +s);
            //}
            System.out.println("TSTSTS My HJ Simple Circuit/Cycle");
        }
    
    
    public HawickJamesSimpleCycles(Graph<V, E> graph, Set<String> ts, int sc, String w)
            throws IllegalArgumentException
        {
            this.graph = GraphTests.requireDirected(graph, "Graph must be directed");
            this.termVertices = new LinkedHashSet<String>();
            this.termVertices.addAll(ts);
            this.maxSimpleCircuits = sc;
            this.width = w;
            //for (String s: termVertices) {
            System.out.println("#### Maximum of simple circuits: " + maxSimpleCircuits);
            //}
            System.out.println("#### Constraint: " + this.width);
        }

    
    //public HawickJamesSimpleCycles(Graph<V, E> graph, Set<String> ts, int sc)
      //      throws IllegalArgumentException
        //{
          //  this.graph = GraphTests.requireDirected(graph, "Graph must be directed");
            //this.termVertices = new LinkedHashSet<String>();
            //this.termVertices.addAll(ts);
            //this.maxSimpleCircuits = sc;
            //for (String s: termVertices) {
            	//System.out.println("simple circuits MAX: " + maxSimpleCircuits);
            //}
            //System.out.println("HRISE -- HawJam SC !!!!!!!!!!!!!!!!!!!!!");
        //}
    
    
    @SuppressWarnings("unchecked")
    private void initState(Operation o)
    {
        nCycles = 0;
        nVertices = graph.vertexSet().size();
        if (o == Operation.ENUMERATE) {
            cycles = new ArrayList<>();
        }
        blocked = new boolean[nVertices];
        stack = new ArrayDeque<>(nVertices);

        B = new ArrayList[nVertices];
        for (int i = 0; i < nVertices; i++) {
            // B[i] = new ArrayList<Integer>(nVertices);
            B[i] = new ArrayList<>();
        }

        iToV = (V[]) graph.vertexSet().toArray();
        vToI = new HashMap<>();
        for (int i = 0; i < iToV.length; i++) {
            vToI.put(iToV[i], i);
        }

        Ak = buildAdjacencyList();

        stack.clear();
    }

    @SuppressWarnings("unchecked")
    private List<Integer>[] buildAdjacencyList()
    {
        @SuppressWarnings("rawtypes") List[] Ak = new ArrayList[nVertices];
        for (int j = 0; j < nVertices; j++) {
            V v = iToV[j];
            List<V> s = Graphs.successorListOf(graph, v);
            Ak[j] = new ArrayList<Integer>(s.size());

            for (V value : s) {
                Ak[j].add(vToI.get(value));
            }
        }

        return Ak;
    }

    private void clearState()
    {
        Ak = null;
        nVertices = 0;
        blocked = null;
        stack = null;
        iToV = null;
        vToI = null;

        Ak = null;
        B = null;
    }

    private boolean circuit(Integer v, Operation o)
    {
      
    	
    	boolean f = false;

      if (nCycles >= maxSimpleCircuits){
    	  return f;
      }
    	
        stack.push(v);
        
      
        
        blocked[v] = true;

        for (Integer w : Ak[v]) {
            if (w < start) {
                continue;
            }

            if (Objects.equals(w, start)) {
            	
              int lowBoundVertices = -1;	
              int upBoundVertices = -1;	
              if (this.width.equals("NARROW")) {
                 lowBoundVertices = (int) Math.round(0.75*nVertices);	
                 upBoundVertices = (int) Math.round(1.25*nVertices);	
                 //System.out.println("NARROWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
              } else {
            	  if (this.width.equals("WIDE")) {
                      lowBoundVertices = (int) Math.round(0.5*nVertices);	
                      upBoundVertices = (int) Math.round(1.5*nVertices);
                      //System.out.println("WIDEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                   } else {
                	   System.out.println("Error WIDTH!");
                   }
              }
              //System.out.println("LOW: " + lowBoundVertices + "HIGH: " + upBoundVertices);
              
              if ( (stack.size() >= lowBoundVertices) && (stack.size() <= upBoundVertices) ){	
            	
            	boolean lastVerticeCycle = false;
                if (o == Operation.ENUMERATE) {
                	
                	 //System.out.println("Number CYCLES Inside: " + nCycles);
              
                	
                    List<V> cycle = new ArrayList<>(stack.size());

                   
                    
                    for (Integer aStack : stack) {
                        cycle.add(iToV[aStack]);
                    }

                    
                   //System.out.println("cycle last: " + cycle.get(0)); 
                   if (termVertices.contains(cycle.get(0))) {
                	   cycles.add(cycle);
                	   lastVerticeCycle = true;
                   }
                    
                   
                } // ENUMERATE

                //if (o == Operation.PRINT_ONLY) {
                  //  for (Integer i : stack) {
                    //    System.out.print(iToV[i].toString() + " ");
                   // }
                   // System.out.println("");
               // }
                if (lastVerticeCycle) {
                  nCycles++;
                }   
               } // end stack.size
                f = true;
            } else if (!blocked[w]) {
                if (circuit(w, o)) {
                    f = true;
                }
            }
        }

        if (f) {
            unblock(v);
        } else {
            for (Integer w : Ak[v]) {
                if (w < start) {
                    continue;
                }

                if (!B[w].contains(v)) {
                    B[w].add(v);
                }
            }
        }
      //} // limit
        stack.pop();
      //} // limit
        return f;
    }

    private void unblock(Integer u)
    {
        blocked[u] = false;

        for (int wPos = 0; wPos < B[u].size(); wPos++) {
            Integer w = B[u].get(wPos);

            wPos -= removeFromList(B[u], w);

            if (blocked[w]) {
                unblock(w);
            }
        }
    }

    /**
     * Remove all occurrences of a value from the list.
     *
     * @param u the Integer to be removed.
     * @param list the list from which all the occurrences of u must be removed.
     */
    private int removeFromList(List<Integer> list, Integer u)
    {
        int nOccurrences = 0;

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer w = iterator.next();
            if (Objects.equals(w, u)) {
                nOccurrences++;
                iterator.remove();
            }
        }

        return nOccurrences;
    }

    /**
     * Get the graph
     * 
     * @return graph
     */
    public Graph<V, E> getGraph()
    {
        return graph;
    }

    /**
     * Set the graph
     * 
     * @param graph graph
     */
    public void setGraph(Graph<V, E> graph)
    {
        this.graph = GraphTests.requireDirected(graph, "Graph must be directed");
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public List<List<V>> findSimpleCycles()
        throws IllegalArgumentException
    {
        if (graph == null) {
            throw new IllegalArgumentException("Null graph.");
        }

        initState(Operation.ENUMERATE);

        for (int i = 0; i < nVertices; i++) {
            for (int j = 0; j < nVertices; j++) {
                blocked[j] = false;
                B[j].clear();
            }

            start = vToI.get(iToV[i]);
            circuit(start, Operation.ENUMERATE);
        }

        List<List<V>> result = cycles;
        clearState();
        return result;
    }
    
    
    
  //  @Override
    //public List<List<V>> findSimpleCycles()
      //  throws IllegalArgumentException
    //{
      //  if (graph == null) {
        //    throw new IllegalArgumentException("Null graph.");
        //}

        //initState(Operation.ENUMERATE);

        //for (int i = 0; i < nVertices; i++) {
          //  for (int j = 0; j < nVertices; j++) {
            //    blocked[j] = false;
              //  B[j].clear();
            //}

            //start = vToI.get(iToV[i]);
            //if (nCycles <= maxSimpleCircuits) {
            //	System.out.println("Number CYCLES: " + nCycles);
            //  circuit(start, Operation.ENUMERATE);
            //}
        //}

       // List<List<V>> result = cycles;
       // clearState();
        //return result;
    //}

    /**
     * Print to the standard output all simple cycles without building a list to keep them, thus
     * avoiding high memory consumption when investigating large and much connected graphs.
     */
    public void printSimpleCycles()
    {
        if (graph == null) {
            throw new IllegalArgumentException("Null graph.");
        }

        initState(Operation.PRINT_ONLY);

        for (int i = 0; i < nVertices; i++) {
            for (int j = 0; j < nVertices; j++) {
                blocked[j] = false;
                B[j].clear();
            }

            start = vToI.get(iToV[i]);
            circuit(start, Operation.PRINT_ONLY);
        }

        clearState();
    }

    /**
     * Count the number of simple cycles. It can count up to Long.MAX cycles in a graph.
     * 
     * @return the number of simple cycles
     */
    public long countSimpleCycles()
    {
        if (graph == null) {
            throw new IllegalArgumentException("Null graph.");
        }

        initState(Operation.COUNT_ONLY);

        for (int i = 0; i < nVertices; i++) {
            for (int j = 0; j < nVertices; j++) {
                blocked[j] = false;
                B[j].clear();
            }

            start = vToI.get(iToV[i]);
            circuit(start, Operation.COUNT_ONLY);
        }

        clearState();

        return nCycles;
    }
    
    
    
    private boolean circuitOrig(Integer v, Operation o)
    {
      
    	
    	boolean f = false;

      //if (stack.size() >= (nVertices - 2)){
    	
        stack.push(v);
        
      
        
        blocked[v] = true;

        for (Integer w : Ak[v]) {
            if (w < start) {
                continue;
            }

            if (Objects.equals(w, start)) {
            	
              int lowBoundVertices = (int) Math.round(0.75*nVertices);	
              int upBoundVertices = (int) Math.round(1.25*nVertices);	
              //System.out.println("LOW: " + lowBoundVertices + "HIGH: " + upBoundVertices);
              
              if ( (stack.size() >= lowBoundVertices) && (stack.size() <= upBoundVertices) ){	
              	
                if (o == Operation.ENUMERATE) {
                	
                	 System.out.println("Stack size: " + stack.size());
              
                	
                    List<V> cycle = new ArrayList<>(stack.size());

                   
                    
                    for (Integer aStack : stack) {
                        cycle.add(iToV[aStack]);
                    }

                   
                    
                    cycles.add(cycle);
                } // ENUMERATE

                //if (o == Operation.PRINT_ONLY) {
                  //  for (Integer i : stack) {
                    //    System.out.print(iToV[i].toString() + " ");
                   // }
                   // System.out.println("");
               // }

                nCycles++;
               }
                f = true;
            } else if (!blocked[w]) {
                if (circuit(w, o)) {
                    f = true;
                }
            }
        }

        if (f) {
            unblock(v);
        } else {
            for (Integer w : Ak[v]) {
                if (w < start) {
                    continue;
                }

                if (!B[w].contains(v)) {
                    B[w].add(v);
                }
            }
        }
      //} // limit
        stack.pop();
      //} // limit
        return f;
    }
    
   // @Override
    public List<List<V>> findSimpleCyclesOrig()
        throws IllegalArgumentException
    {
        if (graph == null) {
            throw new IllegalArgumentException("Null graph.");
        }

        initState(Operation.ENUMERATE);

        for (int i = 0; i < nVertices; i++) {
            for (int j = 0; j < nVertices; j++) {
                blocked[j] = false;
                B[j].clear();
            }

            start = vToI.get(iToV[i]);
            circuit(start, Operation.ENUMERATE);
        }

        List<List<V>> result = cycles;
        clearState();
        return result;
    }
   

    
}
