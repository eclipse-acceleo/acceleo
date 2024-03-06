/*******************************************************************************
 *  Copyright (c) 2017, 2024 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/

package org.eclipse.acceleo.aql.ide;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * Plugin's activator class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoPlugin extends EMFPlugin {

	/**
	 * Plugin's id.
	 */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.aql.ide"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	public static final AcceleoPlugin INSTANCE = new AcceleoPlugin();

	/**
	 * The implementation plugin for Eclipse.
	 */
	private static Implementation plugin;

	/**
	 * The constructor.
	 */
	public AcceleoPlugin() {
		super(new ResourceLocator[] {});
	}

	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * Class implementing the EclipsePlugin instance, instanciated when the code is run in an OSGi context.
	 * 
	 * @author cedric
	 */
	public static class Implementation extends EclipsePlugin {

		/**
		 * Create the Eclipse Implementation.
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}

	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance.
	 */
	public static AcceleoPlugin getDefault() {
		return INSTANCE;
	}

	/**
	 * Logs the given exception as error or warning.
	 * 
	 * @param exception
	 *            The exception to log.
	 * @param blocker
	 *            <code>True</code> if the message must be logged as error, <code>False</code> to log it as a
	 *            warning.
	 */
	public static void log(Exception exception, boolean blocker) {
		int severity = IStatus.WARNING;
		if (blocker) {
			severity = IStatus.ERROR;
		}
		AcceleoPlugin.INSTANCE.log(new Status(severity, PLUGIN_ID, exception.getMessage(), exception));
	}

	/**
	 * Puts the given message in the error log view, as error or warning.
	 * 
	 * @param message
	 *            The message to put in the error log view.
	 * @param blocker
	 *            <code>True</code> if the message must be logged as error, <code>False</code> to log it as a
	 *            warning.
	 */
	public static void log(String message, boolean blocker) {
		int severity = IStatus.WARNING;
		if (blocker) {
			severity = IStatus.ERROR;
		}
		String errorMessage = message;
		if (errorMessage == null || "".equals(errorMessage)) { //$NON-NLS-1$
			errorMessage = "Logging null message should never happens."; //$NON-NLS-1$
		}
		AcceleoPlugin.INSTANCE.log(new Status(severity, PLUGIN_ID, errorMessage));
	}

	/**
	 * Tells if the given resource is a Acceleo {@link Module} with a {@link Template#isMain() main template}.
	 * 
	 * @param resource
	 *            the {@link IResource}
	 * @return <code>true</code> if the given resource is a Acceleo {@link Module} with a
	 *         {@link Template#isMain() main template}, <code>false</code> otherwise
	 */
	public static boolean isAcceleoMain(IResource resource) {
		boolean res = false;

		final AcceleoParser parser = new AcceleoParser();
		final IFile file = (IFile)resource;
		if (file.isAccessible()) {
			try (InputStream contents = file.getContents()) {
				final Module module = parser.parse(contents, Charset.forName(file.getCharset()), "none")
						.getModule();
				for (ModuleElement element : module.getModuleElements()) {
					if (element instanceof Template && ((Template)element).isMain()) {
						res = true;
						break;
					}
				}
			} catch (IOException e) {
				AcceleoPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoPlugin.PLUGIN_ID,
						"couldn't parse module " + resource.getFullPath(), e));
			} catch (CoreException e) {
				AcceleoPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoPlugin.PLUGIN_ID,
						"couldn't parse module " + resource.getFullPath(), e));
			}
		} else {
			res = false;
		}

		return res;
	}

}
