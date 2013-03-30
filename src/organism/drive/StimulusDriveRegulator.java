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
import java.util.HashSet;
import java.util.Set;

import organism.stimulation.Stimulus;
import organism.stimulation.Utility;

public class StimulusDriveRegulator implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  protected DriveSubsystem driveSubsystem;

  public StimulusDriveRegulator(DriveSubsystem driveSubsystem) {
    super();
    this.driveSubsystem = driveSubsystem;
  }
  
  public Utility driveRegulator(Stimulus s, Utility u){
    
    // Must work on a copy of the drives set, since it will be modified
    Set<Drive> ds = new HashSet<Drive>();
    ds.addAll(driveSubsystem.getActiveDrives());
    
    //return f(s, u, ds);
    return Utility.valueOf(u.add(f(s, Utility.NEUTRAL, ds)));
  }
  
  protected Utility f(Stimulus s, Utility u, Set<Drive> ds){
    
    // Recursion base
    if(ds.size() == 0){
      return Utility.NEUTRAL;
    }
    
    else{
      Drive d = ds.iterator().next();
      ds.remove(d);
      
      // If the drive can be satiated by the stimulus
      if(d.getDesires().contains(s)){
        return Utility.valueOf(u.add(d.getImportance()).add(f(s, u, ds)));
      }
      else{
        return f(s, u, ds);
      }
    }
  }
}
