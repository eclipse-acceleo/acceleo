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
package org.eclipse.acceleo.model.mtl.impl.spec;

import org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl;
import org.eclipse.ocl.EvaluationVisitorDecorator;
import org.eclipse.ocl.utilities.Visitor;

/**
 * Specializes the implementation of the TemplateInvocation so that its accept() method delegates the
 * evaluation to our visitor.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TemplateInvocationSpec extends TemplateInvocationImpl {
	/**
	 * We know the visitor will be a decorator if the MTLEvaluationVisitor is in use (expected behavior of the
	 * MTL evaluation engine). This ensures we delegate the call to this decorator.
	 * 
	 * @param v
	 *            The current evaluation visitor.
	 * @param <T>
	 *            see {@link OCLExpression#accept(Visitor)}.
	 * @param <U>
	 *            see {@link OCLExpression#accept(Visitor)}.
	 * @return Result of this TemplateInvocation evaluation.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T, U extends Visitor<T, ?, ?, ?, ?, ?, ?, ?, ?, ?>> T accept(U v) {
		if (v instanceof EvaluationVisitorDecorator) {
			return (T)((EvaluationVisitorDecorator)v).visitExpression(this);
		}
		throw new UnsupportedOperationException();
	}
}
