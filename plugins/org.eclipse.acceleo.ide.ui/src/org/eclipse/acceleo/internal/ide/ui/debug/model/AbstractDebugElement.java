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

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

/**
 * An Acceleo debug model element. Common abstract class for the debug target, the process, the stack frame,
 * the thread...
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public abstract class AbstractDebugElement extends PlatformObject implements IDebugElement {

	/**
	 * Containing target.
	 */
	protected IDebugTarget fTarget;

	/**
	 * Constructs a new debug element contained in the given debug target.
	 * 
	 * @param target
	 *            debug target (Acceleo VM)
	 */
	public AbstractDebugElement(IDebugTarget target) {
		fTarget = target;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDebugElement#getModelIdentifier()
	 */
	public String getModelIdentifier() {
		return AcceleoModelPresentation.ID_ACCELEO_DEBUG_MODEL;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDebugElement#getDebugTarget()
	 */
	public IDebugTarget getDebugTarget() {
		return fTarget;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDebugElement#getLaunch()
	 */
	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.PlatformObject#getAdapter(java.lang.Class)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		Object result;
		if (adapter == IDebugElement.class) {
			result = this;
		} else if (adapter == ILaunch.class) {
			result = getLaunch();
		} else {
			result = super.getAdapter(adapter);
		}
		return result;
	}

	/**
	 * Throws a debug exception.
	 * 
	 * @param message
	 *            is the message
	 * @param e
	 *            is the exception
	 * @throws DebugException
	 *             the given message is used to create and throw a new debug exception
	 */
	protected void abort(String message, Throwable e) throws DebugException {
		throw new DebugException(new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
				DebugPlugin.INTERNAL_ERROR, message, e));
	}

	/**
	 * Notifies all registered debug event set listeners of the given debug event.
	 * 
	 * @param event
	 *            the event to be fired
	 */
	protected void fireEvent(DebugEvent event) {
		DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] {event });
	}

	/**
	 * Fires a <code>CREATE</code> event for this element.
	 */
	protected void fireCreationEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CREATE));
	}

	/**
	 * Fires a <code>RESUME</code> event for this element with the given detail.
	 * 
	 * @param detail
	 *            event detail code
	 */
	public void fireResumeEvent(int detail) {
		fireEvent(new DebugEvent(this, DebugEvent.RESUME, detail));
	}

	/**
	 * Fires a <code>SUSPEND</code> event for this element with the given detail.
	 * 
	 * @param detail
	 *            event detail code
	 */
	public void fireSuspendEvent(int detail) {
		fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, detail));
	}

	/**
	 * Fires a <code>TERMINATE</code> event for this element.
	 */
	protected void fireTerminateEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
	}

	/**
	 * Returns the breakpoint manager.
	 * 
	 * @return the breakpoint manager
	 * @see IBreakpointManager
	 */
	protected IBreakpointManager getBreakpointManager() {
		return DebugPlugin.getDefault().getBreakpointManager();
	}
}
