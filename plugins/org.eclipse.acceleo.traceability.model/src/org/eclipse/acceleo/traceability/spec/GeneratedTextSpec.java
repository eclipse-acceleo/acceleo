/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.spec;

import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.impl.GeneratedTextImpl;

/**
 * This specific implementation of the {@link org.eclipse.acceleo.traceability.GeneratedText} will deal with
 * non generated bits.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class GeneratedTextSpec extends GeneratedTextImpl implements Comparable<GeneratedText> {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.GeneratedTextImpl#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append('[');
		buffer.append(getStartOffset());
		buffer.append(',');
		buffer.append(getEndOffset());
		buffer.append(']');
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.traceability.impl.GeneratedTextImpl#compareTo(org.eclipse.acceleo.traceability.GeneratedText)
	 */
	@Override
	public int compareTo(GeneratedText o) {
		return getStartOffset() - o.getStartOffset();
	}
}
