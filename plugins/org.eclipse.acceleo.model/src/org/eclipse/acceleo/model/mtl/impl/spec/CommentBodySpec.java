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
package org.eclipse.acceleo.model.mtl.impl.spec;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl;

/**
 * Specializes the comment body implementation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class CommentBodySpec extends CommentBodyImpl {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(IAcceleoConstants.COMMENT + ' ' + this.getStartPosition() + '-' + this.getEndPosition());
		return result.toString();
	}
}
