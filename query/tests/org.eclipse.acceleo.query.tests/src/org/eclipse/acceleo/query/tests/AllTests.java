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
package org.eclipse.acceleo.query.tests;

import org.eclipse.acceleo.query.ast.test.AstBuilderTest;
import org.eclipse.acceleo.query.ast.test.AstEvaluatorTest;
import org.eclipse.acceleo.query.collections.test.LazyListTest;
import org.eclipse.acceleo.query.collections.test.LazySetTests;
import org.eclipse.acceleo.query.parser.tests.BuildTest;
import org.eclipse.acceleo.query.parser.tests.CombineIteratorTest;
import org.eclipse.acceleo.query.parser.tests.CompletionTest;
import org.eclipse.acceleo.query.parser.tests.EvaluationTest;
import org.eclipse.acceleo.query.parser.tests.LexerTest;
import org.eclipse.acceleo.query.parser.tests.ValidationTest;
import org.eclipse.acceleo.query.runtime.servicelookup.BasicLookupCrossReferencerTest;
import org.eclipse.acceleo.query.runtime.servicelookup.BasicLookupTest;
import org.eclipse.acceleo.query.runtime.test.EvaluationServiceLoggingTests;
import org.eclipse.acceleo.query.runtime.test.EvaluationServicesTest;
import org.eclipse.acceleo.query.services.tests.BooleanServicesTest;
import org.eclipse.acceleo.query.services.tests.CollectionServicesTest;
import org.eclipse.acceleo.query.services.tests.ComparableServicesTest;
import org.eclipse.acceleo.query.services.tests.EObjectServicesTest;
import org.eclipse.acceleo.query.services.tests.NumberServicesTest;
import org.eclipse.acceleo.query.services.tests.StringServicesTest;
import org.eclipse.acceleo.query.tests.unit.AnyDSLEvaluationTests;
import org.eclipse.acceleo.query.tests.unit.AnyDSLValidationTests;
import org.eclipse.acceleo.query.tests.unit.EcoreReverseTests;
import org.eclipse.acceleo.query.tests.unit.EcoreValidationTests;
import org.eclipse.acceleo.query.tests.unit.UMLEvaluationTests;
import org.eclipse.acceleo.query.tests.unit.UMLValidationTests;
import org.eclipse.acceleo.query.type.test.PrimitiveTypeTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class aggregates tests for the org.eclipse.acceleo.query.tests plug-in.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Suite.class)
@SuiteClasses(value = {AstBuilderTest.class, AstEvaluatorTest.class, LazyListTest.class, LazySetTests.class,
		BuildTest.class, CombineIteratorTest.class, CompletionTest.class, EvaluationTest.class,
		LexerTest.class, ValidationTest.class, BasicLookupCrossReferencerTest.class, BasicLookupTest.class,
		EvaluationServiceLoggingTests.class, EvaluationServicesTest.class, BooleanServicesTest.class,
		CollectionServicesTest.class, ComparableServicesTest.class, EObjectServicesTest.class,
		NumberServicesTest.class, StringServicesTest.class, AnyDSLEvaluationTests.class,
		AnyDSLValidationTests.class, EcoreReverseTests.class, EcoreValidationTests.class,
		UMLEvaluationTests.class, UMLValidationTests.class, PrimitiveTypeTests.class })
public class AllTests {

}