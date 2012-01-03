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
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordSequenceRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.VariableRule;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'for' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoForScanner extends AbstractAcceleoScanner {
	/**
	 * Instantiates our scanner given the preference lookup order.
	 * 
	 * @param lookupOrder
	 *            Order in which to look preferences up.
	 */
	public AcceleoForScanner(IEclipsePreferences[] lookupOrder) {
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
		rules.addAll(computeKeywordRules());
		rules.addAll(computeDelimiterRules());
		rules.add(computeVariableRule());
		rules.addAll(computeOCLKeywordRules());

		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(createToken(AcceleoColor.OCL_EXPRESSION));
	}

	/**
	 * Creates all keyword rules pertaining to an Acceleo For.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeKeywordRules() {
		List<IRule> rules = new ArrayList<IRule>();

		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.FOR,
				IAcceleoConstants.PARENTHESIS_BEGIN));
		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.FOR,
				IAcceleoConstants.DEFAULT_END));
		rules.add(computeKeywordRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.AFTER,
				IAcceleoConstants.PARENTHESIS_BEGIN));
		rules.add(computeKeywordRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.BEFORE,
				IAcceleoConstants.PARENTHESIS_BEGIN));
		rules.add(computeKeywordRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.SEPARATOR,
				IAcceleoConstants.PARENTHESIS_BEGIN));
		rules.add(computeKeywordRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.GUARD,
				IAcceleoConstants.PARENTHESIS_BEGIN));

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
				AcceleoColor.FOR, null, SWT.ITALIC | SWT.BOLD));
	}

	/**
	 * Creates all delimiter rules pertaining to an Acceleo For.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeDelimiterRules() {
		List<IRule> rules = new ArrayList<IRule>();

		// The "[" delimiter must be followed by the "for" keyword or the "/" of a closing tag
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.FOR));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR));

		// The "/" of a body's end must be either preceded by "[" or followed by "]"
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, null));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_END_BODY_CHAR,
				IAcceleoConstants.DEFAULT_END));

		// The "]" character can be preceded by either ")", "}", or the "for" keyword
		rules.add(computeDelimiterRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.DEFAULT_END, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.BRACKETS_END, IAcceleoConstants.DEFAULT_END, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.FOR, IAcceleoConstants.DEFAULT_END, null));

		// Parenthesis are delimiter themselves if and only if the are preceded or followed by a keyword
		rules.add(computeDelimiterRule(IAcceleoConstants.FOR, IAcceleoConstants.PARENTHESIS_BEGIN, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.AFTER, IAcceleoConstants.PARENTHESIS_BEGIN, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.BEFORE, IAcceleoConstants.PARENTHESIS_BEGIN, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.SEPARATOR, IAcceleoConstants.PARENTHESIS_BEGIN, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.GUARD, IAcceleoConstants.PARENTHESIS_BEGIN, null));

		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.AFTER));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.BEFORE));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.SEPARATOR));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.GUARD));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END,
				IAcceleoConstants.BRACKETS_BEGIN));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.DEFAULT_END));

		// And brackets are delimiters too
		rules.add(computeDelimiterRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.BRACKETS_BEGIN,
				null));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.BRACKETS_END, IAcceleoConstants.DEFAULT_END));

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
		return new KeywordSequenceRule(precedingText, delimiter, followingText, createToken(AcceleoColor.FOR,
				null, SWT.BOLD));
	}

	/**
	 * Creates a rule matching the for variables.
	 * 
	 * @return the new delimiter rule
	 */
	private IRule computeVariableRule() {
		return new VariableRule(new String[] {}, createToken(AcceleoColor.VARIABLE, null, SWT.NONE));
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
	 * @see org.eclipse.acceleo.ide.ui.editors.template.scanner.AcceleoBlockScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return AcceleoPartitionScanner.ACCELEO_FOR;
	}
}
