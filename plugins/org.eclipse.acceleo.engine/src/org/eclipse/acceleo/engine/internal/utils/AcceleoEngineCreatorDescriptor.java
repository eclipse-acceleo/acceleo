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
import org.eclipse.acceleo.engine.generation.IAcceleoEngineCreator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Describes an engine creator as contributed to the extension point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoEngineCreatorDescriptor {
	/** Name of the extension point's creator tag "class" atribute. */
	public static final String ENGINE_CREATORS_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$

	/** Name of the extension point's creator tag "label" atribute. */
	private static final String ENGINE_CREATORS_ATTRIBUTE_LABEL = "label"; //$NON-NLS-1$

	/** Configuration element of this descriptor. */
	private final IConfigurationElement element;

	/** Qualified class name of this engine creator. */
	private final String engineClassName;

	/** Instance of the engine that's been created through this descriptor. */
	private IAcceleoEngineCreator engineCreator;

	/** Label of this particular engine. */
	private final String label;

	/**
	 * Instantiates a descriptor with all information.
	 * 
	 * @param configuration
	 *            Configuration element from which to create this descriptor.
	 */
	public AcceleoEngineCreatorDescriptor(IConfigurationElement configuration) {
		element = configuration;
		this.label = configuration.getAttribute(ENGINE_CREATORS_ATTRIBUTE_LABEL);
		this.engineClassName = configuration.getAttribute(ENGINE_CREATORS_ATTRIBUTE_CLASS);
	}

	/**
	 * Returns the class name of this engine.
	 * 
	 * @return The class name of this engine.
	 */
	public String getClassName() {
		return engineClassName;
	}

	/**
	 * Returns the engine instance.
	 * 
	 * @return The engine instance.
	 */
	public IAcceleoEngineCreator getEngineCreator() {
		if (engineCreator == null) {
			try {
				engineCreator = (IAcceleoEngineCreator)element
						.createExecutableExtension(ENGINE_CREATORS_ATTRIBUTE_CLASS);
			} catch (final CoreException e) {
				AcceleoEnginePlugin.log(e, false);
			}
		}
		return engineCreator;
	}

	/**
	 * Returns the label of this engine.
	 * 
	 * @return The label of this engine.
	 */
	public String getLabel() {
		return label;
	}
}
