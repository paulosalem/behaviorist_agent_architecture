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
package organism.responding;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.jdom.Element;
import organism.emotion.EmotionSubsystem;
import organism.general.Intensity;
import organism.io.Storable;
import organism.responding.operant.Operant;
import organism.responding.operant.OperantSubsystem;
import organism.responding.respondent.Reflex;
import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Stimulus;
import organism.util.UnexpectedCaseException;


/**
 * The subsystem responsible for providing behavioral responses.
 * 
 * @author Paulo Salem
 *
 */
public class RespondingSubsystem implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private StimulationSubsystem stimSubsys;
  
  private OperantSubsystem operantSubsys;
  
  private EmotionSubsystem emoSubsys;
  
  private CurrentBehaviors currentBehaviors = new CurrentBehaviors();
  
  private CurrentResponses currentResponses = new CurrentResponses();
  
  private Actions actions;
  
  private ActionHistory actionHistory = new ActionHistory();
  
  private ActionConflict actionConflict;
  
  private ActionBaselevel actionBaselevel;
  
  private Set<Operant> operants;
  
  private Set<Reflex> reflexes;
  
  /**
   * Manages the available actions.
   */
  private ActionManager actionManager = new ActionManager();

  
  public RespondingSubsystem(StimulationSubsystem stimSubsys, OperantSubsystem operantSubsys, EmotionSubsystem emoSubsys, ActionManager actionManager, Actions actions, ActionConflict actionConflict, ActionBaselevel actionBaselevel,  Set<Reflex> reflexes, Set<Operant> operants){
    this.stimSubsys = stimSubsys;
    this.operantSubsys = operantSubsys;
    this.emoSubsys = emoSubsys;
    this.actionManager = actionManager;
    this.actions = actions;
    this.actionConflict = actionConflict;
    this.actionBaselevel = actionBaselevel;
    
    this.reflexes = reflexes;
    this.operants = operants;
    
  }
  
  
  private ReflexSchedulingOp reflexSchedulingOp = new ReflexSchedulingOp();
  private OperantSchedulingOp operantSchedulingOp = new OperantSchedulingOp();
  private BaseLevelSchedulingOp baseLevelSchedulingOp = new BaseLevelSchedulingOp();
  private OperantConflictResolutionOp operantConflictResolutionOp = new OperantConflictResolutionOp();
  private ReflexConflictResolutionOp reflexConflictResolutionOp = new ReflexConflictResolutionOp_Ref2();
  private OperantReflexConflictResolutionOp operantReflexConflictResolutionOp = new OperantReflexConflictResolutionOp();
  private BaseLevelConflictResolutionOp baseLevelConflictResolutionOp = new BaseLevelConflictResolutionOp();
  private OperantEmissionOp operantEmissionOp = new OperantEmissionOp();
  private ReflexElicitationOp reflexElicitationOp = new ReflexElicitationOp();
  private BaseLevelEmissionOp baseLevelEmissionOp = new BaseLevelEmissionOp();
  private InactiveResponseUpdateOp_1 inactiveResponseUpdateOp_1 = new InactiveResponseUpdateOp_1();
  private InactiveResponseUpdateOp_2 inactiveResponseUpdateOp_2 = new InactiveResponseUpdateOp_2();
  private ActiveResponseUpdateOp activeResponseUpdateOp = new ActiveResponseUpdateOp();
  private ResponseTerminationOp_1 responseTerminationOp_1 = new ResponseTerminationOp_1();
  private ResponseTerminationOp_2 responseTerminationOp_2 = new ResponseTerminationOp_2();
  private ResponseTerminationOp_3 responseTerminationOp_3 = new ResponseTerminationOp_3();

  /**
   * Schedules reflexive behavioral responses.
   * 
   * @param s The stimulus that might elicit a reflex.
   * @param i The intensity of the stimulation.
   */
  public void performReflexScheduling(Stimulus s, Intensity i){
    if(reflexSchedulingOp.precondition(this, stimSubsys, s, i)){
      reflexSchedulingOp.apply(this, stimSubsys, s, i);
    }
  }
  
  /**
   * Schedules operant behavioral responses.
   * 
   */
  public void performOperantScheduling(){
    if(operantSchedulingOp.precondition(this, stimSubsys, operantSubsys.getOperantUtility())){
      operantSchedulingOp.apply(this, stimSubsys, operantSubsys.getOperantUtility());
    }
  }
  
  /**
   * Schedules spontaneous behavioral responses.
   * 
   * @param instant The current instant.
   */
  public void performBaseLevelScheduling(int instant){
    if(baseLevelSchedulingOp.precondition(this)){
      baseLevelSchedulingOp.apply(this, emoSubsys);
    }  
  }
  
  /**
   * Solves conflicts among scheduled behaviors.
   */
  public void performConflictResolution(){
    
    if(operantConflictResolutionOp.precondition(this, stimSubsys, operantSubsys.getOperantUtility()) 
        && reflexConflictResolutionOp.precondition(this)
        && operantReflexConflictResolutionOp.precondition(this)
        && baseLevelConflictResolutionOp.precondition(this)){
      
      operantConflictResolutionOp.apply(this, stimSubsys, operantSubsys.getOperantUtility());
      reflexConflictResolutionOp.apply(this);
      operantReflexConflictResolutionOp.apply(this);
      baseLevelConflictResolutionOp.apply(this);
    }
    
    else{
      throw new UnexpectedCaseException("Could not perform conflic resolution.");
    }
  }
  
  /**
   * Emits the specified operant if possible.
   * 
   * @param o The operant to be emitted.
   */
  public void performOperantEmission(Operant o, int instant){
    if(operantEmissionOp.precondition(this, o, instant)){
      operantEmissionOp.apply(this, o, instant);
    }
  }
  
  /**
   * Elicits the specified reflex if possible.
   * 
   * @param r The reflex to be elicited.
   */
  public void performReflexElicitation(Reflex r, int instant){
    if(reflexElicitationOp.precondition(this, r, instant)){
      reflexElicitationOp.apply(this, r, instant);
    }
  }

  /**
   * Emits the specified action if possible.
   * 
   * @param a The action to be emitted.
   */
  public void performBaseLevelEmission(Action a, int instant){
    if(baseLevelEmissionOp.precondition(this, a, instant)){
      baseLevelEmissionOp.apply(this, a, instant);
    }
  }
  
  /**
   * Updates the specified response.
   * 
   * @param rp The response to be updated.
   */
  public void performResponseUpdate(Response rp){
    
    if(inactiveResponseUpdateOp_1.precondition(currentResponses, rp)){
      inactiveResponseUpdateOp_1.apply(currentResponses, rp);
    }
    
    // Or
    
    if(inactiveResponseUpdateOp_2.precondition(currentResponses, rp)){
      inactiveResponseUpdateOp_2.apply(currentResponses, rp);
    }
    
    // Or
    
    if(activeResponseUpdateOp.precondition(currentResponses, rp)){
      activeResponseUpdateOp.apply(currentResponses, rp);
    }

  }
  
  
  /**
   * Terminates the specified response.
   * 
   * @param rp The response to be terminated.
   */
  public void performResponseTermination(Response rp){

    if(responseTerminationOp_1.precondition(currentResponses, rp)){
      responseTerminationOp_1.apply(currentResponses, currentBehaviors, rp);
    }
    
    // Or

    if(responseTerminationOp_2.precondition(currentResponses, currentBehaviors, rp)){
      responseTerminationOp_2.apply(currentResponses, currentBehaviors, rp);
    }    
    
    // Or
    
    if(responseTerminationOp_3.precondition(currentResponses, currentBehaviors, rp)){
      responseTerminationOp_3.apply(currentResponses, currentBehaviors, rp);
    }
  }
  
  public void performTOperantOp(Operant operant, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    operantSubsys.performTOperantOp(operant, discriminativeStimuli, consequence, action, delay);  
  }
  
  public Operant performTOperantFormationOp(Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    return operantSubsys.performTOperantFormationOp(discriminativeStimuli, consequence, action, delay);
  }
  
  public void performTOperantEliminationOp(Operant o){
	  operantSubsys.performTOperantEliminationOp(o);
  }

  public Set<Operant> getOperants() {
    return operants;
  }

  public Set<Reflex> getReflexes() {
    return reflexes;
  }

  public CurrentBehaviors getCurrentBehaviors() {
    return currentBehaviors;
  }

  public ActionBaselevel getActionBaselevel() {
    return actionBaselevel;
  }

  public Actions getActions() {
    return actions;
  }

  public ActionConflict getActionConflict() {
    return actionConflict;
  }

  public CurrentResponses getCurrentResponses() {
    return currentResponses;
  }

  public ActionHistory getActionHistory() {
    return actionHistory;
  }

  public ActionManager getActionManager() {
    return actionManager;
  }

  @Override
  public void loadXML(Element xml) {
    actions.loadXML(xml.getChild("actions"));
    actionConflict.loadXML(xml.getChild("action-conflict"));
    
    List<Element> operantEL = xml.getChild("operants").getChildren("operant");
    for(Element operantE: operantEL){
      Operant operant = new Operant(actionManager, stimSubsys.getStimulusManager());
      operant.loadXML(operantE);
      operants.add(operant);
    }
    
    List<Element> reflexEL = xml.getChild("reflexes").getChildren("reflex");
    for(Element reflexE: reflexEL){
      Reflex reflex = new Reflex(actionManager, stimSubsys.getStimulusManager());
      reflex.loadXML(reflexE);
      reflexes.add(reflex);
    }
  }

  @Override
  public Element toXML() {
    
    Element subsystemE = new Element("responding-subsystem");
    
    subsystemE.addContent(actions.toXML());
    
    subsystemE.addContent(actionConflict.toXML());
    
    
    // Operants
    Element operantsE = new Element("operants");
    subsystemE.addContent(operantsE);
    for(Operant o: operants){
      operantsE.addContent(o.toXML());
    }
    
    // Reflexes
    Element reflexesE = new Element("reflexes");
    subsystemE.addContent(reflexesE);
    for(Reflex r: reflexes){
      reflexesE.addContent(r.toXML());
    }
    
    return subsystemE;
  }  
  
  
  
  
}
