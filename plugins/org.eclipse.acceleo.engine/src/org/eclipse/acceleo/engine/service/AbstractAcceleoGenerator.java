/*******************************************************************************
 * Copyright (c) 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.service;


/**
 * This will be used as the base class for all generated launchers (the java classes generated beside mtl
 * modules).
 * <p>
 * The basic behavior of subclasses will be to call a template which first argument is taken from a model
 * (we'll iterate on the whole content of the model/EObject passed in our constructor to find elements
 * matching the first parameter of said template) and optional arguments.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public abstract class AbstractAcceleoGenerator {
	// /**
	// * This will hold the list of generation listeners that are to be notified when text is generated.
	// *
	// * @deprecated return a list of generation listeners directly through {@link #getListeners()} instead.
	// */
	// @Deprecated
	// protected List<IAcceleoTextGenerationListener> generationListeners = new
	// ArrayList<IAcceleoTextGenerationListener>(
	// 1);
	//
	// /**
	// * Instantiates an Acceleo generator for a model located at a given URI.
	// *
	// * @param modelURI
	// * URI where the model on which this generator will be used is located.
	// * @param targetFolder
	// * This will be used as the output folder for this generation : it will be the base path
	// * against which all file block URLs will be resolved.
	// * @param arguments
	// * If the template which will be called requires more than one argument taken from the model,
	// * pass them here.
	// * @throws IOException
	// * Will be thrown if an output file cannot be saved.
	// */
	// public AbstractAcceleoGenerator(URI modelURI, File targetFolder, List<?> arguments) throws IOException
	// {
	// ResourceSet resourceSet = new ResourceSetImpl();
	// registerResourceFactories(resourceSet);
	// registerPackages(resourceSet);
	// addListeners();
	// addProperties();
	// final URL templateURL;
	// if (EMFPlugin.IS_ECLIPSE_RUNNING) {
	// URL resourceURL = Generate.class.getResource(MODULE_FILE_NAME + ".emtl");
	// if (resourceURL != null) {
	// templateURL = FileLocator.toFileURL(resourceURL);
	// } else {
	// templateURL = null;
	// }
	// } else {
	// templateURL = Generate.class.getResource(MODULE_FILE_NAME + ".emtl");
	// }
	// if (templateURL == null) {
	// throw new IOException("'" + MODULE_FILE_NAME + ".emtl' not found");
	// } else {
	// URI templateURI = createTemplateURI(templateURL.toString());
	// module = (Module)load(templateURI, resourceSet);
	// model = load(modelURI, resourceSet);
	// this.targetFolder = targetFolder;
	// this.arguments = arguments;
	// }
	// }
	//
	// /**
	// * Instantiates an Acceleo generator for the given EObject.
	// *
	// * @param element
	// * We'll iterate over the content of this element to find Objects matching the first parameter
	// * of the template we need to call.
	// * @param targetFolder
	// * This will be used as the output folder for this generation : it will be the base path
	// * against which all file block URLs will be resolved.
	// * @param arguments
	// * If the template which will be called requires more than one argument taken from the model,
	// * pass them here.
	// * @throws IOException
	// * Will be thrown if an output file cannot be saved.
	// */
	// public AbstractAcceleoGenerator(EObject element, File targetFolder, List<? extends Object> arguments)
	// throws IOException {
	// ResourceSet resourceSet = element.eResource().getResourceSet();
	// registerResourceFactories(resourceSet);
	// registerPackages(resourceSet);
	// addListeners();
	// addProperties();
	// final URL templateURL;
	// if (EMFPlugin.IS_ECLIPSE_RUNNING) {
	// URL resourceURL = Generate.class.getResource(MODULE_FILE_NAME + ".emtl");
	// if (resourceURL != null) {
	// templateURL = FileLocator.toFileURL(resourceURL);
	// } else {
	// templateURL = null;
	// }
	// } else {
	// templateURL = Generate.class.getResource(MODULE_FILE_NAME + ".emtl");
	// }
	// if (templateURL == null) {
	// throw new IOException("'" + MODULE_FILE_NAME + ".emtl' not found");
	// } else {
	// URI templateURI = createTemplateURI(templateURL.toString());
	// module = (Module)load(templateURI, resourceSet);
	// this.model = element;
	// this.targetFolder = targetFolder;
	// this.arguments = arguments;
	// }
	// }
	//
	// /**
	// * If this generator needs to listen to text generation events, listeners can be returned from here.
	// *
	// * @return List of listeners that are to be notified when text is generated through this launch.
	// */
	// public List<IAcceleoTextGenerationListener> getListeners() {
	// return new ArrayList<IAcceleoTextGenerationListener>(generationListeners);
	// }
	//
	// /**
	// * If the generation modules need properties files, this is where to add them. Take note that the first
	// * added properties files will take precedence over subsequent ones if they contain conflicting keys.
	// *
	// * @deprecated return a list of generation listeners directly through {@link #getListeners()} instead.
	// */
	// @Deprecated
	// protected void addProperties() {
	// // empty implementation
	// }
	//
	// /**
	// * This will update the resource set's package registry with all usual EPackages.
	// *
	// * @param resourceSet
	// * The resource set which registry has to be updated.
	// */
	// protected void registerPackages(ResourceSet resourceSet) {
	// resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
	// resourceSet.getPackageRegistry().put(GenModelPackage.eINSTANCE.getNsURI(), GenModelPackage.eINSTANCE);
	//
	// resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
	// org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
	// resourceSet.getPackageRegistry().put(ExpressionsPackage.eINSTANCE.getNsURI(),
	// ExpressionsPackage.eINSTANCE);
	//
	// resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);
	//
	//		resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
	// getOCLStdLibPackage());
	// }
	//
	// /**
	// * This will update the resource set's resource factory registry with all usual factories.
	// *
	// * @param resourceSet
	// * The resource set which registry has to be updated.
	// */
	// protected void registerResourceFactories(ResourceSet resourceSet) {
	//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
	// new EcoreResourceFactoryImpl());
	// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
	// IAcceleoConstants.EMTL_FILE_EXTENSION, new EMtlResourceFactoryImpl());
	// }
	//
	// /**
	// * Returns the package containing the OCL standard library.
	// *
	// * @return The package containing the OCL standard library.
	// */
	// private EPackage getOCLStdLibPackage() {
	// EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
	// EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
	// EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
	// .getBag());
	// environment.dispose();
	// return oclStdLibPackage;
	// }
}
