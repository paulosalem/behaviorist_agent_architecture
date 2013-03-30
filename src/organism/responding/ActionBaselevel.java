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
import java.util.Map;

import organism.general.Probability;

/**
 * Defines the base level emission of spontaneous actions.
 * 
 * @author Paulo Salem
 *
 */
public class ActionBaselevel implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  /**
   * The actions available to the organism.
   */
  private Actions actions;
  
  /**
   * A map from actions to the probability of their spontaneous emission.
   */
  private Map<Action, Probability> baseLevel;

  public ActionBaselevel(Actions actions) {
    super();
    
    this.actions = actions;
    actions.setActionBaselevel(this);
    
    this.baseLevel = new HashMap<Action, Probability>();
    for(Action a: actions.getAllActions()){
      Probability p = new Probability(0.0);
      this.baseLevel.put(a, p);
    }
    
  }
  
  public ActionBaselevel(Actions actions, Map<Action, Probability> baseLevel) {
    super();
    
    // TODO check function domain according to the spec!
    
    this.actions = actions;
    actions.setActionBaselevel(this);
    this.baseLevel = baseLevel;
  }
  
  
  /**
   * 
   * @param a An action.
   * @return The base level probability of action emission, if it is defined;
   *         <code>null</code> otherwise.
   */
  public Probability getBaseLevel(Action a){
    return baseLevel.get(a);
  }
  
  public void setBaseLevel(Action a, Probability p){
    baseLevel.put(a, p);
  }


  
}
