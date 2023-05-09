/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class UnitTestModels {

	private Setup setup;

	private ResourceSet set;

	public UnitTestModels(Setup setupToUse) {
		this.setup = setupToUse;
	}

	public Resource reverse() throws URISyntaxException, IOException {
		String localPath = "ecore/reverse.qmodel";
		return loadResource(localPath);
	}

	public Resource reverseEcore() throws URISyntaxException, IOException {
		String localPath = "ecore/reverse.ecore";
		return loadResource(localPath);
	}

	public Resource anydsl() throws URISyntaxException, IOException {
		String localPath = "anydsl/anyDSLModel.qmodel";
		return loadResource(localPath);
	}

	public Resource uml() throws URISyntaxException, IOException {
		String localPath = "uml/uml.qmodel";
		return loadResource(localPath);
	}

	public Resource umlWithFragment() throws URISyntaxException, IOException {
		String localPath = "fragmented/container.uml";
		return loadResource(localPath);
	}

	private Resource loadResource(String localPath) throws URISyntaxException, IOException {
		set = setup.newConfiguredResourceSet();
		URL url = this.getClass().getClassLoader().getResource(localPath);
		URI uri = URI.createURI(url.toURI().toString());
		Resource res = set.getResource(uri, true);
		res.load(Collections.EMPTY_MAP);
		return res;
	}

	public ResourceSet getResourceSet() {
		return set;
	}
}
