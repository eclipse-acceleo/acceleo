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
package org.eclipse.acceleo.module.example.uml2java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;

/**
 * Entry point of the 'GenerateJava' generation module.
 * 
 * @author <a href="mailto:stephane.bouchet@obeo.fr">Stephane Bouchet</a>
 */
public class GenerateJava {

	/**
	 * The name of the module.
	 * 
	 * @generated
	 */
	public static final String MODULE_FILE_NAME = "generateJava";

	/**
	 * The name of the templates that are to be generated.
	 * 
	 * @generated
	 */
	public static final String[] TEMPLATE_NAMES = { "generateClass", "generateInterface", };

	/**
	 * The root element of the module.
	 */
	private Module module;

	/**
	 * The model.
	 */
	private EObject model;

	/**
	 * The output folder.
	 */
	private File targetFolder;

	/**
	 * The other arguments.
	 */
	List<? extends Object> arguments;

	/**
	 * Constructor.
	 * 
	 * @param modelURI
	 *            is the URI of the model.
	 * @param targetFolder
	 *            is the output folder
	 * @param arguments
	 *            are the other arguments
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public GenerateJava(URI modelURI, File targetFolder, List<? extends Object> arguments) throws IOException {
    ResourceSet resourceSet = new ResourceSetImpl();
    registerResourceFactories(resourceSet);
    registerPackages(resourceSet);
    final URL templateURL;
    if (EMFPlugin.IS_ECLIPSE_RUNNING) {
      templateURL = FileLocator.toFileURL(GenerateJava.class.getResource(MODULE_FILE_NAME + ".emtl"));
    } else {
      templateURL = GenerateJava.class.getResource(MODULE_FILE_NAME + ".emtl");
    }
    if (templateURL == null) {
      throw new IOException("'" + MODULE_FILE_NAME + ".emtl' not found");
    } else {
      URI templateURI = createTemplateURI(templateURL.getPath());
      module = (Module)load(templateURI, resourceSet);
      model = load(modelURI, resourceSet);
      this.targetFolder = targetFolder;
      this.arguments = arguments;
    }
  }

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            is the root element of the model.
	 * @param targetFolder
	 *            is the output folder
	 * @param arguments
	 *            are the other arguments
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public GenerateJava(EObject model, File targetFolder, List<? extends Object> arguments)
			throws IOException {
    ResourceSet resourceSet = model.eResource().getResourceSet();
    registerResourceFactories(resourceSet);
    registerPackages(resourceSet);
    final URL templateURL;
    if (EMFPlugin.IS_ECLIPSE_RUNNING) {
      templateURL = FileLocator.toFileURL(GenerateJava.class.getResource(MODULE_FILE_NAME + ".emtl"));
    } else {
      templateURL = GenerateJava.class.getResource(MODULE_FILE_NAME + ".emtl");
    }
    if (templateURL == null) {
      throw new IOException("'" + MODULE_FILE_NAME + ".emtl' not found");
    } else {
      URI templateURI = createTemplateURI(templateURL.getPath());
      module = (Module)load(templateURI, resourceSet);
      this.model = model;
      this.targetFolder = targetFolder;
      this.arguments = arguments;
    }
  }

	/**
	 * Creates the template URI.
	 * 
	 * @param entry
	 *            is the local path of the EMTL file
	 * @return an EMF URI of the file specified
	 * @generated
	 */
	protected URI createTemplateURI(String entry) {
    return URI.createFileURI(URI.decode(entry));
  }

	/**
	 * Gets the model.
	 * 
	 * @return the model root element
	 */
	public EObject getModel() {
		return model;
	}

	/**
	 * Updates the registry used for looking up a package based namespace, in the resource set.
	 * 
	 * @param resourceSet
	 *            is the resource set
	 * @generated
	 */
	private void registerPackages(ResourceSet resourceSet) {
    resourceSet.getPackageRegistry().put(org.eclipse.uml2.uml.UMLPackage.eINSTANCE.getNsURI(), org.eclipse.uml2.uml.UMLPackage.eINSTANCE);
    resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(), org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
    resourceSet.getPackageRegistry().put(org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE.getNsURI(), org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE);
    resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);
    resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", getOCLStdLibPackage());
  }

	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 * @generated
	 */
	private EPackage getOCLStdLibPackage() {
    EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
    EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
    return (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary().getBag());
  }

	/**
	 * Updates the registry used for looking up resources factory in the given resource set.
	 * 
	 * @param resourceSet
	 *            The resource set that is to be updated.
	 * @generated
	 */
	private void registerResourceFactories(ResourceSet resourceSet) {
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("emtl", new org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl());
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
  }

	/**
	 * The main method.
	 * 
	 * @param args
	 *            are the arguments
	 * @generated
	 */
	public static void main(String[] args) {
    try {
      if (args.length < 2) {
        System.out.println("Arguments not valid : {model, folder}.");
      } else {
        URI modelURI = URI.createFileURI(args[0]);
        File folder = new File(args[1]);
        List<String> arguments = new ArrayList<String>();
        for (int i = 2; i < args.length; i++) {
          arguments.add(args[i]);
        }
        GenerateJava generator = new GenerateJava(modelURI, folder, arguments);
        generator.doGenerate(new BasicMonitor());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

	/**
   * Launches the generation.
   * 
   * @param monitor
   *             This will be used to display progress information to the user.
   * @throws IOException
   *             Thrown when the output cannot be saved.
   * @generated
   */
  public void doGenerate(Monitor monitor) throws IOException {
    if (!targetFolder.exists()) {
      targetFolder.mkdirs();
    }
    for (int i = 0; i < TEMPLATE_NAMES.length; i++) {
      AcceleoService.doGenerate(module, TEMPLATE_NAMES[i], model, arguments, targetFolder, false, monitor);
    }
  }

  /**
	 * Loads a model from an {@link org.eclipse.emf.common.util.URI URI} in a given {@link ResourceSet}.
	 * <p>
	 * This will return the first root of the loaded model, other roots can be accessed via the resource's
	 * content.
	 * </p>
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The model loaded from the URI.
	 * @throws IOException
	 *             If the given file does not exist.
	 * @generated
	 */
	private EObject load(URI modelURI, ResourceSet resourceSet) throws IOException {
    EObject result = null;
    final Resource modelResource = createResource(modelURI, resourceSet);
    final Map<String, String> options = new HashMap<String, String>();
    options.put(XMLResource.OPTION_ENCODING, System.getProperty("file.encoding"));
    modelResource.load(options);
    if (modelResource.getContents().size() > 0) {
      result = modelResource.getContents().get(0);
    }
    return result;
  }

	/**
	 * This will create a {@link Resource} given the model extension it is intended for and a ResourceSet.
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The {@link Resource} given the model extension it is intended for.
	 * @generated
	 */
	private Resource createResource(URI modelURI, ResourceSet resourceSet) {
    String fileExtension = modelURI.fileExtension();
    if (fileExtension == null || fileExtension.length() == 0) {
      fileExtension = Resource.Factory.Registry.DEFAULT_EXTENSION;
    }
    final Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
    final Object resourceFactory = registry.getExtensionToFactoryMap().get(fileExtension);
    if (resourceFactory != null) {
      resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension,
          resourceFactory);
    } else {
      resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension,
          new XMIResourceFactoryImpl());
    }
    return resourceSet.createResource(modelURI);
  }

}
