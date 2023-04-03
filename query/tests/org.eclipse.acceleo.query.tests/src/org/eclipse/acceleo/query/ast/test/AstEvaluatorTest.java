/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.CrossReferencerToAQL;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.LambdaValue;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AstEvaluatorTest extends AstBuilder {

	private AstEvaluator evaluator;

	/**
	 * This will create the cross referencer that's to be used by the "eInverse" library. It will attempt to
	 * create the cross referencer on the target's resourceSet. If it is null, we'll then attempt to create
	 * the cross referencer on the target's resource. When the resource too is null, we'll create the cross
	 * referencer on the target's root container.
	 * 
	 * @param target
	 *            Target of the cross referencing.
	 */
	private CrossReferenceProvider createEInverseCrossreferencer(EObject target) {
		Resource res = null;
		ResourceSet rs = null;
		CrossReferencer crossReferencer;
		if (target.eResource() != null) {
			res = target.eResource();
		}
		if (res != null && res.getResourceSet() != null) {
			rs = res.getResourceSet();
		}

		if (rs != null) {
			// Manually add the ecore.ecore resource in the list of cross
			// referenced notifiers
			final Resource ecoreResource = EcorePackage.eINSTANCE.getEClass().eResource();
			final Collection<Notifier> notifiers = new ArrayList<Notifier>();
			for (Resource crossReferenceResource : rs.getResources()) {
				if (!"emtl".equals(crossReferenceResource.getURI().fileExtension())) {
					notifiers.add(crossReferenceResource);
				}
			}
			notifiers.add(ecoreResource);

			crossReferencer = new CrossReferencer(notifiers) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		} else if (res != null) {
			crossReferencer = new CrossReferencer(res) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		} else {
			EObject targetObject = EcoreUtil.getRootContainer(target);
			crossReferencer = new CrossReferencer(targetObject) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		}
		return new CrossReferencerToAQL(crossReferencer);
	}

	@Before
	public void setup() {
		IQueryEnvironment environment = Query.newEnvironmentWithDefaultServices(createEInverseCrossreferencer(
				EcorePackage.eINSTANCE));
		evaluator = new AstEvaluator(new EvaluationServices(environment));
	}

	@Test
	public void testIntLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals(Integer.valueOf(1), evaluator.eval(varDefinitions, integerLiteral(1)));
	}

	@Test
	public void testRealLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals(Double.valueOf(1d), evaluator.eval(varDefinitions, realLiteral(1.0)));
	}

	@Test
	public void testBoolLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals(Boolean.TRUE, evaluator.eval(varDefinitions, booleanLiteral(true)));
		assertOKResultEquals(Boolean.FALSE, evaluator.eval(varDefinitions, booleanLiteral(false)));
	}

	@Test
	public void testStringLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals("john doe", evaluator.eval(varDefinitions, stringLiteral("john doe")));
	}

	@Test
	public void testTypeLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals(EcorePackage.Literals.ECLASS, evaluator.eval(varDefinitions,
				eClassifierTypeLiteral(EcorePackage.eNAME, EcorePackage.Literals.ECLASS.getName())));
		assertOKResultEquals(Integer.class, evaluator.eval(varDefinitions, typeLiteral(Integer.class)));
	}

	@Test
	public void testFeatureAccess() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		assertOKResultEquals("EClass", evaluator.eval(varDefinitions, callService(
				AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, varRef("self"), stringLiteral("name"))));
		EvaluationResult result = evaluator.eval(varDefinitions, callService(
				AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, varRef("self"), stringLiteral(
						("eAllSuperTypes"))));
		assertTrue(result.getResult() instanceof List);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		List<?> listResult = (List<?>)result.getResult();
		assertEquals(3, listResult.size());
		assertTrue(listResult.contains(EcorePackage.Literals.EMODEL_ELEMENT));
		assertTrue(listResult.contains(EcorePackage.Literals.ENAMED_ELEMENT));
		assertTrue(listResult.contains(EcorePackage.Literals.ECLASSIFIER));
	}

	@Test
	public void testVarRef() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		assertOKResultEquals(EcorePackage.Literals.ECLASS, evaluator.eval(varDefinitions, varRef("self")));
	}

	@Test
	public void testCall() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		final Call callService = callService("size", callService(
				AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, varRef("self"), stringLiteral(
						"eAllSuperTypes")));
		callService.setType(CallType.COLLECTIONCALL);
		EvaluationResult result = evaluator.eval(varDefinitions, callService);
		assertOKResultEquals(Integer.valueOf(3), result);
	}

	@Test
	public void testLambda() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("x", Integer.valueOf(1));
		Lambda lambda = lambda(varRef("x"));
		EvaluationResult value = evaluator.eval(varDefinitions, lambda);
		assertTrue(value.getResult() instanceof LambdaValue);
		assertEquals(Diagnostic.OK, value.getDiagnostic().getSeverity());
		assertTrue(value.getDiagnostic().getChildren().isEmpty());
		assertEquals(Integer.valueOf(1), ((LambdaValue)value.getResult()).eval(new Object[0]));
	}

	@Test
	public void testNullLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals(null, evaluator.eval(varDefinitions, nullLiteral()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetInExtensionLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();

		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		final List<Expression> values = new ArrayList<Expression>();
		values.add(varRef("self"));
		values.add(varRef("self"));
		values.add(booleanLiteral(true));
		values.add(booleanLiteral(false));

		final EvaluationResult result = evaluator.eval(varDefinitions, setInExtension(values));
		assertTrue(result.getResult() instanceof Set);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		Set<Object> setResult = (Set<Object>)result.getResult();
		assertEquals(3, setResult.size());
		Iterator<Object> it = setResult.iterator();
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(Boolean.TRUE, it.next());
		assertEquals(Boolean.FALSE, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSequenceInExtensionLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();

		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		final List<Expression> values = new ArrayList<Expression>();
		values.add(varRef("self"));
		values.add(varRef("self"));
		values.add(booleanLiteral(true));
		values.add(booleanLiteral(false));

		final EvaluationResult result = evaluator.eval(varDefinitions, sequenceInExtension(values));
		assertTrue(result.getResult() instanceof List);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		List<Object> listResult = (List<Object>)result.getResult();
		assertEquals(4, listResult.size());
		Iterator<Object> it = listResult.iterator();
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(Boolean.TRUE, it.next());
		assertEquals(Boolean.FALSE, it.next());
		assertFalse(it.hasNext());
	}

	/**
	 * Test that the true branch is properly evaluated when the predicate evaluates to <code>true</code>
	 */
	@Test
	public void testConditionnalTrueBranch() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		Conditional conditional = conditional(booleanLiteral(true), stringLiteral("trueBranch"),
				stringLiteral("falseBranch"));
		assertOKResultEquals("trueBranch", evaluator.eval(varDefinitions, conditional));
	}

	/**
	 * Test that the false branch is properly evaluated when the predicate evaluates to <code>false</code>
	 */
	@Test
	public void testConditionnalFalseBranch() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		Conditional conditional = conditional(booleanLiteral(false), stringLiteral("trueBranch"),
				stringLiteral("falseBranch"));
		assertOKResultEquals("falseBranch", evaluator.eval(varDefinitions, conditional));
	}

	/**
	 * Test that the result is <code>Nothing</code> when the predicate isn't a boolean
	 */
	@Test
	public void testConditionnalBadPredicate() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		Conditional conditional = conditional(stringLiteral("Hey, what's this?!"), stringLiteral(
				"trueBranch"), stringLiteral("falseBranch"));
		final EvaluationResult result = evaluator.eval(varDefinitions, conditional);
		assertTrue(result.getResult() instanceof Nothing);
		assertEquals(Diagnostic.WARNING, result.getDiagnostic().getSeverity());
		assertEquals(1, result.getDiagnostic().getChildren().size());
		String message = result.getDiagnostic().getChildren().get(0).getMessage();
		assertTrue(message.contains("Conditional"));
		assertTrue(message.contains("boolean"));
	}

	@Test
	public void testLetBasic() {
		Let let = let(callService("concat", varRef("x"), varRef("y")), binding("x", null, stringLiteral(
				"prefix")), binding("y", null, stringLiteral("suffix")));
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals("prefixsuffix", evaluator.eval(varDefinitions, let));
	}

	@Test
	public void testLetArenotRecursive() {
		Let let = let(callService("concat", varRef("x"), varRef("y")), binding("x", null, stringLiteral(
				"prefix")), binding("y", null, callService("concat", varRef("x"), stringLiteral("end"))));
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("x", "firstx");
		assertOKResultEquals("prefixfirstxend", evaluator.eval(varDefinitions, let));
	}

	@Test
	public void testLetWithNothingBound() {
		Let let = let(callService("concat", varRef("x"), varRef("y")), binding("x", null, varRef("prefix")),
				binding("y", null, stringLiteral("suffix")));
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		final EvaluationResult result = evaluator.eval(varDefinitions, let);
		assertTrue(result.getResult() instanceof Nothing);
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getSeverity());
		assertEquals(2, result.getDiagnostic().getChildren().size());
		String message1 = result.getDiagnostic().getChildren().get(0).getMessage();
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getChildren().get(0).getSeverity());
		assertTrue(message1.contains("Couldn't find the 'prefix' variable"));
		assertEquals(Diagnostic.WARNING, result.getDiagnostic().getChildren().get(1).getSeverity());
		String message2 = result.getDiagnostic().getChildren().get(1).getMessage();
		assertTrue(message2.contains("Couldn't find the 'concat"));
	}

	@Test
	public void testLetWithNothingBody() {
		Let let = let(callService("concat", varRef("novar"), varRef("y")), binding("x", null, stringLiteral(
				"prefix")), binding("y", null, stringLiteral("suffix")));
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		final EvaluationResult result = evaluator.eval(varDefinitions, let);
		assertTrue(result.getResult() instanceof Nothing);
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getSeverity());
		assertEquals(2, result.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getChildren().get(0).getSeverity());
		String message1 = result.getDiagnostic().getChildren().get(0).getMessage();
		assertTrue(message1.contains("Couldn't find the 'novar' variable"));
		assertEquals(Diagnostic.WARNING, result.getDiagnostic().getChildren().get(1).getSeverity());
		String message2 = result.getDiagnostic().getChildren().get(1).getMessage();
		assertTrue(message2.contains("Couldn't find the 'concat"));
	}

	@Test
	public void testeClassifierTypeLiteral() {

		final List<TypeLiteral> types = new ArrayList<TypeLiteral>();
		types.add((TypeLiteral)eClassifierTypeLiteral(EcorePackage.eNAME, EcorePackage.eINSTANCE.getEClass()
				.getName()));
		types.add((TypeLiteral)eClassifierTypeLiteral(EcorePackage.eNAME, EcorePackage.eINSTANCE.getEPackage()
				.getName()));
		types.add((TypeLiteral)eClassifierTypeLiteral(EcorePackage.eNAME, EcorePackage.eINSTANCE
				.getEAttribute().getName()));
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		final EvaluationResult result = evaluator.eval(varDefinitions, typeSetLiteral(types));
		assertTrue(result.getResult() instanceof Set);
		assertEquals(3, ((Set<?>)result.getResult()).size());
		final Iterator<?> it = ((Set<?>)result.getResult()).iterator();
		assertEquals(EcorePackage.eINSTANCE.getEClass(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEAttribute(), it.next());
	}

	@Test
	public void testLetOverwriteVariable() {
		Let let = let(varRef("self"), binding("self", null, stringLiteral("selfOverritten")));
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("self", "self");
		assertOKResultEquals("selfOverritten", evaluator.eval(varDefinitions, let));
	}

	@Test
	public void testLetOverwriteBinding() {
		Let let = let(varRef("a"), binding("a", null, stringLiteral("a")), binding("a", null, stringLiteral(
				"aOverritten")));
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("self", "self");
		assertOKResultEquals("aOverritten", evaluator.eval(varDefinitions, let));
	}

	private void assertOKResultEquals(Object expected, EvaluationResult result) {
		assertEquals(expected, result.getResult());
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
	}

}
