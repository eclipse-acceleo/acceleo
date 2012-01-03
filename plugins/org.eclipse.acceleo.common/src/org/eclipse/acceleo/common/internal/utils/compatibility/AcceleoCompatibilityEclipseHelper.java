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

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;

/**
 * Eclipse specific utilities for the OCL compatibility.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoCompatibilityEclipseHelper {
	/** Unique ID of the OCL bundle. */
	private static final String OCL_BUNDLE_ID = "org.eclipse.ocl"; //$NON-NLS-1$

	/** hides default constructor. */
	private AcceleoCompatibilityEclipseHelper() {
		// prevents instantiation
	}

	/**
	 * Returns the current OCL version using OSGi utilities.
	 * 
	 * @return the current OCL version.
	 */
	public static OCLVersion getCurrentOCLVersion() {
		OCLVersion version = OCLVersion.GANYMEDE;
		Object oclVersion = Platform.getBundle(OCL_BUNDLE_ID).getHeaders().get("Bundle-Version"); //$NON-NLS-1$
		if (oclVersion instanceof String) {
			Version oclOSGiVersion = Version.parseVersion((String)oclVersion);
			if (oclOSGiVersion.getMajor() == 1 && oclOSGiVersion.getMinor() >= 3) {
				version = OCLVersion.GALILEO;
			} else if (oclOSGiVersion.getMajor() > 1) {
				version = OCLVersion.HELIOS;
			}
		}
		return version;
	}
}
