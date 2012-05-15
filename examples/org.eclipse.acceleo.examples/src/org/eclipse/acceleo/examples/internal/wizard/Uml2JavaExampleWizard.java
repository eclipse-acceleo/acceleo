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
package org.eclipse.acceleo.examples.internal.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoCompatibilityHelper;
import org.eclipse.acceleo.common.internal.utils.compatibility.OCLVersion;
import org.eclipse.acceleo.examples.internal.AcceleoExamplesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * This will allow the user to unzip the uml2java example module.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class Uml2JavaExampleWizard extends AbstractExampleWizard {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.examples.internal.wizard.AbstractExampleWizard#getProjectDescriptors()
	 */
	@Override
	protected Collection<ProjectDescriptor> getProjectDescriptors() {
		final List<ProjectDescriptor> projects = new ArrayList<ProjectDescriptor>(2);

		// Assume the UML version is the indicator of the Eclipse version
		Bundle bundle = Platform.getBundle("org.eclipse.uml2.uml");
		if (bundle == null) {
			AcceleoExamplesPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoExamplesPlugin.PLUGIN_ID, "UML2 is not installed"));
		} else {
			final String bundleName = "org.eclipse.acceleo.examples";
			Version version = bundle.getVersion();
			if (version.getMajor() >= 4) {
				// Juno
				String baseName = "org.eclipse.acceleo.examples.uml2java";
				projects.add(new ProjectDescriptor(bundleName, "examples/" + baseName + ".zip", baseName)); //$NON-NLS-1$ //$NON-NLS-2$
				projects.add(new ProjectDescriptor(bundleName,
						"examples/" + baseName + ".model" + ".zip", baseName + ".model")); //$NON-NLS-1$ //$NON-NLS-2$
				projects.add(new ProjectDescriptor(bundleName,
						"examples/" + baseName + ".ui" + ".zip", baseName //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								+ ".ui")); //$NON-NLS-1$
			} else {
				// Old mechanism
				// Assume the version of OCL is an indication of the Eclipse version
				String baseName = "org.eclipse.acceleo.module.example.uml2java";
				if (AcceleoCompatibilityHelper.getCurrentVersion() == OCLVersion.HELIOS) {
					baseName += ".helios"; //$NON-NLS-1$
				}
				projects.add(new ProjectDescriptor(bundleName, "examples/" + baseName + ".zip", baseName)); //$NON-NLS-1$ //$NON-NLS-2$ 
				projects.add(new ProjectDescriptor(bundleName,
						"examples/" + baseName + ".ui" + ".zip", baseName //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								+ ".ui")); //$NON-NLS-1$
			}
		}

		return projects;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.examples.internal.wizard.AbstractExampleWizard#log(java.lang.Exception)
	 */
	@Override
	protected void log(Exception e) {
		if (e instanceof CoreException) {
			AcceleoExamplesPlugin.getDefault().getLog().log(((CoreException)e).getStatus());
		} else {
			AcceleoExamplesPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoExamplesPlugin.PLUGIN_ID, IStatus.ERROR, e.getMessage(),
							e));
		}
	}
}
