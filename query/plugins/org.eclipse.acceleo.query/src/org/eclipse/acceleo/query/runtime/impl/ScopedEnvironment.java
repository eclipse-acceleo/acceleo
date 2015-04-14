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
package org.eclipse.acceleo.query.runtime.impl;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;

/**
 * /** The {@link ScopedEnvironment} provides a mean to define variables in a limited life-time scope.
 * Variables that are define outside the evaluation context and that are passed to the evaluation context
 * through the API have a scope that covers the entire evaluation but variables that are defined in iterators
 * have a scope that covers only the iterator. The same applies to variables defined in let operators : they
 * have a scope that covers only the expression they are defined in.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class ScopedEnvironment {
	/**
	 * Stack of maps that contain definition of variables that are lived over the same scope..
	 */
	private List<Map<String, Object>> scopes;

	/**
	 * Creates a new ScopedEnvironment instance.
	 */
	public ScopedEnvironment() {
		this.scopes = new ArrayList<Map<String, Object>>();
	}

	/**
	 * Push a new scope containing the provided definitions. The parameter isn't copied and is pushed as is.
	 * 
	 * @param scope
	 *            the definition to push.
	 */
	public void pushScope(Map<String, Object> scope) {
		this.scopes.add(scope);
	}

	/**
	 * Pop the last pushed scope.
	 * 
	 * @return the last pushed scope
	 * @throws EmptyStackException
	 *             if no scope is present in the environment.
	 */
	public Map<String, Object> popScope() {
		if (this.scopes.size() > 0) {
			return scopes.remove(scopes.size() - 1);
		} else {
			throw new EmptyStackException();
		}
	}

	/**
	 * Returns the definition of the specified variable if one is found and <ocde>null</code> otherwise.
	 * 
	 * @param variableName
	 *            the seeked variable's name.
	 * @return the value of the specified variable or <code>null</code>
	 */
	public Object getVariableValue(String variableName) {
		for (int i = scopes.size() - 1; i >= 0; i--) {
			Object result = scopes.get(i).get(variableName);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Define a new variable. Return the existing value if any.
	 * 
	 * @param variableName
	 *            the name of the new definition
	 * @param value
	 *            the value of the new variable
	 * @return the value of the old variable if any had the same name.
	 * @throws EmptyStackException
	 *             if no scope is present in the environment.
	 */
	public Object defineVariable(String variableName, Object value) {
		if (scopes.size() == 0) {
			throw new EmptyStackException();
		} else {
			return this.scopes.get(scopes.size() - 1).put(variableName, value);
		}
	}

	/**
	 * Return a copy of this {@link ScopedEnvironment}.
	 * 
	 * @return a copy of <code>this</code>.
	 */
	public ScopedEnvironment copy() {
		ScopedEnvironment copy = new ScopedEnvironment();
		for (Map<String, Object> scope : scopes) {
			copy.pushScope(Maps.newHashMap(scope));
		}
		return copy;
	}
}
