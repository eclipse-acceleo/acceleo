/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.namespace;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.parser.CombineIterator;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.namespace.QualifiedNameValidationServices;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QualifiedNameAstValidator extends AstValidator {

	/**
	 * The is incompatible with message.
	 */
	private static final String IS_INCOMPATIBLE_WITH = " is incompatible with ";

	/**
	 * Constructor.
	 * 
	 * @param services
	 *            the {@link QualifiedNameValidationServices}
	 */
	public QualifiedNameAstValidator(QualifiedNameValidationServices services) {
		super(services);
	}

	@Override
	protected QualifiedNameValidationServices getServices() {
		return (QualifiedNameValidationServices)super.getServices();
	}

	/**
	 * Validates the return {@link IType} compatibility of overriding {@link IService}.
	 * 
	 * @param name
	 *            the {@link IService#getName() name}
	 * @param returnTypes
	 *            the possible return {@link IType}
	 * @param parameterTypes
	 *            the possible parameter {@link IType}
	 * @return the validation message if any probleme is present, <code>null</code> if everything is valid
	 */
	public String validateOverrideReturnType(String name, Set<IType> returnTypes,
			List<Set<IType>> parameterTypes) {
		final String res;

		IQualifiedNameQueryEnvironment queryEnvironment = getServices().getQueryEnvironment();
		final CombineIterator<IType> it = new CombineIterator<IType>(parameterTypes);
		final StringBuilder builder = new StringBuilder();
		while (it.hasNext()) {
			final List<IType> types = it.next();
			final IService<?> superService = queryEnvironment.getLookupEngine().superServiceLookup(name, types
					.toArray(new IType[types.size()]));
			if (superService != null) {
				Set<IType> superReturnTypes = superService.getType(queryEnvironment);
				final StringBuilder incompatibleTypeBuilder = new StringBuilder();
				for (IType superReturnType : superReturnTypes) {
					for (IType returnType : returnTypes) {
						if (!superReturnType.isAssignableFrom(returnType)) {
							incompatibleTypeBuilder.append("\t" + superReturnType + IS_INCOMPATIBLE_WITH
									+ returnType + "\n");
						}
					}
				}
				if (incompatibleTypeBuilder.length() != 0) {
					builder.append(superService.getLongSignature() + "\n");
					builder.append(incompatibleTypeBuilder.toString());
				}
			}
		}

		if (builder.length() != 0) {
			res = "Return type incompatible with overrided service:\n" + builder.substring(0, builder.length()
					- 1);
		} else {
			res = null;
		}

		return res;
	}

}
