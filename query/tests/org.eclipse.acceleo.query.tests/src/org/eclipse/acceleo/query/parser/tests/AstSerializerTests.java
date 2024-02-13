/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link AstSerializer}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Parameterized.class)
public class AstSerializerTests {

	/**
	 * The {@link QueryBuilderEngine}.
	 */
	private static final QueryBuilderEngine ENGINE = new QueryBuilderEngine();

	/**
	 * The {@link AstSerializer} to test.
	 */
	private static final AstSerializer SERIALIZER = new AstSerializer();

	/**
	 * The {@link List} of expressions to test.
	 * 
	 * @return the {@link List} of expressions to test
	 */
	@Parameters(name = "{0}")
	public static List<String> expressions() {
		final List<String> res = new ArrayList<String>();

		res.add("");

		res.add("null");
		res.add("1");
		res.add("3.14");
		res.add("true");
		res.add("false");
		res.add("'a string'");
		res.add("'a string \\' \\\\'");
		res.add("Sequence{1, 2, 3}");
		res.add("OrderedSet{1, 2, 3}");

		res.add("String");
		res.add("Integer");
		res.add("Real");
		res.add("Boolean");
		res.add("Sequence(String)");
		res.add("OrderedSet(String)");
		res.add("ecore::EPackage");
		res.add("{ecore::EPackage | ecore::EClass}");

		res.add("self");
		res.add("self.");
		res.add("self.name");

		res.add("-2");
		res.add("1 + 2");
		res.add("1 - 2");
		res.add("1 / 2");
		res.add("1 * 2");

		res.add("not false");
		res.add("true or false");
		res.add("true and false");
		res.add("true xor false");
		res.add("true implies false");

		res.add("1 <= 2");
		res.add("1 >= 2");
		res.add("1 <> 2");
		res.add("1 = 2");
		res.add("1 < 2");
		res.add("1 > 2");

		res.add("self->select(s | s.name = 'a' + 'B')");
		res.add("self->select(s : ecore::EPackage | s.name = 'a')");

		res.add("if self = 4 then 2 else 1 endif");

		res.add("let s = '' in s.toUpper()");

		res.add("-2 + 3");
		res.add("-(2 + 3)");
		res.add("not (1 > 2)");
		res.add("a and b or c and d");
		res.add("a and (b or c) and d");
		res.add("1 + 2 * 3 + 4");
		res.add("(1 + 2) * (3 + 4)");
		res.add("1 - 2 * 3 - 4");
		res.add("(1 - 2) * (3 - 4)");
		res.add("1 + 2 / 3 + 4");
		res.add("(1 + 2) / (3 + 4)");
		res.add("1 - 2 / 3 - 4");
		res.add("(1 - 2) / (3 - 4)");
		res.add("a or b <= c or d");
		res.add("(a or b) <= (c or d)");
		res.add("a and b <= c and d");
		res.add("(a and b) <= (c and d)");
		res.add("a or b >= c or d");
		res.add("(a or b) >= (c or d)");
		res.add("a and b >= c and d");
		res.add("(a and b) <> (c and d)");
		res.add("a or b <> c or d");
		res.add("(a or b) <> (c or d)");
		res.add("a and b <> c and d");
		res.add("(a and b) <> (c and d)");
		res.add("(a and b) < (c and d)");
		res.add("a or b < c or d");
		res.add("(a or b) < (c or d)");
		res.add("a and b < c and d");
		res.add("(a and b) < (c and d)");
		res.add("(a and b) > (c and d)");
		res.add("a or b > c or d");
		res.add("(a or b) > (c or d)");
		res.add("a and b > c and d");
		res.add("(a and b) > (c and d)");
		res.add("1 - 3 + 4");
		res.add("1 - (3 + 4)");
		res.add("1 + 3 - 4");
		res.add("1 / 3 * 4");
		res.add("1 / (3 * 4)");
		res.add("1 - -1");
		res.add("1 / -1");

		res.add("self._endif");
		res.add("self._endif()");
		res.add("self._endif(a)");
		res.add("self->_endif()");
		res.add("self->_endif(a)");
		res.add("_Real::_String");
		res.add("_Real::_String::_Integer");

		res.add("('a' + 'b').toUpper()");

		return res;
	}

	/**
	 * The expression {@link String}.
	 */
	private final String expression;

	/**
	 * The {@link Expression}.
	 */
	private final Expression ast;

	public AstSerializerTests(String expression) {
		this.expression = expression;
		this.ast = ENGINE.build(expression).getAst();
	}

	@Test
	public void serialize() {
		assertEquals(expression, SERIALIZER.serialize(ast));
	}

}
