/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
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
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
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
	public AcceleoQueryScanner(ColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN), new KeywordRule(
				IAcceleoConstants.LITERAL_END), new KeywordRule(IAcceleoConstants.LITERAL_ESCAPE), new Token(
				new TextAttribute(manager.getColor(IAcceleoColorConstants.LITERAL)))));
		rules.add(new WhitespaceRule(new AcceleoWhitespaceDetector()));
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN, manager));
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_END, manager));
		rules.add(computeKeywordRule(IAcceleoConstants.QUERY, manager));

		rules.add(computeKeywordRule(IAcceleoConstants.SELF, manager));
		rules.add(computeKeywordRule(IAcceleoConstants.VISIBILITY_KIND_PUBLIC, manager));
		rules.add(computeKeywordRule(IAcceleoConstants.VISIBILITY_KIND_PROTECTED, manager));
		rules.add(computeKeywordRule(IAcceleoConstants.VISIBILITY_KIND_PRIVATE, manager));

		// OCL keywords
		rules.add(computeKeywordRule("if", manager)); //$NON-NLS-1$
		rules.add(computeKeywordRule("then", manager)); //$NON-NLS-1$
		rules.add(computeKeywordRule("else", manager)); //$NON-NLS-1$
		rules.add(computeKeywordRule("endif", manager)); //$NON-NLS-1$
		rules.add(computeKeywordRule("and", manager)); //$NON-NLS-1$
		rules.add(computeKeywordRule("or", manager)); //$NON-NLS-1$
		rules.add(computeKeywordRule("not", manager)); //$NON-NLS-1$

		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(new Token(new TextAttribute(manager.getColor(IAcceleoColorConstants.DEFAULT))));
	}

	/**
	 * Creates a rule for the given keyword.
	 * 
	 * @param keyword
	 *            is the keyword
	 * @param manager
	 *            is the color manager
	 * @return the new keyword rule
	 */
	private IRule computeKeywordRule(String keyword, ColorManager manager) {
		return new KeywordRule(keyword, true, false, new Token(new TextAttribute(manager
				.getColor(IAcceleoColorConstants.QUERY), null, SWT.BOLD)));
	}

	/**
	 * Creates a rule for the given delimiter.
	 * 
	 * @param delimiter
	 *            is the delimiter text
	 * @param manager
	 *            is the color manager
	 * @return the new delimiter rule
	 */
	private IRule computeDelimiterRule(String delimiter, ColorManager manager) {
		return new KeywordRule(delimiter, false, false, new Token(new TextAttribute(manager
				.getColor(IAcceleoColorConstants.QUERY), null, SWT.BOLD)));
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
