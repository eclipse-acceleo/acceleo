/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryValidationEngine;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * {@link QueryValidationEngine} is the default query validation engine.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QueryValidationEngine implements IQueryValidationEngine {

	/**
	 * The environment containing all necessary information and used to execute query services.
	 */
	private IQueryEnvironment queryEnvironment;

	/**
	 * Constructor. It takes an {@link IQueryEnvironment} as parameter.
	 * 
	 * @param queryEnvironment
	 *            The environment containing all necessary information and used to execute query services.
	 */
	public QueryValidationEngine(IQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IQueryValidationEngine#validate(java.lang.String, java.util.Map)
	 */
	@Override
	public IValidationResult validate(String expression, Map<String, Set<IType>> variableTypes) {
		IQueryBuilderEngine builder = new QueryBuilderEngine(queryEnvironment);
		AstResult build = builder.build(expression);
		final AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));
		return validator.validate(variableTypes, build);
	}

}
