/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.maven;

/**
 * Utility class mapping a classpath entry of an Acceleo project in the pom.xml.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class Entry {
	/**
	 * The input folder.
	 */
	private String input;

	/**
	 * The output folder.
	 */
	private String output;

	/**
	 * Returns the input folder.
	 * 
	 * @return The input folder.
	 */
	public String getInput() {
		return input;
	}

	/**
	 * Returns the output folder.
	 * 
	 * @return The output folder.
	 */
	public String getOutput() {
		return output;
	}
}
