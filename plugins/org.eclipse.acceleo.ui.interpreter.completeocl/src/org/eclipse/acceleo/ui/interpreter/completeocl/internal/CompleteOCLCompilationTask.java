/*******************************************************************************
 * Copyright (c) 2013, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.completeocl.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.util.Pivotable;
import org.eclipse.ocl.examples.xtext.console.xtfo.EmbeddedXtextEditor;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;

/**
 * This class allows adopters to compile the String representation of a Complete OCL package in an
 * asynchronous way. The resulting {@link CompilationResult} will be the compiled pivot {@link Root}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CompleteOCLCompilationTask implements Callable<CompilationResult> {
	/** Current interpreter context. */
	private final InterpreterContext context;

	/** Embedded editor containing the package to compile. */
	private final EmbeddedXtextEditor editor;

	/**
	 * Instantiates our compilation task given the current interpreter context.
	 * 
	 * @param context
	 *            The current interpreter context.
	 * @param editor
	 *            The current editor.
	 */
	public CompleteOCLCompilationTask(InterpreterContext context, EmbeddedXtextEditor editor) {
		this.context = context;
		this.editor = editor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public CompilationResult call() throws Exception {
		checkCancelled();

		final boolean nothingToCompile = context.getExpression() == null
				|| context.getExpression().length() == 0;

		if (nothingToCompile) {
			return null;
		}

		final XtextResource resource = editor.getResource();

		Root root = null;
		CompilationResult compilationResult = null;
		if (resource != null) {
			final IParseResult result = resource.getParseResult();

			if (result != null && result.getRootASTElement() instanceof Pivotable) {
				final Element pivotElement = ((Pivotable)result.getRootASTElement()).getPivot();

				if (pivotElement instanceof Root) {
					root = (Root)pivotElement;
				}
			}

			final IStatus status = parseProblems(resource);
			if (root == null) {
				final IStatus actualStatus;
				if (status.getSeverity() != IStatus.ERROR) {
					actualStatus = new Status(IStatus.ERROR, CompleteOCLInterpreterActivator.PLUGIN_ID,
							"Unexpected error while parsing expression.");
				} else {
					actualStatus = status;
				}
				compilationResult = new CompilationResult(actualStatus);
			} else {
				compilationResult = new CompilationResult(root, status);
			}
		} else {
			compilationResult = new CompilationResult(new Status(IStatus.ERROR,
					CompleteOCLInterpreterActivator.PLUGIN_ID, "Unexpected error while parsing expression."));
		}

		return compilationResult;
	}

	/**
	 * Checks the given resource for any error or warning and creates a MultiStatus containing, in order, one
	 * entry for each error and one entry for each warning.
	 * 
	 * @param resource
	 *            The resource which problems we need to check.
	 * @return A MultiStatus for this resource's problems if any, <code>null</code> if none.
	 */
	private static IStatus parseProblems(Resource resource) {
		final List<IStatus> problems = new ArrayList<IStatus>();
		for (Diagnostic diagnostic : resource.getErrors()) {
			problems.add(new Status(IStatus.ERROR, CompleteOCLInterpreterActivator.PLUGIN_ID, diagnostic
					.getMessage()));
		}
		for (Diagnostic diagnostic : resource.getWarnings()) {
			problems.add(new Status(IStatus.WARNING, CompleteOCLInterpreterActivator.PLUGIN_ID, diagnostic
					.getMessage()));
		}

		if (problems.isEmpty()) {
			return null;
		}

		final MultiStatus status = new MultiStatus(CompleteOCLInterpreterActivator.PLUGIN_ID, 1,
				"Problems encountered while compiling expression.", null);
		for (IStatus child : problems) {
			status.add(child);
		}
		return status;
	}

	/**
	 * Throws a new {@link CancellationException} if the current thread has been cancelled.
	 */
	private void checkCancelled() {
		if (Thread.currentThread().isInterrupted()) {
			throw new CancellationException();
		}
	}
}
