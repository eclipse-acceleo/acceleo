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
package org.eclipse.acceleo.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceSetImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceImpl;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * The main class of the Acceleo Parser. Creates an AST from a list of Acceleo files, using a CST step. You
 * just have to launch the 'parse' method...
 * 
 * @Deprecated Used only by the deprecated Ant Task
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
@Deprecated
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
	 * Indicates if we will compile the mtl file as a binary resource.
	 * 
	 * @since 3.1
	 */
	private boolean asBinaryResource;

	/**
	 * Indicates if we will trimmed the compilation.
	 */
	private boolean trimmedCompilation;

	/**
	 * The operations in the module.
	 */
	private ListMultimap<Module, ASTNode> operationsInModule = ArrayListMultimap.create();

	/**
	 * The constructor.
	 */
	public AcceleoParser() {
		// API compatibility, nothing to do here
	}

	/**
	 * The constructor.
	 * 
	 * @param asBinaryResource
	 *            Indicates if we will compile the mtl file as a binary resource (default: false).
	 * @since 3.1
	 */
	public AcceleoParser(boolean asBinaryResource) {
		this.asBinaryResource = asBinaryResource;
	}

	/**
	 * The constructor.
	 * 
	 * @param asBinaryResource
	 *            Indicates if we will compile the mtl file as a binary resource (default: false).
	 * @param trimmedCompilation
	 *            Indicates if we will trimmed the emtl produced (default: false).
	 * @since 3.2
	 */
	public AcceleoParser(boolean asBinaryResource, boolean trimmedCompilation) {
		this.asBinaryResource = asBinaryResource;
		this.trimmedCompilation = trimmedCompilation;
	}

	/**
	 * This will create and return the list of signatures for the given Acceleo module. The list will contain
	 * all signatures in a particular order :
	 * <ul>
	 * <li>The module itself (in the form
	 * <code>&lt;moduleName&gt;(&lt;metamodeluri&gt[,&lt;metamodeluri&gt;]*)</code>).</li>
	 * <li>All of the <b>template</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * <li>All of the <b>query</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * </ul>
	 * 
	 * @param module
	 *            The module from which to extract the signatures.
	 * @return The list of signatures for the given Acceleo module.
	 */
	private static List<String> createSignatureList(org.eclipse.acceleo.parser.cst.Module module) {
		if (module == null) {
			return Collections.emptyList();
		}

		final List<String> signatures = new ArrayList<String>();

		StringBuilder moduleSignature = new StringBuilder();
		if (module.getName() != null) {
			moduleSignature.append(module.getName());
		}
		moduleSignature.append('(');
		Iterator<org.eclipse.acceleo.parser.cst.TypedModel> modelIterator = module.getInput().iterator();
		while (modelIterator.hasNext()) {
			org.eclipse.acceleo.parser.cst.TypedModel model = modelIterator.next();
			for (EPackage packaje : model.getTakesTypesFrom()) {
				moduleSignature.append(packaje.getNsURI());
			}
			if (modelIterator.hasNext()) {
				moduleSignature.append(',');
			}
		}
		moduleSignature.append(')');

		final List<String> templateSignatures = new ArrayList<String>();
		final List<String> querySignatures = new ArrayList<String>();
		for (org.eclipse.acceleo.parser.cst.ModuleElement moduleElement : module.getOwnedModuleElement()) {
			if (moduleElement instanceof org.eclipse.acceleo.parser.cst.Template) {
				templateSignatures
						.add(createTemplateSignature((org.eclipse.acceleo.parser.cst.Template)moduleElement));
			} else if (moduleElement instanceof org.eclipse.acceleo.parser.cst.Query) {
				querySignatures
						.add(createQuerySignature((org.eclipse.acceleo.parser.cst.Query)moduleElement));
			}
		}

		signatures.add(moduleSignature.toString());
		signatures.addAll(templateSignatures);
		signatures.addAll(querySignatures);

		return signatures;
	}

	/**
	 * This will create a String representing the signature of the given template. The form of this String is
	 * fixed and will be :
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>.
	 * 
	 * @param template
	 *            The template for which we need a signature.
	 * @return The signature of <code>template</code>.
	 */
	private static String createTemplateSignature(org.eclipse.acceleo.parser.cst.Template template) {
		StringBuilder signature = new StringBuilder();
		signature.append("Template "); //$NON-NLS-1$
		if (template.getVisibility() != null) {
			signature.append(template.getVisibility().getLiteral());
		}
		signature.append(' ');
		if (template.getName() != null) {
			signature.append(template.getName());
		}
		signature.append('(');
		Iterator<org.eclipse.acceleo.parser.cst.Variable> paramIterator = template.getParameter().iterator();
		while (paramIterator.hasNext()) {
			org.eclipse.acceleo.parser.cst.Variable param = paramIterator.next();
			if (param.getName() != null) {
				signature.append(param.getName());
			}
			signature.append(':');
			if (param.getType() != null) {
				signature.append(param.getType());
			}
			if (paramIterator.hasNext()) {
				signature.append(',');
			}
		}
		signature.append(')');
		return signature.toString();
	}

	/**
	 * This will create a String representing the signature of the given query. The form of this String is
	 * fixed and will be :
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>
	 * 
	 * @param query
	 *            The query for which we need a signature.
	 * @return The signature of <code>query</code>.
	 */
	private static String createQuerySignature(org.eclipse.acceleo.parser.cst.Query query) {
		StringBuilder signature = new StringBuilder();
		signature.append("Query "); //$NON-NLS-1$
		if (query.getVisibility() != null) {
			signature.append(query.getVisibility().getLiteral());
		}
		signature.append(' ');
		if (query.getName() != null) {
			signature.append(query.getName());
		}
		signature.append('(');
		Iterator<org.eclipse.acceleo.parser.cst.Variable> paramIterator = query.getParameter().iterator();
		while (paramIterator.hasNext()) {
			org.eclipse.acceleo.parser.cst.Variable param = paramIterator.next();
			if (param.getName() != null) {
				signature.append(param.getName());
			}
			signature.append(':');
			if (param.getType() != null) {
				signature.append(param.getType());
			}
			if (paramIterator.hasNext()) {
				signature.append(',');
			}
		}
		signature.append(')');
		signature.append(':');
		if (query.getType() != null) {
			signature.append(query.getType());
		}
		return signature.toString();
	}

	/**
	 * This will create and return the list of signatures for the given Acceleo module. The list will contain
	 * all signatures in a particular order :
	 * <ul>
	 * <li>The module itself (in the form
	 * <code>&lt;moduleName&gt;(&lt;metamodeluri&gt[,&lt;metamodeluri&gt;]*)</code>).</li>
	 * <li>All of the <b>template</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * <li>All of the <b>query</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * </ul>
	 * 
	 * @param module
	 *            The module from which to extract the signatures.
	 * @return The list of signatures for the given Acceleo module.
	 */
	private static List<String> createSignatureList(Module module) {
		if (module == null) {
			return Collections.emptyList();
		}

		final List<String> signatures = new ArrayList<String>();

		StringBuilder moduleSignature = new StringBuilder();
		if (module.getName() != null) {
			moduleSignature.append(module.getName());
		}
		moduleSignature.append('(');
		Iterator<TypedModel> modelIterator = module.getInput().iterator();
		while (modelIterator.hasNext()) {
			TypedModel model = modelIterator.next();
			for (EPackage packaje : model.getTakesTypesFrom()) {
				moduleSignature.append(packaje.getNsURI());
			}
			if (modelIterator.hasNext()) {
				moduleSignature.append(',');
			}
		}
		moduleSignature.append(')');

		final List<String> templateSignatures = new ArrayList<String>();
		final List<String> querySignatures = new ArrayList<String>();
		for (ModuleElement moduleElement : module.getOwnedModuleElement()) {
			if (moduleElement instanceof Template) {
				templateSignatures.add(createTemplateSignature((Template)moduleElement));
			} else if (moduleElement instanceof Query) {
				querySignatures.add(createQuerySignature((Query)moduleElement));
			}
		}

		signatures.add(moduleSignature.toString());
		signatures.addAll(templateSignatures);
		signatures.addAll(querySignatures);

		return signatures;
	}

	/**
	 * This will create a String representing the signature of the given template. The form of this String is
	 * fixed and will be :
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>.
	 * 
	 * @param template
	 *            The template for which we need a signature.
	 * @return The signature of <code>template</code>.
	 */
	private static String createTemplateSignature(Template template) {
		StringBuilder signature = new StringBuilder();
		signature.append("Template "); //$NON-NLS-1$
		if (template.getVisibility() != null) {
			signature.append(template.getVisibility().getLiteral());
		}
		signature.append(' ');
		if (template.getName() != null) {
			signature.append(template.getName());
		}
		signature.append('(');
		Iterator<Variable> paramIterator = template.getParameter().iterator();
		while (paramIterator.hasNext()) {
			Variable param = paramIterator.next();
			if (param.getName() != null) {
				signature.append(param.getName());
			}
			signature.append(':');
			if (param.getType() != null) {
				signature.append(param.getType().getName());
			}
			if (paramIterator.hasNext()) {
				signature.append(',');
			}
		}
		signature.append(')');
		return signature.toString();
	}

	/**
	 * This will create a String representing the signature of the given query. The form of this String is
	 * fixed and will be :
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>
	 * 
	 * @param query
	 *            The query for which we need a signature.
	 * @return The signature of <code>query</code>.
	 */
	private static String createQuerySignature(Query query) {
		StringBuilder signature = new StringBuilder();
		signature.append("Query "); //$NON-NLS-1$
		if (query.getVisibility() != null) {
			signature.append(query.getVisibility().getLiteral());
		}
		signature.append(' ');
		if (query.getName() != null) {
			signature.append(query.getName());
		}
		signature.append('(');
		Iterator<Variable> paramIterator = query.getParameter().iterator();
		while (paramIterator.hasNext()) {
			Variable param = paramIterator.next();
			if (param.getName() != null) {
				signature.append(param.getName());
			}
			signature.append(':');
			if (param.getType() != null) {
				signature.append(param.getType().getName());
			}
			if (paramIterator.hasNext()) {
				signature.append(',');
			}
		}
		signature.append(')');
		signature.append(':');
		if (query.getType() != null) {
			signature.append(query.getType().getName());
		}
		return signature.toString();
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
	 * @deprecated
	 */
	@Deprecated
	public void parse(List<File> inputFiles, List<URI> outputURIs, List<URI> dependenciesURIs) {
		List<AcceleoFile> acceleoFiles = new ArrayList<AcceleoFile>();
		for (File inputFile : inputFiles) {
			acceleoFiles.add(new AcceleoFile(inputFile, AcceleoFile.simpleModuleName(inputFile)));
		}
		parse(acceleoFiles, outputURIs, dependenciesURIs, null, new BasicMonitor());
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
	 * @deprecated
	 */
	@Deprecated
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
		ResourceSet oResourceSet = new AcceleoResourceSetImpl();
		oResourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);
		List<Resource> newResources = new ArrayList<Resource>();
		List<AcceleoSourceBuffer> sources = new ArrayList<AcceleoSourceBuffer>();
		Iterator<URI> itOutputURIs = outputURIs.iterator();
		Set<String> allImportedFiles = new CompactHashSet<String>();
		/*
		 * These boolean will be used in order to break the building loop if the signatures of the first
		 * resource (the only "modified" one) have remained the same after the recompilation. If true, we will
		 * also build the files dependant on this "first resource". If false, the loop will be broken.
		 */
		boolean firstIteration = true;
		boolean buildDependantFiles = true;
		for (Iterator<AcceleoFile> itAcceleoFiles = acceleoFiles.iterator(); !monitor.isCanceled()
				&& itAcceleoFiles.hasNext() && itOutputURIs.hasNext() && buildDependantFiles;) {
			AcceleoFile acceleoFile = itAcceleoFiles.next();
			monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.ParseFileCST", //$NON-NLS-1$
					new Object[] {acceleoFile.getMtlFile().getName() }));
			URI oURI = itOutputURIs.next();
			AcceleoSourceBuffer source = new AcceleoSourceBuffer(acceleoFile);
			sources.add(source);

			final ResourceSet previousRS = new AcceleoResourceSetImpl();
			List<String> previousSignatures = new ArrayList<String>();
			if (firstIteration) {
				try {
					EObject previousRoot = ModelUtils.load(oURI, previousRS);
					if (previousRoot instanceof Module) {
						previousSignatures = createSignatureList((Module)previousRoot);
					}
				} catch (IOException e) {
					// Swallow this : we just didn't have a precompiled state
				} catch (WrappedException e) {
					// Swallow this : we just didn't have a precompiled state
					// CHECKSTYLE:OFF
				} catch (RuntimeException e) {
					// CHECKSTYLE:ON
					// Swallow this : we just didn't have a precompiled state (maven build)
				} finally {
					Thread unloadThread = new Thread() {
						/**
						 * {@inheritDoc}
						 * 
						 * @see java.lang.Thread#run()
						 */
						@Override
						public void run() {
							for (Resource res : previousRS.getResources()) {
								res.unload();
							}
							previousRS.getResources().clear();
						}
					};
					unloadThread.start();
				}
			}

			Resource oResource = createResource(oURI, oResourceSet);
			if (oResource instanceof EMtlBinaryResourceImpl) {
				EMtlBinaryResourceImpl binaryResourceImpl = (EMtlBinaryResourceImpl)oResource;
				binaryResourceImpl.setTrimPosition(trimmedCompilation);
			} else if (oResource instanceof EMtlResourceImpl) {
				EMtlResourceImpl resourceImpl = (EMtlResourceImpl)oResource;
				resourceImpl.setTrimPosition(trimmedCompilation);
			}
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

			if (firstIteration) {
				List<String> newSignatures = createSignatureList(source.getCST());

				buildDependantFiles = !previousSignatures.equals(newSignatures);
				firstIteration = false;
			}
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

		resolveAST(oResourceSet, mapURIs, sources, monitor);
	}

	/**
	 * Resolves the AST.
	 * 
	 * @param oResourceSet
	 *            The resource set.
	 * @param mapURIs
	 *            The map uris.
	 * @param sources
	 *            The sources.
	 * @param monitor
	 *            The monitor.
	 */
	private void resolveAST(final ResourceSet oResourceSet, Map<URI, URI> mapURIs,
			List<AcceleoSourceBuffer> sources, Monitor monitor) {
		try {
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
			trimEnvironment(oResourceSet);
			for (Iterator<AcceleoSourceBuffer> itSources = sources.iterator(); !monitor.isCanceled()
					&& itSources.hasNext();) {
				AcceleoSourceBuffer source = itSources.next();
				if (source.getFile() != null) {
					monitor.subTask(AcceleoParserMessages.getString(
							"AcceleoParser.SaveAST", new Object[] {source //$NON-NLS-1$
									.getFile().getName(), }));
				}
				Module eModule = source.getAST();
				if (eModule != null) {
					Resource newResource = eModule.eResource();
					Map<String, String> options = new HashMap<String, String>();
					if (!asBinaryResource) {
						String encoding = source.getEncoding();
						if (encoding == null) {
							encoding = "UTF-8"; //$NON-NLS-1$
						}
						options.put(XMLResource.OPTION_ENCODING, encoding);
					}
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
		} finally {
			Thread unloadThread = new Thread() {
				/**
				 * {@inheritDoc}
				 * 
				 * @see java.lang.Thread#run()
				 */
				@Override
				public void run() {
					Iterator<Resource> resources = oResourceSet.getResources().iterator();
					while (resources.hasNext()) {
						resources.next().unload();
					}
				}
			};
			unloadThread.start();
		}
	}

	/**
	 * Trim the useless data in the given resource set by removing the signature of non used EOperations,
	 * Templates and Queries.
	 * 
	 * @param oResourceSet
	 *            The resource set.
	 */
	private void trimEnvironment(ResourceSet oResourceSet) {
		List<Resource> resources = oResourceSet.getResources();
		ConcurrentLinkedQueue<Resource> conResources = new ConcurrentLinkedQueue<Resource>(resources);
		for (Resource resource : conResources) {
			List<EObject> contents = resource.getContents();
			if (contents.size() > 0 && contents.get(0) instanceof Module) {
				Module module = (Module)contents.get(0);
				fillCache(module);

				List<EOperation> eOperations = new ArrayList<EOperation>();

				for (int i = 1; i < contents.size(); i++) {
					EObject eObject = contents.get(i);
					TreeIterator<EObject> eAllContents = eObject.eAllContents();
					while (eAllContents.hasNext()) {
						EObject next = eAllContents.next();
						if (next instanceof EOperation && !eOperations.contains(next)
								&& !operationUsed((EOperation)next, module, resource)) {
							eOperations.add((EOperation)next);
						}
					}
				}

				// Trim environment
				for (EOperation eOperation : eOperations) {
					EcoreUtil.remove(eOperation);
				}

				// We no longer need this
				emptyCache();
			}
		}
	}

	/**
	 * Clears up the cache we created.
	 */
	private void emptyCache() {
		operationsInModule.clear();
	}

	/**
	 * Fills the cache with the data from a given module.
	 * 
	 * @param module
	 *            The module
	 */
	private void fillCache(Module module) {
		TreeIterator<EObject> eAllContents = module.eAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			if (next.eResource() != module.eResource()) {
				eAllContents.prune();
				continue;
			}
			if (next instanceof OperationCallExp) {
				OperationCallExp operationCallExp = (OperationCallExp)next;
				operationsInModule.put(module, operationCallExp);
			} else if (next instanceof TemplateInvocation) {
				TemplateInvocation templateInvocation = (TemplateInvocation)next;
				operationsInModule.put(module, templateInvocation);
			} else if (next instanceof Template) {
				Template template = (Template)next;
				operationsInModule.put(module, template);
			} else if (next instanceof QueryInvocation) {
				QueryInvocation queryInvocation = (QueryInvocation)next;
				operationsInModule.put(module, queryInvocation);
			} else if (next instanceof Query) {
				Query query = (Query)next;
				operationsInModule.put(module, query);
			}
		}
	}

	/**
	 * Indicates if the given operation represents an element (EOperation, Template or Query) used in the
	 * given module.
	 * 
	 * @param operation
	 *            The operation
	 * @param module
	 *            The module
	 * @param resource
	 *            The {@link Resource} containing this module.
	 * @return <code>true</code> if the operation is usefull, <code>false</code> otherwise.
	 */
	private boolean operationUsed(EOperation operation, Module module, Resource resource) {
		boolean result = false;

		List<ASTNode> nodes = operationsInModule.get(module);
		final Set<Module> importedModules = new HashSet<Module>(module.getImports());
		final Set<Module> extendedModules = new HashSet<Module>(module.getExtends());
		for (int i = 0; i < nodes.size() && !result; i++) {
			final ASTNode astNode = nodes.get(i);
			if (astNode instanceof OperationCallExp) {
				OperationCallExp operationCallExp = (OperationCallExp)astNode;
				if (operationCallExp.getReferredOperation().equals(operation)) {
					result = true;
				}
			} else if (astNode instanceof TemplateInvocation) {
				final TemplateInvocation templateInvocation = (TemplateInvocation)astNode;
				final EObject definitionContainer = templateInvocation.getDefinition().eContainer();
				if (definitionContainer == module || importedModules.contains(definitionContainer)
						|| extendedModules.contains(definitionContainer)) {
					result = templateEqual(templateInvocation.getDefinition(), operation);
				}
			} else if (astNode instanceof Template) {
				final EObject astNodeContainer = astNode.eContainer();
				if (astNodeContainer == module || importedModules.contains(astNodeContainer)
						|| extendedModules.contains(astNodeContainer)) {
					result = templateEqual((Template)astNode, operation);
					Template template = (Template)astNode;
					List<Template> overrides = template.getOverrides();
					for (Template overridenTemplates : overrides) {
						result = result || templateEqual(overridenTemplates, operation);
					}
				}
			} else if (astNode instanceof QueryInvocation) {
				final QueryInvocation queryInvocation = (QueryInvocation)astNode;
				final EObject definitionContainer = queryInvocation.getDefinition().eContainer();
				if (definitionContainer == module || importedModules.contains(definitionContainer)
						|| extendedModules.contains(definitionContainer)) {
					result = queryEqual(queryInvocation.getDefinition(), operation);
				}
			} else if (astNode instanceof Query) {
				final EObject astNodeContainer = astNode.eContainer();
				if (astNodeContainer == module || importedModules.contains(astNodeContainer)
						|| extendedModules.contains(astNodeContainer)) {
					result = queryEqual((Query)astNode, operation);
				}
			}
		}

		return result;
	}

	/**
	 * Indicates if the query matches the given operation.
	 * 
	 * @param definition
	 *            The definition of the query.
	 * @param operation
	 *            The operation.
	 * @return <code>true</code> if the operation represents the query, <code>false</code> otherwise.
	 */
	private boolean queryEqual(Query definition, EOperation operation) {
		boolean result = true;
		result = result && definition.getName().equals(operation.getName());
		List<Variable> parameters = definition.getParameter();
		List<EParameter> eParameters = operation.getEParameters();

		result = result && parameters.size() == eParameters.size();
		if (result) {
			for (int i = 0; i < parameters.size(); i++) {
				Variable variable = parameters.get(i);
				EParameter eParameter = eParameters.get(i);
				if (variable.getName() != null) {
					result = result && variable.getName().equals(eParameter.getName());
				}
				if (variable.getEType() != null) {
					result = result && variable.getEType().equals(eParameter.getEType());
				}
			}
		}

		return result;
	}

	/**
	 * Indicates if the template matches the given operation.
	 * 
	 * @param definition
	 *            The template definition.
	 * @param operation
	 *            the operation.
	 * @return <code>true</code> if the operation represents the template, <code>false</code> otherwise.
	 */
	private boolean templateEqual(Template definition, EOperation operation) {
		boolean result = true;
		result = result && definition.getName().equals(operation.getName());
		List<Variable> parameters = definition.getParameter();
		List<EParameter> eParameters = operation.getEParameters();

		result = result && parameters.size() == eParameters.size();
		if (result) {
			for (int i = 0; i < parameters.size(); i++) {
				Variable variable = parameters.get(i);
				EParameter eParameter = eParameters.get(i);
				if (variable.getName() != null) {
					result = result && variable.getName().equals(eParameter.getName());
				}
				if (variable.getEType() != null) {
					result = result && variable.getEType().equals(eParameter.getEType());
				}
			}
		}

		return result;
	}

	/**
	 * Create the resource that will be used for the serialization.
	 * 
	 * @param oURI
	 *            The URI of the resource.
	 * @param oResourceSet
	 *            The resource set
	 * @return The resource that will be used for the serialization.
	 */
	private Resource createResource(URI oURI, ResourceSet oResourceSet) {
		if (asBinaryResource) {
			return ModelUtils.createBinaryResource(oURI, oResourceSet);
		}
		return ModelUtils.createResource(oURI, oResourceSet);
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
		Set<String> allImportedFiles = new CompactHashSet<String>();
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
