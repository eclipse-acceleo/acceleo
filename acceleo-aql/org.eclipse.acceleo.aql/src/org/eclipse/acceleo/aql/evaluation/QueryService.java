/*******************************************************************************
 * Copyright (c) 2016, 2017  Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Specific implementation of an IService wrapping an Acceleo Query.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class QueryService extends AbstractModuleElementService {
	/** The current evaluation environment. */
	private final AcceleoEvaluationEnvironment env;

	/** The underlying query. */
	private final Query query;

	/**
	 * Wraps the given query as an IService.
	 * 
	 * @param env
	 *            The current evaluation environment.
	 * @param query
	 *            The wrapped query.
	 */
	public QueryService(AcceleoEvaluationEnvironment env, Query query) {
		this.env = env;
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.evaluation.AbstractModuleElementService#getModuleElement()
	 */
	@Override
	public ModuleElement getModuleElement() {
		return query;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.evaluation.AbstractModuleElementService#getVisibility()
	 */
	@Override
	public VisibilityKind getVisibility() {
		return query.getVisibility();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getName()
	 */
	@Override
	public String getName() {
		return query.getName();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment)
	 */
	@Override
	public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		List<IType> result = new ArrayList<IType>();
		for (Variable var : query.getParameters()) {
			EClassifierType rawType = new EClassifierType(queryEnvironment, var.getType());
			// TODO for now, using only the raw variable type, do we need special handling for collections?
			result.add(rawType);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getNumberOfParameters()
	 */
	@Override
	public int getNumberOfParameters() {
		return query.getParameters().size();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getType(org.eclipse.acceleo.query.ast.Call,
	 *      org.eclipse.acceleo.query.runtime.impl.ValidationServices,
	 *      org.eclipse.acceleo.query.runtime.IValidationResult,
	 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.List)
	 */
	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		Set<IType> result = new LinkedHashSet<IType>();
		result.add(new EClassifierType(queryEnvironment, query.getType()));
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#internalInvoke(java.lang.Object[])
	 */
	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		// FIXME parameters
		return new EvaluationSwitch(env).doSwitch(query);
	}
}
