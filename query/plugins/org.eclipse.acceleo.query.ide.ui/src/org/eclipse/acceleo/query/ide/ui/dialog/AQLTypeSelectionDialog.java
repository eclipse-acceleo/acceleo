/*******************************************************************************
 *  Copyright (c) 2018, 2023 Obeo. 
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

import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
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
 * Type selection dialog based on {@link AQLUtils#computeAvailableTypes(List, boolean, boolean, boolean)}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLTypeSelectionDialog extends MessageDialog {

	/**
	 * {@link Collection} {@link ITreeContentProvider}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class CollectionContentProvider extends ArrayContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object parentElement) {
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
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
	 * The default type of the variable.
	 */
	private final String defaultType;

	/**
	 * The {@link List} of available types.
	 */
	private final List<String> availableTypes;

	/**
	 * The selected type.
	 */
	private String selectedType;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            the parent {@link Shell}
	 * @param variableName
	 *            the variable name
	 * @param defaultType
	 * @param nsURIs
	 *            the {@link List} of regitered {@link EPackage#getNsURI() nsURI} the default type of the
	 *            variable
	 * @param includePrimitiveTypes
	 *            <code>true</code> if we should include primitive types, <code>false</code> otherwise
	 * @param includeSequenceTypes
	 *            <code>true</code> if we should include {@link SequenceType}, <code>false</code> otherwise
	 * @param includeSetTypes
	 *            <code>true</code> if we should include {@link SetType}, <code>false</code> otherwise
	 */
	public AQLTypeSelectionDialog(Shell parentShell, String variableName, String defaultType,
			List<String> uris, boolean includePrimitiveTypes, boolean includeSequenceTypes,
			boolean includeSetTypes) {
		super(parentShell, "Select a variable type for " + variableName, null, "Select a type.",
				MessageDialog.QUESTION, new String[] {IDialogConstants.OK_LABEL,
						IDialogConstants.CANCEL_LABEL }, 0);
		this.defaultType = defaultType;
		availableTypes = AQLUtils.computeAvailableTypes(uris, includePrimitiveTypes, includeSequenceTypes,
				includeSetTypes);
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		final Composite container = new Composite(parent, parent.getStyle());
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final FilteredTree filteredTree = new FilteredTree(container, SWT.BORDER, new PatternFilter(), true);
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
				selectedType = (String)selected;
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				buttonPressed(IDialogConstants.OK_ID);
			}
		});
		treeViewer.setInput(availableTypes);
		if (defaultType != null) {
			treeViewer.setSelection(new StructuredSelection(defaultType));
		}

		return container;
	}

	/**
	 * Gets the selected type.
	 * 
	 * @return the selected type
	 */
	public String getSelectedType() {
		return selectedType;
	}

}
