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
package org.eclipse.acceleo.equinox;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.equinox.internal.AcceleoEquinoxLauncherPlugin;
import org.eclipse.acceleo.equinox.internal.AcceleoGeneratorRunner;
import org.eclipse.acceleo.equinox.internal.EMFCodeGenRunner;
import org.eclipse.acceleo.equinox.internal.LaunchConfigurationRunner;
import org.eclipse.acceleo.equinox.internal.WorkspaceHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

/**
 * Application class for the Equinox Acceleo Launcher. Parses the arguments and launch user specified code
 * generators..
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public class AcceleoLauncher implements IApplication {
	/**
	 * The prefix used for a generate action.
	 */
	private static final String GENERATE_PREFIX = "generate:";

	/**
	 * The prefix used for starting a launch configuration.
	 */
	private static final String LAUNCH_PREFIX = "launch:";

	/**
	 * Default timeout when waiting for jobs to finish.
	 */
	private static final int DEFAULT_JOBS_TIMEOUT = 200;

	/**
	 * Return code for the application in case of error.
	 */
	private static final Integer APPLICATION_ERROR = Integer.valueOf(-1);

	/**
	 * The EMF Ecore/Genmodel generator.
	 */
	private static final String EMF_CODEGEN = "org.eclipse.emf.codegen.ecore/org.eclipse.emf.codegen.ecore.generator.Generator"; //$NON-NLS-1$

	/**
	 * The input models.
	 */
	@Option(name = "-models", usage = "Specify the model to use as inputs of the generation. Relative paths might be used or absolute uris to target the host environment like platform:/resource/someProject/model/someModel.ecore for instance.", metaVar = "INPUT", handler = StringArrayOptionHandler.class)
	private String[] models = new String[0];

	/**
	 * The list of generators to launch.
	 */
	@Option(name = "-actions", usage = "Specify the actions to execute, for instance generate:org.generator.plugin.id/org.generator.plugin.class.name to launch a code generation.", handler = StringArrayOptionHandler.class)
	private String[] actions = new String[0];

	/**
	 * The list of paths to search for project to be imported in the workspace.
	 */
	@Option(name = "-projects-paths", usage = "Specify paths to search for project and import those in the workspace.", metaVar = "INPUT", handler = StringArrayOptionHandler.class)
	private String[] projectSearchPaths = {System.getProperty("user.dir") };

	/**
	 * Generators parameters.
	 */
	@Option(name = "-parameters", usage = "Parameters to pass to the generators.", handler = StringArrayOptionHandler.class)
	private String[] generatorParameters = new String[0];

	/**
	 * The root ouput folder.
	 */
	@Option(name = "-output", usage = "Specifiy the root folder to generate to, default is the application working folder.", metaVar = "OUTPUT")
	private File output = new File(System.getProperty("user.dir"));

	/**
	 * The job waits timeout.
	 */
	@Option(name = "-jobs-wait-timeout", usage = "Specify the duration before timeout when waiting for eclipse Jobs completion.")
	private int jobsWaitTimeout = DEFAULT_JOBS_TIMEOUT;

	/**
	 * Workspace location. This argument is here only to mimic the OSGi applications common arguments so that
	 * they are displayed in usage.
	 */
	@Option(name = "-data", usage = "Specify the folder which will keep the workspace.", metaVar = "FOLDER")
	private File dataFolder;

	/**
	 * consoleLog. This argument is here only to mimic the OSGi applications common arguments so that they are
	 * displayed in usage.
	 */
	@Option(name = "-consoleLog", usage = "Log messages in the console.")
	private boolean consoleLog;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = (String[])context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		info("Workspace location is : " + Platform.getInstanceLocation().getURL()
				+ " use -data to change it.");
		return new AcceleoLauncher().doMain(args);
	}

	/**
	 * Launcher logic.
	 * 
	 * @param args
	 *            application parameters passed by the Equinox framework.
	 * @return the return value of the application
	 */
	public Object doMain(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);

		boolean somethingWentWrong = false;
		try {
			parser.parseArgument(args);

			validateArguments();

			final Monitor monitor = new BasicMonitor.Printing(System.out);
			AcceleoPreferences.switchForceDeactivationNotifications(true);
			WorkspaceHandler wks = new WorkspaceHandler();

			if (projectSearchPaths.length > 0) {
				wks.clearWorkspace(monitor);
				for (String rootFolder : projectSearchPaths) {
					wks.importProjectsInWorkspace(rootFolder, monitor);
				}
			}

			ResourceSet set = new ResourceSetImpl();
			List<URI> modelURIS = new ArrayList<URI>();
			for (String modelPath : models) {
				URI rawURI = null;
				try {
					rawURI = URI.createURI(modelPath, true);
				} catch (IllegalArgumentException e) {
					/*
					 * the passed uri is not in the URI format and should be considered as a direct file
					 * denotation.
					 */
				}

				if (rawURI != null && !rawURI.hasAbsolutePath()) {
					rawURI = URI.createFileURI(modelPath);
				}
				for (IProject prj : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {

					try {
						prj.open(BasicMonitor.toIProgressMonitor(monitor));
						prj.refreshLocal(IResource.DEPTH_INFINITE, BasicMonitor.toIProgressMonitor(monitor));
					} catch (CoreException e) {
						AcceleoEquinoxLauncherPlugin.INSTANCE.log(e);
						/*
						 * we don't set "somethingWentWrong" in case of error in this phase as the launch
						 * might not fail even if one of the projects in the workspace is in a stale state.
						 */
					}

				}

				int secondsWaiting = 0;
				while (!Job.getJobManager().isIdle() && secondsWaiting <= jobsWaitTimeout) {
					try {
						Job currentJob = Job.getJobManager().currentJob();
						String jobName = "unknown";
						ISchedulingRule currentrule = Job.getJobManager().currentRule();
						if (currentrule != null) {
							jobName = currentrule.toString();
						}
						if (currentJob != null) {
							jobName = currentJob.getName();
							if (jobName == null) {
								jobName = currentJob.getClass().getCanonicalName();
							}
						}
						info("Waiting for Job termination : " + jobName);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						AcceleoEquinoxLauncherPlugin.INSTANCE.log(e);
						/*
						 * we don't set "somethingWentWrong" in case of error in this phase as it's we are
						 * just trying, best-effort style, to wait for the workspace to be ready.
						 */
					}
					secondsWaiting++;
				}
				if (set.getURIConverter().exists(rawURI, Collections.EMPTY_MAP)) {
					modelURIS.add(rawURI);
				} else {
					somethingWentWrong = true;
					if (rawURI != null) {
						AcceleoEquinoxLauncherPlugin.INSTANCE.log(new Status(IStatus.ERROR,
								AcceleoEquinoxLauncherPlugin.INSTANCE.getSymbolicName(), "File " + modelPath
										+ " does not exist or is not accessible through the URI:" + rawURI
												.toString()));
					} else {
						AcceleoEquinoxLauncherPlugin.INSTANCE.log(new Status(IStatus.ERROR,
								AcceleoEquinoxLauncherPlugin.INSTANCE.getSymbolicName(), "File " + modelPath
										+ " does not exist."));
					}
				}

			}

			for (String action : actions) {
				if (action.startsWith(GENERATE_PREFIX)) {
					String generatorsFullName = action.substring(GENERATE_PREFIX.length());
					if (EMF_CODEGEN.equals(generatorsFullName)) {
						EMFCodeGenRunner eGen = new EMFCodeGenRunner();
						try {
							eGen.generateAll(modelURIS, monitor);
						} catch (CoreException e) {
							somethingWentWrong = true;
							AcceleoEquinoxLauncherPlugin.INSTANCE.log(new Status(IStatus.ERROR,
									AcceleoEquinoxLauncherPlugin.INSTANCE.getSymbolicName(),
									"Error launching EMF Codegen", e));
						}
					} else {
						try {
							new AcceleoGeneratorRunner(output, generatorParameters).launchAcceleoGenerator(
									monitor, modelURIS, generatorsFullName);
						} catch (RuntimeException e) {
							/*
							 * the exception has been logged in the launchAcceleoGenerator method.
							 */
							somethingWentWrong = true;
						}
					}
					if (projectSearchPaths.length > 0) {
						wks.clearWorkspace(monitor);
					}
				} else if (action.startsWith(LAUNCH_PREFIX)) {
					String launchConfigurationPath = action.substring(LAUNCH_PREFIX.length());
					try {
						new LaunchConfigurationRunner().launch(launchConfigurationPath, BasicMonitor
								.toIProgressMonitor(monitor));
					} catch (CoreException e) {
						somethingWentWrong = true;
						AcceleoEquinoxLauncherPlugin.INSTANCE.log(new Status(IStatus.ERROR,
								AcceleoEquinoxLauncherPlugin.INSTANCE.getSymbolicName(),
								"Error launching the " + launchConfigurationPath + " file.", e));
					}

				}
			}
		} catch (CmdLineException e) {
			/*
			 * problem in the command line
			 */
			AcceleoEquinoxLauncherPlugin.INSTANCE.log(new Status(IStatus.ERROR,
					AcceleoEquinoxLauncherPlugin.INSTANCE.getSymbolicName(), e.getMessage(), e));

			/*
			 * print the list of available options
			 */
			parser.printUsage(System.err);
			System.err.println();
			somethingWentWrong = true;
		}

		if (somethingWentWrong) {
			return APPLICATION_ERROR;
		}
		return IApplication.EXIT_OK;

	}

	/**
	 * Validate arguments which are mandatory only in some circumstances.
	 * 
	 * @throws CmdLineException
	 *             if the arguments are not valid.
	 */
	private void validateArguments() throws CmdLineException {
		/*
		 * some arguments are required but depending on the actions: generate: model is required, launch:
		 * nothing is required
		 */
		boolean hasGenerator = false;
		for (String a : actions) {
			if (a.startsWith(GENERATE_PREFIX)) {
				hasGenerator = true;
			}
		}
		if (hasGenerator && (models == null || models.length == 0)) {
			throw new CmdLineException("The -models flag is required when launching a generator.");
		}

	}

	/**
	 * Log a message as an info.
	 * 
	 * @param message
	 *            the error message.
	 */
	private void info(String message) {
		AcceleoEquinoxLauncherPlugin.INSTANCE.log(new Status(IStatus.INFO,
				AcceleoEquinoxLauncherPlugin.INSTANCE.getSymbolicName(), message));
	}

	@Override
	public void stop() {
		/*
		 * nothing special to do when the application stops.
		 */

	}

}
