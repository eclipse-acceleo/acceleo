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
package org.eclipse.acceleo.internal.ide.ui.debug.core;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.ocl.utilities.ASTNode;

/**
 * Item of the debugger execution stack. The debugger stack is shown in the "Launch View". Each item is
 * characterized by a line in an Acceleo file and a set of variables.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class StackInfo {
	/** AST node this stack element points to. */
	private ASTNode node;

	/**
	 * The current Acceleo file.
	 */
	private File file;

	/**
	 * A line in the Acceleo file.
	 */
	private int line;

	/**
	 * The beginning character of the current AST node of the Acceleo file.
	 */
	private int charStart;

	/**
	 * The ending character of the current AST node of the Acceleo file.
	 */
	private int charEnd;

	/**
	 * The variables to show in the "Variables View" before an input step or after an output step. This map
	 * contains the variable names and their values.
	 */
	private Map<String, Object> variables = new LinkedHashMap<String, Object>();

	/**
	 * Gets the current AST node.
	 * 
	 * @return the AST node.
	 */
	public ASTNode getASTNode() {
		return node;
	}

	/**
	 * Sets the current AST node.
	 * 
	 * @param node
	 *            is the AST node
	 */
	public void setNode(ASTNode node) {
		this.node = node;
	}

	/**
	 * Gets the current Acceleo file.
	 * 
	 * @return the template file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the current Acceleo file.
	 * 
	 * @param file
	 *            is the template file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Gets the line in the Acceleo file.
	 * 
	 * @return the line in the Acceleo file
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Sets the line in the Acceleo file.
	 * 
	 * @param line
	 *            is the line in the template file
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * Gets the beginning character of the current AST node of the Acceleo file.
	 * 
	 * @return the index of the first character
	 */
	public int getCharStart() {
		return charStart;
	}

	/**
	 * Sets the beginning character of the current AST node of the Acceleo file.
	 * 
	 * @param charStart
	 *            is the index of the first character
	 */
	public void setCharStart(int charStart) {
		this.charStart = charStart;
	}

	/**
	 * Gets the ending character of the current AST node of the Acceleo file.
	 * 
	 * @return the index of the last character
	 */
	public int getCharEnd() {
		return charEnd;
	}

	/**
	 * Sets the ending character of the current AST node of the Acceleo file.
	 * 
	 * @param charEnd
	 *            is the index of the last character
	 */
	public void setCharEnd(int charEnd) {
		this.charEnd = charEnd;
	}

	/**
	 * Adds a new variable for the given name and the given value. It will be shown in the "Variables View"
	 * before an input step or after an output step.
	 * 
	 * @param aName
	 *            is the name of the new variable
	 * @param aValue
	 *            is the value of the new variable
	 */
	public void addVariable(String aName, Object aValue) {
		variables.put(aName, aValue);
	}

	/**
	 * Gets the variables to show in the "Variables View" before an input step or after an output step. It
	 * returns a map that contains the variable names and their values.
	 * 
	 * @return the variables of the execution context
	 */
	public Map<String, Object> getVariables() {
		return variables;
	}
}
