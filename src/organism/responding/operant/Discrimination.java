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
import java.util.HashSet;
import java.util.Set;

import organism.stimulation.Stimulus;

/**
 * Provides a mechanism to check whether a stimulus is capable of
 * discriminating an operant.
 * 
 * @author Paulo Salem
 *
 */
public class Discrimination implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // TODO not being used. May be excluded.
/*
  public boolean discriminates(Stimulus s, Operant o){
    
    HashSet<Stimulus> ss = new HashSet<Stimulus>();
    ss.add(s);
    
    if(discriminates(ss, o)){
      return true;
    }
    
    // No discrimination was found
    return false;
  }
  */
  
  public boolean discriminates(Set<Stimulus> ss, Operant o){

    // The specified set must be equal to some antecedent in
    // order to discriminate the operant
    for(Set<Stimulus> antecedent: o.getAntecedents()){
      
      // Check if the antecedent is contained in the stimuli given
      if(ss.containsAll(antecedent)){
        return true;
      }
    }
    
    /*
    for(Stimulus s: ss){
      if(discriminates(s, o)){
        return true;
      }
    }*/
    
    return false;
  }
  
  public boolean discriminatesNonEmpty(Set<Stimulus> ss, Operant o){

    // The specified set must be equal to some antecedent in
    // order to discriminate the operant
    for(Set<Stimulus> antecedent: o.getAntecedents()){
      
      // Check if the antecedent is non-empty and is contained in the stimuli given
      if(!antecedent.isEmpty() && ss.containsAll(antecedent)){
        return true;
      }
    }
    
    /*
    for(Stimulus s: ss){
      if(discriminates(s, o)){
        return true;
      }
    }*/
    
    return false;
  }
}
