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

import java.util.HashSet;
import java.util.Set;

import organism.stimulation.Stimulus;
import organism.stimulation.StimulusUtility;
import organism.stimulation.Utility;


public class OperantUtility_Ref1 extends OperantUtility {

  protected Discrimination discrimination;
  
  public OperantUtility_Ref1(StimulusUtility stimulusUtility, OperantImplication operantImplication, Discrimination discrimination){
    super(stimulusUtility, operantImplication);
    this.discrimination = discrimination;
  }

  
  public Utility utility(Operant o, Set<Stimulus> ss){
    
    Set<Stimulus> reachable = new HashSet<Stimulus>();
    
    // Find all reachable stimuli
    if(discrimination.discriminates(ss, o)){
      reachable = operantImplication.consequences(o);
    }

    
    //
    // Find the best among the reachable, where the best is:
    //   - the maximum positive utility, if there are only positive utilities
    //   - the minimum negative utility, if there is at least one negative utility
    //   - neutral utility, if none of the previous cases hold
    //
    
    // We keep track of whether a negative utility was found
    boolean foundNegative = false;

    // In principle, the operant is neutral
    Utility best = Utility.NEUTRAL;
        
    // But if there are reachable stimuli, select the best one
    if(!reachable.isEmpty()){
      // Begin with any reachable stimulus
      best = stimulusUtility.utility(reachable.iterator().next());
      if(best.lessThan(Utility.NEUTRAL)){
        foundNegative = true;
      }
      
      
      for(Stimulus s: reachable){
        Utility u = stimulusUtility.utility(s);
        
        if(u.lessThan(Utility.NEUTRAL)){
          foundNegative = true;
        }
        
        if(!foundNegative && u.greaterThan(best)){
          // No negative utility found so far, we can continue looking for the maximum positive one
          best = u; 
        }
        else if(foundNegative && u.lessThan(best)){
          // We have found a negative utility already, so now we are looking for the minimum one
          best = u;
        }
      }
    }
    
    return best;
    
  }

}
