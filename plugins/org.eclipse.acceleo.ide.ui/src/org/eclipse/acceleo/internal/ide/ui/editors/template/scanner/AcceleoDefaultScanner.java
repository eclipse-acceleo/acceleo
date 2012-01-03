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

import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

/**
 * A default scanner.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoDefaultScanner extends AbstractAcceleoScanner {
	/**
	 * Instantiates our scanner given the preference lookup order.
	 * 
	 * @param lookupOrder
	 *            Order in which to look preferences up.
	 */
	public AcceleoDefaultScanner(IEclipsePreferences[] lookupOrder) {
		super(lookupOrder);
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
		setRules(rules);
		setDefaultReturnToken(createToken(AcceleoColor.DEFAULT));
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
