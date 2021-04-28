/*******************************************************************************
 * Copyright (c) 2016, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Deque;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.emf.common.util.URI;

/**
 * This environment will keep track of Acceleo's evaluation context. TODO doc.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEnvironment implements IAcceleoEnvironment {

	/** The AQL environment that will be used to evaluate aql expressions from this Acceleo context. */
	private IQualifiedNameQueryEnvironment aqlEnvironment;

	/** This will hold the writer stack for the file blocks. */
	private final Deque<IAcceleoWriter> writers = new ArrayDeque<IAcceleoWriter>();

	/** The current generation strategy. */
	private final IAcceleoGenerationStrategy generationStrategy;

	/**
	 * The {@link GenerationResult}.
	 */
	private final GenerationResult generationResult = new GenerationResult();

	/**
	 * Constructor.
	 * 
	 * @param aqlEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param generationStrategy
	 *            the {@link IAcceleoGenerationStrategy}
	 */
	public AcceleoEnvironment(IQualifiedNameQueryEnvironment aqlEnvironment,
			IAcceleoGenerationStrategy generationStrategy) {
		this.generationStrategy = generationStrategy;

		this.aqlEnvironment = aqlEnvironment;
		/* FIXME we need a cross reference provider, and we need to make it configurable */
		org.eclipse.acceleo.query.runtime.Query.configureEnvironment(aqlEnvironment, null, null);
	}

	@Override
	public IQualifiedNameQueryEnvironment getQueryEnvironment() {
		return aqlEnvironment;
	}

	@Override
	public void openWriter(URI uri, OpenModeKind openMode, Charset charset, String lineDelimiter)
			throws IOException {
		final IAcceleoWriter writer = generationStrategy.createWriterFor(uri, openMode, charset,
				lineDelimiter);
		writers.addLast(writer);
		generationResult.getGeneratedFiles().add(uri);
	}

	@Override
	public void closeWriter() throws IOException {
		final IAcceleoWriter writer = writers.removeLast();
		writer.close();
	}

	@Override
	public void write(String text) throws IOException {
		IAcceleoWriter writer = writers.peekLast();
		writer.append(text);
	}

	@Override
	public GenerationResult getGenerationResult() {
		return generationResult;
	}

}
