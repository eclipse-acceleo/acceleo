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
package org.eclipse.acceleo.internal.ide.ui.launching;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaLaunchShortcut;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Launch shortcut for local Java applications.
 * <p>
 * This class may be instantiated or subclassed.
 * </p>
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoApplicationLaunchShortcut extends JavaLaunchShortcut {

	/**
	 * Returns the Java elements corresponding to the given objects.
	 * 
	 * @param objects
	 *            selected objects
	 * @return corresponding Java elements
	 */
	private IJavaElement[] getJavaElements(Object[] objects) {
		List<IJavaElement> list = new ArrayList<IJavaElement>(objects.length);
		for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];
			if (object instanceof IFile
					&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)object).getFileExtension())) {
				try {
					String name1 = new Path(((IFile)object).getName()).removeFileExtension().lastSegment()
							.toLowerCase();
					IResource[] members = ((IFile)object).getParent().members();
					for (int j = 0; j < members.length; j++) {
						IResource resource = members[j];
						if (resource != object && resource instanceof IFile
								&& "java".equals(((IFile)resource).getFileExtension())) { //$NON-NLS-1$
							String name2 = new Path(((IFile)resource).getName()).removeFileExtension()
									.lastSegment().toLowerCase();
							if (name1.equals(name2)) {
								object = resource;
								break;
							}
						}
					}
				} catch (CoreException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
				IFile javaFile = ((IFile)object).getParent().getFile(
						new Path(((IFile)object).getName()).removeFileExtension().addFileExtension("java")); //$NON-NLS-1$
				if (javaFile.exists()) {
					object = javaFile;
				}
			}
			if (object instanceof IAdaptable) {
				IJavaElement element = (IJavaElement)((IAdaptable)object).getAdapter(IJavaElement.class);
				if (element != null) {
					if (element instanceof IMember && ((IMember)element).getDeclaringType() != null) {
						element = ((IMember)element).getDeclaringType();
					}
					list.add(element);
				}
			}
		}
		return list.toArray(new IJavaElement[list.size()]);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaLaunchShortcut#createConfiguration(org.eclipse.jdt.core.IType)
	 */
	protected ILaunchConfiguration createConfiguration(IType type) {
		ILaunchConfiguration config = null;
		ILaunchConfigurationWorkingCopy wc = null;
		try {
			ILaunchConfigurationType configType = getConfigurationType();
			wc = configType.newInstance(null, getLaunchManager().generateUniqueLaunchConfigurationNameFrom(
					type.getElementName()));
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, type
					.getFullyQualifiedName());
			wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, type.getJavaProject()
					.getElementName());
			wc.setMappedResources(new IResource[] {type.getUnderlyingResource()});
			config = wc.doSave();
			IStructuredSelection selection;
			if (config == null) {
				selection = new StructuredSelection();
			} else {
				selection = new StructuredSelection(config);
			}
			DebugUITools.openLaunchConfigurationDialogOnGroup(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), selection, IDebugUIConstants.ID_RUN_LAUNCH_GROUP);
		} catch (CoreException e) {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window != null) {
				MessageDialog.openError(window.getShell(), AcceleoUIMessages
						.getString("AcceleoApplicationLaunchShortcut.Error"), e.getStatus().getMessage()); //$NON-NLS-1$
			} else {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		return config;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaLaunchShortcut#getConfigurationType()
	 */
	protected ILaunchConfigurationType getConfigurationType() {
		return getLaunchManager()
				.getLaunchConfigurationType(
						org.eclipse.acceleo.internal.ide.ui.launching.IAcceleoLaunchConfigurationConstants.ID_ACCELEO_APPLICATION);
	}

	/**
	 * Returns the singleton launch manager.
	 * 
	 * @return launch manager
	 */
	private ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaLaunchShortcut#findTypes(java.lang.Object[],
	 *      org.eclipse.jface.operation.IRunnableContext)
	 */
	protected IType[] findTypes(Object[] elements, IRunnableContext context) throws InterruptedException,
			CoreException {
		try {
			if (elements.length == 1) {
				IType type = isMainMethod(elements[0]);
				if (type != null) {
					return new IType[] {type};
				}
			}
			IJavaElement[] javaElements = getJavaElements(elements);
			AcceleoMainMethodSearchEngine engine = new AcceleoMainMethodSearchEngine();
			int constraints = IJavaSearchScope.SOURCES;
			constraints |= IJavaSearchScope.APPLICATION_LIBRARIES;
			IJavaSearchScope scope = SearchEngine.createJavaSearchScope(javaElements, constraints);
			return engine.searchMainMethods(context, scope, true);
		} catch (InvocationTargetException e) {
			throw (CoreException)e.getTargetException();
		}
	}

	/**
	 * Returns the smallest enclosing <code>IType</code> if the specified object is a main method, or
	 * <code>null</code>.
	 * 
	 * @param o
	 *            the object to inspect
	 * @return the smallest enclosing <code>IType</code> of the specified object if it is a main method or
	 *         <code>null</code> if it is not
	 */
	private IType isMainMethod(Object o) {
		if (o instanceof IAdaptable) {
			IAdaptable adapt = (IAdaptable)o;
			IJavaElement element = (IJavaElement)adapt.getAdapter(IJavaElement.class);
			if (element != null && element.getElementType() == IJavaElement.METHOD) {
				try {
					IMethod method = (IMethod)element;
					if (method.isMainMethod()) {
						return method.getDeclaringType();
					}
				} catch (JavaModelException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaLaunchShortcut#getTypeSelectionTitle()
	 */
	protected String getTypeSelectionTitle() {
		return AcceleoUIMessages.getString("AcceleoApplicationLaunchShortcut.getTypeSelectionTitle"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaLaunchShortcut#getEditorEmptyMessage()
	 */
	protected String getEditorEmptyMessage() {
		return AcceleoUIMessages.getString("AcceleoApplicationLaunchShortcut.getEditorEmptyMessage"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaLaunchShortcut#getSelectionEmptyMessage()
	 */
	protected String getSelectionEmptyMessage() {
		return AcceleoUIMessages.getString("AcceleoApplicationLaunchShortcut.getSelectionEmptyMessage"); //$NON-NLS-1$
	}
}
