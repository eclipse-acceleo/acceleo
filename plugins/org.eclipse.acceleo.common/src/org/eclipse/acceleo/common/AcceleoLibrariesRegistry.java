/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common;

import java.util.Set;

import org.eclipse.acceleo.common.internal.utils.AcceleoLibrariesEclipseUtil;
import org.eclipse.acceleo.common.library.connector.ILibrary;
import org.eclipse.acceleo.common.library.connector.IQuery;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.ecore.EClassifier;

/**
 * This will allow Acceleo to know which libraries are added to the evaluation context.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.8
 */
public final class AcceleoLibrariesRegistry {
	/** Singleton instance of the registry. */
	public static final AcceleoLibrariesRegistry INSTANCE = new AcceleoLibrariesRegistry();

	/** This will contain the libraries registered for Acceleo evaluations. */
	private final Set<ILibrary> registeredLibraries = new CompactLinkedHashSet<ILibrary>();

	/**
	 * This class is a singleton. Access instance through {@link #INSTANCE}.
	 */
	private AcceleoLibrariesRegistry() {
		// Hides default constructor
	}

	/**
	 * Adds a library to the registry.
	 * 
	 * @param library
	 *            Library that is to be registered for Acceleo evaluations.
	 * @return <code>true</code> if the set didn't already contain <code>library</code>.
	 */
	public boolean addLibrary(ILibrary library) {

		return registeredLibraries.add(library);
	}

	/**
	 * Returns all registered library. <b>Note</b> that workspace libraries are refreshed each time this is
	 * called if Eclipse is running.
	 * 
	 * @return All registered libraries.
	 */
	public Set<ILibrary> getAllRegisteredLibraries() {
		final Set<ILibrary> compound = new CompactLinkedHashSet<ILibrary>();
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			compound.addAll(AcceleoLibrariesEclipseUtil.getRegisteredLibraries());
		}
		compound.addAll(registeredLibraries);
		return compound;
	}

	/**
	 * Returns all registered services methods. <b>Note</b> that workspace services are refreshed each time
	 * this is called if Eclipse is running.
	 * 
	 * @return All applicable registered services methods.
	 */
	public Set<IQuery> getAllRegisteredQueries() {
		final Set<ILibrary> libraries = getAllRegisteredLibraries();
		// at least
		final Set<IQuery> queries = new CompactLinkedHashSet<IQuery>(libraries.size());
		for (ILibrary library : libraries) {
			for (IQuery query : library.getQueries()) {
				queries.add(query);
			}
		}
		return queries;
	}

	/**
	 * Returns all registered queries applicable for the given type. <b>Note</b> that workspace queries are
	 * refreshed each time this is called if Eclipse is running.
	 * 
	 * @param receiverType
	 *            Type of the receiver we seek applicable query for.
	 * @return All applicable registered queries.
	 */
	public Set<IQuery> getRegisteredQueries(EClassifier receiverType) {
		final Set<IQuery> allQueries = getAllRegisteredQueries();
		final Set<IQuery> applicableQueries = new CompactLinkedHashSet<IQuery>(allQueries.size());
		for (IQuery query : allQueries) {
			final EClassifier contextType = query.getContextType();
			if (contextType.equals(receiverType)) {
				applicableQueries.add(query);
			}
		}
		return applicableQueries;
	}

	/**
	 * Removes a library from the registry.
	 * 
	 * @param service
	 *            Library that is to be removed from Acceleo evaluations contexts.
	 * @return <code>true</code> if the set contained <code>service</code>.
	 */
	public boolean removeLibrary(ILibrary service) {
		return registeredLibraries.remove(service);
	}

	/**
	 * Clears all registered libraries out of the registry.
	 */
	public void clearRegistry() {
		registeredLibraries.clear();
	}
}
