/*******************************************************************************
 *  Copyright (c) 2017, 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.services.configurator;

/**
 * Basic implementation of {@link IServicesConfiguratorDescriptor}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ServicesConfiguratorDescriptor implements IServicesConfiguratorDescriptor {

	/**
	 * The language name
	 */
	private final String language;

	/**
	 * The {@link IServicesConfigurator}.
	 */
	private final IServicesConfigurator configurator;

	/**
	 * Constructor.
	 * 
	 * @param language
	 *            the language name
	 * @param configurator
	 *            the {@link IServicesConfigurator}
	 */
	public ServicesConfiguratorDescriptor(String language, IServicesConfigurator configurator) {
		this.language = language;
		this.configurator = configurator;
	}

	@Override
	public IServicesConfigurator getServicesConfigurator() {
		return configurator;
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public int hashCode() {
		return configurator.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ServicesConfiguratorDescriptor
				&& ((ServicesConfiguratorDescriptor)obj).configurator == configurator;
	}

}
