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

	/**
	 * Tells if the given {@link IType} can be assigned to this {@link IType}.
	 * 
	 * @param type
	 *            the {@link IType}
	 * @return <code>true</code> if the given {@link IType} can be assigned to this {@link IType},
	 *         <code>false</code> otherwise
	 */
	boolean isAssignableFrom(IType type);

}
