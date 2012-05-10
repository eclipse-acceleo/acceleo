/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.core.runtime.content.IContentDescriber;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;

/**
 * The Acceleo resource factory registry ensure the support for xmi/binary resources in stand alone.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoResourceFactoryRegistry extends ResourceFactoryRegistryImpl {

	/**
	 * The delegate.
	 */
	private Resource.Factory.Registry delegate;

	/**
	 * The constructor.
	 */
	public AcceleoResourceFactoryRegistry() {
		delegate = new ResourceFactoryRegistryImpl();

		// We initialize our available informations with the values from the global registry.
		this.contentTypeIdentifierToFactoryMap.putAll(Resource.Factory.Registry.INSTANCE
				.getContentTypeToFactoryMap());
		this.extensionToFactoryMap.putAll(Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap());
		this.protocolToFactoryMap.putAll(Resource.Factory.Registry.INSTANCE.getProtocolToFactoryMap());
	}

	/**
	 * The constructor.
	 * 
	 * @param delegate
	 *            The resource factory registry to which we will delegate most of the operations.
	 */
	public AcceleoResourceFactoryRegistry(Resource.Factory.Registry delegate) {
		this.delegate = delegate;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl#getFactory(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Factory getFactory(URI uri) {
		Factory factory = delegatedGetFactory(uri, null);
		if (factory != null) {
			return factory;
		}
		return delegate.getFactory(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl#getFactory(org.eclipse.emf.common.util.URI,
	 *      java.lang.String)
	 */
	@Override
	public Factory getFactory(URI uri, String contentType) {
		Factory factory = delegatedGetFactory(uri, contentType);
		if (factory != null) {
			return factory;
		}
		return delegate.getFactory(uri, contentType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl#getProtocolToFactoryMap()
	 */
	@Override
	public Map<String, Object> getProtocolToFactoryMap() {
		if (!this.protocolToFactoryMap.isEmpty()) {
			return this.protocolToFactoryMap;
		}
		return delegate.getProtocolToFactoryMap();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl#getExtensionToFactoryMap()
	 */
	@Override
	public Map<String, Object> getExtensionToFactoryMap() {
		if (!this.extensionToFactoryMap.isEmpty()) {
			return this.extensionToFactoryMap;
		}
		return delegate.getExtensionToFactoryMap();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl#getContentTypeToFactoryMap()
	 */
	@Override
	public Map<String, Object> getContentTypeToFactoryMap() {
		if (!this.contentTypeIdentifierToFactoryMap.isEmpty()) {
			return this.contentTypeIdentifierToFactoryMap;
		}
		return delegate.getContentTypeToFactoryMap();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl#delegatedGetFactory(org.eclipse.emf.common.util.URI,
	 *      java.lang.String)
	 */
	@Override
	protected Factory delegatedGetFactory(URI uri, String contentTypeIdentifier) {
		Factory factory = computeFactory(uri, contentTypeIdentifier);

		// If we didn't succeed in loading one of Acceleo factories, we will delegate the problem back to EMF
		if (factory == null) {
			factory = super.delegatedGetFactory(uri, contentTypeIdentifier);
		}
		return factory;
	}

	/**
	 * Computes the factory for the given uri and content type identifier.
	 * 
	 * @param uri
	 *            The uri
	 * @param contentTypeIdentifier
	 *            The content type identifier
	 * @return The factory for the given uri and content type identifier
	 */
	private Factory computeFactory(URI uri, String contentTypeIdentifier) {
		Factory factory = null;

		// Not an emtl file ? let EMF handle it
		if (!IAcceleoConstants.EMTL_FILE_EXTENSION.equals(uri.fileExtension())) {
			return factory;
		}

		// Unknown content and uri of an emtl file.
		String path = uri.toFileString();

		if (path == null) {
			path = uri.toString();
			if (path.startsWith("file:")) { //$NON-NLS-1$
				path = path.substring("file:".length()); //$NON-NLS-1$
			}
		}

		if (path != null) {
			path = URI.decode(path);

			InputStream stream = null;
			InputStream containingStream = null;
			try {
				String jarPrefix = "jar:file:/"; //$NON-NLS-1$
				String os = System.getProperty("os.name").toLowerCase(); //$NON-NLS-1$
				if (os.indexOf("win") == -1) { //$NON-NLS-1$
					// We are not on windows, let's assume that we are on a unix based system
					jarPrefix = "jar:file:"; //$NON-NLS-1$
				}
				String jarSeparator = "!/"; //$NON-NLS-1$

				if (path.startsWith(jarPrefix) && path.contains(jarSeparator)) {
					String jarPath = path.substring(jarPrefix.length(), path.indexOf(jarSeparator));
					try {
						JarFile jar = new JarFile(jarPath);
						ZipEntry entry = jar.getEntry(path.substring(path.indexOf(jarSeparator)
								+ jarSeparator.length()));
						if (entry != null) {
							stream = jar.getInputStream(entry);
						}
					} catch (IOException e) {
						// do not log, the jar does not exists
					}

				} else {
					File file = new File(path);
					if (file.exists()) {
						stream = new FileInputStream(file);
					}
				}

				if ((contentTypeIdentifier == null || ContentHandler.UNSPECIFIED_CONTENT_TYPE
						.equals(contentTypeIdentifier))
						&& stream != null) {

					final int bufferSize = 1024;
					containingStream = new LazyInputStream(stream, bufferSize);

					EMtlBinaryResourceContentDescriber binaryContentDescriber = new EMtlBinaryResourceContentDescriber();
					int describe = binaryContentDescriber.describe(containingStream, null);

					if (describe == IContentDescriber.VALID) {
						factory = new EMtlBinaryResourceFactoryImpl();
					} else {
						factory = new EMtlResourceFactoryImpl();
					}

				}
			} catch (FileNotFoundException e) {
				AcceleoCommonPlugin.log(e, false);
			} catch (IOException e) {
				AcceleoCommonPlugin.log(e, false);
			} finally {
				try {
					if (containingStream != null) {
						containingStream.close();
					}
				} catch (IOException e) {
					AcceleoCommonPlugin.log(e, false);
				}
				try {
					if (stream != null) {
						stream.close();
					}
				} catch (IOException e) {
					AcceleoCommonPlugin.log(e, false);
				}
			}
		}
		if (factory == null) {
			if (IAcceleoConstants.BINARY_CONTENT_TYPE.equals(contentTypeIdentifier)) {
				factory = new EMtlBinaryResourceFactoryImpl();
			} else if (IAcceleoConstants.XMI_CONTENT_TYPE.equals(contentTypeIdentifier)) {
				factory = new EMtlResourceFactoryImpl();
			}
		}

		return factory;
	}
}
