/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.language;

import java.util.Collections;
import java.util.List;

/**
 * Instances of this class represent an expression that has been split into an arbitrary number of
 * sub-components.
 * 
 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
 */
public class SplitExpression {
	/** The expression as a whole, before any splitting took place. */
	private final Object fullExpression;

	/** Sub-steps computed for this expression. */
	private final List<SubExpression> subSteps;

	/**
	 * Instantiates a split expression given its full expression and the list of its sub-steps.
	 * 
	 * @param fullExpression
	 *            The expression as a whole.
	 * @param subSteps
	 *            The list of sub-components of this expression.
	 */
	public SplitExpression(Object fullExpression, List<SubExpression> subSteps) {
		this.fullExpression = fullExpression;
		this.subSteps = subSteps;
	}

	/**
	 * Returns the full expression represented by this instance.
	 * 
	 * @return The full expression represented by this instance.
	 */
	public Object getFullExpression() {
		return fullExpression;
	}

	/**
	 * Returns an immutable view of the sub steps composing this expression.
	 * 
	 * @return An immutable view of the sub steps composing this expression.
	 */
	public List<SubExpression> getSubSteps() {
		return Collections.unmodifiableList(subSteps);
	}
}
