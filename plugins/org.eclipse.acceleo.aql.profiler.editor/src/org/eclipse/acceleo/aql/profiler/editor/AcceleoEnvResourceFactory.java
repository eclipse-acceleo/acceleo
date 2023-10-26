/*******************************************************************************
 * Copyright (c) 2020, 2023 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.editor;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * A resource factory able to resolve acceleoenv URIs.
 * 
 * @author wpiers
 */
public class AcceleoEnvResourceFactory extends ResourceFactoryImpl {

	/**
	 * The {@link IQualifiedNameQueryEnvironment} used to resolve modules.
	 */
	private IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link ResourceSet} for models.
	 */
	private ResourceSet resourceSetForModels;

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
		// clean any previous environment
		if (queryEnvironment != null && resourceSetForModels != null) {
			dispose();
		}
		final IQualifiedNameResolver resolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
				AcceleoPlugin.getPlugin().getClass().getClassLoader(), project,
				AcceleoParser.QUALIFIER_SEPARATOR);

		final Map<String, String> options = new LinkedHashMap<>();
		final ArrayList<Exception> exceptions = new ArrayList<>();
		// the ResourceSet will not be used
		resourceSetForModels = AQLUtils.createResourceSetForModels(exceptions, this, new ResourceSetImpl(),
				options);
		// TODO report exceptions
		queryEnvironment = AcceleoUtil.newAcceleoQueryEnvironment(options, resolver, resourceSetForModels);

		final AcceleoEvaluator evaluator = new AcceleoEvaluator(queryEnvironment.getLookupEngine());
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
		resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Resource createResource(URI uri) {
		String qualifiedName = uri.toString().replaceFirst(AcceleoParser.ACCELEOENV_URI_PROTOCOL, ""); //$NON-NLS-1$
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final Object resolved = resolver.resolve(qualifiedName);
		if (resolved instanceof Module) {
			return ((Module)resolved).eResource();
		} else {
			return null;
		}
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
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final String moduleQualifiedName = resolver.getQualifiedName(module);
		java.net.URI sourceURI = resolver.getSourceURI(moduleQualifiedName);
		if (sourceURI != null) {
			IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(sourceURI);
			if (files.length > 0) {
				return files[0];
			}
		}
		return null;
	}

	public void dispose() {
		AQLUtils.cleanResourceSetForModels(this, resourceSetForModels);
		AcceleoUtil.cleanServices(queryEnvironment, resourceSetForModels);
	}
}
