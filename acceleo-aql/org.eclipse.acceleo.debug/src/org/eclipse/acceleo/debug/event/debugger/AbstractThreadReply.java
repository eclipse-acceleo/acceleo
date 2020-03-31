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
 * A {@link org.eclipse.acceleo.debug.Thread thread} contextual {@link IDSLDebuggerReply reply}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractThreadReply implements IDSLDebuggerReply {

	/**
	 * The {@link org.eclipse.acceleo.debug.Thread#getName() thread name}.
	 */
	private final String threadName;

	/**
	 * Constructor.
	 * 
	 * @param threadName
	 *            the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 */
	public AbstractThreadReply(String threadName) {
		this.threadName = threadName;
	}

	/**
	 * Gets the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}.
	 * 
	 * @return the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 */
	public String getThreadName() {
		return threadName;
	}

}
