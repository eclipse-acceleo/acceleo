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

import org.eclipse.jface.text.rules.RuleBasedScanner;

/**
 * A rule based scanner. All the scanners of the template editor should extend this class.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public abstract class AbstractAcceleoScanner extends RuleBasedScanner {

	/**
	 * Returns the type of the tokens read by this scanner.
	 * 
	 * @return the type of the tokens
	 */
	public abstract String getConfiguredContentType();

}
