/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.configurator;

import java.util.Map;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Configure {@link ResourceSet}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IResourceSetConfigurator extends IOptionProvider {

	/**
	 * Create a new resourceSet which would need specific initialization for loading the models according to
	 * the given options.
	 * 
	 * @param context
	 *            the {@link Object} context used in {@link #cleanResourceSetForModels(Object)}
	 * @param options
	 *            the {@link Map} of options
	 * @return the created {@link ResourceSet} if any, <code>null</code> otherwise
	 * @see #cleanResourceSetForModels(Object)
	 */
	ResourceSet createResourceSetForModels(Object context, Map<String, String> options);

	/**
	 * Cleans the {@link #createResourceSetForModels(Object, Map) created} {@link ResourceSet} for the given
	 * {@link IReadOnlyQueryEnvironment}.
	 * 
	 * @param context
	 *            the {@link Object} context
	 */
	void cleanResourceSetForModels(Object context);

}
