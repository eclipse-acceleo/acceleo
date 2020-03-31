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
package org.eclipse.acceleo.debug.util;

import org.eclipse.acceleo.debug.StackFrame;

/**
 * A listener notified when a {@link DSLDebugTargetAdapter} current instructions are changed.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IDSLCurrentInstructionListener {

	/**
	 * The current instruction has been changed for the given {@link StackFrame}.
	 * 
	 * @param debugModelID
	 *            the debug model identifier
	 * @param frame
	 *            the {@link StackFrame}
	 */
	void currentInstructionChanged(String debugModelID, StackFrame frame);

	/**
	 * The given {@link StackFrame} has terminated.
	 * 
	 * @param debugModelID
	 *            the debug model identifier
	 * @param frame
	 *            the {@link StackFrame}
	 */
	void terminated(String debugModelID, StackFrame frame);

	/**
	 * Sets the current {@link StackFrame}.
	 * 
	 * @param debugModelID
	 *            the debug model identifier
	 * @param frame
	 *            the current {@link StackFrame}
	 */
	void setCurrentFrame(String debugModelID, StackFrame frame);

}
