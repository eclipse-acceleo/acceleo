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
	 * Backlog for the socket server.
	 */
	private static final int BACKLOG = 50;

	/**
	 * The {@link ServerSocket}.
	 */
	private ServerSocket serverSocket;

	/**
	 * The server {@link Thread}.
	 */
	private Thread serverThread;

	/**
	 * Starts this server.
	 * 
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public synchronized void start(String host, int port) throws UnknownHostException, IOException {
		serverSocket = new ServerSocket(port, BACKLOG, InetAddress.getByName(host));
		serverThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					final Socket client = serverSocket.accept();
					final AcceleoLanguageServer acceleoLanguageServer = new AcceleoLanguageServer();
					final Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(
							acceleoLanguageServer, client.getInputStream(), client.getOutputStream());
					launcher.startListening(); // the thread is created inside this method
					acceleoLanguageServer.connect(launcher.getRemoteProxy());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "Acceleo LS: " + serverSocket.getInetAddress().getHostName() + ":" + serverSocket.getLocalPort());
		serverThread.start();
	}

	/**
	 * Stops this server.
	 * 
	 * @throws IOException
	 *             if an I/O error occurs when stopping the server.
	 */
	public synchronized void stop() throws IOException {
		serverSocket.close();
		serverThread.interrupt();
	}

}