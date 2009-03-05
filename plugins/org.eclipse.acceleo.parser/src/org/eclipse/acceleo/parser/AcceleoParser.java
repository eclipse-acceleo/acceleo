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
package org.eclipse.acceleo.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * The main class of the Acceleo Parser. Creates an AST from a list of Acceleo files, using a CST step. You just have
 * to launch the 'parse' method...
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoParser {

	/**
	 * To store the problems of each file.
	 */
	private Map<File, AcceleoParserProblems> problems;

	/**
	 * Creates an AST from a list of Acceleo files, using a CST step.
	 * <p>
	 * Assert inputFiles.size() == outputURIs.size()
	 * 
	 * @param inputFiles
	 *            are the files to parse
	 * @param outputURIs
	 *            are the URIs of the output files to create
	 * @param dependenciesURIs
	 *            URIs of the dependencies that need to be loaded before link resolution
	 */
	public void parse(List<File> inputFiles, List<URI> outputURIs, List<URI> dependenciesURIs) {
		assert inputFiles.size() == outputURIs.size();
		ResourceSet oResourceSet = new ResourceSetImpl();
		List<Resource> newResources = new ArrayList<Resource>();
		List<AcceleoSourceBuffer> sources = new ArrayList<AcceleoSourceBuffer>();
		Iterator<URI> itOutputURIs = outputURIs.iterator();
		for (Iterator<File> itInputFiles = inputFiles.iterator(); itInputFiles.hasNext()
				&& itOutputURIs.hasNext();) {
			File inputFile = itInputFiles.next();
			URI oURI = itOutputURIs.next();
			AcceleoSourceBuffer source = new AcceleoSourceBuffer(inputFile);
			sources.add(source);
			Resource oResource = ModelUtils.createResource(oURI, oResourceSet);
			newResources.add(oResource);
			source.createCST();
			source.createAST(oResource);
		}
		for (Iterator<URI> itDependenciesURIs = dependenciesURIs.iterator(); itDependenciesURIs.hasNext();) {
			URI oURI = itDependenciesURIs.next();
			if (!outputURIs.contains(oURI)) {
				try {
					ModelUtils.load(oURI, oResourceSet);
				} catch (IOException e) {
					for (Iterator<AcceleoSourceBuffer> iterator = sources.iterator(); iterator.hasNext();) {
						iterator.next().log(
								AcceleoParserMessages.getString(
										"AcceleoParser" + ".Error.InvalidAST", oURI.lastSegment()), 0, -1); //$NON-NLS-1$ //$NON-NLS-2$

					}
				}
			}
		}
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			source.resolveAST();
		}
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			Module eModule = source.getAST();
			if (eModule != null) {
				Resource newResource = eModule.eResource();
				Map<String, String> options = new HashMap<String, String>();
				options.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
				try {
					newResource.save(options);
				} catch (IOException e) {
					source
							.log(
									AcceleoParserMessages
											.getString(
													"AcceleoParser.Error.FileSaving", newResource.getURI().lastSegment(), e.getMessage()), 0, -1); //$NON-NLS-1$
				}
			} else {
				source.log(AcceleoParserMessages.getString("AcceleoParser.Error.InvalidAST", source.getFile() //$NON-NLS-1$
						.getName()), 0, -1);
			}
		}

		problems = new HashMap<File, AcceleoParserProblems>(sources.size());
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			problems.put(source.getFile(), source.getProblems());
		}
		Iterator<Resource> resources = oResourceSet.getResources().iterator();
		while (resources.hasNext()) {
			resources.next().unload();
		}
	}

	/**
	 * Creates an AST from a list of Acceleo files, using a CST step.
	 * <p>
	 * Assert inputFiles.size() == outputURIs.size()
	 * 
	 * @param source
	 *            is the source to parse
	 * @param resource
	 *            where the AST will be stored
	 * @param dependenciesURIs
	 *            URIs of the dependencies that need to be loaded before link resolution
	 */
	public void parse(AcceleoSourceBuffer source, Resource resource, List<URI> dependenciesURIs) {
		List<URI> resourceSetURIs = new ArrayList<URI>();
		if (resource.getResourceSet() != null) {
			Iterator<Resource> itResources = resource.getResourceSet().getResources().iterator();
			while (itResources.hasNext()) {
				Resource otherResource = itResources.next();
				resourceSetURIs.add(otherResource.getURI());
			}
		}
		source.createCST();
		source.createAST(resource);
		if (resource.getResourceSet() != null) {
			for (Iterator<URI> itDependenciesURIs = dependenciesURIs.iterator(); itDependenciesURIs.hasNext();) {
				URI oURI = itDependenciesURIs.next();
				if (!resourceSetURIs.contains(oURI)) {
					try {
						ModelUtils.load(oURI, resource.getResourceSet());
					} catch (IOException e) {
						source.log(AcceleoParserMessages.getString(
								"AcceleoParser.Error.InvalidAST", oURI.lastSegment()), 0, -1); //$NON-NLS-1$

					}
				}
			}
		}
		source.resolveAST();
		if (source.getFile() != null) {
			problems = new HashMap<File, AcceleoParserProblems>(1);
			problems.put(source.getFile(), source.getProblems());
		}
	}

	/**
	 * Gets the parsing problems of the given file.
	 * 
	 * @param file
	 *            is the parsed file
	 * @return the parsing problems, or null
	 */
	public AcceleoParserProblems getProblems(File file) {
		if (problems != null) {
			return problems.get(file);
		} else {
			return null;
		}
	}

}
