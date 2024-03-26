/*******************************************************************************
 *  Copyright (c) 2018, 2020 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Resource selection dialog.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractResourceSelectionDialog extends MessageDialog {

	/**
	 * Filters {@link WorkbenchContentProvider}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class FilteredContentProvider extends WorkbenchContentProvider {
		@Override
		public Object[] getChildren(Object element) {
			final List<Object> res = new ArrayList<>();
			for (Object obj : super.getChildren(element)) {
				if (isValidTree(obj)) {
					res.add(obj);
				}
			}
			return res.toArray();
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof IContainer && getChildren(element).length > 0;
		}

		private boolean isValidTree(Object obj) {
			boolean res = false;

			if (obj instanceof IResource && !((IResource)obj).isDerived()) {
				if (isValid((IResource)obj)) {
					res = true;
				} else {
					for (Object child : super.getChildren(obj)) {
						if (isValidTree(child)) {
							res = true;
							break;
						}
					}
				}
			}

			return res;
		}
	}

	/**
	 * Listen to selection changes of the container tree.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class ContainerSelectionChangedListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			final Object selected = ((IStructuredSelection)event.getSelection()).getFirstElement();
			final Button okButton = getButton(OK);
			final boolean enableOkButton;
			if (selected instanceof IFile) {
				final IFile file = (IFile)selected;
				resourceText.setText(file.getFullPath().toString());
				resourceName = file.getFullPath().toString();
				enableOkButton = true;
			} else if (!onlyFileSelection && selected instanceof IContainer) {
				final IContainer container = (IContainer)selected;
				resourceText.setText(container.getFullPath().toString());
				resourceName = container.getFullPath().toString();
				enableOkButton = true;
			} else {
				enableOkButton = false;
			}
			if (okButton != null && !okButton.isDisposed()) {
				okButton.setEnabled(enableOkButton);
			}
		}
	}

	/**
	 * The table minimum height.
	 */
	private static final int TABLE_MINIMUM_HEIGHT = 400;

	/**
	 * The table minimum width.
	 */
	private static final int TABLE_MINIMUM_WIDTH = 200;

	/**
	 * Tells if only file selection is allowed.
	 */
	protected boolean onlyFileSelection;

	/**
	 * The file {@link Text}.
	 */
	private Text resourceText;

	/**
	 * The default file name.
	 */
	private final String defaultResourceName;

	/**
	 * The selected resource name.
	 */
	private String resourceName;

	/**
	 * Creates a file selection dialog.
	 * 
	 * @param parentShell
	 *            the parent {@link Shell}
	 * @param message
	 *            the message
	 * @param title
	 *            the title
	 * @param defaultResourceName
	 *            the default resource name
	 * @param onlyFile
	 *            tells if only file selection is allowed
	 */
	public AbstractResourceSelectionDialog(Shell parentShell, String title, String message,
			String defaultResourceName, boolean onlyFile) {
		super(parentShell, title, null, message, MessageDialog.QUESTION, new String[] {
				IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
		this.defaultResourceName = defaultResourceName;
		this.onlyFileSelection = onlyFile;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		final Composite container = new Composite(parent, parent.getStyle());
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		resourceText = createFilePathComposite(container, defaultResourceName);

		final TreeViewer containerTreeViewer = new TreeViewer(container, SWT.BORDER);
		Tree tree = containerTreeViewer.getTree();
		final GridData gdTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdTable.minimumWidth = TABLE_MINIMUM_WIDTH;
		gdTable.minimumHeight = TABLE_MINIMUM_HEIGHT;
		tree.setLayoutData(gdTable);
		containerTreeViewer.setContentProvider(new FilteredContentProvider());

		containerTreeViewer.setLabelProvider(new WorkbenchLabelProvider());
		containerTreeViewer.addSelectionChangedListener(new ContainerSelectionChangedListener());
		containerTreeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		if (defaultResourceName != null && !defaultResourceName.isEmpty()) {
			IResource resource = findResource(defaultResourceName);
			if (resource != null && !resource.exists()) {
				resource = resource.getParent();
			}
			containerTreeViewer.setSelection(new StructuredSelection(resource));
		}

		containerTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				okPressed();
			}
		});

		return container;
	}

	/**
	 * Creates the file path {@link Composite}.
	 * 
	 * @param container
	 *            the parent {@link Composite}
	 * @param defaultName
	 *            the default file name
	 * @return the file path {@link Text}
	 */
	protected Text createFilePathComposite(final Composite container, String defaultName) {
		final Composite fileComposite = new Composite(container, container.getStyle());
		fileComposite.setLayout(new GridLayout(2, false));
		fileComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

		final Label templateURILabel = new Label(fileComposite, container.getStyle());
		templateURILabel.setText("File: ");

		final Text res = new Text(fileComposite, SWT.BORDER);
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(fileComposite, container.getStyle());
		new Label(fileComposite, container.getStyle());
		if (defaultName != null) {
			res.setText(defaultName);
			resourceName = defaultName;
		}
		res.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				resourceName = res.getText();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// nothing to do here
			}
		});

		return res;
	}

	public String getFileName() {
		return resourceName;
	}

	/**
	 * Finds an actual resource for the given name. Cannot return null.
	 * 
	 * @param defaultName
	 *            the default resource name
	 * @return the resource
	 */
	protected abstract IResource findResource(String defaultName);

	/**
	 * Checks whether or not the resource can be displayed. If yes its containers will be displayed anyway.
	 * 
	 * @param resource
	 *            the resource to check
	 * @return <true> if the resource can be displayed
	 */
	protected abstract boolean isValid(IResource resource);

}
