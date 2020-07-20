/*******************************************************************************
 * Copyright (c) 2020 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.editor;

import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.ide.Activator;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.profiler.presentation.ProfilerEditorPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * A resource factory able to resolve acceleoenv URIs.
 * 
 * @author wpiers
 */
public class AcceleoEnvResourceFactory extends ResourceFactoryImpl {

	/**
	 * The environment used to resolve modules.
	 */
	private AcceleoEnvironment environment;

	/**
	 * The project where to find modules.
	 */
	private IProject project;

	/**
	 * Creates a factory for the given project.
	 * 
	 * @param project
	 *            the project
	 */
	public AcceleoEnvResourceFactory(IProject project) {
		this.project = project;
		init();
	}

	/**
	 * Inits the factory acceleo environnement. Resets any module cache.
	 */
	public void init() {
		environment = new AcceleoEnvironment(new DefaultGenerationStrategy(), URI.createURI("TMP")); //$NON-NLS-1$
		environment.setModuleResolver(Activator.getPlugin().createQualifiedNameResolver(environment
				.getQueryEnvironment(), project));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Resource createResource(URI uri) {
		String qualifiedName = uri.toString().replaceFirst(AcceleoParser.ACCELEOENV_URI_PROTOCOL, ""); //$NON-NLS-1$
		org.eclipse.acceleo.Module module = environment.getModule(qualifiedName);
		return module.eResource();
	}

	/**
	 * Returns the source IFile containing the module.
	 * 
	 * @param module
	 *            the module
	 * @return the source file
	 * @throws URISyntaxException
	 *             if the module resource URI cannot be resolved
	 */
	public IFile getSourceFile(Module module) {
		URL sourceURL = environment.getModuleSourceURL(module);
		if (sourceURL != null) {
			try {
				IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(sourceURL
						.toURI());
				if (files.length > 0) {
					return files[0];
				}
			} catch (URISyntaxException e) {
				ProfilerEditorPlugin.getPlugin().log(e);
			}
		}
		return null;
	}
}
