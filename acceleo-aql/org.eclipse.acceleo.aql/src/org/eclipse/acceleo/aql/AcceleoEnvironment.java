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
package org.eclipse.acceleo.aql;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
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
import org.eclipse.acceleo.aql.evaluation.AcceleoCallStack;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.AcceleoQueryEnvironment;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.QueryService;
import org.eclipse.acceleo.aql.evaluation.TemplateService;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.resolver.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.validation.type.IType;
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

	/** Keeps track of the services each qualified name provides, mapped to their names. */
	private Map<String, Map<String, Set<IService<?>>>> qualifiedNameServices;

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

	// TODO without a default value, the environment will not be able to resolve imported or extended modules.
	// Can we set a default with the information we have at creation time?
	/**
	 * The resolver for this environment.
	 * <p>
	 * This will be used whenever a module tries to access a qualified name, such as import or extends.
	 * </p>
	 */
	private IQualifiedNameResolver resolver;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	private AcceleoEvaluator evaluator;

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
		this.qualifiedNameServices = new LinkedHashMap<>();
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

	private Module resolveModule(String qualifiedName) {
		if (resolver == null) {
			return qualifiedNameToModule.get(qualifiedName);
		}

		Module module;
		try {
			module = resolver.resolveModule(qualifiedName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			module = null;
		}
		if (module != null) {
			registerModule(qualifiedName, module);
		}
		return module;
	}

	@Override
	public String getModuleQualifiedName(Module module) {
		return moduleToQualifiedName.get(module);
	}

	@Override
	public URL getModuleURL(Module module) {
		return resolver.getModuleURL(getModuleQualifiedName(module));
	}

	@Override
	public URL getModuleSourceURL(Module module) {
		return resolver.getModuleSourceURL(getModuleQualifiedName(module));
	}

	@Override
	public String getExtend(String qualifiedName) {
		String extended = moduleExtends.get(qualifiedName);
		if (extended != null && !hasQualifiedName(extended)) {
			// TODO log runtime error? This should happen at evaluation time if the extended module cannot be
			// resolved
		}
		return extended;
	}

	@Override
	public Collection<String> getImports(String qualifiedName) {
		Collection<String> imported = moduleImports.getOrDefault(qualifiedName, new LinkedList<>());
		for (String importedName : imported) {
			if (!hasQualifiedName(importedName)) {
				// TODO log runtime error? would happen at evaluation time if an import cannot be resolved
			}
		}
		return imported;
	}

	/**
	 * Returns all IServices with the given {@code name} provided by the given qualified name.
	 * 
	 * @param qualifiedName
	 *            The qualified name which services we're looking up.
	 * @param serviceName
	 *            Name of the service(s) we're searching for.
	 * @return All IServices with the given {@code name} provided by the given module, <code>null</code> if
	 *         none.
	 */
	public Set<IService<?>> getServicesWithName(String qualifiedName, String serviceName) {
		final Module module = getModule(qualifiedName);
		if (module == null && !qualifiedNameServices.containsKey(qualifiedName)) {
			resolveClass(qualifiedName);
		}

		return qualifiedNameServices.getOrDefault(qualifiedName, new LinkedHashMap<>()).getOrDefault(
				serviceName, new LinkedHashSet<IService<?>>());
	}

	/**
	 * Gets the {@link Set} of known {@link IService} with a compatible receiver {@link IType types} for the
	 * given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @param receiverTypes
	 *            the receiver {@link IType types}
	 * @return the {@link Set} of known {@link IService} with a compatible receiver {@link IType types}
	 */
	public Set<IService<?>> getServices(String qualifiedName, Set<IType> receiverTypes) {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		final Module module = getModule(qualifiedName);
		if (module == null && !qualifiedNameServices.containsKey(qualifiedName)) {
			resolveClass(qualifiedName);
		}

		final Set<IService<?>> storedServices = new LinkedHashSet<IService<?>>();
		for (Entry<String, Set<IService<?>>> entry : qualifiedNameServices.getOrDefault(qualifiedName,
				new LinkedHashMap<>()).entrySet()) {
			storedServices.addAll(entry.getValue());
		}
		for (IType type : receiverTypes) {
			if (type != null) {
				for (IService<?> service : storedServices) {
					if (service.getParameterTypes(aqlEnvironment).get(0).isAssignableFrom(type)) {
						result.add(service);
					}
				}
			}
		}

		return result;
	}

	/**
	 * Resolves the {@link Class} with the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the resolved {@link Class}
	 */
	private Class<?> resolveClass(String qualifiedName) {
		Class<?> res = null;

		if (this.resolver != null) {
			try {
				res = resolver.resolveClass(qualifiedName);
				final Map<String, Set<IService<?>>> servicesMap = new LinkedHashMap<>();
				qualifiedNameServices.put(qualifiedName, servicesMap);
				for (IService<?> service : ServiceUtils.getServices(aqlEnvironment, res)) {
					final Set<IService<?>> services = servicesMap.computeIfAbsent(service.getName(),
							key -> new LinkedHashSet<IService<?>>());
					services.add(service);
				}
			} catch (ClassNotFoundException e) {
				// the class doesn't exist
				res = null;
			}
		}

		return res;
	}

	@Override
	public void registerModule(String qualifiedName, Module module) {
		if (this.qualifiedNameToModule.containsKey(qualifiedName)) {
			unregisterModule(qualifiedName);
		}
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

		final Map<String, Set<IService<?>>> servicesMap = new LinkedHashMap<>();
		qualifiedNameServices.put(qualifiedName, servicesMap);
		for (ModuleElement element : module.getModuleElements()) {
			if (element instanceof Template) {
				String name = ((Template)element).getName();
				servicesMap.computeIfAbsent(name, key -> new LinkedHashSet<>()).add(new TemplateService(this,
						(Template)element));
			} else if (element instanceof Query) {
				String name = ((Query)element).getName();
				servicesMap.computeIfAbsent(name, key -> new LinkedHashSet<>()).add(new QueryService(this,
						(Query)element));
			}
		}
	}

	@Override
	public void unregisterModule(String qualifiedName) {
		Objects.requireNonNull(qualifiedName);

		Module moduleToUnregister = this.qualifiedNameToModule.get(qualifiedName);

		this.qualifiedNameToModule.remove(qualifiedName);
		this.moduleToQualifiedName.remove(moduleToUnregister);
		this.moduleExtends.remove(qualifiedName);
		this.moduleImports.remove(qualifiedName);
		this.qualifiedNameServices.remove(qualifiedName);

		if (moduleToUnregister != null) {
			for (Metamodel metamodel : moduleToUnregister.getMetamodels()) {
				EPackage ePackage = metamodel.getReferencedPackage();
				// Only remove the EPackage if none of the other modules of the environment also registered
				// it.
				if (this.moduleToQualifiedName.keySet().stream().noneMatch(otherModule -> !moduleToUnregister
						.equals(otherModule) && !otherModule.getMetamodels().stream().map(
								Metamodel::getReferencedPackage).anyMatch(
										otherEPackage -> otherEPackage == ePackage))) {
					this.aqlEnvironment.removeEPackage(ePackage);
				}
			}
		}
	}

	@Override
	public IQueryEnvironment getQueryEnvironment() {
		return aqlEnvironment;
	}

	@Override
	public boolean hasQualifiedName(String qualifiedName) {
		return qualifiedNameServices.containsKey(qualifiedName) || resolveModule(qualifiedName) != null
				|| resolveClass(qualifiedName) != null;
	}

	@Override
	public Module getModule(String qualifiedName) {
		final Module res;

		final Module cachedModule = qualifiedNameToModule.get(qualifiedName);
		if (cachedModule != null) {
			res = cachedModule;
		} else {
			res = resolveModule(qualifiedName);
		}

		return res;
	}

	@Override
	public Module getModule(URL url) {
		final Module res;

		final String qualifiedName = resolver.getQualifierName(url);
		if (qualifiedName != null) {
			res = getModule(qualifiedName);
		} else {
			res = null;
		}

		return res;
	}

	@Override
	public Set<String> getAvailableQualifiedNames() {
		return resolver.getAvailableQualifiedNames();
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

	@Override
	public IQualifiedNameResolver getModuleResolver() {
		return this.resolver;
	}

	@Override
	public void setModuleResolver(IQualifiedNameResolver nameResolver) {
		this.resolver = nameResolver;
	}

	@Override
	public void setEvaluator(AcceleoEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	@Override
	public AcceleoEvaluator getEvaluator() {
		return evaluator;
	}

}
