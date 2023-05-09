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
package org.eclipse.acceleo.debug.event;

/**
 * A processor for {@link IDSLDebugEvent}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IDSLDebugEventProcessor {

	/**
	 * Handles the given {@link IDSLDebugEvent} event.
	 * 
	 * @param event
	 *            the {@link IDSLDebugEvent}
	 * @return an {@link Object} if any result is needed, <code>null</code> otherwise
	 */
	Object handleEvent(IDSLDebugEvent event);

}
