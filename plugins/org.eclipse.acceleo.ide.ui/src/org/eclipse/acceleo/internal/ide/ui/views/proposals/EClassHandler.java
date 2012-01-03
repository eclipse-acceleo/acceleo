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
package org.eclipse.acceleo.internal.ide.ui.views.proposals;

import org.eclipse.emf.ecore.EClass;

/**
 * An EClass handler. The goal of this class is to handle an EClass object in several handlers to support
 * duplicated elements in one tree viewer.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class EClassHandler {

	/**
	 * The EClass.
	 */
	private EClass eClass;

	/**
	 * Constructor.
	 * 
	 * @param eClass
	 *            is the EClass
	 */
	public EClassHandler(EClass eClass) {
		this.eClass = eClass;
	}

	/**
	 * Gets the EClass.
	 * 
	 * @return the EClass
	 */
	public EClass getEClass() {
		return eClass;
	}
}
