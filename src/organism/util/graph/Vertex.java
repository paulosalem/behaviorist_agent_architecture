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

/**
 * A graph vertex that contains the specified kind of data.
 * 
 * @author Paulo Salem
 *
 * @param <T> The data type contained in the vertex. 
 */
public class Vertex<T> implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private T content;
  
  private Vertex<T> auxV1;
  private Vertex<T> auxV2;
  private Vertex<T> auxV3;
  
  private boolean auxB1;
  private boolean auxB2;
  
  public Vertex(T content){
    this.content = content;
    
    this.auxV1 = null;
    this.auxV2 = null;
    this.auxV3 = null;
  }

  public T getContent() {
    return content;
  }
  
  // TODO change equality criterion?
  /*
  
  @Override
  public boolean equals(Object obj){
    
    if(obj instanceof Vertex){
      Vertex v = (Vertex) obj;
      
      // Verices are equal iff their contents are equal.
      if(this.content.equals(v.getContent())){
        return true;
      }
    }
    
    return false;
  }
  
  @Override
  public int hashCode(){
    
    return content.hashCode();
  }
  */
  
  @Override
  public String toString(){
	  return content.toString();
  }

  public Vertex<T> getAuxV1() {
    return auxV1;
  }

  public void setAuxV1(Vertex<T> auxV1) {
    this.auxV1 = auxV1;
  }

  public Vertex<T> getAuxV2() {
    return auxV2;
  }

  public void setAuxV2(Vertex<T> auxV2) {
    this.auxV2 = auxV2;
  }

  public Vertex<T> getAuxV3() {
    return auxV3;
  }

  public void setAuxV3(Vertex<T> auxV3) {
    this.auxV3 = auxV3;
  }

  public boolean isAuxB1() {
    return auxB1;
  }

  public void setAuxB1(boolean auxB1) {
    this.auxB1 = auxB1;
  }

  public boolean isAuxB2() {
    return auxB2;
  }

  public void setAuxB2(boolean auxB2) {
    this.auxB2 = auxB2;
  }
  
  @Override
  public int hashCode(){
    return content.hashCode();
  }
  
  /**
   * Two vertices are equal iff their contents are equal.
   */
  @Override
  public boolean equals(Object obj){
    
    if(obj instanceof Vertex){
      Vertex v = (Vertex) obj;
      
      if(v.getContent().equals(this.content)){
        return true;
      }
    }
    
    return false;
  }
  
  
  
}
