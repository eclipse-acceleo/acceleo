/*******************************************************************************
 *  Copyright (c) 2025 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

/**
 * Type qualified name selection dialog based on a {@link IQualifiedNameResolver}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QualifiedNameSelectionDialog extends MessageDialog {

	/**
	 * The table minimum height.
	 */
	private static final int TABLE_MINIMUM_HEIGHT = 400;

	/**
	 * The table minimum width.
	 */
	private static final int TABLE_MINIMUM_WIDTH = 200;

	/**
	 * The default type of the variable.
	 */
	private final String defaultQualifiedName;

	/**
	 * The {@link List} of available qualified names.
	 */
	private final List<String> availableQualifiedNames;

	/**
	 * The selected type.
	 */
	private String selectedQualifiedName;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            the parent {@link Shell}
	 * @param message
	 *            the message
	 * @param defaultQualifiedName
	 *            the default selected qualified name
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 */
	public QualifiedNameSelectionDialog(Shell parentShell, String message, String defaultQualifiedName,
			IQualifiedNameResolver resolver) {
		super(parentShell, "Select a qualified name.", null, message, MessageDialog.QUESTION, new String[] {
				IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
		this.defaultQualifiedName = defaultQualifiedName;
		availableQualifiedNames = new ArrayList<>(resolver.getAvailableQualifiedNames());
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		final Composite container = new Composite(parent, parent.getStyle());
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final FilteredTree filteredTree = new FilteredTree(container, SWT.BORDER, new PatternFilter(), true,
				true);
		final TreeViewer treeViewer = filteredTree.getViewer();
		final Tree tree = treeViewer.getTree();
		final GridData gdTable = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdTable.minimumWidth = TABLE_MINIMUM_WIDTH;
		gdTable.minimumHeight = TABLE_MINIMUM_HEIGHT;
		tree.setLayoutData(gdTable);
		treeViewer.setContentProvider(new CollectionContentProvider());
		treeViewer.setLabelProvider(new LabelProvider());
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				final Object selected = ((IStructuredSelection)event.getSelection()).getFirstElement();
				selectedQualifiedName = (String)selected;
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				buttonPressed(IDialogConstants.OK_ID);
			}
		});
		treeViewer.setInput(availableQualifiedNames);
		if (defaultQualifiedName != null) {
			treeViewer.setSelection(new StructuredSelection(defaultQualifiedName));
		}

		return container;
	}

	/**
	 * Gets the selected qualified name.
	 * 
	 * @return the selected qualified name
	 */
	public String getSelectedQualifiedName() {
		return selectedQualifiedName;
	}

}
