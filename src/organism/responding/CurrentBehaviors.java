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
import java.util.Set;

import organism.responding.operant.Operant;
import organism.responding.respondent.Reflex;

/**
 * Stores the behaviors currently scheduled for execution. These behaviors
 * will not necessarily be executed, because conflict resolution procedures
 * might cancel them.
 * 
 * @author Paulo Salem
 *
 */
public class CurrentBehaviors implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Set<Reflex> elicited = new HashSet<Reflex>();
  
  private Set<Operant> emitted = new HashSet<Operant>();
  
  private Set<Action> spontaneous = new HashSet<Action>();
  
  
  public void addReflex(Reflex r){
    elicited.add(r);
  }
  
  public void addOperant(Operant o){
    emitted.add(o);
  }
  
  public void addSpontaneousAction(Action a){
    spontaneous.add(a);
  }

  public void removeReflex(Reflex r){
    elicited.remove(r);
  }
  
  public void removeReflexes(Set<Reflex> rs){
	    for(Reflex r: rs){
	    	removeReflex(r);
	    }
  }
  
  public void removeOperant(Operant o){
    emitted.remove(o);
  }
  
  public void removeOperants(Set<Operant> os){
	    for(Operant o: os){
	    	removeOperant(o);
	    }
  }
  
  public void removeSpontaneousAction(Action a){
    spontaneous.remove(a);
  }

  public void removeSpontaneousActions(Set<Action> as){
	    for(Action a: as){
	    	removeSpontaneousAction(a);
	    }
   }

  
  public Set<Reflex> getElicited() {
    return elicited;
  }

  public Set<Operant> getEmitted() {
    return emitted;
  }

  public Set<Action> getSpontaneous() {
    return spontaneous;
  }
  
  
}
