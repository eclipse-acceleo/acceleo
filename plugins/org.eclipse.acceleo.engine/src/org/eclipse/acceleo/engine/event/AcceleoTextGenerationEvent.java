/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.event;

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.emf.ecore.EObject;

/**
 * This event will be fired when text is generated. It will hold references to the generated text, the object
 * which triggered the generation and the Block from which it was generated.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoTextGenerationEvent {
	/** This will hold the generated text. */
	private final String text;

	/** This field will hold a reference towards the originating block. */
	private final Block block;

	/** The object which triggered generation of this text. */
	private final EObject source;

	/**
	 * Instantiates a text generation event.
	 * 
	 * @param generatedText
	 *            Text which generation triggered this event.
	 * @param block
	 *            The block from which <code>generatedText</code> has been generated.
	 * @param source
	 *            EObject which triggered the generation.
	 */
	public AcceleoTextGenerationEvent(String generatedText, Block block, EObject source) {
		this.text = generatedText;
		this.block = block;
		this.source = source;
	}

	/**
	 * Returns the generated text.
	 * 
	 * @return The generated text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * returns the originating block.
	 * 
	 * @return The originating block.
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * Returns the object which triggered generation of this text.
	 * 
	 * @return The object which triggered generation of this text.
	 */
	public EObject getSource() {
		return source;
	}
}
