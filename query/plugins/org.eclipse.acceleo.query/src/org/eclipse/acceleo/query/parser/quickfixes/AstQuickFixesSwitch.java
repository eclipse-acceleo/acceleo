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

import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.query.parser.Positions;

/**
 * Provides a {@link List} of {@link IAstQuickFix}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstQuickFixesSwitch extends AstSwitch<List<IAstQuickFix>> {

	/**
	 * The {@link Positions}.
	 */
	private final Positions<ASTNode> positions;

	/**
	 * Constructor.
	 * 
	 * @param positions
	 *            the {@link Positions}.
	 */
	public AstQuickFixesSwitch(Positions<ASTNode> positions) {
		this.positions = positions;
	}

	// TODO see https://github.com/eclipse-acceleo/acceleo/issues/106

}
