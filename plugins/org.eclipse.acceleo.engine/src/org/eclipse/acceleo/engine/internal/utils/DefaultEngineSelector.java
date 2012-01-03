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

import java.util.List;

import org.eclipse.acceleo.engine.generation.IAcceleoEngine;
import org.eclipse.acceleo.engine.generation.IAcceleoEngineCreator;

/**
 * Selects the first engine that can be created from the registry.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class DefaultEngineSelector {
	/**
	 * Selects the first engine that can be created from the given descriptors list.
	 * 
	 * @param descriptors
	 *            Descriptors from which to select a createable engine.
	 * @return The first engine that can be created from the given descriptors list.
	 */
	public IAcceleoEngine selectEngine(List<AcceleoEngineCreatorDescriptor> descriptors) {
		for (AcceleoEngineCreatorDescriptor descriptor : descriptors) {
			IAcceleoEngineCreator creator = descriptor.getEngineCreator();
			if (creator.canBeCreated()) {
				return creator.createEngine();
			}
		}
		return null;
	}
}
