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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile.main;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * This is a wizard page to create an Acceleo Main template. Its role is to create a new Acceleo Main file
 * resource in the provided container. If the container resource (a folder or a project) is selected in the
 * workspace when the wizard page is opened, it will accept it as the target container. The wizard page
 * creates one file with the extension "mtl".
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewMainTemplateWizardPage extends WizardPage {

	/**
	 * Default text in the "Template Name" text widget.
	 */
	private static final String DEFAULT_MAIN_TEMPLATE_NAME = "main"; //$NON-NLS-1$

	/**
	 * The containing resource path in the workspace.
	 */
	private Text templateContainer;

	/**
	 * The template name.
	 */
	private Text templateName;

	/**
	 * The workspace selection when the page has been opened.
	 */
	private String selectedContainer;

	/**
	 * Constructor.
	 * 
	 * @param selectedContainer
	 *            is the containing resource path in the workspace (can be null)
	 */
	public AcceleoNewMainTemplateWizardPage(String selectedContainer) {
		super(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizardPage.Name")); //$NON-NLS-1$
		this.selectedContainer = selectedContainer;
		setTitle(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizardPage.Title1")); //$NON-NLS-1$
		setDescription(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizardPage.Description", //$NON-NLS-1$
				IAcceleoConstants.MTL_FILE_EXTENSION));
		setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite rootContainer = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		rootContainer.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		gridData = new GridData(GridData.FILL_BOTH);
		final int heightHint = 420;
		gridData.heightHint = heightHint;
		rootContainer.setLayoutData(gridData);
		createTemplateGroup(rootContainer);
		setControl(rootContainer);
		dialogChanged();
	}

	/**
	 * Creates a group for the template settings : name, path...
	 * 
	 * @param rootContainer
	 *            is the root control
	 */
	private void createTemplateGroup(Composite rootContainer) {
		final Composite templateGroup = new Composite(rootContainer, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		templateGroup.setLayout(layout);
		templateGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		createTemplateContainerComposite(templateGroup);
		createTemplateContainerName(templateGroup);
	}

	/**
	 * Create the widgets for the template container name.
	 * 
	 * @param templateGroup
	 *            is the parent composite
	 */
	private void createTemplateContainerName(Composite templateGroup) {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		Label nameLabel = new Label(templateGroup, SWT.NULL);
		nameLabel.setText(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizardPage.TemplateName") + ':'); //$NON-NLS-1$
		templateName = new Text(templateGroup, SWT.BORDER | SWT.SINGLE);
		templateName.setText(DEFAULT_MAIN_TEMPLATE_NAME);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		final int widthHint = 270;
		gridData.widthHint = widthHint;
		gridData.horizontalSpan = 2;
		templateName.setLayoutData(gridData);
		templateName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
	}

	/**
	 * Creates a composite for the template container selection.
	 * 
	 * @param templateGroup
	 *            is the template group
	 */
	private void createTemplateContainerComposite(Composite templateGroup) {
		Label label = new Label(templateGroup, SWT.NULL);
		label.setText(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizardPage.TemplateContainer") + ':'); //$NON-NLS-1$

		templateContainer = new Text(templateGroup, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		templateContainer.setLayoutData(gd);
		templateContainer.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();

			}
		});
		if (selectedContainer != null) {
			templateContainer.setText(selectedContainer);
		}
		Button button = new Button(templateGroup, SWT.PUSH);
		button.setText(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizardPage.Browse")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowseWorkspace();
			}
		});
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for the template container field.
	 */
	private void handleBrowseWorkspace() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin
				.getWorkspace().getRoot(), false, AcceleoUIMessages
				.getString("AcceleoNewMainTemplateWizardPage.ContainerSelection")); //$NON-NLS-1$
		IResource current = null;
		IPath path = new Path(getTemplateContainer());
		while (current == null && path.segmentCount() > 0) {
			current = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
			if (current == null) {
				path = path.removeLastSegments(1);
			}
		}
		if (current != null) {
			dialog.setInitialSelections(new Object[] {current });
		}
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				templateContainer.setText(((Path)result[0]).toString());
			}
		}
	}

	/**
	 * Validates the changes on the page.
	 */
	public void dialogChanged() {
		IPath containerPath = new Path(getTemplateContainer());
		String fileName = getTemplateName();
		if (containerPath.segmentCount() == 0) {
			updateStatus(AcceleoUIMessages
					.getString("AcceleoNewMainTemplateWizardPage.Error.MissingContainer")); //$NON-NLS-1$
		} else if (ResourcesPlugin.getWorkspace().getRoot().getProject(containerPath.segment(0)).exists()
				&& !ResourcesPlugin.getWorkspace().getRoot().getProject(containerPath.segment(0))
						.isAccessible()) {
			updateStatus(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizardPage.Error.ReadOnly")); //$NON-NLS-1$
		} else if (fileName.length() == 0) {
			updateStatus(AcceleoUIMessages
					.getString("AcceleoNewMainTemplateWizardPage.Error.MissingFileName")); //$NON-NLS-1$
		} else if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus(AcceleoUIMessages
					.getString("AcceleoNewMainTemplateWizardPage.Error.InvalidFileName")); //$NON-NLS-1$
		} else if (ResourcesPlugin.getWorkspace().getRoot().exists(
				containerPath.append(fileName).addFileExtension(IAcceleoConstants.MTL_FILE_EXTENSION))) {
			updateStatus(AcceleoUIMessages.getString(
					"AcceleoNewMainTemplateWizardPage.Error.ExistingFile", fileName)); //$NON-NLS-1$
		} else {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(containerPath.append(fileName));
			if (file.getProject().isAccessible()
					&& new AcceleoProject(file.getProject()).getOutputFilePath(file) == null) {
				updateStatus(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizardPage.Error.JavaFolder")); //$NON-NLS-1$
			} else {
				updateStatus(null);
			}
		}
	}

	/**
	 * Updates the status of the page.
	 * 
	 * @param message
	 *            is the status message.
	 */
	public void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	/**
	 * Gets the path of the container (workspace relative path).
	 * 
	 * @return the path of the container
	 */
	public String getTemplateContainer() {
		if (templateContainer != null && templateContainer.getText() != null) {
			return templateContainer.getText();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Gets the template name (without extension).
	 * 
	 * @return the template name
	 */
	public String getTemplateName() {
		if (templateName != null && templateName.getText() != null) {
			return templateName.getText();
		}
		return ""; //$NON-NLS-1$
	}

}
