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
 * Request sent to suspend the execution of a thread or the debugger.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SuspendRequest extends AbstractThreadRequest {

	/**
	 * Constructor for debugger.
	 */
	public SuspendRequest() {
		super(null);
	}

	/**
	 * Constructor for thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 */
	public SuspendRequest(Long threadID) {
		super(threadID);
	}

}
