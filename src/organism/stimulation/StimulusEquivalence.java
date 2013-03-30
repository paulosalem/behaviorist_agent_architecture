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

/**
 * Defines an equivalence relation among stimuli.
 * 
 * @author Paulo Salem
 *
 */
public class StimulusEquivalence implements Serializable {
    
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private StimulusImplication stimulusImplication;
  
  public StimulusEquivalence(StimulusImplication stimulusImplication){
    this.stimulusImplication = stimulusImplication; 
  }
  
  /**
   * Checks whether two specified stimuli are equivalent to
   * the organism.
   * 
   * @param s1 The first stimulus.
   * @param s2 The second stimulus.
   * 
   * @return <code>true</code> if the stimuli are equivalent;
   *         <code>false</code> otherwise.
   */
  public boolean equals(Stimulus s1, Stimulus s2){
    if(stimulusImplication.causes(s1, s2) && stimulusImplication.causes(s2, s1)
        && stimulusImplication.correlation(s1, s2) == stimulusImplication.correlation(s2, s1)){
      return true;
    }

    return false;
  }

}
