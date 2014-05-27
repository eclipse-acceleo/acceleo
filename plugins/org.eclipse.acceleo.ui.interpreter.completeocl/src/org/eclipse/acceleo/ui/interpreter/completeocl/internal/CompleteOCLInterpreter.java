/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.completeocl.internal;

import java.util.Iterator;
import java.util.concurrent.Callable;

import org.eclipse.acceleo.ui.interpreter.completeocl.internal.action.HTMLExportCompleteOCLEvaluationResultAction;
import org.eclipse.acceleo.ui.interpreter.completeocl.internal.action.ImportCompleteOCLResourceAction;
import org.eclipse.acceleo.ui.interpreter.completeocl.internal.action.ModelExportCompleteOCLEvaluationResultAction;
import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.acceleo.ui.interpreter.language.SplitExpression;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.utilities.PivotUtil;
import org.eclipse.ocl.examples.xtext.completeocl.ui.internal.CompleteOCLActivator;
import org.eclipse.ocl.examples.xtext.completeocl.utilities.CompleteOCLPlugin;
import org.eclipse.ocl.examples.xtext.console.xtfo.EmbeddedXtextEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

/**
 * Implements the necessary API for a Complete OCL interpreter.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CompleteOCLInterpreter extends AbstractLanguageInterpreter {
	/** The current editor. */
	protected EmbeddedXtextEditor editor;

	/** Used when we cannot infer a better Manager from contextual information. */
	private MetaModelManager defaultMetaModelManager;

	/**
	 * The last Resource that was used as an evaluation target. May be the Resource of the last "EObject" used
	 * as a target.
	 */
	protected Resource lastTargetResource;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#getCompilationTask(InterpreterContext)
	 */
	@Override
	public Callable<CompilationResult> getCompilationTask(InterpreterContext context) {
		return new CompleteOCLCompilationTask(context, editor);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#getEvaluationTask(EvaluationContext)
	 */
	@Override
	public Callable<EvaluationResult> getEvaluationTask(EvaluationContext context) {
		lastTargetResource = findTargetResource(context);
		return new CompleteOCLEvaluationTask(context, getContextMetaModelManager(context));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#getExpressionSplittingTask(EvaluationContext)
	 */
	@Override
	public Callable<SplitExpression> getExpressionSplittingTask(EvaluationContext context) {
		return new CompleteOCLExpressionSplittingTask(context, getContextMetaModelManager(context));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#createSourceViewer(Composite)
	 */
	@Override
	public SourceViewer createSourceViewer(Composite parent) {
		editor = new EmbeddedXtextEditor(parent, CompleteOCLActivator.getInstance().getInjector(
				CompleteOCLPlugin.LANGUAGE_ID), SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		SourceViewer viewer = editor.getViewer();
		addLoadResourceActionToMenu(viewer);
		viewer.getControl().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				editor.getResource().unload();
				editor = null;
			}
		});
		return viewer;
	}

	private void addLoadResourceActionToMenu(TextViewer viewer) {
		Menu menu = viewer.getTextWidget().getMenu();
		boolean createMenu = true;
		if (menu != null) {
			MenuManager manager = (MenuManager)menu
					.getData("org.eclipse.jface.action.MenuManager.managerKey"); //$NON-NLS-1$
			if (manager != null) {
				manager.addMenuListener(createExpressionMenuListener(viewer));
				createMenu = false;
			}
		}

		if (createMenu) {
			MenuManager menuManager = new MenuManager(null);
			menuManager.setRemoveAllWhenShown(true);
			menuManager.addMenuListener(createExpressionMenuListener(viewer));
			menu = menuManager.createContextMenu(viewer.getTextWidget());
			viewer.getTextWidget().setMenu(menu);
		}
	}

	private IMenuListener createExpressionMenuListener(final TextViewer viewer) {
		return new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(new Separator());
				manager.add(new ImportCompleteOCLResourceAction(viewer));
				MetaModelManager metaModeManager = getMetaModelManager(lastTargetResource);
				if (metaModeManager == null) {
					metaModeManager = getDefaultMetaModelManager();
				}

				MenuManager submenuManager = new MenuManager("Export Evaluation Result");
				submenuManager.add(new HTMLExportCompleteOCLEvaluationResultAction(editor.getResource(),
						lastTargetResource, metaModeManager));
				submenuManager.add(new ModelExportCompleteOCLEvaluationResultAction(editor.getResource(),
						lastTargetResource, metaModeManager));
				manager.add(submenuManager);
			}
		};
	}

	private Resource findTargetResource(EvaluationContext context) {
		Resource target = null;
		final Iterator<Notifier> notifiers = context.getTargetNotifiers().iterator();
		while (notifiers.hasNext() && target == null) {
			final Notifier next = notifiers.next();
			if (next instanceof EObject) {
				target = ((EObject)next).eResource();
			} else if (next instanceof Resource) {
				target = (Resource)next;
			}
		}
		return target;
	}

	private MetaModelManager getContextMetaModelManager(EvaluationContext context) {
		MetaModelManager contextManager = null;
		if (!context.getTargetNotifiers().isEmpty()) {
			final Iterator<Notifier> notifiers = context.getTargetNotifiers().iterator();
			while (notifiers.hasNext() && contextManager == null) {
				final Notifier next = notifiers.next();
				if (next instanceof EObject) {
					contextManager = getMetaModelManager((EObject)next);
				} else if (next instanceof Resource) {
					contextManager = getMetaModelManager((Resource)next);
				}
			}
		}
		if (contextManager == null) {
			contextManager = getDefaultMetaModelManager();
		}
		return contextManager;
	}

	/**
	 * Find the metamodel manager for the given target.
	 * 
	 * @param target
	 *            The current selected element.
	 * @return The metamodel manager.
	 */
	private MetaModelManager getMetaModelManager(EObject target) {
		if (target != null) {
			return PivotUtil.findMetaModelManager(target);
		}
		return null;
	}

	/**
	 * Find the metamodel manager for the given target.
	 * 
	 * @param target
	 *            The current selected element.
	 * @return The metamodel manager.
	 */
	private MetaModelManager getMetaModelManager(Resource target) {
		if (target != null) {
			return PivotUtil.findMetaModelManager(target);
		}
		return null;
	}

	private MetaModelManager getDefaultMetaModelManager() {
		if (defaultMetaModelManager == null) {
			defaultMetaModelManager = new MetaModelManager();
		}
		return defaultMetaModelManager;
	}
}
