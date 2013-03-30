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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import organism.io.Storable;

/**
 * Manages conflicts between actions.
 * 
 * @author Paulo Salem
 *
 */
public class ActionConflict implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  /**
   * The possible conflicting situations.
   */
  public enum Conflict {Conflicting, Nonconflicting}


  /**
   * A map from actions to actions that conflict with them
   */
  private Map<Action, Set<Action>> conflicts;
  
  private ActionManager actionManager;
  
  public ActionConflict(ActionManager actionManager){
    this.conflicts = new HashMap<Action, Set<Action>>();
    this.actionManager = actionManager;
  }
  
  
  /**
   * Checks whether two actions conflict with each other.
   * 
   * @param a1 The first conflicting action.
   * @param a2 The second conflicting action.
   * 
   * @return <code>Conflicting</code> if they do conflict;
   *         <code>Nonconflicting</code> otherwise.
   */
  public Conflict conflict(Action a1, Action a2){
    
    // Look for conflict in a1's list
    Set<Action> as = conflicts.get(a1);
    if(as != null){
      if(as.contains(a2)){
        return Conflict.Conflicting;
      }
    }
    
    // Symmetrically, look for conflict in a2's list
    as = conflicts.get(a2);
    if(as != null){
      if(as.contains(a1)){
        return Conflict.Conflicting;
      }
    }
    
    // If nothing was found, there is no conflict
    return Conflict.Nonconflicting;
  }
  
  
  /**
   * Adds a new conflicting pair of actions.
   * 
   * @param a1 The first conflicting action.
   * @param a2 The second conflicting action.
   */
  public void addConflict(Action a1, Action a2){

    // add to the a1 conflict list
    Set<Action> as = conflicts.get(a1);
    if(as == null){
      as = new HashSet<Action>();
      conflicts.put(a1, as);
    }
    as.add(a2);
    
    // symmetrically, add to the a2 conflict list
    as = conflicts.get(a2);
    if(as == null){
      as = new HashSet<Action>();
      conflicts.put(a2, as);
    }
    as.add(a1);
  }


  @Override
  public void loadXML(Element xml) {
    
    List<Element> conflictEL = xml.getChildren("conflict");
    
    for(Element conflictE: conflictEL){

      // We assume that the actions are already loaded in ActionManager
      int id1 = Integer.valueOf(conflictE.getAttributeValue("id1"));
      int id2 = Integer.valueOf(conflictE.getAttributeValue("id2"));
      
      Action a1 = actionManager.getPrototypeClone(id1);
      Action a2 = actionManager.getPrototypeClone(id2);
      
      addConflict(a1, a2);
    }
  }


  @Override
  public Element toXML() {
    
    Element conflictsE = new Element("action-conflict");
    
    for(Action a: conflicts.keySet()){
      for(Action b: conflicts.get(a)){
        
        Element conflictE = new Element("conflict");
        conflictsE.addContent(conflictE);
        
        conflictE.setAttribute("id1", Integer.toString(a.getId()));
        conflictE.setAttribute("id2", Integer.toString(b.getId()));
      }
    }
    
    return conflictsE;
  }
  
  
}

