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

import java.util.List;
import java.util.Optional;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.impl.AbstractService;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Abstract implementation of a service that can wrap an Acceleo module element for AQL uses.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public abstract class AbstractModuleElementService extends AbstractService {

	/** The current evaluation environment. */
	private final AcceleoEnvironment env;

	/**
	 * Constructor.
	 * 
	 * @param moduleElement
	 *            the (non-{@code null}) {@link ModuleElement} wrapped by this service.
	 * @param env
	 *            The current evaluation environment.
	 */
	public AbstractModuleElementService(ModuleElement moduleElement, AcceleoEnvironment env) {
		super(moduleElement);
		this.env = env;
	}

	/**
	 * Returns the wrapped module element.
	 * 
	 * @return The wrapped module element.
	 */
	public abstract ModuleElement getModuleElement();

	/**
	 * Returns the underlying element's visibility if any.
	 * 
	 * @return The underlying element's visibility if any.
	 */
	public abstract VisibilityKind getVisibility();

	/**
	 * Gets the {@link AcceleoEnvironment}.
	 * 
	 * @return the {@link AcceleoEnvironment}
	 */
	protected AcceleoEnvironment getEnv() {
		return env;
	}

	/**
	 * Gets the module qualified name.
	 * 
	 * @return the module qualified name
	 */
	public String getModuleQualifiedName() {
		return env.getModuleQualifiedName((Module)getModuleElement().eContainer());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getShortSignature()
	 */
	@Override
	public String getShortSignature() {
		final List<IType> parameterTypes = getParameterTypes(getEnv().getQueryEnvironment());
		final IType[] argumentTypes = parameterTypes.toArray(new IType[parameterTypes.size()]);

		return serviceShortSignature(argumentTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getLongSignature()
	 */
	@Override
	public String getLongSignature() {
		String namespace = getNamespace();
		if (namespace != null) {
			return namespace + "::" + getShortSignature();
		}
		return getShortSignature();
	}

	/**
	 * Returns the namespace of the underlying module element. This is used to recreate its qualified name.
	 * 
	 * @return The namespace of the underlying module element.
	 */
	private String getNamespace() {
		String result = null;
		Resource res = getModuleElement().eResource();
		if (res != null) {
			result = res.getURI().toString();
		} else {
			EObject container = getModuleElement().eContainer();
			while (!(container instanceof Module)) {
				container = container.eContainer();
			}

			if (container instanceof Module) {
				result = ((Module)container).getName();
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof AbstractModuleElementService
				&& getModuleElement() == ((AbstractModuleElementService)obj).getModuleElement();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getModuleElement().hashCode();
	}

	@Override
	public Object invoke(Object... arguments) throws AcceleoQueryEvaluationException {
		startInvoke();
		Object result = super.invoke(arguments);
		endInvoke();
		return result;
	}

	private void startInvoke() {
		Module newModule = (Module)getModuleElement().eContainer();
		String currentQualifiedName = getEnv().getCurrentStack().getStartingModuleQualifiedName();
		String newQualifiedName = getEnv().getModuleQualifiedName(newModule);
		if (currentQualifiedName != newQualifiedName) {
			// The module element we're calling is not from our current stack's tip.
			// If it is in our current module's hierarchy, we only need to push the new module element on the
			// stack.
			if (isInExtends(currentQualifiedName, newQualifiedName)) {
				getEnv().push(getModuleElement());
			} else {
				// We can only be here if the module we're calling is in our imports or their respective
				// hierarchy. We need to change the environment current namespace to said import.
				Optional<String> importedModule = getEnv().getImports(currentQualifiedName).stream().filter(
						imported -> isInExtends(imported, newQualifiedName)).findFirst();
				if (importedModule.isPresent()) {
					getEnv().pushImport(importedModule.get(), getModuleElement());
				} else {
					// FIXME log exception : we couldn't find the import from which this called service
					// originates
				}
			}
		} else {
			getEnv().push(getModuleElement());
		}
	}

	private boolean isInExtends(String start, String calleeQualifiedName) {
		String currentQualifiedName = start;
		while (currentQualifiedName != null) {
			if (currentQualifiedName.equals(calleeQualifiedName)) {
				return true;
			}
			currentQualifiedName = getEnv().getExtend(currentQualifiedName);
		}
		return false;
	}

	private void endInvoke() {
		getEnv().popStack(getModuleElement());
	}

}
