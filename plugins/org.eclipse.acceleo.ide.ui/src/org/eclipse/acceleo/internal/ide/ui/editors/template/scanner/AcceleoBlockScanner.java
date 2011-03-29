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
 * This scanner will be in charge of normal blocks.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoBlockScanner extends AbstractAcceleoScanner {
	/**
	 * List of the potential keywords in a "regular" Acceleo block. "Regular" blocks are those that do not
	 * have their own scanner. "Keyword" in these case all need to be directly preceded by "[" or "[/", except
	 * for "extends" which will have its own rule.
	 */
	private static final String[] KEYWORDS = {IAcceleoConstants.MODULE, IAcceleoConstants.IMPORT,
			IAcceleoConstants.TRACE, IAcceleoConstants.FILE, IAcceleoConstants.SUPER, };

	/**
	 * Constructor.
	 * 
	 * @param manager
	 *            is the color manager
	 */
	public AcceleoBlockScanner(AcceleoColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN), new KeywordRule(
				IAcceleoConstants.LITERAL_END), new KeywordRule(IAcceleoConstants.LITERAL_ESCAPE), new Token(
				new TextAttribute(manager.getColor(AcceleoColor.LITERAL)))));

		rules.add(new WhitespaceRule(new AcceleoWhitespaceDetector()));
		rules.addAll(computeKeywordRules(manager));
		rules.addAll(computeDelimiterRules(manager));
		rules.add(computeVariableRule(manager));
		rules.addAll(computeModuleNameRules(manager));
		rules.addAll(computeOCLKeywordRules(manager));

		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(new Token(new TextAttribute(manager.getColor(AcceleoColor.OCL_EXPRESSION),
				null, SWT.NONE)));
	}

	/**
	 * Creates all keyword rules pertaining to an Acceleo regular block (neither template, query, macro, for,
	 * let, nor if).
	 * 
	 * @param manager
	 *            is the color manager
	 * @return The created rules.
	 */
	private List<IRule> computeKeywordRules(AcceleoColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();

		// "block" keywords : these are the blocks that do not have their own scanner.
		for (String keyword : KEYWORDS) {
			rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_BEGIN, keyword, null, manager));
			rules.add(computeKeywordRule(IAcceleoConstants.DEFAULT_END_BODY_CHAR, keyword,
					IAcceleoConstants.DEFAULT_END, manager));
		}

		// "extends" keyword will follow a closing parenthesis
		rules.add(computeKeywordRule(new String[] {IAcceleoConstants.PARENTHESIS_END, },
				IAcceleoConstants.EXTENDS, null, manager));

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
				new TextAttribute(manager.getColor(AcceleoColor.KEYWORD), null, SWT.BOLD)));
	}

	/**
	 * Creates a rule for the given keyword.
	 * 
	 * @param precedingWords
	 *            The words that needs to precede the keyword. Can be <code>null</code>.
	 * @param keyword
	 *            The delimiter we need to detect.
	 * @param followingWords
	 *            The words that needs to follow the keyword. Can be <code>null</code>.
	 * @param manager
	 *            is the color manager
	 * @return the new keyword rule
	 */
	private IRule computeKeywordRule(String[] precedingWords, String keyword, String[] followingWords,
			AcceleoColorManager manager) {
		return new KeywordSequenceRule(precedingWords, keyword, followingWords, new Token(new TextAttribute(
				manager.getColor(AcceleoColor.KEYWORD), null, SWT.BOLD)));
	}

	/**
	 * Creates all delimiter rules pertaining to an Acceleo regular block (neither template, query, macro,
	 * for, let, nor if).
	 * 
	 * @param manager
	 *            The color manager.
	 * @return The created rules.
	 */
	private List<IRule> computeDelimiterRules(AcceleoColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();

		// Take care of all the rules that need to iterate on the keywords
		for (String keyword : KEYWORDS) {
			// The "[" is a delimiter if before a keyword or the "/" of a closing tag
			rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_BEGIN, keyword, manager));

			// The "]" character can be preceded by either ")", "}", or a keyword
			rules.add(computeDelimiterRule(keyword, IAcceleoConstants.DEFAULT_END, null, manager));

			// Parenthesis are delimiter themselves if and only if the are preceded or followed by a keyword
			rules.add(computeDelimiterRule(keyword, IAcceleoConstants.PARENTHESIS_BEGIN, null, manager));
			rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END, keyword, manager));
		}

		// The "[" is a delimiter wherever it might be
		rules.add(computeDelimiterRule((String)null, IAcceleoConstants.DEFAULT_BEGIN, null, manager));

		// The "/" of a body's end must be either preceded by "[" or followed by "]"
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, null, manager));
		rules.add(computeDelimiterRule(null, IAcceleoConstants.DEFAULT_END_BODY_CHAR,
				IAcceleoConstants.DEFAULT_END, manager));

		// parenthesis are delimiters if directly followed by a closing tag
		rules.add(computeDelimiterRule(null, IAcceleoConstants.PARENTHESIS_END,
				IAcceleoConstants.DEFAULT_END, manager));

		// The "]" character can be preceded by either ")", "}", or "/"
		rules.add(computeDelimiterRule(IAcceleoConstants.PARENTHESIS_END, IAcceleoConstants.DEFAULT_END,
				null, manager));
		rules.add(computeDelimiterRule(IAcceleoConstants.BRACKETS_END, IAcceleoConstants.DEFAULT_END, null,
				manager));
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_END_BODY_CHAR,
				IAcceleoConstants.DEFAULT_END, null, manager));

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
				manager.getColor(AcceleoColor.KEYWORD), null, SWT.BOLD)));
	}

	/**
	 * Creates a rule matching the template parameters.
	 * 
	 * @param manager
	 *            is the color manager
	 * @return the new variable rule
	 */
	private IRule computeVariableRule(AcceleoColorManager manager) {
		return new VariableRule(new String[] {}, new Token(new TextAttribute(manager
				.getColor(AcceleoColor.VARIABLE), null, SWT.NONE)));
	}

	/**
	 * Computes a rule matching the module name.
	 * 
	 * @param manager
	 *            The color manager.
	 * @return The created rule.
	 */
	private List<IRule> computeModuleNameRules(AcceleoColorManager manager) {
		/*
		 * Module names can be preceded by either "module", "import" or "extends". "Extends" is handled by the
		 * "module" rule.
		 */
		List<IRule> rules = new ArrayList<IRule>();

		rules.add(new BlockNameRule(IAcceleoConstants.MODULE, new Token(new TextAttribute(manager
				.getColor(AcceleoColor.MODULE_NAME), null, SWT.NONE))));
		rules.add(new BlockNameRule(IAcceleoConstants.IMPORT, new Token(new TextAttribute(manager
				.getColor(AcceleoColor.MODULE_NAME), null, SWT.NONE))));

		return rules;
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
		return AcceleoPartitionScanner.ACCELEO_BLOCK;
	}
}
