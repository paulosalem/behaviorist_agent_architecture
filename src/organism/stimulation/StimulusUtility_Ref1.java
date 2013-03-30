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

import java.util.HashSet;
import java.util.Set;

import organism.drive.DriveSubsystem;
import organism.emotion.EmotionSubsystem;
import organism.general.Magnitude;
import organism.util.graph.Graph;
import organism.util.graph.Vertex;

/**
 * Provides a refinement to the organism's utility function. It simply
 * uses the utility of the best reachable stimulus in the implication
 * closure in order to define the utility of the specified stimulus.
 * In particular, it ignores the value of correlations among stimuli.
 * 
 * @author Paulo Salem
 *
 */
public class StimulusUtility_Ref1 extends StimulusUtility{
  
  

  public StimulusUtility_Ref1(StimulationSubsystem stimulationSubsystem, EmotionSubsystem emotionSubsystem, DriveSubsystem driveSubsystem){
    super(stimulationSubsystem, emotionSubsystem, driveSubsystem);
  }
  
  @Override
  public Utility utility(Stimulus s) {
    
    Utility u = emotionSubsystem.getStimulusEmotionalRegulator().emotionalRegulator(s, base(s));
    u = driveSubsystem.getStimulusDriveRegulator().driveRegulator(s, u);
    
    return u;
  }
  
  
  protected Utility base(Stimulus s){
    
    Stimulus best = findBestReachableFrom(s);
    
    if(best != null){
      return stimulationSubsystem.getPrimaryUtility(best);
    }
    else{
      // If we can't locate a utility at all, than assign it
      // a neutral one
      return Utility.NEUTRAL;
    }
    
  }

  
  protected Stimulus findBestReachableFrom(Stimulus s){
    
    Graph<Stimulus> g = stimulationSubsystem.getStimulusImplication().getStimuliGraph();
    Vertex<Stimulus> u = g.getVertexThatContains(s);
    
    //
    // Perform a breadth-first search
    //
    
    // Mark all vertices as non-visited
    for(Vertex v: g.getVertices()){
    	v.setAuxB1(false);
    }
    
    Utility max = Utility.MIN;
    Stimulus best = null;
    
    // Begin at u
    Set<Vertex<Stimulus>> vs = new HashSet<Vertex<Stimulus>>();
    vs.add(u);
    u.setAuxB1(true);
    
    // Loop while there are vertices to see    
    while(!(vs.size() == 0)){
      Set<Vertex<Stimulus>> next = new HashSet<Vertex<Stimulus>>();
      for(Vertex<Stimulus> v: vs){
        
        // Check if it is a primary stimulus
        if(stimulationSubsystem.getPrimaryStimuli().contains(v.getContent())){
          
          // Check if we found a better stimulus
          Utility ut = stimulationSubsystem.getPrimaryUtility(v.getContent());
          if(ut.greaterThanOrEqualTo(max)){
            max = ut;
            best = v.getContent();
          }
        }
        
        Set<Vertex<Stimulus>> adj = g.getAdjacentVertices(v);
        for(Vertex x: adj){
        	if(!x.isAuxB1()){
        		next.add(x);
        		x.setAuxB1(true); // visited
        	}
        }
      
      }
      
      // Discard the vertices already analyzed and go on to
      // the next ones
      vs = next;
    }
    
    
    return best;
  }

}
