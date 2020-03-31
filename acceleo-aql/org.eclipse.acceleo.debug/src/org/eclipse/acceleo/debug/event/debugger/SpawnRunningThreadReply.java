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
	 * Constructor.
	 * 
	 * @param threadName
	 *            the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 * @param context
	 *            the {@link EObject} representing the current context of the thread
	 */
	public SpawnRunningThreadReply(String threadName, EObject context) {
		super(threadName);
		this.context = context;
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
