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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * The 'FileBlocks' selection page is used to initialize the main template content. The new template will call
 * the selected elements. We can display the templates of the current project, or the templates available in
 * the whole eclipse instance.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class FileBlocksWizardPage extends WizardPage implements IEditingDomainProvider {

	/**
	 * The editing domain.
	 */
	private AdapterFactoryEditingDomain editingDomain;

	/**
	 * The adapter factory.
	 */
	private ComposedAdapterFactory adapterFactory;

	/**
	 * The Templates part.
	 */
	private CheckboxTreeViewer templatesViewer;

	/**
	 * The resource sets to unload, when we close the view.
	 */
	private Set<ResourceSet> toUnload = new CompactHashSet<ResourceSet>();

	/**
	 * The active project.
	 */
	private IProject project;

	/**
	 * Indicates if we browse the templates of the whole eclipse instance.
	 */
	private boolean browseNotAccessibleTemplates;

	/**
	 * Constructor.
	 * 
	 * @param project
	 *            is the project
	 * @param browseNotAccessibleTemplates
	 *            indicates if we display the templates of the whole eclipse instance
	 */
	public FileBlocksWizardPage(IProject project, boolean browseNotAccessibleTemplates) {
		super(AcceleoUIMessages.getString("FileBlocksWizardPage.Name")); //$NON-NLS-1$
		setTitle(AcceleoUIMessages.getString("FileBlocksWizardPage.Title1")); //$NON-NLS-1$
		setDescription(AcceleoUIMessages.getString("FileBlocksWizardPage.Description")); //$NON-NLS-1$
		setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$
		this.project = project;
		this.browseNotAccessibleTemplates = browseNotAccessibleTemplates;
		List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new EcoreItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		adapterFactory = new ComposedAdapterFactory(factories);
		BasicCommandStack commandStack = new BasicCommandStack();
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack,
				new HashMap<Resource, Boolean>());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite rootContainer = new Composite(parent, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalIndent = 1;
		rootContainer.setLayoutData(gridData);
		GridLayout rootContainerLayout = new GridLayout();
		rootContainer.setLayout(rootContainerLayout);
		createFileTemplatesViewer(rootContainer);
		setControl(rootContainer);
		IWorkspaceRunnable create = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				update(monitor);
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(create, null);
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
					.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		}
	}

	/**
	 * Creates the widget for the file block templates part in the given composite.
	 * 
	 * @param parent
	 *            is the parent composite
	 */
	private void createFileTemplatesViewer(Composite parent) {
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 100;
		templatesViewer = new CheckboxTreeViewer(parent, SWT.BORDER);
		templatesViewer.getTree().setLayoutData(data);
		templatesViewer.getTree().setFont(parent.getFont());
		templatesViewer.setContentProvider(new FileBlocksContentProvider(adapterFactory));
		templatesViewer.setLabelProvider(new FileBlocksLabelProvider(adapterFactory));
		templatesViewer.addCheckStateListener(new ICheckStateListener() {
			private boolean active;

			public void checkStateChanged(CheckStateChangedEvent event) {
				if (!active) {
					active = true;
					try {
						if (event.getElement() instanceof FileBlocksProjectHandler) {
							FileBlocksProjectHandler handler = (FileBlocksProjectHandler)event.getElement();
							templatesViewer.expandToLevel(handler, 1);
							for (Module eModule : handler.getModules()) {
								templatesViewer.expandToLevel(eModule, 1);
								templatesViewer.setChecked(eModule, event.getChecked());
								checkStateTemplate(eModule, event.getChecked());
							}
						} else if (event.getElement() instanceof Module) {
							Module eModule = (Module)event.getElement();
							checkStateTemplate(eModule, event.getChecked());
						}
					} finally {
						active = false;
					}
				}
			}
		});
	}

	/**
	 * Updates the templates information by getting the settings of the current project.
	 * 
	 * @param monitor
	 *            is the current monitor
	 */
	private void update(IProgressMonitor monitor) {
		if (project != null) {
			Set<ResourceSet> newResourceSet = new CompactHashSet<ResourceSet>();
			AcceleoProject acceleoProject = new AcceleoProject(project);
			final List<FileBlocksProjectHandler> projects = new ArrayList<FileBlocksProjectHandler>();
			ResourceSet resourceSet = acceleoProject.loadAccessibleOutputFiles(monitor);
			newResourceSet.add(resourceSet);
			computeFileBlocksProjectHandlers(resourceSet, true, projects);
			if (!monitor.isCanceled()) {
				if (browseNotAccessibleTemplates) {
					resourceSet = acceleoProject.loadNotAccessibleOutputFiles(monitor);
					newResourceSet.add(resourceSet);
					computeFileBlocksProjectHandlers(resourceSet, false, projects);
				}
			}
			if (!monitor.isCanceled()) {
				update(projects);
				if (toUnload.size() > 0) {
					for (ResourceSet resourceSetToUnload : toUnload) {
						unloadResourceSet(resourceSetToUnload);
					}
					toUnload.clear();
				}
				toUnload.addAll(newResourceSet);
			} else {
				for (ResourceSet resourceSetToUnload : newResourceSet) {
					unloadResourceSet(resourceSetToUnload);
				}
			}
		} else {
			final List<FileBlocksProjectHandler> projects = new ArrayList<FileBlocksProjectHandler>();
			ResourceSet resourceSet = AcceleoProject.loadAllPlatformOutputFiles(monitor);
			if (!monitor.isCanceled()) {
				computeFileBlocksProjectHandlers(resourceSet, false, projects);
				update(projects);
				if (toUnload.size() > 0) {
					for (ResourceSet resourceSetToUnload : toUnload) {
						unloadResourceSet(resourceSetToUnload);
					}
					toUnload.clear();
				}
				toUnload.add(resourceSet);
			} else {
				unloadResourceSet(resourceSet);
			}
		}
	}

	/**
	 * Updates the templates information with the Acceleo projects and the Acceleo plugins.
	 * 
	 * @param projects
	 *            are the project handlers used to show a workspace project or a bundle project in the view
	 */
	private void update(final List<FileBlocksProjectHandler> projects) {
		Object[] checkedElements = templatesViewer.getCheckedElements();
		templatesViewer.setInput(projects.toArray());
		for (Object checkedElement : checkedElements) {
			if (checkedElement instanceof EObject && ((EObject)checkedElement).eResource() != null) {
				EObject eObject = (EObject)checkedElement;
				URI fileURI = eObject.eResource().getURI();
				if (fileURI != null) {
					String eObjectFragmentURI = eObject.eResource().getURIFragment(eObject);
					EObject newEObject = expandFragment(fileURI, eObjectFragmentURI);
					templatesViewer.setChecked(newEObject, true);
				}
			}
		}
	}

	/**
	 * Clears the resources of the given resource set.
	 * 
	 * @param resourceSetToUnload
	 *            is a set of resources to unload
	 */
	private void unloadResourceSet(ResourceSet resourceSetToUnload) {
		for (Resource resource : resourceSetToUnload.getResources()) {
			if (resource.isLoaded()) {
				resource.unload();
			}
		}
	}

	/**
	 * Creates the projects that contain the EMTL files.
	 * 
	 * @param emtlResourceSet
	 *            is the resource set that contains the EMTL files resources.
	 * @param isResolved
	 *            indicates if the projects are resolved or not in the current classpath
	 * @param projects
	 *            are the projects to create, it is an input/output parameter
	 */
	private void computeFileBlocksProjectHandlers(ResourceSet emtlResourceSet, boolean isResolved,
			List<FileBlocksProjectHandler> projects) {
		if (emtlResourceSet != null) {
			Map<String, List<Module>> project2emtl = new HashMap<String, List<Module>>();
			for (Resource emtlResource : emtlResourceSet.getResources()) {
				if (emtlResource.getContents().size() > 0
						&& emtlResource.getContents().get(0) instanceof Module) {
					Module eModule = (Module)emtlResource.getContents().get(0);
					String projectName = getModuleProjectName(eModule);
					List<Module> toFill = project2emtl.get(projectName);
					if (toFill == null) {
						toFill = new ArrayList<Module>();
						project2emtl.put(projectName, toFill);
					}
					toFill.add(eModule);
				}
			}
			for (Map.Entry<String, List<Module>> entry : project2emtl.entrySet()) {
				String projectName = entry.getKey();
				List<Module> eModules = entry.getValue();
				FileBlocksProjectHandler projectHanlder = new FileBlocksProjectHandler(projectName, eModules
						.toArray(new Module[eModules.size()]), isResolved);
				if (project != null && projectName.equals(project.getName())) {
					projects.add(0, projectHanlder);
				} else {
					projects.add(projectHanlder);
				}
			}
		}
	}

	/**
	 * Gets the module project name for the given root element of an EMTL file.
	 * 
	 * @param eModule
	 *            the root element of the EMTL file
	 * @return the module project name, it means the first significant segment of the corresponding URI :
	 *         eModule.eResource().getURI()
	 */
	private String getModuleProjectName(Module eModule) {
		String result;
		Resource resource = eModule.eResource();
		if (resource != null) {
			URI uri = resource.getURI();
			String path = uri.toString();
			String prefix = "platform:/resource/"; //$NON-NLS-1$
			if (path.startsWith(prefix)) {
				path = path.substring(prefix.length());
			} else {
				prefix = "platform:/plugin/"; //$NON-NLS-1$
				if (path.startsWith(prefix)) {
					path = path.substring(prefix.length());
				}
			}
			IPath relativePath = new Path(path);
			if (relativePath.segmentCount() > 1) {
				if (uri.isPlatformPlugin()) {
					result = relativePath.segment(0) + " [plugin]"; //$NON-NLS-1$
				} else {
					result = relativePath.segment(0);
				}
			} else {
				result = "[others]"; //$NON-NLS-1$
			}
		} else {
			result = "[others]"; //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		if (toUnload.size() > 0) {
			for (ResourceSet resourceSet : toUnload) {
				unloadResourceSet(resourceSet);
			}
			toUnload.clear();
		}
		super.dispose();
	}

	/**
	 * Try to expand the given element in the tree view.
	 * 
	 * @param eObjectFileURI
	 *            file URI of the element that is to be expanded
	 * @param eObjectFragmentURI
	 *            fragment URI of the element that is to be expanded
	 * @return the expanded EObject
	 */
	private EObject expandFragment(URI eObjectFileURI, String eObjectFragmentURI) {
		if (eObjectFileURI != null && eObjectFragmentURI != null
				&& templatesViewer.getInput() instanceof Object[]) {
			Object[] inputs = (Object[])templatesViewer.getInput();
			for (Object input : inputs) {
				if (input instanceof FileBlocksProjectHandler) {
					FileBlocksProjectHandler projectHandler = (FileBlocksProjectHandler)input;
					for (Module eModule : projectHandler.getModules()) {
						EObject newEObject;
						if (eModule.eResource() != null
								&& eModule.eResource().getURI().equals(eObjectFileURI)) {
							newEObject = eModule.eResource().getEObject(eObjectFragmentURI);
						} else {
							newEObject = null;
						}
						if (newEObject instanceof ModuleElement) {
							templatesViewer.setExpandedState(projectHandler, true);
							templatesViewer.setExpandedState(newEObject.eContainer(), true);
							templatesViewer.setSelection(new StructuredSelection(newEObject), true);
						} else if (newEObject instanceof Module) {
							templatesViewer.setExpandedState(projectHandler, true);
							templatesViewer.setSelection(new StructuredSelection(newEObject), true);
						}
						if (newEObject != null) {
							return newEObject;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Sets the checked state of the given module and its module elements.
	 * 
	 * @param eModule
	 *            is the module to check
	 * @param state
	 *            is the checked state
	 */
	private synchronized void checkStateTemplate(Module eModule, boolean state) {
		for (ModuleElement eModuleElement : eModule.getOwnedModuleElement()) {
			templatesViewer.expandToLevel(eModuleElement, 1);
			templatesViewer.setChecked(eModuleElement, state);
		}
	}

	/**
	 * Gets the AST nodes of the selected templates.
	 * 
	 * @return the templates, or an empty array
	 */
	public Template[] getSelectedTemplates() {
		List<Template> result = new ArrayList<Template>();
		Object[] templateCheckedElements = templatesViewer.getCheckedElements();
		if (templateCheckedElements.length > 0) {
			for (int i = 0; i < templateCheckedElements.length; i++) {
				Object templateCheckedElement = templateCheckedElements[i];
				if (templateCheckedElement instanceof Template) {
					result.add((Template)templateCheckedElement);
				}
			}
		}
		return result.toArray(new Template[result.size()]);
	}

}
