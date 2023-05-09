/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.acceleo.aql.profiler;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * Profiler interface.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IProfiler {

	/**
	 * Reset the statistics of the profiler.
	 */
	void reset();

	/**
	 * Start monitoring the given object.
	 * 
	 * @param <L>
	 *            the type of child {@link ProfileEntry}
	 * @param monitored
	 *            the {@link EObject} to monitor
	 * @return the created {@link ProfileEntry}
	 */
	<L extends ProfileEntry> L start(EObject monitored);

	/**
	 * Stop monitoring the current object.
	 * 
	 * @param <L>
	 *            the type of child {@link ProfileEntry}
	 * @return the current {@link ProfileEntry}
	 */
	<L extends ProfileEntry> L stop();

	/**
	 * Gets the {@link ProfileResource} builded by the profiler if any, <code>null</code> if called just after
	 * initialization or {@link IProfiler#reset()}.
	 * 
	 * @return the {@link ProfileResource} builded by the profiler
	 */
	ProfileResource getResource();

	/**
	 * Save profiling results to the given {@link URI}.
	 * 
	 * @param modelURI
	 *            the {@link URI} where to save results.
	 * @throws IOException
	 *             if model serialization fail
	 */
	void save(URI modelURI) throws IOException;

}
