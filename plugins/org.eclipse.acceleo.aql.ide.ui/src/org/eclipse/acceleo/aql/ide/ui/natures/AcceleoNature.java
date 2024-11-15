/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.natures;

import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * The Acceleo nature.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoNature implements IProjectNature {

	/**
	 * The nature ID.
	 */
	public static final String ID = AcceleoUIPlugin.PLUGIN_ID + ".acceleoNature";

	/**
	 * The current {@link IProject}.
	 */
	private IProject project;

	@Override
	public void configure() throws CoreException {
		AcceleoUIPlugin.getDefault().getServiceContext().buildProject(getProject());

		// TODO builder
		// IProjectDescription desc = getProject().getDescription();
		// ICommand[] commands = desc.getBuildSpec();
		// for (int i = 0; i < commands.length; ++i) {
		// if (commands[i].getBuilderName().equals(AcceleoBuilder.BUILDER_ID)) {
		// return;
		// }
		// }
		// ICommand[] newCommands = new ICommand[commands.length + 1];
		// System.arraycopy(commands, 0, newCommands, 0, commands.length);
		// ICommand command = desc.newCommand();
		// command.setBuilderName(AcceleoBuilder.BUILDER_ID);
		// newCommands[newCommands.length - 1] = command;
		// desc.setBuildSpec(newCommands);
		// getProject().setDescription(desc, null);
	}

	@Override
	public void deconfigure() throws CoreException {
		AcceleoUIPlugin.getDefault().getServiceContext().cleanProject(getProject());

		// TODO builder
		// IProjectDescription description = getProject().getDescription();
		// ICommand[] commands = description.getBuildSpec();
		// for (int i = 0; i < commands.length; ++i) {
		// if (commands[i].getBuilderName().equals(AcceleoBuilder.BUILDER_ID)) {
		// ICommand[] newCommands = new ICommand[commands.length - 1];
		// System.arraycopy(commands, 0, newCommands, 0, i);
		// System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
		// description.setBuildSpec(newCommands);
		// return;
		// }
		// }
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}

}
