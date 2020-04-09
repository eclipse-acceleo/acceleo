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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;

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
	 * Mapping of {@link ASTNode} to {@link IValidationMessage}.
	 */
	private final Map<ASTNode, List<IValidationMessage>> messages = new LinkedHashMap<>();

	/**
	 * Mapping of AQL AST to AQL validation result.
	 */
	private Map<AstResult, IValidationResult> aqlValidationResutls = new HashMap<AstResult, IValidationResult>();

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
		return new ArrayList<IValidationMessage>(messages.getOrDefault(node, new LinkedList<>()));
	}

	@Override
	public IValidationResult getValidationResult(AstResult aqlAst) {
		return aqlValidationResutls.get(aqlAst);
	}

	/* visible to the validator only */void addMessage(ASTNode node, IValidationMessage newMessage) {
		messages.computeIfAbsent(node, key -> new LinkedList<>()).add(newMessage);
	}

	/* visible to the validator only */void addMessages(ASTNode node,
			Collection<IValidationMessage> newMessages) {
		messages.computeIfAbsent(node, key -> new LinkedList<>()).addAll(newMessages);
	}

	/**
	 * Gets the mapping of AQL AST to AQL validation result.
	 * 
	 * @return the mapping of AQL AST to AQL validation result
	 */
	public Map<AstResult, IValidationResult> getAqlValidationResutls() {
		return aqlValidationResutls;
	}

}
