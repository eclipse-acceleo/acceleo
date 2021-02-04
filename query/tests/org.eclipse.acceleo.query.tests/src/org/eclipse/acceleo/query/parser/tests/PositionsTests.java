/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.tests;

import org.eclipse.acceleo.query.ast.Or;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class PositionsTests {

	private QueryBuilderEngine engine;

	private IQueryEnvironment queryEnvironment;

	public PositionsTests() {
		queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		engine = new QueryBuilderEngine(queryEnvironment);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getNodeAtPositionOutOfRange() {
		AstResult build = engine.build("a or b");
		build.getAstNode(10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getNodeAtLineColumnOutOfRange() {
		AstResult build = engine.build("a or b");
		build.getAstNode(1, 0);
	}

	@Test
	public void getNodeAtPosition() {
		AstResult build = engine.build("a or b");

		EObject node = build.getAstNode(0);

		assertTrue(node instanceof VarRef);
		assertEquals("a", ((VarRef)node).getVariableName());

		node = build.getAstNode(3);

		assertTrue(node instanceof Or);

		node = build.getAstNode(6);

		assertTrue(node instanceof VarRef);
		assertEquals("b", ((VarRef)node).getVariableName());
	}

}
