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
package org.eclipse.acceleo.internal.traceability.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.AbstractAcceleoEnvironmentFactory;
import org.eclipse.acceleo.engine.generation.AcceleoEngine;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.internal.traceability.environment.AcceleoTraceabilityEnvironmentFactory;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.traceability.TraceabilityFactory;
import org.eclipse.acceleo.traceability.TraceabilityModel;
import org.eclipse.emf.common.util.Monitor;

/**
 * This implementation of an {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine} will be used to
 * create an environment factory that instantiates traceability-enabled evaluation visitors.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoTraceabilityEngine extends AcceleoEngine {
	/** All traceability information for this session will be saved in this instance. */
	private TraceabilityModel evaluationTrace = TraceabilityFactory.eINSTANCE.createTraceabilityModel();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.AcceleoEngine#createEnvironmentFactory(java.io.File,
	 *      org.eclipse.acceleo.model.mtl.Module,
	 *      org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy,
	 *      org.eclipse.emf.common.util.Monitor)
	 */
	@Override
	protected AbstractAcceleoEnvironmentFactory createEnvironmentFactory(File genRoot, Module rootModule,
			IAcceleoGenerationStrategy strategy, Monitor monitor) {
		final List<IAcceleoTextGenerationListener> listenersCopy = new ArrayList<IAcceleoTextGenerationListener>(
				listeners);
		return new AcceleoTraceabilityEnvironmentFactory(genRoot, rootModule, listenersCopy,
				propertiesLookup, strategy, monitor, evaluationTrace);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.AcceleoEngine#fireGenerationEnd()
	 */
	@Override
	protected void fireGenerationEnd() {
		if (!notifyOnGenerationEnd) {
			return;
		}
		for (IAcceleoTextGenerationListener listener : listeners) {
			if (listener.listensToGenerationEnd()) {
				listener.generationEnd(new AcceleoTextGenerationEvent(null, null, null, evaluationTrace));
			}
		}
	}
}
