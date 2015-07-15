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
package org.eclipse.acceleo.query.runtime;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * a new version of {@link IEPackageProvider}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 3.7
 */
public interface IEPackageProvider2 extends IEPackageProvider {

	/**
	 * Gets the {@link Set} of containing {@link EStructuralFeature} for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of containing {@link EStructuralFeature} for the given {@link EClass}
	 * @since 4.0
	 */
	Set<EStructuralFeature> getContainingEStructuralFeatures(EClass eCls);

	/**
	 * Gets the {@link Set} of all containing {@link EStructuralFeature} transitively for the given
	 * {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of all containing {@link EStructuralFeature} transitively for the given
	 *         {@link EClass}
	 * @since 4.0
	 */
	Set<EStructuralFeature> getAllContainingEStructuralFeatures(EClass eCls);

}
