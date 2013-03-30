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

import organism.general.Correlation;

/**
 * An (directed) arc from one kind of data to another. 
 * 
 * 
 * @author Paulo Salem
 *
 * @param <T> The data type of vertices.
 */
public class Arc<T> implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected Vertex<T> beginning;
  
  protected Vertex<T> end;
  
  protected Correlation correlation;
  


  /*
  public Arc(T t1, T t2, Correlation c){
    Vertex<T> v1 = new Vertex<T>(t1); 
    Vertex<T> v2 = new Vertex<T>(t2);

    this.beginning = v1;
    this.end = v2;
    this.correlation = c;
  }*/
  
  public Arc(Vertex<T> beginning, Vertex<T> end, Correlation c){
    this.beginning = beginning;
    this.end = end;
    this.correlation = c;
  }


  public Vertex<T> getBeginning() {
    return beginning;
  }


  public Vertex<T> getEnd() {
    return end;
  }


  public Correlation getCorrelation() {
    return correlation;
  }
  
  @Override
  public String toString(){
	  //return beginning.toString() + "--" + correlation.toString() + "--" + end.toString();
	  return correlation.toString();
  }
  
  
  @Override
  public int hashCode(){
    return beginning.hashCode() + end.hashCode();
  }
  
  /**
   * Two arcs are equal iff their vertices are equal.
   */
  @Override
  public boolean equals(Object obj){
    
    if(obj instanceof Arc){
      Arc a = (Arc) obj;
      
      if(a.getBeginning().equals(this.beginning) 
          && a.getEnd().equals(this.end)){
        return true;
      }
    }
    
    return false;
  }
  
  
}
