/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation;

import java.util.List;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;

/**
 * Acceleo validation result.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAcceleoValidationResult {

	/**
	 * Gets the validated {@link AcceleoAstResult}.
	 * 
	 * @return the validated {@link AcceleoAstResult}
	 */
	AcceleoAstResult getAcceleoAstResult();

	/**
	 * Gets the {@link List} of all {@link IValidationMessage} for
	 * {@link IAcceleoValidationResult#getModule() validated module}.
	 * 
	 * @return the {@link List} of all {@link IValidationMessage} for
	 *         {@link IAcceleoValidationResult#getModule() validated module}
	 */
	List<IValidationMessage> getValidationMessages();

	/**
	 * Gets the {@link List} of {@link IValidationMessage} for the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the {@link List} of {@link IValidationMessage} for the given {@link ASTNode}
	 */
	List<IValidationMessage> getValidationMessages(ASTNode node);

	/**
	 * Gets the {@link IValidationResult AQL validation result} for the given {@link AstResult AQL AST}.
	 * 
	 * @param aqlAst
	 *            the {@link AstResult AQL AST}
	 * @return the {@link IValidationResult AQL validation result} for the given {@link AstResult AQL AST}
	 */
	IValidationResult getValidationResult(AstResult aqlAst);

}
