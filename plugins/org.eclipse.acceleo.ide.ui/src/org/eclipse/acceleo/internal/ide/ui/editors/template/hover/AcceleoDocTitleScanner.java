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
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import org.eclipse.acceleo.internal.ide.ui.editors.template.ColorManager;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.IAcceleoColorConstants;
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
	 * 
	 * @param manager
	 *            is the color manager
	 */
	public AcceleoDocTitleScanner(ColorManager manager) {
		final Color green = manager.getColor(IAcceleoColorConstants.COMMENT);

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