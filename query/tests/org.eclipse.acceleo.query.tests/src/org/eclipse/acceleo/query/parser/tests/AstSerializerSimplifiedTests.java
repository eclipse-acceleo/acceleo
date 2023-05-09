/*******************************************************************************
 * Copyright (c) 2022 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.tests;

import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link AstSerializer} for simplified expressions.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstSerializerSimplifiedTests {

	/**
	 * The {@link QueryBuilderEngine}.
	 */
	private static final QueryBuilderEngine ENGINE = new QueryBuilderEngine();

	/**
	 * The {@link AstSerializer} to test.
	 */
	private static final AstSerializer SERIALIZER = new AstSerializer();

	@Test
	public void uselessSpaces() {
		final Expression ast = ENGINE.build("  a   +   b  ").getAst();

		assertEquals("a + b", SERIALIZER.serialize(ast));
	}

	@Test
	public void uselessParenthesis() {
		final Expression ast = ENGINE.build("(a)").getAst();

		assertEquals("a", SERIALIZER.serialize(ast));
	}

	@Test
	public void uselessParenthesisPrecedence() {
		final Expression ast = ENGINE.build("a + (b * c)").getAst();

		assertEquals("a + b * c", SERIALIZER.serialize(ast));
	}

	@Test
	public void uselessUnderscoreFeatureAccess() {
		final Expression ast = ENGINE.build("self._someFeature").getAst();

		assertEquals("self.someFeature", SERIALIZER.serialize(ast));
	}

	@Test
	public void uselessUnderscoreserviceCall() {
		final Expression ast = ENGINE.build("self._someService()").getAst();

		assertEquals("self.someService()", SERIALIZER.serialize(ast));
	}

	@Test
	public void javaStyleDifferent() {
		final Expression ast = ENGINE.build("a != b").getAst();

		assertEquals("a <> b", SERIALIZER.serialize(ast));
	}

	@Test
	public void javaStyleEquals() {
		final Expression ast = ENGINE.build("a == b").getAst();

		assertEquals("a = b", SERIALIZER.serialize(ast));
	}

}
