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

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.impl.BasicFilter;
import org.eclipse.acceleo.query.runtime.impl.QueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EOperationCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.ServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableDeclarationCompletionProposal;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CompletionTest {

	QueryCompletionEngine engine;

	IQueryEnvironment queryEnvironment;

	/**
	 * Variable types.
	 */
	Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		queryEnvironment = new QueryEnvironment(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		engine = new QueryCompletionEngine(queryEnvironment);

		variableTypes.clear();
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("self", selfTypes);
		final Set<IType> stuffTypes = new LinkedHashSet<IType>();
		stuffTypes.add(new EClassifierType(EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("stuff", stuffTypes);
	}

	@Test
	public void nullTest() {
		final ICompletionResult completionResult = engine.getCompletion(null, 0, variableTypes);
		assertEquals(129, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals(null, completionResult.getPrefix());
		assertEquals(null, completionResult.getRemaining());
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeOffsetTest() {
		final ICompletionResult completionResult = engine.getCompletion("aa", -1, variableTypes);
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void outOffsetTest() {
		final ICompletionResult completionResult = engine.getCompletion("aa", 10, variableTypes);
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
	}

	@Test
	public void emptyTest() {
		final ICompletionResult completionResult = engine.getCompletion("", 0, variableTypes);
		assertEquals(129, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void navigationSegmentEmptyTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.", 5, variableTypes);

		assertEquals(60, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void navigationSegmentPrefixTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.na", 7, variableTypes);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("na", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void navigationSegmentPrefixRemainingTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.nam", 7, variableTypes);

		assertEquals("na", completionResult.getPrefix());
		assertEquals("m", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfTest() {
		final ICompletionResult completionResult = engine.getCompletion("self ", 5, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(3, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void someIntTest() {
		final Set<IType> someIntTypes = new LinkedHashSet<IType>();
		someIntTypes.add(new EClassifierType(EcorePackage.eINSTANCE.getEInt()));
		variableTypes.put("someInt", someIntTypes);
		final ICompletionResult completionResult = engine.getCompletion("someInt ", 8, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(10, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void alfMultTest() {
		final Set<IType> someIntTypes = new LinkedHashSet<IType>();
		someIntTypes.add(new EClassifierType(EcorePackage.eINSTANCE.getEInt()));
		variableTypes.put("someInt", someIntTypes);
		final ICompletionResult completionResult = engine.getCompletion("someInt * ", 10, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(130, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void notTest() {
		final ICompletionResult completionResult = engine.getCompletion("not ", 4, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(129, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfDotTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.", 5, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(60, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfArrowTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->", 6, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(40, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void errorTypeLiteralOneSegmentTest() {
		final ICompletionResult completionResult = engine.getCompletion("ecore::", 7, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void errorTypeLiteralTwoSegmentsTest() {
		final ICompletionResult completionResult = engine.getCompletion("ecore::EClass::", 15, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfSelectTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->select(", 13, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		final ICompletionProposal proposal = completionResult.getProposals(new BasicFilter(completionResult))
				.get(0);
		assertEquals(true, proposal instanceof VariableDeclarationCompletionProposal);
		assertEquals("anEClass : ecore::EClass | ", proposal.getProposal());
		assertEquals("anEClass : ecore::EClass | ".length(), proposal.getCursorOffset());
	}

	@Test
	public void selfSelectMissingVariableTypeTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->select( a : ", 17,
				variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(117, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfServiceManyArgumentsTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->service(self, ", 20,
				variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(129, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	public void assertNoVariableCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof VariableCompletionProposal);
		}
	}

	public void assertNoFeatureCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof EFeatureCompletionProposal);
		}
	}

	public void assertNoEOperationCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof EOperationCompletionProposal);
		}
	}

	public void assertNoVariableDeclarationCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof VariableDeclarationCompletionProposal);
		}
	}

	public void assertNoServiceCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof ServiceCompletionProposal);
		}
	}

	public void assertOnlyVariableCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(true, prop instanceof VariableCompletionProposal);
		}
	}

	public void assertApplyOn(ICompletionResult completionResult, Object... types) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			for (Object type : types) {
				if (prop instanceof EFeatureCompletionProposal) {
					if (type instanceof EClass) {
						assertEquals(type + " is not compatible with "
								+ ((EFeatureCompletionProposal)prop).getObject().getEContainingClass(), true,
								((EFeatureCompletionProposal)prop).getObject().getEContainingClass() == type
										|| ((EClass)type).getEAllSuperTypes().contains(
												((EFeatureCompletionProposal)prop).getObject()
														.getEContainingClass()));
					} else {
						fail("the receiver type must be an EClass for FeatureCompletionProposal");
					}
				} else if (prop instanceof ServiceCompletionProposal) {
					final Class<?> cls;
					if (type instanceof EClass) {
						cls = ((EClass)type).getInstanceClass();
					} else {
						cls = (Class<?>)type;
					}
					assertEquals(true, ((ServiceCompletionProposal)prop).getObject().getServiceMethod()
							.getParameterTypes()[0].isAssignableFrom(cls));
				}
			}
		}
	}

}
