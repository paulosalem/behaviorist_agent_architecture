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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import organism.io.Storable;

/**
 * Stimulation parameters that give each organism its unique "personality" concerning
 * stimulation.
 * 
 * @author Paulo Salem
 *
 */
public class StimulationParameters implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Stimulation hints available to the organism.
   */
  private StimulationHints stimulationHints;
  
  /**
   * All the stimuli that the organism is able to perceive.
   */
  private Set<Stimulus> stimuli;
  
  /**
   * The set of stimuli that are naturally pleasant or painful to
   * the organism.
   */
  private Set<Stimulus> primaryStimuli;
  
  /**
   * A map from primary stimuli to their primary utilities.
   */
  private Map<Stimulus, Utility> primaryUtility;
  
  /**
   * The maximum delay between stimuli so that stimulus conditioning 
   * may take place.
   */
  private int maxDelay;
  
  private StimulusManager stimulusManager;
  

  public StimulationParameters(StimulusManager stimulusManager){
    super();
    
    this.stimulationHints = new StimulationHints(stimulusManager);
    this.stimuli = new HashSet<Stimulus>();
    this.primaryStimuli = new HashSet<Stimulus>();
    this.primaryUtility = new HashMap<Stimulus, Utility>();
    this.maxDelay = 0;
    this.stimulusManager = stimulusManager;
  }
  
  public StimulationParameters(StimulationHints stimulationHints,
      Set<Stimulus> stimuli, Set<Stimulus> primaryStimuli, 
      Map<Stimulus, Utility> primaryUtility, int maxDelay,
      StimulusManager stimulusManager) {
    super();
    this.stimulationHints = stimulationHints;
    this.stimuli = stimuli;
    this.primaryStimuli = primaryStimuli;
    this.primaryUtility = primaryUtility;
    this.maxDelay = maxDelay;
    this.stimulusManager = stimulusManager;
  }

  public StimulationHints getStimulationHints() {
    return stimulationHints;
  }

  public Set<Stimulus> getStimuli() {
    return stimuli;
  }

  public Set<Stimulus> getPrimaryStimuli() {
    return primaryStimuli;
  }

  
  public Map<Stimulus, Utility> getPrimaryUtility() {
    return primaryUtility;
  }

  public int getMaxDelay() {
    return maxDelay;
  }

  @Override
  public void loadXML(Element xml) {
    stimulationHints.loadXML(xml.getChild("stimulation-hints"));
    
    List<Element> stimuliLE = xml.getChild("stimuli").getChildren();
    stimuli.clear();
    for(Element stimulusE: stimuliLE){
       Stimulus stimulus = new Stimulus(0); // A dummy ID. We will load the real one below
       stimulus.loadXML(stimulusE);
       
       // Make available
       stimuli.add(stimulus);
       
       // Register prototype
       stimulusManager.putPrototype(stimulus);
      
       boolean isPrimary = Boolean.parseBoolean(stimulusE.getAttributeValue("primary"));
       
       if(isPrimary){
         Utility utility = new Utility(Double.parseDouble(stimulusE.getAttributeValue("utility")));
         
         // Add to primary stimuli and set its utility
         primaryStimuli.add(stimulus);
         primaryUtility.put(stimulus, utility);
       }
    }
    
    Element maxDelayE = xml.getChild("max-delay");
    maxDelay = Integer.parseInt(maxDelayE.getAttributeValue("value"));
    
  }

  @Override
  public Element toXML() {
    
    Element parametersE = new Element("stimulation-parameters");

    parametersE.addContent(stimulationHints.toXML());
    
    Element stimuliE = new Element("stimuli");
    parametersE.addContent(stimuliE);
    for(Stimulus s: stimuli){
      Element sE = s.toXML();
      
      if(primaryStimuli.contains(s)){
        sE.setAttribute("primary", "true");
        sE.setAttribute("utility", primaryUtility.get(s).toString());
      }
      else{
        sE.setAttribute("primary", "false");
      }
      
      stimuliE.addContent(sE);
    }

    Element maxDelayE = new Element("max-delay");
    parametersE.addContent(maxDelayE);
    maxDelayE.setAttribute("value", Integer.toString(maxDelay));
    

    return parametersE;

  }


  
  
  
}
