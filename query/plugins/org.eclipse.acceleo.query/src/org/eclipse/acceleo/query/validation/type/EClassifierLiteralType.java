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

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.emf.ecore.EClassifier;

/**
 * {@link EClassifier} literal validation type.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class EClassifierLiteralType extends EClassifierType {

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param type
	 *            the {@link EClassifier}
	 */
	public EClassifierLiteralType(IReadOnlyQueryEnvironment queryEnvironment, EClassifier type) {
		super(queryEnvironment, type);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EClassifierLiteral=" + getType().getName();
	}

}
