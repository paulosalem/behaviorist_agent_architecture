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

import org.jdom.Element;
import organism.emotion.Emotion.EmotionStatus;
import organism.general.Intensity;
import organism.io.Storable;
import organism.stimulation.StimulationSubsystem;

/**
 * Subsystem that manages the organism's emotional state.
 * 
 * @author Paulo Salem
 *
 */
public class EmotionSubsystem implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected Anger anger = new Anger();
  
  protected Depression depression = new Depression();
  
  protected Frustration frustration = new Frustration();
  
  protected StimulusEmotionalRegulator stimulusEmotionalRegulator;
  
  protected ResponseEmotionalRegulator responseEmotionalRegulator;
  
  private StartDepressionOp startDepressionOp = new StartDepressionOp(); 
  private StartAngerOp startAngerOp = new StartAngerOp();
  private StartFrustrationOp startFrustrationOp = new StartFrustrationOp();
  
  private UpdateDepressionOp updateDepressionOp = new UpdateDepressionOp(); 
  private UpdateAngerOp updateAngerOp = new UpdateAngerOp();
  private UpdateFrustrationOp updateFrustrationOp = new UpdateFrustrationOp();
  
  private EndDepressionOp endDepressionOp = new EndDepressionOp(); 
  private EndAngerOp endAngerOp = new EndAngerOp();
  private EndFrustrationOp endFrustrationOp = new EndFrustrationOp();
  
  
  public EmotionSubsystem(StimulationSubsystem stimulationSubsystem){
    stimulationSubsystem.getStimulusUtility().setEmotionSubsystem(this);
    this.stimulusEmotionalRegulator = new StimulusEmotionalRegulator(this, stimulationSubsystem);
    this.responseEmotionalRegulator = new ResponseEmotionalRegulator(this);
  }
  
  /////////////////////////////////////////////////////////////////////////////
  // Start operations
  /////////////////////////////////////////////////////////////////////////////
  
  public void performStartDepressionOp(Intensity intensity, int duration){
    if(startDepressionOp.precondition()){
      startDepressionOp.apply(this, intensity, duration);
    }
  }
  
  public void performStartAngerOp(Intensity intensity, int duration){
    if(startAngerOp.precondition()){
      startAngerOp.apply(this, intensity, duration);
    }
  }
  
  public void performStartFrustrationOp(Intensity intensity, int duration){
    if(startFrustrationOp.precondition()){
      startFrustrationOp.apply(this, intensity, duration);
    }
  }
  
  
  /////////////////////////////////////////////////////////////////////////////
  // Update operations
  /////////////////////////////////////////////////////////////////////////////
  
  
  public void performUpdateDepressionOp(){
    if(updateDepressionOp.precondition(this)){
      updateDepressionOp.apply(this);
    }
  }
  
  public void performUpdateAngerOp(){
    if(updateAngerOp.precondition(this)){
      updateAngerOp.apply(this);
    }
  }

  public void performUpdateFrustrationOp(){
    if(updateFrustrationOp.precondition(this)){
      updateFrustrationOp.apply(this);
    }
  }

  
  /////////////////////////////////////////////////////////////////////////////
  // End operations
  /////////////////////////////////////////////////////////////////////////////
  
  public void performEndDepressionOp(){
    if(endDepressionOp.precondition(this)){
      endDepressionOp.apply(this);
    }
  }

  public void performEndAngerOp(){
    if(endAngerOp.precondition(this)){
      endAngerOp.apply(this);
    }
  }
  
  public void performEndFrustrationOp(){
    if(endFrustrationOp.precondition(this)){
      endFrustrationOp.apply(this);
    }  
    
  }
  
  
  

  public Anger getAnger() {
    return anger;
  }

  public Depression getDepression() {
    return depression;
  }

  public Frustration getFrustration() {
    return frustration;
  }
  
  public StimulusEmotionalRegulator getStimulusEmotionalRegulator() {
    return stimulusEmotionalRegulator;
  }

  public ResponseEmotionalRegulator getResponseEmotionalRegulator() {
    return responseEmotionalRegulator;
  }
  
  
  
  /////////////////////////////////////////////////////////////////////////////
  // XML storage
  /////////////////////////////////////////////////////////////////////////////



  @Override
  public void loadXML(Element xml) {
    Element angerE = xml.getChild("anger");
    fromXMLEmotion(anger, angerE);
    
    Element depressionE = xml.getChild("depression");
    fromXMLEmotion(depression, depressionE);
    
    Element frustrationE = xml.getChild("frustration");
    fromXMLEmotion(frustration, frustrationE);
  }

  @Override
  public Element toXML() {
    
    Element subsysE = new Element("emotion-subsystem");

    Element angerE = new Element("anger");
    subsysE.addContent(angerE);
    toXMLEmotion(anger, angerE);
    
    Element depressionE = new Element("depression");
    subsysE.addContent(depressionE);
    toXMLEmotion(depression, depressionE);
    
    Element frustrationE = new Element("frustration");
    subsysE.addContent(frustrationE);
    toXMLEmotion(frustration, frustrationE);
    
	return subsysE;
  }
  
  private Element toXMLEmotion(Emotion emotion, Element element){
    element.setAttribute("status", emotion.getStatus().toString());
    element.setAttribute("intensity", emotion.getIntensity().toString());
    element.setAttribute("duration", Integer.toString(emotion.getDuration()));
    
    return element;
  }
  
  private void fromXMLEmotion(Emotion emotion, Element element){
    EmotionStatus status = EmotionStatus.valueOf(element.getAttributeValue("status"));
    Intensity intensity = new Intensity(Double.valueOf(element.getAttributeValue("intensity")));
    int duration = Integer.valueOf(element.getAttributeValue("duration"));
    
    emotion.setStatus(status);
    emotion.setIntensity(intensity);
    emotion.setDuration(duration);
  }
  

  
  
  
}
