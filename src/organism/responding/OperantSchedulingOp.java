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

import organism.responding.operant.Operant;
import organism.responding.operant.OperantEmissionCond;
import organism.responding.operant.OperantUtility;
import organism.stimulation.StimulationSubsystem;
import organism.util.Operation;
import organism.util.z.ZOperation;

@ZOperation(name = "OperantSchedulingOp")
public class OperantSchedulingOp extends Operation {
  
  /**
   * Checks the preconditions for the operation.
   * 
   * @param respSubsys The responding subsystem.
   * @param stimSubsys The stimulation subsystem.
   * @param operantUtility Operant utility definition.
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
   * @param operantUtility Operant utility definition.
   * 
   */
  public void apply(RespondingSubsystem respSubsys, StimulationSubsystem stimSubsys, OperantUtility operantUtility){
    
    OperantEmissionCond cond = new OperantEmissionCond(respSubsys, stimSubsys, operantUtility);
    
    for(Operant o: respSubsys.getOperants()){
      if(cond.check(o)){
        respSubsys.getCurrentBehaviors().addOperant(o);
      }
    }    
  }
}
