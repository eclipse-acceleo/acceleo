/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.validation.type;

/**
 * Type for validation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IType {

	/**
	 * Gets the type either a {@link Class} or an {@link org.eclipse.emf.ecore.EClass EClass}.
	 * 
	 * @return the type either a {@link Class} or an {@link org.eclipse.emf.ecore.EClass EClass}
	 */
	Object getType();

}
