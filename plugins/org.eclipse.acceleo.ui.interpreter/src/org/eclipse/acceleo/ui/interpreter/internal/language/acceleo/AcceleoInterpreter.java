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

import java.util.List;
import java.util.concurrent.Future;

import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;

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
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#getEvaluationTask()
	 */
	@Override
	public Future<List<Object>> getEvaluationTask() {
		// TODO Auto-generated method stub
		return null;
	}
}
