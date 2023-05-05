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

import java.util.List;

import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.namespace.CallStack;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Lookups {@link IService} in qualified name spaces.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IQualifiedNameLookupEngine extends ILookupEngine {

	/**
	 * Pushes the given qualified name as the current context from an extend.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 */
	void pushContext(String qualifiedName);

	/**
	 * Pushes the given qualified name as the current context from an import.
	 * 
	 * @param importQualifiedName
	 *            the qualified name of the import
	 * @param serviceContextQualifiedName
	 *            the qualified name of the context of the service
	 */
	void pushImportsContext(String importQualifiedName, String serviceContextQualifiedName);

	/**
	 * Pops the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 */
	void popContext(String qualifiedName);

	/**
	 * Clears the cache of the given context.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 */
	void clearContext(String qualifiedName);

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
	 * Gets the current {@link CallStack}.
	 * 
	 * @return the current {@link CallStack} if any, <code>null</code> otherwise
	 */
	CallStack getCurrentContext();

	/**
	 * Gets the {@link IReadOnlyQueryEnvironment}.
	 * 
	 * @return the {@link IReadOnlyQueryEnvironment}
	 */
	IReadOnlyQueryEnvironment getQueryEnvironment();

	/**
	 * Gets the {@link IQualifiedNameResolver}.
	 * 
	 * @return the {@link IQualifiedNameResolver}
	 */
	IQualifiedNameResolver getResolver();

	/**
	 * Gets the {@link IService} {@link #lookup(String, IType[]) lookedup} if the {@link #getExtend(String)
	 * extends} of the {@link #getCurrentContext() current context}.
	 * 
	 * @param name
	 *            the name of the service to retrieve.
	 * @param argumentTypes
	 *            {@link IType} of the arguments to best match.
	 * @return t the {@link IService} {@link #lookup(String, IType[]) lookedup} if the
	 *         {@link #getExtend(String) extends} of the {@link #getCurrentContext() current context} if any,
	 *         <code>null</code> otherwise
	 */
	IService<?> superServiceLookup(String name, IType[] argumentTypes);

}
