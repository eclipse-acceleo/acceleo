/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug.ide;

import org.eclipse.acceleo.debug.ls.DSLDebugSocketServer;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleContext;

/**
 * The Acceleo debugger activator.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class AcceleoDebugPlugin extends EMFPlugin {

	/**
	 * The instance.
	 */
	public static final AcceleoDebugPlugin INSTANCE = new AcceleoDebugPlugin();

	/**
	 * The plug-ing ID.
	 */
	public static final String ID = "org.eclipse.acceleo.aql.ls.debug.ide";

	/**
	 * The implementation.
	 */
	private static Implementation plugin;

	/**
	 * Create the instance. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AcceleoDebugPlugin() {
		super(new ResourceLocator[] {});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static class Implementation extends EclipsePlugin {

		/**
		 * The {@link DSLDebugSocketServer}.
		 */
		private DSLDebugSocketServer server;

		/**
		 * The host to listen from.
		 */
		private static final String HOST = "127.0.0.1";

		/**
		 * Creates an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}

		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);

			server = new DSLDebugSocketServer();
			server.start(new AcceleoDebugFactory(), "Acceleo", HOST, 0);
		}

		/**
		 * Gets the port the server is listening on.
		 * 
		 * @return the port the server is listening on
		 */
		public int getPort() {
			return server.getLocalPort();
		}

		/**
		 * Gets the host the server is listening from.
		 * 
		 * @return the host the server is listening from
		 */
		public String getHost() {
			return HOST;
		}

		@Override
		public void stop(BundleContext context) throws Exception {
			super.stop(context);
			server.stop();
		}
	}

}
