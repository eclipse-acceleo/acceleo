/*******************************************************************************
 * Copyright (c) 2008, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.maven.launcher.compatibility;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

/**
 * The Acceleo Generator MOJO is used to call an Acceleo Generator from Maven.
 * 
 * @goal acceleo-launcher
 * @phase process-resources
 * @requiresDependencyResolution compile+runtime
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 3.5
 */
public class AcceleoLauncherMojo extends AbstractMojo {

	/**
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * The class to use for the uri converter.
	 * 
	 * @parameter expression = "${acceleo-launcher.generatorClass}"
	 */
	private String generatorClass;

	/**
	 * The class to use for the uri converter.
	 * 
	 * @parameter expression = "${acceleo-launcher.model}"
	 */
	private String model;

	/**
	 * The class to use for the uri converter.
	 * 
	 * @parameter expression = "${acceleo-launcher.outputFolder}"
	 */
	private String outputFolder;

	/**
	 * The list of parameters.
	 * 
	 * @parameter expression = "${acceleo-launcher.parameters}"
	 * @required
	 */
	private List<String> parameters;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = getLog();
		log.info("Acceleo maven stand alone generation...");

		log.info("Starting generator class loading...");

		URLClassLoader newLoader = null;
		try {
			List<?> runtimeClasspathElements = project.getRuntimeClasspathElements();
			List<?> compileClasspathElements = project.getCompileClasspathElements();
			URL[] runtimeUrls = new URL[runtimeClasspathElements.size() + compileClasspathElements.size()];
			int i = 0;
			for (Object object : runtimeClasspathElements) {
				if (object instanceof String) {
					String str = (String)object;
					log.debug("Adding the runtime dependency " + str
							+ " to the classloader for the package resolution");
					runtimeUrls[i] = new File(str).toURI().toURL();
					i++;
				} else {
					log.debug("Runtime classpath entry is not a string: " + object);
				}
			}
			for (Object object : compileClasspathElements) {
				if (object instanceof String) {
					String str = (String)object;
					log.debug("Adding the compilation dependency " + str
							+ " to the classloader for the package resolution");
					runtimeUrls[i] = new File(str).toURI().toURL();
					i++;
				} else {
					log.debug("Runtime classpath entry is not a string: " + object);
				}
			}
			newLoader = new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());
		} catch (DependencyResolutionRequiredException e) {
			log.error(e);
		} catch (MalformedURLException e) {
			log.error(e);
		}

		try {
			if (newLoader != null) {
				final Class<?> generatorClazz = Class.forName(generatorClass, true, newLoader);

				log.info("Starting the generation sequence for the generator '" + generatorClass + "'...");
				final Method mainMethod = generatorClazz.getMethod("main", String[].class);

				List<String> arguments = new ArrayList<String>(parameters.size() + 2);
				log.info("Model: '" + model + "'");
				arguments.add(model);
				log.info("Output folder: '" + outputFolder + "'");
				arguments.add(outputFolder);
				for (String parameter : parameters) {
					log.info("Parameter: '" + parameter + "'");
					arguments.add(parameter);
				}

				log.info("Invoking generator.");
				mainMethod.invoke(null, (Object)arguments.toArray(new String[arguments.size()]));

				log.info("Generation completed.");
			}
		} catch (ClassNotFoundException e) {
			log.error(e);
		} catch (SecurityException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (IllegalArgumentException e) {
			log.error(e);
		} catch (InvocationTargetException e) {
			log.error(e);
		}

	}

}
