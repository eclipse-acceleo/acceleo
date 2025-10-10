/*******************************************************************************
 * Copyright (c) 2015, 2025 Obeo.
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
 * An implementation that {@link IQualifiedNameLookupEngine#pushContext(String) pushes extends},
 * {@link IQualifiedNameLookupEngine#pushImportsContext(String, String) pushes imports},
 * {@link IQualifiedNameLookupEngine#popContext(String) pops} {@link IQualifiedNameLookupEngine}'s
 * {@link IQualifiedNameLookupEngine#getCurrentContext() current context}.
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
		final String startQualifiedName = lookupEngine.getCurrentContext().getStartingQualifiedName();
		if (startQualifiedName != contextQualifiedName) {
			// This service we are calling is not in the qualified name currently at the top of the stack.
			if (lookupEngine.isInExtends(startQualifiedName, contextQualifiedName)) {
				// If it is in our current module's hierarchy, we need to push the context qualified name of
				// this service on the stack
				lookupEngine.pushContext(contextQualifiedName);
			} else {
				// We can only be here if the service we're calling is in imports from this service context
				// qualified name or their respective hierarchy. We need to change the environment current
				// namespace to said import.
				final String currentQualifiedName = lookupEngine.getCurrentContext().peek();
				final Optional<String> importedQualifiedName = lookupEngine.getImports(currentQualifiedName)
						.stream().filter(imported -> lookupEngine.isInExtends(imported, contextQualifiedName))
						.findFirst();
				if (importedQualifiedName.isPresent()) {
					lookupEngine.pushImportsContext(importedQualifiedName.get(), contextQualifiedName);
				} else {
					throw new IllegalStateException("The called service " + getLongSignature()
							+ " was not imported nor extended from the current context: "
							+ currentQualifiedName);
				}
			}
		} else {
			// This service is in the qualified name currently at the top of the stack. But we push the
			// context qualified name again so we can pop it after the call.
			lookupEngine.pushContext(contextQualifiedName);
		}
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
