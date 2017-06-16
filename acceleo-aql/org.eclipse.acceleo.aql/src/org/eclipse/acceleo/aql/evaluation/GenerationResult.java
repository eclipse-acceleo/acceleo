/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

/**
 * The result of a {@link AcceleoEvaluator#generate(org.eclipse.acceleo.Module, java.util.Map) generation}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class GenerationResult {

	/**
	 * The {@link Set} of {@link AcceleoEvaluator#generate(org.eclipse.acceleo.Module, java.util.Map)
	 * generated} files.
	 */
	private Set<URI> generatedFiles = new LinkedHashSet<URI>();

	/**
	 * Gets the {@link Set} of {@link AcceleoEvaluator#generate(org.eclipse.acceleo.Module, java.util.Map)
	 * generated} files.
	 * 
	 * @return the {@link Set} of {@link AcceleoEvaluator#generate(org.eclipse.acceleo.Module, java.util.Map)
	 *         generated} files
	 */
	public Set<URI> getGeneratedFiles() {
		return generatedFiles;
	}

}
