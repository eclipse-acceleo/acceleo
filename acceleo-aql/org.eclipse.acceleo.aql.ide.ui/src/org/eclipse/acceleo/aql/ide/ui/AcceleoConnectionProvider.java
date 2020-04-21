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
package org.eclipse.acceleo.aql.ide.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.eclipse.acceleo.aql.ls.AcceleoSocketServer;
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
	 * The port.
	 */
	private static final int PORT = 30000;

	/**
	 * The {@link AcceleoSocketServer}.
	 */
	// TODO we probably want to start this once for all...
	private AcceleoSocketServer socketServer = new AcceleoSocketServer();

	/**
	 * The client {@link Socket}.
	 */
	private Socket socketClient;

	/**
	 * Constructor.
	 */
	public AcceleoConnectionProvider() {
	}

	@Override
	public synchronized void start() throws IOException {
		// TODO Might need to be made a constant and made available from other classes?
		socketServer.start(HOST, PORT);
		socketClient = new Socket(InetAddress.getByName(HOST), PORT);
	}

	@Override
	public synchronized void stop() {
		try {
			socketClient.close();
			socketServer.stop();
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
