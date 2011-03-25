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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoColorManager;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'documentation' sequences.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocumentationScanner extends AbstractAcceleoScanner {

	/**
	 * The constructor.
	 * 
	 * @param manager
	 *            is the color manager
	 */
	public AcceleoDocumentationScanner(AcceleoColorManager manager) {
		// indices declaration, viva checkstyle
		final int seven = 7;
		final int eight = 8;
		final int nine = 9;

		IRule[] rules = new IRule[nine];
		rules[0] = new KeywordRule(IAcceleoConstants.TAG_AUTHOR, true, false, new Token(new TextAttribute(
				manager.getColor(IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		rules[1] = new KeywordRule(IAcceleoConstants.TAG_DEPRECATED, true, false, new Token(
				new TextAttribute(manager.getColor(
						IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		rules[2] = new KeywordRule(IAcceleoConstants.TAG_INHERITDOC, true, false, new Token(
				new TextAttribute(manager.getColor(
						IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		rules[3] = new KeywordRule(IAcceleoConstants.TAG_PARAM, true, false, new Token(new TextAttribute(
				manager.getColor(IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		rules[4] = new KeywordRule(IAcceleoConstants.TAG_SINCE, true, false, new Token(new TextAttribute(
				manager.getColor(IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		rules[5] = new KeywordRule(IAcceleoConstants.TAG_VERSION, true, false, new Token(new TextAttribute(
				manager.getColor(IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		rules[6] = new KeywordRule(IAcceleoConstants.TAG_FIXME, true, false, new Token(new TextAttribute(
				manager.getColor(IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		rules[seven] = new KeywordRule(IAcceleoConstants.TAG_TODO, true, false, new Token(new TextAttribute(
				manager.getColor(IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		rules[eight] = new KeywordRule(IAcceleoConstants.TAG_MAIN, true, false, new Token(new TextAttribute(
				manager.getColor(IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY,
						IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		setRules(rules);
		setDefaultReturnToken(new Token(new TextAttribute(manager.getColor(
				IAcceleoColorConstants.ACCELEO_COLOR_COMMENT_PREFERENCE_KEY, IAcceleoColorConstants.COMMENT))));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.editors.template.scanner.AbstractAcceleoScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return AcceleoPartitionScanner.ACCELEO_DOCUMENTATION;
	}
}
