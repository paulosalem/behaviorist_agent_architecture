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
import java.util.Set;

import organism.emotion.EmotionSubsystem;
import organism.general.Intensity;
import organism.responding.Action;
import organism.responding.ActionManager;
import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Stimulus;
import organism.util.z.ZOperation;

/**
 * A subsystem to manage operant behavior. 
 * 
 * @author Paulo Salem
 *
 */
public class OperantSubsystem implements Serializable{
  
	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
	 * Operant implication definition.
	 */
	protected OperantImplication operantImplication;
	
  /**
   * Operant utility definition.
   */
  protected OperantUtility operantUtility;
  
  
  /**
   * Stimulation subsystem.
   */
  protected StimulationSubsystem stimSubsys;
  
  /**
   * Emotion subsystem.
   */
  protected EmotionSubsystem emoSubsys;
  
  
  protected OperantFormationOp operantFormationOp; 
  protected OperantEliminationOp_1 operantEliminationOp_1;
  protected OperantEliminationOp_2 operantEliminationOp_2;
  
  protected DiscriminationOp discriminationOp;
  protected OperantConditioningOp operantConditioningOp;
  protected ExtinctionOp extinctionOp;
  protected NeutralOp neutralOp;
  
  protected PositiveReinforcement positiveReinforcement;
  protected NegativeReinforcement negativeReinforcement;
  protected PositivePunishment positivePunishment;
  protected NegativePunishment negativePunishment; 
  
  protected NeutralReinforcementOp_1 neutralReinforcementOp_1;
  protected NeutralReinforcementOp_2 neutralReinforcementOp_2;
  
  public OperantSubsystem(Set<Operant> operants, ActionManager actionManager, OperantImplication operantImplication, OperantUtility operantUtility, StimulationSubsystem stimSubsys, EmotionSubsystem emoSubsys){
    this.operantImplication = operantImplication;
	this.operantUtility = operantUtility;
    this.stimSubsys = stimSubsys;
    this.emoSubsys = emoSubsys;
    
    operantFormationOp = new OperantFormationOp(stimSubsys, actionManager);
    operantEliminationOp_1 = new OperantEliminationOp_1(operants);
    operantEliminationOp_2 = new OperantEliminationOp_2();
    
    
    discriminationOp = new DiscriminationOp(operantUtility, stimSubsys);
    operantConditioningOp = new OperantConditioningOp(operantUtility, stimSubsys);
    extinctionOp = new ExtinctionOp(operantUtility, stimSubsys);
    neutralOp = new NeutralOp(operantUtility, stimSubsys);
    
    positiveReinforcement = new PositiveReinforcement(stimSubsys);
    negativeReinforcement = new NegativeReinforcement(stimSubsys);
    positivePunishment = new PositivePunishment(stimSubsys);
    negativePunishment = new NegativePunishment(stimSubsys);  
    
    neutralReinforcementOp_1 = new NeutralReinforcementOp_1(stimSubsys);
    neutralReinforcementOp_2 = new NeutralReinforcementOp_2(stimSubsys);
    
  }

  @ZOperation(name = "T_OperantOp", total = true)
  public void performTOperantOp(Operant operant, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    
    boolean ok = false;
    
    if(performPositiveReinforcementOp_1(operant, discriminativeStimuli, consequence, action, delay)){
      ok = true;
    }
    
    // OR
    
    else if(performNegativeReinforcementOp_1(operant, discriminativeStimuli, consequence, action, delay)){
      ok = true;
    }
    
    // OR
    
    else if(performPositivePunishmentOp_1(operant, discriminativeStimuli, consequence, action, delay)){
      ok = true;
    }
    
    // OR
    
    
    else if(performNegativePunishmentOp_1(operant, discriminativeStimuli, consequence, action, delay)){
      ok = true;
    }
    
    // OR
    
    else if(performNeutralReinforcementOp_1(operant, discriminativeStimuli, consequence, action, delay)){
      ok = true;
    }
    
    
    // TODO some assertion regarding 'ok'? It MUST be true.
  }
  
