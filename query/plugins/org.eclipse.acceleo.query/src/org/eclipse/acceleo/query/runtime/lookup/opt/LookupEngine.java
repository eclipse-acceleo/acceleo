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
package org.eclipse.acceleo.query.runtime.lookup.opt;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;

//CHECKSTYLE:OFF
public class LookupEngine {
	/**
	 * This map associates strings to lookup nodes for the multimethods. Keys are made up using the following
	 * schema : <method name>_<parameter number>.
	 */
	private final Map<String, LookupNode> multiMethods = new HashMap<String, LookupNode>();

	public void addService(Method method, Object instance) {
		String key = new StringBuilder().append(method.getName()).append('_').append(
				method.getParameterTypes().length).toString();
		LookupNode node = multiMethods.get(key);
		if (node == null) {
			if (method.getParameterTypes().length == 0) {
				node = new LeafLookupNode();
			} else {
				node = new InnerLookupNode();
			}
			multiMethods.put(key, node);
		}
		node.addMethod(new Service(method, instance), 0);
	}

	public IService lookup(String name, Class<?>[] argumentTypes) {
		String key = new StringBuilder().append(name).append('_').append(argumentTypes.length).toString();
		LookupNode node = multiMethods.get(key);
		if (node != null) {
			return node.lookup(argumentTypes, 0);
		} else {
			return null;
		}
	}

	public void addServices(Class<?> serviceClass) throws InvalidAcceleoPackageException {
		// TODO Auto-generated method stub

	}

}
