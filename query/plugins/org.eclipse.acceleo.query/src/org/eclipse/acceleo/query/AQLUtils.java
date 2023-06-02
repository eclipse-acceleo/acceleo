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
package org.eclipse.acceleo.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class AQLUtils {

	/**
	 * Constructor.
	 */
	private AQLUtils() {
		// nothing to do here
	}

	/**
	 * Computes the {@link List} of available types for the given {@link List} of regitered
	 * {@link EPackage#getNsURI() nsURI}.
	 * 
	 * @param uris
	 *            the {@link List} of regitered {@link EPackage#getNsURI() nsURI}
	 * @return the {@link List} of available types for the given {@link List} of regitered
	 *         {@link EPackage#getNsURI() nsURI}
	 */
	public static List<String> computeAvailableTypes(List<String> uris, boolean includePrimitiveTypes,
			boolean includeSequenceTypes, boolean includeSetTypes) {
		final Set<String> types = new HashSet<>();

		if (includePrimitiveTypes) {
			types.add(AstSerializer.STRING_TYPE);
			types.add(AstSerializer.INTEGER_TYPE);
			types.add(AstSerializer.REAL_TYPE);
			types.add(AstSerializer.BOOLEAN_TYPE);
		}

		if (uris != null) {
			for (String nsURI : uris) {
				final EPackage ePkg = EPackageRegistryImpl.INSTANCE.getEPackage(nsURI);
				if (ePkg != null) {
					types.addAll(getEClassifiers(ePkg));
				}
			}
		}

		final List<String> res = new ArrayList<>(types.size() * 3);
		for (String type : types) {
			res.add(type);
			if (includeSequenceTypes) {
				res.add("Sequence(" + type + ")");
			}
			if (includeSetTypes) {
				res.add("OrderedSet(" + type + ")");
			}
		}
		Collections.sort(res);

		return res;
	}

	/**
	 * Gets the {@link List} of all classifiers in the given {@link EPackage}.
	 * 
	 * @param ePkg
	 *            the {@link EPackage}
	 * @return the {@link List} of all classifiers in the given {@link EPackage}
	 */
	private static List<String> getEClassifiers(EPackage ePkg) {
		final List<String> res = new ArrayList<>();

		for (EClassifier eClassifier : ePkg.getEClassifiers()) {
			res.add(ePkg.getName() + "::" + eClassifier.getName());
		}
		for (EPackage child : ePkg.getESubpackages()) {
			res.addAll(getEClassifiers(child));
		}

		return res;
	}

}
