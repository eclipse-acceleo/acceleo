/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.delegates;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicSettingDelegate;

/**
 * A setting delegate supporting AQL.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLSettingDelegate extends BasicSettingDelegate.Stateless {

	/**
	 * The {@link IQueryEvaluationEngine}.
	 */
	private final IQueryEvaluationEngine engine;

	/**
	 * The {@link AstResult}.
	 */
	private final AstResult astResult;

	/**
	 * Constructor.
	 * 
	 * @param eStructuralFeature
	 *            the {@link EStructuralFeature}
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 * @param astResult
	 *            the {@link AstResult}
	 */
	public AQLSettingDelegate(EStructuralFeature eStructuralFeature, IQueryEnvironment queryEnvironment,
			AstResult astResult) {
		super(eStructuralFeature);
		engine = QueryEvaluation.newEngine(queryEnvironment);
		this.astResult = astResult;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.util.BasicSettingDelegate.Stateless#get(org.eclipse.emf.ecore.InternalEObject,
	 *      boolean, boolean)
	 */
	@Override
	protected Object get(InternalEObject owner, boolean resolve, boolean coreType) {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", owner);

		final EvaluationResult evaluationResult = engine.eval(astResult, variables);

		if (evaluationResult.getDiagnostic().getSeverity() != Diagnostic.OK) {
			final StringBuilder messages = new StringBuilder();
			for (Diagnostic child : evaluationResult.getDiagnostic().getChildren()) {
				messages.append("\n" + child.getMessage());
			}
			throw new IllegalArgumentException("Unable to evaluate feature \"" + eStructuralFeature.getName()
					+ "\"" + messages.toString());
		}

		return evaluationResult.getResult();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.util.BasicSettingDelegate.Stateless#isSet(org.eclipse.emf.ecore.InternalEObject)
	 */
	@Override
	protected boolean isSet(InternalEObject owner) {
		// since the setting is derived it should not be setted in anyway.
		return false;
	}

}
