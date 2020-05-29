/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.jdt;

import org.eclipse.acceleo.aql.ide.Activator;
import org.eclipse.acceleo.aql.ide.resolver.EclipseQualifiedNameResolver;
import org.eclipse.acceleo.aql.ide.resolver.IQualifiedNameResolverFactory;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.core.resources.IProject;

/**
 * The factory for {@link EclipseQualifiedNameResolver}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseJDTQualifiedNameResolverFactory implements IQualifiedNameResolverFactory {

	@Override
	public EclipseQualifiedNameResolver createResolver(IReadOnlyQueryEnvironment queryEnvironment,
			IProject project) {
		return new EclipseJDTQualifiedNameResolver(Activator.INSTANCE.getClass().getClassLoader(),
				queryEnvironment, project);
	}

}
