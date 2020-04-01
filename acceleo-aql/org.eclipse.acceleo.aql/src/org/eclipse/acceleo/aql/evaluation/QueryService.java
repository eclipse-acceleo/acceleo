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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Specific implementation of an IService wrapping an Acceleo Query.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class QueryService extends AbstractModuleElementService {

	/** The current evaluation environment. */
	private final AcceleoEnvironment env;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	private final AcceleoEvaluator acceleoEvaluator;

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
	public QueryService(AcceleoEnvironment env, Query query) {
		this.env = env;
		this.acceleoEvaluator = new AcceleoEvaluator();
		this.query = query;
	}

	@Override
	public Query getModuleElement() {
		return query;
	}

	@Override
	public String getModuleQualifiedName() {
		return env.getModuleQualifiedName((Module)query.eContainer());
	}

	@Override
	public VisibilityKind getVisibility() {
		return query.getVisibility();
	}

	@Override
	public String getName() {
		return query.getName();
	}

	@Override
	public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		List<IType> result = new ArrayList<IType>();
		final AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));
		for (Variable var : query.getParameters()) {
			IType rawType = validator.getDeclarationTypes(queryEnvironment, validator.validate(null, var
					.getType()).getPossibleTypes(var.getType().getAst())).iterator().next();
			// TODO for now, using only the raw variable type, do we need special handling for collections?
			result.add(rawType);
		}
		return result;
	}

	@Override
	public int getNumberOfParameters() {
		return query.getParameters().size();
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final AstValidator validator = new AstValidator(services);

		final Set<IType> result = validator.getDeclarationTypes(queryEnvironment, validator.validate(null,
				query.getType()).getPossibleTypes(query.getType().getAst()));

		return result;
	}

	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		for (int i = 0; i < arguments.length; i++) {
			Variable var = query.getParameters().get(i);
			variables.put(var.getName(), arguments[i]);
		}

		return acceleoEvaluator.generate(env, query, variables);
	}
}
