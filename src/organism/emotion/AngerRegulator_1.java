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
package organism.emotion;

import java.io.Serializable;

import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Stimulus;
import organism.stimulation.Utility;

public class AngerRegulator_1 implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  private EmotionSubsystem emoSubsys;
  
  private StimulationSubsystem stimSubsys;
  
  public AngerRegulator_1(EmotionSubsystem emoSubsys, StimulationSubsystem stimSubsys){
    this.emoSubsys = emoSubsys;
    this.stimSubsys = stimSubsys;
  }
  
  public boolean precondition(){
    if(emoSubsys.getAnger().getStatus() == Emotion.EmotionStatus.ACTIVE){
      return true;
    }
    
    return false;
  }
  
  public Utility angerRegulator(Stimulus s, Utility u){
    Anger anger = emoSubsys.getAnger();
    
    if(stimSubsys.getStimulationHints().getPainHints().contains(s)){
      return Utility.valueOf(u.add(anger.utilityChange(anger.getIntensity())));
    }
    else{
      return u;
    }
  }
}
