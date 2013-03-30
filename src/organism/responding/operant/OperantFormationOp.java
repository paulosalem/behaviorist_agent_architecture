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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import organism.general.Correlation;
import organism.responding.Action;
import organism.responding.ActionManager;
import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Stimulus;
import organism.stimulation.StimulusManager;
import organism.util.Operation;

public class OperantFormationOp extends Operation implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Stimulation subsystem.
   */
  protected StimulationSubsystem stimSubsys;
  
  protected StimulusManager stimulusManager;
  
  protected ActionManager actionManager;
  
  public OperantFormationOp(StimulationSubsystem stimSubsys, ActionManager actionManager){
    this.stimSubsys = stimSubsys;
    this.actionManager = actionManager;
    this.stimulusManager = stimSubsys.getStimulusManager();
    
  }
  
  public boolean precondition(Stimulus consequence, Set<Stimulus> discriminative, int delay){
    int maxDelay = stimSubsys.getMaxDelay();
    
    if(delay <= maxDelay 
        && !discriminative.contains(consequence)
        && !discriminative.isEmpty()){
      return true;
    }
    
    return false;
  }
  
  public Operant apply(Action action, Stimulus consequence, Set<Stimulus> discriminative, int delay){
    
    Set<Set<Stimulus>> antecedents = new HashSet<Set<Stimulus>>();
    
    // Antecedents starts as the discriminative stimuli
    antecedents.add(discriminative);
    
    // TODO set better value
    Map<Set<Stimulus>, Correlation> consequenceContingency = new HashMap<Set<Stimulus>, Correlation>();
    consequenceContingency.put(discriminative, new Correlation(0.3)); 
    
    return new Operant(antecedents, action, consequence, consequenceContingency, actionManager, stimulusManager);
  }
  
}
