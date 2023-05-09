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
package org.eclipse.acceleo.debug.event.model;

/**
 * Request sent to validate a variable value.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ValidateVariableValueRequest extends AbstractVariableRequest {

	/**
	 * The variable value.
	 */
	private final String value;

	/**
	 * Constructor.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param stackName
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getName() thread name}
	 * @param variableName
	 *            the {@link org.eclipse.acceleo.debug.Variable#getName() variable name}
	 * @param value
	 *            the value to set
	 */
	public ValidateVariableValueRequest(Long threadID, String stackName, String variableName, String value) {
		super(threadID, stackName, variableName);
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
