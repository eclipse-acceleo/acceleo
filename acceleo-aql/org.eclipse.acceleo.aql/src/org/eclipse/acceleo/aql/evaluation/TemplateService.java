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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Specific implementation of an IService wrapping an Acceleo Query.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TemplateService extends AbstractModuleElementService {

	/**
	 * Gets the result of the evaluation of a given {@link Template}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class TemplateEvaluationListener implements IAcceleoEvaluationListener {

		/**
		 * Listen to the evaluation of this {@link Template}.
		 */
		private final Template template;

		/**
		 * The evaluation result.
		 */
		private StringBuilder builder;

		/**
		 * The top level stop {@link ASTNode} if any, <code>null</code> otherwise.
		 */
		private ASTNode astNode = null;

		/**
		 * Constructor.
		 * 
		 * @param template
		 *            listen to the evaluation of this {@link Template}
		 */
		public TemplateEvaluationListener(Template template) {
			this.template = template;
		}

		@Override
		public void startEvaluation(ASTNode node, IAcceleoEnvironment environment,
				Map<String, Object> variables) {
			if (astNode == null && node.eClass() == AcceleoPackage.eINSTANCE.getFileStatement()
					|| astNode == null && node.eClass() == AcceleoPackage.eINSTANCE.getTemplate()) {
				astNode = node;
			}
		}

		@Override
		public void endEvaluation(ASTNode node, IAcceleoEnvironment environment,
				Map<String, Object> variables, Object result) {
			if (astNode == null) {
				if (node.eClass() == AcceleoPackage.eINSTANCE.getExpressionStatement() || node
						.eClass() == AcceleoPackage.eINSTANCE.getTextStatement()) {
					builder.append((String)result);
				}
			} else if (astNode == node) {
				astNode = null;
			}
		}

		/**
		 * Sets the {@link StringBuilder}.
		 * 
		 * @param builder
		 *            the {@link StringBuilder}
		 */
		public void setBuilder(StringBuilder builder) {
			this.builder = builder;
		}

	}

	/** The current evaluation environment. */
	private final AcceleoEnvironment env;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	private final AcceleoEvaluator acceleoEvaluator;

	/** The underlying template. */
	private final Template template;

	/**
	 * The {@link TemplateEvaluationListener}.
	 */
	private final TemplateEvaluationListener listener;

	/**
	 * Wraps the given template as an IService.
	 * 
	 * @param env
	 *            The current evaluation environment.
	 * @param template
	 *            The wrapped template.
	 */
	public TemplateService(AcceleoEnvironment env, Template template) {
		this.env = env;
		this.acceleoEvaluator = new AcceleoEvaluator(env);
		this.template = template;
		this.listener = new TemplateEvaluationListener(template);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.evaluation.AbstractModuleElementService#getModuleElement()
	 */
	@Override
	public Template getModuleElement() {
		return template;
	}

	@Override
	public String getModuleQualifiedName() {
		return env.getModuleQualifiedName((Module)template.eContainer());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.evaluation.AbstractModuleElementService#getVisibility()
	 */
	@Override
	public VisibilityKind getVisibility() {
		return template.getVisibility();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getName()
	 */
	@Override
	public String getName() {
		return template.getName();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment)
	 */
	@Override
	public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<IType> result = new ArrayList<IType>();
		final AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));
		for (Variable var : template.getParameters()) {
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
		return template.getParameters().size();
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
		result.add(new ClassType(queryEnvironment, String.class));
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
			Variable var = template.getParameters().get(i);
			variables.put(var.getName(), arguments[i]);
		}

		env.getEvaluationListeners().add(listener);
		final StringBuilder builder = new StringBuilder();
		listener.setBuilder(builder);
		try {
			acceleoEvaluator.generate(template, variables);
		} finally {
			env.getEvaluationListeners().remove(listener);
		}

		return builder.toString();
	}
}
