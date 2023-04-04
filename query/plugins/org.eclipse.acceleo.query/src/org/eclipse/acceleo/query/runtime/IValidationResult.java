/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Declaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Result of a {@link IQueryValidationEngine#validate(String, java.util.Map) validation}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IValidationResult {

	/**
	 * Gets the {@link AstResult}.
	 * 
	 * @return the {@link AstResult}
	 */
	AstResult getAstResult();

	/**
	 * Gets the possible types of the given {@link Expression}.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the possible types of the given {@link Expression} if known, <code>null</code> otherwise
	 */
	Set<IType> getPossibleTypes(Expression expression);

	/**
	 * Gets the {@link List} of link IValidationMessage}.
	 * 
	 * @return the {@link List} of link IValidationMessage}
	 */
	List<IValidationMessage> getMessages();

	/**
	 * Gets inferred variable {@link IType} when the given boolean {@link Expression} has the given
	 * {@link Boolean value}.
	 * 
	 * @param expression
	 *            the boolean {@link Expression}
	 * @param value
	 *            the {@link Boolean} value
	 * @return inferred variable {@link IType} when the given boolean {@link Expression} has the given
	 *         {@link Boolean value}
	 * @since 4.0.0
	 */
	Map<String, Set<IType>> getInferredVariableTypes(Expression expression, Boolean value);

	/**
	 * Gets the {@link List} of resolved {@link Call} for the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @return the {@link List} of resolved {@link Call} for the given {@link IService}
	 * @since 8.0.1
	 */
	List<Call> getResolvedCalls(IService<?> service);

	/**
	 * Gets the {@link List} of resolved {@link VarRef} for the given {@link Declaration}.
	 * 
	 * @param declaration
	 *            the {@link Declaration}
	 * @return the {@link List} of resolved {@link VarRef} for the given {@link Declaration} if any,
	 *         <code>null</code> otherwise
	 * @since 8.0.1
	 */
	List<VarRef> getResolvedVarRef(Declaration declaration);

	/**
	 * Gets the {@link List} of unresolved {@link VarRef}.
	 * 
	 * @return the {@link List} of unresolved {@link VarRef}
	 * @since 8.0.1
	 */
	List<VarRef> getUnresolvedVarRef();

	/**
	 * Gets the declaration {@link Declaration} for the given {@link VarRef}.
	 * 
	 * @param varRef
	 *            the {@link VarRef}
	 * @return the declaration {@link Declaration} for the given {@link VarRef} if any, <code>null</code>
	 *         otherwise
	 * @since 8.0.1
	 */
	Declaration getDeclaration(VarRef varRef);

	/**
	 * Gets the {@link List} of declaration {@link IService} for the given {@link Call}.
	 * 
	 * @param call
	 *            the {@link VarRef}
	 * @return the {@link List} of declaration {@link IService} for the given {@link Call}
	 * @since 8.0.1
	 */
	List<IService<?>> getDeclarationIService(Call call);

}
