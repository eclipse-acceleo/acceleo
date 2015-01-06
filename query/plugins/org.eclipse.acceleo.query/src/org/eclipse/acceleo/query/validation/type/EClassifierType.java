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
package org.eclipse.acceleo.query.validation.type;

import org.eclipse.emf.ecore.EClassifier;

/**
 * {@link EClassifier} validation type.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EClassifierType extends AbstractType {

	/**
	 * The {@link EClassifier}.
	 */
	private final EClassifier type;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            the {@link EClassifier}
	 */
	public EClassifierType(EClassifier type) {
		this.type = type;
	}

	/**
	 * Gets the {@link EClassifier}.
	 * 
	 * @return the {@link EClassifier}
	 */
	public EClassifier getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EClassifier=" + type.getName();
	}

}
