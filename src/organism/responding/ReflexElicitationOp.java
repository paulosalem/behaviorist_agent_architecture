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

import organism.responding.respondent.Reflex;
import organism.responding.respondent.ReflexAdjustmentOp;
import organism.util.Operation;

/**
 * Transforms an specified reflex into an actual behavioral response.
 * 
 * @author Paulo Salem
 *
 */
public class ReflexElicitationOp extends Operation{
  /**
   * Checks the preconditions for the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param r The reflex to be elicited.
   * @param instant The current instant.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(RespondingSubsystem respSubsys, Reflex r, int instant){
    
    Response rp = respSubsys.getCurrentResponses().getReflexResponse(r);
    
    // If the reflex is not currently associated with a response, the precondition is met
    if(rp == null){
      return true;
    }
    
    // The reflex already originates a response
    return false;
  }
  
  /**
   * Applies the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param r The reflex to be elicited.
   * @param instant The current instant.
   */
  public void apply(RespondingSubsystem respSubsys, Reflex r, int instant){
    
    Response rp = new Response(r, r.getAction(), r.getLatency(), r.getDuration(), r.getStrength());

    respSubsys.getCurrentResponses().addInactiveResponse(rp);
    
    ReflexAdjustmentOp adjOp = new ReflexAdjustmentOp();
    
    // Must check if the reflex has already been elicited. If not, we define that
    // its last elicitation time is the present.
    int t1;
    if(respSubsys.getCurrentResponses().isReflexElicitationTimeDefined(r)){
      t1 = respSubsys.getCurrentResponses().getReflexElicitationTime(r);
    }
    else{
      t1 = instant;
    }
    
    
    int t2 = instant;
    if(adjOp.precondition(r, t1, t2)){
      adjOp.apply(r, t1, t2);
    }
    
    respSubsys.getCurrentResponses().setReflexElicitationTime(r, instant);
    
    respSubsys.getActionHistory().addAction(instant, r.getAction());
    
    
  }
}
