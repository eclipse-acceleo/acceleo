/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.model;

/**
 * Request sent to set a variable value.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SetVariableValueRequest extends AbstractVariableRequest {

	/**
	 * The variable value.
	 */
	private final String value;

	/**
	 * Constructor.
	 * 
	 * @param threadName
	 *            the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 * @param stackName
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getName() thread name}
	 * @param variableName
	 *            the {@link org.eclipse.acceleo.debug.Variable#getName() variable name}
	 * @param value
	 *            the value to validate
	 */
	public SetVariableValueRequest(String threadName, String stackName, String variableName, String value) {
		super(threadName, stackName, variableName);
		this.value = value;
	}

	/**
	 * Gets the variable value.
	 * 
	 * @return the variable value
	 */
	public String getValue() {
		return value;
	}

}
