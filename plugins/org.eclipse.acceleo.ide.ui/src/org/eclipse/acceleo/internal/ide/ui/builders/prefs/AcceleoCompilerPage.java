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
package org.eclipse.acceleo.internal.ide.ui.builders.prefs;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoBuilderSettings;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.PlatformUI;

/**
 * The preference page to configure the Acceleo compiler. We can define for instance the standard compliance
 * mode : strict or pragmatic.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCompilerPage extends PreferencePage implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

	/**
	 * The current selection in the worbench. It should be an IProject element.
	 */
	private IAdaptable element;

	/**
	 * The widget to check or not the strict compliance mode (opposite of the pragmatic compliance mode).
	 */
	private Button strictCompliance;

	/**
	 * The xmi resource radio button.
	 */
	private Button xmiResourceButton;

	/**
	 * The binary resource radio button.
	 */
	private Button binaryResourceButton;

	/**
	 * The trimmed position checkbox.
	 */
	private Button trimmedPositionButton;

	/**
	 * The platform:/resource radio button.
	 */
	private Button platformResourceButton;

	/**
	 * The absolute path radio button.
	 */
	private Button absolutePathButton;

	/**
	 * Constructor.
	 */
	public AcceleoCompilerPage() {
		super();
		setDescription(AcceleoUIMessages.getString("AcceleoCompilerPage.Description")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		if (element instanceof IProject) {
			final IProject project = (IProject)element;
			AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
			if (strictCompliance.getSelection()) {
				settings.setCompliance(AcceleoBuilderSettings.BUILD_STRICT_MTL_COMPLIANCE);
			} else {
				settings.setCompliance(AcceleoBuilderSettings.BUILD_PRAGMATIC_COMPLIANCE);
			}
			if (xmiResourceButton.getSelection()) {
				settings.setResourceKind(AcceleoBuilderSettings.BUILD_XMI_RESOURCE);
			} else {
				settings.setResourceKind(AcceleoBuilderSettings.BUILD_BINARY_RESOURCE);
			}
			if (platformResourceButton.getSelection()) {
				settings.setCompilationKind(AcceleoBuilderSettings.COMPILATION_PLATFORM_RESOURCE);
			} else {
				settings.setCompilationKind(AcceleoBuilderSettings.COMPILATION_ABSOLUTE_PATH);
			}
			settings.setTrimmedPositions(trimmedPositionButton.getSelection());
			try {
				settings.save();
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}

			Job job = new Job(AcceleoUIMessages.getString("AcceleoCompilerPage.BuildingProject", project //$NON-NLS-1$
					.getName())) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
						return new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
					}
					return Status.OK_STATUS;
				}
			};
			job.schedule();
		}
		return super.performOk();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	public IAdaptable getElement() {
		return element;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		this.element = element;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		createResourceKindGroup(composite);
		createComplianceGroup(composite);
		createTrimmmedPositionGroup(composite);
		createCompilationPathGroup(composite);
		return composite;
	}

	/**
	 * Creates a group for compliance settings.
	 * 
	 * @param parent
	 *            is the parent composite
	 */
	private void createComplianceGroup(Composite parent) {
		strictCompliance = new Button(parent, SWT.CHECK);
		strictCompliance.setText(AcceleoUIMessages.getString("AcceleoCompilerPage.StrictMTLCompliance")); //$NON-NLS-1$
		GridData gridData = new GridData();
		gridData.horizontalSpan = 1;
		strictCompliance.setLayoutData(gridData);
		if (element instanceof IProject) {
			IProject project = (IProject)element;
			AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
			if (AcceleoBuilderSettings.BUILD_STRICT_MTL_COMPLIANCE.equals(settings.getCompliance())) {
				strictCompliance.setSelection(true);
			} else if (AcceleoBuilderSettings.BUILD_PRAGMATIC_COMPLIANCE.equals(settings.getCompliance())) {
				strictCompliance.setSelection(false);
			} else {
				strictCompliance.setSelection(false);
			}
		} else {
			strictCompliance.setSelection(false);
		}

		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		ToolItem item = new ToolItem(result, SWT.NONE);
		item.setImage(image);

		String helpMessage = AcceleoUIMessages.getString("AcceleoCompilerPage.StrictMTLComplianceHelp"); //$NON-NLS-1$
		if (helpMessage != null && !"".equals(helpMessage)) { //$NON-NLS-1$
			item.setToolTipText(helpMessage);
		}
	}

	/**
	 * Creates a group for the resource kind.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	private void createResourceKindGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gridData);
		group.setText(AcceleoUIMessages.getString("AcceleoCompilerPage.ResourceKind")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		group.setLayout(layout);
		layout.numColumns = 1;

		binaryResourceButton = new Button(group, SWT.RADIO);
		binaryResourceButton.setText(AcceleoUIMessages.getString("AcceleoCompilerPage.BinaryResourceKind")); //$NON-NLS-1$
		binaryResourceButton.setLayoutData(new GridData());

		xmiResourceButton = new Button(group, SWT.RADIO);
		xmiResourceButton.setText(AcceleoUIMessages.getString("AcceleoCompilerPage.XMIResourceKind")); //$NON-NLS-1$
		xmiResourceButton.setLayoutData(new GridData());

		if (element instanceof IProject) {
			IProject project = (IProject)element;
			AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
			if (AcceleoBuilderSettings.BUILD_XMI_RESOURCE.equals(settings.getResourceKind())) {
				xmiResourceButton.setSelection(true);
				binaryResourceButton.setSelection(false);
			} else if (AcceleoBuilderSettings.BUILD_BINARY_RESOURCE.equals(settings.getResourceKind())) {
				xmiResourceButton.setSelection(false);
				binaryResourceButton.setSelection(true);
			} else {
				xmiResourceButton.setSelection(false);
				binaryResourceButton.setSelection(true);
			}
		} else {
			xmiResourceButton.setSelection(false);
			binaryResourceButton.setSelection(true);
		}

		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		ToolItem item = new ToolItem(result, SWT.NONE);
		item.setImage(image);

		String helpMessage = AcceleoUIMessages.getString("AcceleoCompilerPage.ResourceSerialization"); //$NON-NLS-1$
		if (helpMessage != null && !"".equals(helpMessage)) { //$NON-NLS-1$
			item.setToolTipText(helpMessage);
		}
	}

	/**
	 * Creates a group for compliance settings.
	 * 
	 * @param parent
	 *            is the parent composite
	 */
	private void createTrimmmedPositionGroup(Composite parent) {
		trimmedPositionButton = new Button(parent, SWT.CHECK);
		trimmedPositionButton.setText(AcceleoUIMessages.getString("AcceleoCompilerPage.TrimmedPosition")); //$NON-NLS-1$
		GridData gridData = new GridData();
		gridData.horizontalSpan = 1;
		trimmedPositionButton.setLayoutData(gridData);
		if (element instanceof IProject) {
			IProject project = (IProject)element;
			AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
			trimmedPositionButton.setSelection(settings.isTrimmedPositions());
		} else {
			trimmedPositionButton.setSelection(false);
		}

		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		ToolItem item = new ToolItem(result, SWT.NONE);
		item.setImage(image);

		String helpMessage = AcceleoUIMessages.getString("AcceleoCompilerPage.TrimmedPositionHelp"); //$NON-NLS-1$
		if (helpMessage != null && !"".equals(helpMessage)) { //$NON-NLS-1$
			item.setToolTipText(helpMessage);
		}
	}

	/**
	 * Creates a group for the compilation path kind settings.
	 * 
	 * @param parent
	 *            The parent composite
	 */
	private void createCompilationPathGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gridData);
		group.setText(AcceleoUIMessages.getString("AcceleoCompilerPage.CompilationKind")); //$NON-NLS-1$
		GridLayout layout = new GridLayout();
		group.setLayout(layout);
		layout.numColumns = 1;

		this.platformResourceButton = new Button(group, SWT.RADIO);
		this.platformResourceButton.setText(AcceleoUIMessages
				.getString("AcceleoCompilerPage.PlatformResourcePath")); //$NON-NLS-1$
		this.platformResourceButton.setLayoutData(new GridData());

		this.absolutePathButton = new Button(group, SWT.RADIO);
		this.absolutePathButton.setText(AcceleoUIMessages.getString("AcceleoCompilerPage.AbsolutePath")); //$NON-NLS-1$
		this.absolutePathButton.setLayoutData(new GridData());

		if (element instanceof IProject) {
			IProject project = (IProject)element;
			AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
			if (AcceleoBuilderSettings.COMPILATION_ABSOLUTE_PATH.equals(settings.getCompilationKind())) {
				this.absolutePathButton.setSelection(true);
				this.platformResourceButton.setSelection(false);
			} else if (AcceleoBuilderSettings.COMPILATION_PLATFORM_RESOURCE.equals(settings
					.getCompilationKind())) {
				this.absolutePathButton.setSelection(false);
				this.platformResourceButton.setSelection(true);
			} else {
				this.absolutePathButton.setSelection(true);
				this.platformResourceButton.setSelection(false);
			}
		} else {
			this.absolutePathButton.setSelection(true);
			this.platformResourceButton.setSelection(false);
		}

		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		ToolItem item = new ToolItem(result, SWT.NONE);
		item.setImage(image);

		String helpMessage = AcceleoUIMessages.getString("AcceleoCompilerPage.CompilationPathHelp"); //$NON-NLS-1$
		if (helpMessage != null && !"".equals(helpMessage)) { //$NON-NLS-1$
			item.setToolTipText(helpMessage);
		}
	}
}
