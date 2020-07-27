/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.resolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;

/**
 * Resolve using a {@link ClassLoader}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ClassLoaderQualifiedNameResolver implements IQualifiedNameResolver {

	/**
	 * A slash.
	 */
	private static final String SLASH = "/";

	/**
	 * A dot.
	 */
	private static final String DOT = ".";

	/**
	 * The {@link ClassLoader}.
	 */
	private final ClassLoader classLoader;

	/** The {@link AcceleoParser}. */
	private final AcceleoParser parser;

	/**
	 * The mapping form qualified name to resource {@link URL}.
	 */
	private final Map<String, URL> qualifiedNameToURL = new HashMap<String, URL>();

	/**
	 * The mapping form resource {@link URL} to qualified name.
	 */
	private final Map<URL, String> urlToQualifiedName = new HashMap<URL, String>();

	/**
	 * Constructor.
	 * 
	 * @param classLoader
	 *            the {@link ClassLoader}
	 * @param queryEnvironment
	 *            the AQL environment to use when parsing resolved modules
	 */
	public ClassLoaderQualifiedNameResolver(ClassLoader classLoader,
			IReadOnlyQueryEnvironment queryEnvironment) {
		Objects.requireNonNull(classLoader);
		Objects.requireNonNull(queryEnvironment);
		this.classLoader = classLoader;
		this.parser = new AcceleoParser(queryEnvironment);
	}

	@Override
	public Module resolveModule(String qualifiedName) throws IOException {
		final Module res;

		try (InputStream input = classLoader.getResourceAsStream(getModuleResourceName(qualifiedName));) {
			if (input != null) {
				res = parser.parse(input, StandardCharsets.UTF_8, qualifiedName).getModule();
			} else {
				res = null;
			}
		}
		return res;
	}

	/**
	 * Gets the {@link Module} resource name from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name (e.g. <code>qualified::path::to::module</code>)
	 * @return the {@link Module} resource name from the given qualified name
	 */
	protected String getModuleResourceName(String qualifiedName) {
		return qualifiedName.replace(AcceleoParser.QUALIFIER_SEPARATOR, SLASH) + DOT
				+ AcceleoParser.MODULE_FILE_EXTENSION;
	}

	@Override
	public Class<?> resolveClass(String qualifiedName) throws ClassNotFoundException {
		return classLoader.loadClass(getClassName(qualifiedName));
	}

	/**
	 * Gets the {@link Class} name from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name (e.g. <code>qualified::path::to::module</code>)
	 * @return the {@link Class} name from the given qualified name
	 */
	private String getClassName(String qualifiedName) {
		return qualifiedName.replace(AcceleoParser.QUALIFIER_SEPARATOR, DOT);
	}

	@Override
	public URL getModuleURL(String qualifiedName) {
		// TODO null values are never cached...
		return qualifiedNameToURL.computeIfAbsent(qualifiedName, key -> {
			return classLoader.getResource(getModuleResourceName(key));
		});
	}

	@Override
	public URL getModuleSourceURL(String qualifiedName) {
		return getModuleURL(qualifiedName);
	}

	@Override
	public String getQualifierName(URL resource) {
		// TODO null values are never cached...
		return urlToQualifiedName.computeIfAbsent(resource, key -> {
			String res = null;

			final String filePath = resource.getFile();
			final String[] segments = filePath.substring(0, filePath.length()
					- (AcceleoParser.MODULE_FILE_EXTENSION.length() + 1)).split(SLASH);

			final StringBuilder moduleQualifiedNameBuilder = new StringBuilder();
			for (int i = segments.length - 1; i >= 0; i--) {
				moduleQualifiedNameBuilder.insert(0, segments[i]);
				final String qualifiedName = moduleQualifiedNameBuilder.toString();
				if (getModuleURL(qualifiedName) != null) {
					res = qualifiedName;
				}
				moduleQualifiedNameBuilder.insert(0, AcceleoParser.QUALIFIER_SEPARATOR);
			}

			return res;
		});
	}

	@Override
	public Set<String> getAvailableQualifiedNames() {
		final Set<String> res = new LinkedHashSet<String>();

		try {
			if (classLoader instanceof URLClassLoader) {
				for (URL url : ((URLClassLoader)classLoader).getURLs()) {
					res.addAll(getQualifiedNamesFromURL(url));
				}
			} else {
				final Enumeration<URL> rootResources = classLoader.getResources("");
				while (rootResources.hasMoreElements()) {
					final URL url = rootResources.nextElement();
					res.addAll(getQualifiedNamesFromURL(url));
				}
			}
		} catch (IOException e1) {
			// nothing to do here
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of qualified names for the given {@link URL}.
	 * 
	 * @param url
	 *            the {@link URL}
	 * @return the {@link Set} of qualified names for the given {@link URL}
	 */
	protected Set<String> getQualifiedNamesFromURL(URL url) {
		final Set<String> res = new LinkedHashSet<String>();

		// TODO jar://
		if ("file".equals(url.getProtocol())) {
			try {
				final File file = new File(url.toURI());
				if (file.isDirectory()) {
					res.addAll(getQualifiedNameFromFolder(file, ""));
				} else if (file.isFile()) {
					res.addAll(getQualifiedNameFromJar(file));
				} else {
					// can't happen
				}
			} catch (URISyntaxException e) {
				// nothing to do here
			}
		}

		return res;
	}

	/**
	 * Gets the {@link List} of qualified names in the given jar {@link File}.
	 * 
	 * @param file
	 *            the jar {@link File}
	 * @return the {@link List} of qualified names in the given jar {@link File}
	 */
	protected Set<String> getQualifiedNameFromJar(File file) {
		final Set<String> res = new LinkedHashSet<String>();

		try (ZipFile jarFile = new ZipFile(file);) {
			final Enumeration<? extends ZipEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				final ZipEntry entry = entries.nextElement();
				final String name = entry.getName().replace(SLASH, AcceleoParser.QUALIFIER_SEPARATOR);
				if (name.endsWith(".class") || name.endsWith(DOT + AcceleoParser.MODULE_FILE_EXTENSION)) {
					res.add(name.substring(0, name.lastIndexOf(DOT)));
				}
			}
		} catch (ZipException e) {
			// nothing to do here
		} catch (IOException e) {
			// nothing to do here
		}

		return res;
	}

	/**
	 * Gets the {@link List} of qualified names inside the given folder and the given name space starting
	 * point.
	 * 
	 * @param folder
	 *            the folder
	 * @param nameSpace
	 *            the name space
	 * @return the {@link List} of qualified names inside the given folder and the given name space starting
	 *         point
	 */
	protected Set<String> getQualifiedNameFromFolder(File folder, String nameSpace) {
		final Set<String> res = new LinkedHashSet<String>();

		if (folder.exists() && folder.canRead()) {
			for (File child : folder.listFiles()) {
				if (child.isDirectory()) {
					res.addAll(getQualifiedNameFromFolder(child, nameSpace + child.getName()
							+ AcceleoParser.QUALIFIER_SEPARATOR));
				} else if (child.isFile() && (child.getName().endsWith(".class") || child.getName().endsWith(
						DOT + AcceleoParser.MODULE_FILE_EXTENSION))) {
					res.add(nameSpace + child.getName().substring(0, child.getName().lastIndexOf(DOT)));
				}
			}
		}

		return res;
	}

}
