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

/**
 * {@link Resource} related services.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
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

	/**
	 * Returns the eResource containing the given EObject. Equivalent to a direct call to
	 * {@link EObject#eResource()}.
	 * 
	 * @param eObject
	 *            the EObject.
	 * @return the eResource containing the given EObject.
	 */
	public Resource eResource(EObject eObject) {
		return eObject.eResource();
	}

	/**
	 * Returns the URI of the given Resource. Equivalent to a direct call to {@link Resource#getURI()}.
	 * 
	 * @param resource
	 *            The resource which URI we seek.
	 * @return The URI of the given Resource.
	 */
	public URI getURI(Resource resource) {
		return resource.getURI();
	}

	/**
	 * Returns the direct content of the given Resource. Equivalent to a direct call to
	 * {@link Resource#getContents()}.
	 * 
	 * @param resource
	 *            The resource of which to retrieve the direct contents.
	 * @return The direct content of the given Resource.
	 */
	public List<EObject> getContents(Resource resource) {
		return new ArrayList<EObject>(resource.getContents());
	}

	/**
	 * Returns the objects of the given type from the direct content of the given Resource.
	 * 
	 * @param resource
	 *            The resource from which we'll filter the content.
	 * @param type
	 *            the type that the returned objects must be.
	 * @return The objects that correspond to the given type from the direct content of the given Resource.
	 */
	public List<EObject> getContents(Resource resource, final EClass type) {
		return Lists.newArrayList(Iterables.filter(resource.getContents(), new Predicate<EObject>() {
			@Override
			public boolean apply(EObject input) {
				return type.isSuperTypeOf(input.eClass());
			}
		}));
	}

	/**
	 * Returns the last segment of the given URI. Equivalent to a direct call to {@link URI#lastSegment()}.
	 * 
	 * @param uri
	 *            The URI.
	 * @return The last segment of the given URI.
	 */
	public String lastSegment(URI uri) {
		return uri.lastSegment();
	}

	/**
	 * Returns the extension of the file referred to by the given URI. Equivalent to a direct call to
	 * {@link URI#fileExtension()}.
	 * 
	 * @param uri
	 *            The URI.
	 * @return The extension of the file referred to by the given URI.
	 */
	public String fileExtension(URI uri) {
		return uri.fileExtension();
	}

	/**
	 * Returns <code>true</code> if the given URI is a platform resource URI. Equivalent to a direct call to
	 * {@link URI#isPlatformResource()}.
	 * 
	 * @param uri
	 *            The URI.
	 * @return <code>true</code> if the given URI is a platform resource URI, <code>false</code> otherwise.
	 */
	public Boolean isPlatformResource(URI uri) {
		return uri.isPlatformResource();
	}

	/**
	 * Returns <code>true</code> if the given URI is a platform plugin URI. Equivalent to a direct call to
	 * {@link URI#isPlatformPlugin()}.
	 * 
	 * @param uri
	 *            The URI.
	 * @return <code>true</code> if the given URI is a platform plugin URI, <code>false</code> otherwise.
	 */
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