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
import org.eclipse.acceleo.model.mtl.impl.DocumentationImpl;

/**
 * Specializes the documentation implementation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class DocumentationSpec extends DocumentationImpl {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		if (this.getBody() != null) {
			result.append(IAcceleoConstants.COMMENT).append(' ').append(this.getBody().getValue());
		} else {
			result.append(IAcceleoConstants.COMMENT);
		}

		result.append(' ' + this.getStartPosition()).append('-').append(this.getEndPosition());
		return result.toString();
	}

}
