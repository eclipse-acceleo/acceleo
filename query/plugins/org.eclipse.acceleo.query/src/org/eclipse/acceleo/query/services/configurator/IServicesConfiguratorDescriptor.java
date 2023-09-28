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
 * Describes how to get a {@link IServicesConfigurator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IServicesConfiguratorDescriptor {

	/**
	 * Gets the {@link IServicesConfigurator}.
	 * 
	 * @return the {@link IServicesConfigurator}
	 */
	IServicesConfigurator getServicesConfigurator();

	/**
	 * Gets the language name.
	 * 
	 * @return the language name
	 */
	String getLanguage();

}
