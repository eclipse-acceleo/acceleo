/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
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

import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.acceleo.model.mtl.impl.TemplateImpl;
import org.eclipse.ocl.ecore.OCLExpression;
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
		final VisibilityKind visibilityKind = getVisibility();
		final List<Variable> params = getParameter();
		final OCLExpression guardExp = getGuard();
		final List<Template> overridden = getOverrides();
		final InitSection initSection = getInit();

		final StringBuilder toString = new StringBuilder("template"); //$NON-NLS-1$
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
		toString.append(')');
		if (guardExp != null) {
			toString.append(' ').append('?').append(' ').append('(');
			toString.append(guardExp.toString());
			toString.append(')');
		}
		if (overridden != null && overridden.size() > 0) {
			toString.append(' ');
			toString.append("overrides"); //$NON-NLS-1$
			toString.append(' ');
			for (int i = 0; i < overridden.size(); i++) {
				toString.append(overridden.get(i).toString());
				if (i + 1 < overridden.size()) {
					toString.append(',');
				}
			}
		}
		if (post != null) {
			toString.append(' ');
			toString.append("post"); //$NON-NLS-1$
			toString.append(" ("); //$NON-NLS-1$
			toString.append(post.toString());
			toString.append(')');
		}
		if (initSection != null) {
			toString.append(' ');
			toString.append(initSection.toString());
		}
		return toString.toString();
	}
}
