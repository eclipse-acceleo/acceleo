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
 * A {@link org.eclipse.acceleo.debug.Thread thread} contextual {@link IDSLDebuggerReply reply}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractThreadReply implements IDSLDebuggerReply {

	/**
	 * The {@link Thread#getId() ID}.
	 */
	private final Long id;

	/**
	 * Constructor.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 */
	public AbstractThreadReply(Long threadID) {
		this.id = threadID;
	}

	/**
	 * Gets the {@link Thread#getId() ID}.
	 * 
	 * @return the {@link Thread#getId() ID}
	 */
	public Long getThreadID() {
		return id;
	}
}
