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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * The master composite of the 'AcceleoNewTemplatesWizard' wizard. This composite contains the useful widgets
 * to create and delete the templates. The way to manage the selection of one template in the tree viewer must
 * be subclassed.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public abstract class AbstractAcceleoNewTemplatesMasterComposite extends Composite {

	/**
	 * The tree viewer. It shows the templates.
	 */
	private TreeViewer treeViewer;

	/**
	 * Button to add a new template.
	 */
	private Button addButton;

	/**
	 * Button to remove a template.
	 */
	private Button removeButton;

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot be null)
	 * @param controller
	 *            is the controller
	 * @param showTemplates
	 *            indicates if the templates are shown on the left of the composite
	 */
	public AbstractAcceleoNewTemplatesMasterComposite(Composite parent,
			AcceleoNewTemplatesGlobalController controller, boolean showTemplates) {
		super(parent, SWT.NONE);
		final int numColumns = 3;
		GridLayout rootContainerLayout = new GridLayout();
		rootContainerLayout.numColumns = numColumns;
		this.setLayout(rootContainerLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		this.setLayoutData(gridData);
		Composite treeViewerGroup;
		if (showTemplates) {
			Group group = new Group(this, SWT.NONE);
			group.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Template")); //$NON-NLS-1$
			treeViewerGroup = group;
		} else {
			treeViewerGroup = new Composite(parent, SWT.NONE);
		}
		GridLayout layout = new GridLayout();
		treeViewerGroup.setLayout(layout);
		layout.numColumns = 1;
		treeViewerGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		createTreeViewer(treeViewerGroup);
		treeViewer.setInput(controller);
		gridData = new GridData(GridData.FILL_BOTH);
		final int heightHint = 350;
		gridData.heightHint = heightHint;
		final int widthHint = 130;
		gridData.widthHint = widthHint;
		treeViewer.getTree().setLayoutData(gridData);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				acceleoSelectionChanged(event.getSelection());
			}
		});
		createButtonGroup(treeViewerGroup);
	}

	/**
	 * Creates the group for 'Add' and 'Remove' buttons.
	 * 
	 * @param treeViewerGroup
	 *            is the parent container
	 */
	private void createButtonGroup(Composite treeViewerGroup) {
		Composite buttonGroup = new Composite(treeViewerGroup, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		addButton = new Button(buttonGroup, SWT.NONE);
		addButton.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.AddButton")); //$NON-NLS-1$
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addController(treeViewer.getSelection());
				treeViewer.refresh();
			}
		});
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		final int widthHint = 100;
		gridData.widthHint = widthHint;
		gridData.verticalAlignment = SWT.TOP;
		addButton.setLayoutData(gridData);
		removeButton = new Button(buttonGroup, SWT.NONE);
		removeButton.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.RemoveButton")); //$NON-NLS-1$
		removeButton.setLayoutData(gridData);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeController(treeViewer.getSelection());
				treeViewer.refresh();
			}
		});
		buttonGroup.setLayout(layout);
		buttonGroup.setLayoutData(gridData);
	}

	/**
	 * Creates the tree viewer which shows the templates.
	 * 
	 * @param treeViewerGroup
	 *            is the parent composite
	 */
	private void createTreeViewer(Composite treeViewerGroup) {
		treeViewer = new TreeViewer(treeViewerGroup);
		treeViewer.setContentProvider(new TemplatesTreeContentProvider());
		treeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return AcceleoUIActivator.getDefault().getImage("icons/AcceleoEditor.gif"); //$NON-NLS-1$;
			}

			@Override
			public String getText(Object element) {
				if (element instanceof AcceleoNewTemplatesWizardController
						&& ((AcceleoNewTemplatesWizardController)element).getModel() != null) {
					return ((AcceleoNewTemplatesWizardController)element).getModel().getTemplateShortName();
				}
				return null;
			}
		});
	}

	/**
	 * The way to manage the selection of one template in the tree viewer. It must be redefined.
	 * 
	 * @param selection
	 *            is the current selection
	 */
	protected abstract void acceleoSelectionChanged(ISelection selection);

	/**
	 * The way to add a new template in the tree viewer.
	 * 
	 * @param selection
	 *            is the current selection
	 */
	protected abstract void addController(ISelection selection);

	/**
	 * The way to remove the selected template.
	 * 
	 * @param selection
	 *            is the current selection
	 */
	protected abstract void removeController(ISelection selection);

	/**
	 * Gets the tree viewer.
	 * 
	 * @return the tree viewer
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * This will be used as the content provider of this composite's tree viewer.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	class TemplatesTreeContentProvider implements ITreeContentProvider {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof AcceleoNewTemplatesGlobalController) {
				return ((AcceleoNewTemplatesGlobalController)parentElement).getControllers().toArray();
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		public Object getParent(Object element) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		public boolean hasChildren(Object element) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
			// empty implementation
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// empty implementation
		}
	}
}
