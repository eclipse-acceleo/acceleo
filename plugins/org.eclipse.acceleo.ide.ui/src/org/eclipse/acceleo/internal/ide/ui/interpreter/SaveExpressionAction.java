/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.ui.interpreter.view.InterpreterView;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

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

		// Select the Acceleo module to import
		ViewerFilter viewerFilter = new AcceleoModulesViewerFilter();
		List<ViewerFilter> viewerFilters = new ArrayList<ViewerFilter>();
		viewerFilters.add(viewerFilter);

		IFile[] files = WorkspaceResourceDialog.openFileSelection(this.acceleoSource.getControl().getShell(),
				AcceleoUIMessages.getString("acceleo.interpreter.load.module.path.title"), //$NON-NLS-1$
				AcceleoUIMessages.getString("acceleo.interpreter.load.module.path"), false, null, //$NON-NLS-1$
				viewerFilters);

		if (files != null && files.length == 1) {
			try {
				// Let's change the name of the new module if we still have the temporary interpreter module
				// name

				IFile iFile = files[0];
				StringBuffer fileBufferedContent = FileContent.getFileContent(iFile.getLocation().toFile());

				int indexOfTemplate = fileContent.indexOf("[template"); //$NON-NLS-1$
				int indexOfQuery = fileContent.indexOf("[query"); //$NON-NLS-1$
				int indexOfModuleElement = Math.min(indexOfTemplate, indexOfQuery);
				if (indexOfTemplate == -1) {
					indexOfModuleElement = indexOfQuery;
				} else if (indexOfQuery == -1) {
					indexOfModuleElement = indexOfTemplate;
				}
				fileContent = fileContent.substring(indexOfModuleElement);
				fileBufferedContent.append(System.getProperty("line.separator")); //$NON-NLS-1$
				fileBufferedContent.append(fileContent);

				iFile.setContents(new ByteArrayInputStream(fileBufferedContent.toString().getBytes()), true,
						true, new NullProgressMonitor());
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), iFile);
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}
		}
	}

	/**
	 * Adds the ".mtl" files found in the given container and its children in the list of files.
	 * 
	 * @param files
	 *            The files
	 * @param container
	 *            The container
	 * @throws CoreException
	 *             In case of problems
	 */
	private void computeMTLFiles(List<IFile> files, IContainer container) throws CoreException {
		if (container != null && container.isAccessible()) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile
							&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)resource)
									.getFileExtension())) {
						files.add((IFile)resource);
					} else if (resource instanceof IContainer) {
						computeMTLFiles(files, (IContainer)resource);
					}
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
	 * This viewer filter will only display the Acceleo projects and their folders wontaining Acceleo modules.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	private final class AcceleoModulesViewerFilter extends ViewerFilter {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			boolean result = false;
			List<IFile> files = new ArrayList<IFile>();
			try {
				if (element instanceof IProject) {
					IProject project = (IProject)element;
					if (project.isAccessible() && project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
						AcceleoProject acceleoProject = new AcceleoProject(project);
						result = acceleoProject.getInputFiles().size() > 0;
					}
				} else if (element instanceof IContainer) {
					IContainer container = (IContainer)element;
					IProject project = container.getProject();
					if (project.isAccessible() && project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
						AcceleoProject acceleoProject = new AcceleoProject(project);
						List<IPath> sourceFolders = acceleoProject.getSourceFolders();
						boolean shouldVisit = false;
						for (IPath iPath : sourceFolders) {
							if (container.getLocation().toString().startsWith(
									ResourcesPlugin.getWorkspace().getRoot().getFolder(iPath).getLocation()
											.toString())) {
								computeMTLFiles(files, container);
								shouldVisit = files.size() > 0;
							}
							if (shouldVisit) {
								break;
							}
						}
						result = shouldVisit;
					}
				} else if (element instanceof IFile
						&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)element).getFileExtension())) {
					result = true;
				}
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}
			return result;
		}
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
				if (!text.contains(AcceleoSourceViewer.TEMPLATE)) {
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
				ISelection selection = acceleoSource.getSelection();
				if (selection != null && selection instanceof TextSelection
						&& ((TextSelection)selection).getLength() == 0) {
					// No selection, let's build a whole module
				} else if (selection != null && selection instanceof TextSelection) {
					// Only the selection will be used
					text = ((TextSelection)selection).getText();
				}

				if (!text.contains(AcceleoSourceViewer.QUERY)) {
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
