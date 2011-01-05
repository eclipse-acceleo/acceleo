/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.utils;

import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.profiler.Profiler;

/**
 * This class will allow the manipulation of the profiler.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public final class AcceleoEngineUtils {

	/**
	 * The constructor.
	 */
	private AcceleoEngineUtils() {
		// prevent instantiation
	}

	/**
	 * Sets the profiler for the generation. The profiler will only be set if the profiling is activated as it
	 * is indicated by {@link org.eclipse.acceleo.common.preference.AcceleoPreferences#isProfilerEnabled()}.
	 * 
	 * @param profiler
	 *            The profiler.
	 */
	public static void setProfiler(Profiler profiler) {
		AcceleoEvaluationVisitor.setProfile(profiler);
	}
}
