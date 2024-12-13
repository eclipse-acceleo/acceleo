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

import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.emf.ecore.EClassifier;
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

	@Override
	public String getProposal() {
		return AstBuilder.protectWithUnderscore(feature.getName());
	}

	@Override
	public int getCursorOffset() {
		return getProposal().length();
	}

	@Override
	public EStructuralFeature getObject() {
		return feature;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.feature.getName());

		buffer.append(": ");

		EClassifier eType = this.feature.getEType();
		buffer.append(eType.getEPackage().getName());
		buffer.append("::");
		buffer.append(this.feature.getEType().getName());

		buffer.append(" [");

		buffer.append(this.getBoundString(this.feature.getLowerBound()));
		buffer.append("..");
		buffer.append(this.getBoundString(this.feature.getUpperBound()));
		buffer.append("]");

		return buffer.toString();
	}

	/**
	 * Returns a value to display for the given bound.
	 * 
	 * @param bound
	 *            An integer representing the bound
	 * @return * if the bound is equal to -1, the string representation of the given integer otherwise
	 */
	private String getBoundString(int bound) {
		if (-1 == bound) {
			return "*";
		}
		return String.valueOf(bound);
	}

	@Override
	public String getDescription() {
		final StringBuffer result = new StringBuffer();

		result.append(feature.eClass().getName());
		result.append(" ");
		result.append(toString());
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
