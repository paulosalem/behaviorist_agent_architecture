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
package organism.responding.operant;

import organism.responding.RespondingSubsystem;
import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Utility;
import organism.util.Condition;

/**
 * A condition that is true when an operant may be emitted.
 * 
 * @author Paulo Salem
 */
public class OperantEmissionCond extends Condition {

  protected RespondingSubsystem respSubsys;
  
  protected StimulationSubsystem stimSubsys;
  
  protected OperantUtility operantUtility;
  
  protected Discrimination discrimination = new Discrimination();
   
  
  public OperantEmissionCond(RespondingSubsystem respSubsys, StimulationSubsystem stimSubsys, OperantUtility operantUtility){
    super();
    this.respSubsys = respSubsys;
    this.stimSubsys = stimSubsys;
    this.operantUtility = operantUtility;
  }

  public boolean check(Operant o){

    // Check whether it is discriminated.
    if(!discrimination.discriminates(stimSubsys.getCurrentStimuli(), o)){
      return false;
    }
    
    // Check whether it has positive utility
    Utility u = operantUtility.utility(o, stimSubsys.getCurrentStimuli());
    if(!u.greaterThan(Utility.NEUTRAL)){
      return false;
    }
    
    
    // Check whether other operants with the same action are not harmful
    for(Operant p: respSubsys.getOperants()){
      if(p.getAction().equals(o.getAction())){
        if(!operantUtility.utility(p, stimSubsys.getCurrentStimuli()).greaterThanOrEqualTo(Utility.NEUTRAL)){
        
          // Well, we found another operant that has the same action but leads to harmful stimulus.
          // Therefore, the specified operant should not be emitted.
          return false;
        }
      }
    }
    
    // Since no requirement was violated, the specified operant may be emitted
    return true;
  }
}
