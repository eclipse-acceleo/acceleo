/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
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
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
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
	private final Map<File, AcceleoParserProblems> problems = new HashMap<File, AcceleoParserProblems>();

	/**
	 * To store the warnings of each file.
	 */
	private final Map<File, AcceleoParserWarnings> warnings = new HashMap<File, AcceleoParserWarnings>();

	/**
	 * To store the infos of each file.
	 */
	private final Map<File, AcceleoParserInfos> infos = new HashMap<File, AcceleoParserInfos>();

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
		List<AcceleoFile> acceleoFiles = new ArrayList<AcceleoFile>();
		for (File inputFile : inputFiles) {
			acceleoFiles.add(new AcceleoFile(inputFile, AcceleoFile.simpleModuleName(inputFile)));
		}
		parse(acceleoFiles, outputURIs, dependenciesURIs, new BasicMonitor());
	}

	/**
	 * Creates an AST from a list of Acceleo files, using a CST step.
	 * <p>
	 * Assert acceleoFiles.size() == outputURIs.size()
	 * 
	 * @param acceleoFiles
	 *            are the Acceleo files to parse
	 * @param outputURIs
	 *            are the URIs of the output files to create
	 * @param dependenciesURIs
	 *            URIs of the dependencies that need to be loaded before link resolution
	 * @param monitor
	 *            This will be used as the progress monitor for the parsing
	 * @since 3.0
	 */
	public void parse(List<AcceleoFile> acceleoFiles, List<URI> outputURIs, List<URI> dependenciesURIs,
			Monitor monitor) {
		parse(acceleoFiles, outputURIs, dependenciesURIs, null, monitor);
	}

	/**
	 * Creates an AST from a list of Acceleo files, using a CST step, and using an advanced resolution
	 * mechanism.
	 * <p>
	 * Assert acceleoFiles.size() == outputURIs.size().
	 * </p>
	 * There is sometimes a difference between how you want to load/save an EMTL resource and how you want to
	 * make this resource reusable. Let's take a simple example : we need to manage a file named
	 * 'myNewModule.emtl'. The file URI 'c:/myProject/myNewModule.emtl' would be used to load/save this EMTL
	 * model at the good location, but you would prefer to import this EMTL model in another EMF context with
	 * a platform plugin URI more portable like 'plugin:/resource/myProject/myNewModule.emtl'. The 'mapURIs'
	 * map will help you to define how to load and use the dependencies...
	 * 
	 * @param acceleoFiles
	 *            are the Acceleo files to parse
	 * @param outputURIs
	 *            are the URIs of the output files to create
	 * @param dependenciesURIs
	 *            URIs of the dependencies that need to be loaded before link resolution
	 * @param mapURIs
	 *            Advanced mapping mechanism for the URIs that need to be loaded before link resolution, the
	 *            map key is the loading URI, the map value is the proxy URI (the real way to reuse this
	 *            dependency)
	 * @param monitor
	 *            This will be used as the progress monitor for the parsing
	 * @since 3.0
	 */
	public void parse(List<AcceleoFile> acceleoFiles, List<URI> outputURIs, List<URI> dependenciesURIs,
			Map<URI, URI> mapURIs, Monitor monitor) {
		monitor.beginTask(AcceleoParserMessages.getString("AcceleoParser.ParseFiles", //$NON-NLS-1$
				new Object[] {Integer.valueOf(acceleoFiles.size()), }), acceleoFiles.size() * 3);
		ResourceSet oResourceSet = new ResourceSetImpl();
		List<Resource> newResources = new ArrayList<Resource>();
		List<AcceleoSourceBuffer> sources = new ArrayList<AcceleoSourceBuffer>();
		Iterator<URI> itOutputURIs = outputURIs.iterator();
		Set<String> allImportedFiles = new HashSet<String>();
		for (Iterator<AcceleoFile> itAcceleoFiles = acceleoFiles.iterator(); !monitor.isCanceled()
				&& itAcceleoFiles.hasNext() && itOutputURIs.hasNext();) {
			AcceleoFile acceleoFile = itAcceleoFiles.next();
			monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.ParseFileCST", //$NON-NLS-1$
					new Object[] {acceleoFile.getMtlFile().getName() }));
			URI oURI = itOutputURIs.next();
			AcceleoSourceBuffer source = new AcceleoSourceBuffer(acceleoFile);
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
			for (ModuleExtendsValue extendValue : source.getCST().getExtends()) {
				String importedFileName = extendValue.getName();
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
						iterator.next().logProblem(
								AcceleoParserMessages.getString("AcceleoParser" + ".Error.InvalidAST", oURI //$NON-NLS-1$ //$NON-NLS-2$
										.lastSegment()), 0, -1);

					}
				}
			}
		}
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); !monitor.isCanceled()
				&& itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			if (source.getFile() != null) {
				monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.ParseFileAST", //$NON-NLS-1$
						new Object[] {source.getFile().getName() }));
			}
			source.resolveAST();
			source.resolveASTDocumentation();
			monitor.worked(1);
		}
		if (mapURIs != null) {
			for (Resource resource : oResourceSet.getResources()) {
				URI resourceURI = resource.getURI();
				if (resourceURI != null) {
					URI reusableURI = mapURIs.get(resourceURI);
					if (reusableURI != null) {
						resource.setURI(reusableURI);
					}
				}
			}
		}
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); !monitor.isCanceled()
				&& itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			if (source.getFile() != null) {
				monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.SaveAST", new Object[] {source //$NON-NLS-1$
						.getFile().getName(), }));
			}
			Module eModule = source.getAST();
			if (eModule != null) {
				Resource newResource = eModule.eResource();
				Map<String, String> options = new HashMap<String, String>();
				String encoding = source.getEncoding();
				if (encoding == null) {
					encoding = "UTF-8"; //$NON-NLS-1$
				}
				options.put(XMLResource.OPTION_ENCODING, encoding);
				try {
					newResource.save(options);
				} catch (IOException e) {
					source.logProblem(AcceleoParserMessages.getString(
							"AcceleoParser.Error.FileSaving", newResource //$NON-NLS-1$
									.getURI().lastSegment(), e.getMessage()), 0, -1);
				}
			} else {
				source.logProblem(AcceleoParserMessages.getString(
						"AcceleoParser.Error.InvalidAST", source.getFile() //$NON-NLS-1$
								.getName()), 0, -1);
			}
			monitor.worked(1);
		}

		this.manageParsingResult(sources);

		Iterator<Resource> resources = oResourceSet.getResources().iterator();
		while (resources.hasNext()) {
			resources.next().unload();
		}
	}

	/**
	 * Manages the result of the parsing.
	 * 
	 * @param sources
	 *            The list of source buffers.
	 */
	private void manageParsingResult(List<AcceleoSourceBuffer> sources) {
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			problems.put(source.getFile(), source.getProblems());
		}
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			warnings.put(source.getFile(), source.getWarnings());
		}
		for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); itSources.hasNext();) {
			AcceleoSourceBuffer source = itSources.next();
			infos.put(source.getFile(), source.getInfos());
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
		for (ModuleExtendsValue extendValue : source.getCST().getExtends()) {
			String importedFileName = extendValue.getName();
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
						source.logProblem(AcceleoParserMessages.getString(
								"AcceleoParser.Error.InvalidAST", oURI.lastSegment()), 0, -1); //$NON-NLS-1$

					}
				}
			}
		}
		source.resolveAST();
		source.resolveASTDocumentation();
		if (source.getFile() != null) {
			problems.put(source.getFile(), source.getProblems());

			warnings.put(source.getFile(), source.getWarnings());

			infos.put(source.getFile(), source.getInfos());
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
		return problems.get(file);
	}

	/**
	 * Gets the parsing problems of the given file.
	 * 
	 * @param acceleoFile
	 *            is the parsed file
	 * @return the parsing problems, or null
	 * @since 3.0
	 */
	public AcceleoParserProblems getProblems(AcceleoFile acceleoFile) {
		return problems.get(acceleoFile.getMtlFile());
	}

	/**
	 * Gets the parsing warnings of the given file.
	 * 
	 * @param file
	 *            is the parsed file
	 * @return the parsing warnings, or null
	 * @since 3.1
	 */
	public AcceleoParserWarnings getWarnings(File file) {
		return warnings.get(file);
	}

	/**
	 * Gets the parsing warnings of the given file.
	 * 
	 * @param acceleoFile
	 *            is the parsed file
	 * @return the parsing warnings, or null
	 * @since 3.1
	 */
	public AcceleoParserWarnings getWarnings(AcceleoFile acceleoFile) {
		if (warnings != null && acceleoFile != null) {
			return warnings.get(acceleoFile.getMtlFile());
		}
		return null;
	}

	/**
	 * Gets the parsing infos of the given file.
	 * 
	 * @param file
	 *            is the parsed file
	 * @return the parsing infos, or null
	 * @since 3.1
	 */
	public AcceleoParserInfos getInfos(File file) {
		if (infos != null) {
			return infos.get(file);
		}
		return null;
	}

	/**
	 * Gets the parsing infos of the given file.
	 * 
	 * @param acceleoFile
	 *            is the parsed file
	 * @return the parsing infos, or null
	 * @since 3.1
	 */
	public AcceleoParserInfos getInfos(AcceleoFile acceleoFile) {
		if (infos != null && acceleoFile != null) {
			return infos.get(acceleoFile.getMtlFile());
		}
		return null;
	}
}
