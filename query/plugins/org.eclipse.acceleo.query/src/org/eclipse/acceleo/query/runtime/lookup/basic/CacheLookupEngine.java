/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.lookup.basic;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Lookup engine are used to retrieve services from a name and a set of arguments. This implementation cache
 * service lookup result.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CacheLookupEngine extends BasicLookupEngine {
	/**
	 * No service marker.
	 */
	private static final IService<?> NO_SERVICE = new JavaMethodService(null, null);

	/**
	 * A node of the cache forest.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class Node {

		/**
		 * Children (parameter {@link IType} to next {@link Node}).
		 */
		private final Map<IType, Node> children = new HashMap<IType, Node>();

		/**
		 * The {@link IService} if any, <code>null</code> otherwise.
		 */
		private IService<?> service;
	}

	/**
	 * The cache forest.
	 */
	private final Map<String, Node> cache = new HashMap<String, Node>();

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 */
	public CacheLookupEngine(IReadOnlyQueryEnvironment queryEnvironment) {
		super(queryEnvironment);
	}

	@Override
	public ServiceRegistrationResult registerService(IService<?> service) {
		cache.clear();
		return super.registerService(service);
	}

	@Override
	public IService<?> lookup(String name, IType[] argumentTypes) {
		final IService<?> result;

		final Node cachedNode = getNodeFromCache(name, argumentTypes);
		if (cachedNode != null) {
			if (cachedNode.service == NO_SERVICE) {
				result = null;
			} else if (cachedNode.service == null) {
				result = super.lookup(name, argumentTypes);
				cachedNode.service = result;
			} else {
				result = cachedNode.service;
			}
		} else {
			result = super.lookup(name, argumentTypes);
			cacheService(name, argumentTypes, result);
		}

		return result;
	}

	/**
	 * Caches the given {@link IService} for the given argument types.
	 * 
	 * @param name
	 *            the {@link IService} name
	 * @param argumentTypes
	 *            argument types
	 * @param service
	 *            the {@link IService} to cache
	 */
	private void cacheService(String name, IType[] argumentTypes, IService<?> service) {
		Node currentNode = cache.get(name);
		if (currentNode == null) {
			currentNode = new Node();
			cache.put(name, currentNode);
		}
		for (IType type : argumentTypes) {
			Node nextNode = currentNode.children.get(type);
			if (nextNode == null) {
				nextNode = new Node();
				currentNode.children.put(type, nextNode);
			}
			currentNode = nextNode;
		}
		if (service == null) {
			currentNode.service = NO_SERVICE;
		} else {
			currentNode.service = service;
		}
	}

	/**
	 * Gets the {@link Node} from the cache corresponding to the given name and parameter types.
	 * 
	 * @param name
	 *            the service name
	 * @param argumentTypes
	 *            the argument types
	 * @return the {@link Node} from the cache corresponding to the given name and parameter types if any,
	 *         <code>null</code> otherwise
	 */
	private Node getNodeFromCache(String name, IType[] argumentTypes) {
		final Node result;

		Node currentNode = cache.get(name);
		if (currentNode != null) {
			for (IType type : argumentTypes) {
				currentNode = currentNode.children.get(type);
				if (currentNode == null) {
					break;
				}
			}
			result = currentNode;
		} else {
			result = null;
		}

		return result;
	}

	@Override
	public IService<?> removeService(IService<?> service) {
		cache.clear();
		return super.removeService(service);
	}

}
