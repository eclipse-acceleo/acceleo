/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.traceability.engine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.TraceabilityFactory;

/**
 * This will serve as the base class for traceability contexts.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public abstract class AbstractTrace {
	/** This will keep track of the current starting offset for added traces. */
	protected int currentOffset;

	/** maps input elements corresponding to this expression to the bits they were used to generate. */
	protected final LinkedHashMap<InputElement, Set<GeneratedText>> traces = new LinkedHashMap<InputElement, Set<GeneratedText>>();

	/**
	 * Adds the given trace to the list of traces corresponding to this expression, setting the offset as it
	 * goes.
	 * 
	 * @param input
	 *            Input element from which this region has been generated.
	 * @param trace
	 *            The actual trace that is to be recorded for this expression.
	 * @param length
	 *            Length of this new trace.
	 */
	public void addTrace(InputElement input, GeneratedText trace, int length) {
		Set<GeneratedText> referredTraces = traces.get(input);
		if (referredTraces == null) {
			referredTraces = new CompactLinkedHashSet<GeneratedText>();
			traces.put(input, referredTraces);
		}
		int startOffset = currentOffset;
		currentOffset = currentOffset + length;
		trace.setSourceElement(input);
		trace.setStartOffset(startOffset);
		trace.setEndOffset(currentOffset);
		referredTraces.add(trace);
	}

	/**
	 * Adds the given trace to the list of traces corresponding to this expression, setting the offset as it
	 * goes.
	 * 
	 * @param input
	 *            Input element from which this region has been generated.
	 * @param trace
	 *            The actual trace that is to be recorded for this expression.
	 * @param value
	 *            Generated text value. This will be used to properly set the trace's offsets.
	 */
	public void addTrace(InputElement input, GeneratedText trace, Object value) {
		if (value != null) {
			addTrace(input, trace, value.toString().length());
		}
	}

	/**
	 * Copies the given trace and adds it to this expression trace.
	 * 
	 * @param other
	 *            The trace we are to copy.
	 */
	public void addTraceCopy(AbstractTrace other) {
		int gap = currentOffset;
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : other.getTraces().entrySet()) {
			InputElement input = entry.getKey();
			Set<GeneratedText> referredTraces = traces.get(input);
			if (referredTraces == null) {
				referredTraces = new CompactLinkedHashSet<GeneratedText>();
				traces.put(input, referredTraces);
			}

			for (GeneratedText original : entry.getValue()) {
				GeneratedText text = TraceabilityFactory.eINSTANCE.createGeneratedText();
				currentOffset = currentOffset + original.getEndOffset() - original.getStartOffset();
				text.setSourceElement(input);
				text.setStartOffset(original.getStartOffset() + gap);
				text.setEndOffset(original.getEndOffset() + gap);
				text.setModuleElement(original.getModuleElement());
				referredTraces.add(text);
			}
		}
	}

	/**
	 * Disposes of this trace.
	 */
	public void dispose() {
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : traces.entrySet()) {
			entry.getValue().clear();
		}
		traces.clear();
	}

	/**
	 * Returns the current offset of this trace.
	 * 
	 * @return Current offset of this trace.
	 */
	public int getOffset() {
		return currentOffset;
	}

	/**
	 * Returns this context's traces.
	 * 
	 * @return A copy of this context's traces.
	 */
	public LinkedHashMap<InputElement, Set<GeneratedText>> getTraces() {
		return traces;
	}

	/**
	 * We might need to reset the offset to a given value. This can be used to this end.
	 * 
	 * @param offset
	 *            Offset to reset to.
	 */
	public void setOffset(int offset) {
		currentOffset = offset;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Current offset: " + this.currentOffset); //$NON-NLS-1$
		buffer.append(" --- Traces: " + this.traces); //$NON-NLS-1$
		return buffer.toString();
	}
}
