/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.parser.ast;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * An AST provider. You can create an AST model with the method 'createAST'. An AST is created in a single
 * resource. All the dependencies of this resource should be searched in the other resources of the same
 * 'ResourceSet'. You should resolve the links between the different AST models of the current 'ResourceSet'
 * with the method 'resolveAST'.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface IASTProvider {

	/**
	 * Gets the AST model. It is created when you call the 'createAST' function.
	 * 
	 * @return the AST model
	 */
	org.eclipse.acceleo.model.mtl.Module getAST();

	/**
	 * Creates the CST model. This function must be called before 'createAST'
	 */
	void createCST();

	/**
	 * Creates the AST model. This function must be called before 'resolveAST' and after 'createCST'
	 * 
	 * @param resource
	 *            where the AST will be stored
	 */
	void createAST(Resource resource);

	/**
	 * Resolution step of the AST creation: OCL and invocations. This function must be called after
	 * 'createAST'.
	 */
	void resolveAST();

	/**
	 * To log a new problem.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	@Deprecated
	void log(final String message, final int posBegin, final int posEnd);

	/**
	 * To log a new problem.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	void logProblem(final String message, final int posBegin, final int posEnd);

	/**
	 * To log a new warning.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the warning
	 * @param posEnd
	 *            is the ending index of the warning
	 */
	void logWarning(final String message, final int posBegin, final int posEnd);

	/**
	 * To log a new info.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the info
	 * @param posEnd
	 *            is the ending index of the info
	 */
	void logInfo(final String message, final int posBegin, final int posEnd);

	/***
	 * Returns the line number of the given offset.
	 * 
	 * @param offset
	 *            Offset we need to know the line of.
	 * @return The line number of the given offset.
	 * @since 0.8
	 */
	int getLineOfOffset(int offset);
}
