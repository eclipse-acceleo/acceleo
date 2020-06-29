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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
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
	 * Wraps the given template as an IService.
	 * 
	 * @param env
	 *            The current evaluation environment.
	 * @param template
	 *            The wrapped template.
	 */
	public TemplateService(AcceleoEnvironment env, Template template) {
		super(template, env);
	}

	@Override
	public VisibilityKind getVisibility() {
		return ((Template)getOrigin()).getVisibility();
	}

	@Override
	public String getName() {
		return ((Template)getOrigin()).getName();
	}

	@Override
	public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<IType> result = new ArrayList<IType>();
		final AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));
		for (Variable var : ((Template)getOrigin()).getParameters()) {
			IType rawType = validator.getDeclarationTypes(queryEnvironment, validator.validate(Collections
					.emptyMap(), var.getType()).getPossibleTypes(var.getType().getAst())).iterator().next();
			// TODO for now, using only the raw variable type, do we need special handling for collections?
			result.add(rawType);
		}
		return result;
	}

	@Override
	public int getNumberOfParameters() {
		return ((Template)getOrigin()).getParameters().size();
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		Set<IType> result = new LinkedHashSet<IType>();
		result.add(new ClassType(queryEnvironment, String.class));
		return result;
	}

	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		for (int i = 0; i < arguments.length; i++) {
			Variable var = ((Template)getOrigin()).getParameters().get(i);
			variables.put(var.getName(), arguments[i]);
		}

		return getEnv().getEvaluator().generate((Template)getOrigin(), variables);
	}
}
