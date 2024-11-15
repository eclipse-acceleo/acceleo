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
package org.eclipse.acceleo.aql.ide.ui.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.natures.AcceleoNature;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Enable the {@link AcceleoNature} handler.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DisableAcceleoNatureHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);

		final Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			IProject project = Platform.getAdapterManager().getAdapter(it.next(), IProject.class);
			if (project != null && project.isAccessible()) {
				try {
					final IProjectDescription description = project.getDescription();
					List<String> natures = new ArrayList<String>(Arrays.asList(description.getNatureIds()));
					natures.remove(AcceleoNature.ID);
					description.setNatureIds(natures.toArray(new String[natures.size()]));
					project.setDescription(description, new NullProgressMonitor());
				} catch (CoreException e) {
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,
							AcceleoUIPlugin.PLUGIN_ID, "Can't get project description for " + project
									.getName(), e));
				}
			}
		}

		return null;
	}
}
