/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import java.util.concurrent.Callable;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.ui.interpreter.internal.SWTUtil;
import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.acceleo.ui.interpreter.view.InterpreterView;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

/**
 * This implementation of an {@link AbstractLanguageInterpreter} will be able to provide completion, syntax
 * highlighting, compilation and evaluation of any given Acceleo expression.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoInterpreter extends AbstractLanguageInterpreter {
	/**
	 * Acceleo's compilation task is deeply tied to its Viewer. We'll keep a reference to it in order to pass
	 * it around.
	 */
	private AcceleoSourceViewer acceleoSource;

	/**
	 * The save expression action.
	 */
	private SaveExpressionAction saveExpressionAction;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#addToolBarActions(org.eclipse.jface.action.IToolBarManager)
	 */
	@Override
	public void addToolBarActions(InterpreterView interpreterView, IToolBarManager toolBarManager) {
		super.addToolBarActions(interpreterView, toolBarManager);
		this.saveExpressionAction = new SaveExpressionAction(acceleoSource, interpreterView);
		toolBarManager.add(saveExpressionAction);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#canLinkWithEditor(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public boolean canLinkWithEditor(IEditorPart editorPart) {
		return editorPart instanceof AcceleoEditor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#configureSourceViewer(org.eclipse.jface.text.source.SourceViewer)
	 */
	@Override
	public void configureSourceViewer(final SourceViewer viewer) {
		if (viewer instanceof AcceleoSourceViewer) {
			Document document = new Document();
			viewer.setDocument(document);

			// Creates the source content
			((AcceleoSourceViewer)viewer).initializeContent();

			// Setup syntax highlighting and partitioning
			IDocumentPartitioner partitioner = new FastPartitioner(
					new org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner(),
					org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner.LEGAL_CONTENT_TYPES);
			document.setDocumentPartitioner(partitioner);
			partitioner.connect(document);

			// Setup source content updating
			document.addPositionUpdater(new IPositionUpdater() {
				public void update(DocumentEvent event) {
					((AcceleoSourceViewer)viewer).handlePositionUpdate(event.getOffset(), event.getOffset()
							+ event.getLength(), event.getText());
				}
			});

			viewer.configure(new AcceleoInterpreterConfiguration(AcceleoUIActivator.getDefault()
					.getPreferenceStore()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#createSourceViewer(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public SourceViewer createSourceViewer(Composite parent) {
		acceleoSource = new AcceleoSourceViewer(parent, null, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		SWTUtil.setUpScrollableListener(acceleoSource.getTextWidget());
		return acceleoSource;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#dispose()
	 */
	@Override
	public void dispose() {
		// Null out references
		acceleoSource = null;
		this.saveExpressionAction.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#getCompilationTask(org.eclipse.acceleo.ui.interpreter.language.InterpreterContext)
	 */
	@Override
	public Callable<CompilationResult> getCompilationTask(InterpreterContext context) {
		return new AcceleoCompilationTask(acceleoSource, context);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#getEvaluationTask(org.eclipse.acceleo.ui.interpreter.language.EvaluationContext)
	 */
	@Override
	public Callable<EvaluationResult> getEvaluationTask(EvaluationContext context) {
		return new AcceleoEvaluationTask(context);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter#linkWithEditor(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void linkWithEditor(IEditorPart editorPart) {
		if (editorPart instanceof AcceleoEditor && this.saveExpressionAction != null) {
			this.saveExpressionAction.setCurrentEditor((AcceleoEditor)editorPart);
			final IEditorInput input = editorPart.getEditorInput();
			final IFile file = (IFile)Platform.getAdapterManager().getAdapter(input, IFile.class);
			if (file != null && IAcceleoConstants.MTL_FILE_EXTENSION.equals(file.getFileExtension())) {
				acceleoSource.setModuleImport(file);
			}
		} else if (this.saveExpressionAction != null) {
			this.saveExpressionAction.setCurrentEditor(null);
			acceleoSource.setModuleImport(null);
		}
	}
}
