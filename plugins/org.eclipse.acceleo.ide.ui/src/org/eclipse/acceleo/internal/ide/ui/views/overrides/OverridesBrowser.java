/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.views.overrides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.compare.Splitter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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
	 * Interface for listening to workbench part lifecycle events. It is used to listen when an Acceleo Editor
	 * brought to top.
	 */
	private IPartListener partListener;

	/**
	 * The resource sets to unload, when we close the view...
	 */
	private Set<ResourceSet> toUnload = new HashSet<ResourceSet>();

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
		Composite composite = new Splitter(rootContainer, SWT.VERTICAL);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.numColumns = 1;
		composite.setLayout(layout);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalIndent = 1;
		composite.setLayoutData(gridData);
		createTemplatesViewer(composite);
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null
				&& partListener == null) {
			partListener = createPartListener();
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.addPartListener(partListener);

		}
	}

	/**
	 * It creates the object that will listen when an Acceleo Editor bring to top.
	 * 
	 * @return the listener
	 */
	private IPartListener createPartListener() {
		return new IPartListener() {
			public void partOpened(IWorkbenchPart part) {
				if (part instanceof AcceleoEditor) {
					AcceleoEditor editor = (AcceleoEditor)part;
					updateViewTemplates(editor);
				}
			}

			public void partDeactivated(IWorkbenchPart part) {
			}

			public void partClosed(IWorkbenchPart part) {
			}

			public void partActivated(IWorkbenchPart part) {
			}

			public void partBroughtToTop(IWorkbenchPart part) {
				if (part instanceof AcceleoEditor) {
					AcceleoEditor editor = (AcceleoEditor)part;
					updateViewTemplates(editor);
				}
			}
		};
	}

	/**
	 * Updates the templates in the view by getting the settings of the current template. The current template
	 * is in the given editor.
	 * 
	 * @param editor
	 *            is the editor that contains the current template to display in the Overrides view
	 */
	private synchronized void updateViewTemplates(AcceleoEditor editor) {
		if (toUnload.size() > 0) {
			for (ResourceSet resourceSet : toUnload) {
				for (Resource resource : resourceSet.getResources()) {
					if (resource.isLoaded()) {
						resource.unload();
					}
				}
			}
			toUnload.clear();
		}
		IFile file = editor.getFile();
		if (file != null) {
			AcceleoProject acceleoProject = new AcceleoProject(file.getProject());
			List<ModuleProjectHandler> projects = new ArrayList<ModuleProjectHandler>();
			ResourceSet resourceSet = acceleoProject.loadAccessibleOutputFiles();
			computeModuleProjectHandlers(resourceSet, true, projects);
			toUnload.add(resourceSet);
			resourceSet = acceleoProject.loadNotAccessibleOutputFiles();
			computeModuleProjectHandlers(resourceSet, false, projects);
			toUnload.add(resourceSet);
			templatesViewer.setInput(projects.toArray());
		} else {
			templatesViewer.setInput(null);
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
				projects.add(new ModuleProjectHandler(projectName, eModules.toArray(new Module[eModules
						.size()]), isResolved));
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
		if (partListener != null
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().removePartListener(
					partListener);
			partListener = null;
		}
		if (toUnload.size() > 0) {
			for (ResourceSet resourceSet : toUnload) {
				for (Resource resource : resourceSet.getResources()) {
					if (resource.isLoaded()) {
						resource.unload();
					}
				}
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
							ModuleProjectHandler project = (ModuleProjectHandler)event.getElement();
							for (Module eModule : project.getModules()) {
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
		IEditorPart part = getSite().getPage().getActiveEditor();
		if (part instanceof AcceleoEditor) {
			AcceleoEditor editor = (AcceleoEditor)part;
			updateViewTemplates(editor);
		} else {
			templatesViewer.setInput(null);
		}
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

}
