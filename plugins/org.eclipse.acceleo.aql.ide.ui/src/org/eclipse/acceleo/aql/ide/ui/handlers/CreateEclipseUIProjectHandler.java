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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.aql.ide.ui.module.main.EclipseUIProjectGenerator;
import org.eclipse.acceleo.aql.ide.ui.module.main.StandaloneGenerator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Create Eclipse UI project handler.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CreateEclipseUIProjectHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);

		final Iterator<?> it = selection.iterator();
		final Map<IProject, List<IFile>> projectToFiles = new LinkedHashMap<>();
		while (it.hasNext()) {
			final Object selected = it.next();
			if (selected instanceof IFile) {
				final IFile file = (IFile)selected;
				projectToFiles.computeIfAbsent(file.getProject(), p -> new ArrayList<>()).add(file);
			}
		}

		for (Entry<IProject, List<IFile>> entry : projectToFiles.entrySet()) {
			for (IFile file : entry.getValue()) {
				final StandaloneGenerator standaloneGenerator = new StandaloneGenerator(file);
				standaloneGenerator.generate();
			}
			EclipseUIProjectGenerator eclipseUIProjectGenerator = new EclipseUIProjectGenerator(entry
					.getValue());
			eclipseUIProjectGenerator.generate();
		}

		return null;
	}
}
