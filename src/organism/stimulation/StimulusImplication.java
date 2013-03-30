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
package organism.stimulation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jdom.Element;
import organism.general.Correlation;
import organism.io.Storable;
import organism.util.graph.Arc;
import organism.util.graph.Graph;
import organism.util.graph.Vertex;

/**
 * Defines how stimuli imply each other.
 * 
 * @author Paulo Salem
 *
 */
public class StimulusImplication implements Storable, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected Graph<Stimulus> stimuliGraph;
  
  protected StimulusManager stimulusManager;
  

  public StimulusImplication(StimulusManager stimulusManager){
    this.stimuliGraph = new Graph<Stimulus>();
    this.stimulusManager = stimulusManager;
  }
  
  /**
   * Checks whether a specified stimuli is believed to cause another.
   * 
   * @param s1 The stimulus that may cause another.
   * @param s2 The stimulus that might be caused.
   * 
   * @return <code>true</code> if the stimuli are believed be causally related;
   *         <code>false</code> otherwise.
   */
  public boolean causes(Stimulus s1, Stimulus s2){
	 
	  // One causes another iff there is a path from one to the other
	if(findPath(s1, s2) != null){
		return true;
	}
	
	return false;
    
  }
  
  
  /**
   * Calculates the correlation between two stimuli.
   * 
   * @param s1 The first stimulus.
   * @param s2 The second stimulus.
   * 
   * @return The correlation between the specified stimuli.
   */
  public Correlation correlation(Stimulus s1, Stimulus s2){

    List<Arc<Stimulus>> path = findPath(s1, s2);
  
    // If no path was found, the correlation is the minimum possible
    if(path == null){
      return new Correlation(Correlation.MIN);
    }
    
    // If there is an arc connecting the stimuli, then get
    // the correlation on the arc
    else if(path.size() == 1){
      Arc<Stimulus> a = path.iterator().next();
      return a.getCorrelation();
    }
    
    // Else, find a path from s1 to s2 and calculate a
    // composed correlation over this path
    else{
      Correlation c = Correlation.MIN; // if there is no path, there is no correlation
      
      for(Arc a: path){
        
        if(c == null){
          c = a.getCorrelation();
        }
        else{
          c = Correlation.valueOf(c.multiply(c));
        }
        
      }
      
      return c;
    }

  }
  
  
  /**
   * Calculates the stimuli consequences of the specified stimulus.
   * 
   * @param s The stimulus whose consequences are desired.
   * @return A set of all stimuli implied by the specified one.
   */
  public Set<Stimulus> consequences(Stimulus s){
    
    Set<Stimulus> consequences = new HashSet<Stimulus>();
    
    
    //
    // Perform a breadth-first search
    //
    
    // Mark all vertices as non-visited
    for(Vertex v: stimuliGraph.getVertices()){
    	v.setAuxB1(false);
    }
    
    // Begin at v1
    Vertex<Stimulus> v1 = stimuliGraph.getVertexThatContains(s);
    Set<Vertex> vs = new HashSet<Vertex>();
    vs.add(v1);
    v1.setAuxB1(true);
    
    // Loop while there are vertices to see    
    while(!(vs.size() == 0)){
      Set<Vertex> next = new HashSet<Vertex>();
      for(Vertex v: vs){
        
        Set<Vertex<Stimulus>> adj = stimuliGraph.getAdjacentVertices(v);
        for(Vertex<Stimulus> x: adj){
          
        	if(!x.isAuxB1()){
        		// Add a node to the search fringe
        		next.add(x);
        		x.setAuxB1(true); // visited
          
        		// Add a new consequence
        		consequences.add(x.getContent());
        	}
        }
      }
      
      // Discard the vertices already analyzed and go on to
      // the next ones
      vs = next;
    }
    
    return consequences;
  }
  
  /**
   * Adds a new stimulus implication.
   * 
   * @param s1 The stimulus that causes the other.
   * @param s2 The stimulus that is caused.
   * @param c The correlation of the causation.
   */
  public void putCause(Stimulus s1, Stimulus s2, Correlation c){
    Arc<Stimulus> a = new Arc<Stimulus>(stimuliGraph.getVertexThatContains(s1), stimuliGraph.getVertexThatContains(s2), c);
    stimuliGraph.putArc(a);
  }
  
  /**
   * Removes the direct implication relation between the specified stimuli.
   * 
   * @param s1 The stimulus that directly causes the other.
   * @param s2 The stimulus that is directly caused.
   */
  public void removeDirectCause(Stimulus s1, Stimulus s2){
    Vertex<Stimulus> v1 = stimuliGraph.getVertexThatContains(s1);
    Vertex<Stimulus> v2 = stimuliGraph.getVertexThatContains(s2);
    
    stimuliGraph.removeArcs(v1, v2);
  }
  
  /**
   * Finds a path from the first stimulus to the second such that
   * the number of arcs in the path is minimum.
   * 
   * @param s1 The beginning of the path.
   * @param s2 The ending of the path.
   * 
   * @return The minimum path (w.r.t number of arcs) 
   *         from <code>s1</code> to <code>s2</code>, if it exists;
   *         <code>null<code> otherwise.
   */
  protected List<Arc<Stimulus>> findPath(Stimulus s1, Stimulus s2){
    List<Arc<Stimulus>> path = null; 
    
    Vertex<Stimulus> v1 = stimuliGraph.getVertexThatContains(s1);

    //
    // Perform a breadth-first search
    //
    
    // Mark all vertices as non-visited and clear the parent field
    for(Vertex v: stimuliGraph.getVertices()){
    	v.setAuxB1(false);
    	v.setAuxV1(null);
    }
    
    // Begin at v1
    Set<Vertex> vs = new HashSet<Vertex>();
    vs.add(v1);
    v1.setAuxB1(true); // visited
    
    // Loop while there are vertices to see    
    while(!(vs.size() == 0)){
      Set<Vertex> next = new HashSet<Vertex>();
      for(Vertex v: vs){
        
        // Check if we found the second stimulus
        if(v.getContent().equals(s2)){

          // Indeed s1 causes s2. Let's get the path
          return buildPath(v);
        }
        
        Set<Vertex<Stimulus>> adj = stimuliGraph.getAdjacentVertices(v);
        for(Vertex<Stimulus> x: adj){
        	
        	// If it has not been visited so far
        	if(!x.isAuxB1()){
        		x.setAuxV1(v); // Set parent, so that we can restore the path later
        		next.add(x);
        		x.setAuxB1(true); // visited!
        	}
        }
      }
      
      // Discard the vertices already analyzed and go on to
      // the next ones
      vs = next;
    }    
    

    return path;
  }

  /**
   * Given the last vertex in a path, builds the whole path.
   * It assumes that the field <code>auxV1</code> of the vertices
   * (including the last) contains the parent of that vertex in
   * a search tree. The algorithm assumes that the first vertex
   * with <code>null</code> parent is the root (i.e., the beginning
   * of the path).
   * 
   * @param last The last vertex of the path.
   * 
   * @return A path from the tree root up to the specified vertex.
   */
  protected List<Arc<Stimulus>> buildPath(Vertex<Stimulus> last){
    
    LinkedList<Arc<Stimulus>> path = new LinkedList<Arc<Stimulus>>();
    
    Vertex<Stimulus> current = last;
    Vertex<Stimulus> parent = last.getAuxV1();
    
    while(parent != null){
      
      Arc<Stimulus> arc = stimuliGraph.getFirstArc(parent, current);
      
      path.addFirst(arc);
      
      current = parent;
      parent = current.getAuxV1();
      
    }
    
    return path;
    
  }
  
  public Graph<Stimulus> getStimuliGraph() {
    return stimuliGraph;
  }

  @Override
  public void loadXML(Element xml) {
    
    List<Element> causeEL = xml.getChildren("cause");
    for(Element causeE: causeEL){
      Stimulus s1 = stimulusManager.getPrototypeClone(Integer.parseInt(causeE.getAttributeValue("id1")));
      Stimulus s2 = stimulusManager.getPrototypeClone(Integer.parseInt(causeE.getAttributeValue("id2")));
      Correlation c = new Correlation(Double.parseDouble(causeE.getAttributeValue("correlation")));
      
      // Put the cause 
      putCause(s1, s2, c);
    }
      
  }

  @Override
  public Element toXML() {
    
    Element implicationE = new Element("stimulus-implication");
    
    for(Arc<Stimulus> a: stimuliGraph.getArcs()){
      Element causeE = new Element("cause");
      implicationE.addContent(causeE);
      causeE.setAttribute("id1", Integer.toString(a.getBeginning().getContent().getId()));
      causeE.setAttribute("id2", Integer.toString(a.getEnd().getContent().getId()));
      causeE.setAttribute("correlation", a.getCorrelation().toString());
    }
    

    return implicationE;
  }
  
  
  
}
