/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;

/**
 * Abstract implementation of the language services.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractLanguageServices {

	/**
	 * Log message used when a called service was not found.
	 */
	protected static final String SERVICE_NOT_FOUND = "Couldn't find the '%s' service";

	/**
	 * Log message used when a requested variable was not found.
	 */
	protected static final String VARIABLE_NOT_FOUND = "Couldn't find the '%s' variable";

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	protected final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Creates a new service instance given a {@link IReadOnlyQueryEnvironment} and logging flag.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment} to use
	 */
	public AbstractLanguageServices(IReadOnlyQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	/**
	 * Gets the {@link IReadOnlyQueryEnvironment}.
	 * 
	 * @return the queryEnvironment the {@link IReadOnlyQueryEnvironment}
	 */
	public IReadOnlyQueryEnvironment getQueryEnvironment() {
		return queryEnvironment;
	}

}
