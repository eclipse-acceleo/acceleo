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
package org.eclipse.acceleo.internal.compatibility.parser.mt.ast.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.compatibility.model.mt.MtFactory;
import org.eclipse.acceleo.compatibility.model.mt.ResourceSet;
import org.eclipse.acceleo.compatibility.model.mt.core.CoreFactory;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.MTFileContent;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * The utility class to parse several Acceleo Projects. We create an EMF model to share the content of all the
 * '.mt' generators. The 'createModel' method is used to create the root element of this model.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ProjectParser {

	/**
	 * No public access to the constructor.
	 */
	private ProjectParser() {
		// nothing to do here
	}

	/**
	 * Parse the given projects and create an EMF model to share the content of all the generators.
	 * 
	 * @param projects
	 *            are the projects to parse
	 * @param problems
	 *            are the problems
	 * @return the root element of the model
	 * @throws CoreException
	 *             if an issue occurs, it contains a status object describing the cause of the exception
	 */
	public static ResourceSet createModel(IProject[] projects, List<TemplateSyntaxException> problems)
			throws CoreException {
		ResourceSet root = MtFactory.eINSTANCE.createResourceSet();
		for (int i = 0; i < projects.length; i++) {
			AcceleoWorkspaceUtil.INSTANCE.addWorkspaceContribution(projects[i]);
		}
		try {
			Map<IFile, Template> files = createTemplates(projects, root);
			for (Iterator<Map.Entry<IFile, Template>> filesIt = files.entrySet().iterator(); filesIt
					.hasNext();) {
				Map.Entry<IFile, Template> entry = filesIt.next();
				IFile file = entry.getKey();
				Template template = entry.getValue();
				TemplateParser.parseTemplate(file, template, root, problems);
			}
		} finally {
			AcceleoWorkspaceUtil.INSTANCE.reset();
		}
		return root;
	}

	/**
	 * Creates the templates for the existing '.mt' files.
	 * 
	 * @param projects
	 *            the projects to browse
	 * @param root
	 *            the root element of the model to create
	 * @return a map to attach an '.mt' file and its model representation
	 * @throws CoreException
	 *             if an issue occurs, it contains a status object describing the cause of the exception
	 */
	private static Map<IFile, Template> createTemplates(IProject[] projects, ResourceSet root)
			throws CoreException {
		Map<IFile, Template> files = new HashMap<IFile, Template>();
		Set<IFolder> done = new CompactHashSet<IFolder>();
		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			final IJavaProject javaProject = JavaCore.create(project);
			final IClasspathEntry[] entries = javaProject.getResolvedClasspath(true);
			for (int j = 0; j < entries.length; j++) {
				final IClasspathEntry entry = entries[i];
				if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE && entry.getPath().segmentCount() > 1) {
					final IFolder inputFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
							entry.getPath());
					if (!done.contains(inputFolder) && inputFolder.exists()) {
						done.add(inputFolder);
						createTemplates(files, inputFolder.getFullPath(), inputFolder, root);
					}
				}
			}
		}
		return files;
	}

	/**
	 * Creates the templates for the existing '.mt' files in the given container.
	 * 
	 * @param files
	 *            an output parameter, a map to attach an '.mt' file and its new model representation
	 * @param inputFolder
	 *            is full path in the workspace of the current input folder (src)
	 * @param container
	 *            is the container to browse
	 * @param root
	 *            the root element of the model to create
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private static void createTemplates(Map<IFile, Template> files, IPath inputFolder, IContainer container,
			ResourceSet root) throws CoreException {
		IResource[] children = container.members();
		if (children != null) {
			for (int i = 0; i < children.length; ++i) {
				IResource resource = children[i];
				if (resource instanceof IFile
						&& MTFileContent.MT_FILE_EXTENSION.equals(((IFile)resource).getFileExtension())) {
					String[] segments = ((IFile)resource).getFullPath().removeFileExtension()
							.removeFirstSegments(inputFolder.segmentCount()).segments();
					StringBuffer qualifiedName = new StringBuffer();
					for (int j = 0; j < segments.length; j++) {
						if (j > 0) {
							qualifiedName.append("."); //$NON-NLS-1$
						}
						qualifiedName.append(segments[j]);
					}
					Template template = CoreFactory.eINSTANCE.createTemplate();
					root.getResources().add(template);
					template.setName(qualifiedName.toString());
					files.put((IFile)resource, template);
				} else if (resource instanceof IContainer) {
					createTemplates(files, inputFolder, (IContainer)resource, root);
				}
			}
		}
	}

}
