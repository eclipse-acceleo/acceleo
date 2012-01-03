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
package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

import java.util.StringTokenizer;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * A wizard page used to configure the UI of the new module launcher.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewUIConfigurationPage extends WizardPage {

	/**
	 * Default text of the 'modelNameFilter' widget.
	 */
	private static final String DEFAULT_MODEL_NAME_FILTER = "*.*"; //$NON-NLS-1$

	/**
	 * Default text of the 'targetFolderAccess' widget.
	 */
	private static final String DEFAULT_OUTPUT_FOLDER = "IContainer target = model.getProject().getFolder(\"src-gen\");"; //$NON-NLS-1$

	/**
	 * The module name.
	 */
	private Text moduleName;

	/**
	 * The initial selected project name. It is used to compute the initial module name.
	 */
	private String initialSelectedProjectName;

	/**
	 * The code generation (popup action...) is only available for the file which name validates the filter.
	 */
	private Text modelNameFilter;

	/**
	 * It contains 'Java' code. It defines the way to access the target folder from the model file.
	 * <p>
	 * <code> IContainer target = model.getProject(); </code>
	 * </p>
	 */
	private Text targetFolderAccess;

	/**
	 * Constructor.
	 * 
	 * @param pageName
	 *            is the page name
	 * @param initialSelectedProjectName
	 *            the initial selected project name, it is used to compute the initial module name
	 */
	public AcceleoNewUIConfigurationPage(String pageName, String initialSelectedProjectName) {
		super(pageName);
		setTitle(AcceleoUIMessages.getString("AcceleoNewUIConfigurationPage.Title")); //$NON-NLS-1$
		setDescription(AcceleoUIMessages.getString("AcceleoNewUIConfigurationPage.Description")); //$NON-NLS-1$
		setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$
		this.initialSelectedProjectName = initialSelectedProjectName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite rootContainer = new Composite(parent, SWT.NULL);
		GridLayout rootContainerLayout = new GridLayout();
		final int numColumns = 1;
		final int marginTop = 14;
		final int verticalSpacing = 15;
		final int marginLeft = 7;
		final int marginRight = 7;
		rootContainerLayout.numColumns = numColumns;
		rootContainerLayout.marginTop = marginTop;
		rootContainerLayout.verticalSpacing = verticalSpacing;
		rootContainerLayout.marginLeft = marginLeft;
		rootContainerLayout.marginRight = marginRight;
		rootContainer.setLayout(rootContainerLayout);
		createModuleNameGroup(rootContainer);
		createConfigurationGroup(rootContainer);
		setControl(rootContainer);
		dialogChanged();
		moduleName.setText(computeModuleName());
		modelNameFilter.setText(DEFAULT_MODEL_NAME_FILTER);
		targetFolderAccess.setText(DEFAULT_OUTPUT_FOLDER);

	}

	/**
	 * Initializes the module name by using the initial project name.
	 * 
	 * @return the initial module name
	 */
	private String computeModuleName() {
		if (initialSelectedProjectName != null && initialSelectedProjectName.length() > 0) {
			String mName = initialSelectedProjectName;
			if (mName.endsWith(AcceleoNewProjectUIWizard.MODULE_UI_NAME_SUFFIX)) {
				mName = mName.substring(0, mName.length()
						- AcceleoNewProjectUIWizard.MODULE_UI_NAME_SUFFIX.length());
			}
			if (mName.startsWith(AcceleoNewProjectUIWizard.MODULE_UI_NAME_PREFIX)) {
				mName = mName.substring(AcceleoNewProjectUIWizard.MODULE_UI_NAME_PREFIX.length());
			} else {
				int i = mName.lastIndexOf('.');
				if (i > -1) {
					mName = mName.substring(i + 1);
				}
			}
			if (mName.length() > 0) {
				StringTokenizer st = new StringTokenizer(mName, "._-* \t"); //$NON-NLS-1$
				mName = ""; //$NON-NLS-1$
				while (st.hasMoreTokens()) {
					String token = st.nextToken();
					if (token.length() > 0) {
						mName += Character.toUpperCase(token.charAt(0)) + token.substring(1);
					}
					if (token.length() > 0 && st.hasMoreTokens()) {
						mName += " "; //$NON-NLS-1$
					}
				}
			}
			return mName;
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Creates a group for the module name text area.
	 * 
	 * @param rootContainer
	 *            is the root control
	 */
	private void createModuleNameGroup(Composite rootContainer) {
		Group moduleNameGroup = new Group(rootContainer, SWT.NONE);
		moduleNameGroup.setText(AcceleoUIMessages.getString("AcceleoNewUIConfigurationPage.ModuleName")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		moduleNameGroup.setLayout(layout);
		moduleNameGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		moduleName = new Text(moduleNameGroup, SWT.BORDER | SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 4;
		moduleName.setLayoutData(gridData);
		moduleName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
	}

	/**
	 * Creates a group for the UI settings : file name filter...
	 * 
	 * @param rootContainer
	 *            is the root control
	 */
	private void createConfigurationGroup(Composite rootContainer) {
		Group configGroup = new Group(rootContainer, SWT.NONE);
		configGroup.setText(AcceleoUIMessages.getString("AcceleoNewUIConfigurationPage.Group")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		configGroup.setLayout(layout);
		configGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Composite configContainer = new Composite(configGroup, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 4;
		configContainer.setLayoutData(gridData);
		GridLayout configContainerLayout = new GridLayout();
		configContainerLayout.numColumns = 1;
		configContainer.setLayout(configContainerLayout);
		Label modelLabel = new Label(configContainer, SWT.NULL);
		modelLabel.setText(AcceleoUIMessages.getString("AcceleoNewUIConfigurationPage.ModelName") + ':'); //$NON-NLS-1$
		modelNameFilter = new Text(configContainer, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 4;
		modelNameFilter.setLayoutData(gridData);
		modelNameFilter.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		Label targetLabel = new Label(configContainer, SWT.NULL);
		targetLabel.setText(AcceleoUIMessages.getString("AcceleoNewUIConfigurationPage.TargetFolder") + ':'); //$NON-NLS-1$
		targetFolderAccess = new Text(configContainer, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 4;
		final int heightHint = 60;
		gridData.heightHint = heightHint;
		targetFolderAccess.setLayoutData(gridData);
		targetFolderAccess.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
	}

	/**
	 * Validates the changes on the page.
	 */
	protected void dialogChanged() {
		if (getModuleName().length() == 0) {
			updateStatus(AcceleoUIMessages.getString("AcceleoNewUIConfigurationPage.Error.MissingModuleName")); //$NON-NLS-1$
		} else if (getModelNameFilter().length() == 0) {
			updateStatus(AcceleoUIMessages.getString("AcceleoNewUIConfigurationPage.Error.MissingModelName")); //$NON-NLS-1$
		} else if (getTargetFolderAccess().length() == 0) {
			updateStatus(AcceleoUIMessages
					.getString("AcceleoNewUIConfigurationPage.Error.MissingTargetFolder")); //$NON-NLS-1$
		} else {
			updateStatus(null);
		}
	}

	/**
	 * Updates the status of the page.
	 * 
	 * @param message
	 *            is the error message.
	 */
	private void updateStatus(String message) {
		setMessage(message);
		setPageComplete(true);
	}

	/**
	 * Gets the module name.
	 * 
	 * @return the module name
	 */
	public String getModuleName() {
		return moduleName.getText();
	}

	/**
	 * Gets the model name filter. The code generation (popup action...) is only available for the file which
	 * name validates the filter.
	 * 
	 * @return the model name filter
	 */
	public String getModelNameFilter() {
		return modelNameFilter.getText();
	}

	/**
	 * Gets the 'Java' code that defines the way to access the target folder from the model file.
	 * <p>
	 * <code> IContainer target = model.getProject(); </code>
	 * </p>
	 * 
	 * @return the 'Java' code to access the target folder
	 */
	public String getTargetFolderAccess() {
		return targetFolderAccess.getText();
	}

}
