/*******************************************************************************
 *  Copyright (c) 2025 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.services.configurator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.util.AqlResolverURIHandler;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Register {@link AqlResolverURIHandler}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLServiceConfigurator implements IServicesConfigurator {

	/**
	 * The mapping of instances.
	 */
	private final Map<Object, AqlResolverURIHandler> handlers = new HashMap<>();

	@Override
	public List<String> getOptions() {
		return Collections.emptyList();
	}

	@Override
	public Map<String, String> getInitializedOptions(Map<String, String> options) {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, String> getInitializedOptions(Map<String, String> options, EObject eObj) {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, List<Diagnostic>> validate(IReadOnlyQueryEnvironment queryEnvironment,
			Map<String, String> options) {
		return Collections.emptyMap();
	}

	@Override
	public Set<IService<?>> getServices(IReadOnlyQueryEnvironment queryEnvironment,
			ResourceSet resourceSetForModels, Map<String, String> options, boolean forWorkspace) {
		if (queryEnvironment instanceof IQualifiedNameQueryEnvironment) {
			final IQualifiedNameResolver resolver = ((IQualifiedNameQueryEnvironment)queryEnvironment)
					.getLookupEngine().getResolver();
			final AqlResolverURIHandler handler = new AqlResolverURIHandler(resolver);
			resourceSetForModels.getURIConverter().getURIHandlers().add(0, handler);
			handlers.put(queryEnvironment, handler);
		}

		return Collections.emptySet();
	}

	@Override
	public void cleanServices(IReadOnlyQueryEnvironment queryEnvironment, ResourceSet resourceSetForModels) {
		handlers.remove(queryEnvironment);
	}

}
