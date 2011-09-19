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
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;

/**
 * The SaveExpression action is used when the user click the save button in the interpreter view. If the
 * interpreter view is linked to an editor, it will add the expression in the end of the file currently in the
 * editor, otherwise it will save the content of the expression view in a new file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class SaveExpressionAction extends Action implements IMenuCreator {

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
	 * The menu containing the save as template/query actions.
	 */
	private Menu menu;

	/**
	 * The constructor.
	 * 
	 * @param source
	 *            The Acceleo source viewer.
	 * @param interpreterView
	 *            The interpreter view
	 */
	public SaveExpressionAction(AcceleoSourceViewer source, InterpreterView interpreterView) {
		super(null, IAction.AS_DROP_DOWN_MENU);
		this.setMenuCreator(this);
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
			if (!text.contains(AcceleoSourceViewer.TEMPLATE) && !text.contains(AcceleoSourceViewer.QUERY)) {
				// We will copy the content of the text in a new template
				String expression = this.acceleoSource.computeTemplateFromContext(this.interpreterView
						.getInterpreterContext(), null);

				// We remove the module declaration and its imports
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
			newText = newText + AcceleoSourceViewer.LINE_SEPARATOR + text
					+ AcceleoSourceViewer.LINE_SEPARATOR;
			document.set(newText);
		} else {
			// We don't have any Acceleo editor linked with the interpreter, open a popup to save the file.
			String expression = this.acceleoSource.rebuildFullExpression(this.interpreterView
					.getInterpreterContext());

			saveContentInNewFile(expression);
		}
	}

	/**
	 * Saves the given content in a new file.
	 * 
	 * @param content
	 *            The content to be saved
	 */
	private void saveContentInNewFile(String content) {
		String fileContent = content;

		IFile iFile = WorkspaceResourceDialog.openNewFile(this.acceleoSource.getControl().getShell(),
				AcceleoUIMessages.getString("acceleo.interpreter.new.module.path.title"), AcceleoUIMessages //$NON-NLS-1$
						.getString("acceleo.interpreter.new.module.path"), null, null); //$NON-NLS-1$
		if (iFile != null) {
			try {
				// Let's change the name of the new module if we still have the temporary interpreter module
				// name
				String fileName = iFile.getName();
				if (fileName.endsWith('.' + IAcceleoConstants.MTL_FILE_EXTENSION)) {
					fileName = fileName.substring(0, fileName.length() - 4);
					fileName = "[module " + fileName + "("; //$NON-NLS-1$//$NON-NLS-2$
					fileContent = fileContent.replace("[module temporaryInterpreterModule(", fileName); //$NON-NLS-1$
				}

				iFile.create(new ByteArrayInputStream(fileContent.getBytes()), true,
						new NullProgressMonitor());
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Control)
	 */
	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);
		addActionToMenu(menu, new SaveAsTemplateAction());
		addActionToMenu(menu, new SaveAsQueryAction());
		return menu;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
	 */
	public Menu getMenu(Menu parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);
		addActionToMenu(menu, new SaveAsTemplateAction());
		addActionToMenu(menu, new SaveAsQueryAction());
		return menu;
	}

	/**
	 * Adds the given action to the given menu.
	 * 
	 * @param parent
	 *            The menu
	 * @param action
	 *            The action to be added
	 */
	protected void addActionToMenu(Menu parent, Action action) {
		ActionContributionItem item = new ActionContributionItem(action);
		item.fill(parent, -1);
	}

	/**
	 * This action will let the user save the content of the interpreter in a new template in a new file or in
	 * the linked editor.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	private class SaveAsTemplateAction extends Action {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.Action#getText()
		 */
		@Override
		public String getText() {
			return AcceleoUIMessages.getString("acceleo.interpreter.save.as.template"); //$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.Action#getImageDescriptor()
		 */
		@Override
		public ImageDescriptor getImageDescriptor() {
			return AcceleoUIActivator.getImageDescriptor("icons/template-editor/Template.gif"); //$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			// Compute the name of the template
			String templateName = ""; //$NON-NLS-1$

			ModuleElementNameWizard wizard = new ModuleElementNameWizard(AcceleoUIMessages
					.getString("acceleo.interpreter.save.as.template.title"), AcceleoUIMessages //$NON-NLS-1$
					.getString("acceleo.interpreter.save.as.template.description"), AcceleoUIMessages //$NON-NLS-1$
					.getString("acceleo.interpreter.save.as.template.label")); //$NON-NLS-1$
			final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay()
					.getActiveShell(), wizard);
			int result = dialog.open();
			if (result == Window.OK) {
				templateName = wizard.getModuleElementName();
			} else {
				return;
			}

			String expression = acceleoSource.computeTemplateFromContext(interpreterView
					.getInterpreterContext(), templateName);
			if (acceleoEditor != null) {
				// Copy the content of the interpreter and paste it at the bottom of the linked Acceleo
				// editor.
				String text = acceleoSource.getTextWidget().getText();
				if (!text.contains(AcceleoSourceViewer.TEMPLATE) && !text.contains(AcceleoSourceViewer.QUERY)) {
					// We will copy the content of the text in a new template

					// We remove the module declaration and its imports
					// first template or query
					int templateIndex = expression.indexOf("[template public " + templateName); //$NON-NLS-1$
					if (templateIndex != -1) {
						expression = expression.substring(templateIndex);
					}
					text = expression;
				}
				// We will copy the templates or queries directly
				IDocument document = acceleoEditor.getDocumentProvider().getDocument(
						acceleoEditor.getEditorInput());
				String newText = document.get();
				newText = newText + AcceleoSourceViewer.LINE_SEPARATOR + text
						+ AcceleoSourceViewer.LINE_SEPARATOR;
				document.set(newText);
			} else {
				// We don't have any Acceleo editor linked with the interpreter, open a popup to save the
				// file.
				saveContentInNewFile(expression);
			}
		}
	}

	/**
	 * This action will let the user save the content of the interpreter in a new query in a new file or in
	 * the linked editor.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	private class SaveAsQueryAction extends Action {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.Action#getText()
		 */
		@Override
		public String getText() {
			return AcceleoUIMessages.getString("acceleo.interpreter.save.as.query"); //$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.Action#getImageDescriptor()
		 */
		@Override
		public ImageDescriptor getImageDescriptor() {
			return AcceleoUIActivator.getImageDescriptor("icons/template-editor/Query.gif"); //$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			// Compute the name of the query
			String queryName = ""; //$NON-NLS-1$

			ModuleElementNameWizard wizard = new ModuleElementNameWizard(AcceleoUIMessages
					.getString("acceleo.interpreter.save.as.query.title"), AcceleoUIMessages //$NON-NLS-1$
					.getString("acceleo.interpreter.save.as.query.description"), AcceleoUIMessages //$NON-NLS-1$
					.getString("acceleo.interpreter.save.as.query.label")); //$NON-NLS-1$
			final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay()
					.getActiveShell(), wizard);
			int result = dialog.open();
			if (result == Window.OK) {
				queryName = wizard.getModuleElementName();
			} else {
				return;
			}

			String expression = acceleoSource.computeQueryFromContext(
					interpreterView.getInterpreterContext(), queryName);
			if (acceleoEditor != null) {
				// Copy the content of the interpreter and paste it at the bottom of the linked Acceleo
				// editor.
				String text = acceleoSource.getTextWidget().getText();
				if (!text.contains(AcceleoSourceViewer.TEMPLATE) && !text.contains(AcceleoSourceViewer.QUERY)) {
					// We will copy the content of the text in a new query

					// We remove the module declaration and its imports
					int queryIndex = expression.indexOf("[query public " + queryName); //$NON-NLS-1$
					if (queryIndex != -1) {
						expression = expression.substring(queryIndex);
					}
					text = expression;
				}
				// We will copy the templates or queries directly
				IDocument document = acceleoEditor.getDocumentProvider().getDocument(
						acceleoEditor.getEditorInput());
				String newText = document.get();
				newText = newText + AcceleoSourceViewer.LINE_SEPARATOR + text
						+ AcceleoSourceViewer.LINE_SEPARATOR;
				document.set(newText);
			} else {
				// We don't have any Acceleo editor linked with the interpreter, open a popup to save the
				// file.
				saveContentInNewFile(expression);
			}
		}
	}
}
