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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;

/**
 * {@link IServiceProvider} scanning its own public methods to create {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractServiceProvider implements IServiceProvider {

	/**
	 * {@link List} of {@link IService}.
	 */
	private List<IService> services;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IServiceProvider#getServices()
	 */
	@Override
	public List<IService> getServices(BasicLookupEngine lookup) throws InvalidAcceleoPackageException {
		try {
			if (services == null) {
				services = new ArrayList<IService>();
				final Method[] methods = this.getClass().getMethods();
				for (Method method : methods) {
					if (lookup.isCrossReferencerMethod(method)) {
						method.invoke(this, lookup.getCrossReferencer());
					} else if (lookup.registerMethod(method)) {
						final IService service = getService(method);
						if (service != null) {
							services.add(service);
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new InvalidAcceleoPackageException(BasicLookupEngine.INSTANTIATION_PROBLEM_MSG
					+ getClass().getCanonicalName(), e);
		} catch (IllegalArgumentException e) {
			throw new InvalidAcceleoPackageException(BasicLookupEngine.INSTANTIATION_PROBLEM_MSG
					+ getClass().getCanonicalName(), e);
		} catch (InvocationTargetException e) {
			throw new InvalidAcceleoPackageException(BasicLookupEngine.INSTANTIATION_PROBLEM_MSG
					+ getClass().getCanonicalName(), e);
		}

		return services;
	}

	/**
	 * Gets an {@link IService} from the given {@link Method}.
	 * 
	 * @param method
	 *            the {@link Method}
	 * @return the {@link IService} if any, <code>null</code> if no {@link IService} correspond to the given
	 *         {@link Method}
	 */
	protected abstract IService getService(Method method);

}
