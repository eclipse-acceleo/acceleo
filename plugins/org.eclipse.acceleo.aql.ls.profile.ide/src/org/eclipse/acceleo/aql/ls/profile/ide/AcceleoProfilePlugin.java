/*******************************************************************************
 * Copyright (c) 2020 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.profile.ide;

import org.eclipse.acceleo.aql.ls.debug.ide.AcceleoDebugFactory;
import org.eclipse.acceleo.aql.ls.profile.AcceleoProfiler;
import org.eclipse.acceleo.debug.IDSLDebugger;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.debug.ls.DSLDebugSocketServer;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleContext;

/**
 * The Acceleo profiler activator.
 * 
 * @author wpiers
 */
public final class AcceleoProfilePlugin extends EMFPlugin {

	/**
	 * The instance.
	 */
	public static final AcceleoProfilePlugin INSTANCE = new AcceleoProfilePlugin();

	/**
	 * The server port. TODO should be a property.
	 */
	public static final int PORT = 4701;

	/**
	 * The plug-ing ID.
	 */
	public static final String ID = "org.eclipse.acceleo.aql.ls.profile.ide";

	/**
	 * The implementation.
	 */
	private static Implementation plugin;

	/**
	 * Create the instance. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AcceleoProfilePlugin() {
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
			server.start(new AcceleoDebugFactory() {
				@Override
				public IDSLDebugger createDebugger(IDSLDebugEventProcessor processor) {
					return new AcceleoProfiler(processor);
				}
			}, "Acceleo", "0.0.0.0", PORT);
		}

		@Override
		public void stop(BundleContext context) throws Exception {
			super.stop(context);
			server.stop();
		}
	}

}
