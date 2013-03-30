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

public class BaseLevelEmissionOp extends Operation {
  /**
   * Checks the preconditions for the operation.
   * 
   * @param respSubsys
   *          The responding subsystem.
   * @param a
   *          The action to be emitted.
   * @param instant
   *          The current instant.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(RespondingSubsystem respSubsys, Action a,
      int instant) {
    Response rp = respSubsys.getCurrentResponses().getSpontaneousResponse(a);
    
    // If the spontaneous action is not currently associated with a response, the precondition is met
    if(rp == null){
      return true;
    }
    
    // The spontaneous action already originates a response
    return false;
  }

  /**
   * Applies the operation.
   * 
   * @param respSubsys
   *          The responding subsystem.
   * @param a
   *          The action to be emitted.
   * @param instant
   *          The current instant.
   */
  public void apply(RespondingSubsystem respSubsys, Action a, int instant) {

    // TODO Put better values for response's latency, duration and intensity
    Response rp = new Response(null, a, 1, 5, new Intensity(1.0));

    respSubsys.getCurrentResponses().addInactiveResponse(rp);

    respSubsys.getActionHistory().addAction(instant, a);
  }
}
