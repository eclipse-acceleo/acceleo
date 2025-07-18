/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace.workspace;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspace;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IWorkspaceRegistry;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class WorkspaceRegistry extends EPackageRegistryImpl implements IWorkspaceRegistry {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -6908270616586400928L;

	/**
	 * The {@link IQueryWorkspace} this registry is attached to.
	 */
	private final IQueryWorkspace<?> workspace;

	/**
	 * The {@link Set} of qualified name corresponding to an {@link EPackage}.
	 */
	private final Set<String> ePkgQualifiedNames = new HashSet<>();

	/**
	 * The mapping from qualified name to the set of dependencies {@link EPackage#getNsURI() nsURI}.
	 */
	private final Map<String, Set<String>> dependencies = new HashMap<>();

	/**
	 * The mapping from the {@link EPackage#getNsURI() nsURI} to the {@link Set} of qualified names that
	 * depend on it.
	 */
	private final Map<String, Set<String>> dependsOn = new HashMap<>();

	public WorkspaceRegistry(IQueryWorkspace<?> workspace) {
		super(EPackage.Registry.INSTANCE);
		this.workspace = workspace;
	}

	@Override
	public void register(Class<? extends EPackage> cls, String qualifiedName) {
		try {
			final String nsURI = (String)((Class<?>)cls).getDeclaredField("eNS_URI").get(null);
			final Object registered = EPackage.Registry.INSTANCE.remove(nsURI);
			try {
				final EPackage ePkg = (EPackage)((Class<?>)cls).getDeclaredField("eINSTANCE").get(null);
				put(nsURI, ePkg);
				ePkgQualifiedNames.add(qualifiedName);
				workspace.changeEPackage(nsURI);
			} finally {
				// restore the global registry
				EPackage.Registry.INSTANCE.put(nsURI, registered);
			}
		} catch (Exception e) {
			// a lot of different things can go wrong here
			e.printStackTrace();
		}
	}

	@Override
	public void register(URI uri, String qualifiedName) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setDependencies(String qualifiedName, Set<String> nsURIs) {
		final Set<String> oldDepdencyNsURIs = dependencies.remove(qualifiedName);
		if (oldDepdencyNsURIs != null) {
			for (String oldDepdencyNsURI : oldDepdencyNsURIs) {
				final Set<String> qualifiedNames = dependsOn.get(oldDepdencyNsURI);
				if (qualifiedNames != null && qualifiedNames.remove(qualifiedName) && qualifiedNames
						.isEmpty()) {
					dependsOn.remove(oldDepdencyNsURI);
				}
			}
		}
		if (!nsURIs.isEmpty()) {
			dependencies.put(qualifiedName, nsURIs);
			for (String nsURI : nsURIs) {
				dependsOn.computeIfAbsent(nsURI, id -> new HashSet<>()).add(qualifiedName);
			}
		}
	}

	@Override
	public Set<String> getDependsOn(String nsURI) {
		return dependsOn.getOrDefault(nsURI, Collections.emptySet());
	}

	@Override
	public Set<String> keySet() {
		final Set<String> res = new LinkedHashSet<>(super.keySet());

		res.addAll(delegateRegistry.keySet());

		return res;
	}

	@Override
	public boolean isEPackage(String qualifiedName) {
		return ePkgQualifiedNames.contains(qualifiedName);
	}

}
