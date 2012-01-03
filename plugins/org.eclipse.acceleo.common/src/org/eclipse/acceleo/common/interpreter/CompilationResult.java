/*****************************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *****************************************************************************************/
package org.eclipse.acceleo.common.interpreter;

import org.eclipse.core.runtime.IStatus;

/**
 * This will be used as the result of our compilation tasks. It will hold a reference to the actual compiled
 * element (Module, ModuleElement, OCLExpression ...) along with the status of the compilation.
 * <p>
 * {@link #status} can be either a simple IStatus (only a single error/warning/info has been encountered) or a
 * MultiStatus with all encountered problems as children.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
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
	private IStatus status;

	/**
	 * Creates a compilation result given the compilation issues. This assumes that critical errors were
	 * encountered during the compilation, and that the expression could not be compiled.
	 * 
	 * @param status
	 *            The status of the compilation. Can be a {@link org.eclipse.core.runtime.MultiStatus}.
	 */
	public CompilationResult(IStatus status) {
		this.status = status;
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
	 * Creates a compilation result given the compiled expression and the status of the compilation.
	 * 
	 * @param compiledExpression
	 *            Result of the compilation.
	 * @param status
	 *            The status of the compilation. Can be a {@link org.eclipse.core.runtime.MultiStatus}.
	 */
	public CompilationResult(Object compiledExpression, IStatus status) {
		this.compiledExpression = compiledExpression;
		this.status = status;
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
	 * This can be used to change the module element to which this result points.
	 * 
	 * @param newCompiledExpression
	 *            The new module element to reference.
	 */
	public void setCompiledExpression(Object newCompiledExpression) {
		this.compiledExpression = newCompiledExpression;
	}

	/**
	 * Returns the status of the compilation.
	 * 
	 * @return The status of the compilation
	 */
	public IStatus getStatus() {
		return status;
	}
}
