/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.compiler;

/**
 * The Acceleo Compiler helper.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoCompilerHelper {

	/**
	 * Launchs the compilation.
	 * 
	 * @param input
	 *            The input
	 * @param output
	 *            The output
	 * @param dependencies
	 *            The dependencies
	 * @param binaryResource
	 *            Indicates if we should use binary resources.
	 */
	public void compile(String input, String output, String dependencies, boolean binaryResource) {
		System.out.println(input + output + dependencies + binaryResource);
	}
}
