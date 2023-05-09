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
 * Interface to be implemented by query completion engine implementations.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IQueryCompletionEngine extends IQueryEngine {

	/**
	 * Gets the {@link ICompletionResult} for the given expression at the given offset.
	 * 
	 * @param expression
	 *            the expression to validate
	 * @param offset
	 *            the offset of the cursor in the expression
	 * @param variableTypes
	 *            the set of defined variables
	 * @return the {@link ICompletionResult} for the given expression at the given offset
	 */
	ICompletionResult getCompletion(String expression, int offset, Map<String, Set<IType>> variableTypes);
}
