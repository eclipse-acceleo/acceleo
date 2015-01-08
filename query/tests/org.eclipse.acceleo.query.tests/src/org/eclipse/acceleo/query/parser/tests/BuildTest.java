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
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorCollectionCall;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuildTest {

	QueryBuilderEngine engine;

	IQueryEnvironment queryEnvironment;

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		queryEnvironment = new QueryEnvironment(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		engine = new QueryBuilderEngine(queryEnvironment);

	}

	@Test
	public void variableTest() {
		IQueryBuilderEngine.AstResult build = engine.build("x");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof VarRef);
		assertEquals("x", ((VarRef)ast).getVariableName());
	}

	@Test
	public void featureAccessTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.name");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof FeatureAccess);
		assertEquals("name", ((FeatureAccess)ast).getFeatureName());
		assertEquals(true, ((FeatureAccess)ast).getTarget() instanceof VarRef);
		VarRef varRef = (VarRef)((FeatureAccess)ast).getTarget();
		assertEquals("self", varRef.getVariableName());
	}

	@Test
	public void intliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof IntegerLiteral);
		assertEquals(2, ((IntegerLiteral)ast).getValue());
	}

	@Test
	public void intliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("2.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof IntegerLiteral);
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
	}

	@Test
	public void realliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1.0");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof RealLiteral);
		assertEquals(1.0, ((RealLiteral)ast).getValue(), 0.1);
	}

	@Test
	public void realliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1.0.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof RealLiteral);
		assertEquals(1.0, ((RealLiteral)((Call)ast).getArguments().get(0)).getValue(), 0.1);
	}

	@Test
	public void trueliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof BooleanLiteral);
		assertEquals(true, ((BooleanLiteral)ast).isValue());
	}

	@Test
	public void trueliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof BooleanLiteral);
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void falseliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof BooleanLiteral);
		assertEquals(false, ((BooleanLiteral)ast).isValue());
	}

	@Test
	public void falseliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("false.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof BooleanLiteral);
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void stringliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'acceleo query is great'");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof StringLiteral);
		assertEquals("acceleo query is great", ((StringLiteral)ast).getValue());
	}

	@Test
	public void stringliteralCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'acceleo query is great'.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof StringLiteral);
		assertEquals("acceleo query is great", ((StringLiteral)((Call)ast).getArguments().get(0)).getValue());
	}

	@Test
	public void nullliteralTest() {
		IQueryBuilderEngine.AstResult build = engine.build("null");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof NullLiteral);
	}

	@Test
	public void nullliteralTestCall() {
		IQueryBuilderEngine.AstResult build = engine.build("null.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof NullLiteral);
	}

	@Test
	public void lowerEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1<=2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("lessThanEqual", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof IntegerLiteral);
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof IntegerLiteral);
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void lowerTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1<2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("lessThan", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof IntegerLiteral);
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof IntegerLiteral);
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void greaterEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1>=2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("greaterThanEqual", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof IntegerLiteral);
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof IntegerLiteral);
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void greaterTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1>2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("greaterThan", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof IntegerLiteral);
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof IntegerLiteral);
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void addTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'a' + 'b'");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("add", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof StringLiteral);
		assertEquals("a", ((StringLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof StringLiteral);
		assertEquals("b", ((StringLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void orTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true or false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("or", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof BooleanLiteral);
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof BooleanLiteral);
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void andTest() {
		IQueryBuilderEngine.AstResult build = engine.build("true and false");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("and", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof BooleanLiteral);
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof BooleanLiteral);
		assertEquals(false, ((BooleanLiteral)((Call)ast).getArguments().get(1)).isValue());
	}

	@Test
	public void notTest() {
		IQueryBuilderEngine.AstResult build = engine.build("not true");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("not", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof BooleanLiteral);
		assertEquals(true, ((BooleanLiteral)((Call)ast).getArguments().get(0)).isValue());
	}

	@Test
	public void multTest() {
		IQueryBuilderEngine.AstResult build = engine.build("1*2");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("mult", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof IntegerLiteral);
		assertEquals(1, ((IntegerLiteral)((Call)ast).getArguments().get(0)).getValue());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof IntegerLiteral);
		assertEquals(2, ((IntegerLiteral)((Call)ast).getArguments().get(1)).getValue());
	}

	@Test
	public void sizeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->size()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("size", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.COLLECTIONCALL, ((Call) ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
	}

	@Test
	public void selectTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(e | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.COLLECTIONCALL, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals(
				true,
				((Lambda)((Call)ast).getArguments().get(1)).getParameters().get(0) instanceof VariableDeclaration);
		assertEquals("e", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof BooleanLiteral);
	}

	@Test
	public void selectWithVariableNameTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(var | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.COLLECTIONCALL, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals("var", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(null, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType());
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof BooleanLiteral);
	}

	@Test
	public void selectWithVariableNameAndTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(var : EClass | true)");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.COLLECTIONCALL, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals("self", ((VarRef)((Call)ast).getArguments().get(0)).getVariableName());
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals(
				true,
				((Lambda)((Call)ast).getArguments().get(1)).getParameters().get(0) instanceof VariableDeclaration);
		assertEquals("var", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(true, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType() instanceof TypeLiteral);
		assertEquals(true, ((TypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getValue() == EcorePackage.Literals.ECLASS);
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof BooleanLiteral);
	}

	@Test
	public void setLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void setLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
	}

	@Test
	public void setLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(1) instanceof BooleanLiteral);
	}

	@Test
	public void setLitWithValuesCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self, true }.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof SetInExtensionLiteral);
	}

	@Test
	public void seqLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[]");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void seqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self ]");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
	}

	@Test
	public void seqLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self, true ]");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(1) instanceof BooleanLiteral);
	}

	@Test
	public void seqLitWithValuesCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self, true ].toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof SequenceInExtensionLiteral);
	}

	@Test
	public void explicitSeqLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void explicitSeqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
	}

	@Test
	public void explicitSeqLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(1) instanceof BooleanLiteral);
	}

	@Test
	public void explicitSeqLitWithValuesCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self, true }.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof SequenceInExtensionLiteral);
	}

	@Test
	public void explicitSetLitEmptyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{}");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void explicitSetLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
	}

	@Test
	public void explicitSetLitWithValuesTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self, true }");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(1) instanceof BooleanLiteral);
	}

	@Test
	public void explicitSetLitWithValuesCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self, true }.toString()");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof Call);
		assertEquals("toString", ((Call)ast).getServiceName());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof SetInExtensionLiteral);
	}

	@Test
	public void seqTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(Sequence(String))");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof CollectionTypeLiteral);
		assertEquals(
				true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType() instanceof TypeLiteral);
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

		assertEquals(true, ast instanceof Call);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof CollectionTypeLiteral);
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertEquals(
				true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType() instanceof TypeLiteral);
		assertEquals(true, ((TypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).getValue() == String.class);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEClassifier() {
		IQueryBuilderEngine.AstResult build = engine.build("ecore::EClass");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof TypeLiteral);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((TypeLiteral)ast).getValue());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEEnumLiteral() {
		IQueryBuilderEngine.AstResult build = engine.build("Part::Other");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof EnumLiteral);
		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other"), ((EnumLiteral)ast)
				.getLiteral());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsError() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::EClass");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof ErrorTypeLiteral);
		assertEquals("anydsl", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals("EClass", ((ErrorTypeLiteral)ast).getSegments().get(1));
	}

	@Test
	public void enumLiteralOrEClassifierThreeSegments() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::Part::Other");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof EnumLiteral);
		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other"), ((EnumLiteral)ast)
				.getLiteral());
	}

	@Test
	public void enumLiteralOrEClassifierThreeSegmentsError() {
		IQueryBuilderEngine.AstResult build = engine.build("anydsl::Part::NotExisting");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof ErrorTypeLiteral);
		assertEquals("anydsl", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals("Part", ((ErrorTypeLiteral)ast).getSegments().get(1));
		assertEquals("NotExisting", ((ErrorTypeLiteral)ast).getSegments().get(2));
	}

	@Test
	public void nullTest() {
		IQueryBuilderEngine.AstResult build = engine.build(null);
		assertEquals(true, build.getAst() instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void emtpyTest() {
		IQueryBuilderEngine.AstResult build = engine.build("");
		assertEquals(true, build.getAst() instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletNotTest() {
		IQueryBuilderEngine.AstResult build = engine.build("not");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("not", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletMinTest() {
		IQueryBuilderEngine.AstResult build = engine.build("-");
		assertEquals(true, build.getAst() instanceof Call);
		assertEquals("unaryMin", ((Call)build.getAst()).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call)
		// build.getAst()).getType());
		assertEquals(1, ((Call)build.getAst()).getArguments().size());
		assertEquals(true, ((Call)build.getAst()).getArguments().get(0) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletMultTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self *");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("mult", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompleteDivTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self /");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("div", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletAddTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self +");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("add", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletSubTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self -");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("sub", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());

		build = engine.build("self - ");
		ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("sub", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletLessTanTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("lessThan", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletLessThanEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <=");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("lessThanEqual", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletGreaterThanTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self >");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("greaterThan", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletGreaterThanEqualTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self >=");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("greaterThanEqual", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletEqualsTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self =");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("equals", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletDiffersTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self <>");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("differs", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletAndTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self and");
		assertEquals(true, build.getAst() instanceof Call);
		assertEquals("and", ((Call)build.getAst()).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call)
		// build.getAst()).getType());
		assertEquals(2, ((Call)build.getAst()).getArguments().size());
		assertEquals(true, ((Call)build.getAst()).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)build.getAst()).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletOrTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self or");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("or", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletNavigationSegmentDotTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof ErrorFeatureAccessOrCall);
		assertEquals(true, ((ErrorFeatureAccessOrCall)ast).getTarget() instanceof VarRef);
		assertEquals("self", ((VarRef)((ErrorFeatureAccessOrCall)ast).getTarget()).getVariableName());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletNavigationSegmentArrowTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof ErrorCollectionCall);
		assertEquals(true, ((ErrorCollectionCall)ast).getTarget() instanceof VarRef);
		assertEquals("self", ((VarRef)((ErrorCollectionCall)ast).getTarget()).getVariableName());
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletStringLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("'str");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletRealLitTest() {
		// TODO remove incomplete navigationSegment on integer ?
		IQueryBuilderEngine.AstResult build = engine.build("3.");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof ErrorFeatureAccessOrCall);
		assertEquals(1, build.getErrors().size());
		assertEquals(ast, build.getErrors().get(0));
	}

	@Test
	public void incompletSetLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletSetLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
	}

	@Test
	public void incompletSetLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(1) instanceof ErrorExpression);
		assertEquals(build.getErrors().get(0), ((SetInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletSeqLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletSeqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
	}

	@Test
	public void incompletSeqLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("[ self, ");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(1) instanceof ErrorExpression);
		assertEquals(build.getErrors().get(0), ((SequenceInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletExplicitSeqLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(0, ((SequenceInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletExplicitSeqLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(1, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
	}

	@Test
	public void incompletExplicitSeqLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("Sequence{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(true, ast instanceof SequenceInExtensionLiteral);
		assertEquals(2, ((SequenceInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
		assertEquals(true, ((SequenceInExtensionLiteral)ast).getValues().get(1) instanceof ErrorExpression);
		assertEquals(build.getErrors().get(0), ((SequenceInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletExplicitSetLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(0, ((SetInExtensionLiteral)ast).getValues().size());
	}

	@Test
	public void incompletExplicitSetLitWithValueTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self");
		Expression ast = build.getAst();

		assertEquals(0, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(1, ((SetInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
	}

	@Test
	public void incompletExplicitSetLitWithValueAndCommaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("OrderedSet{ self,");
		Expression ast = build.getAst();

		assertEquals(1, build.getErrors().size());
		assertEquals(true, ast instanceof SetInExtensionLiteral);
		assertEquals(2, ((SetInExtensionLiteral)ast).getValues().size());
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(0) instanceof VarRef);
		assertEquals(true, ((SetInExtensionLiteral)ast).getValues().get(1) instanceof ErrorExpression);
		assertEquals(build.getErrors().get(0), ((SetInExtensionLiteral)ast).getValues().get(1));
	}

	@Test
	public void incompletEnumLitTest() {
		IQueryBuilderEngine.AstResult build = engine.build("toto::");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof ErrorTypeLiteral);
		assertEquals(1, ((ErrorTypeLiteral)ast).getSegments().size());
		assertEquals("toto", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletEnumLitWithPackageTest() {
		IQueryBuilderEngine.AstResult build = engine.build("toto::tata::");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof ErrorTypeLiteral);
		assertEquals(2, ((ErrorTypeLiteral)ast).getSegments().size());
		assertEquals("toto", ((ErrorTypeLiteral)ast).getSegments().get(0));
		assertEquals("tata", ((ErrorTypeLiteral)ast).getSegments().get(1));
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		final Lambda lambda = (Lambda)((Call)ast).getArguments().get(1);
		assertEquals(true, lambda.getExpression() instanceof ErrorExpression);
		assertEquals(1, lambda.getParameters().size());
		assertEquals(1, lambda.getParameters().size());
		assertEquals(true, lambda.getParameters().get(0) instanceof ErrorVariableDeclaration);
		assertEquals(2, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select(e | true");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof BooleanLiteral);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableNameTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a |");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableNameAndExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a | true");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof BooleanLiteral);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithMissingTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a :");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(true, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType() instanceof ErrorTypeLiteral);
		assertEquals(0, ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getSegments().size());
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof ErrorExpression);
		assertEquals(2, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithErrorTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : ecore::");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(true, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType() instanceof ErrorTypeLiteral);
		assertEquals(1, ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getSegments().size());
		assertEquals("ecore", ((ErrorTypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).getSegments().get(0));
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof ErrorExpression);
		assertEquals(2, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationNoPipeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : EClass");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals("a", ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getName());
		assertEquals(true, ((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1)).getParameters()
				.get(0)).getType() instanceof TypeLiteral);
		assertEquals(true, ((TypeLiteral)((VariableDeclaration)((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).getType()).getValue() == EcorePackage.Literals.ECLASS);
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationWithPipeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : EClass |");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletIterationCallWithVariableDeclarationAndExpressionTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self->select( a : EClass | true");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("select", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof Lambda);
		assertEquals(true,
				((Lambda)((Call)ast).getArguments().get(1)).getExpression() instanceof BooleanLiteral);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletServiceCallNoParameterTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service(");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("service", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(1, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletServiceCallOneParameterTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service( true");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals("service", ((Call)ast).getServiceName());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof BooleanLiteral);
		assertEquals(0, build.getErrors().size());
	}

	@Test
	public void incompletServiceCallOneParameterAndComaTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.service( true,");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("service", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(3, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof BooleanLiteral);
		assertEquals(true, ((Call)ast).getArguments().get(2) instanceof ErrorExpression);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletSeqTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(Sequence(");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof CollectionTypeLiteral);
		assertEquals(
				true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType() instanceof TypeLiteral);
		assertEquals(true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == List.class);
		assertEquals(
				true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType() instanceof ErrorTypeLiteral);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletSeqTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(Sequence(String");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof CollectionTypeLiteral);
		assertEquals(
				true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType() instanceof TypeLiteral);
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

		assertEquals(true, ast instanceof Call);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof CollectionTypeLiteral);
		assertEquals(
				true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType() instanceof TypeLiteral);
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertEquals(
				true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType() instanceof ErrorTypeLiteral);
		assertEquals(1, build.getErrors().size());
	}

	@Test
	public void incompletSetTypeWithTypeTest() {
		IQueryBuilderEngine.AstResult build = engine.build("self.filter(OrderedSet(String");
		Expression ast = build.getAst();

		assertEquals(true, ast instanceof Call);
		assertEquals("filter", ((Call)ast).getServiceName());
		// TODO assertEquals(CallType.CALLSERVICE, ((Call) ast).getType());
		assertEquals(2, ((Call)ast).getArguments().size());
		assertEquals(true, ((Call)ast).getArguments().get(0) instanceof VarRef);
		assertEquals(true, ((Call)ast).getArguments().get(1) instanceof CollectionTypeLiteral);
		assertEquals(true, ((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getValue() == Set.class);
		assertEquals(
				true,
				((CollectionTypeLiteral)((Call)ast).getArguments().get(1)).getElementType() instanceof TypeLiteral);
		assertEquals(true, ((TypeLiteral)((CollectionTypeLiteral)((Call)ast).getArguments().get(1))
				.getElementType()).getValue() == String.class);
		assertEquals(0, build.getErrors().size());
	}

}
