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
package org.eclipse.acceleo.engine.internal.utils;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * Instances of this adapter will be attached to {@link org.eclipse.ocl.ecore.OperationCallExp} when they
 * correspond to a "+" operator for which at least one of the operands is a String.
 * <p>
 * This step is necessary so as to bypass OCL limitations : overriding "+" forces us to redefine the behavior
 * of this operator in <em>all</em> cases, whereas we only wish to override this operator for a limited set of
 * delimited operands.
 * </p>
 * <p>
 * Note that the simple fact this adapter is attached to an operation call will be assumed to mean the
 * operator is overriden. No more verification steps will be taken.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public class AcceleoOverrideAdapter extends AdapterImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(Object type) {
		return type == AcceleoOverrideAdapter.class;
	}
}
