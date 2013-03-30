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

import java.util.HashSet;
import java.util.Set;

import organism.responding.operant.Operant;
import organism.responding.respondent.Reflex;
import organism.util.Operation;

public class ResponseTerminationOp_1 extends Operation {
  
  /**
   * Checks the preconditions for the operation.
   * 
   * @param currResp The current responses.
   * @param resp A response.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(CurrentResponses currResp, Response resp){
    if(currResp.getActiveResponses().contains(resp)
        && resp.getDuration() <= 0){
      
      return true;
    }
    
    return false;  
  }
  
  /**
   * Applies the operation.
   * 
   * @param currResp The current responses.
   * @param resp A response.
   * 
   */
  public void apply(CurrentResponses currResp, CurrentBehaviors currBehav, Response resp){

	  
	// First remove the behavior that justified the response.
	  
	Set<Operant> oDelete = new HashSet<Operant>();
    for(Operant o: currBehav.getEmitted()){
      Response rp = currResp.getOperantResponse(o);
      if(rp != null){
        if(rp.equals(resp)){
          oDelete.add(o);
        }
      }
    }
    currBehav.removeOperants(oDelete);
	
    
    Set<Reflex> rDelete = new HashSet<Reflex>();
    for(Reflex r: currBehav.getElicited()){
      Response rp = currResp.getReflexResponse(r);
      if(rp != null){
        if(rp.equals(resp)){
          rDelete.add(r);
        }
      }
    }
    currBehav.removeReflexes(rDelete);
    
    Set<Action> aDelete = new HashSet<Action>();
    for(Action a: currBehav.getSpontaneous()){
        Response rp = currResp.getSpontaneousResponse(a);
        if(rp != null){
          if(rp.equals(resp)){
        	aDelete.add(a);
            
          }
        }
      }
    currBehav.removeSpontaneousActions(aDelete);
    
    
    // Finally, we may remove the response
    currResp.removeActiveResponse(resp);
    

    
  }
}
