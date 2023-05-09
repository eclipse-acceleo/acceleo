/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

/**
 * Provides root {@link EObject} to navigate for all instances search.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 4.0.0
 */
public interface IRootEObjectProvider {

	/**
	 * Gets the {@link Set} of root {@link EObject} to navigate for all instances search.
	 * 
	 * @return the {@link Set} of root {@link EObject} to navigate for all instances search
	 */
	Set<EObject> getRoots();

}
