/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.equinox.internal;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

/**
 * Class responsible for launching an Acceleo/MTL generator.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public class AcceleoGeneratorRunner {
	/**
	 * The root output folder. Might be null.
	 */
	private File output;

	/**
	 * Generators parameters.
	 */
	private String[] generatorParameters = new String[0];

	/**
	 * Create a new runner.
	 * 
	 * @param output
	 *            the root output folder if specified, null otherwise.
	 * @param generatorParameters
	 *            the generators parameter.
	 */
	public AcceleoGeneratorRunner(File output, String[] generatorParameters) {
		this.output = output;
		this.generatorParameters = generatorParameters;
	}

	/**
	 * Launch an Acceleo Generator.
	 * 
	 * @param modelURIS
	 *            the models to use as an input of the generator.
	 * @param monitor
	 *            tracking progress.
	 * @param generatorsFullName
	 *            string capturing the information to retrieve the generator in the form of
	 *            pluginId/some.qualified.ClassName
	 */
	public void launchAcceleoGenerator(final Monitor monitor, List<URI> modelURIS,
			String generatorsFullName) {
		int pluginClassSep = generatorsFullName.indexOf('/');
		if (pluginClassSep != -1 && pluginClassSep < generatorsFullName.length()) {
			String plugin = generatorsFullName.substring(0, pluginClassSep);
			String qualifiedClassName = generatorsFullName.substring(pluginClassSep + 1);

			Bundle bnd = Platform.getBundle(plugin);
			if (bnd != null) {
				try {
					Class<?> generatorClass = bnd.loadClass(qualifiedClassName);
					try {
						final AbstractAcceleoGenerator generatorInstance = (AbstractAcceleoGenerator)generatorClass
								.newInstance();
						for (URI modelURI : modelURIS) {
							try {
								generatorInstance.initialize(modelURI, output, buildParametersList());
								generatorInstance.doGenerate(monitor);
							} catch (IOException e) {
								error("Error launching the generator : " + qualifiedClassName, e);
							}
						}
					} catch (ClassCastException e) {
						error("Class " + generatorClass
								+ " has to be a subclass of AbstractAcceleoGenerator.", e);
					} catch (InstantiationException e) {
						error("Could not instantiate " + generatorClass, e);
					} catch (IllegalAccessException e) {
						AcceleoEquinoxLauncherPlugin.INSTANCE.log(e);
					}
				} catch (ClassNotFoundException e) {
					error("Could not find the generator class : " + qualifiedClassName + " in the plugin :"
							+ bnd.getSymbolicName(), e);

				}
			} else {
				error("Could not find the plugin: " + plugin, null);
			}
		} else {
			error("unexpected format for a generator : " + generatorsFullName
					+ " pluginID/qualifiedClassName is expected", null);
		}
	}

	/**
	 * Log a message as an error.
	 * 
	 * @param message
	 *            the error message.
	 * @param e
	 *            optional exceptions related to the error.
	 */
	private void error(String message, Throwable e) {
		if (e != null) {
			AcceleoEquinoxLauncherPlugin.INSTANCE.log(new Status(IStatus.ERROR,
					AcceleoEquinoxLauncherPlugin.INSTANCE.getSymbolicName(), message, e));
		} else {
			AcceleoEquinoxLauncherPlugin.INSTANCE.log(new Status(IStatus.ERROR,
					AcceleoEquinoxLauncherPlugin.INSTANCE.getSymbolicName(), message));
		}
		throw new RuntimeException(message, e);
	}

	/**
	 * return a list containing the parameters to give to the generator.
	 * 
	 * @return a list containing the parameters to give to the generator.
	 */
	private List<String> buildParametersList() {
		return Arrays.asList(this.generatorParameters);
	}

}
