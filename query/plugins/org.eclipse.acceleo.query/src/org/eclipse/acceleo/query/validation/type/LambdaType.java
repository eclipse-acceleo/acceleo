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
package org.eclipse.acceleo.query.validation.type;

import org.eclipse.acceleo.query.ast.Lambda;

/**
 * {@link Lambda} type.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class LambdaType extends AbstractJavaType implements IJavaType {

	/**
	 * The {@link IType} of this {@link Lambda#getEvaluator() evaluator} type.
	 */
	final IType lambdaEvaluatorType;

	/**
	 * The {@link IType} of this {@link Lambda#getExpression() expression} type.
	 */
	final IType lambdaExpressionType;

	/**
	 * Constructor.
	 * 
	 * @param lambdaEvaluatorType
	 *            the {@link IType} of this {@link Lambda#getEvaluator() evaluator} type
	 * @param lambdaExpressionType
	 *            the {@link IType} of this {@link Lambda#getExpression() expression} type
	 */
	public LambdaType(IType lambdaEvaluatorType, IType lambdaExpressionType) {
		this.lambdaEvaluatorType = lambdaEvaluatorType;
		this.lambdaExpressionType = lambdaExpressionType;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.IJavaType#getType()
	 */
	@Override
	public Class<?> getType() {
		return Lambda.class;
	}

	/**
	 * Gets the {@link IType} of this {@link Lambda#getEvaluator() evaluator} type.
	 * 
	 * @return the {@link IType} of this {@link Lambda#getEvaluator() evaluator} type
	 */
	public IType getLambdaEvaluatorType() {
		return lambdaEvaluatorType;
	}

	/**
	 * Gets the {@link IType} of this {@link Lambda#getExpression() expression} type.
	 * 
	 * @return the {@link IType} of this {@link Lambda#getExpression() expression} type
	 */
	public IType getLambdaExpressionType() {
		return lambdaExpressionType;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getType().hashCode() ^ getLambdaExpressionType().hashCode()
				^ getLambdaEvaluatorType().hashCode();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return getClass() == obj.getClass() && ((LambdaType)obj).getType().equals(getType())
				&& ((LambdaType)obj).getLambdaExpressionType().equals(getLambdaExpressionType())
				&& ((LambdaType)obj).getLambdaEvaluatorType().equals(getLambdaEvaluatorType());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Lambda(" + getLambdaEvaluatorType() + ", " + getLambdaExpressionType().toString() + ")";
	}

}
