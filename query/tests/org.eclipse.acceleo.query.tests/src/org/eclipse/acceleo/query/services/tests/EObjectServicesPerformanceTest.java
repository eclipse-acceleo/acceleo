/*******************************************************************************
 * Copyright (c) 2016, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.tests;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.impl.QueryEvaluationEngine;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Benchmarks for EObject Services
 * 
 * @author cbrun
 */
public class EObjectServicesPerformanceTest {

	/**
	 * 
	 */
	private static final int NB_ITERATIONS = 400;

	public static Resource reverseEcoreModel;

	private IQueryEnvironment queryEnvironment;

	private EObjectServices eObjectService;

	@BeforeClass
	public static void loadModel() throws Exception {
		reverseEcoreModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverseEcore();
	}

	@Before
	public void before() throws Exception {
		this.queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		this.queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		this.eObjectService = new EObjectServices(queryEnvironment, null, null);
	}

	@Test
	public void eAllContentsWithFailingAccessBenchmark() {
		Stopwatch noError = Stopwatch.createUnstarted();
		Stopwatch error = Stopwatch.createUnstarted();
		int iterations = 20;
		noError.start();
		for (int i = 0; i < iterations; i++) {
			for (EObject root : reverseEcoreModel.getContents()) {
				EvaluationResult result = eval(root,
						"self.eAllContents(ecore::EClass)->select(c | not c.abstract)");
				assertTrue(result.getResult() instanceof Collection);
				assertEquals(5219, ((Collection<?>)result.getResult()).size());
			}
		}
		noError.stop();
		error.start();
		for (int i = 0; i < iterations; i++) {
			for (EObject root : reverseEcoreModel.getContents()) {
				EvaluationResult result = eval(root, "self.eAllContents()->select(c | not c.abstract)");
				assertTrue(result.getResult() instanceof Collection);
				assertEquals(5219, ((Collection<?>)result.getResult()).size());
			}
		}
		error.stop();
		System.out.println("PERFO: self.eAllContents(ecore::EClass)->select(c | not c.abstract)  :  "
				+ noError.elapsed(TimeUnit.MILLISECONDS)
				+ "ms /  self.eAllContents()->select(c | not c.abstract) : " + error.elapsed(
						TimeUnit.MILLISECONDS) + "ms");

		final double noErrorElapsed = noError.elapsed(TimeUnit.MILLISECONDS);
		final double errorElapsed = error.elapsed(TimeUnit.MILLISECONDS);

		assertTrue("We expect a maximum overhead of 1800% and we had:" + (errorElapsed / noErrorElapsed) * 100
				+ "%", errorElapsed / noErrorElapsed < 18.0);
	}

	/**
	 * @param root
	 * @param expr
	 * @return
	 */
	private EvaluationResult eval(EObject root, String expr) {
		IQueryBuilderEngine queryBuilder = new QueryBuilderEngine();
		AstResult query = queryBuilder.build(expr);
		IQueryEvaluationEngine evaluationEngine = new QueryEvaluationEngine(queryEnvironment);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", root);
		EvaluationResult result = evaluationEngine.eval(query, variables);
		return result;
	}

	@Test
	public void eAllContentsBenchmark() {
		Stopwatch emf = Stopwatch.createUnstarted();
		Stopwatch aql = Stopwatch.createUnstarted();
		for (int i = 0; i < NB_ITERATIONS; i++) {
			for (EObject root : reverseEcoreModel.getContents()) {
				emf.start();
				List<EPackage> ePackagesEMF = Lists.newArrayList(Iterators.filter(root.eAllContents(),
						EPackage.class));
				emf.stop();
				aql.start();
				List<EObject> ePackagesAQL = eObjectService.eAllContents(root, EcorePackage.eINSTANCE
						.getEPackage());
				aql.stop();

				assertEquals(ePackagesEMF, ePackagesAQL);
				for (EPackage ePackage : ePackagesEMF) {
					emf.start();
					List<EClass> eclassesEMF = Lists.newArrayList(Iterators.filter(ePackage.eAllContents(),
							EClass.class));
					emf.stop();
					aql.start();
					final List<EObject> eClassesAQL = eObjectService.eAllContents(ePackage,
							EcorePackage.eINSTANCE.getEClass());
					aql.stop();
					assertEquals(eclassesEMF, eClassesAQL);
				}
			}
		}
		System.out.println("PERFO: eAllContents(Type)  :  AQL " + aql.elapsed(TimeUnit.MILLISECONDS)
				+ "ms /  EMF : " + emf.elapsed(TimeUnit.MILLISECONDS) + "ms");

		final long aqlElapsed = aql.elapsed(TimeUnit.MILLISECONDS);
		final long emfElapsed = emf.elapsed(TimeUnit.MILLISECONDS);

		// We expect AQL to be faster, but do not fail this test if AQL hasn't been longer than emf + 4s
		assertTrue("The AQL implementation is supposed to be faster than the EMF one.", (aqlElapsed
				- emfElapsed) < 4000L);
	}

	@Test
	public void eContentsBenchmark() {
		Stopwatch emf = Stopwatch.createUnstarted();
		Stopwatch aql = Stopwatch.createUnstarted();
		for (int i = 0; i < NB_ITERATIONS; i++) {
			for (EObject root : reverseEcoreModel.getContents()) {
				emf.start();
				List<EPackage> ePackagesEMF = Lists.newArrayList(Iterables.filter(root.eContents(),
						EPackage.class));
				emf.stop();
				aql.start();
				List<EObject> ePackagesAQL = eObjectService.eContents(root, EcorePackage.eINSTANCE
						.getEPackage());
				aql.stop();

				assertEquals(ePackagesEMF, ePackagesAQL);
				for (EPackage ePackage : ePackagesEMF) {
					emf.start();
					List<EClass> eclassesEMF = Lists.newArrayList(Iterables.filter(ePackage.eContents(),
							EClass.class));
					emf.stop();
					aql.start();
					final List<EObject> eClassesAQL = eObjectService.eContents(ePackage,
							EcorePackage.eINSTANCE.getEClass());
					aql.stop();
					assertEquals(eclassesEMF, eClassesAQL);
				}
			}
		}
		System.out.println("PERFO: eContents(Type)  :  AQL " + aql.elapsed(TimeUnit.MILLISECONDS)
				+ "ms /  EMF : " + emf.elapsed(TimeUnit.MILLISECONDS) + "ms");

		final long aqlElapsed = aql.elapsed(TimeUnit.MILLISECONDS);
		final long emfElapsed = emf.elapsed(TimeUnit.MILLISECONDS);

		// We expect AQL to be faster, but do not fail this test if AQL hasn't been longer than emf + 4s
		assertTrue("The AQL implementation is supposed to be faster than the EMF one.", (aqlElapsed
				- emfElapsed) < 4000L);
	}

}
