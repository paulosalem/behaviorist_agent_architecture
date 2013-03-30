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
import java.util.Map;
import java.util.Set;

/**
 * A record of action execution.
 * 
 * @author Paulo Salem
 *
 */
public class ActionHistory implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  private Map<Integer, Set<Action>> actionsHistory;
  
  public ActionHistory(){
    actionsHistory = new HashMap<Integer, Set<Action>>();
  }
  
  
  public void addAction(int instant, Action a){
    
    Set<Action> as = actionsHistory.get(instant);
    
    if(as == null){
      as = new HashSet<Action>();
      actionsHistory.put(instant, as);
    }
    
    as.add(a);
  }
  
  public Set<Action> getActions(int instant){
 
    Set<Action> as = actionsHistory.get(instant);
    
    if(as == null){
      as = new HashSet<Action>();
      actionsHistory.put(instant, as);
    }
    
    return as;
  }
}
