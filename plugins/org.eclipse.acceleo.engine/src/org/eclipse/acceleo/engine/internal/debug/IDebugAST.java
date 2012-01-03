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
package org.eclipse.acceleo.engine.internal.debug;

import java.util.Map;

/**
 * The interface to implement to be able to debug an AST evaluation.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface IDebugAST {

	/**
	 * Start debugging an AST node.
	 * 
	 * @param astFragment
	 *            is the fragment of the AST node to debug
	 */
	void startDebug(ASTFragment astFragment);

	/**
	 * Update the input of the debugger for the current AST node.
	 * 
	 * @param astFragment
	 *            is the fragment of the AST node to debug
	 * @param variables
	 *            Variables accessible at the time.
	 */
	void stepDebugInput(ASTFragment astFragment, Map<String, Object> variables);

	/**
	 * Update the output of the debugger for the current AST node.
	 * 
	 * @param astFragment
	 *            is the fragment of the AST node to debug
	 * @param input
	 *            is the current input
	 * @param output
	 *            is the new output
	 */
	void stepDebugOutput(ASTFragment astFragment, Object input, Object output);

	/**
	 * Stop debugging an AST node.
	 * 
	 * @param astFragment
	 *            is the fragment of the AST node to debug
	 */
	void endDebug(ASTFragment astFragment);

}
