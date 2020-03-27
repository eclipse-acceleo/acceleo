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

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
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

	/**
	 * Gets the result of the evaluation of a given {@link Query}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class QueryEvaluationListener implements IAcceleoEvaluationListener {

		/**
		 * Listen to the evaluation of this {@link Query}.
		 */
		private final Query query;

		/**
		 * The evaluation result.
		 */
		private Object result;

		/**
		 * Constructor.
		 * 
		 * @param query
		 *            listen to the evaluation of this {@link Query}
		 */
		public QueryEvaluationListener(Query query) {
			this.query = query;
		}

		@Override
		public void startEvaluation(ASTNode node, IAcceleoEnvironment environment,
				Map<String, Object> variables) {
			// nothing to do here
		}

		@Override
		public void endEvaluation(ASTNode node, IAcceleoEnvironment environment,
				Map<String, Object> variables, Object result) {

		}

		/**
		 * Gets the evaluation result.
		 * 
		 * @return the evaluation result
		 */
		public Object getResult() {
			return result;
		}

	}

	/** The current evaluation environment. */
	private final AcceleoEnvironment env;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	private final AcceleoEvaluator acceleoEvaluator;

	/** The underlying query. */
	private final Query query;

	/**
	 * The {@link QueryEvaluationListener}.
	 */
	private final QueryEvaluationListener listener;

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
		this.acceleoEvaluator = new AcceleoEvaluator(env);
		this.query = query;
		this.listener = new QueryEvaluationListener(query);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.evaluation.AbstractModuleElementService#getModuleElement()
	 */
	@Override
	public Query getModuleElement() {
		return query;
	}

	@Override
	public String getModuleQualifiedName() {
		return env.getModuleQualifiedName((Module)query.eContainer());
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
		final AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));
		for (Variable var : query.getParameters()) {
			IType rawType = validator.getDeclarationTypes(queryEnvironment, validator.validate(null, var
					.getType()).getPossibleTypes(var.getType().getAst())).iterator().next();
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
		final AstValidator validator = new AstValidator(services);

		final Set<IType> result = validator.getDeclarationTypes(queryEnvironment, validator.validate(null,
				query.getType()).getPossibleTypes(query.getType().getAst()));

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#internalInvoke(java.lang.Object[])
	 */
	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		for (int i = 0; i < arguments.length; i++) {
			Variable var = query.getParameters().get(i);
			variables.put(var.getName(), arguments[i]);
		}

		env.getEvaluationListeners().add(listener);
		try {
			acceleoEvaluator.generate(query, variables);
		} finally {
			env.getEvaluationListeners().remove(listener);
		}

		return listener.getResult();
	}
}
