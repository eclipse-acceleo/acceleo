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
	 * The {@link AcceleoSocketServer}.
	 */
	// TODO we probably want to start this once for all...
	private AcceleoSocketServer server = new AcceleoSocketServer();

	/**
	 * The client {@link Socket}.
	 */
	private Socket client;

	@Override
	public InputStream getErrorStream() {
		return getInputStream();
	}

	@Override
	public InputStream getInputStream() {
		try {
			return client.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OutputStream getOutputStream() {
		try {
			return client.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public synchronized void start() throws IOException {
		// TODO Might need to be made a constant and made available from other classes?
		server.start("127.0.0.1", 30000);
		client = new Socket(InetAddress.getByName("127.0.0.1"), 30000);
	}

	@Override
	public synchronized void stop() {
		try {
			client.close();
			server.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
