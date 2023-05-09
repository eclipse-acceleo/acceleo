/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.services.tests.AbstractEngineInitializationWithCrossReferencer;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.qmodel.EObjectVariable;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultFactory;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult;
import org.eclipse.acceleo.query.tests.qmodel.Severity;
import org.eclipse.acceleo.query.tests.qmodel.ValidationMessage;
import org.eclipse.acceleo.query.tests.qmodel.Variable;
import org.eclipse.acceleo.query.tests.qmodel.util.QmodelSwitch;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.UMLPackage;

public class AcceleoQueryInterpreter extends AbstractEngineInitializationWithCrossReferencer implements InterpreterUnderTest {

	private Logger logger = Logger.getLogger("AcceleoQueryInterpreter");

	private final String expressionToEvaluate;

	private final EObject startingPoint;

	private final Map<String, Object> variables;

	private AstResult astResult;

	private final QmodelSwitch<EObject> varValueSwitch = new QmodelSwitch<EObject>() {
		@Override
		public EObject caseVariable(Variable object) {
			throw new UnsupportedOperationException("Unsupported variable kind in Query tests: " + object
					.eClass().getName());
		}

		@Override
		public EObject caseEObjectVariable(EObjectVariable object) {
			return object.getValue().getTarget();
		}
	};

	private final QmodelSwitch<Set<IType>> varTypeSwitch = new QmodelSwitch<Set<IType>>() {
		@Override
		public Set<IType> caseVariable(Variable object) {
			throw new UnsupportedOperationException("Unsupported variable kind in Query tests: " + object
					.eClass().getName());
		}

		@Override
		public Set<IType> caseEObjectVariable(EObjectVariable object) {
			Set<IType> types = new LinkedHashSet<IType>();
			types.add(new EClassifierType(queryEnvironment, object.getValue().getTarget().eClass()));
			return types;
		}
	};

	public AcceleoQueryInterpreter(Query q) {
		expressionToEvaluate = stripQuery(q.getExpression());
		startingPoint = q.getStartingPoint().getTarget();
		setQueryEnvironnementWithCrossReferencer(startingPoint);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		queryEnvironment.registerCustomClassMapping(EcorePackage.eINSTANCE.getEStringToStringMapEntry(),
				EStringToStringMapEntryImpl.class);
		queryEnvironment.registerEPackage(UMLPackage.eINSTANCE);
		queryEnvironment.registerEPackage(TypesPackage.eINSTANCE);

		for (String classToImport : q.getClassesToImport()) {
			try {
				final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(), Class
						.forName(classToImport));
				ServiceUtils.registerServices(queryEnvironment, services);
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, "couldn't register class " + classToImport, e);
			}
		}
		variables = new HashMap<>();
		variables.put("self", startingPoint);
		for (Variable var : q.getVariables()) {
			variables.put(var.getName(), varValueSwitch.doSwitch(var));
		}
	}

	/**
	 * Returns the query stripped from the outermost enclosing brackets.
	 * 
	 * @param query
	 *            the query to strip
	 * @return the stripped query.
	 */
	private String stripQuery(String query) {
		if (query.startsWith("[")) {
			return query.substring(1, query.length() - 2);
		} else {
			return query;
		}

	}

	@Override
	public void compileQuery(Query query) {
		IQueryBuilderEngine builder = new QueryBuilderEngine();
		// TODO test build.getErrors()
		AstResult build = builder.build(expressionToEvaluate);
		astResult = build;
	}

	@Override
	public QueryEvaluationResult computeQuery(Query query) {
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(queryEnvironment));
		Object result = evaluator.eval(variables, astResult.getAst());
		QueryEvaluationResultFactory factory = new QueryEvaluationResultFactory();

		return factory.createFromValue(result);
	}

	@Override
	public QueryValidationResult validateQuery(Query q) {
		for (String classToImport : q.getClassesToImport()) {
			try {
				final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(), Class
						.forName(classToImport));
				ServiceUtils.registerServices(queryEnvironment, services);
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, "couldn't register class " + classToImport, e);
			}
		}
		Map<String, Set<IType>> variableTypes = new HashMap<>();
		Set<IType> startingTypes = new LinkedHashSet<IType>();
		startingTypes.add(new EClassifierType(queryEnvironment, startingPoint.eClass()));
		variableTypes.put("self", startingTypes);
		for (Variable var : q.getVariables()) {
			variableTypes.put(var.getName(), varTypeSwitch.doSwitch(var));
		}
		final AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));

		return transformResult(q, validator.validate(variableTypes, astResult));
	}

	private QueryValidationResult transformResult(Query query, IValidationResult validationResult) {
		final QueryValidationResult result = QmodelPackage.eINSTANCE.getQmodelFactory()
				.createQueryValidationResult();

		result.setInterpreter("aql");
		for (IType possibleType : validationResult.getPossibleTypes(astResult.getAst())) {
			result.getPossibleTypes().add(possibleType.toString());
		}

		for (IValidationMessage msg : validationResult.getMessages()) {
			final ValidationMessage message = QmodelPackage.eINSTANCE.getQmodelFactory()
					.createValidationMessage();
			switch (msg.getLevel()) {
				case INFO:
					message.setSeverity(Severity.INFO);
					break;

				case WARNING:
					message.setSeverity(Severity.WARNING);
					break;

				case ERROR:
					message.setSeverity(Severity.ERROR);
					break;

				default:
					throw new IllegalStateException();
			}
			message.setStartPosition(msg.getStartPosition());
			message.setEndPosition(msg.getEndPosition());
			message.setMessage(msg.getMessage());
			result.getValidationMessages().add(message);
		}

		return result;
	}

}
