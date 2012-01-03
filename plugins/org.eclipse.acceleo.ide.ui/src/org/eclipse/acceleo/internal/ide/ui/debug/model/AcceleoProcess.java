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
package org.eclipse.acceleo.internal.ide.ui.debug.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;

/**
 * Standard Acceleo launch process.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoProcess implements IProcess {
	/** Table of client defined attributes. */
	private Map<String, String> attributes = new HashMap<String, String>();

	/** This will be set to true if this process is terminated. */
	private boolean terminated;

	/** Launch around which to wrap this process. */
	private final ILaunch launch;

	/**
	 * Constructs a process for the given launch.
	 * 
	 * @param launch
	 *            Launch around which this process will be wrapped.
	 */
	public AcceleoProcess(ILaunch launch) {
		this.launch = launch;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IProcess#getAttribute(java.lang.String)
	 */
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IProcess#getExitValue()
	 */
	public int getExitValue() throws DebugException {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IProcess#getLabel()
	 */
	public String getLabel() {
		return AcceleoUIMessages.getString("AcceleoProcess.Label.Running"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IProcess#getStreamsProxy()
	 */
	public IStreamsProxy getStreamsProxy() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IProcess#setAttribute(java.lang.String, java.lang.String)
	 */
	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return terminated;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		terminated = true;
		if (attributes != null) {
			attributes.clear();
			attributes = null;
		}
		DebugPlugin.getDefault().fireDebugEventSet(
				new DebugEvent[] {new DebugEvent(this, DebugEvent.TERMINATE) });
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		Object ret = null;
		if (adapter.equals(IProcess.class)) {
			ret = this;
		} else if (adapter.equals(IDebugTarget.class)) {
			ret = getLaunch().getDebugTarget();
		} else if (adapter.equals(ILaunch.class)) {
			ret = getLaunch();
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IProcess#getLaunch()
	 */
	public ILaunch getLaunch() {
		return launch;
	}

}
