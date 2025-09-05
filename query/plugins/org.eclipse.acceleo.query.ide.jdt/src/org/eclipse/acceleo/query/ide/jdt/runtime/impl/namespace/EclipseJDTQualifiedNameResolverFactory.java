/*******************************************************************************
 * Copyright (c) 2020, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.jdt.runtime.impl.namespace;

import java.util.ArrayList;

import org.eclipse.acceleo.query.ide.runtime.impl.namespace.EclipseQualifiedNameResolver;
import org.eclipse.acceleo.query.ide.runtime.namespace.IQualifiedNameResolverFactory;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EPackage;

/**
 * The factory for {@link EclipseQualifiedNameResolver}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseJDTQualifiedNameResolverFactory implements IQualifiedNameResolverFactory {

	@Override
	public IQualifiedNameResolver createResolver(ClassLoader classLoader, EPackage.Registry ePackageRegistry,
			IProject project, String qualifierSeparator, boolean forWorkspace) {
		return new EclipseJDTQualifiedNameResolver(classLoader, ePackageRegistry, project, qualifierSeparator,
				forWorkspace, new ArrayList<>());
	}

	@Override
	public ILoader createJavaLoader(String qualifierSeparator, boolean forWorkspace) {
		return new EclipseJDTJavaLoader(qualifierSeparator, forWorkspace);
	}

}
