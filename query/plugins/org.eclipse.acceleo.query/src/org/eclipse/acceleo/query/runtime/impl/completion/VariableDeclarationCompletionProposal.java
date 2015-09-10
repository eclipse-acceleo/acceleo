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
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IJavaType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;

/**
 * A variable declaration {@link ICompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class VariableDeclarationCompletionProposal implements ICompletionProposal {

	/**
	 * The possible {@link IType} of the {@link org.eclipse.acceleo.query.ast.VariableDeclaration
	 * VariableDeclaration}.
	 */
	private final IType type;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            the possible {@link IType} of the {@link org.eclipse.acceleo.query.ast.VariableDeclaration
	 *            VariableDeclaration}, its raw type must be an {@link EClassifierType}
	 */
	public VariableDeclarationCompletionProposal(IType type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getProposal()
	 */
	@Override
	public String getProposal() {
		final String result;

		IType rawType = type;

		while (rawType instanceof ICollectionType) {
			rawType = ((ICollectionType)rawType).getCollectionType();
		}

		final String name;
		if (rawType instanceof EClassifierType) {
			name = ((EClassifierType)rawType).getType().getName();
		} else if (rawType instanceof NothingType) {
			name = "Nothing";
		} else if (rawType instanceof IJavaType) {
			name = ((IJavaType)rawType).getType().getSimpleName();
		} else {
			throw new IllegalArgumentException("Cannot handle input type " + rawType);
		}
		result = "my" + name + " | ";

		return result;
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
	public IType getObject() {
		return type;
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
	 * Returns the self string with the first characters transformed to lower case one.
	 * 
	 * @param self
	 *            The self string from which we want to convert the first characters into lower case one.
	 * @return the self string with the first character transformed to lower case one. Throws
	 *         NullPointerException if "self" is null.
	 */
	public String toLowerFirst(String self) {
		final String resultString;

		if (self.length() == 0) {
			resultString = self;
		} else if (self.length() == 1) {
			resultString = self.toLowerCase();
		} else {
			resultString = Character.toLowerCase(self.charAt(0)) + self.substring(1);
		}

		return resultString;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getDescription()
	 */
	@Override
	public String getDescription() {
		return toString();
	}

}
