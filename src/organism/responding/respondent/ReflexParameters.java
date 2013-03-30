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
package organism.responding.respondent;

import java.io.Serializable;

import org.jdom.Element;
import organism.general.Intensity;
import organism.general.Probability;
import organism.io.Storable;

/**
 * General restrictions over a reflex.
 * 
 * @author Paulo Salem
 *
 */
public class ReflexParameters implements Storable, Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  protected Probability maxElicitation;
  
  protected Probability minElicitation;
  
  protected Intensity maxStrength;
  
  protected Intensity minStrength;
  
  protected int maxDuration;
  
  protected int minDuration;
  
  protected int maxLatency;
  
  protected int minLatency;
  
  protected Intensity maxThreshold;
  
  protected Intensity minThreshold;
  
  public ReflexParameters(){
    this.maxElicitation = new Probability(1.0);
    this.minElicitation = new Probability(0.0);
    this.maxStrength = new Intensity(1.0);
    this.minStrength = new Intensity(0.0);
    this.maxDuration = 100;
    this.minDuration = 2;
    this.maxLatency = 100;
    this.minLatency = 0;
    this.maxThreshold = new Intensity(1.0);;
    this.minThreshold = new Intensity(0.0);;
  }
  
  public ReflexParameters(Probability maxElicitation,
      Probability minElicitation, Intensity maxStrength, Intensity minStrength,
      int maxDuration, int minDuration, int maxLatency, int minLatency,
      Intensity maxThreshold, Intensity minThreshold) {
    super();
    this.maxElicitation = maxElicitation;
    this.minElicitation = minElicitation;
    this.maxStrength = maxStrength;
    this.minStrength = minStrength;
    this.maxDuration = maxDuration;
    this.minDuration = minDuration;
    this.maxLatency = maxLatency;
    this.minLatency = minLatency;
    this.maxThreshold = maxThreshold;
    this.minThreshold = minThreshold;
  }
  
  public static ReflexParameters createTestParameters(){
	  return new ReflexParameters(
			  new Probability(1.0), 
			  new Probability(0.9), 
			  new Intensity(1.0), 
			  new Intensity(0.5),
			  10,
			  2,
			  10,
			  1,
			  new Intensity(0.1), 
			  new Intensity(0.3));
  }

  public Probability deltaElicitation(Probability p, int instant1, int instant2){
    // TODO
    return p;
  }
  
  public Intensity deltaStrength(Intensity i, int instant1, int instant2){
    // TODO
    return i;
  }
  
  public int deltaDuration(int duration, int instant1, int instant2){
    // TODO
    return duration;
  }
  
  public int deltaLatency(int latency, int instant1, int instant2){
    // TODO
    return latency;
  }
  
  public Intensity deltaThreshold(Intensity i, int instant1, int instant2){
    // TODO
    return i;
  }

  @Override
  public void loadXML(Element xml) {

    
    Element maxElicitationE = xml.getChild("max-elicitation");
    maxElicitation = new Probability(Double.parseDouble(maxElicitationE.getAttributeValue("value")));
    
    Element minElicitationE = xml.getChild("min-elicitation");
    minElicitation = new Probability(Double.parseDouble(minElicitationE.getAttributeValue("value")));
    
    Element maxStrengthE = xml.getChild("max-strength");
    maxStrength = new Intensity(Double.parseDouble(maxStrengthE.getAttributeValue("value")));
    
    Element minStrengthE = xml.getChild("min-strength");
    minStrength = new Intensity(Double.parseDouble(minStrengthE.getAttributeValue("value")));
    
    Element maxDurationE = xml.getChild("max-duration");
    maxDuration = Integer.parseInt(maxDurationE.getAttributeValue("value"));
    
    Element minDurationE = xml.getChild("min-duration");
    minDuration = Integer.parseInt(minDurationE.getAttributeValue("value"));
    
    Element maxLatencyE = xml.getChild("max-latency");
    maxLatency = Integer.parseInt(maxLatencyE.getAttributeValue("value"));
    
    Element minLatencyE = xml.getChild("min-latency");
    minLatency = Integer.parseInt(minLatencyE.getAttributeValue("value"));
    
    Element maxThresholdE = xml.getChild("max-threshold");
    maxThreshold = new Intensity(Double.parseDouble(maxThresholdE.getAttributeValue("value")));
    
    Element minThresholdE = xml.getChild("min-threshold");
    minThreshold = new Intensity(Double.parseDouble(minThresholdE.getAttributeValue("value")));
    
  }

  @Override
  public Element toXML() {
    
    Element parametersE = new Element("reflex-parameters");
    
    Element maxElicitationE = new Element("max-elicitation");
    parametersE.addContent(maxElicitationE);
    maxElicitationE.setAttribute("value", maxElicitation.toString());
    
    Element minElicitationE = new Element("min-elicitation");
    parametersE.addContent(minElicitationE);
    minElicitationE.setAttribute("value", minElicitation.toString());
    
    Element maxStrengthE = new Element("max-strength");
    parametersE.addContent(maxStrengthE);
    maxStrengthE.setAttribute("value", maxStrength.toString());
    
    Element minStrengthE = new Element("min-strength");
    parametersE.addContent(minStrengthE);
    minStrengthE.setAttribute("value", minStrength.toString());
    
    Element maxDurationE = new Element("max-duration");
    parametersE.addContent(maxDurationE);
    maxDurationE.setAttribute("value", Integer.toString(maxDuration));
    
    Element minDurationE = new Element("min-duration");
    parametersE.addContent(minDurationE);
    minDurationE.setAttribute("value", Integer.toString(minDuration));
    
    Element maxLatencyE = new Element("max-latency");
    parametersE.addContent(maxLatencyE);
    maxLatencyE.setAttribute("value", Integer.toString(maxLatency));
    
    Element minLatencyE = new Element("min-latency");
    parametersE.addContent(minLatencyE);
    minLatencyE.setAttribute("value", Integer.toString(minLatency));
    
    Element maxThresholdE = new Element("max-threshold");
    parametersE.addContent(maxThresholdE);
    maxThresholdE.setAttribute("value", maxThreshold.toString());
    
    Element minThresholdE = new Element("min-threshold");
    parametersE.addContent(minThresholdE);
    minThresholdE.setAttribute("value", minThreshold.toString());

    return parametersE;
  }
}
