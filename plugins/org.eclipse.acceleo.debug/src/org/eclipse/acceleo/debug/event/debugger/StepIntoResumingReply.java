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
 * Reply sent when the thread is resumed by a step into.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class StepIntoResumingReply extends ResumingReply {

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.Thread Thread}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 */
	public StepIntoResumingReply(Long threadID) {
		super(threadID);
	}

}
