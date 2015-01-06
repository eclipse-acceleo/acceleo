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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.runtime.IService;

//CHECKSTYLE:OFF
public class InnerLookupNode extends LookupNode {

	private final List<LookupEdge> children;

	private IService method;

	public InnerLookupNode() {
		this.children = new ArrayList<LookupEdge>();
	}

	@Override
	public IService lookup(Class<?>[] argumentTypes, int step) {
		if (step == argumentTypes.length) {
			return method;
		} else {// look for a matching child.
			for (LookupEdge edge : children) {
				if (edge.getLabel().isAssignableFrom(argumentTypes[step])) {
					return edge.getChild().lookup(argumentTypes, step + 1);
				}
			}
			return null;
		}
	}

	private LookupEdge createNewEdge(IService method, int step) {
		InnerLookupNode newNode = new InnerLookupNode();
		newNode.addMethod(method, step + 1);
		return new LookupEdge(method.getServiceMethod().getParameterTypes()[step], newNode);
	}

	@Override
	void addMethod(IService method, int step) {
		Class<?>[] parameterTypes = method.getServiceMethod().getParameterTypes();
		if (step == parameterTypes.length) {
			this.method = method;
		} else {
			// look for the class slot : children are sorted by order of
			// increasing types: a sub-class will appear before it's super class
			// in the list.
			int i = 0;
			int size = children.size();
			Class<?> stepParameterType = parameterTypes[step];
			while (i < size && !children.get(i).getLabel().isAssignableFrom(stepParameterType)) {
				i++;
			}
			if (i == size) {
				children.add(createNewEdge(method, step));
			} else if (children.get(i).getLabel() == stepParameterType) {
				children.get(i).getChild().addMethod(method, step + 1);
			} else {
				children.add(i, createNewEdge(method, step));
			}
		}

	}
}
