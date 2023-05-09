/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.runtime.lookup.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractService;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Lower priority lower type {@link IServiceProvider}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TestServiceProvider implements IServiceProvider {

	public static final String SERVICE_NAME = "service";

	public class Service1 extends AbstractService<Object> {

		/**
		 * @param serviceOrigin
		 */
		protected Service1(Object serviceOrigin) {
			super(serviceOrigin);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getName()
		 */
		@Override
		public String getName() {
			return SERVICE_NAME;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getShortSignature()
		 */
		@Override
		public String getShortSignature() {
			return SERVICE_NAME;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getLongSignature()
		 */
		@Override
		public String getLongSignature() {
			return SERVICE_NAME;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment)
		 */
		@Override
		public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
			final List<IType> result = new ArrayList<IType>();

			result.add(new ClassType(queryEnvironment, type1));

			return result;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getNumberOfParameters()
		 */
		@Override
		public int getNumberOfParameters() {
			return 1;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getPriority()
		 */
		@Override
		public int getPriority() {
			return priority1;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getType(org.eclipse.acceleo.query.ast.Call,
		 *      org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      org.eclipse.acceleo.query.runtime.IValidationResult,
		 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.List)
		 */
		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			return null;
		}

		@Override
		public Set<IType> getType(IReadOnlyQueryEnvironment queryEnvironment) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#internalInvoke(java.lang.Object[])
		 */
		@Override
		protected Object internalInvoke(Object[] arguments) throws Exception {
			return null;
		}

	}

	public class Service2 extends AbstractService<Object> {

		/**
		 * @param serviceOrigin
		 */
		protected Service2(Object serviceOrigin) {
			super(serviceOrigin);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getName()
		 */
		@Override
		public String getName() {
			return SERVICE_NAME;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getShortSignature()
		 */
		@Override
		public String getShortSignature() {
			return SERVICE_NAME;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getLongSignature()
		 */
		@Override
		public String getLongSignature() {
			return SERVICE_NAME;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment)
		 */
		@Override
		public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
			final List<IType> result = new ArrayList<IType>();

			result.add(new ClassType(queryEnvironment, type2));

			return result;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getNumberOfParameters()
		 */
		@Override
		public int getNumberOfParameters() {
			return 1;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getPriority()
		 */
		@Override
		public int getPriority() {
			return priority2;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getType(org.eclipse.acceleo.query.ast.Call,
		 *      org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      org.eclipse.acceleo.query.runtime.IValidationResult,
		 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.List)
		 */
		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			return null;
		}

		@Override
		public Set<IType> getType(IReadOnlyQueryEnvironment queryEnvironment) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#internalInvoke(java.lang.Object[])
		 */
		@Override
		protected Object internalInvoke(Object[] arguments) throws Exception {
			return null;
		}

	}

	private final int priority1;

	private final Class<?> type1;

	private final int priority2;

	private final Class<?> type2;

	public TestServiceProvider(int priority1, Class<?> type1, int priority2, Class<?> type2) {
		this.priority1 = priority1;
		this.type1 = type1;
		this.priority2 = priority2;
		this.type2 = type2;
	}

	@Override
	public List<IService<?>> getServices(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<IService<?>> result = new ArrayList<IService<?>>();

		result.add(new Service1(new Object()));
		result.add(new Service2(new Object()));

		return result;
	}

}
