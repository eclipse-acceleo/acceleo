/*******************************************************************************
 *  Copyright (c) 2020, 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.namespace;

/**
 * Describes how to get a {@link IQualifiedNameResolverFactory}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IResolverFactoryDescriptor {

	/**
	 * Gets the {@link IQualifiedNameResolverFactory}.
	 * 
	 * @return the {@link IQualifiedNameResolverFactory}
	 */
	IQualifiedNameResolverFactory getFactory();

}