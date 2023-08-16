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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstQuickFix implements IAstQuickFix {

	/**
	 * The name.
	 */
	private final String name;

	public AstQuickFix(String name) {
		this.name = name;
	}

	/**
	 * The {@link List} of {@link IAstTextReplacement}.
	 */
	private final List<IAstTextReplacement> textReplacements = new ArrayList<>();

	/**
	 * The {@link List} of {@link IAstResourceChange}.
	 */
	private final List<IAstResourceChange> resourceChanges = new ArrayList<>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IAstTextReplacement> getTextReplacements() {
		// TODO Auto-generated method stub
		return textReplacements;
	}

	@Override
	public List<IAstResourceChange> getResourceChanges() {
		return resourceChanges;
	}

}
