/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.compatibility;

import org.eclipse.emf.common.EMFPlugin;

/**
 * This convenience class will allow us to check the currently running version of OCL and remain compatible
 * through API breakages.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoCompatibilityHelper {
	/** Currently running version of OCL. */
	private static final OCLVersion CURRENT_VERSION;

	static {
		OCLVersion temp = OCLVersion.GANYMEDE;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			temp = AcceleoCompatibilityEclipseHelper.getCurrentOCLVersion();
		} else {
			// We'll need to use reflection here as we are running standalone
			try {
				Class.forName("org.eclipse.ocl.ecore.TemplateParameterType"); //$NON-NLS-1$
				temp = OCLVersion.HELIOS;
			} catch (ClassNotFoundException e) {
				// This can only be prior to Helios
			}
			if (temp == OCLVersion.GANYMEDE) {
				try {
					Class.forName("org.eclipse.ocl.TypeChecker"); //$NON-NLS-1$
					temp = OCLVersion.GALILEO;
				} catch (ClassNotFoundException e) {
					// This can only be prior to Galileo
				}
			}
		}
		CURRENT_VERSION = temp;
	}

	/** Utility classes don't need to be instantiated. */
	private AcceleoCompatibilityHelper() {
		// Hides default constructor
	}

	/**
	 * Returns the currently running version of OCL.
	 * 
	 * @return The current version of OCL.
	 */
	public static OCLVersion getCurrentVersion() {
		return CURRENT_VERSION;
	}
}
