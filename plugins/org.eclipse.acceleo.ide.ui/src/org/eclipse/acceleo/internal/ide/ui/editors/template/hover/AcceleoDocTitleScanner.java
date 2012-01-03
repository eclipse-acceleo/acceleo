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
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;

/**
 * the scanner of h1 title areas.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocTitleScanner extends AbstractAcceleoScanner {
	/**
	 * The h1 start sequence.
	 */
	public static final String H1 = "h1. "; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public AcceleoDocTitleScanner() {
		super(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner#createRules()
	 */
	@Override
	protected void createRules() {
		final Color green = AcceleoColorManager.getColor(AcceleoColor.BLACK);

		// IRule[] rules = new IRule[2];
		// rules[0] = new WhitespaceRule(new AcceleoWhitespaceDetector());
		// rules[1] = new EndOfLineRule(H1, new Token(new TextAttribute(green, null, SWT.BOLD)));
		// this.setRules(rules);

		this.setDefaultReturnToken(new Token(new TextAttribute(green)));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return AcceleoDocPartitionScanner.TITLE;
	}

}
