/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;

/**
 * Adds qualified name validation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QualifiedNameValidationServices extends ValidationServices {

	/**
	 * Constructor.
	 * 
	 * @param queryEnv
	 *            the {@link IQualifiedNameQueryEnvironment}
	 */
	public QualifiedNameValidationServices(IQualifiedNameQueryEnvironment queryEnv) {
		super(queryEnv);
	}

	@Override
	public IQualifiedNameQueryEnvironment getQueryEnvironment() {
		return (IQualifiedNameQueryEnvironment)super.getQueryEnvironment();
	}

}
