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

import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.emf.common.util.Diagnostic;

/**
 * Provides options.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IOptionProvider {

	/**
	 * Gets the {@link List} of options managed by this provider.
	 * 
	 * @return the {@link List} of options managed by this provider
	 */
	List<String> getOptions();

	/**
	 * Gets the {@link Map} of initialized options.
	 * 
	 * @param options
	 *            the {@link Map} of existing options.
	 * @return the {@link Map} of initialized options
	 */
	Map<String, String> getInitializedOptions(Map<String, String> options);

	/**
	 * Validates the given options.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param options
	 *            the {@link Map} of options
	 * @return the {@link Map} of option name to its {@link Diagnostic}
	 */
	Map<String, List<Diagnostic>> validate(IReadOnlyQueryEnvironment queryEnvironment,
			Map<String, String> options);

}
