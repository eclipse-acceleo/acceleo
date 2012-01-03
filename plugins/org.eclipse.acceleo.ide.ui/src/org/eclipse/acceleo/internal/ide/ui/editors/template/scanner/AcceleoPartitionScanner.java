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
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceRule;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * Global partition scanner that exclusively uses predicate rules.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoPartitionScanner extends RuleBasedPartitionScanner {

	/**
	 * Legal content type for a 'comment' part in the text. This data is attached to a 'comment' token, by the
	 * scanner.
	 */
	public static final String ACCELEO_COMMENT = "__ACCELEO_comment"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'template' part in the text. This data is attached to a 'template' token, by
	 * the scanner.
	 */
	public static final String ACCELEO_TEMPLATE = "__ACCELEO_template"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'query' part in the text. This data is attached to a 'query' token, by the
	 * scanner.
	 */
	public static final String ACCELEO_QUERY = "__ACCELEO_query"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'macro' part in the text. This data is attached to a 'macro' token, by the
	 * scanner.
	 */
	public static final String ACCELEO_MACRO = "__ACCELEO_macro"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'protected area' part in the text. This data is attached to a 'protected area'
	 * token, by the scanner.
	 */
	public static final String ACCELEO_PROTECTED_AREA = "__ACCELEO_protected_area"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'if' part in the text. This data is attached to a 'if' token, by the scanner.
	 */
	public static final String ACCELEO_IF = "__ACCELEO_if"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'let' part in the text. This data is attached to a 'let' token, by the
	 * scanner.
	 */
	public static final String ACCELEO_LET = "__ACCELEO_let"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'for' part in the text. This data is attached to a 'for' token, by the
	 * scanner.
	 */
	public static final String ACCELEO_FOR = "__ACCELEO_for"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'block' part in the text. This data is attached to a 'block' token, by the
	 * scanner.
	 */
	public static final String ACCELEO_BLOCK = "__ACCELEO_block"; //$NON-NLS-1$

	/**
	 * Legal content type for a 'documentation' part in the text. This data is attached to a 'documentation'
	 * token, by the scanner.
	 */
	public static final String ACCELEO_DOCUMENTATION = "__ACCELEO_documentation"; //$NON-NLS-1$

	/**
	 * All legal content types.
	 */
	public static final String[] LEGAL_CONTENT_TYPES = new String[] {ACCELEO_COMMENT, ACCELEO_TEMPLATE,
			ACCELEO_QUERY, ACCELEO_MACRO, ACCELEO_PROTECTED_AREA, ACCELEO_IF, ACCELEO_LET, ACCELEO_FOR,
			ACCELEO_BLOCK, ACCELEO_DOCUMENTATION, };

	/**
	 * Constructor.
	 */
	public AcceleoPartitionScanner() {
		List<IRule> rules = new ArrayList<IRule>();
		SequenceBlockRule literal = new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN),
				new KeywordRule(IAcceleoConstants.LITERAL_END), new KeywordRule(
						IAcceleoConstants.LITERAL_ESCAPE), new Token(IDocument.DEFAULT_CONTENT_TYPE));
		computeCommentRules(rules);
		computeDocumentationRules(rules);
		computeBehavioralFeatureRules(rules, literal);
		computeProtectedAreaRules(rules, literal);
		computeIfRules(rules, literal);
		computeLetRules(rules, literal);
		computeForRules(rules, literal);
		computeBlockRules(rules, literal);
		setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
	}

	/**
	 * Adds the 'documentation' rule.
	 * 
	 * @param rules
	 *            is the list of rules (output parameter)
	 */
	private void computeDocumentationRules(List<IRule> rules) {
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.DOCUMENTATION_BEGIN),
				new KeywordRule(IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {}, new Token(
						ACCELEO_DOCUMENTATION)));
	}

	/**
	 * Adds the 'comment' rule.
	 * 
	 * @param rules
	 *            is the list of rules (output parameter)
	 */
	private void computeCommentRules(List<IRule> rules) {
		SequenceRule beginComment = new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.COMMENT, IAcceleoConstants.DEFAULT_END, });
		SequenceRule endComment = new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.COMMENT,
				IAcceleoConstants.DEFAULT_END, });
		rules.add(new SequenceBlockRule(beginComment, endComment, new SequenceBlockRule[] {}, new Token(
				ACCELEO_COMMENT)));
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.COMMENT), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {}, new Token(ACCELEO_COMMENT)));
	}

	/**
	 * Adds the 'template|query|macro' rule.
	 * 
	 * @param rules
	 *            is the list of rules (output parameter)
	 * @param literal
	 *            is the 'literal' rule
	 */
	private void computeBehavioralFeatureRules(List<IRule> rules, SequenceBlockRule literal) {
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.TEMPLATE), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(
				ACCELEO_TEMPLATE)));
		rules.add(new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.TEMPLATE,
				IAcceleoConstants.DEFAULT_END, }, new Token(ACCELEO_TEMPLATE)));
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.QUERY), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(ACCELEO_QUERY)));
		rules.add(new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.QUERY,
				IAcceleoConstants.DEFAULT_END, }, new Token(ACCELEO_QUERY)));
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.MACRO), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(ACCELEO_MACRO)));
		rules.add(new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.MACRO,
				IAcceleoConstants.DEFAULT_END, }, new Token(ACCELEO_MACRO)));
	}

	/**
	 * Adds the 'protected area' rule.
	 * 
	 * @param rules
	 *            is the list of rules (output parameter)
	 * @param literal
	 *            is the 'literal' rule
	 */
	private void computeProtectedAreaRules(List<IRule> rules, SequenceBlockRule literal) {
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.PROTECTED_AREA), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(
				ACCELEO_PROTECTED_AREA)));
		rules.add(new SequenceBlockRule(new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.PROTECTED_AREA, }),
				new KeywordRule(IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal },
				new Token(ACCELEO_PROTECTED_AREA)));
	}

	/**
	 * Adds the 'if' rule.
	 * 
	 * @param rules
	 *            is the list of rules (output parameter)
	 * @param literal
	 *            is the 'literal' rule
	 */
	private void computeIfRules(List<IRule> rules, SequenceBlockRule literal) {
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.IF), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(ACCELEO_IF)));
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.ELSE_IF), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(ACCELEO_IF)));
		rules.add(new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.ELSE,
				IAcceleoConstants.DEFAULT_END, }, new Token(ACCELEO_IF)));
		rules.add(new SequenceRule(
				new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.DEFAULT_END_BODY_CHAR,
						IAcceleoConstants.IF, IAcceleoConstants.DEFAULT_END, }, new Token(ACCELEO_IF)));
	}

	/**
	 * Adds the 'let' rule.
	 * 
	 * @param rules
	 *            is the list of rules (output parameter)
	 * @param literal
	 *            is the 'literal' rule
	 */
	private void computeLetRules(List<IRule> rules, SequenceBlockRule literal) {
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.LET), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(ACCELEO_LET)));
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.ELSE_LET), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(ACCELEO_LET)));
		rules.add(new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.ELSE,
				IAcceleoConstants.DEFAULT_END, }, new Token(ACCELEO_LET)));
		rules.add(new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.LET,
				IAcceleoConstants.DEFAULT_END, }, new Token(ACCELEO_LET)));
	}

	/**
	 * Adds the 'for' rule.
	 * 
	 * @param rules
	 *            is the list of rules (output parameter)
	 * @param literal
	 *            is the 'literal' rule
	 */
	private void computeForRules(List<IRule> rules, SequenceBlockRule literal) {
		rules.add(new SequenceBlockRule(beginSequence(IAcceleoConstants.FOR), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(ACCELEO_FOR)));
		rules.add(new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.DEFAULT_END_BODY_CHAR, IAcceleoConstants.FOR,
				IAcceleoConstants.DEFAULT_END, }, new Token(ACCELEO_FOR)));
	}

	/**
	 * Adds the 'block' rule.
	 * 
	 * @param rules
	 *            is the list of rules (output parameter)
	 * @param literal
	 *            is the 'literal' rule
	 */
	private void computeBlockRules(List<IRule> rules, SequenceBlockRule literal) {
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.DEFAULT_BEGIN), new KeywordRule(
				IAcceleoConstants.DEFAULT_END), new SequenceBlockRule[] {literal }, new Token(ACCELEO_BLOCK)));
	}

	/**
	 * Creates a begin sequence for a keyword : <code>[keyword, [for, [if</code>.
	 * 
	 * @param keyword
	 *            is the name of the keyword
	 * @return a new begin sequence
	 */
	private SequenceRule beginSequence(String keyword) {
		return new SequenceRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN, keyword });
	}

}
