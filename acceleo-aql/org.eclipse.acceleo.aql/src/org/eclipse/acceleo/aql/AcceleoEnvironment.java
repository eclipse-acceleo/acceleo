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

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.evaluation.AbstractModuleElementService;
import org.eclipse.acceleo.aql.evaluation.AcceleoCallStack;
import org.eclipse.acceleo.aql.evaluation.AcceleoQueryEnvironment;
import org.eclipse.acceleo.aql.evaluation.QueryService;
import org.eclipse.acceleo.aql.evaluation.TemplateService;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
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
	private final Multimap<String, String> moduleImports;

	/** Keeps track of the services each module qualified name provides, mapped to their names. */
	private Map<String, SetMultimap<String, AbstractModuleElementService>> moduleServices;

	/** The AQL environment that will be used to evaluate aql expressions from this Acceleo context. */
	private IQueryEnvironment aqlEnvironment;

	/* FIXME This is not functional. We need to be able to push/pop variable values during the evaluation. */
	// FIXME this should not be part of the AcceleoEvaluationEnvironment. the life spawn of a variable is
	// shorter than the AcceleoEvaluationEnvironment
	/** Keeps track of the available variables. */
	private Map<String, Object> variables;

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
	private Deque<AcceleoCallStack> callStacks;

	/**
	 * Initializes an environment for acceleo evaluations.
	 */
	public AcceleoEnvironment() {
		this.qualifiedNameToModule = new LinkedHashMap<>();
		this.moduleToQualifiedName = new LinkedHashMap<>();
		this.moduleExtends = new LinkedHashMap<>();
		this.moduleImports = LinkedListMultimap.create();
		this.moduleServices = new LinkedHashMap<>();
		this.variables = new LinkedHashMap<>();
		this.callStacks = new ArrayDeque<>();

		this.aqlEnvironment = new AcceleoQueryEnvironment(new EPackageProvider(), this);
		/* FIXME we need a cross reference provider, and we need to make it configurable */
		org.eclipse.acceleo.query.runtime.Query.configureEnvironment(aqlEnvironment, null, null);
	}

	/* FIXME not functional, would need to be a stack? Probably not the right place either. */
	/**
	 * Binds a value to a variable name.
	 * 
	 * @param name
	 *            Name of the binding.
	 * @param value
	 *            Value of that binding.
	 */
	public void addVariable(String name, Object value) {
		variables.put(name, value);
	}

	/* FIXME this returns the map itself and not a copy, should it be exposed? */
	/**
	 * Returns all of the variables known to this Acceleo environment.
	 * 
	 * @return All of the variables known to this Acceleo environment.
	 */
	public Map<String, Object> getVariables() {
		return variables;
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.IAcceleoEnvironment#popStack(java.lang.String)
	 */
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.IAcceleoEnvironment#getModuleQualifiedName(org.eclipse.acceleo.Module)
	 */
	public String getModuleQualifiedName(Module module) {
		return moduleToQualifiedName.get(module);
	}

	@Override
	public String getExtend(String qualifiedName) {
		return moduleExtends.get(qualifiedName);
	}

	@Override
	public Collection<String> getImports(String qualifiedName) {
		return moduleImports.get(qualifiedName);
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
		return moduleServices.get(moduleQualifiedName).get(moduleElementName);
	}

	/**
	 * Registers the given module against this environment.
	 * 
	 * @param qualifiedName
	 *            The qualified name of this module.
	 * @param module
	 *            The module to register.
	 */
	public void registerModule(String qualifiedName, Module module) {
		qualifiedNameToModule.put(qualifiedName, module);
		moduleToQualifiedName.put(module, qualifiedName);
		if (module.getExtends() != null && module.getExtends().getQualifiedName() != null) {
			moduleExtends.put(qualifiedName, module.getExtends().getQualifiedName());
		}
		for (Import imp : module.getImports()) {
			final ModuleReference moduleRef = imp.getModule();
			if (moduleRef != null && moduleRef.getQualifiedName() != null) {
				moduleImports.put(qualifiedName, moduleRef.getQualifiedName());
			}
		}

		for (Metamodel metamodel : module.getMetamodels()) {
			if (!(metamodel instanceof ErrorMetamodel)) {
				EPackage referredPackage = metamodel.getReferencedPackage();
				aqlEnvironment.registerEPackage(referredPackage);
				ServiceUtils.registerServices(aqlEnvironment, ServiceUtils.getServices(referredPackage));
			}
		}

		SetMultimap<String, AbstractModuleElementService> services = moduleServices.get(module);
		if (services == null) {
			services = LinkedHashMultimap.create();
			moduleServices.put(qualifiedName, services);
		}
		for (ModuleElement element : module.getModuleElements()) {
			if (element instanceof Template) {
				String name = ((Template)element).getName();
				services.put(name, new TemplateService(this, (Template)element));
			} else if (element instanceof Query) {
				String name = ((Query)element).getName();
				services.put(name, new QueryService(this, (Query)element));
			}
		}
	}

	/**
	 * Returns the AQL environment that needs to be used when evaluating AQL expressions from within this
	 * Acceleo context.
	 * 
	 * @return The AQL environment configured with this Acceleo context.
	 */
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

}
