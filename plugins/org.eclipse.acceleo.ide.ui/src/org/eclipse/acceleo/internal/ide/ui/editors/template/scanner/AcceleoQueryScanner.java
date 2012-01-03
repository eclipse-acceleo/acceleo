/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.scanner;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoOCLReflection;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.BlockNameRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.FirstParenthesisRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordSequenceRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.ReturnTypeRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.VariableRule;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'query' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoQueryScanner extends AbstractAcceleoScanner {
	/**
	 * Instantiates our scanner given the preference lookup order.
	 * 
	 * @param lookupOrder
	 *            Order in which to look preferences up.
	 */
	public AcceleoQueryScanner(IEclipsePreferences[] lookupOrder) {
		super(lookupOrder);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner#createRules()
	 */
	@Override
	protected void createRules() {
		List<IRule> rules = new ArrayList<IRule>();
		AcceleoToken literalToken = createToken(AcceleoColor.LITERAL);
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN), new KeywordRule(
				IAcceleoConstants.LITERAL_END), new KeywordRule(IAcceleoConstants.LITERAL_ESCAPE),
				literalToken));

		rules.add(new WhitespaceRule(new AcceleoWhitespaceDetector()));
		rules.add(computeFirstParenthesisRule());

		rules.addAll(computeKeywordRules());
		rules.addAll(computeDelimiterRules());
		rules.add(computeVariableRule());
		rules.add(computeQueryNameRule());
		rules.add(computeReturnTypeRule());
		rules.addAll(computeOCLKeywordRules());

		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(createToken(AcceleoColor.OCL_EXPRESSION));
	}

	/**
	 * Creates the rule for the first parenthesis of the signature. Needs special handling as it is not
	 * preceded nor followed by a predictable keyword.
	 * 
	 * @return The created rule.
	 */
	private IRule computeFirstParenthesisRule() {
		return new FirstParenthesisRule(IAcceleoConstants.QUERY, createToken(AcceleoColor.QUERY, null,
				SWT.BOLD));
	}

	/**
	 * Creates all keyword rules pertaining to an Acceleo Query.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeKeywordRules() {
		List<IRule> rules = new ArrayList<IRule>();

		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.QUERY, null));
		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.QUERY,
				IAcceleoConstants.DEFAULT_END));

		// Visibility keywords
		rules.add(computeKeywordRule(IAcceleoConstants.QUERY, IAcceleoConstants.VISIBILITY_KIND_PUBLIC, null));
		rules.add(computeKeywordRule(IAcceleoConstants.QUERY, IAcceleoConstants.VISIBILITY_KIND_PROTECTED,
				null));
		rules.add(computeKeywordRule(IAcceleoConstants.QUERY, IAcceleoConstants.VISIBILITY_KIND_PRIVATE, null));

		return rules;
	}

	/**
	 * Creates a keyword rule.
	 * 
	 * @param precedingDelimiter
	 *            The delimiter that needs to precede the keyword.
	 * @param keyword
	 *            The keyword we need to detect.
	 * @param followingDelimiter
	 *            The delimiter that needs to follow the keyword.
	 * @return The created rule.
	 */
	private IRule computeKeywordRule(String precedingDelimiter, String keyword, String followingDelimiter) {
		return new KeywordSequenceRule(precedingDelimiter, keyword, followingDelimiter, createToken(
				AcceleoColor.QUERY, null, SWT.BOLD));
	}

	/**
	 * Creates all delimiter rules pertaining to an Acceleo Query.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeDelimiterRules() {
		List<IRule> rules = new ArrayList<IRule>();

		// The "[" delimiter must be followed by the "query" keyword or the "/" of a closing tag
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.QUERY));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR));

		// The "/" of a body's end must be either preceded by "[" or followed by "]"
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, null));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_END_BODY_CHAR,
				IAcceleoConstants.DEFAULT_END));

		// The "]" is a delimiter, wherever it may be
		rules.add(computeDelimiterRule((String)null, IAcceleoConstants.DEFAULT_END, null));

		// The ":" is a delimiter if it is directly following a ")"
		rules.add(computeDelimiterRule(IAcceleoConstants.PARENTHESIS_END,
				IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, null));

		// The "=" is a delimiter if it is following the return type of the query
		rules.add(computeDelimiterRule(new String[] {IAcceleoConstants.PARENTHESIS_END,
				IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, "*", }, //$NON-NLS-1$
				IAcceleoConstants.VARIABLE_INIT_SEPARATOR, null));

		// The only "delimiter" parenthesis of a query is the one before the return type declaration
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END,
				IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR));

		return rules;
	}

	/**
	 * Creates a rule for the given delimiter.
	 * 
	 * @param precedingText
	 *            The text that needs to precede the delimiter. Can be <code>null</code>.
	 * @param delimiter
	 *            The delimiter we need to detect.
	 * @param followingText
	 *            The text that needs to follow the keyword. Can be <code>null</code>.
	 * @return the new delimiter rule
	 */
	private IRule computeDelimiterRule(String precedingText, String delimiter, String followingText) {
		return new KeywordSequenceRule(precedingText, delimiter, followingText, createToken(
				AcceleoColor.QUERY, null, SWT.BOLD));
	}

	/**
	 * Creates a rule for the given delimiter.
	 * 
	 * @param precedingWords
	 *            The words that needs to precede the delimiter. Can be <code>null</code>.
	 * @param delimiter
	 *            The delimiter we need to detect.
	 * @param followingWords
	 *            The words that needs to follow the delimiter. Can be <code>null</code>.
	 * @return the new delimiter rule
	 */
	private IRule computeDelimiterRule(String[] precedingWords, String delimiter, String[] followingWords) {
		return new KeywordSequenceRule(precedingWords, delimiter, followingWords, createToken(
				AcceleoColor.QUERY, null, SWT.BOLD));
	}

	/**
	 * Computes a rule matching the query name.
	 * 
	 * @return The created rule.
	 */
	private IRule computeQueryNameRule() {
		return new BlockNameRule(IAcceleoConstants.QUERY,
				createToken(AcceleoColor.QUERY_NAME, null, SWT.NONE));
	}

	/**
	 * Creates a rule matching the query parameters.
	 * 
	 * @return the new variable rule
	 */
	private IRule computeVariableRule() {
		return new VariableRule(new String[] {}, createToken(AcceleoColor.QUERY_PARAMETER, null, SWT.NONE));
	}

	/**
	 * Creates a rule matching the query's return type.
	 * 
	 * @return The created rules.
	 */
	private IRule computeReturnTypeRule() {
		return new ReturnTypeRule(createToken(AcceleoColor.QUERY_RETURN, null, SWT.NONE));
	}

	/**
	 * Computes a rule to match all OCL keywords in our query.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeOCLKeywordRules() {
		List<IRule> rules = new ArrayList<IRule>();

		for (String keyword : AcceleoOCLReflection.getReservedKeywords()) {
			rules.add(new KeywordRule(keyword, true, false, createToken(AcceleoColor.OCL_KEYWORD, null,
					SWT.BOLD)));
		}

		return rules;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.editors.template.scanner.AbstractAcceleoScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return AcceleoPartitionScanner.ACCELEO_QUERY;
	}
}
