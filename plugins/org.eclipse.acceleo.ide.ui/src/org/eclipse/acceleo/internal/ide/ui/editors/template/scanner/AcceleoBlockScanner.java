/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
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
import org.eclipse.acceleo.internal.ide.ui.editors.template.ColorManager;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.FirstVariableRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * A scanner for detecting block sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoBlockScanner extends AbstractAcceleoScanner {

	/**
	 * All the keywords sequences of the concrete syntax.
	 */
	private static final String[] KEYWORDS = {IAcceleoConstants.MODULE, IAcceleoConstants.COMMENT,
			IAcceleoConstants.IMPORT, IAcceleoConstants.EXTENDS, IAcceleoConstants.OVERRIDES,
			IAcceleoConstants.TEMPLATE, IAcceleoConstants.VISIBILITY_KIND_PUBLIC,
			IAcceleoConstants.VISIBILITY_KIND_PROTECTED, IAcceleoConstants.VISIBILITY_KIND_PRIVATE,
			IAcceleoConstants.QUERY, IAcceleoConstants.FOR, IAcceleoConstants.BEFORE,
			IAcceleoConstants.AFTER, IAcceleoConstants.SEPARATOR, IAcceleoConstants.GUARD,
			IAcceleoConstants.IF, IAcceleoConstants.ELSE_IF, IAcceleoConstants.ELSE, IAcceleoConstants.LET,
			IAcceleoConstants.ELSE_LET, IAcceleoConstants.TRACE, IAcceleoConstants.MACRO,
			IAcceleoConstants.FILE, IAcceleoConstants.PROTECTED_AREA, IAcceleoConstants.SELF,
			IAcceleoConstants.SUPER, };

	/**
	 * All the delimiters sequences of the concrete syntax. <code>[, ], [/, /]</code>
	 */
	private static final String[] DELIMITERS = {
			IAcceleoConstants.DEFAULT_BEGIN + IAcceleoConstants.DEFAULT_END_BODY_CHAR,
			IAcceleoConstants.DEFAULT_END_BODY_CHAR + IAcceleoConstants.DEFAULT_END,
			IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.DEFAULT_END, };

	/**
	 * Constructor.
	 * 
	 * @param manager
	 *            is the color manager
	 */
	public AcceleoBlockScanner(ColorManager manager) {
		this(manager, false);
	}

	/**
	 * Constructor.
	 * 
	 * @param manager
	 *            is the color manager
	 * @param italic
	 *            indicates if the italic style must be used
	 */
	public AcceleoBlockScanner(ColorManager manager, boolean italic) {
		IRule[] rules = getRules(manager, italic);
		setRules(rules);
		setDefaultReturnToken(getDefaultReturnToken(manager, italic));
	}

	/**
	 * Gets the rules of the scanner.
	 * 
	 * @param manager
	 *            is the color manager
	 * @param italic
	 *            indicates if the italic style must be used
	 * @return the rules of the scanner
	 */
	protected IRule[] getRules(ColorManager manager, boolean italic) {
		int style;
		if (italic) {
			style = SWT.ITALIC;
		} else {
			style = SWT.NONE;
		}
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN), new KeywordRule(
				IAcceleoConstants.LITERAL_END), new KeywordRule(IAcceleoConstants.LITERAL_ESCAPE), new Token(
				new TextAttribute(manager.getColor(IAcceleoColorConstants.LITERAL), null, style))));
		computeKeywordRules(rules, manager, italic);
		computeDelimitersRules(rules, manager, italic);
		Color foreGroundColor = manager.getColor(IAcceleoColorConstants.KEYWORD);
		Color backGroundColor = manager.getColor(IAcceleoColorConstants.FIRST_VARIABLE);
		rules.add(new FirstVariableRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.FOR,
				IAcceleoConstants.PARENTHESIS_BEGIN, }, new Token(new TextAttribute(foreGroundColor,
				backGroundColor, style))));
		rules.add(new FirstVariableRule(
				new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.LET }, new Token(
						new TextAttribute(foreGroundColor, backGroundColor, style))));
		rules.add(new FirstVariableRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.ELSE_LET, }, new Token(new TextAttribute(foreGroundColor, backGroundColor,
				style))));
		rules.add(new WhitespaceRule(new AcceleoWhitespaceDetector()));
		return rules.toArray(new IRule[rules.size()]);
	}

	/**
	 * Creates a rule for each Acceleo keyword.
	 * 
	 * @param rules
	 *            is a list of rules (output parameter)
	 * @param manager
	 *            is the color manager
	 * @param italic
	 *            indicates if the italic style must be used
	 */
	private void computeKeywordRules(List<IRule> rules, ColorManager manager, boolean italic) {
		int style;
		if (italic) {
			style = SWT.ITALIC | SWT.BOLD;
		} else {
			style = SWT.BOLD;
		}
		for (int i = 0; i < KEYWORDS.length; i++) {
			String keyword = KEYWORDS[i];
			rules.add(new KeywordRule(keyword, true, false, new Token(new TextAttribute(manager
					.getColor(IAcceleoColorConstants.KEYWORD), null, style))));
		}
	}

	/**
	 * Creates a rule for each Acceleo delimiter. <code>[, ], [/, /]</code>
	 * 
	 * @param rules
	 *            is a list of rules (output parameter)
	 * @param manager
	 *            is the color manager
	 * @param italic
	 *            indicates if the italic style must be used
	 */
	private void computeDelimitersRules(List<IRule> rules, ColorManager manager, boolean italic) {
		int style;
		if (italic) {
			style = SWT.ITALIC | SWT.BOLD;
		} else {
			style = SWT.BOLD;
		}
		for (int i = 0; i < DELIMITERS.length; i++) {
			String keyword = DELIMITERS[i];
			rules.add(new KeywordRule(keyword, false, false, new Token(new TextAttribute(manager
					.getColor(IAcceleoColorConstants.KEYWORD), null, style))));
		}
	}

	/**
	 * Gets the default token of the scanner.
	 * 
	 * @param manager
	 *            is the color manager
	 * @param italic
	 *            indicates if the italic style must be used
	 * @return the default token
	 */
	protected Token getDefaultReturnToken(ColorManager manager, boolean italic) {
		int style;
		if (italic) {
			style = SWT.ITALIC;
		} else {
			style = SWT.NONE;
		}
		return new Token(new TextAttribute(manager.getColor(IAcceleoColorConstants.BLOCK), null, style));
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
