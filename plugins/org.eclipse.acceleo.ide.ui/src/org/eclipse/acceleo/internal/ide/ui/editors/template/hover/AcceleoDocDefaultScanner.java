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
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoWhitespaceDetector;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

/**
 * A default scanner.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocDefaultScanner extends AbstractAcceleoScanner {
	/**
	 * Constructor.
	 */
	public AcceleoDocDefaultScanner() {
		super(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner#createRules()
	 */
	@Override
	protected void createRules() {
		IRule[] rules = new IRule[1];
		rules[0] = new WhitespaceRule(new AcceleoWhitespaceDetector());
		this.setRules(rules);
		this.setDefaultReturnToken(new Token(new TextAttribute(AcceleoColorManager
				.getColor(AcceleoColor.BLACK))));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.editors.template.scanner.AbstractAcceleoScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return IDocument.DEFAULT_CONTENT_TYPE;
	}
}
