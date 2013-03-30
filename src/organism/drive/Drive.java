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
package organism.drive;

import java.io.Serializable;
import java.util.Set;

import organism.general.Magnitude;
import organism.stimulation.Stimulus;
import organism.stimulation.Utility;

/**
 * Drive implementation.
 * 
 * @author Paulo Salem
 *
 */
public class Drive implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * How important the drive currently is to the organism.
   */
  protected Utility importance;
  
  /**
   * The maximum possible importance of the drive.
   */
  protected Utility maxImportance;

  /**
   * The minimum possible importance of the drive.
   */
  protected Utility minImportance;
  
  
  /**
   * The set of stimuli that can satiate the drive.
   */
  protected Set<Stimulus> desires;
  
  


  public Drive(Set<Stimulus> desires, Utility importance, Utility minImportance, Utility maxImportance) {
	super();
	this.desires = desires;
	this.importance = importance;
	this.maxImportance = maxImportance;
	this.minImportance = minImportance;
  }

public Utility deprivation(Utility u){
    
    // TODO more general mechanism?
  
    Utility nextU = Utility.valueOf(u.add(new Magnitude(0.01)));
    
    if(nextU.greaterThan(maxImportance)){
      nextU = maxImportance;
    }
    
    return nextU;
  }
  
  public Utility satiation(Utility u){
    
    // TODO more general mechanism?
    
    Utility nextU = Utility.valueOf(u.subtract(new Magnitude(0.1)));
    if(nextU.lessThan(minImportance)){
      nextU = minImportance;
    }

    return nextU;
  }

  public Utility getImportance() {
    return importance;
  }
  
  public Utility getMaxImportance() {
    return maxImportance;
  }

  public Utility getMinImportance() {
    return minImportance;
  }

  public void setImportance(Utility importance) {
    this.importance = importance;
  }

  public Set<Stimulus> getDesires() {
    return desires;
  }
  
  @Override
  public String toString(){
	return desires.toString();  
  }

  
}
