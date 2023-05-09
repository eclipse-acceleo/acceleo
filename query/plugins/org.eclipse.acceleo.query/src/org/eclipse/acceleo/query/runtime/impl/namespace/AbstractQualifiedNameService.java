/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.util.Optional;

import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.impl.AbstractService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;

/**
 * An implementation that {@link IQualifiedNameLookupEngine#pushContext(String) push extends},
 * {@link IQualifiedNameLookupEngine#pushContext(String) push imports},
 * {@link IQualifiedNameLookupEngine#popContext(String) pop}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractQualifiedNameService<O> extends AbstractService<O> {

	/**
	 * The qualified name containing this service.
	 */
	private final String contextQualifiedName;

	/**
	 * The {@link IQualifiedNameLookupEngine}.
	 */
	private IQualifiedNameLookupEngine lookupEngine;

	/**
	 * Constructor.
	 * 
	 * @param serviceOrigin
	 *            the (maybe {@code null}) {@link Object origin} of this service
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 * @param contextQualifiedName
	 *            the qualified name containing this service
	 */
	protected AbstractQualifiedNameService(O serviceOrigin, IQualifiedNameLookupEngine lookupEngine,
			String contextQualifiedName) {
		super(serviceOrigin);
		this.lookupEngine = lookupEngine;
		this.contextQualifiedName = contextQualifiedName;
	}

	@Override
	public Object invoke(Object... arguments) throws AcceleoQueryEvaluationException {
		final Object result;

		startInvoke();
		try {
			result = super.invoke(arguments);
		} finally {
			endInvoke();
		}
		return result;
	}

	private void startInvoke() {
		String currentQualifiedName = lookupEngine.getCurrentContext().getStartingQualifiedName();
		if (currentQualifiedName != contextQualifiedName) {
			// The module element we're calling is not from our current stack's tip.
			// If it is in our current module's hierarchy, we only need to push the new module element on the
			// stack.
			if (isInExtends(currentQualifiedName, contextQualifiedName)) {
				lookupEngine.pushContext(contextQualifiedName);
			} else {
				// We can only be here if the module we're calling is in our imports or their respective
				// hierarchy. We need to change the environment current namespace to said import.
				final Optional<String> importedModule = lookupEngine.getImports(currentQualifiedName).stream()
						.filter(imported -> isInExtends(imported, contextQualifiedName)).findFirst();
				if (importedModule.isPresent()) {
					lookupEngine.pushImportsContext(importedModule.get(), contextQualifiedName);
				} else {
					throw new IllegalStateException("The called service " + getLongSignature()
							+ " was not imported nor extended from the current context: "
							+ currentQualifiedName);
				}
			}
		} else {
			lookupEngine.pushContext(contextQualifiedName);
		}
	}

	private boolean isInExtends(String start, String calleeQualifiedName) {
		String currentQualifiedName = start;
		while (currentQualifiedName != null) {
			if (currentQualifiedName.equals(calleeQualifiedName)) {
				return true;
			}
			currentQualifiedName = lookupEngine.getExtend(currentQualifiedName);
		}
		return false;
	}

	private void endInvoke() {
		lookupEngine.popContext(contextQualifiedName);
	}

	/**
	 * Gets the context qualified name.
	 * 
	 * @return the context qualified name
	 */
	protected String getContextQualifiedName() {
		return contextQualifiedName;
	}

	/**
	 * Gets the {@link IQualifiedNameLookupEngine}.
	 * 
	 * @return the {@link IQualifiedNameLookupEngine}
	 */
	protected IQualifiedNameLookupEngine getLookupEngine() {
		return lookupEngine;
	}
}
