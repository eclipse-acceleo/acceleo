/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.runner;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.unit.core.annotation.CompiledModuleTest;
import org.eclipse.acceleo.unit.core.annotation.QueryTest;
import org.eclipse.acceleo.unit.core.annotation.TemplateTest;
import org.eclipse.acceleo.unit.core.generation.AbstractGenerationHelper;
import org.eclipse.acceleo.unit.core.generation.QueryGenerationHelper;
import org.eclipse.acceleo.unit.core.generation.TemplateGenerationHelper;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import static org.junit.Assert.fail;

/**
 * The acceleo runner class.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRunner extends BlockJUnit4ClassRunner {

	/**
	 * Creates an AcceleoRunner to run {@code klass} with {@code modelElement}.
	 * 
	 * @param klass
	 *            class to run.
	 * @throws InitializationError
	 *             if the test class is malformed.
	 */
	public AcceleoRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.BlockJUnit4ClassRunner#computeTestMethods()
	 */
	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		List<FrameworkMethod> methods = new ArrayList<FrameworkMethod>();
		methods.addAll(this.getTestClass().getAnnotatedMethods(TemplateTest.class));
		methods.addAll(this.getTestClass().getAnnotatedMethods(QueryTest.class));
		return methods;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.BlockJUnit4ClassRunner#validateConstructor(java.util.List)
	 */
	@Override
	protected void validateConstructor(List<Throwable> errors) {
		this.validateZeroArgConstructor(errors);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.BlockJUnit4ClassRunner#methodBlock(org.junit.runners.model.FrameworkMethod)
	 */
	@Override
	protected Statement methodBlock(FrameworkMethod method) {
		Object testObject = null;

		try {
			testObject = this.createTest();
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			fail(e.getMessage());
		}

		String qualifiedName = ""; //$NON-NLS-1$
		int index = 0;
		boolean isTemplate = false;
		boolean isQuery = false;
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(TemplateTest.class)) {
				isTemplate = true;
				qualifiedName = ((TemplateTest)annotation).qualifiedName();
				index = ((TemplateTest)annotation).index();
			} else if (annotation.annotationType().equals(QueryTest.class)) {
				qualifiedName = ((QueryTest)annotation).qualifiedName();
				index = ((QueryTest)annotation).index();
				isQuery = true;
			}
		}

		String modulePath = ""; //$NON-NLS-1$
		Annotation[] classAnnotations = this.getTestClass().getAnnotations();
		for (Annotation annotation : classAnnotations) {
			if (annotation.annotationType().equals(CompiledModuleTest.class)) {
				CompiledModuleTest compiledModuleTest = (CompiledModuleTest)annotation;
				modulePath = compiledModuleTest.emtl();
			}
		}

		AbstractGenerationHelper helper = null;
		if (isTemplate) {
			helper = new TemplateGenerationHelper(modulePath, index, qualifiedName);
		} else if (isQuery) {
			helper = new QueryGenerationHelper(modulePath, index, qualifiedName);
		}

		if (helper != null) {
			return new AcceleoStatement(testObject, method, helper);
		}
		return null;
	}

	@Override
	protected String getName() {
		return this.getTestClass().getName();
	}

	@Override
	protected String testName(FrameworkMethod method) {
		return super.testName(method);
	}

	@Override
	protected Object createTest() throws Exception {
		return super.createTest();
	}
}
