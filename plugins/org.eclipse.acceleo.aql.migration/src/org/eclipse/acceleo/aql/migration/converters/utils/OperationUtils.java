/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.converters.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.query.ast.AstFactory;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.ocl.ecore.CollectionType;
import org.eclipse.ocl.ecore.OperationCallExp;

/**
 * Service name resolver.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public final class OperationUtils {

	/**
	 * The operators services.
	 */
	private static final Map<String, String> OPERATORS_SERVICES = new HashMap<>();

	/**
	 * The services to translate.
	 */
	private static final Map<String, String> TRANSLATED_SERVICES = new HashMap<>();

	static {
		OPERATORS_SERVICES.put("not", AstBuilderListener.NOT_SERVICE_NAME);
		OPERATORS_SERVICES.put("<>", AstBuilderListener.DIFFERS_SERVICE_NAME);
		OPERATORS_SERVICES.put("=", AstBuilderListener.EQUALS_SERVICE_NAME);
		OPERATORS_SERVICES.put(">=", AstBuilderListener.GREATER_THAN_EQUAL_SERVICE_NAME);
		OPERATORS_SERVICES.put(">", AstBuilderListener.GREATER_THAN_SERVICE_NAME);
		OPERATORS_SERVICES.put("<=", AstBuilderListener.LESS_THAN_EQUAL_SERVICE_NAME);
		OPERATORS_SERVICES.put("<", AstBuilderListener.LESS_THAN_SERVICE_NAME);
		OPERATORS_SERVICES.put("/", AstBuilderListener.DIV_SERVICE_NAME);
		OPERATORS_SERVICES.put("*", AstBuilderListener.MULT_SERVICE_NAME);
		OPERATORS_SERVICES.put("-", AstBuilderListener.SUB_SERVICE_NAME);
		OPERATORS_SERVICES.put("+", AstBuilderListener.ADD_SERVICE_NAME);
		OPERATORS_SERVICES.put("and", AstBuilderListener.AND_OPERATOR);
		OPERATORS_SERVICES.put("or", AstBuilderListener.OR_OPERATOR);
		OPERATORS_SERVICES.put("xor", AstBuilderListener.XOR_OPERATOR);
		OPERATORS_SERVICES.put("implies", AstBuilderListener.IMPLIES_OPERATOR);

		TRANSLATED_SERVICES.put("toUpperCase", "toUpper");
		TRANSLATED_SERVICES.put("toLowerCase", "toLower");
		TRANSLATED_SERVICES.put("isAlphanum", "isAlphaNum");
		TRANSLATED_SERVICES.put("asBag", "asSequence");
	}

	private OperationUtils() {
	}

	/**
	 * Creates a call matching the given Operation call.
	 * 
	 * @param input
	 *            the input operation call
	 * @return the A4 call
	 */
	public static Call createCall(OperationCallExp input) {
		String operationName = getEOperationName(input.getReferredOperation());

		Call output = null;
		// create call
		if ("and".equals(operationName)) {
			output = AstFactory.eINSTANCE.createAnd();
		} else if ("or".equals(operationName)) {
			output = AstFactory.eINSTANCE.createOr();
		} else if ("implies".equals(operationName)) {
			output = AstFactory.eINSTANCE.createImplies();
		} else {
			output = AstFactory.eINSTANCE.createCall();
		}

		// set call name
		if (OPERATORS_SERVICES.containsKey(operationName)) {
			output.setServiceName(OPERATORS_SERVICES.get(operationName));
		} else if (TRANSLATED_SERVICES.containsKey(operationName)) {
			output.setServiceName(TRANSLATED_SERVICES.get(operationName));
		} else {
			output.setServiceName(operationName);
		}

		// set call type
		if (OPERATORS_SERVICES.containsKey(operationName)) {
			output.setType(CallType.CALLSERVICE);
		} else if (input.getSource().getType() instanceof CollectionType) {
			output.setType(CallType.COLLECTIONCALL);
		} else {
			output.setType(CallType.CALLORAPPLY);
		}
		return output;
	}

	private static String getEOperationName(EOperation referredOperation) {
		String res = null;
		if (referredOperation.eIsProxy()) {
			// This is the only solution, see
			// org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor.visitOperationCallExp
			URI uri = ((InternalEObject)referredOperation).eProxyURI();
			if (uri.fragment() != null && uri.fragment().endsWith("%2F")) { //$NON-NLS-1$
				res = "/";
			}
		} else {
			res = referredOperation.getName();
		}
		return res;
	}

}
