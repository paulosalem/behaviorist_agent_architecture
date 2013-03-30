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
import java.util.Stack;

import organism.general.Correlation;
import organism.stimulation.Stimulus;
import organism.stimulation.StimulusImplication;

/**
 * Provides the mechanism to calculate the operant implication relation.
 * 
 * @author Paulo Salem
 * 
 */
public class OperantImplication implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected StimulusImplication stimulusImplication;
  
  protected Discrimination discrimination;
  
  protected Set<Operant> operants;
  
  public OperantImplication(Set<Operant> operants, StimulusImplication stimulusImplication, Discrimination discrimination){
    this.operants = operants;
    this.stimulusImplication = stimulusImplication;
    this.discrimination = discrimination;
  }
  
  public boolean causes(Operant o, Stimulus s){
    
    Stack<Operant> os = new Stack<Operant>();
    os.add(o);
    
    while(!os.empty()){
      Operant cur = os.pop();
      
      // Calculate all stimuli associated with the operant's consequence
      Set<Stimulus> consequences = stimulusImplication.consequences(cur.getConsequence());
      consequences.add(cur.getConsequence());
      
      // Check whether one of these stimuli is the desired one
      for(Stimulus t: consequences){
        if(t.equals(s)){
          return true;
        }
      }
      
      // Check if these stimuli discriminate other operants from the organism
      for(Operant p: operants){
        if(discrimination.discriminatesNonEmpty(consequences, p)){
          os.add(p);
          break;
        }
      }
      
    }
    
    // If nothing was found, there is no causation
    return false;
  }
  
  
  public Correlation correlation(Operant o, Stimulus s){
    // TODO <-------------------------------------------------------------
    return null;
  }
  
  public Set<Stimulus> consequences(Operant o){
    
    Set<Stimulus> consequences = new HashSet<Stimulus>();
    
    Stack<Operant> os = new Stack<Operant>();
    Set<Operant> visited = new HashSet<Operant>();
    
    os.add(o);
    visited.add(o);
    
    while(!os.empty()){
      Operant cur = os.pop();
      
      // All stimuli associated with the operant's consequence
      consequences.addAll(stimulusImplication.consequences(cur.getConsequence()));
      consequences.add(cur.getConsequence());
      
      
      // Check if these stimuli discriminate other operants from the organism
      for(Operant p: operants){
        if(discrimination.discriminatesNonEmpty(consequences, p)){
      	  // If it has not been examined, now is the time
      	  if(!visited.contains(p)){
      		  os.add(p);
      		  visited.add(p);
      		  break;
      	  }
        }
      }
    }
    
    return consequences;
  }
  
}
