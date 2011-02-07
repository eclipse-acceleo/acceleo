/*******************************************************************************
 * Copyright (c) 2009, 2011 Obeo.
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

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Instances of this class will be used to convert URLs to and from multiple protocols : <code>file</code>,
 * <code>bundleentry</code>, <code>platform:/plugin</code>, <code>jar</code>, <code>http</code>...
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class BundleURLConverter {
	/** Describes the file URL protocol. */
	private static final String FILE_PROTOCOL = "file:"; //$NON-NLS-1$

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
	 * This will try and resolve the {@link #baseURL} in an installed bundle and return it if any,
	 * <code>null</code> otherwise.
	 * 
	 * @return The OSGi bundle in which could be located {@link #baseURL}.
	 */
	public Bundle resolveBundle() {
		if (bundle != null) {
			return bundle;
		}

		String actualPath = baseURL;
		if (actualPath.startsWith(JAR_PROTOCOL)) {
			actualPath = actualPath.substring(JAR_PROTOCOL.length());
			// If the jar file has a qualifier, delete it along with the last ".jar!"
			if (actualPath.contains("_")) { //$NON-NLS-1$
				actualPath = actualPath.replaceFirst("/([^/]*?)_[^_]*\\.jar!/", "/$1/"); //$NON-NLS-1$  //$NON-NLS-2$
			} else {
				actualPath = actualPath.replaceFirst("\\.jar!", ""); //$NON-NLS-1$  //$NON-NLS-2$
			}
		}
		if (actualPath.startsWith(FILE_PROTOCOL + '/')) {
			actualPath = actualPath.substring(FILE_PROTOCOL.length() + 1);
		}

		// FIXME LGO Why are we coming here with this ???
		if (actualPath.contains("#")) { //$NON-NLS-1$
			actualPath = actualPath.substring(0, actualPath.indexOf('#'));
		}

		Bundle tempBundle = null;
		String tempPath = null;
		String[] segments = actualPath.split("/"); //$NON-NLS-1$
		// Note : this loop will be broken as soon as we find the bundle
		for (int i = segments.length - 1; i >= 0 && bundle == null; i--) {
			if (isBundleIDCandidate(segments[i])) {
				tempBundle = AcceleoCommonPlugin.getDefault().getContext().getBundle(
						Long.valueOf(segments[i]).longValue());
			} else {
				tempBundle = Platform.getBundle(segments[i]);
			}

			if (tempBundle != null) {
				tempPath = ""; //$NON-NLS-1$

				int pathStart = i + 1;
				// ".cp" in the file path means this URI points somewhere in the workspace metadata.
				// "@dot" in the path means it has been built through the PDE build with customBuildCallBacks
				if (".cp".equals(segments[pathStart]) || "@dot".equals(segments[pathStart])) { //$NON-NLS-1$ //$NON-NLS-2$
					pathStart += 1;
				} else if (segments.length > pathStart + 1 && ".cp".equals(segments[pathStart + 1])) { //$NON-NLS-1$
					pathStart += 2;
				}

				for (int j = pathStart; j < segments.length; j++) {
					tempPath += '/' + segments[j];
				}
				URL fileURL = tempBundle.getEntry(tempPath);
				if (fileURL != null) {
					bundle = tempBundle;
					bundlePath = tempPath;
					try {
						nativeProtocolURL = FileLocator.resolve(fileURL).toString();
					} catch (IOException e) {
						AcceleoCommonPlugin.log(e, true);
					}
				}
			}
		}

		return bundle;
	}
}
