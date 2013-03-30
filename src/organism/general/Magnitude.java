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

import java.io.Serializable;

/**
 * Represents a rational number such that it is between -1.0 and 1.0. More
 * precisely, let q be this rational number. Then it must be 
 * 
 *   -1.0 <= q <= 1.0
 * 
 * Note: this class has a natural ordering that is inconsistent with equals.
 * This is so because our comparisons employ an epsilon precision constant,
 * which makes different numbers equal. And because such equality
 * depends on the two numbers, not on only one, it is not possible to give
 * a correct implementation of the <code>hashCode</code> method using
 * this equality notion.
 * 
 * @author Paulo Salem
 *
 */
public class Magnitude implements Comparable<Magnitude>, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * A small constant employed to fix the precision of the comparisons performed.
   */
  public static final double EPSILON = 0.001;
  
  
  /**
   * The minimum magnitude.
   */
  public static final Magnitude MIN = new Magnitude(-1.0);
  
  /**
   * The neutral magnitude.
   */
  public static final Magnitude NEUTRAL = new Magnitude(0.0);
  
  /**
   * The maximum magnitude
   */
  public static final Magnitude MAX = new Magnitude(1.0);
  
  
  protected double q;
  
  /**
   * Builds a new magnitude.
   * 
   * @param q A number between -1.0 and 1.0
   */
  public Magnitude(double q){
    
    if(isWithinBounds(q)){
      this.q = q;
    }
    else{
      throw new IllegalArgumentException("The specified parameter must be between -1.0 and 1.0, but was " + q + ".");
    }
    
  }
  
  
  public Magnitude add(Magnitude p){
    
    
    double tmp = q + p.getValue();
    tmp = enforceBounds(tmp);
    Magnitude m = new Magnitude(tmp);
    
    return m;
    
  }
  
  public Magnitude subtract(Magnitude p){
    
    
    double tmp = q - p.getValue();
    tmp = enforceBounds(tmp);
    Magnitude m = new Magnitude(tmp);
    
    return m;
    
  }
  
  public Magnitude multiply(Magnitude p){
    
    double tmp = q * p.getValue();
    tmp = enforceBounds(tmp);
    Magnitude m = new Magnitude(tmp);
    
    return m;
    
  }
  
  public Magnitude divide(Magnitude p){
    
    double tmp = q / p.getValue();
    tmp = enforceBounds(tmp);
    Magnitude m = new Magnitude(tmp);
    
    return m;
    
  }
  

  
  public double getValue(){
    return q;
  }
  
  
  
  protected boolean isWithinBounds(double q){
    
    if(-1.0 <= q && q <= 1.0){
      return true;
    }
    
    return false;
  }
  
  protected double enforceBounds(double q){
    if(q > 1.0){
      return 1.0;
    }
    else if (q < -1.0){
      return -1.0;
    }
    else{
      return q;
    }
  }
  
  
  @Override
  public Object clone(){
    return new Magnitude(q);
  }


  @Override
  public int compareTo(Magnitude m) {
    
    if(equals(m)){
      return 0;
    }
    else if(q > m.getValue()){
      return 1;
    }
    else{
      return -1;
      
    }
    
  }
  
  public boolean equals(Magnitude m) {

    double dif = q - m.getValue();

    // If the numbers are within an EPSILON of each other, then they
    // shall be considered equal
    if (Math.abs(dif) <= EPSILON) {
      return true;
    }

    return false;
  }
  
  /**
   * 
   * @param q Another magnitude.
   * @return <code>true</code> if this magnitude is strictly greater (i.e., >)
   *                           than the specified one;
   *         <code>false</code> otherwise.
   */
  public boolean greaterThan(Magnitude q){
    if(compareTo(q) > 0){
      return true;
    }
    
    return false;
  }
  
  /**
   * 
   * @param q Another magnitude.
   * @return <code>true</code> if this magnitude is greater than or equal to (i.e., >=)
   *                           the specified one;
   *         <code>false</code> otherwise.
   */
  public boolean greaterThanOrEqualTo(Magnitude q){
    int c = compareTo(q);
    
    if(c == 0 || c > 0){
      return true;
    }
    
    return false;
  }
  
  /**
   * 
   * @param q Another magnitude.
   * @return <code>true</code> if this magnitude is strictly less (i.e., <)
   *                           than the specified one;
   *         <code>false</code> otherwise.
   */  
  public boolean lessThan(Magnitude q){
    if(compareTo(q) < 0){
      return true;
    }
    
    return false;
  }
  
  /**
   * 
   * @param q Another magnitude.
   * @return <code>true</code> if this magnitude is less than or equal to (i.e., <=)
   *                           the specified one;
   *         <code>false</code> otherwise.
   */  
  public boolean lessThanOrEqualTo(Magnitude q){
    int c = compareTo(q);
    
    if(c == 0 || c < 0){
      return true;
    }
    
    return false;
  }
  
  public static Magnitude min(){
    return MIN;
  }
  
  @Override
  public String toString(){
	  return ("" +  Math.round(100 * q)/100.0);
  }
}
