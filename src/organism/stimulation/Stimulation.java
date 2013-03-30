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

import organism.general.Intensity;

/**
 * Stimuli are presented to organisms through stimulation. Such
 * stimulation describe the particular circumstance of a stimulus 
 * presentation.
 * 
 * @author Paulo Salem
 *
 */
public class Stimulation implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public enum StimulusStatus{BEGINNING, ENDING, STABLE, ABSENT}
  
  /**
   * The stimulus being presented.
   */ 
  private Stimulus stimulus;
  
  /**
   * The intensity of the stimulation.
   */
  private Intensity intensity;
  
  /**
   * The status of that the stimulation assigns to the stimulus.
   */
  private StimulusStatus status;

  public Stimulation(Stimulus stimulus, Intensity intensity,
      StimulusStatus status) {
    super();
    this.stimulus = stimulus;
    this.intensity = intensity;
    this.status = status;
  }

  public Stimulus getStimulus() {
    return stimulus;
  }

  public Intensity getIntensity() {
    return intensity;
  }

  public StimulusStatus getStatus() {
    return status;
  }

public void setStimulus(Stimulus stimulus) {
	this.stimulus = stimulus;
}

public void setIntensity(Intensity intensity) {
	this.intensity = intensity;
}

public void setStatus(StimulusStatus status) {
	this.status = status;
}
  
  
  
}
