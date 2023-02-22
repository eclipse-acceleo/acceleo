/*******************************************************************************
 * Copyright (c) 2008, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.provider;

import org.eclipse.emf.common.notify.Adapter;

/**
 * Specializes the ProfilerItemProviderAdapterFactory implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProfilerItemProviderAdapterFactorySpec extends ProfilerItemProviderAdapterFactory {

	/**
	 * Constructor.
	 */
	public ProfilerItemProviderAdapterFactorySpec() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.provider.ProfilerItemProviderAdapterFactory#createProfileEntryAdapter()
	 */
	@Override
	public Adapter createProfileEntryAdapter() {
		if (profileEntryItemProvider == null) {
			profileEntryItemProvider = new ProfileEntryItemProviderSpec(this);
		}

		return profileEntryItemProvider;
	}

}
