/*******************************************************************************
 * Copyright (c) 2020  Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;

/**
 * Simple implementation of a Query Environment that delegates all functions to a set package provider and a
 * {@link QualifiedNameLookupEngine}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class QualifiedNameQueryEnvironment extends QueryEnvironment implements IQualifiedNameQueryEnvironment {

	/** The package provider used by this environment. */
	private final EPackageProvider packageProvider;

	/** The lookup engine used by this environment. */
	private QualifiedNameLookupEngine lookupEngine;

	/**
	 * Constructor.
	 * 
	 * @param resolver
	 *            The environment keeping track of Acceleo's context.
	 */
	public QualifiedNameQueryEnvironment(IQualifiedNameResolver resolver) {
		this.packageProvider = new EPackageProvider();
		this.lookupEngine = new QualifiedNameLookupEngine(this, resolver);
	}

	@Override
	public EPackageProvider getEPackageProvider() {
		return packageProvider;
	}

	@Override
	public QualifiedNameLookupEngine getLookupEngine() {
		return lookupEngine;
	}
}
