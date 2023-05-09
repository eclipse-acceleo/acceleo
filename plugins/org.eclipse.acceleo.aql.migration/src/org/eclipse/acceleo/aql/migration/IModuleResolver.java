/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration;

import org.eclipse.acceleo.model.mtl.Module;

/**
 * Describes a component capable of resolving qualified name of modules given a module proxy.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public interface IModuleResolver {

	/**
	 * Returns the qualified name of the given module, proxy or not.
	 * 
	 * @param module
	 *            the context module
	 * @param refModule
	 *            the module to resolve
	 * @return the module qualified name
	 */
	String getQualifiedName(org.eclipse.acceleo.model.mtl.Module module, Module refModule);

}
