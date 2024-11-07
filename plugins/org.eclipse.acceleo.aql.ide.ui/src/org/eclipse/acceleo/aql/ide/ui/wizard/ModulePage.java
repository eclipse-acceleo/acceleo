/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.message.AcceleoUIMessages;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ide.ui.dialog.AQLTypeSelectionDialog;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.presentation.EcoreActionBarContributor.ExtendedLoadResourceAction.RegisteredPackageDialog;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

/**
 * New {@link Module} page.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ModulePage extends WizardPage {

	/**
	 * This will be used to allow the user to browse the workspace for examples.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class ExampleBrowseSelectionAdapter extends SelectionAdapter {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(getShell(), false,
					ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
			dialog.setTitle(AcceleoUIMessages.getString("AcceleoModuleComposite.ModuleElementExample")); //$NON-NLS-1$
			String path = initializeContentFile.getText();
			if (path != null && path.length() > 0 && new Path(path).lastSegment().length() > 0) {
				dialog.setInitialPattern("*." + new Path(path).getFileExtension()); //$NON-NLS-1$
			} else {
				dialog.setInitialPattern("*.java"); //$NON-NLS-1$
			}
			dialog.open();
			if (dialog.getResult() != null && dialog.getResult().length > 0 && dialog
					.getResult()[0] instanceof IFile) {
				initializeContentFile.setText(((IFile)dialog.getResult()[0]).getFullPath().toString());
			}
			updateModuleContiguration();
		}
	}

	/**
	 * Default module name.
	 */
	public static final String MODULE_NAME = "generate"; //$NON-NLS-1$

	/**
	 * Default module element name.
	 */
	public static final String MODULE_ELEMENT_NAME = "generateElement"; //$NON-NLS-1$

	/**
	 * The containing resource path in the workspace.
	 */
	private Text moduleContainer;

	/**
	 * The button to browse the workspace.
	 */
	private Button moduleContainerBrowseButton;

	/**
	 * The module name.
	 */
	private Text moduleName;

	/**
	 * The metamodel table.
	 */
	private Table metamodelTable;

	/**
	 * The add metamodel button.
	 */
	private Button addButton;

	/**
	 * The remove metamodel button.
	 */
	private Button removeButton;

	/**
	 * Available types in the current selected metamodels.
	 */
	private Text metamodelType;

	/**
	 * The metamodel type selection {@link Button}.
	 */
	private Button metamodelTypeSelectButton;

	/**
	 * The label of the module element.
	 */
	private Label moduleElementNameLabel;

	/**
	 * The module element name.
	 */
	private Text moduleElementName;

	/**
	 * The kind of module element: template.
	 */
	private Button templateModuleElementKind;

	/**
	 * The kind of module element: query.
	 */
	private Button queryModuleElementKind;

	/**
	 * Generate documentation.
	 */
	private Button generateDocumentation;

	/**
	 * Generate file.
	 */
	private Button generateFile;

	/**
	 * Generate main.
	 */
	private Button isMain;

	/**
	 * Initialize content.
	 */
	private Button initializeContent;

	/**
	 * The initialiaze content file label.
	 */
	private Label initializeContentFileLabel;

	/**
	 * The path of the initialize content file.
	 */
	private Text initializeContentFile;

	/**
	 * The browse button to initialize the content.
	 */
	private Button initializeContentBrowseButton;

	/**
	 * The initial container.
	 */
	private String initialContainer;

	private final ModuleConfiguration moduleConfiguration = new ModuleConfiguration();

	/**
	 * Constructor.
	 * 
	 * @param initialContainer
	 *            the initial container
	 */
	protected ModulePage(String initialContainer) {
		super("New Acceleo Module File");
		this.initialContainer = initialContainer;
	}

	/**
	 * Gets the edited {@link ModuleConfiguration}.
	 * 
	 * @return the {@link ModuleConfiguration}
	 */
	public ModuleConfiguration getModuleConfiguration() {
		return moduleConfiguration;
	}

	@Override
	public void createControl(Composite parent) {
		final Composite pageGroup = new Composite(parent, SWT.NONE);
		GridLayout pageGroupLayout = new GridLayout();
		pageGroupLayout.numColumns = 4;
		pageGroup.setLayout(pageGroupLayout);
		pageGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

		this.createModuleGroup(pageGroup);
		this.createSeparator(pageGroup);
		this.createModuleElementGroup(pageGroup);
		this.createSeparator(pageGroup);
		this.createInitializeGroup(pageGroup);

		setControl(pageGroup);
		updateModuleContiguration();
	}

	/**
	 * Creates a separator.
	 * 
	 * @param pageGroup
	 *            The composite parent.
	 */
	private void createSeparator(Composite pageGroup) {
		Label label = new Label(pageGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		final int eight = 8;
		gd.minimumHeight = eight;
		gd.heightHint = eight;
		gd.horizontalSpan = 4;
		label.setLayoutData(gd);
	}

	/**
	 * Creates the widgets needed to handle the selection of the container, the name of the module and the
	 * metamodels.
	 * 
	 * @param pageGroup
	 *            The page group
	 */
	private void createModuleGroup(Composite pageGroup) {
		// Parent folder
		Label label = new Label(pageGroup, SWT.NULL);
		label.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.ModuleContainer") + ':'); //$NON-NLS-1$

		moduleContainer = new Text(pageGroup, SWT.BORDER | SWT.SINGLE);
		if (initialContainer != null && !"".equals(initialContainer)) {
			moduleContainer.setText(initialContainer);
		}
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		moduleContainer.setLayoutData(gd);
		moduleContainer.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModuleContiguration();
			}
		});
		moduleContainerBrowseButton = new Button(pageGroup, SWT.PUSH);
		moduleContainerBrowseButton.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.Browse")); //$NON-NLS-1$
		moduleContainerBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowseWorkspace();
				updateModuleContiguration();
			}
		});

		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleContainerHelp")); //$NON-NLS-1$

		// Module name
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		Label moduleNameLabel = new Label(pageGroup, SWT.NULL);
		moduleNameLabel.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.ModuleName") + ':'); //$NON-NLS-1$
		moduleName = new Text(pageGroup, SWT.BORDER | SWT.SINGLE);
		moduleName.setText(MODULE_NAME);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		final int widthHint = 270;
		gridData.widthHint = widthHint;
		gridData.horizontalSpan = 2;
		moduleName.setLayoutData(gridData);
		moduleName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModuleContiguration();
			}
		});

		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleNameHelp")); //$NON-NLS-1$

		// Metamodel

		Label metamodelLabel = new Label(pageGroup, SWT.NONE);
		metamodelLabel.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.MetamodelURIs") + ':'); //$NON-NLS-1$
		gridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gridData.horizontalSpan = 1;
		metamodelLabel.setLayoutData(gridData);

		metamodelTable = new Table(pageGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		gridData.heightHint = 100;
		gridData.minimumHeight = 100;
		metamodelTable.setLayoutData(gridData);

		Composite tableButtonComposite = new Composite(pageGroup, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		tableButtonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		tableButtonComposite.setLayout(layout);
		addButton = new Button(tableButtonComposite, SWT.PUSH);
		Image addImage = AcceleoUIPlugin.getDefault().getImageRegistry().get(AcceleoUIPlugin.ADD_IMG_KEY);
		addButton.setImage(addImage);
		addButton.setToolTipText(AcceleoUIMessages.getString("AcceleoModuleComposite.AddButton")); //$NON-NLS-1$
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleSelectMetamodelURI();
				updateModuleContiguration();
			}
		});
		removeButton = new Button(tableButtonComposite, SWT.PUSH);
		Image removeImage = AcceleoUIPlugin.getDefault().getImageRegistry().get(AcceleoUIPlugin.DELETE_IMG_KEY);
		removeButton.setImage(removeImage);
		removeButton.setToolTipText(AcceleoUIMessages.getString("AcceleoModuleComposite.RemoveButton")); //$NON-NLS-1$
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int[] selectionIndices = metamodelTable.getSelectionIndices();
				for (int i : selectionIndices) {
					metamodelTable.remove(i);
				}
				metamodelTypeSelectButton.setEnabled(metamodelTable.getItemCount() != 0);
				final List<String> metamodelURIs = new ArrayList<>();
				for (TableItem tableItem : metamodelTable.getItems()) {
					metamodelURIs.add(tableItem.getText());
				}
				if (!AQLUtils.computeAvailableTypes(metamodelURIs, true, true, true).contains(metamodelType
						.getText())) {
					metamodelType.setText("");
				}
				updateModuleContiguration();
			}
		});
		this.createHelpButton(tableButtonComposite, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.MetamodelURIsHelp")); //$NON-NLS-1$
	}

	/**
	 * Creates the module element group.
	 * 
	 * @param pageGroup
	 *            the composite parent.
	 */
	private void createModuleElementGroup(Composite pageGroup) {
		// Module element name
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		moduleElementNameLabel = new Label(pageGroup, SWT.NULL);
		moduleElementNameLabel.setText(AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementNameTemplate") + ':'); //$NON-NLS-1$
		moduleElementName = new Text(pageGroup, SWT.BORDER | SWT.SINGLE);
		moduleElementName.setText(MODULE_ELEMENT_NAME);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		final int widthHint = 270;
		gridData.widthHint = widthHint;
		gridData.horizontalSpan = 2;
		moduleElementName.setLayoutData(gridData);
		moduleElementName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModuleContiguration();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementNameHelp")); //$NON-NLS-1$

		// Module element type
		Label typeLabel = new Label(pageGroup, SWT.NULL);
		typeLabel.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.ModuleElementType") + ':'); //$NON-NLS-1$
		metamodelType = new Text(pageGroup, SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 1;
		metamodelType.setLayoutData(gridData);
		metamodelTable.setToolTipText(AcceleoUIMessages.getString("AcceleoModuleComposite.Type")); //$NON-NLS-1$

		metamodelTypeSelectButton = new Button(pageGroup, SWT.PUSH);
		metamodelTypeSelectButton.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.Select")); //$NON-NLS-1$
		metamodelTypeSelectButton.setEnabled(metamodelTable.getItemCount() != 0);
		metamodelTypeSelectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final List<String> metamodelURIs = new ArrayList<>();
				for (TableItem tableItem : metamodelTable.getItems()) {
					metamodelURIs.add(tableItem.getText());
				}

				AQLTypeSelectionDialog dialog = new AQLTypeSelectionDialog(getShell(), "parameter",
						metamodelType.getText(), metamodelURIs, true, true, true);
				final int dialogResult = dialog.open();
				if (dialogResult == IDialogConstants.OK_ID) {
					metamodelType.setText(dialog.getSelectedType());
					updateModuleContiguration();
				}

			}
		});

		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementTypeHelp")); //$NON-NLS-1$

		// Module element kind (template / query)
		templateModuleElementKind = new Button(pageGroup, SWT.RADIO);
		templateModuleElementKind.setText(AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementKindTemplate")); //$NON-NLS-1$
		templateModuleElementKind.setSelection(true);
		templateModuleElementKind.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchModuleElementKind();
				updateModuleContiguration();
				updateModuleContainer();
			}
		});

		queryModuleElementKind = new Button(pageGroup, SWT.RADIO);
		queryModuleElementKind.setText(AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementKindQuery")); //$NON-NLS-1$
		queryModuleElementKind.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchModuleElementKind();
				updateModuleContiguration();
				updateModuleContainer();
			}
		});

		new Label(pageGroup, SWT.NONE);

		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementKindHelp")); //$NON-NLS-1$

		// Generate Documentation ?
		generateDocumentation = new Button(pageGroup, SWT.CHECK);
		generateDocumentation.setText(AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleGenerateDocumentation")); //$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		generateDocumentation.setLayoutData(gridData);
		generateDocumentation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModuleContiguration();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleGenerateDocumentationHelp")); //$NON-NLS-1$

		// If module element kind == template then activated else desactivated
		generateFile = new Button(pageGroup, SWT.CHECK);
		generateFile.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.TemplateGenerateFile")); //$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		generateFile.setLayoutData(gridData);
		generateFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModuleContiguration();
				updateModuleContainer();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.TemplateGenerateFileHelp")); //$NON-NLS-1$

		isMain = new Button(pageGroup, SWT.CHECK);
		isMain.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.TemplateMain")); //$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		isMain.setLayoutData(gridData);
		isMain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModuleContiguration();
				updateModuleContainer();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.TemplateMainHelp")); //$NON-NLS-1$
	}

	/**
	 * Create the initialization group.
	 * 
	 * @param pageGroup
	 *            The composite parent.
	 */
	private void createInitializeGroup(Composite pageGroup) {
		// Initialize content
		initializeContent = new Button(pageGroup, SWT.CHECK);
		initializeContent.setText(AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementInitializeContent")); //$NON-NLS-1$
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		initializeContent.setLayoutData(gridData);
		initializeContent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchInitializeContent();
				updateModuleContiguration();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementInitializeContentHelp")); //$NON-NLS-1$

		// Initialize content file
		initializeContentFileLabel = new Label(pageGroup, SWT.NONE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		initializeContentFileLabel.setLayoutData(gridData);
		initializeContentFileLabel.setText(AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementInitializeFile")); //$NON-NLS-1$
		initializeContentFileLabel.setEnabled(false);

		initializeContentFile = new Text(pageGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		initializeContentFile.setLayoutData(gridData);
		initializeContentFile.setEnabled(false);
		initializeContentFile.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				updateModuleContiguration();

			}
		});
		initializeContentFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModuleContiguration();
			}
		});

		initializeContentBrowseButton = new Button(pageGroup, SWT.PUSH);
		initializeContentBrowseButton.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.Browse")); //$NON-NLS-1$
		initializeContentBrowseButton.setEnabled(false);
		initializeContentBrowseButton.addSelectionListener(new ExampleBrowseSelectionAdapter());

		this.createHelpButton(pageGroup, AcceleoUIMessages.getString(
				"AcceleoModuleComposite.ModuleElementInitializeFileHelp")); //$NON-NLS-1$
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for the template container field.
	 */
	private void handleBrowseWorkspace() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin
				.getWorkspace().getRoot(), false, AcceleoUIMessages.getString(
						"AcceleoModuleComposite.ContainerSelection")); //$NON-NLS-1$
		IResource current = null;
		IPath path = new Path(moduleContainer.getText());
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
				moduleContainer.setText(((Path)result[0]).toString());
			}
		}
	}

	/**
	 * Creates a help button.
	 * 
	 * @param parent
	 *            The composite parent
	 * @param helpMessage
	 *            the help message
	 */
	private void createHelpButton(Composite parent, String helpMessage) {
		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		ToolItem item = new ToolItem(result, SWT.NONE);
		item.setImage(image);
		if (helpMessage != null && !"".equals(helpMessage)) { //$NON-NLS-1$
			item.setToolTipText(helpMessage);
		}
	}

	/**
	 * Update the module thanks to the data from the composite.
	 */
	private void updateModuleContiguration() {
		moduleConfiguration.setModuleName(moduleName.getText());

		IPath path = new Path(moduleContainer.getText());
		String projectName = path.segment(0);
		moduleConfiguration.setProjectName(projectName);
		moduleConfiguration.setParentFolder(moduleContainer.getText());
		Set<String> metamodelURIs = moduleConfiguration.getNsURIs();
		metamodelURIs.clear();
		for (TableItem tableItem : metamodelTable.getItems()) {
			metamodelURIs.add(tableItem.getText());
		}

		moduleConfiguration.setModuleElementName(moduleElementName.getText());
		moduleConfiguration.setModuleElementParameterType(metamodelType.getText());

		boolean isTemplate = templateModuleElementKind.getSelection();
		boolean isQuery = queryModuleElementKind.getSelection();

		if (isTemplate && !isQuery) {
			moduleConfiguration.setModuleElementEClass(AcceleoPackage.eINSTANCE.getTemplate());
		} else if (isQuery && !isTemplate) {
			moduleConfiguration.setModuleElementEClass(AcceleoPackage.eINSTANCE.getQuery());
			moduleConfiguration.setMainTemplate(false);
			moduleConfiguration.setGenerateFile(false);
		} else {
			// error
		}

		if (isTemplate) {
			boolean hasMain = isMain.getSelection();
			moduleConfiguration.setMainTemplate(hasMain);

			boolean generatesFile = generateFile.getSelection();
			moduleConfiguration.setGenerateFile(generatesFile);
		}

		boolean hasDocumentation = generateDocumentation.getSelection();
		moduleConfiguration.setGenerateDocumentation(hasDocumentation);

		boolean isInitialized = initializeContent.getSelection();
		moduleConfiguration.setIsInitialized(isInitialized);
		moduleConfiguration.setInitializationPath(initializeContentFile.getText());

		this.checkErrors();
	}

	/**
	 * Checks errors.
	 */
	private void checkErrors() {
		IStatus status = new Status(IStatus.OK, AcceleoUIPlugin.PLUGIN_ID, null);

		if (moduleConfiguration.getModuleName() == null || "".equals(moduleConfiguration.getModuleName())) { //$NON-NLS-1$
			String message = AcceleoUIMessages.getString("AcceleoModuleCompositeMessage.InvalidName"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (moduleConfiguration.getParentFolder() == null || "".equals(moduleConfiguration //$NON-NLS-1$
				.getParentFolder())) {
			String message = AcceleoUIMessages.getString("AcceleoModuleCompositeMessage.InvalidParentFolder"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (moduleConfiguration.getNsURIs() == null || moduleConfiguration.getNsURIs().size() == 0) {
			String message = AcceleoUIMessages.getString("AcceleoModuleCompositeMessage.EmptyMetamodelURIs"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (moduleConfiguration.getModuleElementName() == null || "".equals(moduleConfiguration //$NON-NLS-1$
				.getModuleElementName())) {
			String message = AcceleoUIMessages.getString(
					"AcceleoModuleCompositeMessage.InvalidModuleElementName"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (moduleConfiguration.getModuleElementParameterType() == null || "".equals( //$NON-NLS-1$
				moduleConfiguration.getModuleElementParameterType())) {
			String message = AcceleoUIMessages.getString(
					"AcceleoModuleCompositeMessage.InvalidModuleElementParameterType"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (templateModuleElementKind.getSelection() && moduleConfiguration.isIsInitialized()
				&& (moduleConfiguration.getInitializationPath() == null || "" //$NON-NLS-1$
						.equals(moduleConfiguration.getInitializationPath()))) {
			String message = AcceleoUIMessages.getString(
					"AcceleoModuleCompositeMessage.EmptyModuleElementInitializationPath"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (templateModuleElementKind.getSelection() && moduleConfiguration.isIsInitialized()
				&& !(fileExists(moduleConfiguration.getInitializationPath()))) {
			String message = AcceleoUIMessages.getString(
					"AcceleoModuleCompositeMessage.InvalidModuleElementInitializationPath"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (moduleExists(moduleConfiguration.getProjectName(), moduleConfiguration.getParentFolder(),
				moduleConfiguration.getModuleName())) {
			String message = AcceleoUIMessages.getString("AcceleoModuleCompositeMessage.FileAlreadyExists"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		}

		setMessage(MODULE_ELEMENT_NAME, ERROR);

		applyToStatusLine(status);
	}

	/**
	 * Applies the status to the status line of a dialog page.
	 * 
	 * @param status
	 *            the status to apply
	 */
	public void applyToStatusLine(IStatus status) {
		String message = status.getMessage();
		if (message != null && message.length() == 0) {
			message = null;
		}
		switch (status.getSeverity()) {
			case IStatus.OK:
				setMessage(message, IMessageProvider.NONE);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			case IStatus.WARNING:
				setMessage(message, IMessageProvider.WARNING);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			case IStatus.INFO:
				setMessage(message, IMessageProvider.INFORMATION);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			default:
				setMessage(null);
				setErrorMessage(message);
				setPageComplete(false);
				break;
		}
	}

	/**
	 * Switch the enablement of the initialization widgets.
	 */
	private void switchInitializeContent() {
		if (initializeContent.getSelection()) {
			initializeContentFileLabel.setEnabled(true);
			initializeContentFile.setEnabled(true);
			initializeContentBrowseButton.setEnabled(true);
		} else {
			initializeContentFileLabel.setEnabled(false);
			initializeContentFile.setEnabled(false);
			initializeContentBrowseButton.setEnabled(false);
		}
	}

	/**
	 * Update the container of the module.
	 */
	private void updateModuleContainer() {
		/*
		 * If the path of the module is the original path (see initialContainer) directly followed by one of
		 * the four possibilities (common, main, files, requests) then we will update it. We assume that way
		 * that the user did not changed the path of its module.
		 */

		final String common = "/common"; //$NON-NLS-1$
		final String main = "/main"; //$NON-NLS-1$
		final String files = "/files"; //$NON-NLS-1$
		final String requests = "/requests"; //$NON-NLS-1$

		String basicContainer = initialContainer;

		/*
		 * We compute the basic container aka the container minus one of the four possibilities (common, main,
		 * files, requests) to compute the new container later.
		 */

		if (basicContainer != null && basicContainer.endsWith(common)) {
			basicContainer = basicContainer.substring(0, basicContainer.length() - common.length());
		} else if (basicContainer != null && basicContainer.endsWith(main)) {
			basicContainer = basicContainer.substring(0, basicContainer.length() - main.length());
		} else if (basicContainer != null && basicContainer.endsWith(files)) {
			basicContainer = basicContainer.substring(0, basicContainer.length() - files.length());
		} else if (basicContainer != null && basicContainer.endsWith(requests)) {
			basicContainer = basicContainer.substring(0, basicContainer.length() - requests.length());
		}

		boolean canUpdate = false;
		if (basicContainer != null && moduleContainer.getText() != null) {
			String currentContainer = moduleContainer.getText();

			canUpdate = canUpdate || currentContainer.equals(basicContainer + common);
			canUpdate = canUpdate || currentContainer.equals(basicContainer + main);
			canUpdate = canUpdate || currentContainer.equals(basicContainer + files);
			canUpdate = canUpdate || currentContainer.equals(basicContainer + requests);
		}

		/*
		 * If we think that we can update the container, here are the rules that we will follow to update the
		 * container. Modules with a template go in "/common", if the template is a main template aka a
		 * starting point of the generation, we move it to "/main", if the template is not a starting point of
		 * the generation and if it generate a file we move it to "/files". If we have a query, we will see
		 * first if it is in "/common" or "/requests" if that is the case, we don't change anything. If, on
		 * the contrary we are in "/files" or "/main" because we thought about creating a template before, we
		 * will move the module with the query to "/requests" by default.
		 */

		if (canUpdate) {
			boolean isTemplate = templateModuleElementKind.getSelection();
			boolean isQuery = queryModuleElementKind.getSelection();

			if (isTemplate && !isQuery) {
				boolean hasMain = isMain.getSelection();
				boolean generatesFile = generateFile.getSelection();
				if (generatesFile && !hasMain) {
					moduleContainer.setText(basicContainer + files);
				} else if (hasMain) {
					moduleContainer.setText(basicContainer + main);
				} else {
					moduleContainer.setText(basicContainer + common);
				}
			} else if (isQuery && !isTemplate) {
				if (moduleContainer.getText() != null && !(moduleContainer.getText().endsWith(common)
						|| moduleContainer.getText().endsWith(requests))) {
					moduleContainer.setText(basicContainer + requests);
				}
			}
		}
	}

	/**
	 * Switch the element kind between template and query.
	 */
	private void switchModuleElementKind() {
		if (templateModuleElementKind.getSelection()) {
			generateFile.setEnabled(true);
			isMain.setEnabled(true);
			initializeContent.setEnabled(true);
			initializeContentFile.setEnabled(true);
			initializeContentBrowseButton.setEnabled(true);
			moduleElementNameLabel.setText(AcceleoUIMessages.getString(
					"AcceleoModuleComposite.ModuleElementNameTemplate") + ':'); //$NON-NLS-1$
		} else {
			generateFile.setEnabled(false);
			isMain.setEnabled(false);
			initializeContent.setEnabled(false);
			initializeContentFile.setEnabled(false);
			initializeContentBrowseButton.setEnabled(false);
			moduleElementNameLabel.setText(AcceleoUIMessages.getString(
					"AcceleoModuleComposite.ModuleElementNameQuery") + ':'); //$NON-NLS-1$
		}
	}

	/**
	 * Dialog to select the metamodel URI.
	 */
	private void handleSelectMetamodelURI() {
		RegisteredPackageDialog registeredPackageDialog = new RegisteredPackageDialog(getShell());
		registeredPackageDialog.open();
		Object[] result = registeredPackageDialog.getResult();
		if (result != null) {
			List<?> nsURIs = Arrays.asList(result);
			Set<String> uriSet = new LinkedHashSet<String>();
			if (registeredPackageDialog.isDevelopmentTimeVersion()) {
				StringBuffer uris = new StringBuffer();
				try {
					ResourceSet resourceSet = new ResourceSetImpl();
					// TODO AcceleoPackageRegistry.INSTANCE ?
					resourceSet.setPackageRegistry(EPackage.Registry.INSTANCE);
					resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap(true));
					Map<String, URI> ePackageNsURItoGenModelLocationMap = EcorePlugin
							.getEPackageNsURIToGenModelLocationMap(true);
					for (int i = 0; i < result.length; i++) {
						URI location = ePackageNsURItoGenModelLocationMap.get(result[i]);
						Resource resource = resourceSet.getResource(location, true);
						EcoreUtil.resolveAll(resource);
					}
					for (Resource resource : resourceSet.getResources()) {
						for (EPackage ePackage : getAllPackages(resource)) {
							if (nsURIs.contains(ePackage.getNsURI())) {
								uriSet.add(ePackage.getNsURI());
								uris.append(ePackage.getNsURI());
								uris.append(',');
								break;
							}
						}
					}
				} catch (WrappedException e) {
					// It catches an EMF WrappedException.
					// It is very useful if the EMF registry is corrupted by other contributions.
					AcceleoUIPlugin.getDefault().getLog().log(new Status(ERROR, getClass(), e.getMessage(), e));
				}
			} else {
				for (int i = 0; i < result.length; i++) {
					uriSet.add(result[i].toString());
				}
			}

			for (String uri : uriSet) {
				boolean exists = false;
				TableItem[] items = this.metamodelTable.getItems();
				for (TableItem tableItem : items) {
					if (tableItem.getText().equals(uri)) {
						exists = true;
					}
				}
				if (!exists) {
					TableItem item = new TableItem(this.metamodelTable, SWT.NONE);
					item.setText(uri);
				}
			}
		}
		metamodelTypeSelectButton.setEnabled(metamodelTable.getItemCount() != 0);
	}

	/**
	 * Gets all the packages of the given EMF resource.
	 * 
	 * @param resource
	 *            is an EMF resource
	 * @return all packages
	 */
	private Collection<EPackage> getAllPackages(Resource resource) {
		List<EPackage> result = new ArrayList<EPackage>();
		TreeIterator<?> iterator = new EcoreUtil.ContentTreeIterator<Object>(resource.getContents()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<? extends EObject> getEObjectChildren(EObject eObject) {
				if (eObject instanceof EPackage) {
					return ((EPackage)eObject).getESubpackages().iterator();
				}
				return Collections.<EObject> emptyList().iterator();
			}
		};
		while (iterator.hasNext()) {
			Object content = iterator.next();
			if (content instanceof EPackage) {
				result.add((EPackage)content);
			}
		}
		return result;
	}

	/**
	 * Tells if the given path is an {@link IFile} that is {@link IFile#isAccessible() accessible}.
	 * 
	 * @param path
	 *            the path
	 * @return <code>true</code> if the given path is an {@link IFile} that is {@link IFile#isAccessible()
	 *         accessible}, <code>false</code> otherwise
	 */
	private boolean fileExists(String path) {
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));

		return file != null && file.exists() && file.isAccessible();
	}

	/**
	 * Checks if the module exists.
	 * 
	 * @param projectName
	 *            The project name
	 * @param outputPath
	 *            The output container path
	 * @param module
	 *            The module name
	 * @return <code>true</code> if the file to create exists, <code>false</code> otherwise.
	 */
	private boolean moduleExists(String projectName, String outputPath, String module) {
		boolean result = false;

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project.exists() && project.isAccessible()) {
			IPath path = new Path(outputPath);
			final IContainer container;
			if (path.segmentCount() > 1) {
				container = project.getFolder(path.removeFirstSegments(1));
			} else {
				container = project;
			}
			if (container.exists() && container.isAccessible()) {
				IFile file = container.getFile(new Path(module + '.' + AcceleoParser.MODULE_FILE_EXTENSION));
				result = file.exists() && file.isAccessible();
			}
		}

		return result;
	}

}
