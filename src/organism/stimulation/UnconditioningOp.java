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

import organism.util.Operation;

public abstract class UnconditioningOp extends Operation {

  protected StimulationSubsystem stimulationSubsystem;

  
  public UnconditioningOp(StimulationSubsystem stimulationSubsystem) {
    super();
    this.stimulationSubsystem = stimulationSubsystem;
  }


  /**
   * @param implication The stimulus implication relation.
   * @param s1 The stimulus that came first.
   * @param s2 The stimulus that came last.
   * @param delay The time span between the two stimuli.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(StimulusImplication implication, Stimulus s1, Stimulus s2, int currentInstant){
    
    int maxDelay = stimulationSubsystem.getMaxDelay();
    
    int beginning = currentInstant - maxDelay;
    
    if(stimulationSubsystem.getPastStimuli().containsKey(beginning)){
      
      // If the first condition fails (s1 was not perceived)
      if(!stimulationSubsystem.getPastStimuli().get(beginning).contains(s1)){
        return false;
      }
      
      // If the second condition fails (s2 happend within the time limit)
      for(int t = beginning + 1; t <= currentInstant; t++){
        if(stimulationSubsystem.getPastStimuli().get(t).contains(s2)){
          return false;
        }
      }
      
      // If the third condition fails (s1 does not cause s2)
      if(!stimulationSubsystem.getStimulusImplication().causes(s1, s2)){
        return false;
      }
      
    }
    else{
      
      // If the instant is inaccessible here, it is because it is negative.
      // Thus, not enough time has elapsed.
      return false;
    }
    
    // If nothing failed, the precondition holds
    return true;

  }
  
  /**
   * Applies the operation.
   * 
   * @param implication The stimulus implication relation.
   * @param s1 The stimulus that came first.
   * @param s2 The stimulus that came last.
   * @param delay The time span between the two stimuli.
   */  
  public abstract void apply(StimulusImplication implication, Stimulus s1, Stimulus s2);

}
