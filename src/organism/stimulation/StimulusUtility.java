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

import java.io.Serializable;

import organism.drive.DriveSubsystem;
import organism.emotion.EmotionSubsystem;

/**
 * Provides the utility function used by the organism.
 * 
 * @author Paulo Salem
 *
 */
public abstract class StimulusUtility implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * The main subsystem that it depends on.
   */
  protected StimulationSubsystem stimulationSubsystem;
  
  /**
   * The emotion subsystem, which shall provide emotional regulators.
   */
  protected EmotionSubsystem emotionSubsystem;
  
  /**
   * The drive subsystem, which shall provide drive regulators.
   */
  protected DriveSubsystem driveSubsystem;
  
  
  public StimulusUtility(StimulationSubsystem stimulationSubsystem, EmotionSubsystem emotionSubsystem, DriveSubsystem driveSubsystem){
    this.stimulationSubsystem = stimulationSubsystem;
    this.emotionSubsystem = emotionSubsystem;
    this.driveSubsystem = driveSubsystem;
  }
  
  /**
   * Calculates the utility of the specified stimulus.
   * 
   * @param s The stimulus whose utility is desired.
   * 
   * @return The utility of the stimulus.
   */
  public abstract Utility utility(Stimulus s);

  public void setEmotionSubsystem(EmotionSubsystem emotionSubsystem) {
    this.emotionSubsystem = emotionSubsystem;
  }
  
  
  
}
