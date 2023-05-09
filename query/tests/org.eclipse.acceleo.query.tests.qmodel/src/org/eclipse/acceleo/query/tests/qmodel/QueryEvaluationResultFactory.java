/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.qmodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

public class QueryEvaluationResultFactory {

	@SuppressWarnings("unchecked")
	public QueryEvaluationResult createFromValue(Object value) {
		Object actualValue = value;
		if (actualValue instanceof EvaluationResult) {
			actualValue = ((EvaluationResult)actualValue).getResult();
		}
		QueryEvaluationResult res = QmodelFactory.eINSTANCE.createEmptyResult();
		if (actualValue instanceof Set) {
			SetResult mtlResult = QmodelFactory.eINSTANCE.createSetResult();
			for (Object object : (Collection<Object>)actualValue) {
				mtlResult.getValues().add(createFromValue(object));
			}
			res = mtlResult;
		} else if (actualValue instanceof Collection) {
			ListResult mtlResult = QmodelFactory.eINSTANCE.createListResult();
			for (Object object : (Collection<Object>)actualValue) {
				mtlResult.getValues().add(createFromValue(object));
			}
			res = mtlResult;
		} else if (actualValue instanceof String) {
			StringResult strResult = QmodelFactory.eINSTANCE.createStringResult();
			strResult.setValue((String)actualValue);
			res = strResult;
		} else if (actualValue instanceof Integer) {
			IntegerResult bResult = QmodelFactory.eINSTANCE.createIntegerResult();
			bResult.setValue(((Integer)actualValue).intValue());
			res = bResult;
		} else if (actualValue instanceof Boolean) {
			BooleanResult bResult = QmodelFactory.eINSTANCE.createBooleanResult();
			bResult.setValue(((Boolean)actualValue).booleanValue());
			res = bResult;
		} else if (actualValue != null
				&& (actualValue.toString().equals("invalid") || actualValue.toString().equals("Nothing"))) {
			res = QmodelFactory.eINSTANCE.createInvalidResult();
		} else if (actualValue instanceof Nothing) {
			res = QmodelFactory.eINSTANCE.createInvalidResult();
		} else if (actualValue instanceof EObject) {
			EObjectResult mtlResult = QmodelFactory.eINSTANCE.createEObjectResult();
			mtlResult.setValue((EObject)actualValue);
			res = mtlResult;
		} else if (actualValue instanceof Enumerator) {
			EnumeratorResult sResult = QmodelFactory.eINSTANCE.createEnumeratorResult();
			sResult.setValue(((Enumerator)actualValue).getLiteral());
			res = sResult;
		} else if (actualValue instanceof Serializable) {
			SerializableResult sResult = QmodelFactory.eINSTANCE.createSerializableResult();
			sResult.setValue((Serializable)actualValue);
			res = sResult;
		}
		return res;

	}
}
