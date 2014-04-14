/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.ui.internal.util;

/**
 * The Meta Inf class helper.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public final class AUnitMetaInfHelper {

	/**
	 * The constructor.
	 */
	private AUnitMetaInfHelper() {
		// never used.
	}

	/**
	 * Generates the meta inf content.
	 * 
	 * @param projectName
	 *            the project name.
	 * @return the meta inf content.
	 */
	public static String generateMetaInf(String projectName) {

		StringBuffer buffer = new StringBuffer(""); //$NON-NLS-1$
		// new line.
		final String nL = AUnitUtils.getLineSeparator();
		buffer.append("Manifest-Version: 1.0" + nL); //$NON-NLS-1$
		buffer.append("Bundle-ManifestVersion: 2" + nL); //$NON-NLS-1$
		buffer.append("Bundle-Name: Acceleo Sample Module Runtime Plug-in" + nL); //$NON-NLS-1$
		buffer.append("Bundle-SymbolicName: " + projectName + nL); //$NON-NLS-1$
		buffer.append("Bundle-Version: 1.0.0.qualifier" + nL); //$NON-NLS-1$
		buffer.append("Bundle-Vendor: Eclipse Modeling Project" + nL); //$NON-NLS-1$
		buffer.append("Require-Bundle: org.junit4," + nL); //$NON-NLS-1$
		buffer.append(" fr.obeo.acceleo.unit.core," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.core.runtime," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.emf.ecore," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.emf.ecore.xmi," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.ocl," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.ocl.ecore," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.acceleo.common;bundle-version=\"3.2.0\"," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.acceleo.model;bundle-version=\"3.2.0\"," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.acceleo.profiler;bundle-version=\"3.2.0\"," + nL); //$NON-NLS-1$
		buffer.append(" org.eclipse.acceleo.engine;bundle-version=\"3.2.0\"," + nL); //$NON-NLS-1$
		buffer.append(" com.google.guava;bundle-version=\"10.0.1\"" + nL); //$NON-NLS-1$
		buffer.append("Bundle-RequiredExecutionEnvironment: J2SE-1.5" + nL); //$NON-NLS-1$
		buffer.append("Bundle-ActivationPolicy: lazy" + nL); //$NON-NLS-1$
		buffer.append("Eclipse-LazyStart: true" + nL); //$NON-NLS-1$
		buffer.append(nL);

		return buffer.toString();
	}
}
