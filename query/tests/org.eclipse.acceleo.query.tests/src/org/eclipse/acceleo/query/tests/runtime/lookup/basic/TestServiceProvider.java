/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.AbstractService;
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

		@Override
		public String getName() {
			return SERVICE_NAME;
		}

		@Override
		public String getShortSignature() {
			return SERVICE_NAME;
		}

		@Override
		public String getLongSignature() {
			return SERVICE_NAME;
		}

		@Override
		public List<Set<IType>> computeParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
			final List<Set<IType>> result = new ArrayList<>();

			result.add(Collections.singleton(new ClassType(queryEnvironment, type1)));

			return result;
		}

		@Override
		public int getNumberOfParameters() {
			return 1;
		}

		@Override
		public int getPriority() {
			return priority1;
		}

		@Override
		public Set<IType> computeType(IReadOnlyQueryEnvironment queryEnvironment) {
			return null;
		}

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

		@Override
		public String getName() {
			return SERVICE_NAME;
		}

		@Override
		public String getShortSignature() {
			return SERVICE_NAME;
		}

		@Override
		public String getLongSignature() {
			return SERVICE_NAME;
		}

		@Override
		public List<Set<IType>> computeParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
			final List<Set<IType>> result = new ArrayList<>();

			result.add(Collections.singleton(new ClassType(queryEnvironment, type2)));

			return result;
		}

		@Override
		public int getNumberOfParameters() {
			return 1;
		}

		@Override
		public int getPriority() {
			return priority2;
		}

		@Override
		public Set<IType> computeType(IReadOnlyQueryEnvironment queryEnvironment) {
			return null;
		}

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
	public List<IService<?>> getServices(IReadOnlyQueryEnvironment queryEnvironment, boolean forWorkspace) {
		final List<IService<?>> result = new ArrayList<IService<?>>();

		result.add(new Service1(new Object()));
		result.add(new Service2(new Object()));

		return result;
	}

}
