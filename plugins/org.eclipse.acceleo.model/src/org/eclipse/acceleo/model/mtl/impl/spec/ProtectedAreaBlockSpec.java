/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.impl.spec;

import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.impl.ProtectedAreaBlockImpl;

/**
 * Specializes the ProtectedAreaBlock implementation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ProtectedAreaBlockSpec extends ProtectedAreaBlockImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.impl.OCLExpressionImpl#toString()
	 */
	@Override
	public String toString() {
		final InitSection initSection = getInit();

		final StringBuilder toString = new StringBuilder("protected"); //$NON-NLS-1$
		toString.append(' ').append('(');
		toString.append(getMarker().toString());
		toString.append(')');
		if (initSection != null) {
			toString.append(' ');
			toString.append(initSection.toString());
		}
		return toString.toString();
	}
}
