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
package organism;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import organism.drive.Drive;
import organism.drive.DriveSubsystem;
import organism.emotion.EmotionSubsystem;
import organism.general.Correlation;
import organism.general.Intensity;
import organism.general.Probability;
import organism.io.Storable;
import organism.responding.Action;
import organism.responding.ActionBaselevel;
import organism.responding.ActionConflict;
import organism.responding.ActionManager;
import organism.responding.Actions;
import organism.responding.RespondingSubsystem;
import organism.responding.Response;
import organism.responding.operant.Discrimination;
import organism.responding.operant.Operant;
import organism.responding.operant.OperantImplication;
import organism.responding.operant.OperantSubsystem;
import organism.responding.operant.OperantUtility;
import organism.responding.operant.OperantUtility_Ref1;
import organism.responding.respondent.Reflex;
import organism.responding.respondent.ReflexParameters;
import organism.stimulation.Conditioning_Ref1_Parameters;
import organism.stimulation.Stimulation;
import organism.stimulation.StimulationHints;
import organism.stimulation.StimulationParameters;
import organism.stimulation.StimulationSubsystem;
import organism.stimulation.Stimulus;
import organism.stimulation.StimulusManager;
import organism.stimulation.Utility;
import organism.util.graph.Arc;
import organism.util.z.ZOperation;

/**
 * A simulated organism.
 * 
 * @author Paulo Salem
 *
 */
