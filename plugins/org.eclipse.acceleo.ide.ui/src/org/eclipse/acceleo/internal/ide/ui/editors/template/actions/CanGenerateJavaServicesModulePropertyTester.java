/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.JavaServicesUtils;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * This class will be used to determine if the "Generate Java Services Module" action should be displayed for
 * the current selection.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class CanGenerateJavaServicesModulePropertyTester extends PropertyTester {

	/**
	 * The constructor.
	 */
	public CanGenerateJavaServicesModulePropertyTester() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String,
	 *      java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean canGenerateJavaServicesModule = false;

		List<ICompilationUnit> files = new ArrayList<ICompilationUnit>();

		// Compute the compilation unit selected
		if (receiver instanceof IWorkbenchPart) {
			IWorkbenchPart iWorkbenchPart = (IWorkbenchPart)receiver;
			IWorkbenchPartSite site = iWorkbenchPart.getSite();
			IWorkbenchPage page = site.getPage();
			ISelection selection = page.getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection iStructuredSelection = (IStructuredSelection)selection;
				List<?> list = iStructuredSelection.toList();
				for (Object object : list) {
					try {
						if (object instanceof ICompilationUnit
								&& ((ICompilationUnit)object).getResource().getProject().hasNature(
										IAcceleoConstants.ACCELEO_NATURE_ID)) {
							files.add((ICompilationUnit)object);
						}
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
					}
				}
			}
		}

		for (ICompilationUnit iCompilationUnit : files) {
			canGenerateJavaServicesModule = canGenerateJavaServicesModule
					|| JavaServicesUtils.isAcceleoJavaServicesClass(iCompilationUnit);
		}

		// Generate a new module for each Java services
		return canGenerateJavaServicesModule;
	}

}
