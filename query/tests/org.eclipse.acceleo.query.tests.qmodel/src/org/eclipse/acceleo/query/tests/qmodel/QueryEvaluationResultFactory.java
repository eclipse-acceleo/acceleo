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
package org.eclipse.acceleo.query.tests.qmodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;

public class QueryEvaluationResultFactory {

	@SuppressWarnings("unchecked")
	public QueryEvaluationResult createFromValue(Object value) {
		QueryEvaluationResult res = QmodelFactory.eINSTANCE.createEmptyResult();
		if (value instanceof Set) {
			SetResult mtlResult = QmodelFactory.eINSTANCE.createSetResult();
			for (Object object : (Collection<Object>)value) {
				mtlResult.getValues().add(createFromValue(object));
			}
			res = mtlResult;
		} else if (value instanceof Collection) {
			ListResult mtlResult = QmodelFactory.eINSTANCE.createListResult();
			for (Object object : (Collection<Object>)value) {
				mtlResult.getValues().add(createFromValue(object));
			}
			res = mtlResult;
		} else if (value instanceof String) {
			StringResult strResult = QmodelFactory.eINSTANCE.createStringResult();
			strResult.setValue((String)value);
			res = strResult;
		} else if (value instanceof Integer) {
			IntegerResult bResult = QmodelFactory.eINSTANCE.createIntegerResult();
			bResult.setValue(((Integer)value).intValue());
			res = bResult;
		} else if (value instanceof Boolean) {
			BooleanResult bResult = QmodelFactory.eINSTANCE.createBooleanResult();
			bResult.setValue(((Boolean)value).booleanValue());
			res = bResult;
		} else if (value != null
				&& (value.toString().equals("invalid") || value.toString().equals("Nothing"))) {
			res = QmodelFactory.eINSTANCE.createInvalidResult();
		} else if (value instanceof EObject) {
			EObjectResult mtlResult = QmodelFactory.eINSTANCE.createEObjectResult();
			mtlResult.setValue((EObject)value);
			res = mtlResult;
		} else if (value instanceof Enumerator) {
			EnumeratorResult sResult = QmodelFactory.eINSTANCE.createEnumeratorResult();
			sResult.setValue(((Enumerator)value).getLiteral());
			res = sResult;
		} else if (value instanceof Serializable) {
			SerializableResult sResult = QmodelFactory.eINSTANCE.createSerializableResult();
			sResult.setValue((Serializable)value);
			res = sResult;
		}
		return res;

	}
}
