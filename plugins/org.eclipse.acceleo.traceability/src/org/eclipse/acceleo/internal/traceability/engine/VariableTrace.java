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

import org.eclipse.ocl.expressions.Variable;

/**
 * This will be used to map a given OCL variable to both its input element and generated artifact.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <C>
 *            Will be either EClassifier for ecore or Classifier for UML.
 * @param <PM>
 *            Will be either EParameter for ecore or Parameter for UML.
 */
public final class VariableTrace<C, PM> extends AbstractTrace {
	/** The actual variable we created this trace for. */
	private final Variable<C, PM> referredVariable;

	/**
	 * Prepares trace recording for the given variable.
	 * 
	 * @param variable
	 *            Variable we wish to record traceability information for.
	 */
	public VariableTrace(Variable<C, PM> variable) {
		referredVariable = variable;
	}

	/**
	 * Returns the variable this trace refers to.
	 * 
	 * @return The variable this trace refers to.
	 */
	public Variable<C, PM> getReferredVariable() {
		return referredVariable;
	}
}
