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
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordSequenceRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.VariableRule;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'if' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoIfScanner extends AbstractAcceleoScanner {
	/**
	 * Constructor.
	 */
	public AcceleoIfScanner() {
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN), new KeywordRule(
				IAcceleoConstants.LITERAL_END), new KeywordRule(IAcceleoConstants.LITERAL_ESCAPE), new Token(
				new TextAttribute(AcceleoColorManager.getColor(AcceleoColor.LITERAL)))));

		rules.add(new WhitespaceRule(new AcceleoWhitespaceDetector()));
		rules.addAll(computeKeywordRules());
		rules.addAll(computeDelimiterRules());
		rules.add(computeVariableRule());
		rules.addAll(computeOCLKeywordRules());

		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(new Token(new TextAttribute(AcceleoColorManager
				.getColor(AcceleoColor.OCL_EXPRESSION))));
	}

	/**
	 * Creates all keyword rules pertaining to an Acceleo If.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeKeywordRules() {
		List<IRule> rules = new ArrayList<IRule>();

		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IF, null));
		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.ELSE_IF, null));
		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.ELSE,
				IAcceleoConstants.DEFAULT_END));
		rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.IF,
				IAcceleoConstants.DEFAULT_END));

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
		return new KeywordSequenceRule(precedingDelimiter, keyword, followingDelimiter,
				new Token(new TextAttribute(AcceleoColorManager.getColor(AcceleoColor.IF), null, SWT.ITALIC
						| SWT.BOLD)));
	}

	/**
	 * Creates all delimiter rules pertaining to an Acceleo If.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeDelimiterRules() {
		List<IRule> rules = new ArrayList<IRule>();

		/*
		 * The "[" delimiter must be followed by the "if", "elseif" or "else" keywords... or the "/" of a
		 * closing tag
		 */
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IF));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.ELSE_IF));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.ELSE));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR));

		// The "/" of a body's end must be either preceded by "[" or followed by "]"
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, null));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_END_BODY_CHAR,
				IAcceleoConstants.DEFAULT_END));

		// The "]" is a keyword, wherever it is located
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_END, null));

		// Parenthesis are delimiter themselves if and only if the are preceded or followed by a keyword
		rules.add(computeDelimiterRule(IAcceleoConstants.IF, IAcceleoConstants.PARENTHESIS_BEGIN, null));
		rules.add(computeDelimiterRule(IAcceleoConstants.ELSE_IF, IAcceleoConstants.PARENTHESIS_BEGIN, null));

		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.DEFAULT_END));

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
		return new KeywordSequenceRule(precedingText, delimiter, followingText, new Token(new TextAttribute(
				AcceleoColorManager.getColor(AcceleoColor.IF), null, SWT.BOLD)));
	}

	/**
	 * Creates a rule matching the if variables.
	 * 
	 * @return the new delimiter rule
	 */
	private IRule computeVariableRule() {
		return new VariableRule(new String[] {}, new Token(new TextAttribute(AcceleoColorManager
				.getColor(AcceleoColor.VARIABLE), null, SWT.NONE)));
	}

	/**
	 * Computes a rule to match all OCL keywords in our query.
	 * 
	 * @return The created rules.
	 */
	private List<IRule> computeOCLKeywordRules() {
		List<IRule> rules = new ArrayList<IRule>();

		for (String keyword : AcceleoOCLReflection.getReservedKeywords()) {
			rules.add(new KeywordRule(keyword, true, false, new Token(new TextAttribute(AcceleoColorManager
					.getColor(AcceleoColor.OCL_KEYWORD), null, SWT.BOLD))));
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
		return AcceleoPartitionScanner.ACCELEO_IF;
	}
}
