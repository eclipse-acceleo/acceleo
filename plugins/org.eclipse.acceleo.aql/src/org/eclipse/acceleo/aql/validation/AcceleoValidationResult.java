/*******************************************************************************
 * Copyright (c) 2017, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
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
 * Acceleo validation result.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoValidationResult implements IAcceleoValidationResult {

	/**
	 * The {@link AcceleoAstResult}.
	 */
	private final AcceleoAstResult astResult;

	/**
	 * Mapping of {@link AcceleoASTNode} to {@link IValidationMessage}.
	 */
	private final Map<AcceleoASTNode, List<IValidationMessage>> messages = new LinkedHashMap<>();

	/**
	 * Mapping of AQL AST to AQL validation result.
	 */
	private Map<AstResult, IValidationResult> aqlValidationResults = new LinkedHashMap<AstResult, IValidationResult>();

	/**
	 * The mapping from a {@link Variable} to the {@link List} of {@link VarRef} resolved to it.
	 */
	private final Map<Variable, List<VarRef>> variableResolvedVarRef = new HashMap<>();

	/**
	 * The mapping from a {@link VarRef} to its declaration {@link Variable}.
	 */
	private final Map<VarRef, Variable> variableDeclarations = new HashMap<>();

	/**
	 * Constructor.
	 * 
	 * @param astResult
	 *            the {@link AcceleoAstResult}
	 */
	public AcceleoValidationResult(AcceleoAstResult astResult) {
		this.astResult = astResult;
	}

	@Override
	public AcceleoAstResult getAcceleoAstResult() {
		return astResult;
	}

	@Override
	public List<IValidationMessage> getValidationMessages() {
		return messages.values().stream().flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public List<IValidationMessage> getValidationMessages(ASTNode node) {
		final List<IValidationMessage> res = new ArrayList<>();

		if (node instanceof AcceleoASTNode) {
			res.addAll(messages.getOrDefault(node, new LinkedList<>()));
		} else {
			res.addAll(aqlValidationResults.values().stream().map(vr -> vr.getMessages(node)).flatMap(
					List::stream).collect(Collectors.toList()));
		}

		return res;
	}

	@Override
	public IValidationResult getValidationResult(AstResult aqlAst) {
		return aqlValidationResults.get(aqlAst);
	}

	/* visible to the validator only */void addMessage(AcceleoASTNode node, IValidationMessage newMessage) {
		messages.computeIfAbsent(node, key -> new LinkedList<>()).add(newMessage);
	}

	/* visible to the validator only */void addMessages(AcceleoASTNode node,
			Collection<IValidationMessage> newMessages) {
		messages.computeIfAbsent(node, key -> new LinkedList<>()).addAll(newMessages);
	}

	/**
	 * Gets the mapping of AQL AST to AQL validation result.
	 * 
	 * @return the mapping of AQL AST to AQL validation result
	 */
	public Map<AstResult, IValidationResult> getAqlValidationResults() {
		return aqlValidationResults;
	}

	@Override
	public List<Call> getResolvedCalls(IService<?> service) {
		return aqlValidationResults.values().stream().map(vr -> vr.getResolvedCalls(service)).flatMap(
				List::stream).collect(Collectors.toList());
	}

	@Override
	public List<VarRef> getResolvedVarRef(Declaration declaration) {
		final Optional<List<VarRef>> res = aqlValidationResults.values().stream().map(vr -> vr
				.getResolvedVarRef(declaration)).filter(db -> db != null).findFirst();

		return res.orElse(Collections.emptyList());
	}

	/**
	 * Adds the given {@link VarRef} to the resolved calls of the given {@link Variable}.
	 * 
	 * @param variable
	 *            the {@link Variable}
	 * @param varRef
	 *            the {@link VarRef}
	 */
	public void putBindingResolvedVarRef(Variable variable, VarRef varRef) {
		variableResolvedVarRef.computeIfAbsent(variable, s -> new ArrayList<>()).add(varRef);
		variableDeclarations.put(varRef, variable);
	}

	@Override
	public List<VarRef> getResolvedVarRef(Variable variable) {
		return variableResolvedVarRef.getOrDefault(variable, Collections.emptyList());
	}

	@Override
	public Declaration getDeclaration(VarRef varRef) {
		final Optional<Declaration> res = aqlValidationResults.values().stream().map(vr -> vr.getDeclaration(
				varRef)).filter(db -> db != null).findFirst();

		return res.orElse(null);
	}

	@Override
	public List<IService<?>> getDeclarationIService(Call call) {
		final Optional<List<IService<?>>> res = aqlValidationResults.values().stream().map(vr -> vr
				.getDeclarationIService(call)).filter(db -> !db.isEmpty()).findFirst();

		return res.orElse(Collections.emptyList());
	}

	@Override
	public Variable getDeclarationVariable(VarRef varRef) {
		return variableDeclarations.get(varRef);
	}

	@Override
	public Set<IType> getPossibleTypes(Expression expression) {
		final Optional<Set<IType>> res = aqlValidationResults.values().stream().map(vr -> vr.getPossibleTypes(
				expression)).filter(pt -> pt != null).findFirst();

		return res.orElse(null);
	}
}
