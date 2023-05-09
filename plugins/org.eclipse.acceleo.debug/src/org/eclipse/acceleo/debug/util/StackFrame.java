/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * A stack frame.
 *
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class StackFrame {

	/**
	 * The frame context.
	 */
	private final EObject context;

	/**
	 * The current instruction.
	 */
	private EObject instruction;

	/**
	 * Variable mapping.
	 */
	private Map<String, Object> variables;

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            the context
	 */
	public StackFrame(EObject context) {
		this.context = context;
	}

	/**
	 * Gets the current instruction.
	 * 
	 * @return the current instruction
	 */
	public EObject getInstruction() {
		return instruction;
	}

	/**
	 * Sets the current instruction.
	 * 
	 * @param instruction
	 *            the current instruction
	 */
	public void setInstruction(EObject instruction) {
		this.instruction = instruction;
	}

	/**
	 * Gets the variable mapping.
	 * 
	 * @return the variable mapping
	 */
	public Map<String, Object> getVariables() {
		return variables;
	}

	/**
	 * Sets the variable mapping.
	 * 
	 * @param variables
	 *            the variable mapping
	 */
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public EObject getContext() {
		return context;
	}

}
