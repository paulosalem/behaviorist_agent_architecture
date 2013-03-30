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
package organism.responding;

import java.io.Serializable;

import organism.general.Intensity;

/**
 * A behavioral response.
 * 
 * @author Paulo Salem
 *
 */
public class Response implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Indicates the behavior responsible for the generation of the response.
   */
  private Behavior originator;
  
  /**
   * The action to be performed.
   */
  private Action action;
  
  /**
   * How long should it be waited until the action is performed.
   */
  private int latency;
  
  /**
   * For how long the action will be performed.
   */
  private int duration;
  
  /**
   * The "strength" of the response.
   */
  private Intensity intensity;
  

  public Response(Behavior originator, Action action, int latency, int duration, Intensity intensity) {
    super();
    this.originator = originator;
    this.action = action;
    this.latency = latency;
    this.duration = duration;
    this.intensity = intensity;
  }

  public Behavior getOriginator() {
    return originator;
  }

  public Action getAction() {
    return action;
  }

  public int getLatency() {
    return latency;
  }

  public int getDuration() {
    return duration;
  }

  public Intensity getIntensity() {
    return intensity;
  }

  public void setLatency(int latency) {
    this.latency = latency;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public void setIntensity(Intensity intensity) {
    this.intensity = intensity;
  }
  
  @Override
  public String toString(){
	return "(" + action + "," + intensity +")";  
  }
  
  
}
