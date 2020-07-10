/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.editor;

import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.ide.Activator;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

public class AcceleoEnvResourceFactory extends ResourceFactoryImpl {

	/**
	 * The environment used to resolve modules.
	 */
	private AcceleoEnvironment environment;

	public AcceleoEnvResourceFactory(IProject project) {
		environment = new AcceleoEnvironment(new DefaultGenerationStrategy(), URI.createURI("TMP")); //$NON-NLS-1$
		environment.setModuleResolver(Activator.getPlugin().createQualifiedNameResolver(environment
				.getQueryEnvironment(), project));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Resource createResource(URI uri) {
		String qualifiedName = uri.toString().replaceFirst(AcceleoParser.ACCELEOENV_URI_PROTOCOL, ""); //$NON-NLS-1$
		org.eclipse.acceleo.Module module = environment.getModule(qualifiedName);
		return module.eResource();
	}
}
