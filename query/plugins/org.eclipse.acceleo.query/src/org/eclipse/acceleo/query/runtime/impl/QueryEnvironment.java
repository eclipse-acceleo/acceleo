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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

/**
 * The IQueryEnvironment implementation.
 * 
 * @author @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
public class QueryEnvironment implements IQueryEnvironment {
	/**
	 * The lookupEngine to be used for this evaluator.
	 */
	private BasicLookupEngine lookupEngine;

	/**
	 * The package provider that contains the referred packages.
	 */
	private EPackageProvider ePackageProvider;

	/**
	 * The {@link List} of {@link IQueryEnvironmentListener}.
	 */
	private List<IQueryEnvironmentListener> listeners = new ArrayList<IQueryEnvironmentListener>();

	/**
	 * Creates a new {@link QueryEvaluationEngine} instance.
	 * 
	 * @since 4.0.0
	 */
	public QueryEnvironment() {
		ePackageProvider = new EPackageProvider();
		lookupEngine = new BasicLookupEngine(this);
	}

	@Override
	public ServiceRegistrationResult registerServicePackage(Class<?> services)
			throws InvalidAcceleoPackageException {
		final ServiceRegistrationResult result = lookupEngine.registerServices(services);

		if (!result.getRegistered().isEmpty()) {
			for (IQueryEnvironmentListener listener : getListeners()) {
				listener.servicePackageRegistered(result, services);
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#registerServiceInstance(java.lang.Object)
	 */
	@Override
	public ServiceRegistrationResult registerServiceInstance(Object instance)
			throws InvalidAcceleoPackageException {
		final ServiceRegistrationResult result = lookupEngine.registerServiceInstance(instance);

		if (!result.getRegistered().isEmpty()) {
			for (IQueryEnvironmentListener listener : getListeners()) {
				listener.servicePackageRegistered(result, instance.getClass());
			}
		}

		return result;
	}

	@Override
	public boolean isRegisteredServicePackage(Class<?> cls) {
		return lookupEngine.isRegisteredService(cls);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#removeServicePackage(java.lang.Class)
	 */
	@Override
	public void removeServicePackage(Class<?> services) {
		final Class<?> removedClass = lookupEngine.removeServices(services);
		if (removedClass != null) {
			for (IQueryEnvironmentListener listener : getListeners()) {
				listener.servicePackageRemoved(removedClass);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#registerEPackage(org.eclipse.emf.ecore.EPackage)
	 */
	@Override
	public void registerEPackage(EPackage ePackage) {
		final EPackage registeredEPackage = ePackageProvider.registerPackage(ePackage);
		if (registeredEPackage != null) {
			for (IQueryEnvironmentListener listener : getListeners()) {
				listener.ePackageRegistered(ePackage);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#removeEPackage(java.lang.String)
	 */
	@Override
	public void removeEPackage(String name) {
		final Collection<EPackage> ePackages = ePackageProvider.removePackage(name);
		for (EPackage ePackage : ePackages) {
			for (IQueryEnvironmentListener listener : getListeners()) {
				listener.ePackageRemoved(ePackage);
			}
		}
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
		for (IQueryEnvironmentListener listener : getListeners()) {
			listener.customClassMappingRegistered(eClassifier, cls);
		}
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#addQueryEnvironmentListener(org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener)
	 */
	@Override
	public void addQueryEnvironmentListener(IQueryEnvironmentListener listener) {
		if (listener != null) {
			synchronized(listeners) {
				listeners.add(listener);
			}
		} else {
			throw new IllegalArgumentException("IQueryEnvironmentListener can't be null");
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironment#removeQueryEnvironmentListener(org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener)
	 */
	@Override
	public void removeQueryEnvironmentListener(IQueryEnvironmentListener listener) {
		if (listener != null) {
			synchronized(listeners) {
				listeners.remove(listener);
			}
		}
	}

	/**
	 * Gets {@link IQueryEnvironment#addQueryEnvironmentListener(IQueryEnvironmentListener) added}
	 * {@link IQueryEnvironmentListener}.
	 * 
	 * @return {@link IQueryEnvironment#addQueryEnvironmentListener(IQueryEnvironmentListener) added}
	 *         {@link IQueryEnvironmentListener}
	 */
	protected List<IQueryEnvironmentListener> getListeners() {
		final List<IQueryEnvironmentListener> result;

		synchronized(listeners) {
			result = new ArrayList<IQueryEnvironmentListener>(listeners);
		}

		return result;
	}

}
