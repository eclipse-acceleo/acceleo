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
package org.eclipse.acceleo.ide.ui.launching.strategy;

import java.io.File;
import java.util.List;

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.ecore.EObject;

/**
 * An internal extension point is defined to specify multiple launching strategies. It is used to define a
 * specific way of launching an Acceleo generation. The extension point "org.eclipse.acceleo.ide.ui.launching"
 * requires a fully qualified name of a Java class implementing this interface.
 * 
 * @see AcceleoPluginLaunchingStrategy
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface IAcceleoLaunchingStrategy {

	/**
	 * The identifier of the internal extension point specifying the implementation to use for launching
	 * strategy. It is used to define a specific way of launching an Acceleo generation.
	 */
	String LAUNCHING_STRATEGY_EXTENSION_ID = "org.eclipse.acceleo.ide.ui.launching"; //$NON-NLS-1$

	/**
	 * Launches the generation for a list of Acceleo templates.
	 * 
	 * @param module
	 *            the module
	 * @param templateNames
	 *            names of the module templates that are to be generated
	 * @param model
	 *            input model for these Acceleo templates
	 * @param arguments
	 *            arguments of the template call
	 * @param generationRoot
	 *            this will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory should be used as root.
	 */
	void doGenerate(Module module, List<String> templateNames, EObject model,
			List<? extends Object> arguments, File generationRoot);

}
