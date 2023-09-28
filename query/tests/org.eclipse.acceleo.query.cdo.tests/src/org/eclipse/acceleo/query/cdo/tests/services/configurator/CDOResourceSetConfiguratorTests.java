/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.cdo.tests.services.configurator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.acceleo.query.cdo.AqlCDOUtils;
import org.eclipse.acceleo.query.cdo.services.configurator.CDOResourceSetConfigurator;
import org.eclipse.acceleo.query.cdo.tests.CDOServer;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.internal.cdo.view.CDOViewSetPackageRegistryImpl;
import org.eclipse.net4j.signal.RemoteException;
import org.eclipse.net4j.util.lifecycle.LifecycleException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link CDOResourceSetConfigurator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CDOResourceSetConfiguratorTests {

	@Test(expected = LifecycleException.class)
	public void wrongServer() {
		// NOTE: the server is not started on purpose
		final CDOResourceSetConfigurator configurator = new CDOResourceSetConfigurator();
		final Map<String, String> options = new LinkedHashMap<String, String>();
		options.put(AqlCDOUtils.CDO_SERVER_OPTION, "tcp://" + CDOServer.IP + ":" + CDOServer.PORT);
		options.put(AqlCDOUtils.CDO_REPOSITORY_OPTION, CDOServer.REPOSITORY_NAME);
		options.put(AqlCDOUtils.CDO_LOGIN_OPTION, CDOServer.USER_NAME);
		options.put(AqlCDOUtils.CDO_PASSWORD_OPTION, "WrongPassword");

		final ResourceSet rs = configurator.createResourceSetForModels(this, options);
		configurator.cleanResourceSetForModels(this);
	}

	@Test
	public void withAuth() {
		final CDOServer server = new CDOServer(true);
		server.start();
		try {
			final CDOResourceSetConfigurator configurator = new CDOResourceSetConfigurator();
			final Map<String, String> options = new LinkedHashMap<String, String>();
			options.put(AqlCDOUtils.CDO_SERVER_OPTION, "tcp://" + CDOServer.IP + ":" + CDOServer.PORT);
			options.put(AqlCDOUtils.CDO_REPOSITORY_OPTION, CDOServer.REPOSITORY_NAME);
			options.put(AqlCDOUtils.CDO_LOGIN_OPTION, CDOServer.USER_NAME);
			options.put(AqlCDOUtils.CDO_PASSWORD_OPTION, CDOServer.PASSWORD);

			final ResourceSet rs = configurator.createResourceSetForModels(this, options);
			configurator.cleanResourceSetForModels(this);

			assertEquals(true, rs.getPackageRegistry() instanceof CDOViewSetPackageRegistryImpl);
		} finally {
			server.stop();
		}
	}

	@Test(expected = SecurityException.class)
	public void withAuthWrongPassword() {
		final CDOServer server = new CDOServer(true);
		server.start();
		try {
			final CDOResourceSetConfigurator configurator = new CDOResourceSetConfigurator();
			final Map<String, String> options = new LinkedHashMap<String, String>();
			options.put(AqlCDOUtils.CDO_SERVER_OPTION, "tcp://" + CDOServer.IP + ":" + CDOServer.PORT);
			options.put(AqlCDOUtils.CDO_REPOSITORY_OPTION, CDOServer.REPOSITORY_NAME);
			options.put(AqlCDOUtils.CDO_LOGIN_OPTION, CDOServer.USER_NAME);
			options.put(AqlCDOUtils.CDO_PASSWORD_OPTION, "WrongPassword");

			final ResourceSet rs = configurator.createResourceSetForModels(this, options);
			configurator.cleanResourceSetForModels(this);
		} finally {
			server.stop();
		}
	}

	@Test
	public void withoutAuth() {
		final CDOServer server = new CDOServer(false);
		server.start();
		try {
			final CDOResourceSetConfigurator configurator = new CDOResourceSetConfigurator();
			final Map<String, String> options = new LinkedHashMap<String, String>();
			options.put(AqlCDOUtils.CDO_SERVER_OPTION, "tcp://" + CDOServer.IP + ":" + CDOServer.PORT);
			options.put(AqlCDOUtils.CDO_REPOSITORY_OPTION, CDOServer.REPOSITORY_NAME);

			final ResourceSet rs = configurator.createResourceSetForModels(this, options);
			configurator.cleanResourceSetForModels(this);

			assertEquals(true, rs.getPackageRegistry() instanceof CDOViewSetPackageRegistryImpl);
		} finally {
			server.stop();
		}
	}

	@Test(expected = RemoteException.class)
	public void withoutAuthWrongRepo() {
		final CDOServer server = new CDOServer(true);
		server.start();
		try {
			final CDOResourceSetConfigurator configurator = new CDOResourceSetConfigurator();
			final Map<String, String> options = new LinkedHashMap<String, String>();
			options.put(AqlCDOUtils.CDO_SERVER_OPTION, "tcp://" + CDOServer.IP + ":" + CDOServer.PORT);
			options.put(AqlCDOUtils.CDO_REPOSITORY_OPTION, "WrongRepository");
			options.put(AqlCDOUtils.CDO_LOGIN_OPTION, CDOServer.USER_NAME);
			options.put(AqlCDOUtils.CDO_PASSWORD_OPTION, CDOServer.PASSWORD);

			final ResourceSet rs = configurator.createResourceSetForModels(this, options);
			configurator.cleanResourceSetForModels(this);
		} finally {
			server.stop();
		}
	}

}
