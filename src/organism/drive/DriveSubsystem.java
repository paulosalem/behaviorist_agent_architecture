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
import java.util.List;
import java.util.Set;

import org.jdom.Element;
import organism.io.Storable;
import organism.stimulation.Stimulation;
import organism.stimulation.Stimulus;
import organism.stimulation.StimulusManager;
import organism.stimulation.Utility;

/**
 * The drive subsystem.
 * 
 * @author Paulo Salem
 *
 */
public class DriveSubsystem implements Storable, Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected StimulusManager stimulusManager;
	
  protected Set<Drive> activeDrives;
  
  protected SatiationOp satiationOp;
  
  protected DeprivationOp deprivationOp;
  
  protected StimulusDriveRegulator stimulusDriveRegulator;
  

  public DriveSubsystem(Set<Drive> activeDrives, StimulusManager stimulusManager) {
    super();
    this.stimulusManager = stimulusManager;
    this.activeDrives = activeDrives;
    
    this.satiationOp = new SatiationOp();
    this.deprivationOp = new DeprivationOp();
    
    this.stimulusDriveRegulator = new StimulusDriveRegulator(this);
  }

  public void performTDriveOp(Drive d, Set<Stimulation> stimulations){
    
    if(satiationOp.precondition(d, stimulations)){
      satiationOp.apply(d, stimulations);
    }
    
    // OR
    
    else if(deprivationOp.precondition(d, stimulations)){
      deprivationOp.apply(d, stimulations);
    }
  }
  
  public Set<Drive> getActiveDrives() {
    return activeDrives;
  }
  
  public StimulusDriveRegulator getStimulusDriveRegulator() {
    return stimulusDriveRegulator;
  }

  @Override
	public void loadXML(Element xml) {
		
	  assert(xml.getName().equals("drive-subsystem"));
	  
	  List<Element> dsE = xml.getChild("drives").getChildren();
	  Set<Drive> ds = new HashSet<Drive>(); 
	  
	  // Get each drive
	  for(Element dE: dsE){
		  
		  // Drive's importance
		  double u = Double.valueOf(dE.getChild("importance").getAttributeValue("value"));
		  Utility importance = new Utility(u);
		  
  		// Drive's max importance
		  u = Double.valueOf(dE.getChild("max-importance").getAttributeValue("value"));
		  Utility maxImportance = new Utility(u);
		  
	  	// Drive's min importance
		  u = Double.valueOf(dE.getChild("min-importance").getAttributeValue("value"));
		  Utility minImportance = new Utility(u);
		  
		  // Drive's desires
		  List<Element> desiresE = dE.getChild("desires").getChildren();
		  Set<Stimulus> desires = new HashSet<Stimulus>();
		  for(Element sE: desiresE){
			  int id = Integer.valueOf(sE.getAttributeValue("id"));
			  Stimulus s = stimulusManager.getPrototypeClone(id);
			  
			  desires.add(s);
		  }
		  
		  Drive d = new Drive(desires, importance, minImportance, maxImportance);
		  ds.add(d);
	  }
	 
	  // Set the loaded drives to be active
	  activeDrives = ds;
		
	}
  
  @Override
	public Element toXML() {
	  
	    Element subsysE = new Element("drive-subsystem");
	    Element dsE = new Element("drives");
	    subsysE.addContent(dsE);
	    
	    for(Drive d: activeDrives){
	      Element dE = new Element("drive");
	      dsE.addContent(dE);
	      
	      Element importanceE = new Element("importance");
	      importanceE.setAttribute("value", d.getImportance().toString());
	      dE.addContent(importanceE);
	      
	      Element maxImportanceE = new Element("max-importance");
        maxImportanceE.setAttribute("value", d.getMaxImportance().toString());
	      dE.addContent(maxImportanceE);
	        
	      Element minImportanceE = new Element("min-importance");
	      minImportanceE.setAttribute("value", d.getMinImportance().toString());
	      dE.addContent(minImportanceE);


	      
	      Element desiresE = new Element("desires");
	      dE.addContent(desiresE);
	      for(Stimulus s: d.getDesires()){
	    	  Element desireE = new Element("stimulus");
	    	  desireE.setAttribute("id", s.getId().toString());
	    	  
	    	  desiresE.addContent(desireE);
	      }
	     
	      	
	    }
	  
		return subsysE;
	}
}
