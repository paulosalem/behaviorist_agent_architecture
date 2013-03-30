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

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import organism.general.Correlation;
import organism.io.Storable;
import organism.responding.Action;
import organism.responding.ActionManager;
import organism.responding.Behavior;
import organism.stimulation.Stimulus;
import organism.stimulation.StimulusManager;

/**
 * Operant behavior implementation.
 * 
 * @author Paulo Salem
 *
 */
public class Operant extends Behavior implements Storable, Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * A set of sets of stimuli. Each of these stimuli sets correspond to a 
   * potential group of antecedents.
   */
  protected Set<Set<Stimulus>> antecedents;
  
  /**
   * The action that that has to be performed in order to obtain the 
   * stimulus consequence.
   */
  protected Action action;
  
  /**
   * The stimulus consequence that, together with the action, defines the operant.
   */
  protected Stimulus consequence;
  
  /**
   * A map from sets of antecendent stimuli to correlations that indicate
   * the belief that, given a set of antecedents, the consequence
   * will follow through the action.
   */
  protected Map<Set<Stimulus>, Correlation> consequenceContingency;

  protected ActionManager actionManager;
  
  protected StimulusManager stimulusManager;
  
  
  public Operant(ActionManager actionManager, StimulusManager stimulusManager) {
    super();
    this.antecedents = new HashSet<Set<Stimulus>>();
    this.action = null;
    this.consequence = null;
    
    this.consequenceContingency = new HashMap<Set<Stimulus>, Correlation>();
    
    this.actionManager = actionManager;
    this.stimulusManager = stimulusManager;
  }
  
  public Operant(Set<Set<Stimulus>> antecedents, Action action,
      Stimulus consequence, 
      ActionManager actionManager, StimulusManager stimulusManager) {
    super();
    this.antecedents = antecedents;
    this.action = action;
    this.consequence = consequence;
    
    
    this.consequenceContingency = new HashMap<Set<Stimulus>, Correlation>();
    
    this.actionManager = actionManager;
    this.stimulusManager = stimulusManager;
  }
  
  public Operant(Set<Set<Stimulus>> antecedents, Action action,
	      Stimulus consequence, Map<Set<Stimulus>, Correlation> consequenceContingency,
	      ActionManager actionManager, StimulusManager stimulusManager) {
	    super();
	    this.antecedents = antecedents;
	    this.action = action;
	    this.consequence = consequence;
	    this.consequenceContingency = consequenceContingency;
	    
	    this.actionManager = actionManager;
	    this.stimulusManager = stimulusManager;
	  }

  
  /**
   * Checks whether this operant can be discriminated.
   * 
   * @return <code>true</code> if there is some antecedent stimuli set that discriminates this operant;
   *         <code>false</code> otherwise.
   */
  public boolean canBeDiscriminated(){
	  
	  // If there is at least one contingency which correlates with the consequence
	 for(Correlation c: consequenceContingency.values()){
		if(c.greaterThan(Correlation.MIN)){
			return true;
		}
	 }
	 
	 return false;
  }



  public Set<Set<Stimulus>> getAntecedents() {
    return antecedents;
  }




  public Action getAction() {
    return action;
  }




  public Stimulus getConsequence() {
    return consequence;
  }




  public Map<Set<Stimulus>, Correlation> getConsequenceContingency() {
    return consequenceContingency;
  }

  /**
   * Two operants are equal iff they have the same action and the
   * same consequence. 
   */
  @Override
  public boolean equals(Object obj){
    if(obj instanceof Operant){
    	Operant o = (Operant) obj;
      
      if(action.equals(o.getAction())
    		  && consequence.equals(o.getConsequence())){
        return true;
      }
    }
    
    return false;
  }
  
  @Override
  public int hashCode(){
    return action.hashCode() + consequence.hashCode();
  }
  
  @Override
  public String toString(){
	return "(" + 
	         action +
			"->" +
			consequence +
			")";  
  }

  @Override
  public void loadXML(Element xml) {
    Element actionE = xml.getChild("action");
    Element consequenceE = xml.getChild("consequence");
    List<Element> antecedentEL = xml.getChild("antecedents").getChildren("antecedent");
    
    // Set action
    this.action = actionManager.getPrototypeClone(Integer.valueOf(actionE.getAttributeValue("id")));
    
    // Set consequence
    this.consequence = stimulusManager.getPrototypeClone(Integer.valueOf(consequenceE.getAttributeValue("id")));
    
    
    // Antecedent stimuli
    
    this.antecedents = new HashSet<Set<Stimulus>>();
    this.consequenceContingency = new HashMap<Set<Stimulus>, Correlation>();
    
    for(Element antecedentE: antecedentEL){
      List<Element> stimulusEL = antecedentE.getChildren("stimulus");
      
      // Set one possible antecedent
      Set<Stimulus> antecedent = new HashSet<Stimulus>();
      for(Element stimulusE: stimulusEL){
        Stimulus stimulus = stimulusManager.getPrototypeClone(Integer.valueOf(stimulusE.getAttributeValue("id")));
        antecedent.add(stimulus);
      }
      this.antecedents.add(antecedent);
      
      // Set this antecedent's contingency
      Correlation contingency = new Correlation(Double.valueOf(antecedentE.getAttributeValue("contingency")));
      this.consequenceContingency.put(antecedent, contingency);
    }
    
  }

  @Override
  public Element toXML() {
    
    Element operantE = new Element("operant");
    
    Element actionE = new Element("action");
    operantE.addContent(actionE);
    actionE.setAttribute("id", Integer.toString(action.getId()));
    
    Element consequenceE = new Element("consequence");
    operantE.addContent(consequenceE);    
    consequenceE.setAttribute("id", Integer.toString(consequence.getId()));
    
    Element antecedentsE = new Element("antecedents");
    operantE.addContent(antecedentsE);
    for(Set<Stimulus> ss: antecedents){
      
      Element antecedentE = new Element("antecedent");
      antecedentsE.addContent(antecedentE);

      // Set antecent stimuli
      for(Stimulus s: ss){
        Element stimulusE = new Element("stimulus");
        antecedentE.addContent(stimulusE);
        stimulusE.setAttribute("id", Integer.toString(s.getId()));
      }
      
      // Set contingency
      antecedentE.setAttribute("contingency", consequenceContingency.get(ss).toString());
    }
    
    return operantE;
  }
  
  

  
  
  
  
  

}
