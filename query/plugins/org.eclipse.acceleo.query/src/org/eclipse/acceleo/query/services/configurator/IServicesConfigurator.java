/*******************************************************************************
 *  Copyright (c) 2017, 2023 Obeo. 
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

import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Configure for {@link IReadOnlyQueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IServicesConfigurator extends IOptionProvider {

	/**
	 * Gets the {@link Set} of {@link IService} for the given {@link IReadOnlyQueryEnvironment} and options.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param resourceSetForModels
	 *            the {@link ResourceSet} for model elements
	 * @param options
	 *            the {@link Map} of options
	 * @return the {@link Set} of {@link IService} for the given {@link IReadOnlyQueryEnvironment}
	 */
	Set<IService<?>> getServices(IReadOnlyQueryEnvironment queryEnvironment, ResourceSet resourceSetForModels,
			Map<String, String> options);

	/**
	 * Clears the services for the given {@link IReadOnlyQueryEnvironment}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param resourceSetForModels
	 *            the {@link ResourceSet} for model elements
	 */
	void cleanServices(IReadOnlyQueryEnvironment queryEnvironment, ResourceSet resourceSetForModels);

}
