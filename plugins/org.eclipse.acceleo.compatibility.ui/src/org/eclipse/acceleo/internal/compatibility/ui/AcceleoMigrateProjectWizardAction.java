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
package org.eclipse.acceleo.internal.compatibility.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.compatibility.model.mt.Resource;
import org.eclipse.acceleo.compatibility.model.mt.ResourceSet;
import org.eclipse.acceleo.compatibility.model.mt.core.Metamodel;
import org.eclipse.acceleo.ide.ui.popupMenus.AbstractMigrateProjectWizardAction;
import org.eclipse.acceleo.internal.compatibility.mtl.gen.Mt2mtl;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.core.ProjectParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicMonitor;

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
	protected void generateMTL(IPath baseFolder, IPath mainTemplate) throws IOException, CoreException {
		if (baseFolder.segmentCount() > 0) {
			IFile emtFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
					mainTemplate.removeFileExtension().addFileExtension("emt")); //$NON-NLS-1$
			IPath emtPath = emtFile.getLocation();
			ModelUtils.save(root, emtPath.toString());
			IContainer targetContainer = emtFile.getParent();
			File targetFolder = targetContainer.getLocation().toFile();
			Mt2mtl mt2mtl = new Mt2mtl(root, targetFolder, new ArrayList<Object>());
			mt2mtl.doGenerate(new BasicMonitor());
			if (targetContainer.isAccessible()) {
				targetContainer.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			}
			// TODO LGO JMU : The main template must be a real entry point of the code generation
		}
	}
}
