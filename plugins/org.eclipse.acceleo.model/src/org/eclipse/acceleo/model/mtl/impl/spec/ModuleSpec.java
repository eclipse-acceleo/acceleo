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

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.acceleo.model.mtl.impl.ModuleImpl;

/**
 * Specializes the Module implementation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ModuleSpec extends ModuleImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.impl.EPackageImpl#toString()
	 */
	@Override
	public String toString() {
		final List<Module> extended = getExtends();
		final List<TypedModel> metamodels = getInput();

		final StringBuilder toString = new StringBuilder("module"); //$NON-NLS-1$
		toString.append(' ');
		toString.append(getName());
		toString.append('(');
		for (int i = 0; i < metamodels.size(); i++) {
			toString.append(metamodels.get(i).toString());
			if (i + 1 < metamodels.size()) {
				toString.append(',').append(' ');
			}
		}
		toString.append(')');
		if (extended != null && extended.size() > 0) {
			toString.append(' ');
			toString.append("extends"); //$NON-NLS-1$
			toString.append(' ');
			for (int i = 0; i < extended.size(); i++) {
				toString.append(extended.get(i).toString());
				if (i + 1 < extended.size()) {
					toString.append(',').append(' ');
				}
			}
		}
		return toString.toString();
	}
}
