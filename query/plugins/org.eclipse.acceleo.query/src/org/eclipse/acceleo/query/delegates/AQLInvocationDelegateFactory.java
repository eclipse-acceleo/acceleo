/*******************************************************************************
 * Copyright (c) 2016, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.delegates;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.QueryParsing;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate;
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate.Factory;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * An invocation delegate factory supporting AQL.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLInvocationDelegateFactory extends AbstractEnvironmentProvider implements Factory {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate.Factory#createInvocationDelegate(org.eclipse.emf.ecore.EOperation)
	 */
	@Override
	public InvocationDelegate createInvocationDelegate(EOperation operation) {
		final IQueryEnvironment env = getEnvironment();
		final String expression = EcoreUtil.getAnnotation(operation, AstPackage.eNS_URI, "body");

		final IQueryBuilderEngine engine = QueryParsing.newBuilder();
		final AstResult astResult = engine.build(expression);
		final List<String> parameterNames = new ArrayList<String>();
		for (EParameter parameter : operation.getEParameters()) {
			parameterNames.add(parameter.getName());
		}

		// TODO test if something went wrong

		return new AQLInvocationDelegate(env, astResult, parameterNames);
	}

}
