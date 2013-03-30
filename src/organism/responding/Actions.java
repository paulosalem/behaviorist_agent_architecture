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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom.Element;
import organism.general.Probability;
import organism.io.Storable;

/**
 * Defines the actions available to the organism. Such actions are associated
 * non-exclusively with particular behavioral classes.
 * 
 * @author Paulo Salem
 *
 */
public class Actions implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  private Set<Action> operantActions;
  
  private Set<Action> reflexActions;
  
  private ActionBaselevel actionBaselevel;
  
  private ActionManager actionManager;

  public Actions(ActionManager actionManager){
    super();
    this.operantActions = new HashSet<Action>();
    this.reflexActions =  new HashSet<Action>();
    this.actionManager = actionManager;
  }
  
  /**
   * 
   * @param operantActions The actions associated with operants.
   * @param reflexActions The actions associated with reflexes.
   */
  public Actions(Set<Action> operantActions, Set<Action> reflexActions, ActionManager actionManager) {
    super();
    this.operantActions = operantActions;
    this.reflexActions = reflexActions;
    this.actionManager = actionManager;
  }

  public Set<Action> getOperantActions() {
    return operantActions;
  }

  public Set<Action> getReflexActions() {
    return reflexActions;
  }

  public Set<Action> getAllActions(){
    
    Set<Action> all = new HashSet<Action>();
    all.addAll(operantActions);
    all.addAll(reflexActions);
    
    return all;
  }

  public void setActionBaselevel(ActionBaselevel actionBaselevel) {
    this.actionBaselevel = actionBaselevel;
  }

  @Override
  public void loadXML(Element xml) {
    List<Element> actionEL = xml.getChildren("action");
    
    for(Element actionE: actionEL){
      
      int id = Integer.valueOf(actionE.getAttributeValue("id"));
      String name = actionE.getAttributeValue("name");
      Probability baseLevel = new Probability(Double.valueOf(actionE.getAttributeValue("base-level")));
      boolean isOperant = Boolean.valueOf(actionE.getAttributeValue("operant"));
      boolean isReflex = Boolean.valueOf(actionE.getAttributeValue("reflex"));
      
      Action a = new Action(id, name);
      
      // Add new action in the manager
      actionManager.putPrototype(a);
      
      // Set the action's base level
      actionBaselevel.setBaseLevel(a, baseLevel);
      
      // Put in the operant and reflex sets as needed
      if(isOperant){
        operantActions.add(a);
      }
      
      if(isReflex){
        reflexActions.add(a);
      }
    }
    
  }

  @Override
  public Element toXML() {
    
    Element actionsE = new Element("actions");
    
    Set<Action> all = new HashSet<Action>();
    all.addAll(operantActions);
    all.addAll(reflexActions);
    
    for(Action a: all){
      Element actionE = new Element("action");
      actionsE.addContent(actionE);
      
      actionE.setAttribute("id", Integer.toString(a.getId()));
      actionE.setAttribute("name", a.getName());
      actionE.setAttribute("base-level", actionBaselevel.getBaseLevel(a).toString());
      
      if(operantActions.contains(a)){
        actionE.setAttribute("operant", "true"); 
      }
      else{
        actionE.setAttribute("operant", "false");
      }
        
      
      if(reflexActions.contains(a)){
        actionE.setAttribute("reflex", "true");  
      }
      else{
        actionE.setAttribute("reflex", "false");
      }
    }
    
    return actionsE;
  }
  
  
  

}
