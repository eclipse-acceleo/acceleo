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
package org.eclipse.acceleo.common.tests.suite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.textui.TestRunner;

import org.eclipse.acceleo.common.tests.unit.utils.AcceleoCommonPluginTest;
import org.eclipse.acceleo.common.tests.unit.utils.CircularArrayDequeTest;
import org.eclipse.acceleo.common.tests.unit.utils.CompactHashSetTest;
import org.eclipse.acceleo.common.tests.unit.utils.CompactLinkedHashSetTest;
import org.eclipse.acceleo.common.tests.unit.utils.MessagesTest;
import org.eclipse.acceleo.common.tests.unit.utils.modelutils.AttachResourceTest;
import org.eclipse.acceleo.common.tests.unit.utils.modelutils.GetModelsFromTest;
import org.eclipse.acceleo.common.tests.unit.utils.modelutils.LoadFromFileTest;
import org.eclipse.acceleo.common.tests.unit.utils.modelutils.LoadFromInputStreamTest;
import org.eclipse.acceleo.common.tests.unit.utils.modelutils.LoadFromStringTest;
import org.eclipse.acceleo.common.tests.unit.utils.modelutils.SaveSerializeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This suite will launch all the tests defined for the Acceleo common plugin.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@RunWith(Suite.class)
@SuiteClasses({MessagesTest.class, AcceleoCommonPluginTest.class, CircularArrayDequeTest.class,
		CompactHashSetTest.class, CompactLinkedHashSetTest.class, AttachResourceTest.class,
		GetModelsFromTest.class, LoadFromFileTest.class, LoadFromInputStreamTest.class,
		LoadFromStringTest.class, SaveSerializeTest.class })
public class AllCommonTests {
	/**
	 * Launches the test with the given arguments.
	 * 
	 * @param args
	 *            Arguments of the testCase.
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * Creates the {@link junit.framework.TestSuite TestSuite} for all the test.
	 * 
	 * @return The test suite containing all the tests
	 */
	public static Test suite() {
		return new JUnit4TestAdapter(AllCommonTests.class);
	}
}
