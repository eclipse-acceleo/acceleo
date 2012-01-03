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

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.acceleo.internal.ide.ui.wizards.module.example.AcceleoInitializationStrategyUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * The Acceleo module wizard.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoModuleWizard extends Wizard implements INewWizard, IExecutableExtension {
	/**
	 * The configuration element.
	 */
	protected IConfigurationElement configurationElement;

	/**
	 * The current workbench.
	 */
	protected IWorkbench workbench;

	/**
	 * The current selection.
	 */
	protected IStructuredSelection selection;

	/**
	 * The wizard page.
	 */
	private AcceleoModuleWizardPage acceleoModuleWizardPage;

	/**
	 * The constructor.
	 */
	public AcceleoModuleWizard() {
		super();
		setWindowTitle(AcceleoUIMessages.getString("AcceleoNewTemplatesWizard.Title")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench iWorkbench, IStructuredSelection iSelection) {
		this.workbench = iWorkbench;
		this.selection = iSelection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		IContainer firstContainer = null;
		if (selection != null && !selection.isEmpty()) {
			IStructuredSelection aSelection = selection;
			if (aSelection.size() > 0) {
				Object element = aSelection.getFirstElement();
				if (element instanceof IAdaptable) {
					element = ((IAdaptable)element).getAdapter(IResource.class);
				}
				if (element instanceof IContainer) {
					firstContainer = (IContainer)element;
				} else if (element instanceof IResource) {
					firstContainer = ((IResource)element).getParent();
				}
			}
		}

		if (firstContainer != null) {
			String container = firstContainer.getFullPath().toString();
			this.acceleoModuleWizardPage = new AcceleoModuleWizardPage(container);
		} else {
			this.acceleoModuleWizardPage = new AcceleoModuleWizardPage();
		}
		this.addPage(acceleoModuleWizardPage);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
	 *      java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		this.configurationElement = config;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		AcceleoModule acceleoModule = this.acceleoModuleWizardPage.getAcceleoModule();

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(acceleoModule.getProjectName());
		IFile file = null;
		if (project.exists() && project.isAccessible()) {
			IPath path = new Path(acceleoModule.getParentFolder());
			IFolder folder = project.getFolder(path.removeFirstSegments(1));
			AcceleoUIGenerator.getDefault().generateAcceleoModule(acceleoModule, folder);
			file = folder.getFile(acceleoModule.getName() + "." + IAcceleoConstants.MTL_FILE_EXTENSION); //$NON-NLS-1$

			if (acceleoModule.isIsInitialized()) {
				String initializationKind = acceleoModule.getInitializationKind();
				IAcceleoInitializationStrategy strategy = null;
				List<IAcceleoInitializationStrategy> initializationStrategy = AcceleoInitializationStrategyUtils
						.getInitializationStrategy();
				for (IAcceleoInitializationStrategy iAcceleoInitializationStrategy : initializationStrategy) {
					if (iAcceleoInitializationStrategy.getDescription() != null
							&& iAcceleoInitializationStrategy.getDescription().equals(initializationKind)) {
						strategy = iAcceleoInitializationStrategy;
						break;
					}
				}

				IFile exampleFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
						new Path(acceleoModule.getInitializationPath()));

				String moduleElementKind = IAcceleoInitializationStrategy.TEMPLATE_KIND;
				if (acceleoModule.getModuleElement().getKind().equals(ModuleElementKind.QUERY)) {
					moduleElementKind = IAcceleoInitializationStrategy.QUERY_KIND;
				}

				if (strategy != null && file.exists()) {
					try {
						strategy.configure(moduleElementKind, acceleoModule.getModuleElement()
								.isGenerateFile(), acceleoModule.getModuleElement().isIsMain(), acceleoModule
								.isGenerateDocumentation());
						String content = strategy.getContent(exampleFile, acceleoModule.getName(),
								acceleoModule.getMetamodelURIs(), acceleoModule.getModuleElement()
										.getParameterType());
						ByteArrayInputStream javaStream = new ByteArrayInputStream(content.getBytes("UTF8")); //$NON-NLS-1$
						file.setContents(javaStream, true, false, new NullProgressMonitor());
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
					} catch (UnsupportedEncodingException e) {
						AcceleoUIActivator.log(e, true);
					}
				}
			}
		}

		// Update the perspective.
		BasicNewProjectResourceWizard.updatePerspective(this.configurationElement);

		// Open the module created
		if (file != null && file.isAccessible()) {
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				IDE.openEditor(activePage, file);
			} catch (PartInitException e) {
				AcceleoUIActivator.log(e, true);
			}
		}
		return true;
	}
}
