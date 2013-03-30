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
import organism.util.UnexpectedCaseException;

public class StimulusEmotionalRegulator implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private DepressionRegulator_1 depressionRegulator_1;
  private DepressionRegulator_2 depressionRegulator_2;
  
  private AngerRegulator_1 angerRegulator_1;
  private AngerRegulator_2 angerRegulator_2;
  
  public StimulusEmotionalRegulator(EmotionSubsystem emoSubsys, StimulationSubsystem stimSubsys){
  
    depressionRegulator_1 = new DepressionRegulator_1(emoSubsys);
    depressionRegulator_2 = new DepressionRegulator_2(emoSubsys);
    
    angerRegulator_1 = new AngerRegulator_1(emoSubsys, stimSubsys);
    angerRegulator_2 = new AngerRegulator_2(emoSubsys, stimSubsys);
  }
  
  public Utility emotionalRegulator(Stimulus s, Utility u){
    return(angerRegulator(s, depressionRegulator(s, u)));
  }
  
  protected Utility depressionRegulator(Stimulus s, Utility u){
   
    if(depressionRegulator_1.precondition()){
      return depressionRegulator_1.depressionRegulator(s, u);
    }
    
    else if (depressionRegulator_2.precondition()){
      return depressionRegulator_2.depressionRegulator(s, u);
    }
    
    else{
      throw new UnexpectedCaseException("An unexpected case has been found while regulating Depression.");
    }
    
  }
  
  protected Utility angerRegulator(Stimulus s, Utility u){
    if(angerRegulator_1.precondition()){
      return angerRegulator_1.angerRegulator(s, u);
    }
    
    else if (angerRegulator_2.precondition()){
      return angerRegulator_2.angerRegulator(s, u);
    }
    
    else{
      throw new UnexpectedCaseException("An unexpected case has been found while regulating Anger.");
    }
  }
}
