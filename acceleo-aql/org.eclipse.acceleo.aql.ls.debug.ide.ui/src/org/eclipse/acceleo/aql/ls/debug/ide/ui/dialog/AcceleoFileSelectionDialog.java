/*******************************************************************************
 *  Copyright (c) 2018, 2020 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug.ide.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
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
 * File selection dialog.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoFileSelectionDialog extends MessageDialog {

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
				fileText.setText(file.getFullPath().toString());
				fileName = file.getFullPath().toString();
				enableOkButton = true;
			} else if (!onlyFileSelection && selected instanceof IContainer) {
				final IContainer container = (IContainer)selected;
				fileText.setText(container.getFullPath().toString());
				fileName = container.getFullPath().toString();
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
	 * The file {@link Text}.
	 */
	private Text fileText;

	/**
	 * The default file name.
	 */
	private final String defaultFileName;

	/**
	 * The filtered file extension.
	 */
	private final String fileExtension;

	/**
	 * The selected file name.
	 */
	private String fileName;

	/**
	 * Tells if only file selection is allowed.
	 */
	private boolean onlyFileSelection;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            the parent {@link Shell}
	 * @param title
	 *            the title
	 * @param defaultFileName
	 *            the default file name
	 * @param fileExtension
	 *            the filtered file extension
	 * @param onlyFileSelection
	 *            tells if only file selection is allowed
	 */
	public AcceleoFileSelectionDialog(Shell parentShell, String title, String defaultFileName,
			String fileExtension, boolean onlyFileSelection) {
		super(parentShell, title, null, "Select a file.", MessageDialog.QUESTION, new String[] {
				IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
		this.defaultFileName = defaultFileName;
		this.fileExtension = fileExtension;
		this.onlyFileSelection = onlyFileSelection;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		final Composite container = new Composite(parent, parent.getStyle());
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		fileText = createFilePathComposite(container, defaultFileName);

		final TreeViewer containerTreeViewer = new TreeViewer(container, SWT.BORDER);
		Tree tree = containerTreeViewer.getTree();
		final GridData gdTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdTable.minimumWidth = TABLE_MINIMUM_WIDTH;
		gdTable.minimumHeight = TABLE_MINIMUM_HEIGHT;
		tree.setLayoutData(gdTable);
		containerTreeViewer.setContentProvider(new WorkbenchContentProvider() {
			@Override
			public Object[] getChildren(Object element) {
				final List<Object> res = new ArrayList<>();
				for (Object obj : super.getChildren(element)) {
					if (obj instanceof IContainer || (obj instanceof IFile && (fileExtension == null
							|| fileExtension.equals(((IFile)obj).getFileExtension())))) {
						res.add(obj);
					}
				}
				return res.toArray();
			}
		});
		containerTreeViewer.setLabelProvider(new WorkbenchLabelProvider());
		containerTreeViewer.addSelectionChangedListener(new ContainerSelectionChangedListener());
		containerTreeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		if (defaultFileName != null && !defaultFileName.isEmpty()) {
			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(defaultFileName));
			containerTreeViewer.setSelection(new StructuredSelection(file));
		}

		return container;
	}

	/**
	 * Creates the file path {@link Composite}.
	 * 
	 * @param container
	 *            the pasent {@link Composite}
	 * @param defaultName
	 *            the fefault file name
	 * @return the file path {@link Text}
	 */
	protected Text createFilePathComposite(final Composite container, String defaultName) {
		final Composite fileComposite = new Composite(container, container.getStyle());
		fileComposite.setLayout(new GridLayout(2, false));
		fileComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

		final Label templateURILabel = new Label(fileComposite, container.getStyle());
		templateURILabel.setText("File: ");

		final Text res = new Text(fileComposite, container.getStyle());
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(fileComposite, container.getStyle());
		new Label(fileComposite, container.getStyle());
		if (defaultName != null) {
			res.setText(defaultName);
			fileName = defaultName;
		}
		res.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				fileName = res.getText();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// nothing to do here
			}
		});

		return res;
	}

	public String getFileName() {
		return fileName;
	}

}
