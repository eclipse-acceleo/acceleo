/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.debug.core;

/**
 * Debugger events listener during a template evaluation.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface ITemplateDebuggerListener {

	/**
	 * Resume after client action.
	 */
	void resumeClient();

	/**
	 * Resume after one step.
	 */
	void resumeStep();

	/**
	 * Suspend on a breakpoint.
	 */
	void suspendBreakpoint();

	/**
	 * Suspend after a step.
	 */
	void suspendStep();

	/**
	 * Beginning action.
	 */
	void start();

	/**
	 * Ending action.
	 */
	void end();

}
