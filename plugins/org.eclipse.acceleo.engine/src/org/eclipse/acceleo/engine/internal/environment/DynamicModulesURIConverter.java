/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.environment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.internal.utils.workspace.BundleURLConverter;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
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
	/** The parent URIConverted if any. */
	private URIConverter parent;

	/** This will be initialized with the environment that asked for the construction of a converter. */
	private final AcceleoEvaluationEnvironment parentEnvironment;

	/**
	 * This is only meant to be instantiated once for each generation, from the
	 * {@link AcceleoEvaluationEnvironment} only.
	 * 
	 * @param parent
	 *            The parent URIConverter. Can be <code>null</code>.
	 * @param parentEnvironment
	 *            Environment that asked for this URI converter instance.
	 */
	DynamicModulesURIConverter(URIConverter parent, AcceleoEvaluationEnvironment parentEnvironment) {
		this.parent = parent;
		this.parentEnvironment = parentEnvironment;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ExtensibleURIConverterImpl#normalize(URI)
	 */
	@Override
	public URI normalize(URI uri) {
		URI normalized = normalizeWithParent(uri);
		if (normalized == null || normalized.equals(uri)) {
			normalized = dynamicNormalize(uri);
		}
		return normalized;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#getURIMap()
	 */
	@Override
	public Map<URI, URI> getURIMap() {
		if (parent != null) {
			return parent.getURIMap();
		}
		return super.getURIMap();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#getURIHandlers()
	 */
	@Override
	public EList<URIHandler> getURIHandlers() {
		if (parent != null) {
			return parent.getURIHandlers();
		}
		return super.getURIHandlers();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#getURIHandler(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public URIHandler getURIHandler(URI uri) {
		if (parent != null) {
			return parent.getURIHandler(uri);
		}
		return super.getURIHandler(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#getContentHandlers()
	 */
	@Override
	public EList<ContentHandler> getContentHandlers() {
		if (parent != null) {
			return parent.getContentHandlers();
		}
		return super.getContentHandlers();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#createInputStream(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public InputStream createInputStream(URI uri) throws IOException {
		if (parent != null) {
			return parent.createInputStream(uri);
		}
		return super.createInputStream(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#createInputStream(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
		if (parent != null) {
			return parent.createInputStream(uri, options);
		}
		return super.createInputStream(uri, options);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#createOutputStream(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public OutputStream createOutputStream(URI uri) throws IOException {
		if (parent != null) {
			return parent.createOutputStream(uri);
		}
		return super.createOutputStream(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#createOutputStream(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		if (parent != null) {
			return parent.createOutputStream(uri, options);
		}
		return super.createOutputStream(uri, options);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#delete(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public void delete(URI uri, Map<?, ?> options) throws IOException {
		if (parent != null) {
			parent.delete(uri, options);
		}
		super.delete(uri, options);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#contentDescription(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
		if (parent != null) {
			return parent.contentDescription(uri, options);
		}
		return super.contentDescription(uri, options);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#exists(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public boolean exists(URI uri, Map<?, ?> options) {
		if (parent != null) {
			return parent.exists(uri, options);
		}
		return super.exists(uri, options);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#getAttributes(org.eclipse.emf.common.util.URI,
	 *      java.util.Map)
	 */
	@Override
	public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
		if (parent != null) {
			return parent.getAttributes(uri, options);
		}
		return super.getAttributes(uri, options);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#setAttributes(org.eclipse.emf.common.util.URI,
	 *      java.util.Map, java.util.Map)
	 */
	@Override
	public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException {
		if (parent != null) {
			parent.setAttributes(uri, attributes, options);
		}
		super.setAttributes(uri, attributes, options);
	}

	/**
	 * Normalizes the given URI using the parent environment.
	 * 
	 * @param uri
	 *            The uri we are to normalize.
	 * @return The normalized form of <code>uri</code>.
	 */
	private URI normalizeWithParent(URI uri) {
		if (parent != null) {
			return parent.normalize(uri);
		}
		return uri;
	}

	/**
	 * This will be called if the parent {@link URIConverter} didn't know how to convert the given URI.
	 * 
	 * @param uri
	 *            The uri we are to normalize.
	 * @return The normalized form of <code>uri</code>.
	 */
	private URI dynamicNormalize(URI uri) {
		URI normalized = getURIMap().get(uri);
		if (normalized == null && EMFPlugin.IS_ECLIPSE_RUNNING) {
			BundleURLConverter conv = new BundleURLConverter(uri.toString());
			if (conv.resolveBundle() != null) {
				normalized = URI.createURI(conv.resolveAsPlatformPlugin());
			}
		}
		if (normalized == null
				&& (!IAcceleoConstants.EMTL_FILE_EXTENSION.equals(uri.fileExtension()) || !"file".equals(uri.scheme()))) { //$NON-NLS-1$
			normalized = super.normalize(uri);
		}
		if (normalized != null) {
			getURIMap().put(uri, normalized);
			return normalized;
		}

		String moduleName = uri.lastSegment();
		moduleName = moduleName.substring(0, moduleName.lastIndexOf('.'));
		Set<URI> candidateURIs = new CompactLinkedHashSet<URI>();

		// Search matching module in the current generation context
		Set<Module> candidateModules = searchCurrentModuleForCandidateMatches(moduleName);
		for (Module candidateModule : candidateModules) {
			if (candidateModule.eResource() != null) {
				candidateURIs.add(candidateModule.eResource().getURI());
			}
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
			BundleURLConverter conv = new BundleURLConverter(uri.toString());
			if (conv.resolveBundle() != null) {
				normalized = URI.createURI(conv.resolveAsPlatformPlugin());
			} else {
				String uriToString = uri.toString();
				if (uriToString.indexOf('#') > 0) {
					uriToString = uriToString.substring(0, uriToString.indexOf('#'));
				}
				String resolvedPath = AcceleoWorkspaceUtil.resolveInBundles(uriToString);
				if (resolvedPath != null) {
					normalized = URI.createURI(resolvedPath);
				}
			}
		}
		if (normalized == null) {
			normalized = super.normalize(uri);
		}
		if (!uri.equals(normalized)) {
			getURIMap().put(uri, normalized);
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
		Set<Module> candidates = new CompactLinkedHashSet<Module>();
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
		final Set<URI> candidates = new CompactLinkedHashSet<URI>();
		final List<ResourceSet> resourceSets = new ArrayList<ResourceSet>();
		for (Module module : parentEnvironment.getCurrentModules()) {
			if (module != null && module.eResource() != null) {
				final ResourceSet resourceSet = module.eResource().getResourceSet();
				if (!resourceSets.contains(resourceSet)) {
					resourceSets.add(resourceSet);
				}
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
