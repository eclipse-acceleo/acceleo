/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.resolver;

import java.io.IOException;

import org.eclipse.acceleo.Module;

/**
 * Describes a component capable of resolving modules given their qualified name (e.g.
 * <code>qualified::path::to::module</code>).
 * 
 * @author lgoubet
 */
public interface IQualifiedNameResolver {

	/**
	 * Resolves the {@link Module} with the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name (e.g. <code>qualified::path::to::module</code>)
	 * @return the {@link Module} with the given qualified name if any, <code>null</code> otherwise
	 * @throws IOException
	 *             if the module can't be loaded
	 */
	Module resolveModule(String qualifiedName) throws IOException;

	/**
	 * Resolves the {@link Class} with the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name (e.g. <code>qualified::path::to::module</code>)
	 * @return the {@link Class} with the given qualified name if any, <code>null</code> otherwise
	 * @throws ClassNotFoundException
	 *             if the class can't be found
	 */
	Class<?> resolveClass(String qualifiedName) throws ClassNotFoundException;

}
