/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.completion;

import java.util.Set;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * A variable {@link ICompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class VariableCompletionProposal implements ICompletionProposal {

	/**
	 * The variable name.
	 */
	private final String varName;

	/**
	 * The {@link Set} of possible {@link IType}.
	 */
	private Set<IType> types;

	/**
	 * Constructor.
	 * 
	 * @param varName
	 *            the variable name
	 * @param types
	 *            the {@link Set} of possible {@link IType}
	 */
	public VariableCompletionProposal(String varName, Set<IType> types) {
		this.varName = varName;
		this.types = types;
	}

	@Override
	public String getProposal() {
		return varName;
	}

	@Override
	public int getCursorOffset() {
		return varName.length();
	}

	@Override
	public String getObject() {
		return varName;
	}

	@Override
	public String toString() {
		return getProposal();
	}

	@Override
	public String getDescription() {
		return "Variable " + varName + " = " + AQLUtils.getAqlTypeString(types);
	}

}
