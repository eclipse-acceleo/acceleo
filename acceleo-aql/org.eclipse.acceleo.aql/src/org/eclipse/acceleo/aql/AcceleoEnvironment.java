/*******************************************************************************
 * Copyright (c) 20016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;

/**
 * The Acceleo environment.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoEnvironment implements IAcceleoEnvironment {

	/**
	 * The {@link IROQueryEnvironment}.
	 */
	private final IQueryEnvironment queryEnvironment;

	/**
	 * Constructor.
	 * 
	 * @param environment
	 *            the {@link IQueryEnvironment}
	 */
	public AcceleoEnvironment(IQueryEnvironment environment) {
		this.queryEnvironment = environment;
	}

	@Override
	public boolean hasModule(String qualifiedName) {
		// TODO Auto-generated method stub
		return !qualifiedName.contains("notExisting");
	}

	@Override
	public IQueryEnvironment getQueryEnvironment() {
		return queryEnvironment;
	}

}
