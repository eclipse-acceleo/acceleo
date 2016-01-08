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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * {@link EPackageProvider} instances are used to access a set of ecore packages and getting classifiers, and
 * enums literals. This implementation cache {@link EOperation} lookup result.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CacheEPackageProvider extends EPackageProvider {
	/**
	 * No {@link EOperation} marker.
	 */
	private static final EOperation NO_OPERATION = EcorePackage.eINSTANCE.getEcoreFactory()
			.createEOperation();

	/**
	 * Keeps track of the {@link EClassifier} and the {@link EParameter#isMany() isMany} of {@link EParameter}
	 * .
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class ParameterType {

		/**
		 * <code>0</code>single value, <code>1</code> many.
		 */
		private final int many;

		/**
		 * {@link EParameter#getEType() EParameter type}.
		 */
		private final EClassifier eClassifier;

		/**
		 * Constructor.
		 * 
		 * @param eClassifier
		 *            {@link EParameter#getEType() EParameter type}
		 * @param many
		 *            {@link EParameter#isMany() is Many}
		 */
		public ParameterType(EClassifier eClassifier, boolean many) {
			this.eClassifier = eClassifier;
			if (many) {
				this.many = 1;
			} else {
				this.many = 0;
			}
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return many ^ eClassifier.hashCode();
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			final boolean result;

			if (obj instanceof ParameterType) {
				result = ((ParameterType)obj).many == many && ((ParameterType)obj).eClassifier == eClassifier;
			} else {
				result = false;
			}

			return result;
		}

	}

	/**
	 * A node of the cache forest.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class Node {

		/**
		 * Children (parameter {@link ParameterType} to next {@link Node}).
		 */
		private final Map<ParameterType, Node> children = new HashMap<ParameterType, Node>();

		/**
		 * The {@link EOperation} if any, <code>null</code> otherwise.
		 */
		private EOperation operation;
	}

	/**
	 * The cache forest.
	 */
	private final Map<String, Node> cache = new HashMap<String, Node>();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.EPackageProvider#lookupEOperation(org.eclipse.emf.ecore.EClass,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	public EOperation lookupEOperation(EClass receiverEClass, String eOperationName,
			List<EParameter> parameters) {
		final EOperation result;

		final List<ParameterType> parameterTypes = getParameterTypes(receiverEClass, parameters);
		final Node cachedNode = getNodeFromCache(eOperationName, parameterTypes);
		if (cachedNode != null) {
			if (cachedNode.operation == NO_OPERATION) {
				result = null;
			} else if (cachedNode.operation == null) {
				result = super.lookupEOperation(receiverEClass, eOperationName, parameters);
				cachedNode.operation = result;
			} else {
				result = cachedNode.operation;
			}
		} else {
			result = super.lookupEOperation(receiverEClass, eOperationName, parameters);
			cacheOperation(eOperationName, parameterTypes, result);
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link ParameterType} from the given receiver {@link EClass} and the
	 * {@link List} of {@link EPackageProvider}.
	 * 
	 * @param receiverEClass
	 *            the receiver {@link EClass}
	 * @param parameters
	 *            the {@link List} of {@link EParameter}
	 * @return the {@link List} of {@link ParameterType} from the given receiver {@link EClass} and the
	 *         {@link List} of {@link EPackageProvider}
	 */
	private List<ParameterType> getParameterTypes(EClass receiverEClass, List<EParameter> parameters) {
		final List<ParameterType> result = new ArrayList<ParameterType>(parameters.size() + 1);

		result.add(new ParameterType(receiverEClass, false));
		for (EParameter parameter : parameters) {
			result.add(new ParameterType(parameter.getEType(), parameter.isMany()));
		}

		return result;
	}

	/**
	 * Caches the given {@link EOperation} for the given argument types.
	 * 
	 * @param eOperationName
	 *            the {@link EOperation#getName() name}
	 * @param parameterTypes
	 *            the {@link List} of {@link ParameterType}
	 * @param operation
	 *            the {@link EOperation} to cache
	 */
	private void cacheOperation(String eOperationName, List<ParameterType> parameterTypes,
			EOperation operation) {
		Node currentNode = cache.get(eOperationName);
		if (currentNode == null) {
			currentNode = new Node();
			cache.put(eOperationName, currentNode);
		}
		for (ParameterType parameterType : parameterTypes) {
			Node nextNode = currentNode.children.get(parameterType);
			if (nextNode == null) {
				nextNode = new Node();
				currentNode.children.put(parameterType, nextNode);
			}
			currentNode = nextNode;
		}
		if (operation == null) {
			currentNode.operation = NO_OPERATION;
		} else {
			currentNode.operation = operation;
		}
	}

	/**
	 * Gets the {@link Node} from the cache corresponding to the given name and parameter types.
	 * 
	 * @param name
	 *            the service name
	 * @param parameterTypes
	 *            the parameter types
	 * @return the {@link Node} from the cache corresponding to the given name and parameter types if any,
	 *         <code>null</code> otherwise
	 */
	private Node getNodeFromCache(String name, List<ParameterType> parameterTypes) {
		final Node result;

		Node currentNode = cache.get(name);
		if (currentNode != null) {
			for (ParameterType type : parameterTypes) {
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.EPackageProvider#registerPackage(org.eclipse.emf.ecore.EPackage)
	 */
	@Override
	public EPackage registerPackage(EPackage ePackage) {
		cache.clear();
		return super.registerPackage(ePackage);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.EPackageProvider#removePackage(java.lang.String)
	 */
	@Override
	public Collection<EPackage> removePackage(String name) {
		cache.clear();
		return super.removePackage(name);
	}

}
