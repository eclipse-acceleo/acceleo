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

import org.eclipse.acceleo.Module;

/**
 * Describes a component capable of resolving modules given their qualified name (e.g.
 * <code>qualified::path::to::module</code>).
 * 
 * @author lgoubet
 */
public interface IModuleResolver {

	Module resolveModule(String qualifiedName);

}
