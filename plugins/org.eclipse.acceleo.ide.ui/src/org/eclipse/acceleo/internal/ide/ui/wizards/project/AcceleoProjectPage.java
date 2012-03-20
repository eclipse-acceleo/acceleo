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
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMStandin;
import org.eclipse.jdt.launching.environments.IExecutionEnvironment;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * The first page of the new Acceleo project wizard.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoProjectPage extends WizardNewProjectCreationPage {

	/** The last selected EE JRE settings key. */
	private static final String LAST_SELECTED_EE_SETTINGS_KEY = JavaUI.ID_PLUGIN
			+ ".last.selected.execution.enviroment"; //$NON-NLS-1$

	/** The last selected JRE settings key. */
	private static final String LAST_SELECTED_JRE_SETTINGS_KEY = JavaUI.ID_PLUGIN
			+ ".last.selected.project.jre"; //$NON-NLS-1$

	/**
	 * The JRE preference link.
	 */
	private Link fPreferenceLink;

	/**
	 * The installed JVMs.
	 */
	private IVMInstall[] fInstalledJVMs;

	/**
	 * The JRE compliance.
	 */
	private String[] fJRECompliance;

	/**
	 * The installed execution environment.
	 */
	private IExecutionEnvironment[] fInstalledEEs;

	/**
	 * The execution environment compliance.
	 */
	private String[] fEECompliance;

	/**
	 * The selection.
	 */
	private IStructuredSelection selection;

	/**
	 * The Execution environment button.
	 */
	private Button executionEnvJREButton;

	/**
	 * The project specific JRE button.
	 */
	private Button projectSpecificJREButton;

	/**
	 * The default JRE button.
	 */
	private Button defaultJREButton;

	/**
	 * The execution environment combo.
	 */
	private Combo executionEnvJRECombo;

	/**
	 * The project specific JRE combo.
	 */
	private Combo projectSpecificJRECombo;

	/**
	 * The constructor.
	 * 
	 * @param pageName
	 *            The title of the page.
	 * @param selection
	 *            The selection.
	 */
	public AcceleoProjectPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		this.selection = selection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(org.eclipse.swt.widgets.Composite parent) {
		super.createControl(parent);
		createJREGroup((Composite)getControl());
		createWorkingSetGroup((Composite)getControl(), selection, new String[] {
				AcceleoProjectWizard.RESOURCE_WORKING_SET_ID, AcceleoProjectWizard.JAVA_WORKING_SET_ID, });
		Dialog.applyDialogFont(getControl());
	}

	/**
	 * Creates the JRE group.
	 * 
	 * @param parent
	 *            The composite parent.
	 */
	private void createJREGroup(Composite parent) {
		Group jreGroup = new Group(parent, SWT.NONE);
		jreGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		jreGroup.setFont(parent.getFont());
		jreGroup.setLayout(initGridLayout(new GridLayout(2, false), true));
		jreGroup.setText(AcceleoUIMessages.getString("AcceleoProjectPage.JRE")); //$NON-NLS-1$

		executionEnvJREButton = new Button(jreGroup, SWT.RADIO);
		executionEnvJREButton.setText(AcceleoUIMessages
				.getString("AcceleoProjectPage.ExecutionEnvironmentButtonLabel")); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		gd.horizontalAlignment = GridData.FILL;
		executionEnvJREButton.setLayoutData(gd);
		executionEnvJRECombo = new Combo(jreGroup, SWT.READ_ONLY);
		executionEnvJRECombo.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));

		projectSpecificJREButton = new Button(jreGroup, SWT.RADIO);
		projectSpecificJREButton.setText(AcceleoUIMessages
				.getString("AcceleoProjectPage.ProjectSpecificButtonLabel")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 1;
		gd.horizontalAlignment = GridData.FILL;
		projectSpecificJREButton.setLayoutData(gd);
		projectSpecificJRECombo = new Combo(jreGroup, SWT.READ_ONLY);
		projectSpecificJRECombo.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));

		defaultJREButton = new Button(jreGroup, SWT.RADIO);
		defaultJREButton.setText(AcceleoUIMessages.getString(
				"AcceleoProjectPage.DefaultJREButtonLabel", getDefaultJVMName())); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 1;
		gd.horizontalAlignment = GridData.FILL;
		defaultJREButton.setLayoutData(gd);

		fPreferenceLink = new Link(jreGroup, SWT.NONE);
		fPreferenceLink.setFont(jreGroup.getFont());
		fPreferenceLink.setText(AcceleoUIMessages.getString("AcceleoProjectPage.ConfigureJRE")); //$NON-NLS-1$
		fPreferenceLink.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		fPreferenceLink.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				final String jreID = "org.eclipse.jdt.debug.ui.preferences.VMPreferencePage"; //$NON-NLS-1$
				final String eeID = "org.eclipse.jdt.debug.ui.jreProfiles"; //$NON-NLS-1$
				final String complianceId = "org.eclipse.jdt.ui.preferences.CompliancePreferencePage"; //$NON-NLS-1$
				final String dataNoLink = "PropertyAndPreferencePage.nolink"; //$NON-NLS-1$
				Map<String, Boolean> data = new HashMap<String, Boolean>();
				data.put(dataNoLink, Boolean.TRUE);
				PreferencesUtil.createPreferenceDialogOn(getShell(), jreID,
						new String[] {jreID, complianceId, eeID }, data).open();
				defaultJREButton.setText(AcceleoUIMessages.getString(
						"AcceleoProjectPage.DefaultJREButtonLabel", getDefaultJVMName())); //$NON-NLS-1$
				fillInstalledJREs();
				fillExecutionEnvironments();
			}
		});

		fillInstalledJREs();
		fillExecutionEnvironments();
		// Select Execution Environment by default.
		executionEnvJREButton.setSelection(true);

		SelectionListener listener = new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				checkCompatibleEnvironment();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				checkCompatibleEnvironment();
			}
		};
		executionEnvJREButton.addSelectionListener(listener);
		executionEnvJRECombo.addSelectionListener(listener);
		projectSpecificJREButton.addSelectionListener(listener);
		projectSpecificJRECombo.addSelectionListener(listener);
		defaultJREButton.addSelectionListener(listener);
	}

	/**
	 * Checks if the environment is compatible with Acceleo.
	 */
	private void checkCompatibleEnvironment() {
		if (executionEnvJREButton.getSelection()) {
			String text = executionEnvJRECombo.getText();
			if (!"J2SE-1.5".equals(text) && !"JavaSE-1.6".equals(text) && !"JavaSE-1.7".equals(text)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				this.setMessage(
						AcceleoUIMessages.getString("AcceleoProjectPage.NotJava5or6or7"), IMessageProvider.WARNING); //$NON-NLS-1$
			} else {
				this.setMessage(null);
			}
		} else {
			this.setMessage(
					AcceleoUIMessages.getString("AcceleoProjectPage.NotJava5or6or7"), IMessageProvider.WARNING); //$NON-NLS-1$
		}
	}

	/**
	 * Returns the last selected EE JRE.
	 * 
	 * @return The last selected EE JRE.
	 */
	private String getLastSelectedEE() {
		IDialogSettings settings = JavaPlugin.getDefault().getDialogSettings();
		return settings.get(LAST_SELECTED_EE_SETTINGS_KEY);
	}

	/**
	 * Returns the last selected JRE.
	 * 
	 * @return The last selected JRE.
	 */
	private String getLastSelectedJRE() {
		IDialogSettings settings = JavaPlugin.getDefault().getDialogSettings();
		return settings.get(LAST_SELECTED_JRE_SETTINGS_KEY);
	}

	/**
	 * Returns the default JVM name.
	 * 
	 * @return The default JVM name.
	 */
	private String getDefaultJVMName() {
		IVMInstall install = JavaRuntime.getDefaultVMInstall();
		if (install != null) {
			return install.getName();
		}
		return AcceleoUIMessages.getString("AcceleoProjectPage.Unknown"); //$NON-NLS-1$
	}

	/**
	 * Returns the default execution environment name.
	 * 
	 * @return The default execution environment name.
	 */
	private String getDefaultEEName() {
		IVMInstall defaultVM = JavaRuntime.getDefaultVMInstall();

		IExecutionEnvironment[] environments = JavaRuntime.getExecutionEnvironmentsManager()
				.getExecutionEnvironments();
		if (defaultVM != null) {
			for (int i = 0; i < environments.length; i++) {
				IVMInstall eeDefaultVM = environments[i].getDefaultVM();
				if (eeDefaultVM != null && defaultVM.getId().equals(eeDefaultVM.getId())) {
					return environments[i].getId();
				}
			}
		}

		String defaultCC;
		if (defaultVM instanceof IVMInstall2) {
			defaultCC = JavaModelUtil.getCompilerCompliance((IVMInstall2)defaultVM, JavaCore.VERSION_1_4);
		} else {
			defaultCC = JavaCore.VERSION_1_4;
		}

		String result = "JavaSE-1.6"; //$NON-NLS-1$
		for (int i = 0; i < environments.length; i++) {
			String eeCompliance = JavaModelUtil.getExecutionEnvironmentCompliance(environments[i]);
			if (defaultCC.endsWith(eeCompliance)) {
				result = environments[i].getId();
				break;
			}
		}

		return result;
	}

	/**
	 * Returns the selected VM.
	 * 
	 * @return The selected VM.
	 */
	public String getSelectedJVM() {
		String selectedVM = getDefaultJVMName();
		if (projectSpecificJREButton.getSelection()) {
			int index = projectSpecificJRECombo.getSelectionIndex();
			// paranoia
			if (index >= 0 && index < fInstalledJVMs.length) {
				selectedVM = fInstalledJVMs[index].getName();
			}
		} else if (executionEnvJREButton.getSelection()) {
			selectedVM = executionEnvJRECombo.getText();
		}
		return selectedVM;
	}

	/**
	 * Returns the JRE container path.
	 * 
	 * @return The JRE container path.
	 */
	public IPath getJREContainerPath() {
		IPath jerContainerPath = JavaRuntime.newJREContainerPath(JavaRuntime.getDefaultVMInstall());
		if (projectSpecificJREButton.getSelection()) {
			int index = projectSpecificJRECombo.getSelectionIndex();
			if (index >= 0 && index < fInstalledJVMs.length) { // paranoia
				jerContainerPath = JavaRuntime.newJREContainerPath(fInstalledJVMs[index]);
			}
		} else if (executionEnvJREButton.getSelection()) {
			int index = executionEnvJRECombo.getSelectionIndex();
			if (index >= 0 && index < fInstalledEEs.length) { // paranoia
				jerContainerPath = JavaRuntime.newJREContainerPath(fInstalledEEs[index]);
			}
		}
		return jerContainerPath;
	}

	/**
	 * Returns the selected compiler compliance.
	 * 
	 * @return The selected compiler compliance.
	 */
	private String getSelectedCompilerCompliance() {
		String selectedCompilerCompilance = null;
		if (projectSpecificJREButton.getSelection()) {
			int index = projectSpecificJRECombo.getSelectionIndex();
			if (index >= 0 && index < fJRECompliance.length) { // paranoia
				selectedCompilerCompilance = fJRECompliance[index];
			}
		} else if (executionEnvJREButton.getSelection()) {
			int index = executionEnvJRECombo.getSelectionIndex();
			if (index >= 0 && index < fEECompliance.length) { // paranoia
				selectedCompilerCompilance = fEECompliance[index];
			}
		}
		return selectedCompilerCompilance;
	}

	/**
	 * Returns the workspace JREs.
	 * 
	 * @return The workspace JREs.
	 */
	private IVMInstall[] getWorkspaceJREs() {
		List<VMStandin> standins = new ArrayList<VMStandin>();
		IVMInstallType[] types = JavaRuntime.getVMInstallTypes();
		for (int i = 0; i < types.length; i++) {
			IVMInstallType type = types[i];
			IVMInstall[] installs = type.getVMInstalls();
			for (int j = 0; j < installs.length; j++) {
				IVMInstall install = installs[j];
				standins.add(new VMStandin(install));
			}
		}
		return standins.toArray(new IVMInstall[standins.size()]);
	}

	/**
	 * Initialize the grid layout.
	 * 
	 * @param layout
	 *            The grid layout.
	 * @param margins
	 *            Indicates if we should use margins.
	 * @return The configured grid layout.
	 */
	private GridLayout initGridLayout(GridLayout layout, boolean margins) {
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		if (margins) {
			layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
			layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		} else {
			layout.marginWidth = 0;
			layout.marginHeight = 0;
		}
		return layout;
	}

	/**
	 * Fill the project specific combo.
	 */
	private void fillInstalledJREs() {
		String selectedItem = getLastSelectedJRE();
		int selectionIndex = -1;
		if (projectSpecificJREButton.getSelection()) {
			selectionIndex = projectSpecificJRECombo.getSelectionIndex();
			// paranoia
			if (selectionIndex != -1) {
				selectedItem = projectSpecificJRECombo.getItems()[selectionIndex];
			}
		}

		fInstalledJVMs = getWorkspaceJREs();
		Arrays.sort(fInstalledJVMs, new Comparator<IVMInstall>() {

			@SuppressWarnings("unchecked")
			public int compare(IVMInstall i0, IVMInstall i1) {
				if (i1 instanceof IVMInstall2 && i0 instanceof IVMInstall2) {
					String cc0 = JavaModelUtil.getCompilerCompliance((IVMInstall2)i0, JavaCore.VERSION_1_4);
					String cc1 = JavaModelUtil.getCompilerCompliance((IVMInstall2)i1, JavaCore.VERSION_1_4);
					int result = cc1.compareTo(cc0);
					if (result != 0) {
						return result;
					}
				}
				return Policy.getComparator().compare(i0.getName(), i1.getName());
			}

		});
		// find new index
		selectionIndex = -1;
		String[] jreLabels = new String[fInstalledJVMs.length];
		fJRECompliance = new String[fInstalledJVMs.length];
		for (int i = 0; i < fInstalledJVMs.length; i++) {
			jreLabels[i] = fInstalledJVMs[i].getName();
			if (selectedItem != null && jreLabels[i].equals(selectedItem)) {
				selectionIndex = i;
			}
			if (fInstalledJVMs[i] instanceof IVMInstall2) {
				fJRECompliance[i] = JavaModelUtil.getCompilerCompliance((IVMInstall2)fInstalledJVMs[i],
						JavaCore.VERSION_1_4);
			} else {
				fJRECompliance[i] = JavaCore.VERSION_1_4;
			}
		}
		projectSpecificJRECombo.setItems(jreLabels);
		if (selectionIndex == -1) {
			projectSpecificJRECombo.setText(getDefaultJVMName());
		} else {
			projectSpecificJRECombo.setText(selectedItem);
		}
	}

	/**
	 * Fills the execution environments.
	 */
	private void fillExecutionEnvironments() {
		String selectedItem = getLastSelectedEE();
		int selectionIndex = -1;
		if (executionEnvJREButton.getSelection()) {
			selectionIndex = executionEnvJRECombo.getSelectionIndex();
			// paranoia
			if (selectionIndex != -1) {
				selectedItem = executionEnvJRECombo.getItems()[selectionIndex];
			}
		}

		fInstalledEEs = JavaRuntime.getExecutionEnvironmentsManager().getExecutionEnvironments();
		Arrays.sort(fInstalledEEs, new Comparator<IExecutionEnvironment>() {
			@SuppressWarnings("unchecked")
			public int compare(IExecutionEnvironment arg0, IExecutionEnvironment arg1) {
				return Policy.getComparator().compare(arg0.getId(), arg1.getId());
			}
		});
		// find new index
		selectionIndex = -1;
		String[] eeLabels = new String[fInstalledEEs.length];
		fEECompliance = new String[fInstalledEEs.length];
		for (int i = 0; i < fInstalledEEs.length; i++) {
			eeLabels[i] = fInstalledEEs[i].getId();
			if (selectedItem != null && eeLabels[i].equals(selectedItem)) {
				selectionIndex = i;
			}
			fEECompliance[i] = JavaModelUtil.getExecutionEnvironmentCompliance(fInstalledEEs[i]);
		}
		executionEnvJRECombo.setItems(eeLabels);
		if (selectionIndex == -1) {
			executionEnvJRECombo.setText(getDefaultEEName());
		} else {
			executionEnvJRECombo.setText(selectedItem);
		}
	}
}
