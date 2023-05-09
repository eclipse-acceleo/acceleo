/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.validation.type;

import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * A Set of {@link EClassifier} literal.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EClassifierSetLiteralType extends SetType {

	/**
	 * The {@link Set} of {@link EClassifier}.
	 */
	private final Set<EClassifier> eClassifiers;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param eClassifiers
	 *            the {@link Set} of {@link EClassifier}
	 */
	public EClassifierSetLiteralType(IReadOnlyQueryEnvironment queryEnvironment, Set<EClassifier> eClassifiers) {
		super(queryEnvironment,
				new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClassifier()));
		this.eClassifiers = eClassifiers;
	}

	/**
	 * Gets the {@link Set} of {@link EClassifier}.
	 * 
	 * @return the {@link Set} of {@link EClassifier}
	 */
	public Set<EClassifier> getEClassifiers() {
		return eClassifiers;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.SetType#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder res = new StringBuilder();

		res.append("Set(");
		for (EClassifier eClassifier : eClassifiers) {
			res.append(eClassifier.toString());
		}
		res.replace(res.length() - 1, res.length(), ")");

		return res.toString();
	}

}
