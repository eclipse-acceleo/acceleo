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
package org.eclipse.acceleo.internal.traceability;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.engine.generation.IAcceleoEngine;
import org.eclipse.acceleo.engine.generation.IAcceleoEngineCreator;
import org.eclipse.acceleo.internal.traceability.engine.AcceleoTraceabilityEngine;

/**
 * This will be used to create traceability-enabled engines instead of the regular ones.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TraceabilityEngineCreator implements IAcceleoEngineCreator {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngineCreator#canBeCreated()
	 */
	public boolean canBeCreated() {
		return AcceleoPreferences.isTraceabilityEnabled();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngineCreator#createEngine()
	 */
	public IAcceleoEngine createEngine() {
		return new AcceleoTraceabilityEngine();
	}

}
