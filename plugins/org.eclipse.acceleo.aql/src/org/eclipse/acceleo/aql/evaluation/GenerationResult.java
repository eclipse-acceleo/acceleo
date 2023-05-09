/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
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
	 * The {@link Set} of lost files.
	 */
	private Set<URI> lostFiles = new LinkedHashSet<URI>();

	/**
	 * The {@link Diagnostic} of the generation.
	 */
	private BasicDiagnostic diagnostic = new BasicDiagnostic();

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

	/**
	 * Gets the {@link Set} of lost files.
	 * 
	 * @return the {@link Set} of lost files
	 */
	public Set<URI> getLostFiles() {
		return lostFiles;
	}

	/**
	 * Gets the {@link Diagnostic}.
	 * 
	 * @return the {@link Diagnostic}
	 */
	public Diagnostic getDiagnostic() {
		return diagnostic;
	}

	/**
	 * Adds the given {@link Diagnostic}.
	 * 
	 * @param diag
	 *            the {@link Diagnostic}
	 */
	public void addDiagnostic(Diagnostic diag) {
		this.diagnostic.add(diag);
	}

}
