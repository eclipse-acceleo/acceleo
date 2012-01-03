/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.compatibility;

/**
 * This enum will grow as API breakages arise and compatibility needs deem new enum constants necessary.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public enum OCLVersion {
	/**
	 * This constant will be set as the current version under Ganymede (3.4).
	 */
	GANYMEDE,

	/**
	 * This constant will be set as the current version under Galileo (3.5).
	 */
	GALILEO,

	/**
	 * This constant will be set as the current version under Helios (3.6).
	 */
	HELIOS,
}
