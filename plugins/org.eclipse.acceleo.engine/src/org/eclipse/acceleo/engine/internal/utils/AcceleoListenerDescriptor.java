/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.utils;

import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Describes a traceability listener as contributed to the extension point.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public final class AcceleoListenerDescriptor {

	/**
	 * The configuration element of this descriptor.
	 */
	private final IConfigurationElement element;

	/**
	 * The name of the class of the listener.
	 */
	private final String className;

	/**
	 * Instance of the listener that's been created through this descriptor.
	 **/
	private IAcceleoTextGenerationListener listener;

	/**
	 * The nature used by this listener.
	 */
	private final String nature;

	/**
	 * Indicates if this listener forces the processing of traceability information by the Acceleo engine.
	 */
	private final boolean forceTraceability;

	/**
	 * The constructor.
	 * 
	 * @param elem
	 *            Configuration elemen from which to create this descriptor.
	 */
	public AcceleoListenerDescriptor(IConfigurationElement elem) {
		this.element = elem;
		this.className = elem.getAttribute(TraceabilityRegistryListeners.TRACEABILITY_LISTENERS_CLASS);
		this.nature = elem.getAttribute(TraceabilityRegistryListeners.TRACEABILITY_LISTENERS_NATURE);
		String traceability = elem
				.getAttribute(TraceabilityRegistryListeners.TRACEABILITY_LISTENERS_FORCE_TRACEABILITY);
		this.forceTraceability = Boolean.valueOf(traceability).booleanValue();
	}

	/**
	 * Returns the listener instance.
	 * 
	 * @return the listener instance.
	 */
	public IAcceleoTextGenerationListener getTraceabilityListener() {
		if (listener == null) {
			try {
				Object object = element
						.createExecutableExtension(TraceabilityRegistryListeners.TRACEABILITY_LISTENERS_CLASS);
				if (object instanceof IAcceleoTextGenerationListener) {
					listener = (IAcceleoTextGenerationListener)object;
				}
			} catch (final CoreException e) {
				AcceleoEnginePlugin.log(e, false);
			}
		}
		return listener;
	}

	/**
	 * Returns the name of the class of the listener.
	 * 
	 * @return The name of the class of the listener.
	 */
	public Object getName() {
		return className;
	}

	/**
	 * Returns the nature used by this listener.
	 * 
	 * @return The nature used by this listener.
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * Indicates if this listener forces the traceability.
	 * 
	 * @return <code>true</code> if this listener forces the processing of the traceability,
	 *         <code>false</code> otherwise.
	 */
	public boolean isForceTraceability() {
		return forceTraceability;
	}
}
