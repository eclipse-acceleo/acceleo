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

/**
 * Describes a component capable of resolving a java class given its qualified name in the acceleo format
 * (e.g. qualified::path::to::service).
 * 
 * @author lgoubet
 */
public interface IJavaResolver {

	Class<?> resolveJavaService(String qualifiedName);

}
