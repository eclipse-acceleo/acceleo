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
package org.eclipse.acceleo.internal.ide.ui.launching;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;

/**
 * Constant definitions for Acceleo launch configurations.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
public interface IAcceleoLaunchConfigurationConstants {

	/**
	 * Identifier for the Acceleo Application launch configuration type.
	 */
	String ID_ACCELEO_APPLICATION = "org.eclipse.acceleo.ide.ui.launching.launchConfigurationType"; //$NON-NLS-1$

	/**
	 * Launch configuration attribute key. The value is the path of the model.
	 */
	String ATTR_MODEL_PATH = AcceleoUIActivator.PLUGIN_ID + ".MODEL_PATH"; //$NON-NLS-1$

	/**
	 * Launch configuration attribute key. The value is the path of the model.
	 */
	String ATTR_PROFILE_MODEL_PATH = AcceleoUIActivator.PLUGIN_ID + ".PROFILE_MODEL_PATH"; //$NON-NLS-1$

	/**
	 * Launch configuration attribute key. The value is the path of the target folder.
	 */
	String ATTR_TARGET_PATH = AcceleoUIActivator.PLUGIN_ID + ".TARGET_PATH"; //$NON-NLS-1$

	/**
	 * Launch configuration attribute key. The value is true if we would like to compute the traceability
	 * information.
	 */
	String ATTR_COMPUTE_TRACEABILITY = AcceleoUIActivator.PLUGIN_ID + ".COMPUTE_TRACEABILITY"; //$NON-NLS-1$

	/**
	 * Launch configuration attribute key. The value is true if we would like to compute the profiling
	 * information.
	 */
	String ATTR_COMPUTE_PROFILING = AcceleoUIActivator.PLUGIN_ID + ".COMPUTE_PROFILING"; //$NON-NLS-1$

	/**
	 * Launch configuration attribute key. This is the value of the code generation arguments.
	 */
	String ATTR_ARGUMENTS = AcceleoUIActivator.PLUGIN_ID + ".ARGUMENTS"; //$NON-NLS-1$

	/**
	 * Launch configuration attribute key. This is the value of the launching strategy.
	 */
	String ATTR_LAUNCHING_STRATEGY_DESCRIPTION = AcceleoUIActivator.PLUGIN_ID
			+ ".LAUNCHING_STRATEGY_DESCRIPTION"; //$NON-NLS-1$

}
