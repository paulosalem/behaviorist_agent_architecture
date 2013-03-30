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

import organism.general.Probability;
import organism.responding.Action;
import organism.util.UnexpectedCaseException;

public class ResponseEmotionalRegulator implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  
  private FrustrationRegulator_1 frustrationRegulator_1; 
  
  private FrustrationRegulator_2 frustrationRegulator_2;
  

  public ResponseEmotionalRegulator(EmotionSubsystem emotionSubsystem){
    
    frustrationRegulator_1 = new FrustrationRegulator_1(emotionSubsystem);
    frustrationRegulator_2 = new FrustrationRegulator_2(emotionSubsystem);
    
  }
  
  public Probability responseRegulator(Action a, Probability p){
    return frustrationRegulator(a, p);
  }
  
  protected Probability frustrationRegulator(Action a, Probability p){
    if(frustrationRegulator_1.precondition()){
      return frustrationRegulator_1.frustrationRegulator(a, p);
    }
    
    else if (frustrationRegulator_2.precondition()){
      return frustrationRegulator_2.frustrationRegulator(a, p);
    }
    
    else{
      throw new UnexpectedCaseException("An unexpected case has been found while regulating Frustration.");
    }
  }
  
}
