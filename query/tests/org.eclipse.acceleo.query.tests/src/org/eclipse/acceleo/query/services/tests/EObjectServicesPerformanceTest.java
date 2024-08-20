/*******************************************************************************
 * Copyright (c) 2016, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	private class Stopwatch {

		long lastStart;

		long elapsed;

		public void start() {
			lastStart = System.currentTimeMillis();
		}

		public void stop() {
			elapsed += System.currentTimeMillis() - lastStart;
		}

		public long elapsed() {
			return elapsed;
		}

	}

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
		Stopwatch noError = new Stopwatch();
		Stopwatch error = new Stopwatch();
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
				+ noError.elapsed() + "ms /  self.eAllContents()->select(c | not c.abstract) : " + error
						.elapsed() + "ms");

		final double noErrorElapsed = noError.elapsed();
		final double errorElapsed = error.elapsed();

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
		Stopwatch emf = new Stopwatch();
		Stopwatch aql = new Stopwatch();
		for (int i = 0; i < NB_ITERATIONS; i++) {
			for (EObject root : reverseEcoreModel.getContents()) {
				emf.start();
				final List<EPackage> ePackagesEMF = new ArrayList<>();
				final Iterator<EObject> itPkg = root.eAllContents();
				while (itPkg.hasNext()) {
					final EObject current = itPkg.next();
					if (current instanceof EPackage) {
						ePackagesEMF.add((EPackage)current);
					}
				}
				emf.stop();
				aql.start();
				List<EObject> ePackagesAQL = eObjectService.eAllContents(root, EcorePackage.eINSTANCE
						.getEPackage());
				aql.stop();

				assertEquals(ePackagesEMF, ePackagesAQL);
				for (EPackage ePackage : ePackagesEMF) {
					emf.start();
					final List<EClass> eclassesEMF = new ArrayList<>();
					final Iterator<EObject> itCls = ePackage.eAllContents();
					while (itCls.hasNext()) {
						final EObject current = itCls.next();
						if (current instanceof EClass) {
							eclassesEMF.add((EClass)current);
						}
					}
					emf.stop();
					aql.start();
					final List<EObject> eClassesAQL = eObjectService.eAllContents(ePackage,
							EcorePackage.eINSTANCE.getEClass());
					aql.stop();
					assertEquals(eclassesEMF, eClassesAQL);
				}
			}
		}
		System.out.println("PERFO: eAllContents(Type)  :  AQL " + aql.elapsed() + "ms /  EMF : " + emf
				.elapsed() + "ms");

		final long aqlElapsed = aql.elapsed();
		final long emfElapsed = emf.elapsed();

		// We expect AQL to be faster, but do not fail this test if AQL hasn't been longer than emf + 4s
		assertTrue("The AQL implementation is supposed to be faster than the EMF one.", (aqlElapsed
				- emfElapsed) < 4000L);
	}

	@Test
	public void eContentsBenchmark() {
		Stopwatch emf = new Stopwatch();
		Stopwatch aql = new Stopwatch();
		for (int i = 0; i < NB_ITERATIONS; i++) {
			for (EObject root : reverseEcoreModel.getContents()) {
				emf.start();
				@SuppressWarnings("unchecked")
				List<EPackage> ePackagesEMF = new ArrayList<EPackage>((Collection<? extends EPackage>)root
						.eContents().stream().filter(eObj -> eObj instanceof EPackage).collect(Collectors
								.toList()));
				emf.stop();
				aql.start();
				List<EObject> ePackagesAQL = eObjectService.eContents(root, EcorePackage.eINSTANCE
						.getEPackage());
				aql.stop();

				assertEquals(ePackagesEMF, ePackagesAQL);
				for (EPackage ePackage : ePackagesEMF) {
					emf.start();
					@SuppressWarnings("unchecked")
					List<EClass> eclassesEMF = new ArrayList<EClass>((Collection<? extends EClass>)ePackage
							.eContents().stream().filter(eObj -> eObj instanceof EClass).collect(Collectors
									.toList()));
					emf.stop();
					aql.start();
					final List<EObject> eClassesAQL = eObjectService.eContents(ePackage,
							EcorePackage.eINSTANCE.getEClass());
					aql.stop();
					assertEquals(eclassesEMF, eClassesAQL);
				}
			}
		}
		System.out.println("PERFO: eContents(Type)  :  AQL " + aql.elapsed() + "ms /  EMF : " + emf.elapsed()
				+ "ms");

		final long aqlElapsed = aql.elapsed();
		final long emfElapsed = emf.elapsed();

		// We expect AQL to be faster, but do not fail this test if AQL hasn't been longer than emf + 4s
		assertTrue("The AQL implementation is supposed to be faster than the EMF one.", (aqlElapsed
				- emfElapsed) < 4000L);
	}

}
