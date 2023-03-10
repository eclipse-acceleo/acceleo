/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.namespace;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;

/**
 * Resolves qualified names.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IQualifiedNameResolver {

	/**
	 * Gets the qualified name from the given {@link URI}.
	 * 
	 * @param uri
	 *            the {@link URI}
	 * @return the qualified name from the given {@link URI} if any, <code>null</code> otherwise
	 */
	String getQualifiedName(URI uri);

	/**
	 * Gets the {@link URI} from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link URI} from the given qualified name if any, <code>null</code> otherwise
	 */
	URI getURI(String qualifiedName);

	/**
	 * Gets the source {@link URI} from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the source {@link URI} from the given qualified name if any, <code>null</code> otherwise
	 */
	URI getSourceURI(String qualifiedName);

	/**
	 * Gets the {@link ISourceLocation} for the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @return the {@link ISourceLocation} for the given {@link IService} if any, <code>null</code> otherwise
	 */
	ISourceLocation getSourceLocation(IService<?> service);

	/**
	 * Gets the {@link ISourceLocation} for the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link ISourceLocation} for the given qualified name if any, <code>null</code> otherwise
	 */
	ISourceLocation getSourceLocation(String qualifiedName);

	/**
	 * Clears the cache for the given {@link Set} of qualified names.
	 * 
	 * @param qualifiedNames
	 *            the {@link Set} of qualified names
	 */
	void clear(Set<String> qualifiedNames);

	/**
	 * Gets the {@link Object} corresponding to the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link Object} corresponding to the given qualified name if any, <code>null</code>
	 *         otherwise
	 */
	Object resolve(String qualifiedName);

	/**
	 * Register the given {@link Object} to the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @param object
	 *            the {@link Object}
	 */
	void register(String qualifiedName, Object object);

	/**
	 * Gets the qualified name from the given {@link Object} that have been {@link #resolve(String) resolved}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the qualified name from the given {@link Object} that have been {@link #resolve(String)
	 *         resolved} if any, <code>null</code> otherwise
	 */
	String getQualifiedName(Object object);

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
	 * Gets the super qualified name of the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the super qualified name of the given qualified name if any, <code>null</code> otherwise
	 */
	String getExtend(String qualifiedName);

	/**
	 * Gets the {@link List} of imported qualified name for the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link List} of imported qualified name for the given qualified name
	 */
	List<String> getImports(String qualifiedName);

	/**
	 * Gets the {@link List} of qualified name depending on the given qualified name. Opposite relation of
	 * {@link #getExtend(String)} and {@link #getImports(String)}.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link List} of qualified name depending on the given qualified name. Opposite relation of
	 *         {@link #getExtend(String)} and {@link #getImports(String)}
	 */
	List<String> getDependOn(String qualifiedName);

	/**
	 * Gets the {@link Set} of available qualified names.
	 * 
	 * @return the {@link Set} of available qualified names
	 */
	Set<String> getAvailableQualifiedNames();

	/**
	 * Gets the {@link InputStream} from the given resource name.
	 * 
	 * @param resourceName
	 *            the resource name
	 * @return the {@link InputStream} from the given resource name if any, <code>null</code> otherwise
	 */
	InputStream getInputStream(String resourceName);

	/**
	 * Gets the {@link Class} from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link Class} from the given qualified name if any, <code>null</code> otherwise
	 */
	Class<?> getClass(String qualifiedName);

	/**
	 * Adds the given {@link ILoader}.
	 * 
	 * @param loader
	 *            the {@link ILoader} to add
	 */
	void addLoader(ILoader loader);

	/**
	 * Removes the given {@link ILoader}.
	 * 
	 * @param loader
	 *            the {@link ILoader} to remove
	 */
	void removeLoader(ILoader loader);

	/**
	 * Clears all {@link ILoader}.
	 */
	void clearLoaders();

}
