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

import org.eclipse.acceleo.query.ast.And;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorBinding;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.ErrorConditional;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorStringLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Implies;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.Or;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BuildTest {

	QueryBuilderEngine engine;

	IQueryEnvironment queryEnvironment;

	@Before
	public void setup() {
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
		assertTrue(expectedClass.isAssignableFrom(actualExpression.getClass()));
		if (!Error.class.isAssignableFrom(expectedClass)) {
			assertFalse(Error.class.isAssignableFrom(actualExpression.getClass()));
		}
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, VarRef.class, 0, 1, ast);
		assertEquals("x", ((VarRef)ast).getVariableName());
	}

	@Test
	public void featureAccessTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.name");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 9, ast);
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, ((Call)ast).getServiceName());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		VarRef varRef = (VarRef)((Call)ast).getArguments().get(0);
		assertEquals("self", varRef.getVariableName());
		assertExpression(build, StringLiteral.class, 5, 9, ((Call)ast).getArguments().get(1));
		assertEquals("name", ((StringLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void intliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, IntegerLiteral.class, 0, 1, ast);
		assertEquals(2, ((IntegerLiteral)ast).getValue());
	}

	@Test
	public void intliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("2.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, RealLiteral.class, 0, 3, ast);
		assertEquals(1.0, ((RealLiteral)ast).getValue(), 0.1);
	}

	@Test
	public void realliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1.0.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ast);
		assertEquals(true, ((BooleanLiteral)ast).isValue());
	}

	@Test
	public void trueliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, BooleanLiteral.class, 0, 5, ast);
		assertEquals(false, ((BooleanLiteral)ast).isValue());
	}

	@Test
	public void falseliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("false.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, StringLiteral.class, 0, 24, ast);
		assertEquals("acceleo query is great", ((StringLiteral)ast).getValue());
	}

	@Test
	public void stringliteralEscapeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'\\b'");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 4, ast);
		assertEquals("\b", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\b'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 5, ast);
		assertEquals("\\b", ((StringLiteral)ast).getValue());

		build = engine.build("'\\t'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 4, ast);
		assertEquals("\t", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\t'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 5, ast);
		assertEquals("\\t", ((StringLiteral)ast).getValue());

		build = engine.build("'\\n'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 4, ast);
		assertEquals("\n", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\n'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 5, ast);
		assertEquals("\\n", ((StringLiteral)ast).getValue());

		build = engine.build("'\\f'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 4, ast);
		assertEquals("\f", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\f'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 5, ast);
		assertEquals("\\f", ((StringLiteral)ast).getValue());

		build = engine.build("'\\r'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 4, ast);
		assertEquals("\r", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\r'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 5, ast);
		assertEquals("\\r", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\"'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 4, ast);
		assertEquals("\"", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\\"'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 5, ast);
		assertEquals("\\\"", ((StringLiteral)ast).getValue());

		build = engine.build("'\\''");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 4, ast);
		assertEquals("\'", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 4, ast);
		assertEquals("\\", ((StringLiteral)ast).getValue());

		build = engine.build("'\\x09'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 6, ast);
		assertEquals("\t", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\x09'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 7, ast);
		assertEquals("\\x09", ((StringLiteral)ast).getValue());

		build = engine.build("'\\u0041'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 8, ast);
		assertEquals("A", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\u0041'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 9, ast);
		assertEquals("\\u0041", ((StringLiteral)ast).getValue());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void invalidStringliteralEscapeTest() {
		engine.build("'\\w'");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void incompletStringliteralEscapeTest() {
		engine.build("'\\'");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void invalidXStringliteralEscapeTest() {
		engine.build("'\\xZZ'");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void incompletXStringliteralEscapeTest() {
		engine.build("'\\x0'");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void invalidUStringliteralEscapeTest() {
		engine.build("'\\uZZZZ'");
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void incompletUStringliteralEscapeTest() {
		engine.build("'\\u0'");
	}

	@Test
	public void stringliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'acceleo query is great'.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, NullLiteral.class, 0, 4, ast);
	}

	@Test
	public void nullliteralTestCall() {
		IQueryBuilderEngine.AstResult build = engine.build("null.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 4, ast);
		assertEquals("lessThanEqual", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 3, ast);
		assertEquals("lessThan", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 4, ast);
		assertEquals("greaterThanEqual", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 3, ast);
		assertEquals("greaterThan", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 9, ast);
		assertEquals("add", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Or.class, 0, 13, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Or)ast).getType());
		assertEquals(2, ((Or)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ((Or)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Or)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 8, 13, ((Or)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((Or)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void xorTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true xor false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 14, ast);
		assertEquals("xor", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Implies.class, 0, 18, ast);
		assertEquals("implies", ((Implies)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Implies)ast).getType());
		assertEquals(2, ((Implies)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ((Implies)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Implies)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 13, 18, ((Implies)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((Implies)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void andTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true and false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, And.class, 0, 14, ast);
		assertEquals("and", ((And)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((And)ast).getType());
		assertEquals(2, ((And)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 4, ((And)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((And)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 9, 14, ((And)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((And)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void notTest() {
		IQueryBuilderEngine.AstResult build = engine.build("not true");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 8, ast);
		assertEquals("not", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 4, 8, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void multTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1*2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 3, ast);
		assertEquals("mult", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 12, ast);
		assertEquals("size", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
	}

	@Test
	public void selectTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(e | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 22, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 24, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
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
		IQueryBuilderEngine.AstResult build = engine.build("self->select(var : ecore::EClass | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 40, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 35, 39, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 32, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("var", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, TypeLiteral.class, 19, 32, ((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType());
		assertEquals(true, ((TypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getValue() == EcorePackage.Literals.ECLASS);
		assertExpression(build, BooleanLiteral.class, 35, 39, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
	}

	@Test
	public void explicitSeqLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 10, ast);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void explicitSeqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 16, ast);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 14, ((SequenceInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void explicitSeqLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 12, ast);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void explicitSetLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 18, ast);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 16, ((SetInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void explicitSetLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
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
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void setTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(OrderedSet(String))");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 31, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 30, ((Call)ast).getArguments().get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertExpression(build, TypeLiteral.class, 23, 29, ((CollectionTypeLiteral)((Call)ast).getArguments()
				.get(1)).getElementType());
		assertEquals(true, ((TypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).getValue() == String.class);
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void typeLiteral() {
		IQueryBuilderEngine.AstResult build = engine.build("ecore::EClass");
		Expression ast = build.getAst();

		assertExpression(build, TypeLiteral.class, 0, 13, ast);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((TypeLiteral)ast).getValue());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void integerTypeLiteral() {
		IQueryBuilderEngine.AstResult build = engine.build("Integer");
		Expression ast = build.getAst();

		assertExpression(build, TypeLiteral.class, 0, 7, ast);
		assertEquals(java.lang.Integer.class, ((TypeLiteral)ast).getValue());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void realTypeLiteral() {
		IQueryBuilderEngine.AstResult build = engine.build("Real");
		Expression ast = build.getAst();

		assertExpression(build, TypeLiteral.class, 0, 4, ast);
		assertEquals(java.lang.Double.class, ((TypeLiteral)ast).getValue());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void classifierError() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::EClass");
		Expression ast = build.getAst();

		assertExpression(build, ErrorTypeLiteral.class, 0, 14, ast);
		assertFalse(((ErrorTypeLiteral)ast).isMissingColon());
		assertEquals("anydsl", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals("EClass", ((ErrorTypeLiteral)ast).getSegments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal anydsl::EClass", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void enumLiteral() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::Part::Other");
		Expression ast = build.getAst();

		assertExpression(build, EnumLiteral.class, 0, 19, ast);
		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other"), ((EnumLiteral)ast)
				.getLiteral());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void enumLiteralError() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::Part::NotExisting");
		Expression ast = build.getAst();

		assertExpression(build, ErrorEnumLiteral.class, 0, 25, ast);
		assertFalse(((ErrorEnumLiteral)ast).isMissingColon());
		assertEquals("anydsl", ((ErrorEnumLiteral)ast).getSegments().get(0));
		assertEquals("Part", ((ErrorEnumLiteral)ast).getSegments().get(1));
		assertEquals("NotExisting", ((ErrorEnumLiteral)ast).getSegments().get(2));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid enum literal: no literal registered with this name", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void precedingSiblings() {
		IQueryBuilderEngine.AstResult build = engine.build("self.precedingSiblings()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 24, ast);
		assertEquals("precedingSiblings", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void followingSiblings() {
		IQueryBuilderEngine.AstResult build = engine.build("self.followingSiblings()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 24, ast);
		assertEquals("followingSiblings", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void eInverse() {
		IQueryBuilderEngine.AstResult build = engine.build("self.eInverse()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 15, ast);
		assertEquals("eInverse", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
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
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void letOneBinding() {
		IQueryBuilderEngine.AstResult build = engine.build("let a = 'a' in a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 16, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, StringLiteral.class, 8, 11, ((Let)ast).getBindings().get(0).getValue());
		assertExpression(build, VarRef.class, 15, 16, ((Let)ast).getBody());
	}

	@Test
	public void letTwoBindings() {
		IQueryBuilderEngine.AstResult build = engine.build("let a = 'a', b = 'b' in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 29, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, StringLiteral.class, 8, 11, ((Let)ast).getBindings().get(0).getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertEquals(null, ((Let)ast).getBindings().get(1).getType());
		assertExpression(build, StringLiteral.class, 17, 20, ((Let)ast).getBindings().get(1).getValue());
		assertExpression(build, Call.class, 24, 29, ((Let)ast).getBody());
	}

	@Test
	public void letOneBindingWithType() {
		IQueryBuilderEngine.AstResult build = engine.build("let a : ecore::EString = 'a' in a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 33, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertTrue(((Let)ast).getBindings().get(0).getType() instanceof TypeLiteral);
		assertExpression(build, StringLiteral.class, 25, 28, ((Let)ast).getBindings().get(0).getValue());
		assertExpression(build, VarRef.class, 32, 33, ((Let)ast).getBody());
	}

	@Test
	public void letTwoBindingsWithType() {
		IQueryBuilderEngine.AstResult build = engine
				.build("let a : ecore::EString = 'a', b : ecore::EString = 'b' in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 63, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertTrue(((Let)ast).getBindings().get(0).getType() instanceof TypeLiteral);
		assertExpression(build, StringLiteral.class, 25, 28, ((Let)ast).getBindings().get(0).getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertTrue(((Let)ast).getBindings().get(1).getType() instanceof TypeLiteral);
		assertExpression(build, StringLiteral.class, 51, 54, ((Let)ast).getBindings().get(1).getValue());
		assertExpression(build, Call.class, 58, 63, ((Let)ast).getBody());
	}

	@Test
	public void nullTest() {
		IQueryBuilderEngine.AstResult build = engine.build(null);
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, -1, -1, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals("null or empty string.", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void emtpyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("");
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, 0, 0, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals("null or empty string.", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletNotTest() {
		IQueryBuilderEngine.AstResult build = engine.build("not");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 3, ast);
		assertEquals("not", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, ErrorExpression.class, 3, 3, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletMinTest() {
		IQueryBuilderEngine.AstResult build = engine.build("-");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 1, ast);
		assertEquals("unaryMin", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)build.getAst()).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, ErrorExpression.class, 1, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletMultTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self *");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("mult", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompleteDivTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self /");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("divOp", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletAddTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self +");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("add", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletSubTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self -");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("sub", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));

		build = engine.build("self - ");
		ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("sub", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletLessTanTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("lessThan", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletLessThanEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <=");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("lessThanEqual", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletGreaterThanTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self >");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("greaterThan", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletGreaterThanEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self >=");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("greaterThanEqual", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletEqualsTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self =");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 6, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletEqualsJavaStyleTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self ==");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletDiffersTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <>");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("differs", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletDiffersJavaStyleTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self !=");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 7, ast);
		assertEquals("differs", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletAndTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self and");
		Expression ast = build.getAst();

		assertExpression(build, And.class, 0, 8, ast);
		assertEquals("and", ((And)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((And)build.getAst()).getType());
		assertEquals(2, ((And)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((And)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 8, 8, ((And)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletOrTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self or");
		Expression ast = build.getAst();

		assertExpression(build, Or.class, 0, 7, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Or)ast).getType());
		assertEquals(2, ((Or)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Or)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 7, ((Or)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletXorTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self xor");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 8, ast);
		assertEquals("xor", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 8, 8, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletImpliesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self implies");
		Expression ast = build.getAst();

		assertExpression(build, Implies.class, 0, 12, ast);
		assertEquals("implies", ((Implies)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Implies)ast).getType());
		assertEquals(2, ((Implies)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Implies)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 12, 12, ((Implies)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletNavigationSegmentDotTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 5, ast);
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing feature access or service call", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletNavigationSegmentArrowTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->");
		Expression ast = build.getAst();

		assertExpression(build, ErrorCall.class, 0, 6, ast);
		assertExpression(build, VarRef.class, 0, 4, ((ErrorCall)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((ErrorCall)ast).getArguments().get(0)).getVariableName());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing collection service call", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletStringLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'str");
		Expression ast = build.getAst();

		assertExpression(build, ErrorStringLiteral.class, 0, 4, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("String literal is not properly closed by a simple-quote: 'str", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletStringLitTestInExpression() {
		IQueryBuilderEngine.AstResult build = engine.build("self = 'str");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 11, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorStringLiteral.class, 7, 11, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("String literal is not properly closed by a simple-quote: 'str", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletRealLitTest() {
		// TODO remove incomplete navigationSegment on integer ?
		IQueryBuilderEngine.AstResult build = engine.build("3.");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 2, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(ast, build.getErrors().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing feature access or service call", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletExplicitSeqLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 9, ast);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletExplicitSeqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 14, ast);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 14, ((SequenceInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void incompletExplicitSeqLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
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
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SetInExtensionLiteral.class, 0, 11, ast);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletExplicitSetLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SetInExtensionLiteral.class, 0, 16, ast);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 16, ((SetInExtensionLiteral)ast).getValues().get(0));
	}

	@Test
	public void incompletExplicitSetLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SetInExtensionLiteral.class, 0, 17, ast);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 16, ((SetInExtensionLiteral)ast).getValues().get(0));
		assertExpression(build, ErrorExpression.class, 17, 17, ((SetInExtensionLiteral)ast).getValues()
				.get(1));
		assertEquals(build.getErrors().get(0), ((SetInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletClassifierTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("toto::");
		Expression ast = build.getAst();

		assertExpression(build, ErrorTypeLiteral.class, 0, 6, ast);
		assertFalse(((ErrorTypeLiteral)ast).isMissingColon());
		assertEquals(1, ((ErrorTypeLiteral)ast).getSegments().size());
		assertEquals("toto", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal no viable alternative at input 'toto::'", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletEnumLitWithPackageTest() {
		IQueryBuilderEngine.AstResult build = engine.build("toto::tata::");
		Expression ast = build.getAst();

		assertExpression(build, ErrorEnumLiteral.class, 0, 12, ast);
		assertFalse(((ErrorEnumLiteral)ast).isMissingColon());
		assertEquals(2, ((ErrorEnumLiteral)ast).getSegments().size());
		assertEquals("toto", ((ErrorEnumLiteral)ast).getSegments().get(0));
		assertEquals("tata", ((ErrorEnumLiteral)ast).getSegments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid enum literal: missing literal name", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing Ident at ''", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void incompletIterationCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 13, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 13, 13, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorExpression.class, 13, 13, lambda.getExpression());
		assertEquals(1, lambda.getParameters().size());
		assertEquals(1, lambda.getParameters().size());
		assertVariableDeclaration(build, 13, 13, (VariableDeclaration)lambda.getParameters().get(0));
		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing variable declaration", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(e | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 21, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 17, 21, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 17, 21, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithVariableNameTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a |");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 17, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 17, 17, ((Call)ast).getArguments().get(1));
		assertExpression(build, ErrorExpression.class, 17, 17, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(2, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithVariableNameAndExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 22, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 18, 22, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 18, 22, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithMissingTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a :");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 17, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 17, 17, ((Call)ast).getArguments().get(1));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, ErrorTypeLiteral.class, 17, 17, ((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType());
		assertFalse(((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).isMissingColon());
		assertEquals(0, ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getSegments().size());
		assertExpression(build, ErrorExpression.class, 17, 17, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal no viable alternative at input '<EOF>'", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithErrorTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : ecore:: |");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 27, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 27, 27, ((Call)ast).getArguments().get(1));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, ErrorTypeLiteral.class, 18, 25, ((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType());
		assertFalse(((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).isMissingColon());
		assertEquals(1, ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getSegments().size());
		assertEquals("ecore", ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).getSegments().get(0));
		assertExpression(build, ErrorExpression.class, 27, 27, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal ecore::<missing Ident>", build.getDiagnostic().getChildren()
				.get(0).getMessage());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationNoPipeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : ecore::EClass");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 31, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 31, 31, ((Call)ast).getArguments().get(1));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, TypeLiteral.class, 18, 31, ((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType());
		assertEquals(true, ((TypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getValue() == EcorePackage.Literals.ECLASS);
		assertExpression(build, ErrorExpression.class, 31, 31, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("incomplete variable definition", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationWithPipeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : ecore::EClass |");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 33, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 33, 33, ((Call)ast).getArguments().get(1));
		assertExpression(build, ErrorExpression.class, 33, 33, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(2, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationAndExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : ecore::EClass | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 38, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 34, 38, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 34, 38, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletServiceCallNoParameterTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 13, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletServiceCallOneParameterTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service( true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 18, ast);
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals("service", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, BooleanLiteral.class, 14, 18, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletServiceCallOneParameterAndComaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service( true,");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 19, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(3, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, BooleanLiteral.class, 14, 18, ((Call)ast).getArguments().get(1));
		assertExpression(build, ErrorExpression.class, 19, 19, ((Call)ast).getArguments().get(2));
		assertEquals(2, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void incompletSeqTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(Sequence(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 21, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 21, ((Call)ast).getArguments().get(1));
		assertEquals(true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == List.class);
		assertExpression(build, ErrorTypeLiteral.class, 21, 21, ((CollectionTypeLiteral)((Call)ast)
				.getArguments().get(1)).getElementType());
		assertFalse(((ErrorTypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).isMissingColon());
		assertEquals(2, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal no viable alternative at input '<EOF>'", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void incompletSeqTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(Sequence(String");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 27, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 27, ((Call)ast).getArguments().get(1));
		assertExpression(build, TypeLiteral.class, 21, 27, ((CollectionTypeLiteral)((Call)ast).getArguments()
				.get(1)).getElementType());
		assertEquals(true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == List.class);
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType()
				.getValue() == String.class);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("mismatched input '' expecting ')'", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(((Call)ast).getArguments().get(1), build.getDiagnostic().getChildren().get(0).getData()
				.get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void incompletSetTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(OrderedSet(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 23, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 23, ((Call)ast).getArguments().get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertExpression(build, ErrorTypeLiteral.class, 23, 23, ((CollectionTypeLiteral)((Call)ast)
				.getArguments().get(1)).getElementType());
		assertFalse(((ErrorTypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).isMissingColon());
		assertEquals(2, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal no viable alternative at input '<EOF>'", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void incompletSetTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(OrderedSet(String");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 29, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 29, ((Call)ast).getArguments().get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertExpression(build, TypeLiteral.class, 23, 29, ((CollectionTypeLiteral)((Call)ast).getArguments()
				.get(1)).getElementType());
		assertEquals(true, ((TypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).getValue() == String.class);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("mismatched input '' expecting ')'", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(((Call)ast).getArguments().get(1), build.getDiagnostic().getChildren().get(0).getData()
				.get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void errorStringLiteralInSelectInCall() {
		final IQueryBuilderEngine.AstResult build = engine.build("self->select(a | a.startsWith('");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 31, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 17, 31, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 14, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, ErrorCall.class, 17, 31, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		final Call call = (ErrorCall)((Lambda)((Call)ast).getArguments().get(1)).getExpression();
		assertEquals("startsWith", call.getServiceName());
		assertEquals(CallType.CALLORAPPLY, call.getType());
		assertEquals(2, call.getArguments().size());
		assertExpression(build, VarRef.class, 17, 18, call.getArguments().get(0));
		assertExpression(build, ErrorStringLiteral.class, 30, 31, call.getArguments().get(1));
	}

	@Test
	public void errorStringLiteralAndCommaInSelectInCall() {
		final IQueryBuilderEngine.AstResult build = engine.build("self->select(a | a.startsWith(a, '");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 34, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 17, 34, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 14, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, ErrorCall.class, 17, 34, ((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		final Call call = (ErrorCall)((Lambda)((Call)ast).getArguments().get(1)).getExpression();
		assertEquals("startsWith", call.getServiceName());
		assertEquals(CallType.CALLORAPPLY, call.getType());
		assertEquals(3, call.getArguments().size());
		assertExpression(build, VarRef.class, 17, 18, call.getArguments().get(0));
		assertExpression(build, VarRef.class, 30, 31, call.getArguments().get(1));
		assertExpression(build, ErrorStringLiteral.class, 33, 34, call.getArguments().get(2));
	}

	@Test
	public void incompleteLetNoExpression() {
		IQueryBuilderEngine.AstResult build = engine.build("let a=2 in");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 10, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, IntegerLiteral.class, 6, 7, ((Let)ast).getBindings().get(0).getValue());
		assertExpression(build, ErrorExpression.class, 10, 10, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetNoBindingNoExpression() {
		IQueryBuilderEngine.AstResult build = engine.build("let");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 3, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertExpression(build, ErrorExpression.class, 3, 3, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetNoBinding() {
		IQueryBuilderEngine.AstResult build = engine.build("let in a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 8, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertTrue(((Let)ast).getBindings().get(0) instanceof ErrorBinding);
		assertEquals(null, ((ErrorBinding)((Let)ast).getBindings().get(0)).getName());
		assertEquals(null, ((ErrorBinding)((Let)ast).getBindings().get(0)).getType());
		assertExpression(build, ErrorExpression.class, 8, 8, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetIncompleteFirstBinding() {
		IQueryBuilderEngine.AstResult build = engine.build("let a= in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 15, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, ErrorExpression.class, 7, 7, ((Let)ast).getBindings().get(0).getValue());
		assertExpression(build, Call.class, 10, 15, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetIncompleteFirstBindingErrorType() {
		IQueryBuilderEngine.AstResult build = engine.build("let a : ecore:: = in a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 22, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertExpression(build, ErrorTypeLiteral.class, 8, 15, ((Let)ast).getBindings().get(0).getType());
		assertFalse(((ErrorTypeLiteral)((Let)ast).getBindings().get(0).getType()).isMissingColon());
		assertExpression(build, ErrorExpression.class, 18, 18, ((Let)ast).getBindings().get(0).getValue());
		assertExpression(build, VarRef.class, 21, 22, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetIncompleteSecondBinding() {
		IQueryBuilderEngine.AstResult build = engine.build("let a=2 ,b= in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 20, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, IntegerLiteral.class, 6, 7, ((Let)ast).getBindings().get(0).getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertEquals(null, ((Let)ast).getBindings().get(1).getType());
		assertExpression(build, ErrorExpression.class, 12, 12, ((Let)ast).getBindings().get(1).getValue());
		assertExpression(build, Call.class, 15, 20, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetIncompleteSecondBindingErrorType() {
		IQueryBuilderEngine.AstResult build = engine.build("let a=2 ,b : ecore:: = in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 31, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, IntegerLiteral.class, 6, 7, ((Let)ast).getBindings().get(0).getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertTrue(((Let)ast).getBindings().get(1).getType() instanceof ErrorTypeLiteral);
		assertExpression(build, ErrorExpression.class, 23, 23, ((Let)ast).getBindings().get(1).getValue());
		assertExpression(build, Call.class, 26, 31, ((Let)ast).getBody());
	}

	/**
	 * Test the ast builder on a conditional expression
	 */
	@Test
	public void testConditional() {
		final IQueryBuilderEngine.AstResult build = engine.build("if true then 5 else 'string' endif");
		Expression ast = build.getAst();

		assertFalse(ast instanceof ErrorConditional);
		assertExpression(build, Conditional.class, 0, 34, ast);
		assertExpression(build, BooleanLiteral.class, 3, 7, ((Conditional)ast).getPredicate());
		assertExpression(build, IntegerLiteral.class, 13, 14, ((Conditional)ast).getTrueBranch());
		assertExpression(build, StringLiteral.class, 20, 28, ((Conditional)ast).getFalseBranch());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void testIncompleteConditionIf() {
		final IQueryBuilderEngine.AstResult build = engine.build("if");
		Expression ast = build.getAst();

		assertExpression(build, ErrorConditional.class, 0, 2, ast);
		assertExpression(build, ErrorExpression.class, 2, 2, ((ErrorConditional)ast).getPredicate());
		assertEquals(null, ((ErrorConditional)ast).getTrueBranch());
		assertEquals(null, ((ErrorConditional)ast).getFalseBranch());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(((ErrorConditional)ast).getPredicate(), build.getDiagnostic().getChildren().get(0)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals("incomplet conditional", build.getDiagnostic().getChildren().get(1).getMessage());
	}

	@Test
	public void testIncompleteConditionIfThen() {
		final IQueryBuilderEngine.AstResult build = engine.build("if then");
		Expression ast = build.getAst();

		assertExpression(build, ErrorConditional.class, 0, 7, ast);
		assertExpression(build, ErrorExpression.class, 3, 3, ((ErrorConditional)ast).getPredicate());
		assertExpression(build, ErrorExpression.class, 7, 7, ((ErrorConditional)ast).getTrueBranch());
		assertEquals(null, ((ErrorConditional)ast).getFalseBranch());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(((ErrorConditional)ast).getPredicate(), build.getDiagnostic().getChildren().get(0)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(((ErrorConditional)ast).getTrueBranch(), build.getDiagnostic().getChildren().get(1)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(2).getData().get(0));
		assertEquals("incomplet conditional", build.getDiagnostic().getChildren().get(2).getMessage());
	}

	@Test
	public void testIncompleteConditionIfThenElse() {
		final IQueryBuilderEngine.AstResult build = engine.build("if then else");
		Expression ast = build.getAst();

		assertExpression(build, ErrorConditional.class, 0, 12, ast);
		assertExpression(build, ErrorExpression.class, 3, 3, ((ErrorConditional)ast).getPredicate());
		assertExpression(build, ErrorExpression.class, 8, 8, ((ErrorConditional)ast).getTrueBranch());
		assertExpression(build, ErrorExpression.class, 12, 12, ((ErrorConditional)ast).getFalseBranch());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(4, build.getDiagnostic().getChildren().size());
		assertEquals(((ErrorConditional)ast).getPredicate(), build.getDiagnostic().getChildren().get(0)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(((ErrorConditional)ast).getTrueBranch(), build.getDiagnostic().getChildren().get(1)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(((ErrorConditional)ast).getFalseBranch(), build.getDiagnostic().getChildren().get(2)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(3).getData().get(0));
		assertEquals("incomplet conditional", build.getDiagnostic().getChildren().get(3).getMessage());
	}

	@Test
	public void testIncompleteConditionNoExpressions() {
		final IQueryBuilderEngine.AstResult build = engine.build("if then else endif");
		Expression ast = build.getAst();

		assertExpression(build, ErrorConditional.class, 0, 18, ast);
		assertExpression(build, ErrorExpression.class, 3, 3, ((ErrorConditional)ast).getPredicate());
		assertExpression(build, ErrorExpression.class, 8, 8, ((ErrorConditional)ast).getTrueBranch());
		assertExpression(build, ErrorExpression.class, 13, 13, ((ErrorConditional)ast).getFalseBranch());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(4, build.getDiagnostic().getChildren().size());
		assertEquals(((ErrorConditional)ast).getPredicate(), build.getDiagnostic().getChildren().get(0)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(((ErrorConditional)ast).getTrueBranch(), build.getDiagnostic().getChildren().get(1)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(((ErrorConditional)ast).getFalseBranch(), build.getDiagnostic().getChildren().get(2)
				.getData().get(0));
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(3).getData().get(0));
		assertEquals("incomplet conditional", build.getDiagnostic().getChildren().get(3).getMessage());
	}

	@Test
	public void testNotPrecedenceEqual() {
		IQueryBuilderEngine.AstResult build = engine.build("not self.name = 'self'");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 22, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, Call.class, 0, 13, ((Call)ast).getArguments().get(0));
		Call not = (Call)((Call)ast).getArguments().get(0);
		assertEquals("not", not.getServiceName());
		assertEquals(1, not.getArguments().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void testBooleanOperatorsPrecedence1() {
		IQueryBuilderEngine.AstResult build = engine.build("not a.abstract and b.abstract or c.abstract");
		Expression ast = build.getAst();

		assertExpression(build, Or.class, 0, 43, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertEquals(2, ((Or)ast).getArguments().size());
		assertExpression(build, And.class, 0, 29, ((Or)ast).getArguments().get(0));
		And and = (And)((Or)ast).getArguments().get(0);
		assertEquals("and", and.getServiceName());
		assertEquals(2, and.getArguments().size());
		assertExpression(build, Call.class, 0, 14, ((And)and).getArguments().get(0));
		Call not = (Call)((And)and).getArguments().get(0);
		assertEquals("not", not.getServiceName());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void testBooleanOperatorsPrecedence2() {
		IQueryBuilderEngine.AstResult build = engine.build("a.abstract or not b.abstract and c.abstract");
		Expression ast = build.getAst();

		assertExpression(build, Or.class, 0, 43, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertEquals(2, ((Or)ast).getArguments().size());
		assertExpression(build, And.class, 14, 43, ((Or)ast).getArguments().get(1));
		And and = (And)((Or)ast).getArguments().get(1);
		assertEquals("and", and.getServiceName());
		assertEquals(2, and.getArguments().size());
		assertExpression(build, Call.class, 14, 28, ((And)and).getArguments().get(0));
		Call not = (Call)((Call)and).getArguments().get(0);
		assertEquals("not", not.getServiceName());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void testClassifierSetType() {
		IQueryBuilderEngine.AstResult build = engine
				.build("{ecore::EClass | ecore::EPackage | ecore::EAttribute}");
		Expression ast = build.getAst();

		assertExpression(build, TypeSetLiteral.class, 0, 53, ast);
		assertEquals(3, ((TypeSetLiteral)ast).getTypes().size());
		assertExpression(build, TypeLiteral.class, 1, 14, (TypeLiteral)((TypeSetLiteral)ast).getTypes()
				.get(0));
		assertExpression(build, TypeLiteral.class, 17, 32, (TypeLiteral)((TypeSetLiteral)ast).getTypes().get(
				1));
		assertExpression(build, TypeLiteral.class, 35, 52, (TypeLiteral)((TypeSetLiteral)ast).getTypes().get(
				2));
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void incompletClassifierSetType() {
		IQueryBuilderEngine.AstResult build = engine.build("{");
		Expression ast = build.getAst();

		assertExpression(build, TypeSetLiteral.class, 0, 1, ast);
		assertEquals(1, ((TypeSetLiteral)ast).getTypes().size());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals(((TypeSetLiteral)ast).getTypes().get(0), build.getDiagnostic().getChildren().get(0)
				.getData().get(0));
		assertEquals("missing classifier literal", build.getDiagnostic().getChildren().get(0).getMessage());
		assertExpression(build, TypeSetLiteral.class, 0, 1, ast);
	}

	@Test
	public void incompletClassifierSetTypeEmpty() {
		IQueryBuilderEngine.AstResult build = engine.build("{}");
		Expression ast = build.getAst();

		assertExpression(build, TypeSetLiteral.class, 0, 2, ast);
		assertEquals(1, ((TypeSetLiteral)ast).getTypes().size());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals(((TypeSetLiteral)ast).getTypes().get(0), build.getDiagnostic().getChildren().get(0)
				.getData().get(0));
		assertEquals("missing classifier literal", build.getDiagnostic().getChildren().get(0).getMessage());
	}

	@Test
	public void incompletClassifierSetTypeWithValue() {
		IQueryBuilderEngine.AstResult build = engine.build("{ecore::EClass");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, TypeSetLiteral.class, 0, 14, ast);
		assertEquals(1, ((TypeSetLiteral)ast).getTypes().size());
		assertExpression(build, TypeLiteral.class, 1, 14, ((TypeSetLiteral)ast).getTypes().get(0));
	}

	@Test
	public void incompletClassifierSetTypeWithValueAndPipe() {
		IQueryBuilderEngine.AstResult build = engine.build("{ecore::EClass |");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing classifier literal", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, TypeSetLiteral.class, 0, 16, ast);
		assertEquals(2, ((TypeSetLiteral)ast).getTypes().size());
		assertExpression(build, TypeLiteral.class, 1, 14, ((TypeSetLiteral)ast).getTypes().get(0));
		assertExpression(build, ErrorTypeLiteral.class, 16, 16, ((TypeSetLiteral)ast).getTypes().get(1));
		assertFalse(((ErrorTypeLiteral)((TypeSetLiteral)ast).getTypes().get(1)).isMissingColon());
		assertEquals(build.getErrors().get(0), ((TypeSetLiteral)ast).getTypes().get(1));
	}

	@Test
	public void incompletTypeSeparatorInSelect() {
		IQueryBuilderEngine.AstResult build = engine
				.build("self.packagedElement->select(e | e.oclIsTypeOf(uml:");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 51, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, Call.class, 0, 20, ((Call)ast).getArguments().get(0));
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME,
				((Call)((Call)ast).getArguments().get(0)).getServiceName());
		assertExpression(build, StringLiteral.class, 5, 20, ((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1));
		final StringLiteral featureName = (StringLiteral)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1);
		assertEquals("packagedElement", featureName.getValue());
		assertExpression(build, Lambda.class, 33, 51, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorCall.class, 33, 51, lambda.getExpression());
		final ErrorCall call = (ErrorCall)lambda.getExpression();
		assertEquals(2, call.getArguments().size());
		assertExpression(build, VarRef.class, 33, 34, call.getArguments().get(0));
		assertExpression(build, ErrorTypeLiteral.class, 47, 51, call.getArguments().get(1));
		assertTrue(((ErrorTypeLiteral)call.getArguments().get(1)).isMissingColon());

		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal uml:", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing collection service call", build.getDiagnostic().getChildren().get(1)
				.getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletEnumLiteralSeparatorInSelect() {
		IQueryBuilderEngine.AstResult build = engine
				.build("self.packagedElement->select(e | e.oclIsTypeOf(anydsl::Color:");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 61, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME,
				((Call)((Call)ast).getArguments().get(0)).getServiceName());
		assertExpression(build, StringLiteral.class, 5, 20, ((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1));
		final StringLiteral featureName = (StringLiteral)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1);
		assertEquals("packagedElement", featureName.getValue());
		assertExpression(build, Lambda.class, 33, 61, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorCall.class, 33, 61, lambda.getExpression());
		final ErrorCall call = (ErrorCall)lambda.getExpression();
		assertEquals(2, call.getArguments().size());
		assertExpression(build, VarRef.class, 33, 34, call.getArguments().get(0));
		assertExpression(build, ErrorEnumLiteral.class, 47, 61, call.getArguments().get(1));
		assertEquals(2, ((ErrorEnumLiteral)call.getArguments().get(1)).getSegments().size());
		assertTrue(((ErrorEnumLiteral)call.getArguments().get(1)).isMissingColon());

		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid enum literal: ':' instead of '::'", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing collection service call", build.getDiagnostic().getChildren().get(1)
				.getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletTypeLiteralInSelect() {
		IQueryBuilderEngine.AstResult build = engine
				.build("self.eAllContents(ecore::EClass)->select(a: ecore::");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 51, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, Call.class, 0, 32, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 51, 51, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorExpression.class, 51, 51, lambda.getExpression());
		assertExpression(build, ErrorTypeLiteral.class, 44, 51, lambda.getParameters().get(0).getType());

		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal ecore::", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletParentExpressionInSelect() {
		IQueryBuilderEngine.AstResult build = engine
				.build("self.value->select(value | not (value.owner.oclAsType(uml::Slot)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 64, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME,
				((Call)((Call)ast).getArguments().get(0)).getServiceName());
		assertExpression(build, StringLiteral.class, 5, 10, ((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1));
		final StringLiteral featureName = (StringLiteral)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1);
		assertEquals("value", featureName.getValue());
		assertExpression(build, Lambda.class, 27, 64, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, Call.class, 27, 64, lambda.getExpression());
		assertEquals("not", ((Call)lambda.getExpression()).getServiceName());
		assertEquals(1, ((Call)lambda.getExpression()).getArguments().size());

		assertEquals(2, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal uml::Slot", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("invalid iteration call", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void invalidLambda_484323() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{'hello'}->any(1)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 27, ast);
		assertEquals("any", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 19, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 25, 26, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, IntegerLiteral.class, 25, 26, lambda.getExpression());
		assertEquals(1, ((IntegerLiteral)lambda.getExpression()).getValue());
		assertEquals(1, lambda.getParameters().size());
		assertTrue(lambda.getParameters().get(0) instanceof ErrorVariableDeclaration);

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing variable declaration", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void invalidToken() {
		IQueryBuilderEngine.AstResult build = engine.build("self µ= null");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 12, ast);

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals("token recognition error at: 'µ'", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertTrue("token recognition error at: 'µ'", build.getDiagnostic().getChildren().get(0).getData()
				.isEmpty());
	}

	@Test
	public void emoji() {
		IQueryBuilderEngine.AstResult build = engine
				.build("Sequence{'\u1F61C','\u1F62D','\u1F63D','\u1F1EB\u1F1F7'}");
		Expression ast = build.getAst();

		assertExpression(build, SequenceInExtensionLiteral.class, 0, 31, ast);
		assertExpression(build, StringLiteral.class, 9, 13, ((SequenceInExtensionLiteral)ast).getValues()
				.get(0));
		assertEquals("\u1F61C", ((StringLiteral)((SequenceInExtensionLiteral)ast).getValues().get(0))
				.getValue());
		assertExpression(build, StringLiteral.class, 14, 18, ((SequenceInExtensionLiteral)ast).getValues()
				.get(1));
		assertEquals("\u1F62D", ((StringLiteral)((SequenceInExtensionLiteral)ast).getValues().get(1))
				.getValue());
		assertExpression(build, StringLiteral.class, 19, 23, ((SequenceInExtensionLiteral)ast).getValues()
				.get(2));
		assertEquals("\u1F63D", ((StringLiteral)((SequenceInExtensionLiteral)ast).getValues().get(2))
				.getValue());
		assertExpression(build, StringLiteral.class, 24, 30, ((SequenceInExtensionLiteral)ast).getValues()
				.get(3));
		assertEquals("\u1F1EB\u1F1F7", ((StringLiteral)((SequenceInExtensionLiteral)ast).getValues().get(3))
				.getValue());
	}

	@Test
	public void incompleteSelect_494432() {
		IQueryBuilderEngine.AstResult build = engine
				.build("var->select(oclIsKindOf(String) or oclIsKindOf(Integer))");
		Expression ast = build.getAst();

		assertExpression(build, ErrorCall.class, 0, 56, ast);
		assertEquals("select", ((ErrorCall)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((ErrorCall)ast).getType());
		assertFalse(((ErrorCall)ast).isMissingEndParenthesis());
	}

	@Test
	public void emptySelect() {
		IQueryBuilderEngine.AstResult build = engine.build("var->select()");
		Expression ast = build.getAst();

		assertExpression(build, ErrorCall.class, 0, 13, ast);
		assertEquals("select", ((ErrorCall)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((ErrorCall)ast).getType());
		assertFalse(((ErrorCall)ast).isMissingEndParenthesis());
		assertEquals(2, ((ErrorCall)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 3, ((ErrorCall)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 12, 12, ((ErrorCall)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((ErrorCall)ast).getArguments().get(1);
		assertEquals(1, lambda.getParameters().size());
		assertTrue(lambda.getParameters().get(0) instanceof ErrorVariableDeclaration);
		assertEquals(null, ((ErrorVariableDeclaration)lambda.getParameters().get(0)).getName());
		assertExpression(build, ErrorExpression.class, 12, 12, lambda.getExpression());
	}

}
