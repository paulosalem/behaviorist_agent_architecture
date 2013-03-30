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
package organism.responding.respondent;

import organism.general.Intensity;
import organism.responding.RespondingSubsystem;
import organism.responding.Response;
import organism.responding.operant.Operant;
import organism.util.Operation;

public class ReflexAdjustmentOp extends Operation {

  /**
   * Checks the preconditions for the operation.
   * 
   * @param r The reflex being adjusted
   * @param instant1 The first instant.
   * @param instant2 The second instant.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(Reflex r, int instant1, int instant2){
    // none   
    return true;
  }
  
  /**
   * Applies the operation.
   * 
   * @param r The reflex being adjusted
   * @param instant1 The first instant.
   * @param instant2 The second instant.
   */
  public void apply(Reflex r, int instant1, int instant2){
    
    r.setElicitation(r.getParameters().deltaElicitation(r.getElicitation(), instant1, instant2));
    r.setStrength(r.getParameters().deltaStrength(r.getStrength(), instant1, instant2));
    r.setDuration(r.getParameters().deltaDuration(r.getDuration(), instant1, instant2));
    r.setLatency(r.getParameters().deltaLatency(r.getLatency(), instant1, instant2));
  }
}