  @ZOperation(name = "T_OperantFormationOp", total = true)
  public Operant performTOperantFormationOp(Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    
    Operant o = null;
    
    if(o == null){
    	o = performPositiveReinforcementOp_2(discriminativeStimuli, consequence, action, delay);
    }
    
    // OR
    
    if(o == null){
    	o = performNegativeReinforcementOp_2(discriminativeStimuli, consequence, action, delay);
    }
    
    // OR
    
    if(o == null){
    	o = performPositivePunishmentOp_2(discriminativeStimuli, consequence, action, delay);
    }
    
    // OR
    
    if(o == null){
    	o = performNegativePunishmentOp_2(discriminativeStimuli, consequence, action, delay);
    }
    
    // OR
    
    if(o == null){
    	o = this.performNeutralReinforcementOp_2(discriminativeStimuli, consequence, action, delay);
    }

    return o;
  }
  
  @ZOperation(name = "T_OperantEliminationOp", total = true)
  public void performTOperantEliminationOp(Operant o){
	  
	  if(operantEliminationOp_1.precondition(o)){
		  operantEliminationOp_1.apply(o);
	  }
	  
	  else if(operantEliminationOp_2.precondition(o)){
		  operantEliminationOp_2.apply(o);
	  }
  }
  
  
  
  @ZOperation(name = "PositiveReinforcementOp_1")
  private boolean performPositiveReinforcementOp_1(Operant operant, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    
    if(positiveReinforcement.precondition(consequence)){
     if(performFundamentalOperantOp(operant, discriminativeStimuli, consequence, action, delay)){
        positiveReinforcement.apply();
        return true;
      }
    }
    
    return false;
    
  }
  
  @ZOperation(name = "NegativeReinforcementOp_1")
  private boolean performNegativeReinforcementOp_1(Operant operant, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){

	if(negativeReinforcement.precondition(consequence)){
      if(performFundamentalOperantOp(operant, discriminativeStimuli, consequence, action, delay)){
      
        negativeReinforcement.apply();
        return true;
      }
    }
    
    return false;
    
  }
  
  @ZOperation(name = "PositivePunishmentOp_1")
  private boolean performPositivePunishmentOp_1(Operant operant, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    
	if(positivePunishment.precondition(consequence)){
      if(performFundamentalOperantOp(operant, discriminativeStimuli, consequence, action, delay)){
      
        positivePunishment.apply();
        
        // TODO StartAngerOp: improve the operation's parameters
        emoSubsys.performStartAngerOp(new Intensity(1.0), 100);
        
        return true;
      }
    }
    
    return false;
  }
  
  @ZOperation(name = "NegativePunishmentOp_1")
  private boolean performNegativePunishmentOp_1(Operant operant, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){

	if(negativePunishment.precondition(consequence)){
      if(performFundamentalOperantOp(operant, discriminativeStimuli, consequence, action, delay)){
      
        negativePunishment.apply();
        
        // TODO StartDepressionOp: improve the operation's parameters
        emoSubsys.performStartDepressionOp(new Intensity(0.1), 10);
        
        return true;
      }
    }

    return false;
  }
  
  @ZOperation(name = "NeutralReinforcementOp_1")
  private boolean performNeutralReinforcementOp_1(Operant operant, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){

	if(neutralReinforcementOp_1.precondition(operant, consequence, action, delay)){
      if(performFundamentalOperantOp(operant, discriminativeStimuli, consequence, action, delay)){
      
    	  neutralReinforcementOp_1.apply();
        
        return true;
      }
    }

    return false;
  }
  
  
  @ZOperation(name = "PositiveReinforcementOp_2")
  private Operant performPositiveReinforcementOp_2(Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    
    Operant o = null;
    
    if(operantFormationOp.precondition(consequence, discriminativeStimuli, delay)){
      if(positiveReinforcement.precondition(consequence)){
        
        o = operantFormationOp.apply(action, consequence, discriminativeStimuli, delay);
        positiveReinforcement.apply();
      }
    }
    
    return o;
  }
  
