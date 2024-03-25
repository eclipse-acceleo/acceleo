/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.handlers;

import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Wrap the selection to a {@link ProtectedArea}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class WrapInProtectedHandler extends DocumentRangeCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return execute(event, AcceleoLanguageServer.WRAP_IN_PROTECTED_COMMAND);
	}

}
