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
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColorManager;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * The scanner of bold areas.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocBoldScanner extends AbstractAcceleoScanner {
	/**
	 * The default bold character.
	 */
	public static final String DEFAULT_BOLD = "*"; //$NON-NLS-1$

	/**
	 * The default start bold.
	 */
	public static final String START_BOLD = "<b>"; //$NON-NLS-1$

	/**
	 * The default end bold.
	 */
	public static final String END_BOLD = "</b>"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public AcceleoDocBoldScanner() {
		super(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner#createRules()
	 */
	@Override
	protected void createRules() {
		final Color black = AcceleoColorManager.getColor(AcceleoColor.BLACK);
		final IToken boldToken = new Token(new TextAttribute(black, null, SWT.BOLD));

		// IRule[] rules = new IRule[3];
		// rules[0] = new WhitespaceRule(new AcceleoWhitespaceDetector());
		// rules[1] = new MultiLineRule(DEFAULT_BOLD, DEFAULT_BOLD, boldToken);
		// rules[2] = new MultiLineRule(START_BOLD, END_BOLD, boldToken);
		// this.setRules(rules);

		this.setDefaultReturnToken(boldToken);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return AcceleoDocPartitionScanner.BOLD;
	}
}
