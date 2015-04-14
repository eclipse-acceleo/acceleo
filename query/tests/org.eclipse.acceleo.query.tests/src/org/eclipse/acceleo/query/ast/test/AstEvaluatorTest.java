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

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.CrossReferencerToAQL;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.LambdaValue;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.emf.common.notify.Notifier;
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
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		evaluator = new AstEvaluator(new EvaluationServices(environment, true));
	}

	@Test
	public void testIntLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		assertEquals(1, evaluator.eval(varDefinitions, integerLiteral(1)));
	}

	@Test
	public void testRealLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		assertEquals(1.0, evaluator.eval(varDefinitions, realLiteral(1.0)));
	}

	@Test
	public void testBoolLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		assertTrue((Boolean)evaluator.eval(varDefinitions, booleanLiteral(true)));
		assertFalse((Boolean)evaluator.eval(varDefinitions, booleanLiteral(false)));
	}

	@Test
	public void testStringLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		assertEquals("john doe", evaluator.eval(varDefinitions, stringLiteral("john doe")));
	}

	@Test
	public void testTypeLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		assertEquals(EcorePackage.Literals.ECLASS, evaluator.eval(varDefinitions,
				typeLiteral(EcorePackage.Literals.ECLASS)));
		assertEquals(Integer.class, evaluator.eval(varDefinitions, typeLiteral(Integer.class)));
	}

	@Test
	public void testFeatureAccess() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		assertEquals("EClass", evaluator.eval(varDefinitions, featureAccess(varRef("self"), "name")));
		Object result = evaluator.eval(varDefinitions, featureAccess(varRef("self"), "eAllSuperTypes"));
		assertTrue(result instanceof List);
		assertEquals(3, ((List<?>)result).size());
		assertTrue(((List<?>)result).contains(EcorePackage.Literals.EMODEL_ELEMENT));
		assertTrue(((List<?>)result).contains(EcorePackage.Literals.ENAMED_ELEMENT));
		assertTrue(((List<?>)result).contains(EcorePackage.Literals.ECLASSIFIER));
	}

	@Test
	public void testVarRef() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		assertEquals(EcorePackage.Literals.ECLASS, evaluator.eval(varDefinitions, varRef("self")));
	}

	@Test
	public void testCall() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		Object result = evaluator.eval(varDefinitions, callService(CallType.COLLECTIONCALL, "size",
				featureAccess(varRef("self"), "eAllSuperTypes")));
		assertTrue(result instanceof Integer);
		assertEquals(3, result);
	}

	@Test
	public void testLambda() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		varDefinitions.put("x", new Integer(1));
		Lambda lambda = lambda(varRef("x"));
		Object value = evaluator.eval(varDefinitions, lambda);
		assertTrue(value instanceof LambdaValue);
		assertEquals(new Integer(1), ((LambdaValue)value).eval(new Object[0]));
	}

	@Test
	public void testNullLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();
		assertEquals(null, evaluator.eval(varDefinitions, nullLiteral()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetInExtensionLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();

		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		final List<Expression> values = new ArrayList<Expression>();
		values.add(varRef("self"));
		values.add(varRef("self"));
		values.add(booleanLiteral(true));
		values.add(booleanLiteral(false));

		final Object result = evaluator.eval(varDefinitions, setInExtension(values));
		assertEquals(true, result instanceof Set);
		assertEquals(3, ((Set<Object>)result).size());
		Iterator<Object> it = ((Set<Object>)result).iterator();
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(Boolean.TRUE, it.next());
		assertEquals(Boolean.FALSE, it.next());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSequenceInExtensionLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();

		varDefinitions.put("self", EcorePackage.Literals.ECLASS);
		final List<Expression> values = new ArrayList<Expression>();
		values.add(varRef("self"));
		values.add(varRef("self"));
		values.add(booleanLiteral(true));
		values.add(booleanLiteral(false));

		final Object result = evaluator.eval(varDefinitions, sequenceInExtension(values));
		assertEquals(true, result instanceof List);
		assertEquals(4, ((List<Object>)result).size());
		Iterator<Object> it = ((List<Object>)result).iterator();
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(Boolean.TRUE, it.next());
		assertEquals(Boolean.FALSE, it.next());
	}

}
