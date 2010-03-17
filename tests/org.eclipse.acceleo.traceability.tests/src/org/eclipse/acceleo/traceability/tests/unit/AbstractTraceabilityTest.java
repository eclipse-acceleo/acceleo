/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
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
import java.util.ArrayList;

import junit.framework.AssertionFailedError;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.framework.Bundle;

/**
 * This will be used as the base class for our traceability tests. It adds functionality to locate files within plugin fragments.
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public abstract class AbstractTraceabilityTest extends AbstractAcceleoTest {
	/** ID of the traceability tests plugin fragment. */
	protected final static String TRACEABILITY_TESTS_FRAGMENT_ID = "org.eclipse.acceleo.traceability.tests";
	
	/** Location of the model used by the traceability tests. */
	protected final static String MODEL_LOCATION = "data/multiplePackages.ecore";
	
	{
		try {
			final URI inputModelURI = URI.createURI("platform:/fragment" + '/' + TRACEABILITY_TESTS_FRAGMENT_ID
					+ '/' + MODEL_LOCATION, true);
			inputModel = ModelUtils.load(inputModelURI, resourceSet);
		} catch (IOException e) {
			fail("Error loading the input model.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#createFile(java.lang.String)
	 */
	@Override
	protected File createFile(String pathName) {
		File file = null;
		for (Bundle fragment : Platform.getFragments(bundle)) {
			if (TRACEABILITY_TESTS_FRAGMENT_ID.equals(fragment.getSymbolicName())) {
				try {
					String fileLocation = FileLocator.resolve(fragment.getEntry(pathName)).getPath();
					file = new File(fileLocation);
				} catch (IOException e) {
					throw new AssertionFailedError(e.getMessage());
				}
			}
		}
		return file;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#parse(java.lang.String)
	 */
	@Override
	protected Resource parse(String mtlPath) {
		File file = createFile(mtlPath);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);

		final URI moduleURI = URI.createURI("platform:/fragment" + '/' + TRACEABILITY_TESTS_FRAGMENT_ID + '/'
				+ (new Path(mtlPath)).removeFileExtension().addFileExtension(EMTL_EXTENSION), true);

		Resource modelResource = ModelUtils.createResource(moduleURI, resourceSet);
		AcceleoParser parser = new AcceleoParser();
		assertNull(parser.getProblems(file));
		parser.parse(source, modelResource, new ArrayList<URI>());
		return modelResource;
	}
}
