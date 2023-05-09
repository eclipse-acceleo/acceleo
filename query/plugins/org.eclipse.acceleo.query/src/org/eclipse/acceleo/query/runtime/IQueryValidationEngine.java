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
package org.eclipse.acceleo.query.runtime;

import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Interface to be implemented by query validation engine implementations.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IQueryValidationEngine extends IQueryEngine {
	/**
	 * Validates an expression given a validation context made of variable-type mappings.
	 * 
	 * @param expression
	 *            the expression to validate.
	 * @param variableTypes
	 *            the set of defined variables.
	 * @return a {@link IValidationResult}.
	 */
	IValidationResult validate(String expression, Map<String, Set<IType>> variableTypes);
}
