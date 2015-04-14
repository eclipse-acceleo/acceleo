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
package org.eclipse.acceleo.query.runtime.test;

import com.google.common.collect.Maps;

import java.util.EmptyStackException;
import java.util.Map;

import org.eclipse.acceleo.query.runtime.impl.ScopedEnvironment;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ScopedEnvironmentTests {

	@Test
	public void createScopeTest() {
		ScopedEnvironment env = new ScopedEnvironment();
		assertNull(env.getVariableValue("var"));
	}

	@Test
	public void pushScopeTest() {
		ScopedEnvironment env = new ScopedEnvironment();
		Map<String, Object> scope = Maps.newHashMap();
		scope.put("x", new Integer(1));
		env.pushScope(scope);
		assertEquals(new Integer(1), env.getVariableValue("x"));
	}

	@Test
	public void getVariableTest() {
		ScopedEnvironment env = new ScopedEnvironment();
		Map<String, Object> scope = Maps.newHashMap();
		scope.put("x", new Integer(1));
		scope.put("y", new Integer(2));
		env.pushScope(scope);
		assertEquals(new Integer(1), env.getVariableValue("x"));
		assertEquals(new Integer(2), env.getVariableValue("y"));
		assertEquals(null, env.getVariableValue("z"));
	}

	@Test
	public void overrideScopeTest() {
		ScopedEnvironment env = new ScopedEnvironment();
		Map<String, Object> scope = Maps.newHashMap();
		scope.put("x", new Integer(1));
		env.pushScope(scope);
		scope = Maps.newHashMap();
		scope.put("x", new Integer(2));
		env.pushScope(scope);
		assertEquals(new Integer(2), env.getVariableValue("x"));
	}

	@Test
	public void popScopeTest() {
		ScopedEnvironment env = new ScopedEnvironment();
		Map<String, Object> scope = Maps.newHashMap();
		scope.put("x", new Integer(1));
		env.pushScope(scope);
		scope = Maps.newHashMap();
		scope.put("x", new Integer(2));
		env.pushScope(scope);
		assertEquals(new Integer(2), env.getVariableValue("x"));
		Map<String, Object> popped = env.popScope();
		assertEquals(new Integer(1), env.getVariableValue("x"));
		assertEquals(scope, popped);
	}

	@Test(expected = EmptyStackException.class)
	public void emptyEnvPopTest() {
		ScopedEnvironment env = new ScopedEnvironment();
		env.popScope();
	}

	@Test(expected = EmptyStackException.class)
	public void emptyEnvDefinitionTest() {
		ScopedEnvironment env = new ScopedEnvironment();
		env.defineVariable("x", 4);
	}

	@Test
	public void defineVariableTest() {
		ScopedEnvironment env = new ScopedEnvironment();
		Map<String, Object> scope = Maps.newHashMap();
		scope.put("x", new Integer(1));
		env.pushScope(scope);
		assertEquals(new Integer(1), env.defineVariable("x", new Integer(2)));
		assertEquals(new Integer(2), env.getVariableValue("x"));
	}

	@Test
	public void testcopy() {
		ScopedEnvironment env = new ScopedEnvironment();
		Map<String, Object> scope = Maps.newHashMap();
		scope.put("x", new Integer(1));
		env.pushScope(scope);
		scope = Maps.newHashMap();
		scope.put("x", new Integer(2));
		env.pushScope(scope);
		ScopedEnvironment copy = env.copy();
		assertEquals(new Integer(2), copy.getVariableValue("x"));
		Map<String, Object> popped = copy.popScope();
		assertEquals(new Integer(1), copy.getVariableValue("x"));
		assertTrue(scope != popped);
	}
}
