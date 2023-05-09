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
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param name
	 *            the variable name
	 */
	public DeleteVariableReply(Long threadID, String name) {
		super(threadID);
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
