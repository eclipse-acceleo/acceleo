/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.completion;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * An {@link EStructuralFeature} proposal.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EFeatureCompletionProposal implements ICompletionProposal {

	/**
	 * The {@link EStructuralFeature}.
	 */
	private final EStructuralFeature feature;

	/**
	 * Constructor.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature}
	 */
	public EFeatureCompletionProposal(EStructuralFeature feature) {
		this.feature = feature;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getProposal()
	 */
	@Override
	public String getProposal() {
		return feature.getName();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getCursorOffset()
	 */
	@Override
	public int getCursorOffset() {
		return feature.getName().length();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getObject()
	 */
	@Override
	public EStructuralFeature getObject() {
		return feature;
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
		result.append(feature.eClass().getName());
		result.append(" named ");
		result.append(feature.getName());
		result.append(" in ");
		result.append(feature.getEContainingClass().getName());
		result.append('(');
		result.append(feature.getEContainingClass().getEPackage().getNsURI());
		result.append(')');
		String doc = EcoreUtil.getDocumentation(feature);
		if (doc != null) {
			result.append('\n');
			result.append(doc);
		}
		return result.toString();
	}

}
