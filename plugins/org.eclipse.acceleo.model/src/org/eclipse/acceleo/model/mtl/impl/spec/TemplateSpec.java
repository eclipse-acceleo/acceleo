/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
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

import org.eclipse.acceleo.model.mtl.impl.TemplateImpl;
import org.eclipse.ocl.ecore.Variable;

/**
 * Specializes the template implementation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TemplateSpec extends TemplateImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.TemplateImpl#toString()
	 */
	@Override
	public String toString() {
		final List<Variable> params = getParameter();

		final StringBuilder toString = new StringBuilder(getName());
		toString.append('(');
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i).getType() != null) {
				toString.append(params.get(i).getType().getName());
			} else {
				toString.append(""); //$NON-NLS-1$
			}
			if (i + 1 < params.size()) {
				toString.append(',');
			}
		}
		toString.append(')');

		return toString.toString();
	}
}
