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
package org.eclipse.acceleo.query.parser.tests;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorCollectionCall;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuildTest {

	QueryBuilderEngine engine;

	IQueryEnvironment queryEnvironment;

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		engine = new QueryBuilderEngine(queryEnvironment);

	}

	/**
	 * Asserts the type and the positions of the given {@link Expression}.
	 * 
	 * @param astResult
	 *            the {@link AstResult}
	 * @param expectedClass
	 *            the expected {@link Class}
	 * @param expectedStart
	 *            the expected start position
	 * @param expectedEnd
	 *            the expected end position
	 * @param actualExpression
	 *            the actual {@link Expression}
	 */
	private void assertExpression(IQueryBuilderEngine.AstResult astResult,
			Class<? extends Expression> expectedClass, int expectedStart, int expectedEnd,
			Expression actualExpression) {
		assertEquals(true, expectedClass.isAssignableFrom(actualExpression.getClass()));
		assertEquals(expectedStart, astResult.getStartPosition(actualExpression));
		assertEquals(expectedEnd, astResult.getEndPosition(actualExpression));
	}

	/**
	 * Asserts the positions of the given {@link VariableDeclaration}.
	 * 
	 * @param astResult
	 *            the {@link AstResult}
	 * @param expectedStart
	 *            the expected start position
	 * @param expectedEnd
	 *            the expected end position
	 * @param actualVariableDeclaration
	 *            the actual {@link VariableDeclaration}
	 */
	private void assertVariableDeclaration(IQueryBuilderEngine.AstResult astResult, int expectedStart,
			int expectedEnd, VariableDeclaration actualVariableDeclaration) {
		assertEquals(expectedStart, astResult.getStartPosition(actualVariableDeclaration));
		assertEquals(expectedEnd, astResult.getEndPosition(actualVariableDeclaration));
	}

	@Test
	public void variableTest() {
		IQueryBuilderEngine.AstResult build = engine.build("x");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, VarRef.class, 0, 1, ast);
		assertEquals("x", ((VarRef)ast).getVariableName());
	}

	@Test
	public void featureAccessTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.name");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, FeatureAccess.class, 0, 9, ast);
		assertEquals("name", ((FeatureAccess)ast).getFeatureName());
		assertExpression(build, VarRef.class, 0, 4, ((FeatureAccess)ast).getTarget());
		VarRef varRef = (VarRef)((FeatureAccess)ast).getTarget();
		assertEquals("self", varRef.getVariableName());
	}

	@Test
	public void intliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, IntegerLiteral.class, 0, 1, ast);
		assertEquals(2, ((IntegerLiteral)ast).getValue());
	}

	@Test
	public void intliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("2.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 12, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
	}

	@Test
	public void realliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1.0");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, RealLiteral.class, 0, 3, ast);
		assertEquals(1.0, ((RealLiteral)ast).getValue(), 0.1);
	}

	@Test
	public void realliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1.0.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 14, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, RealLiteral.class, 0, 3, ((Call)ast).getArguments().get(0));
		assertEquals(1.0, ((RealLiteral)((Call)ast).getArguments().get(0)).getValue(), 0.1);
	}

	@Test
	public void trueliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ast);
		assertEquals(true, ((BooleanLiteral)ast).isValue());
	}

	@Test
	public void trueliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 15, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void falseliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, BooleanLiteral.class, 0, 5, ast);
		assertEquals(false, ((BooleanLiteral)ast).isValue());
	}

	@Test
	public void falseliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("false.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 16, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 5, ((Call)ast).getArguments().get(0));
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void stringliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'acceleo query is great'");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 24, ast);
		assertEquals("acceleo query is great", ((StringLiteral)ast).getValue());
	}

	@Test
	public void stringliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'acceleo query is great'.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 35, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, StringLiteral.class, 0, 24, ((Call)ast).getArguments().get(0));
		assertEquals("acceleo query is great", ((StringLiteral)((Call)ast).getArguments().get(0)).getValue());
	}

	@Test
	public void nullliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("null");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, NullLiteral.class, 0, 4, ast);
	}

	@Test
	public void nullliteralTestCall() {
		IQueryBuilderEngine.AstResult build = engine.build("null.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 15, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, NullLiteral.class, 0, 4, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void lowerEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1<=2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 4, ast);
		assertEquals("lessThanEqual", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 3, 4, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void lowerTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1<2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 3, ast);
		assertEquals("lessThan", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 2, 3, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void greaterEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1>=2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 4, ast);
		assertEquals("greaterThanEqual", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 3, 4, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void greaterTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1>2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 3, ast);
		assertEquals("greaterThan", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 2, 3, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void addTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'a' + 'b'");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 9, ast);
		assertEquals("add", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, StringLiteral.class, 0, 3, ((Call)ast).getArguments().get(0));
		assertEquals("a", ((StringLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, StringLiteral.class, 6, 9, ((Call)ast).getArguments().get(1));
		assertEquals("b", ((StringLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void orTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true or false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 13, ast);
		assertEquals("or", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 8, 13, ((Call)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void xorTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true xor false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 14, ast);
		assertEquals("xor", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 9, 14, ((Call)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void impliesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true implies false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 18, ast);
		assertEquals("implies", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 13, 18, ((Call)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void andTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true and false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 14, ast);
		assertEquals("and", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 9, 14, ((Call)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void notTest() {
		IQueryBuilderEngine.AstResult build = engine.build("not true");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 8, ast);
		assertEquals("not", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 4, 8, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void multTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1*2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 3, ast);
		assertEquals("mult", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 2, 3, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void sizeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->size()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 12, ast);
		assertEquals("size", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.COLLECTIONCALL, ((Call) ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
	}

	@Test
	public void selectTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(e | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 22, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.COLLECTIONCALL, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 17, 21, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 14, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("e", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, BooleanLiteral.class, 17, 21, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
	}

	@Test
	public void selectWithVariableNameTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(var | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 24, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.COLLECTIONCALL, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 19, 23, ((Call)ast).getArguments().get(1));
		assertEquals("var", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, BooleanLiteral.class, 19, 23, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
	}

	@Test
	public void selectWithVariableNameAndTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(var : EClass | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 33, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.COLLECTIONCALL, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 28, 32, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 25, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("var", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, TypeLiteral.class, 19, 25, ((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType());
		assertEquals(true, ((TypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getValue() == EcorePackage.Literals.ECLASS);
		assertExpression(build, BooleanLiteral.class, 28, 32, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
	}

	@Test
	public void setLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 2, ast);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void setLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 8, ast);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 2, 6, ((SetInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void setLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 14, ast);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 2, 6, ((SetInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, BooleanLiteral.class, 8, 12, ((SetInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void setLitWithValuesCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self, true }.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 25, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 14, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void seqLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[]");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 2, ast);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void seqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self ]");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 8, ast);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 2, 6, ((SequenceInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void seqLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self, true ]");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 14, ast);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 2, 6, ((SequenceInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, BooleanLiteral.class, 8, 12, ((SequenceInExtensionLiteral)ast).getValues()
				.get(1));
	}

	@Test
	public void seqLitWithValuesCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self, true ].toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 25, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 14, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void explicitSeqLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 10, ast);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void explicitSeqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 16, ast);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 14, ((SequenceInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void explicitSeqLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 22, ast);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 14, ((SequenceInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, BooleanLiteral.class, 16, 20, ((SequenceInExtensionLiteral)ast).getValues()
				.get(1));
	}

	@Test
	public void explicitSeqLitWithValuesCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self, true }.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 33, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 22, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void explicitSetLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 12, ast);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void explicitSetLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 18, ast);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 16, ((SetInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void explicitSetLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 24, ast);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 16, ((SetInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, BooleanLiteral.class, 18, 22, ((SetInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void explicitSetLitWithValuesCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self, true }.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, Call.class, 0, 35, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 24, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void seqTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(Sequence(String))");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 29, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 28, ((Call)ast).getArguments().get(1));
		assertExpression(build, TypeLiteral.class, 21, 27, ((CollectionTypeLiteral)((Call)ast).getArguments()
				.get(1)).getElementType());
		assertEquals(true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == List.class);
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType()
				.getValue() == String.class);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void setTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(OrderedSet(String))");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 31, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 30, ((Call)ast).getArguments().get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertExpression(build, TypeLiteral.class, 23, 29, ((CollectionTypeLiteral)((Call)ast).getArguments()
				.get(1)).getElementType());
		assertEquals(true, ((TypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).getValue() == String.class);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEClassifier() {
		IQueryBuilderEngine.AstResult build = engine.build("ecore::EClass");
		Expression ast = build.getAst();

		assertExpression(build, TypeLiteral.class, 0, 13, ast);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((TypeLiteral)ast).getValue());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEEnumLiteral() {
		IQueryBuilderEngine.AstResult build = engine.build("Part::Other");
		Expression ast = build.getAst();

		assertExpression(build, EnumLiteral.class, 0, 11, ast);
		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other"), ((EnumLiteral)ast)
				.getLiteral());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsError() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::EClass");
		Expression ast = build.getAst();

		assertExpression(build, ErrorTypeLiteral.class, 0, 14, ast);
		assertEquals("anydsl", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals("EClass", ((ErrorTypeLiteral)ast).getSegments().get(1));
	}

	@Test
	public void enumLiteralOrEClassifierThreeSegments() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::Part::Other");
		Expression ast = build.getAst();

		assertExpression(build, EnumLiteral.class, 0, 19, ast);
		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other"), ((EnumLiteral)ast)
				.getLiteral());
	}

	@Test
	public void enumLiteralOrEClassifierThreeSegmentsError() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::Part::NotExisting");
		Expression ast = build.getAst();

		assertExpression(build, ErrorTypeLiteral.class, 0, 25, ast);
		assertEquals("anydsl", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals("Part", ((ErrorTypeLiteral)ast).getSegments().get(1));
		assertEquals("NotExisting", ((ErrorTypeLiteral)ast).getSegments().get(2));
	}

	@Test
	public void precedingSiblings() {
		IQueryBuilderEngine.AstResult build = engine.build("self.precedingSiblings()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 24, ast);
		assertEquals("precedingSiblings", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void precedingSiblingsType() {
		IQueryBuilderEngine.AstResult build = engine.build("self.precedingSiblings(ecore::EClass)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 37, ast);
		assertEquals("precedingSiblings", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, TypeLiteral.class, 23, 36, ((Call)ast).getArguments().get(1));
		final TypeLiteral typeLiteral = (TypeLiteral)((Call)ast).getArguments().get(1);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), typeLiteral.getValue());
	}

	@Test
	public void followingSiblingsType() {
		IQueryBuilderEngine.AstResult build = engine.build("self.followingSiblings(ecore::EClass)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 37, ast);
		assertEquals("followingSiblings", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, TypeLiteral.class, 23, 36, ((Call)ast).getArguments().get(1));
		final TypeLiteral typeLiteral = (TypeLiteral)((Call)ast).getArguments().get(1);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), typeLiteral.getValue());
	}

	@Test
	public void followingSiblings() {
		IQueryBuilderEngine.AstResult build = engine.build("self.followingSiblings()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 24, ast);
		assertEquals("followingSiblings", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void eInverse() {
		IQueryBuilderEngine.AstResult build = engine.build("self.eInverse()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 15, ast);
		assertEquals("eInverse", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void eInverseType() {
		IQueryBuilderEngine.AstResult build = engine.build("self.eInverse(ecore::EClass)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 28, ast);
		assertEquals("eInverse", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, TypeLiteral.class, 14, 27, ((Call)ast).getArguments().get(1));
		final TypeLiteral typeLiteral = (TypeLiteral)((Call)ast).getArguments().get(1);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), typeLiteral.getValue());
	}

	@Test
	public void eInverseString() {
		IQueryBuilderEngine.AstResult build = engine.build("self.eInverse('name')");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 21, ast);
		assertEquals("eInverse", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, StringLiteral.class, 14, 20, ((Call)ast).getArguments().get(1));
		final StringLiteral stringLiteral = (StringLiteral)((Call)ast).getArguments().get(1);
		assertEquals("name", stringLiteral.getValue());
	}

	@Test
	public void nullTest() {
		IQueryBuilderEngine.AstResult build = engine.build(null);
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, -1, -1, ast);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void emtpyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("");
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, 0, 0, ast);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletNotTest() {
		IQueryBuilderEngine.AstResult build = engine.build("not");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 3, ast);
		assertEquals("not", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, ErrorExpression.class, 3, 3, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletMinTest() {
		IQueryBuilderEngine.AstResult build = engine.build("-");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 1, ast);
		assertEquals("unaryMin", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call)
		// build.getAst()).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, ErrorExpression.class, 1, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletMultTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self *");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("mult", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompleteDivTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self /");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("divOp", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletAddTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self +");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("add", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletSubTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self -");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("sub", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());

		build = engine.build("self - ");
		ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("sub", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletLessTanTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("lessThan", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletLessThanEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <=");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("lessThanEqual", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletGreaterThanTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self >");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("greaterThan", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletGreaterThanEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self >=");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("greaterThanEqual", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletEqualsTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self =");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletDiffersTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <>");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("differs", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletAndTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self and");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 8, ast);
		assertEquals("and", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call)
		// build.getAst()).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 8, 8, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletOrTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self or");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("or", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletXorTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self xor");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 8, ast);
		assertEquals("xor", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 8, 8, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletImpliesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self implies");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 12, ast);
		assertEquals("implies", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 12, 12, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletNavigationSegmentDotTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.");
		Expression ast = build.getAst();

		assertExpression(build, ErrorFeatureAccessOrCall.class, 0, 5, ast);
		assertExpression(build, VarRef.class, 0, 4, ((ErrorFeatureAccessOrCall)ast).getTarget());
		assertEquals("self", ((VarRef)((ErrorFeatureAccessOrCall)ast).getTarget()).getVariableName());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletNavigationSegmentArrowTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->");
		Expression ast = build.getAst();

		assertExpression(build, ErrorCollectionCall.class, 0, 6, ast);
		assertExpression(build, VarRef.class, 0, 4, ((ErrorCollectionCall)ast).getTarget());
		assertEquals("self", ((VarRef)((ErrorCollectionCall)ast).getTarget()).getVariableName());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletStringLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'str");
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, 0, 1, ast);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletRealLitTest() {
		// TODO remove incomplete navigationSegment on integer ?
		IQueryBuilderEngine.AstResult build = engine.build("3.");
		Expression ast = build.getAst();

		assertExpression(build, ErrorFeatureAccessOrCall.class, 0, 2, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(ast, build.getErrors().get(0));
	}

	@Test
	public void incompletSetLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 1, ast);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletSetLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 6, ast);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 2, 6, ((SetInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void incompletSetLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 7, ast);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 2, 6, ((SetInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((SetInExtensionLiteral)ast).getValues().get(1));
		assertEquals(build.getErrors().get(0), ((SetInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletSeqLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 1, ast);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletSeqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 6, ast);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 2, 6, ((SequenceInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void incompletSeqLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 7, ast);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 2, 6, ((SequenceInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((SequenceInExtensionLiteral)ast).getValues()
				.get(1));
		assertEquals(build.getErrors().get(0), ((SequenceInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletExplicitSeqLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 9, ast);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletExplicitSeqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 14, ast);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 14, ((SequenceInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void incompletExplicitSeqLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 15, ast);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 14, ((SequenceInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, ErrorExpression.class, 15, 15, ((SequenceInExtensionLiteral)ast).getValues()
				.get(1));
		assertEquals(build.getErrors().get(0), ((SequenceInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletExplicitSetLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 11, ast);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletExplicitSetLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 16, ast);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 16, ((SetInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void incompletExplicitSetLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 17, ast);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 16, ((SetInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, ErrorExpression.class, 17, 17, ((SetInExtensionLiteral)ast).getValues()
				.get(1));
		assertEquals(build.getErrors().get(0), ((SetInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletEnumLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("toto::");
		Expression ast = build.getAst();

		assertExpression(build, ErrorTypeLiteral.class, 0, 6, ast);
		assertEquals(1, ((ErrorTypeLiteral)ast).getSegments().size());
		assertEquals("toto", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletEnumLitWithPackageTest() {
		IQueryBuilderEngine.AstResult build = engine.build("toto::tata::");
		Expression ast = build.getAst();

		assertExpression(build, ErrorTypeLiteral.class, 0, 12, ast);
		assertEquals(2, ((ErrorTypeLiteral)ast).getSegments().size());
		assertEquals("toto", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals("tata", ((ErrorTypeLiteral)ast).getSegments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 13, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 13, 13, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorExpression.class, 13, 13, lambda.getExpression());
		assertEquals(1, lambda.getParameters().size());
		assertEquals(1, lambda.getParameters().size());
		assertVariableDeclaration(build, 13, 13, (VariableDeclaration)lambda.getParameters().get(0));
		assertEquals(2, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(e | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 21, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 17, 21, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 17, 21, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableNameTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a |");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 17, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 17, 17, ((Call)ast).getArguments().get(1));
		assertExpression(build, ErrorExpression.class, 17, 17, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableNameAndExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 22, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 18, 22, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 18, 22, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithMissingTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a :");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 17, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 17, 17, ((Call)ast).getArguments().get(1));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, ErrorTypeLiteral.class, 17, 17, ((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType());
		assertEquals(0, ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getSegments().size());
		assertExpression(build, ErrorExpression.class, 17, 17, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(2, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithErrorTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : ecore::");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 25, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 25, 25, ((Call)ast).getArguments().get(1));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, ErrorTypeLiteral.class, 18, 25, ((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType());
		assertEquals(1, ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getSegments().size());
		assertEquals("ecore", ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).getSegments().get(0));
		assertExpression(build, ErrorExpression.class, 25, 25, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(2, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationNoPipeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : EClass");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 24, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 24, 24, ((Call)ast).getArguments().get(1));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, TypeLiteral.class, 18, 24, ((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType());
		assertEquals(true, ((TypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getValue() == EcorePackage.Literals.ECLASS);
		assertExpression(build, ErrorExpression.class, 24, 24, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationWithPipeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : EClass |");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 26, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 26, 26, ((Call)ast).getArguments().get(1));
		assertExpression(build, ErrorExpression.class, 26, 26, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationAndExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : EClass | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 31, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 27, 31, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 27, 31, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletServiceCallNoParameterTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 13, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletServiceCallOneParameterTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service( true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 18, ast);
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals("service", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, BooleanLiteral.class, 14, 18, ((Call)ast).getArguments().get(1));
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletServiceCallOneParameterAndComaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service( true,");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 19, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(3, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, BooleanLiteral.class, 14, 18, ((Call)ast).getArguments().get(1));
		assertExpression(build, ErrorExpression.class, 19, 19, ((Call)ast).getArguments().get(2));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletSeqTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(Sequence(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 21, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 21, ((Call)ast).getArguments().get(1));
		assertEquals(true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == List.class);
		assertExpression(build, ErrorTypeLiteral.class, 21, 21, ((CollectionTypeLiteral)((Call)ast)
				.getArguments().get(1)).getElementType());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletSeqTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(Sequence(String");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 27, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 27, ((Call)ast).getArguments().get(1));
		assertExpression(build, TypeLiteral.class, 21, 27, ((CollectionTypeLiteral)((Call)ast).getArguments()
				.get(1)).getElementType());
		assertEquals(true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == List.class);
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType()
				.getValue() == String.class);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletSetTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(OrderedSet(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 23, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 23, ((Call)ast).getArguments().get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertExpression(build, ErrorTypeLiteral.class, 23, 23, ((CollectionTypeLiteral)((Call)ast)
				.getArguments().get(1)).getElementType());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletSetTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(OrderedSet(String");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 29, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 29, ((Call)ast).getArguments().get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertExpression(build, TypeLiteral.class, 23, 29, ((CollectionTypeLiteral)((Call)ast).getArguments()
				.get(1)).getElementType());
		assertEquals(true, ((TypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).getValue() == String.class);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompleteLetTest1() {
		IQueryBuilderEngine.AstResult build = engine.build("let a=2 in");
		assertExpression(build, Let.class, -1, -1, build.getAst());
	}

	@Test
	public void incompleteLetTest2() {
		IQueryBuilderEngine.AstResult build = engine.build("let a=2 i");
		assertExpression(build, Let.class, -1, -1, build.getAst());
	}

	@Test
	public void incompleteLetTest3() {
		IQueryBuilderEngine.AstResult build = engine.build("let a=2 ,b=");
		assertExpression(build, Let.class, -1, -1, build.getAst());
	}

	/**
	 * Test the ast builder on a conditionnal expression
	 */
	@Test
	public void testConditional() {
		IQueryBuilderEngine.AstResult build = engine.build("if true then 5 else 'string' endif");
		Expression ast = build.getAst();
		assertTrue(ast instanceof Conditional);
		Conditional cond = (Conditional)ast;
		assertTrue(cond.getPredicate() instanceof BooleanLiteral);
		assertTrue(cond.getTrueBranch() instanceof IntegerLiteral);
		assertTrue(cond.getFalseBranch() instanceof StringLiteral);
	}
}
