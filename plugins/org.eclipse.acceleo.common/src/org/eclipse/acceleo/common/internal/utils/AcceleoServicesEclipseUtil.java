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
package org.eclipse.acceleo.common.internal.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.internal.utils.workspace.BundleURLConverter;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

/**
 * Eclipse-specific utilities for Acceleo services. It will be initialized with all services that could be
 * parsed from the extension point if Eclipse is running and won't be used when outside of Eclipse.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoServicesEclipseUtil {
	/** Services are cached within {@link AcceleoWorkspaceUtil}. This will only store their qualified names. */
	private static final Set<String> REGISTERED_SERVICES = new CompactLinkedHashSet<String>();

	/**
	 * If we need to load services that are not in the workspace, but instead in the plugins, we'll have to
	 * cache them separately.
	 */
	private static final Map<String, Class<?>> REGISTERED_CLASSES = new HashMap<String, Class<?>>();

	/**
	 * Utility classes don't need a default constructor.
	 */
	private AcceleoServicesEclipseUtil() {
		// hides constructor
	}

	/**
	 * This will clear the registry off all services that have been registered for evaluation.
	 */
	public static void clearRegistry() {
		REGISTERED_SERVICES.clear();
	}

	/**
	 * This will return the singleton instance of the given class that serves as invocation source.
	 * 
	 * @param serviceClass
	 *            The class we need the service singleton of.
	 * @return The singleton instance of the given service class.
	 * @since 3.0
	 */
	public static Object getServiceInstance(Class<?> serviceClass) {
		return AcceleoWorkspaceUtil.INSTANCE.getServiceInstance(serviceClass);
	}

	/**
	 * This will return an instance of class named <code>qualifiedName</code> loaded from the given bundle.
	 * This is fully equivalent to calling <code>bundle.loadClass(qualifiedName).newInstance()</code>.
	 * <p>
	 * As a result of this call, the service will be added to the list of registered services, allowing it to
	 * be retrieved through {@link #getRegisteredServices()} afterwards.
	 * </p>
	 * 
	 * @param bundle
	 *            The {@link Bundle} containing the acceleo file which tries to make use of a service name
	 *            <code>qualifiedName</code>.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public static Class<?> registerService(Bundle bundle, String qualifiedName) {
		Class<?> clazz = REGISTERED_CLASSES.get(qualifiedName);
		if (clazz != null) {
			return clazz;
		}

		try {
			clazz = bundle.loadClass(qualifiedName);
			if (clazz != null) {
				REGISTERED_SERVICES.add(qualifiedName);
				REGISTERED_CLASSES.put(qualifiedName, clazz);
			}
		} catch (ClassNotFoundException e) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassLookupFailure", //$NON-NLS-1$
					qualifiedName, bundle.getSymbolicName()), e, false);
		}
		return clazz;
	}

	/**
	 * This will refresh workspace contributions to Acceleo and load the given service if it is located in a
	 * workspace bundle.
	 * 
	 * @param project
	 *            The {@link IProject} containing the acceleo file which tries to make use of a service name
	 *            <code>qualifiedName</code>.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public static Class<?> registerService(IProject project, String qualifiedName) {
		final Class<?> clazz = AcceleoWorkspaceUtil.INSTANCE.getClass(project, qualifiedName);
		if (clazz != null) {
			REGISTERED_SERVICES.add(qualifiedName);
		}
		return clazz;
	}

	/**
	 * This will return an instance of class named <code>qualifiedName</code> loaded from the given bundle.
	 * This will first attempt to search through the workspace projects if one of them corresponds to this
	 * symbolic name, and will be fully equivalent to calling
	 * <code>Platform.getBundle(bundleName).loadClass(qualifiedName).newinstance()</code> otherwise.
	 * 
	 * @param bundleName
	 *            The symbolic name of the bundle {@link Bundle} containing the acceleo file which tries to
	 *            make use of a service name <code>qualifiedName</code>.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public static Class<?> registerService(String bundleName, String qualifiedName) {
		Class<?> clazz = REGISTERED_CLASSES.get(qualifiedName);
		if (clazz != null) {
			return clazz;
		}

		final IProject project = AcceleoWorkspaceUtil.getProject(bundleName);
		if (project != null) {
			clazz = registerService(project, qualifiedName);
		} else {
			clazz = registerService(Platform.getBundle(bundleName), qualifiedName);
		}
		return clazz;
	}

	/**
	 * This will return an instance of class named <code>qualifiedName</code> loaded from the bundle
	 * containing the file described by <code>uri</code>.
	 * <p>
	 * As a result of this call, the service will be added to the list of registered services, allowing it to
	 * be retrieved through {@link #getRegisteredServices()} afterwards.
	 * </p>
	 * 
	 * @param uri
	 *            URI of the module currently being evaluated. This will be used as a source to find the
	 *            required service by looking through its dependencies.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public static Class<?> registerService(URI uri, String qualifiedName) {
		Class<?> clazz = REGISTERED_CLASSES.get(qualifiedName);
		if (clazz != null) {
			return clazz;
		}

		if (uri.isPlatformPlugin()) {
			final String bundleName = uri.segment(1);
			final Bundle bundle = Platform.getBundle(bundleName);
			if (bundle != null) {
				clazz = registerService(bundle, qualifiedName);
			}
		} else {
			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			String projectName = null;
			if (uri.isPlatformResource()) {
				projectName = uri.segment(1);
			} else {
				final URI workspaceRootURI = URI.createURI(URI.decode(workspaceRoot.getLocationURI()
						.toString()));
				URI workspaceRelative = uri.deresolve(workspaceRootURI);
				if (!workspaceRelative.equals(uri)) {
					// Have a look at the workspaceRelative uri to see if it starts with "../../"
					workspaceRelative = trimDoubleDot(workspaceRelative);

					projectName = workspaceRelative.segment(1);
				}
			}
			if (projectName != null) {
				final IProject project = workspaceRoot.getProject(projectName);
				if (project != null && project.exists()) {
					clazz = registerService(project, qualifiedName);
				}
			}
			if (clazz == null) {
				clazz = workspaceSuffixWorkaround(uri, qualifiedName);
			}
		}
		// This is our last, most costly ... but most effective test
		if (clazz == null) {
			BundleURLConverter converter = new BundleURLConverter(uri.toString());
			Bundle bundle = converter.resolveInBundle(qualifiedName);
			if (bundle != null) {
				clazz = registerService(bundle, qualifiedName);
			}
		}
		if (clazz != null) {
			REGISTERED_SERVICES.add(qualifiedName);
		}
		return clazz;
	}

	/**
	 * Trims the ".." at the beginning of the uri.
	 * 
	 * @param workspaceRelative
	 *            The given uri
	 * @return The uri without the ".." at the beginning.
	 */
	private static URI trimDoubleDot(URI workspaceRelative) {
		int segmentToTrim = 0;
		String[] segments = workspaceRelative.segments();
		for (String segment : segments) {
			if ("..".equals(segment)) { //$NON-NLS-1$
				segmentToTrim++;
			} else {
				break;
			}
		}

		if (segmentToTrim != 0 && workspaceRelative.segments().length > (segmentToTrim * 2)) {
			String[] newSegments = new String[segments.length - (segmentToTrim * 2)];
			System.arraycopy(segments, segmentToTrim * 2, newSegments, 0, segments.length
					- (segmentToTrim * 2));

			if (newSegments.length > 0) {
				URI uriTmp = URI.createURI(newSegments[0]);
				for (int i = 1; i < newSegments.length; i++) {
					uriTmp = uriTmp.appendSegment(newSegments[i]);
				}
				return uriTmp;
			}
		}
		return workspaceRelative;
	}

	/**
	 * This is a workaround for workspace with same suffix.
	 * 
	 * @param uri
	 *            URI of the module currently being evaluated. This will be used as a source to find the
	 *            required service by looking through its dependencies.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	private static Class<?> workspaceSuffixWorkaround(URI uri, String qualifiedName) {
		Class<?> clazz = null;
		String resolvedURI = AcceleoWorkspaceUtil.resolveAsPlatformPlugin(URI.decode(uri.toString()));
		URI platformURI = null;
		if (resolvedURI != null) {
			platformURI = URI.createURI(resolvedURI);
		}
		if (platformURI != null) {
			String bundleName = platformURI.segment(1);
			Bundle bundle = Platform.getBundle(bundleName);
			if (bundle != null) {
				clazz = registerService(bundle, qualifiedName);
			}
		}
		return clazz;
	}
}
