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
package organism.stimulation;

import organism.util.Operation;

public class StimulationUpdateOp_2 extends Operation {
  
  /**
   * @param stimulation The stimulation to be updated.
   * 
   * @return <code>true</code> if the operation's preconditions are true;
   *         <code>false</code> otherwise.
   */
   public boolean precondition(Stimulation stimulation){
     return stimulation.getStatus() == Stimulation.StimulusStatus.ENDING;
   }
   
   /**
    * Applies the operation.
    * 
    * @param subsystem The stimulation subsystem being changed.
    * @param stimulation The stimulation being updated.
    */
   public void apply(StimulationSubsystem subsystem, int currentInstant, Stimulation stimulation){

     // DON'T remove the ending stimulus from the current ones. This will
     // be done by another operation, when the stimulus becomes absent
     
     // Notify that the stimulus is ending
     subsystem.getStimulusStatus().put(stimulation.getStimulus(), Stimulation.StimulusStatus.ENDING);
     
   }
}
