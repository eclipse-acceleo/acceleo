/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.standalone.tests.generation;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceSetImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Test;

/**
 * Stand alone test for the generation with Acceleo binary and xmi resources.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class GenerationTests extends AbstractAcceleoGenerator {

	/**
	 * The user.dir property key.
	 */
	private static final String USERDIR = "user.dir";

	/**
	 * Launch a generation from an Acceleo module saved as a XMI resource.
	 */
	@Test
	public void generateWithXMIResource() {
		ResourceSet resourceSet = new AcceleoResourceSetImpl();
		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);

		String path = System.getProperty(USERDIR);
		path = path + "/data/xmi/standAloneGenerate.emtl";

		URI moduleURI = URI.createFileURI(URI.decode(path));
		try {
			EObject eObject = ModelUtils.load(moduleURI, resourceSet);
			assertThat(eObject, is(instanceOf(Module.class)));
			Module module = (Module)eObject;

			path = System.getProperty(USERDIR);
			path = path + "/data/model.ecore";
			URI modelURI = URI.createFileURI(URI.decode(path));
			EObject myModel = ModelUtils.load(modelURI, resourceSet);

			AcceleoService service = new AcceleoService(new PreviewStrategy());
			Map<String, String> doGenerate = service.doGenerate(module, "generateElement", myModel, null,
					null);
			Entry<String, String> entry = doGenerate.entrySet().iterator().next();
			assertThat(
					entry.getValue().toString().trim(),
					is("Model: myClass\nString Literal: literal\nStandard library: true\nNon standard library: myClassnon standart"));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Launch a generation from an Acceleo module saved as a XMI resource.
	 */
	@Test
	public void generateWithBinaryResource() {
		ResourceSet resourceSet = new AcceleoResourceSetImpl();
		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);

		String path = System.getProperty(USERDIR);
		path = path + "/data/binary/standAloneGenerate.emtl";

		URI moduleURI = URI.createFileURI(URI.decode(path));
		try {
			EObject eObject = ModelUtils.load(moduleURI, resourceSet);
			assertThat(eObject, is(instanceOf(Module.class)));
			Module module = (Module)eObject;

			path = System.getProperty(USERDIR);
			path = path + "/data/model.ecore";
			URI modelURI = URI.createFileURI(URI.decode(path));
			EObject myModel = ModelUtils.load(modelURI, resourceSet);

			AcceleoService service = new AcceleoService(new PreviewStrategy());
			Map<String, String> doGenerate = service.doGenerate(module, "generateElement", myModel, null,
					null);
			Entry<String, String> entry = doGenerate.entrySet().iterator().next();
			assertThat(
					entry.getValue().toString().trim(),
					is("Model: myClass\nString Literal: literal\nStandard library: true\nNon standard library: myClassnon standart"));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * This will be called in order to find and load the module that will be launched through this launcher.
	 * We expect this name not to contain file extension, and the module to be located beside the launcher.
	 * 
	 * @return The name of the module that is to be launched.
	 * @generated
	 */
	@Override
	public String getModuleName() {
		return null;
	}

	/**
	 * This will be used to get the list of templates that are to be launched by this launcher.
	 * 
	 * @return The list of templates to call on the module {@link #getModuleName()}.
	 * @generated
	 */
	@Override
	public String[] getTemplateNames() {
		return null;
	}
}
