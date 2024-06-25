/*******************************************************************************
 * Copyright (c) 2016, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.delegates;

import java.util.Map;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.QueryParsing;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.util.QueryDelegate;
import org.eclipse.emf.ecore.util.QueryDelegate.Factory;

/**
 * A query delegate factory supporting AQL.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLQueryDelegateFactory extends AbstractEnvironmentProvider implements Factory {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.util.QueryDelegate.Factory#createQueryDelegate(org.eclipse.emf.ecore.EClassifier,
	 *      java.util.Map, java.lang.String)
	 */
	@Override
	public QueryDelegate createQueryDelegate(EClassifier context, Map<String, EClassifier> parameters,
			String expression) {
		final IQueryEnvironment env = getEnvironment();

		final IQueryBuilderEngine engine = QueryParsing.newBuilder();
		final AstResult astResult = engine.build(expression);

		if (astResult.getDiagnostic().getSeverity() == Diagnostic.ERROR) {
			final StringBuilder messages = new StringBuilder();
			for (Diagnostic child : astResult.getDiagnostic().getChildren()) {
				messages.append("\n" + child.getMessage());
			}
			throw new IllegalArgumentException("Unable to parse \"" + expression + "\"" + messages
					.toString());
		}

		return new AQLQueryDelegate(env, astResult);
	}

}
