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
 * A {@link org.eclipse.acceleo.debug.Thread thread} contextual {@link IDSLModelRequest request}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractThreadRequest implements IDSLModelRequest {

	/**
	 * The {@link Thread#getId()}.
	 */
	private final Long threadID;

	/**
	 * Constructor.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 */
	public AbstractThreadRequest(Long threadID) {
		this.threadID = threadID;
	}

	/**
	 * Gets the {@link Thread#getId()}.
	 * 
	 * @return the {@link Thread#getId()}
	 */
	public Long getThreadID() {
		return threadID;
	}

}
