/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstCompletor;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IQueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.IQueryValidationEngine;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * {@link QueryCompletionEngine} is the default query validation engine.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QueryCompletionEngine implements IQueryCompletionEngine {

	/**
	 * A colon.
	 */
	private static final String COLON = ":";

	/**
	 * The environment containing all necessary information and used to execute query services.
	 */
	private IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Constructor. It takes an {@link IReadOnlyQueryEnvironment} as parameter.
	 * 
	 * @param queryEnvironment
	 *            The environment containing all necessary information and used to execute query services.
	 */
	public QueryCompletionEngine(IReadOnlyQueryEnvironment queryEnvironment) {
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
			throw new IllegalArgumentException("offset (" + offset
					+ ") must be in the range of the given expression: \"" + expression + "\"");
		}
		final String prefix = getPrefix(expression, offset);
		final String remaining = getRemaining(expression, offset);
		final String toParse = getToParse(expression, offset, prefix);

		final IQueryValidationEngine builder = new QueryValidationEngine(queryEnvironment);
		final IValidationResult validationResult = builder.validate(toParse, variableTypes);
		result = new CompletionResult(completor.getProposals(variableTypes, validationResult));
		result.setPrefix(prefix);
		result.setRemaining(remaining);

		if (prefix != null) {
			result.setReplacementOffset(offset - prefix.length());
			result.setReplacementLength(prefix.length() + remaining.length());
		}

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
				if (Character.isLetter(charAt) || Character.isDigit(charAt) || charAt == '_'
						|| charAt == ':') {
					--start;
				} else {
					break;
				}
			}
			final String prefix = expression.substring(start, offset);
			if (COLON.equals(prefix)) {
				result = "";
			} else if (!prefix.endsWith(COLON)) {
				// special case of valid EClassifier and EEnumLiteral
				final String[] splited = prefix.split(COLON + COLON);
				if (splited.length == 2) {
					// EClassifier
					if (!queryEnvironment.getEPackageProvider().getTypes(splited[0], splited[1]).isEmpty()) {
						result = "";
					} else {
						result = prefix;
					}
				} else if (splited.length == 3) {
					// EEnumLiteral
					if (!queryEnvironment.getEPackageProvider().getEnumLiterals(splited[0], splited[1],
							splited[2]).isEmpty()) {
						result = "";
					} else {
						result = prefix;
					}
				} else {
					result = prefix;
				}
			} else {
				result = prefix;
			}
		}

		return result;
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
	public static String getRemaining(String expression, int offset) {
		final String result;

		if (expression == null) {
			result = null;
		} else {
			int length = expression.length();
			int end = offset;
			while (end < length) {
				char charAt = expression.charAt(end);
				if (Character.isLetter(charAt) || Character.isDigit(charAt) || charAt == '_'
						|| charAt == ':') {
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
		} else if (prefix.contains(COLON)) {
			// special case of EClassifier and EEnumLiteral
			result = expression.substring(0, expression.lastIndexOf(COLON) + 1);
		} else {
			result = expression.substring(0, offset - prefix.length());
		}

		return result;
	}

}
