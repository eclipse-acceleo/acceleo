/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.collection;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;

/**
 * Reject {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class RejectService extends AbstractCollectionService {

	/**
	 * Constructor.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 */
	public RejectService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
		super(serviceMethod, serviceInstance, forWorkspace);
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final LambdaType lambdaType = (LambdaType)argTypes.get(1);
		final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
		if (isBooleanType(queryEnvironment, lambdaExpressionType)) {
			final Expression expression;
			if (call != null) {
				expression = ((Lambda)call.getArguments().get(1)).getExpression();
			} else {
				expression = null;
			}
			final Set<IType> inferredTypes;
			if (validationResult != null) {
				inferredTypes = validationResult.getInferredVariableTypes(expression, Boolean.FALSE).get(
						lambdaType.getLambdaEvaluatorName());
			} else {
				inferredTypes = null;
			}
			if (inferredTypes == null) {
				result.add(createReturnCollectionWithType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
			} else {
				for (IType inferredType : inferredTypes) {
					result.add(createReturnCollectionWithType(queryEnvironment, inferredType));
				}
			}
		} else {
			result.add(createReturnCollectionWithType(queryEnvironment, services.nothing(
					"expression in a reject must return a boolean")));
		}

		return result;
	}
}
