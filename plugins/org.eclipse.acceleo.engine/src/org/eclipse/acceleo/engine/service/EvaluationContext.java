/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.service;

import com.google.common.collect.ListMultimap;

import org.eclipse.acceleo.common.interpreter.CompilationResult;
import org.eclipse.emf.ecore.EObject;

/**
 * This represents the context of an evaluation as required by Acceleo.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public class EvaluationContext {
	/** Target of this evaluation. */
	private final EObject targetEObject;

	/** Variables accessible to this evaluation context. */
	private ListMultimap<String, Object> variables;

	/** The result of the expression's compilation. */
	private final CompilationResult compilationResult;

	/**
	 * Instantiates an evaluation context for the given compilation result.
	 * <p>
	 * The <em>targetEObject</em> must correspond to the compilation's target type, and <em>variables</em>
	 * must map every single variable that was accessible at compilation time.
	 * </p>
	 * 
	 * @param targetEObject
	 *            The target EObject of this evaluation.
	 * @param variables
	 *            Accessible variables for the evaluation.
	 * @param compilationResult
	 *            Result of the compilation.
	 */
	public EvaluationContext(EObject targetEObject, ListMultimap<String, Object> variables,
			CompilationResult compilationResult) {
		this.targetEObject = targetEObject;
		this.variables = variables;
		this.compilationResult = compilationResult;
	}

	/**
	 * Returns the target of this evaluation.
	 * 
	 * @return The target of this evaluation.
	 */
	public EObject getTargetEObject() {
		return targetEObject;
	}

	/**
	 * Returns the compilation result.
	 * 
	 * @return The compilation result.
	 */
	public CompilationResult getCompilationResult() {
		return compilationResult;
	}

	/**
	 * Returns the list of accessible variables.
	 * 
	 * @return The list of accessible variables.
	 */
	public ListMultimap<String, Object> getVariables() {
		return variables;
	}
}
