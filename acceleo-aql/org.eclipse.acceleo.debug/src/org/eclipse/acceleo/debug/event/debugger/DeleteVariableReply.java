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
package org.eclipse.acceleo.debug.event.debugger;

/**
 * Reply sent when the thread has to delete a variable.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DeleteVariableReply extends AbstractThreadReply {

	/**
	 * The variable name.
	 */
	private final String name;

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.Thread Thread}.
	 * 
	 * @param threadName
	 *            the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 * @param name
	 *            the variable name
	 */
	public DeleteVariableReply(String threadName, String name) {
		super(threadName);
		this.name = name;
	}

	/**
	 * Gets the variable name.
	 * 
	 * @return the variable name
	 */
	public String getName() {
		return name;
	}

}
