/*******************************************************************************
 * Copyright (c) 2016, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.delegates;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.QueryParsing;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Internal.SettingDelegate;
import org.eclipse.emf.ecore.EStructuralFeature.Internal.SettingDelegate.Factory;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * A setting delegate factory supporting AQL.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLSettingDelegateFactory extends AbstractEnvironmentProvider implements Factory {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Internal.SettingDelegate.Factory#createSettingDelegate(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public SettingDelegate createSettingDelegate(EStructuralFeature eStructuralFeature) {
		final IQueryEnvironment env = getEnvironment();
		final String expression = EcoreUtil.getAnnotation(eStructuralFeature, AstPackage.eNS_URI,
				"derivation");

		final IQueryBuilderEngine engine = QueryParsing.newBuilder();
		final AstResult astResult = engine.build(expression);

		// TODO test if something went wrong

		return new AQLSettingDelegate(eStructuralFeature, env, astResult);
	}

}
