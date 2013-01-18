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
package org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.JavaServicesUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Quick fix resolution on the Acceleo problem marker. To create a new query at the end of the file with the
 * marker information.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCreateJavaServiceWrapperResolutionAfterLastMember extends AbstractCreateModuleElementResolution {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution2#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages
				.getString("AcceleoCreateJavaServiceWrapperResolutionAfterLastMember.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution2#getImage()
	 */
	public Image getImage() {
		return AcceleoUIActivator.getDefault().getImage("icons/quickfix/QuickFixCreateQuery.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 */
	public String getLabel() {
		return AcceleoUIMessages.getString("AcceleoCreateJavaServiceWrapperResolutionAfterLastMember.Label"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix.AbstractCreateModuleElementResolution#append(java.lang.StringBuilder,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void append(StringBuilder newText, String name, String paramType, String paramName) {
		try {
			for (IFile javaFile : getProjectJavaFiles()) {
				createJavaWrappers(newText, javaFile, name);
			}
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
	}

	/**
	 * Returns a list of existing java files in the current project.
	 * 
	 * @return the current project java files
	 * @throws CoreException
	 *             when an issue occurs
	 */
	private List<IFile> getProjectJavaFiles() throws CoreException {
		List<IFile> javaFiles = new ArrayList<IFile>();
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null && window.getActivePage() != null
				&& window.getActivePage().getActiveEditor() instanceof AcceleoEditor) {
			AcceleoEditor editor = (AcceleoEditor)window.getActivePage().getActiveEditor();
			IFile mtlFile = editor.getFile();
			if (mtlFile != null) {
				IProject project = mtlFile.getProject();
				members(javaFiles, project, "java"); //$NON-NLS-1$
			}
		}
		return javaFiles;
	}

	/**
	 * Returns a list of existing member files (that validate the file extension) in this resource.
	 * 
	 * @param filesOutput
	 *            an output parameter to get all the files
	 * @param container
	 *            is the container to browse
	 * @param extension
	 *            is the extension
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void members(List<IFile> filesOutput, IContainer container, String extension)
			throws CoreException {
		if (container != null) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile && extension.equals(((IFile)resource).getFileExtension())) {
						filesOutput.add((IFile)resource);
					} else if (resource instanceof IContainer) {
						members(filesOutput, (IContainer)resource, extension);
					}
				}
			}
		}
	}

	/**
	 * Reads the content of the java file and creates in the buffer all the Java wrappers that could call the
	 * service with the given name.
	 * 
	 * @param buffer
	 *            is the buffer to fill
	 * @param javaFile
	 *            is the java file that contains the Java services
	 * @param serviceName
	 *            is the name of the service to call
	 */
	private void createJavaWrappers(StringBuilder buffer, IFile javaFile, String serviceName) {
		IJavaElement javaElement = JavaCore.create(javaFile);
		if (javaElement instanceof ICompilationUnit) {
			ICompilationUnit classFile = (ICompilationUnit)javaElement;
			IType[] types;
			try {
				types = classFile.getTypes();
			} catch (JavaModelException e) {
				AcceleoUIActivator.log(e, true);
				types = new IType[0];
			}
			for (IType iType : types) {
				try {
					IMethod[] methods = iType.getMethods();
					for (IMethod iMethod : methods) {
						buffer.append(JavaServicesUtils.createQuery(iType, iMethod, true));
					}
				} catch (JavaModelException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		}
	}
}
