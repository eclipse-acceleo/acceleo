/*******************************************************************************
 * Copyright (c) 2016, 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.List;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.query.runtime.impl.AbstractService;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Abstract implementation of a service that can wrap an Acceleo module element for AQL uses.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public abstract class AbstractModuleElementService extends AbstractService {
	/**
	 * Returns the wrapped module element.
	 * 
	 * @return The wrapped module element.
	 */
	public abstract ModuleElement getModuleElement();

	/**
	 * Returns the underlying element's visibility if any.
	 * 
	 * @return The underlying element's visibility if any.
	 */
	public abstract VisibilityKind getVisibility();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getShortSignature()
	 */
	@Override
	public String getShortSignature() {
		final List<IType> parameterTypes = getParameterTypes(null);
		final IType[] argumentTypes = parameterTypes.toArray(new IType[parameterTypes.size()]);

		return serviceShortSignature(argumentTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getLongSignature()
	 */
	@Override
	public String getLongSignature() {
		String namespace = getNamespace();
		if (namespace != null) {
			return namespace + "::" + getShortSignature();
		}
		return getShortSignature();
	}

	/**
	 * Returns the namespace of the underlying module element. This is used to recreate its qualified name.
	 * 
	 * @return The namespace of the underlying module element.
	 */
	private String getNamespace() {
		String result = null;
		Resource res = getModuleElement().eResource();
		if (res != null) {
			result = res.getURI().toString();
		} else {
			EObject container = getModuleElement().eContainer();
			while (!(container instanceof Module)) {
				container = container.eContainer();
			}

			if (container instanceof Module) {
				result = ((Module)container).getName();
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof AbstractModuleElementService
				&& getModuleElement() == ((AbstractModuleElementService)obj).getModuleElement();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getModuleElement().hashCode();
	}
}
