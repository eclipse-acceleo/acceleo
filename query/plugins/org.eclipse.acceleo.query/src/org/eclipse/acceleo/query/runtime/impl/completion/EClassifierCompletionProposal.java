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
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * An {@link EClassifier} {@link ICompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EClassifierCompletionProposal implements ICompletionProposal {

	/**
	 * The {@link EClassifier}.
	 */
	private final EClassifier eClassifier;

	/**
	 * Constructor.
	 * 
	 * @param eClassifier
	 *            the {@link EClassifier}
	 */
	public EClassifierCompletionProposal(EClassifier eClassifier) {
		this.eClassifier = eClassifier;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getProposal()
	 */
	@Override
	public String getProposal() {
		final String packageName = AstBuilder.protectWithUnderscore(eClassifier.getEPackage().getName());
		final String classifierName = AstBuilder.protectWithUnderscore(eClassifier.getName());
		return packageName + "::" + classifierName;
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
	public EClassifier getObject() {
		return eClassifier;
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
		result.append(eClassifier.eClass().getName());
		result.append(" named ");
		result.append(eClassifier.getName());
		result.append(" in ");
		result.append(eClassifier.getEPackage().getNsPrefix());
		result.append('(');
		result.append(eClassifier.getEPackage().getNsURI());
		result.append(')');
		String doc = EcoreUtil.getDocumentation(eClassifier);
		if (doc != null) {
			result.append('\n');
			result.append(doc);
		}
		return result.toString();
	}
}
