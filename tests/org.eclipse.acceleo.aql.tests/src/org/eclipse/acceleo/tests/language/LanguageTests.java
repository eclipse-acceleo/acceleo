/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.language;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class aggregates tests for the org.eclipse.acceleo.aql.tests.language package.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Suite.class)
@SuiteClasses(value = {ModuleTests.class, FileStatementTests.class, TemplateTests.class, QueryTests.class,
		TextStatementTests.class, ForStatementTests.class, IfStatementTests.class,
		ExpressionStatementTests.class, ProtectedAreaTests.class, LetStatementTests.class,
		ModuleDocumentationTests.class, ModuleElementDocumentationTests.class, CommentTests.class,
		BlockCommentTests.class, })
public class LanguageTests {

}
