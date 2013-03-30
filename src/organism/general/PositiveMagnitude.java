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
package organism.general;

/**
 * A magnitude restricted to the positive elements. More precisely,
 * let q be a positive magnitude. Then,
 * 
 *   0.0 <= q <= 1.0
 *   
 * @author Paulo Salem
 *
 */
public class PositiveMagnitude extends Magnitude {

  
  /**
   * Builds a new magnitude.
   * 
   * @param q A number between 0.0 and 1.0
   */
  public PositiveMagnitude(double q){
    super(q);
    if(isWithinBounds(q)){
      this.q = q;
    }
    else{
      throw new IllegalArgumentException("The specified parameter must be between -1.0 and 1.0");
    }
  }
  
  

  protected boolean isWithinBounds(double q){
    
    if(0.0 <= q && q <= 1.0){
      return true;
    }
    
    return false;
  }
  
  protected double enforceBounds(double q){
    if(q > 1.0){
      return 1.0;
    }
    else if (q < 0.0){
      return 0.0;
    }
    else{
      return q;
    }
  }
  
  
}
