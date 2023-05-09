/*******************************************************************************
 * Copyright (c) 2015, 2022 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.completion;

import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * An {@link EEnumLiteral} {@link ICompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EEnumLiteralCompletionProposal implements ICompletionProposal {

	/**
	 * The {@link EEnumLiteral}.
	 */
	private final EEnumLiteral literal;

	/**
	 * Constructor.
	 * 
	 * @param literal
	 *            the {@link EEnumLiteral}
	 */
	public EEnumLiteralCompletionProposal(EEnumLiteral literal) {
		this.literal = literal;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getProposal()
	 */
	@Override
	public String getProposal() {
		final String packageName = AstBuilder.protectWithUnderscore(literal.getEEnum().getEPackage()
				.getName());
		final String enumName = AstBuilder.protectWithUnderscore(literal.getEEnum().getName());
		final String literalName = AstBuilder.protectWithUnderscore(literal.getName());
		return packageName + "::" + enumName + "::" + literalName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getCursorOffset()
	 */
	@Override
	public int getCursorOffset() {
		return getProposal().length();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getObject()
	 */
	@Override
	public EEnumLiteral getObject() {
		return literal;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getProposal();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getDescription()
	 */
	@Override
	public String getDescription() {
		StringBuffer result = new StringBuffer();
		result.append(literal.eClass().getName());
		result.append(" named ");
		result.append(literal.getName());
		result.append(" in ");
		result.append(literal.getEEnum().getName());
		result.append('(');
		result.append(literal.getEEnum().getEPackage().getNsURI());
		result.append(')');
		String doc = EcoreUtil.getDocumentation(literal);
		if (doc != null) {
			result.append('\n');
			result.append(doc);
		}
		return result.toString();

	}

}
