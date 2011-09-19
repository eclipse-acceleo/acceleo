/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import java.io.ByteArrayInputStream;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.ui.interpreter.view.InterpreterView;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;

/**
 * The SaveExpression action is used when the user click the save button in the interpreter view. If the
 * interpreter view is linked to an editor, it will add the expression in the end of the file currently in the
 * editor, otherwise it will save the content of the expression view in a new file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class SaveExpressionAction extends Action {

	/**
	 * The Acceleo editor currently used linked with the Acceleo interpreter view.
	 */
	private AcceleoEditor acceleoEditor;

	/**
	 * The Acceleo source viewer.
	 */
	private AcceleoSourceViewer acceleoSource;

	/**
	 * The interpreter view.
	 */
	private InterpreterView interpreterView;

	/**
	 * The constructor.
	 * 
	 * @param source
	 *            The Acceleo source viewer.
	 * @param interpreterView
	 *            The interpreter view
	 */
	public SaveExpressionAction(AcceleoSourceViewer source, InterpreterView interpreterView) {
		super(null, IAction.AS_PUSH_BUTTON);
		this.acceleoSource = source;
		this.interpreterView = interpreterView;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return AcceleoUIActivator.getImageDescriptor("icons/interpreter/save_expression.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return AcceleoUIMessages.getString("acceleo.interpreter.save.expression"); //$NON-NLS-1$
	}

	/**
	 * Sets the current Acceleo editor linked with the interpreter view.
	 * 
	 * @param editor
	 *            The Acceleo editor.
	 */
	public void setCurrentEditor(AcceleoEditor editor) {
		this.acceleoEditor = editor;
		if (editor != null) {
			this.setToolTipText(AcceleoUIMessages.getString("acceleo.interpreter.save.expression.editor")); //$NON-NLS-1$
		} else {
			this.setToolTipText(AcceleoUIMessages.getString("acceleo.interpreter.save.expression")); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (this.acceleoEditor != null) {
			// Copy the content of the interpreter and paste it at the bottom of the linked Acceleo editor.
			String text = this.acceleoSource.getTextWidget().getText();
			if (!text.contains("[template") && !text.contains("[query")) { //$NON-NLS-1$ //$NON-NLS-2$
				// We will copy the content of the text in a new template
				String expression = this.acceleoSource.rebuildFullExpression(this.interpreterView
						.getInterpreterContext());

				// We remove the module declaration and its imports by starting the copy of the text from the
				// first template or query
				int templateIndex = expression.indexOf("[template public temporaryInterpreterTemplate"); //$NON-NLS-1$
				if (templateIndex != -1) {
					expression = expression.substring(templateIndex);
				}
				text = expression;
			}
			// We will copy the templates or queries directly
			IDocument document = this.acceleoEditor.getDocumentProvider().getDocument(
					this.acceleoEditor.getEditorInput());
			String newText = document.get();
			newText = newText + System.getProperty("line.separator") //$NON-NLS-1$
					+ text + System.getProperty("line.separator"); //$NON-NLS-1$
			document.set(newText);
		} else {
			// We don't have any Acceleo editor linked with the interpreter, open a popup to save the file.
			String expression = this.acceleoSource.rebuildFullExpression(this.interpreterView
					.getInterpreterContext());

			IFile iFile = WorkspaceResourceDialog
					.openNewFile(this.acceleoSource.getControl().getShell(), AcceleoUIMessages
							.getString("acceleo.interpreter.new.module.path.title"), AcceleoUIMessages //$NON-NLS-1$
							.getString("acceleo.interpreter.new.module.path"), null, null); //$NON-NLS-1$
			if (iFile != null) {
				try {
					// Let's change the name of the new module
					String fileName = iFile.getName();
					if (fileName.endsWith('.' + IAcceleoConstants.MTL_FILE_EXTENSION)) {
						fileName = fileName.substring(0, fileName.length() - 4);
						expression = expression.replace("temporaryInterpreterModule", fileName); //$NON-NLS-1$
					}

					iFile.create(new ByteArrayInputStream(expression.getBytes()), true,
							new NullProgressMonitor());
				} catch (CoreException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		}
	}

	/**
	 * This will be called in order to clear all the references of this.
	 */
	public void dispose() {
		this.acceleoEditor = null;
		this.acceleoSource = null;
		this.interpreterView = null;
	}
}
