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
package org.eclipse.acceleo.internal.ide.ui.wizards.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.wizards.module.AcceleoModuleComposite;
import org.eclipse.acceleo.internal.ide.ui.wizards.module.IAcceleoModuleCompositeListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TreeItem;

/**
 * The modules creation page of the Acceleo project wizard.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoModulesCreationPage extends WizardPage implements IAcceleoModuleCompositeListener {

	/**
	 * The Acceleo Module composite used to create the modules.
	 */
	private final List<AcceleoModule> acceleoModules = new ArrayList<AcceleoModule>();

	/**
	 * The treeviewer containing all the modules.
	 */
	private TreeViewer treeViewer;

	/**
	 * Button to add a new module.
	 */
	private Button addButton;

	/**
	 * Button to remove a module.
	 */
	private Button removeButton;

	/**
	 * The root.
	 */
	private Composite root;

	/**
	 * The Acceleo module composite.
	 */
	private AcceleoModuleComposite acceleoModuleComposite;

	/**
	 * The root container of the modules.
	 */
	private String container;

	/**
	 * The constructor.
	 */
	public AcceleoModulesCreationPage() {
		super(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Name")); //$NON-NLS-1$
		setTitle(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Title1")); //$NON-NLS-1$
		setDescription(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Description", //$NON-NLS-1$
				IAcceleoConstants.MTL_FILE_EXTENSION));
		setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$
	}

	/**
	 * Sets the container.
	 * 
	 * @param container
	 *            The container.
	 */
	public void setContainer(String container) {
		this.container = container;
		if (acceleoModuleComposite != null) {
			this.acceleoModuleComposite.setModuleContainer(container);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		root = new Composite(parent, SWT.NONE);
		final int numColumns = 3;
		GridLayout rootContainerLayout = new GridLayout();
		rootContainerLayout.numColumns = numColumns;
		root.setLayout(rootContainerLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		root.setLayoutData(gridData);

		this.createModuleGroup(root);
		this.createModuleComposite(root);
		this.setControl(root);

		this.treeViewer.setInput(acceleoModules);
	}

	/**
	 * Creates the module group.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	private void createModuleGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Template")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		group.setLayout(layout);
		layout.numColumns = 1;
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		final int widthHint = 150;
		gridData.widthHint = widthHint;
		gridData.minimumWidth = widthHint;
		final int min = 500;
		gridData.heightHint = min;
		gridData.minimumHeight = min;
		group.setLayoutData(gridData);
		createTreeViewer(group);
		createButtons(group);
	}

	/**
	 * Creates the tree viewer.
	 * 
	 * @param group
	 *            The module group.
	 */
	private void createTreeViewer(Group group) {
		treeViewer = new TreeViewer(group);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		final int heightHint = 500;
		gridData.heightHint = heightHint;
		final int widthHint = 150;
		gridData.widthHint = widthHint;
		treeViewer.getTree().setLayoutData(gridData);
		treeViewer.setContentProvider(new ModulesTreeContentProvider());
		treeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return AcceleoUIActivator.getDefault().getImage("icons/AcceleoEditor.gif"); //$NON-NLS-1$;
			}

			@Override
			public String getText(Object element) {
				if (element instanceof AcceleoModule) {
					return ((AcceleoModule)element).getName();
				}
				return null;
			}
		});
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				moduleSelectionChanged(event.getSelection());
			}
		});
	}

	/**
	 * Handles the change in the selection of the module.
	 * 
	 * @param selection
	 *            The selection
	 */
	private void moduleSelectionChanged(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof AcceleoModule) {
				AcceleoModule acceleoModule = (AcceleoModule)firstElement;
				acceleoModuleComposite.setAcceleoModuleWithoutUpdate(acceleoModule);
			}
		}
	}

	/**
	 * Creates the add and remove buttons.
	 * 
	 * @param group
	 *            The group.
	 */
	private void createButtons(Group group) {
		Composite buttonGroup = new Composite(group, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		addButton = new Button(buttonGroup, SWT.NONE);
		Image addImage = AcceleoUIActivator.getDefault().getImage("icons/add_obj.gif"); //$NON-NLS-1$
		addButton.setImage(addImage);
		addButton.setToolTipText(AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.AddButton.Tooltip")); //$NON-NLS-1$
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addModule(treeViewer.getSelection());
				treeViewer.refresh();
			}
		});
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		final int widthHint = 100;
		gridData.widthHint = widthHint;
		gridData.verticalAlignment = SWT.TOP;
		addButton.setLayoutData(gridData);
		removeButton = new Button(buttonGroup, SWT.NONE);
		Image removeImage = AcceleoUIActivator.getDefault().getImage("icons/delete_obj.gif"); //$NON-NLS-1$
		removeButton.setImage(removeImage);
		removeButton.setToolTipText(AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.RemoveButton.Tooltip")); //$NON-NLS-1$
		removeButton.setLayoutData(gridData);
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeModule(treeViewer.getSelection());
				treeViewer.refresh();
			}
		});
		buttonGroup.setLayout(layout);
		buttonGroup.setLayoutData(gridData);
	}

	/**
	 * Adds an Acceleo module.
	 * 
	 * @param selection
	 *            The current selection
	 */
	private void addModule(ISelection selection) {
		AcceleoModule acceleoModule = AcceleowizardmodelFactory.eINSTANCE.createAcceleoModule();
		acceleoModule.setName(AcceleoModuleComposite.MODULE_NAME);
		AcceleoModuleElement acceleoModuleElement = AcceleowizardmodelFactory.eINSTANCE
				.createAcceleoModuleElement();
		acceleoModuleElement.setName(AcceleoModuleComposite.MODULE_ELEMENT_NAME);
		acceleoModule.setModuleElement(acceleoModuleElement);
		this.acceleoModules.add(acceleoModule);
		this.acceleoModuleComposite.setAcceleoModuleWithoutUpdate(acceleoModule);
		TreeItem[] items = this.treeViewer.getTree().getItems();
		this.treeViewer.getTree().setSelection(items[items.length - 1]);
	}

	/**
	 * Removes an Acceleo module.
	 * 
	 * @param selection
	 *            The current selection.
	 */
	private void removeModule(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			this.acceleoModules.removeAll(structuredSelection.toList());
		}
	}

	/**
	 * Creates the module composite.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	private void createModuleComposite(Composite parent) {
		acceleoModuleComposite = new AcceleoModuleComposite(parent, this);
		if (container != null) {
			acceleoModuleComposite.setModuleContainer(container);
		}
		AcceleoModule acceleoModule = AcceleowizardmodelFactory.eINSTANCE.createAcceleoModule();
		acceleoModule.setName(AcceleoModuleComposite.MODULE_NAME);
		AcceleoModuleElement acceleoModuleElement = AcceleowizardmodelFactory.eINSTANCE
				.createAcceleoModuleElement();
		acceleoModuleElement.setName(AcceleoModuleComposite.MODULE_ELEMENT_NAME);
		acceleoModule.setModuleElement(acceleoModuleElement);
		this.acceleoModules.add(acceleoModule);
		this.acceleoModuleComposite.setAcceleoModule(acceleoModule);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.module.IAcceleoModuleCompositeListener#applyToStatusLine(org.eclipse.core.runtime.IStatus)
	 */
	public void applyToStatusLine(IStatus status) {
		String message = status.getMessage();
		if (message != null && message.length() == 0) {
			message = null;
		}
		switch (status.getSeverity()) {
			case IStatus.OK:
				setMessage(message, IMessageProvider.NONE);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			case IStatus.WARNING:
				setMessage(message, IMessageProvider.WARNING);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			case IStatus.INFO:
				setMessage(message, IMessageProvider.INFORMATION);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			default:
				setMessage(null);
				setErrorMessage(message);
				setPageComplete(false);
				break;
		}
		this.checkDuplicates();
		this.treeViewer.refresh();
	}

	/**
	 * Checks duplicate modules.
	 */
	private void checkDuplicates() {
		for (int i = 0; i < acceleoModules.size(); i++) {
			AcceleoModule acceleoModule = acceleoModules.get(i);
			for (int j = 0; j < acceleoModules.size(); j++) {
				AcceleoModule acceleoModule2 = acceleoModules.get(j);
				if (i != j) {
					String moduleName = acceleoModule.getParentFolder() + acceleoModule.getName();
					String moduleName2 = acceleoModule2.getParentFolder() + acceleoModule2.getName();
					if (moduleName.equals(moduleName2)) {
						this.setMessage(null);
						this.setErrorMessage(AcceleoUIMessages
								.getString("AcceleoModuleCreationPage.TwoIdenticalModule")); //$NON-NLS-1$
						this.setPageComplete(false);
						return;
					}
				}
			}
		}
	}

	/**
	 * Returns the list of Acceleo modules to create in the project.
	 * 
	 * @return The list of Acceleo modules to create in the project.
	 */
	public List<AcceleoModule> getAllModules() {
		return acceleoModules;
	}

	/**
	 * The content provider.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	private class ModulesTreeContentProvider implements ITreeContentProvider {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// do nothing

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof List) {
				List<AcceleoModule> modules = new ArrayList<AcceleoModule>();
				List<?> list = (List<?>)parentElement;
				for (Object object : list) {
					if (object instanceof AcceleoModule) {
						modules.add((AcceleoModule)object);
					}
				}
				return modules.toArray();
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
	}

}
