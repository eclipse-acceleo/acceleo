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
package org.eclipse.acceleo.traceability.spec;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.ModelFile;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.ModuleFile;
import org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This implementation of the {@link org.eclipse.acceleo.traceability.TraceabilityModel} will handle non
 * generated bits.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TraceabilityModelSpec extends TraceabilityModelImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getGeneratedFile(java.lang.String)
	 */
	@Override
	public GeneratedFile getGeneratedFile(String filePath) {
		for (GeneratedFile file : getGeneratedFiles()) {
			if (file.getPath().equals(filePath)) {
				return file;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getGeneratedFile(org.eclipse.acceleo.traceability.ModuleElement)
	 */
	@Override
	public GeneratedFile getGeneratedFile(ModuleElement moduleElement) {
		for (GeneratedFile file : getGeneratedFiles()) {
			if (file.getFileBlock().equals(moduleElement)) {
				return file;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getGenerationModule(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public ModuleFile getGenerationModule(Resource resource) {
		return getGenerationModule(resource.getURI().toString());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getGenerationModule(java.lang.String)
	 */
	@Override
	public ModuleFile getGenerationModule(String moduleURI) {
		for (ModuleFile file : getModules()) {
			if (file.getPath().equals(moduleURI)) {
				return file;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getInputModel(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public ModelFile getInputModel(Resource resource) {
		return getInputModel(resource.getURI().toString());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getInputModel(java.lang.String)
	 */
	@Override
	public ModelFile getInputModel(String modelURI) {
		for (ModelFile file : getModelFiles()) {
			if (file.getPath().equals(modelURI)) {
				return file;
			}
		}
		return null;
	}
}
