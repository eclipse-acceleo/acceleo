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
package org.eclipse.acceleo.internal.ide.ui.launching;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * The Acceleo properties files tab.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPropertiesFilesTab extends AbstractLaunchConfigurationTab {

	/**
	 * The arguments text widget.
	 */
	private Text argumentsText;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite g = new Composite(parent, SWT.NONE);
		g.setLayout(new GridLayout(1, false));
		g.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 1;
		g.setLayoutData(gd);

		((GridLayout)g.getLayout()).verticalSpacing = 0;
		createAcceleoArgumentsEditor(g);
		setControl(g);
	}

	/**
	 * Creates the widgets for specifying the arguments.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createAcceleoArgumentsEditor(Composite parent) {
		Font font = parent.getFont();
		Group mainGroup = createGroup(parent, AcceleoUIMessages
				.getString("AcceleoPropertiesFilesTab.Arguments"), 2, 1, //$NON-NLS-1$
				GridData.FILL_HORIZONTAL);
		Composite comp = createComposite(mainGroup, font, 2, 2, GridData.FILL_BOTH, 0, 0);
		argumentsText = new Text(comp, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		final int heightHint = 150;
		gd.heightHint = heightHint;
		gd.widthHint = 100;
		gd.horizontalSpan = 2;
		argumentsText.setLayoutData(gd);
		argumentsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		createHelpButton(comp, AcceleoUIMessages.getString("AcceleoPropertiesFilesTab.Help.Properties")); //$NON-NLS-1$
	}

	/**
	 * Creates a help button in the given parent with the given help message and the given help ID.
	 * 
	 * @param parent
	 *            The composite
	 * @param helpMessage
	 *            The help message seen by the user
	 * @return The toolbar with the button.
	 */
	private ToolBar createHelpButton(Composite parent, String helpMessage) {
		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
		((GridLayout)parent.getLayout()).numColumns++;
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER
				| GridData.VERTICAL_ALIGN_BEGINNING));
		ToolItem item = new ToolItem(result, SWT.NONE);
		item.setImage(image);
		if (helpMessage != null && !"".equals(helpMessage)) { //$NON-NLS-1$
			item.setToolTipText(helpMessage);
		}
		return result;
	}

	/**
	 * Creates a Composite widget.
	 * 
	 * @param parent
	 *            the parent composite to add this composite to
	 * @param font
	 *            is the font
	 * @param columns
	 *            the number of columns within the composite
	 * @param hspan
	 *            the horizontal span the composite should take up on the parent
	 * @param fill
	 *            the style for how this composite should fill into its parent Can be one of
	 *            <code>GridData.FILL_HORIZONAL</code>, <code>GridData.FILL_BOTH</code> or
	 *            <code>GridData.FILL_VERTICAL</code>
	 * @param marginwidth
	 *            the width of the margin to place around the composite (default is 5, specified by
	 *            GridLayout)
	 * @param marginheight
	 *            the height of the margin to place around the composite (default is 5, specified by
	 *            GridLayout)
	 * @return the new group
	 */
	private Composite createComposite(Composite parent, Font font, int columns, int hspan, int fill,
			int marginwidth, int marginheight) {
		Composite g = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(columns, false);
		layout.marginWidth = marginwidth;
		layout.marginHeight = marginheight;
		g.setLayout(layout);
		g.setFont(font);
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	/**
	 * Creates a Group widget.
	 * 
	 * @param parent
	 *            the parent composite to add this group to
	 * @param text
	 *            the text for the heading of the group
	 * @param columns
	 *            the number of columns within the group
	 * @param hspan
	 *            the horizontal span the group should take up on the parent
	 * @param fill
	 *            the style for how this composite should fill into its parent Can be one of
	 *            <code>GridData.FILL_HORIZONAL</code>, <code>GridData.FILL_BOTH</code> or
	 *            <code>GridData.FILL_VERTICAL</code>
	 * @return the new group
	 */
	private Group createGroup(Composite parent, String text, int columns, int hspan, int fill) {
		Group g = new Group(parent, SWT.NONE);
		g.setLayout(new GridLayout(columns, false));
		g.setText(text);
		g.setFont(parent.getFont());
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_ARGUMENTS, ""); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		updateAcceleoArgumentsFromConfig(configuration);
	}

	/**
	 * Loads the arguments from the launch configuration's preference store.
	 * 
	 * @param config
	 *            the configuration to load the arguments
	 */
	protected void updateAcceleoArgumentsFromConfig(ILaunchConfiguration config) {
		String args = ""; //$NON-NLS-1$
		try {
			args = config.getAttribute(IAcceleoLaunchConfigurationConstants.ATTR_ARGUMENTS, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		argumentsText.setText(args);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_ARGUMENTS, argumentsText
				.getText());

		// update Java arguments
		AcceleoJavaArgumentsTab argumentTab = null;
		AcceleoMainTab mainTab = null;
		ILaunchConfigurationTab[] tabs = this.getLaunchConfigurationDialog().getTabs();
		for (ILaunchConfigurationTab iLaunchConfigurationTab : tabs) {
			if (iLaunchConfigurationTab instanceof AcceleoJavaArgumentsTab) {
				argumentTab = (AcceleoJavaArgumentsTab)iLaunchConfigurationTab;
			} else if (iLaunchConfigurationTab instanceof AcceleoMainTab) {
				mainTab = (AcceleoMainTab)iLaunchConfigurationTab;
			}
		}

		if (argumentTab != null && mainTab != null) {
			argumentTab.updateArguments(configuration, mainTab.getModel().trim(), mainTab.getTarget().trim(),
					argumentsText.getText());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return AcceleoUIMessages.getString("AcceleoPropertiesFilesTab.Name"); //$NON-NLS-1$
	}

	/**
	 * Returns the content of the properties files text area.
	 * 
	 * @return The content of the properties files text area.
	 */
	public String getPropertiesFiles() {
		return argumentsText.getText();
	}

}
