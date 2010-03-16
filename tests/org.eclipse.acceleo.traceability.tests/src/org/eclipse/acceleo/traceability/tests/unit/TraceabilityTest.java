/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.unit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.BasicMonitor;

/**
 * Makes sure we retrieve the proper traceability information.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class TraceabilityTest extends AbstractTraceabilityTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/traceability.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "Traceability";
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testTraceabilityInformation() throws IOException {
		generationRoot = new File(getGenerationRootPath("Traceability"));
		referenceRoot = new File(getReferenceRootPath("Traceability"));

		Map<Module, Set<String>> generateFor = new HashMap<Module, Set<String>>();
		Set<String> templateNames = new LinkedHashSet<String>();
		templateNames.add("traceability");
		templateNames.add("traceabilityAttributes");
		generateFor.put(module, templateNames);
		Map<String, String> preview = new AcceleoService(defaultStrategy).doGenerate(generateFor, inputModel, generationRoot, new BasicMonitor());
		assertSame(2, preview.size());
		Map.Entry<String, String> entry = preview.entrySet().iterator().next();
		assertEquals(generationRoot.getPath() + File.separatorChar + "MyClass.java", entry.getKey());
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new InstanceScope().getNode(AcceleoEnginePlugin.PLUGIN_ID).putBoolean(
				"org.eclipse.acceleo.traceability.activation", true);
	}
}
