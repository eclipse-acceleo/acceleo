/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.parser.AstCompletor;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IQueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryValidationEngine;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * {@link QueryCompletionEngine} is the default query validation engine.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QueryCompletionEngine implements IQueryCompletionEngine {
	/**
	 * the set of keywords of the language.
	 */
	private static final Set<String> KEYWORD_SET = Sets.newHashSet("if", "then", "else", "endif");

	/**
	 * The environment containing all necessary information and used to execute query services.
	 */
	private IQueryEnvironment queryEnvironment;

	/**
	 * Constructor. It takes an {@link IQueryEnvironment} as parameter.
	 * 
	 * @param queryEnvironment
	 *            The environment containing all necessary information and used to execute query services.
	 */
	public QueryCompletionEngine(IQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryCompletionEngine#getCompletion(java.lang.String, int,
	 *      java.util.Map)
	 */
	@Override
	public ICompletionResult getCompletion(String expression, int offset,
			Map<String, Set<IType>> variableTypes) {
		final ICompletionResult result;

		final AstCompletor completor = new AstCompletor(new CompletionServices(queryEnvironment));
		if (offset < 0 || (expression != null && offset > expression.length())) {
			throw new IllegalArgumentException("offset must be in the range of the given expression.");
		}
		final String prefix = getPrefix(expression, offset);
		final String remaining = getRemaining(expression, offset);
		final String toParse = getToParse(expression, offset, prefix);

		final IQueryValidationEngine builder = new QueryValidationEngine(queryEnvironment);
		final IValidationResult validationResult = builder.validate(toParse, variableTypes);
		result = new CompletionResult(completor.getProposals(variableTypes.keySet(), validationResult));
		result.setPrefix(prefix);
		result.setRemaining(remaining);

		int replacementLength = 0;
		int replacementOffset = 0;
		if (toParse != null) {
			replacementOffset = toParse.length();
		}

		// Errors on types or enum literals must be handled differently
		List<Error> errors = validationResult.getAstResult().getErrors();
		for (Error error : errors) {
			if (error instanceof ErrorTypeLiteral || error instanceof ErrorEnumLiteral) {
				int errorStart = validationResult.getAstResult().getStartPosition((Expression)error);
				int errorEnd = validationResult.getAstResult().getEndPosition((Expression)error);
				if (errorStart <= offset - prefix.length() && offset - prefix.length() <= errorEnd) {
					replacementOffset = validationResult.getAstResult().getStartPosition((Expression)error);
					replacementLength = validationResult.getAstResult().getEndPosition((Expression)error)
							- replacementOffset;
					break;
				}
			}
		}

		// The prefix part is to be replaced in all cases
		if (prefix != null) {
			replacementLength += prefix.length();
		}
		result.setReplacementOffset(replacementOffset);
		result.setReplacementLength(replacementLength);

		return result;
	}

	/**
	 * Gets the {@link String} containing only identifier part before the cursor if any, an empty
	 * {@link String} otherwise.
	 * 
	 * @param expression
	 *            the original expression to parse
	 * @param offset
	 *            the offset of the cursor
	 * @return the {@link String} containing only identifier part before the cursor if any, an empty
	 *         {@link String} otherwise
	 */
	private String getPrefix(String expression, int offset) {
		final String result;

		if (expression == null) {
			result = null;
		} else {
			int start = offset;
			while (start - 1 >= 0) {
				char charAt = expression.charAt(start - 1);
				if (Character.isLetter(charAt) || Character.isDigit(charAt) || charAt == '_') {
					--start;
				} else {
					break;
				}
			}
			result = expression.substring(start, offset);
		}
		if (KEYWORD_SET.contains(result)) {
			return "";
		} else {
			return result;
		}
	}

	/**
	 * Gets the {@link String} containing only identifier part after the cursor if any, <code>null</code> or
	 * an empty {@link String} otherwise.
	 * 
	 * @param expression
	 *            the original expression to parse
	 * @param offset
	 *            the offset of the cursor
	 * @return the {@link String} containing only identifier part after the cursor if any, <code>null</code>
	 *         or an empty {@link String} otherwise
	 */
	private String getRemaining(String expression, int offset) {
		final String result;

		if (expression == null) {
			result = null;
		} else {
			int length = expression.length();
			int end = offset;
			while (end < length) {
				char charAt = expression.charAt(end);
				if (Character.isLetter(charAt) || Character.isDigit(charAt) || charAt == '_') {
					++end;
				} else {
					break;
				}
			}
			result = expression.substring(offset, end);
		}

		return result;
	}

	/**
	 * Gets the portion of the original expression to parse.
	 * 
	 * @param expression
	 *            the original expression to parse
	 * @param offset
	 *            the offset of the cursor
	 * @param prefix
	 *            the {@link String} containing only letter before the cursor if any, an empty {@link String}
	 *            otherwise
	 * @return the portion of the original expression to parse
	 */
	private String getToParse(String expression, int offset, String prefix) {
		final String result;

		if (expression == null) {
			result = null;
		} else {
			result = expression.substring(0, offset - prefix.length());
		}

		return result;
	}

}
