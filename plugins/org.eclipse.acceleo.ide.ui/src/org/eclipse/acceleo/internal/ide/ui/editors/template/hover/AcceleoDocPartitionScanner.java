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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * The partition scanner of the AcceleoDoc hover popup.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocPartitionScanner extends RuleBasedPartitionScanner {

	/**
	 * The AcceleoDoc partitioning ID.
	 */
	public static final String PARTITIONING = "org.eclipse.acceleo.ide.ui.editor.hover.acceleodoc.partitioning"; //$NON-NLS-1$

	/**
	 * Token of the bold partition.
	 */
	public static final String BOLD = "__ACCELEO_DOC_BOLD"; //$NON-NLS-1$

	/**
	 * Token of the title partition.
	 */
	public static final String TITLE = "__ACCELEO_DOC_TITLE"; //$NON-NLS-1$

	/**
	 * The different type of partition.
	 */
	public static final String[] TYPES = new String[] {IDocument.DEFAULT_CONTENT_TYPE, BOLD, TITLE };

	/**
	 * The constructor.
	 */
	public AcceleoDocPartitionScanner() {
		IPredicateRule[] predicateRules = new IPredicateRule[] {
				new MultiLineRule(AcceleoDocBoldScanner.DEFAULT_BOLD, AcceleoDocBoldScanner.DEFAULT_BOLD,
						new Token(BOLD)),
				new MultiLineRule(AcceleoDocBoldScanner.START_BOLD, AcceleoDocBoldScanner.END_BOLD,
						new Token(BOLD)), new EndOfLineRule(AcceleoDocTitleScanner.H1, new Token(TITLE)), };
		this.setPredicateRules(predicateRules);
	}

}
