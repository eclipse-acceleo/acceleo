/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.ls;

import org.eclipse.acceleo.debug.IDSLDebugger;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.debug.event.debugger.IDSLDebuggerReply;

/**
 * Factory for {@link IDSLDebugger}.
 *
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IDSLDebuggerFactory {

	/**
	 * Creates a {@link IDSLDebugger}.
	 * 
	 * @param processor
	 *            the {@link IDSLDebugEventProcessor} handling {@link IDSLDebuggerReply}
	 * @return the created {@link IDSLDebugger}
	 */
	IDSLDebugger createDebugger(IDSLDebugEventProcessor processor);

}
