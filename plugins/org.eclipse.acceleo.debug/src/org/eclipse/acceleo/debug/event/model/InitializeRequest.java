/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.model;

import java.util.Map;

/**
 * Request sent to initialize the debugger.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class InitializeRequest implements IDSLModelRequest {

	/**
	 * If <code>true</code> start without debugging.
	 */
	private boolean noDebug;

	/**
	 * Mapping from argument name to its value.
	 */
	private final Map<String, Object> arguments;

	/**
	 * Constructor.
	 * 
	 * @param noDebug
	 *            <code>true</code> if no debug is needed
	 * @param arguments
	 *            the {@link Map} of arguments
	 */
	public InitializeRequest(boolean noDebug, Map<String, Object> arguments) {
		this.noDebug = noDebug;
		this.arguments = arguments;
	}

	/**
	 * Tells if debbug is needed.
	 * 
	 * @return <code>false</code> if debbug is needed, <code>true</code> otherwise
	 */
	public boolean isNoDebug() {
		return noDebug;
	}

	/**
	 * Gets the {@link Map} of arguments.
	 * 
	 * @return the {@link Map} of arguments
	 */
	public Map<String, Object> getArguments() {
		return arguments;
	}

}
