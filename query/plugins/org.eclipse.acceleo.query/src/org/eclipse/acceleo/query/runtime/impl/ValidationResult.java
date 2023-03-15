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
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Result of a {@link org.eclipse.acceleo.query.runtime.IQueryValidationEngine#validate(String, java.util.Map)
 * validation} .
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ValidationResult implements IValidationResult {

	/**
	 * Query possible types for a known {@link Expression}.
	 */
	private final Map<Expression, Set<IType>> types = new HashMap<Expression, Set<IType>>();

	/**
	 * Override of variables {@link IType} after an {@link Expression} is {@link Boolean#TRUE} or
	 * {@link Boolean#FALSE}.
	 */
	private final Map<Expression, Map<Boolean, Map<String, Set<IType>>>> inferredVariableType = new HashMap<Expression, Map<Boolean, Map<String, Set<IType>>>>();

	/**
	 * The mapping from a {@link IService} to the {@link List} of {@link Call} resolved to it.
	 */
	private final Map<IService<?>, List<Call>> resolvedCalls = new HashMap<>();

	/**
	 * The mapping from a {@link Binding} to the {@link List} of {@link VarRef} resolved to it.
	 */
	private final Map<Binding, List<VarRef>> bindingResolvedVarRef = new HashMap<>();

	/**
	 * The mapping from a {@link VariableDeclaration} to the {@link List} of {@link VarRef} resolved to it.
	 */
	private final Map<VariableDeclaration, List<VarRef>> variableDeclarationResolvedVarRef = new HashMap<>();

	/**
	 * The {@link List} of unresolved {@link VarRef}.
	 */
	private final List<VarRef> unresolvedVarRef = new ArrayList<>();

	/**
	 * Messages.
	 */
	private final List<IValidationMessage> messages = new ArrayList<IValidationMessage>();

	/**
	 * The {@link AstResult}.
	 */
	private final AstResult astResult;

	/**
	 * Constructor.
	 * 
	 * @param astResult
	 *            the {@link AstResult}
	 */
	public ValidationResult(AstResult astResult) {
		this.astResult = astResult;
	}

	/**
	 * Adds the given {@link Set} of {@link IType} as possible types of the given {@link Expression}.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @param possibleTypes
	 *            the {@link Set} of possible {@link IType}
	 */
	public void addTypes(Expression expression, Set<IType> possibleTypes) {
		types.put(expression, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getPossibleTypes(org.eclipse.acceleo.query.ast.Expression)
	 */
	@Override
	public Set<IType> getPossibleTypes(Expression expression) {
		return types.get(expression);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getMessages()
	 */
	@Override
	public List<IValidationMessage> getMessages() {
		return messages;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getAstResult()
	 */
	@Override
	public AstResult getAstResult() {
		return astResult;
	}

	/**
	 * Adds inferred {@link IType} for the given {@link Expression} and {@link Boolean value}.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @param value
	 *            the {@link Boolean} value
	 * @param inferredTypes
	 *            the inferred {@link IType}
	 */
	public void putInferredVariableTypes(Expression expression, Boolean value,
			Map<String, Set<IType>> inferredTypes) {
		Map<Boolean, Map<String, Set<IType>>> map = inferredVariableType.get(expression);
		if (map == null) {
			map = new HashMap<Boolean, Map<String, Set<IType>>>();
			inferredVariableType.put(expression, map);
		}
		Map<String, Set<IType>> t = map.get(value);
		if (t == null) {
			t = new HashMap<String, Set<IType>>();
			map.put(value, t);
		}
		t.putAll(inferredTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getInferredVariableTypes(org.eclipse.acceleo.query.ast.Expression,
	 *      java.lang.Boolean)
	 */
	public Map<String, Set<IType>> getInferredVariableTypes(Expression expression, Boolean value) {
		final Map<String, Set<IType>> result;

		final Map<Boolean, Map<String, Set<IType>>> map = inferredVariableType.get(expression);
		if (map != null) {
			final Map<String, Set<IType>> inferedTypes = map.get(value);
			if (inferedTypes != null) {
				result = inferedTypes;
			} else {
				result = Collections.emptyMap();
			}
		} else {
			result = Collections.emptyMap();
		}

		return result;
	}

	/**
	 * Adds the given {@link Call} to the resolved calls of the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @param call
	 *            the {@link Call}
	 * @since 8.0.1
	 */
	public void putResolvedCall(IService<?> service, Call call) {
		resolvedCalls.computeIfAbsent(service, s -> new ArrayList<>()).add(call);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getResolvedCalls(org.eclipse.acceleo.query.runtime.IService)
	 */
	@Override
	public List<Call> getResolvedCalls(IService<?> service) {
		return resolvedCalls.getOrDefault(service, Collections.emptyList());
	}

	/**
	 * Adds the given {@link VarRef} to the resolved calls of the given {@link Binding}.
	 * 
	 * @param binding
	 *            the {@link Binding}
	 * @param varRef
	 *            the {@link VarRef}
	 * @since 8.0.1
	 */
	public void putBindingResolvedVarRef(Binding binding, VarRef varRef) {
		bindingResolvedVarRef.computeIfAbsent(binding, s -> new ArrayList<>()).add(varRef);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getResolvedVarRef(org.eclipse.acceleo.query.ast.Binding)
	 */
	@Override
	public List<VarRef> getResolvedVarRef(Binding binding) {
		return bindingResolvedVarRef.get(binding);
	}

	/**
	 * Adds the given {@link VarRef} to the resolved calls of the given {@link VariableDeclaration}.
	 * 
	 * @param variableDeclaration
	 *            the {@link VariableDeclaration}
	 * @param varRef
	 *            the {@link VarRef}
	 * @since 8.0.1
	 */
	public void putVariableDeclarationResolvedVarRef(VariableDeclaration variableDeclaration, VarRef varRef) {
		variableDeclarationResolvedVarRef.computeIfAbsent(variableDeclaration, s -> new ArrayList<>()).add(
				varRef);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getResolvedVarRef(org.eclipse.acceleo.query.ast.VariableDeclaration)
	 */
	@Override
	public List<VarRef> getResolvedVarRef(VariableDeclaration variableDeclaration) {
		return variableDeclarationResolvedVarRef.get(variableDeclaration);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getUnresolvedVarRef()
	 */
	@Override
	public List<VarRef> getUnresolvedVarRef() {
		return unresolvedVarRef;
	}

}
