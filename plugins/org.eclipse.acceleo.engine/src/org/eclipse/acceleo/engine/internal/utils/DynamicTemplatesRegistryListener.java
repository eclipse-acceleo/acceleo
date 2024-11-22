/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

/**
 * This listener will allow us to be aware of contribution changes against the dynamic templates extension
 * point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class DynamicTemplatesRegistryListener implements IRegistryEventListener {
	/** Name of the extension point to parse for template locations. */
	public static final String DYNAMIC_TEMPLATES_EXTENSION_POINT = "org.eclipse.acceleo.engine.dynamic.templates"; //$NON-NLS-1$

	/** Name of the extension point's templates tag "path" atribute. */
	private static final String DYNAMIC_TEMPLATES_ATTRIBUTE_PATH = "path"; //$NON-NLS-1$

	/** Name of the extension point's "templates" tag. */
	private static final String DYNAMIC_TEMPLATES_TAG_TEMPLATES = "templates"; //$NON-NLS-1$

	/** Name of the extension point's "generator" tag. */
	private static final String DYNAMIC_TEMPLATES_TAG_GENERATOR = "generator"; //$NON-NLS-1$

	/** Name of the extension point's "generator ID" tag. */
	private static final String DYNAMIC_MODULES_TAG_GENERATOR_ID = "generatorID"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
	 */
	public void added(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			parseExtension(extension);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
	 */
	public void added(IExtensionPoint[] extensionPoints) {
		// no need to listen to this event
	}

	/**
	 * Though this listener reacts to the extension point changes, there could have been contributions before
	 * it's been registered. This will parse these extensions.
	 */
	public void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();

		// Dynamic templates
		for (IExtension extension : registry.getExtensionPoint(DYNAMIC_TEMPLATES_EXTENSION_POINT)
				.getExtensions()) {
			parseExtension(extension);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
	 */
	public void removed(IExtension[] extensions) {
		/*
		 * Extensions will be removed on the fly by AcceleoDynamicTemplatesEclipseUtil when trying to access
		 * uninstalled bundles
		 */
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
	 */
	public void removed(IExtensionPoint[] extensionPoints) {
		// no need to listen to this event
	}

	/**
	 * Parses a single extension contribution.
	 * 
	 * @param extension
	 *            Parses the given extension and adds its contribution to the registry.
	 */
	private void parseExtension(IExtension extension) {
		final IConfigurationElement[] configElements = extension.getConfigurationElements();
		final List<String> paths = new ArrayList<String>(configElements.length);
		final List<String> generators = new ArrayList<String>(configElements.length);
		for (IConfigurationElement elem : configElements) {
			if (DYNAMIC_TEMPLATES_TAG_TEMPLATES.equals(elem.getName())) {
				String path = elem.getAttribute(DYNAMIC_TEMPLATES_ATTRIBUTE_PATH);
				if (path != null) {
					paths.add(path);
				}
			} else if (DYNAMIC_TEMPLATES_TAG_GENERATOR.equals(elem.getName())) {
				String generator = elem.getAttribute(DYNAMIC_MODULES_TAG_GENERATOR_ID);
				if (generator != null) {
					generators.add(generator);
				}
			}
		}
		Bundle bundle = Platform.getBundle(extension.getContributor().getName());
		// If bundle is null, the bundle id is different than its name.
		if (bundle != null) {
			if (paths.size() == 0) {
				paths.add("/"); //$NON-NLS-1$
			}
		} else {
			bundle = new BundleAdapter(extension.getContributor());
		}
		AcceleoDynamicModulesDescriptor acceleoDynamicModulesDescriptor = new AcceleoDynamicModulesDescriptor(
				generators, paths);
		AcceleoDynamicTemplatesEclipseUtil.addExtendingBundle(bundle, acceleoDynamicModulesDescriptor);
	}

	protected class BundleAdapter implements Bundle {

		private IContributor contributor;

		public BundleAdapter(IContributor contributor) {
			this.contributor = contributor;
		}

		public int compareTo(Bundle o) {
			return 0;
		}

		public int getState() {
			return 0;
		}

		public void start(int options) throws BundleException {

		}

		public void start() throws BundleException {

		}

		public void stop(int options) throws BundleException {

		}

		public void stop() throws BundleException {

		}

		public void update(InputStream input) throws BundleException {

		}

		public void update() throws BundleException {

		}

		public void uninstall() throws BundleException {

		}

		public Dictionary<String, String> getHeaders() {
			return null;
		}

		public long getBundleId() {
			return 0;
		}

		public String getLocation() {
			return null;
		}

		public ServiceReference<?>[] getRegisteredServices() {
			return null;
		}

		public ServiceReference<?>[] getServicesInUse() {
			return null;
		}

		public boolean hasPermission(Object permission) {
			return false;
		}

		public URL getResource(String name) {
			return null;
		}

		public Dictionary<String, String> getHeaders(String locale) {
			return null;
		}

		public String getSymbolicName() {
			return null;
		}

		public Class<?> loadClass(String name) throws ClassNotFoundException {
			return null;
		}

		public Enumeration<URL> getResources(String name) throws IOException {
			return null;
		}

		public Enumeration<String> getEntryPaths(String path) {
			return null;
		}

		public URL getEntry(String path) {
			return null;
		}

		public long getLastModified() {
			return 0;
		}

		public Enumeration<URL> findEntries(String path, String filePattern, boolean recurse) {
			String pluginID = contributor.getName();
			URI plPath = org.eclipse.emf.ecore.resource.URIConverter.URI_MAP.get(URI.createURI(
					"platform:/plugin/" + pluginID + "/"));
			if (plPath == null) {
				plPath = org.eclipse.emf.ecore.resource.URIConverter.URI_MAP.get(URI.createURI(
						"platform:/resource/" + pluginID + "/"));
			}
			if (plPath == null) {
				return null;
			}
			Collection<URL> ret = null;
			if (plPath.isFile()) {
				ret = findResourceRecursive(new File(plPath.toFileString()), filePattern, recurse);
			} else if (plPath.isArchive() && "jar".equals(plPath.scheme())) {
				URL url;
				try {
					url = new URL(plPath.toString());
					JarURLConnection urlcon = (JarURLConnection)url.openConnection();
					JarFile jarFile = urlcon.getJarFile();
					ret = findResourceInJar(url, jarFile, filePattern);
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			return new VectorEnumeration<URL>(ret);
		}

		private Collection<URL> findResourceInJar(URL jarUrl, JarFile jarFile, String filePattern)
				throws MalformedURLException {
			Vector<URL> out = new Vector<URL>();
			Enumeration<JarEntry> entries = jarFile.entries();
			String regex = filePattern.substring(1);
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();

				if (entry.getName().endsWith(regex)) {
					out.add(new URL(jarUrl + entry.getName()));
				}
			}
			return out;
		}

		private final Collection<URL> findResourceRecursive(File libFolder, String regex, boolean recurse) {
			Vector<URL> out = new Vector<URL>();

			File[] jars = libFolder.listFiles(new RegexFileFilter(regex));
			for (File file : jars) {
				try {
					out.add(file.toURL());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

			if (recurse) {
				File[] directories = libFolder.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return pathname.isDirectory();
					}
				});
				if (directories != null) {
					for (int i = 0; i < directories.length; i++) {
						out.addAll(findResourceRecursive(directories[i], regex, recurse));
					}
				}
			}
			return out;
		}

		public BundleContext getBundleContext() {
			return null;
		}

		public Map<X509Certificate, List<X509Certificate>> getSignerCertificates(int signersType) {
			return null;
		}

		public Version getVersion() {
			return null;
		}

		public <A> A adapt(Class<A> type) {
			return null;
		}

		public File getDataFile(String filename) {
			return null;
		}

	}

	protected class VectorEnumeration<E> implements Enumeration<E> {

		Iterator<E> it;

		VectorEnumeration(Collection<E> col) {
			it = col.iterator();
		}

		public boolean hasMoreElements() {
			return it.hasNext();
		}

		public E nextElement() {
			return it.next();
		}
	}

	protected class RegexFileFilter implements FileFilter {
		private String regex;

		RegexFileFilter(String regex) {
			this.regex = regex.substring(1);
		}

		public boolean accept(File pathname) {
			return pathname.getAbsolutePath().endsWith(regex);
		}
	}
}
