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

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final Multimap<ASTNode, IValidationMessage> messages = LinkedListMultimap.create();

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
		return new ArrayList<IValidationMessage>(messages.values());
	}

	@Override
	public List<IValidationMessage> getValidationMessages(ASTNode node) {
		return new ArrayList<IValidationMessage>(messages.get(node));
	}

	@Override
	public IValidationResult getValidationResult(AstResult aqlAst) {
		return aqlValidationResutls.get(aqlAst);
	}

	/**
	 * Gets the mapping of {@link ASTNode} to {@link IValidationMessage}.
	 * 
	 * @return the mapping of {@link ASTNode} to {@link IValidationMessage}
	 */
	public Multimap<ASTNode, IValidationMessage> getMessages() {
		return messages;
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
