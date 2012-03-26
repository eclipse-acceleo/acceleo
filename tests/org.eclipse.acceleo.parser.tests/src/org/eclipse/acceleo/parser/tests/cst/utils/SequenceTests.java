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
package org.eclipse.acceleo.parser.tests.cst.utils;

import static org.junit.Assert.assertEquals;

import org.eclipse.acceleo.internal.parser.cst.utils.Region;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.internal.parser.cst.utils.SequenceBlock;
import org.junit.Test;

public class SequenceTests {

	@Test
	public void testSequence() {
		StringBuffer buffer = new StringBuffer("g.abcde"); //$NON-NLS-1$
		assertEquals(new Sequence("a").search(buffer).b(), 2); //$NON-NLS-1$
		assertEquals(new Sequence("abcde").search(buffer).b(), 2); //$NON-NLS-1$
		assertEquals(new Sequence("f").search(buffer).b(), -1); //$NON-NLS-1$

		Sequence seq = new Sequence("g"); //$NON-NLS-1$
		assertEquals(seq.search(buffer), new Region(0, 1, seq));
		assertEquals(seq.search(buffer).toString(), "[0,1]"); //$NON-NLS-1$
		seq = new Sequence("f"); //$NON-NLS-1$
		assertEquals(seq.search(buffer), Region.NOT_FOUND);
	}

	@Test
	public void testSequenceBlock() {
		StringBuffer buffer = new StringBuffer("a((h)g)h"); //$NON-NLS-1$
		SequenceBlock parenthesis = new SequenceBlock(new Sequence("("), new Sequence(")"), null, true, null); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(new Sequence("g").search(buffer, 0, buffer.length(), null, //$NON-NLS-1$
				new SequenceBlock[] {parenthesis }).b(), -1);
		assertEquals(new Sequence("h").search(buffer, 0, buffer.length(), null, //$NON-NLS-1$
				new SequenceBlock[] {parenthesis }).b(), 7);
	}

}
