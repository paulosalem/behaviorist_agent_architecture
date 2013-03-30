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
package organism.io;

import org.jdom.Element;

/**
 * Classes that implement this interface are capable of being stored as XML files and later
 * restored from them.
 * 
 * @author Paulo Salem
 *
 */
public interface Storable {

	/**
	 * Returns a partial XML document describing the state of the object. Such description
	 * should be such that it can be loaded later by the <code>loadXML</code> method.
	 * 
	 * @return A partial XML document.
	 */
	public Element toXML();
	
	
	/**
	 * Loads a XML description, setting all object variables to the values described there.
	 */
	public void loadXML(Element xml);
}
