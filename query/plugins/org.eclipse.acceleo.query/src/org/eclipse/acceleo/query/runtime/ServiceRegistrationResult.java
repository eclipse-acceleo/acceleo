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
package org.eclipse.acceleo.query.runtime;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Result of a {@link IQueryEnvironment#registerServicePackage(Class) service package registration}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ServiceRegistrationResult {

	/**
	 * The {@link List} of registered {@link Method}.
	 */
	private final List<Method> registered = new ArrayList<Method>();

	/**
	 * Mapping from newly registered methods and {@link Method} it duplicates.
	 */
	private final Map<Method, List<Method>> duplicated = new LinkedHashMap<Method, List<Method>>();

	/**
	 * Mapping from newly registered methods and {@link Method} it masks.
	 */
	private final Map<Method, List<Method>> masked = new LinkedHashMap<Method, List<Method>>();

	/**
	 * Mapping from newly registered methods and {@link Method} it is masked by.
	 */
	private final Map<Method, List<Method>> isMaskedBy = new LinkedHashMap<Method, List<Method>>();

	/**
	 * Gets the {@link List} of registered {@link Method}.
	 * 
	 * @return the {@link List} of registered {@link Method}
	 */
	public List<Method> getRegistered() {
		return registered;
	}

	/**
	 * Gets the mapping from newly registered methods and {@link Method} it duplicates.
	 * 
	 * @return the mapping from newly registered methods and {@link Method} it duplicates
	 */
	public Map<Method, List<Method>> getDuplicated() {
		return duplicated;
	}

	/**
	 * Gets the mapping from newly registered methods and {@link Method} it masks.
	 * 
	 * @return the mapping from newly registered methods and {@link Method} it masks
	 */
	public Map<Method, List<Method>> getMasked() {
		return masked;
	}

	/**
	 * Gets the mapping from newly registered methods and {@link Method} it is masked by.
	 * 
	 * @return the mapping from newly registered methods and {@link Method} it is masked by
	 */
	public Map<Method, List<Method>> getIsMaskedBy() {
		return isMaskedBy;
	}

	/**
	 * Merges the given {@link ServiceRegistrationResult} with this one.
	 * 
	 * @param otherResult
	 *            the {@link ServiceRegistrationResult} to merge
	 */
	public void merge(ServiceRegistrationResult otherResult) {
		registered.addAll(otherResult.registered);
		duplicated.putAll(otherResult.duplicated);
		masked.putAll(otherResult.masked);
		isMaskedBy.putAll(otherResult.isMaskedBy);
	}

	/**
	 * Adds the the given duplicated {@link Method} as a duplicates of the new {@link Method}.
	 * 
	 * @param newMethod
	 *            the new {@link Method}
	 * @param duplicatedMethod
	 *            the duplicated {@link Method}
	 */
	public void addDuplicated(Method newMethod, Method duplicatedMethod) {
		List<Method> methods = duplicated.get(newMethod);
		if (methods == null) {
			methods = new ArrayList<Method>();
			duplicated.put(newMethod, methods);
		}
		methods.add(duplicatedMethod);
	}

	/**
	 * Adds the the given masked {@link Method} as a masks of the new {@link Method}.
	 * 
	 * @param newMethod
	 *            the new {@link Method}
	 * @param maskedMethod
	 *            the masked {@link Method}
	 */
	public void addMasked(Method newMethod, Method maskedMethod) {
		List<Method> methods = masked.get(newMethod);
		if (methods == null) {
			methods = new ArrayList<Method>();
			masked.put(newMethod, methods);
		}
		methods.add(maskedMethod);
	}

	/**
	 * Adds the the given is masked by {@link Method} as a is masked by of the new {@link Method}.
	 * 
	 * @param newMethod
	 *            the new {@link Method}
	 * @param isMaskedByMethod
	 *            the is masked by {@link Method}
	 */
	public void addIsMaskedBy(Method newMethod, Method isMaskedByMethod) {
		List<Method> methods = isMaskedBy.get(newMethod);
		if (methods == null) {
			methods = new ArrayList<Method>();
			isMaskedBy.put(newMethod, methods);
		}
		methods.add(isMaskedByMethod);
	}

}
