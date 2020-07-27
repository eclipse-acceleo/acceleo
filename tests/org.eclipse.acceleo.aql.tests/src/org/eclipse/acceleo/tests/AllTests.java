/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests;

import org.eclipse.acceleo.tests.completion.CompletionTests;
import org.eclipse.acceleo.tests.evaluation.EvaluationTests;
import org.eclipse.acceleo.tests.language.LanguageTests;
import org.eclipse.acceleo.tests.resolution.ResolutionTests;
import org.eclipse.acceleo.tests.resolver.ResolverTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class aggregates tests for the org.eclipse.acceleo.aql.tests plug-in.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Suite.class)
@SuiteClasses(value = {LanguageTests.class, CompletionTests.class, EvaluationTests.class,
		ResolutionTests.class, ResolverTests.class, })
public class AllTests {

}
