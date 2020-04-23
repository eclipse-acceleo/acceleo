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
package org.eclipse.acceleo.aql.location;

import org.eclipse.acceleo.ASTNode;

/**
 * An {@link AbstractAcceleoLocationLink} that points to an {@link ASTNode}.
 * 
 * @author Florent Latombe
 */
public class AcceleoLocationLinkToAcceleo extends AbstractAcceleoLocationLink<ASTNode> {

	/**
	 * The constructor.
	 * 
	 * @param fromSemanticElement
	 *            the (non-{@code null}) origin {@link ASTNode}.
	 * @param toSemanticElement
	 *            the (non-{@code null}) destination {@link ASTNode}.
	 */
	public AcceleoLocationLinkToAcceleo(ASTNode fromSemanticElement, ASTNode toSemanticElement) {
		super(fromSemanticElement, toSemanticElement);
	}

}
