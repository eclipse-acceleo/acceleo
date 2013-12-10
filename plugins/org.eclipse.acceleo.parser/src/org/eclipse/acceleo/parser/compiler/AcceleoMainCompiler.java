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
package org.eclipse.acceleo.parser.compiler;

import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;

/**
 * The main compiler.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
@Deprecated
public final class AcceleoMainCompiler {

	/**
	 * The constructor.
	 */
	private AcceleoMainCompiler() {
		// prevent instantiation.
	}

	/**
	 * The entry point of the compilation.
	 * 
	 * @param args
	 *            The arguments.
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			throw new IllegalArgumentException(AcceleoParserMessages
					.getString("AcceleoMainCompiler.NotEnoughParameters")); //$NON-NLS-1$
		}
		AcceleoCompilerHelper acceleoCompilerHelper = new AcceleoCompilerHelper();
		acceleoCompilerHelper.setSourceFolder(args[0]);
		acceleoCompilerHelper.setOutputFolder(args[1]);
		acceleoCompilerHelper.setBinaryResource(Boolean.valueOf(args[2]).booleanValue());
		if (args.length == 4 && args[3] != null && !"".equals(args[3])) { //$NON-NLS-1$
			acceleoCompilerHelper.setDependencies(args[3]);
		}
		acceleoCompilerHelper.execute();
	}

}
