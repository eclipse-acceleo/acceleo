/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.tests.utils.AbstractLanguageTestSuite;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

public class PositionsTests {

	/**
	 * 136.
	 */
	private static final int CONST_136 = 136;

	/**
	 * 75.
	 */
	private static final int CONST_75 = 75;

	/**
	 * The {@link AcceleoAstResult}.
	 */
	private final AcceleoAstResult astResult;

	/**
	 * Constructor.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public PositionsTests() throws FileNotFoundException, IOException {
		final AcceleoParser parser = new AcceleoParser(Query.newEnvironmentWithDefaultServices(null));

		try (FileInputStream stream = new FileInputStream("resources/language/query/nominal/nominal.mtl")) {
			astResult = parser.parse(AbstractLanguageTestSuite.getContent(stream,
					AbstractLanguageTestSuite.UTF_8), "org::eclipse::acceleo::tests::");
		}
	}

	/**
	 * Tests {@link Positions}.
	 */
	@Test
	public void getNodeAtPosition() {
		EObject node = astResult.getAstNode(2, CONST_75);

		assertTrue(node instanceof VarRef);
		assertEquals("myParam", ((VarRef)node).getVariableName());

		node = astResult.getAstNode(CONST_136);

		assertTrue(node instanceof VarRef);
		assertEquals("myParam", ((VarRef)node).getVariableName());

	}

}
