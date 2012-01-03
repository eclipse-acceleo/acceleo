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

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.impl.GeneratedTextImpl;

/**
 * This specific implementation of the {@link org.eclipse.acceleo.traceability.GeneratedText} will deal with
 * non generated bits.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class GeneratedTextSpec extends GeneratedTextImpl {
	/** Cache the hash code, we'll reinitialize this cache whenever one of the fields change. */
	private transient int hashCode;

	/**
	 * {@inheritDoc}
	 * 
	 * @see GeneratedTextImpl#setEndOffset(int)
	 */
	@Override
	public void setEndOffset(int newEndOffset) {
		hashCode = 0;
		super.setEndOffset(newEndOffset);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GeneratedTextImpl#setModuleElement(ModuleElement)
	 */
	@Override
	public void setModuleElement(ModuleElement newModuleElement) {
		hashCode = 0;
		super.setModuleElement(newModuleElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GeneratedTextImpl#setOutputFile(GeneratedFile)
	 */
	@Override
	public void setOutputFile(GeneratedFile newOutputFile) {
		hashCode = 0;
		super.setOutputFile(newOutputFile);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GeneratedTextImpl#setSourceElement(InputElement)
	 */
	@Override
	public void setSourceElement(InputElement newSourceElement) {
		hashCode = 0;
		super.setSourceElement(newSourceElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GeneratedTextImpl#setStartOffset(int)
	 */
	@Override
	public void setStartOffset(int newStartOffset) {
		hashCode = 0;
		super.setStartOffset(newStartOffset);
	}

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

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (hashCode != 0) {
			return hashCode;
		}

		final int prime = 31;
		hashCode = prime;

		int tmp = startOffset << prime;
		hashCode = prime * hashCode + (startOffset * tmp);
		if (moduleElement == null) {
			hashCode = prime * hashCode;
		} else {
			hashCode = prime * hashCode + moduleElement.hashCode();
		}
		if (getOutputFile() == null) {
			hashCode = prime * hashCode;
		} else {
			hashCode = prime * hashCode + getOutputFile().hashCode();
		}
		if (sourceElement == null) {
			hashCode = prime * hashCode;
		} else {
			hashCode = prime * hashCode + sourceElement.hashCode();
		}
		tmp = endOffset << prime;
		hashCode = prime * hashCode + (endOffset * tmp);

		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object other) {
		boolean result = this == other;
		if (!result && other instanceof GeneratedText) {
			if (((GeneratedText)other).getStartOffset() == startOffset
					&& ((GeneratedText)other).getEndOffset() == endOffset) {
				result = true;
				if (getOutputFile() == null) {
					result = ((GeneratedText)other).getOutputFile() == null;
				} else {
					GeneratedFile otherOutputFile = ((GeneratedText)other).getOutputFile();
					result = otherOutputFile != null && otherOutputFile.equals(getOutputFile());
				}
				if (result && moduleElement == null) {
					result = ((GeneratedText)other).getModuleElement() == null;
				} else if (result) {
					ModuleElement otherModuleElement = ((GeneratedText)other).getModuleElement();
					result = otherModuleElement != null && otherModuleElement.equals(moduleElement);
				}
				if (result && sourceElement == null) {
					result = ((GeneratedText)other).getSourceElement() == null;
				} else if (result) {
					InputElement otherSourceElement = ((GeneratedText)other).getSourceElement();
					result = otherSourceElement != null && otherSourceElement.equals(sourceElement);
				}
			}
		}
		return result;
	}
}
