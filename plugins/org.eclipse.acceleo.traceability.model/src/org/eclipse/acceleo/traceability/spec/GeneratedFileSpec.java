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
package org.eclipse.acceleo.traceability.spec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.impl.GeneratedFileImpl;

/**
 * This specific implementation of the {@link org.eclipse.acceleo.traceability.GeneratedFile} will deal with
 * non generated bits.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class GeneratedFileSpec extends GeneratedFileImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.GeneratedFileImpl#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(getPath());
		buffer.append("\n\n"); //$NON-NLS-1$
		List<GeneratedText> regions = new ArrayList<GeneratedText>(getGeneratedRegions());
		Collections.sort(regions);
		for (GeneratedText text : regions) {
			buffer.append(text.toString());
			buffer.append(" <--- "); //$NON-NLS-1$
			buffer.append(text.getSourceElement().toString());
			buffer.append("\n"); //$NON-NLS-1$
		}
		return buffer.toString();
	}
}
