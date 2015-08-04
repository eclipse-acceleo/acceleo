/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;

//@formatter:off
@ServiceProvider(
	value = "Services available for Resources and URIs"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class ResourceServices extends AbstractServiceProvider {
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider#getService(java.lang.reflect.Method)
	 */
	@Override
	protected IService getService(Method method) {
		if ("getContents".equals(method.getName())) {
			return new GetContentsService(method, this);
		}
		return new Service(method, this);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the Resource containing the given EObject. This service is equivalent to a direct call " +
				"to EObject#eResource().",
	    params = {
			@Param(name = "eObject", value = "The EObject")
		},
		result = "The Resource containing the given EObject."
	)
	// @formatter:on
	public Resource eResource(EObject eObject) {
		return eObject.eResource();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the URI of the given Resource. This service is equivalent to a direct call to Resource#getURI()",
	    params = {
			@Param(name = "resource", value = "The Resource which URI we seek")
		},
		result = "The URI of the given Resource."
	)
	// @formatter:on
	public URI getURI(Resource resource) {
		return resource.getURI();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the direct content of the given Resource. This service is equivalent to a direct call to Resource#getContents()",
	    params = {
			@Param(name = "resource", value = "The Resource which contents we seek")
		},
		result = "The direct content of the given Resource."
	)
	// @formatter:on
	public List<EObject> getContents(Resource resource) {
		return new ArrayList<EObject>(resource.getContents());
	}

	// @formatter:off
	@Documentation(
		value = "Returns the EObjects of the given type from the direct content of the given Resource.",
	    params = {
			@Param(name = "resource", value = "The Resource which filtered contents we seek"),
			@Param(name = "type", value = "The type that the returned EObjects must match")
		},
		result = "The EObjects from the direct content of the given Resource that match the given type."
	)
	// @formatter:on
	public List<EObject> getContents(Resource resource, final EClass type) {
		return Lists.newArrayList(Iterables.filter(resource.getContents(), new Predicate<EObject>() {
			@Override
			public boolean apply(EObject input) {
				return type.isSuperTypeOf(input.eClass());
			}
		}));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the last segment of the given URI. This service is equivalent to a direct call to URI#lastSegment()",
	    params = {
			@Param(name = "uri", value = "The URI")
		},
		result = "The last segment of the given URI."
	)
	// @formatter:on
	public String lastSegment(URI uri) {
		return uri.lastSegment();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the extension of the file referred to by the given URI. This service is equivalent " +
				"to a direct call to URI#fileExtension()",
	    params = {
			@Param(name = "uri", value = "The URI")
		},
		result = "The extension of the file referred to by the given URI."
	)
	// @formatter:on
	public String fileExtension(URI uri) {
		return uri.fileExtension();
	}

	// @formatter:off
	@Documentation(
		value = "Returns <code>true</code> if the given URI is a platform resource URI. This service is equivalent " +
				"to a direct call to URI#isPlatformResource()",
	    params = {
			@Param(name = "uri", value = "The URI")
		},
		result = "<code>true</code> if the given URI is a platform resource URI, <code>false</code> otherwise."
	)
	// @formatter:on
	public Boolean isPlatformResource(URI uri) {
		return uri.isPlatformResource();
	}

	// @formatter:off
	@Documentation(
		value = "Returns <code>true</code> if the given URI is a platform plugin URI. This service is equivalent " +
				"to a direct call to URI#isPlatformPlugin()",
	    params = {
			@Param(name = "uri", value = "The URI")
		},
		result = "<code>true</code> if the given URI is a platform plugin URI, <code>false</code> otherwise."
	)
	// @formatter:on
	public Boolean isPlatformPlugin(URI uri) {
		return uri.isPlatformPlugin();
	}

	/**
	 * Resource#getContents {@link IService}.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private static final class GetContentsService extends FilterService {
		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private GetContentsService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
				List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() == EcorePackage.eINSTANCE.getEResource()) {
				if (argTypes.size() == 1) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							EcorePackage.eINSTANCE.getEObject())));
				} else if (argTypes.size() == 2) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							((EClassifierLiteralType)argTypes.get(1)).getType())));
				}
			} else {
				result.add(new SequenceType(queryEnvironment, services.nothing(
						"Can only call getContents() on Resources, not on %s", argTypes.get(0))));
			}

			return result;
		}
	}
}
