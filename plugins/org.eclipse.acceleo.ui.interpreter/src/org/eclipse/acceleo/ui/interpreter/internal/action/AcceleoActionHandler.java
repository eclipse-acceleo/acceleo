/*******************************************************************************
 * Copyright (c) 2010, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.action;

import org.eclipse.acceleo.ui.interpreter.internal.view.AcceleoInterpreterView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This will be used as the action handler for all of Acceleo's source viewer operations.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoActionHandler extends AbstractHandler {
	/** Action ID of the "Evaluate" action. */
	public static final String EVALUTE_ACTION_ID = "org.eclipse.acceleo.ui.interpreter.evaluateaction"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (EVALUTE_ACTION_ID.equals(event.getCommand().getId())) {
			final IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
			if (activePart instanceof AcceleoInterpreterView) {
				EvaluateAction action = new EvaluateAction();
				action.initialize((AcceleoInterpreterView)activePart);
				action.run();
			}
		}
		return null;
	}
}
