/*******************************************************************************
 * Copyright (c) 2016, 2017  Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;

/**
 * Simple implementation of a Query Environment that delegates all functions to a set package provider and
 * lookup engines.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoQueryEnvironment extends QueryEnvironment {
	/** The package provider used by this environment. */
	private final EPackageProvider packageProvider;

	/** The lookup engine used by this environment. */
	private BasicLookupEngine lookupEngine;

	/**
	 * Initializes an environment given the package provider and lookup engine to use.
	 * 
	 * @param packageProvider
	 *            The package provider this environment will use.
	 * @param acceleoEnvironment
	 *            The environment keeping track of Acceleo's context.
	 */
	public AcceleoQueryEnvironment(EPackageProvider packageProvider,
			AcceleoEvaluationEnvironment acceleoEnvironment) {
		this.packageProvider = packageProvider;
		this.lookupEngine = new AcceleoLookupEngine(this, acceleoEnvironment);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.QueryEnvironment#getEPackageProvider()
	 */
	@Override
	public EPackageProvider getEPackageProvider() {
		return packageProvider;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.QueryEnvironment#getLookupEngine()
	 */
	@Override
	public BasicLookupEngine getLookupEngine() {
		return lookupEngine;
	}
}
