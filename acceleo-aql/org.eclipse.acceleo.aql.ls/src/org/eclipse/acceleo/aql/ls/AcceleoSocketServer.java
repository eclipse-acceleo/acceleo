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
package org.eclipse.acceleo.aql.ls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

/**
 * {@link AcceleoLanguageServer} over a socket.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoSocketServer {

	/**
	 * The {@link ServerSocket}.
	 */
	private ServerSocket server;

	/**
	 * The server {@link Thread}.
	 */
	private Thread serverThread;

	public synchronized void start(String host, int port) throws UnknownHostException, IOException {
		server = new ServerSocket(port, 50, InetAddress.getByName(host));
		serverThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						final Socket client = server.accept();
						final Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(
								new AcceleoLanguageServer(), client.getInputStream(), client
										.getOutputStream());
						launcher.startListening(); // the thread is created inside this method
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, "Acceleo LS: " + server.getInetAddress().getHostName() + ":" + server.getLocalPort());
		serverThread.start();
	}

	public synchronized void stop() throws IOException {
		server.close();
		serverThread.interrupt();
	}

}
