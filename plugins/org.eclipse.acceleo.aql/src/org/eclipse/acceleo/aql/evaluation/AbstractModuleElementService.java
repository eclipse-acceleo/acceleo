/*******************************************************************************
 * Copyright (c) 2016, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.List;

import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.query.runtime.impl.namespace.AbstractQualifiedNameService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Abstract implementation of a service that can wrap an Acceleo module element for AQL uses.
 * 
 * @param <O>
 *            the kind of {@link ModuleElement}
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public abstract class AbstractModuleElementService<O extends ModuleElement> extends AbstractQualifiedNameService<O> {

	/**
	 * The {@link Visibility}.
	 */
	private final Visibility visibility;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	private final AcceleoEvaluator evaluator;

	/**
	 * Constructor.
	 * 
	 * @param moduleElement
	 *            the (non-{@code null}) {@link ModuleElement} wrapped by this service.
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 * @param contextQualifiedName
	 *            the qualified name containing this service
	 */
	public AbstractModuleElementService(O moduleElement, AcceleoEvaluator evaluator,
			IQualifiedNameLookupEngine lookupEngine, String contextQualifiedName) {
		super(moduleElement, lookupEngine, contextQualifiedName);
		this.visibility = getVisibility(moduleElement);
		this.evaluator = evaluator;
	}

	/**
	 * Gets the {@link Visibility} from the given {@link VisibilityKind}.
	 * 
	 * @param visibilityKind
	 *            the {@link VisibilityKind}
	 * @return the {@link Visibility} from the given {@link VisibilityKind}
	 */
	protected Visibility getVisibility(VisibilityKind visibilityKind) {
		final Visibility res;

		switch (visibilityKind) {
			case PRIVATE:
				res = Visibility.PRIVATE;
				break;
			case PROTECTED:
				res = Visibility.PROTECTED;
				break;
			case PUBLIC:
				res = Visibility.PUBLIC;
				break;

			default:
				res = Visibility.PUBLIC;
				break;
		}

		return res;
	}

	/**
	 * Gets the {@link AcceleoEvaluator}.
	 * 
	 * @return the {@link AcceleoEvaluator}
	 */
	protected AcceleoEvaluator getEvaluator() {
		return evaluator;
	}

	/**
	 * Gets the {@link Visibility} of the given {@link ModuleElement}.
	 * 
	 * @param moduleElement
	 *            the {@link ModuleElement}
	 * @return the {@link Visibility} of the given {@link ModuleElement}
	 */
	protected abstract Visibility getVisibility(O moduleElement);

	@Override
	public Visibility getVisibility() {
		return visibility;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getShortSignature()
	 */
	@Override
	public String getShortSignature() {
		final List<IType> parameterTypes = getParameterTypes(getLookupEngine().getQueryEnvironment());
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
		String namespace = getContextQualifiedName();
		if (namespace != null) {
			return namespace + "::" + getShortSignature();
		}
		return getShortSignature();
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
				&& getOrigin() == ((AbstractModuleElementService<?>)obj).getOrigin();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getOrigin().hashCode();
	}

}
