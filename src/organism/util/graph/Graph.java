/*******************************************************************************
 * Behaviourist Agent Architecture
 * 
 * This software was developed by Paulo Salem da Silva for his doctoral thesis, 
 * which is entitled
 *   
 *   "Verification of Behaviourist Multi-Agent Systems by means of 
 *    Formally Guided Simulations"
 * 
 * This software, therefore, constitutes a companion to the thesis. As such, 
 * it should be seen as an experimental product, suitable for research purposes,
 * but not ready for production.
 * 
 * 
 * Copyright (c) 2008 - 2012, Paulo Salem da Silva
 * All rights reserved.
 * 
 * This software may be used, modified and distributed freely, provided that the 
 * following rules are followed:
 * 
 *   (i)   this copyright notice must be maintained in any redistribution, in both 
 *         original and modified form,  of this software;
 *   (ii)  this software must be provided free of charge, although services which 
 *         require the software may be charged;
 *   (iii) for non-commercial purposes, this software may be used, modified and 
 *         distributed free of charge;
 *   (iv)  for commercial purposes, only the original, unmodified, version of this 
 *         software may be used.
 * 
 * For other uses of the software, please contact the author.
 ******************************************************************************/
package organism.util.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A mathematical graph (i.e., a set of vertexes and a set of arcs).
 * 
 * @author Paulo Salem
 *
 */
public class Graph<T> implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * The set of vertices of this graph.
   */
  private Set<Vertex<T>> vertices;
  
  /**
   * An adjacency structure that maps each vertex on a list
   * of the arcs beginning in it.
   */
  private Map<Vertex<T>, Set<Arc<T>>> adjacency;
  
  
  /**
   * Creates an empty graph.
   */
  public Graph(){
    vertices = new HashSet<Vertex<T>>();
    adjacency = new HashMap<Vertex<T>, Set<Arc<T>>>();
  }
  
  /**
   * Creates a graph with the specified vertices and arcs.
   * 
   * @param adjacency A map from vertexes to their neighbors.
   */
  public Graph(Set<Vertex<T>> vertices, Set<Arc<T>> arcs){

    for(Vertex<T> v: vertices){
      putVertex(v);
    }
    
    for(Arc<T> a: arcs){
      putArc(a);
    }
  }

  
  /**
   * Adds a vertex to the graph. If it is alread present, nothing changes.
   * 
   * @param v The vertex to be added.
   */
  public void putVertex(Vertex<T> v){
    vertices.add(v);
    
    // Setup an empty adjacency list
    if(adjacency.get(v) == null){
    	adjacency.put(v, new HashSet<Arc<T>>());
    }
  }
  
  public void putVertices(Set<Vertex<T>> vs){
	  for(Vertex<T> v: vs){
		  putVertex(v);
	  }
    
  }

  
  /**
   * Puts a new arc in the graph. The vertices of the arc must be already
   * present in the graph. If the arc is already present, nothing changes.
   * 
   * @param a The arc to be added.
   */
  public void putArc(Arc<T> a){
    
    // The vertices of the arc must be in the graph
    if(vertices.contains(a.getBeginning()) && vertices.contains(a.getEnd())){
      
      // Get the arcs leaving the beginning
      Set<Arc<T>> as = adjacency.get(a.getBeginning());
      
      // If there are none yet, we create the adjacent arc set
      if(as == null){
        as = new HashSet<Arc<T>>();
        adjacency.put(a.getBeginning(), as);
      }
      
      // Add the new arc. If it is already there, remove the old.
      if(as.contains(a)){
        as.remove(a);
      }
      as.add(a);
      
    }
    else{
      throw new IllegalArgumentException("The vertices of the arc must be already in the graph.");
    }
  }
  
  
  /**
   * Removes all arcs from one vertex to the other.
   * 
   * @param v1 The beginning of the arcs.
   * @param v2 The end of the arcs.
   */
  public void removeArcs(Vertex<T> v1, Vertex<T> v2){
    
    Set<Arc<T>> as = adjacency.get(v1);
    Set<Arc<T>> delete = new HashSet<Arc<T>>();
    
    // Check which must be deleted
    for(Arc<T> a: as){
      if(a.getEnd().equals(v2)){
        delete.add(a);
      }
    }
    
    // Delete the appropriate ones
    for(Arc<T> a: delete){
      as.remove(a);
    }
  }
  
  
  /**
   * Seeks the vertices adjacent to the specified one.
   * 
   * @param v The vertex whose adjacents we want.
   * 
   * @return A set of adjacent vertices.
   */
  public Set<Vertex<T>> getAdjacentVertices(Vertex<T> v){
    
    Set<Vertex<T>> vs = new HashSet<Vertex<T>>();
    
    Set<Arc<T>> as = adjacency.get(v);
    
    if(as != null){
      for(Arc<T> a: as){
        vs.add(a.getEnd());
      }
    }
    
    return vs;
  }
  
  
  public Set<Arc<T>> getArcs(Vertex<T> from, Vertex<T> to) {

		Set<Arc<T>> arcs = new HashSet<Arc<T>>();

		Set<Arc<T>> adj = adjacency.get(from);

			for (Arc a : adj) {
				if (a.getEnd().equals(to)) {
					arcs.add(a);
				}
			}
		

		return arcs;
	}
  
  public Arc<T> getFirstArc(Vertex<T> from, Vertex<T> to){
    
    Set<Arc<T>> arcs =  getArcs(from, to);
    
    if(arcs.size() > 0){
      return arcs.iterator().next();
    }
    
    return null;
  }
  
  public Set<Vertex<T>> getVertices(){
    return vertices;
  }
  
  public Vertex<T> getVertexThatContains(T t){
	  for(Vertex<T> v: vertices){
		  if(v.getContent().equals(t)){
			  return v;
		  }
	  }
	  
	  return null;
  }
  
  public Set<Arc<T>> getArcs(){
    
    Set<Arc<T>> arcs = new HashSet<Arc<T>>();
    
    for(Set<Arc<T>> sa: adjacency.values()){
      arcs.addAll(sa);
    }

    return arcs;
  }
  
}
