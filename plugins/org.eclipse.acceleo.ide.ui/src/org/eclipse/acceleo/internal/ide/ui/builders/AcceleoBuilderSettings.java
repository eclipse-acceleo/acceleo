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
package org.eclipse.acceleo.internal.ide.ui.builders;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

/**
 * The builder compiles the Acceleo templates in a background task. This is the options of this compiler. We
 * can define for instance the standard compliance mode : full or pragmatic.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoBuilderSettings {

	/**
	 * Strict MTL build compliance mode.
	 */
	public static final String BUILD_STRICT_MTL_COMPLIANCE = "strict"; //$NON-NLS-1$

	/**
	 * Pragmatic build compliance mode which allows more useful operations than the 'Full OMG' one.
	 */
	public static final String BUILD_PRAGMATIC_COMPLIANCE = "pragmatic"; //$NON-NLS-1$

	/**
	 * Build compliance keyword in the Acceleo builder arguments list of the '.project' file.
	 */
	private static final String BUILD_COMPLIANCE_KEYWORD = "compliance"; //$NON-NLS-1$

	/**
	 * The project.
	 */
	private IProject project;

	/**
	 * The build compliance mode of the project : BUILD_STRICT_MTL_COMPLIANCE or BUILD_PRAGMATIC_COMPLIANCE.
	 */
	private String compliance;

	/**
	 * Constructor.
	 * 
	 * @param project
	 *            is the project
	 */
	public AcceleoBuilderSettings(IProject project) {
		this.project = project;
		try {
			IProjectDescription desc = project.getDescription();
			ICommand[] commands = desc.getBuildSpec();
			ICommand command = getCommand(commands);
			if (command != null) {
				Object arg = command.getArguments().get(BUILD_COMPLIANCE_KEYWORD);
				if (BUILD_STRICT_MTL_COMPLIANCE.equals(arg)) {
					compliance = BUILD_STRICT_MTL_COMPLIANCE;
				} else {
					compliance = BUILD_PRAGMATIC_COMPLIANCE;
				}
			} else {
				compliance = BUILD_PRAGMATIC_COMPLIANCE;
			}
		} catch (CoreException e) {
			compliance = BUILD_PRAGMATIC_COMPLIANCE;
		}
	}

	/**
	 * Gets the Acceleo builder command in the given commands.
	 * 
	 * @param commands
	 *            are the commands
	 * @return the Acceleo builder command
	 */
	private ICommand getCommand(ICommand[] commands) {
		if (commands != null) {
			for (int i = 0; i < commands.length; ++i) {
				if (commands[i].getBuilderName().equals(AcceleoBuilder.BUILDER_ID)) {
					return commands[i];
				}
			}
		}
		return null;
	}

	/**
	 * Gets the build compliance mode : BUILD_STRICT_MTL_COMPLIANCE or BUILD_PRAGMATIC_COMPLIANCE.
	 * 
	 * @return the build compliance mode
	 */
	public String getCompliance() {
		return compliance;
	}

	/**
	 * Sets the build compliance mode : BUILD_STRICT_MTL_COMPLIANCE or BUILD_PRAGMATIC_COMPLIANCE. You have to
	 * call the save method if you want to write the new compliance mode in the '.project' file.
	 * 
	 * @param compliance
	 *            is the compliance mode
	 */
	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}

	/**
	 * To write the new settings in the '.project' file.
	 * 
	 * @throws CoreException
	 *             when an exception occurs
	 */
	public void save() throws CoreException {
		IProjectDescription desc = project.getDescription();
		ICommand[] commands = desc.getBuildSpec();
		ICommand command = getCommand(commands);
		if (command != null) {
			Map<String, String> args = new HashMap<String, String>();
			args.put(BUILD_COMPLIANCE_KEYWORD, compliance);
			command.setArguments(args);
			desc.setBuildSpec(commands);
			project.setDescription(desc, null);
		}
	}

}
