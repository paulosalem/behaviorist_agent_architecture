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
package organism.stimulation;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Manages the stimuli prototypes available to an organism. 
 * 
 * This class is part of an implementation of Prototype design pattern.
 * 
 * @author Paulo Salem
 *
 */
public class StimulusManager implements Serializable{

  
	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  private StimulationSubsystem stimulationSubsystem;

	/**
	 * A map from an IDs to their corresponding stimuli.
	 */
	private Map<Integer, Stimulus> stimuli = new HashMap<Integer, Stimulus>();
  
  public StimulusManager(){
    
  }
	
  public StimulusManager(StimulationSubsystem stimulationSubsystem){
    this.stimulationSubsystem = stimulationSubsystem;
  }
  
  
  /**
   * Puts a new stimulus prototype in the manager. If it already exists,
   * it shall be substituted.
   * 
   * @param s The stimulus to add.
   */
  public void putPrototype(Stimulus s){
    stimuli.put(s.id, s);
    
    if(stimulationSubsystem != null){
      // At first, the stimulus is absent
      stimulationSubsystem.getStimulusStatus().put(s, Stimulation.StimulusStatus.ABSENT);
    }
  }
  
  
  /**
   * Gets a clone of the stimulus with the specified id.
   * 
   * @param stimulusId The id of the stimulus to be cloned.
   * 
   * @return The cloned stimulus, if a prototype exists;
   *         <code>null</code> otherwise.
   */
  public Stimulus getPrototypeClone(int stimulusId){
    Stimulus s = stimuli.get(stimulusId);
    
    if(s == null){
      return null;
    }
    
    return (Stimulus)s.clone();
  }
  
  /**
   * Returns a clone of the first stimulus with the specified name.
   * 
   * @param name the name of the desired stimulus.
   * @return a clone of the first stimulus with the specified name.
   */
  public Stimulus getPrototypeClone(String name){
    for(Stimulus s: stimuli.values()){
      if(s.getName().equals(name)){
        return s;
      }
    }
    
    return null;
  }

  
  /**
   * Returns all the available prototypes. Notice that these are not clones of the prototypes.
   * For such clones, call <code>getAllPrototypeClones()</code>. 
   * 
   * @return A set with all available prototypes.
   */
  public Collection<Stimulus> getAllPrototypes(){
    return stimuli.values();
  }
  
  /**
   * 
   * @return A set with clones of all available prototypes.
   */
  public Set<Stimulus> getAllPrototypeClones(){
	  
	  Set<Stimulus> clones = new HashSet<Stimulus>();
	  
	  for(Stimulus s: stimuli.values()){
		  clones.add((Stimulus)s.clone());
	  }
	  
	  return clones;
  }
  


  public void setStimulationSubsystem(StimulationSubsystem stimulationSubsystem) {
    this.stimulationSubsystem = stimulationSubsystem;
    
    // If it is a different subsystem, reinitialize the stimuli
    if(this.stimulationSubsystem != stimulationSubsystem){
      for(Stimulus s: stimuli.values()){
        // At first, the stimulus is absent
        stimulationSubsystem.getStimulusStatus().put(s, Stimulation.StimulusStatus.ABSENT);
      }   
    }
  }
 
  
}
