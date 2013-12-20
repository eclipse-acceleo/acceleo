/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.service.java;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoCompatibilityEclipseHelper;
import org.eclipse.acceleo.common.internal.utils.compatibility.OCLVersion;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.emf.common.util.URI;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This will be used to test the invocation of Java services from Acceleo.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoJavaServicesTest extends AbstractAcceleoTest {
	{
		try {
			final URI inputModelURI = URI.createPlatformPluginURI('/' + AcceleoEngineTestPlugin.PLUGIN_ID
					+ '/' + "data/abstractClass.ecore", true); //$NON-NLS-1$
			inputModel = ModelUtils.load(inputModelURI, resourceSet);
		} catch (IOException e) {
			fail("Error loading the input model."); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/Service/Java/java_services.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "Service";
	}

	/**
	 * Test invocation of java methods which don't define any arguments. We expect the method to be called on
	 * <code>self</code> regardless of the source class.
	 * <p>
	 * For example, a call such as this :
	 * 
	 * <pre>
	 * [invoke('java.lang.Object', 'toString', Sequence{eClass})/]
	 * </pre>
	 * 
	 * will call "eClass.toString()", which is valid as org.eclipse.emf.ecore.EClass is a subclass of
	 * java.lang.Object.
	 * 
	 * <pre>
	 * [invoke('org.eclipse.emf.ecore.impl.EClassImpl', 'getName', Sequence{eClass})/]
	 * </pre>
	 * 
	 * will call "eClass.getName()". Again, this is valid. <b>However</b>, this code :
	 * 
	 * <pre>
	 * [invoke('org.java.lang.Integer', 'byteValue', Sequence{eClass})/]
	 * </pre>
	 * 
	 * will try and call "eClass.byteValue()", which will yield an evaluation exception.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testNoArgumentServiceInvocation() throws IOException {
		this.init("NoArgument"); //$NON-NLS-1$
		this.generate("test_call_no_argument", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test invocation of java methods which define a single argument.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Ignore
	@Test
	public void testSingleArgumentServiceInvocation() throws IOException {
		this.init("SingleArgument"); //$NON-NLS-1$
		this.generate("test_call_single_argument", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test invocation of java methods which define multiple arguments.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Ignore
	@Test
	public void testMultipleArgumentServiceInvocation() throws IOException {
		this.init("MultipleArgument"); //$NON-NLS-1$
		this.generate("test_call_multiple_argument", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();

		if (AcceleoCompatibilityEclipseHelper.getCurrentOCLVersion() == OCLVersion.GANYMEDE) {
			// OCL 1.2 couldn't create a Sequence containing both an EClass and a String
			return;
		}
	}

	/**
	 * Test that methods containing "instanceof" operators are properly executed (prevents simple class loader
	 * issues).
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Ignore
	@Test
	public void testInstanceOfServiceInvocation() throws IOException {
		this.init("InstanceOf"); //$NON-NLS-1$
		this.generate("test_call_instanceofs", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test that methods are using the best choice of source & parameters.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Ignore
	@Test
	public void testSourceAndParametersChoice() throws IOException {
		this.init("SourceAndParameters"); //$NON-NLS-1$
		this.generate("test_call_source_and_parameters", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}
}
