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
package org.eclipse.acceleo.internal.ide.ui.wizards.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind;
import org.eclipse.acceleo.internal.ide.ui.wizards.module.example.AcceleoInitializationStrategyUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.presentation.EcoreActionBarContributor.ExtendedLoadResourceAction.RegisteredPackageDialog;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
 * The composite used to initialize new Acceleo modules.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoModuleComposite extends Composite {
	/**
	 * Default module name.
	 */
	public static final String MODULE_NAME = "generate"; //$NON-NLS-1$

	/**
	 * Default module element name.
	 */
	public static final String MODULE_ELEMENT_NAME = "generateElement"; //$NON-NLS-1$

	/**
	 * The Acceleo module composite listener.
	 */
	private IAcceleoModuleCompositeListener listener;

	/**
	 * The Acceleo module.
	 */
	private AcceleoModule acceleoModule = AcceleowizardmodelFactory.eINSTANCE.createAcceleoModule();

	/**
	 * The Acceleo module element.
	 */
	private AcceleoModuleElement acceleoModuleElement = AcceleowizardmodelFactory.eINSTANCE
			.createAcceleoModuleElement();

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
	 * Available types in the current metamodel. The metamodel is identified by the metamodel URI text widget.
	 */
	private Combo metamodelType;

	/**
	 * Available types names in the current metamodel. The metamodel is identified by the metamodel URI text
	 * widget.
	 * <p>
	 * <code> assert metamodelType.getItemCount() == metamodelTypes.length </code>
	 */
	private String[] metamodelTypes;

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
	 * The initialize content kind.
	 */
	private Combo initializeContentKind;

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
	 * Indicates if we should update the module.
	 */
	private boolean shouldUpdate;

	/**
	 * The initial container.
	 */
	private String initialContainer;

	/**
	 * The constructor.
	 * 
	 * @param parent
	 *            The parent composite.
	 * @param listener
	 *            The listener of the module.
	 */
	public AcceleoModuleComposite(Composite parent, IAcceleoModuleCompositeListener listener) {
		super(parent, SWT.NONE);
		this.shouldUpdate = true;
		this.listener = listener;
		acceleoModule.setModuleElement(acceleoModuleElement);
		this.createControls();
		this.updateModule();
	}

	/**
	 * Sets the module container.
	 * 
	 * @param container
	 *            The module container.
	 */
	public void setModuleContainer(String container) {
		initialContainer = container;
		if (this.moduleContainer != null) {
			this.moduleContainer.setText(container);
		}
	}

	/**
	 * Indicates if we have all the necessary information to complete the creation of the module.
	 * 
	 * @return <code>true</code> if we have everything needed to create a module.
	 */
	public boolean isComplete() {
		return true;
	}

	/**
	 * Initialize the composite from an Acceleo module.
	 * 
	 * @param acceleoModule
	 *            The Acceleo module.
	 */
	public void setAcceleoModule(AcceleoModule acceleoModule) {
		// module name
		this.acceleoModule = acceleoModule;
		if (this.acceleoModule.getName() != null) {
			moduleName.setText(this.acceleoModule.getName());
		}
		// module parent folder
		if (this.acceleoModule.getParentFolder() != null) {
			moduleContainer.setText(this.acceleoModule.getParentFolder());
		}
		// module metamodels
		this.metamodelTable.removeAll();
		EList<String> metamodelURIs = this.acceleoModule.getMetamodelURIs();
		for (String uri : metamodelURIs) {
			TableItem item = new TableItem(this.metamodelTable, SWT.NONE);
			item.setText(uri);
		}
		// refresh available type
		updateMetaModelTypes();
		// module element name
		if (this.acceleoModule.getModuleElement().getName() != null) {
			moduleElementName.setText(this.acceleoModule.getModuleElement().getName());
		}
		// module element parameter type
		if (this.acceleoModule.getModuleElement().getParameterType() != null) {
			metamodelType.setText(this.acceleoModule.getModuleElement().getParameterType());
		}
		// module element kind
		if (this.acceleoModule.getModuleElement().getKind() == ModuleElementKind.QUERY) {
			queryModuleElementKind.setSelection(true);
			templateModuleElementKind.setSelection(false);
			isMain.setEnabled(false);
			generateFile.setEnabled(false);
		} else if (this.acceleoModule.getModuleElement().getKind() == ModuleElementKind.TEMPLATE) {
			queryModuleElementKind.setSelection(false);
			templateModuleElementKind.setSelection(true);
			isMain.setEnabled(true);
			generateFile.setEnabled(true);
		}
		// generate documentation
		if (this.acceleoModule.isGenerateDocumentation()) {
			generateDocumentation.setSelection(true);
		} else {
			generateDocumentation.setSelection(false);
		}
		// generate file
		if (this.acceleoModule.getModuleElement().isGenerateFile()) {
			generateFile.setSelection(true);
		} else {
			generateFile.setSelection(false);
		}
		// is main
		if (this.acceleoModule.getModuleElement().isIsMain()) {
			isMain.setSelection(true);
		} else {
			isMain.setSelection(false);
		}
		// initialize content
		if (this.acceleoModule.isIsInitialized()) {
			initializeContent.setSelection(true);
			initializeContentKind.setEnabled(true);
			initializeContentFile.setEnabled(true);
		} else {
			initializeContent.setSelection(false);
			initializeContentKind.setEnabled(false);
			initializeContentFile.setEnabled(false);
		}
		// initialize kind
		if (this.acceleoModule.getInitializationKind() != null) {
			initializeContentKind.setText(this.acceleoModule.getInitializationKind());
		}
		// initialize path
		if (this.acceleoModule.getInitializationPath() != null) {
			initializeContentFile.setText(this.acceleoModule.getInitializationPath());
		}
	}

	/**
	 * Initialize the composite from an Acceleo module without updating the module in the process.
	 * 
	 * @param anAcceleoModule
	 *            The Acceleo module.
	 */
	public void setAcceleoModuleWithoutUpdate(AcceleoModule anAcceleoModule) {
		this.shouldUpdate = false;
		this.setAcceleoModule(anAcceleoModule);
		this.shouldUpdate = true;
		this.updateModule();
	}

	/**
	 * Update the module thanks to the data from the composite.
	 */
	private void updateModule() {
		if (!shouldUpdate) {
			return;
		}
		this.acceleoModule.setName(moduleName.getText());

		IPath path = new Path(moduleContainer.getText());
		String projectName = path.segment(0);
		this.acceleoModule.setProjectName(projectName);
		this.acceleoModule.setParentFolder(moduleContainer.getText());
		EList<String> metamodelURIs = this.acceleoModule.getMetamodelURIs();
		TableItem[] items = metamodelTable.getItems();
		for (TableItem tableItem : items) {
			metamodelURIs.add(tableItem.getText());
		}

		this.acceleoModule.getModuleElement().setName(moduleElementName.getText());
		this.acceleoModule.getModuleElement().setParameterType(metamodelType.getText());

		boolean isTemplate = templateModuleElementKind.getSelection();
		boolean isQuery = queryModuleElementKind.getSelection();

		if (isTemplate && !isQuery) {
			this.acceleoModule.getModuleElement().setKind(ModuleElementKind.TEMPLATE);
		} else if (isQuery && !isTemplate) {
			this.acceleoModule.getModuleElement().setKind(ModuleElementKind.QUERY);
			this.acceleoModule.getModuleElement().setIsMain(false);
			this.acceleoModule.getModuleElement().setGenerateFile(false);
		} else {
			// error
		}

		if (isTemplate) {
			boolean hasMain = isMain.getSelection();
			this.acceleoModule.getModuleElement().setIsMain(hasMain);

			boolean generatesFile = generateFile.getSelection();
			this.acceleoModule.getModuleElement().setGenerateFile(generatesFile);
		}

		boolean hasDocumentation = generateDocumentation.getSelection();
		this.acceleoModule.setGenerateDocumentation(hasDocumentation);

		boolean isInitialized = initializeContent.getSelection();
		this.acceleoModule.setIsInitialized(isInitialized);
		this.acceleoModule.setInitializationKind(initializeContentKind.getText());
		this.acceleoModule.setInitializationPath(initializeContentFile.getText());

		this.checkErrors();
	}

	/**
	 * Checks errors.
	 */
	private void checkErrors() {
		IStatus status = new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, null);

		if (acceleoModule.getName() == null || "".equals(acceleoModule.getName())) { //$NON-NLS-1$
			String message = AcceleoUIMessages.getString("AcceleoModuleCompositeMessage.InvalidName"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		} else if (acceleoModule.getParentFolder() == null || "".equals(acceleoModule.getParentFolder())) { //$NON-NLS-1$
			String message = AcceleoUIMessages.getString("AcceleoModuleCompositeMessage.InvalidParentFolder"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		} else if (acceleoModule.getMetamodelURIs() == null || acceleoModule.getMetamodelURIs().size() == 0) {
			String message = AcceleoUIMessages.getString("AcceleoModuleCompositeMessage.EmptyMetamodelURIs"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		} else if (acceleoModule.getModuleElement().getName() == null
				|| "".equals(acceleoModule.getModuleElement().getName())) { //$NON-NLS-1$
			String message = AcceleoUIMessages
					.getString("AcceleoModuleCompositeMessage.InvalidModuleElementName"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		} else if (acceleoModule.getModuleElement().getParameterType() == null
				|| "".equals(acceleoModule.getModuleElement().getParameterType())) { //$NON-NLS-1$
			String message = AcceleoUIMessages
					.getString("AcceleoModuleCompositeMessage.InvalidModuleElementParameterType"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		} else if (acceleoModule.isIsInitialized() && (acceleoModule.getInitializationKind() == null || "" //$NON-NLS-1$
				.equals(acceleoModule.getInitializationKind()))) {
			String message = AcceleoUIMessages
					.getString("AcceleoModuleCompositeMessage.InvalidModuleElementInitializationKind"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		} else if (acceleoModule.isIsInitialized() && (acceleoModule.getInitializationPath() == null || "" //$NON-NLS-1$
				.equals(acceleoModule.getInitializationPath()))) {
			String message = AcceleoUIMessages
					.getString("AcceleoModuleCompositeMessage.EmptyModuleElementInitializationPath"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		} else if (acceleoModule.isIsInitialized() && !(fileExists(acceleoModule.getInitializationPath()))) {
			String message = AcceleoUIMessages
					.getString("AcceleoModuleCompositeMessage.InvalidModuleElementInitializationPath"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		} else if (moduleExists(acceleoModule.getProjectName(), acceleoModule.getParentFolder(),
				acceleoModule.getName())) {
			String message = AcceleoUIMessages.getString("AcceleoModuleCompositeMessage.FileAlreadyExists"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
		}

		this.listener.applyToStatusLine(status);
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
				IFile file = container.getFile(new Path(module + '.' + IAcceleoConstants.MTL_FILE_EXTENSION));
				result = file.exists() && file.isAccessible();
			}
		}

		return result;
	}

	/**
	 * Creates the control of the composite.
	 */
	private void createControls() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		this.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		gridData = new GridData(GridData.FILL_BOTH);
		final int heightHint = 540;
		gridData.heightHint = heightHint;
		gridData.minimumHeight = heightHint;
		this.setLayoutData(gridData);
		this.createWidgets();
	}

	/**
	 * Place the widgets in this composite.
	 */
	private void createWidgets() {
		final Composite pageGroup = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		pageGroup.setLayout(layout);
		pageGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

		this.createModuleGroup(pageGroup);
		this.createSeparator(pageGroup);
		this.createModuleElementGroup(pageGroup);
		this.createSeparator(pageGroup);
		this.createInitializeGroup(pageGroup);
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
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		moduleContainer.setLayoutData(gd);
		moduleContainer.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModule();
			}
		});
		moduleContainerBrowseButton = new Button(pageGroup, SWT.PUSH);
		moduleContainerBrowseButton.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.Browse")); //$NON-NLS-1$
		moduleContainerBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowseWorkspace();
				updateModule();
			}
		});

		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleContainerHelp")); //$NON-NLS-1$

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
				updateModule();
			}
		});

		this.createHelpButton(pageGroup, AcceleoUIMessages.getString("AcceleoModuleComposite.ModuleNameHelp")); //$NON-NLS-1$

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
		Image addImage = AcceleoUIActivator.getDefault().getImage("icons/add_obj.gif"); //$NON-NLS-1$
		addButton.setImage(addImage);
		addButton.setToolTipText(AcceleoUIMessages.getString("AcceleoModuleComposite.AddButton")); //$NON-NLS-1$
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleSelectMetamodelURI();
				updateMetaModelTypes();
				updateModule();
			}
		});
		removeButton = new Button(tableButtonComposite, SWT.PUSH);
		Image removeImage = AcceleoUIActivator.getDefault().getImage("icons/delete_obj.gif"); //$NON-NLS-1$
		removeButton.setImage(removeImage);
		removeButton.setToolTipText(AcceleoUIMessages.getString("AcceleoModuleComposite.RemoveButton")); //$NON-NLS-1$
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int[] selectionIndices = metamodelTable.getSelectionIndices();
				for (int i : selectionIndices) {
					metamodelTable.remove(i);
				}
				updateMetaModelTypes();
				updateModule();
			}
		});
		this.createHelpButton(tableButtonComposite, AcceleoUIMessages
				.getString("AcceleoModuleComposite.MetamodelURIsHelp")); //$NON-NLS-1$
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for the template container field.
	 */
	private void handleBrowseWorkspace() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin
				.getWorkspace().getRoot(), false, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ContainerSelection")); //$NON-NLS-1$
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
	 * Updates the metamodel types.
	 */
	private void updateMetaModelTypes() {
		if (metamodelType != null) {
			String oldSelection = metamodelType.getText();
			List<String> typeValues = new ArrayList<String>();
			TableItem[] items = this.metamodelTable.getItems();
			for (TableItem tableItem : items) {
				String tableItemText = tableItem.getText();
				StringTokenizer st = new StringTokenizer(tableItemText, ","); //$NON-NLS-1$
				while (st.hasMoreTokens()) {
					EPackage ePackage = ModelUtils.getEPackage(st.nextToken().trim());
					if (ePackage != null) {
						List<EClassifier> eClassifiers = new ArrayList<EClassifier>();
						computeClassifiers(eClassifiers, ePackage);
						for (int i = 0; i < eClassifiers.size(); i++) {
							EClassifier eClassifier = eClassifiers.get(i);
							typeValues.add(eClassifier.getName());
						}
					}
				}
			}

			Collections.sort(typeValues);

			// We add OclAny to make it available for all metamodels.
			final String oclAny = "OclAny"; //$NON-NLS-1$
			typeValues.add(0, oclAny);
			metamodelTypes = typeValues.toArray(new String[typeValues.size()]);
			metamodelType.setItems(metamodelTypes);
			final int visibleItemCount = 15;
			if (metamodelTypes.length < visibleItemCount) {
				metamodelType.setVisibleItemCount(metamodelTypes.length);
			} else {
				metamodelType.setVisibleItemCount(visibleItemCount);
			}
			if (oclAny.equals(oldSelection)) {
				updateDefaultTypes();
			} else {
				metamodelType.setText(oldSelection);
				if (metamodelType.getText().length() == 0) {
					updateDefaultTypes();
				}
			}
		}
	}

	/**
	 * Gets the classifiers of the current metamodel. The result is put in the first parameter.
	 * 
	 * @param eClassifiers
	 *            are the classifiers (output parameter)
	 * @param ePackage
	 *            is the metamodel to visit
	 */
	private static void computeClassifiers(List<EClassifier> eClassifiers, EPackage ePackage) {
		Iterator<EClassifier> classifiers = ePackage.getEClassifiers().iterator();
		while (classifiers.hasNext()) {
			EClassifier eClassifier = classifiers.next();
			eClassifiers.add(eClassifier);
		}
		Iterator<EPackage> packages = ePackage.getESubpackages().iterator();
		while (packages.hasNext()) {
			computeClassifiers(eClassifiers, packages.next());
		}
	}

	/**
	 * A default value for the 'metamodelType' combobox. This widget defines the available types in the
	 * current metamodel. This method tries to identify which one is probably the good one.
	 */
	private void updateDefaultTypes() {
		metamodelType.setText("Element"); //$NON-NLS-1$
		metamodelType.setText("Model"); //$NON-NLS-1$
		metamodelType.setText("File"); //$NON-NLS-1$
		metamodelType.setText("Type"); //$NON-NLS-1$
		metamodelType.setText("EClass"); //$NON-NLS-1$
		metamodelType.setText("Class"); //$NON-NLS-1$

		if (metamodelType.getText() == null || "".equals(metamodelType.getText())) { //$NON-NLS-1$
			// Empty combo box, so select the second one if the first one is OclAny.
			if (metamodelType.getItemCount() > 1) {
				String item = metamodelType.getItem(0);
				if ("OclAny".equals(item)) { //$NON-NLS-1$
					metamodelType.select(1);
				} else {
					metamodelType.select(0);
				}
			} else if (metamodelType.getItemCount() == 1) {
				metamodelType.select(0);
			}
		}
	}

	/**
	 * Dialog to select the metamodel URI.
	 */
	private void handleSelectMetamodelURI() {
		RegisteredPackageDialog registeredPackageDialog = new AcceleoRegisteredPackageDialog(getShell());
		registeredPackageDialog.open();
		Object[] result = registeredPackageDialog.getResult();
		if (result != null) {
			List<?> nsURIs = Arrays.asList(result);
			Set<String> uriSet = new CompactLinkedHashSet<String>();
			if (registeredPackageDialog.isDevelopmentTimeVersion()) {
				StringBuffer uris = new StringBuffer();
				try {
					ResourceSet resourceSet = new ResourceSetImpl();
					resourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);
					resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());
					Map<String, URI> ePackageNsURItoGenModelLocationMap = EcorePlugin
							.getEPackageNsURIToGenModelLocationMap();
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
					AcceleoUIActivator.log(e, true);
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
	 * Creates the module element group.
	 * 
	 * @param pageGroup
	 *            the composite parent.
	 */
	private void createModuleElementGroup(Composite pageGroup) {
		// Module element name
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		moduleElementNameLabel = new Label(pageGroup, SWT.NULL);
		moduleElementNameLabel.setText(AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementNameTemplate") + ':'); //$NON-NLS-1$
		moduleElementName = new Text(pageGroup, SWT.BORDER | SWT.SINGLE);
		moduleElementName.setText(MODULE_ELEMENT_NAME);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		final int widthHint = 270;
		gridData.widthHint = widthHint;
		gridData.horizontalSpan = 2;
		moduleElementName.setLayoutData(gridData);
		moduleElementName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModule();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementNameHelp")); //$NON-NLS-1$

		// Module element type
		Label typeLabel = new Label(pageGroup, SWT.NULL);
		typeLabel.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.ModuleElementType") + ':'); //$NON-NLS-1$
		metamodelType = new Combo(pageGroup, SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		metamodelType.setLayoutData(gridData);
		metamodelTable.setToolTipText(AcceleoUIMessages.getString("AcceleoModuleComposite.Type")); //$NON-NLS-1$
		metamodelType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do here
			}

			public void widgetSelected(SelectionEvent e) {
				updateModule();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementTypeHelp")); //$NON-NLS-1$

		// Module element kind (template / query)
		templateModuleElementKind = new Button(pageGroup, SWT.RADIO);
		templateModuleElementKind.setText(AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementKindTemplate")); //$NON-NLS-1$
		templateModuleElementKind.setSelection(true);
		templateModuleElementKind.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchModuleElementKind();
				updateModule();
				updateModuleContainer();
			}
		});

		queryModuleElementKind = new Button(pageGroup, SWT.RADIO);
		queryModuleElementKind.setText(AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementKindQuery")); //$NON-NLS-1$
		queryModuleElementKind.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchModuleElementKind();
				updateModule();
				updateModuleContainer();
			}
		});

		new Label(pageGroup, SWT.NONE);

		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementKindHelp")); //$NON-NLS-1$

		// Generate Documentation ?
		generateDocumentation = new Button(pageGroup, SWT.CHECK);
		generateDocumentation.setText(AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleGenerateDocumentation")); //$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		generateDocumentation.setLayoutData(gridData);
		generateDocumentation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModule();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleGenerateDocumentationHelp")); //$NON-NLS-1$

		// If module element kind == template then activated else desactivated
		generateFile = new Button(pageGroup, SWT.CHECK);
		generateFile.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.TemplateGenerateFile")); //$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		generateFile.setLayoutData(gridData);
		generateFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModule();
				updateModuleContainer();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.TemplateGenerateFileHelp")); //$NON-NLS-1$

		isMain = new Button(pageGroup, SWT.CHECK);
		isMain.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.TemplateMain")); //$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		isMain.setLayoutData(gridData);
		isMain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModule();
				updateModuleContainer();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.TemplateMainHelp")); //$NON-NLS-1$
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
				if (moduleContainer.getText() != null
						&& !(moduleContainer.getText().endsWith(common) || moduleContainer.getText()
								.endsWith(requests))) {
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
			moduleElementNameLabel.setText(AcceleoUIMessages
					.getString("AcceleoModuleComposite.ModuleElementNameTemplate") + ':'); //$NON-NLS-1$
		} else {
			generateFile.setEnabled(false);
			isMain.setEnabled(false);
			moduleElementNameLabel.setText(AcceleoUIMessages
					.getString("AcceleoModuleComposite.ModuleElementNameQuery") + ':'); //$NON-NLS-1$
		}
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
		initializeContent.setText(AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementInitializeContent")); //$NON-NLS-1$
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		initializeContent.setLayoutData(gridData);
		initializeContent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switchInitializeContent();
				changeStrategy();
				updateModule();
			}
		});
		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementInitializeContentHelp")); //$NON-NLS-1$

		// Initialize content kind
		initializeContentKind = new Combo(pageGroup, SWT.READ_ONLY);
		initializeContentKind.setEnabled(false);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		initializeContentKind.setLayoutData(gridData);
		initializeContentKind.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do here
			}

			public void widgetSelected(SelectionEvent e) {
				changeStrategy();
				updateModule();
			}
		});
		updateStrategies();

		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementInitializeContentKindHelp")); //$NON-NLS-1$

		// Initialize content file
		initializeContentFileLabel = new Label(pageGroup, SWT.NONE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		initializeContentFileLabel.setLayoutData(gridData);
		initializeContentFileLabel.setText(AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementInitializeFile")); //$NON-NLS-1$
		initializeContentFileLabel.setEnabled(false);

		initializeContentFile = new Text(pageGroup, SWT.BORDER);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		initializeContentFile.setLayoutData(gridData);
		initializeContentFile.setEnabled(false);
		initializeContentFile.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				updateModule();

			}
		});
		initializeContentFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateModule();
			}
		});

		initializeContentBrowseButton = new Button(pageGroup, SWT.PUSH);
		initializeContentBrowseButton.setText(AcceleoUIMessages.getString("AcceleoModuleComposite.Browse")); //$NON-NLS-1$
		initializeContentBrowseButton.setEnabled(false);
		initializeContentBrowseButton.addSelectionListener(new ExampleBrowseSelectionAdapter());

		this.createHelpButton(pageGroup, AcceleoUIMessages
				.getString("AcceleoModuleComposite.ModuleElementInitializeFileHelp")); //$NON-NLS-1$
	}

	/**
	 * Switch the enablement of the initialization widgets.
	 */
	private void switchInitializeContent() {
		if (initializeContent.getSelection()) {
			initializeContentKind.setEnabled(true);
			initializeContentFileLabel.setEnabled(true);
			initializeContentFile.setEnabled(true);
			initializeContentBrowseButton.setEnabled(true);
		} else {
			initializeContentKind.setEnabled(false);
			initializeContentFileLabel.setEnabled(false);
			initializeContentFile.setEnabled(false);
			initializeContentBrowseButton.setEnabled(false);
		}
	}

	/**
	 * Reads the current example strategy and updates the state of the widgets for this strategy.
	 */
	private void changeStrategy() {
		metamodelTable.setEnabled(true);
		metamodelType.setEnabled(true);
		addButton.setEnabled(true);
		removeButton.setEnabled(true);
		generateFile.setEnabled(true);
		isMain.setEnabled(true);
		generateDocumentation.setEnabled(true);
		templateModuleElementKind.setEnabled(true);
		queryModuleElementKind.setEnabled(true);

		IAcceleoInitializationStrategy strategy = getInitializationStrategy();
		if (strategy != null) {
			metamodelTable.setEnabled(!strategy.forceMetamodelURI());
			metamodelType.setEnabled(!strategy.forceMetamodelType());
			addButton.setEnabled(!strategy.forceMetamodelURI());
			removeButton.setEnabled(!strategy.forceMetamodelURI());
			generateFile.setEnabled(!strategy.forceHasFile());
			isMain.setEnabled(!strategy.forceHasMain());
			generateDocumentation.setEnabled(!strategy.forceDocumentation());
			if (strategy.forceQuery() && !strategy.forceTemplate()) {
				templateModuleElementKind.setSelection(false);
				queryModuleElementKind.setSelection(true);
				templateModuleElementKind.setEnabled(false);
			} else if (strategy.forceTemplate() && !strategy.forceQuery()) {
				templateModuleElementKind.setSelection(true);
				queryModuleElementKind.setSelection(false);
				queryModuleElementKind.setEnabled(false);
			}
		}
	}

	/**
	 * Gets the selected initialization strategy. It will be used to initialize automatically the module
	 * element content from the example. The default behavior is to copy the content of the example in a
	 * simple template, but it can be more powerful.
	 * 
	 * @return the selected initialization strategy, or null if selection is empty
	 */
	public IAcceleoInitializationStrategy getInitializationStrategy() {
		if (initializeContent.getSelection()) {
			if (initializeContentKind != null
					&& initializeContentKind.getSelectionIndex() > -1
					&& initializeContentKind.getSelectionIndex() < AcceleoInitializationStrategyUtils
							.getInitializationStrategy().size()) {
				return AcceleoInitializationStrategyUtils.getInitializationStrategy().get(
						initializeContentKind.getSelectionIndex());
			}
		}
		return null;
	}

	/**
	 * Refreshes the available example strategies in the current Eclipse instance.
	 */
	private void updateStrategies() {
		if (initializeContentKind != null) {
			List<String> descriptions = new ArrayList<String>();
			for (Iterator<IAcceleoInitializationStrategy> iterator = AcceleoInitializationStrategyUtils
					.getInitializationStrategy().iterator(); iterator.hasNext();) {
				descriptions.add(iterator.next().getDescription());
			}
			initializeContentKind.setItems(descriptions.toArray(new String[descriptions.size()]));
			final int visibleItemCount = 15;
			if (descriptions.size() < visibleItemCount) {
				initializeContentKind.setVisibleItemCount(descriptions.size());
			} else {
				initializeContentKind.setVisibleItemCount(visibleItemCount);
			}
		}
	}

	/**
	 * Returns the Acceleo module initialized.
	 * 
	 * @return the Acceleo module initialized.
	 */
	public AcceleoModule getAcceleoModule() {
		return this.acceleoModule;
	}

	/**
	 * This will be used to allow the user to browse the workspace for examples.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	class ExampleBrowseSelectionAdapter extends SelectionAdapter {
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
				IAcceleoInitializationStrategy strategy = getInitializationStrategy();
				if (strategy != null) {
					dialog.setInitialPattern(strategy.getInitialFileNameFilter());
				} else {
					dialog.setInitialPattern("*.java"); //$NON-NLS-1$
				}
			}
			dialog.open();
			if (dialog.getResult() != null && dialog.getResult().length > 0
					&& dialog.getResult()[0] instanceof IFile) {
				initializeContentFile.setText(((IFile)dialog.getResult()[0]).getFullPath().toString());
				moduleElementName.setText(((IFile)dialog.getResult()[0]).getFullPath().removeFileExtension()
						.lastSegment().toLowerCase().replace('-', '_'));
			}
			updateModule();
		}
	}

}
