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

import java.util.List;

import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.acceleo.model.mtl.impl.QueryImpl;
import org.eclipse.ocl.ecore.Variable;

/**
 * Specializes the Query implementation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class QuerySpec extends QueryImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl#toString()
	 */
	@Override
	public String toString() {
		final VisibilityKind visibilityKind = getVisibility();
		final List<Variable> params = getParameter();

		final StringBuilder toString = new StringBuilder("query"); //$NON-NLS-1$
		toString.append(' ');
		toString.append(visibilityKind.getLiteral());
		toString.append(' ');
		toString.append(getName());
		toString.append('(');
		for (int i = 0; i < params.size(); i++) {
			toString.append(params.get(i).toString());
			if (i + 1 < params.size()) {
				toString.append(',');
			}
		}
		toString.append(')').append(' ').append(':').append(' ');
		toString.append(getType().getName());
		return toString.toString();
	}
}
