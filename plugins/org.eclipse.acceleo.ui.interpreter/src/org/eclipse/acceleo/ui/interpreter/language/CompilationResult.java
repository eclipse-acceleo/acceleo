/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.language;

import org.eclipse.core.runtime.IStatus;

/**
 * This should be used as the result of a language interpreter's compilation task. Later on, it will be passed
 * to the evaluation task of the language interpreter.
 * <p>
 * {@link #problems} can be populated with either a simple {@link org.eclipse.core.runtime.Status} or a
 * {@link org.eclipse.core.runtime.MultiStatus}. If this is not <code>null</code>, the issue(s) will be
 * reported on the interpreter UI.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CompilationResult {
	/**
	 * This will hold the actual result of the compilation. Note that this can legally be <code>null</code> if
	 * the compilation encountered critical errors.
	 */
	private Object compiledExpression;

	/**
	 * This can hold any info, warning or error that has been encountered by the compilation. This can legally
	 * be <code>null</code> if no problem was encountered.
	 */
	private IStatus problems;

	/**
	 * Creates a compilation result given the compilation issues. This assumes that critical errors were
	 * encountered during the compilation, and that the expression could not be compiled.
	 * 
	 * @param problems
	 *            The problems encountered during the compilation. Can be a
	 *            {@link org.eclipse.core.runtime.MultiStatus}.
	 */
	public CompilationResult(IStatus problems) {
		this.problems = problems;
	}

	/**
	 * Creates a compilation result given the compiled expression. This assumes that no issues were
	 * encountered during the compilation.
	 * 
	 * @param compiledExpression
	 *            Result of the compilation.
	 */
	public CompilationResult(Object compiledExpression) {
		this.compiledExpression = compiledExpression;
	}

	/**
	 * Creates a compilation result given the compiled expression and the problems encountered while compiling
	 * it.
	 * 
	 * @param compiledExpression
	 *            Result of the compilation.
	 * @param problems
	 *            The problems encountered during the compilation. Can be a
	 *            {@link org.eclipse.core.runtime.MultiStatus}.
	 */
	public CompilationResult(Object compiledExpression, IStatus problems) {
		this.compiledExpression = compiledExpression;
		this.problems = problems;
	}

	/**
	 * Returns the compiled expression.
	 * 
	 * @return The compiled expression.
	 */
	public Object getCompiledExpression() {
		return compiledExpression;
	}

	/**
	 * Returns the problems encountered during this compilation.
	 * 
	 * @return The problems encountered during this compilation.
	 */
	public IStatus getProblems() {
		return problems;
	}

	/**
	 * Sets the compiled expression that resulted from this compilation.
	 * 
	 * @param compiledExpression
	 *            The compiled expression that resulted from this compilation.
	 */
	public void setCompiledExpression(Object compiledExpression) {
		this.compiledExpression = compiledExpression;
	}

	/**
	 * Sets the problems encountered during this compilation to the given new status.
	 * 
	 * @param problems
	 *            The problems encountered during this compilation.
	 */
	public void setProblems(IStatus problems) {
		this.problems = problems;
	}
}
