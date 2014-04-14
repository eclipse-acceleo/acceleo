/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.ui.internal.wizards.newproject;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.unit.ui.Activator;
import org.eclipse.acceleo.unit.ui.internal.wizards.AUnitMessages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * The AUnitProjectPage.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public class AUnitProjectPage extends WizardNewProjectCreationPage {

	/**
	 * The "Resource working set" ID.
	 */
	private static final String RESOURCE_WORKING_SET_ID = "org.eclipse.ui.resourceWorkingSetPage"; //$NON-NLS-1$

	/**
	 * The "Java working set" ID.
	 */
	private static final String JAVA_WORKING_SET_ID = "org.eclipse.jdt.ui.JavaWorkingSetPage"; //$NON-NLS-1$

	/**
	 * The current selection.
	 */
	private IStructuredSelection selection;

	/**
	 * The basePackage text field.
	 */
	private Text acceleoTargetPath;

	/**
	 * The targetProject attribute.
	 */
	private IProject targetProject;

	/**
	 * The constructor.
	 * 
	 * @param selection
	 *            the selection.
	 */
	public AUnitProjectPage(IStructuredSelection selection) {
		super(AUnitMessages.getString("AUnitProjectPage.Title")); //$NON-NLS-1$

		this.selection = selection;
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof IJavaProject) {
			IJavaProject javaProject = (IJavaProject)firstElement;
			targetProject = javaProject.getProject();
			try {
				IProjectNature nature = targetProject.getNature(IAcceleoConstants.ACCELEO_NATURE_ID);
				if (nature != null) {
					this.setInitialProjectName(targetProject.getName() + ".test"); //$NON-NLS-1$
				} else {
					targetProject = null;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.dialogs.WizardNewProjectCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		Group projectGroup = new Group((Composite)getControl(), SWT.NONE);
		projectGroup.setText(AUnitMessages.getString("AUnitProjectPage.ProjectGroupTitle")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label acceleoTargetLabel = new Label(projectGroup, SWT.NONE);
		acceleoTargetLabel.setText(AUnitMessages.getString("AUnitProjectPage.AcceleoTargetLabel")); //$NON-NLS-1$
		acceleoTargetLabel.setFont(parent.getFont());
		acceleoTargetLabel.setToolTipText(AUnitMessages.getString("AUnitProjectPage.AcceleoTargetLabelHelp")); //$NON-NLS-1$

		// new project name entry field
		acceleoTargetPath = new Text(projectGroup, SWT.BORDER);
		if (targetProject != null) {
			acceleoTargetPath.setText(targetProject.getName());
		}
		GridData data = new GridData(GridData.FILL_HORIZONTAL);

		final int width = 250;
		data.widthHint = width;
		getAcceleoTargetPath().setLayoutData(data);
		getAcceleoTargetPath().setFont(parent.getFont());
		getAcceleoTargetPath().addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				boolean validatePage = validatePage();
				setPageComplete(validatePage);
			}
		});
		Button modelButton = new Button(projectGroup, SWT.PUSH);
		modelButton.setText(AUnitMessages.getString("AUnitProjectPage.SearchModelText")); //$NON-NLS-1$
		modelButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				/*
				 * Empty by default
				 */
			}

			public void widgetSelected(SelectionEvent e) {
				handleBrowseAcceleoTargetButton();
			}
		});
		this.validatePage();

		createWorkingSetGroup((Composite)getControl(), selection, new String[] {RESOURCE_WORKING_SET_ID,
				JAVA_WORKING_SET_ID, });
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#validatePage()
	 */
	@Override
	protected boolean validatePage() {
		boolean isValid = super.validatePage();

		return isValid;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#getTitle()
	 */
	@Override
	public String getTitle() {
		return AUnitMessages.getString("AUnitProjectPage.Title"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getImage()
	 */
	@Override
	public Image getImage() {
		return Activator.getDefault().getImage("icons/acceleo-unit-project-wizard.png"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#getDescription()
	 */
	@Override
	public String getDescription() {
		return AUnitMessages.getString("AUnitProjectPage.Description"); //$NON-NLS-1$
	}

	/**
	 * Show a dialog that lists all the models.
	 */
	private void handleBrowseAcceleoTargetButton() {
		FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(getShell(), false,
				ResourcesPlugin.getWorkspace().getRoot(), IResource.PROJECT);
		dialog.setTitle(AUnitMessages.getString("AUnitProjectPage.SelectModelDialogTitle")); //$NON-NLS-1$

		String path = getAcceleoTargetPath().getText();
		if (path != null && path.length() > 0 && new Path(path).lastSegment().length() > 0) {
			dialog.setInitialPattern(new Path(path).lastSegment());
		}

		dialog.open();
		if (dialog.getResult() != null && dialog.getResult().length > 0
				&& dialog.getResult()[0] instanceof IProject) {
			getAcceleoTargetPath().setText(((IProject)dialog.getResult()[0]).getName());
		}
	}

	/**
	 * The acceleoTargetPath getter.
	 * 
	 * @return the acceleoTargetPath
	 */
	public Text getAcceleoTargetPath() {
		return acceleoTargetPath;
	}
}
