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
package org.eclipse.acceleo.maven;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoParser;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoProjectClasspathEntry;
import org.eclipse.acceleo.internal.parser.compiler.IAcceleoParserURIHandler;
import org.eclipse.acceleo.internal.parser.compiler.IParserListener;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserWarning;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * The Acceleo Parser MOJO is used to call the Acceleo Parser from Maven.
 * 
 * @goal acceleo-compile
 * @phase compile
 * @requiresDependencyResolution compile+runtime
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoParserMojo extends AbstractMojo {

	/**
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * Indicates if we are compiling the Acceleo modules as binary resources.
	 * 
	 * @parameter expression = "${acceleo-compile.binaryResource}"
	 * @required
	 */
	private boolean useBinaryResources;

	/**
	 * The class to use for the uri converter.
	 * 
	 * @parameter expression = "${acceleo-compile.uriHandler}"
	 */
	private String uriHandler;

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
	 * Indicates if we are compiling the Acceleo modules as binary resources.
	 * 
	 * @parameter expression = "${acceleo-compile.usePlatformResourcePath}"
	 * @required
	 */
	private boolean usePlatformResourcePath;

	/**
	 * Indicates if we should fail on errors.
	 * 
	 * @parameter expression = "${acceleo-compile.failOnError}"
	 */
	private boolean failOnError = true;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = getLog();
		log.info("Acceleo maven stand alone build...");

		log.info("Starting packages registration...");

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

		for (String packageToRegister : this.packagesToRegister) {
			try {
				if (newLoader != null) {
					Class<?> forName = Class.forName(packageToRegister, true, newLoader);
					Field nsUri = forName.getField("eNS_URI");
					Field eInstance = forName.getField("eINSTANCE");

					Object nsURIInvoked = nsUri.get(null);
					if (nsURIInvoked instanceof String) {
						log.info("Registering package '" + packageToRegister + "'.");
						AcceleoPackageRegistry.INSTANCE.put((String)nsURIInvoked, eInstance.get(null));
					} else {
						log.error("The URI field is not a string.");
					}
				}
			} catch (ClassNotFoundException e) {
				log.error(e);
			} catch (IllegalAccessException e) {
				log.error(e);
			} catch (SecurityException e) {
				log.error(e);
			} catch (NoSuchFieldException e) {
				log.error(e);
			}

		}

		log.info("Starting the build sequence for the project '" + this.acceleoProject.getRoot() + "'...");
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

			log.debug("Input: " + inputDirectory.getAbsolutePath());
			log.debug("Output: " + outputDirectory.getAbsolutePath());

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
		List<String> jars = this.acceleoProject.getJars();
		if (jars != null) {
			Set<URI> newDependencies = new LinkedHashSet<URI>();
			for (String jar : jars) {
				log.info("Resolving jar: '" + jar + "'...");

				boolean jarFound = false;

				File jarFile = new File(jar);
				if (jarFile.isFile()) {
					URI uri = URI.createFileURI(jar);
					newDependencies.add(uri);

					jarFound = true;
					log.info("Found jar for '" + jar + "' on the filesystem: '" + jarFile.getAbsolutePath()
							+ "'.");
				} else {
					StringTokenizer tok = new StringTokenizer(jar, ":");

					String groupdId = null;
					String artifactId = null;
					String version = null;

					int c = 0;
					while (tok.hasMoreTokens()) {
						String nextToken = tok.nextToken();
						if (c == 0) {
							groupdId = nextToken;
						} else if (c == 1) {
							artifactId = nextToken;
						} else if (c == 2) {
							version = nextToken;
						}

						c++;
					}

					List<URI> urisFromArtifacts = this.getURIsFromArtifacts(groupdId, artifactId, version,
							jar);
					if (urisFromArtifacts.size() > 0) {
						jarFound = true;
					}
					newDependencies.addAll(urisFromArtifacts);

					List<URI> urisFromDependencies = this.getURIsFromDependencies(groupdId, artifactId,
							version, jar);
					if (urisFromDependencies.size() > 0) {
						jarFound = true;
					}
					newDependencies.addAll(urisFromDependencies);
				}
				if (!jarFound) {
					throw new MojoExecutionException("The jar " + jar
							+ " has not been found on the dependencies available.");
				}
			}
			aProject.addDependencies(newDependencies);
		}

		log.info("Starting parsing...");
		AcceleoParser parser = new AcceleoParser(aProject, this.useBinaryResources,
				this.usePlatformResourcePath, false);
		AcceleoParserListener listener = new AcceleoParserListener();
		parser.addListeners(listener);

		// Load and plug the uri resolver
		if (this.uriHandler != null && newLoader != null) {
			try {
				Class<?> forName = Class.forName(this.uriHandler, true, newLoader);
				Object newInstance = forName.newInstance();
				if (newInstance instanceof IAcceleoParserURIHandler) {
					IAcceleoParserURIHandler resolver = (IAcceleoParserURIHandler)newInstance;
					parser.setURIHandler(resolver);
				}
			} catch (ClassNotFoundException e) {
				log.error(e);
			} catch (InstantiationException e) {
				log.error(e);
			} catch (IllegalAccessException e) {
				log.error(e);
			}
		}

		Set<File> builtFiles = parser.buildAll(new BasicMonitor());

		boolean errorFound = false;
		for (File builtFile : builtFiles) {
			Collection<AcceleoParserProblem> problems = parser.getProblems(builtFile);
			Collection<AcceleoParserWarning> warnings = parser.getWarnings(builtFile);

			if (problems.size() > 0) {
				log.info("Errors for file '" + builtFile.getName() + "': " + problems);
				errorFound = true;
			}
			if (warnings.size() > 0) {
				log.info("Warnings for file '" + builtFile.getName() + "': " + warnings);
			}
		}

		if (errorFound && failOnError) {
			throw new MojoExecutionException("Errors have been found during the build of the generator");
		}

		// Removing everything
		AcceleoPackageRegistry.INSTANCE.clear();
		log.info("Build completed.");
	}

	/**
	 * Return the URIs of the jars that have the given groupId, artifactId, and version in the maven artifact
	 * of this project.
	 * 
	 * @param groupId
	 *            The groupId
	 * @param artifactId
	 *            The artifactIf
	 * @param version
	 *            The version
	 * @param jar
	 *            The name of the jar
	 * @return The URIs of the jars found
	 */
	private List<URI> getURIsFromArtifacts(String groupId, String artifactId, String version, String jar) {
		List<URI> dependencies = new ArrayList<URI>();

		Set<?> artifacts = this.project.getArtifacts();
		for (Object object : artifacts) {
			if (object instanceof Artifact) {
				Artifact artifact = (Artifact)object;
				if (groupId != null && groupId.equals(artifact.getGroupId()) && artifactId != null
						&& artifactId.equals(artifact.getArtifactId())) {
					if (version != null && version.equals(artifact.getVersion())) {
						File artifactFile = artifact.getFile();
						if (artifactFile != null && artifactFile.exists()) {
							URI uri = URI.createFileURI(artifactFile.getAbsolutePath());
							dependencies.add(uri);
							this.getLog().info(
									"Found jar for '" + jar + "' on the filesystem: '" + uri.toString()
											+ "'.");
						}
					} else if (version == null) {
						File artifactFile = artifact.getFile();
						if (artifactFile != null && artifactFile.exists()) {
							URI uri = URI.createFileURI(artifactFile.getAbsolutePath());
							dependencies.add(uri);
							this.getLog().info(
									"Found jar for '" + jar + "' on the filesystem: '" + uri.toString()
											+ "'.");
						}
					}
				}
			}
		}

		return dependencies;
	}

	/**
	 * Return the URIs of the jars that have the given groupId, artifactId, and version in the maven
	 * dependencies of this project.
	 * 
	 * @param groupId
	 *            The groupId
	 * @param artifactId
	 *            The artifactIf
	 * @param version
	 *            The version
	 * @param jar
	 *            The name of the jar
	 * @return The URIs of the jars found
	 */
	private List<URI> getURIsFromDependencies(String groupId, String artifactId, String version, String jar) {
		List<URI> dependencies = new ArrayList<URI>();

		List<?> mavenDependencies = this.project.getDependencies();
		for (Object object : mavenDependencies) {
			if (object instanceof Dependency) {
				Dependency dependency = (Dependency)object;
				if (groupId != null && groupId.equals(dependency.getGroupId()) && artifactId != null
						&& artifactId.equals(dependency.getArtifactId())) {
					if (version != null && version.equals(dependency.getVersion())) {
						String systemPath = dependency.getSystemPath();
						if (systemPath != null && new File(systemPath).exists()) {
							URI uri = URI.createFileURI(systemPath);
							dependencies.add(uri);
							this.getLog().info(
									"Found jar for '" + jar + "' on the filesystem: '" + uri.toString()
											+ "'.");
						}
					} else if (version == null) {
						String systemPath = dependency.getSystemPath();
						if (systemPath != null && new File(systemPath).exists()) {
							URI uri = URI.createFileURI(systemPath);
							dependencies.add(uri);
							this.getLog().info(
									"Found jar for '" + jar + "' on the filesystem: '" + uri.toString()
											+ "'.");
						}
					}
				}
			}
		}
		return dependencies;
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
			getLog().info("Saving ouput file for '" + arg0.getAbsolutePath() + "'.");
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
