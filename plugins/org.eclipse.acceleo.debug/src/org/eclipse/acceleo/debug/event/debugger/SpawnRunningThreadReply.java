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

import org.eclipse.emf.ecore.EObject;

/**
 * Reply sent when the debugger has created a thread.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SpawnRunningThreadReply extends AbstractThreadReply {

	/**
	 * The context of the thread.
	 */
	private final EObject context;

	/**
	 * The thread name.
	 */
	private String threadName;

	/**
	 * Constructor.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param threadName
	 *            the {@link Thread#getName() thread name}
	 * @param context
	 *            the {@link EObject} representing the current context of the thread
	 */
	public SpawnRunningThreadReply(Long threadID, String threadName, EObject context) {
		super(threadID);
		this.threadName = threadName;
		this.context = context;
	}

	public String getThreadName() {
		return threadName;
	}

	/**
	 * Gets the thread context.
	 * 
	 * @return the thread context
	 */
	public EObject getContext() {
		return context;
	}

}
