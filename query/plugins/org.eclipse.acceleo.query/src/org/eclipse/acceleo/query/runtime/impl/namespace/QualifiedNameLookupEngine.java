/*******************************************************************************
 * Copyright (c) 2020, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.lookup.basic.CacheLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Lookup engine for qualified name.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QualifiedNameLookupEngine extends CacheLookupEngine implements IQualifiedNameLookupEngine {

	/**
	 * Mapping from qualifiedName to its {@link CacheLookupEngine}.
	 */
	private final Map<String, CacheLookupEngine> qualifiedNameServices = new HashMap<String, CacheLookupEngine>();

	/**
	 * The {@link IQualifiedNameResolver}.
	 */
	private final IQualifiedNameResolver resolver;

	/**
	 * The qualified names context stack.
	 */
	private final Deque<CallStack> callStacks = new ArrayDeque<CallStack>();

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 */
	public QualifiedNameLookupEngine(IReadOnlyQueryEnvironment queryEnvironment,
			IQualifiedNameResolver resolver) {
		super(queryEnvironment);
		this.resolver = resolver;
	}

	@Override
	public IService<?> lookup(String name, IType[] argumentTypes) {
		final CallStack currentStack = getCurrentContext();

		/* PRIVATE query or template in the same module as our current (last of the stack) */
		String last = currentStack.peek();
		final IService<?> lastService = getLookupEngine(last).lookup(name, argumentTypes);
		IService<?> result = null;
		if (lastService != null && lastService.getVisibility() == IService.Visibility.PRIVATE) {
			result = lastService;
		}

		/*
		 * PUBLIC or PROTECTED template or query in the extends hierarchy of our "lowest" module in that
		 * hierarchy (first of the stack)
		 */
		if (result == null) {
			String start = currentStack.getStartingQualifiedName();
			final IService<?> inLastExtendHierachyService = lookupExtendedService(last, null, name,
					argumentTypes, IService.Visibility.PROTECTED, IService.Visibility.PUBLIC);
			if (inLastExtendHierachyService != null) {
				if (start.equals(last)) {
					result = inLastExtendHierachyService;
				} else {
					result = lookupExtendedService(start, last, name, argumentTypes,
							IService.Visibility.PROTECTED, IService.Visibility.PUBLIC);
				}
			}
		}

		/*
		 * We couldn't find a template or query matching that in our current extends hierarchy, try the
		 * imports of our current (last of the stack) module for a PUBLIC matching module element.
		 */
		if (result == null) {
			result = lookupImportedService(last, name, argumentTypes);
		}

		/* There is no module element matching our target, fall back to regular services. */
		if (result == null) {
			result = super.lookup(name, argumentTypes);
		}

		return result;
	}

	/**
	 * Looks up in the hierarchy of the given {@code start}ing module qualified name for a query or template
	 * matching the given name and arguments.
	 * 
	 * @param startQualifiedName
	 *            The module qualified name we're considering as the "bottom" of our extends hierarchy.
	 * @param stopQualifiedName
	 *            The module qualified name we're considering as the "top" of our extends hierarchy if any,
	 *            <code>null</code> to lookup in all the hierarchy.
	 * @param name
	 *            The name of the service we're looking for.
	 * @param argumentTypes
	 *            Type of the arguments accepted by the service we're looking for.
	 * @param candidateVisibilities
	 *            The IService.Visibility to consider for our services.
	 * @return The service matching the criteria if any, <code>null</code> if none.
	 */
	private IService<?> lookupExtendedService(String startQualifiedName, String stopQualifiedName,
			String name, IType[] argumentTypes, IService.Visibility... candidateVisibilities) {
		final IService<?> service = getLookupEngine(startQualifiedName).lookup(name, argumentTypes);
		IService<?> result = null;

		if (service != null && isVisible(service, candidateVisibilities)) {
			result = service;
		}
		if (result == null && !startQualifiedName.equals(stopQualifiedName)) {
			final String extendedModuleQualifiedName = resolver.getExtend(startQualifiedName);
			if (extendedModuleQualifiedName != null) {
				result = lookupExtendedService(extendedModuleQualifiedName, stopQualifiedName, name,
						argumentTypes, candidateVisibilities);
			}
		}

		return result;
	}

	/**
	 * Looks up in the imports of the given {@code start}ing module qualified name for a query or template
	 * matching the given name and arguments. This will consider public templates and queries from the imports
	 * and their extends hierarchy.
	 * 
	 * @param start
	 *            The module qualified name we're considering as the "root" of our imports lookup.
	 * @param name
	 *            The name of the service we're looking for.
	 * @param argumentTypes
	 *            Type of the arguments accepted by the service we're looking for.
	 * @return The service matching the criteria if any, <code>null</code> if none.
	 */
	private IService<?> lookupImportedService(String start, String name, IType[] argumentTypes) {
		IService<?> result = null;

		for (String imported : resolver.getImports(start)) {
			final IService<?> service = lookupExtendedService(imported, null, name, argumentTypes,
					IService.Visibility.PUBLIC);
			if (service != null) {
				result = service;
				break;
			}
		}

		return result;
	}

	@Override
	public Set<IService<?>> getServices(Set<IType> receiverTypes) {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		final Set<IService<?>> storedServices = getRegisteredServices();
		for (IType type : receiverTypes) {
			if (type != null) {
				for (IService<?> service : storedServices) {
					for (IType parameterType : service.getParameterTypes(queryEnvironment).get(0)) {
						if (parameterType.isAssignableFrom(type)) {
							result.add(service);
							break;
						}
					}
				}
			}
		}

		return result;
	}

	@Override
	public Set<IService<?>> getRegisteredServices() {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();
		final CallStack currentStack = getCurrentContext();

		/* Query or Template in the same module as our current (last of the stack) */
		final String last = currentStack.peek();
		Set<IService<?>> lastServices = getLookupEngine(last).getRegisteredServices();
		result.addAll(filterByVisibility(lastServices, IService.Visibility.PRIVATE));

		/*
		 * PUBLIC or PROTECTED template or query in the extends hierarchy of our "lowest" module in that
		 * hierarchy (first of the stack)
		 */
		String start = currentStack.getStartingQualifiedName();
		result.addAll(getExtendedService(start, IService.Visibility.PROTECTED, IService.Visibility.PUBLIC));

		/*
		 * Imports of our current (last of the stack) module for a PUBLIC matching module element.
		 */
		result.addAll(getImportedService(last));

		result.addAll(super.getRegisteredServices());

		return result;
	}

	/**
	 * Gets the {@link Set} of imported {@link IService} for the given qualified name.
	 * 
	 * @param start
	 *            the qualified name
	 * @return the {@link Set} of imported {@link IService} for the given qualified name and that match the
	 *         given receiver {@link IType}
	 */
	private Set<IService<?>> getImportedService(String start) {
		Set<IService<?>> result = new LinkedHashSet<IService<?>>();
		for (String imported : resolver.getImports(start)) {
			result.addAll(getExtendedService(imported, IService.Visibility.PUBLIC));
		}
		return result;
	}

	/**
	 * Gets the {@link Set} of {@link IService} form the given qualified name that match the given
	 * {@link IService.Visibility}.
	 * 
	 * @param startQualifiedName
	 *            the qualified name
	 * @param candidateVisibilities
	 * @return the {@link Set} of {@link IService} form the given qualified name that match the given
	 *         {@link IService.Visibility}
	 */
	private Set<IService<?>> getExtendedService(String startQualifiedName,
			IService.Visibility... candidateVisibilities) {
		Set<IService<?>> services = getLookupEngine(startQualifiedName).getRegisteredServices();
		Set<IService<?>> result = filterByVisibility(services, candidateVisibilities);
		final String extendedModuleQualifiedName = resolver.getExtend(startQualifiedName);
		if (extendedModuleQualifiedName != null) {
			result.addAll(getExtendedService(extendedModuleQualifiedName, candidateVisibilities));
		}
		return result;
	}

	@Override
	public boolean isRegisteredService(IService<?> service) {
		return getRegisteredServices().contains(service);
	}

	@Override
	public void pushContext(String qualifiedName) {
		final CallStack currentStack = callStacks.peekLast();
		currentStack.push(qualifiedName);
	}

	@Override
	public void pushImportsContext(String importQualifiedName, String serviceContextQualifiedName) {
		final CallStack currentStack = new CallStack(importQualifiedName);
		callStacks.addLast(currentStack);
		currentStack.push(serviceContextQualifiedName);
	}

	@Override
	public void popContext(String qualifiedName) {
		final CallStack currentStack = callStacks.peekLast();
		if (currentStack == null || (!currentStack.pop().equals(qualifiedName) && currentStack.isEmpty())) {
			throw new IllegalStateException("call stack is out of synchronization");
		}
		if (currentStack.isEmpty()) {
			callStacks.pollLast();
		}
	}

	@Override
	public void clearContext(String qualifiedName) {
		qualifiedNameServices.remove(qualifiedName);
		resolver.cleanContextQualifiedName(qualifiedName);
	}

	@Override
	public CallStack getCurrentContext() {
		final CallStack res;

		if (!callStacks.isEmpty()) {
			res = callStacks.peekLast();
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of filtered {@link IService} with the given {@link IService.Visibility} form the
	 * given {@link Set} of {@link IService}.
	 * 
	 * @param services
	 *            the {@link Set} of {@link IService}
	 * @param candidateVisibilities
	 *            the {@link IService.Visibility}
	 * @return the {@link Set} of filtered {@link IService} with the given {@link IService.Visibility} form
	 *         the given {@link Set} of {@link IService}
	 */
	private Set<IService<?>> filterByVisibility(Set<IService<?>> services,
			IService.Visibility... candidateVisibilities) {
		return services.stream().filter(s -> isVisible(s, candidateVisibilities)).collect(Collectors
				.toCollection(LinkedHashSet::new));
	}

	/**
	 * Tells if the given {@link IService} is visible according to given {@link IService.Visibility}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @param candidateVisibilities
	 *            The visibilities we're expecting this service to have.
	 * @return <code>true</code> if the given {@link IService} is visible according to given
	 *         {@link IService.Visibility}, <code>false</code> otherwise
	 */
	private boolean isVisible(IService<?> service, IService.Visibility... candidateVisibilities) {
		final boolean res;

		final List<IService.Visibility> visibilityList = Arrays.asList(candidateVisibilities);
		res = visibilityList.contains(service.getVisibility());

		return res;
	}

	private CacheLookupEngine getLookupEngine(String qualifiedName) {
		if (!qualifiedNameServices.containsKey(qualifiedName)) {
			final Object object = resolver.resolve(qualifiedName);

			final CacheLookupEngine engine = new CacheLookupEngine(queryEnvironment);
			for (IService<?> service : resolver.getServices(this, object, qualifiedName)) {
				engine.registerService(service);
			}
			qualifiedNameServices.put(qualifiedName, engine);
		}

		return qualifiedNameServices.get(qualifiedName);
	}

	@Override
	public IReadOnlyQueryEnvironment getQueryEnvironment() {
		return queryEnvironment;
	}

	@Override
	public String getExtend(String qualifiedName) {
		return resolver.getExtend(qualifiedName);
	}

	@Override
	public List<String> getImports(String qualifiedName) {
		return resolver.getImports(qualifiedName);
	}

	@Override
	public IQualifiedNameResolver getResolver() {
		return resolver;
	}

	@Override
	public IService<?> superServiceLookup(String name, IType[] argumentTypes) {
		final IService<?> result;

		final CallStack currentStack = getCurrentContext();
		final String start = currentStack.getStartingQualifiedName();
		final String extendQualifiedName = getExtend(start);
		if (extendQualifiedName != null) {
			result = lookupExtendedService(extendQualifiedName, null, name, argumentTypes,
					IService.Visibility.PROTECTED, IService.Visibility.PUBLIC);
		} else {
			result = null;
		}

		return result;
	}

	@Override
	public boolean isInExtends(String startQualifiedName, String calleeQualifiedName) {
		String currentQualifiedName = startQualifiedName;
		while (currentQualifiedName != null) {
			if (currentQualifiedName.equals(calleeQualifiedName)) {
				return true;
			}
			currentQualifiedName = getExtend(currentQualifiedName);
		}
		return false;
	}

}
