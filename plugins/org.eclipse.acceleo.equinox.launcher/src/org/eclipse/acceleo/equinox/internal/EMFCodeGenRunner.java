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
package org.eclipse.acceleo.equinox.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * A class launching the EMF "classic" codegen.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public class EMFCodeGenRunner {

	/**
	 * Launch the equivalent of the "generateAll" right click option.
	 * 
	 * @param modelURIS
	 *            models to launch the action on.
	 * @param monitor
	 *            monitor to track progress.
	 * @throws CoreException
	 *             if case of error during generation.
	 */
	public void generateAll(final List<URI> modelURIS, final Monitor monitor) throws CoreException {
		final ResourceSet resourceSet = new ResourceSetImpl();

		Map<URI, URI> result = null;
		/*
		 * Invoke computePlatformURIMap by reflection because this API change in EMF
		 */
		try {
			Method computePlatformURIMap = EcorePlugin.class.getMethod("computePlatformURIMap", Boolean.TYPE);
			result = (Map<URI, URI>)computePlatformURIMap.invoke(null, true);
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		/*
		 * If an excpeption is throw result=null.
		 */
		if (result == null) {
			result = EcorePlugin.computePlatformURIMap();
		}

		if (result != null) {
			resourceSet.getURIConverter().getURIMap().putAll(result);
		}

		final org.eclipse.emf.codegen.ecore.generator.Generator generator = new org.eclipse.emf.codegen.ecore.generator.Generator();
		final List<GenModel> genmodels = new ArrayList<GenModel>();
		for (URI uri : modelURIS) {
			Resource res = resourceSet.getResource(uri, true);
			if (res != null) {
				for (EObject root : res.getContents()) {
					if (root instanceof GenModel) {
						genmodels.add((GenModel)root);
					}
				}
			}
		}

		final List<Diagnostic> diagnostics = new ArrayList<Diagnostic>();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor progressMonitor) throws CoreException {
				for (GenModel genModel : genmodels) {

					genModel.setCanGenerate(true);
					generator.setInput(genModel);
					if (genModel.getModelDirectory() != null && genModel.getModelDirectory().length() > 0) {
						diagnostics.add(generator.generate(genModel,
								GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, monitor));
					}
					if (genModel.getEditDirectory() != null && genModel.getEditDirectory().length() > 0) {
						diagnostics.add(generator.generate(genModel,
								GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE, monitor));
					}
					if (genModel.getEditorDirectory() != null && genModel.getEditorDirectory().length() > 0) {
						diagnostics.add(generator.generate(genModel,
								GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE, monitor));
					}
				}

			}
		};
		workspace.run(runnable, BasicMonitor.toIProgressMonitor(monitor));
	}

}
