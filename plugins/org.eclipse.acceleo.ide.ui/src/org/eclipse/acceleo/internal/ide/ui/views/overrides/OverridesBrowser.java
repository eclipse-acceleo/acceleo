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
package org.eclipse.acceleo.internal.ide.ui.views.overrides;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionTemplateProposal;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * A view to update the settings of the current 'overrides' completion proposal. It displays the Templates
 * available in the current Eclipse instance. The templates not in the classpath of the current template are
 * marked, so it listens when an Acceleo Editor bring to top.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class OverridesBrowser extends ViewPart implements IEditingDomainProvider {

	/**
	 * The identifier of the view.
	 */
	public static final String OVERRIDES_BROWSER_VIEW_ID = "org.eclipse.acceleo.ide.ui.views.overrides.OverridesBrowser"; //$NON-NLS-1$

	/**
	 * The active project.
	 */
	IProject project;

	/**
	 * Indicates if we have to clean the view.
	 */
	boolean clean = true;

	/**
	 * The Templates part.
	 */
	CheckboxTreeViewer templatesViewer;

	/**
	 * The editing domain.
	 */
	private AdapterFactoryEditingDomain editingDomain;

	/**
	 * The adapter factory.
	 */
	private ComposedAdapterFactory adapterFactory;

	/**
	 * Interface for listening to workbench part lifecycle events. It is used to listen when an Acceleo Editor
	 * brought to top.
	 */
	private IPartListener partListener;

	/**
	 * The resource sets to unload, when we close the view...
	 */
	private Set<ResourceSet> toUnload = new CompactHashSet<ResourceSet>();

	/**
	 * The resource change listener to detect that the view must be refreshed.
	 */
	private IResourceChangeListener resourceChangeListener;

	/**
	 * The job to refresh the content of the view.
	 */
	private Job refreshContent;

	/**
	 * Constructor.
	 */
	public OverridesBrowser() {
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
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		IEditorPart part = getSite().getPage().getActiveEditor();
		if (part instanceof AcceleoEditor) {
			AcceleoEditor editor = (AcceleoEditor)part;
			refreshContent(editor.getFile());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite rootContainer = new Composite(parent, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalIndent = 1;
		rootContainer.setLayoutData(gridData);
		GridLayout rootContainerLayout = new GridLayout();
		rootContainer.setLayout(rootContainerLayout);
		createTemplatesViewer(rootContainer);
		if (getSite() != null && getSite().getPage() != null && partListener == null) {
			partListener = createPartListener();
			getSite().getPage().addPartListener(partListener);
		}
		if (resourceChangeListener == null) {
			resourceChangeListener = new IResourceChangeListener() {
				public void resourceChanged(IResourceChangeEvent event) {
					try {
						IResourceDelta delta = event.getDelta();
						IFile aFile = getFileInDelta(delta);
						if (aFile != null) {
							clean = true;
							project = null;
							refreshContent(aFile);
						}
					} catch (CoreException e) {
						AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
					}

				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
		}
	}

	/**
	 * Gets the last significant file that has been modified. It means an Acceleo template file or the
	 * MANIFEST.MF file of the current project.
	 * 
	 * @param delta
	 *            the resource delta represents changes in the state of a resource tree
	 * @return the modified Acceleo template file or the MANIFEST.MF file
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	IFile getFileInDelta(IResourceDelta delta) throws CoreException {
		IFile result = null;
		if (delta != null) {
			IResource resource = delta.getResource();
			if (resource instanceof IFile) {
				if (IAcceleoConstants.MTL_FILE_EXTENSION.equals(resource.getFileExtension())
						|| "MANIFEST.MF".equals(resource.getName())) { //$NON-NLS-1$
					result = (IFile)resource;
				}
			} else {
				IResourceDelta[] children = delta.getAffectedChildren();
				for (int i = 0; result == null && i < children.length; i++) {
					result = getFileInDelta(children[i]);
				}
			}
		}
		return result;
	}

	/**
	 * It creates the object that will listen when an Acceleo Editor bring to top.
	 * 
	 * @return the listener
	 */
	// CHECKSTYLE:OFF
	private IPartListener createPartListener() {
		return new IPartListener() {
			public void partOpened(IWorkbenchPart part) {
				if (part instanceof AcceleoEditor) {
					AcceleoEditor editor = (AcceleoEditor)part;
					refreshContent(editor.getFile());
				}
			}

			public void partDeactivated(IWorkbenchPart part) {
			}

			public void partClosed(IWorkbenchPart part) {
			}

			public void partActivated(IWorkbenchPart part) {
				if (part == OverridesBrowser.this) {
					initializeTemplatesViewerContent();
				}
			}

			public void partBroughtToTop(IWorkbenchPart part) {
				if (part instanceof AcceleoEditor) {
					AcceleoEditor editor = (AcceleoEditor)part;
					refreshContent(editor.getFile());
				}
			}
		};
	}

	// CHECKSTYLE:ON

	/**
	 * It refreshes the view by considering that the given file is the main file. The corresponding project
	 * will be the first one displayed in the view.
	 * 
	 * @param aFile
	 *            is the current template
	 */
	void refreshContent(IFile aFile) {
		IProject aProject;
		if (aFile != null) {
			aProject = aFile.getProject();
		} else {
			aProject = null;
		}
		if (!clean) {
			if (aProject == null || aProject == project) {
				return;
			}
		}
		clean = false;
		project = aProject;
		if (refreshContent != null) {
			refreshContent.cancel();
		}
		refreshContent = new Job("Acceleo") { //$NON-NLS-1$
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				updateViewTemplates(monitor);
				return new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, "OK"); //$NON-NLS-1$
			}
		};
		refreshContent.setPriority(Job.DECORATE);
		refreshContent.setSystem(true);
		final int schedule = 1000;
		refreshContent.schedule(schedule);
	}

	/**
	 * Updates the templates in the view by getting the settings of the current project.
	 * 
	 * @param monitor
	 *            is the current monitor
	 */
	synchronized void updateViewTemplates(IProgressMonitor monitor) {
		if (project != null) {
			Set<ResourceSet> newResourceSet = new CompactHashSet<ResourceSet>();
			AcceleoProject acceleoProject = new AcceleoProject(project);
			final List<ModuleProjectHandler> projects = new ArrayList<ModuleProjectHandler>();
			ResourceSet resourceSet = acceleoProject.loadAccessibleOutputFiles(monitor);
			computeModuleProjectHandlers(resourceSet, true, projects);
			newResourceSet.add(resourceSet);
			if (!monitor.isCanceled()) {
				resourceSet = acceleoProject.loadNotAccessibleOutputFiles(monitor);
				newResourceSet.add(resourceSet);
			}
			if (!monitor.isCanceled()) {
				computeModuleProjectHandlers(resourceSet, false, projects);
				asyncUpdateViewTemplates(projects);
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
			final List<ModuleProjectHandler> projects = new ArrayList<ModuleProjectHandler>();
			ResourceSet resourceSet = AcceleoProject.loadAllPlatformOutputFiles(monitor);
			if (!monitor.isCanceled()) {
				computeModuleProjectHandlers(resourceSet, false, projects);
				asyncUpdateViewTemplates(projects);
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
	 * Updates the Overrides view with the Acceleo projects and the Acceleo plugins.
	 * 
	 * @param projects
	 *            are the project handlers used to show a workspace project or a bundle project in the view
	 */
	private void asyncUpdateViewTemplates(final List<ModuleProjectHandler> projects) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (templatesViewer != null && templatesViewer.getTree() != null
						&& !templatesViewer.getTree().isDisposed()) {
					Object[] checkedElements = templatesViewer.getCheckedElements();
					templatesViewer.setInput(projects.toArray());
					for (Object checkedElement : checkedElements) {
						if (checkedElement instanceof EObject
								&& ((EObject)checkedElement).eResource() != null) {
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
			}
		});
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
	 *            indicates if the projects to create are resolved or not in the current classpath
	 * @param projects
	 *            are the projects to create, it is an input/output parameter
	 */
	private void computeModuleProjectHandlers(ResourceSet emtlResourceSet, boolean isResolved,
			List<ModuleProjectHandler> projects) {
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
				ModuleProjectHandler projectHanlder = new ModuleProjectHandler(projectName, eModules
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
		super.dispose();
		if (getSite() != null && getSite().getPage() != null && partListener != null) {
			getSite().getPage().removePartListener(partListener);
			partListener = null;
		}
		if (resourceChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
			resourceChangeListener = null;
		}
		if (toUnload.size() > 0) {
			for (ResourceSet resourceSet : toUnload) {
				unloadResourceSet(resourceSet);
			}
			toUnload.clear();
		}
	}

	/**
	 * Creates the widget for the Templates part in the given composite.
	 * 
	 * @param parent
	 *            is the parent composite
	 */
	private void createTemplatesViewer(Composite parent) {
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 100;
		templatesViewer = new CheckboxTreeViewer(parent, SWT.BORDER);
		templatesViewer.getTree().setLayoutData(data);
		templatesViewer.getTree().setFont(parent.getFont());
		templatesViewer.setContentProvider(new OverridesBrowserTemplatesProvider(adapterFactory));
		templatesViewer.setLabelProvider(new OverridesBrowserTemplateLabelProvider(adapterFactory));

		templatesViewer.addCheckStateListener(new ICheckStateListener() {
			private boolean active;

			public void checkStateChanged(CheckStateChangedEvent event) {
				if (!active) {
					active = true;
					try {
						if (event.getElement() instanceof ModuleProjectHandler) {
							ModuleProjectHandler projectHandler = (ModuleProjectHandler)event.getElement();
							templatesViewer.expandToLevel(projectHandler, 1);
							for (Module eModule : projectHandler.getModules()) {
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

		templatesViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null
						&& event.getSelection() instanceof IStructuredSelection
						&& ((IStructuredSelection)event.getSelection()).getFirstElement() instanceof EObject) {
					EObject eObject = (EObject)((IStructuredSelection)event.getSelection()).getFirstElement();
					handleDoubleClick(eObject);
				}
			}
		});

		initializeTemplatesViewerContent();
	}

	/**
	 * Initialize the templates viewer content.
	 */
	void initializeTemplatesViewerContent() {
		IEditorPart part = getSite().getPage().getActiveEditor();
		if (part instanceof AcceleoEditor) {
			AcceleoEditor editor = (AcceleoEditor)part;
			refreshContent(editor.getFile());
		} else {
			refreshContent(null);
		}
	}

	/**
	 * Handle the double click on the given EObject.
	 * 
	 * @param eObject
	 *            is the event object
	 */
	void handleDoubleClick(EObject eObject) {
		if (eObject.eResource() != null) {
			IRegion region;
			if (eObject instanceof ModuleElement) {
				region = new Region(((ModuleElement)eObject).getStartPosition(), ((ModuleElement)eObject)
						.getEndPosition());
			} else {
				region = null;
			}
			URI fileURI = eObject.eResource().getURI();
			if (fileURI != null) {
				String eObjectFragmentURI = eObject.eResource().getURIFragment(eObject);
				OpenDeclarationUtils.showEObject(getSite().getPage(), fileURI, region, eObject);
				expandFragment(fileURI, eObjectFragmentURI);
			}
		}
	}

	/**
	 * Try to expand the given element in the view.
	 * 
	 * @param eObjectFileURI
	 *            file URI of the element that is to be expanded
	 * @param eObjectFragmentURI
	 *            fragment URI of the element that is to be expanded
	 * @return the expanded EObject
	 */
	EObject expandFragment(URI eObjectFileURI, String eObjectFragmentURI) {
		if (eObjectFileURI != null && eObjectFragmentURI != null
				&& templatesViewer.getInput() instanceof Object[]) {
			Object[] inputs = (Object[])templatesViewer.getInput();
			for (Object input : inputs) {
				if (input instanceof ModuleProjectHandler) {
					ModuleProjectHandler projectHandler = (ModuleProjectHandler)input;
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
	synchronized void checkStateTemplate(Module eModule, boolean state) {
		for (ModuleElement eModuleElement : eModule.getOwnedModuleElement()) {
			templatesViewer.expandToLevel(eModuleElement, 1);
			templatesViewer.setChecked(eModuleElement, state);
		}
	}

	/**
	 * Gets the selected 'overrides' completion proposals. It means a replacement string with dynamic
	 * variables.
	 * 
	 * @param document
	 *            is the document
	 * @param text
	 *            is the text of the document
	 * @param offset
	 *            is the current offset
	 * @return the list of selected proposals, it can be empty
	 */
	public List<ICompletionProposal> getExtendCompletionProposals(IDocument document, String text, int offset) {
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		StringBuilder proposalBuffer = new StringBuilder();
		Object[] templateCheckedElements = templatesViewer.getCheckedElements();
		if (templateCheckedElements.length > 0) {
			int index = offset;
			while (index > 0 && Character.isJavaIdentifierPart(text.charAt(index - 1))) {
				index--;
			}
			if (index > 0 && (text.charAt(index - 1) == '[' || text.charAt(index - 1) == ']')) {
				index--;
			}
			String start = text.substring(index, offset);
			Map<URI, StringBuffer> fileURI2Buffer = new HashMap<URI, StringBuffer>();
			for (int i = 0; i < templateCheckedElements.length; i++) {
				Object templateCheckedElement = templateCheckedElements[i];
				if (templateCheckedElement instanceof ModuleElement
						&& ((ModuleElement)templateCheckedElement).eResource() != null) {
					ModuleElement element = (ModuleElement)templateCheckedElement;
					URI fileURI = ((ModuleElement)templateCheckedElement).eResource().getURI();
					StringBuffer currentBuffer = fileURI2Buffer.get(fileURI);
					if (currentBuffer == null && fileURI != null) {
						currentBuffer = createMTLContent(fileURI);
						fileURI2Buffer.put(fileURI, currentBuffer);
					}
					if (currentBuffer != null && element.getEndPosition() > -1
							&& currentBuffer.length() >= element.getEndPosition()) {
						StringBuffer currentText = new StringBuffer(currentBuffer.substring(
								element.getStartPosition(), element.getEndPosition()).replace("$", "$$")); //$NON-NLS-1$ //$NON-NLS-2$
						currentText.append(System.getProperty("line.separator")); //$NON-NLS-1$
						StringBuffer result = modifyModuleElementContent(document.get(), element,
								currentText, offset);
						proposalBuffer.append(result);
					}
				}
			}
			if (proposalBuffer.length() > 0) {
				Template template = new Template("Selected Overrides", "Selected Overrides", //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPartitionScanner.ACCELEO_BLOCK, proposalBuffer.toString(), true);
				TemplateContextType type = new TemplateContextType(AcceleoPartitionScanner.ACCELEO_BLOCK,
						AcceleoPartitionScanner.ACCELEO_BLOCK);
				TemplateContext context = new DocumentTemplateContext(type, document, 0, document.getLength());
				Region region = new Region(0, document.getLength());
				AcceleoCompletionTemplateProposal proposal = new AcceleoCompletionTemplateProposal(template,
						context, region, AcceleoUIActivator.getDefault().getImage(
								"icons/template-editor/completion/OverridesBrowser.gif")); //$NON-NLS-1$
				proposals.add(proposal);
			}
		}
		return proposals;
	}

	/**
	 * Gets the MTL file content of the given EMTL file URI.
	 * 
	 * @param fileURI
	 *            is the URI of the EMTL file
	 * @return the MTL file content
	 */
	private StringBuffer createMTLContent(URI fileURI) {
		StringBuffer currentBuffer = new StringBuffer();
		Object fileObject = OpenDeclarationUtils.getIFileXorIOFile(fileURI);
		File absoluteFile = null;
		if (fileObject instanceof IFile) {
			absoluteFile = ((IFile)fileObject).getLocation().toFile();
		} else if (fileObject instanceof File) {
			absoluteFile = (File)fileObject;
		}
		if (absoluteFile != null) {
			String mtlName = new Path(absoluteFile.getName()).removeFileExtension().addFileExtension(
					IAcceleoConstants.MTL_FILE_EXTENSION).lastSegment();
			File[] members = absoluteFile.getParentFile().listFiles();
			for (File member : members) {
				if (mtlName.equals(member.getName())) {
					currentBuffer = FileContent.getFileContent(member);
					break;
				}
			}
		}
		return currentBuffer;
	}

	/**
	 * Modify the content of the given module element, by adding an 'overrides' value.
	 * 
	 * @param text
	 *            The current text of the editor.
	 * @param element
	 *            is the module element
	 * @param content
	 *            is the text of the module element in the MTL file
	 * @param offset
	 *            the offset of the completion
	 * @return The new text
	 */
	private StringBuffer modifyModuleElementContent(String text, ModuleElement element, StringBuffer content,
			int offset) {
		StringBuffer result = new StringBuffer(text);
		int off = offset;

		int iBeginParenth = content.indexOf(IAcceleoConstants.PARENTHESIS_BEGIN);
		if (iBeginParenth > -1) {
			if (element instanceof org.eclipse.acceleo.model.mtl.Template) {
				String templateName = ((org.eclipse.acceleo.model.mtl.Template)element).getName();
				EObject eObject = ((org.eclipse.acceleo.model.mtl.Template)element).eContainer();
				int moduleStart = text.indexOf("[module"); //$NON-NLS-1$
				int moduleEnd = text.indexOf("]", moduleStart); //$NON-NLS-1$
				if (eObject instanceof Module
						&& !text.substring(moduleStart, moduleEnd).contains(" extends ")) { //$NON-NLS-1$
					StringBuilder comment = new StringBuilder();
					comment.append("[comment]\n\t"); //$NON-NLS-1$
					comment.append(AcceleoUIMessages.getString("OverridesBrowser.SelectedOverridesComment2")); //$NON-NLS-1$
					comment.append("\n[/comment]\n"); //$NON-NLS-1$
					comment.append("[comment @Override "); //$NON-NLS-1$
					comment.append(((Module)eObject).getName());
					comment.append("."); //$NON-NLS-1$
					comment.append(templateName);
					comment.append(" /]\n"); //$NON-NLS-1$

					int iEndName = iBeginParenth;
					while (iEndName > 0 && Character.isWhitespace(content.charAt(iEndName - 1))) {
						iEndName--;
					}
					int iBeginName = iEndName;
					while (iBeginName > 0 && Character.isJavaIdentifierPart(content.charAt(iBeginName - 1))) {
						iBeginName--;
					}
					content.insert(iEndName, "}"); //$NON-NLS-1$
					content.insert(iBeginName, "${"); //$NON-NLS-1$

					content.insert(0, comment);

					String textExtend = " extends " + ((Module)eObject).getName(); //$NON-NLS-1$
					off = off + textExtend.length();
					result.insert(moduleEnd, textExtend);
				}
				int iEndParenth = content.indexOf(IAcceleoConstants.PARENTHESIS_END, iBeginParenth);
				if (iEndParenth > -1
						&& ((org.eclipse.acceleo.model.mtl.Template)element).getOverrides().size() == 0) {
					iEndParenth += IAcceleoConstants.PARENTHESIS_END.length();
					final String space = " "; //$NON-NLS-1$
					content.insert(iEndParenth, space + IAcceleoConstants.OVERRIDES + space + templateName
							+ space);
				}
			}
		}
		result.insert(off, content);
		return result;
	}
}
