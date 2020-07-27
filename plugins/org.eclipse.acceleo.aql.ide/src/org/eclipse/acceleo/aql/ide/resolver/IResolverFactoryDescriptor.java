/*******************************************************************************
 *  Copyright (c) 2020 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.resolver;

/**
 * Describes how to get a {@link EclipseClassProvider}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IResolverFactoryDescriptor {

	/**
	 * Gets the {@link QualifiedNameResolverFactory}.
	 * 
	 * @return the {@link QualifiedNameResolverFactory}
	 */
	IQualifiedNameResolverFactory getFactory();

}
