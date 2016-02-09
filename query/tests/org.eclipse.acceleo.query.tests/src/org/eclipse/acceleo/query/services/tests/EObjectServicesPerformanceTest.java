/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.tests;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
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
		this.eObjectService = new EObjectServices(queryEnvironment);
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
		assertTrue("The AQL implementation is supposed to be faster than the EMF one.", aql
				.elapsed(TimeUnit.MILLISECONDS) < emf.elapsed(TimeUnit.MILLISECONDS));
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
		assertTrue("The AQL implementation is supposed to be faster than the EMF one.", aql
				.elapsed(TimeUnit.MILLISECONDS) < emf.elapsed(TimeUnit.MILLISECONDS));
	}

}
