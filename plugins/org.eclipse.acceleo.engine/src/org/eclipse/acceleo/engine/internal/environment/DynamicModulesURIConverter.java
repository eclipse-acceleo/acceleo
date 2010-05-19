/*******************************************************************************
 * Copyright (c) 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;

/**
 * This will allow us to handle references to file scheme URIs within the resource set containing the
 * generation modules and their dynamic overrides. We need this since the dynamic overrides are loaded with
 * file scheme URIs whereas the generation module can be loaded through platform scheme URIs and we need
 * references to be resolved anyway.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
final class DynamicModulesURIConverter extends ExtensibleURIConverterImpl {
	/** This will be initialized with the environment that asked for the construction of a converter. */
	private final AcceleoEvaluationEnvironment parentEnvironment;

	/**
	 * This is only meant to be instantiated once for each generation, from the
	 * {@link AcceleoEvaluationEnvironment} only.
	 * 
	 * @param parent
	 *            Environment that asked for this URI converter instance.
	 */
	DynamicModulesURIConverter(AcceleoEvaluationEnvironment parent) {
		parentEnvironment = parent;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ExtensibleURIConverterImpl#normalize(URI)
	 */
	@Override
	public URI normalize(URI uri) {
		if (!IAcceleoConstants.EMTL_FILE_EXTENSION.equals(uri.fileExtension())
				|| !"file".equals(uri.scheme())) { //$NON-NLS-1$
			return super.normalize(uri);
		}
		URI normalized = getURIMap().get(uri);
		if (normalized == null) {
			String moduleName = uri.lastSegment();
			moduleName = moduleName.substring(0, moduleName.lastIndexOf('.'));
			Set<URI> candidateURIs = new LinkedHashSet<URI>();

			// Search matching module in the current generation context
			Set<Module> candidateModules = searchCurrentModuleForCandidateMatches(moduleName);
			for (Module candidateModule : candidateModules) {
				candidateURIs.add(candidateModule.eResource().getURI());
			}
			// If there were no matching module, search in their ResourceSet(s)
			if (candidateURIs.size() == 0) {
				candidateURIs.addAll(searchResourceSetForMatches(moduleName));
			}
			if (candidateURIs.size() == 1) {
				normalized = candidateURIs.iterator().next();
			} else if (candidateURIs.size() > 0) {
				normalized = findBestMatchFor(uri, candidateURIs);
			}
			// There is a chance that our match should itself be normalized
			if ((normalized == null || "file".equals(normalized.scheme())) //$NON-NLS-1$
					&& EMFPlugin.IS_ECLIPSE_RUNNING) {
				String uriToString = uri.toString();
				if (uriToString.indexOf('#') > 0) {
					uriToString = uriToString.substring(0, uriToString.indexOf('#'));
				}
				String resolvedPath = AcceleoWorkspaceUtil.resolveAsPlatformPluginResource(uriToString);
				if (resolvedPath != null) {
					normalized = URI.createURI(resolvedPath);
				}
			}
			if (normalized == null) {
				normalized = super.normalize(uri);
			}
			if (!uri.equals(normalized)) {
				getURIMap().put(uri, normalized);
			}
		}
		return normalized;
	}

	/**
	 * Returns the normalized form of the URI, using the given multiple candidates (this means that more than
	 * 2 modules had a matching name).
	 * 
	 * @param uri
	 *            The URI that is to be normalized.
	 * @param candidateURIs
	 *            URIs of the modules that can potentially be a match for <code>uri</code>.
	 * @return the normalized form
	 */
	private URI findBestMatchFor(URI uri, Set<URI> candidateURIs) {
		URI normalized = null;
		final Iterator<URI> candidatesIterator = candidateURIs.iterator();
		final List<String> referenceSegments = Arrays.asList(uri.segments());
		Collections.reverse(referenceSegments);
		int highestEqualFragments = 0;
		while (candidatesIterator.hasNext()) {
			final URI next = candidatesIterator.next();
			int equalFragments = 0;
			final List<String> candidateSegments = Arrays.asList(next.segments());
			Collections.reverse(candidateSegments);
			for (int i = 0; i < Math.min(candidateSegments.size(), referenceSegments.size()); i++) {
				if (candidateSegments.get(i) == referenceSegments.get(i)) {
					equalFragments++;
				} else {
					break;
				}
			}
			if (equalFragments > highestEqualFragments) {
				highestEqualFragments = equalFragments;
				normalized = next;
			}
		}
		return normalized;
	}

	/**
	 * This will search the current generation context for a loaded module matching the given
	 * <code>moduleName</code>.
	 * 
	 * @param moduleName
	 *            Name of the module we seek.
	 * @return The Set of all modules currently loaded for generation going by the name
	 *         <code>moduleName</code>.
	 */
	private Set<Module> searchCurrentModuleForCandidateMatches(String moduleName) {
		Set<Module> candidates = new LinkedHashSet<Module>();
		for (Module module : parentEnvironment.getCurrentModules()) {
			if (moduleName.equals(module.getName())) {
				candidates.add(module);
			}
		}
		return candidates;
	}

	/**
	 * This will search throughout the resourceSet(s) containing the loaded modules for modules going by name
	 * <code>moduleName</code>.
	 * 
	 * @param moduleName
	 *            Name of the module we seek.
	 * @return The Set of all modules loaded within the generation ResourceSet going by the name
	 *         <code>moduleName</code>.
	 */
	private Set<URI> searchResourceSetForMatches(String moduleName) {
		final Set<URI> candidates = new LinkedHashSet<URI>();
		final List<ResourceSet> resourceSets = new ArrayList<ResourceSet>();
		for (Module module : parentEnvironment.getCurrentModules()) {
			final ResourceSet resourceSet = module.eResource().getResourceSet();
			if (!resourceSets.contains(resourceSet)) {
				resourceSets.add(resourceSet);
			}
		}
		for (ResourceSet resourceSet : resourceSets) {
			for (Resource resource : resourceSet.getResources()) {
				if (IAcceleoConstants.EMTL_FILE_EXTENSION.equals(resource.getURI().fileExtension())) {
					String candidateName = resource.getURI().lastSegment();
					candidateName = candidateName.substring(0, candidateName.lastIndexOf('.'));
					if (moduleName.equals(candidateName)) {
						candidates.add(resource.getURI());
					}
				}
			}
		}
		return candidates;
	}
}
