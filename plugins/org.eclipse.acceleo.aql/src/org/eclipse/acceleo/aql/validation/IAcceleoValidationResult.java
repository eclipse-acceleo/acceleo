/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Declaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;

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
	 * Gets the {@link List} of all {@link IValidationMessage} for {@link IAcceleoValidationResult#getModule()
	 * validated module}.
	 * 
	 * @return the {@link List} of all {@link IValidationMessage} for
	 *         {@link IAcceleoValidationResult#getModule() validated module}
	 */
	List<IValidationMessage> getValidationMessages();

	/**
	 * Gets the {@link List} of {@link IValidationMessage} for the given {@link AcceleoASTNode}.
	 * 
	 * @param node
	 *            the {@link AcceleoASTNode}
	 * @return the {@link List} of {@link IValidationMessage} for the given {@link AcceleoASTNode}
	 */
	List<IValidationMessage> getValidationMessages(AcceleoASTNode node);

	/**
	 * Gets the {@link IValidationResult AQL validation result} for the given {@link AstResult AQL AST}.
	 * 
	 * @param aqlAst
	 *            the {@link AstResult AQL AST}
	 * @return the {@link IValidationResult AQL validation result} for the given {@link AstResult AQL AST}
	 */
	IValidationResult getValidationResult(AstResult aqlAst);

	/**
	 * Gets the {@link List} of resolved {@link Call} for the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @return the {@link List} of resolved {@link Call} for the given {@link IService}
	 */
	List<Call> getResolvedCalls(IService<?> service);

	/**
	 * Gets the {@link List} of resolved {@link VarRef} for the given {@link Declaration}.
	 * 
	 * @param declaration
	 *            the {@link Declaration}
	 * @return the {@link List} of resolved {@link VarRef} for the given {@link Declaration} if any,
	 *         <code>null</code> otherwise
	 */
	List<VarRef> getResolvedVarRef(Declaration declaration);

	/**
	 * Gets the {@link List} of resolved {@link VarRef} for the given {@link Variable}.
	 * 
	 * @param variable
	 *            the {@link Variable}
	 * @return the {@link List} of resolved {@link VarRef} for the given {@link Variable} if any,
	 *         <code>null</code> otherwise
	 */
	List<VarRef> getResolvedVarRef(Variable variable);

	/**
	 * Gets the {@link Declaration} for the given {@link VarRef}.
	 * 
	 * @param varRef
	 *            the {@link VarRef}
	 * @return the {@link Declaration} for the given {@link VarRef} if any, <code>null</code> otherwise
	 */
	Declaration getDeclaration(VarRef varRef);

	/**
	 * Gets the declaration {@link Variable} for the given {@link VarRef}.
	 * 
	 * @param varRef
	 *            the {@link VarRef}
	 * @return the declaration {@link Variable} for the given {@link VarRef} if any, <code>null</code>
	 *         otherwise
	 */
	Variable getDeclarationVariable(VarRef varRef);

	/**
	 * Gets the {@link List} of declaration {@link IService} for the given {@link Call}.
	 * 
	 * @param call
	 *            the {@link VarRef}
	 * @return the {@link List} of declaration {@link IService} for the given {@link Call}
	 */
	List<IService<?>> getDeclarationIService(Call call);

	/**
	 * Gets the possible types of the given {@link Expression}.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the possible types of the given {@link Expression} if known, <code>null</code> otherwise
	 */
	Set<IType> getPossibleTypes(Expression expression);

}
