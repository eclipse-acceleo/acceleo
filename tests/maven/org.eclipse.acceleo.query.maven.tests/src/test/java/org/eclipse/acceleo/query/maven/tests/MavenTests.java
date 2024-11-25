/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.maven.tests;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.impl.QueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MavenTests {

	@Test
	public void maven() {
		// parsing
		AstResult astResult = new QueryBuilderEngine().build("self.name");

		assertEquals(Diagnostic.OK, astResult.getDiagnostic().getSeverity());
		assertEquals(0, astResult.getDiagnostic().getChildren().size());

		// validation
		IQueryEnvironment queryEnvironment = Query.newEnvironmentWithDefaultServices(null);

		Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();
		Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("self", selfTypes);
		AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));
		IValidationResult validationResult = validator.validate(variableTypes, astResult);

		assertEquals(0, validationResult.getMessages().size());

		// evaluation
		QueryEvaluationEngine engine = new QueryEvaluationEngine(queryEnvironment);
		Map<String, Object> variables = new HashMap<>();
		variables.put("self", EcorePackage.eINSTANCE);
		EvaluationResult evaluationResult = engine.eval(astResult, variables);

		assertEquals(Diagnostic.OK, evaluationResult.getDiagnostic().getSeverity());
		assertEquals(0, evaluationResult.getDiagnostic().getChildren().size());
		assertEquals("ecore", evaluationResult.getResult());
	}

}
