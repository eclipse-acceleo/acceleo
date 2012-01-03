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
package org.eclipse.acceleo.internal.ide.ui.popupMenus;

import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.internal.ide.ui.views.result.TraceabilityModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * Open the declaration of the selected traceability element.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class OpenDeclarationTraceabilityElementAction implements IWorkbenchWindowActionDelegate {

	/**
	 * The current selection.
	 */
	private ISelection currentSelection;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (currentSelection instanceof IStructuredSelection
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			Object selectedElement = ((IStructuredSelection)currentSelection).getFirstElement();
			if (selectedElement instanceof TraceabilityModel) {
				EObject declaration = ((TraceabilityModel)selectedElement).getEObject();
				Resource resource = declaration.eResource();
				URI fileURI = null;
				if (resource != null && resource.getURI() != null) {
					fileURI = resource.getURI();
				} else if (declaration.eIsProxy()) {
					fileURI = ((InternalEObject)declaration).eProxyURI().trimFragment();
				}
				OpenDeclarationUtils.showEObject(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage(), fileURI, OpenDeclarationUtils.createRegion(declaration),
						declaration);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		currentSelection = selection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
	}
}
