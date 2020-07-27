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

import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.ecore.EObject;

/**
 * A partial {@link AbstractLocationLink} implementation whose origin is an AQL AST node, that is an
 * {@link Expression} or a {@link VariableDeclaration}.
 * 
 * @author Florent Latombe
 * @param <DESTINATION_TYPE>
 *            the type of destination this link points to.
 */
public abstract class AbstractAqlLocationLink<DESTINATION_TYPE> extends AbstractLocationLink<EObject, DESTINATION_TYPE> {

	/**
	 * Constructor.
	 * 
	 * @param fromElement
	 *            the (non-{@code null}) origin {@link EObject} ({@link Expression} or
	 *            {@link VariableDeclaration} of this link.
	 * @param toElement
	 *            the (non-{@code null}) destination {@link DESTINATION_TYPE} of this link.
	 */
	public AbstractAqlLocationLink(EObject fromElement, DESTINATION_TYPE toElement) {
		super(fromElement, toElement);
	}

}
