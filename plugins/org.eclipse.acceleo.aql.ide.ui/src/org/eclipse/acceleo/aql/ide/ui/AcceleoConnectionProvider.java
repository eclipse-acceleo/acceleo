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
package org.eclipse.acceleo.aql.ide.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.eclipse.acceleo.aql.ls.AcceleoLanguageServerContext;
import org.eclipse.acceleo.aql.ls.AcceleoSocketServer;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.lsp4e.server.StreamConnectionProvider;

/**
 * Acceleo {@link StreamConnectionProvider}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoConnectionProvider implements StreamConnectionProvider {

	/**
	 * The host.
	 */
	private static final String HOST = "127.0.0.1";

	/**
	 * The {@link AcceleoSocketServer}.
	 */
	// TODO we probably want to start this once for all...
	private final AcceleoSocketServer acceleoSocketServer;

	/**
	 * The client {@link Socket}.
	 */
	private Socket socketClient;

	/**
	 * Constructor.
	 */
	public AcceleoConnectionProvider() {
		AcceleoLanguageServerContext acceleoContext = new EclipseAcceleoLanguageServerContext(ResourcesPlugin
				.getWorkspace());
		this.acceleoSocketServer = new AcceleoSocketServer(acceleoContext);
	}

	@Override
	public synchronized void start() throws IOException {
		// TODO Might need to be made a constant and made available from other classes?
		acceleoSocketServer.start(HOST);
		int serverPort = acceleoSocketServer.getPort();
		socketClient = new Socket(InetAddress.getByName(HOST), serverPort);
	}

	@Override
	public synchronized void stop() {
		try {
			socketClient.close();
			acceleoSocketServer.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public InputStream getInputStream() {
		if (this.socketClient != null) {
			try {
				return socketClient.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public OutputStream getOutputStream() {
		if (this.socketClient != null) {
			try {
				return socketClient.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public InputStream getErrorStream() {
		// return getInputStream();
		return null;
	}
}
