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
import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;

/**
 * Represents a link from an Acceleo {@link ASTNode} to a {@link DESTINATION_TYPE}. TODO: we may need to
 * generify the origin to be able to create AQL links?
 * 
 * @author Florent Latombe
 * @param <DESTINATION_TYPE>
 *            the destination type of this link.
 */
public abstract class AbstractAcceleoLocationLink<DESTINATION_TYPE> extends AbstractLocationLink<ASTNode, DESTINATION_TYPE> {

	/**
	 * Constructor.
	 * 
	 * @param fromSemanticElement
	 *            the (non-{@code null}) origin {@link ASTNode}.
	 * @param toSemanticElement
	 *            the (non-{@code null}) destination {@link DESTINATION_TYPE}.
	 */
	public AbstractAcceleoLocationLink(ASTNode fromSemanticElement, DESTINATION_TYPE toSemanticElement) {
		super(fromSemanticElement, toSemanticElement);
	}
}
