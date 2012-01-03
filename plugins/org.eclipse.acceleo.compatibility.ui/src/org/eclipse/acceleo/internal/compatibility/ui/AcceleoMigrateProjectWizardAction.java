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
package org.eclipse.acceleo.internal.compatibility.ui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.compatibility.model.mt.Resource;
import org.eclipse.acceleo.compatibility.model.mt.ResourceSet;
import org.eclipse.acceleo.compatibility.model.mt.core.Metamodel;
import org.eclipse.acceleo.compatibility.model.mt.core.Script;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.popupMenus.AbstractMigrateProjectWizardAction;
import org.eclipse.acceleo.internal.compatibility.mtl.gen.Mt2mtl;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.core.ProjectParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.parser.AcceleoFile;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

/**
 * An action to create automatically a new Acceleo module from an old Acceleo module (with MT files).
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoMigrateProjectWizardAction extends AbstractMigrateProjectWizardAction {

	/**
	 * The root element of the model which describes the '.mt' files.
	 */
	private ResourceSet root;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void browseTemplates(IProject[] projects) throws CoreException {
		List<TemplateSyntaxException> problems = new ArrayList<TemplateSyntaxException>();
		root = ProjectParser.createModel(projects, problems);
		if (problems.size() > 0) {
			StringBuffer message = new StringBuffer();
			for (Iterator<TemplateSyntaxException> problemsIt = problems.iterator(); problemsIt.hasNext();) {
				TemplateSyntaxException templateSyntaxException = problemsIt.next();
				message.append(templateSyntaxException.toString());
				message.append("\n\n"); //$NON-NLS-1$
			}
			Status status = new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID, message.toString());
			AcceleoCommonPlugin.log(status);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String computeMetamodelURIs() {
		StringBuffer metamodelURIs = new StringBuffer();
		Iterator<Resource> resources = root.getResources().iterator();
		while (resources.hasNext()) {
			Resource resource = resources.next();
			if (resource instanceof Metamodel) {
				if (metamodelURIs.length() > 0) {
					metamodelURIs.append(',');
				}
				metamodelURIs.append(resource.getName());
			}
		}
		return metamodelURIs.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.popupMenus.AbstractMigrateProjectWizardAction#generateMTL(org.eclipse.core.runtime.IPath,
	 *      org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected void generateMTL(final IPath baseFolder, final IPath mainTemplate) {
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				try {
					generateMTLWithProgress(baseFolder, mainTemplate, monitor);
				} catch (CoreException e) {
					AcceleoCommonPlugin.log(e.getStatus());
				} catch (IOException e) {
					Status status = new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID, e.getMessage()
							.toString(), e);
					AcceleoCommonPlugin.log(status);
				}
			}
		};
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, true, operation);
		} catch (InvocationTargetException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID, e.getMessage(), e);
			AcceleoCommonPlugin.getDefault().getLog().log(status);
		} catch (InterruptedException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID, e.getMessage(), e);
			AcceleoCommonPlugin.getDefault().getLog().log(status);
		}
	}

	/**
	 * Generate the output MTL files.
	 * 
	 * @param baseFolder
	 *            is the target folder
	 * @param mainTemplate
	 *            is the main template path in the workspace
	 * @param monitor
	 *            is the monitor
	 * @throws IOException
	 *             when the model cannot be saved
	 * @throws CoreException
	 *             when a workspace issue occurs
	 */
	protected void generateMTLWithProgress(IPath baseFolder, IPath mainTemplate, IProgressMonitor monitor)
			throws IOException, CoreException {
		if (baseFolder.segmentCount() > 0
				&& ResourcesPlugin.getWorkspace().getRoot().getProject(baseFolder.segment(0)).isAccessible()) {
			IFile emtFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
					mainTemplate.removeFileExtension().addFileExtension("emt")); //$NON-NLS-1$
			IPath emtPath = emtFile.getLocation();
			ModelUtils.save(root, emtPath.toString());
			IContainer targetContainer;
			if (baseFolder.segmentCount() > 1) {
				targetContainer = ResourcesPlugin.getWorkspace().getRoot().getProject(baseFolder.segment(0))
						.getFolder(baseFolder.segment(1));
				if (!targetContainer.exists()) {
					targetContainer = emtFile.getParent();
				}
			} else {
				targetContainer = emtFile.getParent();
			}
			File targetFolder = targetContainer.getLocation().toFile();
			Mt2mtl mt2mtl = new Mt2mtl(root, targetFolder, new ArrayList<Object>());
			mt2mtl.doGenerate(BasicMonitor.toMonitor(monitor));
			if (targetContainer.isAccessible()) {
				targetContainer.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			}
			IFile mainFile = ResourcesPlugin.getWorkspace().getRoot().getFile(mainTemplate);
			if (mainFile.exists()) {
				StringBuffer buffer = FileContent.getFileContent(mainFile.getLocation().toFile());
				int start = buffer.indexOf(IAcceleoConstants.DEFAULT_BEGIN + IAcceleoConstants.TEMPLATE);
				if (start == -1) {
					start = 0;
				}
				int iImport = start;
				start = buffer.indexOf(IAcceleoConstants.DEFAULT_END, start) + 1;
				int end = buffer.indexOf(IAcceleoConstants.DEFAULT_BEGIN
						+ IAcceleoConstants.DEFAULT_END_BODY_CHAR + IAcceleoConstants.TEMPLATE
						+ IAcceleoConstants.DEFAULT_END, start);
				if (end == -1) {
					end = buffer.length();
				}
				buffer.delete(start, end);
				StringBuffer newImportContent = new StringBuffer();
				StringBuffer newTemplateContent = new StringBuffer("\n\t"); //$NON-NLS-1$
				newTemplateContent.append(IAcceleoConstants.DEFAULT_BEGIN);
				newTemplateContent.append(IAcceleoConstants.COMMENT);
				newTemplateContent.append(" "); //$NON-NLS-1$
				newTemplateContent.append(IAcceleoConstants.TAG_MAIN);
				newTemplateContent.append(" "); //$NON-NLS-1$
				newTemplateContent.append(IAcceleoConstants.DEFAULT_END_BODY_CHAR);
				newTemplateContent.append(IAcceleoConstants.DEFAULT_END);
				newTemplateContent.append('\n');
				for (Resource resource : root.getResources()) {
					if (resource instanceof Template) {
						Template template = (Template)resource;
						computeImportAndTemplateCall(template, newImportContent, newTemplateContent);
					}
				}
				newImportContent.append('\n');
				newTemplateContent.append('\n');
				buffer.insert(start, newTemplateContent.toString());
				buffer.insert(iImport, newImportContent.toString());
				try {
					ByteArrayInputStream javaStream = new ByteArrayInputStream(buffer.toString().getBytes(
							"UTF8")); //$NON-NLS-1$
					mainFile.setContents(javaStream, true, false, monitor);
				} catch (UnsupportedEncodingException e) {
					throw new CoreException(new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e
							.getMessage(), e));
				}
			}
		}
	}

	/**
	 * Creates the import sequence and the template content for the given MT template file.
	 * 
	 * @param template
	 *            is the current MT template file
	 * @param newImportContent
	 *            is the import sequence to create
	 * @param newTemplateContent
	 *            is the main template content
	 */
	private void computeImportAndTemplateCall(Template template, StringBuffer newImportContent,
			StringBuffer newTemplateContent) {
		String fileTemplateName = null;
		for (Script script : template.getScripts()) {
			if (script.getDescriptor() != null && script.getDescriptor().getFile() != null
					&& script.getDescriptor().getFile().getStatements().size() > 0) {
				fileTemplateName = script.getDescriptor().getName();
				break;
			}
		}
		if (fileTemplateName != null) {
			newImportContent.append("[import "); //$NON-NLS-1$
			String javaPackageName;
			String shortName;
			int iDot = template.getName().lastIndexOf('.');
			if (iDot > -1) {
				javaPackageName = template.getName().substring(0, iDot);
				shortName = template.getName().substring(iDot + 1);
			} else {
				javaPackageName = ""; //$NON-NLS-1$
				shortName = template.getName();
			}
			newImportContent.append(AcceleoFile.javaPackageToFullModuleName(javaPackageName, shortName));
			newImportContent.append(" /]\n"); //$NON-NLS-1$
			newTemplateContent.append("\n\t[comment Call the file block in '"); //$NON-NLS-1$
			newTemplateContent.append(shortName);
			newTemplateContent.append("' /]\n"); //$NON-NLS-1$
			newTemplateContent.append("\t[ "); //$NON-NLS-1$
			newTemplateContent.append(fileTemplateName);
			newTemplateContent.append("() /]\n"); //$NON-NLS-1$
		}
	}

}
