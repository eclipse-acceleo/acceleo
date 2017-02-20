/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;

/**
 * Acceleo environment.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAcceleoEnvironment {
	/**
	 * Registers the given module against this environment.
	 * 
	 * @param qualifiedName
	 *            The qualified name of this module.
	 * @param module
	 *            The module to register.
	 */
	void registerModule(String qualifiedName, Module module);

	/**
	 * Tells if the given {@link org.eclipse.acceleo.Module} qualified name exists.
	 * 
	 * @param qualifiedName
	 *            the {@link org.eclipse.acceleo.Module} qualified name
	 * @return <code>true</code> if the given {@link org.eclipse.acceleo.Module} qualified name exists,
	 *         <code>false</code> otherwise
	 */
	boolean hasModule(String qualifiedName);

	/**
	 * Gets the {@link IQueryEnvironment}.
	 * 
	 * @return the {@link IQueryEnvironment}
	 */
	IQueryEnvironment getQueryEnvironment();

	/**
	 * Push the given module element atop the latest stack, or create a new stack for it if
	 * <code>newStackStartingModule</code> is not null.
	 * 
	 * @param element
	 *            The module element we're entering into.
	 * @param newStackStartingModule
	 *            If this is not null, we'll create a new stack with this module as starting point to push the
	 *            module element on. Otherwise we'll just push this element on the latest stack.
	 */
	void pushStack(ModuleElement element, Module newStackStartingModule);

	/**
	 * Removes a module element from the latest call stack, popping that stack out as well if it's empty
	 * afterwards.
	 * 
	 * @param element
	 *            The module element we're exiting out of.
	 */
	void popStack(ModuleElement element);
}
