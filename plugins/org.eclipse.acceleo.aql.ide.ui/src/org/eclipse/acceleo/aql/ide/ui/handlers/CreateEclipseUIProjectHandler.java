/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.GenerationCompareEditorInput;
import org.eclipse.acceleo.aql.ide.ui.module.main.EclipseUIProjectGenerator;
import org.eclipse.acceleo.aql.ide.ui.module.main.StandaloneGenerator;
import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
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

		final Map<URI, String> preview = new HashMap<>();
		final IRunnableWithProgress generateRunnable = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				final Iterator<?> it = selection.iterator();
				final Map<IProject, List<IFile>> projectToFiles = new LinkedHashMap<>();
				int amountOfWork = 0;
				while (it.hasNext()) {
					final Object selected = it.next();
					if (selected instanceof IFile) {
						final IFile file = (IFile)selected;
						projectToFiles.computeIfAbsent(file.getProject(), p -> new ArrayList<>()).add(file);
						amountOfWork++;
					}
				}

				amountOfWork += projectToFiles.size();
				final SubMonitor subMonitor = SubMonitor.convert(monitor, amountOfWork);
				for (Entry<IProject, List<IFile>> entry : projectToFiles.entrySet()) {
					for (IFile file : entry.getValue()) {
						final Monitor childMonitor = BasicMonitor.toMonitor(subMonitor.split(1));
						try {
							final StandaloneGenerator standaloneGenerator = new StandaloneGenerator(file);
							standaloneGenerator.generate(childMonitor);
							preview.putAll(standaloneGenerator.getPreview());
						} finally {
							childMonitor.done();
						}
						if (monitor.isCanceled()) {
							break;
						}
					}
					final Monitor childMonitor = BasicMonitor.toMonitor(subMonitor.split(1));
					try {
						final EclipseUIProjectGenerator eclipseUIProjectGenerator = new EclipseUIProjectGenerator(
								entry.getValue());
						eclipseUIProjectGenerator.generate(childMonitor);
						preview.putAll(eclipseUIProjectGenerator.getPreview());
					} finally {
						childMonitor.done();
					}
					if (monitor.isCanceled()) {
						break;
					}
				}
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().run(false, true, generateRunnable);

			final GenerationCompareEditorInput compareEditorInput = new GenerationCompareEditorInput(
					new CompareConfiguration(), preview, ResourcesPlugin.getWorkspace().getRoot());
			if (compareEditorInput.hasDifferences()) {
				CompareUI.openCompareDialog(compareEditorInput);
			}
		} catch (InvocationTargetException e) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
					"Couldn't generate.", e));
		} catch (InterruptedException e) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
					"Couldn't generate.", e));
		}

		return null;
	}
}
