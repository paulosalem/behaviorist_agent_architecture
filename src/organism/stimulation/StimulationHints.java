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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom.Element;
import organism.io.Storable;

/**
 * When other organisms produce certain stimuli, it might be possible to
 * infer something about their internal state. We call these stimuli
 * "hints" because they help the organism guess what others are
 * "feeling".
 * 
 * @author Paulo Salem
 */
public class StimulationHints implements Storable, Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Stimuli that signal pleasure in another organism.
   */
  private Set<Stimulus> pleasureHints;
  
  /**
   * Stimuli that signal pain in another organism.
   */
  private Set<Stimulus> painHints;

  
  private StimulusManager stimulusManager;
  
  public StimulationHints(StimulusManager stimulusManager){
    super();
    
    this.pleasureHints = new HashSet<Stimulus>();
    this.painHints = new HashSet<Stimulus>();
    this.stimulusManager = stimulusManager;
    
  }
  
  public StimulationHints(Set<Stimulus> pleasureHints, Set<Stimulus> painHints, StimulusManager stimulusManager) {
    super();
    this.pleasureHints = pleasureHints;
    this.painHints = painHints;
    this.stimulusManager = stimulusManager;
  }


  public Set<Stimulus> getPleasureHints() {
    return pleasureHints;
  }


  public Set<Stimulus> getPainHints() {
    return painHints;
  }


  @Override
  public void loadXML(Element xml) {
    
    List<Element> pleasureStimulusEL = xml.getChild("pleasure-hints").getChildren("stimulus");
    for(Element stimulusE: pleasureStimulusEL){
      Stimulus stimulus = stimulusManager.getPrototypeClone(Integer.parseInt(stimulusE.getAttributeValue("id")));
      pleasureHints.add(stimulus);
    }
    
    List<Element> painStimulusEL = xml.getChild("pain-hints").getChildren("stimulus");
    for(Element stimulusE: painStimulusEL){
      Stimulus stimulus = stimulusManager.getPrototypeClone(Integer.parseInt(stimulusE.getAttributeValue("id")));
      painHints.add(stimulus);
    }
    
  }


  @Override
  public Element toXML() {
    
    Element stimulationHintsE = new Element("stimulation-hints");
    
    Element pleasureE = new Element("pleasure-hints");
    stimulationHintsE.addContent(pleasureE);
    
    for(Stimulus s: pleasureHints){
      Element sE = new Element("stimulus");
      pleasureE.addContent(sE);
      sE.setAttribute("id", Integer.toString(s.getId()));
    }
    
    
    Element painE = new Element("pain-hints");
    stimulationHintsE.addContent(painE);
    
    for(Stimulus s: painHints){
      Element sE = new Element("stimulus");
      painE.addContent(sE);
      sE.setAttribute("id", Integer.toString(s.getId()));
    }
    
    return stimulationHintsE;
  }
  
  

}
