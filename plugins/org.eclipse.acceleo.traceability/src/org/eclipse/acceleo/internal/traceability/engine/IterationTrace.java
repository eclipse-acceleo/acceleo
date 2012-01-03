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
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.Variable;

/**
 * This specific implementation of an {@link ExpressionTrace} will allow us to recall the current iteration
 * index.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <C>
 *            Will be either EClassifier for ecore or Classifier for UML.
 * @param <PM>
 *            Will be either EParameter for ecore or Parameter for UML.
 */
public final class IterationTrace<C, PM> extends ExpressionTrace<C> {
	/** Keeps track of the last iteration count. */
	private int lastIteration = -1;

	/** Keeps track of the current iteration's last offset. */
	private int lastOffsetOfCurrentIteration;

	/** Traces for the current iteration. */
	private Map<InputElement, Set<GeneratedText>> currentIterationTraces;

	/** Variable corresponding to these traces. */
	private Variable<C, PM> variable;

	/**
	 * Default constructor, simply delegates to super.
	 * 
	 * @param expression
	 *            Expression we wish to record traceability information for.
	 */
	public IterationTrace(OCLExpression<C> expression) {
		this(null, expression);
	}

	/**
	 * Default constructor, simply delegates to super.
	 * 
	 * @param var
	 *            The corresponding loop variable, if any.
	 * @param expression
	 *            Expression we wish to record traceability information for.
	 */
	public IterationTrace(Variable<C, PM> var, OCLExpression<C> expression) {
		super(expression);
		this.variable = var;
	}

	/**
	 * Returns the last iteration count.
	 * 
	 * @return The last iteration count.
	 */
	public int getLastIteration() {
		return lastIteration;
	}

	/**
	 * Advances to the next traces, given the String value of the next iteration.
	 * 
	 * @param next
	 *            The next String value of the iteration.
	 */
	public void advanceIteration(String next) {
		int startOffset = lastOffsetOfCurrentIteration;
		int endOffset = startOffset + next.length();
		currentIterationTraces = new LinkedHashMap<InputElement, Set<GeneratedText>>();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : getTraces().entrySet()) {
			for (GeneratedText text : entry.getValue()) {
				if (text.getStartOffset() >= startOffset && text.getEndOffset() <= endOffset) {
					Set<GeneratedText> iterationText = currentIterationTraces.get(entry.getKey());
					if (iterationText == null) {
						iterationText = new CompactLinkedHashSet<GeneratedText>();
						currentIterationTraces.put(entry.getKey(), iterationText);
					}
					iterationText.add(text);
					if (text.getEndOffset() > lastOffsetOfCurrentIteration) {
						lastOffsetOfCurrentIteration = text.getEndOffset();
					}
				}
			}
		}
		lastIteration++;
	}

	/**
	 * Returns the traces for the current iteration.
	 * 
	 * @return The traces for the current iteration.
	 */
	public Map<InputElement, Set<GeneratedText>> getTracesForIteration() {
		return currentIterationTraces;
	}

	/**
	 * Returns the corresponding Variable.
	 * 
	 * @return The corresponding Variable.
	 */
	public Variable<C, PM> getVariable() {
		return variable;
	}
}
