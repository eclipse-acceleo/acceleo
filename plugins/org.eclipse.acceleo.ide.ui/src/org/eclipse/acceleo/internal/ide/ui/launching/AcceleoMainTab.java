/*******************************************************************************
 * Copyright (c) 2008, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.launching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.launching.strategy.IAcceleoLaunchingStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.dialog.FileTreeContentProvider;
import org.eclipse.acceleo.internal.ide.ui.dialog.ResourceSelectionDialog;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * The Acceleo main tab of the launch configuration. It displays and edits project and main type name launch
 * configuration attributes. It provides a way to define the model path and the target folder.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoMainTab extends org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab {

	/**
	 * Image registry key for help image (value <code>"dialog_help_image"</code> ).
	 */
	private static final String DLG_IMG_HELP = "dialog_help_image"; //$NON-NLS-1$

	/**
	 * Profile mode constant.
	 */
	private static final String PROFILE_MODE = "profile"; //$NON-NLS-1$

	/**
	 * Profile model extension.
	 */
	private static final String PROFILE_EXTENSION = "mtlp"; //$NON-NLS-1$

	/**
	 * Browse message constant.
	 */
	private static final String BROWSE_MESSAGE = "AcceleoMainTab.Browse"; //$NON-NLS-1$

	/**
	 * The launch configuration tab that displays program arguments.
	 */
	AcceleoJavaArgumentsTab javaArgumentsTab;

	/**
	 * The model path text widget, relative to the workspace.
	 */
	private Text modelText;

	/**
	 * The profile model path text widget, relative to the workspace.
	 */
	private Text profileModelText;

	/**
	 * The model button, to browse the workspace to select the model.
	 */
	private Button modelButton;

	/**
	 * The profile model button, to browse the workspace to select the model.
	 */
	private Button profileModelButton;

	/**
	 * The target folder path text widget, relative to the workspace.
	 */
	private Text targetText;

	/**
	 * The target button, to browse the workspace to select the target folder.
	 */
	private Button targetButton;

	/**
	 * Checkbox button that indicates if we would like to compute the traceability information.
	 */
	private Button computeTraceability;

	/**
	 * Checkbox button that indicates if we would like to compute the profiling information.
	 */
	private Button computeProfiling;

	/**
	 * Available launching strategies in the current Eclipse instance. An internal extension point is defined
	 * to specify multiple launching strategies.
	 */
	private Combo launchingStrategyCombo;

	/**
	 * The descriptions of all the launching strategies existing in the current Eclipse instance. An internal
	 * extension point is defined to specify multiple launching strategies. It is used to define a specific
	 * way of launching an Acceleo generation.
	 */
	private List<String> launchingStrategies;

	/**
	 * The invisible main type shell. It is used to mask some widgets of the superclass.
	 */
	private Shell mainTypeShell;

	/**
	 * Constructor.
	 * 
	 * @param javaArgumentsTab
	 *            is the launch configuration tab that displays program arguments
	 */
	public AcceleoMainTab(AcceleoJavaArgumentsTab javaArgumentsTab) {
		super();
		this.javaArgumentsTab = javaArgumentsTab;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (mainTypeShell != null && !mainTypeShell.isDisposed()) {
			mainTypeShell.dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite mainComposite = (Composite)getControl();
		createAcceleoModelEditor(mainComposite);

		createAcceleoTargetEditor(mainComposite);
		if (PROFILE_MODE.equals(getLaunchConfigurationDialog().getMode())) {
			createAcceleoProfileModelEditor(mainComposite, true);
		} else {
			createAcceleoProfileModelEditor(mainComposite, false);
		}

		createAcceleoLaunchconfiguration(mainComposite);

		// Add help to the JDT tabs
		Composite mainClassParent = this.fMainText.getParent();
		createHelpButton(mainClassParent, AcceleoUIMessages.getString("AcceleoMainTab.Help.JavaClass")); //$NON-NLS-1$

		Composite projectParent = this.fProjText.getParent();
		createHelpButton(projectParent, AcceleoUIMessages.getString("AcceleoMainTab.Help.Project")); //$NON-NLS-1$
	}

	/**
	 * Creates the configuration group.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	private void createAcceleoLaunchconfiguration(Composite parent) {
		Font font = parent.getFont();
		Group mainGroup = createGroup(parent,
				AcceleoUIMessages.getString("AcceleoMainTab.Configuration"), 2, 1, //$NON-NLS-1$
				GridData.FILL_HORIZONTAL);
		Composite comp = createComposite(mainGroup, font, 2, 2, GridData.FILL_BOTH, 0, 0);

		createAcceleoLaunchingStrategyEditor(comp);

		createAcceleoTraceabilityEditor(comp);
		if (PROFILE_MODE.equals(getLaunchConfigurationDialog().getMode())) {
			createAcceleoProfilingEditor(comp, true);
		} else {
			createAcceleoProfilingEditor(comp, false);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#createMainTypeExtensions(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createMainTypeExtensions(Composite parent) {
		if (mainTypeShell == null) {
			mainTypeShell = new Shell();
		}
		Composite notVisibleComposite = new Composite(mainTypeShell, SWT.NONE);
		super.createMainTypeExtensions(notVisibleComposite);
		notVisibleComposite.setVisible(false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#updateLaunchConfigurationDialog()
	 */
	@Override
	protected void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
		updateSettingsVisibility();
	}

	/**
	 * Marks the traceability widget as invisible if the launching strategy is 'Java Application', and marks
	 * it visible otherwise.
	 */
	private void updateSettingsVisibility() {
		if (computeTraceability != null && launchingStrategyCombo != null) {
			if ("Java Application".equals(launchingStrategyCombo.getText())) { //$NON-NLS-1$
				computeTraceability.setSelection(false);
				computeTraceability.setVisible(false);
			} else {
				computeTraceability.setVisible(true);
			}
			profileModelButton.setEnabled(computeProfiling.getSelection());
			profileModelText.setEnabled(computeProfiling.getSelection());
		}
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
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		ToolItem item = new ToolItem(result, SWT.NONE);
		item.setImage(image);
		if (helpMessage != null && !"".equals(helpMessage)) { //$NON-NLS-1$
			item.setToolTipText(helpMessage);
		}
		return result;
	}

	/**
	 * Creates the widgets for specifying the model path.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createAcceleoModelEditor(Composite parent) {
		Font font = parent.getFont();
		Group mainGroup = createGroup(parent, AcceleoUIMessages.getString("AcceleoMainTab.ModelPath"), 2, 1, //$NON-NLS-1$
				GridData.FILL_HORIZONTAL);
		Composite comp = createComposite(mainGroup, font, 2, 2, GridData.FILL_BOTH, 0, 0);
		modelText = createSingleText(comp, 1);
		modelText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		modelButton = createPushButton(comp, AcceleoUIMessages.getString(BROWSE_MESSAGE), null);
		modelButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				handleBrowseModelButton();
			}
		});

		createHelpButton(comp, AcceleoUIMessages.getString("AcceleoMainTab.Help.Model")); //$NON-NLS-1$
	}

	/**
	 * Creates the widgets for specifying the profile model path.
	 * 
	 * @param parent
	 *            the parent composite
	 * @param activatedByDefault
	 *            Indicates if the profiling is activated by default.
	 */
	protected void createAcceleoProfileModelEditor(Composite parent, boolean activatedByDefault) {
		Font font = parent.getFont();
		Group mainGroup = createGroup(parent,
				AcceleoUIMessages.getString("AcceleoMainTab.ProfileModelPath"), 2, 1, //$NON-NLS-1$
				GridData.FILL_HORIZONTAL);
		Composite comp = createComposite(mainGroup, font, 2, 2, GridData.FILL_BOTH, 0, 0);
		profileModelText = createSingleText(comp, 1);
		profileModelText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		profileModelButton = createPushButton(comp, AcceleoUIMessages.getString(BROWSE_MESSAGE), null);
		profileModelButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				handleBrowseProfileModelButton();
			}
		});

		createHelpButton(comp, AcceleoUIMessages.getString("AcceleoMainTab.Help.Profile")); //$NON-NLS-1$

		profileModelText.setEnabled(activatedByDefault);
		profileModelButton.setEnabled(activatedByDefault);
	}

	/**
	 * Show a dialog that lists all the models.
	 */
	private void handleBrowseModelButton() {
		FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(getShell(), false,
				ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
		dialog.setTitle(AcceleoUIMessages.getString("AcceleoMainTab.SelectModel")); //$NON-NLS-1$
		String path = modelText.getText();
		if (path != null && path.length() > 0 && new Path(path).lastSegment().length() > 0) {
			dialog.setInitialPattern(new Path(path).lastSegment());
		} else {
			String projectName;
			try {
				projectName = getCurrentLaunchConfiguration().getAttribute(
						IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, ""); //$NON-NLS-1$
			} catch (CoreException e) {
				projectName = ""; //$NON-NLS-1$
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
			String initial;
			if (projectName.toLowerCase().contains(".uml")) { //$NON-NLS-1$
				initial = "*.uml"; //$NON-NLS-1$
			} else if (projectName.toLowerCase().contains(".ecore")) { //$NON-NLS-1$
				initial = "*.ecore"; //$NON-NLS-1$
			} else {
				initial = "*.xmi"; //$NON-NLS-1$
			}
			dialog.setInitialPattern(initial);
		}
		dialog.open();
		if (dialog.getResult() != null && dialog.getResult().length > 0
				&& dialog.getResult()[0] instanceof IFile) {
			modelText.setText(((IFile)dialog.getResult()[0]).getFullPath().toString());
		}
	}

	/**
	 * Show a dialog that lists all the models.
	 */
	private void handleBrowseProfileModelButton() {
		IResource initialResource;
		if (new Path(profileModelText.getText()).segmentCount() >= 2
				&& ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(profileModelText.getText()))
						.exists()) {
			initialResource = ResourcesPlugin.getWorkspace().getRoot().getFile(
					new Path(profileModelText.getText()));
		} else {
			initialResource = ResourcesPlugin.getWorkspace().getRoot();
		}
		ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), initialResource,
				AcceleoUIMessages.getString("AcceleoMainTab.SelectProfileModel")); //$NON-NLS-1$
		dialog.setContentProvider(new FileTreeContentProvider(true, PROFILE_EXTENSION));
		dialog.open();

		if (dialog.getResult().length > 0 && dialog.getResult()[0] instanceof IPath
				&& ((IPath)dialog.getResult()[0]).segmentCount() > 0) {

			String path = dialog.getResult()[0].toString();
			if (path.endsWith(PROFILE_EXTENSION)) {
				profileModelText.setText(path);
			} else if (path.endsWith("/")) { //$NON-NLS-1$
				profileModelText.setText(path + "profiling.mtlp"); //$NON-NLS-1$
			} else {
				profileModelText.setText(path + "/profiling.mtlp"); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Creates the widgets for specifying the target folder.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createAcceleoTargetEditor(Composite parent) {
		Font font = parent.getFont();
		Group mainGroup = createGroup(parent, AcceleoUIMessages.getString("AcceleoMainTab.TargetPath"), 2, 1, //$NON-NLS-1$
				GridData.FILL_HORIZONTAL);
		Composite comp = createComposite(mainGroup, font, 2, 2, GridData.FILL_BOTH, 0, 0);
		targetText = createSingleText(comp, 1);
		targetText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		targetButton = createPushButton(comp, AcceleoUIMessages.getString(BROWSE_MESSAGE), null);
		targetButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				handleBrowseTargetButton();
			}
		});
		createHelpButton(comp, AcceleoUIMessages.getString("AcceleoMainTab.Help.Target")); //$NON-NLS-1$
	}

	/**
	 * Show a dialog that lists all the models.
	 */
	private void handleBrowseTargetButton() {
		IResource initial;
		if (targetText.getText() != null && targetText.getText().length() > 0) {
			initial = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(targetText.getText()));
			if (initial instanceof IFile) {
				initial = initial.getParent();
			}
		} else {
			initial = null;
		}
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin
				.getWorkspace().getRoot(), true, AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.ContainerSelection")); //$NON-NLS-1$
		if (initial != null) {
			dialog.setInitialSelections(new Object[] {initial });
		}
		dialog.showClosedProjects(false);
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				targetText.setText(((Path)result[0]).toString());
			}
		}
	}

	/**
	 * Creates the widgets for specifying if we compute the traceability information.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createAcceleoTraceabilityEditor(Composite parent) {
		Font font = parent.getFont();
		Composite comp = createComposite(parent, font, 1, 2, GridData.FILL_BOTH, 0, 0);
		computeTraceability = new Button(comp, SWT.CHECK);
		computeTraceability.setFont(font);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		computeTraceability.setLayoutData(gd);
		computeTraceability.setText(AcceleoUIMessages.getString("AcceleoMainTab.ComputeTraceability")); //$NON-NLS-1$
		computeTraceability.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		createHelpButton(comp, AcceleoUIMessages.getString("AcceleoMainTab.Help.Traceability")); //$NON-NLS-1$
	}

	/**
	 * Creates the widgets for specifying if we compute the traceability information.
	 * 
	 * @param parent
	 *            the parent composite
	 * @param checkedByDefault
	 *            Indicates if the checkbox should be checked by default.
	 */
	protected void createAcceleoProfilingEditor(Composite parent, boolean checkedByDefault) {
		Font font = parent.getFont();

		Composite comp = createComposite(parent, font, 1, 2, GridData.FILL_BOTH, 0, 0);
		computeProfiling = new Button(comp, SWT.CHECK);
		computeProfiling.setFont(font);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		computeProfiling.setLayoutData(gd);
		computeProfiling.setText(AcceleoUIMessages.getString("AcceleoMainTab.ComputeProfiling")); //$NON-NLS-1$
		computeProfiling.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Widget widget = e.widget;
				if (widget instanceof Button) {
					Button button = (Button)widget;
					profileModelText.setEnabled(button.getSelection());
					profileModelButton.setEnabled(button.getSelection());
					AcceleoPreferences.switchProfiler(button.getSelection());
					updateLaunchConfigurationDialog();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		computeProfiling.setSelection(checkedByDefault);
		createHelpButton(comp, AcceleoUIMessages.getString("AcceleoMainTab.Help.Profiling")); //$NON-NLS-1$
	}

	/**
	 * Creates the widgets for specifying the launching strategy.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createAcceleoLaunchingStrategyEditor(Composite parent) {
		Composite comp = createComposite(parent, parent.getFont(), 2, 2, GridData.FILL_BOTH, 0, 0);

		Label label = new Label(comp, SWT.NONE);
		label.setText(AcceleoUIMessages.getString("AcceleoMainTab.LaunchingStrategy")); //$NON-NLS-1$
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.horizontalSpan = 1;
		label.setLayoutData(gridData);

		launchingStrategyCombo = new Combo(comp, SWT.READ_ONLY);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 1;
		launchingStrategyCombo.setLayoutData(gridData);
		launchingStrategyCombo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do here
			}

			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		updateStrategies();
		if (launchingStrategyCombo.getItemCount() > 0) {
			launchingStrategyCombo.select(0);
		}

		createHelpButton(comp, AcceleoUIMessages.getString("AcceleoMainTab.Help.Strategy")); //$NON-NLS-1$
	}

	/**
	 * Refreshes the available example strategies in the current Eclipse instance.
	 */
	private void updateStrategies() {
		if (launchingStrategyCombo != null) {
			List<String> descriptions = new ArrayList<String>();
			Iterator<String> strategies = getLaunchingStrategies().iterator();
			while (strategies.hasNext()) {
				descriptions.add(strategies.next());
			}

			launchingStrategyCombo.setItems(descriptions.toArray(new String[descriptions.size()]));
			final int visibleItemCount = 15;
			if (descriptions.size() < visibleItemCount) {
				launchingStrategyCombo.setVisibleItemCount(descriptions.size());
			} else {
				launchingStrategyCombo.setVisibleItemCount(visibleItemCount);
			}
		}
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
	 * Creates a new text widget.
	 * 
	 * @param parent
	 *            the parent composite to add this text widget to
	 * @param hspan
	 *            the horizontal span to take up on the parent composite
	 * @return the new text widget
	 */
	private Text createSingleText(Composite parent, int hspan) {
		Text t = new Text(parent, SWT.SINGLE | SWT.BORDER);
		t.setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hspan;
		t.setLayoutData(gd);
		return t;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#getName()
	 */
	@Override
	public String getName() {
		return "Acceleo"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#getId()
	 */
	@Override
	public String getId() {
		return "org.eclipse.acceleo.ide.ui.launching.acceleoMainTab"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration config) {
		super.initializeFrom(config);
		updateAcceleoModelFromConfig(config);
		updateAcceleoProfileModelFromConfig(config);
		updateAcceleoTargetFromConfig(config);
		updateAcceleoTraceabilityFromConfig(config);
		updateAcceleoLaunchingStrategyFromConfig(config);
		updateSettingsVisibility();
	}

	/**
	 * Loads the model path from the launch configuration's preference store.
	 * 
	 * @param config
	 *            the configuration to load the model path
	 */
	protected void updateAcceleoModelFromConfig(ILaunchConfiguration config) {
		String model = ""; //$NON-NLS-1$
		try {
			model = config.getAttribute(IAcceleoLaunchConfigurationConstants.ATTR_MODEL_PATH, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		modelText.setText(model);
	}

	/**
	 * Loads the profile model path from the launch configuration's preference store.
	 * 
	 * @param config
	 *            the configuration to load the model path
	 */
	protected void updateAcceleoProfileModelFromConfig(ILaunchConfiguration config) {
		String model = ""; //$NON-NLS-1$
		try {
			model = config.getAttribute(IAcceleoLaunchConfigurationConstants.ATTR_PROFILE_MODEL_PATH, ""); //$NON-NLS-1$
			boolean profiling = config.getAttribute(
					IAcceleoLaunchConfigurationConstants.ATTR_COMPUTE_PROFILING, false);
			if (profiling) {
				computeProfiling.setSelection(true);
			} else {
				computeProfiling.setSelection(false);
			}
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		profileModelText.setText(model);
	}

	/**
	 * Loads the target path from the launch configuration's preference store.
	 * 
	 * @param config
	 *            the configuration to load the target path
	 */
	protected void updateAcceleoTargetFromConfig(ILaunchConfiguration config) {
		String target = ""; //$NON-NLS-1$
		try {
			target = config.getAttribute(IAcceleoLaunchConfigurationConstants.ATTR_TARGET_PATH, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		targetText.setText(target);
	}

	/**
	 * Loads the traceability status from the launch configuration's preference store.
	 * 
	 * @param config
	 *            the configuration to load the target path
	 */
	protected void updateAcceleoTraceabilityFromConfig(ILaunchConfiguration config) {
		boolean traceability = false;
		try {
			traceability = config.getAttribute(
					IAcceleoLaunchConfigurationConstants.ATTR_COMPUTE_TRACEABILITY, false);
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		computeTraceability.setSelection(traceability);
	}

	/**
	 * Loads the launching strategy from the launch configuration's preference store.
	 * 
	 * @param config
	 *            the configuration to load the launching strategy
	 */
	protected void updateAcceleoLaunchingStrategyFromConfig(ILaunchConfiguration config) {
		String id = ""; //$NON-NLS-1$
		try {
			id = config.getAttribute(
					IAcceleoLaunchConfigurationConstants.ATTR_LAUNCHING_STRATEGY_DESCRIPTION, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}

		int item = -1;
		List<String> strategies = this.getLaunchingStrategies();
		for (int i = 0; i < strategies.size(); i++) {
			if (id.startsWith(strategies.get(i))) {
				item = i;
			}
		}

		if (item == -1) {
			item = 0;
		}
		if (launchingStrategyCombo.getItemCount() > item) {
			launchingStrategyCombo.select(item);
		} else if (launchingStrategyCombo.getItemCount() > 0) {
			launchingStrategyCombo.select(0);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(ILaunchConfiguration config) {
		boolean result = super.isValid(config);

		Bundle bundle = Platform.getBundle("org.eclipse.osgi"); //$NON-NLS-1$
		if (bundle != null) {
			int selectionIndex = this.launchingStrategyCombo.getSelectionIndex();
			String selectedStrategy = this.launchingStrategyCombo.getItem(selectionIndex);
			List<String> strategies = this.getLaunchingStrategies();

			// Luna version
			Version keplerVersion = Version.parseVersion("3.10.0"); //$NON-NLS-1$
			if (bundle.getVersion().compareTo(keplerVersion) >= 0
					&& selectedStrategy.equals(strategies.get(1))) {
				this.setErrorMessage(AcceleoUIMessages.getString("AcceleoMainTab.Error.PluginLaunchWithLuna")); //$NON-NLS-1$
			}
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (result) {
			String model = modelText.getText().trim();
			IStatus status = workspace.validatePath(model, IResource.FILE);
			if (status.isOK()) {
				IFile file = workspace.getRoot().getFile(new Path(model));
				if (!file.exists()) {
					setErrorMessage(AcceleoUIMessages.getString("AcceleoMainTab.Error.MissingModel", //$NON-NLS-1$
							new Object[] {model }));
					result = false;
				}
			} else {
				setErrorMessage(AcceleoUIMessages.getString(
						"AcceleoMainTab.Error.InvalidModel", new Object[] {model })); //$NON-NLS-1$
				result = false;
			}
		}
		if (result) {
			String target = targetText.getText().trim();
			IStatus status = workspace.validatePath(target, IResource.FOLDER | IResource.PROJECT);
			if (!status.isOK()) {
				setErrorMessage(AcceleoUIMessages.getString("AcceleoMainTab.Error.InvalidTarget", //$NON-NLS-1$
						new Object[] {target }));
				result = false;
			}
		}
		if (result && computeProfiling.getSelection()) {
			if ("".equals(profileModelText.getText().trim())) { //$NON-NLS-1$
				setErrorMessage(AcceleoUIMessages.getString("AcceleoMainTab.Error.MissingProfileModel")); //$NON-NLS-1$
				result = false;
			} else if (!profileModelText.getText().trim().endsWith(PROFILE_EXTENSION)) {
				setErrorMessage(AcceleoUIMessages
						.getString("AcceleoMainTab.Error.MissingProfileModelExtension")); //$NON-NLS-1$
				result = false;
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		if (javaArgumentsTab != null) {
			AcceleoPropertiesFilesTab acceleoPropertiesFilesTab = null;
			ILaunchConfigurationTab[] tabs = this.getLaunchConfigurationDialog().getTabs();
			for (ILaunchConfigurationTab iLaunchConfigurationTab : tabs) {
				if (iLaunchConfigurationTab instanceof AcceleoPropertiesFilesTab) {
					acceleoPropertiesFilesTab = (AcceleoPropertiesFilesTab)iLaunchConfigurationTab;
				}
			}
			String arguments = null;
			if (acceleoPropertiesFilesTab != null) {
				arguments = acceleoPropertiesFilesTab.getPropertiesFiles();
			}
			javaArgumentsTab.updateArguments(config, modelText.getText().trim(), targetText.getText().trim(),
					arguments);
		}
		super.performApply(config);
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_MODEL_PATH, modelText.getText().trim());
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_COMPUTE_PROFILING, computeProfiling
				.getSelection());
		if (computeProfiling.getSelection()) {
			config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_PROFILE_MODEL_PATH,
					profileModelText.getText().trim());
		}
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_TARGET_PATH, targetText.getText()
				.trim());
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_COMPUTE_TRACEABILITY,
				computeTraceability.getSelection());
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_LAUNCHING_STRATEGY_DESCRIPTION,
				launchingStrategyCombo.getText());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		super.setDefaults(config);
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_MODEL_PATH, ""); //$NON-NLS-1$
		if (computeProfiling != null && computeProfiling.getSelection()) {
			config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_PROFILE_MODEL_PATH, ""); //$NON-NLS-1$
		}
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_TARGET_PATH, ""); //$NON-NLS-1$
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_COMPUTE_TRACEABILITY, false);
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_ARGUMENTS, ""); //$NON-NLS-1$
		config.setAttribute(IAcceleoLaunchConfigurationConstants.ATTR_LAUNCHING_STRATEGY_DESCRIPTION, ""); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab#getImage()
	 */
	@Override
	public Image getImage() {
		return AcceleoUIActivator.getDefault().getImage("icons/template-editor/Template_main.gif"); //$NON-NLS-1$
	}

	/**
	 * Gets all the launching strategies existing in the current Eclipse instance. The identifier of the
	 * internal extension point specifying the implementation to use for launching strategy. It is used to
	 * define a specific way of launching an Acceleo generation.
	 * 
	 * @return all the launching strategies descriptions
	 */
	private List<String> getLaunchingStrategies() {
		List<String> acceleoStrategies = new ArrayList<String>();

		if (launchingStrategies == null) {
			launchingStrategies = new ArrayList<String>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = registry
					.getExtensionPoint(IAcceleoLaunchingStrategy.LAUNCHING_STRATEGY_EXTENSION_ID);
			if (extensionPoint != null && extensionPoint.getExtensions().length > 0) {
				IExtension[] extensions = extensionPoint.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IExtension extension = extensions[i];
					IConfigurationElement[] members = extension.getConfigurationElements();
					for (int j = 0; j < members.length; j++) {
						IConfigurationElement member = members[j];
						String name = member.getContributor().getName();
						String description = member.getAttribute("description"); //$NON-NLS-1$
						if (description != null && description.length() > 0
								&& AcceleoUIActivator.PLUGIN_ID.equals(name)) {
							acceleoStrategies.add(description);
						} else if (description != null && description.length() > 0) {
							launchingStrategies.add(description);
						}
					}
				}
			}
			Collections.sort(launchingStrategies);
		}
		launchingStrategies.addAll(0, acceleoStrategies);
		return launchingStrategies;
	}

	/**
	 * Returns the model.
	 * 
	 * @return The model
	 */
	public String getModel() {
		return this.modelText.getText();
	}

	/**
	 * Returns the target folder.
	 * 
	 * @return The target folder.
	 */
	public String getTarget() {
		return this.targetText.getText();
	}

}
