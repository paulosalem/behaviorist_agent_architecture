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
package organism.responding.operant;

import java.io.Serializable;
import java.util.Set;

import organism.stimulation.Stimulus;
import organism.stimulation.StimulusUtility;
import organism.stimulation.Utility;

/**
 * Defines the utility of operants.
 * 
 * @author Paulo Salem
 *
 */
public abstract class OperantUtility implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Stimulus utility definition.
   */
  protected StimulusUtility stimulusUtility;
  
  /**
   * Operant implication definition.
   */
  protected OperantImplication operantImplication;
  
  
  public OperantUtility(StimulusUtility stimulusUtility, OperantImplication operantImplication){
    this.stimulusUtility = stimulusUtility;
    this.operantImplication = operantImplication;
  }
  
  /**
   * Calculates the utility of the specified operant considering
   * the specifed stimuli.
   * 
   * @param o The operant whose utility is desired.
   * @param ss The stimuli that the organism is currently receiving.
   * 
   * @return The current utility of the operant.
   */
  public abstract Utility utility(Operant o, Set<Stimulus> ss);
}
