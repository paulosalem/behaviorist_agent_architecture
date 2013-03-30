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

import organism.general.Intensity;
import organism.responding.respondent.Reflex;
import organism.responding.respondent.ReflexElicitationCond;
import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Stimulus;
import organism.util.Operation;
import organism.util.z.ZOperation;

@ZOperation(name = "ReflexSchedulingOp")
public class ReflexSchedulingOp  extends Operation {
  
  /**
   * Checks the preconditions for the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param stimSubsys The stimulation subsystem.
   * @param s The eliciting stimulus.
   * @param i The intensity of the stimulation.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(RespondingSubsystem respSubsys, StimulationSubsystem stimSubsys, Stimulus s, Intensity i){
    // no precondition
    return true;
  }
  
  /**
   * Applies the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param stimSubsys The stimulation subsystem.
   * @param s The eliciting stimulus.
   * @param i The intensity of the stimulation.
   */
  public void apply(RespondingSubsystem respSubsys, StimulationSubsystem stimSubsys, Stimulus s, Intensity i){
    
    ReflexElicitationCond cond = new ReflexElicitationCond(stimSubsys.getStimulusImplication());
    
    for(Reflex r: respSubsys.getReflexes()){
      if(cond.check(r, s, i)){
        respSubsys.getCurrentBehaviors().addReflex(r);
      }
    }
  }

}
