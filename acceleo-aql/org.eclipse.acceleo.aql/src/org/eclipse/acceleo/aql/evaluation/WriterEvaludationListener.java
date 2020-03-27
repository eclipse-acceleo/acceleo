/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.emf.common.util.URI;

public class WriterEvaludationListener implements IAcceleoEvaluationListener {

	/**
	 * The {@link GenerationResult}.
	 */
	private final GenerationResult generationResult = new GenerationResult();

	/** This will hold the writer stack for the file blocks. */
	private final Deque<IAcceleoWriter> writers;

	/** The current generation strategy. */
	private final IAcceleoGenerationStrategy generationStrategy;

	/**
	 * The current destination {@link URI}.
	 */
	private final URI destination;

	/**
	 * The last {@link FileStatement} {@link FileStatement#getUrl() URL}.
	 */
	private URI uri;

	/**
	 * The last {@link FileStatement} {@link FileStatement#getCharset() charset}
	 */
	private String charset;

	/**
	 * Constructor.
	 * 
	 * @param generationStrategy
	 *            the {@link IAcceleoGenerationStrategy}.
	 * @param destination
	 *            the destination {@link URI}
	 */
	public WriterEvaludationListener(IAcceleoGenerationStrategy generationStrategy, URI destination) {
		this.generationStrategy = generationStrategy;
		this.destination = destination;
		this.writers = new ArrayDeque<>();
	}

	@Override
	public void startEvaluation(ASTNode node, IAcceleoEnvironment environment,
			Map<String, Object> variables) {
		if (node.eContainingFeature() == AcceleoPackage.eINSTANCE.getFileStatement_Body()) {
			final FileStatement fileStatement = (FileStatement)node.eContainer();
			final OpenModeKind mode = fileStatement.getMode();
			// FIXME line delimiter
			if (charset == null) {
				// FIXME log ?
				charset = "UTF-8";
			}
			openWriter(uri, mode, charset, "\n");
			generationResult.getGeneratedFiles().add(uri);
			uri = null;
			charset = null;
		}
	}

	@Override
	public void endEvaluation(ASTNode node, IAcceleoEnvironment environment, Map<String, Object> variables,
			Object result) {
		if (!writers.isEmpty()) {
			if (node.eClass() == AcceleoPackage.eINSTANCE.getExpressionStatement() || node
					.eClass() == AcceleoPackage.eINSTANCE.getTextStatement()) {
				write((String)result);
			} else if (node.eClass() == AcceleoPackage.eINSTANCE.getFileStatement()) {
				closeWriter();
			} else {
				checkFileStatement(node, result);
			}
		} else {
			checkFileStatement(node, result);
		}
	}

	/**
	 * Checks if we need to open {@link FileWriter}.
	 * 
	 * @param node
	 *            the current {@link ASTNode}
	 * @param result
	 *            the current result for its evaluation
	 */
	private void checkFileStatement(ASTNode node, Object result) {
		if (node.eContainingFeature() == AcceleoPackage.eINSTANCE.getFileStatement_Url()) {
			uri = URI.createURI((String)result, true).resolve(destination);
		} else if (node.eContainingFeature() == AcceleoPackage.eINSTANCE.getFileStatement_Charset()) {

			// TODO add syntax for charset
			if (result != null) {
				charset = (String)result;
			} else {
				// FIXME log ?
				charset = "UTF-8";
			}
		}
	}

	/**
	 * Opens a writer for the given file uri.
	 * 
	 * @param uri
	 *            The {@link URI} for which we need a writer.
	 * @param openMode
	 *            The mode in which to open the file.
	 * @param charset
	 *            Charset for the target file.
	 * @param lineDelimiter
	 *            Line delimiter that should be used for that file.
	 */
	public void openWriter(URI uri, OpenModeKind openMode, String charset, String lineDelimiter) {
		final IAcceleoWriter writer = generationStrategy.createWriterFor(uri, openMode, charset,
				lineDelimiter);
		writers.addLast(writer);
	}

	/**
	 * Closes the last {@link #openWriter(String, OpenModeKind, String, String) opened} writer.
	 */
	public void closeWriter() {
		final IAcceleoWriter writer = writers.removeLast();
		try {
			writer.close();
		} catch (IOException e) {
			// FIXME log a status
			e.printStackTrace();
		}
	}

	/**
	 * Writes the given {@link String} to the last {@link #openWriter(String, OpenModeKind, String, String)
	 * opened} writer.
	 * 
	 * @param text
	 *            the text to write
	 */
	private void write(String text) {
		IAcceleoWriter writer = writers.peekLast();
		try {
			writer.append(text);
		} catch (IOException e) {
			// FIXME log a status everytime, or close the writer and ignore future calls?
			e.printStackTrace();
		}
	}

	/**
	 * Get the {@link GenerationResult}.
	 * 
	 * @return the {@link GenerationResult}
	 */
	public GenerationResult getGenerationResult() {
		return generationResult;
	}

}
