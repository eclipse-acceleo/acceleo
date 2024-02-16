/*******************************************************************************
 * Copyright (c) 2017, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug.ide.ui.launch;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.ls.debug.ide.AcceleoDebugPlugin;
import org.eclipse.acceleo.aql.ls.debug.ide.ui.dialog.AbstractResourceSelectionDialog;
import org.eclipse.acceleo.aql.ls.debug.ide.ui.dialog.FileSelectionDialog;
import org.eclipse.acceleo.aql.ls.debug.ide.ui.dialog.FolderSelectionDialog;
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
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

public class AcceleoMainTab extends AbstractLaunchConfigurationTab {

	/**
	 * The Browse button text.
	 */
	protected static final String BROWSE = "Browse...";

	/**
	 * New line.
	 */
	protected static final String NEW_LINE = "\n";

	/**
	 * New line.
	 */
	protected static final String WINDOWS_NEW_LINE = "\r\n";

	/**
	 * The {@link Gson} instance.
	 */
	protected static final Gson GSON = new Gson();

	/**
	 * The module resource.
	 */
	protected String module;

	/**
	 * The model resource.
	 */
	protected String model;

	/**
	 * The destination.
	 */
	protected String destination;

	/**
	 * The options {@link Map}.
	 */
	protected Map<String, String> options;

	/**
	 * The options.
	 */
	private String optionsString;

	/**
	 * The initial module.
	 */
	private String initialModule;

	/**
	 * The initial model.
	 */
	private String initialModel;

	/**
	 * The initial destination.
	 */
	private String initialDestination;

	/**
	 * The initial options.
	 */
	private String initialOptionsString;

	/**
	 * The module {@link Text}.
	 */
	private Text moduleText;

	/**
	 * The model {@link Text}.
	 */
	private Text modelText;

	/**
	 * The Unix end line radio {@link Button}.
	 */
	private Button unixEndLineButton;

	/**
	 * The Windows end line radio {@link Button}.
	 */
	private Button windowsEndLineButton;

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
		configuration.setAttribute(AcceleoDebugger.OPTIONS, optionsString);
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
			if (configuration.hasAttribute(AcceleoDebugger.OPTIONS)) {
				options = GSON.fromJson(configuration.getAttribute(AcceleoDebugger.OPTIONS, ""),
						AcceleoDebugger.OPTION_MAP_TYPE);
			} else {
				options = new LinkedHashMap<>();
				options.put(AcceleoUtil.NEW_LINE_OPTION, System.lineSeparator());
				// TODO add GUI
				options.put(AcceleoUtil.LOG_URI_OPTION, "acceleo.log");
			}
			optionsString = GSON.toJson(options);
			initialOptionsString = optionsString;
			final String newLine = options.get(AcceleoUtil.NEW_LINE_OPTION);
			unixEndLineButton.setSelection(NEW_LINE.equals(newLine));
			windowsEndLineButton.setSelection(WINDOWS_NEW_LINE.equals(newLine));
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
		initialOptionsString = optionsString;
		setDirty(isDirty());
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean res = true;

		try {
			if (launchConfig.hasAttribute(AcceleoDebugger.MODULE)) {
				final IFile resource = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(launchConfig
						.getAttribute(AcceleoDebugger.MODULE, "")));
				if (!resource.exists()) {
					setErrorMessage("The selected Acceleo module doesn't exists.");
					res = false;
				} else if (!AcceleoPlugin.isAcceleoMain(resource)) {
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
		createNewLine(control);

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

	/**
	 * Creates the new line selection {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 */
	private void createNewLine(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Enf of line:");
		unixEndLineButton = new Button(group, SWT.RADIO);
		unixEndLineButton.setText("Unix");
		unixEndLineButton.addListener(SWT.Selection, e -> {
			if (unixEndLineButton.getSelection()) {
				putOption(AcceleoUtil.NEW_LINE_OPTION, NEW_LINE);
			}
		});
		windowsEndLineButton = new Button(group, SWT.RADIO);
		windowsEndLineButton.setText("Windows");
		windowsEndLineButton.addListener(SWT.Selection, e -> {
			if (windowsEndLineButton.getSelection()) {
				putOption(AcceleoUtil.NEW_LINE_OPTION, WINDOWS_NEW_LINE);
			}
		});
	}

	protected boolean isDirty() {
		return module != initialModule || model != initialModel || destination != initialDestination
				|| !optionsString.equals(initialOptionsString);
	}

	private void handleBrowseModuleButton() {
		final AbstractResourceSelectionDialog dialog = new FileSelectionDialog(getShell(),
				"Select a module file", module, AcceleoParser.MODULE_FILE_EXTENSION, true);
		final int dialogResult = dialog.open();
		if ((dialogResult == IDialogConstants.OK_ID) && !dialog.getFileName().isEmpty()) {
			moduleText.setText(dialog.getFileName());
			setDirty(isDirty());
		}
	}

	/**
	 * Puts the given option.
	 * 
	 * @param name
	 *            the option name
	 * @param value
	 *            the option value
	 */
	protected void putOption(String name, String value) {
		options.put(name, value);
		optionsString = GSON.toJson(options);
		setDirty(isDirty());
		updateLaunchConfigurationDialog();

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
		final AbstractResourceSelectionDialog dialog = new FolderSelectionDialog(getShell(),
				"Select the destination folder", destination);
		final int dialogResult = dialog.open();
		if ((dialogResult == IDialogConstants.OK_ID) && dialog.getFileName() != null && !dialog.getFileName()
				.isEmpty()) {
			destinationText.setText(dialog.getFileName());
			setDirty(isDirty());
		}
	}
}
