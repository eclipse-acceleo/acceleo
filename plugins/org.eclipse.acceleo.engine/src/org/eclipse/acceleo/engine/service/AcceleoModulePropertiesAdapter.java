/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * This adapter contains a set of properties for the evaluation of a module.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoModulePropertiesAdapter extends AdapterImpl {
	/**
	 * The properties.
	 */
	private Set<String> properties = new LinkedHashSet<String>();

	/**
	 * The constructor.
	 */
	public AcceleoModulePropertiesAdapter() {
	}

	/**
	 * Adds a new property to the set of properties.
	 * 
	 * @param property
	 *            The new property.
	 */
	public void addProperty(String property) {
		this.properties.add(property);
	}

	/**
	 * Returns the properties of the attached module.
	 * 
	 * @return The properties of the attached module.
	 */
	public Set<String> getProperties() {
		return properties;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(Object type) {
		return type.equals(AcceleoModulePropertiesAdapter.class);
	}
}
