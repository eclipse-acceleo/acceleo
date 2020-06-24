/*******************************************************************************
 * Copyright (c) 2016, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Specific lookup engine for Acceleo, this implementation will mainly be used to determine which query or
 * template is to be invoked for a given call. See {@link #lookup(String, IType[])} for a description of the
 * lookup order we're implementing.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoLookupEngine extends BasicLookupEngine {
	/** The current acceleo environment. */
	private final AcceleoEnvironment acceleoEnvironment;

	/**
	 * Constructs a lookup engine given the aql and acceleo environments.
	 * 
	 * @param aqlEnvironment
	 *            The AQL environment.
	 * @param acceleoEnvironment
	 *            The Acceleo environment.
	 */
	public AcceleoLookupEngine(IReadOnlyQueryEnvironment aqlEnvironment,
			AcceleoEnvironment acceleoEnvironment) {
		super(aqlEnvironment);
		this.acceleoEnvironment = acceleoEnvironment;
	}

	/*
	 * TODO Can we somehow cache the results or intermediate results?
	 */
	/**
	 * Overrides the default behavior in order to implement a specific lookup order for Acceleo Templates and
	 * services before falling back to AQL's lookup if we can't find a matching module element.
	 * <p>
	 * This will use the call stack to determine what the module of the current call is and what the module of
	 * the first call was. Then, in order or priority, we'll look for:
	 * <ol>
	 * <li>A private query or template in the module of the current call,</li>
	 * <li>A protected or public query or template in the hierarchy of the first module of the current
	 * stack,</li>
	 * <li>A public query or template in the modules imported by the module of the current call and their own
	 * extends hierarchy,</li>
	 * <li>A service registered in the AQL environment.</li>
	 * </ol>
	 * </p>
	 */
	@Override
	public IService<?> lookup(String name, IType[] argumentTypes) {
		AcceleoCallStack currentStack = acceleoEnvironment.getCurrentStack();

		/* PRIVATE query or template in the same module as our current (last of the stack) */
		String last = acceleoEnvironment.getModuleQualifiedName((Module)currentStack.peek().eContainer());
		Set<IService<?>> lastServices = acceleoEnvironment.getServicesWithName(last, name);
		IService<?> result = lookup(lastServices, argumentTypes, VisibilityKind.PRIVATE);

		/*
		 * PUBLIC or PROTECTED template or query in the extends hierarchy of our "lowest" module in that
		 * hierarchy (first of the stack)
		 */
		if (result == null) {
			String start = currentStack.getStartingModuleQualifiedName();
			result = lookupExtendedService(start, name, argumentTypes, VisibilityKind.PROTECTED,
					VisibilityKind.PUBLIC);
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
	 *            The module qualified name we're considering as the "root" of our extends hierarchy.
	 * @param name
	 *            The name of the service we're looking for.
	 * @param argumentTypes
	 *            Type of the arguments accepted by the service we're looking for.
	 * @param candidateVisibilities
	 *            The visibility to consider for our services.
	 * @return The service matching the criteria if any, <code>null</code> if none.
	 */
	private IService<?> lookupExtendedService(String startQualifiedName, String name, IType[] argumentTypes,
			VisibilityKind... candidateVisibilities) {
		Set<IService<?>> services = acceleoEnvironment.getServicesWithName(startQualifiedName, name);
		IService<?> result = lookup(services, argumentTypes, candidateVisibilities);
		if (result == null) {
			final String extendedModuleQualifiedName = acceleoEnvironment.getExtend(startQualifiedName);
			if (extendedModuleQualifiedName != null) {
				result = lookupExtendedService(extendedModuleQualifiedName, name, argumentTypes,
						candidateVisibilities);
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
		Iterator<String> importedIterator = acceleoEnvironment.getImports(start).iterator();
		while (importedIterator.hasNext() && result == null) {
			String imported = importedIterator.next();
			result = lookupExtendedService(imported, name, argumentTypes, VisibilityKind.PUBLIC);
		}
		return result;
	}

	/**
	 * Searches through the given set of services for one that has matches the given arguments and
	 * visibilities.
	 * 
	 * @param services
	 *            The set of candidate services.
	 * @param argumentTypes
	 *            Types of the arguments for our target service.
	 * @param candidateVisibilities
	 *            The visibilities we're expecting this service to have.
	 * @return The matching service if any, <code>null</code> if none.
	 */
	private IService<?> lookup(Set<IService<?>> services, IType[] argumentTypes,
			VisibilityKind... candidateVisibilities) {
		List<VisibilityKind> visibilityList = Arrays.asList(candidateVisibilities);
		// @formatter:off
		Optional<IService<?>> result = services.stream()
				.filter(service -> service.getNumberOfParameters() == argumentTypes.length)
				.filter(service -> isVisible(service, visibilityList))
				.filter(service -> service.matches(queryEnvironment, argumentTypes))
				.findAny();
		// @formatter:on
		return result.orElse(null);
	}

	/**
	 * Tells if the given {@link IService} is visible according to given {@link VisibilityKind}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @param visibilityList
	 *            the {@link List} of {@link VisibilityKind}
	 * @return <code>true</code> if the given {@link IService} is visible according to given
	 *         {@link VisibilityKind}, <code>false</code> otherwise
	 */
	private boolean isVisible(IService<?> service, List<VisibilityKind> visibilityList) {
		final boolean res;

		if (service instanceof AbstractModuleElementService) {
			res = visibilityList.contains(((AbstractModuleElementService)service).getVisibility());
		} else {
			res = true;
		}

		return res;
	}

}
