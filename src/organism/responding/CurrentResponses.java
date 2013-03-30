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

import organism.responding.operant.Operant;
import organism.responding.respondent.Reflex;

/**
 * Stores the behavioral responses actually performed.
 * 
 * @author Paulo Salem
 *
 */
public class CurrentResponses implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
    
  
  private Set<Response> activeResponses;
  
  private Set<Response> inactiveResponses;
  
  private Map<Reflex, Response> reflexResponse;
  
  private Map<Operant, Response> operantResponse;
  
  private Map<Action, Response> spontaneousResponse;

  private Map<Reflex, Integer> reflexElicitationTime;
  
  
  
  public CurrentResponses() {
    super();
    activeResponses = new HashSet<Response>();
    inactiveResponses = new HashSet<Response>();
    reflexResponse = new HashMap<Reflex, Response>();
    operantResponse = new HashMap<Operant, Response>();
    spontaneousResponse = new HashMap<Action, Response>();
    reflexElicitationTime = new HashMap<Reflex, Integer>();
  }


  public Set<Response> getResponses(){
    HashSet<Response> responses = new HashSet<Response>();
    responses.addAll(activeResponses);
    responses.addAll(inactiveResponses);
    
    return responses;
  }

  public void addActiveResponse(Response rp){
    activeResponses.add(rp);
    registerOriginator(rp);
  }
  
  public void addInactiveResponse(Response rp){
    inactiveResponses.add(rp);
    registerOriginator(rp);
  }
  
  public void removeActiveResponse(Response rp){
    activeResponses.remove(rp);
    removeOriginator(rp);
  }
  
  public void removeInactiveResponse(Response rp){
    inactiveResponses.remove(rp);
    removeOriginator(rp);
  }
  
  

  
  public void setReflexElicitationTime(Reflex r, int instant){
    reflexElicitationTime.put(r, instant);
  }
  
  public int getReflexElicitationTime(Reflex r){
    return reflexElicitationTime.get(r);
  }
  
  public boolean isReflexElicitationTimeDefined(Reflex r){
    if(reflexElicitationTime.containsKey(r)){
      return true;
    }
    
    return false;
  }
  
  public Response getOperantResponse(Operant o){
    return operantResponse.get(o);
  }
  
  public Response getReflexResponse(Reflex r){
    return reflexResponse.get(r);
  }
  
  public Response getSpontaneousResponse(Action a){
    return spontaneousResponse.get(a);
  }
  
  public Set<Response> getActiveResponses() {
    return activeResponses;
  }


  public Set<Response> getInactiveResponses() {
    return inactiveResponses;
  }

  private void registerOriginator(Response rp){
    Behavior b = rp.getOriginator();
    
    if(b == null){
      spontaneousResponse.put(rp.getAction(), rp);
    }
    else if(b instanceof Reflex){
      Reflex r = (Reflex) b;
      reflexResponse.put(r, rp);
    }
    else if(b instanceof Operant){
      Operant o = (Operant) b;
      operantResponse.put(o, rp);
      
    }
    
  }
  
  private void removeOriginator(Response rp){
    Behavior b = rp.getOriginator();
    
    if(b == null){
      spontaneousResponse.remove(rp.getAction());
    }
    else if(b instanceof Reflex){
      Reflex r = (Reflex) b;
      reflexResponse.remove(r);
    }
    else if(b instanceof Operant){
      Operant o = (Operant) b;
      operantResponse.remove(o);
      
    }
    
  }
  
  
}
