/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
import org.eclipse.acceleo.query.ast.ClassTypeLiteral;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorBinding;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.ErrorConditional;
import org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorStringLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
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
import org.eclipse.acceleo.query.parser.AstResult;
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

	private QueryBuilderEngine engine;

	private IQueryEnvironment queryEnvironment;

	@Before
	public void setup() {
		queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		engine = new QueryBuilderEngine();
	}

	/**
	 * Asserts the type and the positions of the given {@link Expression}.
	 * 
	 * @param astResult
	 *            the {@link AstResult}
	 * @param expectedClass
	 *            the expected {@link Class}
	 * @param expectedStartPosition
	 *            the expected start position
	 * @param expectedStartLine
	 *            the expected start line
	 * @param expectedStartColumn
	 *            the expected start column
	 * @param expectedEndPosition
	 *            the expected end position
	 * @param expectedEndLine
	 *            the expected end line
	 * @param expectedEndColumn
	 *            the expected end column
	 * @param actualExpression
	 *            the actual {@link Expression}
	 */
	private void assertExpression(AstResult astResult, Class<? extends Expression> expectedClass,
			int expectedStartPosition, int expectedStartLine, int expectedStartColumn,
			int expectedEndPosition, int expectedEndLine, int expectedEndColumn,
			Expression actualExpression) {
		assertTrue(expectedClass.isAssignableFrom(actualExpression.getClass()));
		if (!Error.class.isAssignableFrom(expectedClass)) {
			assertFalse(Error.class.isAssignableFrom(actualExpression.getClass()));
		}
		assertEquals(expectedStartPosition, astResult.getStartPosition(actualExpression));
		assertEquals(expectedStartLine, astResult.getStartLine(actualExpression));
		assertEquals(expectedStartColumn, astResult.getStartColumn(actualExpression));
		assertEquals(expectedEndPosition, astResult.getEndPosition(actualExpression));
		assertEquals(expectedEndLine, astResult.getEndLine(actualExpression));
		assertEquals(expectedEndColumn, astResult.getEndColumn(actualExpression));
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
	private void assertVariableDeclaration(AstResult astResult, int expectedStart, int expectedEnd,
			VariableDeclaration actualVariableDeclaration) {
		assertEquals(expectedStart, astResult.getStartPosition(actualVariableDeclaration));
		assertEquals(expectedEnd, astResult.getEndPosition(actualVariableDeclaration));
	}

	@Test
	public void variableTest() {
		AstResult build = engine.build("x");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 1, 0, 1, ast);
		assertEquals("x", ((VarRef)ast).getVariableName());
	}

	@Test
	public void variableTestWithUnderscore() {
		AstResult build = engine.build("_x");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 2, 0, 2, ast);
		assertEquals("_x", ((VarRef)ast).getVariableName());
	}

	@Test
	public void featureAccessTest() {
		AstResult build = engine.build("self.name");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 9, 0, 9, ast);
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		VarRef varRef = (VarRef)((Call)ast).getArguments().get(0);
		assertEquals("self", varRef.getVariableName());
		assertExpression(build, StringLiteral.class, 5, 0, 5, 9, 0, 9, ((Call)ast).getArguments().get(1));
		assertEquals("name", ((StringLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void featureAccessTestWithUnderscore() {
		AstResult build = engine.build("self._name");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 10, 0, 10, ast);
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		VarRef varRef = (VarRef)((Call)ast).getArguments().get(0);
		assertEquals("self", varRef.getVariableName());
		assertExpression(build, StringLiteral.class, 5, 0, 5, 10, 0, 10, ((Call)ast).getArguments().get(1));
		assertEquals("name", ((StringLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void intliteralTest() {
		AstResult build = engine.build("2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, IntegerLiteral.class, 0, 0, 0, 1, 0, 1, ast);
		assertEquals(2, ((IntegerLiteral)ast).getValue());
	}

	@Test
	public void intliteralCallTest() {
		AstResult build = engine.build("2.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 12, 0, 12, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
	}

	@Test
	public void realliteralTest() {
		AstResult build = engine.build("1.0");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, RealLiteral.class, 0, 0, 0, 3, 0, 3, ast);
		assertEquals(1.0, ((RealLiteral)ast).getValue(), 0.1);
	}

	@Test
	public void realliteralCallTest() {
		AstResult build = engine.build("1.0.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, RealLiteral.class, 0, 0, 0, 3, 0, 3, ((Call)ast).getArguments().get(0));
		assertEquals(1.0, ((RealLiteral)((Call)ast).getArguments().get(0)).getValue(), 0.1);
	}

	@Test
	public void trueliteralTest() {
		AstResult build = engine.build("true");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, BooleanLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals(true, ((BooleanLiteral)ast).isValue());
	}

	@Test
	public void trueliteralCallTest() {
		AstResult build = engine.build("true.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 15, 0, 15, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void falseliteralTest() {
		AstResult build = engine.build("false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, BooleanLiteral.class, 0, 0, 0, 5, 0, 5, ast);
		assertEquals(false, ((BooleanLiteral)ast).isValue());
	}

	@Test
	public void falseliteralCallTest() {
		AstResult build = engine.build("false.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 16, 0, 16, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 0, 0, 5, 0, 5, ((Call)ast).getArguments().get(0));
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void stringliteralTest() {
		AstResult build = engine.build("'acceleo query is great'");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 24, 0, 24, ast);
		assertEquals("acceleo query is great", ((StringLiteral)ast).getValue());
	}

	@Test
	public void stringliteralEscapeTest() {
		AstResult build = engine.build("'\\b'");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("\b", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\b'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 5, 0, 5, ast);
		assertEquals("\\b", ((StringLiteral)ast).getValue());

		build = engine.build("'\\t'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("\t", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\t'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 5, 0, 5, ast);
		assertEquals("\\t", ((StringLiteral)ast).getValue());

		build = engine.build("'\\n'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("\n", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\n'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 5, 0, 5, ast);
		assertEquals("\\n", ((StringLiteral)ast).getValue());

		build = engine.build("'\\f'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("\f", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\f'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 5, 0, 5, ast);
		assertEquals("\\f", ((StringLiteral)ast).getValue());

		build = engine.build("'\\r'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("\r", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\r'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 5, 0, 5, ast);
		assertEquals("\\r", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\"'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("\"", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\\"'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 5, 0, 5, ast);
		assertEquals("\\\"", ((StringLiteral)ast).getValue());

		build = engine.build("'\\''");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("\'", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("\\", ((StringLiteral)ast).getValue());

		build = engine.build("'\\x09'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 6, 0, 6, ast);
		assertEquals("\t", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\x09'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals("\\x09", ((StringLiteral)ast).getValue());

		build = engine.build("'\\u0041'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 8, 0, 8, ast);
		assertEquals("A", ((StringLiteral)ast).getValue());

		build = engine.build("'\\\\u0041'");
		ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 9, 0, 9, ast);
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
		AstResult build = engine.build("'acceleo query is great'.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 35, 0, 35, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 24, 0, 24, ((Call)ast).getArguments().get(0));
		assertEquals("acceleo query is great", ((StringLiteral)((Call)ast).getArguments().get(0)).getValue());
	}

	@Test
	public void nullliteralTest() {
		AstResult build = engine.build("null");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, NullLiteral.class, 0, 0, 0, 4, 0, 4, ast);
	}

	@Test
	public void nullliteralTestCall() {
		AstResult build = engine.build("null.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 15, 0, 15, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, NullLiteral.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void lowerEqualTest() {
		AstResult build = engine.build("1<=2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("lessThanEqual", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 0, 0, 1, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 3, 0, 3, 4, 0, 4, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void lowerTest() {
		AstResult build = engine.build("1<2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 3, 0, 3, ast);
		assertEquals("lessThan", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 0, 0, 1, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 2, 0, 2, 3, 0, 3, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void greaterEqualTest() {
		AstResult build = engine.build("1>=2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals("greaterThanEqual", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 0, 0, 1, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 3, 0, 3, 4, 0, 4, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void greaterTest() {
		AstResult build = engine.build("1>2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 3, 0, 3, ast);
		assertEquals("greaterThan", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 0, 0, 1, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 2, 0, 2, 3, 0, 3, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void addTest() {
		AstResult build = engine.build("'a' + 'b'");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 9, 0, 9, ast);
		assertEquals("add", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, StringLiteral.class, 0, 0, 0, 3, 0, 3, ((Call)ast).getArguments().get(0));
		assertEquals("a", ((StringLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, StringLiteral.class, 6, 0, 6, 9, 0, 9, ((Call)ast).getArguments().get(1));
		assertEquals("b", ((StringLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void orTest() {
		AstResult build = engine.build("true or false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Or.class, 0, 0, 0, 13, 0, 13, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Or)ast).getType());
		assertEquals(2, ((Or)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 0, 0, 4, 0, 4, ((Or)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Or)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 8, 0, 8, 13, 0, 13, ((Or)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((Or)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void xorTest() {
		AstResult build = engine.build("true xor false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals("xor", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 9, 0, 9, 14, 0, 14, ((Call)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void impliesTest() {
		AstResult build = engine.build("true implies false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Implies.class, 0, 0, 0, 18, 0, 18, ast);
		assertEquals("implies", ((Implies)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Implies)ast).getType());
		assertEquals(2, ((Implies)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 0, 0, 4, 0, 4, ((Implies)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Implies)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 13, 0, 13, 18, 0, 18, ((Implies)ast).getArguments().get(
				1));
		assertEquals(false, ((BooleanLiteral)((Implies)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void andTest() {
		AstResult build = engine.build("true and false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, And.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals("and", ((And)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((And)ast).getType());
		assertEquals(2, ((And)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 0, 0, 0, 4, 0, 4, ((And)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((And)ast).getArguments().get(0)).isValue());
		assertExpression(build, BooleanLiteral.class, 9, 0, 9, 14, 0, 14, ((And)ast).getArguments().get(1));
		assertEquals(false, ((BooleanLiteral)((And)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void notTest() {
		AstResult build = engine.build("not true");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 8, 0, 8, ast);
		assertEquals("not", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, BooleanLiteral.class, 4, 0, 4, 8, 0, 8, ((Call)ast).getArguments().get(0));
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void multTest() {
		AstResult build = engine.build("1*2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 3, 0, 3, ast);
		assertEquals("mult", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, IntegerLiteral.class, 0, 0, 0, 1, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertExpression(build, IntegerLiteral.class, 2, 0, 2, 3, 0, 3, ((Call)ast).getArguments().get(1));
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void sizeTest() {
		AstResult build = engine.build("self->size()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 12, 0, 12, ast);
		assertEquals("size", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
	}

	@Test
	public void selectTest() {
		AstResult build = engine.build("self->select(e | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 22, 0, 22, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 13, 0, 13, 21, 0, 21, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 14, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("e", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, BooleanLiteral.class, 17, 0, 17, 21, 0, 21, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
	}

	@Test
	public void selectWithVariableNameTest() {
		AstResult build = engine.build("self->select(var | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 24, 0, 24, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 13, 0, 13, 23, 0, 23, ((Call)ast).getArguments().get(1));
		assertEquals("var", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, BooleanLiteral.class, 19, 0, 19, 23, 0, 23, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
	}

	@Test
	public void selectWithVariableNameTestWithUnderscore() {
		AstResult build = engine.build("self->select(_var | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 25, 0, 25, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 13, 0, 13, 24, 0, 24, ((Call)ast).getArguments().get(1));
		assertEquals("_var", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, BooleanLiteral.class, 20, 0, 20, 24, 0, 24, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
	}

	@Test
	public void selectWithVariableNameAndTypeTest() {
		AstResult build = engine.build("self->select(var : ecore::EClass | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 40, 0, 40, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 13, 0, 13, 39, 0, 39, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 32, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("var", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, EClassifierTypeLiteral.class, 19, 0, 19, 32, 0, 32,
				((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters().get(0))
						.getType());
		assertEquals(EcorePackage.Literals.ECLASS.getName(),
				((EClassifierTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
						.getParameters().get(0)).getType()).getEClassifierName());
		assertExpression(build, BooleanLiteral.class, 35, 0, 35, 39, 0, 39, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
	}

	@Test
	public void explicitSeqLitEmptyTest() {
		AstResult build = engine.build("Sequence{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 0, 0, 10, 0, 10, ast);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void explicitSeqLitWithValueTest() {
		AstResult build = engine.build("Sequence{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 0, 0, 16, 0, 16, ast);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 0, 10, 14, 0, 14, ((SequenceInExtensionLiteral)ast)
				.getValues().get(0));
	}

	@Test
	public void explicitSeqLitWithValuesTest() {
		AstResult build = engine.build("Sequence{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 0, 0, 22, 0, 22, ast);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 0, 10, 14, 0, 14, ((SequenceInExtensionLiteral)ast)
				.getValues().get(0));
		assertExpression(build, BooleanLiteral.class, 16, 0, 16, 20, 0, 20, ((SequenceInExtensionLiteral)ast)
				.getValues().get(1));
	}

	@Test
	public void explicitSeqLitWithValuesCallTest() {
		AstResult build = engine.build("Sequence{ self, true }.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 33, 0, 33, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 0, 0, 22, 0, 22, ((Call)ast)
				.getArguments().get(0));
	}

	@Test
	public void explicitSetLitEmptyTest() {
		AstResult build = engine.build("OrderedSet{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 0, 0, 12, 0, 12, ast);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void explicitSetLitWithValueTest() {
		AstResult build = engine.build("OrderedSet{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 0, 0, 18, 0, 18, ast);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 0, 12, 16, 0, 16, ((SetInExtensionLiteral)ast).getValues()
				.get(0));
	}

	@Test
	public void explicitSetLitWithValuesTest() {
		AstResult build = engine.build("OrderedSet{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 0, 0, 24, 0, 24, ast);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 0, 12, 16, 0, 16, ((SetInExtensionLiteral)ast).getValues()
				.get(0));
		assertExpression(build, BooleanLiteral.class, 18, 0, 18, 22, 0, 22, ((SetInExtensionLiteral)ast)
				.getValues().get(1));
	}

	@Test
	public void explicitSetLitWithValuesCallTest() {
		AstResult build = engine.build("OrderedSet{ self, true }.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 35, 0, 35, ast);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 0, 0, 24, 0, 24, ((Call)ast).getArguments()
				.get(0));
	}

	@Test
	public void seqTypeWithTypeTest() {
		AstResult build = engine.build("self.filter(Sequence(String))");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 29, 0, 29, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 0, 12, 28, 0, 28, ((Call)ast).getArguments()
				.get(1));
		assertExpression(build, TypeLiteral.class, 21, 0, 21, 27, 0, 27, ((CollectionTypeLiteral)((Call)ast)
				.getArguments().get(1)).getElementType());
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getValue() == List.class);
		assertEquals(true, ((ClassTypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).getValue() == String.class);
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void setTypeWithTypeTest() {
		AstResult build = engine.build("self.filter(OrderedSet(String))");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 31, 0, 31, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 0, 12, 30, 0, 30, ((Call)ast).getArguments()
				.get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getValue() == Set.class);
		assertExpression(build, TypeLiteral.class, 23, 0, 23, 29, 0, 29, ((CollectionTypeLiteral)((Call)ast)
				.getArguments().get(1)).getElementType());
		assertEquals(true, ((ClassTypeLiteral)((TypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments()
				.get(1)).getElementType())).getValue() == String.class);
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void typeLiteral() {
		AstResult build = engine.build("ecore::EClass");
		Expression ast = build.getAst();

		assertExpression(build, EClassifierTypeLiteral.class, 0, 0, 0, 13, 0, 13, ast);
		assertEquals(EcorePackage.eINSTANCE.getEClass().getName(), ((EClassifierTypeLiteral)ast)
				.getEClassifierName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void integerTypeLiteral() {
		AstResult build = engine.build("Integer");
		Expression ast = build.getAst();

		assertExpression(build, ClassTypeLiteral.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals(java.lang.Integer.class, ((ClassTypeLiteral)ast).getValue());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void realTypeLiteral() {
		AstResult build = engine.build("Real");
		Expression ast = build.getAst();

		assertExpression(build, ClassTypeLiteral.class, 0, 0, 0, 4, 0, 4, ast);
		assertEquals(java.lang.Double.class, ((ClassTypeLiteral)ast).getValue());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void classifier() {
		AstResult build = engine.build("anydsl::Part");
		Expression ast = build.getAst();

		assertExpression(build, EClassifierTypeLiteral.class, 0, 0, 0, 12, 0, 12, ast);
		assertEquals("anydsl", ((EClassifierTypeLiteral)ast).getEPackageName());
		assertEquals("Part", ((EClassifierTypeLiteral)ast).getEClassifierName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
	}

	@Test
	public void classifierWithUnderscore() {
		AstResult build = engine.build("_anydsl::_Part");
		Expression ast = build.getAst();

		assertExpression(build, EClassifierTypeLiteral.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals("anydsl", ((EClassifierTypeLiteral)ast).getEPackageName());
		assertEquals("Part", ((EClassifierTypeLiteral)ast).getEClassifierName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
	}

	@Test
	public void classifierError() {
		AstResult build = engine.build("anydsl::EClass");
		Expression ast = build.getAst();

		assertExpression(build, EClassifierTypeLiteral.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals("anydsl", ((EClassifierTypeLiteral)ast).getEPackageName());
		assertEquals("EClass", ((EClassifierTypeLiteral)ast).getEClassifierName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
	}

	@Test
	public void enumLiteral() {
		AstResult build = engine.build("anydsl::Part::Other");
		Expression ast = build.getAst();

		assertExpression(build, EnumLiteral.class, 0, 0, 0, 19, 0, 19, ast);
		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other").getName(), ((EnumLiteral)ast)
				.getEEnumLiteralName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void enumLiteralWithUnderscore() {
		AstResult build = engine.build("_anydsl::_Part::_Other");
		Expression ast = build.getAst();

		assertExpression(build, EnumLiteral.class, 0, 0, 0, 22, 0, 22, ast);
		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other").getName(), ((EnumLiteral)ast)
				.getEEnumLiteralName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void enumLiteralError() {
		AstResult build = engine.build("anydsl::Part::NotExisting");
		Expression ast = build.getAst();

		assertExpression(build, EnumLiteral.class, 0, 0, 0, 25, 0, 25, ast);
		assertEquals("anydsl", ((EnumLiteral)ast).getEPackageName());
		assertEquals("Part", ((EnumLiteral)ast).getEEnumName());
		assertEquals("NotExisting", ((EnumLiteral)ast).getEEnumLiteralName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
	}

	@Test
	public void precedingSiblings() {
		AstResult build = engine.build("self.precedingSiblings()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 24, 0, 24, ast);
		assertEquals("precedingSiblings", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void precedingSiblingsType() {
		AstResult build = engine.build("self.precedingSiblings(ecore::EClass)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 37, 0, 37, ast);
		assertEquals("precedingSiblings", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, EClassifierTypeLiteral.class, 23, 0, 23, 36, 0, 36, ((Call)ast).getArguments()
				.get(1));
		final EClassifierTypeLiteral typeLiteral = (EClassifierTypeLiteral)((Call)ast).getArguments().get(1);
		assertEquals(EcorePackage.eINSTANCE.getEClass().getName(), typeLiteral.getEClassifierName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void followingSiblingsType() {
		AstResult build = engine.build("self.followingSiblings(ecore::EClass)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 37, 0, 37, ast);
		assertEquals("followingSiblings", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, EClassifierTypeLiteral.class, 23, 0, 23, 36, 0, 36, ((Call)ast).getArguments()
				.get(1));
		final EClassifierTypeLiteral typeLiteral = (EClassifierTypeLiteral)((Call)ast).getArguments().get(1);
		assertEquals(EcorePackage.eINSTANCE.getEClass().getName(), typeLiteral.getEClassifierName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void followingSiblings() {
		AstResult build = engine.build("self.followingSiblings()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 24, 0, 24, ast);
		assertEquals("followingSiblings", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void eInverse() {
		AstResult build = engine.build("self.eInverse()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 15, 0, 15, ast);
		assertEquals("eInverse", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void eInverseType() {
		AstResult build = engine.build("self.eInverse(ecore::EClass)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 28, 0, 28, ast);
		assertEquals("eInverse", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, EClassifierTypeLiteral.class, 14, 0, 14, 27, 0, 27, ((Call)ast).getArguments()
				.get(1));
		final EClassifierTypeLiteral typeLiteral = (EClassifierTypeLiteral)((Call)ast).getArguments().get(1);
		assertEquals(EcorePackage.eINSTANCE.getEClass().getName(), typeLiteral.getEClassifierName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void eInverseString() {
		AstResult build = engine.build("self.eInverse('name')");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 21, 0, 21, ast);
		assertEquals("eInverse", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, StringLiteral.class, 14, 0, 14, 20, 0, 20, ((Call)ast).getArguments().get(1));
		final StringLiteral stringLiteral = (StringLiteral)((Call)ast).getArguments().get(1);
		assertEquals("name", stringLiteral.getValue());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void letOneBinding() {
		AstResult build = engine.build("let a = 'a' in a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 16, 0, 16, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, StringLiteral.class, 8, 0, 8, 11, 0, 11, ((Let)ast).getBindings().get(0)
				.getValue());
		assertExpression(build, VarRef.class, 15, 0, 15, 16, 0, 16, ((Let)ast).getBody());
	}

	@Test
	public void letOneBindingWithUnderscore() {
		AstResult build = engine.build("let _a = 'a' in _a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 18, 0, 18, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("_a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, StringLiteral.class, 9, 0, 9, 12, 0, 12, ((Let)ast).getBindings().get(0)
				.getValue());
		assertExpression(build, VarRef.class, 16, 0, 16, 18, 0, 18, ((Let)ast).getBody());
	}

	@Test
	public void letTwoBindings() {
		AstResult build = engine.build("let a = 'a', b = 'b' in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 29, 0, 29, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, StringLiteral.class, 8, 0, 8, 11, 0, 11, ((Let)ast).getBindings().get(0)
				.getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertEquals(null, ((Let)ast).getBindings().get(1).getType());
		assertExpression(build, StringLiteral.class, 17, 0, 17, 20, 0, 20, ((Let)ast).getBindings().get(1)
				.getValue());
		assertExpression(build, Call.class, 24, 0, 24, 29, 0, 29, ((Let)ast).getBody());
	}

	@Test
	public void letOneBindingWithType() {
		AstResult build = engine.build("let a : ecore::EString = 'a' in a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 33, 0, 33, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertTrue(((Let)ast).getBindings().get(0).getType() instanceof TypeLiteral);
		assertExpression(build, StringLiteral.class, 25, 0, 25, 28, 0, 28, ((Let)ast).getBindings().get(0)
				.getValue());
		assertExpression(build, VarRef.class, 32, 0, 32, 33, 0, 33, ((Let)ast).getBody());
	}

	@Test
	public void letTwoBindingsWithType() {
		AstResult build = engine.build("let a : ecore::EString = 'a', b : ecore::EString = 'b' in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 63, 0, 63, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertTrue(((Let)ast).getBindings().get(0).getType() instanceof TypeLiteral);
		assertExpression(build, StringLiteral.class, 25, 0, 25, 28, 0, 28, ((Let)ast).getBindings().get(0)
				.getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertTrue(((Let)ast).getBindings().get(1).getType() instanceof TypeLiteral);
		assertExpression(build, StringLiteral.class, 51, 0, 51, 54, 0, 54, ((Let)ast).getBindings().get(1)
				.getValue());
		assertExpression(build, Call.class, 58, 0, 58, 63, 0, 63, ((Let)ast).getBody());
	}

	@Test
	public void letTwoBindingsWithTypeMultiLines() {
		AstResult build = engine.build(
				"let\na\n:\necore::EString\n=\n'a',\nb\n:\necore::EString\n=\n'b'\nin\na\n+\nb");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 63, 14, 1, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertTrue(((Let)ast).getBindings().get(0).getType() instanceof TypeLiteral);
		assertExpression(build, StringLiteral.class, 25, 5, 0, 28, 5, 3, ((Let)ast).getBindings().get(0)
				.getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertTrue(((Let)ast).getBindings().get(1).getType() instanceof TypeLiteral);
		assertExpression(build, StringLiteral.class, 51, 10, 0, 54, 10, 3, ((Let)ast).getBindings().get(1)
				.getValue());
		assertExpression(build, Call.class, 58, 12, 0, 63, 14, 1, ((Let)ast).getBody());
	}

	@Test
	public void LetNoIn() {
		AstResult build = engine.build("let v = self.name");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 17, 0, 17, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("v", ((Let)ast).getBindings().get(0).getName());
		assertExpression(build, ErrorExpression.class, 17, 0, 17, 17, 0, 17, ((Let)ast).getBody());
	}

	@Test
	public void nullTest() {
		AstResult build = engine.build(null);
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, -1, -1, -1, -1, -1, -1, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals("null or empty string.", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void emtpyTest() {
		AstResult build = engine.build("");
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, 0, 0, 0, 0, 0, 0, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals("null or empty string.", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletNotTest() {
		AstResult build = engine.build("not");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 3, 0, 3, ast);
		assertEquals("not", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, ErrorExpression.class, 3, 0, 3, 3, 0, 3, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletMinTest() {
		AstResult build = engine.build("-");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 1, 0, 1, ast);
		assertEquals("unaryMin", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)build.getAst()).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, ErrorExpression.class, 1, 0, 1, 1, 0, 1, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletMultTest() {
		AstResult build = engine.build("self *");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 6, 0, 6, ast);
		assertEquals("mult", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 0, 6, 6, 0, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompleteDivTest() {
		AstResult build = engine.build("self /");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 6, 0, 6, ast);
		assertEquals("divOp", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 0, 6, 6, 0, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletAddTest() {
		AstResult build = engine.build("self +");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 6, 0, 6, ast);
		assertEquals("add", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 0, 6, 6, 0, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletSubTest() {
		AstResult build = engine.build("self -");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 6, 0, 6, ast);
		assertEquals("sub", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 0, 6, 6, 0, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));

		build = engine.build("self - ");
		ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals("sub", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletLessTanTest() {
		AstResult build = engine.build("self <");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 6, 0, 6, ast);
		assertEquals("lessThan", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 0, 6, 6, 0, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletLessThanEqualTest() {
		AstResult build = engine.build("self <=");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals("lessThanEqual", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletGreaterThanTest() {
		AstResult build = engine.build("self >");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 6, 0, 6, ast);
		assertEquals("greaterThan", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 0, 6, 6, 0, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletGreaterThanEqualTest() {
		AstResult build = engine.build("self >=");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals("greaterThanEqual", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletEqualsTest() {
		AstResult build = engine.build("self =");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 6, 0, 6, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 6, 0, 6, 6, 0, 6, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletEqualsJavaStyleTest() {
		AstResult build = engine.build("self ==");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletDiffersTest() {
		AstResult build = engine.build("self <>");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals("differs", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletDiffersJavaStyleTest() {
		AstResult build = engine.build("self !=");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals("differs", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletAndTest() {
		AstResult build = engine.build("self and");
		Expression ast = build.getAst();

		assertExpression(build, And.class, 0, 0, 0, 8, 0, 8, ast);
		assertEquals("and", ((And)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((And)build.getAst()).getType());
		assertEquals(2, ((And)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((And)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 8, 0, 8, 8, 0, 8, ((And)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletOrTest() {
		AstResult build = engine.build("self or");
		Expression ast = build.getAst();

		assertExpression(build, Or.class, 0, 0, 0, 7, 0, 7, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Or)ast).getType());
		assertEquals(2, ((Or)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Or)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((Or)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletXorTest() {
		AstResult build = engine.build("self xor");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 8, 0, 8, ast);
		assertEquals("xor", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 8, 0, 8, 8, 0, 8, ((Call)ast).getArguments().get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletImpliesTest() {
		AstResult build = engine.build("self implies");
		Expression ast = build.getAst();

		assertExpression(build, Implies.class, 0, 0, 0, 12, 0, 12, ast);
		assertEquals("implies", ((Implies)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Implies)ast).getType());
		assertEquals(2, ((Implies)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Implies)ast).getArguments().get(0));
		assertExpression(build, ErrorExpression.class, 12, 0, 12, 12, 0, 12, ((Implies)ast).getArguments()
				.get(1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletNavigationSegmentDotTest() {
		AstResult build = engine.build("self.");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 5, 0, 5, ast);
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
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
		AstResult build = engine.build("self->");
		Expression ast = build.getAst();

		assertExpression(build, ErrorCall.class, 0, 0, 0, 6, 0, 6, ast);
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((ErrorCall)ast).getArguments().get(0));
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
		AstResult build = engine.build("'str");
		Expression ast = build.getAst();

		assertExpression(build, ErrorStringLiteral.class, 0, 0, 0, 1, 0, 1, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("String literal is not properly closed by a simple-quote.", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletStringLitTestInExpression() {
		AstResult build = engine.build("self = 'str");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 8, 0, 8, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorStringLiteral.class, 7, 0, 7, 8, 0, 8, ((Call)ast).getArguments().get(
				1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("String literal is not properly closed by a simple-quote.", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletRealLitTest() {
		// TODO remove incomplete navigationSegment on integer ?
		AstResult build = engine.build("3.");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 2, 0, 2, ast);
		assertFalse(((Call)ast).isSuperCall());
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
		AstResult build = engine.build("Sequence{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 0, 0, 9, 0, 9, ast);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletExplicitSeqLitWithValueTest() {
		AstResult build = engine.build("Sequence{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 0, 10, 14, 0, 14, ((SequenceInExtensionLiteral)ast)
				.getValues().get(0));
	}

	@Test
	public void incompletExplicitSeqLitWithValueAndCommaTest() {
		AstResult build = engine.build("Sequence{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SequenceInExtensionLiteral.class, 0, 0, 0, 15, 0, 15, ast);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 10, 0, 10, 14, 0, 14, ((SequenceInExtensionLiteral)ast)
				.getValues().get(0));
		assertExpression(build, ErrorExpression.class, 15, 0, 15, 15, 0, 15, ((SequenceInExtensionLiteral)ast)
				.getValues().get(1));
		assertEquals(build.getErrors().get(0), ((SequenceInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletExplicitSetLitTest() {
		AstResult build = engine.build("OrderedSet{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SetInExtensionLiteral.class, 0, 0, 0, 11, 0, 11, ast);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletExplicitSetLitWithValueTest() {
		AstResult build = engine.build("OrderedSet{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SetInExtensionLiteral.class, 0, 0, 0, 16, 0, 16, ast);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 0, 12, 16, 0, 16, ((SetInExtensionLiteral)ast).getValues()
				.get(0));
	}

	@Test
	public void incompletExplicitSetLitWithValueAndCommaTest() {
		AstResult build = engine.build("OrderedSet{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, SetInExtensionLiteral.class, 0, 0, 0, 17, 0, 17, ast);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertExpression(build, VarRef.class, 12, 0, 12, 16, 0, 16, ((SetInExtensionLiteral)ast).getValues()
				.get(0));
		assertExpression(build, ErrorExpression.class, 17, 0, 17, 17, 0, 17, ((SetInExtensionLiteral)ast)
				.getValues().get(1));
		assertEquals(build.getErrors().get(0), ((SetInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletClassifierTypeTest() {
		AstResult build = engine.build("toto::");
		Expression ast = build.getAst();

		assertExpression(build, ErrorEClassifierTypeLiteral.class, 0, 0, 0, 6, 0, 6, ast);
		assertFalse(((ErrorEClassifierTypeLiteral)ast).isMissingColon());
		assertEquals("toto", ((ErrorEClassifierTypeLiteral)ast).getEPackageName());
		assertEquals(null, ((ErrorEClassifierTypeLiteral)ast).getEClassifierName());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal no viable alternative at input 'toto::'", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletClassifierTypeTestWithUnderscore() {
		AstResult build = engine.build("_toto::");
		Expression ast = build.getAst();

		assertExpression(build, ErrorEClassifierTypeLiteral.class, 0, 0, 0, 7, 0, 7, ast);
		assertFalse(((ErrorEClassifierTypeLiteral)ast).isMissingColon());
		assertEquals("toto", ((ErrorEClassifierTypeLiteral)ast).getEPackageName());
		assertEquals(null, ((ErrorEClassifierTypeLiteral)ast).getEClassifierName());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal no viable alternative at input '_toto::'", build.getDiagnostic()
				.getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletEnumLitWithPackageTest() {
		AstResult build = engine.build("toto::tata::");
		Expression ast = build.getAst();

		assertExpression(build, ErrorEnumLiteral.class, 0, 0, 0, 12, 0, 12, ast);
		assertFalse(((ErrorEnumLiteral)ast).isMissingColon());
		assertEquals("toto", ((ErrorEnumLiteral)ast).getEPackageName());
		assertEquals("tata", ((ErrorEnumLiteral)ast).getEEnumName());
		assertEquals(null, ((ErrorEnumLiteral)ast).getEEnumLiteralName());
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
	public void incompletEnumLitWithPackageTestWithUnderscore() {
		AstResult build = engine.build("_toto::_tata::");
		Expression ast = build.getAst();

		assertExpression(build, ErrorEnumLiteral.class, 0, 0, 0, 14, 0, 14, ast);
		assertFalse(((ErrorEnumLiteral)ast).isMissingColon());
		assertEquals("toto", ((ErrorEnumLiteral)ast).getEPackageName());
		assertEquals("tata", ((ErrorEnumLiteral)ast).getEEnumName());
		assertEquals(null, ((ErrorEnumLiteral)ast).getEEnumLiteralName());
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
		AstResult build = engine.build("self->select(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 13, 0, 13, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithExpressionTest() {
		AstResult build = engine.build("self->select(e | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 21, 0, 21, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 13, 0, 13, 21, 0, 21, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 17, 0, 17, 21, 0, 21, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithVariableNameTest() {
		AstResult build = engine.build("self->select( a |");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 17, 0, 17, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 14, 0, 14, 17, 0, 17, ((Call)ast).getArguments().get(1));
		assertExpression(build, ErrorExpression.class, 17, 0, 17, 17, 0, 17, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
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
		AstResult build = engine.build("self->select( a | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 22, 0, 22, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 14, 0, 14, 22, 0, 22, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 18, 0, 18, 22, 0, 22, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithMissingTypeTest() {
		AstResult build = engine.build("self->select( a :");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 17, 0, 17, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 14, 0, 14, 17, 0, 17, ((Call)ast)
				.getArguments().get(1));
		assertEquals("a", ((ErrorEClassifierTypeLiteral)((Call)ast).getArguments().get(1)).getEPackageName());
		assertEquals(2, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(2, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal a:", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithErrorTypeTest() {
		AstResult build = engine.build("self->select( a : ecore:: |");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 27, 0, 27, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 14, 0, 14, 27, 0, 27, ((Call)ast).getArguments().get(1));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 18, 0, 18, 25, 0, 25,
				((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters().get(0))
						.getType());
		assertFalse(((ErrorEClassifierTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).isMissingColon());
		assertEquals("ecore", ((ErrorEClassifierTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType()).getEPackageName());
		assertEquals(null, ((ErrorEClassifierTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType()).getEClassifierName());
		assertExpression(build, ErrorExpression.class, 27, 0, 27, 27, 0, 27, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal ecore::<missing Ident>", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationNoPipeTest() {
		AstResult build = engine.build("self->select( a : ecore::EClass");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 31, 0, 31, ast);
		assertFalse(((Call)ast).isSuperCall());
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 14, 0, 14, 31, 0, 31, ((Call)ast).getArguments().get(1));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertExpression(build, EClassifierTypeLiteral.class, 18, 0, 18, 31, 0, 31,
				((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters().get(0))
						.getType());
		assertEquals(EcorePackage.Literals.ECLASS.getName(),
				((EClassifierTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
						.getParameters().get(0)).getType()).getEClassifierName());
		assertExpression(build, ErrorExpression.class, 31, 0, 31, 31, 0, 31, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
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
		AstResult build = engine.build("self->select( a : ecore::EClass |");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 33, 0, 33, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 14, 0, 14, 33, 0, 33, ((Call)ast).getArguments().get(1));
		assertExpression(build, ErrorExpression.class, 33, 0, 33, 33, 0, 33, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
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
		AstResult build = engine.build("self->select( a : ecore::EClass | true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 38, 0, 38, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 14, 0, 14, 38, 0, 38, ((Call)ast).getArguments().get(1));
		assertExpression(build, BooleanLiteral.class, 34, 0, 34, 38, 0, 38, ((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void serviceCallNoParameterTest() {
		AstResult build = engine.build("self.service()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertExpression(build, Call.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void serviceCallNoParameterTestWithUnderscore() {
		AstResult build = engine.build("self._service()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertExpression(build, Call.class, 0, 0, 0, 15, 0, 15, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void incompletServiceCallNoParameterTest() {
		AstResult build = engine.build("self.service(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 13, 0, 13, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletServiceCallNoParameterTestWithUnderscore() {
		AstResult build = engine.build("self._service(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletServiceCallOneParameterTest() {
		AstResult build = engine.build("self.service( true");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 18, 0, 18, ast);
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals("service", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, BooleanLiteral.class, 14, 0, 14, 18, 0, 18, ((Call)ast).getArguments().get(
				1));
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void incompletServiceCallOneParameterAndComaTest() {
		AstResult build = engine.build("self.service( true,");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 19, 0, 19, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(3, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, BooleanLiteral.class, 14, 0, 14, 18, 0, 18, ((Call)ast).getArguments().get(
				1));
		assertExpression(build, ErrorExpression.class, 19, 0, 19, 19, 0, 19, ((Call)ast).getArguments().get(
				2));
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
		AstResult build = engine.build("self.filter(Sequence(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 21, 0, 21, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 0, 12, 21, 0, 21, ((Call)ast).getArguments()
				.get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getValue() == List.class);
		assertExpression(build, ErrorTypeLiteral.class, 21, 0, 21, 21, 0, 21,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType());
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
		AstResult build = engine.build("self.filter(Sequence(String");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 27, 0, 27, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 0, 12, 27, 0, 27, ((Call)ast).getArguments()
				.get(1));
		assertExpression(build, TypeLiteral.class, 21, 0, 21, 27, 0, 27, ((CollectionTypeLiteral)((Call)ast)
				.getArguments().get(1)).getElementType());
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getValue() == List.class);
		assertEquals(true, ((ClassTypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
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
	public void incompletSetTypeTest() {
		AstResult build = engine.build("self.filter(OrderedSet(");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 23, 0, 23, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 0, 12, 23, 0, 23, ((Call)ast).getArguments()
				.get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getValue() == Set.class);
		assertExpression(build, ErrorTypeLiteral.class, 23, 0, 23, 23, 0, 23,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType());
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
		AstResult build = engine.build("self.filter(OrderedSet(String");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 29, 0, 29, ast);
		assertEquals("filter", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, CollectionTypeLiteral.class, 12, 0, 12, 29, 0, 29, ((Call)ast).getArguments()
				.get(1));
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getValue() == Set.class);
		assertExpression(build, TypeLiteral.class, 23, 0, 23, 29, 0, 29, ((CollectionTypeLiteral)((Call)ast)
				.getArguments().get(1)).getElementType());
		assertEquals(true, ((ClassTypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
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
		final AstResult build = engine.build("self->select(a | a.startsWith('");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 31, 0, 31, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 13, 0, 13, 31, 0, 31, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 14, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, ErrorCall.class, 17, 0, 17, 31, 0, 31, ((Lambda)((Call)ast).getArguments()
				.get(1)).getExpression());
		final Call call = (ErrorCall)((Lambda)((Call)ast).getArguments().get(1)).getExpression();
		assertEquals("startsWith", call.getServiceName());
		assertFalse(call.isSuperCall());
		assertEquals(CallType.CALLORAPPLY, call.getType());
		assertEquals(2, call.getArguments().size());
		assertExpression(build, VarRef.class, 17, 0, 17, 18, 0, 18, call.getArguments().get(0));
		assertExpression(build, ErrorStringLiteral.class, 30, 0, 30, 31, 0, 31, call.getArguments().get(1));
	}

	@Test
	public void errorStringLiteralAndCommaInSelectInCall() {
		final AstResult build = engine.build("self->select(a | a.startsWith(a, '");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 34, 0, 34, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 13, 0, 13, 34, 0, 34, ((Call)ast).getArguments().get(1));
		assertVariableDeclaration(build, 13, 14, ((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0));
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertExpression(build, ErrorCall.class, 17, 0, 17, 34, 0, 34, ((Lambda)((Call)ast).getArguments()
				.get(1)).getExpression());
		final Call call = (ErrorCall)((Lambda)((Call)ast).getArguments().get(1)).getExpression();
		assertEquals("startsWith", call.getServiceName());
		assertFalse(call.isSuperCall());
		assertEquals(CallType.CALLORAPPLY, call.getType());
		assertEquals(3, call.getArguments().size());
		assertExpression(build, VarRef.class, 17, 0, 17, 18, 0, 18, call.getArguments().get(0));
		assertExpression(build, VarRef.class, 30, 0, 30, 31, 0, 31, call.getArguments().get(1));
		assertExpression(build, ErrorStringLiteral.class, 33, 0, 33, 34, 0, 34, call.getArguments().get(2));
	}

	@Test
	public void incompleteLetNoExpression() {
		AstResult build = engine.build("let a=2 in");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 10, 0, 10, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, IntegerLiteral.class, 6, 0, 6, 7, 0, 7, ((Let)ast).getBindings().get(0)
				.getValue());
		assertExpression(build, ErrorExpression.class, 10, 0, 10, 10, 0, 10, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetNoBindingNoExpression() {
		AstResult build = engine.build("let");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 3, 0, 3, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertExpression(build, ErrorExpression.class, 3, 0, 3, 3, 0, 3, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetNoBinding() {
		AstResult build = engine.build("let in a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 8, 0, 8, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertTrue(((Let)ast).getBindings().get(0) instanceof ErrorBinding);
		assertEquals(null, ((ErrorBinding)((Let)ast).getBindings().get(0)).getName());
		assertEquals(null, ((ErrorBinding)((Let)ast).getBindings().get(0)).getType());
		assertExpression(build, ErrorExpression.class, 8, 0, 8, 8, 0, 8, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetIncompleteFirstBinding() {
		AstResult build = engine.build("let a= in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 15, 0, 15, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((Let)ast).getBindings().get(0)
				.getValue());
		assertExpression(build, Call.class, 10, 0, 10, 15, 0, 15, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetIncompleteFirstBindingErrorType() {
		AstResult build = engine.build("let a : ecore:: = in a");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 22, 0, 22, ast);
		assertEquals(1, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 8, 0, 8, 15, 0, 15, ((Let)ast)
				.getBindings().get(0).getType());
		assertFalse(((ErrorEClassifierTypeLiteral)((Let)ast).getBindings().get(0).getType())
				.isMissingColon());
		assertExpression(build, ErrorExpression.class, 18, 0, 18, 18, 0, 18, ((Let)ast).getBindings().get(0)
				.getValue());
		assertExpression(build, VarRef.class, 21, 0, 21, 22, 0, 22, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetIncompleteSecondBinding() {
		AstResult build = engine.build("let a=2 ,b= in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 20, 0, 20, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, IntegerLiteral.class, 6, 0, 6, 7, 0, 7, ((Let)ast).getBindings().get(0)
				.getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertEquals(null, ((Let)ast).getBindings().get(1).getType());
		assertExpression(build, ErrorExpression.class, 12, 0, 12, 12, 0, 12, ((Let)ast).getBindings().get(1)
				.getValue());
		assertExpression(build, Call.class, 15, 0, 15, 20, 0, 20, ((Let)ast).getBody());
	}

	@Test
	public void incompleteLetIncompleteSecondBindingErrorType() {
		AstResult build = engine.build("let a=2 ,b : ecore:: = in a + b");
		final Expression ast = build.getAst();

		assertExpression(build, Let.class, 0, 0, 0, 31, 0, 31, ast);
		assertEquals(2, ((Let)ast).getBindings().size());
		assertEquals("a", ((Let)ast).getBindings().get(0).getName());
		assertEquals(null, ((Let)ast).getBindings().get(0).getType());
		assertExpression(build, IntegerLiteral.class, 6, 0, 6, 7, 0, 7, ((Let)ast).getBindings().get(0)
				.getValue());
		assertEquals("b", ((Let)ast).getBindings().get(1).getName());
		assertTrue(((Let)ast).getBindings().get(1).getType() instanceof ErrorEClassifierTypeLiteral);
		assertExpression(build, ErrorExpression.class, 23, 0, 23, 23, 0, 23, ((Let)ast).getBindings().get(1)
				.getValue());
		assertExpression(build, Call.class, 26, 0, 26, 31, 0, 31, ((Let)ast).getBody());
	}

	/**
	 * Test the ast builder on a conditional expression
	 */
	@Test
	public void testConditional() {
		final AstResult build = engine.build("if true then 5 else 'string' endif");
		Expression ast = build.getAst();

		assertFalse(ast instanceof ErrorConditional);
		assertExpression(build, Conditional.class, 0, 0, 0, 34, 0, 34, ast);
		assertExpression(build, BooleanLiteral.class, 3, 0, 3, 7, 0, 7, ((Conditional)ast).getPredicate());
		assertExpression(build, IntegerLiteral.class, 13, 0, 13, 14, 0, 14, ((Conditional)ast)
				.getTrueBranch());
		assertExpression(build, StringLiteral.class, 20, 0, 20, 28, 0, 28, ((Conditional)ast)
				.getFalseBranch());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void testIncompleteConditionIf() {
		final AstResult build = engine.build("if");
		Expression ast = build.getAst();

		assertExpression(build, ErrorConditional.class, 0, 0, 0, 2, 0, 2, ast);
		assertExpression(build, ErrorExpression.class, 2, 0, 2, 2, 0, 2, ((ErrorConditional)ast)
				.getPredicate());
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
		final AstResult build = engine.build("if then");
		Expression ast = build.getAst();

		assertExpression(build, ErrorConditional.class, 0, 0, 0, 7, 0, 7, ast);
		assertExpression(build, ErrorExpression.class, 3, 0, 3, 3, 0, 3, ((ErrorConditional)ast)
				.getPredicate());
		assertExpression(build, ErrorExpression.class, 7, 0, 7, 7, 0, 7, ((ErrorConditional)ast)
				.getTrueBranch());
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
		final AstResult build = engine.build("if then else");
		Expression ast = build.getAst();

		assertExpression(build, ErrorConditional.class, 0, 0, 0, 12, 0, 12, ast);
		assertExpression(build, ErrorExpression.class, 3, 0, 3, 3, 0, 3, ((ErrorConditional)ast)
				.getPredicate());
		assertExpression(build, ErrorExpression.class, 8, 0, 8, 8, 0, 8, ((ErrorConditional)ast)
				.getTrueBranch());
		assertExpression(build, ErrorExpression.class, 12, 0, 12, 12, 0, 12, ((ErrorConditional)ast)
				.getFalseBranch());
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
		final AstResult build = engine.build("if then else endif");
		Expression ast = build.getAst();

		assertExpression(build, ErrorConditional.class, 0, 0, 0, 18, 0, 18, ast);
		assertExpression(build, ErrorExpression.class, 3, 0, 3, 3, 0, 3, ((ErrorConditional)ast)
				.getPredicate());
		assertExpression(build, ErrorExpression.class, 8, 0, 8, 8, 0, 8, ((ErrorConditional)ast)
				.getTrueBranch());
		assertExpression(build, ErrorExpression.class, 13, 0, 13, 13, 0, 13, ((ErrorConditional)ast)
				.getFalseBranch());
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
		AstResult build = engine.build("not self.name = 'self'");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 22, 0, 22, ast);
		assertEquals("equals", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, Call.class, 0, 0, 0, 13, 0, 13, ((Call)ast).getArguments().get(0));
		Call not = (Call)((Call)ast).getArguments().get(0);
		assertEquals("not", not.getServiceName());
		assertFalse(not.isSuperCall());
		assertEquals(1, not.getArguments().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void testBooleanOperatorsPrecedence1() {
		AstResult build = engine.build("not a.abstract and b.abstract or c.abstract");
		Expression ast = build.getAst();

		assertExpression(build, Or.class, 0, 0, 0, 43, 0, 43, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertFalse(((Or)ast).isSuperCall());
		assertEquals(2, ((Or)ast).getArguments().size());
		assertExpression(build, And.class, 0, 0, 0, 29, 0, 29, ((Or)ast).getArguments().get(0));
		And and = (And)((Or)ast).getArguments().get(0);
		assertEquals("and", and.getServiceName());
		assertFalse(and.isSuperCall());
		assertEquals(2, and.getArguments().size());
		assertExpression(build, Call.class, 0, 0, 0, 14, 0, 14, ((And)and).getArguments().get(0));
		Call not = (Call)((And)and).getArguments().get(0);
		assertFalse(not.isSuperCall());
		assertEquals("not", not.getServiceName());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void testBooleanOperatorsPrecedence2() {
		AstResult build = engine.build("a.abstract or not b.abstract and c.abstract");
		Expression ast = build.getAst();

		assertExpression(build, Or.class, 0, 0, 0, 43, 0, 43, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertFalse(((Or)ast).isSuperCall());
		assertEquals(2, ((Or)ast).getArguments().size());
		assertExpression(build, And.class, 14, 0, 14, 43, 0, 43, ((Or)ast).getArguments().get(1));
		And and = (And)((Or)ast).getArguments().get(1);
		assertEquals("and", and.getServiceName());
		assertFalse(and.isSuperCall());
		assertEquals(2, and.getArguments().size());
		assertExpression(build, Call.class, 14, 0, 14, 28, 0, 28, ((And)and).getArguments().get(0));
		Call not = (Call)((Call)and).getArguments().get(0);
		assertFalse(not.isSuperCall());
		assertEquals("not", not.getServiceName());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void testClassifierSetType() {
		AstResult build = engine.build("{ecore::EClass | ecore::EPackage | ecore::EAttribute}");
		Expression ast = build.getAst();

		assertExpression(build, TypeSetLiteral.class, 0, 0, 0, 53, 0, 53, ast);
		assertEquals(3, ((TypeSetLiteral)ast).getTypes().size());
		assertExpression(build, TypeLiteral.class, 1, 0, 1, 14, 0, 14, (TypeLiteral)((TypeSetLiteral)ast)
				.getTypes().get(0));
		assertExpression(build, TypeLiteral.class, 17, 0, 17, 32, 0, 32, (TypeLiteral)((TypeSetLiteral)ast)
				.getTypes().get(1));
		assertExpression(build, TypeLiteral.class, 35, 0, 35, 52, 0, 52, (TypeLiteral)((TypeSetLiteral)ast)
				.getTypes().get(2));
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void incompletClassifierSetType() {
		AstResult build = engine.build("{");
		Expression ast = build.getAst();

		assertExpression(build, TypeSetLiteral.class, 0, 0, 0, 1, 0, 1, ast);
		assertEquals(1, ((TypeSetLiteral)ast).getTypes().size());
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals(((TypeSetLiteral)ast).getTypes().get(0), build.getDiagnostic().getChildren().get(0)
				.getData().get(0));
		assertEquals("missing classifier literal", build.getDiagnostic().getChildren().get(0).getMessage());
		assertExpression(build, TypeSetLiteral.class, 0, 0, 0, 1, 0, 1, ast);
	}

	@Test
	public void incompletClassifierSetTypeEmpty() {
		AstResult build = engine.build("{}");
		Expression ast = build.getAst();

		assertExpression(build, TypeSetLiteral.class, 0, 0, 0, 2, 0, 2, ast);
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
		AstResult build = engine.build("{ecore::EClass");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.WARNING, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing '}' at ''", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(ast, build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, TypeSetLiteral.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals(1, ((TypeSetLiteral)ast).getTypes().size());
		assertExpression(build, TypeLiteral.class, 1, 0, 1, 14, 0, 14, ((TypeSetLiteral)ast).getTypes().get(
				0));
	}

	@Test
	public void incompletClassifierSetTypeWithValueAndPipe() {
		AstResult build = engine.build("{ecore::EClass |");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing classifier literal", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertExpression(build, TypeSetLiteral.class, 0, 0, 0, 16, 0, 16, ast);
		assertEquals(2, ((TypeSetLiteral)ast).getTypes().size());
		assertExpression(build, TypeLiteral.class, 1, 0, 1, 14, 0, 14, ((TypeSetLiteral)ast).getTypes().get(
				0));
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 16, 0, 16, 16, 0, 16, ((TypeSetLiteral)ast)
				.getTypes().get(1));
		assertEquals(build.getErrors().get(0), ((TypeSetLiteral)ast).getTypes().get(1));
	}

	@Test
	public void incompletTypeSeparatorInSelect() {
		AstResult build = engine.build("self.packagedElement->select(e | e.oclIsTypeOf(uml:");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 51, 0, 51, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, Call.class, 0, 0, 0, 20, 0, 20, ((Call)ast).getArguments().get(0));
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, ((Call)((Call)ast).getArguments().get(0))
				.getServiceName());
		assertExpression(build, StringLiteral.class, 5, 0, 5, 20, 0, 20, ((Call)((Call)ast).getArguments()
				.get(0)).getArguments().get(1));
		final StringLiteral featureName = (StringLiteral)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1);
		assertEquals("packagedElement", featureName.getValue());
		assertExpression(build, Lambda.class, 29, 0, 29, 51, 0, 51, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorCall.class, 33, 0, 33, 51, 0, 51, lambda.getExpression());
		final ErrorCall call = (ErrorCall)lambda.getExpression();
		assertEquals(2, call.getArguments().size());
		assertExpression(build, VarRef.class, 33, 0, 33, 34, 0, 34, call.getArguments().get(0));
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 47, 0, 47, 51, 0, 51, call.getArguments()
				.get(1));
		assertTrue(((ErrorEClassifierTypeLiteral)call.getArguments().get(1)).isMissingColon());

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
		AstResult build = engine.build("self.packagedElement->select(e | e.oclIsTypeOf(anydsl::Color:");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 61, 0, 61, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, ((Call)((Call)ast).getArguments().get(0))
				.getServiceName());
		assertExpression(build, StringLiteral.class, 5, 0, 5, 20, 0, 20, ((Call)((Call)ast).getArguments()
				.get(0)).getArguments().get(1));
		final StringLiteral featureName = (StringLiteral)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1);
		assertEquals("packagedElement", featureName.getValue());
		assertExpression(build, Lambda.class, 29, 0, 29, 61, 0, 61, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorCall.class, 33, 0, 33, 61, 0, 61, lambda.getExpression());
		final ErrorCall call = (ErrorCall)lambda.getExpression();
		assertEquals(2, call.getArguments().size());
		assertExpression(build, VarRef.class, 33, 0, 33, 34, 0, 34, call.getArguments().get(0));
		assertExpression(build, ErrorEnumLiteral.class, 47, 0, 47, 61, 0, 61, call.getArguments().get(1));
		assertTrue(((ErrorEnumLiteral)call.getArguments().get(1)).isMissingColon());
		assertEquals("anydsl", ((ErrorEnumLiteral)call.getArguments().get(1)).getEPackageName());
		assertEquals("Color", ((ErrorEnumLiteral)call.getArguments().get(1)).getEEnumName());
		assertEquals(null, ((ErrorEnumLiteral)call.getArguments().get(1)).getEEnumLiteralName());

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
	public void incompletEnumLiteralSeparatorInSelectWithUnderscore() {
		AstResult build = engine.build("self.packagedElement->select(e | e.oclIsTypeOf(_anydsl::_Color:");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 63, 0, 63, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, ((Call)((Call)ast).getArguments().get(0))
				.getServiceName());
		assertExpression(build, StringLiteral.class, 5, 0, 5, 20, 0, 20, ((Call)((Call)ast).getArguments()
				.get(0)).getArguments().get(1));
		final StringLiteral featureName = (StringLiteral)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1);
		assertEquals("packagedElement", featureName.getValue());
		assertExpression(build, Lambda.class, 29, 0, 29, 63, 0, 63, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorCall.class, 33, 0, 33, 63, 0, 63, lambda.getExpression());
		final ErrorCall call = (ErrorCall)lambda.getExpression();
		assertEquals(2, call.getArguments().size());
		assertExpression(build, VarRef.class, 33, 0, 33, 34, 0, 34, call.getArguments().get(0));
		assertExpression(build, ErrorEnumLiteral.class, 47, 0, 47, 63, 0, 63, call.getArguments().get(1));
		assertTrue(((ErrorEnumLiteral)call.getArguments().get(1)).isMissingColon());
		assertEquals("anydsl", ((ErrorEnumLiteral)call.getArguments().get(1)).getEPackageName());
		assertEquals("Color", ((ErrorEnumLiteral)call.getArguments().get(1)).getEEnumName());
		assertEquals(null, ((ErrorEnumLiteral)call.getArguments().get(1)).getEEnumLiteralName());

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
		AstResult build = engine.build("self.eAllContents(ecore::EClass)->select(a: ecore::");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 51, 0, 51, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, Call.class, 0, 0, 0, 32, 0, 32, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 44, 0, 44, 51, 0, 51, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorExpression.class, 51, 0, 51, 51, 0, 51, lambda.getExpression());
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 44, 0, 44, 51, 0, 51, lambda
				.getParameters().get(0).getType());

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
	public void incompletTypeLiteralInSelectWithUndersocre() {
		AstResult build = engine.build("self.eAllContents(ecore::EClass)->select(a: _ecore::");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 52, 0, 52, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, Call.class, 0, 0, 0, 32, 0, 32, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 44, 0, 44, 52, 0, 52, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorExpression.class, 52, 0, 52, 52, 0, 52, lambda.getExpression());
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 44, 0, 44, 52, 0, 52, lambda
				.getParameters().get(0).getType());

		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal _ecore::", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(1).getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletTypeLiteralInSelectWithExpression() {
		AstResult build = engine.build("self.eAllContents(ecore::EClass)->select(a: ecore:: | a)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 56, 0, 56, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, Call.class, 0, 0, 0, 32, 0, 32, ((Call)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 41, 0, 41, 55, 0, 55, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorExpression.class, 55, 0, 55, 55, 0, 55, lambda.getExpression());
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 44, 0, 44, 56, 0, 56, lambda
				.getParameters().get(0).getType());

		assertEquals(3, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(3, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal ecore::|a", build.getDiagnostic().getChildren().get(0)
				.getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals("incomplete variable definition", build.getDiagnostic().getChildren().get(1)
				.getMessage());
		assertEquals(build.getErrors().get(1), build.getDiagnostic().getChildren().get(1).getData().get(0));
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(2).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(2).getMessage());
		assertEquals(build.getErrors().get(2), build.getDiagnostic().getChildren().get(2).getData().get(0));
	}

	@Test
	public void incompletTypeLiteralInSelectWithExpression_500204() {
		AstResult build = engine.build("aPackage->reject(aPackage: Package | aPackage.eContainer() != null)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 67, 0, 67, ast);
		assertEquals(2, ((Call)ast).getArguments().size());
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, VarRef.class, 0, 0, 0, 8, 0, 8, ((Call)ast).getArguments().get(0));
		assertEquals("aPackage", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertExpression(build, Lambda.class, 27, 0, 27, 66, 0, 66, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, ErrorEClassifierTypeLiteral.class, 27, 0, 27, 36, 0, 36, lambda
				.getParameters().get(0).getType());
		assertEquals(null, ((ErrorEClassifierTypeLiteral)lambda.getParameters().get(0).getType())
				.getEPackageName());
		assertEquals(null, ((ErrorEClassifierTypeLiteral)lambda.getParameters().get(0).getType())
				.getEClassifierName());
		assertEquals("", lambda.getParameters().get(0).getName());
		assertExpression(build, Call.class, 37, 0, 37, 66, 0, 66, lambda.getExpression());
		assertEquals("differs", ((Call)lambda.getExpression()).getServiceName());

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("invalid type literal ", build.getDiagnostic().getChildren().get(0).getMessage());

	}

	@Test
	public void incompletParentExpressionInSelect() {
		AstResult build = engine.build("self.value->select(value | not (value.owner.oclAsType(uml::Slot)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 64, 0, 64, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, ((Call)((Call)ast).getArguments().get(0))
				.getServiceName());
		assertExpression(build, StringLiteral.class, 5, 0, 5, 10, 0, 10, ((Call)((Call)ast).getArguments()
				.get(0)).getArguments().get(1));
		final StringLiteral featureName = (StringLiteral)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(1);
		assertEquals("value", featureName.getValue());
		assertExpression(build, Lambda.class, 19, 0, 19, 64, 0, 64, ((Call)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertExpression(build, Call.class, 27, 0, 27, 64, 0, 64, lambda.getExpression());
		assertEquals("not", ((Call)lambda.getExpression()).getServiceName());
		assertEquals(1, ((Call)lambda.getExpression()).getArguments().size());

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing ')'", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void invalidLambda_484323() {
		AstResult build = engine.build("OrderedSet{'hello'}->any(1)");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 27, 0, 27, ast);
		assertEquals("any", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, SetInExtensionLiteral.class, 0, 0, 0, 19, 0, 19, ((Call)ast).getArguments()
				.get(0));
		assertExpression(build, IntegerLiteral.class, 25, 0, 25, 26, 0, 26, ((Call)ast).getArguments().get(
				1));

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
	}

	@Test
	public void invalidToken() {
		AstResult build = engine.build("self µ= null");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 12, 0, 12, ast);

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
		AstResult build = engine.build("Sequence{'\u1F61C','\u1F62D','\u1F63D','\u1F1EB\u1F1F7'}");
		Expression ast = build.getAst();

		assertExpression(build, SequenceInExtensionLiteral.class, 0, 0, 0, 31, 0, 31, ast);
		assertExpression(build, StringLiteral.class, 9, 0, 9, 13, 0, 13, ((SequenceInExtensionLiteral)ast)
				.getValues().get(0));
		assertEquals("\u1F61C", ((StringLiteral)((SequenceInExtensionLiteral)ast).getValues().get(0))
				.getValue());
		assertExpression(build, StringLiteral.class, 14, 0, 14, 18, 0, 18, ((SequenceInExtensionLiteral)ast)
				.getValues().get(1));
		assertEquals("\u1F62D", ((StringLiteral)((SequenceInExtensionLiteral)ast).getValues().get(1))
				.getValue());
		assertExpression(build, StringLiteral.class, 19, 0, 19, 23, 0, 23, ((SequenceInExtensionLiteral)ast)
				.getValues().get(2));
		assertEquals("\u1F63D", ((StringLiteral)((SequenceInExtensionLiteral)ast).getValues().get(2))
				.getValue());
		assertExpression(build, StringLiteral.class, 24, 0, 24, 30, 0, 30, ((SequenceInExtensionLiteral)ast)
				.getValues().get(3));
		assertEquals("\u1F1EB\u1F1F7", ((StringLiteral)((SequenceInExtensionLiteral)ast).getValues().get(3))
				.getValue());
	}

	@Test
	public void incompleteSelect_494432() {
		AstResult build = engine.build("var->select(oclIsKindOf(String) or oclIsKindOf(Integer))");
		Expression ast = build.getAst();

		assertExpression(build, Or.class, 0, 0, 0, 46, 0, 46, ast);
		assertEquals("or", ((Or)ast).getServiceName());
		assertEquals(CallType.CALLSERVICE, ((Or)ast).getType());

		final Expression arg1 = ((Or)ast).getArguments().get(0);
		assertExpression(build, ErrorCall.class, 0, 0, 0, 24, 0, 24, arg1);
		assertEquals("select", ((ErrorCall)arg1).getServiceName());
		assertEquals(2, ((ErrorCall)arg1).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 3, 0, 3, ((ErrorCall)arg1).getArguments().get(0));
		assertEquals("var", ((VarRef)((ErrorCall)arg1).getArguments().get(0)).getVariableName());
		assertExpression(build, VarRef.class, 12, 0, 12, 23, 0, 23, ((ErrorCall)arg1).getArguments().get(1));
		assertEquals("oclIsKindOf", ((VarRef)((ErrorCall)arg1).getArguments().get(1)).getVariableName());

		final Expression arg2 = ((Or)ast).getArguments().get(1);
		assertExpression(build, VarRef.class, 35, 0, 35, 46, 0, 46, arg2);
		assertEquals("oclIsKindOf", ((VarRef)arg2).getVariableName());
	}

	@Test
	public void emptySelect() {
		AstResult build = engine.build("var->select()");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 13, 0, 13, ast);
		assertEquals("select", ((Call)ast).getServiceName());
		assertEquals(CallType.COLLECTIONCALL, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 3, 0, 3, ((Call)ast).getArguments().get(0));

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
	}

	@Test
	public void doubleDot() {
		AstResult build = engine.build("fke.primaryKeyColumn()..name");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 28, 0, 28, ast);
		assertEquals("aqlFeatureAccess", ((Call)ast).getServiceName());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertExpression(build, ErrorCall.class, 0, 0, 0, 24, 0, 24, ((Call)ast).getArguments().get(0));
		final ErrorCall arg0 = (ErrorCall)((Call)ast).getArguments().get(0);
		assertEquals(false, arg0.isMissingEndParenthesis());
		assertEquals("aqlFeatureAccess", arg0.getServiceName());
		assertFalse(arg0.isSuperCall());
		assertEquals(1, arg0.getArguments().size());
		assertExpression(build, Call.class, 0, 0, 0, 22, 0, 22, arg0.getArguments().get(0));
		assertEquals("primaryKeyColumn", ((Call)arg0.getArguments().get(0)).getServiceName());
		assertEquals(1, ((Call)arg0.getArguments().get(0)).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 3, 0, 3, ((Call)arg0.getArguments().get(0))
				.getArguments().get(0));
		assertExpression(build, StringLiteral.class, 24, 0, 24, 28, 0, 28, ((Call)ast).getArguments().get(1));
		final StringLiteral arg1 = (StringLiteral)((Call)ast).getArguments().get(1);
		assertEquals("name", arg1.getValue());
	}

	@Test()
	public void escapedStrings() {
		AstResult build = engine.build("'\\n' + '\\t' + '\\'' + '\\\\' + '\"'");
		Expression ast = build.getAst();

		assertExpression(build, Call.class, 0, 0, 0, 31, 0, 31, ast);
		assertEquals("add", ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLSERVICE, ((Call)ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
	}

	@Test
	public void classifierWithKeyword() {
		AstResult build = engine.build("_Real::_String");
		Expression ast = build.getAst();

		assertExpression(build, EClassifierTypeLiteral.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals("Real", ((EClassifierTypeLiteral)ast).getEPackageName());
		assertEquals("String", ((EClassifierTypeLiteral)ast).getEClassifierName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
	}

	@Test
	public void enumLiteralWithKeyword() {
		AstResult build = engine.build("_Real::_Integer::_String");
		Expression ast = build.getAst();

		assertExpression(build, EnumLiteral.class, 0, 0, 0, 24, 0, 24, ast);
		assertEquals("Real", ((EnumLiteral)ast).getEPackageName());
		assertEquals("Integer", ((EnumLiteral)ast).getEEnumName());
		assertEquals("String", ((EnumLiteral)ast).getEEnumLiteralName());
		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
	}

	@Test
	public void featureAccessWithKeyword() {
		AstResult build = engine.build("self._isUnique");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertEquals(0, build.getDiagnostic().getChildren().size());
		assertExpression(build, Call.class, 0, 0, 0, 14, 0, 14, ast);
		assertEquals(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, ((Call)ast).getServiceName());
		assertFalse(((Call)ast).isSuperCall());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
		VarRef varRef = (VarRef)((Call)ast).getArguments().get(0);
		assertEquals("self", varRef.getVariableName());
		assertExpression(build, StringLiteral.class, 5, 0, 5, 14, 0, 14, ((Call)ast).getArguments().get(1));
		assertEquals("isUnique", ((StringLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void missingVariableCall() {
		AstResult build = engine.build(".someService(a, b)");
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, 0, 0, 0, 0, 0, 0, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void missingVariableCollectionCall() {
		AstResult build = engine.build("->select(e | e)");
		Expression ast = build.getAst();

		assertExpression(build, ErrorExpression.class, 0, 0, 0, 0, 0, 0, ast);
		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertEquals(1, build.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("missing expression", build.getDiagnostic().getChildren().get(0).getMessage());
		assertEquals(build.getErrors().get(0), build.getDiagnostic().getChildren().get(0).getData().get(0));
	}

	@Test
	public void missingLambdaVariableWithExpression() {
		AstResult build = engine.build("self->select(a.startsWith(' '))");

		assertEquals(0, build.getDiagnostic().getChildren().size());
	}

	@Test
	public void superCall() {
		AstResult build = engine.build("self.super:service()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(Diagnostic.OK, build.getDiagnostic().getSeverity());
		assertExpression(build, Call.class, 0, 0, 0, 20, 0, 20, ast);
		assertEquals("service", ((Call)ast).getServiceName());
		assertTrue(((Call)ast).isSuperCall());
		assertEquals(CallType.CALLORAPPLY, ((Call)ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((Call)ast).getArguments().get(0));
	}

	@Test
	public void enumLiteralInSelectWithMissingClosingParenthesis() {
		AstResult build = engine.build("self->select(s | s = anydsl::Color::black");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(Diagnostic.ERROR, build.getDiagnostic().getSeverity());
		assertExpression(build, ErrorCall.class, 0, 0, 0, 41, 0, 41, ast);
		assertEquals("select", ((ErrorCall)ast).getServiceName());
		assertFalse(((ErrorCall)ast).isSuperCall());
		assertTrue(((ErrorCall)ast).isMissingEndParenthesis());
		assertEquals(CallType.COLLECTIONCALL, ((ErrorCall)ast).getType());
		assertEquals(2, ((ErrorCall)ast).getArguments().size());
		assertExpression(build, VarRef.class, 0, 0, 0, 4, 0, 4, ((ErrorCall)ast).getArguments().get(0));
		assertExpression(build, Lambda.class, 13, 0, 13, 41, 0, 41, ((ErrorCall)ast).getArguments().get(1));
		final Lambda lambda = (Lambda)((ErrorCall)ast).getArguments().get(1);
		assertExpression(build, Call.class, 17, 0, 17, 41, 0, 41, lambda.getExpression());
		assertFalse(((Call)lambda.getExpression()).isSuperCall());
		assertEquals("equals", ((Call)lambda.getExpression()).getServiceName());
		assertEquals(2, ((Call)lambda.getExpression()).getArguments().size());
		assertExpression(build, VarRef.class, 17, 0, 17, 18, 0, 18, ((Call)lambda.getExpression())
				.getArguments().get(0));
		assertExpression(build, EnumLiteral.class, 21, 0, 21, 41, 0, 41, ((Call)lambda.getExpression())
				.getArguments().get(1));
	}

}
