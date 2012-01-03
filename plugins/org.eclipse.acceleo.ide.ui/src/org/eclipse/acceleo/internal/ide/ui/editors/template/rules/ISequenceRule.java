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
package org.eclipse.acceleo.internal.ide.ui.editors.template.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;

/**
 * Defines the interface for a rule used in the scanning of a Acceleo file (document partitioning and text
 * styling).
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface ISequenceRule extends IPredicateRule {

	/**
	 * Evaluates the rule by examining the characters available from the provided character scanner.
	 * 
	 * @param scanner
	 *            is the character scanner to be used by this rule
	 * @return the number of examined characters
	 */
	int read(ICharacterScanner scanner);

}
