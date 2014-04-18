/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.emf.common.EMFPlugin;

/**
 * This will provide utility methods for the manipulation of the launching ID.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public final class AcceleoLaunchingUtil {
	/** This will hold all arguments that have been passed to the generation. */
	public static final String LAUNCH_ID_ARGUMENTS = "args="; //$NON-NLS-1$

	/** This will be at the beginning of each of our Launch IDs. */
	public static final String LAUNCH_ID_LAUNCHER = "launcher="; //$NON-NLS-1$

	/** This will hold the path to our generation launcher. */
	public static final String LAUNCH_ID_LAUNCHER_PATH = "launcherPath="; //$NON-NLS-1$

	/** Path to the model for which we generated text. */
	public static final String LAUNCH_ID_MODEL_PATH = "model="; //$NON-NLS-1$

	/** Path to the target folder for the generation. */
	public static final String LAUNCH_ID_TARGET_PATH = "target="; //$NON-NLS-1$

	/** This will be used to separate the different parts composing a path. */
	public static final char LAUNCHER_ID_ELEMENT_SEPARATOR = '|';

	/** This will be used to separate the different parts composing a launch ID. */
	public static final char LAUNCHER_ID_SEPARATOR = '&';

	/**
	 * The constructor.
	 */
	private AcceleoLaunchingUtil() {
		// prevent instantiation
	}

	/**
	 * This will create a launch ID assuming that the generation has been called from an UI project.
	 * 
	 * @param project
	 *            Project where the generation module is located.
	 * @param qualifiedName
	 *            Qualified name of the {@link org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator}
	 *            allowing this generation.
	 * @param model
	 *            Path to the model with which to launch this generation.
	 * @param targetFolder
	 *            Target folder of this generation.
	 * @param args
	 *            Arguments of the generation.
	 * @return The created launcher ID.
	 */
	public static String computeUIProjectID(String project, String qualifiedName, String model,
			String targetFolder, List<String> args) {
		String launcherID = LAUNCH_ID_LAUNCHER + Launcher.UI_PROJECT.ordinal();
		String launcherPath = LAUNCH_ID_LAUNCHER_PATH + project + LAUNCHER_ID_ELEMENT_SEPARATOR
				+ qualifiedName;
		String modelPath = LAUNCH_ID_MODEL_PATH + model;
		String targetPath = LAUNCH_ID_TARGET_PATH + targetFolder;

		StringBuilder arguments = new StringBuilder(LAUNCH_ID_ARGUMENTS);
		Iterator<String> argIterator = args.iterator();
		while (argIterator.hasNext()) {
			arguments.append(argIterator.next());
			if (argIterator.hasNext()) {
				arguments.append(LAUNCHER_ID_ELEMENT_SEPARATOR);
			}
		}

		StringBuilder launchID = new StringBuilder();
		launchID.append(launcherID);
		launchID.append(LAUNCHER_ID_SEPARATOR);
		launchID.append(launcherPath);
		launchID.append(LAUNCHER_ID_SEPARATOR);
		launchID.append(modelPath);
		launchID.append(LAUNCHER_ID_SEPARATOR);
		launchID.append(targetPath);
		if (!args.isEmpty()) {
			launchID.append(LAUNCHER_ID_SEPARATOR);
			launchID.append(arguments.toString());
		}

		return launchID.toString();
	}

	/**
	 * This will create a launch ID assuming that the generation has been called from a launch configuration.
	 * 
	 * @param project
	 *            Project where the generation module is located.
	 * @param qualifiedName
	 *            Qualified name of the {@link org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator}
	 *            allowing this generation.
	 * @param model
	 *            Path to the model with which to launch this generation.
	 * @param targetFolder
	 *            Target folder of this generation.
	 * @param args
	 *            Arguments of the generation.
	 * @return The created launcher ID.
	 */
	public static String computeLaunchConfigID(String project, String qualifiedName, String model,
			String targetFolder, List<String> args) {
		String launcherID = LAUNCH_ID_LAUNCHER + Launcher.LAUNCH_CONFIG.ordinal();
		String launcherPath = LAUNCH_ID_LAUNCHER_PATH + project + LAUNCHER_ID_ELEMENT_SEPARATOR
				+ qualifiedName;
		String modelPath = LAUNCH_ID_MODEL_PATH + model;
		String targetPath = LAUNCH_ID_TARGET_PATH + targetFolder;

		StringBuilder arguments = new StringBuilder(LAUNCH_ID_ARGUMENTS);
		Iterator<String> argIterator = args.iterator();
		while (argIterator.hasNext()) {
			arguments.append(argIterator.next());
			if (argIterator.hasNext()) {
				arguments.append(LAUNCHER_ID_ELEMENT_SEPARATOR);
			}
		}

		StringBuilder launchID = new StringBuilder();
		launchID.append(launcherID);
		launchID.append(LAUNCHER_ID_SEPARATOR);
		launchID.append(launcherPath);
		launchID.append(LAUNCHER_ID_SEPARATOR);
		launchID.append(modelPath);
		launchID.append(LAUNCHER_ID_SEPARATOR);
		launchID.append(targetPath);
		if (!args.isEmpty()) {
			launchID.append(LAUNCHER_ID_SEPARATOR);
			launchID.append(arguments.toString());
		}

		return launchID.toString();
	}

	/**
	 * Checks that all mandatory parameters for a "launch config" relaunch can be parsed from the given launch
	 * ID.
	 * 
	 * @param launchID
	 *            The ID that is to be checked.
	 * @return <code>true</code> if this launch ID can be parsed to relaunch its associated generation,
	 *         <code>false</code> otherwise.
	 */
	public static boolean checkMandatoryLaunchConfigParameters(String launchID) {
		String launcherPath = getLauncherPath(launchID);
		String modelPath = getModelPath(launchID);
		String targetPath = getTargetPath(launchID);

		return launcherPath.length() > 0 && modelPath.length() > 0 && targetPath.length() > 0;
	}

	/**
	 * This will try and retrieve the list of arguments from the given launch ID.
	 * 
	 * @param launchID
	 *            ID from which to retrieve the arguments list.
	 * @return The arguments list if we could parse it from the launchID, an empty list if not.
	 */
	public static List<String> getArguments(String launchID) {
		int argumentsOffset = launchID.indexOf(LAUNCH_ID_ARGUMENTS);

		if (argumentsOffset > 0) {
			String arguments = launchID.substring(argumentsOffset + LAUNCH_ID_ARGUMENTS.length());
			return Arrays.asList(arguments.split(String.valueOf(LAUNCHER_ID_ELEMENT_SEPARATOR)));
		}

		return Collections.emptyList();
	}

	/**
	 * Retrieves the launcher kind from the given launch ID.
	 * 
	 * @param launchID
	 *            ID from which to retrieve a launcher kind.
	 * @return The Launcher kind corresponding to this ID, <code>null</code> if none.
	 */
	public static Launcher getLauncherKind(String launchID) {
		int launcherOffset = launchID.indexOf(LAUNCH_ID_LAUNCHER);
		int firstSeparatorIndex = launchID.indexOf(LAUNCHER_ID_SEPARATOR);

		// The launcher kind is supposed to be at the ID's beginning.
		if (launcherOffset == 0 && firstSeparatorIndex > 0) {
			String launcherKind = launchID.substring(launcherOffset + LAUNCH_ID_LAUNCHER.length(),
					firstSeparatorIndex);
			try {
				int kind = Integer.valueOf(launcherKind).intValue();
				if (kind >= 0 && kind < Launcher.values().length) {
					return Launcher.values()[kind];
				}
			} catch (NumberFormatException e) {
				// Will return null
			}
		}

		return null;
	}

	/**
	 * This will try and retrieve the launcher path from the given launch ID.
	 * 
	 * @param launchID
	 *            ID from which to retrieve a launcher path.
	 * @return The launcher path if we could parse it from the launchID, the empty String if not.
	 */
	public static String getLauncherPath(String launchID) {
		int launcherPathOffset = launchID.indexOf(LAUNCH_ID_LAUNCHER_PATH);

		if (launcherPathOffset > 0) {
			int launcherPathSeparator = launchID.indexOf(LAUNCHER_ID_SEPARATOR, launcherPathOffset);

			if (launcherPathSeparator > 0) {
				return launchID.substring(launcherPathOffset + LAUNCH_ID_LAUNCHER_PATH.length(),
						launcherPathSeparator);
			}
		}

		return ""; //$NON-NLS-1$
	}

	/**
	 * This will try and retrieve the model path from the given launch ID.
	 * 
	 * @param launchID
	 *            ID from which to retrieve a model path.
	 * @return The model path if we could parse it from the launchID, the empty String if not.
	 */
	public static String getModelPath(String launchID) {
		int modelPathOffset = launchID.indexOf(LAUNCH_ID_MODEL_PATH);

		if (modelPathOffset > 0) {
			int modelPathSeparator = launchID.indexOf(LAUNCHER_ID_SEPARATOR, modelPathOffset);

			if (modelPathSeparator > 0) {
				return launchID
						.substring(modelPathOffset + LAUNCH_ID_MODEL_PATH.length(), modelPathSeparator);
			}
		}

		return ""; //$NON-NLS-1$
	}

	/**
	 * This will try and retrieve the target path from the given launch ID.
	 * 
	 * @param launchID
	 *            ID from which to retrieve a target path.
	 * @return The target path if we could parse it from the launchID, the empty String if not.
	 */
	public static String getTargetPath(String launchID) {
		int targetPathOffset = launchID.indexOf(LAUNCH_ID_TARGET_PATH);

		if (targetPathOffset > 0) {
			int targetPathSeparator = launchID.indexOf(LAUNCHER_ID_SEPARATOR, targetPathOffset);
			// there could be no separator after this one : arguments are optional
			if (targetPathSeparator == -1) {
				targetPathSeparator = launchID.length();
			}

			return launchID.substring(targetPathOffset + LAUNCH_ID_TARGET_PATH.length(), targetPathSeparator);
		}

		return ""; //$NON-NLS-1$
	}

	/**
	 * To switch the traceability status.
	 * 
	 * @param activate
	 *            indicates that we would like to compute the traceability information
	 */
	public static void switchTraceability(boolean activate) {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			AcceleoPreferences.switchTraceability(activate);
		}
	}

	/**
	 * This will enumerate the different launching means we have for a generation.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	public static enum Launcher {
		/**
		 * This will be used to indicate that the generation has been launched from an Eclipse launch
		 * configuration.
		 */
		LAUNCH_CONFIG,

		/**
		 * This will be used to indicate that the generation has been launched from an Acceleo UI Project.
		 */
		UI_PROJECT,
	}

}
