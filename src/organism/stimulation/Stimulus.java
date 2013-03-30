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

import org.jdom.Element;

import organism.io.Storable;

/**
 * A prototypical stimulus.
 * 
 * @author Paulo Salem
 *
 */
public class Stimulus implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * A unique identification for the stimulus. 
   */
  protected int id;
  
  /**
   * A user friendly name for the stimulus.
   */
  protected String name;
  
  public Stimulus(Integer id){
    this.id = id;
    this.name = "Stimulus " + id;
  }
  
  public Stimulus(Integer id, String name){
    this.id = id;
    this.name = name;
  }
  
  @Override
  public boolean equals(Object obj){
    if(obj instanceof Stimulus){
      Stimulus s = (Stimulus) obj;
      
      if(id == s.id){
        return true;
      }
    }
    
    return false;
  }
  
  @Override
  public int hashCode(){
    return id;
  }
  
  @Override
  public Object clone(){
    return new Stimulus(id, name);
  }
  
  @Override
  public String toString(){
    return name;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public void loadXML(Element xml) {

    id = Integer.parseInt(xml.getAttributeValue("id"));
    name = xml.getAttributeValue("name");
    
  }

  @Override
  public Element toXML() {
    
    Element stimulusE = new Element("stimulus");
    stimulusE.setAttribute("id", Integer.toString(id));
    stimulusE.setAttribute("name", name);

    return stimulusE;
  }
  
  
  
}
