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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;

/**
 * Helper class for match pairs of special characters.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoPairMatcher extends DefaultCharacterPairMatcher {

	/**
	 * Blocks used by aggregated the matcher.
	 */
	protected static final char[] BLOCKS = {'(', ')', '[', ']', '{', '}' };

	/**
	 * The aggregated matcher, it is used for trivial block matching.
	 */
	protected DefaultCharacterPairMatcher aggregatedMatcher;

	/**
	 * Constructor.
	 */
	public AcceleoPairMatcher() {
		super(new char[0]);
		aggregatedMatcher = new DefaultCharacterPairMatcher(BLOCKS);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.DefaultCharacterPairMatcher#match(org.eclipse.jface.text.IDocument,
	 *      int)
	 */
	@Override
	public IRegion match(IDocument document, int offset) {
		try {
			return performMatch(document, offset);
		} catch (BadLocationException ble) {
			AcceleoUIActivator.log(ble, true);
			return null;
		}
	}

	/**
	 * Performs the actual work of matching for #match(IDocument, int).
	 * 
	 * @param document
	 *            is the document
	 * @param offset
	 *            is the current offset
	 * @return the region
	 * @throws BadLocationException
	 *             indicates the attempt to access a non-existing position
	 */
	private IRegion performMatch(IDocument document, int offset) throws BadLocationException {
		if (offset < 0 || document == null) {
			return null;
		}
		return aggregatedMatcher.match(document, offset);
	}

}
