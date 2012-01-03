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
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.acceleo.ui.interpreter.view.InterpreterView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This will be used to launch the load module action.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class LoadModuleHandler extends AbstractHandler {

	/**
	 * Commend ID of the load module command. Must be kept in sync with the plugin.xml declaration.
	 */
	public static final String LOAD_MODULE_COMMAND_ID = "org.eclipse.acceleo.ide.ui.interpreter.load.module"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (LOAD_MODULE_COMMAND_ID.equals(event.getCommand().getId())) {
			final IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
			if (activePart instanceof InterpreterView) {
				InterpreterView interpreterView = (InterpreterView)activePart;
				AbstractLanguageInterpreter currentLanguageInterpreter = interpreterView
						.getCurrentLanguageInterpreter();
				if (currentLanguageInterpreter instanceof AcceleoInterpreter) {
					AcceleoInterpreter acceleoInterpreter = (AcceleoInterpreter)currentLanguageInterpreter;
					// acceleoInterpreter.runLoadModuleAction();
				}
			}
		}
		return null;
	}

}
