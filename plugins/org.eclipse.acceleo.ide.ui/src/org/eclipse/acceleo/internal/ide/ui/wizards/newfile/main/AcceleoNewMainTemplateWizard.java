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

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * This is a wizard to create an Acceleo Main template. Its role is to create a new Acceleo workflow file
 * resource in the provided container. If the container resource (a folder or a project) is selected in the
 * workspace when the wizard is opened, it will accept it as the target container. The wizard creates one file
 * with the extension "mtl".
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewMainTemplateWizard extends Wizard implements INewWizard {

	/**
	 * The workspace selection when the wizard has been opened.
	 */
	private ISelection selection;

	/**
	 * The new template page.
	 */
	private AcceleoNewMainTemplateWizardPage templatePage;

	/**
	 * The page to select the templates that contain file blocks.
	 */
	private FileBlocksWizardPage fileBlocksPage;

	/**
	 * Constructor.
	 */
	public AcceleoNewMainTemplateWizard() {
		super();
		setWindowTitle(AcceleoUIMessages.getString("AcceleoNewMainTemplateWizard.Title")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection aSelection) {
		this.selection = aSelection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		IContainer firstContainer = null;
		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			IStructuredSelection aSelection = (IStructuredSelection)selection;
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
		String container = ""; //$NON-NLS-1$
		if (firstContainer != null) {
			container = firstContainer.getFullPath().toString();
		}
		templatePage = new AcceleoNewMainTemplateWizardPage(container);
		addPage(templatePage);

		if (firstContainer != null) {
			fileBlocksPage = new FileBlocksWizardPage(firstContainer.getProject(), false);
		} else {
			fileBlocksPage = new FileBlocksWizardPage(null, false);
		}
		addPage(fileBlocksPage);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		IWorkspaceRunnable create = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				createTemplate(monitor);
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(create, null);
			return true;
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
					.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
			return false;
		}
	}

	/**
	 * Creates a template using the given configuration.
	 * 
	 * @param monitor
	 *            is the monitor
	 */
	private void createTemplate(IProgressMonitor monitor) {
		if (templatePage.getTemplateContainer().length() > 0 && templatePage.getTemplateName().length() > 0) {
			IPath templatePath = new Path(templatePage.getTemplateContainer()).append(
					templatePage.getTemplateName()).addFileExtension(IAcceleoConstants.MTL_FILE_EXTENSION);
			try {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
						templatePath.segment(0));
				if (project.isAccessible()) {
					monitor.beginTask(AcceleoUIMessages.getString(
							"AcceleoNewMainTemplateWizard.Task.CreateTemplate", templatePath.lastSegment()), //$NON-NLS-1$
							2);
					IPath projectRelativePath = templatePath.removeFirstSegments(1);
					ByteArrayInputStream javaStream = new ByteArrayInputStream(createTemplateContent(
							templatePage.getTemplateName()).getBytes("UTF8")); //$NON-NLS-1$
					IContainer container = project;
					String[] folders = projectRelativePath.removeLastSegments(1).segments();
					for (int i = 0; i < folders.length; i++) {
						container = container.getFolder(new Path(folders[i]));
						if (!container.exists()) {
							((IFolder)container).create(true, true, monitor);
						}
					}
					final IFile file = container.getFile(new Path(projectRelativePath.lastSegment()));
					if (!file.exists()) {
						file.create(javaStream, true, monitor);
					} else {
						file.setContents(javaStream, true, false, monitor);
					}
					monitor.worked(1);
					monitor.setTaskName(AcceleoUIMessages
							.getString("AcceleoNewMainTemplateWizard.Task.OpenTemplate")); //$NON-NLS-1$
					getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							IWorkbenchPage aPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getActivePage();
							try {
								IDE.openEditor(aPage, file, true);
							} catch (PartInitException e) {
								// continue
								AcceleoUIActivator.log(e, true);
							}
						}
					});
					monitor.worked(1);
				}
			} catch (CoreException e) {
				IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
						.getMessage(), e);
				AcceleoUIActivator.getDefault().getLog().log(status);
			} catch (UnsupportedEncodingException e) {
				IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
						.getMessage(), e);
				AcceleoUIActivator.getDefault().getLog().log(status);
			}
		}
	}

	/**
	 * Creates the content of the template.
	 * 
	 * @param moduleName
	 *            the module name
	 * @return the content of the template
	 */
	private String createTemplateContent(String moduleName) {
		Template[] templates = fileBlocksPage.getSelectedTemplates();
		String defaultEncoding = "UTF-8"; //$NON-NLS-1$
		StringBuilder buffer = new StringBuilder("[comment encoding = "); //$NON-NLS-1$
		buffer.append(defaultEncoding);
		buffer.append(" /]\n"); //$NON-NLS-1$
		buffer.append("[module " + moduleName + "('"); //$NON-NLS-1$ //$NON-NLS-2$
		Set<String> metamodels = new TreeSet<String>();
		Set<String> imports = new TreeSet<String>();
		computeMinimizedHeaderInformation(templates, metamodels, imports);
		for (Iterator<String> metamodelsIt = metamodels.iterator(); metamodelsIt.hasNext();) {
			buffer.append(metamodelsIt.next());
			if (metamodelsIt.hasNext()) {
				buffer.append("', '"); //$NON-NLS-1$
			}
		}
		buffer.append("')/]\n"); //$NON-NLS-1$
		for (Iterator<String> importsIt = imports.iterator(); importsIt.hasNext();) {
			buffer.append("[import " + importsIt.next() + "/]\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buffer.append("\n"); //$NON-NLS-1$
		if (templates.length > 0) {
			buffer.append("\n"); //$NON-NLS-1$
			computeTemplateCalls(buffer, moduleName, templates);
		} else {
			buffer.append("[template public " + moduleName + "(element : OclAny)]\n\t\n"); //$NON-NLS-1$ //$NON-NLS-2$ 
			buffer.append("\t[comment @main /]\n"); //$NON-NLS-1$
			buffer.append("\t[file (element.name.concat('.java'), false, '" + defaultEncoding + "')]\n"); //$NON-NLS-1$ //$NON-NLS-2$
			buffer.append("\t\t\n"); //$NON-NLS-1$
			buffer.append("\t[/file]\n\t\n"); //$NON-NLS-1$
			buffer.append("[/template]\n"); //$NON-NLS-1$
		}
		return buffer.toString();
	}

	/**
	 * Appends in the buffer the invocation of the selected templates.
	 * 
	 * @param buffer
	 *            is the buffer to fill
	 * @param moduleName
	 *            is the module name
	 * @param templates
	 *            are the selected templates
	 */
	private void computeTemplateCalls(StringBuilder buffer, String moduleName, Template[] templates) {
		Set<String> receiverTypes = new TreeSet<String>();
		for (Template template : templates) {
			receiverTypes.add(getTemplateReceiverType(template));
		}
		for (String receiverType : receiverTypes) {
			String var;
			if (receiverType.length() > 0) {
				var = 'a' + receiverType;
			} else {
				var = "arg"; //$NON-NLS-1$
			}
			buffer.append("[template public " + moduleName + receiverType + "(" + var + " : " + receiverType + ")]\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			buffer.append("\t\n"); //$NON-NLS-1$
			buffer.append("\t[comment @main /]\n"); //$NON-NLS-1$
			for (Template template : templates) {
				if (receiverType.equals(getTemplateReceiverType(template))) {
					buffer.append("\t[" + var + "."); //$NON-NLS-1$ //$NON-NLS-2$
					buffer.append(template.getName());
					buffer.append('(');
					for (Iterator<String> argumentTypesIt = getTemplateArgumentTypes(template).iterator(); argumentTypesIt
							.hasNext();) {
						buffer.append(argumentTypesIt.next());
						if (argumentTypesIt.hasNext()) {
							buffer.append(", "); //$NON-NLS-1$
						}
					}
					buffer.append(")/]\n"); //$NON-NLS-1$
				}
			}
			buffer.append("\t\n"); //$NON-NLS-1$
			buffer.append("[/template]\n"); //$NON-NLS-1$

		}
	}

	/**
	 * Computes the file header information by visiting the given templates. It means that we search the
	 * metamodels and the imports values in the given templates array.
	 * 
	 * @param templates
	 *            are the templates to browse
	 * @param metamodels
	 *            are the metamodels found, it's an input/output parameter
	 * @param imports
	 *            are the imported file values, it's an input/output parameter
	 */
	private void computeMinimizedHeaderInformation(Template[] templates, Collection<String> metamodels,
			Collection<String> imports) {
		Set<Module> done = new CompactHashSet<Module>();
		for (Template template : templates) {
			Module module = null;
			EObject parent = template.eContainer();
			while (parent != null) {
				if (parent instanceof Module) {
					module = (Module)parent;
					break;
				}
				parent = parent.eContainer();
			}
			if (module != null && !done.contains(module)) {
				done.add(module);
				for (TypedModel typedModel : module.getInput()) {
					for (EPackage ePackage : typedModel.getTakesTypesFrom()) {
						String nsURI = ePackage.getNsURI();
						if (nsURI != null) {
							metamodels.add(nsURI);
						}
					}
				}
				if (module.getNsURI() != null && module.getNsURI().length() > 0) {
					imports.add(module.getNsURI());
				} else if (module.getName() != null) {
					imports.add(module.getName());
				}
			}
		}
	}

	/**
	 * Gets the argument types of the given template. We removed the first parameter that will be the
	 * receiver.
	 * 
	 * @param template
	 *            is the template AST node
	 * @return the argument types
	 */
	private List<String> getTemplateArgumentTypes(Template template) {
		List<String> parameterTypes = new ArrayList<String>();
		if (template != null && template.getParameter().size() > 1) {
			for (int i = 1; i < template.getParameter().size(); i++) {
				Variable variable = template.getParameter().get(i);
				if (variable != null && variable.getEType() != null) {
					parameterTypes.add(variable.getEType().getName());
				} else {
					parameterTypes.add("OclAny"); //$NON-NLS-1$
				}
			}
		}
		return parameterTypes;
	}

	/**
	 * Gets the receiver type of the given template, it means the type of the first parameter.
	 * 
	 * @param template
	 *            is the template AST node
	 * @return the receiver type
	 */
	private String getTemplateReceiverType(Template template) {
		String context = null;
		if (template != null && template.getParameter().size() > 0) {
			Variable variable = template.getParameter().get(0);
			if (variable != null && variable.getEType() != null) {
				context = variable.getEType().getName();
			}
		}
		if (context == null) {
			context = "OclAny"; //$NON-NLS-1$
		}
		return context;
	}

}
