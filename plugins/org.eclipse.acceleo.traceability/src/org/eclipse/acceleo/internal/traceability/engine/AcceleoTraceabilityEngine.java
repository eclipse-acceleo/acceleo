/*******************************************************************************
 * Copyright (c) 2009 Obeo.
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
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
	/**
	 * Name that will be used for the generated traceability model. Note that this will be created within the
	 * generation root directory.
	 */
	private static final String TRACEABILITY_MODEL_NAME = "generation";

	/** Extension of the generated traceability model. */
	private static final String TRACEABILITY_MODEL_EXTENSION = "trace";

	/** All traceability information for this session will be saved in this instance. */
	private TraceabilityModel evaluationTrace = TraceabilityFactory.eINSTANCE.createTraceabilityModel();

	/** holds a reference to the current generation root. */
	private File generationRoot;

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
		generationRoot = genRoot;
		final List<IAcceleoTextGenerationListener> listenersCopy = new ArrayList<IAcceleoTextGenerationListener>(
				listeners);
		final List<Properties> propertiesCopy = new ArrayList<Properties>(loadedProperties.values());
		propertiesCopy.add(0, customProperties);
		return new AcceleoTraceabilityEnvironmentFactory(genRoot, rootModule, listenersCopy, propertiesCopy,
				strategy, monitor, evaluationTrace);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.AcceleoEngine#hookGenerationEnd(org.eclipse.acceleo.engine.generation.AbstractAcceleoEnvironmentFactory)
	 */
	@Override
	protected void hookGenerationEnd(AbstractAcceleoEnvironmentFactory factory) {
		super.hookGenerationEnd(factory);
		if (generationRoot != null) {
			int number = 0;
			final String traceabilityModelPath = computeTraceabilityModelPath(generationRoot, number);
			File traceabilityFile = new File(traceabilityModelPath);
			while (traceabilityFile.exists()) {
				traceabilityFile = new File(computeTraceabilityModelPath(generationRoot, ++number));
			}
			try {
				ModelUtils.save(evaluationTrace, traceabilityModelPath);
				evaluationTrace.eResource().unload();
			} catch (IOException e) {
				AcceleoEnginePlugin.log(e, false);
			}
		}
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.AcceleoEngine#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		generationRoot = null;
		evaluationTrace = null;
	}

	/**
	 * Returns an absolute path name according to the given parameters. The generated path will look like
	 * 
	 * <em>&lt;root path&gt;&lt;file separator char&gt;&lt;TRACEABILITY_MODEL_NAME&gt;&lt;number&gt;.&lt;TRACEABILITY_MODEL_EXTENSION&gt;</em>
	 * 
	 * @param root
	 *            Current generation root.
	 * @param number
	 *            Number that is to be appended at the end of the model name, in case a traceability model
	 *            already exists.
	 * @return The computed path.
	 */
	private static String computeTraceabilityModelPath(File root, int number) {
		final StringBuilder buffer = new StringBuilder(root.getAbsolutePath());
		buffer.append(File.separatorChar);
		buffer.append(TRACEABILITY_MODEL_NAME);
		if (number > 0) {
			buffer.append(number);
		}
		buffer.append('.');
		buffer.append(TRACEABILITY_MODEL_EXTENSION);
		return buffer.toString();
	}
}
