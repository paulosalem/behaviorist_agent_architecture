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
 * Resolves conflicts between operants and reflexes currently selected.
 * 
 * @author Paulo Salem
 *
 */
public class OperantReflexConflictResolutionOp extends Operation {
  
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
    Set<Operant> os = respSubsys.getCurrentBehaviors().getEmitted();
    Set<Reflex> rs = respSubsys.getCurrentBehaviors().getElicited();
    OperantReflexConflictCond cond = new OperantReflexConflictCond(respSubsys.getActionConflict());
    
    for(Operant o: os){
      for(Reflex r: rs){
        
        // If there is a conflict
        if(cond.check(o, r)){
          removeOne(respSubsys, o, r);
        }
      }
    }
  }
  
  /**
   * Removes either the specified operant or the specified reflex from
   * the current behavior.
   * 
   * The choice is made randomly. Subclasses might override this method
   * in order to implement more sophisticated strategies.
   * 
   * @param respSubsys The responding subsystem.
   * @param o The operant that might be removed.
   * @param r The reflex that might be removed.
   */
  protected void removeOne(RespondingSubsystem respSubsys, Operant o, Reflex r){
    
    if(Math.random() <= 0.5){
      respSubsys.getCurrentBehaviors().getEmitted().remove(o);
    }
    else{
      respSubsys.getCurrentBehaviors().getElicited().remove(r);
    }
  }
  
  
}
