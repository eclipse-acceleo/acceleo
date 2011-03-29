/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
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
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColorManager;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.BlockNameRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.FirstParenthesisRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordSequenceRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.ReturnTypeRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.VariableRule;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'query' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoQueryScanner extends AbstractAcceleoScanner {

	/**
	 * Constructor.
	 * 
	 * @param manager
	 *            is the color manager
	 */
	public AcceleoQueryScanner(AcceleoColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN), new KeywordRule(
				IAcceleoConstants.LITERAL_END), new KeywordRule(IAcceleoConstants.LITERAL_ESCAPE), new Token(
				new TextAttribute(manager.getColor(AcceleoColor.LITERAL)))));

		rules.add(new WhitespaceRule(new AcceleoWhitespaceDetector()));
		rules.add(computeFirstParenthesisRule(manager));

		rules.addAll(computeKeywordRules(manager));
		rules.addAll(computeDelimiterRules(manager));
		rules.add(computeVariableRule(manager));
		rules.add(computeQueryNameRule(manager));
		rules.add(computeReturnTypeRule(manager));
		rules.addAll(computeOCLKeywordRules(manager));

		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(new Token(new TextAttribute(manager.getColor(AcceleoColor.OCL_EXPRESSION))));
	}

	/**
	 * Creates the rule for the first parenthesis of the signature. Needs special handling as it is not
	 * preceded nor followed by a predictable keyword.
	 * 
	 * @param manager
	 *            is the color manager
	 * @return The created rule.
	 */
	private IRule computeFirstParenthesisRule(AcceleoColorManager manager) {
		return new FirstParenthesisRule(IAcceleoConstants.QUERY, new Token(new TextAttribute(manager
				.getColor(AcceleoColor.QUERY), null, SWT.BOLD)));
	}

	/**
	 * Creates all keyword rules pertaining to an Acceleo Query.
	 * 
	 * @param manager
	 *            is the color manager
	 * @return The created rules.
	 */
	private List<IRule> computeKeywordRules(AcceleoColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();

		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.QUERY, null, manager));
		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.QUERY,
				IAcceleoConstants.DEFAULT_END, manager));

		// Visibility keywords
		rules.add(computeKeywordRule(IAcceleoConstants.QUERY, IAcceleoConstants.VISIBILITY_KIND_PUBLIC, null,
				manager));
		rules.add(computeKeywordRule(IAcceleoConstants.QUERY, IAcceleoConstants.VISIBILITY_KIND_PROTECTED,
				null, manager));
		rules.add(computeKeywordRule(IAcceleoConstants.QUERY, IAcceleoConstants.VISIBILITY_KIND_PRIVATE,
				null, manager));

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
	 * @param manager
	 *            is the color manager
	 * @return The created rule.
	 */
	private IRule computeKeywordRule(String precedingDelimiter, String keyword, String followingDelimiter,
			AcceleoColorManager manager) {
		return new KeywordSequenceRule(precedingDelimiter, keyword, followingDelimiter, new Token(
				new TextAttribute(manager.getColor(AcceleoColor.QUERY), null, SWT.BOLD)));
	}

	/**
	 * Creates all delimiter rules pertaining to an Acceleo Query.
	 * 
	 * @param manager
	 *            The color manager.
	 * @return The created rules.
	 */
	private List<IRule> computeDelimiterRules(AcceleoColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();

		// The "[" delimiter must be followed by the "query" keyword or the "/" of a closing tag
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.QUERY,
				manager));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, manager));

		// The "/" of a body's end must be either preceded by "[" or followed by "]"
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, null, manager));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_END_BODY_CHAR,
				IAcceleoConstants.DEFAULT_END, manager));

		// The "]" is a delimiter, wherever it may be
		rules.add(computeDelimiterRule((String)null, IAcceleoConstants.DEFAULT_END, null, manager));

		// The ":" is a delimiter if it is directly following a ")"
		rules.add(computeDelimiterRule(IAcceleoConstants.PARENTHESIS_END,
				IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, null, manager));

		// The "=" is a delimiter if it is following the return type of the query
		rules.add(computeDelimiterRule(new String[] {IAcceleoConstants.PARENTHESIS_END,
				IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, "*", }, //$NON-NLS-1$
				IAcceleoConstants.VARIABLE_INIT_SEPARATOR, null, manager));

		// The only "delimiter" parenthesis of a query is the one before the return type declaration
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END,
				IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, manager));

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
	 * @param manager
	 *            is the color manager
	 * @return the new delimiter rule
	 */
	private IRule computeDelimiterRule(String precedingText, String delimiter, String followingText,
			AcceleoColorManager manager) {
		return new KeywordSequenceRule(precedingText, delimiter, followingText, new Token(new TextAttribute(
				manager.getColor(AcceleoColor.QUERY), null, SWT.BOLD)));
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
	 * @param manager
	 *            is the color manager
	 * @return the new delimiter rule
	 */
	private IRule computeDelimiterRule(String[] precedingWords, String delimiter, String[] followingWords,
			AcceleoColorManager manager) {
		return new KeywordSequenceRule(precedingWords, delimiter, followingWords, new Token(
				new TextAttribute(manager.getColor(AcceleoColor.QUERY), null, SWT.BOLD)));
	}

	/**
	 * Computes a rule matching the query name.
	 * 
	 * @param manager
	 *            The color manager.
	 * @return The created rule.
	 */
	private IRule computeQueryNameRule(AcceleoColorManager manager) {
		return new BlockNameRule(IAcceleoConstants.QUERY, new Token(new TextAttribute(manager
				.getColor(AcceleoColor.QUERY_NAME), null, SWT.NONE)));
	}

	/**
	 * Creates a rule matching the query parameters.
	 * 
	 * @param manager
	 *            is the color manager
	 * @return the new variable rule
	 */
	private IRule computeVariableRule(AcceleoColorManager manager) {
		return new VariableRule(new String[] {}, new Token(new TextAttribute(manager
				.getColor(AcceleoColor.QUERY_PARAMETER), null, SWT.NONE)));
	}

	/**
	 * Creates a rule matching the query's return type.
	 * 
	 * @param manager
	 *            The color manager.
	 * @return The created rules.
	 */
	private IRule computeReturnTypeRule(AcceleoColorManager manager) {
		return new ReturnTypeRule(new Token(new TextAttribute(manager.getColor(AcceleoColor.QUERY_RETURN),
				null, SWT.NONE)));
	}

	/**
	 * Computes a rule to match all OCL keywords in our query.
	 * 
	 * @param manager
	 *            The color manager.
	 * @return The created rules.
	 */
	private List<IRule> computeOCLKeywordRules(AcceleoColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();

		for (String keyword : AcceleoOCLReflection.getReservedKeywords()) {
			rules.add(new KeywordRule(keyword, true, false, new Token(new TextAttribute(manager
					.getColor(AcceleoColor.OCL_KEYWORD), null, SWT.BOLD))));
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
