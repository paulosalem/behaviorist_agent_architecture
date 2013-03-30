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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import organism.drive.DriveSubsystem;
import organism.emotion.EmotionSubsystem;
import organism.io.Storable;
import organism.util.graph.Vertex;
import organism.util.z.ZOperation;


/**
 * The subsystem responsible for processing stimuli.
 * 
 * @author Paulo Salem
 *
 */
public class StimulationSubsystem implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * The parameters of the subsystem.
   */
  private StimulationParameters stimulationParameters;
  
  
  /**
   * The parameters necessary to perform stimulus conditioning.
   */
  protected ConditioningParameters conditioningParameters;
  
  /**
   * The implications among stimuli.
   */
  private StimulusImplication stimulusImplication;
  
  /**
   * The equivalences among stimuli.
   */
  private StimulusEquivalence stimulusEquivalence;
  
  /**
   * Stimuli currently active in the organism.
   */
  private HashSet<Stimulus> currentStimuli;
  
  private HashMap<Integer, HashSet<Stimulus>> pastStimuli;
  
  /**
   * A map from stimuli to their status in the organism.
   */
  private Map<Stimulus, Stimulation.StimulusStatus> stimulusStatus;
  
  /**
   * A map from stimuli to the instant that they began.
   */  
  private Map<Stimulus, Integer> stimulusBeginning;
  
  /**
   * The implementation of the stimulus utility function.
   */
  private StimulusUtility stimulusUtility;
  
  /**
   * Manages the stimuli available to the organism.
   */
  private StimulusManager stimulusManager;
  
  //
  // Below are the several partial operations required for the implementation
  // of the total operations present in this class.
  //
  
  private ConditioningOp_Ref1 conditioningOp_Ref1;
  private UnconditioningOp_Ref1 unconditioningOp_Ref1;
  
  private StimulationUpdateOp_1 stimulationUpdateOp_1;
  private StimulationUpdateOp_2 stimulationUpdateOp_2;
  private StimulationUpdateOp_3 stimulationUpdateOp_3;
  
  private CurrentStimuliUpdateOp_1 currentStimuliUpdateOp_1;
  private CurrentStimuliUpdateOp_2 currentStimuliUpdateOp_2;
  private CurrentStimuliUpdateOp_3 currentStimuliUpdateOp_3;
  private CurrentStimuliUpdateOp_4 currentStimuliUpdateOp_4;
  
  private TPastStimuliUpdateOp pastStimuliUpdateOp;
  
  
  
  
  public StimulationSubsystem(StimulusManager stimulusManager, StimulationParameters stimulationParameters, Conditioning_Ref1_Parameters conditioningParameters, DriveSubsystem driveSubsystem){
    
    basicConstruction(stimulusManager, stimulationParameters, conditioningParameters, driveSubsystem);
    
  }


  public StimulationSubsystem(StimulusManager stimulusManager, StimulationParameters stimulationParameters, Conditioning_Ref1_Parameters conditioningParameters, StimulusImplication stimulusImplication, DriveSubsystem driveSubsystem){
    
    basicConstruction(stimulusManager, stimulationParameters, conditioningParameters, driveSubsystem);
    
    // Moreover, set the stimulus implication
    this.stimulusImplication = stimulusImplication;
  
  }


  /**
   * Performs construction operations shared by all constructors. Must not be called
   * by other methods.
   * 
   * @param stimulationParameters The stimulation parameters.
   * @param conditioningParameters The conditioning parameters.
   */
  private void basicConstruction(StimulusManager stimulusManager, StimulationParameters stimulationParameters, Conditioning_Ref1_Parameters conditioningParameters, DriveSubsystem driveSubsystem){
    this.stimulationParameters = stimulationParameters;
    this.conditioningParameters = conditioningParameters;

    this.stimulusManager = stimulusManager;
    stimulusManager.setStimulationSubsystem(this);
    
    // Initialize subparts
    stimulusImplication = new StimulusImplication(stimulusManager);
    for(Stimulus s: stimulationParameters.getStimuli()){
      Vertex<Stimulus> v = new Vertex<Stimulus>(s);
      stimulusImplication.getStimuliGraph().putVertex(v);
    }
    
    stimulusEquivalence = new StimulusEquivalence(stimulusImplication);
    currentStimuli = new HashSet<Stimulus>();
    pastStimuli = new HashMap<Integer, HashSet<Stimulus>>();
    stimulusStatus = new HashMap<Stimulus, Stimulation.StimulusStatus>();
    stimulusBeginning = new HashMap<Stimulus, Integer>();
    stimulusUtility = new StimulusUtility_Ref1(this, null, driveSubsystem); // Emotion subsystem is set later
    
    // Instantiate operations
    conditioningOp_Ref1 = new ConditioningOp_Ref1(stimulationParameters, conditioningParameters);
    unconditioningOp_Ref1 = new UnconditioningOp_Ref1(this, conditioningParameters); 
    
    stimulationUpdateOp_1 = new StimulationUpdateOp_1();
    stimulationUpdateOp_2 = new StimulationUpdateOp_2();
    stimulationUpdateOp_3 = new StimulationUpdateOp_3();
    
    currentStimuliUpdateOp_1 = new CurrentStimuliUpdateOp_1();
    currentStimuliUpdateOp_2 = new CurrentStimuliUpdateOp_2();
    currentStimuliUpdateOp_3 = new CurrentStimuliUpdateOp_3();
    currentStimuliUpdateOp_4 = new CurrentStimuliUpdateOp_4();
    
    pastStimuliUpdateOp = new TPastStimuliUpdateOp();
  }

  /**
   * Performs stimulus conditioning concerning the two specified stimuli.
   * 
   * @param s1 The stimulus that came first.
   * @param s2 The stimulus that came later
   * @param delay How many instants were elapsed between the two stimuli.
   */
  @ZOperation(name = "T_ConditioningOp_Ref1", total = true)
  public void performConditioning(Stimulus s1, Stimulus s2, int delay){

    
    if(this.conditioningOp_Ref1.precondition(delay)){
      this.conditioningOp_Ref1.apply(stimulusImplication, s1, s2, delay);
    }
    
    // If this fails, the alternative is simply a neutral operation
    // (ConditioningOp_2)
    
    
  }

  /**
   * Performs stimulus unconditioning concerning the two specified stimuli.
   * 
   * @param s1 The stimulus that came first.
   * @param s2 The stimulus that came later
   */
  @ZOperation(name = "T_UnconditioningOp_Ref1", total = true)
  public void performUnconditioning(Stimulus s1, Stimulus s2, int currentInstant){

    
    if(this.unconditioningOp_Ref1.precondition(stimulusImplication, s1, s2, currentInstant)){
      this.unconditioningOp_Ref1.apply(stimulusImplication, s1, s2);
    }
    
    // If this fails, the alternative is simply a neutral operation
    // (UnconditioningOp_2)
    
    
  }
  
  /**
   * Updates a stimulus according to the specified stimulation.
   * 
   * @param stimulation The stimulation that specifies the update
   *                    to be performed on a stimulus.
   */
  @ZOperation(name = "T_StimulationUpdateOp", total = true)
  public void performStimulationUpdate(int currentInstant, Stimulation stimulation){
    
    if(this.stimulationUpdateOp_1.precondition(stimulation)){
      this.stimulationUpdateOp_1.apply(this, currentInstant, stimulation);
    }
    
    // Or
    
    else if(this.stimulationUpdateOp_2.precondition(stimulation)){
      this.stimulationUpdateOp_2.apply(this, currentInstant, stimulation);
    }
    
    // Or
    
    else if(this.stimulationUpdateOp_3.precondition(stimulation)){
      this.stimulationUpdateOp_3.apply(this, currentInstant, stimulation);
    }
    
  }
  
  /**
   * Performs maintenance on the specified stimulus. It is supposed that
   * the stimulus is already current (i.e., has already being delivered
   * to the organism)
   * 
   * @param s The stimulus to be updated.
   */
  @ZOperation(name = "T_CurrentStimuliUpdateOp", total = true)
  public void performCurrentStimuliUpdate(Stimulus s){
    
    if(this.currentStimuliUpdateOp_1.precondition(this, s)){
      this.currentStimuliUpdateOp_1.apply(this, s);
    }
    
    // Or
    
    if(this.currentStimuliUpdateOp_2.precondition(this, s)){
      this.currentStimuliUpdateOp_2.apply(this, s);
    }
    
    // Or
    
    if(this.currentStimuliUpdateOp_3.precondition(this, s)){
      this.currentStimuliUpdateOp_3.apply(this, s);
    }
    
    // Or
    
    if(this.currentStimuliUpdateOp_4.precondition(this, s)){
      this.currentStimuliUpdateOp_4.apply(this, s);
    }
    
  }
  
  /**
   * Records the stimuli present at the specified instant, so that they
   * can be assessed later.
   * 
   * @param currentInstant The instant to which the current stimuli will be permanently associated.
   */
  @ZOperation(name = "T_PastStimuliUpdateOp", total = true)
  public void performPastStimuliUpdateOp(int currentInstant){
    
    if(this.pastStimuliUpdateOp.precondition()){
      this.pastStimuliUpdateOp.apply(this, currentInstant);
    }
    
  }

  public HashSet<Stimulus> getCurrentStimuli() {
    return currentStimuli;
  }

  public HashMap<Integer,HashSet<Stimulus>> getPastStimuli() {
    return pastStimuli;
  }
  
  public Map<Stimulus, Stimulation.StimulusStatus> getStimulusStatus() {
    return stimulusStatus;
  }
  
  


  public StimulusUtility getStimulusUtility() {
    return stimulusUtility;
  }


  public StimulusManager getStimulusManager() {
    return stimulusManager;
  }
  
  public Set<Stimulus> getPrimaryStimuli(){
    return stimulationParameters.getPrimaryStimuli();
    
  }
  
  public int getMaxDelay(){
    return stimulationParameters.getMaxDelay();
  }
  
  public StimulusImplication getStimulusImplication() {
    return stimulusImplication;
  }


  public StimulusEquivalence getStimulusEquivalence() {
    return stimulusEquivalence;
  }


  public Integer getStimulusBeginning(Stimulus s) {
    return stimulusBeginning.get(s);
  }
  
  public void setStimulusBeginning(Stimulus s, int t){
    stimulusBeginning.put(s, t);
  }
  
  public void removeStimulusBeginning(Stimulus s){
    stimulusBeginning.remove(s);
  }


  public Utility getPrimaryUtility(Stimulus s){
    return stimulationParameters.getPrimaryUtility().get(s);
  }
  
  public StimulationHints getStimulationHints(){
    return stimulationParameters.getStimulationHints();
  }


  @Override
  public void loadXML(Element xml) {
    
    stimulationParameters.loadXML(xml.getChild("stimulation-parameters"));
    
    conditioningParameters.loadXML(xml.getChild("conditioning-parameters"));
    
    // Make sure the implication is ready to be loaded. The several stimuli must be added
    // before we define their implication relation.
    for(Stimulus s: stimulationParameters.getStimuli()){
      Vertex<Stimulus> v = new Vertex<Stimulus>(s);
      stimulusImplication.getStimuliGraph().putVertex(v);
    }
    
    stimulusImplication.loadXML(xml.getChild("stimulus-implication"));
    
  }


  @Override
  public Element toXML() {
    
    Element subsystemE = new Element("stimulation-subsystem");
    
    subsystemE.addContent(stimulationParameters.toXML()); 
    
    subsystemE.addContent(conditioningParameters.toXML());

    subsystemE.addContent(stimulusImplication.toXML());

    return subsystemE;
  }
  
  
}
