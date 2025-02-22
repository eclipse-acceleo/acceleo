/*******************************************************************************
 * Copyright (c) 2020, 2024 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug.ide.ui.launch;

import org.eclipse.acceleo.aql.ide.ui.dialog.AbstractResourceSelectionDialog;
import org.eclipse.acceleo.aql.ide.ui.dialog.FileSelectionDialog;
import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.ls.debug.ide.AcceleoDebugPlugin;
import org.eclipse.acceleo.aql.profiler.ProfilerUtils;
import org.eclipse.acceleo.aql.profiler.ProfilerUtils.Representation;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * The profiling launch configuration main tab.
 * 
 * @author wpiers
 */
public class AcceleoProfilerMainTab extends AcceleoMainTab {

	/**
	 * The profile model.
	 */
	private String profileModel;

	/**
	 * The initial profile model.
	 */
	private String initialProfileModel;

	/**
	 * The profile model {@link Representation}.
	 */
	private String profileModelRepresentation;

	/**
	 * The initial profile model {@link Representation}.
	 */
	private String initialProfileModelRepresentation;

	/**
	 * The profile model {@link Text}.
	 */
	private Text profileModelText;

	/**
	 * The profiler model {@link Representation} {@link Combo}.
	 */
	private Combo profileModelRepresentationCombo;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.setAttribute(AcceleoDebugger.PROFILE_MODEL, profileModel);
		configuration.setAttribute(AcceleoDebugger.PROFILE_MODEL_REPRESENTATION, profileModelRepresentation);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		try {
			if (configuration.hasAttribute(AcceleoDebugger.PROFILE_MODEL)) {
				profileModelText.setText(configuration.getAttribute(AcceleoDebugger.PROFILE_MODEL, ""));
				initialProfileModel = profileModel;
			}
			if (configuration.hasAttribute(AcceleoDebugger.PROFILE_MODEL_REPRESENTATION)) {
				profileModelRepresentationCombo.setText(configuration.getAttribute(
						AcceleoDebugger.PROFILE_MODEL_REPRESENTATION, Representation.TREE.name()));
				initialProfileModelRepresentation = profileModelRepresentation;
			}
		} catch (CoreException e) {
			AcceleoDebugPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoDebugPlugin.ID,
					"couldn't initialize from launch configuration", e));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		initialProfileModel = profileModel;
		initialProfileModelRepresentation = profileModelRepresentation;
		setDirty(isDirty());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean res = super.isValid(launchConfig);
		try {
			if (res) {
				if (!launchConfig.hasAttribute(AcceleoDebugger.PROFILE_MODEL)) {
					setErrorMessage("Select a profile model");
					res = false;
				} else if (!launchConfig.hasAttribute(AcceleoDebugger.PROFILE_MODEL_REPRESENTATION)) {
					setErrorMessage("Select a profile model representation");
					res = false;
				}
			}
		} catch (CoreException e) {
			AcceleoDebugPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoDebugPlugin.ID,
					"couldn't validate launch configuration", e));
		}
		if (res) {
			setErrorMessage(null);
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#isDirty()
	 */
	@Override
	protected boolean isDirty() {
		return super.isDirty() || profileModel != initialProfileModel
				|| profileModelRepresentation != initialProfileModelRepresentation;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		profileModelText = createProfileModelEditor((Composite)getControl());
		profileModelRepresentationCombo = createProfileModelRepresentationEditor((Composite)getControl());
	}

	/**
	 * Creates the profile destination {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Text}
	 */
	private Text createProfileModelEditor(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Profile model:");
		final Text res = new Text(group, SWT.BORDER);
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		res.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				profileModel = res.getText();
				setDirty(isDirty());
				updateLaunchConfigurationDialog();
			}
		});
		Button workspaceButton = createPushButton(group, WORKSPACE, null);
		workspaceButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleWorkspaceProfileModelButton();
			}
		});
		Button fileSystemButton = createPushButton(group, FILE_SYSTEM, null);
		fileSystemButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleFileSystemProfileModelButton();
			}
		});
		return res;
	}

	/**
	 * Creates the profile model {@link Combo}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Combo}
	 */
	private Combo createProfileModelRepresentationEditor(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Profile model representation:");
		final Combo res = new Combo(group, SWT.BORDER | SWT.READ_ONLY);
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		res.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				profileModelRepresentation = res.getText();
				setDirty(isDirty());
				updateLaunchConfigurationDialog();
			}
		});
		for (Representation value : Representation.values()) {
			res.add(value.name());
		}
		return res;
	}

	/**
	 * Show a dialog that lists all the models.
	 */
	private void handleWorkspaceProfileModelButton() {
		final AbstractResourceSelectionDialog dialog = new FileSelectionDialog(getShell(),
				"Select the profile model", profileModel, ProfilerUtils.PROFILE_EXTENSION, false);
		final int dialogResult = dialog.open();
		if ((dialogResult == IDialogConstants.OK_ID) && !dialog.getFileName().isEmpty()) {
			String path = dialog.getFileName();
			if (path.endsWith(ProfilerUtils.PROFILE_EXTENSION)) {
				profileModelText.setText(path);
			} else if (path.endsWith("/")) { //$NON-NLS-1$
				profileModelText.setText(path + "profiling." + ProfilerUtils.PROFILE_EXTENSION); //$NON-NLS-1$
			} else {
				profileModelText.setText(path + "/profiling." + ProfilerUtils.PROFILE_EXTENSION); //$NON-NLS-1$
			}
			setDirty(isDirty());
		}
	}

	/**
	 * Show a dialog that lists all the models.
	 */
	private void handleFileSystemProfileModelButton() {
		final FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		dialog.setText("Select the profile model");
		String[] filterExt = {"*." + ProfilerUtils.PROFILE_EXTENSION, "*.*" };
		dialog.setFilterExtensions(filterExt);
		final String selected = dialog.open();
		if (selected != null) {
			if (selected.endsWith(ProfilerUtils.PROFILE_EXTENSION)) {
				profileModelText.setText(URI.createFileURI(selected).toString());
			} else if (selected.endsWith("/")) { //$NON-NLS-1$
				profileModelText.setText(URI.createFileURI(selected + "profiling." //$NON-NLS-1$
						+ ProfilerUtils.PROFILE_EXTENSION).toString());
			} else {
				profileModelText.setText(URI.createFileURI(selected + "/profiling." //$NON-NLS-1$
						+ ProfilerUtils.PROFILE_EXTENSION).toString());
			}
			setDirty(isDirty());
		}
	}
}
