/*
 * Copyright (c) 2005, 2012 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 */
package org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.statements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.statements.Feature;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.statements.FeatureParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Feature parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class FeatureParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			FeatureParser.createFeature(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException.");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testString() {
		String buffer = "call"; //$NON-NLS-1$
		try {
			Feature feature = FeatureParser.createFeature(0, buffer, new Region(0, buffer.length()), null);
			assertTrue("Should contain a CallSet.", CallSet.class.isAssignableFrom(feature.getExpression()
					.getClass()));
			assertEquals(
					"The contained call should be \"call\".", buffer, ((CallSet)feature.getExpression()).getCalls().get(0).getName()); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, feature.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), feature.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
