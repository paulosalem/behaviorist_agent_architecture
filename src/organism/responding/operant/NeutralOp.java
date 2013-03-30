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

import java.util.Set;

import organism.emotion.EmotionSubsystem;
import organism.general.Correlation;
import organism.responding.Action;
import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Stimulus;

public class NeutralOp extends OperantOp {

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public NeutralOp(OperantUtility operantUtility, StimulationSubsystem stimSubsys){
    super(operantUtility, stimSubsys);
  }
  
  public boolean precondition(Operant operant, Action action, Stimulus consequence, Set<Stimulus> discriminativeStimuli, int delay){
    
    if(super.precondition(operant, action, delay)){
      if(!operant.getAntecedents().contains(discriminativeStimuli)
          && !this.stimSubsys.getStimulusImplication().causes(consequence, operant.getConsequence())){
       
        return true;
      }
    }
    
    return false;
  }
  
  public void apply(Operant operant, EmotionSubsystem emoSubsys, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){

    // Nothing changes

  }
}
