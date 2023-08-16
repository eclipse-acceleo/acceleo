/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.quickfixes;

import java.util.List;

/**
 * A quick fix that will perform a {@link List} of {@link IAstResourceChange} then a {@link List} of
 * {@link IAstTextReplacement}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAstQuickFix {
	/**
	 * Gets the name of this fix.
	 * 
	 * @return the name of this fix
	 */
	String getName();

	List<IAstTextReplacement> getTextReplacements();

	List<IAstResourceChange> getResourceChanges();

}
