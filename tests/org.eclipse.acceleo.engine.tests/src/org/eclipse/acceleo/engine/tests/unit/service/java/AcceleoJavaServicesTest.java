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

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoCompatibilityEclipseHelper;
import org.eclipse.acceleo.common.internal.utils.compatibility.OCLVersion;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.emf.common.util.URI;

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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "Service/Java";
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
	public void testNoArgumentServiceInvocation() throws IOException {
		generationRoot = new File(getGenerationRootPath("NoArgument")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NoArgument")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_call_no_argument", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			assertFalse("a lost file shouldn't have been created", generated.getName().endsWith(
					IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("AbstractClass")); //$NON-NLS-1$
		}
	}

	/**
	 * Test invocation of java methods which define a single argument.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testSingleArgumentServiceInvocation() throws IOException {
		generationRoot = new File(getGenerationRootPath("SingleArgument")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("SingleArgument")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_call_single_argument", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			assertFalse("a lost file shouldn't have been created", generated.getName().endsWith(
					IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("AbstractClass")); //$NON-NLS-1$
		}
	}

	/**
	 * Test invocation of java methods which define multiple arguments.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testMultipleArgumentServiceInvocation() throws IOException {
		if (AcceleoCompatibilityEclipseHelper.getCurrentOCLVersion() == OCLVersion.GANYMEDE) {
			// OCL 1.2 couldn't create a Sequence containing both an EClass and a String
			return;
		}
		generationRoot = new File(getGenerationRootPath("MultipleArgument")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("MultipleArgument")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_call_multiple_argument", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			assertFalse("a lost file shouldn't have been created", generated.getName().endsWith(
					IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("true")); //$NON-NLS-1$
		}
	}

	/**
	 * Test that methods containing "instanceof" operators are properly executed (prevents simple class loader
	 * issues).
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testInstanceOfServiceInvocation() throws IOException {
		generationRoot = new File(getGenerationRootPath("InstanceOf")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("InstanceOf")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_call_instanceofs", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			assertFalse("a lost file shouldn't have been created", generated.getName().endsWith(
					IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("true")); //$NON-NLS-1$
		}
	}
}
