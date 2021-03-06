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
package organism.responding.respondent;

import java.io.Serializable;

import org.jdom.Element;
import organism.general.Intensity;
import organism.general.Probability;
import organism.io.Storable;
import organism.responding.Action;
import organism.responding.ActionManager;
import organism.responding.Behavior;
import organism.stimulation.Stimulus;
import organism.stimulation.StimulusManager;

/**
 * Respondent behavior implementation.
 * 
 * @author Paulo Salem
 *
 */
public class Reflex extends Behavior implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected ReflexParameters parameters;
  
  protected Stimulus antecedent;
  
  protected Action action;
  
  protected Intensity threshold;
  
  protected Probability elicitation;
  
  // TODO check this name 
  protected Intensity strength;
  
  protected int duration;
  
  protected int latency;

  protected ActionManager actionManager;
  
  protected StimulusManager stimulusManager;

  public Reflex(ActionManager actionManager, StimulusManager stimulusManager){
    super();
    this.actionManager = actionManager;
    this.stimulusManager = stimulusManager;
  }
  
  
  public Reflex(ReflexParameters parameters,
      Stimulus antecedent, Action action, Intensity threshold,
      Probability elicitation, Intensity strength, int duration, int latency,
      ActionManager actionManager, StimulusManager stimulusManager) {
    super();
    this.parameters = parameters;
    this.antecedent = antecedent;
    this.action = action;
    this.threshold = threshold;
    this.elicitation = elicitation;
    this.strength = strength;
    this.duration = duration;
    this.latency = latency;
    
    this.actionManager = actionManager;
    this.stimulusManager = stimulusManager;
  }

  public Action getAction() {
    return action;
  }

  public ReflexParameters getParameters() {
    return parameters;
  }


  public Stimulus getAntecedent() {
    return antecedent;
  }


  public Intensity getThreshold() {
    return threshold;
  }


  public Probability getElicitation() {
    return elicitation;
  }


  public Intensity getStrength() {
    return strength;
  }


  public int getDuration() {
    return duration;
  }


  public int getLatency() {
    return latency;
  }


  public void setParameters(ReflexParameters parameters) {
    this.parameters = parameters;
  }

  public void setAntecedent(Stimulus antecedent) {
    this.antecedent = antecedent;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public void setThreshold(Intensity threshold) {
    this.threshold = threshold;
  }

  public void setElicitation(Probability elicitation) {
    this.elicitation = elicitation;
  }

  public void setStrength(Intensity strength) {
    this.strength = strength;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public void setLatency(int latency) {
    this.latency = latency;
  }
  
  @Override
  public String toString(){
	return "(" +
			antecedent +
			"->"+
			action+
			")";
  }

  @Override
  public void loadXML(Element xml) {

    parameters = new ReflexParameters();
    parameters.loadXML(xml.getChild("reflex-parameters"));
    
    Element antecedentE = xml.getChild("antecedent-stimulus");
    antecedent = stimulusManager.getPrototypeClone(Integer.parseInt(antecedentE.getAttributeValue("id")));
    
    Element actionE = xml.getChild("action");
    action = actionManager.getPrototypeClone(Integer.parseInt(actionE.getAttributeValue("id")));
    
    Element thresholdE = xml.getChild("threshold");
    threshold = new Intensity(Double.parseDouble(thresholdE.getAttributeValue("value")));
    
    Element elicitationE = xml.getChild("elicitation");
    elicitation = new Probability(Double.parseDouble(elicitationE.getAttributeValue("value")));
    
    Element strengthE = xml.getChild("strength");
    strength = new Intensity(Double.parseDouble(strengthE.getAttributeValue("value")));
    
    Element durationE = xml.getChild("duration");
    duration = Integer.parseInt(durationE.getAttributeValue("value"));
    
    Element latencyE = xml.getChild("latency");
    latency = Integer.parseInt(latencyE.getAttributeValue("value"));
  }

  @Override
  public Element toXML() {
    
    Element reflexE = new Element("reflex");
    
    reflexE.addContent(parameters.toXML());
    
    Element antecedentE = new Element("antecedent-stimulus");
    reflexE.addContent(antecedentE);
    antecedentE.setAttribute("id", Integer.toString(antecedent.getId()));
    
    Element actionE = new Element("action");
    reflexE.addContent(actionE);
    actionE.setAttribute("id", Integer.toString(action.getId()));
    
    Element thresholdE = new Element("threshold");
    reflexE.addContent(thresholdE);
    thresholdE.setAttribute("value", threshold.toString());
    
    Element elicitationE = new Element("elicitation");
    reflexE.addContent(elicitationE);
    elicitationE.setAttribute("value", elicitation.toString());
    
    Element strengthE = new Element("strength");
    reflexE.addContent(strengthE);
    strengthE.setAttribute("value", strength.toString());
    
    Element durationE = new Element("duration");
    reflexE.addContent(durationE);
    durationE.setAttribute("value", Integer.toString(duration));
    
    
    Element latencyE = new Element("latency");
    reflexE.addContent(latencyE);
    latencyE.setAttribute("value", Integer.toString(latency));
    

    return reflexE;
  }
  
  
}
