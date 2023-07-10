/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.acceleo.query.ast.ASTNode;
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
	 * The mapping from a {@link Call} to its {@link List} of declaration {@link IService}.
	 */
	private final Map<Call, List<IService<?>>> serviceDeclarations = new HashMap<>();

	/**
	 * The mapping from a {@link Declaration} to the {@link List} of {@link VarRef} resolved to it.
	 */
	private final Map<Declaration, List<VarRef>> resolvedVarRef = new HashMap<>();

	/**
	 * The mapping from a {@link VarRef} to its declaration {@link Declaration}.
	 */
	private final Map<VarRef, Declaration> declarations = new HashMap<>();

	/**
	 * The {@link List} of unresolved {@link VarRef}.
	 */
	private final List<VarRef> unresolvedVarRef = new ArrayList<>();

	/**
	 * Messages.
	 */
	private final Map<ASTNode, List<IValidationMessage>> messages = new LinkedHashMap<ASTNode, List<IValidationMessage>>();

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

	@Override
	public Set<IType> getPossibleTypes(Expression expression) {
		return types.get(expression);
	}

	@Override
	public List<IValidationMessage> getMessages() {
		return messages.values().stream().flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public List<IValidationMessage> getMessages(ASTNode node) {
		return messages.getOrDefault(node, Collections.emptyList());
	}

	@Override
	public void addMessage(ASTNode node, IValidationMessage message) {
		messages.computeIfAbsent(node, n -> new ArrayList<>()).add(message);
	}

	@Override
	public void removeMessage(ASTNode node, IValidationMessage message) {
		final List<IValidationMessage> nodeMessages = messages.get(node);
		if (nodeMessages != null) {
			if (nodeMessages.remove(message)) {
				if (nodeMessages.isEmpty()) {
					messages.remove(node);
				}
			}
		}
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
		inferredVariableType.computeIfAbsent(expression, e -> new HashMap<Boolean, Map<String, Set<IType>>>())
				.computeIfAbsent(value, v -> new HashMap<String, Set<IType>>()).putAll(inferredTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationResult#getInferredVariableTypes(org.eclipse.acceleo.query.ast.Expression,
	 *      java.lang.Boolean)
	 */
	public Map<String, Set<IType>> getInferredVariableTypes(Expression expression, Boolean value) {
		return inferredVariableType.getOrDefault(expression, Collections.emptyMap()).getOrDefault(value,
				Collections.emptyMap());
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
		serviceDeclarations.computeIfAbsent(call, c -> new ArrayList<>()).add(service);
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
	 * Adds the given {@link VarRef} to the resolved calls of the given {@link Declaration}.
	 * 
	 * @param declaration
	 *            the {@link Declaration}
	 * @param varRef
	 *            the {@link VarRef}
	 * @since 8.0.1
	 */
	public void putResolvedVarRef(Declaration declaration, VarRef varRef) {
		resolvedVarRef.computeIfAbsent(declaration, s -> new ArrayList<>()).add(varRef);
		declarations.put(varRef, declaration);
	}

	@Override
	public List<VarRef> getResolvedVarRef(Declaration declaration) {
		return resolvedVarRef.get(declaration);
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

	@Override
	public Declaration getDeclaration(VarRef varRef) {
		return declarations.get(varRef);
	}

	@Override
	public List<IService<?>> getDeclarationIService(Call call) {
		return serviceDeclarations.getOrDefault(call, Collections.emptyList());
	}

}
