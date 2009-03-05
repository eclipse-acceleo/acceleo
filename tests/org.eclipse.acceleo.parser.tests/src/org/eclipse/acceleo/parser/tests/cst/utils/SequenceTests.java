/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.cst.utils;

import junit.framework.TestCase;

import org.eclipse.acceleo.internal.parser.cst.utils.Region;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.internal.parser.cst.utils.SequenceBlock;

public class SequenceTests extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSequence() {
		StringBuffer buffer = new StringBuffer("abcde");
		assertEquals(new Sequence("a").search(buffer).b(), 0);
		assertEquals(new Sequence("abcde").search(buffer).b(), 0);
		assertEquals(new Sequence("f").search(buffer).b(), -1);

		Sequence seq = new Sequence("a");
		assertEquals(seq.search(buffer), new Region(0, 1, seq));
		assertEquals(seq.search(buffer).toString(), "[0,1]");
		seq = new Sequence("f");
		assertEquals(seq.search(buffer), Region.NOT_FOUND);
	}

	public void testSequenceBlock() {
		StringBuffer buffer = new StringBuffer("a((h)g)h");
		SequenceBlock parenthesis = new SequenceBlock(new Sequence("("), new Sequence(")"), null, true, null);
		assertEquals(new Sequence("g").search(buffer, 0, buffer.length(), null,
				new SequenceBlock[] {parenthesis}).b(), -1);
		assertEquals(new Sequence("h").search(buffer, 0, buffer.length(), null,
				new SequenceBlock[] {parenthesis}).b(), 7);
	}

}
