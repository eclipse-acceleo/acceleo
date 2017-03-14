/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.parser;

import java.util.List;

import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.Module;

/**
 * AcceleoAST result.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoAstResult {

	/**
	 * The {@link AcceleoParser#parse(String) parsed} {@link Module}.
	 */
	private final Module module;

	/**
	 * The {@link List} of {@link Error}.
	 */
	private final List<Error> errors;

	/**
	 * Constructor.
	 * 
	 * @param module
	 *            the {@link AcceleoParser#parse(String) parsed} {@link Module}
	 * @param errors
	 *            the {@link List} of {@link Error}
	 */
	public AcceleoAstResult(Module module, List<Error> errors) {
		this.module = module;
		this.errors = errors;
	}

	/**
	 * Gets the {@link AcceleoParser#parse(String) parsed} {@link Module}.
	 * 
	 * @return the {@link AcceleoParser#parse(String) parsed} {@link Module}
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * Gets the {@link List} of {@link Error}.
	 * 
	 * @return the {@link List} of {@link Error}
	 */
	public List<Error> getErrors() {
		return errors;
	}

}
