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

import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.acceleo.model.mtl.impl.FileBlockImpl;
import org.eclipse.ocl.parser.ValidationVisitor;
import org.eclipse.ocl.util.ToStringVisitor;

/**
 * Specializes the FileBlock implementation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class FileBlockSpec extends FileBlockImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.FileBlockImpl#accept(org.eclipse.ocl.utilities.Visitor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object, U extends org.eclipse.ocl.utilities.Visitor<T, ?, ?, ?, ?, ?, ?, ?, ?, ?>> T accept(
			U v) {
		T result;
		if (v instanceof ToStringVisitor<?, ?, ?, ?, ?, ?, ?, ?, ?>) {
			result = (T)toString();
		} else if (v instanceof ValidationVisitor<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) {
			result = (T)Boolean.TRUE;
		} else {
			result = super.accept(v);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.FileBlockImpl#toString()
	 */
	@Override
	public String toString() {
		final OpenModeKind append = getOpenMode();
		final InitSection initSection = getInit();

		final StringBuilder toString = new StringBuilder("file"); //$NON-NLS-1$
		toString.append('(');
		if (getFileUrl() != null) {
			toString.append(getFileUrl().toString());
			toString.append(',').append(' ');
		}
		if (append == OpenModeKind.APPEND) {
			toString.append("true"); //$NON-NLS-1$
		} else {
			toString.append("false"); //$NON-NLS-1$
		}
		if (getCharset() != null) {
			toString.append(',').append(' ');
			toString.append(getCharset().toString());
		}
		toString.append(')');
		if (initSection != null) {
			toString.append(' ');
			toString.append(initSection.toString());
		}
		return toString.toString();
	}
}
