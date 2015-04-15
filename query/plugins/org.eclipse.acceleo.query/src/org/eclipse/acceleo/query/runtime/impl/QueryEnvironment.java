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
package org.eclipse.acceleo.query.runtime.impl;

import java.util.logging.Logger;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.NumberServices;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

/**
 * The IQueryEnvironment implementation.
 * 
 * @author @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
public class QueryEnvironment implements IQueryEnvironment {

	/**
	 * The logger used to report problems and information.
	 */
	private Logger logger;

	/**
	 * The lookupEngine to be used for this evaluator.
	 */
	private BasicLookupEngine lookupEngine;

	/**
	 * The package provider that contains the referred packages.
	 */
	private EPackageProvider ePackageProvider;

	/**
	 * Creates a new {@link QueryEvaluationEngine} instance.
	 * 
	 * @param crossReferencer
	 *            a new {@link CrossReferencer} that will be used to resolve eReference requests in services
	 *            needed it.
	 */
	public QueryEnvironment(CrossReferenceProvider crossReferencer) {
		ePackageProvider = new EPackageProvider();
		lookupEngine = new BasicLookupEngine(this, crossReferencer);
		this.initStandardServices();
		this.logger = Logger.getLogger(this.getClass().getName());
	}

	/**
	 * Creates a new {@link QueryEvaluationEngine} instance.
	 * 
	 * @param crossReferencer
	 *            a new {@link CrossReferencer} that will be used to resolve eReference requests in services
	 *            needed it.
	 * @param providedLogger
	 *            the provided logger.
	 */
	public QueryEnvironment(CrossReferenceProvider crossReferencer, Logger providedLogger) {
		this(crossReferencer);
		this.logger = providedLogger;
	}

	@Override
	public ServiceRegistrationResult registerServicePackage(Class<?> services)
			throws InvalidAcceleoPackageException {
		return lookupEngine.registerServices(services);
	}

	@Override
	public boolean isRegiteredServicePackage(Class<?> cls) {
		return lookupEngine.isRegisteredService(cls);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#removeServicePackage(java.lang.Class)
	 */
	@Override
	public void removeServicePackage(Class<?> services) {
		lookupEngine.removeServices(services);
	}

	@Override
	public void registerEPackage(EPackage ePackage) {
		ePackageProvider.registerPackage(ePackage);
	}

	@Override
	public void removeEPackage(String nsPrefix) {
		ePackageProvider.removePackage(nsPrefix);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#registerCustomClassMapping(org.eclipse.emf.ecore.EClassifier,
	 *      java.lang.Class)
	 */
	@Override
	public void registerCustomClassMapping(EClassifier eClassifier, Class<?> cls) {
		ePackageProvider.registerCustomClassMapping(eClassifier, cls);
	}

	@Override
	public BasicLookupEngine getLookupEngine() {
		return lookupEngine;
	}

	@Override
	public EPackageProvider getEPackageProvider() {
		return ePackageProvider;
	}

	/**
	 * Initialize standard services.
	 */
	private void initStandardServices() {
		try {
			this.registerServicePackage(AnyServices.class);
			this.registerServicePackage(EObjectServices.class);
			this.registerServicePackage(ComparableServices.class);
			this.registerServicePackage(NumberServices.class);
			this.registerServicePackage(StringServices.class);
			this.registerServicePackage(BooleanServices.class);
			this.registerServicePackage(CollectionServices.class);
		} catch (InvalidAcceleoPackageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#getLogger()
	 */
	@Override
	public Logger getLogger() {
		return this.logger;
	}

}
