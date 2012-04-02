/*****************************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *****************************************************************************************/
package org.eclipse.acceleo.parser.interpreter;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.acceleo.common.interpreter.CompilationResult;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.acceleo.parser.AcceleoParserInfos;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserProblems;
import org.eclipse.acceleo.parser.AcceleoParserWarning;
import org.eclipse.acceleo.parser.AcceleoParserWarnings;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * This class allows adopters to compile the String representation of a module in an asynchronous way. The
 * resulting {@link CompilationResult} will be set to reference either the {@link Module} itself, or one of
 * its queries if we fed its name to this task.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public class AcceleoCompilationTask implements Callable<CompilationResult> {
	/** This constant holds the Acceleo parser's plugin id. */
	private static final String PARSER_PLUGIN_ID = "org.eclipse.acceleo.parser"; //$NON-NLS-1$

	/** Dependencies required by the compiled module. */
	private final Set<URI> dependencies;

	/** String representation of the module we are to compile. */
	private final ModuleDescriptor moduleDescriptor;

	/** The name of the query the {@link CompilationResult}s should reference. */
	private final String queryName;

	/** The resource set in which to compile. */
	private ResourceSet resourceSet;

	/**
	 * Instantiates our compilation task given the current interpreter context and the optional dependencies.
	 * 
	 * @param descriptor
	 *            The String representation of the module we are to compile.
	 * @param dependencies
	 *            Dependencies required by the compiled module. Can be <code>null</code>.
	 * @since 3.3
	 */
	public AcceleoCompilationTask(ModuleDescriptor descriptor, Set<URI> dependencies) {
		this(descriptor, dependencies, null);
	}

	/**
	 * Instantiates our compilation task given the current interpreter context and the optional dependencies.
	 * <p>
	 * This constructor should be called over {@link #AcceleoCompilationTask(String, Set)} when we need a
	 * specific query as the result.
	 * </p>
	 * 
	 * @param descriptor
	 *            The String representation of the module we are to compile.
	 * @param dependencies
	 *            Dependencies required by the compiled module. Can be <code>null</code>.
	 * @param queryName
	 *            The name of the query the {@link CompilationResult}s should reference. Can be
	 *            <code>null</code>.
	 * @since 3.3
	 */
	public AcceleoCompilationTask(ModuleDescriptor descriptor, Set<URI> dependencies, String queryName) {
		this(descriptor, dependencies, queryName, null);
	}

	/**
	 * Instantiates our compilation task given the current interpreter context and the optional dependencies.
	 * <p>
	 * This constructor should be called over {@link #AcceleoCompilationTask(String, Set)} when we need a
	 * specific query as the result.
	 * </p>
	 * 
	 * @param descriptor
	 *            The String representation of the module we are to compile.
	 * @param dependencies
	 *            Dependencies required by the compiled module. Can be <code>null</code>.
	 * @param queryName
	 *            The name of the query the {@link CompilationResult}s should reference. Can be
	 *            <code>null</code>.
	 * @param resourceSet
	 *            The resource set in which to compile. Can be <code>null</code>.
	 * @since 3.3
	 */
	public AcceleoCompilationTask(ModuleDescriptor descriptor, Set<URI> dependencies, String queryName,
			ResourceSet resourceSet) {
		this.moduleDescriptor = descriptor;
		if (dependencies != null) {
			this.dependencies = dependencies;
		} else {
			this.dependencies = Collections.emptySet();
		}
		this.queryName = queryName;
		this.resourceSet = resourceSet;
	}

	/**
	 * Compiles the given list of Acceleo parser messages into a single MultiStatus.
	 * 
	 * @param errors
	 *            List of the errors that arose during the compilation.
	 * @param warnings
	 *            List of the warnings that arose during the compilation.
	 * @param infos
	 *            List of the infos that arose during the compilation.
	 * @return A single MultiStatus referencing all issues.
	 */
	private static IStatus parseProblems(AcceleoParserProblems errors, AcceleoParserWarnings warnings,
			AcceleoParserInfos infos) {
		List<IStatus> problems = new ArrayList<IStatus>();

		for (AcceleoParserProblem error : errors.getList()) {
			problems.add(new Status(IStatus.ERROR, PARSER_PLUGIN_ID, error.getMessage()));
		}
		for (AcceleoParserWarning warning : warnings.getList()) {
			problems.add(new Status(IStatus.WARNING, PARSER_PLUGIN_ID, warning.getMessage()));
		}
		for (AcceleoParserInfo info : infos.getList()) {
			problems.add(new Status(IStatus.INFO, PARSER_PLUGIN_ID, info.getMessage()));
		}

		if (problems.isEmpty()) {
			return null;
		}

		MultiStatus status = new MultiStatus(PARSER_PLUGIN_ID, 1, AcceleoParserMessages
				.getString("AcceleoCompilationTask.CompilationIssues"), null); //$NON-NLS-1$
		for (IStatus child : problems) {
			status.add(child);
		}
		return status;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public CompilationResult call() throws Exception {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
		}
		final Resource old = resourceSet.getResource(moduleDescriptor.getModuleURI(), false);
		if (old != null) {
			resourceSet.getResources().remove(old);
		}
		final Resource resource = resourceSet.createResource(moduleDescriptor.getModuleURI());

		AcceleoSourceBuffer source = new AcceleoSourceBuffer(new StringBuffer(moduleDescriptor
				.getModuleContent()));

		loadImports();

		AcceleoParser parser = new AcceleoParser();
		parser.parse(source, resource, Lists.newArrayList(dependencies));

		EObject resultNode = null;
		if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof Module) {
			resultNode = resource.getContents().get(0);

			if (queryName != null) {
				final Iterator<Query> queries = Iterables.filter(
						((Module)resultNode).getOwnedModuleElement(), Query.class).iterator();
				while (!(resultNode instanceof Query) && queries.hasNext()) {
					final Query query = queries.next();
					if (queryName.equals(query.getName())) {
						resultNode = query;
					}
				}
			}
		}

		IStatus problems = parseProblems(source.getProblems(), source.getWarnings(), source.getInfos());
		return new CompilationResult(resultNode, problems);
	}

	/**
	 * Load and resolve all dependencies in the current resource set.
	 */
	private void loadImports() {
		for (URI dependency : dependencies) {
			resourceSet.getResource(dependency, true);
		}
	}
}
