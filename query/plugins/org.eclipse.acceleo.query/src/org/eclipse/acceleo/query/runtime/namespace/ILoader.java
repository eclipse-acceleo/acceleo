/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.namespace;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;

/**
 * Loads an {@link Object} from a qualified name.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface ILoader {

	/**
	 * Gets the resource name from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the resource name from the given qualified name
	 */
	String resourceName(String qualifiedName);

	/**
	 * Gets the source resource name from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the source resource name from the given qualified name
	 */
	String sourceResourceName(String qualifiedName);

	/**
	 * Get the qualified name form the given resource name.
	 * 
	 * @param resourceName
	 *            the resource name
	 * @return the qualified name form the given resource name if any, <code>null</code> otherwise
	 */
	String qualifiedName(String resourceName);

	/**
	 * Loads the {@link Object} from the given qualified name.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link Object} from the given qualified name
	 */
	Object load(IQualifiedNameResolver resolver, String qualifiedName);

	/**
	 * Tells if the given {@link Object} can be handled by this {@link IServiceResolver}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return <code>true</code> if the given {@link Object} can be handled by this {@link IServiceResolver},
	 *         <code>false</code> otherwise
	 */
	boolean canHandle(Object object);

	/**
	 * Gets the {@link List} of {@link IService} from the given {@link Object}.
	 * 
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 * @param object
	 *            the {@link Object}
	 * @param contextQualifiedName
	 *            the context qualified name
	 * @return the {@link List} of {@link IService} from the given {@link Object}
	 */
	Set<IService<?>> getServices(IQualifiedNameLookupEngine lookupEngine, Object object,
			String contextQualifiedName);

	/**
	 * Gets the {@link List} of qualified names from the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the {@link List} of qualified names from the given {@link Object}
	 */
	List<String> getImports(Object object);

	/**
	 * Gets the super name space of the given {@link Object}.
	 * 
	 * @param object
	 *            the Object
	 * @return the super name space of the given {@link Object} if any, <code>null</code> otherwise
	 */
	String getExtends(Object object);

	/**
	 * Gets the {@link ISourceLocation} for the given {@link IService}.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param service
	 *            the {@link IService}
	 * @return the {@link ISourceLocation} for the given {@link IService} if any, <code>null</code> otherwise
	 */
	ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, IService<?> service);

	/**
	 * Gets the {@link ISourceLocation} for the given qualified name.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link ISourceLocation} for the given qualified name if any, <code>null</code> otherwise
	 */
	ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, String qualifiedName);

}
