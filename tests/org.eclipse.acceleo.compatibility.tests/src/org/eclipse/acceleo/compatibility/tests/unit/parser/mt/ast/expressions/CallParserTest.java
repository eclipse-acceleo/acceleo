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
package org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.eclipse.acceleo.compatibility.model.mt.expressions.Call;
import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.CallParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Call parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class CallParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyCall() {
		String buffer = ""; //$NON-NLS-1$
		try {
			CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testNumericIdentifierCall() {
		String buffer = "1243413"; //$NON-NLS-1$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer, call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Arguments should be empty.", 0, call.getArguments().size());
			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be an empty string.", "", call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testStringIdentifierCall() {
		String buffer = "azerty"; //$NON-NLS-1$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer, call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Arguments should be empty.", 0, call.getArguments().size());
			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be an empty string.", "", call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testStringWithCallSepIdentifierCall() {
		String buffer = "aze" + TemplateConstants.getDefault().getCallSep() + "rty"; //$NON-NLS-1$ //$NON-NLS-2$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer, call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Arguments should be empty.", 0, call.getArguments().size());
			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be an empty string.", "", call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testStringWithArgSepIdentifierCall() {
		String buffer = "aze" + TemplateConstants.getDefault().getArgSep() + "rty"; //$NON-NLS-1$ //$NON-NLS-2$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer, call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Arguments should be empty.", 0, call.getArguments().size());
			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be an empty string.", "", call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testWithPrefixMetamodelCall() {
		String buffer = TemplateConstants.getDefault().getLinkPrefixMetamodel()
				+ TemplateConstants.getDefault().getLinkPrefixSeparator() + "azerty"; //$NON-NLS-1$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer.substring(buffer.indexOf(TemplateConstants.getDefault()
					.getLinkPrefixSeparator())
					+ TemplateConstants.getDefault().getLinkPrefixSeparator().length()), call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Arguments should be empty.", 0, call.getArguments().size());
			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be TemplateConstants.getDefault().getLinkPrefixMetamodel().",
					TemplateConstants.getDefault().getLinkPrefixMetamodel(), call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testWithMultiPrefixMetamodelCall() {
		String buffer = TemplateConstants.getDefault().getLinkPrefixMetamodel()
				+ TemplateConstants.getDefault().getLinkPrefixSeparator()
				+ TemplateConstants.getDefault().getLinkPrefixMetamodel()
				+ TemplateConstants.getDefault().getLinkPrefixSeparator() + "azerty"; //$NON-NLS-1$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer.substring(buffer.indexOf(TemplateConstants.getDefault()
					.getLinkPrefixSeparator())
					+ TemplateConstants.getDefault().getLinkPrefixSeparator().length()), call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Arguments should be empty.", 0, call.getArguments().size());
			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be TemplateConstants.getDefault().LINK_PREFIX_METAMODEL.",
					TemplateConstants.getDefault().getLinkPrefixMetamodel(), call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testWithEmptyArgsCall() {
		String buffer = "azerty()"; //$NON-NLS-1$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer.substring(0, buffer.indexOf(TemplateConstants.getDefault().getParenth()[0])),
					call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Arguments should be empty.", 0, call.getArguments().size());
			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be an empty string.", "", call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testWithEmptyFilterCall() {
		String buffer = "azerty[filter]"; //$NON-NLS-1$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(
					buffer.substring(0, buffer.indexOf(TemplateConstants.getDefault().getBrackets()[0])),
					call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin());
			assertEquals(
					"Should end at TemplateConstants.getDefault().BRACKETS[0] index.", buffer.indexOf(TemplateConstants.getDefault().getBrackets()[0]), call.getEnd()); //$NON-NLS-1$
			assertEquals("Arguments should be empty.", 0, call.getArguments().size());
			assertEquals(
					"Filter should be \"filter\".", "filter", ((CallSet)call.getFilter()).getCalls().get(0).getName()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("prefix should be an empty string.", "", call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testWithArgsCall() {
		String buffer = "azerty(arg1, arg2, arg3)"; //$NON-NLS-1$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer.substring(0, buffer.indexOf(TemplateConstants.getDefault().getParenth()[0])),
					call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Should have 3 arguments.", 3, call.getArguments().size());
			assertEquals("Argument #1 should be \"arg1\".", "arg1", ((CallSet)call.getArguments().get(0))
					.getCalls().get(0).getName());
			assertEquals("Argument #2 should be \"arg2\".", "arg2", ((CallSet)call.getArguments().get(1))
					.getCalls().get(0).getName());
			assertEquals(
					"Argument #3 should be \"arg3\".", "arg3", ((CallSet)call.getArguments().get(2)).getCalls().get(0).getName()); //$NON-NLS-1$ 

			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be an empty string.", "", call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testWithPrefixAndArgsCall() {
		String buffer = TemplateConstants.getDefault().getLinkPrefixMetamodel()
				+ TemplateConstants.getDefault().getLinkPrefixSeparator() + "azerty(arg1, arg2, arg3)"; //$NON-NLS-1$
		try {
			Call call = CallParser.createCall(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(buffer.substring(buffer.indexOf(TemplateConstants.getDefault()
					.getLinkPrefixSeparator())
					+ TemplateConstants.getDefault().getLinkPrefixSeparator().length(), buffer
					.indexOf(TemplateConstants.getDefault().getParenth()[0])), call.getName());
			assertEquals("Should begin at 0 index.", 0, call.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), call.getEnd());
			assertEquals("Should have 3 arguments.", 3, call.getArguments().size());
			assertEquals("Argument #1 should be \"arg1\".", "arg1", ((CallSet)call.getArguments().get(0))
					.getCalls().get(0).getName());
			assertEquals("Argument #2 should be \"arg2\".", "arg2", ((CallSet)call.getArguments().get(1))
					.getCalls().get(0).getName());
			assertEquals(
					"Argument #3 should be \"arg3\".", "arg3", ((CallSet)call.getArguments().get(2)).getCalls().get(0).getName()); //$NON-NLS-1$ 

			assertNull("Filter should be null.", call.getFilter());
			assertEquals("prefix should be TemplateConstants.getDefault().LINK_PREFIX_METAMODEL.",
					TemplateConstants.getDefault().getLinkPrefixMetamodel(), call.getPrefix());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
