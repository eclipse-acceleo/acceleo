/*******************************************************************************
 * Copyright (c) 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.completeocl.internal.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.acceleo.ui.interpreter.InterpreterPlugin;
import org.eclipse.acceleo.ui.interpreter.completeocl.IEvaluationExporter;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.internal.CompleteOCLEvaluator;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.util.Pivotable;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Abstract constraint and operations evaluation results exporter.
 * 
 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
 */
public class AbstractExportCompleteOCLEvaluationResultAction extends Action {
	/** The export actions tooltip text. */
	private final static String TOOLTIP_TEXT = "Evaluate the current compilation result against the currently selected resource";

	/** The xtext resource to evaluate. */
	private final XtextResource resource;

	/** The target resource. */
	private final Resource target;

	/** The exporter. */
	protected final IEvaluationExporter exporter;

	/** The meta model manager. */
	private final MetaModelManager metaModelManager;

	/**
	 * Constructor.
	 * 
	 * @param text
	 *            the action title.
	 * @param resource
	 *            the xtext resource to evaluate.
	 * @param target
	 *            the target resource.
	 * @param metaModelManager
	 *            the meta model manager.
	 * @param exporter
	 *            the used exporter.
	 */
	public AbstractExportCompleteOCLEvaluationResultAction(String text, XtextResource resource,
			Resource target, MetaModelManager metaModelManager, IEvaluationExporter exporter) {
		super(text, IAction.AS_PUSH_BUTTON);
		setToolTipText(TOOLTIP_TEXT);
		this.resource = resource;
		this.target = target;
		this.metaModelManager = metaModelManager;
		this.exporter = exporter;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		final FileDialog fileDialog = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		final String savePath = fileDialog.open();
		if (savePath != null) {
			final IParseResult parseResult = resource.getParseResult();

			final EvaluationResult evalutionResult;
			if (parseResult != null && parseResult.getRootASTElement() instanceof Pivotable) {
				final Element pivotElement = ((Pivotable)parseResult.getRootASTElement()).getPivot();

				if (pivotElement instanceof Root) {
					evalutionResult = new CompleteOCLEvaluator(metaModelManager).evaluateCompleteOCLElement(
							pivotElement, target);
				} else {
					evalutionResult = null;
				}
			} else {
				evalutionResult = null;
			}

			if (evalutionResult != null
					&& (evalutionResult.getStatus() == null || evalutionResult.getStatus().isOK())) {
				if (evalutionResult.getEvaluationResult() instanceof OCLElement) {

					final IRunnableWithProgress op = new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor) {
							try {
								exporter.export(savePath, (OCLElement)evalutionResult.getEvaluationResult(),
										monitor);
							} catch (final CoreException e) {
								handleError(e.getCause(), true);
							} catch (final IOException e) {
								handleError(e.getCause(), true);
							}

						}
					};

					try {
						op.run(new NullProgressMonitor());
					} catch (InvocationTargetException e) {
						handleError(e, false);
					} catch (InterruptedException e) {
						handleError(e, false);
					}
				}
			}
		}
	}

	/**
	 * Handles error cases.
	 * 
	 * @param t
	 *            the throwable.
	 * @param popup
	 *            true if we need to activate the error dialog, false otherwise.
	 */
	static void handleError(Throwable t, boolean popup) {
		final String message = "Internal error:" + t.getMessage();
		final IStatus status;
		if (t instanceof CoreException) {
			status = new Status(((CoreException)t).getStatus().getSeverity(), InterpreterPlugin.PLUGIN_ID,
					message, t);
		} else {
			status = new Status(IStatus.ERROR, InterpreterPlugin.PLUGIN_ID, message, t);
		}
		InterpreterPlugin.getDefault().getLog().log(status);

		if (popup) {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					ErrorDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
							"Creation problems", message, status);
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return resource != null && !resource.getParseResult().hasSyntaxErrors() && target != null;
	}
}
