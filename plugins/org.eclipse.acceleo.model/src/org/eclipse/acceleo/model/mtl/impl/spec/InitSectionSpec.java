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

import org.eclipse.acceleo.model.mtl.impl.InitSectionImpl;
import org.eclipse.ocl.ecore.Variable;

/**
 * Specializes the InitSection implementation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class InitSectionSpec extends InitSectionImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.InitSectionImpl#toString()
	 */
	@Override
	public String toString() {
		final List<Variable> variables = getVariable();

		final StringBuilder toString = new StringBuilder("{"); //$NON-NLS-1$
		for (int i = 0; i < variables.size(); i++) {
			toString.append(variables.get(i).toString());
			if (i + 1 < variables.size()) {
				toString.append(';').append(' ');
			}
		}
		toString.append('}');
		return toString.toString();
	}
}
