/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;

/**
 * Resolve from a {@link ClassLoader}.
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

	/**
	 * The qualified name separator.
	 */
	private final String qualifierSeparator;

	/**
	 * The {@link List} of {@link ILoader}.
	 */
	private final List<ILoader> loaders = new ArrayList<ILoader>();

	/**
	 * Mapping from qualifiedName to its {@link Object}.
	 */
	private final Map<String, Object> qualifiedNameToObject = new HashMap<String, Object>();

	/**
	 * Mapping from an {@link Object} to its qualified name.
	 */
	private final Map<Object, String> objectToQualifiedName = new HashMap<Object, String>();

	/**
	 * Mapping from qualifiedName to its imports.
	 */
	private final Map<String, List<String>> qualifiedNameToImports = new HashMap<String, List<String>>();

	/**
	 * Mapping from qualifiedName to its extend.
	 */
	private final Map<String, String> qualifiedNameToExtend = new HashMap<String, String>();

	/**
	 * Mapping from qualifiedName to qualified names depending on it. Opposite of {@link #getExtend(String)}
	 * and {@link #getImports(String)}.
	 */
	private final Map<String, List<String>> qualifiedNameToDependOn = new HashMap<String, List<String>>();

	/**
	 * Constructor.
	 * 
	 * @param classLoader
	 *            the {@link ClassLoader}
	 * @param qualifierSeparator
	 *            the qualifier name separator
	 */
	public ClassLoaderQualifiedNameResolver(ClassLoader classLoader, String qualifierSeparator) {
		this.classLoader = classLoader;
		this.qualifierSeparator = qualifierSeparator;
	}

	@Override
	public String getQualifiedName(URL url) {
		String res = null;

		final String filePath = url.getFile();
		int pathEnd = filePath.lastIndexOf(DOT);
		if (pathEnd < 0) {
			pathEnd = filePath.length();
		}
		final String[] segments = filePath.substring(0, pathEnd).split(SLASH);

		final StringBuilder moduleQualifiedNameBuilder = new StringBuilder();
		for (int i = segments.length - 1; i >= 0; i--) {
			moduleQualifiedNameBuilder.insert(0, segments[i]);
			final String qualifiedName = moduleQualifiedNameBuilder.toString();
			if (getURL(qualifiedName) != null) {
				res = qualifiedName;
			}
			moduleQualifiedNameBuilder.insert(0, qualifierSeparator);
		}

		return res;
	}

	@Override
	public URL getURL(String qualifiedName) {
		URL res = null;

		for (ILoader loader : loaders) {
			res = classLoader.getResource(loader.resourceName(qualifiedName));
			if (res != null) {
				break;
			}
		}

		return res;
	}

	@Override
	public URL getSourceURL(String qualifiedName) {
		return getURL(qualifiedName);
	}

	@Override
	public ISourceLocation getSourceLocation(IService<?> service) {
		ISourceLocation res = null;

		for (ILoader loader : loaders) {
			res = loader.getSourceLocation(this, service);
			if (res != null) {
				break;
			}
		}

		return res;
	}

	@Override
	public ISourceLocation getSourceLocation(String qualifiedName) {
		ISourceLocation res = null;

		for (ILoader loader : loaders) {
			res = loader.getSourceLocation(this, qualifiedName);
			if (res != null) {
				break;
			}
		}

		return res;
	}

	/**
	 * Loads the {@link Object} from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link Object} from the given qualified name if any, <code>null</code> otherwise
	 */
	private Object load(String qualifiedName) {
		Object res = null;

		boolean registered = false;
		for (ILoader loader : loaders) {
			res = loader.load(this, qualifiedName);
			if (res != null) {
				register(loader, qualifiedName, res);
				registered = true;
				break;
			}
		}

		// we perform a dummy registration to prevent further loading
		if (!registered) {
			qualifiedNameToImports.put(qualifiedName, Collections.emptyList());
			qualifiedNameToExtend.put(qualifiedName, null);
			qualifiedNameToObject.put(qualifiedName, null);
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver#register(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void register(String qualifiedName, Object object) {
		final ILoader loader = getLoaderFor(object);
		if (loader != null) {
			register(loader, qualifiedName, object);
		}
	}

	/**
	 * Registers the given {@link Object} to the given qualified name using the given {@link ILoader}.
	 * 
	 * @param loader
	 *            the {@link ILoader}
	 * @param qualifiedName
	 *            the qualified name
	 * @param object
	 *            the {@link Object}
	 */
	private void register(ILoader loader, String qualifiedName, Object object) {
		final List<String> imports = loader.getImports(object);
		qualifiedNameToImports.put(qualifiedName, imports);
		for (String imported : imports) {
			qualifiedNameToDependOn.computeIfAbsent(imported, qn -> new ArrayList<String>()).add(
					qualifiedName);
		}
		final String ext = loader.getExtends(object);
		qualifiedNameToExtend.put(qualifiedName, ext);
		if (ext != null) {
			qualifiedNameToDependOn.computeIfAbsent(ext, qn -> new ArrayList<String>()).add(qualifiedName);
		}
		final Object removedObject = qualifiedNameToObject.put(qualifiedName, object);
		if (removedObject != null) {
			objectToQualifiedName.remove(removedObject);
		}
		objectToQualifiedName.put(object, qualifiedName);

	}

	/**
	 * Gets the {@link ILoader} for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the {@link ILoader} for the given {@link Object} if any, <code>null</code> otherwise
	 */
	private ILoader getLoaderFor(Object object) {
		ILoader res = null;

		for (ILoader loader : loaders) {
			if (loader.canHandle(object)) {
				res = loader;
				break;
			}
		}

		return res;
	}

	@Override
	public void clear(Set<String> qualifiedNames) {
		for (String qualifiedName : qualifiedNames) {
			final Object object = qualifiedNameToObject.remove(qualifiedName);
			objectToQualifiedName.remove(object);
			final List<String> imports = qualifiedNameToImports.remove(qualifiedName);
			if (imports != null) {
				for (String imported : imports) {
					final List<String> dependendOn = qualifiedNameToDependOn.get(imported);
					if (dependendOn != null) {
						dependendOn.remove(qualifiedName);
					}
				}
			}
			final String extended = qualifiedNameToExtend.remove(qualifiedName);
			if (extended != null) {
				final List<String> dependendOn = qualifiedNameToDependOn.get(extended);
				if (dependendOn != null) {
					dependendOn.remove(qualifiedName);
				}
			}
		}
	}

	@Override
	public Object resolve(String qualifiedName) {
		if (!qualifiedNameToObject.containsKey(qualifiedName)) {
			load(qualifiedName);
		}
		return qualifiedNameToObject.get(qualifiedName);
	}

	@Override
	public Set<IService<?>> getServices(IQualifiedNameLookupEngine lookupEngine, Object object,
			String contextQualifiedName) {
		final Set<IService<?>> res = new LinkedHashSet<IService<?>>();

		final ILoader loader = getLoaderFor(object);
		if (loader != null) {
			res.addAll(loader.getServices(lookupEngine, object, contextQualifiedName));
		}

		return res;
	}

	@Override
	public String getExtend(String qualifiedName) {
		if (!qualifiedNameToExtend.containsKey(qualifiedName)) {
			load(qualifiedName);
		}

		return qualifiedNameToExtend.get(qualifiedName);
	}

	@Override
	public List<String> getImports(String qualifiedName) {
		if (!qualifiedNameToImports.containsKey(qualifiedName)) {
			load(qualifiedName);
		}

		return qualifiedNameToImports.getOrDefault(qualifiedName, Collections.emptyList());
	}

	@Override
	public List<String> getDependOn(String qualifiedName) {
		return qualifiedNameToDependOn.getOrDefault(qualifiedName, Collections.emptyList());
	}

	@Override
	public InputStream getInputStream(String resourceName) {
		return classLoader.getResourceAsStream(resourceName);
	}

	@Override
	public Class<?> getClass(String qualifiedName) {
		Class<?> res;

		try {
			res = classLoader.loadClass(qualifiedName.replace(qualifierSeparator, DOT));
		} catch (ClassNotFoundException e) {
			res = null;
		}

		return res;
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
				for (ILoader loader : loaders) {
					final String qualifiedName = loader.qualifiedName(entry.getName());
					if (qualifiedName != null) {
						res.add(qualifiedName);
						break;
					}
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
							+ qualifierSeparator));
				} else if (child.isFile()) {
					for (ILoader loader : loaders) {
						final String lastSegment = loader.qualifiedName(child.getName());
						if (lastSegment != null) {
							res.add(nameSpace + lastSegment);
							break;
						}
					}
				}
			}
		}

		return res;
	}

	/**
	 * Gets the {@link List} of possible source resource names for the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link List} of possible source resource names for the given qualified name
	 */
	protected List<String> getPossibleResourceNames(String qualifiedName) {
		final List<String> res = new ArrayList<String>();

		for (ILoader loader : loaders) {
			res.add(loader.sourceResourceName(qualifiedName));
		}

		return res;
	}

	@Override
	public String getQualifiedName(Object object) {
		return objectToQualifiedName.get(object);
	}

	@Override
	public void addLoader(ILoader loader) {
		loaders.add(loader);
	}

	@Override
	public void removeLoader(ILoader loader) {
		loaders.remove(loader);
	}

	@Override
	public void clearLoaders() {
		loaders.clear();
	}
}
