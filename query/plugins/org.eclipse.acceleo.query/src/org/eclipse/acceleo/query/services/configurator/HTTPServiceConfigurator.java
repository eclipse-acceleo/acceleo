/*******************************************************************************
 *  Copyright (c) 2019, 2024 Obeo. 
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.util.DataURIHandler;
import org.eclipse.acceleo.query.util.HttpURIHandler;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIHandler;

/**
 * Register the {@link HttpURIHandler} and {@link DataURIHandler}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class HTTPServiceConfigurator implements IServicesConfigurator {

	/**
	 * The {@link HttpURIHandler}.
	 */
	private final URIHandler httpHandler = new HttpURIHandler();

	/**
	 * The {@link DataURIHandler}.
	 */
	private final URIHandler dataHandler = new DataURIHandler();

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
		resourceSetForModels.getURIConverter().getURIHandlers().add(0, dataHandler);
		resourceSetForModels.getURIConverter().getURIHandlers().add(0, httpHandler);

		return Collections.emptySet();
	}

	@Override
	public void cleanServices(IReadOnlyQueryEnvironment queryEnvironment, ResourceSet resourceSetForModels) {
		resourceSetForModels.getURIConverter().getURIHandlers().remove(httpHandler);
		resourceSetForModels.getURIConverter().getURIHandlers().remove(dataHandler);
	}

}
