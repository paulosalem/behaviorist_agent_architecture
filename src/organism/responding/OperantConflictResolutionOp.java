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
import organism.responding.operant.OperantUtility;
import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Utility;
import organism.util.Operation;

public class OperantConflictResolutionOp extends Operation {

  /**
   * Checks the preconditions for the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param stimSubsys The stimulation subsystem.
   * @param operantUtility The mechanism to calculate operant utility.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
  public boolean precondition(RespondingSubsystem respSubsys, StimulationSubsystem stimSubsys, OperantUtility operantUtility){
    // no precondition
    return true;
  }
  
  /**
   * Applies the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param stimSubsys The stimulation subsystem.
   * @param operantUtility The mechanism to calculate operant utility.
   */
  public void apply(RespondingSubsystem respSubsys, StimulationSubsystem stimSubsys, OperantUtility operantUtility){
    
    Set<Operant> delete = new HashSet<Operant>();
    Set<Operant> ignore = new HashSet<Operant>();
    Set<Operant> os = respSubsys.getCurrentBehaviors().getEmitted();
    OperantConflictCond cond = new OperantConflictCond(respSubsys.getActionConflict());
    
    // Check conflicts and builds a delete set
    for(Operant o1: os){
      for(Operant o2: os){
        
        // If there is a conflict
        if(cond.check(o1, o2)){
          // Remove the operant with the lower utility
          
          Utility u1 = operantUtility.utility(o1, stimSubsys.getCurrentStimuli());
          Utility u2 = operantUtility.utility(o2, stimSubsys.getCurrentStimuli());
          
          // TODO debug
          /*
          System.out.println("DEBUG: utility(o1["+o1.getAction().toString()+"]) = " + u1.toString());
          System.out.println("DEBUG: utility(o2["+o2.getAction().toString()+"]) = " + u2.toString());
          */
          
          if(u1.greaterThan(u2)){
            delete.add(o2);
          }
          else if(u1.equals(u2)){
            
            // Perform an arbitrary choice.
            Double p = Math.random();
            
            if(p <= 0.5 && !ignore.contains(o1)){
              delete.add(o1);
              ignore.add(o2); // makes sure we do not exclude o2 later (when o1 and o2 swap places in the comparison)
            }
            else if(p > 0.5 && !ignore.contains(o2)){
              delete.add(o2);
              ignore.add(o1); // makes sure we do not exclude o1 later (when o1 and o2 swap places in the comparison)
            }
          }
        }
      }
    }
    
    // Delete those in the delete set
    for(Operant o: delete){
      os.remove(o);
    }
    
  }
}
