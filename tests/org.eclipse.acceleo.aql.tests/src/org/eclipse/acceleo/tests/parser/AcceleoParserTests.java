/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.acceleo.ErrorQuery;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.parser.AstResult;
import org.junit.Test;

/**
 * Tests recovery at the boundary between the Acceleo parser and the AQL parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoParserTests {

	@Test
	public void queryReturnTypeEndPastEndOfFile() {
		final String returnType = "ecore::";
		final String source = "[module test('http://www.eclipse.org/emf/2002/Ecore')/]\n"
				+ "[query public test(value : String) : " + returnType;
		final AcceleoParser parser = new AcceleoParser() {
			@Override
			protected AstResult parseWhileAqlTypeLiteral(String expression) {
				final AstResult type = super.parseWhileAqlTypeLiteral(expression);
				if (returnType.equals(expression)) {
					// Simulate the out-of-bounds return type offset from the reported failure.
					type.getPositions().setEndPositions(type.getAst(), expression.length() + 1);
				}
				return type;
			}
		};

		final AcceleoAstResult result = parser.parse(source, "UTF-8", "test");
		final ErrorQuery query = (ErrorQuery)result.getModule().getModuleElements().get(0);
		assertTrue(query.getBody().getAql() instanceof ErrorExpression);
		assertEquals(source.length(), query.getMissingEqual());
		assertEquals(source.length(), query.getMissingEnd());
		assertEquals(source.length(), result.getEndPosition(query));
	}

}
