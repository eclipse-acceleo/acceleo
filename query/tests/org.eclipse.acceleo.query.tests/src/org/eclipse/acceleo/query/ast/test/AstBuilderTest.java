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
package org.eclipse.acceleo.query.ast.test;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.AstBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author rguider
 */
public class AstBuilderTest {

	/**
	 * Test method for {@link org.eclipse.acceleo.query.parser.AstBuilder#integerLiteral(int)}.
	 */
	@Test
	public void testIntegerLiteral() {
		IntegerLiteral literal = new AstBuilder().integerLiteral(2);
		assertEquals(2, literal.getValue());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.parser.AstBuilder#realLiteral(double)}.
	 */
	@Test
	public void testRealLiteral() {
		RealLiteral literal = new AstBuilder().realLiteral(1.0);
		assertTrue(1.0 == literal.getValue());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.parser.AstBuilder#stringLiteral(java.lang.String)}.
	 */
	@Test
	public void testStringLiteral() {
		StringLiteral literal = new AstBuilder().stringLiteral("str");
		assertEquals("str", literal.getValue());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.parser.AstBuilder#stringLiteral(java.lang.String)}.
	 */
	@Test
	public void testStringLiteralEscaped() {
		StringLiteral literal = new AstBuilder().stringLiteral("\\n");
		assertEquals("\n", literal.getValue());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.parser.AstBuilder#booleanLiteral(boolean)} .
	 */
	@Test
	public void testBooleanLiteralBoolean() {
		BooleanLiteral literal = new AstBuilder().booleanLiteral(true);
		assertTrue(literal.isValue());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.acceleo.query.parser.AstBuilder#collectionTypeLiteral(java.lang.Object, org.eclipse.acceleo.query.ast.TypeLiteral)}
	 * .
	 */
	@Test
	public void testCollectionTypeLiteral() {
		AstBuilder builder = new AstBuilder();
		CollectionTypeLiteral literal = builder.collectionTypeLiteral(List.class, (TypeLiteral)builder
				.typeLiteral(Integer.class));
		assertEquals(Integer.class, literal.getElementType().getValue());
		assertEquals(List.class, literal.getValue());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.parser.AstBuilder#typeLiteral(java.lang.Object)} .
	 */
	@Test
	public void testTypeLiteral() {
		assertEquals(Integer.class, ((TypeLiteral)new AstBuilder().typeLiteral(Integer.class)).getValue());
	}

	@Test
	public void testTypeLiteralSet() {
		Set<Class<?>> classeInSet = Sets.newLinkedHashSet();
		classeInSet.add(Integer.class);
		assertEquals(Integer.class, ((TypeLiteral)new AstBuilder().typeLiteral(classeInSet)).getValue());

		classeInSet.add(String.class);
		assertEquals(Integer.class, ((TypeSetLiteral)new AstBuilder().typeLiteral(classeInSet)).getTypes()
				.get(0).getValue());
		assertEquals(String.class, ((TypeSetLiteral)new AstBuilder().typeLiteral(classeInSet)).getTypes().get(
				1).getValue());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.acceleo.query.parser.AstBuilder#callService(org.eclipse.acceleo.query.ast.CallType, java.lang.String, org.eclipse.acceleo.query.ast.Expression[])}
	 * .
	 */
	@Test
	public void testCallService() {
		Call call = new AstBuilder().callService("myService", new AstBuilder().integerLiteral(0));
		call.setType(CallType.CALLORAPPLY);
		assertEquals("myService", call.getServiceName());
		assertEquals(0, ((IntegerLiteral)call.getArguments().get(0)).getValue());
		assertEquals(CallType.CALLORAPPLY, call.getType());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.parser.AstBuilder#varRef(java.lang.String)} .
	 */
	@Test
	public void testVarRef() {
		assertEquals("var", new AstBuilder().varRef("var").getVariableName());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.acceleo.query.parser.AstBuilder#featureAccess(org.eclipse.acceleo.query.ast.Expression, java.lang.String)}
	 * .
	 */
	@Test
	public void testFeatureAccess() {
		FeatureAccess access = new AstBuilder().featureAccess(new AstBuilder().varRef("var"), "feature");
		assertEquals("var", ((VarRef)access.getTarget()).getVariableName());
		assertEquals("feature", access.getFeatureName());
	}

	@Test
	public void testNullLiteral() {
		NullLiteral nullLiteral = new AstBuilder().nullLiteral();

		assertTrue(nullLiteral != null);
	}
}
