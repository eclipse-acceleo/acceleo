/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.ocl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.context.EInvocationContext;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.util.Pivotable;
import org.eclipse.ocl.examples.xtext.console.xtfo.EmbeddedXtextEditor;
import org.eclipse.ocl.examples.xtext.essentialocl.utilities.EssentialOCLCSResource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * This class allows adopters to compile the String representation of an OCL expression in an asynchronous
 * way. The resulting {@link CompilationResult} will be the compiled {@link ExpressionInOCL}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class OCLCompilationTask implements Callable<CompilationResult> {
	/** Current interpreter context. */
	private final InterpreterContext context;

	/** Embedded editor containing the expression to compile. */
	private final EmbeddedXtextEditor editor;

	/** The current metaModel Manager. */
	private final MetaModelManager metaModelManager;

	/**
	 * Instantiates our compilation task given the current interpreter context.
	 * 
	 * @param context
	 *            The current interpreter context.
	 * @param editor
	 *            The current editor.
	 * @param metaModelManager
	 *            The Metamodel Manager.
	 */
	public OCLCompilationTask(InterpreterContext context, EmbeddedXtextEditor editor,
			MetaModelManager metaModelManager) {
		this.context = context;
		this.editor = editor;
		this.metaModelManager = metaModelManager;
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
		final boolean noContext = context.getTargetEObjects().isEmpty();
		if (nothingToCompile || noContext) {
			CompilationResult shortcut = null;
			if (noContext) {
				shortcut = new CompilationResult(new Status(IStatus.INFO, OCLInterpreterActivator.PLUGIN_ID,
						"No context selected."));
			}
			return shortcut;
		}

		EObject evaluationTarget = context.getTargetEObjects().get(0);

		ExpressionInOCL expressionInOCL = null;
		CompilationResult compilationResult = null;
		Resource resource = editor.getResource();
		if (resource instanceof XtextResource) {
			refreshEditor(evaluationTarget);
			checkCancelled();

			final IParseResult result = ((XtextResource)resource).getParseResult();
			if (result != null && result.getRootASTElement() instanceof Pivotable) {
				final Element pivotElement = ((Pivotable)result.getRootASTElement()).getPivot();
				if (pivotElement instanceof ExpressionInOCL) {
					expressionInOCL = (ExpressionInOCL)pivotElement;
				}
			}
		}

		if (expressionInOCL == null) {
			compilationResult = new CompilationResult(new Status(IStatus.ERROR,
					OCLInterpreterActivator.PLUGIN_ID, "Unexpected error while parsing expression."));
		} else {
			final IStatus status = parseProblems(resource);
			compilationResult = new CompilationResult(expressionInOCL, status);
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
	private IStatus parseProblems(Resource resource) {
		final List<IStatus> problems = new ArrayList<IStatus>();
		for (Diagnostic diagnostic : resource.getErrors()) {
			problems.add(new Status(IStatus.ERROR, OCLInterpreterActivator.PLUGIN_ID, diagnostic.getMessage()));
		}
		for (Diagnostic diagnostic : resource.getWarnings()) {
			problems.add(new Status(IStatus.WARNING, OCLInterpreterActivator.PLUGIN_ID, diagnostic
					.getMessage()));
		}

		if (problems.isEmpty()) {
			return null;
		}

		final MultiStatus status = new MultiStatus(OCLInterpreterActivator.PLUGIN_ID, 1,
				"Problems encountered while compiling expression.", null);
		for (IStatus child : problems) {
			status.add(child);
		}
		return status;
	}

	/**
	 * This will refresh the editor and force a reparsing of the OCL expression it displays if needed (i.e. if
	 * the target EObject has changed since it was last refreshed).
	 * 
	 * @param target
	 *            The selected model element.
	 */
	private void refreshEditor(EObject target) {
		final IUnitOfWork<Object, XtextResource> refresher = new DocumentRefresher(editor, metaModelManager,
				target);

		final Display display = Display.getDefault();
		if (display != null) {
			display.syncExec(new Runnable() {
				public void run() {
					editor.getDocument().modify(refresher);
				}
			});
		} else {
			editor.getDocument().modify(refresher);
		}
	}

	/**
	 * Throws a new {@link CancellationException} if the current thread has been cancelled.
	 */
	private void checkCancelled() {
		if (Thread.currentThread().isInterrupted()) {
			throw new CancellationException();
		}
	}

	/**
	 * An instance of this class will have the responsibility of setting the context classifier of an Xtext
	 * document and reparse the expression if needed.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private static class DocumentRefresher implements IUnitOfWork<Object, XtextResource> {
		/** Embedded editor containing the expression to compile. */
		private final EmbeddedXtextEditor editor;

		/** The current metaModel Manager. */
		private final MetaModelManager metaModelManager;

		/** EObject which is to be set as the new context of this document. */
		private final EObject target;

		/**
		 * Instantiates a document refresher given its context.
		 * 
		 * @param editor
		 *            The editor which document will be refreshed.
		 * @param metaModelManager
		 *            The current metamodel manager.
		 * @param target
		 *            The context EObject for the incoming parsing.
		 */
		public DocumentRefresher(EmbeddedXtextEditor editor, MetaModelManager metaModelManager, EObject target) {
			this.editor = editor;
			this.metaModelManager = metaModelManager;
			this.target = target;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.xtext.util.concurrent.IUnitOfWork#exec(Object)
		 */
		public Value exec(XtextResource resource) throws Exception {
			EClassifier contextClassifier = target.eClass();
			EssentialOCLCSResource csResource = (EssentialOCLCSResource)resource;
			if (csResource != null) {
				if (csResource.getParserContext() instanceof EInvocationContext) {
					final EInvocationContext context = (EInvocationContext)csResource.getParserContext();
					if (context.getClassContext().getETarget().equals(contextClassifier)) {
						return null;
					}
				}
				csResource.setParserContext(new EInvocationContext(metaModelManager, resource.getURI(),
						contextClassifier, null));
				csResource.reparse(editor.getDocument().get());
			}
			// unused return value
			return null;
		}
	}
}
