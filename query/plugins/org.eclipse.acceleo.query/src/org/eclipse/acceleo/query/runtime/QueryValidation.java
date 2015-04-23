/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;

/**
 * Static utility methods pertaining to the validation of Acceleo queries.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public final class QueryValidation {

	private QueryValidation() {

	}

	/**
	 * Create a new {@link IQueryValidationEngine} suitable for validating and analyzing parsed expressions.
	 * 
	 * @param environment
	 *            the environment to use.
	 * @return a new {@link IQueryValidationEngine} suitable for validating and analyzing parsed expressions.
	 */
	public static IQueryValidationEngine newEngine(IQueryEnvironment environment) {
		return new QueryValidationEngine(environment);
	}

}
