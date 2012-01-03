/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
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

	/** This will contain traceability information when the traceability is enabled on the current generation. */
	private final EObject traceabilityInformation;

	/**
	 * Instantiates an empty text generation event.
	 * 
	 * @since 3.0
	 */
	public AcceleoTextGenerationEvent() {
		this(null, null, null, null);
	}

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
		this(generatedText, block, source, null);
	}

	/**
	 * Instantiates a text generation event.
	 * 
	 * @param generatedText
	 *            Text which generation triggered this event.
	 * @param block
	 *            The block from which <code>generatedText</code> has been generated.
	 * @param source
	 *            EObject which triggered the generation.
	 * @param traceInformation
	 *            Traceability information about this event. This will be <code>null</code> in all cases when
	 *            traceability isn't enabled.
	 * @since 3.0
	 */
	public AcceleoTextGenerationEvent(String generatedText, Block block, EObject source,
			EObject traceInformation) {
		this.text = generatedText;
		this.block = block;
		this.source = source;
		this.traceabilityInformation = traceInformation;
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

	/**
	 * Returns the traceability information associated to this event.
	 * <p>
	 * In all cases when traceability isn't enabled, this will be <code>null</code>.
	 * </p>
	 * <p>
	 * This will be an instance of org.eclipse.acceleo.traceability.GeneratedFile GeneratedFile if the event
	 * has been sent for a generated file, or a org.eclipse.acceleo.traceability.TraceabilityModel
	 * TraceabilityModel if this event corresponds to the end of a generation.
	 * </p>
	 * 
	 * @return The traceability information associated to this event.
	 * @since 3.0
	 */
	public EObject getTraceabilityInformation() {
		return traceabilityInformation;
	}
}
