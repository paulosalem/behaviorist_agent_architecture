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

public class BaseLevelConflictResolutionOp extends Operation {
  
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
    Set<Action> as = respSubsys.getCurrentBehaviors().getSpontaneous();
    
    Set<Operant> deleteOs = new HashSet<Operant>();
    Set<Reflex> deleteRs = new HashSet<Reflex>();
    Set<Action> deleteAs = new HashSet<Action>();
    
    // Operants and spontaneous actions have the same priority according to the specification
    for(Operant o: os){
      for(Action a: as){
        Action oa = o.getAction();
        ActionConflict.Conflict conflict = respSubsys.getActionConflict().conflict(oa, a);
        
        // If the operant has the same action, we keep the operant
        if(oa.equals(a)){
          deleteAs.add(a);
        }
        
        // If the actions conflic
        if(conflict == ActionConflict.Conflict.Conflicting){
          
          // IDEA: maybe we should give more chance to operants?
          if(Math.random() <= 0.5){
            deleteOs.add(o);
          }else{
            deleteAs.add(a);
          }
          
        }
      }
    }
    
    // Reflexes always have priority over spontaneous actions
    for(Reflex r: rs){
      for(Action a: as){
        Action ra = r.getAction();
        ActionConflict.Conflict conflict = respSubsys.getActionConflict().conflict(ra, a);
        
        // If the reflex has the same action or if the actions conflict
        if(ra.equals(a) || conflict == ActionConflict.Conflict.Conflicting){
          deleteAs.add(a);
        }
      }
    }
    
    // If two spontaneous actions conflict, we randomly remove one of them
    for(Action a1: as){
      for(Action a2: as){
        ActionConflict.Conflict conflict = respSubsys.getActionConflict().conflict(a1, a2);
        
        if(conflict == ActionConflict.Conflict.Conflicting){
          
          if(Math.random() <= 0.5){
            deleteAs.add(a1);
          }else{
            deleteAs.add(a2);
          }
          
        }
      }
    }
    
    //
    // Delete the marked operants, reflexes and actions
    //
    
    for(Operant o: deleteOs){
      os.remove(o);
    }
    
    for(Reflex r: deleteRs){
      rs.remove(r);
    }
    
    for(Action a: deleteAs){
      as.remove(a);
    }
    
    
  }
}
