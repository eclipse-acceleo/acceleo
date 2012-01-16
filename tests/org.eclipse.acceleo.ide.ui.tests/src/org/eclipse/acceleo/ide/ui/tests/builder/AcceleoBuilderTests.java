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
package org.eclipse.acceleo.ide.ui.tests.builder;

import java.util.Collections;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.resource.AcceleoProjectUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import static org.junit.Assert.fail;

public class AcceleoBuilderTests {

	@Test
	public void testBuildWithClosedProjectInWorkspace() {
		IProject closedProject = ResourcesPlugin.getWorkspace().getRoot().getProject("closedproject"); //$NON-NLS-1$
		try {
			IProgressMonitor monitor = new NullProgressMonitor();
			closedProject.create(monitor);
			closedProject.close(monitor);

			org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject acceleoProject = AcceleowizardmodelFactory.eINSTANCE
					.createAcceleoProject();
			acceleoProject.setName("acceleoproject"); //$NON-NLS-1$
			acceleoProject.setGeneratorName("generator"); //$NON-NLS-1$
			acceleoProject.setJre("J2SE-1.5"); //$NON-NLS-1$

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("acceleoproject"); //$NON-NLS-1$
			project.create(monitor);
			project.open(monitor);

			AcceleoProjectUtils.generateFiles(acceleoProject, Collections.<AcceleoModule> emptyList(),
					project, false, monitor);

			AcceleoClosedProjectLogListener listener = new AcceleoClosedProjectLogListener();

			AcceleoUIActivator.getDefault().getLog().addLogListener(listener);
			project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);

			if (listener.message != null) {
				fail(listener.message);
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	public class AcceleoClosedProjectLogListener implements ILogListener {

		public String message = null;

		public void logging(IStatus status, String plugin) {
			if (status.getMessage().contains("Resource '/closedproject' is not open.")) { //$NON-NLS-1$
				message = status.getMessage();
			}
		}

	}
}
