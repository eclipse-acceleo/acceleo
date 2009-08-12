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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * The main class of the Acceleo Parser. Creates an AST from a list of Acceleo files, using a CST step. You
 * just have to launch the 'parse' method...
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
		parse(inputFiles, outputURIs, dependenciesURIs, new BasicMonitor());
	}

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
	 * @param monitor
	 *            This will be used as the progress monitor for the parsing
	 */
	public void parse(List<File> inputFiles, List<URI> outputURIs, List<URI> dependenciesURIs, Monitor monitor) {
		assert inputFiles.size() == outputURIs.size();
		monitor.beginTask(AcceleoParserMessages.getString("AcceleoParser.ParseFiles",
				new Object[] {inputFiles.size() }), inputFiles.size() * 3);
		ResourceSet oResourceSet = new ResourceSetImpl();
		List<Resource> newResources = new ArrayList<Resource>();
		List<AcceleoSourceBuffer> sources = new ArrayList<AcceleoSourceBuffer>();
		Iterator<URI> itOutputURIs = outputURIs.iterator();
		Set<String> allImportedFiles = new HashSet<String>();
		for (Iterator<File> itInputFiles = inputFiles.iterator(); !monitor.isCanceled()
				&& itInputFiles.hasNext() && itOutputURIs.hasNext();) {
			File inputFile = itInputFiles.next();
			monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.ParseFileCST",
					new Object[] {inputFile.getName() }));
			URI oURI = itOutputURIs.next();
			AcceleoSourceBuffer source = new AcceleoSourceBuffer(inputFile);
			sources.add(source);
			Resource oResource = ModelUtils.createResource(oURI, oResourceSet);
			newResources.add(oResource);
			source.createCST();
			for (ModuleImportsValue importValue : source.getCST().getImports()) {
				String importedFileName = importValue.getName();
				if (importedFileName != null) {
					int lastSegment = importedFileName.lastIndexOf(IAcceleoConstants.NAMESPACE_SEPARATOR);
					if (lastSegment > -1) {
						importedFileName = importedFileName.substring(
								lastSegment + IAcceleoConstants.NAMESPACE_SEPARATOR.length()).trim();
					}
					allImportedFiles.add(importedFileName + '.' + IAcceleoConstants.EMTL_FILE_EXTENSION);
				}
			}
			source.createAST(oResource);
			monitor.worked(1);
		}
		for (Iterator<URI> itDependenciesURIs = dependenciesURIs.iterator(); !monitor.isCanceled()
				&& itDependenciesURIs.hasNext();) {
			URI oURI = itDependenciesURIs.next();
			if (!outputURIs.contains(oURI) && allImportedFiles.contains(oURI.lastSegment())) {
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
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); !monitor.isCanceled()
				&& itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			if (source.getFile() != null) {
				monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.ParseFileAST",
						new Object[] {source.getFile().getName() }));
			}
			source.resolveAST();
			monitor.worked(1);
		}
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); !monitor.isCanceled()
				&& itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			if (source.getFile() != null) {
				monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.SaveAST", new Object[] {source
						.getFile().getName(), }));
			}
			Module eModule = source.getAST();
			if (eModule != null) {
				Resource newResource = eModule.eResource();
				Map<String, String> options = new HashMap<String, String>();
				options.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
				try {
					newResource.save(options);
				} catch (IOException e) {
					source.log(AcceleoParserMessages.getString("AcceleoParser.Error.FileSaving", newResource //$NON-NLS-1$
							.getURI().lastSegment(), e.getMessage()), 0, -1);
				}
			} else {
				source.log(AcceleoParserMessages.getString("AcceleoParser.Error.InvalidAST", source.getFile() //$NON-NLS-1$
						.getName()), 0, -1);
			}
			monitor.worked(1);
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
		Set<String> allImportedFiles = new HashSet<String>();
		for (ModuleImportsValue importValue : source.getCST().getImports()) {
			String importedFileName = importValue.getName();
			if (importedFileName != null) {
				int lastSegment = importedFileName.lastIndexOf(IAcceleoConstants.NAMESPACE_SEPARATOR);
				if (lastSegment > -1) {
					importedFileName = importedFileName.substring(
							lastSegment + IAcceleoConstants.NAMESPACE_SEPARATOR.length()).trim();
				}
				allImportedFiles.add(importedFileName + '.' + IAcceleoConstants.EMTL_FILE_EXTENSION);
			}
		}
		source.createAST(resource);
		if (resource.getResourceSet() != null) {
			for (Iterator<URI> itDependenciesURIs = dependenciesURIs.iterator(); itDependenciesURIs.hasNext();) {
				URI oURI = itDependenciesURIs.next();
				if (!resourceSetURIs.contains(oURI) && allImportedFiles.contains(oURI.lastSegment())) {
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
