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
package org.eclipse.acceleo.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class aggregates tests for the org.eclipse.acceleo.aql.tests plug-in.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Suite.class)
@SuiteClasses(value = {ModuleTests.class, FileStatementTests.class, TemplateTests.class, QueryTests.class,
		TextStatementTests.class, ForStatementTests.class, IfStatementTests.class,
		ExpressionStatementTests.class, ProtectedAreaTests.class, LetStatementTests.class,
		ModuleDocumentationTests.class, ModuleElementDocumentationTests.class, CommentTests.class,
		CompletionTests.class })
public class AllTests {

}
