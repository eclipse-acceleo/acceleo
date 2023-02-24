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
package org.eclipse.acceleo.debug.ls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.acceleo.debug.IDSLDebugger;
import org.eclipse.lsp4j.debug.launch.DSPLauncher;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.jsonrpc.Launcher;

/**
 * {@link AcceleoLanguageServer} over a socket.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DSLDebugSocketServer {

	/**
	 * The size of the listening socket backlog.
	 */
	private static final int BACKLOG_SIZE = 50;

	/**
	 * The {@link ServerSocket}.
	 */
	private ServerSocket serverSocket;

	/**
	 * The server {@link Thread}.
	 */
	private Thread serverThread;

	/**
	 * Starts the server.
	 * 
	 * @param dslDebugFactory
	 *            the {@link IDSLDebuggerFactory}
	 * @param language
	 *            the language name
	 * @param host
	 *            the host to listen to
	 * @param port
	 *            the port to listen to
	 * @throws UnknownHostException
	 *             if the host is unknown
	 * @throws IOException
	 *             if the listening socket can't be created
	 */
	public synchronized void start(final IDSLDebuggerFactory dslDebugFactory, final String language,
			String host, int port) throws UnknownHostException, IOException {
		serverSocket = new ServerSocket(port, BACKLOG_SIZE, InetAddress.getByName(host));
		serverThread = new Thread(new Runnable() {

			public void run() {
				while (!serverSocket.isClosed() && serverSocket.isBound()) {
					try {
						final Socket client = serverSocket.accept();
						final DSLDebugServer dslDebugServer = new DSLDebugServer();
						final IDSLDebugger debugger = dslDebugFactory.createDebugger(dslDebugServer);
						dslDebugServer.setDebugger(debugger);
						final Launcher<IDebugProtocolClient> launcher = DSPLauncher.createServerLauncher(
								dslDebugServer, client.getInputStream(), client.getOutputStream());
						dslDebugServer.connect(launcher.getRemoteProxy());
						launcher.startListening(); // the thread is created inside this method
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, language + " Debug Server: " + serverSocket.getInetAddress().getHostName() + ":" + serverSocket
				.getLocalPort());
		serverThread.start();
	}

	/**
	 * Stops the server.
	 * 
	 * @throws IOException
	 *             if the listening socket can't be closed
	 */
	public synchronized void stop() throws IOException {
		serverThread.interrupt();
		serverSocket.close();
	}

	/**
	 * Gets the local port.
	 * 
	 * @return the local port
	 */
	public int getLocalPort() {
		return serverSocket.getLocalPort();
	}

}
