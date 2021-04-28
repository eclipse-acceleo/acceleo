/*******************************************************************************
 * Copyright (c) 2017, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql;

import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;

/**
 * Acceleo environment.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAcceleoEnvironment {

	/**
	 * Gets the {@link IQualifiedNameQueryEnvironment}.
	 * 
	 * @return the {@link IQualifiedNameQueryEnvironment}
	 */
	IQualifiedNameQueryEnvironment getQueryEnvironment();

	/**
	 * Gets the {@link GenerationResult}.
	 * 
	 * @return the {@link GenerationResult}
	 */
	GenerationResult getGenerationResult();

}
