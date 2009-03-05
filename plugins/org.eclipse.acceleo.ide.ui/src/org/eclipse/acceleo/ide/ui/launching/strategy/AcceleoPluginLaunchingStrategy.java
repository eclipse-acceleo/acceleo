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

import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.ecore.EObject;

/**
 * Default Acceleo Plug-in launching strategy. It is used to launch an Acceleo application in the current
 * Eclipse thread.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoPluginLaunchingStrategy implements IAcceleoLaunchingStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.launching.strategy.IAcceleoLaunchingStrategy#doGenerate(org.eclipse.acceleo.model.mtl.Module,
	 *      java.util.List, org.eclipse.emf.ecore.EObject, java.util.List, java.io.File)
	 */
	public void doGenerate(Module module, List<String> templateNames, EObject model,
			List<? extends Object> arguments, File generationRoot) {
		for (int i = 0; i < templateNames.size(); i++) {
			AcceleoService.doGenerate(module, templateNames.get(i), model, arguments, generationRoot, false);
		}
	}

}
