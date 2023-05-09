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

import org.eclipse.emf.common.util.URI;

/**
 * Request sent to remove a breakpoint.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class RemoveBreakpointRequest extends AbstractBreakpointRequest {

	/**
	 * Constructor.
	 * 
	 * @param uri
	 *            the {@link URI} pointing the {@link rg.eclipse.emf.ecore.EObject instruction}
	 */
	public RemoveBreakpointRequest(URI uri) {
		super(uri);
	}

}
