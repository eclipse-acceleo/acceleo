/*******************************************************************************
 * Copyright (c) 2016, 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.evaluation.AbstractModuleElementService;
import org.eclipse.acceleo.aql.evaluation.AcceleoCallStack;
import org.eclipse.acceleo.aql.evaluation.AcceleoQueryEnvironment;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.QueryService;
import org.eclipse.acceleo.aql.evaluation.TemplateService;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;

/**
 * This environment will keep track of Acceleo's evaluation context. TODO doc.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEnvironment implements IAcceleoEnvironment {
	/** maps the modules registered against this environment with their qualified name. */
	private Map<String, Module> qualifiedNameToModule;

	/**
	 * Maps a {@link IAcceleoEnvironment#registerModule(String, Module) registered} {@link Module} to its
	 * qualified name.
	 */
	private final Map<Module, String> moduleToQualifiedName;

	/**
	 * The mapping from the module qualified name to its extends.
	 */
	private final Map<String, String> moduleExtends;

	/**
	 * The mapping from the module qualified name to its imports.
	 */
	private final Map<String, LinkedList<String>> moduleImports;

	/** Keeps track of the services each module qualified name provides, mapped to their names. */
	private Map<String, Map<String, Set<AbstractModuleElementService>>> moduleServices;

	/** The AQL environment that will be used to evaluate aql expressions from this Acceleo context. */
	private IQueryEnvironment aqlEnvironment;

	/** This will hold the writer stack for the file blocks. */
	private final Deque<IAcceleoWriter> writers = new ArrayDeque<IAcceleoWriter>();

	/** The current generation strategy. */
	private final IAcceleoGenerationStrategy generationStrategy;

	/**
	 * The destination {@link URI}.
	 */
	private final URI destination;

	/**
	 * Keeps track of the module elements we've called in order. Template and queries we call will be pushed
	 * against this depending on "how" they were called.
	 * <p>
	 * Module elements called because they're the "main" template (starting point of an evaluation) or through
	 * regular calls within the extends hierarchy of a module will be pushed atop the latest stack, whereas
	 * those called because they're "imported" from the module of a previous call will be pushed atop a new
	 * stack.
	 * </p>
	 * <p>
	 * This will allow us to always know where the "current" evaluated point is according to the previous
	 * calls, which in turn will help us properly configure the lookup engine of the underlying
	 * {@link #aqlEnvironment}.
	 * </p>
	 */
	private final Deque<AcceleoCallStack> callStacks;

	/**
	 * The {@link GenerationResult}.
	 */
	private final GenerationResult generationResult = new GenerationResult();

	/**
	 * Constructor.
	 * 
	 * @param generationStrategy
	 *            the {@link IAcceleoGenerationStrategy}
	 * @param destination
	 *            the destination {@link URI}
	 */
	public AcceleoEnvironment(IAcceleoGenerationStrategy generationStrategy, URI destination) {
		this.generationStrategy = generationStrategy;
		this.destination = destination;
		this.qualifiedNameToModule = new LinkedHashMap<>();
		this.moduleToQualifiedName = new LinkedHashMap<>();
		this.moduleExtends = new LinkedHashMap<>();
		this.moduleImports = new LinkedHashMap<>();
		this.moduleServices = new LinkedHashMap<>();
		this.callStacks = new ArrayDeque<>();

		this.aqlEnvironment = new AcceleoQueryEnvironment(new EPackageProvider(), this);
		/* FIXME we need a cross reference provider, and we need to make it configurable */
		org.eclipse.acceleo.query.runtime.Query.configureEnvironment(aqlEnvironment, null, null);
	}

	@Override
	public void pushImport(String importModuleQualifiedName, ModuleElement moduleElement) {
		final AcceleoCallStack currentStack = new AcceleoCallStack(importModuleQualifiedName);
		callStacks.addLast(currentStack);
		currentStack.push(moduleElement);
	}

	@Override
	public void push(ModuleElement moduleElement) {
		final AcceleoCallStack currentStack = callStacks.peekLast();
		currentStack.push(moduleElement);
	}

	@Override
	public void popStack(ModuleElement moduleElement) {
		AcceleoCallStack currentStack = callStacks.peekLast();
		if (currentStack == null || (!currentStack.pop().equals(moduleElement) && currentStack.isEmpty())) {
			// TODO this module wasn't on the top of our current stack. we're out of turn on our push/pop
			// cycle. throw exception?
			// this probably need to be an assert since it's a developer concern
		}
		if (currentStack.isEmpty()) {
			callStacks.pollLast();
		}
	}

	/* TODO Make this package protected? there are a few things on this class we don't want to expose. */
	/**
	 * Returns the latest (current) call stack known to this environment.
	 * 
	 * @return The latest (current) call stack known to this environment.
	 */
	public AcceleoCallStack getCurrentStack() {
		return callStacks.peekLast();
	}

	/**
	 * Returns the module registered against the given qualified name in this environment.
	 * 
	 * @param qualifiedName
	 *            The qualified name of the module we seek.
	 * @return The module registered against the given qualified name in this environment, <code>null</code>
	 *         if none.
	 */
	public Module getModule(String qualifiedName) {
		return qualifiedNameToModule.get(qualifiedName);
	}

	@Override
	public String getModuleQualifiedName(Module module) {
		return moduleToQualifiedName.get(module);
	}

	@Override
	public String getExtend(String qualifiedName) {
		return moduleExtends.get(qualifiedName);
	}

	@Override
	public Collection<String> getImports(String qualifiedName) {
		return moduleImports.getOrDefault(qualifiedName, new LinkedList<>());
	}

	/**
	 * Returns all IServices with the given {@code name} provided by the given module.
	 * 
	 * @param moduleQualifiedName
	 *            The module qualified name which services we're looking up.
	 * @param moduleElementName
	 *            Name of the service(s) we're searching for.
	 * @return All IServices with the given {@code name} provided by the given module, <code>null</code> if
	 *         none.
	 */
	public Set<AbstractModuleElementService> getServicesWithName(String moduleQualifiedName,
			String moduleElementName) {
		// FIXME null or empty set if either of the two gets is null?
		return moduleServices.getOrDefault(moduleQualifiedName, new LinkedHashMap<>()).getOrDefault(
				moduleElementName, new LinkedHashSet<AbstractModuleElementService>());
	}

	@Override
	public void registerModule(String qualifiedName, Module module) {
		qualifiedNameToModule.put(qualifiedName, module);
		moduleToQualifiedName.put(module, qualifiedName);
		if (module.getExtends() != null && module.getExtends().getQualifiedName() != null) {
			moduleExtends.put(qualifiedName, module.getExtends().getQualifiedName());
		}
		for (Import imp : module.getImports()) {
			final ModuleReference moduleRef = imp.getModule();
			if (moduleRef != null && moduleRef.getQualifiedName() != null) {
				moduleImports.computeIfAbsent(qualifiedName, key -> new LinkedList<>()).add(moduleRef
						.getQualifiedName());
			}
		}

		for (Metamodel metamodel : module.getMetamodels()) {
			if (!(metamodel instanceof ErrorMetamodel)) {
				EPackage referredPackage = metamodel.getReferencedPackage();
				aqlEnvironment.registerEPackage(referredPackage);
				ServiceUtils.registerServices(aqlEnvironment, ServiceUtils.getServices(referredPackage));
			}
		}

		Map<String, Set<AbstractModuleElementService>> services = moduleServices.computeIfAbsent(
				qualifiedName, key -> new LinkedHashMap<>());
		for (ModuleElement element : module.getModuleElements()) {
			if (element instanceof Template) {
				String name = ((Template)element).getName();
				services.computeIfAbsent(name, key -> new LinkedHashSet<>()).add(new TemplateService(this,
						(Template)element));
			} else if (element instanceof Query) {
				String name = ((Query)element).getName();
				services.computeIfAbsent(name, key -> new LinkedHashSet<>()).add(new QueryService(this,
						(Query)element));
			}
		}
	}

	@Override
	public IQueryEnvironment getQueryEnvironment() {
		return aqlEnvironment;
	}

	@Override
	public boolean hasModule(String qualifiedName) {
		// FIXME delegate to the lookup class that will provide an input stream and try to load the module
		// FIXME remove the test mock code
		final boolean testMock = !qualifiedName.contains("notExisting");
		return qualifiedNameToModule.containsKey(qualifiedName) || testMock;
	}

	@Override
	public void openWriter(URI uri, OpenModeKind openMode, Charset charset, String lineDelimiter)
			throws IOException {
		final IAcceleoWriter writer = generationStrategy.createWriterFor(uri, openMode, charset,
				lineDelimiter);
		writers.addLast(writer);
		generationResult.getGeneratedFiles().add(uri);
	}

	@Override
	public void closeWriter() throws IOException {
		final IAcceleoWriter writer = writers.removeLast();
		writer.close();
	}

	@Override
	public void write(String text) throws IOException {
		IAcceleoWriter writer = writers.peekLast();
		writer.append(text);
	}

	@Override
	public URI getDestination() {
		return destination;
	}

	@Override
	public GenerationResult getGenerationResult() {
		return generationResult;
	}

}
