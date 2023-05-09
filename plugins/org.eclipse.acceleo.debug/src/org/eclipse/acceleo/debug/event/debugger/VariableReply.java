/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.debugger;

/**
 * Reply sent when the thread has a new variable.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class VariableReply extends AbstractVariableReply {

	/**
	 * The declaration type name.
	 */
	private final String declarationTypeName;

	/**
	 * The variable values.
	 */
	private final Object value;

	/**
	 * Tells if the value can be changed.
	 */
	private final boolean supportModifications;

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.Thread Thread}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param stackName
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getName() stack frame name}
	 * @param declarationTypeName
	 *            the declaration type name
	 * @param variableName
	 *            the variable name
	 * @param value
	 *            the variable value
	 * @param supportModifications
	 *            tells if the value can be changed
	 */
	public VariableReply(Long threadID, String stackName, String declarationTypeName, String variableName,
			Object value, boolean supportModifications) {
		super(threadID, stackName, variableName);
		this.declarationTypeName = declarationTypeName;
		this.value = value;
		this.supportModifications = supportModifications;
	}

	/**
	 * Gets the declaration type name.
	 * 
	 * @return the declaration type name
	 */
	public String getDeclarationTypeName() {
		return declarationTypeName;
	}

	/**
	 * Gets the variable value.
	 * 
	 * @return the variable value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Tells if the value can be changed.
	 * 
	 * @return <code>true</code> if the value can be changed, <code>false</code> otherwise
	 */
	public boolean supportModifications() {
		return supportModifications;
	}

}
