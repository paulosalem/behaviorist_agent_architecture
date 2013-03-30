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
import organism.responding.operant.Operant;
import organism.util.Operation;

/**
 * Transforms an specified operant into an actual behavioral response.
 * 
 * @author Paulo Salem
 *
 */
public class OperantEmissionOp extends Operation {
  
  /**
   * Checks the preconditions for the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param o The operant to be emited.
   * @param instant The current instant.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(RespondingSubsystem respSubsys, Operant o, int instant){
    Response rp = respSubsys.getCurrentResponses().getOperantResponse(o);
    
    // If the operant is not currently associated with a response, the precondition is met
    if(rp == null){
      return true;
    }
    
    // The operant already originates a response
    return false;
  }
  
  /**
   * Applies the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param o The operant to be emitted.
   * @param instant The current instant.
   */
  public void apply(RespondingSubsystem respSubsys, Operant o, int instant){
    
    // TODO Put better values for response's latency, duration and intensity
    Response rp = new Response(o, o.getAction(), 0, 5, new Intensity(1.0));

    respSubsys.getCurrentResponses().addInactiveResponse(rp);
    
    respSubsys.getActionHistory().addAction(instant, o.getAction());
  }
}
