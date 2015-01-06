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
package org.eclipse.acceleo.query.type;

public class LambdaType extends Type {

	private Type resultType;

	private Type parameterType;

	public LambdaType(Type parameterType, Type resultType) {
		super(TypeId.LAMBDA);
		this.parameterType = parameterType;
		this.resultType = resultType;
	}

	public Type getParameterType() {
		return parameterType;
	}

	public Type getResultType() {
		return resultType;
	}

	@Override
	public Type merge(Type type) {
		if (type.getId() == TypeId.LAMBDA) {
			LambdaType other = (LambdaType)type;
			return new LambdaType(this.parameterType.merge(other.getParameterType()), this.resultType
					.merge(other.resultType.merge(resultType)));
		} else {
			return new Any();
		}
	}
}
