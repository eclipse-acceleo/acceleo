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
package org.eclipse.acceleo.internal.ide.ui.launching;

import java.util.StringTokenizer;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.swt.widgets.Composite;

/**
 * A launch configuration javaArgumentsTab that displays program arguments, VM arguments, and working
 * directory launch configuration attributes.
 * <p>
 * This class may be instantiated.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoJavaArgumentsTab extends JavaArgumentsTab {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		fPrgmArgumentsText.setEditable(false);
	}

	/**
	 * Updates the program arguments with a model path, a target folder path, and other Acceleo arguments.
	 * 
	 * @param config
	 *            is an editable copy of the current launch configuration
	 * @param model
	 *            is the model path, relative to the workspace
	 * @param target
	 *            is the target folder path, relative to the workspace
	 * @param args
	 *            are the Acceleo application arguments (properties)
	 */
	public void updateArguments(ILaunchConfigurationWorkingCopy config, String model, String target,
			String args) {
		IPath modelPath;
		if (model != null) {
			modelPath = new Path(model);
		} else {
			modelPath = new Path(""); //$NON-NLS-1$
		}
		if (modelPath.segmentCount() > 1) {
			modelPath = ResourcesPlugin.getWorkspace().getRoot().getFile(modelPath).getLocation();
		}
		if (modelPath == null) {
			modelPath = new Path(""); //$NON-NLS-1$
		}
		IPath targetPath;
		if (target != null) {
			targetPath = new Path(target);
		} else {
			targetPath = new Path(""); //$NON-NLS-1$
		}
		if (targetPath.segmentCount() == 1) {
			targetPath = ResourcesPlugin.getWorkspace().getRoot().getProject(targetPath.lastSegment())
					.getLocation();
		} else if (targetPath.segmentCount() > 1) {
			targetPath = ResourcesPlugin.getWorkspace().getRoot().getFolder(targetPath).getLocation();
		}
		if (targetPath == null) {
			targetPath = new Path(""); //$NON-NLS-1$
		}
		StringBuffer newBuffer = new StringBuffer();
		newBuffer.append('"');
		newBuffer.append(modelPath.toString());
		newBuffer.append("\" \""); //$NON-NLS-1$
		newBuffer.append(targetPath.toString());
		newBuffer.append('"');
		if (args.length() > 0) {
			StringTokenizer st = new StringTokenizer(args, "\n"); //$NON-NLS-1$
			while (st.hasMoreTokens()) {
				String token = st.nextToken().trim();
				newBuffer.append(" \""); //$NON-NLS-1$
				newBuffer.append(token);
				newBuffer.append('"');
			}
		}
		String newText = newBuffer.toString();
		if (!newText.equals(fPrgmArgumentsText.getText())) {
			fPrgmArgumentsText.setText(newText);
			config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, newText);
		}
	}

}
