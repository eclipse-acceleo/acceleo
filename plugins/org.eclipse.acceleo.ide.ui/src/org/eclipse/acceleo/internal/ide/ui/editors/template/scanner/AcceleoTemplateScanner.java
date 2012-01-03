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
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.OverrideNameRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.VariableRule;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'template' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoTemplateScanner extends AbstractAcceleoScanner {
	/**
	 * Instantiates our scanner given the preference lookup order.
	 * 
	 * @param lookupOrder
	 *            Order in which to look preferences up.
	 */
	public AcceleoTemplateScanner(IEclipsePreferences[] lookupOrder) {
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
		rules.add(computeTemplateNameRule());
		rules.add(computeOverrideNameRule());
		rules.addAll(computeOCLKeywordRules());

		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(createToken(AcceleoColor.TEMPLATE_OCL_EXPRESSION));
	}

	/**
	 * Creates the rule for the first parenthesis of the signature. Needs special handling as it is not
	 * preceded nor followed by a predictable keyword.
	 * 
	 * @return The created rule.
	 */
	private IRule computeFirstParenthesisRule() {
		return new FirstParenthesisRule(IAcceleoConstants.TEMPLATE, createToken(AcceleoColor.TEMPLATE, null,
				SWT.BOLD));
	}

	/**
	 * Creates all keyword rules pertaining to an Acceleo Template.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeKeywordRules() {
		List<IRule> rules = new ArrayList<IRule>();

		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.TEMPLATE, null));
		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.TEMPLATE,
				IAcceleoConstants.DEFAULT_END));
		rules.add(computeKeywordRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.OVERRIDES, null));
		rules.add(computeKeywordRule(null, IAcceleoConstants.GUARD, IAcceleoConstants.PARENTHESIS_BEGIN));
		rules.add(computeKeywordRule(null, IAcceleoConstants.POST, IAcceleoConstants.PARENTHESIS_BEGIN));

		// Visibility keywords
		rules.add(computeKeywordRule(IAcceleoConstants.TEMPLATE, IAcceleoConstants.VISIBILITY_KIND_PUBLIC,
				null));
		rules.add(computeKeywordRule(IAcceleoConstants.TEMPLATE, IAcceleoConstants.VISIBILITY_KIND_PROTECTED,
				null));
		rules.add(computeKeywordRule(IAcceleoConstants.TEMPLATE, IAcceleoConstants.VISIBILITY_KIND_PRIVATE,
				null));

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
				AcceleoColor.TEMPLATE, null, SWT.BOLD));
	}

	/**
	 * Creates all delimiter rules pertaining to an Acceleo Template.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeDelimiterRules() {
		List<IRule> rules = new ArrayList<IRule>();

		// The "[" delimiter must be followed by the "template" keyword or the "/" of a closing tag
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.TEMPLATE));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR));

		// The "/" of a body's end must be either preceded by "[" or followed by "]"
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, null));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_END_BODY_CHAR,
				IAcceleoConstants.DEFAULT_END));

		// The "]" character can be preceded by either ")", "}", or the "template" keyword
		rules.add(computeDelimiterRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.DEFAULT_END, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.BRACKETS_END, IAcceleoConstants.DEFAULT_END, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.TEMPLATE, IAcceleoConstants.DEFAULT_END, null));

		// Parenthesis are delimiter themselves if and only if the are preceded or followed by a keyword
		rules.add(computeDelimiterRule(IAcceleoConstants.TEMPLATE, IAcceleoConstants.PARENTHESIS_BEGIN, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.OVERRIDES, IAcceleoConstants.PARENTHESIS_BEGIN, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.GUARD, IAcceleoConstants.PARENTHESIS_BEGIN, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.POST, IAcceleoConstants.PARENTHESIS_BEGIN, null));

		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.OVERRIDES));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.GUARD));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.POST));
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
		return new KeywordSequenceRule(precedingText, delimiter, followingText, createToken(
				AcceleoColor.TEMPLATE, null, SWT.BOLD));
	}

	/**
	 * Computes a rule matching the template name.
	 * 
	 * @return The created rule.
	 */
	private IRule computeTemplateNameRule() {
		return new BlockNameRule(IAcceleoConstants.TEMPLATE, createToken(AcceleoColor.TEMPLATE_NAME, null,
				SWT.NONE));
	}

	/**
	 * Computes a rule matching the overridden template name.
	 * 
	 * @return The created rule.
	 */
	private IRule computeOverrideNameRule() {
		return new OverrideNameRule(createToken(AcceleoColor.TEMPLATE_NAME, null, SWT.NONE));
	}

	/**
	 * Creates a rule matching the template parameters.
	 * 
	 * @return the new variable rule
	 */
	private IRule computeVariableRule() {
		return new VariableRule(new String[] {}, createToken(AcceleoColor.TEMPLATE_PARAMETER, null, SWT.NONE));
	}

	/**
	 * Computes a rule to match all OCL keywords in our query.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeOCLKeywordRules() {
		List<IRule> rules = new ArrayList<IRule>();

		for (String keyword : AcceleoOCLReflection.getReservedKeywords()) {
			rules.add(new KeywordRule(keyword, true, false, createToken(AcceleoColor.TEMPLATE_OCL_KEYWORD,
					null, SWT.BOLD)));
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
		return AcceleoPartitionScanner.ACCELEO_TEMPLATE;
	}
}
