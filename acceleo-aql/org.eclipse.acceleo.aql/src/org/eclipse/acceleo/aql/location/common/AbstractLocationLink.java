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
package org.eclipse.acceleo.aql.location.common;

import java.util.Objects;

import org.eclipse.acceleo.ASTNode;

/**
 * A link from an {@link ORIGIN_TYPE} element to a {@link DESTINATION_TYPE} element.
 * 
 * @author Florent Latombe
 */
public abstract class AbstractLocationLink<ORIGIN_TYPE, DESTINATION_TYPE> {

	/**
	 * The element from which the link starts.
	 */
	private final ORIGIN_TYPE origin;

	/**
	 * The element this links to.
	 */
	private final DESTINATION_TYPE destination;

	/**
	 * Constructor for an {@link AbstractLocationLink} that points to the given {@link ORIGIN_TYPE}.
	 * 
	 * @param fromElement
	 *            the (non-{@code null}) {@link ORIGIN_TYPE} from which this link departs.
	 * @param toElement
	 *            the (non-{@code null}) {@link DESTINATION_TYPE} to point to.
	 */
	public AbstractLocationLink(ORIGIN_TYPE fromElement, DESTINATION_TYPE toElement) {
		this.origin = Objects.requireNonNull(fromElement);
		this.destination = Objects.requireNonNull(toElement);
	}

	/**
	 * Provides the {@link ORIGIN_TYPE} from which this link departs.
	 * 
	 * @return the (non-{@code null}) {@link ORIGIN_TYPE} from which this link departs.
	 */
	public ORIGIN_TYPE getOrigin() {
		return this.origin;
	}

	/**
	 * Provides the {@link ASTNode} pointed by this link.
	 * 
	 * @return the (non-{@code null}) {@link ASTNode} pointed by this.
	 */
	public DESTINATION_TYPE getDestination() {
		return this.destination;
	}
}