  @ZOperation(name = "NegativeReinforcementOp_2")
  private Operant performNegativeReinforcementOp_2(Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    
    Operant o = null;
    
    if(operantFormationOp.precondition(consequence, discriminativeStimuli, delay)){
      if(negativeReinforcement.precondition(consequence)){
        
        o = operantFormationOp.apply(action, consequence, discriminativeStimuli, delay);
        negativeReinforcement.apply();
      }
    }
    
    return o;
  }
  
  @ZOperation(name = "PositivePunishmentOp_2")
  private Operant performPositivePunishmentOp_2(Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    
    Operant o = null;
    
    if(operantFormationOp.precondition(consequence, discriminativeStimuli, delay)){
      if(positivePunishment.precondition(consequence)){
        
        o = operantFormationOp.apply(action, consequence, discriminativeStimuli, delay);
        positivePunishment.apply();
        
        // TODO StartAngerOp: improve the operation's parameters
        emoSubsys.performStartAngerOp(new Intensity(1.0), 100);
      }
    }
    
    return o;
  }
  
  @ZOperation(name = "NegativePunishmentOp_2")
  private Operant performNegativePunishmentOp_2(Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){
    
    Operant o = null;
    
    if(operantFormationOp.precondition(consequence, discriminativeStimuli, delay)){
      if(negativePunishment.precondition(consequence)){
        
        o = operantFormationOp.apply(action, consequence, discriminativeStimuli, delay);
        negativePunishment.apply();
        
        // TODO StartDepressionOp: improve the operation's parameters
        emoSubsys.performStartDepressionOp(new Intensity(0.1), 10);
      }
    }
    
    return o;
  }  

  @ZOperation(name = "NeutralReinforcementOp_2")
  private Operant performNeutralReinforcementOp_2(Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){

	    Operant o = null;
	    
	    if(operantFormationOp.precondition(consequence, discriminativeStimuli, delay)){
	      if(neutralReinforcementOp_2.precondition(consequence, delay)){
	        
	        o = operantFormationOp.apply(action, consequence, discriminativeStimuli, delay);
	        neutralReinforcementOp_2.apply();
	        
	        // TODO StartDepressionOp: improve the operation's parameters
	        emoSubsys.performStartDepressionOp(new Intensity(0.1), 10);
	      }
	    }
	    
	    return o;
  }

  
  @ZOperation(name = "FundamentalOperantOp")
  private boolean performFundamentalOperantOp(Operant operant, Set<Stimulus> discriminativeStimuli, Stimulus consequence, Action action, int delay){

    boolean ok = false;
    
    if(discriminationOp.precondition(operant, action, consequence, discriminativeStimuli, delay)){
      discriminationOp.apply(operant, emoSubsys, discriminativeStimuli, consequence, action, delay);
      ok = true;
    }
    
    // OR
    
    else if(operantConditioningOp.precondition(operant, action, consequence, discriminativeStimuli, delay)){
      operantConditioningOp.apply(operant, emoSubsys, discriminativeStimuli, consequence, action, delay);
      ok = true;
    }
    
    // OR
    
    else if(extinctionOp.precondition(operant, action, consequence, discriminativeStimuli, delay)){
      extinctionOp.apply(operant, emoSubsys, discriminativeStimuli, consequence, action, delay);
      ok = true;
    }
    
    // OR
    
    else if(neutralOp.precondition(operant, action, consequence, discriminativeStimuli, delay)){
      neutralOp.apply(operant, emoSubsys, discriminativeStimuli, consequence, action, delay);
      ok = true;
    }
    
    return ok;
  }

  public OperantUtility getOperantUtility() {
    return operantUtility;
  }
  
  
  
}
