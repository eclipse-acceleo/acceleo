/*******************************************************************************
 * Copyright (c) 2015, 2022 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.completion;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.Throw;
import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.emf.ecore.EClass;

/**
 * An {@link JavaMethodService} proposal.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class JavaMethodServiceCompletionProposal implements IServiceCompletionProposal {

	/**
	 * The current line separator which will be used by the tooling in order to compute the description.
	 */
	private static final String LS = System.getProperty("line.separator");

	/**
	 * The regular gap before the documentation of parameters, return values and exceptions.
	 */
	private static final String GAP = "        ";

	/**
	 * The proposed {@link JavaMethodService}.
	 */
	private final JavaMethodService service;

	/**
	 * Constructor.
	 * 
	 * @param service
	 *            the proposed {@link JavaMethodService}
	 */
	public JavaMethodServiceCompletionProposal(JavaMethodService service) {
		this.service = service;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getProposal()
	 */
	@Override
	public String getProposal() {
		final String res;

		if (service.getOrigin().getDeclaringClass() == CollectionServices.class) {
			res = service.getName() + "()";
		} else {
			res = AstBuilder.protectWithUnderscore(service.getName()) + "()";
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getCursorOffset()
	 */
	@Override
	public int getCursorOffset() {
		final int length = getProposal().length();
		if (service.getNumberOfParameters() == 1) {
			/*
			 * if we have only one parameter we return the offset: self.serviceCall()^
			 */
			return length;
		} else {
			/*
			 * if we more than one parameter we return the offset: self.serviceCall(^)
			 */
			return length - 1;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getObject()
	 */
	@Override
	public IService<Method> getObject() {
		return service;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getProposal();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getDescription()
	 */
	@Override
	public String getDescription() {
		StringBuffer buffer = new StringBuffer();

		Method method = this.service.getOrigin();
		if (method.isAnnotationPresent(Documentation.class)) {
			Documentation documentation = method.getAnnotation(Documentation.class);

			this.appendServiceJavadoc(buffer, documentation);
			this.appendParametersJavadoc(buffer, documentation);
			this.appendResultJavadoc(buffer, documentation);
			this.appendExceptionsJavadoc(buffer, documentation);
		} else {
			// No annotation, default behavior
			buffer.append(this.getServiceSignature(new ArrayList<String>()));
			buffer.append(LS);
		}

		return buffer.toString();
	}

	/**
	 * Appends the Javadoc of the service to the given buffer.
	 * 
	 * @param buffer
	 *            The buffer
	 * @param documentation
	 *            The documentation annotation of the service.
	 */
	private void appendServiceJavadoc(StringBuffer buffer, Documentation documentation) {

		List<String> parameterNames = new ArrayList<String>();
		Param[] params = documentation.params();
		if (params != null) {
			for (Param param : params) {
				parameterNames.add(param.name());
			}
		}

		buffer.append(this.getServiceSignature(parameterNames));
		buffer.append(LS);

		String value = documentation.value();
		buffer.append(LS);
		buffer.append(value).append(LS);
		buffer.append(LS);
	}

	/**
	 * Appends the Javadoc of the parameters to the given buffer.
	 * 
	 * @param buffer
	 *            The buffer
	 * @param documentation
	 *            The documentation annotation of the service
	 */
	private void appendParametersJavadoc(StringBuffer buffer, Documentation documentation) {
		Param[] params = documentation.params();
		if (params != null && params.length > 0) {
			for (Param param : params) {
				buffer.append("  @param ");
				buffer.append(param.name()).append(LS);
				buffer.append(GAP);
				buffer.append(param.value()).append(LS);
			}
			buffer.append(LS);
		}
	}

	/**
	 * Appends the Javadoc of the result to the given buffer.
	 * 
	 * @param buffer
	 *            The buffer
	 * @param documentation
	 *            The documentation annotation of the service
	 */
	private void appendResultJavadoc(StringBuffer buffer, Documentation documentation) {
		String result = documentation.result();
		if (result.length() > 0) {
			buffer.append("  @return").append(LS);
			buffer.append(GAP).append(result).append(LS);
			buffer.append(LS);
		}
	}

	/**
	 * Appends the Javadoc of the exceptions to the given buffer.
	 * 
	 * @param buffer
	 *            The buffer
	 * @param documentation
	 *            The documentation annotation of the service
	 */
	private void appendExceptionsJavadoc(StringBuffer buffer, Documentation documentation) {
		Throw[] exceptions = documentation.exceptions();
		if (exceptions != null && exceptions.length > 0) {
			for (Throw exception : exceptions) {
				buffer.append("  @throw ");
				buffer.append(exception.type().getCanonicalName()).append(LS);
				buffer.append(GAP).append(exception.value()).append(LS);
			}
			buffer.append(LS);
		}
	}

	/**
	 * Returns the signature of the service using the given list of parameter names.
	 * 
	 * @param parameterNames
	 *            The name of the parameters of the service.
	 * @return The signature of the service
	 */
	private StringBuffer getServiceSignature(List<String> parameterNames) {
		StringBuffer result = new StringBuffer();
		result.append(service.getName()).append('(');
		boolean first = true;

		Class<?>[] parameterTypes = service.getOrigin().getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i = i + 1) {
			Object argType = parameterTypes[i];
			if (!first) {
				result.append(", ");
			} else {
				first = false;
			}

			if (parameterNames.size() >= i + 1) {
				String paramName = parameterNames.get(i);
				if (paramName.trim().length() > 0) {
					result.append(paramName);
					result.append(": ");
				}
			}

			if (argType instanceof Class<?>) {
				result.append(((Class<?>)argType).getCanonicalName());
			} else if (argType instanceof EClass) {
				result.append("EClass=" + ((EClass)argType).getName());
			} else {
				// should not happen
				result.append("Object=" + argType.toString());
			}
		}
		result.append(')');

		Class<?> returnType = service.getOrigin().getReturnType();
		if (Void.class.equals(returnType)) {
			result.append(" = void");
		} else {
			result.append(" = ");
			result.append(returnType.getSimpleName());
		}
		return result;
	}
}