public class Organism implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * The stimulation subsystem.
   */
  private StimulationSubsystem stimulationSubsystem;
  
  /**
   * The responding subsystem.
   */
  private RespondingSubsystem respondingSubsystem;
  
  /**
   * The drive subsystem.
   */
  private DriveSubsystem driveSubsystem;
  
  /**
   * The emotion subsystem.
   */
  private EmotionSubsystem emotionSubsystem;
  
  

  /**
   * Creates an empty organism. This is constructor will typically be used to
   * create organisms for later configuration. All necessary structures are available,
   * but either empty or with innocuous settings.
   */
  public Organism(){
    StimulusManager stimulusManager = new StimulusManager();
    ActionManager actionManager = new ActionManager();
    Actions actions = new Actions(actionManager); 
    
    buildOrganism(stimulusManager,
        new StimulationParameters(stimulusManager), 
        new Conditioning_Ref1_Parameters(new Correlation(0.0)), 
        actionManager,
        actions, 
        new ActionConflict(actionManager), 
        new ActionBaselevel(actions), 
        new HashSet<Reflex>(), 
        new HashSet<Operant>(), 
        new HashSet<Drive>());
  }

  
  
  public Organism(StimulusManager stimulusManager, StimulationParameters stimulationParameters, Conditioning_Ref1_Parameters conditioningParameters, ActionManager actionManager, Actions actions, ActionConflict actionConflict, ActionBaselevel actionBaselevel, Set<Reflex> reflexes, Set<Operant> operants, Set<Drive> drives){
    buildOrganism(stimulusManager, stimulationParameters, conditioningParameters, actionManager, actions, actionConflict, actionBaselevel, reflexes, operants, drives);
  }
  
  public Organism(StimulationParameters stimulationParameters, Conditioning_Ref1_Parameters conditioningParameters){
    StimulusManager stimulusManager = new StimulusManager();
    ActionManager actionManager = new ActionManager();
	Actions actions = new Actions(new HashSet<Action>(), new HashSet<Action>(), actionManager);
	ActionBaselevel actionBaselevel = new ActionBaselevel(actions, new HashMap<Action, Probability>());
	  
    buildOrganism(stimulusManager, stimulationParameters, conditioningParameters, actionManager,  actions, new ActionConflict(actionManager), actionBaselevel, new HashSet<Reflex>(), new HashSet<Operant>(), new HashSet<Drive>());
  }
  
  /**
   * Creates a simple organism for the sake of testing. Should be used to test
   * tools that manipulate organims.
   * 
   * @return A simple organism.
   */
  public static Organism createTestOrganism(){
    
	// Test actions
	Action a0 = new Action(0);
	Action a1 = new Action(1);
	Action a2 = new Action(2);
	Action a3 = new Action(3);
	Action a4 = new Action(4);
    
    // Test stimuli
    Stimulus s0 = new Stimulus(0);
    Stimulus s1 = new Stimulus(1);
    Stimulus s2 = new Stimulus(2);
    Stimulus s3 = new Stimulus(3);
    Stimulus s4 = new Stimulus(4);
    Stimulus s5 = new Stimulus(5);
    
    StimulusManager stimulusManager = new StimulusManager();
    ActionManager actionManager = new ActionManager();
    
    
    //
    // Test operants
    //
    
    Set<Set<Stimulus>> antecedents = new HashSet<Set<Stimulus>>();
    Set<Stimulus> antecedent0 = new HashSet<Stimulus>(); // empty set
    Set<Stimulus> antecedent1 = new HashSet<Stimulus>();
    antecedent1.add(s4);
    antecedent1.add(s5);
    
    antecedents.add(antecedent0);
    antecedents.add(antecedent1);
    
    
    
    Operant o0 = new Operant(antecedents, a0, s0, actionManager, stimulusManager);
    o0.getConsequenceContingency().put(antecedent0, new Correlation(0.6));
    o0.getConsequenceContingency().put(antecedent1, new Correlation(0.2));
    
    Set<Operant> operants = new HashSet<Operant>();
    operants.add(o0);
    
    //
    // Test reflexes
    //
    
    Reflex r0 = new Reflex(ReflexParameters.createTestParameters(), s5, a4, new Intensity(0.3), new Probability(1.0), new Intensity(1.0), 11, 2, actionManager, stimulusManager);
    
    Set<Reflex> reflexes = new HashSet<Reflex>();
    reflexes.add(r0);
    
    //
    // Test drives
    //
    
    Set<Stimulus> desires0 = new HashSet<Stimulus>();
    desires0.add(s0);
    
    Set<Stimulus> desires1 = new HashSet<Stimulus>();
    desires1.add(s1);
    
    Set<Stimulus> desires2 = new HashSet<Stimulus>();
    desires2.add(s2);
    
    Drive d0 = new Drive(desires0, new Utility(0.0), Utility.MIN, Utility.MAX);
    Drive d1 = new Drive(desires1, new Utility(-1.0), Utility.MIN, Utility.MAX);
    Drive d2 = new Drive(desires2, new Utility(1.0), Utility.MIN, Utility.MAX);
    
    Set<Drive> drives = new HashSet<Drive>();
    drives.add(d0);
    drives.add(d1);
    drives.add(d2);

    
    //
    // Stimulation subsystem settings
    //
    
    // Stimuli
    Set<Stimulus> stimuli = new HashSet<Stimulus>();

    stimuli.add(s0);
    stimuli.add(s1);
    stimuli.add(s2);
    stimuli.add(s3);
    stimuli.add(s4);
    stimuli.add(s5);
    
    
    // Primary stimuli and their utilities
    Set<Stimulus> primaryStimuli  = new HashSet<Stimulus>();
    Map<Stimulus, Utility> primaryUtility = new HashMap<Stimulus, Utility>();
    
    primaryStimuli.add(s0);
    primaryStimuli.add(s1);
    primaryStimuli.add(s2);
    
    primaryUtility.put(s0, new Utility(0.9));
    primaryUtility.put(s1, new Utility(0.5));
    primaryUtility.put(s2, new Utility(0.1));
    
    
    // Stimulation hints
    Set<Stimulus> pleasureHints = new HashSet<Stimulus>();
    Set<Stimulus> painHints = new HashSet<Stimulus>();
    
    pleasureHints.add(s0);
    painHints.add(s2);
    
    StimulationHints stimHints = new StimulationHints(pleasureHints, painHints, stimulusManager);
    
    
    // The stimulus implication relation begins empty
    
    
    // Maximum delay
    int maxDelay = 10;

    
    // Conditioning parameters
    Conditioning_Ref1_Parameters condParam = new Conditioning_Ref1_Parameters(new Correlation(0.8));

    // Stimulation parameters
    StimulationParameters stimParam = new StimulationParameters(stimHints, stimuli, primaryStimuli, primaryUtility,  maxDelay, stimulusManager);

    
    
    //
    // Responding settings
    //
    


    Set<Action> as = new HashSet<Action>();
    as.add(a0);
    as.add(a1);
    as.add(a2);
    as.add(a3);
    as.add(a4);
    
    Actions actions = new Actions(as, as, actionManager);
    
    ActionConflict actionConflict = new ActionConflict(actionManager);
    
    
    Map<Action, Probability> baseLevel = new HashMap<Action, Probability>();
    baseLevel.put(a0, new Probability(0.1));
    baseLevel.put(a1, new Probability(0.05));
    baseLevel.put(a2, new Probability(0.08));
    baseLevel.put(a3, new Probability(0.01));
    baseLevel.put(a4, new Probability(0.02));
    
	ActionBaselevel actionBaselevel = new ActionBaselevel(actions, baseLevel);

	
	
	//
    // Setup the organism
	//
	
    Organism organism = new Organism(stimulusManager, stimParam, condParam, actionManager, actions, actionConflict, actionBaselevel, reflexes, operants, drives);
    
    // Setup stimuli prototypes
    organism.getStimulationSubsystem().getStimulusManager().putPrototype(s0);
    organism.getStimulationSubsystem().getStimulusManager().putPrototype(s1);
    organism.getStimulationSubsystem().getStimulusManager().putPrototype(s2);
    organism.getStimulationSubsystem().getStimulusManager().putPrototype(s3);
    organism.getStimulationSubsystem().getStimulusManager().putPrototype(s4);
    organism.getStimulationSubsystem().getStimulusManager().putPrototype(s5);

    
    // Setup some test stimulus conditioning
    organism.getStimulationSubsystem().getStimulusImplication().putCause(s1, s2, new Correlation(1.0));
    organism.getStimulationSubsystem().getStimulusImplication().putCause(s1, s3, new Correlation(1.0));
    organism.getStimulationSubsystem().getStimulusImplication().putCause(s3, s4, new Correlation(1.0));
    
    
    // Setup actions prototypes
    organism.getRespondingSubsystem().getActionManager().putPrototype(a0);
    organism.getRespondingSubsystem().getActionManager().putPrototype(a1);
    organism.getRespondingSubsystem().getActionManager().putPrototype(a2);
    organism.getRespondingSubsystem().getActionManager().putPrototype(a3);
    organism.getRespondingSubsystem().getActionManager().putPrototype(a4);
    


    
    return organism;
  }
  
  
  private void buildOrganism(StimulusManager stimulusManager, StimulationParameters stimulationParameters, Conditioning_Ref1_Parameters conditioningParameters, ActionManager actionManager, Actions actions, ActionConflict actionConflict, ActionBaselevel actionBaselevel, Set<Reflex> reflexes, Set<Operant> operants, Set<Drive> drives){
    driveSubsystem = new DriveSubsystem(drives, stimulusManager);
    stimulationSubsystem = new StimulationSubsystem(stimulusManager, stimulationParameters, conditioningParameters, driveSubsystem);
    emotionSubsystem = new EmotionSubsystem(stimulationSubsystem);

    Discrimination discrimination = new Discrimination();
    OperantImplication operantImplication = new OperantImplication(operants, stimulationSubsystem.getStimulusImplication(), discrimination);
    OperantUtility operantUtility = new OperantUtility_Ref1(stimulationSubsystem.getStimulusUtility(), operantImplication, discrimination);
    OperantSubsystem operantSubsystem =  new OperantSubsystem(operants, actionManager, operantImplication, operantUtility, stimulationSubsystem, emotionSubsystem);
    
    respondingSubsystem = new RespondingSubsystem(stimulationSubsystem, operantSubsystem, emotionSubsystem, actionManager,  actions, actionConflict, actionBaselevel, reflexes, operants);
    
  }
  
  /**
   * Performs all the stimuli processing required in the present iteration.
   * 
   * @param stimulations New stimulations to be delivered to the organism.
   * @param currentInstant The current instant.
   */
  @ZOperation(name = "Organism_StimulusProcessing")
	public void performStimuliProcessing(Set<Stimulation> stimulations,
			int currentInstant) {

        //
        // Saves current stimuli
        //
        stimulationSubsystem.performPastStimuliUpdateOp(currentInstant);
    
	    //
	    // Update current stimuli
	    //
	  
	    // Must work on a copy to prevent concurrent modifications erros
        Set<Stimulus> copy = (Set<Stimulus>)stimulationSubsystem.getCurrentStimuli().clone();
	    // Perform the actual update
		for (Stimulus s : copy) {
			stimulationSubsystem.performCurrentStimuliUpdate(s);
		}
	  
	  
		// Perform conditioning
		for (Stimulation st : stimulations) {

			if(st.getStatus() == Stimulation.StimulusStatus.BEGINNING){
			  
			  // All the stimuli that have already happened in the past and which are within maxDelay time
			  for(Stimulus s: stimulationSubsystem.getStimulusManager().getAllPrototypes()){
			    if(stimulationSubsystem.getStimulusBeginning(s) != null){
			      if(stimulationSubsystem.getStimulusBeginning(s) >= currentInstant - stimulationSubsystem.getMaxDelay()){
			        
			        // Apply the conditioning
			        int delay = currentInstant - stimulationSubsystem.getStimulusBeginning(s);
			        stimulationSubsystem.performConditioning(s, st.getStimulus(), delay);
			        
			      }
			    }
			  }
			  
			  
			  // TODO remove, it is obsolete.
			  /*
				for (Stimulus s : stimulationSubsystem.getCurrentStimuli()) { // TODO
																				// is
																				// this
																				// right?
					int delay = currentInstant
							- stimulationSubsystem.getStimulusBeginning(s);
					stimulationSubsystem.performConditioning(s,
							st.getStimulus(), delay);
				}
				*/
			}
		}
		
		
		// Figure out new stimuli
		for (Stimulation st : stimulations) {
			stimulationSubsystem.performStimulationUpdate(currentInstant, st);
		}
		
    // Perform unconditioning
    for(Arc<Stimulus> a: stimulationSubsystem.getStimulusImplication().getStimuliGraph().getArcs()){
      Stimulus s1 = a.getBeginning().getContent();
      Stimulus s2 = a.getEnd().getContent();
      
      stimulationSubsystem.performUnconditioning(s1, s2, currentInstant);
    }

	}
  
  
  /**
	 * Performs the relevant tasks in order to schedule new behavior.
	 * 
	 * @param stimulations
	 *            New stimulations to be delivered to the organism.
	 * @param currentInstant
	 *            The current instant.
	 */
  @ZOperation(name = "Organism_BehaviorSelection")
  public void performBehaviorSelection(Set<Stimulation> stimulations, int currentInstant){
    
    // Schedule operants
    respondingSubsystem.performOperantScheduling();
    
    // Schedule reflexes
    for(Stimulation st: stimulations){
      if(st.getStatus().equals(Stimulation.StimulusStatus.BEGINNING)){
        respondingSubsystem.performReflexScheduling(st.getStimulus(), st.getIntensity());
      }
    }
    
    // Schedule spontaneous actions
    respondingSubsystem.performBaseLevelScheduling(currentInstant);
  }
  

  /**
   * Resolves conflicts between behaviors that might arise after their selection.
   */
  @ZOperation(name = "Organism_ConflictResolution")
  public void performConflictResolution(){
    respondingSubsystem.performConflictResolution();
  }
  
  
  /**
   * Emits the behavioral responses associated with the previously
   * scheduled behavior.
   * 
   * @currentInstant The current instant.
   */
  @ZOperation(name = "Organism_ResponseEmission")
  public void performResponseEmission(int currentInstant){
    
    // Emit operants
    for(Operant o: respondingSubsystem.getCurrentBehaviors().getEmitted()){
      respondingSubsystem.performOperantEmission(o, currentInstant);
    }
    
    // Emit reflexes
    for(Reflex r: respondingSubsystem.getCurrentBehaviors().getElicited()){
      respondingSubsystem.performReflexElicitation(r, currentInstant);
    }
    
    // Emit spontaneous actions
    for(Action a: respondingSubsystem.getCurrentBehaviors().getSpontaneous()){
      respondingSubsystem.performBaseLevelEmission(a, currentInstant);
    }
  }
  
  
  /**
   * Performs maintenance tasks associated with current behavior.
   */
  @ZOperation(name = "Organism_ResponseMaintenance")
  public void performResponseMaintenance(){

    // Update each response
    for(Response rp: respondingSubsystem.getCurrentResponses().getResponses()){
      respondingSubsystem.performResponseUpdate(rp);
    }
    
    // Terminate responses, if necessary
    for(Response rp: respondingSubsystem.getCurrentResponses().getResponses()){
      respondingSubsystem.performResponseTermination(rp);
    }
  }
  
  
  /**
   * Performs the necessary maintenance on the current operants available
   * to the organism.
   * 
   * @param stimulations New stimulation to be delivered to the organism.
   * @param currentInstant The current instant.
   */
  @ZOperation(name = "Organism_OperantOp")
  public void performOperantOp(Set<Stimulation> stimulations, int currentInstant){
    
    int maxDelay = stimulationSubsystem.getMaxDelay();
    
    for(Stimulation st: stimulations){
      if(st.getStatus() != Stimulation.StimulusStatus.ABSENT){
        for(int t = Math.max(currentInstant - maxDelay, 0); t < currentInstant; t++){
          for(Action a: respondingSubsystem.getActionHistory().getActions(t)){
            for(Operant o: respondingSubsystem.getOperants()){
            
              respondingSubsystem.performTOperantOp(o, (Set<Stimulus>) stimulationSubsystem.getPastStimuli().get(t).clone(), st.getStimulus(), a, currentInstant - t);
              
            }
          }
        }
      }
    }
  }
  
  
  /**
   * Creates new operants if possible.
   * 
   * @param stimulations New stimulation to be delivered to the organism.
   * @param currentInstant The current instant.
   */
  @ZOperation(name = "Organism_OperantFormationOp")
  public void performOperantFormationOp(Set<Stimulation> stimulations, int currentInstant){
    
    Set<Operant> nOs = new HashSet<Operant>();
    
    int maxDelay = stimulationSubsystem.getMaxDelay();

    for(Stimulation st: stimulations){
      if(st.getStatus() != Stimulation.StimulusStatus.ABSENT){
        for(int t = Math.max(currentInstant - maxDelay, 0); t < currentInstant; t++){
          for(Action a: respondingSubsystem.getActionHistory().getActions(t)){
            
              Operant nO = respondingSubsystem.performTOperantFormationOp((Set<Stimulus>)stimulationSubsystem.getPastStimuli().get(t).clone(), st.getStimulus(), a, currentInstant - t);
              if(nO != null){
                nOs.add(nO);
              }
            
          }
        }
      }
    }
    
    // Add the new operants that have been formed
    respondingSubsystem.getOperants().addAll(nOs);
  }

  @ZOperation(name = "Organism_OperantEliminationOp")
  public void performOperantEliminationOp(){

	  // A copy of the operants is required to avoid concurrent modifications to the set of operants
	  Set<Operant> copy = new HashSet<Operant>();
	  for(Operant o: respondingSubsystem.getOperants()){
		  copy.add(o);
	  }
	  
	  for(Operant o: copy){
		  respondingSubsystem.performTOperantEliminationOp(o);
	  }
  }

  /**
   * Performs the necessary maintenance on the current drives available
   * to the organism.
   * 
   * @param stimulation New stimulation to be delivered to the organism.
   */  
  @ZOperation(name = "Organism_DrivesUpdate")
  public void performDrivesUpdate(Set<Stimulation> stimulations){

    for(Drive d: driveSubsystem.getActiveDrives()){
        driveSubsystem.performTDriveOp(d, stimulations);
    }
  }
  
  
  /**
   * Performs the necessary maintenance on the current emotions available
   * to the organism.
   */
  @ZOperation(name = "Organism_EmotionUpdate")
  public void performEmotionUpdate(){
    emotionSubsystem.performUpdateDepressionOp();
    emotionSubsystem.performEndDepressionOp();
    
    emotionSubsystem.performUpdateAngerOp();
    emotionSubsystem.performEndAngerOp();
    
    emotionSubsystem.performUpdateFrustrationOp();
    emotionSubsystem.performEndFrustrationOp();
  }

  public StimulationSubsystem getStimulationSubsystem() {
    return stimulationSubsystem;
  }

  public RespondingSubsystem getRespondingSubsystem() {
    return respondingSubsystem;
  }

  public DriveSubsystem getDriveSubsystem() {
    return driveSubsystem;
  }

  public EmotionSubsystem getEmotionSubsystem() {
    return emotionSubsystem;
  }

  @Override
  public void loadXML(Element xml) {
  
    stimulationSubsystem.loadXML(xml.getChild("stimulation-subsystem"));
    respondingSubsystem.loadXML(xml.getChild("responding-subsystem"));
    driveSubsystem.loadXML(xml.getChild("drive-subsystem"));
    emotionSubsystem.loadXML(xml.getChild("emotion-subsystem"));
  
  }

  @Override
  public Element toXML() {
    
    Element organismE = new Element("organism");
    
    organismE.addContent(stimulationSubsystem.toXML());
    organismE.addContent(respondingSubsystem.toXML());
    organismE.addContent(driveSubsystem.toXML());
    organismE.addContent(emotionSubsystem.toXML());

	return organismE;
  }

 
  
}
