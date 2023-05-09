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
package org.eclipse.acceleo.aql.ls.debug.ide;

import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.debug.IDSLDebugger;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.debug.ls.IDSLDebuggerFactory;

public class AcceleoDebugFactory implements IDSLDebuggerFactory {

	@Override
	public IDSLDebugger createDebugger(IDSLDebugEventProcessor processor) {
		return new AcceleoDebugger(processor);
	}

}
