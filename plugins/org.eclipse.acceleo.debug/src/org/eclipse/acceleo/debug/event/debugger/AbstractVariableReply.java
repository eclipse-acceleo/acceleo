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
 * A {@link org.eclipse.acceleo.debug.Varaible variable} contextual {@link IDSLDebuggerReply reply}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractVariableReply extends AbstractThreadReply {

	/**
	 * The {@link org.eclipse.acceleo.debug.StackFrame#getName() stack frame name}.
	 */
	private final String stackName;

	/**
	 * The variable name.
	 */
	private final String variableName;

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.Thread Thread}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param stackName
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getName() stack frame name}
	 * @param variableName
	 *            the {@link org.eclipse.acceleo.debug.Variable#getName() variable name}
	 */
	public AbstractVariableReply(Long threadID, String stackName, String variableName) {
		super(threadID);
		this.stackName = stackName;
		this.variableName = variableName;
	}

	/**
	 * Gets the {@link org.eclipse.acceleo.debug.StackFrame#getName() stack frame name}.
	 * 
	 * @return the {@link org.eclipse.acceleo.debug.StackFrame#getName() stack frame name}
	 */
	public String getStackName() {
		return stackName;
	}

	/**
	 * Gets the {@link org.eclipse.acceleo.debug.Variable#getName() variable name}.
	 * 
	 * @return the {@link org.eclipse.acceleo.debug.Variable#getName() variable name}
	 */
	public String getVariableName() {
		return variableName;
	}

}
