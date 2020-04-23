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
package org.eclipse.acceleo.aql.location.aql;

import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.ecore.EObject;

/**
 * An {@link AbstractAqlLocationLink} implementation that can point to any {@link Object}.
 * 
 * @author Florent Latombe
 */
public class AqlLocationLinkToAny extends AbstractAqlLocationLink<Object> {

	/**
	 * Constructor.
	 * 
	 * @param fromElement
	 *            the (non-{@code null}) origin {@link EObject} ({@link Expression} or
	 *            {@link VariableDeclaration}) of this link.
	 * @param toElement
	 *            the (non-{@code null}) destination {@link Object} of this link.
	 */
	public AqlLocationLinkToAny(EObject fromElement, Object toElement) {
		super(fromElement, toElement);
	}

}
