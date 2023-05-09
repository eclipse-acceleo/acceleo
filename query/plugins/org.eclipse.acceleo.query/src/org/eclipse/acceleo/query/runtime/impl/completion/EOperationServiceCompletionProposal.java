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

import java.util.List;

import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * An {@link EOperation} {@link ICompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 4.1
 */
public class EOperationServiceCompletionProposal implements ICompletionProposal {

	/**
	 * The {@link EOperation}.
	 */
	private final EOperation eOperation;

	/**
	 * Constructor.
	 * 
	 * @param eOperation
	 *            the {@link EOperation}
	 */
	public EOperationServiceCompletionProposal(EOperation eOperation) {
		this.eOperation = eOperation;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getProposal()
	 */
	@Override
	public String getProposal() {
		return AstBuilder.protectWithUnderscore(eOperation.getName()) + "()";
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getCursorOffset()
	 */
	@Override
	public int getCursorOffset() {
		final int length = getProposal().length();
		if (eOperation.getEParameters().size() > 0) {
			/*
			 * if we don't have parameter we return the offset: self.serviceCall()^
			 */
			return length;
		} else {
			/*
			 * if we one or more parameters we return the offset: self.serviceCall(^)
			 */
			return length - 1;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getObject()
	 */
	@Override
	public EOperation getObject() {
		return eOperation;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(this.eOperation.getName());
		buffer.append('(');
		List<EParameter> eParameters = this.eOperation.getEParameters();
		for (int i = 0; i < eParameters.size(); i = i + 1) {
			EParameter eParameter = eParameters.get(i);
			buffer.append(eParameter.getName());
			buffer.append(": ");
			EClassifier eType = eParameter.getEType();
			if (eType != null) {
				buffer.append(eType.getEPackage().getName());
				buffer.append("::");
				buffer.append(eType.getName());
			}

			if (i + 1 < eParameters.size()) {
				buffer.append(", ");
			}
		}
		buffer.append("): ");
		EClassifier eType = this.eOperation.getEType();
		if (eType != null) {
			buffer.append(eType.getEPackage().getName());
			buffer.append("::");
			buffer.append(eType.getName());
		}
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getDescription()
	 */
	@Override
	public String getDescription() {
		StringBuffer result = new StringBuffer();
		result.append(eOperation.eClass().getName());
		result.append(" named ");
		result.append(eOperation.getName());
		result.append(" in ");
		result.append(eOperation.getEContainingClass().getName());
		result.append('(');
		result.append(eOperation.getEContainingClass().getEPackage().getNsURI());
		result.append(')');
		String doc = EcoreUtil.getDocumentation(eOperation);
		if (doc != null) {
			result.append('\n');
			result.append(doc);
		}
		return result.toString();
	}

}
