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

/**
 * Resolves conflic among reflexes. When two reflexes conflict,
 * this class randomly excludes one of them. Subclasses should implement
 * more interesting conflict resolution methods.
 * 
 * @author Paulo Salem
 *
 */
public class ReflexConflictResolutionOp extends Operation {
  
  /**
   * Checks the preconditions for the operation.
   * 
   * @param respSubsys The responding subsystem.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(RespondingSubsystem respSubsys){
    // no precondition
    return true;
  }
  
  /**
   * Applies the operation.
   * 
   * @param respSubsys The responding subsystem.
   */
  public void apply(RespondingSubsystem respSubsys){
    Set<Reflex> rs = respSubsys.getCurrentBehaviors().getElicited();
    Set<Reflex> delete = new HashSet<Reflex>();
    ReflexConflictCond cond = new ReflexConflictCond(respSubsys.getActionConflict());
    
    // Check conflicts and builds a delete set
    for(Reflex r1: rs){
      for(Reflex r2: rs){
        
        // If there is a conflict
        if(cond.check(r1, r2)){
            delete.add(chooseToRemove(r1,r2));
        }
      }
    }
    
    // Delete those in the delete set
    for(Reflex r: delete){
      rs.remove(r);
    }
  }
  

  /**
   * Chooses one of the specified reflexes to be removed from the
   * elicited set. It is assumed that the reflexes conflict with
   * each other.
   * 
   * Subclasses should override this method in order to implement
   * particular refinements for the task. Here, the method
   * chooses the reflex randomly.
   * 
   * @param r1 The first reflex.
   * @param r2 The second reflex.
   * @return A reflex to be removed from the elicited set.
   */
  protected Reflex chooseToRemove(Reflex r1, Reflex r2){
    
    if(Math.random() <= 0.5){
      return r1;
    }
    else{
      return r2;
    }
    
  }
}
