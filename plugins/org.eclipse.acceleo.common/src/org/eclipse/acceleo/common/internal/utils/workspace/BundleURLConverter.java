/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Instances of this class will be used to convert URLs pointing to installed bundles to and from multiple
 * protocols : <code>file</code>, <code>bundleentry</code>, <code>platform:/plugin</code>, <code>jar</code>,
 * <code>http</code>...
 * <p>
 * This should never be used in standalone as its only purpose is to find installed bundles' entries.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class BundleURLConverter {
	/** Describes the file URL protocol. */
	private static final String FILE_PROTOCOL = "file:"; //$NON-NLS-1$

	/** The following segments will be trimmed out of the URIs we try and resolve. */
	private static final List<String> IGNORED_URI_SEGMENTS = new ArrayList<String>(3);

	/** Describes the jar URL protocol. */
	private static final String JAR_PROTOCOL = "jar:"; //$NON-NLS-1$

	/** Describes the platform plugin URL protocol. */
	private static final String PLATFORM_PLUGIN_PROTOCOL = "platform:/plugin"; //$NON-NLS-1$

	/** The base URL this instance will be used to convert. */
	private final String baseURL;

	/** Bundle in which could be resolved {@link #baseURL} if any. */
	private Bundle bundle;

	/**
	 * This will contain the path, relative to the root of {@link #bundle}, were {@link #baseURL} could be
	 * resolved.
	 */
	private String bundlePath;

	/** This will contain an URL using a native java protocol (http, jar, file...). */
	private String nativeProtocolURL;

	static {
		// "@dot" in the path means it has been built through the PDE build with customBuildCallBacks
		IGNORED_URI_SEGMENTS.add("@dot"); //$NON-NLS-1$
		/*
		 * "bin" or "src" in the path means that, somehow, we resolved the URIs against workspace compiled
		 * artifacts. As we are currently trying to resolve the URI in an installed bundle, we know these
		 * segments are noise.
		 */
		IGNORED_URI_SEGMENTS.add("bin"); //$NON-NLS-1$
		IGNORED_URI_SEGMENTS.add("src"); //$NON-NLS-1$
	}

	/**
	 * Constructs and URL converter given the URL we'll have to manipulate.
	 * 
	 * @param base
	 *            The base URL we'll manipulate.
	 */
	public BundleURLConverter(String base) {
		baseURL = base;
	}

	/**
	 * This will check if the given String represents an integer less than five digits long.
	 * 
	 * @param s
	 *            The string we wish compared to an integer.
	 * @return <code>true</code> if <code>s</code> is an integer comprised between 0 and 9999,
	 *         <code>false</code> otherwise.
	 */
	private static boolean isBundleIDCandidate(String s) {
		if (s.length() == 0 || s.length() > 5) {
			return false;
		}

		boolean isInteger = true;
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				isInteger = false;
			}
		}
		return isInteger;
	}

	/**
	 * Returns the base URL this converter is meant to handle.
	 * 
	 * @return The base URL this converter is meant to handle.
	 */
	public String getBaseURL() {
		return baseURL;
	}

	/**
	 * This will try and resolve the given {@link #baseURL} in the installed plugins, then convert it back to
	 * an absolute path towards the described resource. The returned URL will use a native java protocol such
	 * as jar, file, http...
	 * 
	 * @return {@link #baseURL} once converted to a native java protocol if it could be resolved in one of the
	 *         installed plugins, <code>null</code> otherwise.
	 */
	public String resolveAsNativeProtocolURL() {
		if (nativeProtocolURL == null) {
			resolveBundle();
		}
		return nativeProtocolURL;
	}

	/**
	 * Returns {@link #baseURL} once resolved in the installed plugins and converted to a
	 * <code>platform:/plugin</code> protocol.
	 * 
	 * @return {@link #baseURL} converted to a <code>platform:/plugin</code> protocol if it could be resolved
	 *         in one of the installed plugins, <code>null</code> otherwise.
	 */
	public String resolveAsPlatformPlugin() {
		final Bundle containingBundle = resolveBundle();
		// bundlePath should have been resolved by the call to getBundle()
		final String relativePath = bundlePath;
		if (bundlePath == null || containingBundle == null) {
			return null;
		}

		return PLATFORM_PLUGIN_PROTOCOL + '/' + containingBundle.getSymbolicName() + relativePath;
	}

	/**
	 * Tries and resolve a java class going by the given qualified name in the bundle denoted by the
	 * {@link #baseURL}.
	 * 
	 * @param qualifiedName
	 *            The qualified name of the class we seek to find.
	 * @return The OSGi bundle containing the class going by the given qualified name.
	 */
	public Bundle resolveInBundle(String qualifiedName) {
		if (bundle != null) {
			return bundle;
		}

		String actualPath = baseURL;
		if (actualPath.startsWith(JAR_PROTOCOL)) {
			actualPath = trimJarAffixAndQualifier(actualPath);
		}
		if (actualPath.startsWith(FILE_PROTOCOL + '/')) {
			actualPath = actualPath.substring(FILE_PROTOCOL.length() + 1);
		}

		if (actualPath.contains("#")) { //$NON-NLS-1$
			actualPath = actualPath.substring(0, actualPath.indexOf('#'));
		}

		Bundle tempBundle = null;
		String tempPath = null;
		String tempWithIgnoredSegments = null;
		String[] segments = actualPath.split("/|\\\\"); //$NON-NLS-1$
		// Note : this loop will be broken as soon as we find the bundle
		for (int i = segments.length - 1; i >= 0 && bundle == null; i--) {
			if (isBundleIDCandidate(segments[i])) {
				tempBundle = AcceleoCommonPlugin.getDefault().getContext().getBundle(
						Long.valueOf(segments[i]).longValue());
			} else {
				tempBundle = Platform.getBundle(segments[i]);
			}

			if (tempBundle != null && qualifiedName == null) {
				tempPath = ""; //$NON-NLS-1$

				int pathStart = i + 1;
				// ".cp" in the file path means this URI points somewhere in the workspace metadata.
				if (pathStart < segments.length && ".cp".equals(segments[pathStart])) { //$NON-NLS-1$ 
					pathStart += 1;
				} else if (segments.length > pathStart + 1 && ".cp".equals(segments[pathStart + 1])) { //$NON-NLS-1$
					pathStart += 2;
				}

				boolean containsIgnoredSegments = false;
				for (int j = pathStart; j < segments.length; j++) {
					if (containsIgnoredSegments) {
						tempWithIgnoredSegments += '/' + segments[j];
					}
					if (!IGNORED_URI_SEGMENTS.contains(segments[j])) {
						tempPath += '/' + segments[j];
					} else {
						if (!containsIgnoredSegments) {
							containsIgnoredSegments = true;
							tempWithIgnoredSegments = '/' + segments[j];
						}
					}
				}
				URL fileURL = tempBundle.getEntry(tempPath);
				if (fileURL != null) {
					bundle = tempBundle;
					bundlePath = tempPath;
				} else if (tempWithIgnoredSegments != null) {
					fileURL = tempBundle.getEntry(tempWithIgnoredSegments);
					if (fileURL != null) {
						bundle = tempBundle;
						bundlePath = tempWithIgnoredSegments;
					}
				}
				if (fileURL != null) {
					try {
						nativeProtocolURL = FileLocator.resolve(fileURL).toString();
					} catch (IOException e) {
						AcceleoCommonPlugin.log(e, true);
					}
				}
			} else if (tempBundle != null) {
				try {
					tempBundle.loadClass(qualifiedName);
					// If we're here, we found the class.
					bundle = tempBundle;
				} catch (ClassNotFoundException e) {
					// Swallow
				}
			}
		}

		return bundle;
	}

	/**
	 * This will try and resolve the {@link #baseURL} in an installed bundle and return it if any,
	 * <code>null</code> otherwise.
	 * 
	 * @return The OSGi bundle in which could be located {@link #baseURL}.
	 */
	public Bundle resolveBundle() {
		return resolveInBundle(null);
	}

	/**
	 * Removes the jar prefix (protocol) and suffix (jar!), as well as the qualifier if any.
	 * 
	 * @param path
	 *            The path to trim out.
	 * @return The trimmed path.
	 */
	private String trimJarAffixAndQualifier(String path) {
		String actualPath = path.substring(JAR_PROTOCOL.length());
		// If the jar file has a qualifier, delete it along with the last ".jar!"
		if (actualPath.contains("_")) { //$NON-NLS-1$
			actualPath = actualPath.replaceFirst("(?:/|\\\\)([^/]*?)(_|-)[^_/]*\\.jar!/", "/$1/"); //$NON-NLS-1$  //$NON-NLS-2$
		} else {
			actualPath = actualPath.replaceFirst("\\.jar!", ""); //$NON-NLS-1$  //$NON-NLS-2$
		}
		return actualPath;
	}
}
