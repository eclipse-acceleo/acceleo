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
	 * The build resource kind keyword in the .project file.
	 */
	public static final String BUILD_RESOURCE_KIND = "resource.kind"; //$NON-NLS-1$

	/**
	 * The build resource kind for xmi resource.
	 */
	public static final String BUILD_XMI_RESOURCE = "xmi"; //$NON-NLS-1$

	/**
	 * The build resource kind for binary resources.
	 */
	public static final String BUILD_BINARY_RESOURCE = "binary"; //$NON-NLS-1$

	/**
	 * Strict MTL build compliance mode.
	 */
	public static final String BUILD_STRICT_MTL_COMPLIANCE = "strict"; //$NON-NLS-1$

	/**
	 * Pragmatic build compliance mode which allows more useful operations than the 'Full OMG' one.
	 */
	public static final String BUILD_PRAGMATIC_COMPLIANCE = "pragmatic"; //$NON-NLS-1$

	/**
	 * Indicates that we should use platform:/resource uris during the compilation.
	 * 
	 * @since 3.3
	 */
	public static final String COMPILATION_PLATFORM_RESOURCE = "compilation.platform.resource"; //$NON-NLS-1$

	/**
	 * Indicates that we should use the absolute path of the file during the compilation.
	 * 
	 * @since 3.3
	 */
	public static final String COMPILATION_ABSOLUTE_PATH = "compilation.absolute.path"; //$NON-NLS-1$

	/**
	 * Build compliance keyword in the Acceleo builder arguments list of the '.project' file.
	 */
	private static final String BUILD_COMPLIANCE_KEYWORD = "compliance"; //$NON-NLS-1$

	/**
	 * Indicates if we should trim the position from the compiled resources.
	 * 
	 * @since 3.2
	 */
	private static final String TRIM_POSITION_RESOURCE_KEYWORD = "trim-position"; //$NON-NLS-1$

	/**
	 * The compilation path kind key.
	 */
	private static final String COMPILATION_PATH_KIND_KEYWORD = "compilation.kind"; //$NON-NLS-1$

	/**
	 * The project.
	 */
	private IProject project;

	/**
	 * The build compliance mode of the project : BUILD_STRICT_MTL_COMPLIANCE or BUILD_PRAGMATIC_COMPLIANCE.
	 */
	private String compliance;

	/**
	 * The kind of resource that will be built.
	 */
	private String resourceKind;

	/**
	 * Indicates if the positions should be trimmed.
	 * 
	 * @since 3.2
	 */
	private boolean trimmedPositions;

	/**
	 * The compilation kind of the project.
	 */
	private String compilationKind;

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
				String arg = command.getArguments().get(BUILD_COMPLIANCE_KEYWORD);
				if (BUILD_STRICT_MTL_COMPLIANCE.equals(arg)) {
					compliance = BUILD_STRICT_MTL_COMPLIANCE;
				} else {
					compliance = BUILD_PRAGMATIC_COMPLIANCE;
				}

				arg = command.getArguments().get(BUILD_RESOURCE_KIND);
				if (BUILD_BINARY_RESOURCE.equals(arg)) {
					resourceKind = BUILD_BINARY_RESOURCE;
				} else {
					resourceKind = BUILD_XMI_RESOURCE;
				}

				arg = command.getArguments().get(TRIM_POSITION_RESOURCE_KEYWORD);
				if (Boolean.parseBoolean(arg)) {
					trimmedPositions = true;
				}

				arg = command.getArguments().get(COMPILATION_PATH_KIND_KEYWORD);
				if (COMPILATION_PLATFORM_RESOURCE.equals(arg)) {
					compilationKind = COMPILATION_PLATFORM_RESOURCE;
				} else {
					compilationKind = COMPILATION_ABSOLUTE_PATH;
				}

			} else {
				compliance = BUILD_PRAGMATIC_COMPLIANCE;
				resourceKind = BUILD_XMI_RESOURCE;
				compilationKind = COMPILATION_ABSOLUTE_PATH;
				trimmedPositions = false;
			}
		} catch (CoreException e) {
			compliance = BUILD_PRAGMATIC_COMPLIANCE;
			resourceKind = BUILD_XMI_RESOURCE;
			compilationKind = COMPILATION_ABSOLUTE_PATH;
			trimmedPositions = false;
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
	 * Returns the resource kind that will be produced by the compilation.
	 * 
	 * @return The resource kind that will be produced by the compilation.
	 */
	public String getResourceKind() {
		return resourceKind;
	}

	/**
	 * Sets the resource kind that will be produced by the compilation (XMI or Binary).
	 * 
	 * @param resourceKind
	 *            The resource kind.
	 */
	public void setResourceKind(String resourceKind) {
		this.resourceKind = resourceKind;
	}

	/**
	 * Returns the compilation kind setting of this project.
	 * 
	 * @return The compilation kind setting of this project.
	 */
	public String getCompilationKind() {
		return compilationKind;
	}

	/**
	 * Sets the compilation kind setting of this project.
	 * 
	 * @param compilationKind
	 *            The compilation kind setting
	 */
	public void setCompilationKind(String compilationKind) {
		this.compilationKind = compilationKind;
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
			args.put(BUILD_RESOURCE_KIND, resourceKind);
			args.put(COMPILATION_PATH_KIND_KEYWORD, compilationKind);
			args.put(TRIM_POSITION_RESOURCE_KEYWORD, String.valueOf(trimmedPositions));
			command.setArguments(args);
			desc.setBuildSpec(commands);
			project.setDescription(desc, null);
		}
	}

	/**
	 * Indicates if the positions should be trimmed from the emtl files.
	 * 
	 * @return <code>true</code> if the positions should be trimmed, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public boolean isTrimmedPositions() {
		return trimmedPositions;
	}

	/**
	 * Sets the boolean indicating if the positions should be trimmed from the emtl files.
	 * 
	 * @param trimmedPositions
	 *            The boolean
	 * @since 3.2
	 */
	public void setTrimmedPositions(boolean trimmedPositions) {
		this.trimmedPositions = trimmedPositions;
	}

}
