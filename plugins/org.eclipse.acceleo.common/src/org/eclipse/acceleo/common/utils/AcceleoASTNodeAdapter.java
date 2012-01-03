/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.utils;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * Instances of this adapter will be attached to all ASTNodes so as to keep track of the line (in the ".mtl"
 * module file) on which they appear.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public class AcceleoASTNodeAdapter extends AdapterImpl {
	/** Actual line number on which the ASTNode appeared. */
	private final int line;

	/**
	 * Constructs an instance given its line number.
	 * 
	 * @param lineNumber
	 *            The line on which the ASTNode this will be attached to is located.
	 */
	public AcceleoASTNodeAdapter(int lineNumber) {
		line = lineNumber;
	}

	/**
	 * Returns the line number of the ASTNode this instance is attached to.
	 * 
	 * @return The line number of the ASTNode this instance is attached to.
	 */
	public int getLine() {
		return line;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(Object type) {
		return type == AcceleoASTNodeAdapter.class;
	}
}
