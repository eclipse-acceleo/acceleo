/*******************************************************************************
 * Copyright (c) 2010, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.language.acceleo;

import java.util.concurrent.Callable;

import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;

/**
 * This implementation of an {@link AbstractLanguageInterpreter} will be able to provide completion, syntax
 * highlighting, compilation and evaluation of any given Acceleo expression.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoInterpreter extends AbstractLanguageInterpreter {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#getEvaluationTask(org.eclipse.acceleo.ui.interpreter.language.EvaluationContext)
	 */
	@Override
	public Callable<EvaluationResult> getEvaluationTask(EvaluationContext context) {
		// TODO Auto-generated method stub
		return null;
	}
}
