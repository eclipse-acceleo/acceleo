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
package org.eclipse.acceleo.engine.generation;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;

/**
 * Base implementation of the environment factory used by Acceleo.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public abstract class AbstractAcceleoEnvironmentFactory extends EcoreEnvironmentFactory {
	/**
	 * Delegates instantiation to the super constructor.
	 * 
	 * @param registry
	 *            Package registry known to the factory.
	 */
	public AbstractAcceleoEnvironmentFactory(EPackage.Registry registry) {
		super(registry);
	}

	/**
	 * This can be used to dispose of all resources loaded from this factory.
	 */
	public abstract void dispose();

	/**
	 * Returns the preview of the generation handled by this factory's generation context.
	 * 
	 * @return The preview of the generation handled by this factory's generation context.
	 */
	public abstract Map<String, String> getEvaluationPreview();

	/**
	 * This will be called by the engine once the generation has ended. It will be used internally to call for
	 * the current strategy's global handlers.
	 */
	public abstract void hookGenerationEnd();
}
