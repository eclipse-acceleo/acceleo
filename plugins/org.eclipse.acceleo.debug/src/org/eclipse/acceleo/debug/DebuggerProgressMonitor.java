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
package org.eclipse.acceleo.debug;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;

/**
 * An {@link Monitor} that {@link IDSLDebugger#consolePrint(String) print to the console} of the given
 * {@link IDSLDebugger}.
 *
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DebuggerProgressMonitor extends BasicMonitor {

	private final IDSLDebugger debugger;

	public DebuggerProgressMonitor(IDSLDebugger debugger) {
		this.debugger = debugger;
	}

	@Override
	public void beginTask(String name, int totalWork) {
		if (name != null && name.length() != 0) {
			debugger.consolePrint(">>> " + name + '\n');
		}
	}

	@Override
	public void setTaskName(String name) {
		if (name != null && name.length() != 0) {
			debugger.consolePrint("<>> " + name + '\n');
		}
	}

	@Override
	public void subTask(String name) {
		if (name != null && name.length() != 0) {
			debugger.consolePrint(">>  " + name + '\n');
		}
	}

	@Override
	public void setBlocked(Diagnostic reason) {
		super.setBlocked(reason);
		debugger.consolePrint("#>  " + reason.getMessage() + '\n');
	}

	@Override
	public void clearBlocked() {
		debugger.consolePrint("=>  " + getBlockedReason().getMessage() + '\n');
		super.clearBlocked();
	}
}
