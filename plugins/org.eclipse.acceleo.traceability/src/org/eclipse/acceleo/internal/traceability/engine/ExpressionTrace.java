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
import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.expressions.OCLExpression;

/**
 * This will be used to map a given OCL expression to both its input element and generated artifact.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <C>
 *            Will be either EClassifier for ecore or Classifier for UML.
 */
public class ExpressionTrace<C> extends AbstractTrace {
	/** Expression for which this context trace has been created. */
	private final OCLExpression<C> referredExpression;

	/**
	 * Prepares trace recording for the given expression.
	 * 
	 * @param expression
	 *            Expression we wish to record traceability information for.
	 */
	public ExpressionTrace(OCLExpression<C> expression) {
		this.referredExpression = expression;
	}

	/**
	 * Allows for the copy of an expression trace. This will be a partial copy as the referenced GeneratedText
	 * and InputElement instances will be the same.
	 * 
	 * @param other
	 *            The trace that is to be copied.
	 */
	public ExpressionTrace(ExpressionTrace<C> other) {
		referredExpression = other.getReferredExpression();
		currentOffset = other.currentOffset;
		LinkedHashMap<InputElement, Set<GeneratedText>> temp = other.getTraces();
		// We need to replace the Set instance
		for (InputElement key : temp.keySet()) {
			traces.put(key, new CompactLinkedHashSet<GeneratedText>(temp.get(key)));
		}
	}

	/**
	 * Adds all of the given generated regions to the trace associated with <code>input</code>. The generated
	 * regions offsets will be modified to be set just after the current offset.
	 * 
	 * @param input
	 *            Input element for which to merge regions.
	 * @param regions
	 *            Regions we are to merge.
	 */
	public void mergeTrace(InputElement input, Set<GeneratedText> regions) {
		Set<GeneratedText> associatedTraces = traces.get(input);
		if (associatedTraces == null) {
			associatedTraces = new CompactLinkedHashSet<GeneratedText>();
			traces.put(input, associatedTraces);
		}

		for (GeneratedText region : regions) {
			@SuppressWarnings("cast")
			GeneratedText regionCopy = (GeneratedText)EcoreUtil.copy(region);
			int regionLength = region.getEndOffset() - region.getStartOffset();
			regionCopy.setStartOffset(currentOffset);
			regionCopy.setEndOffset(currentOffset + regionLength);
			currentOffset += regionLength;
			associatedTraces.add(regionCopy);
		}
	}

	/**
	 * Returns the expression this trace refers to.
	 * 
	 * @return The expression this trace refers to.
	 */
	public OCLExpression<C> getReferredExpression() {
		return referredExpression;
	}
}
