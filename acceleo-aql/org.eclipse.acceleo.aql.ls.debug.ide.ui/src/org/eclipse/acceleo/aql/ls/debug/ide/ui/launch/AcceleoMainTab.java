/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug.ide.ui.launch;

import org.eclipse.acceleo.aql.ide.Activator;
import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.ls.debug.ide.AcceleoDebugPlugin;
import org.eclipse.acceleo.aql.ls.debug.ide.ui.dialog.AcceleoFileSelectionDialog;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

public class AcceleoMainTab extends AbstractLaunchConfigurationTab {

	/**
	 * The Browse button text.
	 */
	protected static final String BROWSE = "Browse...";

	/**
	 * The module resource.
	 */
	protected String module;

	/**
	 * The initial module.
	 */
	private String initialModule;

	/**
	 * The model resource.
	 */
	protected String model;

	/**
	 * The initial model.
	 */
	private String initialModel;

	/**
	 * The destination.
	 */
	protected String destination;

	/**
	 * The initial destination.
	 */
	private String initialDestination;

	/**
	 * The module {@link Text}.
	 */
	private Text moduleText;

	/**
	 * The model {@link Text}.
	 */
	private Text modelText;

	/**
	 * The destination {@link Text}.
	 */
	private Text destinationText;

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		if (module != null) {
			configuration.setMappedResources(new IResource[] {ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(module)) });
		}

		configuration.setAttribute(AcceleoDebugger.MODULE, module);
		configuration.setAttribute(AcceleoDebugger.MODEL, model);
		configuration.setAttribute(AcceleoDebugger.DESTINATION, destination);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			if (configuration.hasAttribute(AcceleoDebugger.MODULE)) {
				moduleText.setText(configuration.getAttribute(AcceleoDebugger.MODULE, ""));
				initialModule = module;
			}
			if (configuration.hasAttribute(AcceleoDebugger.MODEL)) {
				modelText.setText(configuration.getAttribute(AcceleoDebugger.MODEL, ""));
				initialModel = model;
			}
			if (configuration.hasAttribute(AcceleoDebugger.DESTINATION)) {
				destinationText.setText(configuration.getAttribute(AcceleoDebugger.DESTINATION, ""));
				initialDestination = destination;
			}
		} catch (CoreException e) {
			AcceleoDebugPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoDebugPlugin.ID,
					"couldn't initialize from launch configuration", e));
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		setDefaults(configuration);
		initialModule = module;
		initialModel = model;
		initialDestination = destination;
		setDirty(isDirty());
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean res = true;

		try {
			if (launchConfig.hasAttribute(AcceleoDebugger.MODULE)) {
				final IFile resource = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(launchConfig
						.getAttribute(AcceleoDebugger.MODULE, "")));
				if (!Activator.isAcceleoMain(resource)) {
					setErrorMessage("The selected Acceleo module doesn't contain a main template.");
					res = false;
				}
			} else {
				setErrorMessage("Select an Acceleo module with main template.");
				res = false;
			}
			if (res) {
				if (!launchConfig.hasAttribute(AcceleoDebugger.MODEL)) {
					setErrorMessage("Select a model file.");
					res = false;
				}
			}
			if (res) {
				if (!launchConfig.hasAttribute(AcceleoDebugger.DESTINATION)) {
					setErrorMessage("Select a destination folder.");
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

	@Override
	public String getName() {
		return "Acceleo 4";
	}

	/**
	 * Creates the {@link #setControl(org.eclipse.swt.widgets.Control) control}.
	 * 
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite control = new Composite(parent, parent.getStyle() ^ (SWT.H_SCROLL | SWT.V_SCROLL));
		control.setLayout(new GridLayout(1, false));

		moduleText = createModuleComposite(control);
		modelText = createModelComposite(control);
		destinationText = createDestinationComposite(control);

		setControl(control);
	}

	/**
	 * Creates the module {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Text}
	 */
	private Text createModuleComposite(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Module file:");
		final Text res = new Text(group, SWT.BORDER);
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		res.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				module = res.getText();
				setDirty(isDirty());
				updateLaunchConfigurationDialog();
			}
		});
		Button browseButton = createPushButton(group, BROWSE, null);
		browseButton.setText(BROWSE);
		browseButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleBrowseModuleButton();
			}
		});
		return res;
	}

	/**
	 * Creates the model {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Text}
	 */
	private Text createModelComposite(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Model file:");
		final Text res = new Text(group, SWT.BORDER);
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		res.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				model = res.getText();
				setDirty(isDirty());
				updateLaunchConfigurationDialog();
			}
		});
		Button browseButton = createPushButton(group, BROWSE, null);
		browseButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleBrowseModelButton();
			}
		});
		return res;
	}

	/**
	 * Creates the destination {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Text}
	 */
	private Text createDestinationComposite(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Destination folder:");
		final Text res = new Text(group, SWT.BORDER);
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		res.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				destination = res.getText();
				setDirty(isDirty());
				updateLaunchConfigurationDialog();
			}
		});
		Button browseButton = createPushButton(group, BROWSE, null);
		browseButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleBrowseDestinationButton();
			}
		});
		return res;
	}

	protected boolean isDirty() {
		return module != initialModule || model != initialModel || destination != initialDestination;
	}

	private void handleBrowseModuleButton() {
		final AcceleoFileSelectionDialog dialog = new AcceleoFileSelectionDialog(getShell(),
				"Select a module file", module, AcceleoParser.MODULE_FILE_EXTENSION, false);
		final int dialogResult = dialog.open();
		if ((dialogResult == IDialogConstants.OK_ID) && !dialog.getFileName().isEmpty()) {
			moduleText.setText(dialog.getFileName());
			setDirty(isDirty());
		}
	}

	private void handleBrowseModelButton() {
		FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(getShell(), false,
				ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
		dialog.setTitle("Select the model file");
		String path = modelText.getText();
		if (path != null && path.length() > 0 && new Path(path).lastSegment().length() > 0) {
			dialog.setInitialPattern(new Path(path).lastSegment());
		} else {
			String initial = "*.xmi"; //$NON-NLS-1$
			dialog.setInitialPattern(initial);
		}
		dialog.open();
		if (dialog.getResult() != null && dialog.getResult().length > 0 && dialog
				.getResult()[0] instanceof IFile) {
			modelText.setText(((IFile)dialog.getResult()[0]).getFullPath().toString());
		}
	}

	private void handleBrowseDestinationButton() {
		IResource initial;
		if (destinationText.getText() != null && destinationText.getText().length() > 0) {
			initial = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(destinationText
					.getText()));
			if (initial instanceof IFile) {
				initial = initial.getParent();
			}
		} else {
			initial = null;
		}
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin
				.getWorkspace().getRoot(), true, "Select the destination folder");
		if (initial != null) {
			dialog.setInitialSelections(new Object[] {initial.getParent(), initial });
		}
		dialog.showClosedProjects(false);
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				destinationText.setText(((Path)result[0]).toString());
			}
		}
	}
}
