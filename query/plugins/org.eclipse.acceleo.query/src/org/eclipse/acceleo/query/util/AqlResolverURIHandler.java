/*******************************************************************************
 *  Copyright (c) 2025 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIHandler;

/**
 * An {@link URIHandler} that can read resources via an {@link IQualifiedNameResolver}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AqlResolverURIHandler implements URIHandler {

	/**
	 * Not implemented message.
	 */
	private static final String NOT_IMPLEMENTED = "Not implemented.";

	/**
	 * AQL resolver protocol.
	 */
	private static final String AQL_RESOLVE_PROTOCOL = "aqlresolve";

	/**
	 * The resource host.
	 */
	private static final String RESOURCE_HOST = "resource";

	/**
	 * The {@link IQualifiedNameResolver} used to {@link #createInputStream(URI, Map) create InputStream}.
	 */
	private final IQualifiedNameResolver resolver;

	/**
	 * Constructor.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 */
	public AqlResolverURIHandler(IQualifiedNameResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public boolean canHandle(URI uri) {
		return isAqlResolveURI(uri);
	}

	@Override
	public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
		final InputStream res;

		if (isResourceURI(uri)) {
			res = resolver.getInputStream(getResourceName(uri));
		} else {
			res = null;
		}

		if (res == null) {
			throw new IOException("No rersource " + uri.toString());
		}

		return res;
	}

	@Override
	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		throw new UnsupportedOperationException(NOT_IMPLEMENTED);
	}

	@Override
	public void delete(URI uri, Map<?, ?> options) throws IOException {
		throw new UnsupportedOperationException(NOT_IMPLEMENTED);
	}

	@Override
	public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
		return null;
	}

	@Override
	public boolean exists(URI uri, Map<?, ?> options) {
		boolean res;

		try (InputStream is = createInputStream(uri, null)) {
			res = is != null;
		} catch (IOException e) {
			res = false;
		}

		return res;
	}

	@Override
	public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
		return null;
	}

	@Override
	public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException {
		throw new UnsupportedOperationException(NOT_IMPLEMENTED);
	}

	/**
	 * Gets the resource name from the given {@link URI}.
	 * 
	 * @param uri
	 *            the {@link URI} with {@link #AQL_RESOLVE_PROTOCOL}.
	 * @return the resource name from the given {@link URI} if any, <code>null</code> otherwise
	 */
	public static String getResourceName(URI uri) {
		final String res;

		if (isAqlResolveURI(uri)) {
			final String path = uri.path();
			if (path != null && !path.isEmpty()) {
				res = path.substring(1);
			} else {
				res = null;
			}
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Tells if the given {@link URI} has the {@link #AQL_RESOLVE_PROTOCOL}.
	 * 
	 * @param uri
	 *            the {@link URI} to check
	 * @return <code>true</code> if the given {@link URI} has the {@link #AQL_RESOLVE_PROTOCOL},
	 *         <code>false</code> otherwise
	 */
	private static boolean isAqlResolveURI(URI uri) {
		final boolean res;

		if (AQL_RESOLVE_PROTOCOL.equals(uri.scheme().toLowerCase())) {
			final String host = uri.host();
			if (host != null) {
				res = isResourceURI(uri);
			} else {
				res = false;
			}
		} else {
			res = false;
		}
		return res;
	}

	/**
	 * Tells if the given {@link URI}'s {@link URI#host() host} is {@link #RESOURCE_HOST}.
	 * 
	 * @return <code>true</code> if the given {@link URI}'s {@link URI#host() host} is {@link #RESOURCE_HOST},
	 *         <code>false</code> otherwise
	 */
	private static boolean isResourceURI(URI uri) {
		return RESOURCE_HOST.equals(uri.host().toLowerCase());
	}

	/**
	 * Creates an AQL resolve {@link URI} for the given resource name.
	 * 
	 * @param resourceName
	 *            the resource name
	 * @return the created AQL resolve {@link URI} for the given resource name
	 */
	public static URI createAqlResourceResolverURI(String resourceName) {
		return URI.createURI(AQL_RESOLVE_PROTOCOL + "://" + RESOURCE_HOST + "/" + resourceName);
	}

}
