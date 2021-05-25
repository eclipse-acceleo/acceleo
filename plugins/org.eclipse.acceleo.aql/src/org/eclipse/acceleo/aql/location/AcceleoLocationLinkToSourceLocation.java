/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;

/**
 * An {@link AbstractAcceleoLocationLink} that points to an {@link ASTNode}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoLocationLinkToSourceLocation extends AbstractAcceleoLocationLink<ISourceLocation> {

	/**
	 * The constructor.
	 * 
	 * @param fromSemanticElement
	 *            the (non-{@code null}) origin {@link ASTNode}.
	 * @param sourceLocation
	 *            the (non-{@code null}) destination {@link ISourceLocation}.
	 */
	public AcceleoLocationLinkToSourceLocation(ASTNode fromSemanticElement, ISourceLocation sourceLocation) {
		super(fromSemanticElement, sourceLocation);
	}

}
