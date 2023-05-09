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
public class ValidateVariableValueReply extends AbstractVariableReply {

	/**
	 * Tells if the value is valid.
	 */
	private boolean valid;

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.Thread Thread}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param stackName
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getName() stack frame name}
	 * @param variableName
	 *            the {@link org.eclipse.acceleo.debug.Variable#getName() variable name}
	 * @param valid
	 *            tells if the value is valid
	 */
	public ValidateVariableValueReply(Long threadID, String stackName, String variableName, boolean valid) {
		super(threadID, stackName, variableName);
		this.valid = valid;
	}

	/**
	 * Tells if the value is valid.
	 * 
	 * @return <code>true</code> if the value is valid, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return valid;
	}
}
