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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.editors.template.ColorManager;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;

/**
 * A scanner for detecting 'comment' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCommentScanner extends AbstractAcceleoScanner {

	/**
	 * Constructor.
	 * 
	 * @param manager
	 *            is the color manager
	 */
	public AcceleoCommentScanner(ColorManager manager) {
		IRule[] rules = new IRule[1];
		rules[0] = new KeywordRule(IAcceleoConstants.TAG_MAIN, true, false, new Token(new TextAttribute(
				manager.getColor(IAcceleoColorConstants.COMMENT), null, SWT.BOLD)));
		setRules(rules);
		setDefaultReturnToken(new Token(new TextAttribute(manager.getColor(IAcceleoColorConstants.COMMENT))));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.editors.template.scanner.AbstractAcceleoScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return AcceleoPartitionScanner.ACCELEO_COMMENT;
	}

}
