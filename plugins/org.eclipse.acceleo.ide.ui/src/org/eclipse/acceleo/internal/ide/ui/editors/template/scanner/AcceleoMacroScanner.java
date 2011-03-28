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
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColorManager;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'macro' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoMacroScanner extends AbstractAcceleoScanner {

	/**
	 * Constructor.
	 * 
	 * @param manager
	 *            is the color manager
	 */
	public AcceleoMacroScanner(AcceleoColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN), new KeywordRule(
				IAcceleoConstants.LITERAL_END), new KeywordRule(IAcceleoConstants.LITERAL_ESCAPE), new Token(
				new TextAttribute(manager.getColor(AcceleoColor.LITERAL)))));
		rules.add(new WhitespaceRule(new AcceleoWhitespaceDetector()));
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN, manager));
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_END, manager));
		rules.add(computeKeywordRule(IAcceleoConstants.MACRO, manager));
		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(new Token(new TextAttribute(manager.getColor(AcceleoColor.MACRO))));
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
	private IRule computeKeywordRule(String keyword, AcceleoColorManager manager) {
		return new KeywordRule(keyword, true, false, new Token(new TextAttribute(manager
				.getColor(AcceleoColor.MACRO), null, SWT.BOLD)));
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
	private IRule computeDelimiterRule(String delimiter, AcceleoColorManager manager) {
		return new KeywordRule(delimiter, false, false, new Token(new TextAttribute(manager
				.getColor(AcceleoColor.MACRO), null, SWT.BOLD)));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.editors.template.scanner.AbstractAcceleoScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return AcceleoPartitionScanner.ACCELEO_MACRO;
	}

}
