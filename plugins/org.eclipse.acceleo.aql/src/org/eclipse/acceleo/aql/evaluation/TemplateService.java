/*******************************************************************************
 * Copyright (c) 2016, 2023  Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.completion.proposals.TemplateServiceCompletionProposal;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Specific implementation of an IService wrapping an Acceleo Query.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TemplateService extends AbstractModuleElementService<Template> {

	/**
	 * Wraps the given template as an IService.
	 * 
	 * @param template
	 *            the (non-{@code null}) {@link Template} wrapped by this service.
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 * @param contextQualifiedName
	 *            the qualified name containing this service
	 */
	public TemplateService(Template template, AcceleoEvaluator evaluator,
			IQualifiedNameLookupEngine lookupEngine, String contextQualifiedName) {
		super(template, evaluator, lookupEngine, contextQualifiedName);
	}

	@Override
	protected Visibility getVisibility(Template template) {
		return getVisibility(template.getVisibility());
	}

	@Override
	public String getName() {
		return getOrigin().getName();
	}

	@Override
	public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<IType> result = new ArrayList<IType>();
		final AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));
		for (Variable var : getOrigin().getParameters()) {
			IType rawType = validator.getDeclarationTypes(queryEnvironment, validator.validate(Collections
					.emptyMap(), var.getType()).getPossibleTypes(var.getType().getAst())).iterator().next();
			// TODO for now, using only the raw variable type, do we need special handling for collections?
			result.add(rawType);
		}
		return result;
	}

	@Override
	public int getNumberOfParameters() {
		return getOrigin().getParameters().size();
	}

	@Override
	public Set<IType> getType(IReadOnlyQueryEnvironment queryEnvironment) {
		Set<IType> result = new LinkedHashSet<IType>();
		result.add(new ClassType(queryEnvironment, String.class));

		return result;
	}

	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		for (int i = 0; i < arguments.length; i++) {
			Variable var = getOrigin().getParameters().get(i);
			variables.put(var.getName(), arguments[i]);
		}

		final AcceleoEvaluator evaluator = getEvaluator();
		return evaluator.generate(getOrigin(), variables, evaluator.getGenerationStrategy(), evaluator
				.getDestination());
	}

	@Override
	public List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
			Set<IType> receiverTypes) {
		return Collections.singletonList(new TemplateServiceCompletionProposal(this));
	}

}
