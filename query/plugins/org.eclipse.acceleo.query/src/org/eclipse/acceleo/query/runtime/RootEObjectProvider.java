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
package org.eclipse.acceleo.query.runtime;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

/**
 * Default implementation of {@link IRootEObjectProvider}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 4.0.0
 */
public class RootEObjectProvider implements IRootEObjectProvider {

	/**
	 * Roots.
	 */
	private final Set<EObject> roots;

	/**
	 * Constructor.
	 * 
	 * @param roots
	 *            the {@link Set} of root {@link EObject}
	 */
	public RootEObjectProvider(Set<EObject> roots) {
		this.roots = roots;
	}

	/**
	 * Constructor.
	 * 
	 * @param roots
	 *            the array of root {@link EObject}
	 */
	public RootEObjectProvider(EObject... roots) {
		this.roots = new LinkedHashSet<EObject>();
		for (EObject root : roots) {
			this.roots.add(root);
		}
	}

	@Override
	public Set<EObject> getRoots() {
		return roots;
	}

}
