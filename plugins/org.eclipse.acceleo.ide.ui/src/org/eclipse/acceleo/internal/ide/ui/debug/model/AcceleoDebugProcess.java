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

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;

/**
 * The process of the debug target.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoDebugProcess extends AbstractDebugElement implements IProcess {

	/**
	 * Constructor.
	 * 
	 * @param target
	 *            is the debug target
	 */
	public AcceleoDebugProcess(IDebugTarget target) {
		super(target);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IProcess#getAttribute(java.lang.String)
	 */
	public String getAttribute(String key) {
		return getDebugTarget().getLaunch().getAttribute(key);
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
		return AcceleoUIMessages.getString("AcceleoProcess.Label"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.debug.model.AbstractDebugElement#getLaunch()
	 */
	@Override
	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
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
		getDebugTarget().getLaunch().setAttribute(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.debug.model.AbstractDebugElement#getAdapter(java.lang.Class)
	 */
	@Override
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
		return getDebugTarget().isTerminated();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}

}
