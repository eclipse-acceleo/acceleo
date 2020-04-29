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

import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.ls.debug.ide.ui.dialog.AcceleoFileSelectionDialog;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class AcceleoMainTab extends AbstractLaunchConfigurationTab {

	/**
	 * The Browse button text.
	 */
	private static final String BROWSE = "Browse";

	/**
	 * The module resource.
	 */
	private String module;

	/**
	 * The initial module.
	 */
	private String initialModule;

	/**
	 * The model resource.
	 */
	private String model;

	/**
	 * The initial model.
	 */
	private String initialModel;

	/**
	 * The destination.
	 */
	private String destination;

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
		configuration.setAttribute(AcceleoDebugger.MODULE, module);
		configuration.setAttribute(AcceleoDebugger.MODEL, model);
		configuration.setAttribute(AcceleoDebugger.DESTINATION, destination);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			moduleText.setText(configuration.getAttribute(AcceleoDebugger.MODULE, ""));
			initialModule = module;
			modelText.setText(configuration.getAttribute(AcceleoDebugger.MODEL, ""));
			initialModel = model;
			destinationText.setText(configuration.getAttribute(AcceleoDebugger.DESTINATION, ""));
			initialDestination = destination;
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		setDefaults(configuration);
		initialModule = module;
		initialModel = model;
		setDirty(module != initialModule || model != initialModel);
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		// TODO check is main template
		return true;
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
		final Composite moduleComposite = new Composite(parent, parent.getStyle());
		moduleComposite.setLayout(new GridLayout(3, false));
		moduleComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		final Label moduleLabel = new Label(moduleComposite, parent.getStyle());
		moduleLabel.setText("Module file:");
		final Text res = new Text(moduleComposite, parent.getStyle());
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		res.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				module = res.getText();
				setDirty(module != initialModule || model != initialModel
						|| destination != initialDestination);
				updateLaunchConfigurationDialog();
			}

		});
		Button moduleBrowseButton = new Button(moduleComposite, SWT.BORDER);
		moduleBrowseButton.setText(BROWSE);
		moduleBrowseButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				final AcceleoFileSelectionDialog dialog = new AcceleoFileSelectionDialog(getShell(),
						"Select module file.", module, AcceleoParser.MODULE_FILE_EXTENSION, false);
				final int dialogResult = dialog.open();
				if ((dialogResult == IDialogConstants.OK_ID) && !dialog.getFileName().isEmpty()) {
					moduleText.setText(dialog.getFileName());
					setDirty(module != initialModule || model != initialModel
							|| destination != initialDestination);
				}
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
		final Composite modelComposite = new Composite(parent, parent.getStyle());
		modelComposite.setLayout(new GridLayout(3, false));
		modelComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		final Label modelLabel = new Label(modelComposite, parent.getStyle());
		modelLabel.setText("Model file:");
		final Text res = new Text(modelComposite, parent.getStyle());
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		res.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				model = res.getText();
				setDirty(module != initialModule || model != initialModel
						|| destination != initialDestination);
				updateLaunchConfigurationDialog();
			}
		});
		Button modelBrowseButton = new Button(modelComposite, SWT.BORDER);
		modelBrowseButton.setText(BROWSE);
		modelBrowseButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO create a dialog for model selection
				final AcceleoFileSelectionDialog dialog = new AcceleoFileSelectionDialog(getShell(),
						"Select model file.", module, null, false);
				final int dialogResult = dialog.open();
				if ((dialogResult == IDialogConstants.OK_ID) && !dialog.getFileName().isEmpty()) {
					modelText.setText(dialog.getFileName());
				}
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
		final Composite destinationComposite = new Composite(parent, parent.getStyle());
		destinationComposite.setLayout(new GridLayout(3, false));
		destinationComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		final Label destinationLabel = new Label(destinationComposite, parent.getStyle());
		destinationLabel.setText("Destination folder:");
		final Text res = new Text(destinationComposite, parent.getStyle());
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		res.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				destination = res.getText();
				setDirty(module != initialModule || model != initialModel
						|| destination != initialDestination);
				updateLaunchConfigurationDialog();
			}
		});
		Button modelBrowseButton = new Button(destinationComposite, SWT.BORDER);
		modelBrowseButton.setText(BROWSE);
		modelBrowseButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO create a dialog for folder selection
				final AcceleoFileSelectionDialog dialog = new AcceleoFileSelectionDialog(getShell(),
						"Select destination folder.", module, null, false);
				final int dialogResult = dialog.open();
				if ((dialogResult == IDialogConstants.OK_ID) && !dialog.getFileName().isEmpty()) {
					destinationText.setText(dialog.getFileName());
				}
			}
		});

		return res;
	}

}
