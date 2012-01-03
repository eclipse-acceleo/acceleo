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
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'macro' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoMacroScanner extends AbstractAcceleoScanner {
	/**
	 * Instantiates our scanner given the preference lookup order.
	 * 
	 * @param lookupOrder
	 *            Order in which to look preferences up.
	 */
	public AcceleoMacroScanner(IEclipsePreferences[] lookupOrder) {
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
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_BEGIN));
		rules.add(computeDelimiterRule(IAcceleoConstants.DEFAULT_END));
		rules.add(computeKeywordRule(IAcceleoConstants.MACRO));

		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(createToken(AcceleoColor.MACRO));
	}

	/**
	 * Creates a rule for the given keyword.
	 * 
	 * @param keyword
	 *            is the keyword
	 * @return the new keyword rule
	 */
	private IRule computeKeywordRule(String keyword) {
		return new KeywordRule(keyword, true, false, createToken(AcceleoColor.MACRO, null, SWT.BOLD));
	}

	/**
	 * Creates a rule for the given delimiter.
	 * 
	 * @param delimiter
	 *            is the delimiter text
	 * @return the new delimiter rule
	 */
	private IRule computeDelimiterRule(String delimiter) {
		return new KeywordRule(delimiter, false, false, createToken(AcceleoColor.MACRO, null, SWT.BOLD));
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
