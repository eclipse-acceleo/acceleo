/*******************************************************************************
 *  Copyright (c) 2023 Obeo. 
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
 * Basic implementation of {@link IResourceSetConfiguratorDescriptor}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ResourceSetConfiguratorDescriptor implements IResourceSetConfiguratorDescriptor {

	/**
	 * The {@link IResourceSetConfigurator}.
	 */
	private final IResourceSetConfigurator configurator;

	/**
	 * Constructor.
	 * 
	 * @param configurator
	 *            the {@link IResourceSetConfigurator}
	 */
	public ResourceSetConfiguratorDescriptor(IResourceSetConfigurator configurator) {
		this.configurator = configurator;
	}

	@Override
	public IResourceSetConfigurator getResourceSetConfigurator() {
		return configurator;
	}

	@Override
	public int hashCode() {
		return configurator.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ResourceSetConfiguratorDescriptor
				&& ((ResourceSetConfiguratorDescriptor)obj).configurator == configurator;
	}

}
