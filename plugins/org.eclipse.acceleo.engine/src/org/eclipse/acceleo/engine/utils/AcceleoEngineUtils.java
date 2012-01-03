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
package org.eclipse.acceleo.engine.utils;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.profiler.Profiler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This class will allow the manipulation of the profiler.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.0
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

	/**
	 * Utility method returning the absolute path of all the ".properties" files in the same folder as the
	 * input model.
	 * 
	 * @param eResource
	 *            The resource containing the model.
	 * @return The absolute path of all the ".properties" files in the same folder as the input model.
	 * @since 3.1
	 */
	public static Set<String> getPropertiesFilesNearModel(Resource eResource) {
		Set<String> propertiesFilesNearModel = new LinkedHashSet<String>();

		String file = eResource.getURI().toFileString();
		if (file == null) {
			file = eResource.getURI().toString();
		}
		if (file.startsWith("platform:/resource/")) { //$NON-NLS-1$
			IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
					new Path(file.substring("platform:/resource/".length()))); //$NON-NLS-1$
			file = iFile.getLocation().toFile().getAbsolutePath();
		}

		File modelDirectory = new File(file).getParentFile();

		for (File propertiesFile : modelDirectory.listFiles()) {
			if (propertiesFile.isFile() && propertiesFile.getName().endsWith(".properties")) //$NON-NLS-1$
			{
				String propertiesFilePath = propertiesFile.getAbsolutePath();
				propertiesFilesNearModel.add(propertiesFilePath);
			}
		}

		return propertiesFilesNearModel;
	}
}
