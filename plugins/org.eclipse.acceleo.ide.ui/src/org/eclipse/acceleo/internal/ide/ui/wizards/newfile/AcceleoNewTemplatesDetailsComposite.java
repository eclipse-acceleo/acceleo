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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

/**
 * The details composite of the 'AcceleoNewTemplatesWizard' wizard. This composite contains the useful widgets
 * to manage one template settings.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewTemplatesDetailsComposite extends Composite {

	/**
	 * Fire property change event. The template name has been modified.
	 */
	protected static final int TEMPLATE_NAME = 0;

	/**
	 * Fire property change event. The template parent folder has been modified.
	 */
	protected static final int TEMPLATE_PARENT_FOLDER = 1;

	/**
	 * Fire property change event. The template metamodel URI has been modified.
	 */
	protected static final int TEMPLATE_METAMODEL_URI = 2;

	/**
	 * Fire property change event. The template metamodel type has been modified.
	 */
	protected static final int TEMPLATE_METAMODEL_TYPE = 3;

	/**
	 * Fire property change event. The 'file block' check button has been modified.
	 */
	protected static final int TEMPLATE_IS_FILE = 4;

	/**
	 * Fire property change event. The 'has main annotation' check button has been modified.
	 */
	protected static final int TEMPLATE_HAS_MAIN = 5;

	/**
	 * Fire property change event. The example strategy has been modified.
	 */
	protected static final int TEMPLATE_EXAMPLE_STRATEGY = 6;

	/**
	 * Fire property change event. The example file path has been modified.
	 */
	protected static final int TEMPLATE_EXAMPLE_PATH = 7;

	/**
	 * Fire property change event. The initialization status has been modified.
	 */
	protected static final int TEMPLATE_IS_INITIALISED = 8;

	/**
	 * The advance button widget.
	 */
	protected Button advancedButton;

	/**
	 * The containing resource path in the workspace.
	 */
	private Text templateContainer;

	/**
	 * The template name.
	 */
	private Text templateName;

	/**
	 * The template example file path.
	 */
	private Text templateExamplePath;

	/**
	 * Available example strategies in the current Eclipse instance. An internal extension point is defined to
	 * specify multiple example strategies. It is often used to initialize automatically a template file from
	 * an example in the Acceleo project.
	 */
	private Combo templateExampleStrategy;

	/**
	 * The 'has file' state button.
	 */
	private Button templateHasFileButtonState;

	/**
	 * The 'has main' state button.
	 */
	private Button templateHasMainButtonState;

	/**
	 * The metamodel URI text widget.
	 */
	private Text metamodelURI;

	/**
	 * The button to browse the metamodel URIs.
	 */
	private Button metamodelBrowseButton;

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
	 * The workspace selection when the page has been opened.
	 */
	private String selectedContainer;

	/**
	 * The initialization state button.
	 */
	private Button templateIsInitializeButtonState;

	/**
	 * The advanced expandable composite.
	 */
	private Composite exampleContainer;

	/**
	 * The example file label.
	 */
	private Label exampleFileLabel;

	/**
	 * Browse the workspace to find an example. This example is useful to initialize the template.
	 */
	private Button exampleBrowseButton;

	/**
	 * The controller of the page.
	 */
	private AcceleoNewTemplatesWizardController controller;

	/**
	 * Constructor.
	 * 
	 * @param rootContainer
	 *            a widget which will be the parent of the new instance (cannot be null)
	 * @param style
	 *            the style of widget to construct
	 */
	public AcceleoNewTemplatesDetailsComposite(Composite rootContainer, int style) {
		super(rootContainer, style);
		createDetailsComposite();
		updateDefaultTypes();
		updateStrategies();
	}

	/**
	 * Creates the widgets.
	 */
	private void createDetailsComposite() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		this.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		gridData = new GridData(GridData.FILL_BOTH);
		final int heightHint = 420;
		gridData.heightHint = heightHint;
		this.setLayoutData(gridData);
		createTemplateGroup(this);
	}

	/**
	 * Creates a group for the template settings : name, path...
	 * 
	 * @param rootContainer
	 *            is the root control
	 */
	private void createTemplateGroup(Composite rootContainer) {
		final Composite templateGroup = new Composite(rootContainer, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		templateGroup.setLayout(layout);
		templateGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

		createTemplateContainerComposite(templateGroup);
		createTemplateContainerName(templateGroup);
		createMetamodelGroup(templateGroup);

		exampleContainer = null;
		new Label(templateGroup, SWT.NONE);
		advancedButton = new Button(templateGroup, SWT.PUSH);
		advancedButton.setFont(templateGroup.getFont());
		advancedButton.setText(AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage" + ".AdvanceShowedButton")); //$NON-NLS-1$ //$NON-NLS-2$
		GridData data = setButtonLayoutData(advancedButton);
		data.horizontalAlignment = GridData.BEGINNING;
		data.horizontalSpan = 3;
		advancedButton.setLayoutData(data);
		advancedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleAdvancedButtonSelect(templateGroup);
			}
		});
		createAdvancedButtonSelect(templateGroup);
	}

	/**
	 * Sets and returns the layout data of the given button.
	 * 
	 * @param button
	 *            is the button
	 * @return the layout data
	 */
	private GridData setButtonLayoutData(Button button) {
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		button.setLayoutData(data);
		return data;
	}

	/**
	 * Creates the advanced option widgets.
	 * 
	 * @param templateGroup
	 *            is the advanced composite
	 */
	private void createAdvancedButtonSelect(Composite templateGroup) {
		exampleContainer = new Composite(templateGroup, SWT.NONE);
		GridLayout exampleContainerLayout = new GridLayout();
		exampleContainerLayout.numColumns = 1;
		exampleContainer.setLayout(exampleContainerLayout);
		GridData exampleContainerGridData = new GridData(GridData.FILL_HORIZONTAL);
		exampleContainerGridData.horizontalSpan = 3;
		exampleContainer.setLayout(exampleContainerLayout);
		exampleContainer.setLayoutData(exampleContainerGridData);

		createTemplateExample();

		Composite additionalOption = new Composite(exampleContainer, SWT.NULL);
		GridLayout additionalLayout = new GridLayout();
		additionalLayout.numColumns = 1;
		additionalOption.setLayout(additionalLayout);
		createTemplateStatusWidgets(additionalOption);
		templateGroup.layout();
		exampleContainer.setVisible(false);
		advancedButton.setText(AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.AdvanceShowedButton")); //$NON-NLS-1$
	}

	/**
	 * Shows/hides the advanced option widgets.
	 * 
	 * @param templateGroup
	 *            is the advanced composite
	 */
	private void handleAdvancedButtonSelect(Composite templateGroup) {
		if (exampleContainer.isVisible()) {
			exampleContainer.setVisible(false);
			advancedButton.setText(AcceleoUIMessages
					.getString("AcceleoNewTemplateWizardPage.AdvanceShowedButton")); //$NON-NLS-1$
		} else {
			exampleContainer.setVisible(true);
			advancedButton.setText(AcceleoUIMessages
					.getString("AcceleoNewTemplateWizardPage.AdvanceHiddenButton")); //$NON-NLS-1$
		}
	}

	/**
	 * Creates a group for the template example : strategy, file.
	 */
	private void createTemplateExample() {
		Composite initializeTemplate = new Composite(exampleContainer, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		initializeTemplate.setLayoutData(gridData);
		initializeTemplate.setLayout(layout);

		templateIsInitializeButtonState = new Button(initializeTemplate, SWT.CHECK);
		templateIsInitializeButtonState.setText(AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.TemplateOutput")); //$NON-NLS-1$
		gridData = new GridData();
		final int widthHintInitializeButton = 140;
		gridData.widthHint = widthHintInitializeButton;
		templateIsInitializeButtonState.setLayoutData(gridData);

		templateExampleStrategy = new Combo(initializeTemplate, SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		templateExampleStrategy.setLayoutData(gridData);
		templateExampleStrategy.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do here
			}

			public void widgetSelected(SelectionEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_EXAMPLE_STRATEGY);
				changeStrategy();
			}
		});
		updateStrategies();

		Composite initializeFileTemplate = new Composite(exampleContainer, SWT.NULL);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		initializeFileTemplate.setLayoutData(gridData);
		initializeFileTemplate.setLayout(layout);
		exampleFileLabel = new Label(initializeFileTemplate, SWT.NULL);
		exampleFileLabel.setText(AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.TemplateOutputFile") + ':'); //$NON-NLS-1$
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.RIGHT;
		final int widthHintExampleFileLabel = 35;
		gridData.widthHint = widthHintExampleFileLabel;
		exampleFileLabel.setLayoutData(gridData);
		templateExamplePath = new Text(initializeFileTemplate, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		final int widthHintExamplePath = 230;
		gridData.widthHint = widthHintExamplePath;
		templateExamplePath.setLayoutData(gridData);
		templateExamplePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_EXAMPLE_PATH);
			}
		});

		exampleBrowseButton = new Button(initializeFileTemplate, SWT.PUSH);
		exampleBrowseButton.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage" + ".Browse")); //$NON-NLS-1$ //$NON-NLS-2$
		exampleBrowseButton.addSelectionListener(new ExampleBrowseSelectionAdapter());

		initExampleStrategyGroup();
	}

	/**
	 * Initializes the example strategy group.
	 */
	private void initExampleStrategyGroup() {
		templateIsInitializeButtonState.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_IS_INITIALISED);
				setExampleStrategyGroupEnabled(templateIsInitializeButtonState.getSelection());
				changeStrategy();
			}

		});
		templateIsInitializeButtonState.setSelection(false);
		templateExampleStrategy.setEnabled(false);
		exampleFileLabel.setEnabled(false);
		templateExamplePath.setEnabled(false);
		exampleBrowseButton.setEnabled(false);
		templateExampleStrategy.select(0);
	}

	/**
	 * Indicates if the example strategy group should be enabled, or not.
	 * 
	 * @param enabled
	 *            indicates if the example strategy group should be enabled
	 */
	private void setExampleStrategyGroupEnabled(boolean enabled) {
		templateExampleStrategy.setEnabled(enabled);
		exampleFileLabel.setEnabled(enabled);
		templateExamplePath.setEnabled(enabled);
		exampleBrowseButton.setEnabled(enabled);
	}

	/**
	 * Gets the selected example strategy. It will be used to initialize automatically the template content
	 * from the example. The default behavior is to copy the content of the example in a simple template, but
	 * the automation can be more powerfull.
	 * 
	 * @return the selected example strategy, or null if selection is empty
	 */
	public IAcceleoExampleStrategy getTemplateExampleStrategy() {
		return null;
	}

	/**
	 * Create the widgets for the template container name.
	 * 
	 * @param templateGroup
	 *            is the parent composite
	 */
	private void createTemplateContainerName(Composite templateGroup) {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		Label nameLabel = new Label(templateGroup, SWT.NULL);
		nameLabel.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.TemplateName") + ':'); //$NON-NLS-1$
		templateName = new Text(templateGroup, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		final int widthHint = 270;
		gridData.widthHint = widthHint;
		gridData.horizontalSpan = 2;
		templateName.setLayoutData(gridData);
		templateName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_NAME);
			}
		});
		templateName.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				controller.templateNameManualChange = true;
			}
		});
	}

	/**
	 * Creates a composite for the template container selection.
	 * 
	 * @param templateGroup
	 *            is the template group
	 */
	private void createTemplateContainerComposite(Composite templateGroup) {
		Label label = new Label(templateGroup, SWT.NULL);
		label.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.TemplateContainer") + ':'); //$NON-NLS-1$

		templateContainer = new Text(templateGroup, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		templateContainer.setLayoutData(gd);
		templateContainer.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_PARENT_FOLDER);

			}
		});
		Button button = new Button(templateGroup, SWT.PUSH);
		button.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Browse")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowseWorkspace();
			}
		});
	}

	/**
	 * Creates the template status widgets. It means the "has file" and the "has main" check boxes.
	 * 
	 * @param statusContainer
	 *            is the template group
	 */
	private void createTemplateStatusWidgets(Composite statusContainer) {
		templateHasFileButtonState = new Button(statusContainer, SWT.CHECK);
		templateHasFileButtonState.setText(AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.AcceleoHasFileBlock")); //$NON-NLS-1$
		templateHasFileButtonState.setSelection(true);
		templateHasFileButtonState.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_IS_FILE);
			}
		});
		templateHasMainButtonState = new Button(statusContainer, SWT.CHECK);
		templateHasMainButtonState.setText(AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.AcceleoHasMain")); //$NON-NLS-1$
		templateHasMainButtonState.setSelection(true);
		templateHasMainButtonState.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_HAS_MAIN);
			}
		});
		if (controller != null) {
			controller.dialogChanged();
		}
	}

	/**
	 * Creates a group for the metamodel settings : URI, main type...
	 * 
	 * @param rootContainer
	 *            is the root control
	 */
	private void createMetamodelGroup(Composite rootContainer) {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		Label uriLabel = new Label(rootContainer, SWT.NULL);
		uriLabel.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.MetamodelURI") + ':'); //$NON-NLS-1$
		metamodelURI = new Text(rootContainer, SWT.BORDER | SWT.SINGLE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		metamodelURI.setLayoutData(gridData);
		metamodelURI.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				controller.firePropertiesChanged(e, TEMPLATE_METAMODEL_URI);
				controller.dialogChanged();
				updateTypes();
			}
		});
		metamodelBrowseButton = new Button(rootContainer, SWT.PUSH);
		metamodelBrowseButton.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Browse")); //$NON-NLS-1$
		metamodelBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleSelectMetamodelURI();
			}
		});

		Label typeLabel = new Label(rootContainer, SWT.NULL);
		typeLabel.setText(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.TemplateType") + ':'); //$NON-NLS-1$
		metamodelType = new Combo(rootContainer, SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		metamodelType.setLayoutData(gridData);
		metamodelType.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do here
			}

			public void widgetSelected(SelectionEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_METAMODEL_TYPE);
			}
		});
		metamodelType.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				controller.dialogChanged();
				controller.firePropertiesChanged(e, TEMPLATE_METAMODEL_TYPE);
			}
		});
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
			if (registeredPackageDialog.isDevelopmentTimeVersion()) {
				StringBuffer uris = new StringBuffer();
				try {
					ResourceSet resourceSet = new ResourceSetImpl();
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
				String textURI = uris.toString();
				if (textURI.endsWith(",")) { //$NON-NLS-1$
					textURI = textURI.substring(0, textURI.length() - 1);
				}
				metamodelURI.setText(textURI.trim());
			} else {
				StringBuffer uris = new StringBuffer();
				for (int i = 0; i < result.length; i++) {
					uris.append(result[i]);
					if (i + 1 < result.length) {
						uris.append(',');
					}
				}
				metamodelURI.setText(uris.toString().trim());
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
	 * Uses the standard container selection dialog to choose the new value for the template container field.
	 */
	private void handleBrowseWorkspace() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin
				.getWorkspace().getRoot(), false, AcceleoUIMessages
				.getString("AcceleoNewTemplateWizardPage.ContainerSelection")); //$NON-NLS-1$
		IResource current = null;
		IPath path = new Path(getTemplateContainer());
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
				templateContainer.setText(((Path)result[0]).toString());
			}
		}
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	public void initializeContainer() {
		if (selectedContainer != null) {
			templateContainer.setText(selectedContainer.toString());
		}
	}

	/**
	 * Refreshes the available example strategies in the current Eclipse instance.
	 */
	private void updateStrategies() {
	}

	/**
	 * Updates the metamodel types.
	 */
	private void updateTypes() {
		if (metamodelType != null) {
			String oldSelection = metamodelType.getText();
			List<String> typeValues = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(getMetamodelURI(), ","); //$NON-NLS-1$
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

			Collections.sort(typeValues);
			metamodelTypes = typeValues.toArray(new String[typeValues.size()]);
			metamodelType.setItems(metamodelTypes);
			final int visibleItemCount = 15;
			if (metamodelTypes.length < visibleItemCount) {
				metamodelType.setVisibleItemCount(metamodelTypes.length);
			} else {
				metamodelType.setVisibleItemCount(visibleItemCount);
			}
			metamodelType.setText(oldSelection);
			if (metamodelType.getText().length() == 0) {
				updateDefaultTypes();
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
	}

	/**
	 * Gets the path of the container (workspace relative path).
	 * 
	 * @return the path of the container
	 */
	public String getTemplateContainer() {
		return templateContainer.getText();
	}

	/**
	 * Gets the template name (without extension).
	 * 
	 * @return the template name
	 */
	public String getTemplateName() {
		return templateName.getText();
	}

	/**
	 * Gets the path of the example file (workspace relative path).
	 * 
	 * @return the path of the example file
	 */
	public String getTemplateExample() {
		return templateExamplePath.getText();
	}

	/**
	 * Indicates if the template must have a file block.
	 * 
	 * @return true if the template must have a file block
	 */
	public boolean getTemplateHasFileBlock() {
		return templateHasFileButtonState.getSelection();
	}

	/**
	 * Indicates if the template is a main entry point. If true, it must have a main annotation (@main).
	 * 
	 * @return true if the template must have a main annotation
	 */
	public boolean getTemplateIsMain() {
		return templateHasMainButtonState.getSelection();
	}

	/**
	 * Returns the metamodel URI. It cans return multiple metamodels by using a comma separator.
	 * 
	 * @return the metamodel URI
	 */
	public String getMetamodelURI() {
		return metamodelURI.getText();
	}

	/**
	 * Returns the text field used to prompt users for the metamodel on which to create the new module.
	 * 
	 * @return The text field used to prompt users for the metamodel on which to create the new module.
	 */
	public Text getMetamodelField() {
		return metamodelURI;
	}

	/**
	 * Returns the main metamodel type.
	 * 
	 * @return the main metamodel type
	 */
	public String getMetamodelType() {
		if (metamodelType != null && metamodelTypes != null && metamodelType.getSelectionIndex() > -1
				&& metamodelType.getSelectionIndex() < metamodelTypes.length) {
			return metamodelTypes[metamodelType.getSelectionIndex()];
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Get the selected container. It means the selection when the page has been opened.
	 * 
	 * @return the selected container
	 */
	public String getContainer() {
		return this.selectedContainer;
	}

	/**
	 * Sets the selected container. It means the selection when the page has been opened.
	 * 
	 * @param container
	 *            the selected container
	 */
	public void setContainer(String container) {
		this.selectedContainer = container;
		setTemplateContainer(container);
	}

	/**
	 * Sets the controller which manages the detail composite view and its model.
	 * 
	 * @param controller
	 *            is the controller
	 */
	public void setController(AcceleoNewTemplatesWizardController controller) {
		this.controller = controller;
	}

	/**
	 * Sets the 'has file' selection state.
	 * 
	 * @param templateHasFileButtonState
	 *            the 'has file' selection state
	 */
	public void setTemplateHasFileButtonState(boolean templateHasFileButtonState) {
		this.templateHasFileButtonState.setSelection(templateHasFileButtonState);
	}

	/**
	 * Sets the 'has main annotation' selection state.
	 * 
	 * @param templateHasMainButtonState
	 *            the 'has main annotation' selection state
	 */
	public void setTemplateHasMainButtonState(boolean templateHasMainButtonState) {
		this.templateHasMainButtonState.setSelection(templateHasMainButtonState);
	}

	/**
	 * Sets the 'initialization' selection state.
	 * 
	 * @param templateIsInitializeButtonState
	 *            the 'initialization' selection state
	 */
	public void setTemplateIsInitializeButtonState(boolean templateIsInitializeButtonState) {
		this.templateIsInitializeButtonState.setSelection(templateIsInitializeButtonState);
		setExampleStrategyGroupEnabled(templateIsInitializeButtonState);
	}

	/**
	 * Sets the containing resource path in the workspace.
	 * 
	 * @param templateContainer
	 *            the containing resource path
	 */
	public void setTemplateContainer(String templateContainer) {
		this.templateContainer.setText(templateContainer);
	}

	/**
	 * Sets the template name.
	 * 
	 * @param templateName
	 *            is the template name
	 */
	public void setTemplateName(String templateName) {
		this.templateName.setText(templateName);
	}

	/**
	 * Sets the template metamodel URI.
	 * 
	 * @param metamodelURI
	 *            is the template metamodel URI
	 */
	public void setMetamodelURI(String metamodelURI) {
		this.metamodelURI.setText(metamodelURI);
	}

	/**
	 * Sets the metamodel type.
	 * 
	 * @param metamodelType
	 *            is the label of the metamodel type to select in the types combo box
	 */
	public void setMetamodelType(String metamodelType) {
		if (metamodelType != null && metamodelType.length() > 0) {
			List<String> asList = Arrays.asList(this.metamodelType.getItems());
			if (asList.contains(metamodelType)) {
				this.metamodelType.select(asList.indexOf(metamodelType));
			}
		}
	}

	/**
	 * Sets the template example file path.
	 * 
	 * @param templateExamplePath
	 *            the template example file path
	 */
	public void setTemplateExamplePath(String templateExamplePath) {
		this.templateExamplePath.setText(templateExamplePath);
	}

	/**
	 * Sets the template example strategy name. It defines the way to initialize the template.
	 * 
	 * @param templateExampleStrategy
	 *            the template example strategy name
	 */
	public void setTemplateExampleStrategy(String templateExampleStrategy) {
		if (templateExampleStrategy != null && templateExampleStrategy.length() > 0) {
			List<String> asList = Arrays.asList(this.templateExampleStrategy.getItems());
			if (asList.contains(templateExampleStrategy)) {
				this.templateExampleStrategy.select(asList.indexOf(templateExampleStrategy));
			}
		} else {
			this.templateExampleStrategy.select(0);
		}
	}

	/**
	 * Reads the current example strategy and updates the state of the widgets for this strategy.
	 */
	private void changeStrategy() {
		IAcceleoExampleStrategy strategy = getTemplateExampleStrategy();
		if (strategy != null) {
			metamodelURI.setEnabled(!strategy.forceMetamodelURI());
			metamodelType.setEnabled(!strategy.forceMetamodelType());
			metamodelBrowseButton.setEnabled(!strategy.forceMetamodelURI());
			templateHasFileButtonState.setEnabled(!strategy.forceHasFile());
			templateHasMainButtonState.setEnabled(!strategy.forceHasMain());
		} else {
			metamodelURI.setEnabled(true);
			metamodelType.setEnabled(true);
			metamodelBrowseButton.setEnabled(true);
			templateHasFileButtonState.setEnabled(true);
			templateHasMainButtonState.setEnabled(true);
		}
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
			dialog.setTitle(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.TemplateExample")); //$NON-NLS-1$
			String path = templateExamplePath.getText();
			if (path != null && path.length() > 0 && new Path(path).lastSegment().length() > 0) {
				dialog.setInitialPattern("*." + new Path(path).getFileExtension()); //$NON-NLS-1$
			} else {
				IAcceleoExampleStrategy strategy = getTemplateExampleStrategy();
				if (strategy != null) {
					dialog.setInitialPattern(strategy.getInitialFileNameFilter());
				} else {
					dialog.setInitialPattern("*.java"); //$NON-NLS-1$
				}
			}
			dialog.open();
			if (dialog.getResult() != null && dialog.getResult().length > 0
					&& dialog.getResult()[0] instanceof IFile) {
				templateExamplePath.setText(((IFile)dialog.getResult()[0]).getFullPath().toString());
				if (!controller.templateNameManualChange) {
					templateName.setText(((IFile)dialog.getResult()[0]).getFullPath().removeFileExtension()
							.lastSegment().toLowerCase().replace('-', '_'));
				}
			}
		}
	}
}
