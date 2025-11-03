/*******************************************************************************
 * Copyright (c) 2016, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.runtime.impl.NullValue;
import org.eclipse.acceleo.query.runtime.impl.namespace.AbstractQualifiedNameService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Abstract implementation of a service that can wrap an Acceleo module element for AQL uses.
 * 
 * @param <O>
 *            the kind of {@link ModuleElement}
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public abstract class AbstractModuleElementService<O extends ModuleElement> extends AbstractQualifiedNameService<O> {

	/**
	 * The {@link Visibility}.
	 */
	private final Visibility visibility;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	private final AcceleoEvaluator evaluator;

	/**
	 * The short signature.
	 */
	private final String shortSignature;

	/**
	 * Constructor.
	 * 
	 * @param moduleElement
	 *            the (non-{@code null}) {@link ModuleElement} wrapped by this service.
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 * @param contextQualifiedName
	 *            the qualified name containing this service
	 */
	public AbstractModuleElementService(O moduleElement, AcceleoEvaluator evaluator,
			IQualifiedNameLookupEngine lookupEngine, String contextQualifiedName) {
		super(moduleElement, lookupEngine, contextQualifiedName);
		this.visibility = getVisibility(moduleElement);
		this.evaluator = evaluator;

		if (lookupEngine != null) {
			final List<Set<IType>> parameterTypes = getParameterTypes(lookupEngine.getQueryEnvironment());
			final Object[] argumentTypes = parameterTypes.toArray(new Object[parameterTypes.size()]);
			this.shortSignature = serviceShortSignature(argumentTypes);
		} else {
			this.shortSignature = "";
		}
	}

	/**
	 * Gets the {@link Visibility} from the given {@link VisibilityKind}.
	 * 
	 * @param visibilityKind
	 *            the {@link VisibilityKind}
	 * @return the {@link Visibility} from the given {@link VisibilityKind}
	 */
	protected Visibility getVisibility(VisibilityKind visibilityKind) {
		final Visibility res;

		switch (visibilityKind) {
			case PRIVATE:
				res = Visibility.PRIVATE;
				break;
			case PROTECTED:
				res = Visibility.PROTECTED;
				break;
			case PUBLIC:
				res = Visibility.PUBLIC;
				break;

			default:
				res = Visibility.PUBLIC;
				break;
		}

		return res;
	}

	/**
	 * Gets the {@link AcceleoEvaluator}.
	 * 
	 * @return the {@link AcceleoEvaluator}
	 */
	protected AcceleoEvaluator getEvaluator() {
		return evaluator;
	}

	/**
	 * Gets the {@link Visibility} of the given {@link ModuleElement}.
	 * 
	 * @param moduleElement
	 *            the {@link ModuleElement}
	 * @return the {@link Visibility} of the given {@link ModuleElement}
	 */
	protected abstract Visibility getVisibility(O moduleElement);

	@Override
	public Visibility getVisibility() {
		return visibility;
	}

	@Override
	public String getShortSignature() {
		return shortSignature;
	}

	@Override
	public String getLongSignature() {
		String namespace = getContextQualifiedName();
		if (namespace != null) {
			return namespace + "::" + getShortSignature();
		}
		return getShortSignature();
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AbstractModuleElementService
				&& getOrigin() == ((AbstractModuleElementService<?>)obj).getOrigin();
	}

	@Override
	public int hashCode() {
		return getOrigin().hashCode();
	}

	/**
	 * Gets the variable value for the given {@link Variable} and value.
	 * 
	 * @param variable
	 *            the {@link Variable}
	 * @param value
	 *            the value
	 * @return the variable value for the given {@link Variable} and value
	 */
	protected Object getArgumentValue(Variable variable, Object value) {
		final Object res;

		if (value == null) {
			final Set<IType> types = AQLUtils.getTypes(getLookupEngine().getQueryEnvironment(),
					(TypeLiteral)variable.getTypeAql());
			res = new NullValue(types);
		} else {
			res = value;
		}

		return res;
	}

}
