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
package org.eclipse.acceleo.maven;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoParser;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoProjectClasspathEntry;
import org.eclipse.acceleo.internal.parser.compiler.IParserListener;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserWarning;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;

/**
 * The Acceleo Parser MOJO is used to call the Acceleo Parser from Maven.
 * 
 * @goal acceleo-compile
 * @phase compile
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoParserMojo extends AbstractMojo {

	/**
	 * Indicates if we are compiling the Acceleo modules as binary resources.
	 * 
	 * @parameter expression = "${acceleo-compile.binaryResource}"
	 * @required
	 */
	private boolean useBinaryResources;

	/**
	 * The list of packages to register.
	 * 
	 * @parameter expression = "${acceleo-compile.packagesToRegister}"
	 * @required
	 */
	private List<String> packagesToRegister;

	/**
	 * The Acceleo project that should be built.
	 * 
	 * @parameter expression = "${acceleo-compile.acceleoProject}"
	 * @required
	 */
	private AcceleoProject acceleoProject;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = getLog();
		log.info("ACCELEO MAVEN STAND ALONE BUILD");

		log.info("Registering packages...");
		for (String packageToRegister : this.packagesToRegister) {
			try {
				Class<?> forName = Class.forName(packageToRegister);
				Field nsUri = forName.getField("eNS_URI");
				Field eInstance = forName.getField("eINSTANCE");

				Object newInstance = forName.newInstance();
				Object nsURIInvoked = nsUri.get(newInstance);
				if (nsURIInvoked instanceof String) {
					log.info("Registering package '" + packageToRegister + "'.");
					AcceleoPackageRegistry.INSTANCE.put((String)nsURIInvoked, eInstance.get(newInstance));
				} else {
					log.error("The URI field is not a string.");
				}

			} catch (ClassNotFoundException e) {
				log.error(e);
			} catch (InstantiationException e) {
				log.error(e);
			} catch (IllegalAccessException e) {
				log.error(e);
			} catch (SecurityException e) {
				log.error(e);
			} catch (NoSuchFieldException e) {
				log.error(e);
			}

		}

		log.info("Starting the build sequence...");
		log.info("Mapping the pom.xml to AcceleoProject...");

		Preconditions.checkNotNull(this.acceleoProject);
		Preconditions.checkNotNull(this.acceleoProject.getRoot());
		Preconditions.checkNotNull(this.acceleoProject.getEntries());
		Preconditions.checkState(this.acceleoProject.getEntries().size() >= 1);

		File root = this.acceleoProject.getRoot();

		org.eclipse.acceleo.internal.parser.compiler.AcceleoProject aProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
				root);
		List<Entry> entries = this.acceleoProject.getEntries();
		Set<AcceleoProjectClasspathEntry> classpathEntries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		for (Entry entry : entries) {
			File inputDirectory = new File(root, entry.getInput());
			File outputDirectory = new File(root, entry.getOutput());
			AcceleoProjectClasspathEntry classpathEntry = new AcceleoProjectClasspathEntry(inputDirectory,
					outputDirectory);
			classpathEntries.add(classpathEntry);
		}
		aProject.addClasspathEntries(classpathEntries);

		List<AcceleoProject> dependencies = this.acceleoProject.getDependencies();
		if (dependencies != null) {
			for (AcceleoProject dependingAcceleoProject : dependencies) {
				File dependingProjectRoot = dependingAcceleoProject.getRoot();
				Preconditions.checkNotNull(dependingProjectRoot);

				org.eclipse.acceleo.internal.parser.compiler.AcceleoProject aDependingProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
						dependingProjectRoot);

				List<Entry> dependingProjectEntries = dependingAcceleoProject.getEntries();
				Set<AcceleoProjectClasspathEntry> dependingClasspathEntries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
				for (Entry entry : dependingProjectEntries) {
					File inputDirectory = new File(root, entry.getInput());
					File outputDirectory = new File(root, entry.getOutput());
					AcceleoProjectClasspathEntry classpathEntry = new AcceleoProjectClasspathEntry(
							inputDirectory, outputDirectory);
					dependingClasspathEntries.add(classpathEntry);
				}

				aDependingProject.addClasspathEntries(dependingClasspathEntries);
				aProject.addProjectDependencies(Sets.newHashSet(aDependingProject));
			}
		}

		log.info("Adding jar dependencies...");
		List<File> jars = this.acceleoProject.getJars();
		if (jars != null) {
			Set<URI> newDependencies = new LinkedHashSet<URI>();
			for (File jar : jars) {
				URI uri = URI.createFileURI(jar.getAbsolutePath());
				newDependencies.add(uri);
			}
			aProject.addDependencies(newDependencies);
		}

		log.info("Starting parsing...");
		AcceleoParser parser = new AcceleoParser(aProject, this.useBinaryResources);
		AcceleoParserListener listener = new AcceleoParserListener();
		parser.addListeners(listener);

		Set<File> builtFiles = parser.buildAll(new BasicMonitor());

		for (File builtFile : builtFiles) {
			Collection<AcceleoParserProblem> problems = parser.getProblems(builtFile);
			Collection<AcceleoParserWarning> warnings = parser.getWarnings(builtFile);
			if (problems.size() > 0) {
				log.info("Errors for file '" + builtFile.getName() + "': " + problems);
			}
			if (warnings.size() > 0) {
				log.info("Warnings for file '" + builtFile.getName() + "': " + warnings);
			}
		}

		log.info("Build completed.");
	}

	/**
	 * The listener used to log message for maven.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 * @since 3.2
	 */
	private class AcceleoParserListener implements IParserListener {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.parser.compiler.IParserListener#endBuild(java.io.File)
		 */
		public void endBuild(File arg0) {
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.parser.compiler.IParserListener#fileSaved(java.io.File)
		 */
		public void fileSaved(File arg0) {
			getLog().info("Saving file '" + arg0.getAbsolutePath() + "'.");
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.parser.compiler.IParserListener#loadDependency(java.io.File)
		 */
		public void loadDependency(File arg0) {
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.parser.compiler.IParserListener#loadDependency(org.eclipse.emf.common.util.URI)
		 */
		public void loadDependency(URI arg0) {
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.parser.compiler.IParserListener#startBuild(java.io.File)
		 */
		public void startBuild(File arg0) {
		}
	}
}
